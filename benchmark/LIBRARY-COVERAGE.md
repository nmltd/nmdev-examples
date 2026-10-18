# Library coverage and outstanding integration work

The initial measured run contains NM Dev plus Apache Commons Math, EJML and JAMA. **No other library in the list below has been demonstrated to be impossible to include.** A missing adapter is not a failed benchmark, and old release dates are not grounds for excluding a library.

Release metadata checked on 2026-10-18. “Latest” here means the available release in the listed Maven artifact line, not an assertion about every fork or unpublished development branch.

| Library | Available version / distribution | Initial-run status | Actual outstanding work or constraint |
|---|---|---|---|
| Apache Commons Math | 3.6.1 | Measured | Adapter and correctness checks completed. |
| Colt | 1.2.0 | Not ported | Migrate the old adapter to JMH and validate it. No incompatibility has been established. |
| Parallel Colt | 0.10.1 | Not ported | Migrate and validate the adapter; explicitly configure its concurrency rather than assuming a single JMH worker limits all internal threads. |
| EJML | 0.46.1 | Measured | Updated dense API adapter; correctness checks completed. |
| JAMA (listed as AMA) | 1.0.3 | Measured | Adapter and correctness checks completed. |
| jblas | 1.2.5 | Native runtime not tested | Check native-library loading on the actual OS/architecture and record BLAS thread configuration. A runtime failure has not been demonstrated. |
| JLAPACK | Old `org.netlib:jlapack:0.8` coordinate unresolved from Maven Central | Dependency migration needed | The old POM noted that this artifact was available only in the old private repository. Check an obtainable F2J/LAPACK distribution and its API compatibility; label its actual distribution/version. Do not treat this as evidence that LAPACK itself cannot be measured. |
| MTJ | 1.0.4 | Not ported | Integrate and explicitly identify the netlib backend. Its native and pure-Java fallback configurations must not be reported under an ambiguous shared label. |
| ojAlgo | 57.5.0 | Not ported | Rewrite the old `PrimitiveMatrix` adapter for the current API and validate equivalent outputs. No exclusion is justified solely by the old adapter being outdated. |
| UJMP | 0.3.0 | Not ported | Integrate, specify its backend/settings and validate materialized results. It supports the relevant matrix operations. |

## Evidence

- [Checked Maven Central metadata](results/2026-10-18/extra-library-metadata.json). Colt's older metadata lists versions without a release field; its available versions include 1.0.3 and 1.2.0.
- [jblas installation and native loading](https://github.com/jblas-project/jblas#install).
- [MTJ backend documentation](https://github.com/fommil/matrix-toolkits-java) and [netlib backend selection](https://github.com/fommil/netlib-java#customisation).
- [ojAlgo current project](https://github.com/optimatika/ojAlgo).
- [UJMP operations and decompositions](https://github.com/ujmp/universal-java-matrix-package).
- The original benchmark POM and adapters are identified by the commit in [Source-Provenance.md](Source-Provenance.md).

Only the explicitly measured adapters have passed this project's current correctness and timed-run checks. Availability of an artifact alone does not establish successful integration. Further adapters should be included after compiling, validating equivalent operations and recording any native backend and thread settings.
