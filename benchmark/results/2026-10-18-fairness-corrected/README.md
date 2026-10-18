# Linear algebra benchmark results — 2026-10-18-fairness-corrected

[Suite](../../README.md) · [Methodology](../../METHODOLOGY.md) · [Library coverage](../../LIBRARY-COVERAGE.md)

> Corrected harness: deferred ojAlgo results are materialized inside timing, native inputs are checked for mutation, and every fork validates repeat execution. These short iterations provide a diagnostic baseline; use longer runs on dedicated hardware for fine rankings. See the [fairness audit](../2026-10-18-fairness-audit/README.md).

**13 operations × 11 libraries × 2 sizes = 286 measured cases**, containing 1,716 measured iteration means. Lower milliseconds per operation is faster. These are observations on a shared VM, not universal performance rankings.

**[Interactive charts with tooltips](charts.html)** — download and open the self-contained HTML in a browser. Hover, tap or keyboard-focus a point for its version/backend, timing, confidence interval and ratio to NM Dev. GitHub inline image previews do not expose point tooltips. Standalone SVGs also contain native tooltip titles.

## Environment

AMD EPYC 9V74 80-Core Processor; Linux-6.18.55-x86_64-with-glibc2.39. Visible CPUs: 9; container CPU quota: `800000 100000`. JVM 17.0.20. No exclusive CPU reservation; any requested affinity is recorded below.

2 JVM forks per case; 2 warmup iterations of 200 ms; 3 measurement iterations of 200 ms. One JMH worker. The fork options fix a 1 GiB heap. Requested computational threads: 1; requested CPU affinity: 0. Affinity does not reserve a CPU exclusively.

Native thread environment variables request the recorded computational thread count. Parallel Colt, ojAlgo and UJMP also receive explicit library thread limits; MTJ uses an explicitly forced pure-Java F2J backend. Native jblas thread counts are not independently audited. See the environment and backend records below.

## Versions and backends

| Library | Measured version / backend |
| --- | --- |
| NMDEV | NM Dev 1.2.6-SNAPSHOT — Combined obfuscated 1.2.6-SNAPSHOT binary; checksum in artifacts.json |
| EJML | EJML 0.46.1 — ejml-ddense 0.46.1 Java implementation |
| COMMONS | Commons Math 3.6.1 — Array2DRowRealMatrix / Java implementation |
| JAMA | JAMA 1.0.3 — Jama.Matrix / Java implementation |
| COLT | Colt 1.2.0 — cern.colt.matrix / Java implementation |
| PARALLEL_COLT | Parallel Colt 0.10.1 — DenseDoubleMatrix2D / Java JPLASMA and netlib-java F2J dependencies; ConcurrencyUtils threads=1 |
| JBLAS | jblas 1.2.5 (native) — 1.2.5 bundled native libjblas; successful native loading in correctness run; requested native thread environment recorded, native thread count not independently audited |
| F2J_LAPACK | F2J LAPACK/BLAS 0.1 — net.sourceforge.f2j:arpack_combined_all:0.1; direct Dgemm and LAPACK translated Java routines. Replacement distribution, not org.netlib:jlapack:0.8 |
| MTJ | MTJ 1.0.4 (F2J) — 1.0.4 with com.github.fommil.netlib F2jBLAS/F2jLAPACK/F2jARPACK explicitly forced by JVM properties |
| OJALGO | ojAlgo 57.5.0 — 57.5.0 R064Store/decomposition factories; OjAlgoUtils.limitThreadsTo(1) |
| UJMP | UJMP 0.3.0 (core) — 0.3.0 core algorithms; external Commons Math, EJML, jblas, MTJ, ojAlgo and Parallel Colt backends explicitly disabled; threads=1 |

## Correctness and interpretation

All **429 baseline correctness cases passed twice**. Every measured fork also checks its selected result before timing. Reference arithmetic and residual checks are outside timing.

All **66 targeted row-swap/row-cycle cases** also passed. These check determinant signs, LU permutation orientation and solves on pivoting inputs.

Tables show **mean ± JMH 99.9% confidence half-width**, in ms/op. These error bars are not standard deviations. Overlapping intervals or high uncertainty make fine rankings unreliable. Chart ratios are ratios of means and do not show ratio uncertainty.

