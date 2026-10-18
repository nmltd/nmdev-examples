# NM Dev linear algebra benchmarks

A maintained replacement for the [original benchmark suite](https://github.com/nmltd/numerical-methods-java/tree/main/benchmark), covering its 13 linear-algebra operations with current, runnable adapters.

**[Published results](results/2026-10-18/README.md) · [Methodology](METHODOLOGY.md) · [Library coverage](LIBRARY-COVERAGE.md) · [Source provenance](Source-Provenance.md)**

## Included libraries

| Library | Version pinned for this run | Artifact |
|---|---|---|
| NM Dev | 1.2.6-SNAPSHOT | `dev.nm:nmdev-dist` — exact binary SHA-256 is recorded |
| EJML | 0.46.1 | `org.ejml:ejml-ddense` and `ejml-core` |
| Apache Commons Math | 3.6.1 | `org.apache.commons:commons-math3` |
| JAMA | 1.0.3 | `gov.nist.math:jama` |
| JMH harness | 1.37 | `org.openjdk.jmh:jmh-core` |

Third-party versions were checked against Maven Central release metadata on 2026-10-18. These are the latest stable releases in the listed artifact lines at that check; the age of a release does not change its version. NM Dev's snapshot is identified by checksum rather than treated as an immutable release.

The maintained scope is these four libraries. Unmeasured legacy adapters, their dependencies, obsolete R/JRI/Windows installation notes and historical web reports are removed. Their absence is not evidence that those libraries cannot run in another environment. The conic-optimization benchmarks are not part of this linear-algebra run.

## Operations

Transpose, addition, scaling, multiplication, determinant, inverse, inverse of an SPD matrix, Cholesky, LU, QR, symmetric eigendecomposition, SVD and solving a linear system. Matrices are square, at sizes 64 and 256; the right-hand side has one column. These sizes differ from the historical benchmark's much larger defaults.

## Build with Maven

Requires a **JDK 11 or later**. The measured run uses JDK 17. See the repository's [package authentication instructions](../docs/Getting-Started.md). Keep your valid `nm.lic` at the repository root; it is never bundled or committed.

From the repository root:

```bash
mvn -f benchmark/pom.xml clean package
bash benchmark/scripts/run.sh benchmark/results/my-run
```

This is an independent Maven project and does not change the Java 8 requirement of the basic tutorials. Its source and dependencies are not added to the main tutorial application.

## Alternative direct build

If Maven cannot access repositories, use the checked dependency lockfile and an existing published combined NM Dev JAR:

```bash
export NMDEV_JAR=/absolute/path/to/nmdev-dist-1.2.6-SNAPSHOT.jar
bash benchmark/scripts/build-direct.sh
bash benchmark/scripts/run.sh benchmark/results/my-run
```

The direct build verifies third-party checksums, uses the JMH annotation processor, and copies dependencies only into the ignored `target/` directory. It does not download NM Dev or a license. The published run was compiled this way because the execution environment's JVM could not resolve Maven Central, while HTTPS downloads were available.

Defaults: one JMH worker; two independent JVM forks per case; three 300 ms warmup iterations; five 500 ms measurement iterations; fixed 1 GiB heap and `ActiveProcessorCount=1` in each fork. Allow roughly 12–15 minutes for a full run on similar hardware. Additional JMH options can follow the output-directory argument. Label any changed configuration in the results you publish.

## Interactive charts

Download and open [charts.html](results/2026-10-18/charts.html) in a browser to inspect timings, confidence intervals and ratios by hovering, tapping or focusing chart points. Standalone SVGs also contain native titles. GitHub inline image previews do not expose point-level tooltips.

## Regenerate the report

After a complete run:

```bash
python3 benchmark/scripts/report.py benchmark/results/my-run
```

The report script uses Python only for summarizing JMH output. Install Matplotlib if you also want to regenerate the SVG charts. Its published-report configuration expects the default two sizes, two forks and five measured iterations. Preserve environment details and binary checksums with any new published run.

## Correctness and results

`CorrectnessMain` checks all operations at sizes 4, 64 and 256, twice each. JMH setup repeats the selected correctness check before timing. Results are returned to JMH so calculations are consumed. Matrix conversion and verification are outside timing, while allocations required by an operation are included.

Never infer production performance solely from this shared-machine run. Inspect absolute timings, uncertainty, raw iterations and your own workload. See [methodology](METHODOLOGY.md) for algorithm and fairness details.
