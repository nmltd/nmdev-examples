# NM Dev 1.2.9 — Windows full benchmark

All 1,260 cases and six preflight checks passed. All 1,260 combined case results and 7,560 measured iteration means validated. Of 2,520 expected individual fork JSON files, 2,519 are present and their checksums validated. The archive lacks `cases/large__1thread__OJALGO__multiplication__1024/fork-2.json`; its combined case samples remain available.

Intel Core i7-8750H, Windows 11, Oracle JDK 27, 12 JVM-visible logical processors. Requested thread profiles remain separate; native worker counts were not independently audited. Short measurements may have wide confidence intervals.

NM Dev binary SHA-256: `aa7384451b526273108e3d9e12ed1a428421004b45a77fd11f7306d6a709f154`. Downloaded binaries are excluded from the original archive, so the recorded binary hash cannot be recomputed from that archive. Raw timings are preserved; public records omit JVM executable paths and command-line arguments.
