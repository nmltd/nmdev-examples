package dev.nm.benchmark.launcher;

import com.google.gson.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.openjdk.jmh.results.*;
import org.openjdk.jmh.results.format.*;

/**
 * One public application entry point. With no arguments it prepares all dependencies,
 * runs all profiles serially, resumes verified forks and creates a shareable ZIP.
 * Re-run the same JAR in the same directory after interruption; no profile selection
 * or result-generation command is needed. JDK 17+ and initial Internet access suffice.
 */
public final class BenchmarkMain {
    static final List<String> LIBRARIES = List.of("NMDEV","EJML","COMMONS","COMMONS_BLOCK","JAMA","COLT","PARALLEL_COLT","JBLAS","F2J_LAPACK","MTJ","OJALGO","UJMP");
    static final List<String> OPERATIONS = List.of("transpose","addition","scale","multiplication","determinant","inverse","inverseSPD","cholesky","lu","qr","eigenSymmetric","svd","solve");
    static final List<String> LARGE_OPERATIONS = List.of("multiplication","lu","cholesky","qr","solve");
    static final AtomicReference<Process> ACTIVE = new AtomicReference<>();
    static volatile boolean stopping;
    record Case(String profile, int threads, String library, String operation, String dimension) {
        String id() { return profile + "__" + threads + "thread__" + library + "__" + operation + "__" + dimension; }
        Map<String,String> parameters() { return profile.startsWith("rectangular") ? Map.of("library",library,"shape",dimension) : Map.of("library",library,"operation",operation,"size",dimension); }
        String benchmark() { return "dev.nm.benchmark." + (profile.startsWith("rectangular") ? "ExpandedRectangularBenchmark" : "MatrixBenchmark") + ".compute"; }
    }
    static List<Case> plan(boolean smoke, List<String> libraries) {
        return plan(smoke,libraries,Runtime.getRuntime().availableProcessors());
    }
    static List<Case> plan(boolean smoke, List<String> libraries, int availableProcessors) {
        if(availableProcessors<1)throw new IllegalArgumentException("availableProcessors must be positive");
        List<Case> cases = new ArrayList<>();
        for (String lib : libraries) for (String op : smoke ? List.of("multiplication") : OPERATIONS) for (int size : smoke ? new int[]{8} : new int[]{64,256}) cases.add(new Case("baseline",1,lib,op,""+size));
        for (int threads : new int[]{1,4}) for (String lib : libraries) for (String op : smoke ? List.of("cholesky") : LARGE_OPERATIONS) for (int size : smoke ? new int[]{8} : new int[]{512,1024,2000}) cases.add(new Case("large",threads,lib,op,""+size));
        for (String lib : libraries) for (String shape : smoke ? List.of("8x5x3") : List.of("128x512x64","512x128x256","1024x256x64","255x257x129")) cases.add(new Case("rectangular",1,lib,"multiplication",shape));
        // Separate machine-capacity profiles even when capacity happens to be 1 or 4.
        // One JMH caller measures one operation, with library workers configured separately.
        for(Case item:new ArrayList<>(cases)) {
            if(item.profile.equals("large") && item.threads!=1)continue;
            cases.add(new Case(item.profile+"-all-cores",availableProcessors,item.library,item.operation,item.dimension));
        }
        return cases;
    }
    public static void main(String[] args) throws Exception {
        boolean smoke = false; Path output = Path.of("nmdev-benchmark-output"); int maxCases = Integer.MAX_VALUE;
        List<String> libraries = LIBRARIES;
        for (int i=0;i<args.length;i++) switch(args[i]) {
            case "--output": output=Path.of(args[++i]); break;
            case "--smoke": smoke=true; break;
            case "--max-cases": maxCases=Integer.parseInt(args[++i]); break;
            case "--libraries": libraries=List.of(args[++i].split(",")); if(!LIBRARIES.containsAll(libraries)) throw new IllegalArgumentException("Unknown library"); break;
            case "--help": System.out.println("java -jar nmdev-benchmark-1.2.0.jar [--output directory]\nNo arguments runs all 11 libraries (12 implementation variants) and all profiles. Repeat the same command to resume.\nDiagnostics only: --smoke [--libraries NMDEV,EJML] [--max-cases N]"); return;
            default: throw new IllegalArgumentException("Unknown argument: "+args[i]);
        }
        if (!smoke && !libraries.equals(LIBRARIES)) throw new IllegalArgumentException("--libraries is a diagnostic option requiring --smoke");
        output=output.toAbsolutePath().normalize(); Files.createDirectories(output);
        final Path root=output;
        try (FileChannel channel=FileChannel.open(output.resolve(".runner.lock"),StandardOpenOption.CREATE,StandardOpenOption.WRITE)) {
            FileLock lock=channel.tryLock(); if(lock==null) throw new IOException("This result directory already has an active benchmark runner");
            try (lock) {
                Thread hook=new Thread(()->{
                    stopping=true;
                    Process process=ACTIVE.get();
                    if(process!=null) { process.descendants().forEach(ProcessHandle::destroy); process.destroy(); }
                    try { Storage.json(root.resolve("interrupted.json"),Map.of("utc",Instant.now().toString(),"reason","JVM shutdown hook; re-run the same command to resume")); Storage.bundle(root); }
                    catch(Exception ex) { System.err.println("Could not finish interruption ZIP: "+ex.getMessage()); }
                },"benchmark-shutdown");
                Runtime.getRuntime().addShutdownHook(hook);
                try { run(output,smoke,libraries,maxCases); }
                catch(Exception ex) {
                    Storage.json(output.resolve("launcher-error.json"),Map.of("utc",Instant.now().toString(),"type",ex.getClass().getName(),"message",String.valueOf(ex.getMessage())));
                    Storage.bundle(output); throw ex;
                } finally { try { Runtime.getRuntime().removeShutdownHook(hook); } catch (IllegalStateException ignored) { /* shutdown is already in progress */ } }
            }
        }
    }
    static void run(Path output, boolean smoke, List<String> libraries, int maxCases) throws Exception {
        List<Case> cases=plan(smoke,libraries);
        Map<String,Object> machine=MachineInfo.collect();
        if(!Files.exists(output.resolve("machine.json"))) Storage.json(output.resolve("machine.json"),machine);
        Storage.json(output.resolve("latest-machine.json"),machine);
        System.out.println("NM Dev unattended benchmark: "+cases.size()+" cases, two independent JVM forks per case.");
        System.out.println("Output: "+output+"; repeat this command to resume. Long operations can take several minutes.");
        Map<String,String> artifacts=Dependencies.prepare(output); Dependencies.harness(output);
        Map<String,Object> request=new LinkedHashMap<>();
        request.put("format",2); request.put("all_cores_available_processors",Runtime.getRuntime().availableProcessors()); request.put("suite",smoke?"SMOKE-NOT-FOR-PUBLICATION":"FULL"); request.put("cases",cases); request.put("artifacts",artifacts);
        request.put("machine",MachineInfo.identity(machine)); request.put("application_sha256",Storage.sha(Dependencies.application()));
        request.put("iteration_timeout","60m"); request.put("forks_per_jmh_invocation",1);
        request.put("warmup_iterations",2);request.put("measurement_iterations",3);request.put("forks",2);request.put("iteration_target",smoke?"10ms":"100ms warmup / 200ms measurement");
        request.put("child_jvm_options","-Xms256m -Xmx1g; ActiveProcessorCount and benchmark.threads per case; F2J BLAS/LAPACK/ARPACK forced");
        Path manifest=output.resolve("request.json"); JsonElement specification=Storage.JSON.toJsonTree(request);
        if(Files.exists(manifest) && !Storage.read(manifest).equals(specification)) throw new IOException("Run configuration, binaries or machine changed. Use a different --output directory; existing checkpoints were preserved.");
        Storage.json(manifest,specification); Files.deleteIfExists(output.resolve("completed-utc.txt"));
        Storage.atomic(output.resolve("README.md"),("# NM Dev benchmark output\n\nSuite: "+request.get("suite")+". Profiles ending in -all-cores request all JVM-visible logical processors recorded in request.json. Single-threaded APIs remain single threaded; requested workers do not establish actual utilization. COMMONS uses Array2DRowRealMatrix; COMMONS_BLOCK uses BlockRealMatrix, constructed before timing. Compare matching operations and dimensions across profiles; do not pool thread settings. All measurements are local to the machine recorded in machine.json.\n\nCase checkpoints include two independently warmed JVM forks. Fork JSON is preserved verbatim; combined JSON uses JMH's own aggregators. Requested native thread counts are not independently audited.\n\nUpload the sibling *-results-<UTC timestamp>.zip. It excludes licenses, downloaded binaries, credentials and private runtime cache. Partial runs are explicitly marked in progress.json. To resume, keep the complete local output directory, including .runtime and binary fork checkpoints, and run the same JAR again.\n").getBytes(java.nio.charset.StandardCharsets.UTF_8));
        // Preflight failures are recorded; each timed fork independently validates its
        // own full-size inputs. An unsupported native backend does not hide other libraries.
        Path preflight=output.resolve("preflight");Files.createDirectories(preflight);
        List<String> preflightFailures=new ArrayList<>();
        for(String name:List.of("OriginValidationMain","CommonsBlockValidationMain","FairnessValidationMain","CorrectnessMain","PivotValidationMain","ExpandedRectangularValidationMain")) {
            Path done=preflight.resolve(name+".json");
            if(!Files.exists(done) || Storage.read(done).getAsJsonObject().get("exit_code").getAsInt()!=0) {
                int code=process(output,new ArrayList<>(List.of("dev.nm.benchmark."+name)),1,preflight.resolve(name+".log"),"preflight "+name);
                Storage.json(done,Map.of("exit_code",code,"utc",Instant.now().toString()));
            }
            if(Storage.read(done).getAsJsonObject().get("exit_code").getAsInt()!=0) preflightFailures.add(name);
        }
        JsonArray combined=new JsonArray(); List<Map<String,Object>> failed=new ArrayList<>(); int completed=0,attempted=0;
        try {
            for(Case item:cases) {
                if(attempted++>=maxCases) break;
                Path directory=output.resolve("cases").resolve(item.id());Files.createDirectories(directory);
                System.out.println("Case "+attempted+"/"+cases.size()+": "+item.id());
                boolean successful=true;
                for(int fork=1;fork<=2;fork++) {
                    Path json=directory.resolve("fork-"+fork+".json"),binary=directory.resolve("fork-"+fork+".bin"),proof=directory.resolve("fork-"+fork+"-checkpoint.json");
                    if(validFork(json,binary,proof,item)) {System.out.println("  Retaining saved fork "+fork);continue;}
                    boolean saved=false;
                    for(int attempt=1;attempt<=2 && !saved;attempt++) {
                        Path pending=json.resolveSibling(json.getFileName()+".pending");Files.deleteIfExists(pending);Files.deleteIfExists(binary);
                        List<String> cli=new ArrayList<>(List.of(ForkWorker.class.getName(),binary.toString(),item.benchmark(),"-f","1","-wi","2","-w",smoke?"10ms":"100ms","-i","3","-r",smoke?"10ms":"200ms","-t","1","-to","60m","-prof","gc","-foe","true","-rf","json","-rff",pending.toString(),"-jvmArgsAppend",jvmFlags(item.threads)));
                        for(var parameter:new TreeMap<>(item.parameters()).entrySet()) {cli.add("-p");cli.add(parameter.getKey()+"="+parameter.getValue());}
                        int code=process(output,cli,item.threads,directory.resolve("fork-"+fork+"-attempt-"+attempt+".log"),item.id()+" fork "+fork);
                        if(code==0 && validRow(pending,item) && Files.isRegularFile(binary)) {
                            Storage.move(pending,json);
                            Storage.json(proof,Map.of("json_sha256",Storage.sha(json),"binary_sha256",Storage.sha(binary),"completed_utc",Instant.now().toString()));
                            saved=true;System.out.println("  Saved fork "+fork+" (full-precision checkpoint)");Storage.bundle(output);
                        } else System.err.println("  Fork failed (exit "+code+"); attempt "+attempt+"/2; see case log.");
                    }
                    if(!saved) {successful=false;failed.add(Map.of("case",item.id(),"fork",fork,"reason","Two attempts failed; no timing result published for this case"));break;}
                }
                if(successful) {
                    JsonObject row=merge(directory);Storage.json(directory.resolve("result.json"),row);
                    JsonObject labelled=row.deepCopy();labelled.addProperty("requested_threads",item.threads);labelled.addProperty("profile",item.profile);labelled.addProperty("case_id",item.id());
                    combined.add(labelled);completed++;
                }
                Storage.json(output.resolve("jmh.json"),combined);
                Storage.json(output.resolve("failures.json"),failed);
                Storage.json(output.resolve("progress.json"),Map.of("complete",completed==cases.size() && preflightFailures.isEmpty(),"completed_cases",completed,"attempted_cases",Math.min(attempted,cases.size()),"total_cases",cases.size(),"failed_cases",failed.size(),"updated_utc",Instant.now().toString()));
                summary(output,combined);Storage.bundle(output);
            }
        } finally {Storage.bundle(output);}
        if(completed==cases.size() && preflightFailures.isEmpty()) Storage.atomic(output.resolve("completed-utc.txt"),(Instant.now()+"\n").getBytes(java.nio.charset.StandardCharsets.UTF_8));
        Storage.json(output.resolve("suite-status.json"),Map.of("complete",completed==cases.size() && preflightFailures.isEmpty(),"completed_cases",completed,"total_cases",cases.size(),"failed_cases",failed.size(),"preflight_failures",preflightFailures,"suite",smoke?"SMOKE-NOT-FOR-PUBLICATION":"FULL"));
        Storage.bundle(output);
        System.out.println("Saved "+completed+"/"+cases.size()+" cases. Upload "+Storage.bundlePath(output));
        if(!failed.isEmpty() || !preflightFailures.isEmpty()) throw new IOException("Some cases or preflight checks failed; their logs and partial results are packaged. Re-run to retry failed forks.");
    }
    static String jvmFlags(int threads) {return "-Xms256m -Xmx1g -XX:ActiveProcessorCount="+threads+" -Dbenchmark.threads="+threads+" -Dcom.github.fommil.netlib.BLAS=com.github.fommil.netlib.F2jBLAS -Dcom.github.fommil.netlib.LAPACK=com.github.fommil.netlib.F2jLAPACK -Dcom.github.fommil.netlib.ARPACK=com.github.fommil.netlib.F2jARPACK";}
    static int process(Path output,List<String> arguments,int threads,Path log,String label) throws Exception {
        if (stopping) throw new InterruptedIOException("Application is stopping; completed forks are retained");
        Path runtime=output.resolve(".runtime");
        String cp=runtime.resolve("classes")+File.pathSeparator+Dependencies.classpath(runtime.resolve("lib"))+File.pathSeparator+Dependencies.application();
        String java=Path.of(System.getProperty("java.home"),"bin",System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win")?"java.exe":"java").toString();
        List<String> command=new ArrayList<>(List.of(java));command.addAll(List.of(jvmFlags(threads).split(" ")));command.addAll(List.of("-cp",cp));command.addAll(arguments);
        Files.createDirectories(log.getParent());ProcessBuilder builder=new ProcessBuilder(command).directory(runtime.toFile()).redirectErrorStream(true).redirectOutput(log.toFile());
        for(String name:List.of("OPENBLAS_NUM_THREADS","OMP_NUM_THREADS","MKL_NUM_THREADS","BLIS_NUM_THREADS","VECLIB_MAXIMUM_THREADS")) builder.environment().put(name,""+threads);
        Process child=builder.start();ACTIVE.set(child);
        try {
            while(!child.waitFor(20,TimeUnit.SECONDS)) {Storage.json(output.resolve("active.json"),Map.of("label",label,"pid",child.pid(),"heartbeat_utc",Instant.now().toString(),"status","running"));System.out.println("  Running: "+label+" at "+Instant.now());}
            Storage.json(output.resolve("active.json"),Map.of("label",label,"exit_code",child.exitValue(),"heartbeat_utc",Instant.now().toString(),"status","finished"));
            return child.exitValue();
        } finally {ACTIVE.compareAndSet(child,null);}
    }
    static boolean validRow(Path path,Case item) {
        try {JsonArray rows=Storage.read(path).getAsJsonArray();if(rows.size()!=1)return false;JsonObject row=rows.get(0).getAsJsonObject();
            if (row.has("benchmark") && !row.get("benchmark").getAsString().equals(item.benchmark())) return false;
            if(!row.getAsJsonObject("params").equals(Storage.JSON.toJsonTree(item.parameters())))return false;
            if(row.get("forks").getAsInt()!=1 || row.get("warmupIterations").getAsInt()!=2 || row.get("measurementIterations").getAsInt()!=3)return false;
            JsonObject metric=row.getAsJsonObject("primaryMetric");if(!Double.isFinite(metric.get("score").getAsDouble()))return false;
            JsonArray raw=metric.getAsJsonArray("rawData");if(raw.size()!=1 || raw.get(0).getAsJsonArray().size()!=3)return false;
            for(JsonElement x:raw.get(0).getAsJsonArray())if(!Double.isFinite(x.getAsDouble()) || x.getAsDouble()<0)return false;
            return true;
        }catch(Exception ex){return false;}
    }
    static boolean validFork(Path json,Path binary,Path proof,Case item) {
        try {JsonObject p=Storage.read(proof).getAsJsonObject();return p.get("json_sha256").getAsString().equals(Storage.sha(json)) && p.get("binary_sha256").getAsString().equals(Storage.sha(binary)) && validRow(json,item);}
        catch(Exception ex){return false;}
    }
    static JsonObject merge(Path directory) throws Exception {
        RunResult first=ForkWorker.readOwnCheckpoint(directory.resolve("fork-1.bin")),second=ForkWorker.readOwnCheckpoint(directory.resolve("fork-2.bin"));
        List<BenchmarkResult> forks=new ArrayList<>(first.getBenchmarkResults());forks.addAll(second.getBenchmarkResults());
        ByteArrayOutputStream bytes=new ByteArrayOutputStream();
        ResultFormatFactory.getInstance(ResultFormatType.JSON,new PrintStream(bytes,true,java.nio.charset.StandardCharsets.UTF_8)).writeOut(List.of(new RunResult(first.getParams(),forks)));
        JsonObject row=JsonParser.parseString(bytes.toString(java.nio.charset.StandardCharsets.UTF_8)).getAsJsonArray().get(0).getAsJsonObject();
        row.addProperty("forks",2);return row;
    }
    static void summary(Path output,JsonArray rows) throws IOException {
        StringBuilder csv=new StringBuilder("profile,requested_threads,library,operation,size_or_shape,mean_ms_per_op,jmh_99_9_error_ms,iteration_means\n");
        for(JsonElement value:rows){JsonObject r=value.getAsJsonObject(),p=r.getAsJsonObject("params"),m=r.getAsJsonObject("primaryMetric");
            csv.append(r.get("profile").getAsString()).append(',').append(r.get("requested_threads")).append(',').append(p.get("library").getAsString()).append(',').append(p.has("operation")?p.get("operation").getAsString():"multiplication").append(',').append(p.has("size")?p.get("size").getAsString():p.get("shape").getAsString()).append(',').append(m.get("score")).append(',').append(m.get("scoreError")).append(",6\n");}
        Storage.atomic(output.resolve("summary.csv"),csv.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }
    private BenchmarkMain() {}
}
