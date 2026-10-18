# Linear algebra benchmark results — 2026-10-18-expanded

[Suite](../../README.md) · [Methodology](../../METHODOLOGY.md) · [Library coverage](../../LIBRARY-COVERAGE.md)

> Historical run: ojAlgo scalar multiplication returned a deferred expression. Its scaling timings are invalid for completed-work comparisons. Raw measurements are preserved. See the [fairness audit](../2026-10-18-fairness-audit/README.md).

**13 operations × 11 libraries × 2 sizes = 286 measured cases**, containing 2,860 measured iteration means. Lower milliseconds per operation is faster. These are observations on a shared VM, not universal performance rankings.

**[Interactive charts with tooltips](charts.html)** — download and open the self-contained HTML in a browser. Hover, tap or keyboard-focus a point for its version/backend, timing, confidence interval and ratio to NM Dev. GitHub inline image previews do not expose point tooltips. Standalone SVGs also contain native tooltip titles.

## Environment

AMD EPYC 9V74 80-Core Processor; Linux-6.18.55-x86_64-with-glibc2.39. Visible CPUs: 9; container CPU quota: `800000 100000`. JVM 17.0.20. No exclusive CPU reservation; any requested affinity is recorded below.

2 JVM forks per case; 3 warmup iterations of 300 ms; 5 measurement iterations of 500 ms. One JMH worker. The fork options fix a 1 GiB heap. Requested computational threads: 1; requested CPU affinity: unrecorded. Affinity does not reserve a CPU exclusively.

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

Native input conversion is outside timing; necessary copies, allocations and full factor output materialization are timed. This compares public API tasks, not identical algorithms. NM Dev Inverse detects symmetry and attempts Cholesky on SPD inputs, falling back to LU if that path raises a runtime exception. The exact measured JAR was verified separately by bytecode and runtime constructor-breakpoint tracing; see the [fairness audit](../2026-10-18-fairness-audit/README.md). F2J transpose/addition/scaling use adapter-owned Java loops because LAPACK does not define those matrix convenience APIs.

## 64 × 64 matrices

| Operation | NMDEV | EJML | COMMONS | JAMA | COLT | PARALLEL_COLT | JBLAS | F2J_LAPACK | MTJ | OJALGO | UJMP |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| transpose | 0.0039702 ± 0.00019 | 0.0032517 ± 0.00014 | 0.0047412 ± 0.00021 | 0.0045587 ± 0.00029 | 0.0039691 ± 0.00071 | 0.0038187 ± 0.00032 | 0.0044554 ± 0.00017 | 0.0048602 ± 0.00021 | 0.010694 ± 0.0012 | 0.0052099 ± 0.00025 | 0.024113 ± 0.0012 |
| addition | 0.0027391 ± 0.00028 | 0.002455 ± 9.2e-05 | 0.0042159 ± 0.00037 | 0.0033434 ± 0.00011 | 0.0045049 ± 0.00032 | 0.004329 ± 0.00019 | 0.0025364 ± 0.00019 | 0.012167 ± 0.00065 | 0.016577 ± 0.00046 | 0.0028027 ± 0.00013 | 0.0061402 ± 0.00031 |
| scale | 0.0021898 ± 5.4e-05 | 0.0021311 ± 7.4e-05 | 0.0037556 ± 0.00016 | 0.002995 ± 0.00015 | 0.0032758 ± 7.7e-05 | 0.0038059 ± 0.0002 | 0.0021535 ± 4.6e-05 | 0.0021719 ± 0.00012 | 0.013692 ± 0.00053 | 4.9356e-05 ± 2.1e-06 | 0.0054994 ± 0.00028 |
| multiplication | 0.17538 ± 0.026 | 0.10412 ± 0.0047 | 0.1622 ± 0.017 | 0.15749 ± 0.01 | 0.23193 ± 0.029 | 0.20319 ± 0.013 | 0.034223 ± 0.0013 | 0.14991 ± 0.0078 | 0.16235 ± 0.0077 | 0.10386 ± 0.007 | 0.15039 ± 0.013 |
| determinant | 0.036012 ± 0.0022 | 0.050019 ± 0.0029 | 0.05525 ± 0.0021 | 0.050337 ± 0.0019 | 0.078948 ± 0.0044 | 0.18408 ± 0.05 | 0.035901 ± 0.0015 | 0.039635 ± 0.00077 | 0.0497 ± 0.0027 | 0.022511 ± 0.00089 | 0.04942 ± 0.0024 |
| inverse | 0.34316 ± 0.016 | 0.22919 ± 0.01 | 0.10696 ± 0.011 | 0.17982 ± 0.012 | 0.25285 ± 0.021 | 0.48502 ± 0.042 | 0.065254 ± 0.0026 | 0.12296 ± 0.0065 | 0.20096 ± 0.028 | 0.13219 ± 0.015 | 0.18728 ± 0.0085 |
| inverseSPD | 0.42303 ± 0.058 | 0.10585 ± 0.005 | 0.088603 ± 0.0036 | 0.25861 ± 0.016 | 0.33323 ± 0.0098 | 0.51264 ± 0.053 | 0.054212 ± 0.004 | 0.09072 ± 0.0086 | 0.2739 ± 0.0088 | 0.073997 ± 0.0014 | 0.26409 ± 0.017 |
| cholesky | 0.032253 ± 0.0015 | 0.032082 ± 0.0017 | 0.040351 ± 0.0041 | 0.029985 ± 0.00083 | 0.03757 ± 0.0021 | 0.12533 ± 0.0063 | 0.018944 ± 0.00097 | 0.028358 ± 0.0017 | 0.080125 ± 0.0052 | 0.02463 ± 0.0018 | 0.032786 ± 0.0018 |
| lu | 0.037204 ± 0.0031 | 0.057429 ± 0.0028 | 0.065733 ± 0.0057 | 0.058106 ± 0.0013 | 0.093769 ± 0.0089 | 0.19382 ± 0.048 | 0.037949 ± 0.0014 | 0.048994 ± 0.0037 | 0.048585 ± 0.001 | 0.038802 ± 0.0063 | 0.057779 ± 0.0034 |
| qr | 0.27939 ± 0.0095 | 0.17481 ± 0.017 | 0.20312 ± 0.017 | 0.3032 ± 0.014 | 0.40277 ± 0.046 | 0.63006 ± 0.16 | 0.12243 ± 0.007 | 0.20297 ± 0.018 | 0.2022 ± 0.0065 | 0.19007 ± 0.016 | 0.31405 ± 0.0083 |
| eigenSymmetric | 2.3953 ± 0.46 | 0.55533 ± 0.045 | 0.64099 ± 0.021 | 0.62118 ± 0.044 | 0.61765 ± 0.039 | 0.70196 ± 0.1 | 0.39466 ± 0.017 | 0.81831 ± 0.044 | 0.6614 ± 0.085 | 0.26877 ± 0.01 | 0.62125 ± 0.034 |
| svd | 1.8857 ± 0.14 | 1.1353 ± 0.089 | 1.5958 ± 0.09 | 1.4657 ± 0.056 | 1.4493 ± 0.053 | 1.9584 ± 0.88 | 1.0531 ± 0.04 | 1.6287 ± 0.12 | 1.8445 ± 0.74 | 0.63153 ± 0.059 | 1.5561 ± 0.17 |
| solve | 0.093838 ± 0.011 | 0.056686 ± 0.0023 | 0.062741 ± 0.0018 | 0.05779 ± 0.0048 | 0.10242 ± 0.011 | 0.19474 ± 0.025 | 0.023493 ± 0.0008 | 0.045697 ± 0.0029 | 0.043814 ± 0.00058 | 0.032291 ± 0.0057 | 0.060152 ± 0.0045 |

