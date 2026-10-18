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

Also see [the copied starter project guide](starter-project.md), [source provenance](Source-Provenance.md) and [validation notes](Validation.md).

Every tutorial explains the problem, mathematical formulation, API calls, expected results, assumptions and an exercise. Run all six with:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.RunAllExamples
```
