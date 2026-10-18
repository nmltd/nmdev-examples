/* Copyright (c) NM LTD. See LICENSE. */
package dev.nm.benchmark;

/** Checks determinant sign and non-symmetric LU permutations on row-permuted inputs. */
public final class PivotValidationMain {
    public static void main(String[] args) {
        String[] libraries={"NMDEV","EJML","COMMONS","JAMA","COLT","PARALLEL_COLT","JBLAS","F2J_LAPACK","MTJ","OJALGO","UJMP"};
        java.io.PrintStream csv=System.out;System.setOut(System.err);
        csv.println("library,fixture,operation,status");
        for(String library:libraries)for(int fixture=0;fixture<2;fixture++)for(String op:new String[]{"determinant","lu","solve"}){
            Inputs inputs=new Inputs(4);
            double[] first=inputs.a[0];inputs.a[0]=inputs.a[1];
            if(fixture==0){inputs.a[1]=first;}
            else{inputs.a[1]=inputs.a[2];inputs.a[2]=first;}
            // Row permutations preserve A^T A, so the SPD fixture remains consistent.
            Adapter adapter=MatrixBenchmark.create(library,inputs);
            Validation.check(op,inputs,adapter,adapter.run(op));
            csv.printf("%s,%s,%s,PASS%n",library,fixture==0?"row-swap":"row-cycle",op);
        }
    }
}
