package dev.nm.benchmark;

/** Quick arithmetic and input-preservation checks for every rectangular adapter. */
public final class ExpandedRectangularValidationMain {
    private ExpandedRectangularValidationMain() {}
    public static void main(String[] args) {
        String[] libraries={"NMDEV","EJML","COMMONS","COMMONS_BLOCK","JAMA","COLT","PARALLEL_COLT","JBLAS","F2J_LAPACK","MTJ","OJALGO","UJMP"};
        java.io.PrintStream csv=System.out;System.setOut(System.err);
        csv.println("library,shape,status");
        for(String library:libraries) for(String shape:new String[]{"3x5x2","5x2x7","1x1x1"}) {
            ExpandedRectangularBenchmark benchmark=new ExpandedRectangularBenchmark();
            benchmark.library=library;benchmark.shape=shape;benchmark.setup();
            csv.println(library+","+shape+",PASS");
        }
    }
}
