/* Copyright (c) NM LTD. Adapted from the original benchmark adapters; see Source-Provenance.md. */
package dev.nm.benchmark;

import cern.colt.matrix.tdouble.*;
import cern.colt.matrix.tdouble.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.tdouble.algo.DenseDoubleAlgebra;
import cern.colt.matrix.tdouble.algo.decomposition.*;
import cern.jet.math.tdouble.DoubleFunctions;
import edu.emory.mathcs.utils.ConcurrencyUtils;
final class ParallelColtAdapter implements Adapter {
 final DoubleMatrix2D a,b,spd,rhs; final DenseDoubleAlgebra algebra=new DenseDoubleAlgebra();
 ParallelColtAdapter(Inputs in){ConcurrencyUtils.setNumberOfThreads(1);a=new DenseDoubleMatrix2D(in.a);b=new DenseDoubleMatrix2D(in.b);spd=new DenseDoubleMatrix2D(in.spd);rhs=new DenseDoubleMatrix2D(in.rhs);}
 public Object run(String op){switch(op){
 case "transpose":return a.viewDice().copy();case "addition":return a.copy().assign(b,DoubleFunctions.plus);case "scale":return a.copy().assign(DoubleFunctions.mult(1.5));case "multiplication":return a.zMult(b,new DenseDoubleMatrix2D(a.rows(),a.columns()));
 case "determinant":return algebra.det(a);case "inverse":return algebra.inverse(a);case "inverseSPD":{DoubleMatrix2D out=DoubleFactory2D.dense.identity(a.rows());algebra.chol(spd).solve(out);return out;}case "cholesky":return algebra.chol(spd).getL();
 case "lu":{DenseDoubleLUDecomposition f=algebra.lu(a);return new Object[]{f.getL(),f.getU(),f.getPivot()};}
 case "qr":{DenseDoubleQRDecomposition f=algebra.qr(a);return new Object[]{f.getQ(true),f.getR(true)};}
 case "eigenSymmetric":{DenseDoubleEigenvalueDecomposition f=algebra.eig(spd);return new Object[]{f.getV(),f.getD()};}
 case "svd":{DenseDoubleSingularValueDecomposition f=algebra.svd(a);return new Object[]{f.getU(),f.getS(),f.getV()};}
 case "solve":return algebra.solve(a,rhs);default:throw new IllegalArgumentException(op);}}
 public double[][] array(Object x){return ((DoubleMatrix2D)x).toArray();}
}
