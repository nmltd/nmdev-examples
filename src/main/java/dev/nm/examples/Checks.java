/*
 * Copyright (c) NM LTD. https://nm.dev/
 * Adapted from NM Dev test cases for tutorial use. See NOTICE.md.
 * THIS SOFTWARE IS LICENSED, NOT SOLD. Use is subject to the NM license.
 * Provided AS IS, without warranty. See LICENSE.
 */
package dev.nm.examples;


/** Small numerical checks that remain enabled when Java assertions are disabled. */
public final class Checks {
    private Checks() { }

    /**
     * Checks an absolute error tolerance and rejects NaN or infinity.
     * @param label quantity being checked
     * @param expected reference value
     * @param actual computed value
     * @param tolerance maximum permitted absolute error
     */
    public static void close(String label, double expected, double actual, double tolerance) {
        if (!Double.isFinite(actual) || Math.abs(expected - actual) > tolerance) {
            throw new IllegalStateException(label + ": expected " + expected + ", got " + actual);
        }
    }
}
