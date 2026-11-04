package dev.nm.benchmark.launcher;

import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;
import java.util.jar.JarFile;

/** A selected binary is identified by its bytes, independently of its filename. */
record NmdevInput(Path path, String sha256, String version) {
    static final String RUNTIME_NAME = "nmdev-selected.jar";
    static NmdevInput open(Path source) throws IOException {
        Path path=source.toAbsolutePath().normalize();
        if(!Files.isRegularFile(path)) throw new IOException("NMDEV JAR does not exist: "+path);
        String version="";
        try(JarFile jar=new JarFile(path.toFile())) {
            if(jar.getJarEntry("dev/nm/algebra/linear/matrix/doubles/matrixtype/dense/DenseMatrix.class")==null)
                throw new IOException("Selected JAR does not contain NMDEV DenseMatrix: "+path);
            var entry=jar.getJarEntry("META-INF/maven/dev.nm/nmdev/pom.properties");
            if(entry!=null) {
                Properties properties=new Properties();
                try(var input=jar.getInputStream(entry)) {properties.load(input);}
                version=properties.getProperty("version","");
            }
            if(version.isBlank() && jar.getManifest()!=null)
                version=jar.getManifest().getMainAttributes().getValue("Implementation-Version");
        }
        return new NmdevInput(path,Storage.sha(path),version==null?"":version);
    }
    String label() {return version.isBlank()?"NM Dev (selected JAR; SHA-256 "+sha256+")":"NM Dev "+version;}
    static void requireCompatibleRun(Path output,String sha256) throws IOException {
        Path request=output.resolve("request.json");
        if(Files.exists(request)) {
            var artifacts=Storage.read(request).getAsJsonObject().getAsJsonObject("artifacts");
            if(artifacts==null || !artifacts.has(RUNTIME_NAME) || !sha256.equals(artifacts.get(RUNTIME_NAME).getAsString()))
                throw new IOException("NMDEV JAR differs from this run. Use a new --output directory; saved results were preserved.");
        }
    }
    void install(Path destination) throws IOException {
        Files.createDirectories(destination.getParent());
        // One private, fixed alias prevents an old version sorting ahead on the classpath.
        try(var files=Files.list(destination.getParent())) {
            if(files.anyMatch(p->p.getFileName().toString().startsWith("nmdev-") && p.toString().endsWith(".jar") && !p.equals(destination)))
                throw new IOException("Old NMDEV JARs exist in the runtime cache. Use a new --output directory.");
        }
        if(!Files.exists(destination) || !Files.isSameFile(path,destination)) {
            Path temporary=destination.resolveSibling(destination.getFileName()+".copy");
            Files.copy(path,temporary,StandardCopyOption.REPLACE_EXISTING);
            if(!sha256.equals(Storage.sha(temporary))) {Files.deleteIfExists(temporary);throw new IOException("Selected NMDEV JAR changed while copying");}
            Storage.move(temporary,destination);
        } else if(!sha256.equals(Storage.sha(destination))) throw new IOException("Selected NMDEV JAR changed while preparing the run");
    }
}
