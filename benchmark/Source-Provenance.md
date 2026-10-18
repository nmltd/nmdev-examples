# Source provenance and migration

The source benchmark directory was inspected and copied from [`nmltd/numerical-methods-java`](https://github.com/nmltd/numerical-methods-java/tree/d941c30553488363a5a5258a7c85f49082df70fb/benchmark) at commit `d941c30553488363a5a5258a7c85f49082df70fb`. The original benchmark framework and adapters credit Ken Yiu and Numerical Method Inc.; the current work is maintained by NM LTD.

The maintained code adapts the original 13 linear-algebra operations and the NM Dev, Apache Commons, EJML and JAMA calling patterns. It replaces the legacy stopwatch/framework with JMH, updates dependency coordinates and APIs, preserves returned values, adds independent validation and separates input conversion from timing. Package names move from `com.numericalmethod.benchmark` to `dev.nm.benchmark`.

Per the user's instruction, the copied archival suite was removed rather than publishing unused adapters. Colt, Parallel Colt, JBLAS, JLAPACK, LA4J, MTJ, ojAlgo, UJMP and R/JRI adapters are not in the maintained project or measured report. Their removal is a scope decision, not a demonstration that their latest releases are unavailable or unusable. Obsolete dependency declarations, Windows/R setup notes, optimization harnesses and 2015 chart assets were also removed.

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

