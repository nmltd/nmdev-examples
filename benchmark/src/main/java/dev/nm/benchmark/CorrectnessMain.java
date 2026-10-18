/* Copyright (c) NM LTD. See the repository LICENSE. */
package dev.nm.benchmark;

import java.util.Locale;

/** Produces a CSV correctness record independently of JMH measurement. */
public final class CorrectnessMain {
    public static void main(String[] args){
        String[] libraries={"NMDEV","EJML","COMMONS","JAMA"};
        String[] operations={"transpose","addition","scale","multiplication","determinant","inverse","inverseSPD","cholesky","lu","qr","eigenSymmetric","svd","solve"};
        System.out.println("library,size,operation,relative_residual,status");
        for(int size:new int[]{4,64,256})for(String library:libraries)for(String op:operations){
            Inputs inputs=new Inputs(size);Adapter adapter=MatrixBenchmark.create(library,inputs);
            double residual=Validation.check(op,inputs,adapter,adapter.run(op));
            // A second invocation checks that repeat execution remains valid.
            Validation.check(op,inputs,adapter,adapter.run(op));
            System.out.printf(Locale.ROOT,"%s,%d,%s,%.12g,PASS%n",library,size,op,residual);
        }
    }
}
