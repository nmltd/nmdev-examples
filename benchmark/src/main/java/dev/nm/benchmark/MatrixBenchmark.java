/* Copyright (c) NM LTD. See the repository LICENSE. */
package dev.nm.benchmark;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;

/**
 * Updated counterpart of the legacy 13-operation linear algebra suite.
 * One JMH worker, independent forks and returned results prevent discarded-work timing.
 * Setup converts seeded arrays before timing. Allocation intrinsic to each operation is timed.
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations=3,time=300,timeUnit=TimeUnit.MILLISECONDS)
@Measurement(iterations=5,time=500,timeUnit=TimeUnit.MILLISECONDS)
@Fork(value=2,jvmArgsAppend={"-Xms1g","-Xmx1g","-XX:ActiveProcessorCount=1"})
@Threads(1)
public class MatrixBenchmark {
    @Param({"NMDEV","EJML","COMMONS","JAMA"}) public String library;
    @Param({"64","256"}) public int size;
    @Param({"transpose","addition","scale","multiplication","determinant","inverse","inverseSPD","cholesky","lu","qr","eigenSymmetric","svd","solve"}) public String operation;
    private Adapter adapter;

    /** Creates native inputs and validates the selected operation before every fork's timing. */
    @Setup(Level.Trial) public void setup() {
        Inputs inputs=new Inputs(size);
        adapter=create(library,inputs);
        Validation.check(operation,inputs,adapter,adapter.run(operation));
    }
    static Adapter create(String name,Inputs inputs) {
        switch(name){case "NMDEV":return new NmAdapter(inputs);case "EJML":return new EjmlAdapter(inputs);case "COMMONS":return new CommonsAdapter(inputs);case "JAMA":return new JamaAdapter(inputs);default:throw new IllegalArgumentException(name);}
    }
    /** Returns every result to JMH's generated blackhole consumer. */
    @Benchmark public Object compute(){return adapter.run(operation);}
}
