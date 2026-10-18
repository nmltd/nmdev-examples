/* Copyright (c) NM LTD. See the repository LICENSE. */
package dev.nm.benchmark;

import dev.nm.algebra.linear.matrix.doubles.Matrix;
import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import dev.nm.algebra.linear.matrix.doubles.factorization.triangle.cholesky.Chol;
import dev.nm.algebra.linear.matrix.doubles.factorization.triangle.LU;
import dev.nm.algebra.linear.matrix.doubles.factorization.qr.QR;
import dev.nm.algebra.linear.matrix.doubles.factorization.svd.SVD;
import dev.nm.algebra.linear.matrix.doubles.factorization.eigen.EigenDecomposition;
import dev.nm.algebra.linear.matrix.doubles.operation.Inverse;
import dev.nm.algebra.linear.matrix.doubles.operation.MatrixMeasure;
import dev.nm.algebra.linear.matrix.doubles.linearsystem.LUSolver;

final class NmAdapter implements Adapter {
    final Matrix a,b,spd,rhs;
    NmAdapter(Inputs in) {a=new DenseMatrix(in.a);b=new DenseMatrix(in.b);spd=new DenseMatrix(in.spd);rhs=new DenseMatrix(in.rhs);}
    public Object[] inputs(){return new Object[]{a,b,spd,rhs};}
    public Object run(String op) {
        switch(op) {
            case "transpose": return a.t();
            case "addition": return a.add(b);
            case "scale": return a.scaled(1.5);
            case "multiplication": return a.multiply(b);
            case "determinant": return MatrixMeasure.det(a);
            case "inverse": return new Inverse(a,0.0);
            case "inverseSPD": return new Inverse(spd,0.0);
            case "cholesky": return new Chol(spd).L();
            case "lu": {LU f=new LU(a);return new Object[]{f.L(),f.U(),f.P()};}
            case "qr": {QR f=new QR(a);return new Object[]{f.Q(),f.R()};}
            case "eigenSymmetric": {EigenDecomposition f=new EigenDecomposition(spd);return new Object[]{f.Q(),f.D()};}
            case "svd": {SVD f=new SVD(a,true);return new Object[]{f.U(),f.D(),f.V()};}
            case "solve": return new LUSolver().solve(a,rhs);
            default: throw new IllegalArgumentException(op);
        }
    }
    public double[][] array(Object nativeMatrix) {
        Matrix m=(Matrix)nativeMatrix;double[][] out=new double[m.nRows()][m.nCols()];
        for(int i=0;i<out.length;i++)for(int j=0;j<out[i].length;j++)out[i][j]=m.get(i+1,j+1);
        return out;
    }
}
