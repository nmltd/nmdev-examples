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
    static synchronized void bundle(Path output) throws IOException {
        Path target = output.resolveSibling(output.getFileName() + "-results.zip");
        Path temporary = Files.createTempFile(target.toAbsolutePath().getParent(), target.getFileName() + "-pending-", ".zip");
        List<Path> files;
        try (var walk = Files.walk(output)) { files = walk.filter(Files::isRegularFile).filter(p -> shareable(output.relativize(p))).sorted().toList(); }
        try (ZipOutputStream zip = new ZipOutputStream(Files.newOutputStream(temporary))) {
            for (Path file : files) { zip.putNextEntry(new ZipEntry(output.relativize(file).toString().replace('\\', '/'))); Files.copy(file, zip); zip.closeEntry(); }
        }
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
    }
    private Storage() {}
}
