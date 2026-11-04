package dev.nm.benchmark.launcher;

import java.nio.file.*;
import java.util.Map;
import java.util.jar.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class NmdevInputTest {
    @TempDir Path temporary;
    Path jar(String name,String version,String bytes) throws Exception {
        Path path=temporary.resolve(name);
        try(var jar=new JarOutputStream(Files.newOutputStream(path))) {
            jar.putNextEntry(new JarEntry("dev/nm/algebra/linear/matrix/doubles/matrixtype/dense/DenseMatrix.class"));
            jar.write(bytes.getBytes(java.nio.charset.StandardCharsets.UTF_8));jar.closeEntry();
            jar.putNextEntry(new JarEntry("META-INF/maven/dev.nm/nmdev/pom.properties"));
            jar.write(("version="+version+"\n").getBytes(java.nio.charset.StandardCharsets.UTF_8));jar.closeEntry();
        }
        return path;
    }
    @Test void acceptsArbitraryFilenameAndCopiesTheExactSelectedBytes() throws Exception {
        var selected=NmdevInput.open(jar("my latest binary.jar","1.2.10","test bytes"));
        Path runtime=temporary.resolve("output/.runtime/lib/nmdev-selected.jar");
        selected.install(runtime);
        assertEquals(Storage.sha(selected.path()),Storage.sha(runtime));
        assertEquals("NM Dev 1.2.10",selected.label());
        selected.install(runtime); // Resume with the same bytes is idempotent.
        NmdevInput.open(runtime).install(runtime); // The caller may select the cached file itself.
    }
    @Test void rejectsAChangedBinaryBeforeTouchingAnExistingRun() throws Exception {
        var first=NmdevInput.open(jar("old.jar","1.2.9","original"));
        var next=NmdevInput.open(jar("new.jar","1.2.10","changed"));
        Path output=temporary.resolve("saved");Files.createDirectories(output);
        Storage.json(output.resolve("request.json"),Map.of("artifacts",Map.of(NmdevInput.RUNTIME_NAME,first.sha256())));
        byte[] saved=Files.readAllBytes(output.resolve("request.json"));
        NmdevInput.requireCompatibleRun(output,first.sha256());
        assertThrows(java.io.IOException.class,()->NmdevInput.requireCompatibleRun(output,next.sha256()));
        assertArrayEquals(saved,Files.readAllBytes(output.resolve("request.json")));
    }
    @Test void refusesInvalidOrMissingInputAndOldShadowingJars() throws Exception {
        Path wrong=temporary.resolve("not-a-jar.jar");Files.writeString(wrong,"invalid");
        assertThrows(java.io.IOException.class,()->NmdevInput.open(wrong));
        assertThrows(java.io.IOException.class,()->NmdevInput.open(temporary.resolve("missing.jar")));
        var selected=NmdevInput.open(jar("selected.jar","1.2.10","valid selection"));
        Path lib=temporary.resolve("lib");Files.createDirectories(lib);
        Files.copy(selected.path(),lib.resolve("nmdev-1.2.9.jar"));
        assertThrows(java.io.IOException.class,()->selected.install(lib.resolve(NmdevInput.RUNTIME_NAME)));
        assertFalse(Files.exists(lib.resolve(NmdevInput.RUNTIME_NAME)));
    }
}
