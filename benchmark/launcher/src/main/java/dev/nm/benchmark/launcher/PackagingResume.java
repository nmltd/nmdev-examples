package dev.nm.benchmark.launcher;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.channels.*;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.jar.*;

/** Explicit, audited migration for the ZIP-writer fix only; never changes measurements. */
public final class PackagingResume {
    public static void main(String[] args) throws Exception {
        if (args.length < 1 || args.length > 2)
            throw new IllegalArgumentException("Usage: PackagingResume original-application.jar [output-directory]");
        Path oldJar=Path.of(args[0]).toAbsolutePath();
        Path output=Path.of(args.length==2?args[1]:"nmdev-benchmark-output").toAbsolutePath();
        Path manifest=output.resolve("request.json"), currentJar=Dependencies.application();
        try(FileChannel channel=FileChannel.open(output.resolve(".runner.lock"),StandardOpenOption.CREATE,StandardOpenOption.WRITE);
            FileLock lock=channel.tryLock()) {
            if(lock==null) throw new IOException("Stop the benchmark before upgrading its packaging code");
            JsonObject request=Storage.read(manifest).getAsJsonObject();
            String oldHash=Storage.sha(oldJar),newHash=Storage.sha(currentJar);
            if(!request.get("application_sha256").getAsString().equals(oldHash))
                throw new IOException("Original JAR does not match saved request; no checkpoint changes made");
            verifyPackagingOnly(oldJar,currentJar);
            Path backup=output.resolve("request-before-packaging-"+oldHash+".json");
            if(!Files.exists(backup)) Storage.atomic(backup,Files.readAllBytes(manifest));
            Storage.json(output.resolve("packaging-upgrade-"+newHash+".json"),Map.of(
                "utc",Instant.now().toString(),"old_application_sha256",oldHash,"new_application_sha256",newHash,
                "verification","All JAR file contents identical except Storage.class and added PackagingResume.class; fork checkpoints untouched"));
            request.addProperty("application_sha256",newHash);
            Storage.json(manifest,request);
            System.out.println("Verified packaging-only upgrade. Saved forks were not modified. Run the new JAR normally to resume.");
        }
    }
    static void verifyPackagingOnly(Path oldJar,Path newJar) throws IOException {
        String prefix="dev/nm/benchmark/launcher/";
        Set<String> allowed=Set.of(prefix+"Storage.class",prefix+"PackagingResume.class");
        try(JarFile oldFile=new JarFile(oldJar.toFile());JarFile newFile=new JarFile(newJar.toFile())) {
            Map<String,JarEntry> oldEntries=entries(oldFile),newEntries=entries(newFile);
            Set<String> names=new TreeSet<>(oldEntries.keySet());names.addAll(newEntries.keySet());
            for(String name:names) {
                if(allowed.contains(name)) continue;
                JarEntry a=oldEntries.get(name),b=newEntries.get(name);
                if(a==null||b==null) throw new IOException("Non-packaging JAR entry changed: "+name);
                try(var x=oldFile.getInputStream(a);var y=newFile.getInputStream(b)) {
                    if(!Arrays.equals(x.readAllBytes(),y.readAllBytes())) throw new IOException("Non-packaging JAR content changed: "+name);
                }
            }
        }
    }
    private static Map<String,JarEntry> entries(JarFile file) {
        Map<String,JarEntry> result=new HashMap<>();
        file.stream().filter(e->!e.isDirectory()).forEach(e->result.put(e.getName(),e));return result;
    }
    private PackagingResume() {}
}
