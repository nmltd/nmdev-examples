package dev.nm.benchmark;

import java.nio.file.Paths;

/** Untimed guard against a bundled or stale jar shadowing an explicitly selected library. */
public final class OriginValidationMain {
    public static void main(String[] args) throws Exception {
        String[][] cases={
            {"NMDEV","dev.nm.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix","nmdev-1.2.6.jar"},
            {"COMMONS","org.apache.commons.math4.legacy.linear.Array2DRowRealMatrix","commons-math4-legacy-4.0-SNAPSHOT.jar"},
            {"PARALLEL_COLT","cern.colt.matrix.tdouble.impl.DenseDoubleMatrix2D","parallelcolt-0.11.4.jar"},
            {"EJML","org.ejml.data.DMatrixRMaj","ejml-core-0.46.1.jar"},
            {"OJALGO","org.ojalgo.matrix.store.R064Store","ojalgo-57.5.0.jar"},
            {"COLT","cern.colt.matrix.impl.DenseDoubleMatrix2D","colt-1.2.0.jar"},
            {"JAMA","Jama.Matrix","jama-1.0.3.jar"},
            {"JBLAS","org.jblas.DoubleMatrix","jblas-1.2.5.jar"},
            {"MTJ","no.uib.cipr.matrix.DenseMatrix","mtj-1.0.4.jar"},
            {"UJMP","org.ujmp.core.Matrix","ujmp-core-0.3.0.jar"},
            {"F2J_LAPACK","org.netlib.blas.Dgemm","arpack_combined_all-0.1.jar"}
        };
        System.out.println("library,class,loaded_jar,status");
        for(String[] entry:cases) {
            Class<?> type=Class.forName(entry[1],false,ClassLoader.getSystemClassLoader());
            String file=Paths.get(type.getProtectionDomain().getCodeSource().getLocation().toURI()).getFileName().toString();
            if(!file.equals(entry[2]))throw new IllegalStateException(entry[0]+" loaded from "+file+", expected "+entry[2]);
            System.out.println(entry[0]+","+entry[1]+","+file+",PASS");
        }
    }
}
