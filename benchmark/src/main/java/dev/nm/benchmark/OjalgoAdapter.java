/* Copyright (c) NM LTD. Adapted from the original benchmark adapters; see Source-Provenance.md. */
package dev.nm.benchmark;

import org.ojalgo.OjAlgoUtils;
import org.ojalgo.matrix.store.*;
import org.ojalgo.matrix.decomposition.*;
import org.ojalgo.structure.Access2D;
final class OjalgoAdapter implements Adapter {
 final R064Store a,b,spd,rhs;final int n;
 OjalgoAdapter(Inputs in){OjAlgoUtils.limitThreadsTo(1);a=R064Store.FACTORY.copy(RawStore.wrap(in.a));b=R064Store.FACTORY.copy(RawStore.wrap(in.b));spd=R064Store.FACTORY.copy(RawStore.wrap(in.spd));rhs=R064Store.FACTORY.copy(RawStore.wrap(in.rhs));n=in.a.length;}
 public Object run(String op){switch(op){
 case "transpose":return R064Store.FACTORY.copy(a.transpose());case "addition":return a.add(b);case "scale":return a.multiply(1.5);case "multiplication":return a.multiply(b);
 case "determinant":{LU<Double> f=LU.R064.make(a);f.decompose(a);return f.getDeterminant();}
 case "inverse":{LU<Double> f=LU.R064.make(a);f.decompose(a);return f.getInverse();}
 case "inverseSPD":{Cholesky<Double> f=Cholesky.R064.make(spd);f.decompose(spd);return f.getInverse();}
 case "cholesky":{Cholesky<Double> f=Cholesky.R064.make(spd);f.decompose(spd);return R064Store.FACTORY.copy(f.getL());}
 case "lu":{LU<Double> f=LU.R064.make(a);f.decompose(a);return new Object[]{R064Store.FACTORY.copy(f.getL()),R064Store.FACTORY.copy(f.getU()),f.getPivotOrder()};}
 case "qr":{QR<Double> f=QR.R064.make(a);f.decompose(a);return new Object[]{R064Store.FACTORY.copy(f.getQ()),R064Store.FACTORY.copy(f.getR())};}
 case "eigenSymmetric":{Eigenvalue<Double> f=Eigenvalue.R064.make(spd,true);f.decompose(spd);return new Object[]{R064Store.FACTORY.copy(f.getV()),R064Store.FACTORY.copy(f.getD())};}
 case "svd":{SingularValue<Double> f=SingularValue.R064.make(a);f.decompose(a);return new Object[]{R064Store.FACTORY.copy(f.getU()),R064Store.FACTORY.copy(f.getD()),R064Store.FACTORY.copy(f.getV())};}
 case "solve":{LU<Double> f=LU.R064.make(a);f.decompose(a);return f.getSolution(rhs);}
 default:throw new IllegalArgumentException(op);}}
 public double[][] array(Object x){Access2D<?> m=(Access2D<?>)x;double[][] out=new double[(int)m.countRows()][(int)m.countColumns()];for(int i=0;i<out.length;i++)for(int j=0;j<out[i].length;j++)out[i][j]=m.doubleValue(i,j);return out;}
}
