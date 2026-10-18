# Ordinary least squares regression in Java

[Tutorial index](../README.md) · [Java source](../../src/main/java/dev/nm/examples/LinearRegressionExample.java)

## Run this example

Complete [Getting Started](../Getting-Started.md), then run from the repository root:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.LinearRegressionExample
```

## Problem and mathematics

Fit four observations: x = `[1,2,3,4]`, y = `[6,5,7,10]`. Ordinary least squares chooses slope and intercept to minimize

```math
\sum_i\left(y_i-\beta_1x_i-\beta_0\right)^2.
```

This is a deliberately small teaching dataset from the test suite. It illustrates the API and diagnostics; it is not evidence of a useful forecasting relationship.

## Walk through the API

`LMProblem` takes the response vector, a design matrix and an intercept flag. Each row is an observation; each column is a predictor. Setting the flag to `true` asks the library to append an intercept. Do not append a constant column as well.

Construct `OLSRegression(problem)`. Retrieve coefficients through `beta().betaHat()`, predictions through `residuals().fitted()` and errors through `residuals().residuals()`. For this API, predictor coefficients come first and the intercept comes last.

## Expected result and validation

```math
\hat y=1.4x+3.5.
```

| Quantity | Value |
|---|---|
| Coefficients | `[1.4, 3.5]` — slope, then intercept |
| Fitted values | `[4.9, 6.3, 7.7, 9.1]` |
| Residuals, y minus fitted | `[1.1, -1.3, -0.7, 0.9]` |
| Residual sum of squares | `4.2` |
| R squared | `0.7` |

The executable checks the coefficients, RSS and R squared to `1e-12`. The slope can also be verified by computing centered cross-products divided by centered squared x values; the intercept follows from the sample means.

## Assumptions and mistakes to avoid

A good in-sample R squared does not establish predictive accuracy or causality. Collinear columns can make the design rank deficient. Statistical inference needs assumptions about the errors beyond the least-squares minimization itself.

With only four observations and two fitted coefficients, inference is especially fragile. Use representative held-out data for prediction assessment.

## Try it

Set the intercept flag to `false` and compare the fitted line and RSS. Explain why forcing the line through zero changes the question. Do not directly compare centered and uncentered R squared as if their definitions were identical.

## Source and API reference

Adapted from `OLSRegressionTest.java`, `test_0020`. See [source provenance](../Source-Provenance.md) for the exact NM Dev commit and test path. The full runnable Java source linked above includes imports, construction steps and checks. Browse the [NM Dev Javadoc](https://nmltd.github.io/nmdev/) for the classes used here.
