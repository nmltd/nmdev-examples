package dev.nm.benchmark.launcher;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;
import javax.tools.*;

/** Prepares immutable public inputs without Maven credentials, Python or Git. */
final class Dependencies {
    static final String NM_URL = "https://github.com/nmltd/maven-packages/releases/download/nmdev-1.2.6-validated/nmdev-1.2.6.jar";
    static final String NM_SHA = "0b2d807d17927b482fadcdc313768a61d2fbdc80c7da4226005a198e0a6796ba";
    static final String LICENSE_URL = "https://raw.githubusercontent.com/nmltd/nmdev-examples/ad1fbacaeea9c92ead37beb3002566c33b5a6006/nm.lic";
    static final String LICENSE_SHA = "2fbc9e56f7d8d933bfd7c59c39cf08bee151305b285f35a19882cc2c844bb1c1";
    static final String COLT_URL = "https://codeload.github.com/rwl/ParallelColt/zip/7c34364026680c1113f1a9586b9fb8c55080330c";
    static final String COLT_SHA = "a083064fd6f36780a7b4be15b80e5146c9b21f656ff76a6fb392045bea46388e";
    static Path application() throws Exception { return Path.of(BenchmarkMain.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toAbsolutePath(); }
    static void resource(String name, Path destination) throws IOException {
        try (InputStream in = BenchmarkMain.class.getResourceAsStream("/" + name)) {
            if (in == null) throw new IOException("Missing packaged resource: " + name);
            Storage.atomic(destination, in.readAllBytes());
        }
    }
    static void download(String url, String sha, Path path) throws Exception {
        if (Files.isRegularFile(path) && Storage.sha(path).equals(sha)) return;
        Files.createDirectories(path.getParent());
        Exception last = null;
        HttpClient.Builder clientBuilder = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).connectTimeout(Duration.ofSeconds(30));
        // Java does not automatically honor the proxy environment used by curl/Maven wrappers.
        String proxy = System.getenv("HTTPS_PROXY");
        if (proxy == null) proxy = System.getenv("https_proxy");
        if (proxy != null && !proxy.isBlank()) {
            URI address;
            try { address = URI.create(proxy); } catch (IllegalArgumentException ex) { throw new IOException("Invalid HTTPS_PROXY configuration"); }
            if (address.getHost() == null) throw new IOException("Invalid HTTPS_PROXY host");
            clientBuilder.proxy(ProxySelector.of(new InetSocketAddress(address.getHost(), address.getPort() < 0 ? 80 : address.getPort())));
            if (address.getUserInfo() != null) {
                String[] credentials = address.getUserInfo().split(":", 2);
                clientBuilder.authenticator(new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return getRequestorType() == RequestorType.PROXY ? new PasswordAuthentication(credentials[0], (credentials.length > 1 ? credentials[1] : "").toCharArray()) : null;
                    }
                });
            }
        }
        HttpClient client = clientBuilder.build();
        for (int attempt = 1; attempt <= 3; attempt++) {
            Path temporary = path.resolveSibling(path.getFileName() + ".download");
            try {
                System.out.println("Downloading " + path.getFileName() + " (attempt " + attempt + ")");
                var response = client.send(HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofMinutes(3)).header("User-Agent", "nmdev-benchmark/1.1.0").build(), HttpResponse.BodyHandlers.ofFile(temporary, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE));
                if (response.statusCode() != 200) throw new IOException("HTTP " + response.statusCode() + " from " + url);
                if (!Storage.sha(temporary).equals(sha)) throw new IOException("SHA-256 mismatch for " + path.getFileName());
                Storage.move(temporary, path); return;
            } catch (Exception ex) { last = ex; Files.deleteIfExists(temporary); if (attempt < 3) Thread.sleep(attempt * 1000L); }
        }
        throw new IOException("Unable to prepare verified dependency " + path.getFileName(), last);
    }
    static Map<String,String> prepare(Path output) throws Exception {
        Path runtime = output.resolve(".runtime"), lib = runtime.resolve("lib");
        Files.createDirectories(lib);
        resource("config/dependencies.lock.tsv", output.resolve("dependencies.lock.tsv.txt"));
        resource("config/versions.json", output.resolve("versions.json"));
        String lock = Files.readString(output.resolve("dependencies.lock.tsv.txt"));
        Map<String,String> hashes = new TreeMap<>();
        for (String line : lock.split("\\R")) {
            if (line.isBlank() || line.startsWith("#")) continue;
            String[] values = line.split("\\t");
            if (values.length != 3) throw new IOException("Malformed dependency lock entry");
            if (!values[2].startsWith("source:")) download(values[2], values[1], lib.resolve(values[0]));
        }
        download(NM_URL, NM_SHA, lib.resolve("nmdev-1.2.6.jar"));
        Path customLicense = System.getProperty("nmdev.license") == null ? null : Path.of(System.getProperty("nmdev.license")).toAbsolutePath();
        if (customLicense != null) Storage.atomic(runtime.resolve("nm.lic"), Files.readAllBytes(customLicense));
        else download(LICENSE_URL, LICENSE_SHA, runtime.resolve("nm.lic"));
        Path parallel = lib.resolve("parallelcolt-0.11.4.jar"), stamp = runtime.resolve("parallelcolt-build.json");
        if (!Files.exists(parallel) || !Files.exists(stamp) || !Storage.read(stamp).getAsJsonObject().get("sha256").getAsString().equals(Storage.sha(parallel))) {
            download(COLT_URL, COLT_SHA, runtime.resolve("parallelcolt-source.zip"));
            Path source = runtime.resolve("parallelcolt-source"); unzip(runtime.resolve("parallelcolt-source.zip"), source);
            Path project;
            try (var dirs = Files.list(source)) { project = dirs.filter(Files::isDirectory).findFirst().orElseThrow(); }
            Path classes = runtime.resolve("parallelcolt-classes"); Files.createDirectories(classes);
            compile(project.resolve("src/main/java"), classes, classpath(lib), false);
            List<Path> entries;
            try (var walk = Files.walk(classes)) { entries = walk.filter(Files::isRegularFile).sorted().toList(); }
            try (JarOutputStream jar = new JarOutputStream(Files.newOutputStream(parallel))) {
                for (Path file : entries) add(jar, classes.relativize(file).toString().replace('\\','/'), file);
                if (Files.exists(project.resolve("license.txt"))) add(jar, "license.txt", project.resolve("license.txt"));
                Path resources = project.resolve("src/main/resources");
                if (Files.exists(resources)) try (var walk = Files.walk(resources)) { for (Path file : walk.filter(Files::isRegularFile).sorted().toList()) add(jar, resources.relativize(file).toString().replace('\\','/'), file); }
            }
            Storage.json(stamp, Map.of("sha256", Storage.sha(parallel), "source_url", COLT_URL, "source_sha256", COLT_SHA, "compiler", System.getProperty("java.version")));
        }
        try (var files = Files.list(lib)) { for (Path file : files.filter(p -> p.toString().endsWith(".jar")).sorted().toList()) hashes.put(file.getFileName().toString(), Storage.sha(file)); }
        Storage.json(output.resolve("artifacts.json"), hashes);
        Storage.json(output.resolve("parallelcolt-build.json"), Storage.read(stamp));
        Storage.json(output.resolve("binary-provenance.json"), Map.of("nmdev_url", NM_URL, "nmdev_sha256", NM_SHA, "nmdev_checksum_source", "https://github.com/nmltd/maven-packages/releases/download/nmdev-1.2.6-validated/SHA256SUMS", "public_trial_url", LICENSE_URL,
            "parallelcolt_source_url", COLT_URL, "parallelcolt_source_sha256", COLT_SHA, "parallelcolt_binary_is_locally_built", true));
        return hashes;
    }
    static void harness(Path output) throws Exception {
        Path runtime = output.resolve(".runtime"), source = runtime.resolve("harness-src"), classes = runtime.resolve("classes");
        Files.createDirectories(classes);
        Path app = application();
        if (!Files.isRegularFile(app)) throw new IOException("Run the packaged JAR so the exact harness sources can be extracted");
        try (JarFile jar = new JarFile(app.toFile()); ZipOutputStream evidence = new ZipOutputStream(Files.newOutputStream(output.resolve("harness-source.zip")))) {
            for (JarEntry entry : Collections.list(jar.entries())) {
                if (entry.getName().startsWith("harness-src/") && entry.getName().endsWith(".java")) {
                    byte[] data;
                    try (InputStream in = jar.getInputStream(entry)) { data = in.readAllBytes(); }
                    Path destination = runtime.resolve(entry.getName()); Storage.atomic(destination, data);
                    evidence.putNextEntry(new ZipEntry(entry.getName())); evidence.write(data); evidence.closeEntry();
                }
            }
        }
        String fingerprint = Storage.sha(app);
        Path stamp = runtime.resolve("harness-build.txt");
        if (!Files.exists(stamp) || !Files.readString(stamp).equals(fingerprint)) {
            compile(source, classes, classpath(runtime.resolve("lib")), true);
            Storage.atomic(stamp, fingerprint.getBytes(StandardCharsets.UTF_8));
        }
    }
    static String classpath(Path lib) throws IOException {
        try (var files = Files.list(lib)) { return String.join(File.pathSeparator, files.filter(p -> p.toString().endsWith(".jar")).sorted().map(p -> p.toAbsolutePath().toString()).toList()); }
    }
    static void compile(Path source, Path classes, String cp, boolean annotationProcessing) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new IOException("A full JDK 17+ is required; a JRE cannot compile the pinned sources");
        List<File> sources;
        try (var walk = Files.walk(source)) { sources = walk.filter(p -> p.toString().endsWith(".java")).sorted().map(Path::toFile).toList(); }
        try (var manager = compiler.getStandardFileManager(null, Locale.ROOT, StandardCharsets.UTF_8)) {
            var args = new ArrayList<>(List.of("--release", annotationProcessing ? "11" : "8", "-classpath", cp, "-d", classes.toString()));
            if (!annotationProcessing) args.add("-proc:none");
            else { args.add("-processorpath"); args.add(cp); }
            StringWriter diagnostics = new StringWriter();
            boolean success = compiler.getTask(diagnostics, manager, null, args, null, manager.getJavaFileObjectsFromFiles(sources)).call();
            Storage.atomic(classes.resolveSibling(classes.getFileName() + "-compiler.log"), diagnostics.toString().getBytes(StandardCharsets.UTF_8));
            if (!success) throw new IOException("Compilation failed for " + source.getFileName() + ": " + diagnostics);
        }
    }
    static void unzip(Path zip, Path destination) throws IOException {
        Files.createDirectories(destination);
        try (ZipInputStream in = new ZipInputStream(Files.newInputStream(zip))) {
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                Path path = destination.resolve(entry.getName()).normalize();
                if (!path.startsWith(destination)) throw new IOException("Invalid source ZIP path");
                if (entry.isDirectory()) Files.createDirectories(path);
                else { Files.createDirectories(path.getParent()); Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING); }
            }
        }
    }
    private static void add(JarOutputStream jar, String name, Path file) throws IOException { var entry = new JarEntry(name); entry.setTime(1665878400000L); jar.putNextEntry(entry); Files.copy(file, jar); jar.closeEntry(); }
    private Dependencies() {}
}
