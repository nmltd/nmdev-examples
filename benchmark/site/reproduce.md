# Reproduce the benchmark


## One-command application

Requires a **full JDK 17+** and Maven to build. Download or clone [nmdev-examples](https://github.com/nmltd/nmdev-examples), then run:

```text
mvn -f benchmark/pom.xml package
java -jar benchmark/target/nmdev-benchmark-1.2.0.jar --output nmdev-benchmark-all-cores
```

The public entry point `dev.nm.benchmark.launcher.BenchmarkMain` prepares verified dependencies and the public trial license, builds Parallel Colt automatically, runs all eleven libraries across all profiles, records machine information, and packages the results. No Python, shell scripts, GitHub token or manual profile selection is required. First-run Internet access and a full JDK are needed for automatic compilation.

The full run contains 1,260 cases (including all-core profiles and both Commons dense layouts) and can take many hours. Run the same JAR/command again after an interruption to retain completed forks and repeat only unfinished ones. The shareable file is `nmdev-benchmark-output-results.zip`, beside its local checkpoint directory. It includes CPU, memory, OS, JVM, dependency hashes, logs, validation and full-precision measurements; it excludes licenses and private runtime files. [Complete local-run instructions](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/LOCAL-RUN.md).

The launcher pins the public NM Dev 1.2.6 release by its published checksum. Its binary hash differs from the older archived benchmark binary; measurements from different binaries or machines are separate experiments. Native-library availability depends on host architecture; failures are recorded explicitly instead of substituted results.

## Historical shell-based runs

The commands below reproduce older archives and are optional. They require a valid local `nm.lic`, the previously measured NM Dev binary, Linux shell tools and Python. They are not needed for the unattended application.

```bash
export NMDEV_JAR=/absolute/path/to/nmdev-1.2.6.jar
bash benchmark/scripts/build-direct.sh
bash benchmark/scripts/run.sh benchmark/results/my-run
```

The Java 8 tutorial application and the Java 17 benchmark launcher have independent Maven builds. Do not use the root tutorial POM to build the benchmark.

## Checks and report generation

The run stops on failed baseline correctness, deferred-output or native-input-preservation checks. Difficult-input diagnostics are a separate command and deliberately retain failures. See [Validation](validation.html) for the checks and evidence.

```bash
python3 benchmark/scripts/report.py benchmark/results/my-run
```

Python only summarizes JMH measurements. Matplotlib renders the SVGs and self-contained HTML with hover, focus and tap tooltips. Download/open `charts.html` in a browser; GitHub inline image previews do not expose point tooltips. Raw timing JSON, logs, CSV, environment, backends, checksums and correctness evidence accompany each published run.

Results on a shared VM are a workload-specific baseline. Native BLAS and pure-Java paths are explicitly labelled; performance claims should reflect these differences and the recorded uncertainty.

## Publish updated results

Install the report and site dependencies, then generate the report and website from the repository root:

```bash
python3 -m pip install -r benchmark/scripts/requirements.txt
python3 benchmark/scripts/report.py benchmark/results/my-run
python3 benchmark/scripts/publish-pages.py benchmark/results/my-run
```

Commit the generated `docs/*.html` and `docs/.nojekyll` together with the result archive. The Documentation workflow builds from Markdown, checks internal links, uploads the generated site and publishes it through GitHub Pages on pushes to `main`. Pull requests build and check without deployment. Publishing the site does not rerun the Java benchmarks. Edit shared benchmark guide content in `benchmark/site/*.md`; the HTML pages are generated.

## Coverage and computational thread profiles

The defaults remain two forks, three 300 ms warmups and five 500 ms measurements. To reproduce the shorter fairness audit, add `-f 2 -wi 2 -w 200ms -i 3 -r 200ms`. Every invocation records its actual JMH overrides. Do not compare short and long runs as if they had identical uncertainty.

```bash
# Pin a single computational worker; affinity is optional and must be valid on your host.
BENCH_CPUSET=0 BENCH_THREADS=1 bash benchmark/scripts/run.sh benchmark/results/my-fair-baseline

# Larger square matrices: all eleven libraries, sizes 512/1024/2000, five operations.
BENCH_CPUSET=0 BENCH_PROFILE=large bash benchmark/scripts/run.sh benchmark/results/my-large

# Tall, wide and odd-sized multiplication, with all eleven supported adapters.
BENCH_CPUSET=0 BENCH_PROFILE=rectangular bash benchmark/scripts/run.sh benchmark/results/my-rectangular

# Four-computational-thread comparison, one JMH client worker. Run serially after the one-thread track.
BENCH_CPUSET=0-3 BENCH_THREADS=4 BENCH_PROFILE=large bash benchmark/scripts/run.sh benchmark/results/my-four-thread

# Separate difficult-input diagnostics (nonzero exit if any failures are recorded).
java -XX:ActiveProcessorCount=1 -cp 'benchmark/target/classes:benchmark/target/lib/*' \
  dev.nm.benchmark.DifficultValidationMain > difficult-correctness.csv
```

The large profile intentionally avoids automatically scheduling every decomposition at historical 5,000×5,000 sizes. To change the parameter lists, edit or add a profile; do not append duplicate JMH `-p` declarations, which combine values. Historical-sized tests are a separate experiment, not implied by the 512/1024/2000 track.

`run.sh` rebuilds the harness against the local combined JAR, verifies locked third-party artifacts, runs all baseline guards, and records allocation metrics with `-prof gc`. Use new directories for each profile; previous JSON/logs are never overwritten. If your Maven build uses a differently named NM Dev JAR, run the direct build first with `NMDEV_JAR` pointing at that file.

## Verify the measured binary's SPD inverse path

The JDI helper needs a full JDK with `jdk.jdi` and compiler modules. It sets constructor breakpoints only on Chol and LU, without modifying the JAR. The debug run is separate from timing.

```bash
java com.sun.tools.javac.Main --add-modules jdk.jdi -d benchmark/target/classes benchmark/scripts/InversePathTrace.java
java --add-modules jdk.jdi -cp 'benchmark/target/classes:benchmark/target/lib/*' InversePathTrace spd 64
java --add-modules jdk.jdi -cp 'benchmark/target/classes:benchmark/target/lib/*' InversePathTrace spd 256
java --add-modules jdk.jdi -cp 'benchmark/target/classes:benchmark/target/lib/*' InversePathTrace general 64
java com.sun.tools.javap.Main -p -c -classpath benchmark/target/lib/nmdev-1.2.6.jar \
  dev.nm.algebra.linear.matrix.doubles.operation.Inverse
```

Expected: SPD calls Chol and not LU; the nonsymmetric control calls LU and not Chol. Verify the JAR's SHA-256 against the run's `artifacts.json` before interpreting the trace. The helper exits nonzero if the observed constructor counts disagree.

## Current dependency versions

See the [version audit](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/dependency-audit/2026-10-18/README.md). The current primary Commons adapter uses the pinned 4.0-SNAPSHOT build; its timestamped module URLs and checksums are in the lockfile. Parallel Colt 0.11.4 is built from the exact upstream release tag by the direct build script because its old Maven Central coordinate stops at 0.10.1. Use the direct build route with JDK 17.0.20 to reproduce this source-built dependency's pinned binary hash.

The unattended Maven application builds the pinned Parallel Colt source automatically. Historical direct-build commands preserve their original dependency hashes and metadata. Version labels alone do not establish binary equivalence.

## 2000-square comparison

Run the same eleven-library, five-operation profile at size 2000 only, keeping one-thread and four-thread results separate:

```bash
BENCH_CPUSET=0 BENCH_THREADS=1 BENCH_PROFILE=size2000 bash benchmark/scripts/run.sh benchmark/results/my-2000-one -wi 2 -w 100ms -i 3 -r 200ms
BENCH_CPUSET=0-3 BENCH_THREADS=4 BENCH_PROFILE=size2000 bash benchmark/scripts/run.sh benchmark/results/my-2000-four -wi 2 -w 100ms -i 3 -r 200ms
```

The short iteration targets are diagnostic: an iteration still completes a full operation even when it takes longer than its target. Every fork runs full-size correctness and input-preservation checks before timing. Setup and reference checks can dominate wall-clock runtime. This profile does not include every baseline library/operation.

Use `BENCH_PROFILE=size2000` for size 2000 alone. Do not append a second `-p size=...` to the same invocation: JMH combines repeated parameter declarations rather than replacing the profile's size list.


## Reproduce the all-library coverage supplement

The `large` and `size2000` profiles now include all eleven supported libraries. The saved expansion used `large-missing` to measure only the seven adapters omitted from the earlier large archives, retaining those earlier measurements unchanged. Each thread profile runs sequentially with the same dependency binaries and measured operation sources.

```bash
BENCH_CPUSET=0 BENCH_THREADS=1 BENCH_PROFILE=large-missing bash benchmark/scripts/run.sh benchmark/results/my-missing-one -wi 2 -w 100ms -i 3 -r 200ms
BENCH_CPUSET=0-3 BENCH_THREADS=4 BENCH_PROFILE=large-missing bash benchmark/scripts/run.sh benchmark/results/my-missing-four -wi 2 -w 100ms -i 3 -r 200ms
```

The single [comparison page](benchmarks.html) keeps size, operation, source archive and requested thread setting attached to each point. Do not average results across the source archives. Shared-VM load can vary between runs, and requested native thread counts are not independently audited.
