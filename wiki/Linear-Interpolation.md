# Linear interpolation in Java

[Tutorial index](Home) · [Java source](https://github.com/nmltd/nmdev-examples/blob/main/src/main/java/dev/nm/examples/InterpolationExample.java)

## Run this example

Complete [Getting Started](Getting-Started), then run from the repository root:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.InterpolationExample
```

## Problem and mathematics

Estimate values between observations `(0,0)` and `(1,4)`. Between any two adjacent observations, linear interpolation uses

$$f(x)=y_0+\frac{x-x_0}{x_1-x_0}(y_1-y_0).$$

For these observations the line is `f(x)=4x`. This tiny case makes the result independently verifiable before using a larger calibration table.

## Walk through the API

`PartialFunction` associates the x array with the y array. `new LinearInterpolation().fit(points)` returns a `UnivariateRealFunction`. Call `evaluate(x)` on that function for each query.

The example checks two interior values and an exterior value. It explicitly labels the exterior calculation as extrapolation so that a caller does not confuse it with an estimate supported by surrounding observations.

## Expected result and validation

| Query | Result | Interpretation |
|---|---:|---|
| `0.5` | `2.0` | Interpolation |
| `0.75` | `3.0` | Interpolation |
| `2.0` | `8.0` | Extrapolation |

The library extends the endpoint segment outside the observed interval, as demonstrated by the source test. The sample checks each value to `1e-12`.

## Assumptions and mistakes to avoid

Provide matching coordinate lengths and distinct, ordered x values. Interpolation reproduces observations; it does not fit a noise-reducing regression model. Piecewise-linear curves also have corners, so their derivatives are not generally continuous.

For a bounded physical calibration, reject exterior queries in your application even though the library can evaluate them. Extrapolation is a modelling decision.

## Try it

Use observations `(0,0)`, `(1,1)`, `(2,4)`. At `1.5` the interpolant is `2.5`, whereas the quadratic `x*x` gives `2.25`. Explain why both observations can lie on a quadratic while a linear interpolant differs between them.

## Source and API reference

Adapted from `LinearInterpolationTest.java`, `test_0010`. See [source provenance](https://github.com/nmltd/nmdev-examples/blob/main/docs/Source-Provenance.md) for the exact NM Dev commit and test path. The full runnable Java source linked above includes imports, construction steps and checks. Browse the [NM Dev Javadoc](https://nmltd.github.io/nmdev/) for the classes used here.
