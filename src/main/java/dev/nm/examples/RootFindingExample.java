/*
 * Copyright (c) NM LTD. https://nm.dev/
 * Adapted from NM Dev test cases for tutorial use. See NOTICE.md.
 * THIS SOFTWARE IS LICENSED, NOT SOLD. Use is subject to the NM license.
 * Provided AS IS, without warranty. See LICENSE.
 */
package dev.nm.examples;


import dev.nm.analysis.function.rn2r1.univariate.AbstractUnivariateRealFunction;
import dev.nm.analysis.function.rn2r1.univariate.UnivariateRealFunction;
import dev.nm.analysis.root.univariate.BisectionRoot;
import dev.nm.analysis.root.univariate.NoRootFoundException;
import java.util.Locale;

/**
 * Finds a bracketed root of x^4 - sin(x) cos(x), from
 * BisectionRootTest.test_solve_0010. Continuity and opposite endpoint signs
 * make bisection appropriate; this does not find every root of a function.
 */
public final class RootFindingExample {
    private RootFindingExample() { }

    /** Solves on [0.4, 1.0] and checks both the root and its residual.
     * @param args unused command-line arguments
     * @throws NoRootFoundException if the solver cannot converge
     */
    public static void main(String[] args) throws NoRootFoundException {
        UnivariateRealFunction f = new AbstractUnivariateRealFunction() {
            @Override public double evaluate(double x) {
                return Math.pow(x, 4) - Math.sin(x) * Math.cos(x);
            }
        };
        double lower = 0.4, upper = 1.0;
        // An invalid bracket is a modelling problem, not a reason to loosen tolerances.
        if (f.evaluate(lower) * f.evaluate(upper) >= 0) {
            throw new IllegalArgumentException("Choose endpoints with opposite signs");
        }
        double root = new BisectionRoot(1e-10, 10000).solve(f, lower, upper);
        Checks.close("root", 0.8396570801734923, root, 1e-10);
        Checks.close("function residual", 0, f.evaluate(root), 1e-10);
        System.out.printf(Locale.ROOT, "Root = %.12f; f(root) = %.3e%n", root, f.evaluate(root));
    }
}
