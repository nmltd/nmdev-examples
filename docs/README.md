# NM Dev tutorial guide

Start with [Getting Started](Getting-Started.md), then work through the tutorials in this order.

| Tutorial | Runnable class in `dev.nm.examples` |
|---|---|
| [Cholesky factorization in Java](tutorials/Cholesky-Factorization.md) | `CholeskyExample` |
| [Root finding by bisection in Java](tutorials/Root-Finding.md) | `RootFindingExample` |
| [Linear interpolation in Java](tutorials/Linear-Interpolation.md) | `InterpolationExample` |
| [Normal probabilities and quantiles in Java](tutorials/Normal-Probabilities.md) | `NormalProbabilityExample` |
| [Ordinary least squares regression in Java](tutorials/Linear-Regression.md) | `LinearRegressionExample` |
| [Constrained quadratic programming in Java](tutorials/Quadratic-Programming.md) | `QuadraticProgrammingExample` |

## More practical examples

| Tutorial | Runnable class in `dev.nm.examples` |
|---|---|
| [Solve a linear system without an inverse](tutorials/Linear-Solves.md) | `LinearSolveExample` |
| [Least squares with QR and SVD](tutorials/Least-Squares-QR-SVD.md) | `LeastSquaresExample` |
| [Principal component analysis with SVD](tutorials/Principal-Component-Analysis.md) | `PCAExample` |
| [Numerical integration with Simpson quadrature](tutorials/Numerical-Integration.md) | `IntegrationExample` |
| [Unconstrained optimization with BFGS](tutorials/Unconstrained-Optimization.md) | `UnconstrainedOptimizationExample` |

Also see [the copied starter project guide](starter-project.md), [source provenance](Source-Provenance.md) and [validation notes](Validation.md).

Every tutorial explains the problem, mathematical formulation, API calls, expected results, assumptions and an exercise. Run all eleven with:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.RunAllExamples
```
