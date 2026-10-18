/* Copyright (c) NM LTD. https://nm.dev/ — tutorial example; see LICENSE. */
package dev.nm.examples;
import dev.nm.algebra.linear.matrix.doubles.Matrix;
import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import dev.nm.algebra.linear.vector.doubles.Vector;
import dev.nm.algebra.linear.vector.doubles.dense.DenseVector;
import java.util.Locale;
import dev.nm.stat.factor.pca.PCAbySVD;
/** Centers two collinear features and checks sign-invariant principal directions. */
public final class PCAExample {
 private PCAExample() { }
 /** @param args unused */
 public static void main(String[] args) {
  Matrix observations=new DenseMatrix(new double[][]{{1,2},{2,4},{3,6},{4,8}});
  // Rows are observations; columns are features. Center, but do not standardize units.
  PCAbySVD pca=new PCAbySVD(observations,true,false);
  Checks.close("first mean",2.5,pca.mean().get(1),1e-12);
  Checks.close("second mean",5,pca.mean().get(2),1e-12);
  Checks.close("PC1 explained fraction",1,pca.proportionVar(1),1e-10);
  Checks.close("PC2 explained fraction",0,pca.proportionVar(2),1e-10);
  Vector loading=pca.loading(1);
  // Eigenvector signs are arbitrary; check the direction using absolute components.
  Checks.close("loading magnitude 1",1/Math.sqrt(5),Math.abs(loading.get(1)),1e-10);
  Checks.close("loading magnitude 2",2/Math.sqrt(5),Math.abs(loading.get(2)),1e-10);
  Matrix reconstructed=pca.scores().multiply(pca.loadings().t());
  for(int i=1;i<=4;i++)for(int j=1;j<=2;j++)
   Checks.close("centered reconstruction",observations.get(i,j)-pca.mean().get(j),reconstructed.get(i,j),1e-10);
  System.out.printf(Locale.ROOT,"PC1 explained variance: %.6f%%%n",100*pca.proportionVar(1));
 }
}
