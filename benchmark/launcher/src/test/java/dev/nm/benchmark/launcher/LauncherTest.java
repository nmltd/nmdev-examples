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
        var plan=BenchmarkMain.plan(false,BenchmarkMain.LIBRARIES);
        assertEquals(660,plan.size()); assertEquals(660,plan.stream().map(BenchmarkMain.Case::id).distinct().count());
        assertEquals(286,plan.stream().filter(c->c.profile().equals("baseline")).count());
        assertEquals(44,plan.stream().filter(c->c.profile().equals("rectangular")).count());
        for(int threads:new int[]{1,4})for(String library:BenchmarkMain.LIBRARIES)
            assertEquals(15,plan.stream().filter(c->c.profile().equals("large")&&c.threads()==threads&&c.library().equals(library)).count());
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
        try(ZipFile zip=new ZipFile(temporary.resolve("output-results.zip").toFile())) {
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
        Path target=temporary.resolve("locked-results.zip");Files.createDirectories(target);
        Files.writeString(target.resolve("reader"),"do not replace");
        assertDoesNotThrow(()->Storage.bundle(output));
        assertEquals(before,Files.readString(proof));
        try(var files=Files.list(temporary)) {
            Path pending=files.filter(f->f.getFileName().toString().startsWith("locked-results.zip-pending-")).findFirst().orElseThrow();
            try(ZipFile zip=new ZipFile(pending.toFile())) {assertNotNull(zip.getEntry("machine.json"));}
        }
        Files.delete(target.resolve("reader"));Files.delete(target);
        Storage.bundle(output);
        try(ZipFile zip=new ZipFile(target.toFile())) {assertNotNull(zip.getEntry("fork-checkpoint.json"));}
    }
    @Test void machineIdentityDoesNotChangeWithFreeMemoryOrTimestamp() {
        var first=MachineInfo.collect();var second=new LinkedHashMap<>(first);second.put("recorded_utc","later");second.put("free_physical_memory_bytes_at_start",0);
        assertEquals(MachineInfo.identity(first),MachineInfo.identity(second));assertNotNull(first.get("cpu_model"));assertNotNull(first.get("available_processors"));assertNotNull(first.get("physical_memory_bytes"));
    }
}
