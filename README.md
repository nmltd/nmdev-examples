# NM Dev Examples

Learn numerical programming in Java with runnable NM Dev examples: linear algebra, root finding, interpolation, probability, regression and constrained optimization.

**[Website](https://nmltd.github.io/nmdev-examples/) · [Get started](https://nmltd.github.io/nmdev-examples/getting-started.html) · [Tutorials](https://nmltd.github.io/nmdev-examples/tutorials.html) · [API reference](https://nmltd.github.io/nmdev/) · [NM Dev](https://nm.dev/products/nmdev)**

## Run your first example

Requires Java 8 or later, Maven, GitHub Packages credentials and a valid NM Dev license. A public trial license is included as [`nm.lic`](nm.lic) in the repository root; its validity and module entitlements are enforced by NM Dev. Replace it with a valid trial or commercial license when needed. See [setup instructions](https://nmltd.github.io/nmdev-examples/getting-started.html) before running:

```bash
mvn -U clean verify
mvn exec:java -Dexec.mainClass=dev.nm.examples.RunAllExamples
```

## Tutorials

| Tutorial | Runnable class in `dev.nm.examples` |
|---|---|
| [Cholesky factorization in Java](https://nmltd.github.io/nmdev-examples/tutorials/Cholesky-Factorization.html) | `CholeskyExample` |
| [Root finding by bisection in Java](https://nmltd.github.io/nmdev-examples/tutorials/Root-Finding.html) | `RootFindingExample` |
| [Linear interpolation in Java](https://nmltd.github.io/nmdev-examples/tutorials/Linear-Interpolation.html) | `InterpolationExample` |
| [Normal probabilities and quantiles in Java](https://nmltd.github.io/nmdev-examples/tutorials/Normal-Probabilities.html) | `NormalProbabilityExample` |
| [Ordinary least squares regression in Java](https://nmltd.github.io/nmdev-examples/tutorials/Linear-Regression.html) | `LinearRegressionExample` |
| [Constrained quadratic programming in Java](https://nmltd.github.io/nmdev-examples/tutorials/Quadratic-Programming.html) | `QuadraticProgrammingExample` |
| [Linear systems](https://nmltd.github.io/nmdev-examples/tutorials/Linear-Solves.html) | `LinearSolveExample` |
| [QR/SVD least squares](https://nmltd.github.io/nmdev-examples/tutorials/Least-Squares-QR-SVD.html) | `LeastSquaresExample` |
| [Principal component analysis](https://nmltd.github.io/nmdev-examples/tutorials/Principal-Component-Analysis.html) | `PCAExample` |
| [Numerical integration](https://nmltd.github.io/nmdev-examples/tutorials/Numerical-Integration.html) | `IntegrationExample` |
| [BFGS optimization](https://nmltd.github.io/nmdev-examples/tutorials/Unconstrained-Optimization.html) | `UnconstrainedOptimizationExample` |

Each tutorial has detailed explanations, complete source, expected results and executable numerical checks. No market data, external API keys or network calls are needed while running the calculations.

## Benchmarks

[Explore the benchmark website](https://nmltd.github.io/nmdev-examples/benchmarks.html) for interactive charts, results, methodology and reproduction instructions. [Source code and raw measurements](benchmark/README.md) remain in this repository.

## Original starter

The project began as a copy of [`samples/new-nmdev-project`](https://github.com/nmltd/numerical-methods-java/tree/main/samples/new-nmdev-project). Its `HelloWorld`, `Graph` and existing JUnit test are retained. The same public trial `nm.lic` from that starter is included here. Private licenses and package tokens are not included. The Maven project name and artifact ID are updated for this repository. See [the starter guide](docs/starter-project.md).

## Library and licensing

The application depends on the published combined `dev.nm:nmdev:1.2.6` binary. Library implementation source and binaries are not included. These examples do not grant a new license to the commercial library. Existing source notices are retained; see [LICENSE](LICENSE) and [NOTICE.md](NOTICE.md).

## Reporting problems

Include your Java version, NM Dev version, example class, command and full error message when reporting a problem. Never post package tokens or a private commercial license. Numerical checks run with `mvn verify`; generated build files belong in `target/` and should not be committed.
