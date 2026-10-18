# Library coverage

All eleven adapters below compile and have passed 429 baseline correctness cases twice plus 66 targeted pivot/sign cases. The expanded JMH comparison reruns all adapters with identical harness settings. See [completed results](results.html).

| Library | Version | Integration result |
|---|---|---|
| NM Dev | 1.2.6 | Passed; combined binary checksum recorded |
| Apache Commons Math | 4.0-SNAPSHOT (20261018.085925-1575) | Passed |
| Colt | 1.2.0 | Passed |
| Parallel Colt | 0.11.4 (upstream source tag) | Passed; added JPLASMA/core-lapack dependencies and one-thread setting |
| EJML | 0.46.1 | Passed |
| JAMA | 1.0.3 | Passed |
| jblas | 1.2.5 | Passed; bundled native libraries loaded on this Linux x86-64 environment |
| F2J LAPACK/BLAS | arpack_combined_all 0.1 | Passed; direct translated routines replace the unavailable legacy JLAPACK distribution |
| MTJ | 1.0.4 | Passed; forced pure-Java F2J backend |
| ojAlgo | 57.5.0 | Passed; ported to current R064Store/decomposition APIs |
| UJMP | 0.3.0 | Passed; core algorithms, external backends disabled |

## Distribution and backend distinctions

- The old `org.netlib:jlapack:0.8` dependency remains unavailable from Maven Central. The included replacement is explicitly identified as `net.sourceforge.f2j:arpack_combined_all:0.1`, using `Dgemm` and translated LAPACK routines. It is not presented as the same artifact or release.
- MTJ is measured with `F2jBLAS`, `F2jLAPACK` and `F2jARPACK` forced by JVM properties. It is not a native-accelerated MTJ result.
- jblas loads its bundled native implementation successfully. Native thread environment variables request one thread, but native worker counts have not been independently audited.
- UJMP's optional external Commons Math, EJML, jblas, MTJ, ojAlgo and Parallel Colt backends are disabled, preventing silent delegation to a different measured library.
- Parallel Colt, ojAlgo and UJMP receive explicit one-thread settings, in addition to the common one-worker JMH configuration.

The original four-library report remains available as a separate historical run. Its omissions were integration scope decisions, not evidence that other libraries could not run.


## Current version selection

The latest comparison upgrades the primary Commons adapter to **Commons Math 4.0-SNAPSHOT**, Parallel Colt to **0.11.4** from its upstream release tag, and NM Dev to the validated **1.2.6** release JAR. See the [complete version audit](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/dependency-audit/2026-10-18/README.md) for all eleven libraries, release-versus-snapshot distinctions and exact checksums. Version labels below are read from the selected run.
