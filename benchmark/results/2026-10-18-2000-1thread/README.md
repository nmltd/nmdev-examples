# Linear algebra benchmark results — 2026-10-18-2000-1thread

[Suite](../../README.md) · [Methodology](../../METHODOLOGY.md) · [Library coverage](../../LIBRARY-COVERAGE.md)

> Corrected harness: deferred ojAlgo results are materialized inside timing, native inputs are checked for mutation, and every fork validates repeat execution. These short iterations provide a diagnostic baseline; use longer runs on dedicated hardware for fine rankings. See the [fairness audit](../2026-10-18-fairness-audit/README.md).

**5 operations × 4 libraries × 1 sizes = 20 measured cases**, containing 120 measured iteration means. Lower milliseconds per operation is faster. These are observations on a shared VM, not universal performance rankings.

**[Interactive charts with tooltips](charts.html)** — download and open the self-contained HTML in a browser. Hover, tap or keyboard-focus a point for its version/backend, timing, confidence interval and ratio to NM Dev. GitHub inline image previews do not expose point tooltips. Standalone SVGs also contain native tooltip titles.

## Environment

AMD EPYC 9V74 80-Core Processor; Linux-6.18.55-x86_64-with-glibc2.39. Visible CPUs: 9; container CPU quota: `800000 100000`. JVM 17.0.20. No exclusive CPU reservation; any requested affinity is recorded below.

2 JVM forks per case; 2 warmup iterations of 100 ms; 3 measurement iterations of 200 ms. One JMH worker. The fork options fix a 1 GiB heap. Requested computational threads: 1; requested CPU affinity: 0. Affinity does not reserve a CPU exclusively.

Native thread environment variables request the recorded computational thread count. Parallel Colt, ojAlgo and UJMP also receive explicit library thread limits; MTJ uses an explicitly forced pure-Java F2J backend. Native jblas thread counts are not independently audited. See the environment and backend records below.

## Versions and backends

| Library | Measured version / backend |
| --- | --- |
| NMDEV | NM Dev 1.2.6 — Validated combined obfuscated 1.2.6 binary; ActiveProcessorCount=1; exact hash and source proof in validated-nmdev-artifact.json |
| EJML | EJML 0.46.1 — EJML 0.46.1 ejml-ddense CommonOps_DDRM and standard decomposition factories; these adapter API paths do not request EJML concurrent kernels |
| JBLAS | jblas 1.2.5 (native) — Bundled native jblas 1.2.5; native thread environment requests 1; actual native worker counts not independently audited |
| OJALGO | ojAlgo 57.5.0 — ojAlgo 57.5.0 R064Store/decomposition factories; configured thread limit=1; output factors materialized inside timing |

## Correctness and interpretation

At size 2000, all 40 measured JVM forks passed two repeat-execution checks before timing, including numerical residuals and native input preservation. Full-size residual values were not recorded as a separate CSV. See [full-size validation evidence](full-size-validation.json). The baseline CSV below covers sizes 4/64/256.

All **429 baseline correctness cases passed twice**. Every measured fork also checks its selected result before timing. Reference arithmetic and residual checks are outside timing.

All **66 targeted row-swap/row-cycle cases** also passed. These check determinant signs, LU permutation orientation and solves on pivoting inputs.

Tables show **mean ± JMH 99.9% confidence half-width**, in ms/op. These error bars are not standard deviations. Overlapping intervals or high uncertainty make fine rankings unreliable. Chart ratios are ratios of means and do not show ratio uncertainty.

Native input conversion is outside timing; necessary copies, allocations and full factor output materialization are timed. This compares public API tasks, not identical algorithms. NM Dev Inverse detects symmetry and attempts Cholesky on SPD inputs, falling back to LU if that path raises a runtime exception. The exact measured JAR was verified separately by bytecode and runtime constructor-breakpoint tracing; see the fairness audit. F2J transpose/addition/scaling use adapter-owned Java loops because LAPACK does not define those matrix convenience APIs.

## 2000 × 2000 matrices

| Operation | NMDEV | EJML | JBLAS | OJALGO |
| --- | ---: | ---: | ---: | ---: |
| multiplication | 5226.5 ± 1.4e+02 | 5188.5 ± 1.2e+03 | 879.27 ± 38 | 4691.8 ± 4.9e+02 |
| cholesky | 1190.5 ± 93 | 378.97 ± 2.1e+02 | 154.28 ± 15 | 593.45 ± 1.6e+02 |
| lu | 943.74 ± 1.9e+02 | 2483.9 ± 2.4e+02 | 362.65 ± 36 | 1914.7 ± 6.8e+02 |
| qr | 7084.9 ± 6e+02 | 6633.2 ± 8.3e+02 | 1743.1 ± 2.8e+02 | 3907.3 ± 8.8e+02 |
| solve | 957.07 ± 73 | 2666.7 ± 5.6e+02 | 294.01 ± 35 | 1966.8 ± 3.6e+02 |

![Timing ratios relative to NM Dev at size 2000](relative-times-2000.svg)

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
- [Exact harness source snapshot](harness-source.zip)
- [Deferred-output and mutation regression checks](fairness-check.txt)

Regenerate with `python3 benchmark/scripts/report.py benchmark/results/2026-10-18-2000-1thread`. Python only summarizes measurements and renders charts; all measured calculations run in Java/JMH. Matplotlib is required to regenerate charts and the self-contained HTML.
