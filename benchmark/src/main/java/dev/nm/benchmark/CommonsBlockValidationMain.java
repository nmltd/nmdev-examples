package dev.nm.benchmark;

import org.apache.commons.math4.legacy.linear.BlockRealMatrix;

/** Exercises complete and partial Commons blocks, outside performance measurements. */
public final class CommonsBlockValidationMain {
    public static void main(String[] args) {
        for(int size:new int[]{51,52,53,105}) {
            Inputs inputs=new Inputs(size);
            Adapter adapter=MatrixBenchmark.create("COMMONS_BLOCK",inputs);
            for(Object input:adapter.inputs())
                if(!(input instanceof BlockRealMatrix))throw new AssertionError("Expected block input");
            for(String op:new String[]{"transpose","addition","scale","multiplication","cholesky","lu","qr","solve"})
                Validation.checkRepeated(op,inputs,adapter);
        }
        for(String shape:new String[]{"51x52x53","105x53x107"}) {
            ExpandedRectangularBenchmark benchmark=new ExpandedRectangularBenchmark();
            benchmark.library="COMMONS_BLOCK";benchmark.shape=shape;benchmark.setup();
        }
        System.out.println("PASS: Commons block boundaries, rectangular products and input preservation");
    }
    private CommonsBlockValidationMain() {}
}
