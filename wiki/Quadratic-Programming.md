# Constrained quadratic programming in Java

[Tutorial index](Home) · [Java source](https://github.com/nmltd/nmdev-examples/blob/main/src/main/java/dev/nm/examples/QuadraticProgrammingExample.java)

## Run this example

Complete [Getting Started](Getting-Started), then run from the repository root:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.QuadraticProgrammingExample
```

## Problem and mathematics

Minimize

```math
f(x)=\tfrac12x^THx+p^Tx=x_1^2+x_2^2-2x_1-5x_2
```

subject to

```math
x_1-2x_2\ge-2,\quad -x_1-2x_2\ge-6,\quad -x_1+2x_2\ge-2,\quad x_1\ge0,\quad x_2\ge0.
```

Here H = diag(2,2), so the objective is strictly convex and a feasible optimum is unique. This problem is adapted from the NM Dev test identified as example 16.4 in Nocedal and Wright's *Numerical Optimization*.

## Walk through the API

1. Construct H and the linear coefficient vector p.
2. Create `QuadraticFunction(H,p)`. The factor **one half** is part of the API's convention.
3. Put each inequality's coefficients in one row of A and its right-hand side in b.
4. Use `LinearGreaterThanConstraints(A,b)`: the convention is **A*x >= b**.
5. Create `QPProblem(objective, null, constraints)`. The null argument means no equality constraints.
6. Call `solve(problem).search()` on `QPDualActiveSetMinimizer`, then `minimizer()`.

The example uses a tolerance of `1e-10` and an iteration cap of 1,000, rather than the effectively unbounded cap in the test.

## Expected result and independent validation

```math
x^{\ast}=(1.4,1.7),\qquad f(x^{\ast})=-6.45.
```

The constraint slacks A*x-b are `[0,1.2,4,1.4,1.7]`; only the first constraint is active. The executable checks the reference point, objective, every constraint and stationarity.

For the convention A*x >= b, the Lagrangian is f(x) minus lambda-transpose times (A*x-b). At the solution:

```math
Hx^{\ast}+p=(0.8,-1.6)^T=A^T\lambda,\qquad\lambda=(0.8,0,0,0,0)^T.
```

Nonnegative multipliers, feasibility, stationarity and complementary slackness certify optimality for this convex problem. This gives stronger evidence than simply comparing a printed vector to another solver's output.

## Assumptions and mistakes to avoid

Do not reverse inequality signs without negating both sides. Do not drop the one-half convention when translating a mathematical objective. This example has a positive-definite Hessian; it does not demonstrate that the solver can handle every indefinite, singular or badly scaled problem.

Check feasibility even when the solver returns a point, and make solver failures visible. Tolerances should reflect data scale and application needs.

## Try it

Remove the first inequality and solve again. The unconstrained minimizer is `(1,2.5)` and satisfies the remaining constraints. Explain how the original first constraint moved the optimum.

## Source and API reference

Adapted from `QPDualActiveSetMinimizerTest.java`, `test_0030`. See [source provenance](https://github.com/nmltd/nmdev-examples/blob/main/docs/Source-Provenance.md) for the exact NM Dev commit and test path. The full runnable Java source linked above includes imports, construction steps and checks. Browse the [NM Dev Javadoc](https://nmltd.github.io/nmdev/) for the classes used here.
