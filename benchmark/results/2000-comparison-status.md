# 2000 × 2000 comparison

The corrected harness is being extended with a `size2000` profile: NM Dev, EJML, ojAlgo and jblas; multiplication, LU, Cholesky, QR and linear solve. One-thread and four-thread profiles are measured separately, with two independent JVM forks per case, two 100 ms warmup targets and three 200 ms measurement targets per fork. Full operations are completed even when longer than an iteration target.

Every fork checks full-size numerical residuals, repeated execution and input preservation before timing. Existing results are preserved. Completed raw measurements, timing tables and interactive charts will be added after each run finishes.
