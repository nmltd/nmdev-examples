# Solve a linear system without an inverse

[Tutorial index](../README.md) · [Java source](../../src/main/java/dev/nm/examples/LinearSolveExample.java)

## Run this example

Complete [Getting Started](../Getting-Started.md), then run from the repository root:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.LinearSolveExample
```

## Problem and mathematics

```math
A x=b,\quad A=\begin{bmatrix}3&1\\1&2\end{bmatrix},\quad b=\begin{bmatrix}9\\8\end{bmatrix}.
```

## Walk through the API

A linear system arises in calibration, physical models and numerical algorithms. Solve directly instead of constructing an inverse: it avoids unnecessary work and an extra source of rounding error. Here the determinant is 5, so the solution is unique.

Create `DenseMatrix` and `DenseVector`, then call `new LinearSystemSolver(1e-12).solve(a).getParticularSolution(b)`. The tolerance controls numerical zero decisions; choose it with the data scale in mind. Indices start at 1. A singular system may have no solution or a family of solutions; this example deliberately uses a nonsingular system.

## Expected result and validation

```text
Solution: x1=2.000000, x2=3.000000
```

Substitution gives 3(2)+3=9 and 2+2(3)=8. The executable checks both coordinates and every entry of `a.multiply(x).minus(b)` at absolute tolerance 1e-10. For real data, also inspect a scaled backward error relative to the magnitudes of A, x and b.

## Exercise

Change b to (7, 9), solve again, and check the equations. Then make two rows identical and study how the solver reports inconsistency.
