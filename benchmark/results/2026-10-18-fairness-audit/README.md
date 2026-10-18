# Benchmark fairness audit — 18 October 2026

This audit repairs the measurement harness and expands coverage. It does not change nmdev's numerical implementations. [Current latest-version baseline](../2026-10-18-latest/README.md) · [Earlier fairness checkpoint](../2026-10-18-fairness-corrected/README.md) · [Harness source](../../src/main/java/dev/nm/benchmark)

## Corrected deferred scaling

The historical expanded ojAlgo adapter returned `a.multiply(1.5)`. In ojAlgo 57.5.0 that call returns a deferred scalar-operation store. Validation outside timing evaluated its entries, so passing numerical checks did not prove the timed call completed scaling. Returning the expression to a JMH blackhole did not force its entries to be calculated.

The corrected adapter materializes nonphysical scalar/addition/inverse/solve outputs inside the timed call. Already physical stores are returned directly, avoiding an unnecessary extra copy. Every ojAlgo decomposition factor requiring materialization remains materialized. The trial setup rejects nonphysical ojAlgo matrix outputs, checks correctness twice and verifies exact native-input preservation for all adapters. A regression check explicitly rejects the old deferred expression.

Historical JSON and numeric data are preserved. Their report/chart annotations identify the invalid scaling comparison. The corrected baseline reruns all 286 cases rather than mixing a few new measurements into the historical run. Its shorter timing settings are recorded in the new archive, so differences from the historical run cannot all be attributed to the adapter fix.

## Verified SPD inverse path in the measured JAR

The exact combined obfuscated JAR matches the historical artifact record:

```text
SHA-256 a3e2b2492e79530b8e12920b48f48bfc295786b138aa26300a16b53bafe1386c
Bytes   4300123
```

Bytecode confirms that `Inverse` checks symmetry and attempts Cholesky, with general LU inversion as a runtime-exception fallback. Separate JDI constructor-breakpoint runs observe the actual constructors without modifying or replacing any JAR classes:

| Fixture | Chol constructors | LU constructors | Inverse residual |
|---|---:|---:|---:|
| Seeded SPD, 64×64 | 1 | 0 | 1.5543122344752192E-15 |
| Seeded SPD, 256×256 | 1 | 0 | 2.55351295663786E-15 |
| Seeded nonsymmetric control, 64×64 | 0 | 1 | 1.5543122344752192E-15 |

The previous statement that nmdev takes a general inverse path for these SPD inputs was incorrect. Reports and the report generator now describe symmetry detection, Cholesky and fallback correctly. Debugger observation is outside all timing runs.

Evidence: [bytecode](inverse-bytecode.txt), [64 SPD trace](inverse-spd-64-trace.txt), [256 SPD trace](inverse-spd-256-trace.txt), [nonsymmetric control](inverse-general-trace.txt). Reproduction commands are in the [guide](../../site/reproduce.md).

## Expanded coverage and validation

- Baseline: eleven libraries, thirteen operations, sizes 64/256; all 429 baseline correctness cases pass twice, with native-input checks. All 66 targeted pivot cases pass.
- Rectangular multiplication: four adapters (nmdev, EJML, ojAlgo, native jblas), four shapes including odd dimensions; all 16 independently checked cases pass twice. [Correctness record](rectangular-correctness.csv) and [latest timed track](../2026-10-18-latest-rectangular/README.md).
- Large square and controlled internal-thread profiles use sizes 512/1024 and explicit processor budgets. These tracks are not pooled with the baseline. A budget is a limit, not proof that an API uses all processors: the selected EJML DDRM routines remain sequential; native jblas worker counts are not independently audited.
- Exact JAR hashes, source snapshots, affinity requests, JMH overrides, GC/allocation metrics and raw measurements accompany each timed track. Shared-host interference remains possible; affinity is not exclusive core reservation. Native jblas worker counts are not independently audited.

## Difficult-input accuracy findings

The independent diagnostic has 165 cases across eleven libraries, with repeated execution and input-preservation checks. **162 pass and 3 fail.** All failures are retained in [the CSV](difficult-correctness.csv); the diagnostic exits nonzero.

