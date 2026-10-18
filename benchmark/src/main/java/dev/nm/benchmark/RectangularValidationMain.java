package dev.nm.benchmark;

/** Runs each rectangular setup's independent arithmetic and input-preservation checks. */
public final class RectangularValidationMain {
    public static void main(String[] args) {
        java.io.PrintStream csv=System.out;System.setOut(System.err);
        csv.println("library,shape,status");
        for(String lib:new String[]{"NMDEV","EJML","OJALGO","JBLAS"})for(String shape:new String[]{"128x512x64","512x128x256","1024x256x64","255x257x129"}) {
            RectangularBenchmark benchmark=new RectangularBenchmark();benchmark.library=lib;benchmark.shape=shape;
            benchmark.setup();csv.println(lib+","+shape+",PASS");
        }
    }
}
