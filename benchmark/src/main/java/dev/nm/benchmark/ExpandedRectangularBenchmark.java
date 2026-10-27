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
public class ExpandedRectangularBenchmark {
    @Param({"NMDEV","EJML","COMMONS","COMMONS_BLOCK","JAMA","COLT","PARALLEL_COLT","JBLAS","F2J_LAPACK","MTJ","OJALGO","UJMP"}) public String library;
    @Param({"128x512x64","512x128x256","1024x256x64","255x257x129"}) public String shape;
    private int m,k,n;
    private Matrix na,nb;
    private DMatrixRMaj ea,eb;
    private R064Store oa,ob;
    private DoubleMatrix ja,jb;
    private org.apache.commons.math4.legacy.linear.RealMatrix ca,cb;
    private Jama.Matrix ma,mb;
    private cern.colt.matrix.DoubleMatrix2D cta,ctb;
    private cern.colt.matrix.tdouble.DoubleMatrix2D pca,pcb;
    private F2jLapackAdapter.M fa,fb;
    private no.uib.cipr.matrix.DenseMatrix ta,tb;
    private org.ujmp.core.Matrix ua,ub;
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
            case "COMMONS_BLOCK":ca=new org.apache.commons.math4.legacy.linear.BlockRealMatrix(a);cb=new org.apache.commons.math4.legacy.linear.BlockRealMatrix(b);break;
            case "COMMONS":ca=new org.apache.commons.math4.legacy.linear.Array2DRowRealMatrix(a);cb=new org.apache.commons.math4.legacy.linear.Array2DRowRealMatrix(b);break;
            case "JAMA":ma=new Jama.Matrix(a);mb=new Jama.Matrix(b);break;
            case "COLT":cta=new cern.colt.matrix.impl.DenseDoubleMatrix2D(a);ctb=new cern.colt.matrix.impl.DenseDoubleMatrix2D(b);break;
            case "PARALLEL_COLT":edu.emory.mathcs.utils.pc.ConcurrencyUtils.setNumberOfThreads(ThreadSettings.count());pca=new cern.colt.matrix.tdouble.impl.DenseDoubleMatrix2D(a);pcb=new cern.colt.matrix.tdouble.impl.DenseDoubleMatrix2D(b);break;
            case "F2J_LAPACK":fa=new F2jLapackAdapter.M(m,k,F2jLapackAdapter.flat(a));fb=new F2jLapackAdapter.M(k,n,F2jLapackAdapter.flat(b));break;
            case "MTJ":ta=new no.uib.cipr.matrix.DenseMatrix(a);tb=new no.uib.cipr.matrix.DenseMatrix(b);break;
            case "UJMP":org.ujmp.core.util.UJMPSettings settings=org.ujmp.core.util.UJMPSettings.getInstance();settings.setNumberOfThreads(ThreadSettings.count());settings.setUseCommonsMath(false);settings.setUseParallelColt(false);settings.setUseOjalgo(false);settings.setUseMTJ(false);settings.setUseEJML(false);settings.setUseJBlas(false);ua=org.ujmp.core.Matrix.Factory.importFromArray(a);ub=org.ujmp.core.Matrix.Factory.importFromArray(b);break;
            default:throw new IllegalArgumentException(library);
        }
        double[][] beforeA=copy(a),beforeB=copy(b);
        double[][] expected=Validation.multiply(beforeA,beforeB);
        for(int repeat=0;repeat<2;repeat++) {
            Object out=compute();Validation.assertMaterialized(out);
            Validation.small(Validation.residual(expected,array(out)),"rectangular "+shape);
            Validation.exact(beforeA,input(true),"rectangular A mutation");
            Validation.exact(beforeB,input(false),"rectangular B mutation");
        }
    }
    @Benchmark public Object compute() {
        switch(library) {
            case "NMDEV":return na.multiply(nb);
            case "EJML":DMatrixRMaj out=new DMatrixRMaj(m,n);CommonOps_DDRM.mult(ea,eb,out);return out;
            case "OJALGO":return OjalgoAdapter.materialize(oa.multiply(ob));
            case "JBLAS":return ja.mmul(jb);
            case "COMMONS_BLOCK":case "COMMONS":return ca.multiply(cb);
            case "JAMA":return ma.times(mb);
            case "COLT":return cta.zMult(ctb,null);
            case "PARALLEL_COLT":return pca.zMult(pcb,new cern.colt.matrix.tdouble.impl.DenseDoubleMatrix2D(m,n));
            case "F2J_LAPACK":double[] result=new double[m*n];org.netlib.blas.Dgemm.dgemm("N","N",m,n,k,1,fa.data,0,m,fb.data,0,k,0,result,0,m);return new F2jLapackAdapter.M(m,n,result);
            case "MTJ":return ta.mult(tb,new no.uib.cipr.matrix.DenseMatrix(m,n));
            case "UJMP":return ua.mtimes(ub);
            default:throw new IllegalArgumentException(library);
        }
    }
    private static double[][] copy(double[][] input){double[][] out=new double[input.length][];for(int i=0;i<input.length;i++)out[i]=input[i].clone();return out;}
    private double[][] input(boolean left) {
        switch(library) {
            case "NMDEV":return array(left?na:nb);
            case "EJML":return array(left?ea:eb);
            case "OJALGO":return array(left?oa:ob);
            case "JBLAS":return array(left?ja:jb);
            case "COMMONS_BLOCK":case "COMMONS":return array(left?ca:cb);
            case "JAMA":return array(left?ma:mb);
            case "COLT":return array(left?cta:ctb);
            case "PARALLEL_COLT":return array(left?pca:pcb);
            case "F2J_LAPACK":return array(left?fa:fb);
            case "MTJ":return array(left?ta:tb);
            case "UJMP":return array(left?ua:ub);
            default:throw new IllegalArgumentException(library);
        }
    }
    private double[][] array(Object value) {
        if(value instanceof Matrix){Matrix x=(Matrix)value;double[][] out=new double[x.nRows()][x.nCols()];for(int i=0;i<out.length;i++)for(int j=0;j<out[i].length;j++)out[i][j]=x.get(i+1,j+1);return out;}
        if(value instanceof DMatrixRMaj){DMatrixRMaj x=(DMatrixRMaj)value;double[][] out=new double[x.numRows][x.numCols];for(int i=0;i<out.length;i++)for(int j=0;j<out[i].length;j++)out[i][j]=x.get(i,j);return out;}
        if(value instanceof org.ojalgo.structure.Access2D<?>){org.ojalgo.structure.Access2D<?> x=(org.ojalgo.structure.Access2D<?>)value;double[][] out=new double[(int)x.countRows()][(int)x.countColumns()];for(int i=0;i<out.length;i++)for(int j=0;j<out[i].length;j++)out[i][j]=x.doubleValue(i,j);return out;}
        if(value instanceof org.apache.commons.math4.legacy.linear.RealMatrix)return ((org.apache.commons.math4.legacy.linear.RealMatrix)value).getData();
        if(value instanceof Jama.Matrix)return ((Jama.Matrix)value).getArrayCopy();
        if(value instanceof cern.colt.matrix.DoubleMatrix2D)return ((cern.colt.matrix.DoubleMatrix2D)value).toArray();
        if(value instanceof cern.colt.matrix.tdouble.DoubleMatrix2D)return ((cern.colt.matrix.tdouble.DoubleMatrix2D)value).toArray();
        if(value instanceof F2jLapackAdapter.M){F2jLapackAdapter.M x=(F2jLapackAdapter.M)value;double[][] out=new double[x.rows][x.cols];for(int i=0;i<x.rows;i++)for(int j=0;j<x.cols;j++)out[i][j]=x.data[i+j*x.rows];return out;}
        if(value instanceof no.uib.cipr.matrix.Matrix){no.uib.cipr.matrix.Matrix x=(no.uib.cipr.matrix.Matrix)value;double[][] out=new double[x.numRows()][x.numColumns()];for(int i=0;i<out.length;i++)for(int j=0;j<out[i].length;j++)out[i][j]=x.get(i,j);return out;}
        if(value instanceof org.ujmp.core.Matrix)return ((org.ujmp.core.Matrix)value).toDoubleArray();
        return ((DoubleMatrix)value).toArray2();
    }
}
