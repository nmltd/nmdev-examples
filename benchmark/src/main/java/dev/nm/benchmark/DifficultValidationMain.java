package dev.nm.benchmark;

import java.util.Locale;

/** Accuracy diagnostics are kept separate from baseline speed rankings. All failures are recorded. */
public final class DifficultValidationMain {
    public static void main(String[] args) {
        String[] libraries=args.length>0?args[0].split(","):new String[]{"NMDEV","EJML","COMMONS","JAMA","COLT","PARALLEL_COLT","JBLAS","F2J_LAPACK","MTJ","OJALGO","UJMP"};
        java.io.PrintStream csv=System.out;System.setOut(System.err);
        csv.println("library,fixture,size,operation,residual,status");int failures=0;
        for(String library:libraries)for(String fixture:new String[]{"scaled-pivot","clustered-spd","hilbert","rank-deficient"}) {
            Inputs in=fixture(fixture);
            String[] ops=fixture.equals("rank-deficient")?new String[]{"qr","svd"}:
                fixture.equals("clustered-spd")?new String[]{"eigenSymmetric","svd","cholesky","inverseSPD"}:
                fixture.equals("hilbert")?new String[]{"lu","solve","cholesky","inverseSPD"}:new String[]{"lu","solve","inverse","qr","svd"};
            for(String op:ops)try {
                Adapter adapter=MatrixBenchmark.create(library,in);
                Object[] nativeInputs=adapter.inputs();double[][][] before=new double[nativeInputs.length][][];
                for(int i=0;i<before.length;i++)before[i]=adapter.array(nativeInputs[i]);
                double r=0;
                for(int repeat=0;repeat<2;repeat++){
                    Object out=adapter.run(op);Validation.assertMaterialized(out);
                    r=Math.max(r,Validation.measureDifficult(op,in,adapter,out));
                    for(int i=0;i<before.length;i++)Validation.exact(before[i],adapter.array(nativeInputs[i]),"native input mutation");
                }
                boolean pass=Double.isFinite(r)&&r<=1e-8;
                if(!pass)failures++;
                csv.printf(Locale.ROOT,"%s,%s,%d,%s,%.12g,%s%n",library,fixture,in.a.length,op,r,pass?"PASS":"FAIL");
            }catch(Throwable e){failures++;csv.printf("%s,%s,%d,%s,,FAIL%n",library,fixture,in.a.length,op);System.err.println(library+"/"+fixture+"/"+op+": "+e);}
        }
        System.err.println("Difficult-input failures: "+failures+" (diagnostic, never silently excluded)");
        if(failures>0)System.exit(1);
    }
    static Inputs fixture(String name) {
        Inputs in=new Inputs(8);int n=in.a.length;
        if(name.equals("scaled-pivot")) {
            for(int i=0;i<n;i++)for(int j=0;j<n;j++)in.a[i][j]*=Math.pow(10,i-3);
            double[] row=in.a[0];in.a[0]=in.a[n-1];in.a[n-1]=row;
        }else if(name.equals("hilbert")) {
            for(int i=0;i<n;i++)for(int j=0;j<n;j++)in.a[i][j]=1.0/(i+j+1);
        }else if(name.equals("rank-deficient")) {
            for(int i=0;i<n;i++)for(int j=0;j<n;j++)in.a[i][j]=i<3&&i==j?1:0;
        }else if(name.equals("clustered-spd")) {
            // Dense Q diag(lambda) Q^T, Q is a Householder reflector; two tight eigenvalue clusters.
            double[][] q=Validation.identity(n),d=new double[n][n];
            for(int i=0;i<n;i++){d[i][i]=(i<n/2?1:2)+i*1e-12;for(int j=0;j<n;j++)q[i][j]-=2.0/n;}
            double[][] a=Validation.multiply(Validation.multiply(q,d),Validation.transpose(q));
            for(int i=0;i<n;i++)for(int j=0;j<n;j++)in.a[i][j]=a[i][j];
        }else throw new IllegalArgumentException(name);
        double[][] spd=name.equals("hilbert")||name.equals("clustered-spd")?in.a:Validation.multiply(Validation.transpose(in.a),in.a);
        for(int i=0;i<n;i++)System.arraycopy(spd[i],0,in.spd[i],0,n);
        return in;
    }
}
