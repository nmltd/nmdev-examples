/*
 * Copyright (c) NM LTD. https://nm.dev/
 * Adapted from NM Dev test cases for tutorial use. See NOTICE.md.
 * THIS SOFTWARE IS LICENSED, NOT SOLD. Use is subject to the NM license.
 * Provided AS IS, without warranty. See LICENSE.
 */
package dev.nm.examples;


import dev.nm.algebra.linear.matrix.doubles.Matrix;
import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import dev.nm.algebra.linear.vector.doubles.Vector;
import dev.nm.algebra.linear.vector.doubles.dense.DenseVector;
import dev.nm.analysis.function.rn2r1.QuadraticFunction;
import dev.nm.solver.multivariate.constrained.constraint.linear.LinearGreaterThanConstraints;
import dev.nm.solver.multivariate.constrained.convex.sdp.socp.qp.QPSolution;
import dev.nm.solver.multivariate.constrained.convex.sdp.socp.qp.problem.QPProblem;
import dev.nm.solver.multivariate.constrained.convex.sdp.socp.qp.solver.activeset.QPDualActiveSetMinimizer;
import java.util.Arrays;
import java.util.Locale;

/**
 * Minimizes a strictly convex quadratic subject to five linear inequalities.
 * Adapted from QPDualActiveSetMinimizerTest.test_0030 (Nocedal/Wright example 16.4).
 * QuadraticFunction uses one half x^T H x + p^T x; inequalities use A x &gt;= b.
 */
public final class QuadraticProgrammingExample {
    private QuadraticProgrammingExample() { }

    /** Checks the optimizer, objective, feasibility and an independent optimality certificate.
     * @param args unused command-line arguments
     * @throws Exception if the problem is infeasible or the solver fails
     */
    public static void main(String[] args) throws Exception {
        Matrix h = new DenseMatrix(new double[][]{{2, 0}, {0, 2}});
        Vector p = new DenseVector(new double[]{-2, -5});
        QuadraticFunction objective = new QuadraticFunction(h, p);
        Matrix a = new DenseMatrix(new double[][]{{1, -2}, {-1, -2}, {-1, 2}, {1, 0}, {0, 1}});
        Vector b = new DenseVector(new double[]{-2, -6, -2, 0, 0});
        QPProblem problem = new QPProblem(objective, null, new LinearGreaterThanConstraints(a, b));
        // A finite iteration limit keeps an example from running indefinitely.
        QPSolution solution = new QPDualActiveSetMinimizer(1e-10, 1000).solve(problem).search();
        Vector x = solution.minimizer();
        Checks.close("x1", 1.4, x.get(1), 1e-8);
        Checks.close("x2", 1.7, x.get(2), 1e-8);
        Checks.close("objective", -6.45, objective.evaluate(x), 1e-8);
        Vector slack = a.multiply(x).minus(b);
        for (int i = 1; i <= slack.size(); i++) {
            if (!Double.isFinite(slack.get(i)) || slack.get(i) < -1e-8) {
                throw new IllegalStateException("Constraint " + i + " is violated");
            }
        }
        // Only row 1 is active. For A*x >= b, stationarity is H*x+p = A^T*lambda.
        // lambda=(0.8,0,0,0,0) is nonnegative and satisfies complementarity.
        Vector gradient = h.multiply(x).add(p);
        Checks.close("stationarity x1", 0.8, gradient.get(1), 1e-8);
        Checks.close("stationarity x2", -1.6, gradient.get(2), 1e-8);
        Checks.close("active constraint", 0, slack.get(1), 1e-8);
        System.out.println("Optimizer = " + Arrays.toString(x.toArray()));
        System.out.printf(Locale.ROOT, "Objective = %.8f%n", objective.evaluate(x));
        System.out.println("Constraint slacks = " + Arrays.toString(slack.toArray()));
    }
}
