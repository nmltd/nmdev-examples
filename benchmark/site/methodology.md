# Benchmark methodology

## What is measured

JMH 1.37 measures average milliseconds per completed operation. Each library receives the same seeded input values and uses its native matrix representation. The benchmark returns the resulting materialized matrix, scalar or array of factor objects to JMH's generated consumer. Returning a lazy expression alone is insufficient. The corrected ojAlgo adapter materializes scalar expressions inside timing; existing physical stores are returned directly to avoid unnecessary extra copies. No network requests or random generation occur in timed operations.

Inputs are created and converted in trial setup. Outputs are allocated inside the operation, as required by the selected APIs. EJML decomposition APIs receive a fresh copy when the adapter uses an in-place decomposition, protecting repeated inputs; that copy is timed. Other APIs may make internal copies. This measures the cost of the documented adapter calls, not a lower-level allocation-free kernel.

Each operation includes a string switch and the adapter invocation. This common dispatch overhead can matter for the smallest, fastest operations.

## Input data

`java.util.Random` uses seed `20261018 + n` for each matrix size. A has bounded off-diagonal entries `(uniform[0,1)-0.5)/n`, with `1+i/n` added to diagonal entry i. B has similarly bounded entries without the diagonal addition. The SPD matrix is `A^T A`; the right-hand side has n rows and one column.

These baseline dense inputs are well-conditioned and intentionally avoid near-singularity. Separate rectangular multiplication and difficult-input accuracy tracks extend coverage; their results are never pooled with the baseline. No sparse performance is measured. QR and SVD use square matrices in this run, unlike some historical defaults. A single seed is not a workload distribution.

## Equivalent mathematical tasks and API differences

| Operation | Returned work / validation |
|---|---|
| Transpose, addition, scale, multiplication | Materialized result; independent reference arithmetic |
| Determinant | Scalar; independent partial-pivot elimination reference |
| Inverse / SPD inverse | Full inverse; product with input compared to identity |
| Cholesky | Lower factor; `L L^T` compared to SPD input |
| LU | L, U and row pivot information; `P A` compared to `L U` |
| QR | Q and R; reconstruction and Q orthogonality |
| Symmetric eigen | Full eigenvectors and diagonal eigenvalues; eigen equation and orthogonality |
| SVD | Full U, diagonal singular values, V; reconstruction and both orthogonality checks |
| Solve | New factorization plus solve for one RHS; residual against RHS |

NM Dev uses `Inverse` for both general and SPD inverse. `Inverse` detects symmetry and attempts Cholesky, with an LU fallback on a runtime exception. Bytecode and debugger constructor-breakpoint checks against the exact measured JAR confirm the SPD fixture takes Cholesky, without LU fallback. EJML, Commons Math and JAMA use Cholesky-based paths for the SPD inverse. General inversion paths can also differ by library. These are task-level API comparisons; a result is not a controlled comparison of identical algorithms.

Factor retrieval and any required materialization are timed. Libraries differ in pivot representation and factor storage. EJML's symmetric-eigen adapter assembles dense Q and D from the returned eigenpairs so it returns the same mathematical outputs. No attempt is made to tune each library beyond the adapters shown in source. Review the adapter source before drawing conclusions about API choices.

## Correctness checks

The independent reference code uses ordinary Java loops, not another measured adapter. The initial run checked 156 combinations. The expanded run checks all 429 combinations (13 operations, eleven libraries, sizes 4/64/256) twice, plus 66 targeted row-swap/row-cycle checks for determinant signs, LU permutations and solves. For matrices, the reported residual is the maximum absolute entry error divided by the larger of 1 and the maximum absolute entry of the expected matrix. Determinants use relative scalar error. Orthogonality tests are included for QR, eigen and SVD. Any nonfinite output or residual above `1e-8` fails the run.

These are numerical sanity checks on the chosen inputs, not a comprehensive numerical-accuracy or stability study. Setup validation is excluded from timing. A failed check stops execution instead of silently suppressing a library or reporting its time as zero.

## Timing and interpretation

