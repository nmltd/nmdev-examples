# Normal probabilities and quantiles in Java

[Tutorial index](Home) · [Java source](https://github.com/nmltd/nmdev-examples/blob/main/src/main/java/dev/nm/examples/NormalProbabilityExample.java)

## Run this example

Complete [Getting Started](Getting-Started), then run from the repository root:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.NormalProbabilityExample
```

## Problem and mathematics

For a standard-normal random variable Z, compute a cumulative probability, a right-tail probability and the 97.5th percentile:

$$\Phi(z)=P(Z\le z),\qquad q=\Phi^{-1}(0.975).$$

These operations arise in statistical thresholds and uncertainty calculations. Their validity depends on whether a normal model is appropriate for the data.

## Walk through the API

The no-argument `NormalDistribution` constructor creates a distribution with mean zero and variance one. `cdf(z)` gives cumulative probability, `density(z)` would give a density, and `quantile(p)` maps a probability to a value.

The example uses symmetry, `normal.cdf(-2)`, for the right-tail probability at 2. In extreme positive tails, subtracting `cdf(z)` from one can lose significant digits or round to zero.

## Expected result and validation

| Quantity | Approximate value |
|---|---:|
| `P(Z <= 2)` | `0.977249868052` |
| `P(Z > 2)` | `0.022750131948` |
| 97.5th percentile | `1.959963985` |

The CDF reference values are taken from the source test. The executable also checks `cdf(quantile(0.975))` against `0.975`, to `1e-9`, as a functional consistency check. A round trip alone is not an independent accuracy benchmark, which is why reference probabilities are checked too.

## Assumptions and mistakes to avoid

A density at one point is not the probability of that point. Quantile inputs are probabilities, not percentages: pass `0.975`, not `97.5`. For a general normal variable X, standardize with `(x-mu)/sigma`, where sigma is the standard deviation, not the variance.

## Try it

Compute `cdf(1.96)-cdf(-1.96)` and interpret the resulting central probability. Then examine a far-tail value and compare the symmetry calculation with subtraction from one.

## Source and API reference

Adapted from `NormalDistributionTest.java`, `testNormal_0010 and testNormal_0020`. See [source provenance](https://github.com/nmltd/nmdev-examples/blob/main/docs/Source-Provenance.md) for the exact NM Dev commit and test path. The full runnable Java source linked above includes imports, construction steps and checks. Browse the [NM Dev Javadoc](https://nmltd.github.io/nmdev/) for the classes used here.
