/* Copyright (c) NM LTD. Adapted from the original benchmark adapters; see Source-Provenance.md. */
package dev.nm.benchmark;

import org.netlib.lapack.*;
import org.netlib.blas.Dgemm;
import org.netlib.util.intW;
/** Direct F2J translated LAPACK/BLAS, replacing the unavailable legacy JLAPACK artifact. */
final class F2jLapackAdapter implements Adapter {
 final int n;final double[] a,b,spd,rhs;
 F2jLapackAdapter(Inputs in){n=in.a.length;a=flat(in.a);b=flat(in.b);spd=flat(in.spd);rhs=flat(in.rhs);}
 static final class M {final int rows,cols;final double[] data;M(int r,int c,double[] d){rows=r;cols=c;data=d;}}
 static double[] flat(double[][] x){double[] out=new double[x.length*x[0].length];for(int j=0;j<x[0].length;j++)for(int i=0;i<x.length;i++)out[i+j*x.length]=x[i][j];return out;}
 static void ok(intW info){if(info.val!=0)throw new IllegalStateException("LAPACK info="+info.val);}
 M matrix(double[] x){return new M(n,n,x);}
 M diagonal(double[] values){double[] d=new double[n*n];for(int i=0;i<n;i++)d[i+i*n]=values[i];return matrix(d);}
 public Object[] inputs(){return new Object[]{matrix(a),matrix(b),matrix(spd),new M(n,1,rhs)};}
    public Object run(String op){intW info=new intW(0);double[] x;int[] piv=new int[n];switch(op){
 case "transpose":{x=new double[n*n];for(int i=0;i<n;i++)for(int j=0;j<n;j++)x[i+j*n]=a[j+i*n];return matrix(x);}
 case "addition":case "scale":{x=new double[n*n];for(int i=0;i<x.length;i++)x[i]=op.equals("addition")?a[i]+b[i]:a[i]*1.5;return matrix(x);}
 case "multiplication":{x=new double[n*n];Dgemm.dgemm("N","N",n,n,n,1,a,0,n,b,0,n,0,x,0,n);return matrix(x);}
 case "determinant":case "lu":case "inverse":{
 x=a.clone();Dgetrf.dgetrf(n,n,x,0,n,piv,0,info);ok(info);
 if(op.equals("determinant")){double d=1;for(int i=0;i<n;i++){d*=x[i+i*n];if(piv[i]!=i+1)d=-d;}return d;}
 if(op.equals("inverse")){double[] work=new double[64*n];Dgetri.dgetri(n,x,0,n,piv,0,work,0,work.length,info);ok(info);return matrix(x);}
 double[] l=new double[n*n],u=new double[n*n];int[] order=new int[n];for(int i=0;i<n;i++)order[i]=i;
 for(int i=0;i<n;i++){int j=piv[i]-1,t=order[i];order[i]=order[j];order[j]=t;for(int k=0;k<n;k++){if(i>k)l[i+k*n]=x[i+k*n];else u[i+k*n]=x[i+k*n];}l[i+i*n]=1;}
 return new Object[]{matrix(l),matrix(u),order};}
 case "inverseSPD":case "cholesky":{x=spd.clone();Dpotrf.dpotrf("L",n,x,0,n,info);ok(info);if(op.equals("inverseSPD")){Dpotri.dpotri("L",n,x,0,n,info);ok(info);for(int i=0;i<n;i++)for(int j=i+1;j<n;j++)x[i+j*n]=x[j+i*n];}else for(int i=0;i<n;i++)for(int j=i+1;j<n;j++)x[i+j*n]=0;return matrix(x);}
 case "qr":{x=a.clone();double[] tau=new double[n],work=new double[64*n],r=new double[n*n];Dgeqrf.dgeqrf(n,n,x,0,n,tau,0,work,0,work.length,info);ok(info);for(int i=0;i<n;i++)for(int j=i;j<n;j++)r[i+j*n]=x[i+j*n];Dorgqr.dorgqr(n,n,n,x,0,n,tau,0,work,0,work.length,info);ok(info);return new Object[]{matrix(x),matrix(r)};}
 case "eigenSymmetric":{x=spd.clone();double[] values=new double[n],work=new double[64*n];Dsyev.dsyev("V","L",n,x,0,n,values,0,work,0,work.length,info);ok(info);return new Object[]{matrix(x),diagonal(values)};}
 case "svd":{x=a.clone();double[] values=new double[n],u=new double[n*n],vt=new double[n*n],v=new double[n*n],work=new double[64*n];Dgesvd.dgesvd("A","A",n,n,x,0,n,values,0,u,0,n,vt,0,n,work,0,work.length,info);ok(info);for(int i=0;i<n;i++)for(int j=0;j<n;j++)v[i+j*n]=vt[j+i*n];return new Object[]{matrix(u),diagonal(values),matrix(v)};}
 case "solve":{x=a.clone();double[] y=rhs.clone();Dgesv.dgesv(n,1,x,0,n,piv,0,y,0,n,info);ok(info);return new M(n,1,y);}
 default:throw new IllegalArgumentException(op);}}
 public double[][] array(Object x){M m=(M)x;double[][] out=new double[m.rows][m.cols];for(int i=0;i<m.rows;i++)for(int j=0;j<m.cols;j++)out[i][j]=m.data[i+j*m.rows];return out;}
}
