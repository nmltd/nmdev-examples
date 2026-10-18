# Unconstrained optimization with BFGS

[Tutorial index](../README.md) · [Java source](../../src/main/java/dev/nm/examples/UnconstrainedOptimizationExample.java)

## Run this example

Complete [Getting Started](../Getting-Started.md), then run from the repository root:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.UnconstrainedOptimizationExample
```

## Problem and mathematics

```math
f(x)=\tfrac12x^THx+p^Tx+6=(x_1-2)^2+2(x_2+1)^2.
```

## Walk through the API

BFGS iteratively approximates curvature to minimize a smooth objective. This deliberately simple convex objective has a known global minimum, letting us verify solver usage independently.

Create `QuadraticFunction` with H=diag(2,4), p=(-4,4) and constant 6. Note the one-half convention in the quadratic term. Wrap it in `C2OptimProblemImpl`, then call `new BFGSMinimizer(false, 1e-9, 500).solve(problem).search(initial)` from (0,0).

This problem overload estimates derivatives numerically. Production callers can supply gradient and Hessian functions where available. Scale variables and objectives sensibly, use finite iteration limits, and check convergence. BFGS on a general nonconvex function finds a local candidate, not a guaranteed global minimum. Use a constrained solver for bounds or inequalities.

## Expected result and validation

```text
BFGS optimum: x1=2.000000, x2=-1.000000, objective=0.000e+00
```

The Hessian is positive definite, so the stationary point (2,-1) is the unique global minimum. The executable checks the coordinates at tolerance 1e-5, the objective at 1e-9, and the analytic gradient Hx+p at 1e-5. This avoids relying only on the optimizer's stopping flag.

## Exercise

Change the initial guess to (10,-10), then alter H to give very different curvature along each coordinate. Compare convergence before and after rescaling variables.
