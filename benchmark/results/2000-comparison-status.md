# 2000 × 2000 comparison

Both profiles are complete: **40 measured cases** across NM Dev, EJML, ojAlgo and jblas, covering multiplication, LU, Cholesky, QR and linear solve. All 80 JVM forks passed their full-size checks before timing; the archives contain 240 measured iteration means.

- [Interactive one-thread chart](https://nmltd.github.io/nmdev-examples/benchmarks-2000-1thread.html) · [tables and evidence](2026-10-18-2000-1thread/README.md)
- [Interactive four-thread chart](https://nmltd.github.io/nmdev-examples/benchmarks-2000-4thread.html) · [tables and evidence](2026-10-18-2000-4thread/README.md)

The profiles run separately and request one or four compute threads. Actual native worker counts are not independently audited; standard EJML adapter calls do not request concurrent kernels. See each archive’s backend records.

Settings: two independent JVM forks per case, two 100 ms warmup targets and three 200 ms measurement targets per fork. Full operations complete even when longer than an iteration target. Every fork checks full-size residuals, repeated execution and input preservation outside timing. Full-size residual values were not recorded as a separate CSV; the baseline correctness CSV covers sizes 4/64/256.
