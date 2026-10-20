# Linear algebra benchmark results — 2026-10-18-latest-large-4threads

[Suite](../../../README.md) · [Methodology](../../../METHODOLOGY.md) · [Library coverage](../../../LIBRARY-COVERAGE.md)

> Corrected harness: deferred ojAlgo results are materialized inside timing, native inputs are checked for mutation, and every fork validates repeat execution. These short iterations provide a diagnostic baseline; use longer runs on dedicated hardware for fine rankings. See the [fairness audit](../fairness-audit/README.md).

**5 operations × 4 libraries × 2 sizes = 40 measured cases**, containing 240 measured iteration means. Lower milliseconds per operation is faster. These are observations on a shared VM, not universal performance rankings.

**[Interactive charts with tooltips](charts.html)** — download and open the self-contained HTML in a browser. Hover, tap or keyboard-focus a point for its version/backend, timing, confidence interval and ratio to NM Dev. GitHub inline image previews do not expose point tooltips. Standalone SVGs also contain native tooltip titles.

## Environment

AMD EPYC 9V74 80-Core Processor; Linux-6.18.55-x86_64-with-glibc2.39. Visible CPUs: 9; container CPU quota: `800000 100000`. JVM 17.0.20. No exclusive CPU reservation; any requested affinity is recorded below.

2 JVM forks per case; 2 warmup iterations of 100 ms; 3 measurement iterations of 200 ms. One JMH worker. The fork options fix a 1 GiB heap. Requested computational threads: 4; requested CPU affinity: 0-3. Affinity does not reserve a CPU exclusively.

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
| multiplication | 46.904 ± 19 | 44.195 ± 3.2 | 16.295 ± 1.2 | 17.773 ± 9.9 |
| cholesky | 11.187 ± 3.1 | 17.564 ± 1.3 | 4.2706 ± 1.8 | 19.925 ± 5.8 |
| lu | 28.387 ± 19 | 38.87 ± 2 | 9.675 ± 2.6 | 10.665 ± 1.1 |
| qr | 105.92 ± 35 | 79.522 ± 6.1 | 40.287 ± 3 | 44.832 ± 13 |
| solve | 34.196 ± 26 | 38.018 ± 2.4 | 6.2151 ± 1.3 | 10.102 ± 1.3 |

![Timing ratios relative to NM Dev at size 512](relative-times-512.svg)

NM Dev is 1×. Below 1× means less time than NM Dev; above 1× means more time. Logarithmic scale. Use the interactive HTML for exact point details.

## 1024 × 1024 matrices

| Operation | NMDEV | EJML | JBLAS | OJALGO |
| --- | ---: | ---: | ---: | ---: |
| multiplication | 223.74 ± 16 | 346.84 ± 32 | 128.24 ± 9.4 | 123.04 ± 76 |
| cholesky | 65.491 ± 10 | 56.477 ± 3.9 | 27.545 ± 5.3 | 102.26 ± 43 |
| lu | 124.71 ± 1.3e+02 | 317.85 ± 14 | 65.102 ± 31 | 102.75 ± 34 |
| qr | 448.12 ± 1.8e+02 | 640.52 ± 56 | 293.31 ± 55 | 261.62 ± 55 |
| solve | 98.914 ± 55 | 316.71 ± 43 | 44.556 ± 9.7 | 110.17 ± 44 |

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

Regenerate with `python3 benchmark/scripts/report.py benchmark/results/2026-10-18/large-4threads`. Python only summarizes measurements and renders charts; all measured calculations run in Java/JMH. Matplotlib is required to regenerate charts and the self-contained HTML.
