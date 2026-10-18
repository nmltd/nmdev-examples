/* Copyright (c) NM LTD. https://nm.dev/ — tutorial example; see LICENSE. */
package dev.nm.examples;
import dev.nm.algebra.linear.matrix.doubles.Matrix;
import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import dev.nm.algebra.linear.vector.doubles.Vector;
import dev.nm.algebra.linear.vector.doubles.dense.DenseVector;
import java.util.Locale;
import dev.nm.analysis.function.rn2r1.QuadraticFunction;
import dev.nm.solver.problem.C2OptimProblemImpl;
import dev.nm.solver.multivariate.unconstrained.c2.quasinewton.BFGSMinimizer;
/** Minimizes a strictly convex quadratic using BFGS and checks the exact optimum. */
public final class UnconstrainedOptimizationExample {
 private UnconstrainedOptimizationExample() { }
 /** @param args unused
  * @throws Exception if the optimizer cannot solve the problem */
 public static void main(String[] args) throws Exception {
  Matrix h=new DenseMatrix(new double[][]{{2,0},{0,4}});
  Vector p=new DenseVector(new double[]{-4,4});
  QuadraticFunction f=new QuadraticFunction(h,p,6);
  // This overload estimates derivatives numerically. Production callers can supply them.
  Vector x=new BFGSMinimizer(false,1e-9,500).solve(new C2OptimProblemImpl(f))
    .search(new DenseVector(new double[]{0,0}));
  Checks.close("optimal x1",2,x.get(1),1e-5); Checks.close("optimal x2",-1,x.get(2),1e-5);
  Checks.close("minimum objective",0,f.evaluate(x),1e-9);
  Vector gradient=h.multiply(x).add(p);
  for(int i=1;i<=2;i++) Checks.close("analytic gradient",0,gradient.get(i),1e-5);
  System.out.printf(Locale.ROOT,"BFGS optimum: x1=%.6f, x2=%.6f, objective=%.3e%n",x.get(1),x.get(2),f.evaluate(x));
 }
}
