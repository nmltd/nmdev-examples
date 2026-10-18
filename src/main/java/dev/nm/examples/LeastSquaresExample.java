/* Copyright (c) NM LTD. https://nm.dev/ — tutorial example; see LICENSE. */
package dev.nm.examples;
import dev.nm.algebra.linear.matrix.doubles.Matrix;
import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import dev.nm.algebra.linear.vector.doubles.Vector;
import dev.nm.algebra.linear.vector.doubles.dense.DenseVector;
import java.util.Locale;
import dev.nm.algebra.linear.matrix.doubles.factorization.qr.HouseholderQR;
import dev.nm.algebra.linear.matrix.doubles.factorization.svd.SVD;
import dev.nm.algebra.linear.matrix.doubles.linearsystem.BackwardSubstitution;
/** Uses QR for a full-rank fit and truncated SVD for a second full-rank fit. */
public final class LeastSquaresExample {
 private LeastSquaresExample() { }
 /** @param args unused */
 public static void main(String[] args) {
  Matrix a=new DenseMatrix(new double[][]{{1,0},{1,1},{1,2},{1,3}});
  Vector b=new DenseVector(new double[]{1,2,2,4});
  HouseholderQR qr=new HouseholderQR(a,1e-12);
  // Thin Q has two columns. Solve the small triangular system, not A^T A.
  Vector x=new BackwardSubstitution().solve(qr.R(),qr.Q().t().multiply(b));
  Checks.close("intercept",0.9,x.get(1),1e-10); Checks.close("slope",0.9,x.get(2),1e-10);
  Vector stationarity=a.t().multiply(a.multiply(x).minus(b));
  for(int i=1;i<=2;i++) Checks.close("least squares stationarity",0,stationarity.get(i),1e-10);
  System.out.printf(Locale.ROOT,"QR fit: intercept=%.6f, slope=%.6f%n",x.get(1),x.get(2));
  Matrix design=new DenseMatrix(new double[][]{{1,0},{1,1},{1,2},{1,3}});
  Vector rhs=new DenseVector(new double[]{1,2,2,4});
  SVD svd=new SVD(design,true,1e-12,SVD.Method.MR3);
  double[] singular=svd.getSingularValues(); double cutoff=1e-12*singular[0];
  double[] coefficients=new double[2]; int rank=0;
  for(int k=1;k<=singular.length;k++) {
   if(singular[k-1]<=cutoff) continue;
   rank++; double projection=0;
   for(int i=1;i<=rhs.size();i++) projection+=svd.U().get(i,k)*rhs.get(i);
   for(int j=1;j<=2;j++) coefficients[j-1]+=svd.V().get(j,k)*projection/singular[k-1];
  }
  Checks.close("numerical rank",2,rank,0);
  Vector svdFit=new DenseVector(coefficients);
  Checks.close("SVD intercept",0.9,svdFit.get(1),1e-10);
  Checks.close("SVD slope",0.9,svdFit.get(2),1e-10);
  Vector normalResidual=design.t().multiply(design.multiply(svdFit).minus(rhs));
  for(int i=1;i<=2;i++) Checks.close("SVD stationarity",0,normalResidual.get(i),1e-10);
  System.out.printf(Locale.ROOT,"SVD fit: rank=%d, x1=%.6f, x2=%.6f%n",rank,coefficients[0],coefficients[1]);
 }
}
