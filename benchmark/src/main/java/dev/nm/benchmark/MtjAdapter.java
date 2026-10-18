/* Copyright (c) NM LTD. Adapted from the original benchmark adapters; see Source-Provenance.md. */
package dev.nm.benchmark;

import no.uib.cipr.matrix.*;
final class MtjAdapter implements Adapter {
 final DenseMatrix a,b,spd,rhs; final int n;
 MtjAdapter(Inputs in){a=new DenseMatrix(in.a);b=new DenseMatrix(in.b);spd=new DenseMatrix(in.spd);rhs=new DenseMatrix(in.rhs);n=a.numRows();}
 public Object run(String op){try{switch(op){
 case "transpose":return a.transpose(new DenseMatrix(n,n));case "addition":return a.copy().add(b);case "scale":return a.copy().scale(1.5);case "multiplication":return a.mult(b,new DenseMatrix(n,n));
 case "determinant":{DenseLU f=DenseLU.factorize(a);Matrix u=f.getU();double d=1;int[] p=f.getPivots();for(int i=0;i<n;i++){d*=u.get(i,i);if(p[i]!=i+1)d=-d;}return d;}
 case "inverse":return a.solve(Matrices.identity(n),new DenseMatrix(n,n));case "inverseSPD":return DenseCholesky.factorize(spd).solve(Matrices.identity(n));
 case "cholesky":{Matrix u=DenseCholesky.factorize(spd).getU();return u.transpose(new DenseMatrix(n,n));}
 case "lu":{DenseLU f=DenseLU.factorize(a);int[] swaps=f.getPivots(),p=new int[n];for(int i=0;i<n;i++)p[i]=i;for(int i=0;i<n;i++){int j=swaps[i]-1,t=p[i];p[i]=p[j];p[j]=t;}return new Object[]{f.getL(),f.getU(),p};}
 case "qr":{QR f=QR.factorize(a);return new Object[]{f.getQ(),f.getR()};}
 case "eigenSymmetric":{SymmDenseEVD f=SymmDenseEVD.factorize(spd);return new Object[]{f.getEigenvectors(),diagonal(f.getEigenvalues())};}
 case "svd":{SVD f=SVD.factorize(a);return new Object[]{f.getU(),diagonal(f.getS()),f.getVt().transpose(new DenseMatrix(n,n))};}
 case "solve":return a.solve(rhs,new DenseMatrix(n,1));default:throw new IllegalArgumentException(op);}}catch(NotConvergedException e){throw new IllegalStateException(e);}}
 static DenseMatrix diagonal(double[] values){DenseMatrix m=new DenseMatrix(values.length,values.length);for(int i=0;i<values.length;i++)m.set(i,i,values[i]);return m;}
 public double[][] array(Object x){Matrix m=(Matrix)x;double[][] out=new double[m.numRows()][m.numColumns()];for(int i=0;i<out.length;i++)for(int j=0;j<out[i].length;j++)out[i][j]=m.get(i,j);return out;}
}
