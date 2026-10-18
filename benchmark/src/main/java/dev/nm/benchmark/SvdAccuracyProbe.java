package dev.nm.benchmark;

import dev.nm.algebra.linear.matrix.doubles.Matrix;
import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import dev.nm.algebra.linear.matrix.doubles.factorization.svd.SVD;
import dev.nm.misc.PrecisionUtils;

/** Diagnose the default SVD's difficult-input failures without changing library behavior. */
public final class SvdAccuracyProbe {
    public static void main(String[] args) {
        java.io.PrintStream csv=System.out;System.setOut(System.err);
        csv.println("fixture,method,residual,status");
        for(String fixture:new String[]{"scaled-pivot","clustered-spd"})for(SVD.Method method:SVD.Method.values()) {
            Inputs in=DifficultValidationMain.fixture(fixture);Matrix a=new DenseMatrix(in.a);
            SVD f=new SVD(a,true,PrecisionUtils.autoEpsilon(a),method);
            double r=Validation.measure("svd",in,new NmAdapter(in),new Object[]{f.U(),f.D(),f.V()});
            csv.println(fixture+","+method+","+r+","+(Double.isFinite(r)&&r<=1e-8?"PASS":"FAIL"));
        }
    }
}
