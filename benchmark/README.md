# NM Dev linear algebra benchmarks

Reproducible Java/JMH comparisons derived from the original numerical-methods-java benchmark suite. The maintained adapters cover 13 dense linear-algebra tasks across eleven libraries/distributions.

**[Initial completed report](results/2026-10-18/README.md) · [Expanded completed report](results/2026-10-18-expanded/README.md) · [Methodology](METHODOLOGY.md) · [Coverage](LIBRARY-COVERAGE.md)**

## Included versions

| Library / distribution | Version | Selected backend |
|---|---|---|
| NM Dev | 1.2.6-SNAPSHOT | Combined obfuscated binary; exact checksum recorded |
| Apache Commons Math | 3.6.1 | Array2DRowRealMatrix / Java |
| Colt | 1.2.0 | Java dense matrices |
| Parallel Colt | 0.10.1 | Java dense / JPLASMA; one configured thread |
| EJML | 0.46.1 | ejml-ddense / Java |
| JAMA | 1.0.3 | Java |
| jblas | 1.2.5 | Bundled native libjblas |
| F2J LAPACK/BLAS | arpack_combined_all 0.1 | Direct translated Java LAPACK/BLAS; replacement for unavailable legacy JLAPACK artifact |
| MTJ | 1.0.4 | F2jBLAS/F2jLAPACK/F2jARPACK explicitly selected |
| ojAlgo | 57.5.0 | R064Store / Java; one configured thread |
| UJMP | 0.3.0 | Core algorithms, external backends disabled; one configured thread |

Release availability was checked against Maven Central metadata on 2026-10-18. Old release dates are not grounds for exclusion. Versions refer to the listed artifact lines, not every fork. The original `org.netlib:jlapack:0.8` coordinate is not relabelled as the F2J distribution; the replacement's actual coordinate and version are reported.

## Tasks and configuration

Transpose, addition, scaling, multiplication, determinant, general inverse, SPD inverse, Cholesky, LU, QR, symmetric eigendecomposition, SVD and a linear solve with one RHS. Timed square matrix sizes: 64 and 256. These differ from the historical benchmark's large defaults.

The full eleven-library matrix has **286 timed cases**. Each uses two JVM forks, three 300 ms warmup iterations and five 500 ms measurement iterations per fork. One JMH worker, one JVM-visible processor and a fixed 1 GiB heap. Native thread environment variables request one thread; native thread counts are not independently audited. See methodology for API and algorithm differences.

## Build and run

Requires **JDK 11+**; the measured runs use JDK 17. Configure [GitHub Packages credentials](../docs/Getting-Started.md) and place your valid external `nm.lic` in the repository root. No binary or license is committed.

```bash
mvn -f benchmark/pom.xml clean package
bash benchmark/scripts/run.sh benchmark/results/my-run
```

Alternatively use an existing published combined NM Dev JAR and the checked dependency lockfile:

```bash
export NMDEV_JAR=/absolute/path/to/nmdev-dist-1.2.6-SNAPSHOT.jar
bash benchmark/scripts/build-direct.sh
bash benchmark/scripts/run.sh benchmark/results/my-run
```

The direct build is the validated route used in this environment. It verifies third-party checksums and invokes the JMH annotation processor. The Maven project is independent of the Java 8 tutorial application. Runtime dependencies are copied only into ignored `target/` directories.

Allow roughly 35–45 minutes for the complete expanded run on similar hardware. Follow progress in your output directory's `jmh.log`. Pass optional JMH arguments after the output-directory argument and record any changed settings.

## Checks and report generation

`CorrectnessMain` verifies **429 baseline cases twice** (eleven libraries, thirteen tasks, sizes 4/64/256). `PivotValidationMain` adds **66 cases** for determinant signs, LU permutation orientation and solves under row swaps and row cycles. The benchmark also validates its selected calculation before every timed fork. Failed checks stop the run; missing data is never filled with zero or fabricated timings.

```bash
python3 benchmark/scripts/report.py benchmark/results/my-run
```

Python only summarizes JMH measurements. Matplotlib renders the SVGs and self-contained HTML with hover, focus and tap tooltips. Download/open `charts.html` in a browser; GitHub inline image previews do not expose point tooltips. Raw timing JSON, logs, CSV, environment, backends, checksums and correctness evidence accompany each published run.

Results on a shared VM are a workload-specific baseline. Native BLAS and pure-Java paths are explicitly labelled; performance claims should reflect these differences and the recorded uncertainty.