Every case uses two fresh JVM forks, three 300 ms warmup iterations and five 500 ms measurement iterations per fork. One worker is used. JVM options set a 1 GiB fixed heap and one active processor. This constrains JVM-visible parallelism but is not CPU affinity pinning and does not audit every possible native thread. The expanded run includes native jblas, explicitly labelled, alongside Java implementations. MTJ uses explicitly forced F2J backends; UJMP external backends are disabled. Native thread environment variables request one thread, but native worker counts are not independently audited. Parallel Colt, ojAlgo and UJMP also receive library-specific one-thread settings.

The JSON preserves all ten measured iteration means per case and JMH's 99.9% confidence interval. The report displays mean timings and their JMH error half-widths. Overlapping intervals or high relative errors make fine rankings unreliable; two forks are a baseline rather than an exhaustive variability study.

No forced garbage collection is requested between invocations. Allocation, garbage collection, JIT behavior and API implementation costs can influence results. Benchmark cases run in JMH's deterministic parameter order, not randomized order. Environmental drift can therefore affect comparisons.

The host is a shared virtual machine/container. CPU scheduling, unrelated work, frequency changes and thermal conditions are not controlled. This is a reproducible local baseline, not a claim of universal superiority. Larger inputs, more forks, longer warmup and dedicated hardware are appropriate for publication-quality performance claims.

## Expanded adapter details

The expanded rerun preserves the initial harness timing settings and input seed. It reruns the original four libraries as well as the seven added adapters; their timing results are not spliced into the old measurements.

- Colt and Parallel Colt use native matrix objects. Copying precedes mutating addition/scaling APIs. Transpose is materialized. Parallel Colt includes the JPLASMA/core-lapack dependency required by its factorization routines.
- jblas uses its bundled native implementation. Its upper Cholesky factor is transposed into the common lower-factor output. LU permutation direction and determinant sign are checked on dedicated pivoting fixtures. SVD materializes the singular-value diagonal matrix for equivalent outputs.
- F2J calls translated BLAS/LAPACK directly on column-major arrays. Transpose, addition and scaling are wrapper-owned Java loops, because those convenience APIs are not defined by LAPACK. Necessary copies, workspaces, pivot conversion and dense factor extraction are timed. This is the named replacement distribution, not the old JLAPACK binary.
- MTJ copies inputs for mutating operations, converts its swap sequence to a row order, and uses an LU-based determinant rather than the old adapter's eigendecomposition. Its upper Cholesky factor and SVD V-transpose output are materialized in the common orientation.
- ojAlgo uses current R064Store and decomposition factories; lazy factor views are materialized before returning timed outputs.
- UJMP uses only its core algorithms, with optional external backends disabled. Its results pass independent arithmetic and factorization checks.

These choices are visible in source and are part of the measured API workload. They should not be interpreted as a claim that every adapter is optimally tuned for its library.

## Fairness revision 2 and expanded tracks

The earlier expanded archive timed an unevaluated ojAlgo scaling expression. Those scaling values are invalid for completed-work comparisons. Historical JSON remains unchanged and the archive report is marked. The corrected run reruns the full 286-case matrix; it does not splice corrected values into an older run.

The corrected trial setup runs each operation twice, validates outputs and checks bit-for-bit preservation of native A/B/SPD/RHS inputs. It rejects any ojAlgo matrix output that is not a physical store. `FairnessValidationMain` demonstrates rejection of the old deferred scalar result and of a changed input. These checks are outside timed methods.

The baseline still has 13 operations and eleven adapters. The large profile has sizes 512/1024/2000 and multiplication, LU, Cholesky, QR and solve for NM Dev, EJML, ojAlgo and native jblas. The rectangular profile measures multiplication for those same four libraries at shapes 128×512×64, 512×128×256, 1024×256×64 and 255×257×129 (m×k×n). Each measured rectangular fork independently checks arithmetic twice and exact input preservation.

`BENCH_THREADS` configures JVM-visible processors, explicit adapter thread limits and requested native thread environment variables. `BENCH_CPUSET` optionally pins the launcher and inherited fork processes. One JMH worker is retained: this measures internal library scaling, not concurrent client throughput. EJML's selected DDRM calls remain sequential; increasing the processor budget does not substitute a different parallel API. Native jblas worker counts remain unaudited. Affinity does not reserve physical cores or eliminate shared-host interference.

