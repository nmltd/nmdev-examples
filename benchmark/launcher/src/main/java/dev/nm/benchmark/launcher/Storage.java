package dev.nm.benchmark.launcher;

import com.google.gson.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.security.*;
import java.util.*;
import java.util.zip.*;

/** Crash-safe checkpoint writes and an explicit allowlist for shareable output. */
final class Storage {
    static final Gson JSON = new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().create();
    static JsonElement read(Path path) throws IOException { return JsonParser.parseString(Files.readString(path)); }
    static void json(Path path, Object value) throws IOException { atomic(path, JSON.toJson(value).getBytes(java.nio.charset.StandardCharsets.UTF_8)); }
    static void atomic(Path path, byte[] bytes) throws IOException {
        Files.createDirectories(path.toAbsolutePath().getParent());
        Path temp = path.resolveSibling(path.getFileName() + ".tmp");
        try (FileOutputStream out = new FileOutputStream(temp.toFile())) { out.write(bytes); out.getFD().sync(); }
        move(temp, path);
    }
    static void move(Path from, Path to) throws IOException {
        try { Files.move(from, to, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING); }
        catch (AtomicMoveNotSupportedException ex) { Files.move(from, to, StandardCopyOption.REPLACE_EXISTING); }
    }
    static String sha(Path path) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            try (InputStream in = Files.newInputStream(path)) { byte[] b = new byte[65536]; int n; while ((n = in.read(b)) != -1) md.update(b, 0, n); }
            return HexFormat.of().formatHex(md.digest());
        } catch (NoSuchAlgorithmException ex) { throw new IllegalStateException(ex); }
    }
    static String digest(Object value) {
        try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(JSON.toJson(value).getBytes(java.nio.charset.StandardCharsets.UTF_8))); }
        catch (NoSuchAlgorithmException ex) { throw new IllegalStateException(ex); }
    }
    static boolean shareable(Path relative) {
        String p = relative.toString().replace('\\', '/');
        // Runtime cache can contain a private license, downloaded binaries or credentials.
        if (p.startsWith(".runtime/") || p.startsWith(".cache/") || p.contains("..")) return false;
        String name = relative.getFileName().toString();
        return name.endsWith(".json") || name.endsWith(".jsonl") || name.endsWith(".csv")
            || name.endsWith(".log") || name.endsWith(".md") || name.endsWith(".txt") || name.equals("harness-source.zip");
    }
    /** One UTC timestamp per result directory, retained across ZIP refreshes and resumes. */
    static synchronized Path bundlePath(Path output) throws IOException {
        if(!Files.isDirectory(output))throw new NoSuchFileException(output.toString());
        Path stamp=output.resolve("zip-timestamp.txt");
        String timestamp;
        if(Files.exists(stamp))timestamp=Files.readString(stamp).trim();
        else {
            timestamp=java.time.format.DateTimeFormatter.ofPattern("uuuuMMdd-HHmmss-SSS'Z'")
                .withZone(java.time.ZoneOffset.UTC).format(java.time.Instant.now());
            atomic(stamp,(timestamp+"\n").getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
        if(!timestamp.matches("[0-9]{8}-[0-9]{6}-[0-9]{3}Z"))
            throw new IOException("Invalid ZIP timestamp in "+stamp);
        return output.resolveSibling(output.getFileName()+"-results-"+timestamp+".zip");
    }
    static synchronized void bundle(Path output) throws IOException {
        bundle(output, null);
    }
    // Snapshot callback enables deterministic tests of disappearing source files.
    static synchronized void bundle(Path output, Runnable afterSnapshot) throws IOException {
        try { bundleSnapshot(output, afterSnapshot); }
        catch (IOException | java.io.UncheckedIOException | SecurityException ex) {
            System.err.println("WARNING: Results ZIP refresh failed: " + ex
                + ". Saved checkpoints remain authoritative; benchmark continues and retries packaging at the next refresh.");
            try {
                json(output.resolve("packaging-failure.json"), Map.of(
                    "utc", java.time.Instant.now().toString(), "error", ex.toString(),
                    "status", "ZIP refresh failed; previous ZIP may be stale; local checkpoints retained"));
            } catch (IOException | SecurityException reportError) {
                System.err.println("WARNING: Could not save packaging failure report: " + reportError);
            }
        }
    }
    private static void bundleSnapshot(Path output, Runnable afterSnapshot) throws IOException {
        Path target = bundlePath(output);
        Path temporary = Files.createTempFile(target.toAbsolutePath().getParent(), target.getFileName() + "-pending-", ".partial");
        boolean complete = false;
        try {
        List<Path> files;
        try (var walk = Files.walk(output)) { files = walk.filter(Files::isRegularFile).filter(p -> shareable(output.relativize(p))).sorted().toList(); }
        if (afterSnapshot != null) afterSnapshot.run();
        try (ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(temporary))) {
            for (Path file : files) { zip.putNextEntry(new ZipEntry(output.relativize(file).toString().replace('\\', '/'))); Files.copy(file, zip); zip.closeEntry(); }
        }
        Path ready = temporary.resolveSibling(temporary.getFileName().toString().replace(".partial", ".zip"));
        move(temporary, ready);
        temporary = ready;
        complete = true;
        // Windows readers (Explorer, antivirus, sync clients) can deny replacement.
        // The complete staged ZIP is already closed; retain it rather than aborting
        // measurements or deleting the last published bundle.
        for (int attempt = 0; attempt < 4; attempt++) {
            try { move(temporary, target); return; }
            catch (FileSystemException ex) {
                if (attempt == 3) {
                    System.err.println("WARNING: Cannot replace results ZIP: " + ex.getMessage()
                        + "; latest complete bundle retained at " + temporary
                        + ". Benchmark continues; close ZIP readers to allow the next refresh.");
                    return;
                }
                try { Thread.sleep(100L * (attempt + 1)); }
                catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    System.err.println("WARNING: ZIP refresh interrupted; complete bundle retained at " + temporary);
                    return;
                }
            }
        }
        } finally {
            // An unsuccessful snapshot must not look like an uploadable ZIP.
            if (!complete) {
                try { Files.deleteIfExists(temporary); }
                catch (IOException cleanupError) { System.err.println("WARNING: Incomplete staging file remains: " + temporary); }
            }
        }
    }
    private Storage() {}
}
