/* Copyright (c) NM LTD. See the repository LICENSE. */
package dev.nm.benchmark;

import java.util.Locale;

/** Produces a CSV correctness record independently of JMH measurement. */
public final class CorrectnessMain {
    public static void main(String[] args){
        String[] libraries=args.length>0?args[0].split(","):new String[]{"NMDEV","EJML","COMMONS","COMMONS_BLOCK","JAMA","COLT","PARALLEL_COLT","JBLAS","F2J_LAPACK","MTJ","OJALGO","UJMP"};
        String[] operations={"transpose","addition","scale","multiplication","determinant","inverse","inverseSPD","cholesky","lu","qr","eigenSymmetric","svd","solve"};
        java.io.PrintStream csv=System.out;
        System.setOut(System.err); // Keep library diagnostics out of the CSV.
        int failures=0;
        csv.println("library,size,operation,relative_residual,status");
        for(int size:new int[]{4,64,256})for(String library:libraries)for(String op:operations){
            try {
            Inputs inputs=new Inputs(size);Adapter adapter=MatrixBenchmark.create(library,inputs);
            double residual=Validation.checkRepeated(op, inputs, adapter);
            csv.printf(Locale.ROOT,"%s,%d,%s,%.12g,PASS%n",library,size,op,residual);
            } catch(Throwable error) { failures++; csv.printf("%s,%d,%s,,FAIL%n",library,size,op); System.err.println(library+"/"+size+"/"+op);error.printStackTrace(); }
        }
        if(failures>0)throw new IllegalStateException(failures+" correctness cases failed");
    }
}
