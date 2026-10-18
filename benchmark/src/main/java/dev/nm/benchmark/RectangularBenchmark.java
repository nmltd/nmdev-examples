package dev.nm.benchmark;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.*;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ojalgo.OjAlgoUtils;
import org.ojalgo.matrix.store.R064Store;
import org.ojalgo.matrix.store.RawStore;
import org.jblas.DoubleMatrix;
import dev.nm.algebra.linear.matrix.doubles.Matrix;
import dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;

/** Independent rectangular multiplication track; never pooled with square factorization results. */
@State(Scope.Thread) @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations=3,time=300,timeUnit=TimeUnit.MILLISECONDS)
@Measurement(iterations=5,time=500,timeUnit=TimeUnit.MILLISECONDS)
@Fork(value=2,jvmArgsAppend={"-Xms1g","-Xmx1g","-XX:ActiveProcessorCount=1"}) @Threads(1)
public class RectangularBenchmark {
    @Param({"NMDEV","EJML","OJALGO","JBLAS"}) public String library;
    @Param({"128x512x64","512x128x256","1024x256x64","255x257x129"}) public String shape;
    private int m,k,n;
    private Matrix na,nb;
    private DMatrixRMaj ea,eb;
    private R064Store oa,ob;
    private DoubleMatrix ja,jb;
    private double[][] a,b;
    @Setup(Level.Trial) public void setup() {
        String[] dims=shape.split("x");m=Integer.parseInt(dims[0]);k=Integer.parseInt(dims[1]);n=Integer.parseInt(dims[2]);
        if(m<1||k<1||n<1)throw new IllegalArgumentException(shape);
        Random random=new Random(20261018L+m*31L+k*17L+n);
        a=new double[m][k];b=new double[k][n];
        for(double[] row:a)for(int j=0;j<row.length;j++)row[j]=random.nextDouble()-0.5;
        for(double[] row:b)for(int j=0;j<row.length;j++)row[j]=random.nextDouble()-0.5;
        switch(library) {
            case "NMDEV":na=new DenseMatrix(a);nb=new DenseMatrix(b);break;
            case "EJML":ea=new DMatrixRMaj(a);eb=new DMatrixRMaj(b);break;
            case "OJALGO":OjAlgoUtils.limitThreadsTo(ThreadSettings.count());oa=R064Store.FACTORY.copy(RawStore.wrap(a));ob=R064Store.FACTORY.copy(RawStore.wrap(b));break;
            case "JBLAS":ja=new DoubleMatrix(a);jb=new DoubleMatrix(b);break;
            default:throw new IllegalArgumentException(library);
        }
        double[][] expected=Validation.multiply(a,b);
        for(int repeat=0;repeat<2;repeat++) {
            Validation.small(Validation.residual(expected,array(compute())),"rectangular "+shape);
            Validation.exact(a,input(true),"rectangular A mutation");
            Validation.exact(b,input(false),"rectangular B mutation");
        }
    }
    @Benchmark public Object compute() {
        switch(library) {
            case "NMDEV":return na.multiply(nb);
            case "EJML":DMatrixRMaj out=new DMatrixRMaj(m,n);CommonOps_DDRM.mult(ea,eb,out);return out;
            case "OJALGO":return OjalgoAdapter.materialize(oa.multiply(ob));
            case "JBLAS":return ja.mmul(jb);
            default:throw new IllegalArgumentException(library);
        }
    }
    private double[][] input(boolean left) {
        switch(library) {
            case "NMDEV":return array(left?na:nb);
            case "EJML":return array(left?ea:eb);
            case "OJALGO":return array(left?oa:ob);
            case "JBLAS":return array(left?ja:jb);
            default:throw new IllegalArgumentException(library);
        }
    }
    private double[][] array(Object value) {
        if(value instanceof Matrix){Matrix x=(Matrix)value;double[][] out=new double[x.nRows()][x.nCols()];for(int i=0;i<out.length;i++)for(int j=0;j<out[i].length;j++)out[i][j]=x.get(i+1,j+1);return out;}
        if(value instanceof DMatrixRMaj){DMatrixRMaj x=(DMatrixRMaj)value;double[][] out=new double[x.numRows][x.numCols];for(int i=0;i<out.length;i++)for(int j=0;j<out[i].length;j++)out[i][j]=x.get(i,j);return out;}
        if(value instanceof org.ojalgo.structure.Access2D<?>){org.ojalgo.structure.Access2D<?> x=(org.ojalgo.structure.Access2D<?>)value;double[][] out=new double[(int)x.countRows()][(int)x.countColumns()];for(int i=0;i<out.length;i++)for(int j=0;j<out[i].length;j++)out[i][j]=x.doubleValue(i,j);return out;}
        return ((DoubleMatrix)value).toArray2();
    }
}