![Timing ratios relative to NM Dev at size 64](relative-times-64.svg)

NM Dev is 1×. Below 1× means less time than NM Dev; above 1× means more time. Logarithmic scale. Use the interactive HTML for exact point details.

## 256 × 256 matrices

| Operation | NMDEV | EJML | COMMONS | JAMA | COLT | PARALLEL_COLT | JBLAS | F2J_LAPACK | MTJ | OJALGO | UJMP |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| transpose | 0.19332 ± 0.016 | 0.17368 ± 0.0091 | 0.077141 ± 0.0062 | 0.074084 ± 0.0064 | 0.15751 ± 0.012 | 0.15233 ± 0.0084 | 0.13016 ± 0.014 | 0.16199 ± 0.0083 | 0.15389 ± 0.0091 | 0.094234 ± 0.0056 | 0.49752 ± 0.074 |
| addition | 0.06149 ± 0.014 | 0.040887 ± 0.0085 | 0.052559 ± 0.0024 | 0.042799 ± 0.0008 | 0.09396 ± 0.003 | 0.074378 ± 0.0025 | 0.040655 ± 0.0037 | 0.21861 ± 0.0048 | 0.26488 ± 0.012 | 0.046558 ± 0.0022 | 0.22469 ± 0.0092 |
| scale | 0.086961 ± 0.1 | 0.03437 ± 0.0016 | 0.04621 ± 0.0024 | 0.039061 ± 0.0018 | 0.053651 ± 0.0048 | 0.05713 ± 0.002 | 0.03463 ± 0.0015 | 0.035533 ± 0.0011 | 0.22593 ± 0.026 | 4.8922e-05 ± 1.7e-06 | 0.21976 ± 0.0082 |
| multiplication | 16.719 ± 4.1 | 5.5781 ± 0.33 | 13.731 ± 0.54 | 12.832 ± 0.39 | 33.579 ± 2.9 | 33.427 ± 1 | 2.1841 ± 0.14 | 9.4675 ± 1.1 | 10.204 ± 0.32 | 5.613 ± 0.43 | 15.755 ± 1.1 |
| determinant | 5.4998 ± 2.4 | 3.8443 ± 0.14 | 4.1 ± 0.14 | 3.6442 ± 0.16 | 3.3745 ± 0.3 | 8.1931 ± 0.32 | 1.1133 ± 0.046 | 3.4104 ± 0.2 | 3.6801 ± 0.23 | 1.4085 ± 0.077 | 3.6871 ± 0.18 |
| inverse | 20.146 ± 2.7 | 13.934 ± 0.53 | 6.3744 ± 0.22 | 11.748 ± 0.63 | 12.85 ± 0.42 | 44.587 ± 2.9 | 2.7953 ± 0.24 | 11.516 ± 0.46 | 12.682 ± 0.51 | 9.7189 ± 1.1 | 11.707 ± 0.79 |
| inverseSPD | 17.328 ± 1 | 10.084 ± 0.47 | 4.0338 ± 0.13 | 24.83 ± 0.57 | 34.788 ± 3.1 | 15.583 ± 0.79 | 2.3234 ± 0.17 | 5.5125 ± 0.11 | 14.889 ± 1.5 | 4.7229 ± 0.26 | 26.343 ± 0.77 |
| cholesky | 1.9637 ± 0.18 | 1.7963 ± 0.069 | 1.6736 ± 0.12 | 1.9308 ± 0.13 | 1.5852 ± 0.18 | 2.4259 ± 0.15 | 0.54789 ± 0.07 | 1.9576 ± 0.25 | 2.5483 ± 0.15 | 1.0415 ± 0.048 | 1.8761 ± 0.038 |
| lu | 6.0058 ± 3.2 | 4.3437 ± 0.24 | 4.4804 ± 0.41 | 3.9795 ± 0.41 | 3.6049 ± 0.34 | 8.1745 ± 0.32 | 1.153 ± 0.043 | 3.5978 ± 0.097 | 3.3639 ± 0.12 | 1.6198 ± 0.065 | 3.7744 ± 0.19 |
| qr | 21.663 ± 1.9 | 10.268 ± 0.59 | 12.205 ± 0.62 | 39.652 ± 2.6 | 106.57 ± 4.7 | 20.957 ± 2.2 | 5.1835 ± 0.25 | 15.034 ± 0.48 | 14.855 ± 0.5 | 4.86 ± 0.26 | 38.356 ± 1.2 |
| eigenSymmetric | 56.84 ± 10 | 25.388 ± 1.1 | 45.923 ± 2.8 | 46.41 ± 1.5 | 45.421 ± 1.6 | 50.14 ± 2 | 9.3937 ± 0.58 | 30.596 ± 1.8 | 26.309 ± 2.5 | 11.455 ± 0.73 | 46.693 ± 2.4 |
| svd | 104.1 ± 3 | 50.607 ± 0.68 | 143.99 ± 6.7 | 136.87 ± 17 | 132.58 ± 7.1 | 58.843 ± 5 | 117.72 ± 2.6 | 148.85 ± 7 | 60.094 ± 4.8 | 22.938 ± 0.92 | 132.69 ± 5.1 |
| solve | 5.4006 ± 2 | 4.1918 ± 0.32 | 4.0269 ± 0.12 | 3.9355 ± 0.3 | 3.7064 ± 0.14 | 11.322 ± 4.4 | 0.78152 ± 0.077 | 3.3853 ± 0.25 | 3.5278 ± 0.25 | 1.508 ± 0.066 | 3.7623 ± 0.19 |