| Library | Fixture / operation | Result |
|---|---|---|
| nmdev | Row-scaled pivoting matrix, default MR3 SVD | Reconstruction/orthogonality check fails; combined residual 0.184660650255 |
| nmdev | Tightly clustered SPD eigenvalues, default MR3 SVD | Reconstruction/orthogonality check fails; combined residual 2.95076969792e-5 |
| EJML | Exactly rank-deficient matrix, QR | Selected decomposition API returns failure |

The alternative nmdev Golub–Kahan SVD passes both difficult fixtures at residuals approximately 4.53e-14 and 4.29e-13. [Method comparison](svd-method-accuracy.csv). This identifies a follow-up accuracy investigation in the default MR3 path; no default algorithm was changed in this audit.

Hilbert solves/inverses use normalized Frobenius backward error, not an unscaled absolute residual. All those cases pass that test. A small backward error does not guarantee accurate solutions on an ill-conditioned problem. Other diagnostics retain reconstruction and orthogonality checks with a common 1e-8 threshold. See [methodology](../../site/methodology.md) and [diagnostic log](difficult-correctness.log).

## Latest-version rerun

The [version audit](../../dependency-audit/2026-10-18/README.md) identified newer Commons Math and Parallel Colt distributions. The current run uses Commons Math 4.0-SNAPSHOT (timestamp pinned), Parallel Colt 0.11.4 (unmodified upstream release source), and today's validated NM Dev 1.2.6 obfuscated customer JAR. Other libraries match their latest official releases found. The primary Commons adapter imports math4 classes.

The new NM Dev JAR is pinned by [CI provenance and SHA-256](../../dependency-audit/2026-10-18/nmdev-validated.json). Separate [64 SPD](latest-inverse-spd-64-trace.txt), [256 SPD](latest-inverse-spd-256-trace.txt), [nonsymmetric](latest-inverse-general-trace.txt) and [bytecode](latest-inverse-bytecode.txt) checks reproduce the same Cholesky/LU dispatch conclusion in the release binary. The original traces above remain evidence for the original published snapshot binary.

The latest difficult-input diagnostic again records **162 passes and 3 failures out of 165 cases**, with the same two default MR3 SVD failures and one rank-deficient EJML QR rejection. See [latest diagnostics](latest-difficult-correctness.csv) and [SVD method comparison](latest-svd-method-accuracy.csv). The library's default algorithm has not been changed.

A separate class-origin check confirms that all eleven primary implementations load from their selected JARs, including Math4 and Parallel Colt 0.11.4. See [loaded-class origins](latest-class-origins.csv). This untimed check also guards future runs against an older bundled implementation shadowing a chosen dependency.

The [latest diagnostic source snapshot](latest-diagnostic-source.zip) and [checksums](latest-diagnostic-source-checksums.json) preserve the exact validation harness used with the updated dependencies.

## Completed latest-version timing tracks

| Track | Cases | Computational budget | Report |
|---|---:|---|---|
| Baseline, 64/256, thirteen operations, eleven libraries | 286 | 1; CPU 0 | [Results](../2026-10-18-latest/README.md) |
| Rectangular multiplication, four shapes, four libraries | 16 | 1; CPU 0 | [Results](../2026-10-18-latest-rectangular/README.md) |
| Large square, 512/1024, five operations, four libraries | 40 | 1; CPU 0 | [Results](../2026-10-18-latest-large-1thread/README.md) |
| Large square, same tasks and libraries | 40 | 4; CPUs 0–3 | [Results](../2026-10-18-latest-large-4threads/README.md) |

All **382 timed cases** contain two complete forks with three measured iterations per fork and GC/allocation profiler data. Every profile passed the 429-case baseline and 66-case pivot guards before timing. Each timed fork also checked its selected task twice and verified native-input preservation. The large and rectangular runs used two 100 ms warmups and three 200 ms measurements; the baseline used two 200 ms warmups and three 200 ms measurements. These short shared-host experiments are diagnostic. Processor budgets do not guarantee parallel execution or exclusive cores, and means from separate tracks should not be pooled into a universal ranking.
