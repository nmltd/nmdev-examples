/*
 * Copyright (c) NM LTD. https://nm.dev/
 * Adapted from NM Dev test cases for tutorial use. See NOTICE.md.
 * THIS SOFTWARE IS LICENSED, NOT SOLD. Use is subject to the NM license.
 * Provided AS IS, without warranty. See LICENSE.
 */
package dev.nm.examples;


import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import dev.nm.algebra.linear.vector.doubles.dense.DenseVector;
import dev.nm.stat.regression.linear.LMProblem;
import dev.nm.stat.regression.linear.ols.OLSRegression;
import java.util.Arrays;
import java.util.Locale;

/**
 * Fits y = slope*x + intercept, from OLSRegressionTest.test_0020.
 * Shows coefficient ordering, predictions and residual diagnostics.
 * Four observations illustrate an API; they cannot establish predictive validity.
 */
public final class LinearRegressionExample {
    private LinearRegressionExample() { }

    /** Fits the model and checks the reference coefficients and residual sum of squares.
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        // Rows are observations; columns are explanatory variables.
        // true appends an intercept: do not also supply a column of ones.
        LMProblem problem = new LMProblem(new DenseVector(new double[]{6, 5, 7, 10}),
                new DenseMatrix(new double[][]{{1}, {2}, {3}, {4}}), true);
        OLSRegression fit = new OLSRegression(problem);
        double[] coefficients = fit.beta().betaHat().toArray();
        // This API returns predictor coefficients first and the intercept last.
        Checks.close("slope", 1.4, coefficients[0], 1e-12);
        Checks.close("intercept", 3.5, coefficients[1], 1e-12);
        Checks.close("RSS", 4.2, fit.residuals().RSS(), 1e-12);
        Checks.close("R squared", 0.7, fit.residuals().R2(), 1e-12);
        System.out.printf(Locale.ROOT, "y = %.6f*x + %.6f%n", coefficients[0], coefficients[1]);
        System.out.println("Fitted values = " + Arrays.toString(fit.residuals().fitted().toArray()));
        System.out.println("Residuals = " + Arrays.toString(fit.residuals().residuals().toArray()));
        System.out.printf(Locale.ROOT, "RSS = %.6f; R squared = %.6f%n", fit.residuals().RSS(), fit.residuals().R2());
    }
}
