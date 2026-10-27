# Benchmark methodology

This guide is maintained on the [benchmark website](https://nmltd.github.io/nmdev-examples/methodology.html).

Edit its [website source](site/methodology.md) and rebuild Pages; this file preserves existing links.


## Machine-capacity and Commons block comparisons (application 1.2.0)

The default application now runs **1,260 cases**, with eleven libraries and twelve implementation variants. Existing 1-thread baseline, 1/4-thread large, and 1-thread rectangular profiles remain. Additional `baseline-all-cores`, `large-all-cores`, and `rectangular-all-cores` profiles use every logical processor reported by the launcher JVM's `Runtime.availableProcessors()`. They remain separate even on machines with only one or four processors. JMH still uses one caller: the timed unit is one matrix operation, with library-internal parallelism permitted.

All-core requests set JVM processor visibility, `benchmark.threads`, and native BLAS/OpenMP thread environment variables to that count. This makes processors available; it does not force sequential algorithms to run in parallel. In particular, Commons row/block multiplication, JAMA, Colt, the current EJML `CommonOps_DDRM` adapter, and F2J stay sequential. Parallel Colt, ojAlgo and UJMP receive explicit worker limits; NM Dev uses JVM processor visibility; native jblas worker counts are not independently audited. This is a comparison of the configured APIs on the same hardware, including parallel versus sequential execution, rather than a claim of equal actual worker counts or exhaustive fastest-API selection.

`COMMONS` retains `Array2DRowRealMatrix`; `COMMONS_BLOCK` uses `BlockRealMatrix` from the exact same locked Commons Math 4 binary. Both are measured for every operation, including all large sizes and rectangular products. Every native input is constructed before timing; timed result allocation and decomposition-internal conversions remain included. Both variants receive repeated correctness and input-preservation checks. A block layout improves locality but does not itself provide parallelism.

`machine.json` records CPU model, physical-core information when detectable, JVM-visible logical processors, memory, OS/JDK, JVM options and Linux container limits/cpuset where available. Windows additionally records host physical cores and logical processors. All-core means the resources visible to the JVM; do not launch with an artificial `-XX:ActiveProcessorCount` cap when measuring machine capacity. VM/container restrictions and OS affinity can limit resources; worker requests are not measured utilization or exclusive core reservation.

Keep profiles and Commons variants separate in reporting. For matched operation/size cases, speedup is comparison time / candidate time, and time reduction is 100 × (comparison time − candidate time) / comparison time. Never pool the 1-thread, 4-thread and all-core profiles into one ranking. Published historical results remain unchanged until a new run is validated.

Build and run the new experiment from the repository root:

```powershell
mvn -s benchmark/settings.xml -gs benchmark/settings.xml -f benchmark/pom.xml package
java -jar benchmark/target/nmdev-benchmark-1.2.0.jar --output nmdev-benchmark-all-cores
```

Use this **new output directory**; 1.2.0 changes the measurement specification and cannot resume the older 660-case experiment. Keep its completed results and original JAR. Repeat the same new command with the same 1.2.0 JAR to resume the new experiment.
