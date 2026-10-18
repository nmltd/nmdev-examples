/* Copyright (c) NM LTD. https://nm.dev/ — tutorial example; see LICENSE. */
package dev.nm.examples;
import dev.nm.algebra.linear.matrix.doubles.Matrix;
import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import dev.nm.algebra.linear.vector.doubles.Vector;
import dev.nm.algebra.linear.vector.doubles.dense.DenseVector;
import java.util.Locale;
import dev.nm.algebra.linear.matrix.doubles.linearsystem.LinearSystemSolver;
/** Solves Ax=b directly, then checks every equation without forming an inverse. */
public final class LinearSolveExample {
 private LinearSolveExample() { }
 /** @param args unused */
 public static void main(String[] args) {
  Matrix a=new DenseMatrix(new double[][]{{3,1},{1,2}});
  Vector b=new DenseVector(new double[]{9,8});
  Vector x=new LinearSystemSolver(1e-12).solve(a).getParticularSolution(b);
  Checks.close("x1",2,x.get(1),1e-10); Checks.close("x2",3,x.get(2),1e-10);
  Vector residual=a.multiply(x).minus(b);
  for(int i=1;i<=b.size();i++) Checks.close("equation residual",0,residual.get(i),1e-10);
  System.out.printf(Locale.ROOT,"Solution: x1=%.6f, x2=%.6f%n",x.get(1),x.get(2));
 }
}
