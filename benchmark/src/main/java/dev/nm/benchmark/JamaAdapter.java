/* Copyright (c) NM LTD. See the repository LICENSE. */
package dev.nm.benchmark;

import Jama.*;

final class JamaAdapter implements Adapter {
    final Matrix a,b,spd,rhs;
    JamaAdapter(Inputs in){a=new Matrix(in.a);b=new Matrix(in.b);spd=new Matrix(in.spd);rhs=new Matrix(in.rhs);}
    public Object run(String op) {
        switch(op) {
            case "transpose": return a.transpose();
            case "addition": return a.plus(b);
            case "scale": return a.times(1.5);
            case "multiplication": return a.times(b);
            case "determinant": return a.det();
            case "inverse": return a.inverse();
            case "inverseSPD": return spd.chol().solve(Matrix.identity(a.getRowDimension(),a.getRowDimension()));
            case "cholesky": return spd.chol().getL();
            case "lu": {LUDecomposition f=a.lu();return new Object[]{f.getL(),f.getU(),f.getPivot()};}
            case "qr": {QRDecomposition f=a.qr();return new Object[]{f.getQ(),f.getR()};}
            case "eigenSymmetric": {EigenvalueDecomposition f=spd.eig();return new Object[]{f.getV(),f.getD()};}
            case "svd": {SingularValueDecomposition f=a.svd();return new Object[]{f.getU(),f.getS(),f.getV()};}
            case "solve": return a.solve(rhs);
            default: throw new IllegalArgumentException(op);
        }
    }
    public double[][] array(Object nativeMatrix){return ((Matrix)nativeMatrix).getArrayCopy();}
}
