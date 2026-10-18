/*
 * Copyright (c) NM LTD. https://nm.dev/
 * Adapted from NM Dev test cases for tutorial use. See NOTICE.md.
 * THIS SOFTWARE IS LICENSED, NOT SOLD. Use is subject to the NM license.
 * Provided AS IS, without warranty. See LICENSE.
 */
package dev.nm.examples;


import dev.nm.algebra.linear.matrix.doubles.Matrix;
import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import dev.nm.algebra.linear.matrix.doubles.factorization.triangle.cholesky.CholeskyBanachiewicz;
import java.util.Locale;

/**
 * Factors a symmetric positive-definite matrix and verifies A = L L^T.
 * The matrix comes from CholeskyBanachiewiczTest.test_0010.
 * This is a building block for covariance models and positive-definite systems.
 * A singular or indefinite matrix needs a different treatment.
 */
public final class CholeskyExample {
    private CholeskyExample() { }

    /** Runs the factorization and checks its reconstruction, using one-based matrix indices.
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        Matrix a = new DenseMatrix(new double[][]{{2, 1, 1}, {1, 2, 1}, {1, 1, 2}});
        Matrix l = new CholeskyBanachiewicz(a).L();
        // Transpose, then multiply: elementwise multiplication would be incorrect.
        Matrix reconstructed = l.multiply(l.t());
        double maxError = 0;
        for (int i = 1; i <= a.nRows(); i++) {
            for (int j = 1; j <= a.nCols(); j++) {
                maxError = Math.max(maxError, Math.abs(a.get(i, j) - reconstructed.get(i, j)));
            }
        }
        Checks.close("reconstruction error", 0, maxError, 1e-12);
        System.out.println("L = " + l);
        System.out.printf(Locale.ROOT, "Maximum reconstruction error = %.3e%n", maxError);
    }
}
