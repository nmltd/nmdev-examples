# Library version audit — 18 October 2026

The current comparison uses the most recent official releases found for each library, plus the explicitly requested Apache Commons Math development snapshot. A development branch name is not treated as a published release. Maven metadata was checked together with upstream release tags where available; this catches releases missing from old Maven coordinates.

| Library | Earlier comparison | Selected version | Evidence / notes |
|---|---|---|---|
| NM Dev | 1.2.6-SNAPSHOT, older binary | **1.2.6**, latest validated obfuscated build | [Successful CI provenance and SHA-256](nmdev-validated.json); source 2bd4e00f42ee1e2c65a0fa1ea77489b8966e1ed3, numerical source matches current main (only a workflow documentation file differs). Downloaded only after validation completed. |
| Apache Commons Math | 3.6.1 | **4.0-SNAPSHOT** | [Official snapshot metadata](commons-math4-snapshot.xml); legacy module 4.0-20261018.085925-1575, including the 4.x core/exception modules. Central's released 4.x version is 4.0-beta1. The requested snapshot is explicitly labelled as development software. |
| Parallel Colt | 0.10.1 | **0.11.4** | [Official upstream tag](https://github.com/rwl/ParallelColt/tree/parallelcolt-0.11.4), commit 7c34364026680c1113f1a9586b9fb8c55080330c. Built unmodified numerical source with JDK 17.0.20, targeting Java 8. Old Central coordinate stops at 0.10.1. Master's 0.11.5-SNAPSHOT is an untagged development version, not a later release. |
| EJML | 0.46.1 | 0.46.1 | [Central metadata](ejml.xml), [upstream tags](ejml-tags.txt). Development branch advertises 0.46.2-SNAPSHOT; no newer release tag found. |
| ojAlgo | 57.5.0 | 57.5.0 | [Central metadata](ojalgo.xml), [upstream tags](ojalgo-tags.txt). Some older website pages still show earlier versions. |
| jblas | 1.2.5 | 1.2.5 | [Central metadata](jblas.xml), [upstream tags](jblas-tags.txt). Native backend explicitly labelled. |
| MTJ | 1.0.4 | 1.0.4 | [Central metadata](mtj.xml), [upstream tags](mtj-tags.txt). F2J backend explicitly selected. |
| UJMP | 0.3.0 | 0.3.0 | [Central metadata](ujmp.xml), [upstream tags](ujmp-tags.txt). Core algorithms selected; external backends disabled. |
| JAMA | 1.0.3 | 1.0.3 | [Central metadata](jama.xml); original [NIST distribution](https://math.nist.gov/javanumerics/jama/). |
| Colt | 1.2.0 | 1.2.0 | [Central metadata](colt.xml), whose version list is authoritative even though its latest/release fields are empty. |
| F2J LAPACK/BLAS distribution | 0.1 | 0.1 | [Central metadata](f2j.xml). This is arpack_combined_all, a replacement distribution; it is not represented as JLAPACK 0.8. |

## Reproducibility

[Raw metadata with retrieval times](metadata.json), [upstream tag audit](upstream-tags.json), the [dependency lockfile](../../dependencies.lock.tsv) and per-run artifact checksums identify exact inputs. Every snapshot module has its timestamped download URL and SHA-256 pinned in the lockfile. The primary Commons adapter imports math4 classes. Commons Math 3.6.1 remains only as a compatible transitive dependency of JTransforms; it is not the Commons implementation timed by the primary adapter.

Parallel Colt is compiled from its exact upstream release because that release is absent from its old Central coordinate. [The source builder](../../scripts/build-parallelcolt.py) verifies the commit and creates a deterministic archive. A different compiler build may produce a different binary hash; the checked run uses JDK 17.0.20. No optimization or algorithm modifications are applied to third-party source.

Version labels are stored per run in `versions.json`; regenerating historical results does not relabel older jars as new versions. The older completed fairness checkpoint remains a separate archive. The latest-version comparison reruns every library rather than splicing new-version results into the old measurements.

[Runtime class-origin verification](../../results/2026-10-18-fairness-audit/latest-class-origins.csv) confirms the expected JAR for every primary implementation.
