package dev.nm.benchmark.launcher;

import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class ResumeTest {
    @TempDir Path temporary;
    @Test void periodicArchivesDoNotRecompressSavedCheckpointsButFinalSnapshotIsImmediate() throws Exception {
        Path output=temporary.resolve("run");Files.createDirectories(output);
        Storage.json(output.resolve("checkpoint.json"),Map.of("saved",1));
        Storage.bundlePeriodically(output);
        Path zip=Storage.bundlePath(output);byte[] initial=Files.readAllBytes(zip);
        Storage.json(output.resolve("checkpoint.json"),Map.of("saved",2));
        for(int i=0;i<640;i++)Storage.bundlePeriodically(output);
        assertArrayEquals(initial,Files.readAllBytes(zip));
        assertTrue(Files.readString(output.resolve("checkpoint.json")).contains("2"));
        Storage.bundle(output);
        assertFalse(Arrays.equals(initial,Files.readAllBytes(zip)));
    }
    @Test void upgradeRejectsChangedNumericalSourcesAndDependencyBytes() throws Exception {
        Path old=jar("old.jar",Map.of("harness-src/MatrixBenchmark.java","original","dep.class","same","dev/nm/benchmark/launcher/BenchmarkMain.class","old"));
        Path good=jar("good.jar",Map.of("harness-src/MatrixBenchmark.java","original","dep.class","same","dev/nm/benchmark/launcher/BenchmarkMain.class","new","dev/nm/benchmark/launcher/ResumeUpgrade.class","helper"));
        Path sourceChanged=jar("source.jar",Map.of("harness-src/MatrixBenchmark.java","changed","dep.class","same"));
        Path dependencyChanged=jar("dep.jar",Map.of("harness-src/MatrixBenchmark.java","original","dep.class","different"));
        assertDoesNotThrow(()->ResumeUpgrade.verify(old,good));
        assertThrows(java.io.IOException.class,()->ResumeUpgrade.verify(old,sourceChanged));
        assertThrows(java.io.IOException.class,()->ResumeUpgrade.verify(old,dependencyChanged));
    }
    @Test void resultOrderingIsStableWhenAnEarlierCaseIsRecoveredAfterLaterCompletedCases() {
        var early=new BenchmarkMain.Case("baseline",1,"NMDEV","scale","64");
        var later=new BenchmarkMain.Case("baseline",1,"EJML","scale","64");
        var a=new com.google.gson.JsonObject();a.addProperty("case_id",early.id());
        var b=new com.google.gson.JsonObject();b.addProperty("case_id",later.id());
        Map<String,com.google.gson.JsonObject> results=new LinkedHashMap<>();results.put(later.id(),b);results.put(early.id(),a);
        var ordered=BenchmarkMain.orderedResults(List.of(early,later),results);
        assertEquals(early.id(),ordered.get(0).getAsJsonObject().get("case_id").getAsString());
        assertEquals(later.id(),ordered.get(1).getAsJsonObject().get("case_id").getAsString());
    }
    private Path jar(String name,Map<String,String> entries) throws Exception {
        Path path=temporary.resolve(name);
        try(var jar=new JarOutputStream(Files.newOutputStream(path))) {
            for(var entry:entries.entrySet()) {
                jar.putNextEntry(new JarEntry(entry.getKey()));jar.write(entry.getValue().getBytes(java.nio.charset.StandardCharsets.UTF_8));jar.closeEntry();
            }
        }
        return path;
    }
}
