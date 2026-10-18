# Benchmark methodology

## What is measured

JMH 1.37 measures average milliseconds per completed operation. Each library receives the same seeded input values and uses its native matrix representation. The benchmark returns the resulting matrix, scalar or array of factor objects to JMH's generated consumer. No network requests or random generation occur in timed operations.

Inputs are created and converted in trial setup. Outputs are allocated inside the operation, as required by the selected APIs. EJML decomposition APIs receive a fresh copy when the adapter uses an in-place decomposition, protecting repeated inputs; that copy is timed. Other APIs may make internal copies. This measures the cost of the documented adapter calls, not a lower-level allocation-free kernel.

Each operation includes a string switch and the adapter invocation. This common dispatch overhead can matter for the smallest, fastest operations.

## Input data

`java.util.Random` uses seed `20261018 + n` for each matrix size. A has bounded off-diagonal entries `(uniform[0,1)-0.5)/n`, with `1+i/n` added to diagonal entry i. B has similarly bounded entries without the diagonal addition. The SPD matrix is `A^T A`; the right-hand side has n rows and one column.

These deterministic dense inputs are well-conditioned and intentionally avoid near-singularity. Results say nothing about sparse, ill-conditioned, rectangular, low-rank or highly structured inputs. QR and SVD use square matrices in this run, unlike some historical defaults. A single seed is not a workload distribution.

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

NM Dev uses `Inverse` for both general and SPD inverse, matching the original adapter. EJML, Commons Math and JAMA use Cholesky-based paths for the SPD inverse. General inversion paths can also differ by library. These are task-level API comparisons; a result is not a controlled comparison of identical algorithms.

Factor retrieval and any required materialization are timed. Libraries differ in pivot representation and factor storage. EJML's symmetric-eigen adapter assembles dense Q and D from the returned eigenpairs so it returns the same mathematical outputs. No attempt is made to tune each library beyond the adapters shown in source. Review the adapter source before drawing conclusions about API choices.

## Correctness checks

The independent reference code uses ordinary Java loops, not another measured adapter. All 156 combinations (13 operations, four libraries, sizes 4/64/256) are checked twice. For matrices, the reported residual is the maximum absolute entry error divided by the larger of 1 and the maximum absolute entry of the expected matrix. Determinants use relative scalar error. Orthogonality tests are included for QR, eigen and SVD. Any nonfinite output or residual above `1e-8` fails the run.

These are numerical sanity checks on the chosen inputs, not a comprehensive numerical-accuracy or stability study. Setup validation is excluded from timing. A failed check stops execution instead of silently suppressing a library or reporting its time as zero.

## Timing and interpretation

Every case uses two fresh JVM forks, three 300 ms warmup iterations and five 500 ms measurement iterations per fork. One worker is used. JVM options set a 1 GiB fixed heap and one active processor. This constrains JVM-visible parallelism but is not CPU affinity pinning and does not audit every possible native thread. The selected adapters use Java implementations without configured native BLAS.

The JSON preserves all ten measured iteration means per case and JMH's 99.9% confidence interval. The report displays mean timings and their JMH error half-widths. Overlapping intervals or high relative errors make fine rankings unreliable; two forks are a baseline rather than an exhaustive variability study.

No forced garbage collection is requested between invocations. Allocation, garbage collection, JIT behavior and API implementation costs can influence results. Benchmark cases run in JMH's deterministic parameter order, not randomized order. Environmental drift can therefore affect comparisons.

The host is a shared virtual machine/container. CPU scheduling, unrelated work, frequency changes and thermal conditions are not controlled. This is a reproducible local baseline, not a claim of universal superiority. Larger inputs, more forks, longer warmup and dedicated hardware are appropriate for publication-quality performance claims.
