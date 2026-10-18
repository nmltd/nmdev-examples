/*
 * Copyright (c) NM LTD. https://nm.dev/
 * Adapted from NM Dev test cases for tutorial use. See NOTICE.md.
 * THIS SOFTWARE IS LICENSED, NOT SOLD. Use is subject to the NM license.
 * Provided AS IS, without warranty. See LICENSE.
 */
package dev.nm.examples;


import dev.nm.stat.distribution.univariate.NormalDistribution;
import java.util.Locale;

/**
 * Computes standard-normal probabilities and quantiles, adapted from
 * NormalDistributionTest.testNormal_0010 and testNormal_0020.
 * Density is not probability; the CDF gives P(Z &lt;= z).
 */
public final class NormalProbabilityExample {
    private NormalProbabilityExample() { }

    /** Checks reference probabilities and the CDF/quantile round trip.
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        NormalDistribution normal = new NormalDistribution(); // mean zero, variance one
        double belowTwo = normal.cdf(2);
        // Symmetry avoids subtracting two nearly equal numbers for this right tail.
        double aboveTwo = normal.cdf(-2);
        double percentile = normal.quantile(0.975);
        Checks.close("CDF at two", 0.977249868051821, belowTwo, 1e-12);
        Checks.close("right tail", 0.0227501319481792, aboveTwo, 1e-12);
        Checks.close("quantile round trip", 0.975, normal.cdf(percentile), 1e-9);
        System.out.printf(Locale.ROOT, "P(Z <= 2) = %.12f; P(Z > 2) = %.12f%n", belowTwo, aboveTwo);
        System.out.printf(Locale.ROOT, "97.5th percentile = %.9f%n", percentile);
    }
}
