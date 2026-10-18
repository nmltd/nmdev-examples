/*
 * Copyright (c) NM LTD. https://nm.dev/
 * Adapted from NM Dev test cases for tutorial use. See NOTICE.md.
 * THIS SOFTWARE IS LICENSED, NOT SOLD. Use is subject to the NM license.
 * Provided AS IS, without warranty. See LICENSE.
 */
package dev.nm.examples;


import dev.nm.analysis.curvefit.interpolation.univariate.LinearInterpolation;
import dev.nm.analysis.function.rn2r1.univariate.UnivariateRealFunction;
import dev.nm.analysis.function.tuple.PartialFunction;
import java.util.Locale;

/**
 * Fits a line through two observations, adapted from LinearInterpolationTest.test_0010.
 * Interpolation estimates between observations; extrapolation extends the endpoint
 * line outside them and requires a separate domain justification.
 */
public final class InterpolationExample {
    private InterpolationExample() { }

    /** Evaluates two interior points and one extrapolated point.
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        // PartialFunction pairs each x coordinate with the corresponding y value.
        UnivariateRealFunction line = new LinearInterpolation().fit(
                new PartialFunction(new double[]{0, 1}, new double[]{0, 4}));
        Checks.close("midpoint", 2, line.evaluate(0.5), 1e-12);
        Checks.close("interior point", 3, line.evaluate(0.75), 1e-12);
        Checks.close("extrapolated point", 8, line.evaluate(2), 1e-12);
        System.out.printf(Locale.ROOT, "f(0.5) = %.1f; f(0.75) = %.1f%n", line.evaluate(0.5), line.evaluate(0.75));
        System.out.printf(Locale.ROOT, "f(2) = %.1f (extrapolation)%n", line.evaluate(2));
    }
}
