# Principal component analysis with SVD

[Tutorial index](../README.md) · [Java source](../../src/main/java/dev/nm/examples/PCAExample.java)

## Run this example

Complete [Getting Started](../Getting-Started.md), then run from the repository root:

```bash
mvn exec:java -Dexec.mainClass=dev.nm.examples.PCAExample
```

## Problem and mathematics

```math
X=\begin{bmatrix}1&2\\2&4\\3&6\\4&8\end{bmatrix},\quad X_c=X-\mathbf{1}\mu^T,\quad X_c=U\Sigma V^T.
```

## Walk through the API

PCA finds orthogonal directions of variation. Rows are observations and columns are features. Here feature two is exactly twice feature one, so all centered variation lies in one direction.

`new PCAbySVD(observations, true, false)` centers the features and leaves their units unchanged. Call `mean()`, `loading(1)`, `proportionVar(1)` and `scores()` to inspect the result. `loadings()` contains the principal directions by columns, and scores times loadings-transpose reconstructs centered data when all components are kept.

Centering removes offsets; standardization also changes the relative influence of features. Enable scaling only when a correlation-style PCA fits your model. Constant features need special handling. Principal direction signs are arbitrary, and nearly equal component variances can make individual directions unstable.

## Expected result and validation

```text
PC1 explained variance: 100.000000%
```

The means are (2.5, 5). The first direction is proportional to (1, 2), with absolute normalized entries 1/sqrt(5) and 2/sqrt(5). The executable checks these sign-invariant magnitudes, variance fractions (1, 0), and reconstruction of every centered observation.

## Exercise

Perturb the second feature slightly. Observe how the second component gains variance, and compare centered-only PCA with standardized PCA.
