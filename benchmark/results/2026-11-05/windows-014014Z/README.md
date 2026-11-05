# NM Dev 1.2.10 — Windows full benchmark

All 1,260 cases and six preflight checks passed. All 2,520 individual fork JSON checksums and 7,560 measured iteration means validated. No individual fork JSON files are missing. Completed 2026-11-05 at 01:40:14 UTC.

Intel Core i7-8750H, Windows 11, Oracle JDK 27, 12 JVM-visible logical processors. Requested thread profiles remain separate; native worker counts were not independently audited. Short measurements may have wide confidence intervals.

This run compares 11 libraries through 12 implementation variants, including Commons Math 4.0-beta1 Array2DRowRealMatrix and BlockRealMatrix. Earlier published runs used a Commons snapshot; changes between runs cannot be attributed solely to NM Dev.

NM Dev binary SHA-256: `a4284d16dcb83851c5486892a4425e7b5c6b9341895b484609449aee30d4f846`. The selected JAR hash matches the published 1.2.10 release; that verified identity supplies the public version label. The original provenance is retained. Downloaded binaries are excluded from the original archive, so their hashes cannot be recomputed from that archive. Raw numerical timings are unchanged; public records omit JVM executable paths and command-line arguments.

The original archive filename contains `pending` because Windows blocked replacement of the earlier ZIP. Its complete results and all fork files validate. A retained historical AccessDenied error records the earlier interrupted attempt; the final suite completed with zero failed cases.
