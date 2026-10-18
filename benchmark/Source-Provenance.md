# Source provenance and migration

The source benchmark directory was inspected and copied from [`nmltd/numerical-methods-java`](https://github.com/nmltd/numerical-methods-java/tree/d941c30553488363a5a5258a7c85f49082df70fb/benchmark) at commit `d941c30553488363a5a5258a7c85f49082df70fb`. The original benchmark framework and adapters credit Ken Yiu and Numerical Method Inc.; the current work is maintained by NM LTD.

The maintained code adapts the original 13 linear-algebra operations and the NM Dev, Apache Commons, EJML and JAMA calling patterns. It replaces the legacy stopwatch/framework with JMH, updates dependency coordinates and APIs, preserves returned values, adds independent validation and separates input conversion from timing. Package names move from `com.numericalmethod.benchmark` to `dev.nm.benchmark`.

The initial migrated run covered four libraries. The expanded rerun adds Colt, Parallel Colt, jblas, MTJ, ojAlgo and UJMP adapters using the original calling patterns with current APIs and correctness checks. A direct F2J adapter replaces the unavailable legacy JLAPACK distribution and is named separately. The obsolete archival framework, historical web reports, LA4J and R/JRI are not part of the current requested eleven-library comparison.

Original operation mapping:

| Original class | Current operation |
|---|---|
| MatrixTranspose | transpose |
| MatrixAddition | addition |
| MatrixScale | scale |
| MatrixMultiplication | multiplication |
| MatrixDeterminant | determinant |
| MatrixInverse | inverse |
| MatrixInverseSPD | inverseSPD |
| CholeskyDecomposition | cholesky |
| LUDecomposition | lu |
| QRDecomposition | qr |
| EigenDecompositionSymmetric | eigenSymmetric |
| SingularValueDecomposition | svd |
| SolvingEquality | solve |

Each new adapter is under `src/main/java/dev/nm/benchmark/`. See [methodology](METHODOLOGY.md) for changes to input size, timing boundaries and algorithm choices. No NM Dev binary or license credential is published in this repository.

