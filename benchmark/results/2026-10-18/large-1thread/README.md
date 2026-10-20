# Linear algebra benchmark results — 2026-10-18-latest-large-1thread

[Suite](../../../README.md) · [Methodology](../../../METHODOLOGY.md) · [Library coverage](../../../LIBRARY-COVERAGE.md)

> Corrected harness: deferred ojAlgo results are materialized inside timing, native inputs are checked for mutation, and every fork validates repeat execution. These short iterations provide a diagnostic baseline; use longer runs on dedicated hardware for fine rankings. See the [fairness audit](../fairness-audit/README.md).

**5 operations × 4 libraries × 2 sizes = 40 measured cases**, containing 240 measured iteration means. Lower milliseconds per operation is faster. These are observations on a shared VM, not universal performance rankings.

**[Interactive charts with tooltips](charts.html)** — download and open the self-contained HTML in a browser. Hover, tap or keyboard-focus a point for its version/backend, timing, confidence interval and ratio to NM Dev. GitHub inline image previews do not expose point tooltips. Standalone SVGs also contain native tooltip titles.

## Environment

AMD EPYC 9V74 80-Core Processor; Linux-6.18.55-x86_64-with-glibc2.39. Visible CPUs: 9; container CPU quota: `800000 100000`. JVM 17.0.20. No exclusive CPU reservation; any requested affinity is recorded below.

2 JVM forks per case; 2 warmup iterations of 100 ms; 3 measurement iterations of 200 ms. One JMH worker. The fork options fix a 1 GiB heap. Requested computational threads: 1; requested CPU affinity: 0. Affinity does not reserve a CPU exclusively.

Native thread environment variables request the recorded computational thread count. Parallel Colt, ojAlgo and UJMP also receive explicit library thread limits; MTJ uses an explicitly forced pure-Java F2J backend. Native jblas thread counts are not independently audited. See the environment and backend records below.

## Versions and backends

| Library | Measured version / backend |
| --- | --- |
| NMDEV | NM Dev 1.2.6 |
| EJML | EJML 0.46.1 |
| JBLAS | jblas 1.2.5 (native) |
| OJALGO | ojAlgo 57.5.0 |

## Correctness and interpretation

All **429 baseline correctness cases passed twice**. Every measured fork also checks its selected result before timing. Reference arithmetic and residual checks are outside timing.

All **66 targeted row-swap/row-cycle cases** also passed. These check determinant signs, LU permutation orientation and solves on pivoting inputs.

Tables show **mean ± JMH 99.9% confidence half-width**, in ms/op. These error bars are not standard deviations. Overlapping intervals or high uncertainty make fine rankings unreliable. Chart ratios are ratios of means and do not show ratio uncertainty.

Native input conversion is outside timing; necessary copies, allocations and full factor output materialization are timed. This compares public API tasks, not identical algorithms. NM Dev Inverse detects symmetry and attempts Cholesky on SPD inputs, falling back to LU if that path raises a runtime exception. The exact measured JAR was verified separately by bytecode and runtime constructor-breakpoint tracing; see the fairness audit. F2J transpose/addition/scaling use adapter-owned Java loops because LAPACK does not define those matrix convenience APIs.

## 512 × 512 matrices

| Operation | NMDEV | EJML | JBLAS | OJALGO |
| --- | ---: | ---: | ---: | ---: |
| multiplication | 130.83 ± 35 | 42.669 ± 0.91 | 19.403 ± 6 | 45.365 ± 4 |
| cholesky | 18.309 ± 0.75 | 20.872 ± 18 | 3.7034 ± 0.27 | 9.9433 ± 2 |
| lu | 17.028 ± 20 | 43.38 ± 12 | 9.5693 ± 0.76 | 11.309 ± 3.7 |
| qr | 133.94 ± 27 | 84.687 ± 5.8 | 43.506 ± 3.9 | 46.478 ± 41 |
| solve | 24.607 ± 28 | 39.102 ± 5.5 | 5.8758 ± 0.83 | 11.619 ± 7.8 |

![Timing ratios relative to NM Dev at size 512](relative-times-512.svg)

NM Dev is 1×. Below 1× means less time than NM Dev; above 1× means more time. Logarithmic scale. Use the interactive HTML for exact point details.

## 1024 × 1024 matrices

| Operation | NMDEV | EJML | JBLAS | OJALGO |
| --- | ---: | ---: | ---: | ---: |
| multiplication | 801.6 ± 29 | 345.73 ± 22 | 132.31 ± 15 | 352.35 ± 41 |
| cholesky | 154.11 ± 17 | 52.673 ± 4.9 | 27.373 ± 2.6 | 73.96 ± 16 |
| lu | 71.267 ± 30 | 313.81 ± 20 | 54.94 ± 6.8 | 147.26 ± 39 |
| qr | 930.81 ± 2.1e+02 | 634.61 ± 27 | 295.97 ± 1.5e+02 | 307.87 ± 69 |
| solve | 92.236 ± 43 | 325.05 ± 73 | 42.004 ± 4.6 | 147.04 ± 18 |

![Timing ratios relative to NM Dev at size 1024](relative-times-1024.svg)

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
- [Artifact versions and SHA-256 checksums](artifacts.json)
- [Measured source checksums](source-checksums.json)
- [Exact harness source snapshot](harness-source.zip)
- [Deferred-output and mutation regression checks](fairness-check.txt)

Regenerate with `python3 benchmark/scripts/report.py benchmark/results/2026-10-18/large-1thread`. Python only summarizes measurements and renders charts; all measured calculations run in Java/JMH. Matplotlib is required to regenerate charts and the self-contained HTML.