Native input conversion is outside timing; necessary copies, allocations and full factor output materialization are timed. This compares public API tasks, not identical algorithms. NM Dev Inverse detects symmetry and attempts Cholesky on SPD inputs, falling back to LU if that path raises a runtime exception. The exact measured JAR was verified separately by bytecode and runtime constructor-breakpoint tracing; see the fairness audit. F2J transpose/addition/scaling use adapter-owned Java loops because LAPACK does not define those matrix convenience APIs.

## 64 × 64 matrices

| Operation | NMDEV | EJML | COMMONS | JAMA | COLT | PARALLEL_COLT | JBLAS | F2J_LAPACK | MTJ | OJALGO | UJMP |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| transpose | 0.0043627 ± 0.00058 | 0.0037078 ± 0.00068 | 0.0046801 ± 0.00052 | 0.0047165 ± 0.00066 | 0.0039092 ± 0.00077 | 0.0039753 ± 0.0011 | 0.0047365 ± 0.00069 | 0.005517 ± 0.00066 | 0.0094908 ± 0.0011 | 0.0059573 ± 0.00049 | 0.024582 ± 0.0011 |
| addition | 0.0024639 ± 0.00031 | 0.0023928 ± 0.00022 | 0.0049748 ± 0.00077 | 0.0036221 ± 0.00054 | 0.0058432 ± 0.00067 | 0.0046398 ± 0.00072 | 0.0025778 ± 0.00061 | 0.01169 ± 0.001 | 0.016921 ± 0.0006 | 0.0030721 ± 0.00043 | 0.0067325 ± 0.0007 |
| scale | 0.0022771 ± 0.00041 | 0.002223 ± 0.00061 | 0.0038613 ± 0.00059 | 0.0036616 ± 0.0018 | 0.0035423 ± 0.00066 | 0.0040159 ± 0.00065 | 0.0022107 ± 0.00025 | 0.0021985 ± 0.00013 | 0.013539 ± 0.00029 | 0.0037299 ± 0.0013 | 0.0059392 ± 0.0012 |
| multiplication | 0.16823 ± 0.028 | 0.10893 ± 0.013 | 0.17192 ± 0.014 | 0.15987 ± 0.014 | 0.22852 ± 0.043 | 0.21132 ± 0.019 | 0.041365 ± 0.016 | 0.16911 ± 0.018 | 0.16945 ± 0.014 | 0.12521 ± 0.027 | 0.15689 ± 0.014 |
| determinant | 0.043636 ± 0.023 | 0.050242 ± 0.01 | 0.058733 ± 0.016 | 0.052255 ± 0.013 | 0.079096 ± 0.011 | 0.19955 ± 0.1 | 0.039775 ± 0.0066 | 0.049623 ± 0.038 | 0.051952 ± 0.0073 | 0.023854 ± 0.0016 | 0.054072 ± 0.021 |
| inverse | 0.6232 ± 0.67 | 0.25524 ± 0.024 | 0.11868 ± 0.035 | 0.2094 ± 0.046 | 0.43778 ± 0.66 | 0.52448 ± 0.69 | 0.067666 ± 0.014 | 0.14207 ± 0.028 | 0.21317 ± 0.064 | 0.157 ± 0.023 | 0.19912 ± 0.044 |
| inverseSPD | 0.45323 ± 0.25 | 0.11691 ± 0.029 | 0.089528 ± 0.017 | 0.26104 ± 0.026 | 0.37548 ± 0.079 | 0.55673 ± 0.15 | 0.054017 ± 0.006 | 0.10749 ± 0.014 | 0.35049 ± 0.049 | 0.098764 ± 0.015 | 0.28537 ± 0.069 |
| cholesky | 0.034114 ± 0.01 | 0.030927 ± 0.00075 | 0.041828 ± 0.0095 | 0.030569 ± 0.0069 | 0.0386 ± 0.0088 | 0.1509 ± 0.023 | 0.020136 ± 0.0027 | 0.028402 ± 0.0014 | 0.091338 ± 0.048 | 0.025141 ± 0.002 | 0.035601 ± 0.008 |
| lu | 0.044747 ± 0.019 | 0.0617 ± 0.018 | 0.064917 ± 0.013 | 0.061389 ± 0.021 | 0.11703 ± 0.068 | 0.2425 ± 0.038 | 0.041233 ± 0.015 | 0.051429 ± 0.014 | 0.05543 ± 0.012 | 0.037821 ± 0.013 | 0.061842 ± 0.019 |
| qr | 0.36732 ± 0.35 | 0.19331 ± 0.045 | 0.19915 ± 0.04 | 0.33238 ± 0.025 | 0.41823 ± 0.11 | 0.90495 ± 1 | 0.12775 ± 0.018 | 0.21172 ± 0.083 | 0.22603 ± 0.043 | 0.24942 ± 0.17 | 0.35069 ± 0.043 |
| eigenSymmetric | 4.7635 ± 1.5 | 0.72316 ± 0.41 | 0.78757 ± 0.21 | 0.75124 ± 0.68 | 0.69088 ± 0.13 | 0.66512 ± 0.046 | 0.45345 ± 0.096 | 1.4963 ± 1.9 | 3.3513 ± 3.1 | 0.46552 ± 0.84 | 0.66223 ± 0.044 |
| svd | 6.0736 ± 6.4 | 1.3166 ± 0.53 | 17.05 ± 37 | 27.49 ± 1e+02 | 8.7315 ± 6.4 | 15.418 ± 5.8 | 1.148 ± 0.22 | 4.2083 ± 2.2 | 10.489 ± 4 | 2.1122 ± 1.1 | 12.512 ± 21 |
| solve | 0.17159 ± 0.21 | 0.063306 ± 0.02 | 0.067142 ± 0.019 | 0.063179 ± 0.03 | 0.13855 ± 0.14 | 0.3658 ± 0.51 | 0.023473 ± 0.00074 | 0.051999 ± 0.027 | 0.055529 ± 0.03 | 0.050365 ± 0.042 | 0.069111 ± 0.039 |

