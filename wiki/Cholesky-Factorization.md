# Cholesky factorization in Java

[Tutorial index](Home) · [Java source](https://github.com/nmltd/nmdev-examples/blob/main/src/main/java/dev/nm/examples/CholeskyExample.java)

## Run this example

Complete [Getting Started](Getting-Started), then run from the repository root:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.CholeskyExample
```

## Problem and mathematics

Factor the symmetric positive-definite matrix

$$A=\begin{bmatrix}2&1&1\\1&2&1\\1&1&2\end{bmatrix}=LL^T.$$

Cholesky factorization is useful when working with covariance matrices or solving positive-definite linear systems. It produces a lower-triangular factor. It does not compute an inverse, and an inverse is usually unnecessary when solving a system.

## Walk through the API

1. Construct `DenseMatrix` from a row-major `double[][]`.
2. Pass the matrix to `CholeskyBanachiewicz`.
3. Retrieve the lower-triangular factor with `L()`.
4. Use `l.multiply(l.t())` to reconstruct the original matrix.
5. Loop from **1**, not 0: NM Dev matrix access is one-based.

The Java source prints the factor and checks the largest absolute reconstruction error. Matrix multiplication and transpose are essential; multiplying corresponding entries would not reconstruct the matrix.

## Expected result and validation

The lower-triangular factor is approximately

$$L=\begin{bmatrix}1.414214&0&0\\0.707107&1.224745&0\\0.707107&0.408248&1.154701\end{bmatrix}.$$

The reconstruction error should be below `1e-12`. Exact formatting and last-bit rounding may differ across runtimes. This check tests the defining factorization identity rather than a particular printed representation.

## Assumptions and mistakes to avoid

The matrix must be symmetric and positive definite. Positive semidefinite matrices can be singular, so a sample covariance matrix is not automatically suitable. Do not silently add a diagonal adjustment just to make the calculation succeed: that changes the model. Poor conditioning also requires a scale-aware tolerance in production.

## Try it

Construct a lower-triangular matrix with positive diagonal, form `L*L^T`, then factor the product. Check that the recovered factor matches your original. Next use a singular matrix and inspect the library's failure rather than swallowing it.

## Source and API reference

Adapted from `CholeskyBanachiewiczTest.java`, `test_0010`. See [source provenance](https://github.com/nmltd/nmdev-examples/blob/main/docs/Source-Provenance.md) for the exact NM Dev commit and test path. The full runnable Java source linked above includes imports, construction steps and checks. Browse the [NM Dev Javadoc](https://nmltd.github.io/nmdev/) for the classes used here.
