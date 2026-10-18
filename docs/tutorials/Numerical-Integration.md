# Numerical integration with Simpson quadrature

[Tutorial index](../README.md) · [Java source](../../src/main/java/dev/nm/examples/IntegrationExample.java)

## Run this example

Complete [Getting Started](../Getting-Started.md), then run from the repository root:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.IntegrationExample
```

## Problem and mathematics

```math
\int_0^\pi\sin(x)\,dx=[-\cos(x)]_0^\pi=2.
```

## Walk through the API

Quadrature approximates an integral from function evaluations. Simpson quadrature is appropriate here because sine is smooth on a finite interval.

Define an `AbstractUnivariateRealFunction` whose `evaluate(double)` returns `Math.sin(x)`. Construct `new Simpson(1e-10, 20)` and call `integrate(sine, 0, Math.PI)`. The precision and maximum iteration count bound refinement; they do not guarantee accuracy for arbitrary functions.

Discontinuities, singularities, highly oscillatory functions and infinite intervals need different treatment or interval splitting. A small change between refinements is not an independent error certificate.

## Expected result and validation

```text
Integral of sin(x) on [0,pi]: 2.0000000000
```

The executable compares the numerical value with the analytic integral 2 at tolerance 1e-8, independently of the integrator's stopping precision. When no antiderivative is known, compare refinements and an independent quadrature method.

## Exercise

Integrate x squared over [0,1] and check 1/3. Then compare results for a function with a sharp peak as you vary the precision and iteration budget.