All runs include JMH's GC profiler, raw allocation metrics, exact invocation overrides, dependency hashes and a zipped harness-source snapshot. Short audit runs are diagnostic; longer defaults remain available for stronger timing estimates. Sizes, thread budgets and fork counts must be compared within their own run.

## Difficult-input accuracy diagnostics

`DifficultValidationMain` tests row scaling with pivoting, tightly clustered eigenvalues, an 8×8 Hilbert matrix, and exact rank deficiency. It records every PASS/FAIL and exits nonzero if any case fails. Failed cases are retained in the audit and are not silently dropped or represented by a zero timing.

For solves and inverses, these diagnostics use Frobenius-norm backward error `||AX-B|| / (||A|| ||X|| + ||B||)`, checking both multiplication orientations for an inverse. This assesses the residual relative to the computation's scale; it does not imply a small forward error for an ill-conditioned system. Other operations retain reconstruction/orthogonality checks and the common `1e-8` threshold. A failed rank-deficient QR call is recorded as an API failure, not proof that a mathematically valid QR factorization cannot exist.


## Machine-capacity and Commons block comparisons (application 1.2.0)

The default application now runs **1,260 cases**, with eleven libraries and twelve implementation variants. Existing 1-thread baseline, 1/4-thread large, and 1-thread rectangular profiles remain. Additional `baseline-all-cores`, `large-all-cores`, and `rectangular-all-cores` profiles use every logical processor reported by the launcher JVM's `Runtime.availableProcessors()`. They remain separate even on machines with only one or four processors. JMH still uses one caller: the timed unit is one matrix operation, with library-internal parallelism permitted.

All-core requests set JVM processor visibility, `benchmark.threads`, and native BLAS/OpenMP thread environment variables to that count. This makes processors available; it does not force sequential algorithms to run in parallel. In particular, Commons row/block multiplication, JAMA, Colt, the current EJML `CommonOps_DDRM` adapter, and F2J stay sequential. Parallel Colt, ojAlgo and UJMP receive explicit worker limits; NM Dev uses JVM processor visibility; native jblas worker counts are not independently audited. This is a comparison of the configured APIs on the same hardware, including parallel versus sequential execution, rather than a claim of equal actual worker counts or exhaustive fastest-API selection.

`COMMONS` retains `Array2DRowRealMatrix`; `COMMONS_BLOCK` uses `BlockRealMatrix` from the exact same locked Commons Math 4 binary. Both are measured for every operation, including all large sizes and rectangular products. Every native input is constructed before timing; timed result allocation and decomposition-internal conversions remain included. Both variants receive repeated correctness and input-preservation checks. A block layout improves locality but does not itself provide parallelism.

`machine.json` records CPU model, physical-core information when detectable, JVM-visible logical processors, memory, OS/JDK, JVM options and Linux container limits/cpuset where available. Windows additionally records host physical cores and logical processors. All-core means the resources visible to the JVM; do not launch with an artificial `-XX:ActiveProcessorCount` cap when measuring machine capacity. VM/container restrictions and OS affinity can limit resources; worker requests are not measured utilization or exclusive core reservation.

Keep profiles and Commons variants separate in reporting. For matched operation/size cases, speedup is comparison time / candidate time, and time reduction is 100 × (comparison time − candidate time) / comparison time. Never pool the 1-thread, 4-thread and all-core profiles into one ranking. Published historical results remain unchanged until a new run is validated.

Build and run the new experiment from the repository root:

```powershell
mvn -s benchmark/settings.xml -gs benchmark/settings.xml -f benchmark/pom.xml package
java -jar benchmark/target/nmdev-benchmark-1.2.0.jar --output nmdev-benchmark-all-cores
```

Use this **new output directory**; 1.2.0 changes the measurement specification and cannot resume the older 660-case experiment. Keep its completed results and original JAR. Repeat the same new command with the same 1.2.0 JAR to resume the new experiment.
