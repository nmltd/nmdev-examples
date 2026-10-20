# Rectangular multiplication — 2026-10-18-latest-rectangular

Shapes are m×k×n. All returned results are materialized, inputs are constructed outside timing, and every fork checks arithmetic twice and exact input preservation. This is a separate four-library track, not an eleven-library decomposition comparison.

Short diagnostic run on a shared VM. See the exact fork settings, affinity and thread requests in [environment.json](environment.json). Native jblas worker counts are not independently audited.

**Versions:** NM Dev 1.2.6, EJML 0.46.1, ojAlgo 57.5.0, jblas 1.2.5 (native). [Exact version labels](versions.json).

| Shape | NMDEV ms/op | EJML ms/op | OJALGO ms/op | JBLAS ms/op |
|---|---:|---:|---:|---:|
| 1024x256x64 | 18.2068 ± 1.2 | 6.89375 ± 0.75 | 6.33805 ± 4.7 | 2.83331 ± 3.4 |
| 128x512x64 | 4.27058 ± 0.39 | 1.71776 ± 0.24 | 1.92979 ± 2 | 0.451501 ± 0.044 |
| 255x257x129 | 9.49153 ± 1 | 2.96713 ± 0.25 | 3.14544 ± 0.65 | 0.996403 ± 0.051 |
| 512x128x256 | 20.4682 ± 2.9 | 5.73532 ± 0.28 | 5.78412 ± 0.43 | 2.01192 ± 0.66 |

Errors are JMH 99.9% confidence half-widths. Wide or overlapping intervals do not support fine rankings. JVM allocation counts do not include all native allocations.

[Raw JMH](jmh.json) · [Log](jmh.log) · [Summary and allocation](summary.csv) · [Artifacts](artifacts.json) · [Exact source snapshot](harness-source.zip) · [Fairness audit](../fairness-audit/README.md)
