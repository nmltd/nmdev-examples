# Benchmark Results

**[Open the interactive benchmark dashboard](https://nmltd.github.io/nmdev-examples/)**

Compare 13 dense linear-algebra operations across **11 libraries** at **64 × 64** and **256 × 256** matrix sizes. The 2026-10-18 run completed **286 measured cases**, using two JVM forks per case and ten measured iteration means per case.

## Read the charts

- Lower time ratios mean faster execution. NM Dev is the **1× reference**.
- A point at **0.5×** took half the NM Dev time; a point at **2×** took twice the time.
- The horizontal axis is logarithmic. Equal distances indicate equal multiplicative changes.
- Hover, tap or keyboard-focus a point for its library version/backend, operation, matrix size, mean milliseconds, 99.9% confidence interval and ratio to NM Dev. Press Escape to dismiss the tooltip.
- Confidence intervals describe timing uncertainty. The displayed ratio is a ratio of means, without a confidence interval for that ratio.

The dashboard runs on GitHub Pages. The images below are static previews; open the dashboard for interactive tooltips.

## 64 × 64 matrices

![Relative times for 64 by 64 matrices](https://raw.githubusercontent.com/nmltd/nmdev-examples/main/benchmark/results/2026-10-18-expanded/relative-times-64.svg)

## 256 × 256 matrices

![Relative times for 256 by 256 matrices](https://raw.githubusercontent.com/nmltd/nmdev-examples/main/benchmark/results/2026-10-18-expanded/relative-times-256.svg)

## Libraries and validation

NM Dev, EJML, Apache Commons Math, JAMA, Colt, Parallel Colt, jblas, F2J LAPACK, MTJ, ojAlgo and UJMP are included. **F2J LAPACK is a replacement distribution for the unavailable original JLAPACK artifact**, not the original JLAPACK binary. Exact versions, checksums and backend settings are documented in the report.

All **429 baseline correctness cases passed twice**, and **66 targeted pivot/sign checks passed**. Each measured fork also validates its selected result before timing. These checks cover the supplied inputs; they do not establish correctness for every possible matrix.

The run used a shared AMD EPYC VM, Java 17, one JMH worker, two forks, three 300 ms warmup iterations and five 500 ms measurement iterations per fork. Native jblas and Java implementations use different backend paths. Native worker counts were not independently audited. Results apply to this environment and these inputs; they are not universal performance rankings.

## Evidence and reproduction

- [Completed report, exact versions and timing tables](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-10-18-expanded/README.md)
- [Methodology and limitations](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/METHODOLOGY.md)
- [Library coverage and backend choices](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/LIBRARY-COVERAGE.md)
- [Raw JMH measurements and correctness evidence](https://github.com/nmltd/nmdev-examples/tree/main/benchmark/results/2026-10-18-expanded)
- [Run the benchmark yourself](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/README.md)
- [Original four-library run](https://github.com/nmltd/nmdev-examples/tree/main/benchmark/results/2026-10-18)
