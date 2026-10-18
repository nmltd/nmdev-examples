/*
 * Copyright (c) NM LTD. https://nm.dev/
 * Adapted from NM Dev test cases for tutorial use. See NOTICE.md.
 * THIS SOFTWARE IS LICENSED, NOT SOLD. Use is subject to the NM license.
 * Provided AS IS, without warranty. See LICENSE.
 */
package dev.nm.examples;


/** Runs the six tutorials and their numerical checks without a JUnit dependency. */
public final class RunAllExamples {
    private RunAllExamples() { }
    /** Runs every tutorial in learning order.
     * @param args unused command-line arguments
     * @throws Exception if any calculation or numerical check fails
     */
    public static void main(String[] args) throws Exception {
        CholeskyExample.main(args);
        RootFindingExample.main(args);
        InterpolationExample.main(args);
        NormalProbabilityExample.main(args);
        LinearRegressionExample.main(args);
        QuadraticProgrammingExample.main(args);
        System.out.println("All six tutorials passed their numerical checks.");
    }
}
