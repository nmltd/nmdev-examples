package dev.nm.benchmark;

import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import dev.nm.algebra.linear.matrix.doubles.operation.Inverse;

/** One untimed inversion for debugger method-entry verification of the distributed JAR. */
public final class InversePathProbe {
    public static void main(String[] args) {
        int size=args.length>1?Integer.parseInt(args[1]):64;
        Inputs in=new Inputs(size);
        double[][] input=args.length>0&&args[0].equals("general")?in.a:in.spd;
        NmAdapter adapter=new NmAdapter(in);
        double r=Validation.residual(Validation.identity(size),Validation.multiply(input,adapter.array(new Inverse(new DenseMatrix(input),0.0))));
        Validation.small(r,"inverse path probe");
        System.out.println("inverse residual="+r);
    }
}
