/* Copyright (c) NM LTD. See the repository LICENSE. */
package dev.nm.benchmark;

import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_DDRM;
import org.ejml.interfaces.decomposition.*;
import org.ejml.interfaces.linsol.LinearSolverDense;

final class EjmlAdapter implements Adapter {
    final DMatrixRMaj a,b,spd,rhs; final int n;
    EjmlAdapter(Inputs in){a=new DMatrixRMaj(in.a);b=new DMatrixRMaj(in.b);spd=new DMatrixRMaj(in.spd);rhs=new DMatrixRMaj(in.rhs);n=a.numRows;}
    public Object run(String op) {
        switch(op) {
            case "transpose": return CommonOps_DDRM.transpose(a,null);
            case "addition": {DMatrixRMaj out=new DMatrixRMaj(n,n);CommonOps_DDRM.add(a,b,out);return out;}
            case "scale": {DMatrixRMaj out=new DMatrixRMaj(n,n);CommonOps_DDRM.scale(1.5,a,out);return out;}
            case "multiplication": {DMatrixRMaj out=new DMatrixRMaj(n,n);CommonOps_DDRM.mult(a,b,out);return out;}
            case "determinant": return CommonOps_DDRM.det(a);
            case "inverse": {DMatrixRMaj out=new DMatrixRMaj(n,n);if(!CommonOps_DDRM.invert(a,out))throw new IllegalStateException("Singular matrix");return out;}
            case "inverseSPD": {LinearSolverDense<DMatrixRMaj> f=LinearSolverFactory_DDRM.symmPosDef(n);if(!f.setA(spd.copy()))throw new IllegalStateException("Not SPD");DMatrixRMaj out=new DMatrixRMaj(n,n);f.invert(out);return out;}
            case "cholesky": {CholeskyDecomposition_F64<DMatrixRMaj> f=DecompositionFactory_DDRM.chol(n,true);if(!f.decompose(spd.copy()))throw new IllegalStateException("Cholesky failed");return f.getT(null);}
            case "lu": {LUDecomposition_F64<DMatrixRMaj> f=DecompositionFactory_DDRM.lu(n,n);if(!f.decompose(a.copy()))throw new IllegalStateException("LU failed");return new Object[]{f.getLower(null),f.getUpper(null),f.getRowPivotV(null)};}
            case "qr": {QRDecomposition<DMatrixRMaj> f=DecompositionFactory_DDRM.qr(n,n);if(!f.decompose(a.copy()))throw new IllegalStateException("QR failed");return new Object[]{f.getQ(null,true),f.getR(null,true)};}
            case "eigenSymmetric": {EigenDecomposition_F64<DMatrixRMaj> f=DecompositionFactory_DDRM.eig(n,true,true);if(!f.decompose(spd.copy()))throw new IllegalStateException("Eigen failed");DMatrixRMaj q=new DMatrixRMaj(n,n),d=new DMatrixRMaj(n,n);for(int i=0;i<n;i++){DMatrixRMaj v=f.getEigenVector(i);for(int j=0;j<n;j++)q.set(j,i,v.get(j,0));d.set(i,i,f.getEigenvalue(i).getReal());}return new Object[]{q,d};}
            case "svd": {SingularValueDecomposition_F64<DMatrixRMaj> f=DecompositionFactory_DDRM.svd(n,n,true,true,true);if(!f.decompose(a.copy()))throw new IllegalStateException("SVD failed");return new Object[]{f.getU(null,false),f.getW(null),f.getV(null,false)};}
            case "solve": {LinearSolverDense<DMatrixRMaj> f=LinearSolverFactory_DDRM.lu(n);if(!f.setA(a.copy()))throw new IllegalStateException("LU failed");DMatrixRMaj out=new DMatrixRMaj(n,1);f.solve(rhs,out);return out;}
            default: throw new IllegalArgumentException(op);
        }
    }
    public double[][] array(Object nativeMatrix){DMatrixRMaj m=(DMatrixRMaj)nativeMatrix;double[][] out=new double[m.numRows][m.numCols];for(int i=0;i<m.numRows;i++)for(int j=0;j<m.numCols;j++)out[i][j]=m.get(i,j);return out;}
}
