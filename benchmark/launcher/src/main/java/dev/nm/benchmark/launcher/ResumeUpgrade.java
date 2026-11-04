package dev.nm.benchmark.launcher;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.channels.*;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.jar.*;

/** Audited 1.3.0 -> 1.3.1 export/resume upgrade. Never edits a saved fork. */
public final class ResumeUpgrade {
    static final String ORIGINAL_SHA="a6b58052fedb17ea91f4c2812ac816708c700dfc386a9e3019306e9a26fde9cf";
    public static void main(String[] args) throws Exception {
        if(args.length!=2)throw new IllegalArgumentException("Usage: ResumeUpgrade original-1.3.0.jar output-directory");
        Path oldJar=Path.of(args[0]).toAbsolutePath(),output=Path.of(args[1]).toAbsolutePath(),newJar=Dependencies.application();
        if(!ORIGINAL_SHA.equals(Storage.sha(oldJar)))throw new IOException("Original application is not the published 1.3.0 binary");
        try(var channel=FileChannel.open(output.resolve(".runner.lock"),StandardOpenOption.CREATE,StandardOpenOption.WRITE);
            FileLock lock=channel.tryLock()) {
            if(lock==null)throw new IOException("Stop the active benchmark before upgrading resume/export code");
            Path manifest=output.resolve("request.json");JsonObject request=Storage.read(manifest).getAsJsonObject();
            String newHash=Storage.sha(newJar),oldHash=request.get("application_sha256").getAsString();
            if(newHash.equals(oldHash)) {System.out.println("This run already uses the faster resume implementation.");return;}
            if(!ORIGINAL_SHA.equals(oldHash))throw new IOException("Saved run did not use the published 1.3.0 application");
            verify(oldJar,newJar);
            Path backup=output.resolve("request-before-resume-"+oldHash+".json");
            if(!Files.exists(backup))Storage.atomic(backup,Files.readAllBytes(manifest));
            Storage.json(output.resolve("resume-upgrade-"+newHash+".json"),Map.of("utc",Instant.now().toString(),
                "old_application_sha256",oldHash,"new_application_sha256",newHash,
                "verification","Numerical harness, dependencies, case model and other application classes byte-identical; audited resume/export classes and application version metadata only; saved forks untouched"));
            request.addProperty("application_sha256",newHash);Storage.json(manifest,request);
            System.out.println("Verified resume/export upgrade. Existing fork checkpoints were preserved.");
        }
    }
    static void verify(Path oldJar,Path newJar) throws IOException {
        String prefix="dev/nm/benchmark/launcher/";
        Set<String> allowed=Set.of(prefix+"BenchmarkMain.class",prefix+"Storage.class",prefix+"ResumeUpgrade.class");
        Set<String> versioned=Set.of("META-INF/maven/dev.nm/nmdev-benchmark/pom.xml","META-INF/maven/dev.nm/nmdev-benchmark/pom.properties");
        try(JarFile oldFile=new JarFile(oldJar.toFile());JarFile newFile=new JarFile(newJar.toFile())) {
            Map<String,JarEntry> oldEntries=entries(oldFile),newEntries=entries(newFile);
            Set<String> names=new TreeSet<>(oldEntries.keySet());names.addAll(newEntries.keySet());
            for(String name:names) {
                if(allowed.contains(name))continue;
                JarEntry before=oldEntries.get(name),after=newEntries.get(name);
                if(before==null || after==null)throw new IOException("Unexpected JAR entry difference: "+name);
                try(var a=oldFile.getInputStream(before);var b=newFile.getInputStream(after)) {
                    byte[] previous=a.readAllBytes(),next=b.readAllBytes();
                    if(Arrays.equals(previous,next))continue;
                    if(versioned.contains(name) && new String(previous,java.nio.charset.StandardCharsets.UTF_8).replace("1.3.0","1.3.1").equals(new String(next,java.nio.charset.StandardCharsets.UTF_8)))continue;
                    throw new IOException("Measurement/dependency content changed: "+name);
                }
            }
        }
    }
    private static Map<String,JarEntry> entries(JarFile jar) {
        Map<String,JarEntry> result=new HashMap<>();jar.stream().filter(e->!e.isDirectory()).forEach(e->result.put(e.getName(),e));return result;
    }
    private ResumeUpgrade() {}
}
