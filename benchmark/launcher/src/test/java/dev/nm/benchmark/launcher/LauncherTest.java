package dev.nm.benchmark.launcher;

import com.google.gson.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

class LauncherTest {
    @TempDir Path temporary;
    @Test void fullPlanIncludesEveryLibraryAtEveryProfile() {
        var plan=BenchmarkMain.plan(false,BenchmarkMain.LIBRARIES,16);
        assertEquals(1260,plan.size()); assertEquals(1260,plan.stream().map(BenchmarkMain.Case::id).distinct().count());
        assertEquals(312,plan.stream().filter(c->c.profile().equals("baseline")).count());
        assertEquals(48,plan.stream().filter(c->c.profile().equals("rectangular")).count());
        for(int threads:new int[]{1,4})for(String library:BenchmarkMain.LIBRARIES)
            assertEquals(15,plan.stream().filter(c->c.profile().equals("large")&&c.threads()==threads&&c.library().equals(library)).count());
    }
    @Test void machineCapacityProfilesRemainDistinctAndRouteRectangularCases() {
        for(int available:new int[]{1,4,16}) {
            var plan=BenchmarkMain.plan(false,BenchmarkMain.LIBRARIES,available);
            var all=plan.stream().filter(c->c.profile().endsWith("-all-cores")).toList();
            assertEquals(540,all.size());
            assertTrue(all.stream().allMatch(c->c.threads()==available));
            assertEquals(plan.size(),plan.stream().map(BenchmarkMain.Case::id).distinct().count());
            var rectangular=all.stream().filter(c->c.profile().startsWith("rectangular")).findFirst().orElseThrow();
            assertTrue(rectangular.benchmark().contains("ExpandedRectangularBenchmark"));
            assertTrue(rectangular.parameters().containsKey("shape"));
            for(String library:BenchmarkMain.LIBRARIES) {
                assertEquals(15,all.stream().filter(c->c.profile().equals("large-all-cores")&&c.library().equals(library)).count());
            }
        }
        assertThrows(IllegalArgumentException.class,()->BenchmarkMain.plan(false,BenchmarkMain.LIBRARIES,0));
    }
    @Test void timestampedZipNameIsPortableAndStableAcrossRefreshAndResume() throws Exception {
        Path output=temporary.resolve("timestamped");Files.createDirectories(output);
        Path first=Storage.bundlePath(output);
        assertTrue(first.getFileName().toString().matches("timestamped-results-[0-9]{8}-[0-9]{6}-[0-9]{3}Z\\.zip"));
        Storage.bundle(output);
        assertEquals(first,Storage.bundlePath(output));
        assertTrue(Files.isRegularFile(first));
        // The timestamp is persisted, not held in process memory.
        assertEquals(first,output.resolveSibling("timestamped-results-"+Files.readString(output.resolve("zip-timestamp.txt")).trim()+".zip"));
        Storage.bundle(output);
        try(var files=Files.list(temporary)) {
            assertEquals(1,files.filter(p->p.getFileName().toString().endsWith(".zip")).count());
        }
    }
    @Test void checkpointRetainsFullPrecisionAndRejectsCorruption() throws Exception {
        var item=new BenchmarkMain.Case("large",4,"EJML","qr","2000");
        JsonObject row=new JsonObject();row.add("params",Storage.JSON.toJsonTree(item.parameters()));row.addProperty("forks",1);row.addProperty("warmupIterations",2);row.addProperty("measurementIterations",3);
        JsonObject metric=new JsonObject();metric.addProperty("score",1.23456789012345);metric.add("rawData",Storage.JSON.toJsonTree(new double[][]{{1.1,2.2,3.3}}));row.add("primaryMetric",metric);
        Path json=temporary.resolve("fork.json"),binary=temporary.resolve("fork.bin"),proof=temporary.resolve("proof.json");
        Storage.json(json,List.of(row));Files.writeString(binary,"test binary");Storage.json(proof,Map.of("json_sha256",Storage.sha(json),"binary_sha256",Storage.sha(binary)));
        assertTrue(BenchmarkMain.validFork(json,binary,proof,item));
        assertEquals(1.23456789012345,Storage.read(json).getAsJsonArray().get(0).getAsJsonObject().getAsJsonObject("primaryMetric").get("score").getAsDouble());
        Files.writeString(binary,"interrupted/corrupted data");assertFalse(BenchmarkMain.validFork(json,binary,proof,item));
    }
    @Test void zipContainsMachineAndResultsButExcludesPrivateRuntime() throws Exception {
        Path output=temporary.resolve("output");Files.createDirectories(output.resolve(".runtime"));
        Files.writeString(output.resolve(".runtime/nm.lic"),"private");Files.writeString(output.resolve(".runtime/token.json"),"private");Files.writeString(output.resolve(".runtime/library.jar"),"private");
        Storage.json(output.resolve("machine.json"),MachineInfo.collect());Storage.json(output.resolve("progress.json"),Map.of("complete",false));
        Storage.bundle(output);
        try(ZipFile zip=new ZipFile(Storage.bundlePath(output).toFile())) {
            assertNotNull(zip.getEntry("machine.json"));assertNotNull(zip.getEntry("progress.json"));
            assertTrue(Collections.list(zip.entries()).stream().noneMatch(e->e.getName().contains(".runtime")||e.getName().endsWith(".lic")||e.getName().endsWith(".jar")));
        }
    }
    @Test void unavailableZipDestinationRetainsCompleteBundleAndCheckpoints() throws Exception {
        Path output=temporary.resolve("locked");Files.createDirectories(output);
        Storage.json(output.resolve("machine.json"),Map.of("test",true));
        Path proof=output.resolve("fork-checkpoint.json");Storage.json(proof,Map.of("saved",true));
        String before=Files.readString(proof);
        // A nonempty directory deterministically denies replacement on Linux and Windows.
        Path target=Storage.bundlePath(output);Files.createDirectories(target);
        Files.writeString(target.resolve("reader"),"do not replace");
        assertDoesNotThrow(()->Storage.bundle(output));
        assertEquals(before,Files.readString(proof));
        try(var files=Files.list(temporary)) {
            Path pending=files.filter(f->f.getFileName().toString().startsWith(target.getFileName()+"-pending-")).findFirst().orElseThrow();
            try(ZipFile zip=new ZipFile(pending.toFile())) {assertNotNull(zip.getEntry("machine.json"));}
        }
        Files.delete(target.resolve("reader"));Files.delete(target);
        Storage.bundle(output);
        try(ZipFile zip=new ZipFile(target.toFile())) {assertNotNull(zip.getEntry("fork-checkpoint.json"));}
    }
    @Test void packagingUpgradeRejectsAnyMeasurementChange() throws Exception {
        Path original=temporary.resolve("old.jar"),patched=temporary.resolve("new.jar"),changed=temporary.resolve("changed.jar");
        writeTestJar(original,"same","old");writeTestJar(patched,"same","fixed");writeTestJar(changed,"different","fixed");
        assertDoesNotThrow(()->PackagingResume.verifyPackagingOnly(original,patched));
        assertThrows(java.io.IOException.class,()->PackagingResume.verifyPackagingOnly(original,changed));
    }
    private void writeTestJar(Path path,String harness,String storage) throws Exception {
        try(var zip=new java.util.jar.JarOutputStream(Files.newOutputStream(path))) {
            for(var e:Map.of("harness-src/MatrixBenchmark.java",harness,"dev/nm/benchmark/launcher/Storage.class",storage).entrySet()) {
                zip.putNextEntry(new java.util.jar.JarEntry(e.getKey()));zip.write(e.getValue().getBytes(java.nio.charset.StandardCharsets.UTF_8));zip.closeEntry();
            }
        }
    }
    @Test void disappearingSnapshotFileDoesNotAbortOrReplacePreviousZip() throws Exception {
        Path output=temporary.resolve("vanishing");Files.createDirectories(output);
        Storage.json(output.resolve("machine.json"),Map.of("test",true));
        Path source=output.resolve("fork-2.json");Files.writeString(source,"saved fork");
        Storage.bundle(output);
        Path target=Storage.bundlePath(output);
        byte[] previous=Files.readAllBytes(target);
        assertDoesNotThrow(()->Storage.bundle(output,()->{
            try { Files.delete(source); } catch(java.io.IOException ex) { throw new java.io.UncheckedIOException(ex); }
        }));
        assertArrayEquals(previous,Files.readAllBytes(target));
        assertTrue(Files.readString(output.resolve("packaging-failure.json")).contains("NoSuchFileException"));
        try(var files=Files.list(temporary)) {
            assertFalse(files.anyMatch(p->p.getFileName().toString().endsWith(".partial")));
        }
        Files.writeString(source,"recovered fork");Storage.bundle(output);
        try(ZipFile zip=new ZipFile(target.toFile())) {
            assertNotNull(zip.getEntry("packaging-failure.json"));
            assertNotNull(zip.getEntry("fork-2.json"));
        }
    }
    @Test void packagingIoFailureDoesNotStopCheckpointWork() throws Exception {
        Path output=temporary.resolve("absent-output");
        assertDoesNotThrow(()->Storage.bundle(output));
        assertTrue(Files.exists(output.resolve("packaging-failure.json")));
        Path checkpoint=output.resolve("next-checkpoint.json");
        Storage.json(checkpoint,Map.of("saved",true));Storage.bundle(output);
        try(ZipFile zip=new ZipFile(Storage.bundlePath(output).toFile())) {
            assertNotNull(zip.getEntry("next-checkpoint.json"));
        }
    }
    @Test void machineIdentityDoesNotChangeWithFreeMemoryOrTimestamp() {
        var first=MachineInfo.collect();var second=new LinkedHashMap<>(first);second.put("recorded_utc","later");second.put("free_physical_memory_bytes_at_start",0);
        assertEquals(MachineInfo.identity(first),MachineInfo.identity(second));assertNotNull(first.get("cpu_model"));assertNotNull(first.get("available_processors"));assertNotNull(first.get("physical_memory_bytes"));
    }
}
