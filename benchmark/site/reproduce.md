# Reproduce the benchmark


Requires **JDK 11+**; the measured runs use JDK 17. Configure [GitHub Packages credentials](https://github.com/nmltd/nmdev-examples/blob/main/docs/Getting-Started.md) and place your valid external `nm.lic` in the repository root. No binary or license is committed.

```bash
mvn -f benchmark/pom.xml clean package
bash benchmark/scripts/run.sh benchmark/results/my-run
```

Alternatively use an existing published combined NM Dev JAR and the checked dependency lockfile:

```bash
export NMDEV_JAR=/absolute/path/to/nmdev-1.2.6.jar
bash benchmark/scripts/build-direct.sh
bash benchmark/scripts/run.sh benchmark/results/my-run
```

The direct build is the validated route used in this environment. It verifies third-party checksums and invokes the JMH annotation processor. The Maven project is independent of the Java 8 tutorial application. Runtime dependencies are copied only into ignored `target/` directories.

Allow roughly 35–45 minutes for the complete expanded run on similar hardware. Follow progress in your output directory's `jmh.log`. Pass optional JMH arguments after the output-directory argument and record any changed settings.

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

# Larger square matrices: four libraries, sizes 512/1024, five operations.
BENCH_CPUSET=0 BENCH_PROFILE=large bash benchmark/scripts/run.sh benchmark/results/my-large

# Tall, wide and odd-sized multiplication, with four explicitly supported adapters.
BENCH_CPUSET=0 BENCH_PROFILE=rectangular bash benchmark/scripts/run.sh benchmark/results/my-rectangular

# Four-computational-thread comparison, one JMH client worker. Run serially after the one-thread track.
BENCH_CPUSET=0-3 BENCH_THREADS=4 BENCH_PROFILE=large bash benchmark/scripts/run.sh benchmark/results/my-four-thread

# Separate difficult-input diagnostics (nonzero exit if any failures are recorded).
java -XX:ActiveProcessorCount=1 -cp 'benchmark/target/classes:benchmark/target/lib/*' \
  dev.nm.benchmark.DifficultValidationMain > difficult-correctness.csv
```

The large profile intentionally avoids automatically scheduling every decomposition at historical 5,000×5,000 sizes. Use explicit JMH `-p size=... -p operation=... -p library=...` overrides when resources permit. Historical-sized tests are a separate experiment, not implied by the 512/1024 track.

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

If you use the Maven route, first run the direct builder and install the resulting source-built Parallel Colt artifact into your local Maven repository:

```bash
mvn install:install-file -Dfile=benchmark/target/lib/parallelcolt-0.11.4.jar \
  -DpomFile=benchmark/target/parallelcolt-source/pom.xml
mvn -f benchmark/pom.xml package
```

The direct route is the validated build used for these measurements. Per-run `versions.json` and `validated-nmdev-artifact.json` preserve labels and proof of the tested customer JAR. Current version labels do not replace the labels of historical archives.
