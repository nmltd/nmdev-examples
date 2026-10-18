# Root finding by bisection in Java

[Tutorial index](Home) · [Java source](https://github.com/nmltd/nmdev-examples/blob/main/src/main/java/dev/nm/examples/RootFindingExample.java)

## Run this example

Complete [Getting Started](Getting-Started), then run from the repository root:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.RootFindingExample
```

## Problem and mathematics

Find a root in `[0.4, 1.0]` of

```math
f(x)=x^4-\sin(x)\cos(x).
```

Bisection repeatedly halves a bracket. For a continuous function, opposite signs at the endpoints establish that at least one root lies inside. The chosen interval matters: this example is not a search for every root of the equation.

## Walk through the API

Create an anonymous `AbstractUnivariateRealFunction` and implement `evaluate(double)`. Construct `BisectionRoot` with tolerance `1e-10` and a maximum of 10,000 iterations, matching the source test. Call `solve(f, lower, upper)`.

The example checks the endpoint signs before calling the solver. It leaves `NoRootFoundException` visible to the caller. A production application should report convergence failure with the input interval and model context.

## Expected result and validation

```math
x\approx0.8396570801734923.
```

The executable checks the distance to this reference and the function residual `abs(f(root))`, both to `1e-10`. These checks answer different questions: a small residual does not always mean a small error in x, especially near a flat root.

## Assumptions and mistakes to avoid

Opposite signs across a discontinuity do not establish a root. A bracket whose endpoints have the same sign may contain roots but is unsuitable for this bisection setup. In particular, `[0.4, 0.8]` does not bracket the root used here; the upstream tests cover that invalid interval.

Use function units and variable scale when choosing tolerances. Avoid requesting precision beyond what double arithmetic and the model can support.

## Try it

Replace the function with `x*x - 2` on `[1, 2]` and compare against `Math.sqrt(2)`. Then deliberately choose `[2, 3]` and observe the bracket validation.

## Source and API reference

Adapted from `BisectionRootTest.java`, `test_solve_0010`. See [source provenance](https://github.com/nmltd/nmdev-examples/blob/main/docs/Source-Provenance.md) for the exact NM Dev commit and test path. The full runnable Java source linked above includes imports, construction steps and checks. Browse the [NM Dev Javadoc](https://nmltd.github.io/nmdev/) for the classes used here.
