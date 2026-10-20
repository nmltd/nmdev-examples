/* Copyright (c) NM LTD. See the repository LICENSE. */
package dev.nm.benchmark;

import org.apache.commons.math4.legacy.linear.*;

final class CommonsAdapter implements Adapter {
    final RealMatrix a,b,spd,rhs;
    CommonsAdapter(Inputs in){this(in,false);}
    /** Convert native inputs before timing; both Commons variants use identical operations. */
    CommonsAdapter(Inputs in, boolean block){a=matrix(in.a,block);b=matrix(in.b,block);spd=matrix(in.spd,block);rhs=matrix(in.rhs,block);}
    private static RealMatrix matrix(double[][] data, boolean block){return block?new BlockRealMatrix(data):new Array2DRowRealMatrix(data);}

    public Object[] inputs(){return new Object[]{a,b,spd,rhs};}
    public Object run(String op) {
        switch(op) {
            case "transpose": return a.transpose();
            case "addition": return a.add(b);
            case "scale": return a.scalarMultiply(1.5);
            case "multiplication": return a.multiply(b);
            case "determinant": return new LUDecomposition(a).getDeterminant();
            case "inverse": return new LUDecomposition(a).getSolver().getInverse();
            case "inverseSPD": return new CholeskyDecomposition(spd).getSolver().getInverse();
            case "cholesky": return new CholeskyDecomposition(spd).getL();
            case "lu": {LUDecomposition f=new LUDecomposition(a);return new Object[]{f.getL(),f.getU(),f.getPivot()};}
            case "qr": {QRDecomposition f=new QRDecomposition(a);return new Object[]{f.getQ(),f.getR()};}
            case "eigenSymmetric": {EigenDecomposition f=new EigenDecomposition(spd);return new Object[]{f.getV(),f.getD()};}
            case "svd": {SingularValueDecomposition f=new SingularValueDecomposition(a);return new Object[]{f.getU(),f.getS(),f.getV()};}
            case "solve": return new LUDecomposition(a).getSolver().solve(rhs);
            default: throw new IllegalArgumentException(op);
        }
    }
    public double[][] array(Object nativeMatrix){return ((RealMatrix)nativeMatrix).getData();}
}