![Timing ratios relative to NM Dev at size 64](relative-times-64.svg)

NM Dev is 1×. Below 1× means less time than NM Dev; above 1× means more time. Logarithmic scale. Use the interactive HTML for exact point details.

## 256 × 256 matrices

| Operation | NMDEV | EJML | COMMONS | JAMA | COLT | PARALLEL_COLT | JBLAS | F2J_LAPACK | MTJ | OJALGO | UJMP |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| transpose | 0.20312 ± 0.016 | 0.18447 ± 0.017 | 0.090992 ± 0.022 | 0.080085 ± 0.01 | 0.16221 ± 0.018 | 0.15584 ± 0.014 | 0.16303 ± 0.011 | 0.16102 ± 0.014 | 0.17223 ± 0.023 | 0.14609 ± 0.018 | 0.54974 ± 0.13 |
| addition | 0.06684 ± 0.046 | 0.04359 ± 0.0071 | 0.062818 ± 0.031 | 0.051855 ± 0.0097 | 0.10173 ± 0.012 | 0.081524 ± 0.0059 | 0.044852 ± 0.0058 | 0.22407 ± 0.041 | 0.26127 ± 0.01 | 0.0594 ± 0.023 | 0.23096 ± 0.0079 |
| scale | 0.061038 ± 0.047 | 0.037255 ± 0.0038 | 0.049035 ± 0.0048 | 0.043266 ± 0.014 | 0.059137 ± 0.012 | 0.063991 ± 0.011 | 0.040034 ± 0.0079 | 0.039217 ± 0.0043 | 0.24917 ± 0.0039 | 0.054565 ± 0.023 | 0.21426 ± 0.01 |
| multiplication | 19.026 ± 1.6 | 6.0365 ± 2.4 | 13.644 ± 0.69 | 12.778 ± 0.78 | 34.779 ± 1.9 | 34.07 ± 4.1 | 2.4676 ± 0.42 | 9.0101 ± 0.41 | 9.4371 ± 1.8 | 6.0209 ± 1.2 | 16.355 ± 1.2 |
| determinant | 2.8615 ± 1.8 | 4.2528 ± 0.39 | 4.4093 ± 0.43 | 3.805 ± 0.31 | 3.6685 ± 0.73 | 10.617 ± 7.8 | 1.1881 ± 0.21 | 3.8011 ± 0.57 | 4.018 ± 0.35 | 1.5685 ± 0.39 | 3.8202 ± 0.27 |
| inverse | 24.474 ± 8.4 | 13.884 ± 1.3 | 7.2183 ± 0.83 | 11.601 ± 0.23 | 15.034 ± 8 | 20.582 ± 5.4 | 2.7609 ± 0.7 | 11.815 ± 0.71 | 14.892 ± 2.2 | 9.3345 ± 0.44 | 12.297 ± 0.44 |
| inverseSPD | 18.121 ± 5.7 | 10.019 ± 0.62 | 4.5424 ± 1.5 | 25.442 ± 1.6 | 37.201 ± 6.8 | 15.32 ± 3.8 | 2.72 ± 0.97 | 6.2405 ± 0.48 | 15.867 ± 4 | 5.6495 ± 0.25 | 25.709 ± 2.8 |
| cholesky | 2.0377 ± 0.34 | 1.8755 ± 0.42 | 1.6419 ± 0.26 | 2.0761 ± 0.17 | 1.6858 ± 0.35 | 2.5915 ± 0.73 | 0.54718 ± 0.018 | 1.9628 ± 0.27 | 3.0659 ± 0.75 | 1.0899 ± 0.33 | 1.9598 ± 0.47 |
| lu | 2.9072 ± 2 | 4.2467 ± 1 | 4.64 ± 0.88 | 4.0015 ± 0.99 | 4.1229 ± 1.4 | 11.429 ± 7.2 | 1.2581 ± 0.32 | 3.9125 ± 0.46 | 4.1366 ± 1.3 | 1.9193 ± 0.94 | 4.0336 ± 1.1 |
| qr | 20.556 ± 14 | 10.985 ± 1.4 | 12.931 ± 1.3 | 39.601 ± 7.4 | 102.01 ± 7.9 | 23.935 ± 6.2 | 5.6877 ± 0.46 | 15.997 ± 3.5 | 17.796 ± 5.9 | 5.1147 ± 1.1 | 38.043 ± 0.74 |
| eigenSymmetric | 94.78 ± 39 | 26.804 ± 3.8 | 49.489 ± 5.2 | 47.25 ± 8.6 | 51.458 ± 14 | 45.107 ± 3.9 | 10.037 ± 1.5 | 40.299 ± 37 | 58.993 ± 22 | 11.707 ± 2 | 45.507 ± 1.8 |
| svd | 137.44 ± 1.4e+02 | 53.076 ± 5.9 | 200.1 ± 2.1e+02 | 164.38 ± 1.3e+02 | 277.65 ± 3.5e+02 | 145.88 ± 58 | 113.42 ± 1.7 | 224.98 ± 1.1e+02 | 119.85 ± 31 | 63.03 ± 38 | 244.64 ± 2.5e+02 |
| solve | 3.0269 ± 1.4 | 4.4561 ± 0.56 | 4.2178 ± 0.16 | 3.9082 ± 0.16 | 4.9683 ± 5.1 | 12.491 ± 9.6 | 0.74519 ± 0.067 | 3.7462 ± 0.34 | 3.707 ± 0.34 | 1.7586 ± 0.49 | 4.0195 ± 0.63 |

