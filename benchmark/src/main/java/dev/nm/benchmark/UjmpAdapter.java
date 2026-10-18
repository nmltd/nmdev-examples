/* Copyright (c) NM LTD. Adapted from the original benchmark adapters; see Source-Provenance.md. */
package dev.nm.benchmark;

import org.ujmp.core.Matrix;
import org.ujmp.core.util.UJMPSettings;
final class UjmpAdapter implements Adapter {
 final Matrix a,b,spd,rhs;
 UjmpAdapter(Inputs in){UJMPSettings s=UJMPSettings.getInstance();s.setNumberOfThreads(1);s.setUseCommonsMath(false);s.setUseParallelColt(false);s.setUseOjalgo(false);s.setUseMTJ(false);s.setUseEJML(false);s.setUseJBlas(false);a=Matrix.Factory.importFromArray(in.a);b=Matrix.Factory.importFromArray(in.b);spd=Matrix.Factory.importFromArray(in.spd);rhs=Matrix.Factory.importFromArray(in.rhs);}
 public Object run(String op){switch(op){
 case "transpose":return a.transpose(org.ujmp.core.calculation.Calculation.Ret.NEW);case "addition":return a.plus(b);case "scale":return a.times(1.5);case "multiplication":return a.mtimes(b);case "determinant":return a.det();case "inverse":return a.inv();case "inverseSPD":return spd.invSPD();case "cholesky":return spd.chol();
 case "lu":return a.lu();case "qr":return a.qr();case "eigenSymmetric":return spd.eigSymm();case "svd":return a.svd();case "solve":return a.solve(rhs);default:throw new IllegalArgumentException(op);}}
 public double[][] array(Object x){return ((Matrix)x).toDoubleArray();}
}
