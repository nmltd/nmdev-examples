/* Copyright (c) NM LTD. Adapted from the original benchmark adapters; see Source-Provenance.md. */
package dev.nm.benchmark;

import org.jblas.*;
final class JblasAdapter implements Adapter {
 final DoubleMatrix a,b,spd,rhs;
 JblasAdapter(Inputs in){a=new DoubleMatrix(in.a);b=new DoubleMatrix(in.b);spd=new DoubleMatrix(in.spd);rhs=new DoubleMatrix(in.rhs);}
 public Object run(String op){switch(op){
 case "transpose":return a.transpose();case "addition":return a.add(b);case "scale":return a.mul(1.5);case "multiplication":return a.mmul(b);
 case "determinant":{Decompose.LUDecomposition<DoubleMatrix> f=Decompose.lu(a);double d=f.u.diag().prod();int[] pivot=new int[a.rows];for(int i=0;i<a.rows;i++)for(int j=0;j<a.rows;j++)if(f.p.get(i,j)>0.5)pivot[i]=j;int inversions=0;for(int i=0;i<pivot.length;i++)for(int j=i+1;j<pivot.length;j++)if(pivot[i]>pivot[j])inversions++;return inversions%2==0?d:-d;}
 case "inverse":return Solve.solve(a,DoubleMatrix.eye(a.rows));case "inverseSPD":return Solve.solvePositive(spd,DoubleMatrix.eye(a.rows));
 // jblas returns the upper Cholesky factor; materialize its transpose for common lower output.
 case "cholesky":return Decompose.cholesky(spd).transpose();
 case "lu":{Decompose.LUDecomposition<DoubleMatrix> f=Decompose.lu(a);return new Object[]{f.l,f.u,f.p.transpose()};}
 case "qr":{Decompose.QRDecomposition<DoubleMatrix> f=Decompose.qr(a);return new Object[]{f.q,f.r};}
 case "eigenSymmetric":{DoubleMatrix[] f=Eigen.symmetricEigenvectors(spd);return new Object[]{f[0],f[1]};}
 case "svd":{DoubleMatrix[] f=Singular.fullSVD(a);return new Object[]{f[0],DoubleMatrix.diag(f[1]),f[2]};}
 case "solve":return Solve.solve(a,rhs);default:throw new IllegalArgumentException(op);}}
 public double[][] array(Object x){return ((DoubleMatrix)x).toArray2();}
}
