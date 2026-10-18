# 2000 × 2000 comparison

The **one-thread profile is complete**: 20 measured cases across NM Dev, EJML, ojAlgo and jblas, covering multiplication, LU, Cholesky, QR and linear solve. All 40 JVM forks passed their full-size checks before timing.

- [Interactive one-thread chart](https://nmltd.github.io/nmdev-examples/benchmarks-2000-1thread.html)
- [One-thread tables and evidence](2026-10-18-2000-1thread/README.md)

The four-thread profile is running separately. Its results will be published after it completes. The request is for four compute threads; actual native worker counts are not independently audited.

Settings: two independent JVM forks per case, two 100 ms warmup targets and three 200 ms measurement targets per fork. Full operations complete even when longer than an iteration target. Every fork checks full-size residuals, repeated execution and input preservation outside timing.
