/* Copyright (c) NM LTD. Adapted from the original benchmark adapters; see Source-Provenance.md. */
package dev.nm.benchmark;

import cern.colt.matrix.*;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.*;
import cern.jet.math.Functions;
final class ColtAdapter implements Adapter {
 final DoubleMatrix2D a,b,spd,rhs; final Algebra algebra=new Algebra();
 ColtAdapter(Inputs in){a=new DenseDoubleMatrix2D(in.a);b=new DenseDoubleMatrix2D(in.b);spd=new DenseDoubleMatrix2D(in.spd);rhs=new DenseDoubleMatrix2D(in.rhs);}
 public Object[] inputs(){return new Object[]{a,b,spd,rhs};}
    public Object run(String op){switch(op){
 case "transpose":return a.viewDice().copy();case "addition":return a.copy().assign(b,Functions.plus);case "scale":return a.copy().assign(Functions.mult(1.5));case "multiplication":return a.zMult(b,null);
 case "determinant":return algebra.det(a);case "inverse":return algebra.inverse(a);case "inverseSPD":return new CholeskyDecomposition(spd).solve(DoubleFactory2D.dense.identity(a.rows()));case "cholesky":return new CholeskyDecomposition(spd).getL();
 case "lu":{LUDecomposition f=new LUDecomposition(a);return new Object[]{f.getL(),f.getU(),f.getPivot()};}
 case "qr":{QRDecomposition f=new QRDecomposition(a);return new Object[]{f.getQ(),f.getR()};}
 case "eigenSymmetric":{EigenvalueDecomposition f=new EigenvalueDecomposition(spd);return new Object[]{f.getV(),f.getD()};}
 case "svd":{SingularValueDecomposition f=new SingularValueDecomposition(a);return new Object[]{f.getU(),f.getS(),f.getV()};}
 case "solve":return algebra.solve(a,rhs);default:throw new IllegalArgumentException(op);}}
 public double[][] array(Object x){return ((DoubleMatrix2D)x).toArray();}
}
