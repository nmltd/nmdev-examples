/* Copyright (c) NM LTD. See the repository LICENSE. */
package dev.nm.benchmark;

/** Independent small reference calculations and factorization residual checks. Not timed. */
final class Validation {
    /** Check native input preservation, eager outputs and repeatability outside timing. */
    static double checkRepeated(String op, Inputs in, Adapter adapter) {
        Object[] nativeInputs=adapter.inputs();
        double[][][] before=new double[nativeInputs.length][][];
        for(int i=0;i<before.length;i++) before[i]=adapter.array(nativeInputs[i]);
        double residual=0;
        for(int repeat=0;repeat<2;repeat++) {
            Object out=adapter.run(op);
            assertMaterialized(out);
            residual=Math.max(residual,check(op,in,adapter,out));
            for(int i=0;i<before.length;i++) exact(before[i],adapter.array(nativeInputs[i]),"input "+i+" changed by "+op);
        }
        return residual;
    }
    static void assertMaterialized(Object out) {
        if(out instanceof Object[]) {for(Object part:(Object[])out) assertMaterialized(part);return;}
        if(out instanceof org.ojalgo.matrix.store.MatrixStore<?> && !(out instanceof org.ojalgo.matrix.store.PhysicalStore<?>))
            throw new IllegalStateException("Deferred ojAlgo output: "+out.getClass().getName());
    }
    static void exact(double[][] expected,double[][] actual,String label) {
        if(expected.length!=actual.length||expected[0].length!=actual[0].length)throw new IllegalStateException(label+" dimensions");
        for(int i=0;i<expected.length;i++)for(int j=0;j<expected[i].length;j++)
            if(Double.doubleToLongBits(expected[i][j])!=Double.doubleToLongBits(actual[i][j]))throw new IllegalStateException(label);
    }
    static double[][] transpose(double[][] a){double[][] c=new double[a[0].length][a.length];for(int i=0;i<a.length;i++)for(int j=0;j<a[0].length;j++)c[j][i]=a[i][j];return c;}
    static double[][] multiply(double[][] a,double[][] b){double[][] c=new double[a.length][b[0].length];for(int i=0;i<a.length;i++)for(int k=0;k<b.length;k++)for(int j=0;j<b[0].length;j++)c[i][j]+=a[i][k]*b[k][j];return c;}
    static double[][] identity(int n){double[][] c=new double[n][n];for(int i=0;i<n;i++)c[i][i]=1;return c;}
    static double residual(double[][] expected,double[][] actual){
        if(expected.length!=actual.length||expected[0].length!=actual[0].length)throw new IllegalStateException("Wrong dimensions");
        double error=0,scale=0;for(int i=0;i<expected.length;i++)for(int j=0;j<expected[0].length;j++){
            if(!Double.isFinite(actual[i][j]))throw new IllegalStateException("Nonfinite result");
            error=Math.max(error,Math.abs(expected[i][j]-actual[i][j]));scale=Math.max(scale,Math.abs(expected[i][j]));
        }
        return error/Math.max(1,scale);
    }
    // Partial pivot elimination, independent of every adapter's determinant routine.
    static double determinant(double[][] input){double[][] a=new double[input.length][];for(int i=0;i<a.length;i++)a[i]=input[i].clone();double d=1;for(int k=0;k<a.length;k++){int pivot=k;for(int i=k+1;i<a.length;i++)if(Math.abs(a[i][k])>Math.abs(a[pivot][k]))pivot=i;if(a[pivot][k]==0)return 0;if(pivot!=k){double[] t=a[k];a[k]=a[pivot];a[pivot]=t;d=-d;}d*=a[k][k];for(int i=k+1;i<a.length;i++){double t=a[i][k]/a[k][k];for(int j=k+1;j<a.length;j++)a[i][j]-=t*a[k][j];}}return d;}
    static void small(double value,String label){if(!Double.isFinite(value)||value>1e-8)throw new IllegalStateException(label+" residual "+value);}
    static double check(String op,Inputs in,Adapter adapter,Object result){
        double r=measure(op,in,adapter,result);small(r,op);return r;
    }
    static double norm(double[][] a){double n=0;for(double[] row:a)for(double x:row)n=Math.hypot(n,x);return n;}
    static double backward(double[][] a,double[][] x,double[][] b){
        double[][] ax=multiply(a,x);double e=0;
        for(int i=0;i<b.length;i++)for(int j=0;j<b[0].length;j++)e=Math.hypot(e,ax[i][j]-b[i][j]);
        return e/(norm(a)*norm(x)+norm(b));
    }
    static double measureDifficult(String op,Inputs in,Adapter adapter,Object out){
        if(op.equals("solve"))return backward(in.a,adapter.array(out),in.rhs);
        if(op.equals("inverse")||op.equals("inverseSPD")){
            double[][] a=op.equals("inverse")?in.a:in.spd,x=adapter.array(out),eye=identity(a.length);
            return Math.max(backward(a,x,eye),backward(x,a,eye));
        }
        return measure(op,in,adapter,out);
    }
    static double measure(String op,Inputs in,Adapter adapter,Object result){
        double[][] a=in.a,b=in.b,expected=null,actual=null;int n=a.length;double r=0;
        if(op.equals("determinant")){double d=determinant(a);r=Math.abs(((Number)result).doubleValue()/d-1);return r;}
        if(op.equals("lu")){
            Object[] f=(Object[])result;double[][] left;
            if(f[2] instanceof int[]){int[] pivot=(int[])f[2];left=new double[n][];for(int i=0;i<n;i++)left[i]=a[pivot[i]];}
            else left=multiply(adapter.array(f[2]),a);
            r=residual(left,multiply(adapter.array(f[0]),adapter.array(f[1])));
        }else if(op.equals("qr")){
            Object[] f=(Object[])result;double[][] q=adapter.array(f[0]);r=Math.max(residual(a,multiply(q,adapter.array(f[1]))),residual(identity(n),multiply(transpose(q),q)));
        }else if(op.equals("svd")){
            Object[] f=(Object[])result;double[][] u=adapter.array(f[0]),v=adapter.array(f[2]);
            r=Math.max(residual(a,multiply(multiply(u,adapter.array(f[1])),transpose(v))),Math.max(residual(identity(n),multiply(transpose(u),u)),residual(identity(n),multiply(transpose(v),v))));
        }else if(op.equals("eigenSymmetric")){
            Object[] f=(Object[])result;double[][] q=adapter.array(f[0]);r=Math.max(residual(multiply(in.spd,q),multiply(q,adapter.array(f[1]))),residual(identity(n),multiply(transpose(q),q)));
        }else{
            double[][] out=adapter.array(result);
            switch(op){
                case "transpose":expected=transpose(a);actual=out;break;
                case "addition":case "scale":expected=new double[n][n];for(int i=0;i<n;i++)for(int j=0;j<n;j++)expected[i][j]=op.equals("addition")?a[i][j]+b[i][j]:1.5*a[i][j];actual=out;break;
                case "multiplication":expected=multiply(a,b);actual=out;break;
                case "inverse":expected=identity(n);actual=multiply(a,out);break;
                case "inverseSPD":expected=identity(n);actual=multiply(in.spd,out);break;
                case "cholesky":expected=in.spd;actual=multiply(out,transpose(out));break;
                case "solve":expected=in.rhs;actual=multiply(a,out);break;
                default:throw new IllegalArgumentException(op);
            }
            r=residual(expected,actual);
        }
        return r;
    }
}
