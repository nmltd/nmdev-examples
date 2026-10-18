# Least squares with QR and SVD

[Tutorial index](../README.md) · [Java source](../../src/main/java/dev/nm/examples/LeastSquaresExample.java)

## Run this example

Complete [Getting Started](../Getting-Started.md), then run from the repository root:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.LeastSquaresExample
```

## Problem and mathematics

```math
\min_\beta\|A\beta-b\|_2^2,\quad A=\begin{bmatrix}1&0\\1&1\\1&2\\1&3\end{bmatrix},\quad b=\begin{bmatrix}1\\2\\2\\4\end{bmatrix}.
```

## Walk through the API

Fit an intercept and slope to four observations. The columns of A are independent. The residual need not be zero; at the least-squares optimum it is orthogonal to every column of A.

For QR, `HouseholderQR(a, 1e-12)` supplies the thin Q and triangular R. Project with `qr.Q().t().multiply(b)`, then solve R beta = Q-transpose b using `BackwardSubstitution`. This avoids explicitly forming the normal equations, which can square the condition number.

For SVD, the example explicitly selects `SVD.Method.MR3` and computes the sum of v_k times (u_k-transpose b) divided by each retained singular value. The cutoff is 1e-12 times the largest singular value. This is a pedagogical relative cutoff, not a universal choice for noisy data. Both paths must recover the same coefficients on this full-rank problem.

**Published-binary limitation:** a separate rank-deficient check using identical columns failed during development against the combined 1.2.6-SNAPSHOT binary. This tutorial does not claim verified minimum-norm behavior for rank-deficient inputs. Validate reconstruction and residuals on your own problem before relying on that path.

## Expected result and validation

```text
QR fit: intercept=0.900000, slope=0.900000
SVD fit: rank=2, x1=0.900000, x2=0.900000
```

The fitted values are (0.9, 1.8, 2.7, 3.6). Their residual sum of squares is 0.7. Both paths check the known coefficients and A-transpose(A beta-b)=0 at tolerance 1e-10. A nonzero residual is expected; stationarity, not exact interpolation, is the relevant check.

## Exercise

Add a fifth observation, rerun both methods, and compare their coefficients and residual sum of squares.