![Timing ratios relative to NM Dev at size 256](relative-times-256.svg)

NM Dev is 1×. Below 1× means less time than NM Dev; above 1× means more time. Logarithmic scale. Use the interactive HTML for exact point details.

## Limits

No aggregate speedup is reported because averaging operation ratios imposes an arbitrary workload weighting. This run uses one deterministic family of well-conditioned square matrices. It does not measure sparse matrices, conic optimization, parallel scaling, very large inputs or stability on difficult problems. Parameter order is deterministic, and the VM is shared. Reproduce your own workload on dedicated hardware before making broad performance claims.

## Raw evidence

- [JMH JSON and per-fork iteration means](jmh.json)
- [Complete JMH log](jmh.log)
- [Summary CSV](summary.csv)
- [JVM allocation per operation; native allocations are not fully included](allocation.csv)
- [Baseline correctness residuals](correctness.csv)
- [Targeted pivot/sign checks](pivot-correctness.csv)
- [Environment and requested thread settings](environment.json)
- [Backend records](backends.json)
- [Artifact versions and SHA-256 checksums](artifacts.json)
- [Measured source checksums](source-checksums.json)
- [Dependency release metadata](dependency-metadata.json)
- [Exact harness source snapshot](harness-source.zip)
- [Deferred-output and mutation regression checks](fairness-check.txt)

Regenerate with `python3 benchmark/scripts/report.py benchmark/results/2026-10-18-fairness-corrected`. Python only summarizes measurements and renders charts; all measured calculations run in Java/JMH. Matplotlib is required to regenerate charts and the self-contained HTML.
