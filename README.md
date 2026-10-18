# NM Dev Examples

Learn numerical programming in Java with runnable NM Dev examples: linear algebra, root finding, interpolation, probability, regression and constrained optimization.

**[Get started](docs/Getting-Started.md) · [Tutorials](docs/README.md) · [API reference](https://nmltd.github.io/nmdev/) · [NM Dev](https://nm.dev/products/nmdev)**

## Run your first example

Requires Java 8 or later, Maven, GitHub Packages credentials and a valid NM Dev license. Obtain a valid NM Dev trial or commercial license and save it as `nm.lic` in the repository root; its validity and module entitlements are enforced by NM Dev. See [setup instructions](docs/Getting-Started.md) before running:

```bash
mvn -U clean verify
mvn exec:java -Dexec.mainClass=dev.nm.examples.RunAllExamples
```

## Tutorials

| Tutorial | Runnable class in `dev.nm.examples` |
|---|---|
| [Cholesky factorization in Java](docs/tutorials/Cholesky-Factorization.md) | `CholeskyExample` |
| [Root finding by bisection in Java](docs/tutorials/Root-Finding.md) | `RootFindingExample` |
| [Linear interpolation in Java](docs/tutorials/Linear-Interpolation.md) | `InterpolationExample` |
| [Normal probabilities and quantiles in Java](docs/tutorials/Normal-Probabilities.md) | `NormalProbabilityExample` |
| [Ordinary least squares regression in Java](docs/tutorials/Linear-Regression.md) | `LinearRegressionExample` |
| [Constrained quadratic programming in Java](docs/tutorials/Quadratic-Programming.md) | `QuadraticProgrammingExample` |

Each tutorial has detailed explanations, complete source, expected results and executable numerical checks. No market data, external API keys or network calls are needed while running the calculations.

## Benchmarks

**[Interactive dashboard](https://nmltd.github.io/nmdev-examples/) · [Benchmark guide](https://github.com/nmltd/nmdev-examples/wiki/Benchmark-Results)**

The [benchmark suite](benchmark/README.md) compares 13 dense linear-algebra operations across eleven libraries/distributions, including NM Dev, EJML, Apache Commons Math, JAMA, Colt, Parallel Colt, jblas, F2J LAPACK, MTJ, ojAlgo and UJMP, with JMH timings, correctness checks and published raw results.

## Original starter

The project began as a copy of [`samples/new-nmdev-project`](https://github.com/nmltd/numerical-methods-java/tree/main/samples/new-nmdev-project). Its `HelloWorld`, `Graph` and existing JUnit test are retained. License credentials are not redistributed here. The Maven project name and artifact ID are updated for this repository. See [the starter guide](docs/starter-project.md).

## Library and licensing

The application depends on the published combined `dev.nm:nmdev-dist:1.2.6-SNAPSHOT` binary. Library implementation source and binaries are not included. These examples do not grant a new license to the commercial library. Existing source notices are retained; see [LICENSE](LICENSE) and [NOTICE.md](NOTICE.md).

## Reporting problems

Include your Java version, NM Dev version, example class, command and full error message when reporting a problem. Never post package tokens or a private commercial license. Numerical checks run with `mvn verify`; generated build files belong in `target/` and should not be committed.