![Timing ratios relative to NM Dev at size 256](relative-times-256.svg)

NM Dev is 1×. Below 1× means less time than NM Dev; above 1× means more time. Logarithmic scale. Use the interactive HTML for exact point details.

## Limits

No aggregate speedup is reported because averaging operation ratios imposes an arbitrary workload weighting. This run uses one deterministic family of well-conditioned square matrices. It does not measure sparse matrices, conic optimization, parallel scaling, very large inputs or stability on difficult problems. Parameter order is deterministic, and the VM is shared. Reproduce your own workload on dedicated hardware before making broad performance claims.

## Raw evidence

- [JMH JSON and per-fork iteration means](jmh.json)
- [Complete JMH log](jmh.log)
- [Summary CSV](summary.csv)
- [Baseline correctness residuals](correctness.csv)
- [Targeted pivot/sign checks](pivot-correctness.csv)
- [Environment and requested thread settings](environment.json)
- [Backend records](backends.json)
- [Post-run backend configuration probe](backend-observation.txt)
- [Artifact versions and SHA-256 checksums](artifacts.json)
- [Measured source checksums](source-checksums.json)
- [Dependency release metadata](dependency-metadata.json)

Regenerate with `python3 benchmark/scripts/report.py benchmark/results/2026-10-18-expanded`. Python only summarizes measurements and renders charts; all measured calculations run in Java/JMH. Matplotlib is required to regenerate charts and the self-contained HTML.
