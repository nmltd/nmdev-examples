/* Copyright (c) NM LTD. https://nm.dev/ — tutorial example; see LICENSE. */
package dev.nm.examples;
import dev.nm.algebra.linear.matrix.doubles.Matrix;
import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import dev.nm.algebra.linear.vector.doubles.Vector;
import dev.nm.algebra.linear.vector.doubles.dense.DenseVector;
import java.util.Locale;
import dev.nm.analysis.function.rn2r1.univariate.AbstractUnivariateRealFunction;
import dev.nm.analysis.function.rn2r1.univariate.UnivariateRealFunction;
import dev.nm.analysis.integration.univariate.riemann.newtoncotes.Simpson;
/** Integrates a smooth function and checks an exact antiderivative. */
public final class IntegrationExample {
 private IntegrationExample() { }
 /** @param args unused */
 public static void main(String[] args) {
  UnivariateRealFunction sine=new AbstractUnivariateRealFunction() {
   @Override public double evaluate(double x) { return Math.sin(x); }
  };
  double integral=new Simpson(1e-10,20).integrate(sine,0,Math.PI);
  Checks.close("integral of sine",2,integral,1e-8);
  System.out.printf(Locale.ROOT,"Integral of sin(x) on [0,pi]: %.10f%n",integral);
 }
}
