package dev.nm.benchmark.launcher;

import java.lang.management.*;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

/** Whitelisted machine metadata; no usernames, hostnames, environment dumps or tokens. */
final class MachineInfo {
    static Map<String,Object> collect() {
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("recorded_utc", Instant.now().toString());
        for (String key : List.of("os.name","os.version","os.arch","java.version","java.vendor","java.vm.name","java.vm.version")) m.put(key, System.getProperty(key));
        m.put("available_processors", Runtime.getRuntime().availableProcessors());
        m.put("jvm_max_heap_bytes", Runtime.getRuntime().maxMemory());
        var os = ManagementFactory.getOperatingSystemMXBean();
        if (os instanceof com.sun.management.OperatingSystemMXBean) {
            var extended = (com.sun.management.OperatingSystemMXBean) os;
            m.put("physical_memory_bytes", extended.getTotalMemorySize());
            m.put("free_physical_memory_bytes_at_start", extended.getFreeMemorySize());
            m.put("swap_total_bytes", extended.getTotalSwapSpaceSize());
        } else m.put("physical_memory_bytes", "unavailable");
        String cpu = "unavailable";
        try {
            Path proc = Path.of("/proc/cpuinfo");
            if (Files.exists(proc)) {
                List<String> lines = Files.readAllLines(proc);
                cpu = lines.stream().filter(s -> s.startsWith("model name") || s.startsWith("Hardware")).map(s -> s.substring(s.indexOf(':') + 1).trim()).findFirst().orElse("unavailable");
                Set<String> cores = new HashSet<>(); String socket = "0";
                for (String line : lines) {
                    if (line.startsWith("physical id")) socket = line.substring(line.indexOf(':') + 1).trim();
                    if (line.startsWith("core id")) cores.add(socket + ":" + line.substring(line.indexOf(':') + 1).trim());
                }
                if (!cores.isEmpty()) m.put("visible_physical_cores", cores.size());
            } else if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("mac")) {
                cpu = command("/usr/sbin/sysctl", "-n", "machdep.cpu.brand_string");
                m.put("physical_cores", command("/usr/sbin/sysctl", "-n", "hw.physicalcpu"));
            } else if (System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win")) {
                cpu = command("powershell.exe", "-NoProfile", "-NonInteractive", "-Command", "(Get-CimInstance Win32_Processor | Select-Object -ExpandProperty Name) -join '; '");
            }
        } catch (Exception ex) { m.put("cpu_detection_note", ex.getClass().getSimpleName()); }
        m.put("cpu_model", cpu);
        for (String name : List.of("cpu.max", "memory.max", "memory.events", "pids.events")) {
            try { Path p = Path.of("/sys/fs/cgroup", name); if (Files.exists(p)) m.put("cgroup_" + name, Files.readString(p).trim()); }
            catch (Exception ignored) { m.put("cgroup_" + name, "unavailable"); }
        }
        m.put("native_thread_counts_independently_audited", false);
        m.put("cpu_affinity", "not set by portable launcher");
        return m;
    }
    private static String command(String... args) throws Exception {
        Process p = new ProcessBuilder(args).redirectErrorStream(true).start();
        if (!p.waitFor(5, TimeUnit.SECONDS)) { p.destroyForcibly(); return "unavailable (query timeout)"; }
        return p.exitValue() == 0 ? new String(p.getInputStream().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8).trim() : "unavailable";
    }
    static Map<String,Object> identity(Map<String,Object> info) {
        Map<String,Object> stable = new TreeMap<>();
        for (String key : List.of("os.name","os.version","os.arch","java.version","java.vendor","java.vm.version","cpu_model","available_processors","physical_memory_bytes","cgroup_cpu.max","cgroup_memory.max")) stable.put(key, info.get(key));
        return stable;
    }
    private MachineInfo() {}
}
