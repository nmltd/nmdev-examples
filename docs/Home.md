# Numerical computing in Java with NM Dev

Build numerical applications with runnable examples, mathematical explanations and executable checks. Start with a small calculation, then explore linear algebra, statistics, integration and optimization.

**[Get started](Getting-Started.md) · [Explore eleven tutorials](README.md) · [Compare benchmark results](benchmarks.html)**

## Solve a real system in a few lines

```java
Matrix a = new DenseMatrix(new double[][]{{3, 1}, {1, 2}});
Vector b = new DenseVector(new double[]{9, 8});
Vector x = new LinearSystemSolver(1e-12)
    .solve(a).getParticularSolution(b);
// x = (2, 3). Matrix and vector indices start at 1.
```

[Read the complete linear-system tutorial](tutorials/Linear-Solves.md) for imports, setup, runnable source and residual checks.

## Choose your next example

| Goal | Tutorial |
|---|---|
| Solve equations and fit data | [Linear systems](tutorials/Linear-Solves.md), [QR and SVD least squares](tutorials/Least-Squares-QR-SVD.md) |
| Understand variation | [PCA](tutorials/Principal-Component-Analysis.md), [linear regression](tutorials/Linear-Regression.md) |
| Compute integrals and probabilities | [Simpson quadrature](tutorials/Numerical-Integration.md), [normal probabilities](tutorials/Normal-Probabilities.md) |
| Find a minimum | [BFGS optimization](tutorials/Unconstrained-Optimization.md), [constrained quadratic programming](tutorials/Quadratic-Programming.md) |

## Explore performance with evidence

[Open the interactive benchmarks](benchmarks.html) to compare eleven libraries, select visible libraries and inspect individual timings. Read the methodology and exact backend settings before interpreting the results. Source code, measurements and archived runs are available for reproduction.

## Install and evaluate NM Dev

Examples require Java, Maven, GitHub Packages credentials and a valid NM Dev license. [Follow the setup guide](Getting-Started.md) or [visit NM Dev for evaluation and licensing](https://nm.dev/products/nmdev).

[NM Dev API reference](https://nmltd.github.io/nmdev/) · [Example source on GitHub](https://github.com/nmltd/nmdev-examples)
