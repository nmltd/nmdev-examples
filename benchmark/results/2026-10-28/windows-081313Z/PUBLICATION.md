# Benchmark results — windows-081313Z

1,260 cases across 12 implementation variants; 2,520 independently warmed fork records; 7,560 measured iteration means. Lower ms/op is faster.

Intel(R) Core(TM) i7-8750H CPU @ 2.20GHz; 12 logical processors; 63.92 GiB physical RAM; Windows 11 amd64; Oracle Corporation JDK 27 (27+35-2325).

Two separately warmed JVM forks per case; two 100 ms warmups and three 200 ms measurements per fork; one JMH worker; requested compute threads are shown per profile. Fork heap: 256 MiB initial / 1 GiB maximum. CPU affinity was not set. Native worker counts were not independently audited. These short measurements may have wide confidence intervals; rankings are machine-specific.

The run measures **NM Dev 1.2.8**. Profiles are kept separate; requested compute thread counts do not imply every library executes in parallel.

## Validation

All 1,260 requested cases, available fork JSON checksums, raw samples, arithmetic means, summary values, JVM thread flags and machine identity fields passed validation. All 6 preflight processes exited successfully. The runner reports completion with zero failed cases. 1 individual fork JSON files are absent from the supplied ZIP; combined measurements remain available, but those missing files cannot be independently checksum-verified. Downloaded binaries are excluded, so their recorded hashes cannot be recomputed from this archive.

[Validation evidence](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-10-28/windows-081313Z/validation-report.json) · [Saved JMH measurements](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-10-28/windows-081313Z/jmh.json) · [Summary CSV](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-10-28/windows-081313Z/summary.csv) · [Hardware and archive digest](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-10-28/windows-081313Z/publication-format.json) · [Binary provenance](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-10-28/windows-081313Z/binary-provenance.json)

## Baseline — 1 requested compute thread(s)

[Interactive chart](benchmarks.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 64 | transpose | NM Dev 1.2.8 | 0.00525066519 | 0.000669290806 |
| 256 | transpose | NM Dev 1.2.8 | 0.191295137 | 0.0174173722 |
| 64 | addition | NM Dev 1.2.8 | 0.00359694126 | 0.000829868532 |
| 256 | addition | NM Dev 1.2.8 | 0.104152372 | 0.0174188628 |
| 64 | scale | NM Dev 1.2.8 | 0.00318359123 | 0.000768376845 |
| 256 | scale | NM Dev 1.2.8 | 0.09520395 | 0.0177161834 |
| 64 | multiplication | NM Dev 1.2.8 | 0.118181927 | 0.0346754738 |
| 256 | multiplication | NM Dev 1.2.8 | 6.32834071 | 1.16908633 |
| 64 | determinant | NM Dev 1.2.8 | 0.0653859386 | 0.0165178882 |
| 256 | determinant | NM Dev 1.2.8 | 7.59586119 | 2.16468576 |
| 64 | inverse | NM Dev 1.2.8 | 0.6591694 | 0.707473426 |
| 256 | inverse | NM Dev 1.2.8 | 28.2238265 | 2.27418401 |
| 64 | inverseSPD | NM Dev 1.2.8 | 0.520899854 | 0.237347451 |
| 256 | inverseSPD | NM Dev 1.2.8 | 23.9893519 | 0.684003901 |
| 64 | cholesky | NM Dev 1.2.8 | 0.0538602804 | 0.0333563903 |
| 256 | cholesky | NM Dev 1.2.8 | 2.94075967 | 0.202485965 |
| 64 | lu | NM Dev 1.2.8 | 0.073847646 | 0.0347329799 |
| 256 | lu | NM Dev 1.2.8 | 7.55459797 | 1.41935495 |
| 64 | qr | NM Dev 1.2.8 | 0.338758612 | 0.0590334991 |
| 256 | qr | NM Dev 1.2.8 | 26.9464197 | 5.65369871 |
| 64 | eigenSymmetric | NM Dev 1.2.8 | 4.5086227 | 1.62696953 |
| 256 | eigenSymmetric | NM Dev 1.2.8 | 70.7933778 | 44.2064311 |
| 64 | svd | NM Dev 1.2.8 | 3.1062663 | 2.31116474 |
| 256 | svd | NM Dev 1.2.8 | 101.557997 | 8.07223905 |
| 64 | solve | NM Dev 1.2.8 | 0.180202469 | 0.051513989 |
| 256 | solve | NM Dev 1.2.8 | 7.8979636 | 2.45486078 |
| 64 | transpose | EJML 0.46.1 | 0.00512761313 | 0.00128592733 |
| 256 | transpose | EJML 0.46.1 | 0.156202871 | 0.0253886727 |
| 64 | addition | EJML 0.46.1 | 0.00364776737 | 0.000356571233 |
| 256 | addition | EJML 0.46.1 | 0.0741727843 | 0.0281625756 |
| 64 | scale | EJML 0.46.1 | 0.00315939527 | 0.000245499407 |
| 256 | scale | EJML 0.46.1 | 0.0546143951 | 0.00708706034 |
| 64 | multiplication | EJML 0.46.1 | 0.0949414808 | 0.00831678924 |
| 256 | multiplication | EJML 0.46.1 | 3.85915394 | 0.272474624 |
| 64 | determinant | EJML 0.46.1 | 0.0816591962 | 0.0028314529 |
| 256 | determinant | EJML 0.46.1 | 5.3360534 | 0.607382467 |
| 64 | inverse | EJML 0.46.1 | 0.299769135 | 0.0225243215 |
| 256 | inverse | EJML 0.46.1 | 17.7485542 | 0.441408821 |
| 64 | inverseSPD | EJML 0.46.1 | 0.148693337 | 0.0275818423 |
| 256 | inverseSPD | EJML 0.46.1 | 9.85727786 | 0.562961814 |
| 64 | cholesky | EJML 0.46.1 | 0.044549625 | 0.00280874742 |
| 256 | cholesky | EJML 0.46.1 | 2.7814181 | 0.176646137 |
| 64 | lu | EJML 0.46.1 | 0.0928376077 | 0.0104706831 |
| 256 | lu | EJML 0.46.1 | 5.36973678 | 0.270235717 |
| 64 | qr | EJML 0.46.1 | 0.21726981 | 0.0272808268 |
| 256 | qr | EJML 0.46.1 | 10.5224902 | 0.946869994 |
| 64 | eigenSymmetric | EJML 0.46.1 | 0.634308681 | 0.437025968 |
| 256 | eigenSymmetric | EJML 0.46.1 | 22.1928576 | 1.13981729 |
| 64 | svd | EJML 0.46.1 | 0.97970678 | 0.196531308 |
| 256 | svd | EJML 0.46.1 | 40.0978161 | 1.42430656 |
| 64 | solve | EJML 0.46.1 | 0.0903370873 | 0.00766200201 |
| 256 | solve | EJML 0.46.1 | 5.33451266 | 0.295548095 |
| 64 | transpose | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.00622369907 | 0.00041449043 |
| 256 | transpose | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.152991654 | 0.0445414138 |
| 64 | addition | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.00545182833 | 0.00146417716 |
| 256 | addition | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.0861536724 | 0.00606242502 |
| 64 | scale | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.00429923258 | 0.000442848168 |
| 256 | scale | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.0790636258 | 0.0176098462 |
| 64 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.260633143 | 0.00953204637 |
| 256 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 19.7963295 | 2.31669402 |
| 64 | determinant | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.0920813876 | 0.00951485001 |
| 256 | determinant | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 6.29452202 | 0.587645637 |
| 64 | inverse | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.158900403 | 0.02335066 |
| 256 | inverse | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 10.322135 | 0.42089934 |
| 64 | inverseSPD | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.116176042 | 0.00944851879 |
| 256 | inverseSPD | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 5.93911511 | 0.28789581 |
| 64 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.0529114065 | 0.00992141904 |
| 256 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 2.36724268 | 0.971484588 |
| 64 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.101073029 | 0.0070626587 |
| 256 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 6.46887011 | 0.321423589 |
| 64 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.257923094 | 0.0139669821 |
| 256 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 19.3251424 | 0.978067892 |
| 64 | eigenSymmetric | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 1.14424887 | 1.56721809 |
| 256 | eigenSymmetric | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 54.7826583 | 4.93085477 |
| 64 | svd | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 21.4041971 | 56.1755046 |
| 256 | svd | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 226.582667 | 49.2910727 |
| 64 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.108397573 | 0.00940780357 |
| 256 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 6.51349162 | 0.325224174 |
| 64 | transpose | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.00547544986 | 0.000384237539 |
| 256 | transpose | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.100791698 | 0.0242009395 |
| 64 | addition | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.00396870793 | 0.000234489331 |
| 256 | addition | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.0817566812 | 0.00899525122 |
| 64 | scale | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.00351448351 | 0.0003846721 |
| 256 | scale | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.0727161418 | 0.0091284064 |
| 64 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.162692385 | 0.00511294067 |
| 256 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 9.20639872 | 0.403272864 |
| 64 | determinant | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.0921973678 | 0.00946167866 |
| 256 | determinant | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 8.09879107 | 4.72888759 |
| 64 | inverse | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.206282649 | 0.165908964 |
| 256 | inverse | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 11.5074829 | 3.16998404 |
| 64 | inverseSPD | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.11946132 | 0.0133493373 |
| 256 | inverseSPD | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 6.02408431 | 0.128704681 |
| 64 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.0599112128 | 0.0221011864 |
| 256 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 2.42373001 | 0.489166621 |
| 64 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.109989581 | 0.0454964255 |
| 256 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 6.44355278 | 0.373674492 |
| 64 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.259766811 | 0.0318530418 |
| 256 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 15.1373999 | 1.78712601 |
| 64 | eigenSymmetric | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 1.1707244 | 1.49760585 |
| 256 | eigenSymmetric | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 57.8615667 | 18.3898741 |
| 64 | svd | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 45.9919436 | 63.6858021 |
| 256 | svd | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 269.7454 | 208.334224 |
| 64 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.10910238 | 0.0126919235 |
| 256 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 7.16386239 | 3.81968586 |
| 64 | transpose | JAMA 1.0.3 | 0.00614335417 | 0.000934838992 |
| 256 | transpose | JAMA 1.0.3 | 0.148304457 | 0.0229831322 |
| 64 | addition | JAMA 1.0.3 | 0.00497837467 | 0.00102484257 |
| 256 | addition | JAMA 1.0.3 | 0.0906217737 | 0.0129437553 |
| 64 | scale | JAMA 1.0.3 | 0.00446418171 | 0.00109788512 |
| 256 | scale | JAMA 1.0.3 | 0.0745490789 | 0.0132122637 |
| 64 | multiplication | JAMA 1.0.3 | 0.192832891 | 0.0145692863 |
| 256 | multiplication | JAMA 1.0.3 | 15.9989526 | 0.495186787 |
| 64 | determinant | JAMA 1.0.3 | 0.0726342846 | 0.00434171921 |
| 256 | determinant | JAMA 1.0.3 | 4.76093167 | 0.156632391 |
| 64 | inverse | JAMA 1.0.3 | 0.234577417 | 0.0150263961 |
| 256 | inverse | JAMA 1.0.3 | 15.2695035 | 0.9023523 |
| 64 | inverseSPD | JAMA 1.0.3 | 0.360100992 | 0.0397314919 |
| 256 | inverseSPD | JAMA 1.0.3 | 30.4706452 | 1.72147204 |
| 64 | cholesky | JAMA 1.0.3 | 0.0418317412 | 0.00199422394 |
| 256 | cholesky | JAMA 1.0.3 | 2.53415136 | 0.222017064 |
| 64 | lu | JAMA 1.0.3 | 0.0891928638 | 0.023555977 |
| 256 | lu | JAMA 1.0.3 | 4.8800427 | 0.227718415 |
| 64 | qr | JAMA 1.0.3 | 0.428545283 | 0.0479737794 |
| 256 | qr | JAMA 1.0.3 | 42.4031133 | 5.45807421 |
| 64 | eigenSymmetric | JAMA 1.0.3 | 1.07236157 | 0.999865989 |
| 256 | eigenSymmetric | JAMA 1.0.3 | 62.1233375 | 4.40487529 |
| 64 | svd | JAMA 1.0.3 | 8.7113388 | 18.800385 |
| 256 | svd | JAMA 1.0.3 | 197.320233 | 31.498051 |
| 64 | solve | JAMA 1.0.3 | 0.0903719055 | 0.00624880874 |
| 256 | solve | JAMA 1.0.3 | 5.29949856 | 0.72434639 |
| 64 | transpose | Colt 1.2.0 | 0.0052590061 | 0.000406223 |
| 256 | transpose | Colt 1.2.0 | 0.151142536 | 0.00709685251 |
| 64 | addition | Colt 1.2.0 | 0.00655321275 | 0.00112745996 |
| 256 | addition | Colt 1.2.0 | 0.113034028 | 0.016629742 |
| 64 | scale | Colt 1.2.0 | 0.00515834183 | 0.00118093895 |
| 256 | scale | Colt 1.2.0 | 0.108770904 | 0.0431159698 |
| 64 | multiplication | Colt 1.2.0 | 0.28717316 | 0.121017112 |
| 256 | multiplication | Colt 1.2.0 | 31.7602341 | 8.85361949 |
| 64 | determinant | Colt 1.2.0 | 0.141739685 | 0.0755722974 |
| 256 | determinant | Colt 1.2.0 | 6.03283331 | 0.826467088 |
| 64 | inverse | Colt 1.2.0 | 0.631064624 | 0.878243071 |
| 256 | inverse | Colt 1.2.0 | 23.2433102 | 2.64627563 |
| 64 | inverseSPD | Colt 1.2.0 | 0.434558157 | 0.0418535854 |
| 256 | inverseSPD | Colt 1.2.0 | 44.8701864 | 21.4458499 |
| 64 | cholesky | Colt 1.2.0 | 0.0649898717 | 0.0281768678 |
| 256 | cholesky | Colt 1.2.0 | 3.3228449 | 1.93729324 |
| 64 | lu | Colt 1.2.0 | 0.234372106 | 0.657029735 |
| 256 | lu | Colt 1.2.0 | 5.7824072 | 0.676300869 |
| 64 | qr | Colt 1.2.0 | 0.482431006 | 0.0239644288 |
| 256 | qr | Colt 1.2.0 | 71.0266333 | 1.85854358 |
| 64 | eigenSymmetric | Colt 1.2.0 | 0.991842563 | 0.596321513 |
| 256 | eigenSymmetric | Colt 1.2.0 | 62.8956 | 2.50387788 |
| 64 | svd | Colt 1.2.0 | 7.16727767 | 7.00897236 |
| 256 | svd | Colt 1.2.0 | 230.19345 | 94.6816045 |
| 64 | solve | Colt 1.2.0 | 0.186206243 | 0.138927836 |
| 256 | solve | Colt 1.2.0 | 7.21890189 | 1.60755006 |
| 64 | transpose | Parallel Colt 0.11.4 (upstream tag build) | 0.00631007045 | 0.00354071376 |
| 256 | transpose | Parallel Colt 0.11.4 (upstream tag build) | 0.198934194 | 0.153730417 |
| 64 | addition | Parallel Colt 0.11.4 (upstream tag build) | 0.00665000329 | 0.00135610009 |
| 256 | addition | Parallel Colt 0.11.4 (upstream tag build) | 0.123059909 | 0.0275795054 |
| 64 | scale | Parallel Colt 0.11.4 (upstream tag build) | 0.00647376189 | 0.00209406557 |
| 256 | scale | Parallel Colt 0.11.4 (upstream tag build) | 0.089502078 | 0.0105501576 |
| 64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 0.334382933 | 0.201922385 |
| 256 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 30.359949 | 5.61712012 |
| 64 | determinant | Parallel Colt 0.11.4 (upstream tag build) | 0.165047746 | 0.0842871315 |
| 256 | determinant | Parallel Colt 0.11.4 (upstream tag build) | 7.06825853 | 0.815048956 |
| 64 | inverse | Parallel Colt 0.11.4 (upstream tag build) | 0.852672195 | 0.741659012 |
| 256 | inverse | Parallel Colt 0.11.4 (upstream tag build) | 21.6140546 | 2.44058654 |
| 64 | inverseSPD | Parallel Colt 0.11.4 (upstream tag build) | 0.585543418 | 0.195218857 |
| 256 | inverseSPD | Parallel Colt 0.11.4 (upstream tag build) | 19.016832 | 4.46014436 |
| 64 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 0.237759409 | 0.111771987 |
| 256 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 3.67939497 | 0.70159484 |
| 64 | lu | Parallel Colt 0.11.4 (upstream tag build) | 0.263916662 | 0.409853628 |
| 256 | lu | Parallel Colt 0.11.4 (upstream tag build) | 7.55798976 | 4.40187748 |
| 64 | qr | Parallel Colt 0.11.4 (upstream tag build) | 2.92516063 | 7.8306771 |
| 256 | qr | Parallel Colt 0.11.4 (upstream tag build) | 26.3254534 | 19.8581735 |
| 64 | eigenSymmetric | Parallel Colt 0.11.4 (upstream tag build) | 1.1103774 | 0.744041177 |
| 256 | eigenSymmetric | Parallel Colt 0.11.4 (upstream tag build) | 69.9575347 | 24.6892811 |
| 64 | svd | Parallel Colt 0.11.4 (upstream tag build) | 12.7910259 | 9.72800096 |
| 256 | svd | Parallel Colt 0.11.4 (upstream tag build) | 129.5914 | 47.7373394 |
| 64 | solve | Parallel Colt 0.11.4 (upstream tag build) | 0.352915556 | 0.218311353 |
| 256 | solve | Parallel Colt 0.11.4 (upstream tag build) | 9.39065523 | 5.39754687 |
| 64 | transpose | jblas 1.2.5 (native) | 0.00604714642 | 0.000947953219 |
| 256 | transpose | jblas 1.2.5 (native) | 0.168940095 | 0.063663514 |
| 64 | addition | jblas 1.2.5 (native) | 0.00331570278 | 0.000300669268 |
| 256 | addition | jblas 1.2.5 (native) | 0.0634829964 | 0.00344414223 |
| 64 | scale | jblas 1.2.5 (native) | 0.00305957755 | 9.45900311e-05 |
| 256 | scale | jblas 1.2.5 (native) | 0.0561102659 | 0.00941159977 |
| 64 | multiplication | jblas 1.2.5 (native) | 0.205070494 | 0.0330212199 |
| 256 | multiplication | jblas 1.2.5 (native) | 11.9957689 | 1.39068239 |
| 64 | determinant | jblas 1.2.5 (native) | 0.108703091 | 0.0138807953 |
| 256 | determinant | jblas 1.2.5 (native) | 4.7726077 | 0.34337086 |
| 64 | inverse | jblas 1.2.5 (native) | 0.268057393 | 0.0326552092 |
| 256 | inverse | jblas 1.2.5 (native) | 13.805784 | 1.05197356 |
| 64 | inverseSPD | jblas 1.2.5 (native) | 0.329258401 | 0.102634406 |
| 256 | inverseSPD | jblas 1.2.5 (native) | 19.1946894 | 1.04065999 |
| 64 | cholesky | jblas 1.2.5 (native) | 0.0542144015 | 0.00353640242 |
| 256 | cholesky | jblas 1.2.5 (native) | 3.04062933 | 0.124276745 |
| 64 | lu | jblas 1.2.5 (native) | 0.113962723 | 0.0417047265 |
| 256 | lu | jblas 1.2.5 (native) | 5.01455225 | 0.321084091 |
| 64 | qr | jblas 1.2.5 (native) | 0.473783914 | 0.154862853 |
| 256 | qr | jblas 1.2.5 (native) | 25.3773257 | 2.73261414 |
| 64 | eigenSymmetric | jblas 1.2.5 (native) | 0.888498817 | 0.0653252963 |
| 256 | eigenSymmetric | jblas 1.2.5 (native) | 30.6585929 | 3.7397467 |
| 64 | svd | jblas 1.2.5 (native) | 2.33360901 | 0.136872967 |
| 256 | svd | jblas 1.2.5 (native) | 145.668208 | 6.98607606 |
| 64 | solve | jblas 1.2.5 (native) | 0.0968630943 | 0.0109257886 |
| 256 | solve | jblas 1.2.5 (native) | 4.37540796 | 0.116471798 |
| 64 | transpose | F2J LAPACK/BLAS 0.1 | 0.0067183137 | 0.00108528796 |
| 256 | transpose | F2J LAPACK/BLAS 0.1 | 0.38363392 | 0.015752321 |
| 64 | addition | F2J LAPACK/BLAS 0.1 | 0.0173794704 | 0.00309540285 |
| 256 | addition | F2J LAPACK/BLAS 0.1 | 0.268055937 | 0.0193613209 |
| 64 | scale | F2J LAPACK/BLAS 0.1 | 0.00296466541 | 0.000328293349 |
| 256 | scale | F2J LAPACK/BLAS 0.1 | 0.0611587653 | 0.0230511142 |
| 64 | multiplication | F2J LAPACK/BLAS 0.1 | 0.117287345 | 0.062596291 |
| 256 | multiplication | F2J LAPACK/BLAS 0.1 | 4.08492244 | 0.439015801 |
| 64 | determinant | F2J LAPACK/BLAS 0.1 | 0.0465010237 | 0.00354425769 |
| 256 | determinant | F2J LAPACK/BLAS 0.1 | 2.58428866 | 0.806101052 |
| 64 | inverse | F2J LAPACK/BLAS 0.1 | 0.16788883 | 0.0562182149 |
| 256 | inverse | F2J LAPACK/BLAS 0.1 | 8.80108595 | 4.05548549 |
| 64 | inverseSPD | F2J LAPACK/BLAS 0.1 | 0.149677044 | 0.0248125914 |
| 256 | inverseSPD | F2J LAPACK/BLAS 0.1 | 7.37692198 | 5.83797223 |
| 64 | cholesky | F2J LAPACK/BLAS 0.1 | 0.0390071247 | 0.00917593609 |
| 256 | cholesky | F2J LAPACK/BLAS 0.1 | 1.30670793 | 0.0602904132 |
| 64 | lu | F2J LAPACK/BLAS 0.1 | 0.0630023879 | 0.00611978579 |
| 256 | lu | F2J LAPACK/BLAS 0.1 | 3.22521106 | 1.23647438 |
| 64 | qr | F2J LAPACK/BLAS 0.1 | 0.300717924 | 0.105063948 |
| 256 | qr | F2J LAPACK/BLAS 0.1 | 14.8827785 | 2.05297226 |
| 64 | eigenSymmetric | F2J LAPACK/BLAS 0.1 | 1.36127836 | 0.977851993 |
| 256 | eigenSymmetric | F2J LAPACK/BLAS 0.1 | 35.0901982 | 31.3925364 |
| 64 | svd | F2J LAPACK/BLAS 0.1 | 3.1060981 | 0.49091958 |
| 256 | svd | F2J LAPACK/BLAS 0.1 | 118.173783 | 16.0668712 |
| 64 | solve | F2J LAPACK/BLAS 0.1 | 0.053795892 | 0.00850209583 |
| 256 | solve | F2J LAPACK/BLAS 0.1 | 2.80001794 | 1.77225053 |
| 64 | transpose | MTJ 1.0.4 (F2J) | 0.0130973525 | 0.00335327664 |
| 256 | transpose | MTJ 1.0.4 (F2J) | 0.668634748 | 0.218458748 |
| 64 | addition | MTJ 1.0.4 (F2J) | 0.0266833669 | 0.00279766158 |
| 256 | addition | MTJ 1.0.4 (F2J) | 0.386645967 | 0.0060558978 |
| 64 | scale | MTJ 1.0.4 (F2J) | 0.021396279 | 0.0101672056 |
| 256 | scale | MTJ 1.0.4 (F2J) | 0.400242803 | 0.164846012 |
| 64 | multiplication | MTJ 1.0.4 (F2J) | 0.10419236 | 0.0268428374 |
| 256 | multiplication | MTJ 1.0.4 (F2J) | 5.48816202 | 0.643346062 |
| 64 | determinant | MTJ 1.0.4 (F2J) | 0.0701147298 | 0.0146618697 |
| 256 | determinant | MTJ 1.0.4 (F2J) | 2.85278565 | 1.32404822 |
| 64 | inverse | MTJ 1.0.4 (F2J) | 0.280506201 | 0.034423361 |
| 256 | inverse | MTJ 1.0.4 (F2J) | 14.6533017 | 5.32149167 |
| 64 | inverseSPD | MTJ 1.0.4 (F2J) | 0.399175701 | 0.0938908003 |
| 256 | inverseSPD | MTJ 1.0.4 (F2J) | 24.7582998 | 13.1168016 |
| 64 | cholesky | MTJ 1.0.4 (F2J) | 0.103784575 | 0.0117741815 |
| 256 | cholesky | MTJ 1.0.4 (F2J) | 4.69674016 | 3.3037995 |
| 64 | lu | MTJ 1.0.4 (F2J) | 0.0709612498 | 0.0227027547 |
| 256 | lu | MTJ 1.0.4 (F2J) | 2.58801817 | 0.213314863 |
| 64 | qr | MTJ 1.0.4 (F2J) | 0.317400031 | 0.0628161562 |
| 256 | qr | MTJ 1.0.4 (F2J) | 15.403945 | 2.27007312 |
| 64 | eigenSymmetric | MTJ 1.0.4 (F2J) | 2.96372653 | 3.86665374 |
| 256 | eigenSymmetric | MTJ 1.0.4 (F2J) | 33.0367521 | 25.0457886 |
| 64 | svd | MTJ 1.0.4 (F2J) | 12.2541675 | 10.5679644 |
| 256 | svd | MTJ 1.0.4 (F2J) | 94.5611069 | 63.6881782 |
| 64 | solve | MTJ 1.0.4 (F2J) | 0.0629219624 | 0.0313734541 |
| 256 | solve | MTJ 1.0.4 (F2J) | 2.37279804 | 0.223046375 |
| 64 | transpose | ojAlgo 57.5.0 | 0.00920683593 | 0.00284305779 |
| 256 | transpose | ojAlgo 57.5.0 | 0.169443046 | 0.0372698188 |
| 64 | addition | ojAlgo 57.5.0 | 0.00529980577 | 0.00354946599 |
| 256 | addition | ojAlgo 57.5.0 | 0.0751790078 | 0.0104676055 |
| 64 | scale | ojAlgo 57.5.0 | 0.00410254627 | 0.00110483516 |
| 256 | scale | ojAlgo 57.5.0 | 0.063032381 | 0.0139239739 |
| 64 | multiplication | ojAlgo 57.5.0 | 0.0873654887 | 0.00885837032 |
| 256 | multiplication | ojAlgo 57.5.0 | 3.912305 | 0.3988396 |
| 64 | determinant | ojAlgo 57.5.0 | 0.0574081872 | 0.00659392845 |
| 256 | determinant | ojAlgo 57.5.0 | 1.9296158 | 0.390361182 |
| 64 | inverse | ojAlgo 57.5.0 | 0.232106925 | 0.05625883 |
| 256 | inverse | ojAlgo 57.5.0 | 12.2377676 | 0.437401274 |
| 64 | inverseSPD | ojAlgo 57.5.0 | 0.187714719 | 0.231694431 |
| 256 | inverseSPD | ojAlgo 57.5.0 | 6.80250724 | 0.925004567 |
| 64 | cholesky | ojAlgo 57.5.0 | 0.0600971212 | 0.0458807153 |
| 256 | cholesky | ojAlgo 57.5.0 | 1.16817542 | 0.310412742 |
| 64 | lu | ojAlgo 57.5.0 | 0.0723617821 | 0.00695949152 |
| 256 | lu | ojAlgo 57.5.0 | 2.18866884 | 0.58338995 |
| 64 | qr | ojAlgo 57.5.0 | 0.250779389 | 0.0355790111 |
| 256 | qr | ojAlgo 57.5.0 | 8.67583325 | 2.57825879 |
| 64 | eigenSymmetric | ojAlgo 57.5.0 | 0.750042463 | 0.644588071 |
| 256 | eigenSymmetric | ojAlgo 57.5.0 | 25.5451454 | 14.931666 |
| 64 | svd | ojAlgo 57.5.0 | 1.73814811 | 1.1010006 |
| 256 | svd | ojAlgo 57.5.0 | 56.3952086 | 24.4479784 |
| 64 | solve | ojAlgo 57.5.0 | 0.0559607185 | 0.00556002268 |
| 256 | solve | ojAlgo 57.5.0 | 2.20260532 | 1.89045401 |
| 64 | transpose | UJMP 0.3.0 (core) | 0.0364371112 | 0.0295485338 |
| 256 | transpose | UJMP 0.3.0 (core) | 0.805490797 | 0.409975508 |
| 64 | addition | UJMP 0.3.0 (core) | 0.00895686994 | 0.00377579919 |
| 256 | addition | UJMP 0.3.0 (core) | 0.324593466 | 0.0790721832 |
| 64 | scale | UJMP 0.3.0 (core) | 0.00810053108 | 0.00385661488 |
| 256 | scale | UJMP 0.3.0 (core) | 0.293431607 | 0.0392267275 |
| 64 | multiplication | UJMP 0.3.0 (core) | 0.220721759 | 0.0465079733 |
| 256 | multiplication | UJMP 0.3.0 (core) | 25.1042836 | 8.61845816 |
| 64 | determinant | UJMP 0.3.0 (core) | 0.0711254634 | 0.00322332923 |
| 256 | determinant | UJMP 0.3.0 (core) | 6.00495175 | 5.61172848 |
| 64 | inverse | UJMP 0.3.0 (core) | 0.347150454 | 0.391210615 |
| 256 | inverse | UJMP 0.3.0 (core) | 14.9831619 | 0.539725088 |
| 64 | inverseSPD | UJMP 0.3.0 (core) | 0.387786941 | 0.0976412541 |
| 256 | inverseSPD | UJMP 0.3.0 (core) | 32.7443976 | 12.2608422 |
| 64 | cholesky | UJMP 0.3.0 (core) | 0.0574931017 | 0.0103928025 |
| 256 | cholesky | UJMP 0.3.0 (core) | 2.81484775 | 0.264580961 |
| 64 | lu | UJMP 0.3.0 (core) | 0.0893582396 | 0.00934893754 |
| 256 | lu | UJMP 0.3.0 (core) | 5.12917313 | 1.07611518 |
| 64 | qr | UJMP 0.3.0 (core) | 0.434187137 | 0.0246119945 |
| 256 | qr | UJMP 0.3.0 (core) | 41.1403333 | 1.78731684 |
| 64 | eigenSymmetric | UJMP 0.3.0 (core) | 0.976880001 | 0.601266822 |
| 256 | eigenSymmetric | UJMP 0.3.0 (core) | 77.2399264 | 53.6175766 |
| 64 | svd | UJMP 0.3.0 (core) | 8.10096996 | 3.72899167 |
| 256 | svd | UJMP 0.3.0 (core) | 224.693817 | 39.2520017 |
| 64 | solve | UJMP 0.3.0 (core) | 0.100316679 | 0.0158040186 |
| 256 | solve | UJMP 0.3.0 (core) | 5.15208998 | 0.127493757 |

## Large — 1 requested compute thread(s)

[Interactive chart](benchmarks-large-1thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 512 | multiplication | NM Dev 1.2.8 | 52.3419147 | 24.794393 |
| 1024 | multiplication | NM Dev 1.2.8 | 564.04505 | 154.422169 |
| 2000 | multiplication | NM Dev 1.2.8 | 2628.78738 | 615.503472 |
| 512 | lu | NM Dev 1.2.8 | 36.3053769 | 15.4853089 |
| 1024 | lu | NM Dev 1.2.8 | 269.116333 | 307.102362 |
| 2000 | lu | NM Dev 1.2.8 | 1851.39248 | 452.458658 |
| 512 | cholesky | NM Dev 1.2.8 | 23.2069926 | 1.44518266 |
| 1024 | cholesky | NM Dev 1.2.8 | 189.551242 | 14.9685428 |
| 2000 | cholesky | NM Dev 1.2.8 | 1396.65635 | 45.909141 |
| 512 | qr | NM Dev 1.2.8 | 137.871667 | 9.80451009 |
| 1024 | qr | NM Dev 1.2.8 | 1089.21275 | 103.25268 |
| 2000 | qr | NM Dev 1.2.8 | 7838.51237 | 465.705488 |
| 512 | solve | NM Dev 1.2.8 | 32.3787822 | 10.8289311 |
| 1024 | solve | NM Dev 1.2.8 | 214.382658 | 44.695083 |
| 2000 | solve | NM Dev 1.2.8 | 1969.41148 | 791.847398 |
| 512 | multiplication | EJML 0.46.1 | 35.3581225 | 17.3472331 |
| 1024 | multiplication | EJML 0.46.1 | 485.640117 | 102.031748 |
| 2000 | multiplication | EJML 0.46.1 | 3855.91538 | 438.953777 |
| 512 | lu | EJML 0.46.1 | 47.7192517 | 7.19040649 |
| 1024 | lu | EJML 0.46.1 | 473.487333 | 116.399056 |
| 2000 | lu | EJML 0.46.1 | 3434.64142 | 460.51837 |
| 512 | cholesky | EJML 0.46.1 | 23.1229222 | 0.98377972 |
| 1024 | cholesky | EJML 0.46.1 | 84.6774917 | 59.0629272 |
| 2000 | cholesky | EJML 0.46.1 | 556.675083 | 177.426677 |
| 512 | qr | EJML 0.46.1 | 87.0616556 | 12.5973526 |
| 1024 | qr | EJML 0.46.1 | 920.497167 | 463.212993 |
| 2000 | qr | EJML 0.46.1 | 7515.28418 | 1464.26103 |
| 512 | solve | EJML 0.46.1 | 53.5064083 | 5.22154761 |
| 1024 | solve | EJML 0.46.1 | 441.764683 | 25.4947042 |
| 2000 | solve | EJML 0.46.1 | 3425.94378 | 465.52493 |
| 512 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 273.893025 | 204.620378 |
| 1024 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 8349.35572 | 220.275295 |
| 2000 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 93503.5461 | 4478.86504 |
| 512 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 65.2612625 | 18.9526559 |
| 1024 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 1199.40905 | 93.9697491 |
| 2000 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 20178.4899 | 871.656385 |
| 512 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 16.8235274 | 1.09454955 |
| 1024 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 149.868517 | 46.4867396 |
| 2000 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 1502.10305 | 419.955943 |
| 512 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 137.851592 | 44.2511428 |
| 1024 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 1025.6733 | 240.447207 |
| 2000 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 8354.6701 | 2554.42442 |
| 512 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 78.3455333 | 41.1652586 |
| 1024 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 1322.58345 | 481.256321 |
| 2000 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 20387.053 | 1061.34107 |
| 512 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 81.1163972 | 42.2882953 |
| 1024 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 665.682117 | 62.0431423 |
| 2000 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 4751.49715 | 734.587219 |
| 512 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 67.9530042 | 30.5698632 |
| 1024 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 1251.65642 | 357.120215 |
| 2000 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 19969.1598 | 840.405755 |
| 512 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 16.18968 | 0.966347052 |
| 1024 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 132.96945 | 8.77376883 |
| 2000 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 1185.98242 | 73.5457259 |
| 512 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 111.644033 | 3.29982167 |
| 1024 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 978.501983 | 115.392802 |
| 2000 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 7448.9699 | 1752.63983 |
| 512 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 55.2777917 | 1.60066391 |
| 1024 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 1119.1818 | 117.620091 |
| 2000 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 19382.5968 | 425.511376 |
| 512 | multiplication | JAMA 1.0.3 | 143.272375 | 7.14644371 |
| 1024 | multiplication | JAMA 1.0.3 | 1231.80103 | 27.0899632 |
| 2000 | multiplication | JAMA 1.0.3 | 9129.34255 | 234.23795 |
| 512 | lu | JAMA 1.0.3 | 42.66032 | 2.41445977 |
| 1024 | lu | JAMA 1.0.3 | 409.213967 | 36.0386674 |
| 2000 | lu | JAMA 1.0.3 | 3304.28292 | 391.481293 |
| 512 | cholesky | JAMA 1.0.3 | 21.088545 | 0.618816638 |
| 1024 | cholesky | JAMA 1.0.3 | 192.190733 | 14.7167492 |
| 2000 | cholesky | JAMA 1.0.3 | 1528.3207 | 58.2310417 |
| 512 | qr | JAMA 1.0.3 | 424.5728 | 58.3336858 |
| 1024 | qr | JAMA 1.0.3 | 15955.5772 | 728.454026 |
| 2000 | qr | JAMA 1.0.3 | 195662.438 | 2034.68164 |
| 512 | solve | JAMA 1.0.3 | 42.65695 | 0.547301519 |
| 1024 | solve | JAMA 1.0.3 | 396.868883 | 21.2754377 |
| 2000 | solve | JAMA 1.0.3 | 3188.54175 | 73.7118087 |
| 512 | multiplication | Colt 1.2.0 | 287.211817 | 7.55057241 |
| 1024 | multiplication | Colt 1.2.0 | 5997.22173 | 101.982559 |
| 2000 | multiplication | Colt 1.2.0 | 41721.4329 | 111.733083 |
| 512 | lu | Colt 1.2.0 | 41.5799833 | 2.80246545 |
| 1024 | lu | Colt 1.2.0 | 406.102917 | 40.1712382 |
| 2000 | lu | Colt 1.2.0 | 3275.7412 | 1270.12125 |
| 512 | cholesky | Colt 1.2.0 | 20.766855 | 0.343551279 |
| 1024 | cholesky | Colt 1.2.0 | 169.3979 | 5.93041996 |
| 2000 | cholesky | Colt 1.2.0 | 1322.99867 | 79.2266509 |
| 512 | qr | Colt 1.2.0 | 670.3032 | 16.4466656 |
| 1024 | qr | Colt 1.2.0 | 10811.1359 | 533.013802 |
| 2000 | qr | Colt 1.2.0 | 122871.339 | 516.361838 |
| 512 | solve | Colt 1.2.0 | 44.1742233 | 5.18083172 |
| 1024 | solve | Colt 1.2.0 | 404.853683 | 55.9443059 |
| 2000 | solve | Colt 1.2.0 | 2912.32287 | 220.698198 |
| 512 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 274.2553 | 159.167735 |
| 1024 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 6360.62305 | 321.182256 |
| 2000 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 41899.729 | 57.3332869 |
| 512 | lu | Parallel Colt 0.11.4 (upstream tag build) | 46.7529167 | 1.86350867 |
| 1024 | lu | Parallel Colt 0.11.4 (upstream tag build) | 405.799033 | 19.4896913 |
| 2000 | lu | Parallel Colt 0.11.4 (upstream tag build) | 3037.39412 | 25.4544547 |
| 512 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 23.6681481 | 0.832531643 |
| 1024 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 186.742558 | 5.67732358 |
| 2000 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 1291.43247 | 26.1347859 |
| 512 | qr | Parallel Colt 0.11.4 (upstream tag build) | 152.226925 | 7.02382631 |
| 1024 | qr | Parallel Colt 0.11.4 (upstream tag build) | 1168.16215 | 54.6912758 |
| 2000 | qr | Parallel Colt 0.11.4 (upstream tag build) | 8230.87565 | 193.894983 |
| 512 | solve | Parallel Colt 0.11.4 (upstream tag build) | 51.5822117 | 5.61656472 |
| 1024 | solve | Parallel Colt 0.11.4 (upstream tag build) | 432.084133 | 52.0192749 |
| 2000 | solve | Parallel Colt 0.11.4 (upstream tag build) | 3206.17192 | 97.7450994 |
| 512 | multiplication | jblas 1.2.5 (native) | 89.5955556 | 5.74020282 |
| 1024 | multiplication | jblas 1.2.5 (native) | 814.26775 | 9.60389823 |
| 2000 | multiplication | jblas 1.2.5 (native) | 6186.58272 | 85.9115799 |
| 512 | lu | jblas 1.2.5 (native) | 33.5468139 | 1.11931101 |
| 1024 | lu | jblas 1.2.5 (native) | 264.176717 | 3.94050149 |
| 2000 | lu | jblas 1.2.5 (native) | 1807.31837 | 117.234278 |
| 512 | cholesky | jblas 1.2.5 (native) | 24.9910613 | 2.15448965 |
| 1024 | cholesky | jblas 1.2.5 (native) | 200.581242 | 1.79762055 |
| 2000 | cholesky | jblas 1.2.5 (native) | 1426.10635 | 23.0744159 |
| 512 | qr | jblas 1.2.5 (native) | 187.14515 | 2.7259679 |
| 1024 | qr | jblas 1.2.5 (native) | 1542.36853 | 37.7014544 |
| 2000 | qr | jblas 1.2.5 (native) | 11642.3462 | 180.724014 |
| 512 | solve | jblas 1.2.5 (native) | 31.8563595 | 3.06424065 |
| 1024 | solve | jblas 1.2.5 (native) | 239.046967 | 7.70055853 |
| 2000 | solve | jblas 1.2.5 (native) | 1722.05058 | 35.7023072 |
| 512 | multiplication | F2J LAPACK/BLAS 0.1 | 30.0257381 | 1.79534371 |
| 1024 | multiplication | F2J LAPACK/BLAS 0.1 | 406.888783 | 29.8508222 |
| 2000 | multiplication | F2J LAPACK/BLAS 0.1 | 3522.48097 | 265.402774 |
| 512 | lu | F2J LAPACK/BLAS 0.1 | 14.2710371 | 0.493351886 |
| 1024 | lu | F2J LAPACK/BLAS 0.1 | 116.896308 | 2.26347438 |
| 2000 | lu | F2J LAPACK/BLAS 0.1 | 718.705 | 60.1876653 |
| 512 | cholesky | F2J LAPACK/BLAS 0.1 | 8.30826503 | 1.09091771 |
| 1024 | cholesky | F2J LAPACK/BLAS 0.1 | 64.2667583 | 5.76036638 |
| 2000 | cholesky | F2J LAPACK/BLAS 0.1 | 567.204383 | 15.4879425 |
| 512 | qr | F2J LAPACK/BLAS 0.1 | 111.041733 | 2.10197794 |
| 1024 | qr | F2J LAPACK/BLAS 0.1 | 936.883083 | 6.94907175 |
| 2000 | qr | F2J LAPACK/BLAS 0.1 | 7141.59345 | 147.505333 |
| 512 | solve | F2J LAPACK/BLAS 0.1 | 13.1440438 | 0.582356132 |
| 1024 | solve | F2J LAPACK/BLAS 0.1 | 101.293019 | 2.70558427 |
| 2000 | solve | F2J LAPACK/BLAS 0.1 | 664.533233 | 85.7268754 |
| 512 | multiplication | MTJ 1.0.4 (F2J) | 29.7369405 | 1.56186112 |
| 1024 | multiplication | MTJ 1.0.4 (F2J) | 408.137633 | 25.6738403 |
| 2000 | multiplication | MTJ 1.0.4 (F2J) | 3551.64008 | 302.319841 |
| 512 | lu | MTJ 1.0.4 (F2J) | 13.5636136 | 0.925637486 |
| 1024 | lu | MTJ 1.0.4 (F2J) | 103.467908 | 3.68081123 |
| 2000 | lu | MTJ 1.0.4 (F2J) | 686.895767 | 37.5894849 |
| 512 | cholesky | MTJ 1.0.4 (F2J) | 24.8471829 | 0.536528708 |
| 1024 | cholesky | MTJ 1.0.4 (F2J) | 194.275683 | 2.96608891 |
| 2000 | cholesky | MTJ 1.0.4 (F2J) | 1395.6663 | 24.4821085 |
| 512 | qr | MTJ 1.0.4 (F2J) | 111.187333 | 0.743210859 |
| 1024 | qr | MTJ 1.0.4 (F2J) | 938.039533 | 26.235396 |
| 2000 | qr | MTJ 1.0.4 (F2J) | 7152.78102 | 171.939919 |
| 512 | solve | MTJ 1.0.4 (F2J) | 13.1062271 | 0.859409369 |
| 1024 | solve | MTJ 1.0.4 (F2J) | 100.487519 | 8.13432506 |
| 2000 | solve | MTJ 1.0.4 (F2J) | 672.407817 | 52.9132435 |
| 512 | multiplication | ojAlgo 57.5.0 | 27.8304307 | 2.11432644 |
| 1024 | multiplication | ojAlgo 57.5.0 | 381.23435 | 39.1549485 |
| 2000 | multiplication | ojAlgo 57.5.0 | 3386.51738 | 263.123915 |
| 512 | lu | ojAlgo 57.5.0 | 13.3893933 | 0.32771503 |
| 1024 | lu | ojAlgo 57.5.0 | 131.281817 | 13.5443045 |
| 2000 | lu | ojAlgo 57.5.0 | 1519.68512 | 255.005312 |
| 512 | cholesky | ojAlgo 57.5.0 | 7.00559425 | 0.202633957 |
| 1024 | cholesky | ojAlgo 57.5.0 | 66.350775 | 13.6387856 |
| 2000 | cholesky | ojAlgo 57.5.0 | 676.115333 | 22.1338039 |
| 512 | qr | ojAlgo 57.5.0 | 53.82475 | 1.13228426 |
| 1024 | qr | ojAlgo 57.5.0 | 568.187583 | 38.8773476 |
| 2000 | qr | ojAlgo 57.5.0 | 5062.7461 | 400.723378 |
| 512 | solve | ojAlgo 57.5.0 | 12.7280096 | 1.19966414 |
| 1024 | solve | ojAlgo 57.5.0 | 126.460217 | 13.6017298 |
| 2000 | solve | ojAlgo 57.5.0 | 1449.64612 | 179.534761 |
| 512 | multiplication | UJMP 0.3.0 (core) | 229.37185 | 7.05352084 |
| 1024 | multiplication | UJMP 0.3.0 (core) | 7647.9992 | 147.506318 |
| 2000 | multiplication | UJMP 0.3.0 (core) | 86822.9083 | 691.560866 |
| 512 | lu | UJMP 0.3.0 (core) | 43.2771733 | 2.36607512 |
| 1024 | lu | UJMP 0.3.0 (core) | 395.228667 | 8.16831237 |
| 2000 | lu | UJMP 0.3.0 (core) | 3151.23937 | 139.561952 |
| 512 | cholesky | UJMP 0.3.0 (core) | 21.52875 | 0.874523458 |
| 1024 | cholesky | UJMP 0.3.0 (core) | 193.901117 | 7.19440392 |
| 2000 | cholesky | UJMP 0.3.0 (core) | 1538.14665 | 68.7076027 |
| 512 | qr | UJMP 0.3.0 (core) | 431.602683 | 155.781866 |
| 1024 | qr | UJMP 0.3.0 (core) | 15747.2975 | 125.502995 |
| 2000 | qr | UJMP 0.3.0 (core) | 195549.534 | 2779.92079 |
| 512 | solve | UJMP 0.3.0 (core) | 43.7592533 | 4.9507459 |
| 1024 | solve | UJMP 0.3.0 (core) | 407.418633 | 46.7231894 |
| 2000 | solve | UJMP 0.3.0 (core) | 3197.51895 | 106.273644 |

## Large — 4 requested compute thread(s)

[Interactive chart](benchmarks-large-4thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 512 | multiplication | NM Dev 1.2.8 | 13.2922314 | 1.98907346 |
| 1024 | multiplication | NM Dev 1.2.8 | 108.589619 | 46.6449269 |
| 2000 | multiplication | NM Dev 1.2.8 | 626.1094 | 11.5896214 |
| 512 | lu | NM Dev 1.2.8 | 24.3746954 | 1.26699452 |
| 1024 | lu | NM Dev 1.2.8 | 126.3606 | 59.8569373 |
| 2000 | lu | NM Dev 1.2.8 | 1141.07507 | 64.3001203 |
| 512 | cholesky | NM Dev 1.2.8 | 11.4863078 | 0.602366854 |
| 1024 | cholesky | NM Dev 1.2.8 | 71.5058833 | 1.3240402 |
| 2000 | cholesky | NM Dev 1.2.8 | 441.624033 | 4.97493989 |
| 512 | qr | NM Dev 1.2.8 | 80.7969667 | 3.44364854 |
| 1024 | qr | NM Dev 1.2.8 | 401.573133 | 39.2236946 |
| 2000 | qr | NM Dev 1.2.8 | 2883.64185 | 321.450954 |
| 512 | solve | NM Dev 1.2.8 | 26.0264637 | 4.37135221 |
| 1024 | solve | NM Dev 1.2.8 | 131.5219 | 42.9094447 |
| 2000 | solve | NM Dev 1.2.8 | 1400.18048 | 269.885164 |
| 512 | multiplication | EJML 0.46.1 | 26.8490667 | 2.41462326 |
| 1024 | multiplication | EJML 0.46.1 | 380.4448 | 27.2385747 |
| 2000 | multiplication | EJML 0.46.1 | 3446.20908 | 333.293229 |
| 512 | lu | EJML 0.46.1 | 44.1824767 | 3.35446424 |
| 1024 | lu | EJML 0.46.1 | 427.192633 | 34.2236693 |
| 2000 | lu | EJML 0.46.1 | 3084.9139 | 68.653558 |
| 512 | cholesky | EJML 0.46.1 | 22.7312944 | 0.504845794 |
| 1024 | cholesky | EJML 0.46.1 | 72.4488611 | 2.55009004 |
| 2000 | cholesky | EJML 0.46.1 | 496.989017 | 20.9190022 |
| 512 | qr | EJML 0.46.1 | 78.1183833 | 5.45174442 |
| 1024 | qr | EJML 0.46.1 | 748.977917 | 25.4490786 |
| 2000 | qr | EJML 0.46.1 | 6063.801 | 505.076792 |
| 512 | solve | EJML 0.46.1 | 44.02945 | 2.44640689 |
| 1024 | solve | EJML 0.46.1 | 425.09405 | 30.1408107 |
| 2000 | solve | EJML 0.46.1 | 3131.35323 | 48.6682678 |
| 512 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 193.8293 | 23.2153812 |
| 1024 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 8088.05927 | 2368.3821 |
| 2000 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 89591.3633 | 4214.80837 |
| 512 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 56.7036333 | 5.2024518 |
| 1024 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 1038.488 | 340.668391 |
| 2000 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 19457.8315 | 1269.13564 |
| 512 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 16.1223551 | 0.824846253 |
| 1024 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 135.713158 | 14.7082541 |
| 2000 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 1197.63698 | 188.633727 |
| 512 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 120.0617 | 30.6849188 |
| 1024 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 1097.99437 | 268.900278 |
| 2000 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 8509.25077 | 1699.7175 |
| 512 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 56.1396875 | 3.27108771 |
| 1024 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 956.024533 | 300.709473 |
| 2000 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 19111.8289 | 955.460845 |
| 512 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 70.2864889 | 0.428112337 |
| 1024 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 603.074567 | 14.5032483 |
| 2000 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 4440.12745 | 69.5372075 |
| 512 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 55.8545083 | 1.61468462 |
| 1024 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 1137.13365 | 154.190157 |
| 2000 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 19896.0546 | 286.542394 |
| 512 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 15.9611668 | 1.15585529 |
| 1024 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 134.830892 | 22.4692525 |
| 2000 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 1219.72688 | 74.072989 |
| 512 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 115.137525 | 13.1927071 |
| 1024 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 936.593817 | 55.1546305 |
| 2000 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 7152.15665 | 204.467582 |
| 512 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 55.8230458 | 1.893704 |
| 1024 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 1034.0222 | 422.768739 |
| 2000 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 19302.28 | 293.401479 |
| 512 | multiplication | JAMA 1.0.3 | 144.926092 | 11.4045456 |
| 1024 | multiplication | JAMA 1.0.3 | 1231.74892 | 21.3421243 |
| 2000 | multiplication | JAMA 1.0.3 | 9132.57237 | 134.277189 |
| 512 | lu | JAMA 1.0.3 | 42.7348133 | 1.60610976 |
| 1024 | lu | JAMA 1.0.3 | 406.2744 | 43.1515445 |
| 2000 | lu | JAMA 1.0.3 | 3336.79575 | 382.370668 |
| 512 | cholesky | JAMA 1.0.3 | 21.1635683 | 0.465135823 |
| 1024 | cholesky | JAMA 1.0.3 | 196.148292 | 21.7595088 |
| 2000 | cholesky | JAMA 1.0.3 | 1544.9011 | 109.420571 |
| 512 | qr | JAMA 1.0.3 | 498.681233 | 38.2106073 |
| 1024 | qr | JAMA 1.0.3 | 15675.8858 | 420.59063 |
| 2000 | qr | JAMA 1.0.3 | 199489.743 | 8211.47298 |
| 512 | solve | JAMA 1.0.3 | 43.8289733 | 0.907846939 |
| 1024 | solve | JAMA 1.0.3 | 413.661233 | 8.58888868 |
| 2000 | solve | JAMA 1.0.3 | 3257.66425 | 32.9789291 |
| 512 | multiplication | Colt 1.2.0 | 287.568467 | 17.8148139 |
| 1024 | multiplication | Colt 1.2.0 | 5971.52918 | 1019.80652 |
| 2000 | multiplication | Colt 1.2.0 | 41329.5537 | 775.634853 |
| 512 | lu | Colt 1.2.0 | 41.2085983 | 3.48744267 |
| 1024 | lu | Colt 1.2.0 | 363.054583 | 28.8403831 |
| 2000 | lu | Colt 1.2.0 | 2921.03062 | 254.825266 |
| 512 | cholesky | Colt 1.2.0 | 20.67584 | 0.578408385 |
| 1024 | cholesky | Colt 1.2.0 | 168.68135 | 8.34935933 |
| 2000 | cholesky | Colt 1.2.0 | 1325.91587 | 68.7078885 |
| 512 | qr | Colt 1.2.0 | 666.23565 | 35.5723249 |
| 1024 | qr | Colt 1.2.0 | 10443.2113 | 1156.23145 |
| 2000 | qr | Colt 1.2.0 | 123825.444 | 2161.50603 |
| 512 | solve | Colt 1.2.0 | 44.6960233 | 4.3519182 |
| 1024 | solve | Colt 1.2.0 | 425.7039 | 36.4545541 |
| 2000 | solve | Colt 1.2.0 | 2949.92323 | 80.1118516 |
| 512 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 71.0730722 | 7.16865265 |
| 1024 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 1363.36927 | 73.3608197 |
| 2000 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 11239.8638 | 653.715073 |
| 512 | lu | Parallel Colt 0.11.4 (upstream tag build) | 41.3897333 | 1.97522509 |
| 1024 | lu | Parallel Colt 0.11.4 (upstream tag build) | 347.671033 | 12.9429171 |
| 2000 | lu | Parallel Colt 0.11.4 (upstream tag build) | 2925.98945 | 335.908194 |
| 512 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 14.8351814 | 1.68373632 |
| 1024 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 71.2790208 | 5.10650024 |
| 2000 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 391.634867 | 26.8230168 |
| 512 | qr | Parallel Colt 0.11.4 (upstream tag build) | 66.1119167 | 3.60220119 |
| 1024 | qr | Parallel Colt 0.11.4 (upstream tag build) | 376.754583 | 36.3724519 |
| 2000 | qr | Parallel Colt 0.11.4 (upstream tag build) | 2361.26003 | 89.6739618 |
| 512 | solve | Parallel Colt 0.11.4 (upstream tag build) | 45.1547733 | 2.8753096 |
| 1024 | solve | Parallel Colt 0.11.4 (upstream tag build) | 385.441167 | 12.8894203 |
| 2000 | solve | Parallel Colt 0.11.4 (upstream tag build) | 2900.80812 | 145.848005 |
| 512 | multiplication | jblas 1.2.5 (native) | 90.1805833 | 11.9340829 |
| 1024 | multiplication | jblas 1.2.5 (native) | 820.465933 | 19.3893881 |
| 2000 | multiplication | jblas 1.2.5 (native) | 6217.9349 | 150.176443 |
| 512 | lu | jblas 1.2.5 (native) | 33.3788817 | 1.14513995 |
| 1024 | lu | jblas 1.2.5 (native) | 264.654033 | 4.18221406 |
| 2000 | lu | jblas 1.2.5 (native) | 1779.4275 | 53.7950194 |
| 512 | cholesky | jblas 1.2.5 (native) | 24.332263 | 0.817091494 |
| 1024 | cholesky | jblas 1.2.5 (native) | 199.30405 | 1.36887975 |
| 2000 | cholesky | jblas 1.2.5 (native) | 1425.3983 | 28.0150071 |
| 512 | qr | jblas 1.2.5 (native) | 186.514683 | 2.2208609 |
| 1024 | qr | jblas 1.2.5 (native) | 1543.10068 | 47.0247463 |
| 2000 | qr | jblas 1.2.5 (native) | 11550.8003 | 192.119528 |
| 512 | solve | jblas 1.2.5 (native) | 31.5204167 | 1.62070581 |
| 1024 | solve | jblas 1.2.5 (native) | 238.8003 | 5.2984448 |
| 2000 | solve | jblas 1.2.5 (native) | 1685.21673 | 41.7951894 |
| 512 | multiplication | F2J LAPACK/BLAS 0.1 | 30.3751143 | 2.24570363 |
| 1024 | multiplication | F2J LAPACK/BLAS 0.1 | 416.76365 | 45.7661752 |
| 2000 | multiplication | F2J LAPACK/BLAS 0.1 | 3523.3817 | 229.431061 |
| 512 | lu | F2J LAPACK/BLAS 0.1 | 14.3354263 | 0.520314652 |
| 1024 | lu | F2J LAPACK/BLAS 0.1 | 116.887958 | 6.69527323 |
| 2000 | lu | F2J LAPACK/BLAS 0.1 | 719.802917 | 54.7614237 |
| 512 | cholesky | F2J LAPACK/BLAS 0.1 | 8.27478875 | 0.497446643 |
| 1024 | cholesky | F2J LAPACK/BLAS 0.1 | 64.6708875 | 5.94488944 |
| 2000 | cholesky | F2J LAPACK/BLAS 0.1 | 577.4562 | 44.6319962 |
| 512 | qr | F2J LAPACK/BLAS 0.1 | 110.996233 | 1.05492762 |
| 1024 | qr | F2J LAPACK/BLAS 0.1 | 936.100783 | 15.1390882 |
| 2000 | qr | F2J LAPACK/BLAS 0.1 | 7135.86785 | 172.404803 |
| 512 | solve | F2J LAPACK/BLAS 0.1 | 12.9070146 | 0.372832611 |
| 1024 | solve | F2J LAPACK/BLAS 0.1 | 102.469125 | 6.73946194 |
| 2000 | solve | F2J LAPACK/BLAS 0.1 | 683.16625 | 111.06424 |
| 512 | multiplication | MTJ 1.0.4 (F2J) | 33.6424698 | 4.90453257 |
| 1024 | multiplication | MTJ 1.0.4 (F2J) | 406.703683 | 23.4378571 |
| 2000 | multiplication | MTJ 1.0.4 (F2J) | 3596.43747 | 150.361448 |
| 512 | lu | MTJ 1.0.4 (F2J) | 13.5172189 | 0.780435535 |
| 1024 | lu | MTJ 1.0.4 (F2J) | 102.878236 | 6.53274788 |
| 2000 | lu | MTJ 1.0.4 (F2J) | 659.036183 | 44.6013741 |
| 512 | cholesky | MTJ 1.0.4 (F2J) | 24.7623074 | 0.415675303 |
| 1024 | cholesky | MTJ 1.0.4 (F2J) | 194.802383 | 2.08420409 |
| 2000 | cholesky | MTJ 1.0.4 (F2J) | 1405.10678 | 13.7309139 |
| 512 | qr | MTJ 1.0.4 (F2J) | 111.592917 | 1.41402241 |
| 1024 | qr | MTJ 1.0.4 (F2J) | 931.9916 | 9.99010526 |
| 2000 | qr | MTJ 1.0.4 (F2J) | 7190.66218 | 41.1952511 |
| 512 | solve | MTJ 1.0.4 (F2J) | 12.9102448 | 0.555718533 |
| 1024 | solve | MTJ 1.0.4 (F2J) | 100.449806 | 9.40464935 |
| 2000 | solve | MTJ 1.0.4 (F2J) | 667.684233 | 54.1214364 |
| 512 | multiplication | ojAlgo 57.5.0 | 8.10847271 | 0.916642119 |
| 1024 | multiplication | ojAlgo 57.5.0 | 101.197386 | 2.07382764 |
| 2000 | multiplication | ojAlgo 57.5.0 | 957.230967 | 63.52931 |
| 512 | lu | ojAlgo 57.5.0 | 13.2493917 | 0.17267487 |
| 1024 | lu | ojAlgo 57.5.0 | 101.080358 | 32.876643 |
| 2000 | lu | ojAlgo 57.5.0 | 1301.56065 | 354.245622 |
| 512 | cholesky | ojAlgo 57.5.0 | 22.3463254 | 2.61130575 |
| 1024 | cholesky | ojAlgo 57.5.0 | 73.1315389 | 2.66711825 |
| 2000 | cholesky | ojAlgo 57.5.0 | 569.62815 | 40.6767374 |
| 512 | qr | ojAlgo 57.5.0 | 55.2273833 | 6.87044077 |
| 1024 | qr | ojAlgo 57.5.0 | 420.337033 | 8.17979073 |
| 2000 | qr | ojAlgo 57.5.0 | 4217.9305 | 245.379904 |
| 512 | solve | ojAlgo 57.5.0 | 12.4408762 | 0.749371036 |
| 1024 | solve | ojAlgo 57.5.0 | 103.036619 | 28.6380919 |
| 2000 | solve | ojAlgo 57.5.0 | 1290.98585 | 257.030382 |
| 512 | multiplication | UJMP 0.3.0 (core) | 70.9614569 | 22.0779107 |
| 1024 | multiplication | UJMP 0.3.0 (core) | 1914.18285 | 51.4005083 |
| 2000 | multiplication | UJMP 0.3.0 (core) | 24166.7704 | 785.976766 |
| 512 | lu | UJMP 0.3.0 (core) | 42.7775433 | 1.02716572 |
| 1024 | lu | UJMP 0.3.0 (core) | 406.1583 | 50.3683848 |
| 2000 | lu | UJMP 0.3.0 (core) | 3352.2355 | 486.08349 |
| 512 | cholesky | UJMP 0.3.0 (core) | 21.699125 | 0.647720669 |
| 1024 | cholesky | UJMP 0.3.0 (core) | 192.265108 | 3.1378057 |
| 2000 | cholesky | UJMP 0.3.0 (core) | 1581.78355 | 103.542399 |
| 512 | qr | UJMP 0.3.0 (core) | 485.432417 | 20.9043723 |
| 1024 | qr | UJMP 0.3.0 (core) | 15917.8222 | 117.887145 |
| 2000 | qr | UJMP 0.3.0 (core) | 200248.339 | 2765.78395 |
| 512 | solve | UJMP 0.3.0 (core) | 43.10069 | 0.814032344 |
| 1024 | solve | UJMP 0.3.0 (core) | 406.730633 | 33.2864084 |
| 2000 | solve | UJMP 0.3.0 (core) | 3204.38773 | 25.8985662 |

## Rectangular — 1 requested compute thread(s)

[Interactive chart](benchmarks-rectangular-1thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 128x512x64 | multiplication | NM Dev 1.2.8 | 1.45073821 | 0.0919390836 |
| 512x128x256 | multiplication | NM Dev 1.2.8 | 5.74752657 | 0.449437208 |
| 1024x256x64 | multiplication | NM Dev 1.2.8 | 5.68897896 | 0.10623708 |
| 255x257x129 | multiplication | NM Dev 1.2.8 | 3.08382785 | 1.07193909 |
| 128x512x64 | multiplication | EJML 0.46.1 | 1.39205812 | 0.115815277 |
| 512x128x256 | multiplication | EJML 0.46.1 | 3.27619217 | 0.554235266 |
| 1024x256x64 | multiplication | EJML 0.46.1 | 5.84539759 | 0.286644872 |
| 255x257x129 | multiplication | EJML 0.46.1 | 2.29935293 | 0.104185161 |
| 128x512x64 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 5.01760926 | 0.346309069 |
| 512x128x256 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 17.3957764 | 0.278792157 |
| 1024x256x64 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 17.7207972 | 0.518653944 |
| 255x257x129 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 9.37990152 | 0.131057858 |
| 128x512x64 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 2.17390835 | 0.0290454284 |
| 512x128x256 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 9.07313653 | 0.269387923 |
| 1024x256x64 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 8.72283901 | 0.0426298366 |
| 255x257x129 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 4.72617829 | 0.0421794159 |
| 128x512x64 | multiplication | JAMA 1.0.3 | 4.14013231 | 0.0305895603 |
| 512x128x256 | multiplication | JAMA 1.0.3 | 12.7901396 | 0.137623433 |
| 1024x256x64 | multiplication | JAMA 1.0.3 | 15.1518988 | 0.0422314739 |
| 255x257x129 | multiplication | JAMA 1.0.3 | 7.61390185 | 0.046231751 |
| 128x512x64 | multiplication | Colt 1.2.0 | 4.07313137 | 0.577552346 |
| 512x128x256 | multiplication | Colt 1.2.0 | 16.6504521 | 0.719702456 |
| 1024x256x64 | multiplication | Colt 1.2.0 | 12.962624 | 0.496536214 |
| 255x257x129 | multiplication | Colt 1.2.0 | 6.60239355 | 0.236580811 |
| 128x512x64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 4.57629705 | 0.156861148 |
| 512x128x256 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 18.2414117 | 1.31512741 |
| 1024x256x64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 12.3888917 | 0.475491638 |
| 255x257x129 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 6.08112852 | 0.0343552675 |
| 128x512x64 | multiplication | jblas 1.2.5 (native) | 2.82577097 | 0.107509646 |
| 512x128x256 | multiplication | jblas 1.2.5 (native) | 11.7989556 | 1.03099071 |
| 1024x256x64 | multiplication | jblas 1.2.5 (native) | 12.183021 | 1.2763041 |
| 255x257x129 | multiplication | jblas 1.2.5 (native) | 5.47849775 | 0.0460842187 |
| 128x512x64 | multiplication | F2J LAPACK/BLAS 0.1 | 1.33399694 | 0.0654636447 |
| 512x128x256 | multiplication | F2J LAPACK/BLAS 0.1 | 3.67678974 | 0.130140874 |
| 1024x256x64 | multiplication | F2J LAPACK/BLAS 0.1 | 3.71505134 | 0.297971971 |
| 255x257x129 | multiplication | F2J LAPACK/BLAS 0.1 | 2.28501396 | 0.0552399455 |
| 128x512x64 | multiplication | MTJ 1.0.4 (F2J) | 1.32447007 | 0.0136875727 |
| 512x128x256 | multiplication | MTJ 1.0.4 (F2J) | 3.63100027 | 0.128243166 |
| 1024x256x64 | multiplication | MTJ 1.0.4 (F2J) | 3.77188392 | 0.673579927 |
| 255x257x129 | multiplication | MTJ 1.0.4 (F2J) | 2.2940426 | 0.113891991 |
| 128x512x64 | multiplication | ojAlgo 57.5.0 | 1.15924774 | 0.0357141956 |
| 512x128x256 | multiplication | ojAlgo 57.5.0 | 3.47454505 | 0.18880128 |
| 1024x256x64 | multiplication | ojAlgo 57.5.0 | 3.56657764 | 0.39653852 |
| 255x257x129 | multiplication | ojAlgo 57.5.0 | 2.1943222 | 0.185583836 |
| 128x512x64 | multiplication | UJMP 0.3.0 (core) | 5.49336306 | 0.0700156153 |
| 512x128x256 | multiplication | UJMP 0.3.0 (core) | 24.7387926 | 0.430440404 |
| 1024x256x64 | multiplication | UJMP 0.3.0 (core) | 33.6278917 | 2.12315152 |
| 255x257x129 | multiplication | UJMP 0.3.0 (core) | 9.62749098 | 0.213815477 |

## Baseline-All-Cores — 12 requested compute thread(s)

[Interactive chart](benchmarks-baseline-all-cores-12thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 64 | transpose | NM Dev 1.2.8 | 0.00493894593 | 0.000594168933 |
| 256 | transpose | NM Dev 1.2.8 | 0.11866387 | 0.0151285698 |
| 64 | addition | NM Dev 1.2.8 | 0.00338831616 | 0.000479700263 |
| 256 | addition | NM Dev 1.2.8 | 0.0810256375 | 0.0157356982 |
| 64 | scale | NM Dev 1.2.8 | 0.00290578073 | 0.000740564076 |
| 256 | scale | NM Dev 1.2.8 | 0.0755172405 | 0.0169715833 |
| 64 | multiplication | NM Dev 1.2.8 | 0.0904672778 | 0.005891034 |
| 256 | multiplication | NM Dev 1.2.8 | 1.69746318 | 0.440021706 |
| 64 | determinant | NM Dev 1.2.8 | 0.0567787215 | 0.00930647792 |
| 256 | determinant | NM Dev 1.2.8 | 7.37167459 | 2.50618839 |
| 64 | inverse | NM Dev 1.2.8 | 0.265243887 | 0.147842978 |
| 256 | inverse | NM Dev 1.2.8 | 9.97094214 | 2.39836171 |
| 64 | inverseSPD | NM Dev 1.2.8 | 0.231109925 | 0.0358835534 |
| 256 | inverseSPD | NM Dev 1.2.8 | 5.53786877 | 0.516315838 |
| 64 | cholesky | NM Dev 1.2.8 | 0.043335124 | 0.00418581378 |
| 256 | cholesky | NM Dev 1.2.8 | 2.64493854 | 0.0988686736 |
| 64 | lu | NM Dev 1.2.8 | 0.0571571053 | 0.00909408761 |
| 256 | lu | NM Dev 1.2.8 | 6.95183346 | 1.42041999 |
| 64 | qr | NM Dev 1.2.8 | 0.297321498 | 0.0152602883 |
| 256 | qr | NM Dev 1.2.8 | 19.9355003 | 3.20554366 |
| 64 | eigenSymmetric | NM Dev 1.2.8 | 3.53985185 | 2.0731936 |
| 256 | eigenSymmetric | NM Dev 1.2.8 | 41.39658 | 6.82309846 |
| 64 | svd | NM Dev 1.2.8 | 2.19361951 | 0.544408329 |
| 256 | svd | NM Dev 1.2.8 | 65.7015903 | 5.22610907 |
| 64 | solve | NM Dev 1.2.8 | 0.197316032 | 0.0503516942 |
| 256 | solve | NM Dev 1.2.8 | 7.5541247 | 2.47751397 |
| 64 | transpose | EJML 0.46.1 | 0.00472275739 | 0.000454379615 |
| 256 | transpose | EJML 0.46.1 | 0.139632535 | 0.0104791189 |
| 64 | addition | EJML 0.46.1 | 0.00315149495 | 0.000183688308 |
| 256 | addition | EJML 0.46.1 | 0.0570675439 | 0.00593720897 |
| 64 | scale | EJML 0.46.1 | 0.00295269029 | 0.00071887949 |
| 256 | scale | EJML 0.46.1 | 0.0484935236 | 0.00356720959 |
| 64 | multiplication | EJML 0.46.1 | 0.0881860097 | 0.0062070776 |
| 256 | multiplication | EJML 0.46.1 | 3.55153963 | 0.35222545 |
| 64 | determinant | EJML 0.46.1 | 0.0800395933 | 0.00749432529 |
| 256 | determinant | EJML 0.46.1 | 5.14695475 | 0.227333169 |
| 64 | inverse | EJML 0.46.1 | 0.291994721 | 0.0112650477 |
| 256 | inverse | EJML 0.46.1 | 17.2129611 | 0.0267136653 |
| 64 | inverseSPD | EJML 0.46.1 | 0.136269518 | 0.0128110865 |
| 256 | inverseSPD | EJML 0.46.1 | 9.44334052 | 0.53700088 |
| 64 | cholesky | EJML 0.46.1 | 0.042960642 | 0.000491909917 |
| 256 | cholesky | EJML 0.46.1 | 2.68972536 | 0.0886833986 |
| 64 | lu | EJML 0.46.1 | 0.088902178 | 0.00660739153 |
| 256 | lu | EJML 0.46.1 | 5.22738297 | 0.123611229 |
| 64 | qr | EJML 0.46.1 | 0.208041547 | 0.0172866619 |
| 256 | qr | EJML 0.46.1 | 10.1307504 | 0.49410053 |
| 64 | eigenSymmetric | EJML 0.46.1 | 0.544304071 | 0.0783538039 |
| 256 | eigenSymmetric | EJML 0.46.1 | 21.5955933 | 0.723729904 |
| 64 | svd | EJML 0.46.1 | 0.880395846 | 0.0782915123 |
| 256 | svd | EJML 0.46.1 | 38.5664167 | 0.666376758 |
| 64 | solve | EJML 0.46.1 | 0.0871396294 | 0.0059894546 |
| 256 | solve | EJML 0.46.1 | 5.23197299 | 0.210090087 |
| 64 | transpose | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.00608007698 | 0.00214352489 |
| 256 | transpose | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.140805094 | 0.0256649099 |
| 64 | addition | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.00460060148 | 0.00114034247 |
| 256 | addition | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.0739806615 | 0.00238893365 |
| 64 | scale | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.00423230585 | 0.00117814577 |
| 256 | scale | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.0657531386 | 0.00154185105 |
| 64 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.252838748 | 0.0132013771 |
| 256 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 19.8578727 | 0.323504081 |
| 64 | determinant | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.0870681815 | 0.00203677214 |
| 256 | determinant | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 6.05804465 | 0.255174041 |
| 64 | inverse | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.155556263 | 0.00919003726 |
| 256 | inverse | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 9.93586429 | 0.298568381 |
| 64 | inverseSPD | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.110371607 | 0.00663133402 |
| 256 | inverseSPD | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 5.55209956 | 0.338567282 |
| 64 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.0491803135 | 0.00311225624 |
| 256 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 2.07333537 | 0.0832535698 |
| 64 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.0959766513 | 0.00483094577 |
| 256 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 6.29200338 | 0.226712089 |
| 64 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.249107755 | 0.00866567999 |
| 256 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 14.4514074 | 0.565813907 |
| 64 | eigenSymmetric | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.72823131 | 0.0498936399 |
| 256 | eigenSymmetric | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 52.7822375 | 3.4269433 |
| 64 | svd | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 27.3105846 | 48.8615608 |
| 256 | svd | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 216.632783 | 31.7885502 |
| 64 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 0.10271077 | 0.00168495979 |
| 256 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 6.28973221 | 0.293475677 |
| 64 | transpose | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.00573256953 | 0.00161053439 |
| 256 | transpose | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.0978543549 | 0.0239074932 |
| 64 | addition | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.0037417963 | 0.00066155816 |
| 256 | addition | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.0741751548 | 0.0145922334 |
| 64 | scale | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.00338381105 | 0.000697774396 |
| 256 | scale | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.0669327763 | 0.0115373132 |
| 64 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.157588397 | 0.00541905104 |
| 256 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 8.85553052 | 0.579505253 |
| 64 | determinant | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.0878009725 | 0.00207753615 |
| 256 | determinant | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 5.99450658 | 0.154968247 |
| 64 | inverse | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.153110207 | 0.00812710201 |
| 256 | inverse | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 10.0101211 | 0.411176542 |
| 64 | inverseSPD | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.109128901 | 0.00637402567 |
| 256 | inverseSPD | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 5.58853622 | 0.862304673 |
| 64 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.0495119154 | 0.00195548448 |
| 256 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 2.09151139 | 0.102971786 |
| 64 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.0979959991 | 0.00442165859 |
| 256 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 6.2754913 | 0.0671499864 |
| 64 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.251167387 | 0.0130594615 |
| 256 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 14.443798 | 0.638792838 |
| 64 | eigenSymmetric | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.762532841 | 0.128621251 |
| 256 | eigenSymmetric | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 52.2236583 | 1.77618175 |
| 64 | svd | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 16.5139047 | 55.453657 |
| 256 | svd | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 224.772608 | 33.6141421 |
| 64 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 0.105137797 | 0.00917580357 |
| 256 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 6.36652361 | 0.204252332 |
| 64 | transpose | JAMA 1.0.3 | 0.00616517915 | 0.00216271408 |
| 256 | transpose | JAMA 1.0.3 | 0.137872619 | 0.00944172431 |
| 64 | addition | JAMA 1.0.3 | 0.00429132896 | 0.000236857035 |
| 256 | addition | JAMA 1.0.3 | 0.073037585 | 0.00306986675 |
| 64 | scale | JAMA 1.0.3 | 0.00389966218 | 0.000314790031 |
| 256 | scale | JAMA 1.0.3 | 0.0655184884 | 0.00112057137 |
| 64 | multiplication | JAMA 1.0.3 | 0.182115682 | 0.00552255986 |
| 256 | multiplication | JAMA 1.0.3 | 15.2205307 | 0.452843015 |
| 64 | determinant | JAMA 1.0.3 | 0.0685917563 | 0.00365062139 |
| 256 | determinant | JAMA 1.0.3 | 4.63271402 | 0.156778617 |
| 64 | inverse | JAMA 1.0.3 | 0.229581014 | 0.00895985725 |
| 256 | inverse | JAMA 1.0.3 | 14.532742 | 0.44241398 |
| 64 | inverseSPD | JAMA 1.0.3 | 0.340280807 | 0.0189862429 |
| 256 | inverseSPD | JAMA 1.0.3 | 31.0326262 | 4.16426282 |
| 64 | cholesky | JAMA 1.0.3 | 0.0400089685 | 0.00169793189 |
| 256 | cholesky | JAMA 1.0.3 | 2.3955184 | 0.118656395 |
| 64 | lu | JAMA 1.0.3 | 0.0819019347 | 0.00412458187 |
| 256 | lu | JAMA 1.0.3 | 4.86536831 | 0.374442707 |
| 64 | qr | JAMA 1.0.3 | 0.413138157 | 0.0204428437 |
| 256 | qr | JAMA 1.0.3 | 41.6787567 | 2.43914424 |
| 64 | eigenSymmetric | JAMA 1.0.3 | 0.799649266 | 0.0334446743 |
| 256 | eigenSymmetric | JAMA 1.0.3 | 64.6000097 | 10.8515428 |
| 64 | svd | JAMA 1.0.3 | 7.34809819 | 4.79372037 |
| 256 | svd | JAMA 1.0.3 | 216.326758 | 68.9656244 |
| 64 | solve | JAMA 1.0.3 | 0.0869098751 | 0.00631941876 |
| 256 | solve | JAMA 1.0.3 | 4.86030138 | 0.153346776 |
| 64 | transpose | Colt 1.2.0 | 0.00527176464 | 0.000958869391 |
| 256 | transpose | Colt 1.2.0 | 0.142892609 | 0.0113835006 |
| 64 | addition | Colt 1.2.0 | 0.00619280976 | 0.000515738773 |
| 256 | addition | Colt 1.2.0 | 0.107777069 | 0.0114024304 |
| 64 | scale | Colt 1.2.0 | 0.0047746326 | 0.000211048085 |
| 256 | scale | Colt 1.2.0 | 0.0765407558 | 0.00583948581 |
| 64 | multiplication | Colt 1.2.0 | 0.224876727 | 0.00936929313 |
| 256 | multiplication | Colt 1.2.0 | 26.6282312 | 3.39785219 |
| 64 | determinant | Colt 1.2.0 | 0.104115683 | 0.0065431354 |
| 256 | determinant | Colt 1.2.0 | 5.41321818 | 0.357817195 |
| 64 | inverse | Colt 1.2.0 | 0.359029639 | 0.0589973829 |
| 256 | inverse | Colt 1.2.0 | 18.8117414 | 2.65109869 |
| 64 | inverseSPD | Colt 1.2.0 | 0.396541125 | 0.0316574674 |
| 256 | inverseSPD | Colt 1.2.0 | 35.7101833 | 3.53748865 |
| 64 | cholesky | Colt 1.2.0 | 0.052381386 | 0.0036279451 |
| 256 | cholesky | Colt 1.2.0 | 2.57631419 | 0.203400832 |
| 64 | lu | Colt 1.2.0 | 0.119660225 | 0.0105036892 |
| 256 | lu | Colt 1.2.0 | 5.66336701 | 0.32783106 |
| 64 | qr | Colt 1.2.0 | 0.454710359 | 0.0545326607 |
| 256 | qr | Colt 1.2.0 | 68.8154556 | 2.49906506 |
| 64 | eigenSymmetric | Colt 1.2.0 | 0.822867703 | 0.0489300105 |
| 256 | eigenSymmetric | Colt 1.2.0 | 69.981575 | 11.6849759 |
| 64 | svd | Colt 1.2.0 | 6.78684903 | 2.04172216 |
| 256 | svd | Colt 1.2.0 | 209.519283 | 31.0667722 |
| 64 | solve | Colt 1.2.0 | 0.153233448 | 0.0181867938 |
| 256 | solve | Colt 1.2.0 | 6.21110044 | 0.265215812 |
| 64 | transpose | Parallel Colt 0.11.4 (upstream tag build) | 0.00519122859 | 0.000763192436 |
| 256 | transpose | Parallel Colt 0.11.4 (upstream tag build) | 0.115274493 | 0.0128648796 |
| 64 | addition | Parallel Colt 0.11.4 (upstream tag build) | 0.00630801471 | 0.00068649672 |
| 256 | addition | Parallel Colt 0.11.4 (upstream tag build) | 0.113521928 | 0.0107391051 |
| 64 | scale | Parallel Colt 0.11.4 (upstream tag build) | 0.00554795317 | 0.00236335469 |
| 256 | scale | Parallel Colt 0.11.4 (upstream tag build) | 0.106762078 | 0.0146442735 |
| 64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 0.122146548 | 0.0124915544 |
| 256 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 7.04261743 | 0.282044235 |
| 64 | determinant | Parallel Colt 0.11.4 (upstream tag build) | 0.130966824 | 0.0161808361 |
| 256 | determinant | Parallel Colt 0.11.4 (upstream tag build) | 5.58191251 | 0.123238852 |
| 64 | inverse | Parallel Colt 0.11.4 (upstream tag build) | 0.377691896 | 0.0435327278 |
| 256 | inverse | Parallel Colt 0.11.4 (upstream tag build) | 18.9244303 | 0.671156633 |
| 64 | inverseSPD | Parallel Colt 0.11.4 (upstream tag build) | 3.45229765 | 0.733132741 |
| 256 | inverseSPD | Parallel Colt 0.11.4 (upstream tag build) | 17.3555326 | 1.87511969 |
| 64 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 1.34181912 | 0.255767757 |
| 256 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 4.72822737 | 1.10832976 |
| 64 | lu | Parallel Colt 0.11.4 (upstream tag build) | 0.14374083 | 0.036219079 |
| 256 | lu | Parallel Colt 0.11.4 (upstream tag build) | 5.78863475 | 0.196135275 |
| 64 | qr | Parallel Colt 0.11.4 (upstream tag build) | 3.07513597 | 0.613990742 |
| 256 | qr | Parallel Colt 0.11.4 (upstream tag build) | 18.1103486 | 1.72531453 |
| 64 | eigenSymmetric | Parallel Colt 0.11.4 (upstream tag build) | 0.825331532 | 0.0802660144 |
| 256 | eigenSymmetric | Parallel Colt 0.11.4 (upstream tag build) | 63.9359889 | 10.2661987 |
| 64 | svd | Parallel Colt 0.11.4 (upstream tag build) | 2.4911303 | 1.07075595 |
| 256 | svd | Parallel Colt 0.11.4 (upstream tag build) | 78.2451278 | 7.83832513 |
| 64 | solve | Parallel Colt 0.11.4 (upstream tag build) | 0.180198455 | 0.0512143674 |
| 256 | solve | Parallel Colt 0.11.4 (upstream tag build) | 6.59148704 | 0.280865841 |
| 64 | transpose | jblas 1.2.5 (native) | 0.00590515013 | 0.00097163507 |
| 256 | transpose | jblas 1.2.5 (native) | 0.144632766 | 0.0154591884 |
| 64 | addition | jblas 1.2.5 (native) | 0.00313635087 | 0.000242972245 |
| 256 | addition | jblas 1.2.5 (native) | 0.0569796472 | 0.00574253924 |
| 64 | scale | jblas 1.2.5 (native) | 0.00292640739 | 0.000889060089 |
| 256 | scale | jblas 1.2.5 (native) | 0.0490676627 | 0.00417833882 |
| 64 | multiplication | jblas 1.2.5 (native) | 0.176929316 | 0.0131464176 |
| 256 | multiplication | jblas 1.2.5 (native) | 11.2977624 | 0.978546529 |
| 64 | determinant | jblas 1.2.5 (native) | 0.105016732 | 0.0102442073 |
| 256 | determinant | jblas 1.2.5 (native) | 4.600007 | 0.0800666225 |
| 64 | inverse | jblas 1.2.5 (native) | 0.274051734 | 0.0417290509 |
| 256 | inverse | jblas 1.2.5 (native) | 13.464237 | 0.501335254 |
| 64 | inverseSPD | jblas 1.2.5 (native) | 0.297297735 | 0.012141818 |
| 256 | inverseSPD | jblas 1.2.5 (native) | 19.0265318 | 0.722056356 |
| 64 | cholesky | jblas 1.2.5 (native) | 0.051879522 | 0.00251525161 |
| 256 | cholesky | jblas 1.2.5 (native) | 2.85833117 | 0.108735871 |
| 64 | lu | jblas 1.2.5 (native) | 0.10634483 | 0.00663420494 |
| 256 | lu | jblas 1.2.5 (native) | 4.63362462 | 0.0612925977 |
| 64 | qr | jblas 1.2.5 (native) | 0.432484254 | 0.00946587256 |
| 256 | qr | jblas 1.2.5 (native) | 24.7706942 | 1.04127922 |
| 64 | eigenSymmetric | jblas 1.2.5 (native) | 0.816497969 | 0.0430682851 |
| 256 | eigenSymmetric | jblas 1.2.5 (native) | 28.5531574 | 0.92752379 |
| 64 | svd | jblas 1.2.5 (native) | 2.23337852 | 0.116597498 |
| 256 | svd | jblas 1.2.5 (native) | 140.814092 | 2.51799753 |
| 64 | solve | jblas 1.2.5 (native) | 0.0894818528 | 0.00614972409 |
| 256 | solve | jblas 1.2.5 (native) | 4.07153476 | 0.18512329 |
| 64 | transpose | F2J LAPACK/BLAS 0.1 | 0.00677714494 | 0.00188212219 |
| 256 | transpose | F2J LAPACK/BLAS 0.1 | 0.369553474 | 0.0173764023 |
| 64 | addition | F2J LAPACK/BLAS 0.1 | 0.0159300567 | 0.000685975213 |
| 256 | addition | F2J LAPACK/BLAS 0.1 | 0.259963323 | 0.0137548902 |
| 64 | scale | F2J LAPACK/BLAS 0.1 | 0.00294078074 | 0.000797307644 |
| 256 | scale | F2J LAPACK/BLAS 0.1 | 0.0492382759 | 0.004035125 |
| 64 | multiplication | F2J LAPACK/BLAS 0.1 | 0.0936490758 | 0.00268366974 |
| 256 | multiplication | F2J LAPACK/BLAS 0.1 | 3.89492273 | 0.140129917 |
| 64 | determinant | F2J LAPACK/BLAS 0.1 | 0.0453806238 | 0.00218302662 |
| 256 | determinant | F2J LAPACK/BLAS 0.1 | 2.02122564 | 0.113773824 |
| 64 | inverse | F2J LAPACK/BLAS 0.1 | 0.132874866 | 0.0140083871 |
| 256 | inverse | F2J LAPACK/BLAS 0.1 | 6.81760778 | 0.18103367 |
| 64 | inverseSPD | F2J LAPACK/BLAS 0.1 | 0.125215218 | 0.00897249592 |
| 256 | inverseSPD | F2J LAPACK/BLAS 0.1 | 5.21392736 | 0.135199048 |
| 64 | cholesky | F2J LAPACK/BLAS 0.1 | 0.0366019794 | 0.00384065438 |
| 256 | cholesky | F2J LAPACK/BLAS 0.1 | 1.28245418 | 0.0406909334 |
| 64 | lu | F2J LAPACK/BLAS 0.1 | 0.0607249178 | 0.00671541943 |
| 256 | lu | F2J LAPACK/BLAS 0.1 | 2.49260668 | 0.165052524 |
| 64 | qr | F2J LAPACK/BLAS 0.1 | 0.254159796 | 0.0186493684 |
| 256 | qr | F2J LAPACK/BLAS 0.1 | 14.1560367 | 0.342026269 |
| 64 | eigenSymmetric | F2J LAPACK/BLAS 0.1 | 0.99994881 | 0.165994756 |
| 256 | eigenSymmetric | F2J LAPACK/BLAS 0.1 | 26.6502021 | 1.43097368 |
| 64 | svd | F2J LAPACK/BLAS 0.1 | 2.31005079 | 0.446789232 |
| 256 | svd | F2J LAPACK/BLAS 0.1 | 107.454542 | 13.1288606 |
| 64 | solve | F2J LAPACK/BLAS 0.1 | 0.051750687 | 0.00982743039 |
| 256 | solve | F2J LAPACK/BLAS 0.1 | 2.17519297 | 0.231522536 |
| 64 | transpose | MTJ 1.0.4 (F2J) | 0.0121303149 | 0.000690476098 |
| 256 | transpose | MTJ 1.0.4 (F2J) | 0.634826781 | 0.0200873106 |
| 64 | addition | MTJ 1.0.4 (F2J) | 0.0253331399 | 0.00209401535 |
| 256 | addition | MTJ 1.0.4 (F2J) | 0.372766082 | 0.0130362487 |
| 64 | scale | MTJ 1.0.4 (F2J) | 0.0183724527 | 0.0027817539 |
| 256 | scale | MTJ 1.0.4 (F2J) | 0.301436008 | 0.0193659535 |
| 64 | multiplication | MTJ 1.0.4 (F2J) | 0.0930595058 | 0.00500710548 |
| 256 | multiplication | MTJ 1.0.4 (F2J) | 3.93802008 | 0.357441648 |
| 64 | determinant | MTJ 1.0.4 (F2J) | 0.0600012448 | 0.00436265939 |
| 256 | determinant | MTJ 1.0.4 (F2J) | 2.31789228 | 0.119403897 |
| 64 | inverse | MTJ 1.0.4 (F2J) | 0.260746348 | 0.0228257844 |
| 256 | inverse | MTJ 1.0.4 (F2J) | 12.2471433 | 0.785875077 |
| 64 | inverseSPD | MTJ 1.0.4 (F2J) | 0.348803563 | 0.0422067435 |
| 256 | inverseSPD | MTJ 1.0.4 (F2J) | 19.5356027 | 2.14877738 |
| 64 | cholesky | MTJ 1.0.4 (F2J) | 0.0942133729 | 0.0104169536 |
| 256 | cholesky | MTJ 1.0.4 (F2J) | 3.30604409 | 0.0676986675 |
| 64 | lu | MTJ 1.0.4 (F2J) | 0.0604697047 | 0.00494243475 |
| 256 | lu | MTJ 1.0.4 (F2J) | 2.29145609 | 0.134350173 |
| 64 | qr | MTJ 1.0.4 (F2J) | 0.28525136 | 0.0473929789 |
| 256 | qr | MTJ 1.0.4 (F2J) | 14.50612 | 1.01116035 |
| 64 | eigenSymmetric | MTJ 1.0.4 (F2J) | 0.947806585 | 0.300410779 |
| 256 | eigenSymmetric | MTJ 1.0.4 (F2J) | 21.4951883 | 1.7998984 |
| 64 | svd | MTJ 1.0.4 (F2J) | 2.54507075 | 1.6138488 |
| 256 | svd | MTJ 1.0.4 (F2J) | 69.5783931 | 47.0208818 |
| 64 | solve | MTJ 1.0.4 (F2J) | 0.0509610339 | 0.00624052617 |
| 256 | solve | MTJ 1.0.4 (F2J) | 2.27051141 | 0.139865318 |
| 64 | transpose | ojAlgo 57.5.0 | 0.00662376787 | 0.00172316289 |
| 256 | transpose | ojAlgo 57.5.0 | 0.149631192 | 0.026454227 |
| 64 | addition | ojAlgo 57.5.0 | 0.00362985552 | 0.000223632132 |
| 256 | addition | ojAlgo 57.5.0 | 0.0719748362 | 0.00797762687 |
| 64 | scale | ojAlgo 57.5.0 | 0.00325389207 | 0.000247720733 |
| 256 | scale | ojAlgo 57.5.0 | 0.0521629436 | 0.0136156099 |
| 64 | multiplication | ojAlgo 57.5.0 | 0.0773591067 | 0.00726794769 |
| 256 | multiplication | ojAlgo 57.5.0 | 1.05809599 | 0.0736242185 |
| 64 | determinant | ojAlgo 57.5.0 | 0.0532270432 | 0.00109243193 |
| 256 | determinant | ojAlgo 57.5.0 | 1.64306667 | 0.08740072 |
| 64 | inverse | ojAlgo 57.5.0 | 0.198422745 | 0.0157626293 |
| 256 | inverse | ojAlgo 57.5.0 | 5.28344556 | 0.122730118 |
| 64 | inverseSPD | ojAlgo 57.5.0 | 0.120533698 | 0.00921504372 |
| 256 | inverseSPD | ojAlgo 57.5.0 | 7.61539306 | 0.597578719 |
| 64 | cholesky | ojAlgo 57.5.0 | 0.0326850923 | 0.00396183197 |
| 256 | cholesky | ojAlgo 57.5.0 | 4.67442834 | 0.887313323 |
| 64 | lu | ojAlgo 57.5.0 | 0.0659962895 | 0.0077744281 |
| 256 | lu | ojAlgo 57.5.0 | 1.87686937 | 0.127990651 |
| 64 | qr | ojAlgo 57.5.0 | 0.228275968 | 0.016496775 |
| 256 | qr | ojAlgo 57.5.0 | 10.1800954 | 1.09687773 |
| 64 | eigenSymmetric | ojAlgo 57.5.0 | 0.499603177 | 0.0565181467 |
| 256 | eigenSymmetric | ojAlgo 57.5.0 | 23.4560116 | 13.1646285 |
| 64 | svd | ojAlgo 57.5.0 | 1.32017201 | 0.270231946 |
| 256 | svd | ojAlgo 57.5.0 | 42.9711456 | 9.43812347 |
| 64 | solve | ojAlgo 57.5.0 | 0.0505805533 | 0.004196266 |
| 256 | solve | ojAlgo 57.5.0 | 1.71771293 | 0.094613801 |
| 64 | transpose | UJMP 0.3.0 (core) | 0.0323869821 | 0.0240682371 |
| 256 | transpose | UJMP 0.3.0 (core) | 0.635137761 | 0.165758505 |
| 64 | addition | UJMP 0.3.0 (core) | 0.00827146585 | 0.00267643092 |
| 256 | addition | UJMP 0.3.0 (core) | 0.267981256 | 0.0283709462 |
| 64 | scale | UJMP 0.3.0 (core) | 0.00748538254 | 0.00267716387 |
| 256 | scale | UJMP 0.3.0 (core) | 0.263225086 | 0.0404899472 |
| 64 | multiplication | UJMP 0.3.0 (core) | 0.211015655 | 0.0345224368 |
| 256 | multiplication | UJMP 0.3.0 (core) | 4.21122607 | 0.826749976 |
| 64 | determinant | UJMP 0.3.0 (core) | 0.0674694156 | 0.00521485884 |
| 256 | determinant | UJMP 0.3.0 (core) | 4.6002986 | 0.112269107 |
| 64 | inverse | UJMP 0.3.0 (core) | 0.235800937 | 0.0191503359 |
| 256 | inverse | UJMP 0.3.0 (core) | 14.7176333 | 0.551158601 |
| 64 | inverseSPD | UJMP 0.3.0 (core) | 0.36654829 | 0.066522795 |
| 256 | inverseSPD | UJMP 0.3.0 (core) | 30.3374929 | 1.37482849 |
| 64 | cholesky | UJMP 0.3.0 (core) | 0.0460851626 | 0.00755973347 |
| 256 | cholesky | UJMP 0.3.0 (core) | 2.54393885 | 0.15592351 |
| 64 | lu | UJMP 0.3.0 (core) | 0.0835662273 | 0.00749272709 |
| 256 | lu | UJMP 0.3.0 (core) | 4.86119041 | 0.411724945 |
| 64 | qr | UJMP 0.3.0 (core) | 0.42755639 | 0.0481041843 |
| 256 | qr | UJMP 0.3.0 (core) | 40.5263611 | 2.41168486 |
| 64 | eigenSymmetric | UJMP 0.3.0 (core) | 0.882580227 | 0.306036398 |
| 256 | eigenSymmetric | UJMP 0.3.0 (core) | 62.0546958 | 3.67859939 |
| 64 | svd | UJMP 0.3.0 (core) | 7.13241386 | 3.50362934 |
| 256 | svd | UJMP 0.3.0 (core) | 204.059933 | 26.0212055 |
| 64 | solve | UJMP 0.3.0 (core) | 0.085055521 | 0.00642363434 |
| 256 | solve | UJMP 0.3.0 (core) | 4.90461675 | 0.195132518 |

## Large-All-Cores — 12 requested compute thread(s)

[Interactive chart](benchmarks-large-all-cores-12thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 512 | multiplication | NM Dev 1.2.8 | 10.333443 | 0.69849588 |
| 1024 | multiplication | NM Dev 1.2.8 | 68.2463736 | 5.7082871 |
| 2000 | multiplication | NM Dev 1.2.8 | 501.39 | 13.7785778 |
| 512 | lu | NM Dev 1.2.8 | 22.8792093 | 1.20464426 |
| 1024 | lu | NM Dev 1.2.8 | 117.112833 | 21.5557682 |
| 2000 | lu | NM Dev 1.2.8 | 1340.42423 | 43.80447 |
| 512 | cholesky | NM Dev 1.2.8 | 9.84216349 | 0.601189218 |
| 1024 | cholesky | NM Dev 1.2.8 | 50.1047467 | 5.7561727 |
| 2000 | cholesky | NM Dev 1.2.8 | 252.1095 | 20.5509132 |
| 512 | qr | NM Dev 1.2.8 | 71.3897278 | 1.83214061 |
| 1024 | qr | NM Dev 1.2.8 | 331.0046 | 25.8436067 |
| 2000 | qr | NM Dev 1.2.8 | 2874.50727 | 167.934007 |
| 512 | solve | NM Dev 1.2.8 | 25.0764782 | 3.32621901 |
| 1024 | solve | NM Dev 1.2.8 | 120.938242 | 15.5440029 |
| 2000 | solve | NM Dev 1.2.8 | 1355.78937 | 95.0848707 |
| 512 | multiplication | EJML 0.46.1 | 26.6396958 | 1.98603673 |
| 1024 | multiplication | EJML 0.46.1 | 366.1083 | 5.48834301 |
| 2000 | multiplication | EJML 0.46.1 | 3461.52898 | 36.8608773 |
| 512 | lu | EJML 0.46.1 | 44.0530333 | 3.67803823 |
| 1024 | lu | EJML 0.46.1 | 414.641817 | 30.946209 |
| 2000 | lu | EJML 0.46.1 | 3162.71808 | 83.8360472 |
| 512 | cholesky | EJML 0.46.1 | 22.715263 | 0.654300832 |
| 1024 | cholesky | EJML 0.46.1 | 71.8326833 | 1.07577008 |
| 2000 | cholesky | EJML 0.46.1 | 495.827117 | 14.3467572 |
| 512 | qr | EJML 0.46.1 | 79.3849222 | 7.68750963 |
| 1024 | qr | EJML 0.46.1 | 730.044333 | 50.4601227 |
| 2000 | qr | EJML 0.46.1 | 6215.67083 | 173.933491 |
| 512 | solve | EJML 0.46.1 | 44.17175 | 0.963367888 |
| 1024 | solve | EJML 0.46.1 | 421.7497 | 57.7417867 |
| 2000 | solve | EJML 0.46.1 | 3132.38488 | 30.4877318 |
| 512 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 180.178633 | 5.04503831 |
| 1024 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 8732.44128 | 237.926099 |
| 2000 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 100244.98 | 27516.2058 |
| 512 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 56.7443083 | 3.13305552 |
| 1024 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 906.256917 | 593.124438 |
| 2000 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 20013.2712 | 1100.7268 |
| 512 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 16.1635487 | 0.972320685 |
| 1024 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 133.718058 | 8.76301478 |
| 2000 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 1228.76847 | 97.5159675 |
| 512 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 123.532942 | 37.9809092 |
| 1024 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 1178.29145 | 19.0000681 |
| 2000 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 8129.01943 | 2341.49255 |
| 512 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 56.4510083 | 3.17092031 |
| 1024 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 908.86515 | 468.579004 |
| 2000 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 19842.1268 | 1047.9186 |
| 512 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 70.2593056 | 1.3307479 |
| 1024 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 601.913633 | 9.16913489 |
| 2000 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 4459.80792 | 20.5635584 |
| 512 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 56.5020708 | 5.50844451 |
| 1024 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 978.7519 | 579.95825 |
| 2000 | lu | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 19679.2199 | 956.594879 |
| 512 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 15.9888179 | 0.958580955 |
| 1024 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 132.4541 | 18.0282481 |
| 2000 | cholesky | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 1237.32068 | 103.244 |
| 512 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 112.408683 | 6.23836127 |
| 1024 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 955.0989 | 56.0557453 |
| 2000 | qr | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 7242.0544 | 58.2853593 |
| 512 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 55.4677625 | 3.64958566 |
| 1024 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 978.802133 | 462.708881 |
| 2000 | solve | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 19662.9812 | 407.413446 |
| 512 | multiplication | JAMA 1.0.3 | 141.263467 | 13.3710554 |
| 1024 | multiplication | JAMA 1.0.3 | 1229.74647 | 24.5816391 |
| 2000 | multiplication | JAMA 1.0.3 | 9247.0227 | 69.6988062 |
| 512 | lu | JAMA 1.0.3 | 42.8777067 | 1.45804313 |
| 1024 | lu | JAMA 1.0.3 | 390.7028 | 30.0081434 |
| 2000 | lu | JAMA 1.0.3 | 3177.70458 | 37.5761775 |
| 512 | cholesky | JAMA 1.0.3 | 21.0925833 | 0.611451914 |
| 1024 | cholesky | JAMA 1.0.3 | 189.645892 | 5.72702192 |
| 2000 | cholesky | JAMA 1.0.3 | 1551.93925 | 18.2384412 |
| 512 | qr | JAMA 1.0.3 | 479.435983 | 18.0035777 |
| 1024 | qr | JAMA 1.0.3 | 15603.3267 | 792.250443 |
| 2000 | qr | JAMA 1.0.3 | 206623.079 | 22393.6152 |
| 512 | solve | JAMA 1.0.3 | 43.4340333 | 1.77672387 |
| 1024 | solve | JAMA 1.0.3 | 400.729267 | 31.542763 |
| 2000 | solve | JAMA 1.0.3 | 3327.13642 | 431.091943 |
| 512 | multiplication | Colt 1.2.0 | 278.377767 | 24.1011742 |
| 1024 | multiplication | Colt 1.2.0 | 5809.42178 | 146.144999 |
| 2000 | multiplication | Colt 1.2.0 | 41906.6796 | 751.847789 |
| 512 | lu | Colt 1.2.0 | 43.8300533 | 10.1174203 |
| 1024 | lu | Colt 1.2.0 | 386.406083 | 60.003584 |
| 2000 | lu | Colt 1.2.0 | 3113.10447 | 185.737814 |
| 512 | cholesky | Colt 1.2.0 | 21.2964474 | 3.10687817 |
| 1024 | cholesky | Colt 1.2.0 | 166.976775 | 2.98937206 |
| 2000 | cholesky | Colt 1.2.0 | 1344.77113 | 13.6313615 |
| 512 | qr | Colt 1.2.0 | 659.494217 | 20.2559683 |
| 1024 | qr | Colt 1.2.0 | 11590.6275 | 3557.50406 |
| 2000 | qr | Colt 1.2.0 | 123361.896 | 954.976926 |
| 512 | solve | Colt 1.2.0 | 45.1938333 | 4.11913792 |
| 1024 | solve | Colt 1.2.0 | 403.117017 | 62.2979492 |
| 2000 | solve | Colt 1.2.0 | 3034.98445 | 384.897115 |
| 512 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 47.4159933 | 13.8839332 |
| 1024 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 1157.28007 | 59.7440868 |
| 2000 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 8258.36328 | 176.040703 |
| 512 | lu | Parallel Colt 0.11.4 (upstream tag build) | 41.0239967 | 1.19982277 |
| 1024 | lu | Parallel Colt 0.11.4 (upstream tag build) | 361.3019 | 37.8809895 |
| 2000 | lu | Parallel Colt 0.11.4 (upstream tag build) | 2927.493 | 114.463302 |
| 512 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 18.3665336 | 1.35166193 |
| 1024 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 55.0697083 | 4.05883767 |
| 2000 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 209.938408 | 37.975314 |
| 512 | qr | Parallel Colt 0.11.4 (upstream tag build) | 87.9586222 | 6.07670896 |
| 1024 | qr | Parallel Colt 0.11.4 (upstream tag build) | 257.8858 | 32.0791143 |
| 2000 | qr | Parallel Colt 0.11.4 (upstream tag build) | 1212.09295 | 52.4961279 |
| 512 | solve | Parallel Colt 0.11.4 (upstream tag build) | 46.1236867 | 2.56943245 |
| 1024 | solve | Parallel Colt 0.11.4 (upstream tag build) | 378.156517 | 32.9344633 |
| 2000 | solve | Parallel Colt 0.11.4 (upstream tag build) | 2871.4037 | 47.1710083 |
| 512 | multiplication | jblas 1.2.5 (native) | 89.6151278 | 10.1233627 |
| 1024 | multiplication | jblas 1.2.5 (native) | 809.612867 | 7.65440333 |
| 2000 | multiplication | jblas 1.2.5 (native) | 6278.88012 | 106.545584 |
| 512 | lu | jblas 1.2.5 (native) | 34.369998 | 6.2533381 |
| 1024 | lu | jblas 1.2.5 (native) | 265.18275 | 4.20448751 |
| 2000 | lu | jblas 1.2.5 (native) | 1789.00017 | 33.6318921 |
| 512 | cholesky | jblas 1.2.5 (native) | 24.3752352 | 0.957993597 |
| 1024 | cholesky | jblas 1.2.5 (native) | 199.651383 | 1.39555649 |
| 2000 | cholesky | jblas 1.2.5 (native) | 1438.97352 | 15.4261233 |
| 512 | qr | jblas 1.2.5 (native) | 186.977475 | 3.09248859 |
| 1024 | qr | jblas 1.2.5 (native) | 1563.24935 | 84.5568148 |
| 2000 | qr | jblas 1.2.5 (native) | 11662.0328 | 67.5684324 |
| 512 | solve | jblas 1.2.5 (native) | 31.5392167 | 2.55347059 |
| 1024 | solve | jblas 1.2.5 (native) | 240.811283 | 13.6982151 |
| 2000 | solve | jblas 1.2.5 (native) | 1698.8176 | 7.46940983 |
| 512 | multiplication | F2J LAPACK/BLAS 0.1 | 29.3272515 | 1.47226784 |
| 1024 | multiplication | F2J LAPACK/BLAS 0.1 | 405.9435 | 7.27304245 |
| 2000 | multiplication | F2J LAPACK/BLAS 0.1 | 3580.88783 | 153.761225 |
| 512 | lu | F2J LAPACK/BLAS 0.1 | 14.2872583 | 0.365301826 |
| 1024 | lu | F2J LAPACK/BLAS 0.1 | 114.787092 | 9.67707223 |
| 2000 | lu | F2J LAPACK/BLAS 0.1 | 723.3881 | 52.3606128 |
| 512 | cholesky | F2J LAPACK/BLAS 0.1 | 8.12226218 | 0.429418057 |
| 1024 | cholesky | F2J LAPACK/BLAS 0.1 | 67.4769792 | 3.00335991 |
| 2000 | cholesky | F2J LAPACK/BLAS 0.1 | 608.436617 | 176.253501 |
| 512 | qr | F2J LAPACK/BLAS 0.1 | 111.556017 | 2.77992436 |
| 1024 | qr | F2J LAPACK/BLAS 0.1 | 937.541067 | 23.237094 |
| 2000 | qr | F2J LAPACK/BLAS 0.1 | 7193.01205 | 70.7790517 |
| 512 | solve | F2J LAPACK/BLAS 0.1 | 12.900949 | 0.554000566 |
| 1024 | solve | F2J LAPACK/BLAS 0.1 | 98.2295833 | 8.60651266 |
| 2000 | solve | F2J LAPACK/BLAS 0.1 | 654.270767 | 55.3856453 |
| 512 | multiplication | MTJ 1.0.4 (F2J) | 30.0087595 | 2.00784037 |
| 1024 | multiplication | MTJ 1.0.4 (F2J) | 397.431583 | 20.525432 |
| 2000 | multiplication | MTJ 1.0.4 (F2J) | 3585.60747 | 100.67685 |
| 512 | lu | MTJ 1.0.4 (F2J) | 13.3965426 | 0.191333607 |
| 1024 | lu | MTJ 1.0.4 (F2J) | 99.6596778 | 3.29051603 |
| 2000 | lu | MTJ 1.0.4 (F2J) | 669.402117 | 58.1373621 |
| 512 | cholesky | MTJ 1.0.4 (F2J) | 25.3083282 | 2.13002056 |
| 1024 | cholesky | MTJ 1.0.4 (F2J) | 196.275683 | 6.52223079 |
| 2000 | cholesky | MTJ 1.0.4 (F2J) | 1404.50163 | 27.1641748 |
| 512 | qr | MTJ 1.0.4 (F2J) | 112.376875 | 2.0367041 |
| 1024 | qr | MTJ 1.0.4 (F2J) | 930.229083 | 10.6448748 |
| 2000 | qr | MTJ 1.0.4 (F2J) | 7212.25985 | 61.1096216 |
| 512 | solve | MTJ 1.0.4 (F2J) | 13.2733089 | 1.7225941 |
| 1024 | solve | MTJ 1.0.4 (F2J) | 98.6528417 | 3.94555318 |
| 2000 | solve | MTJ 1.0.4 (F2J) | 652.3643 | 44.5243524 |
| 512 | multiplication | ojAlgo 57.5.0 | 7.43060944 | 0.274062756 |
| 1024 | multiplication | ojAlgo 57.5.0 | 60.8751083 | 6.68855309 |
| 2000 | multiplication | ojAlgo 57.5.0 | 612.819183 | 31.2319621 |
| 512 | lu | ojAlgo 57.5.0 | 13.045799 | 0.231690757 |
| 1024 | lu | ojAlgo 57.5.0 | 93.4501278 | 9.83533879 |
| 2000 | lu | ojAlgo 57.5.0 | 1303.11393 | 121.375118 |
| 512 | cholesky | ojAlgo 57.5.0 | 22.511957 | 2.83841011 |
| 1024 | cholesky | ojAlgo 57.5.0 | 78.3551167 | 4.03555242 |
| 2000 | cholesky | ojAlgo 57.5.0 | 588.68445 | 131.424758 |
| 512 | qr | ojAlgo 57.5.0 | 52.6750542 | 4.8338547 |
| 1024 | qr | ojAlgo 57.5.0 | 429.657933 | 33.8533817 |
| 2000 | qr | ojAlgo 57.5.0 | 4250.51328 | 341.798197 |
| 512 | solve | ojAlgo 57.5.0 | 12.5424787 | 0.509131988 |
| 1024 | solve | ojAlgo 57.5.0 | 104.729292 | 62.8306512 |
| 2000 | solve | ojAlgo 57.5.0 | 1305.82392 | 131.883714 |
| 512 | multiplication | UJMP 0.3.0 (core) | 42.0155667 | 1.89795834 |
| 1024 | multiplication | UJMP 0.3.0 (core) | 1821.21607 | 49.9552549 |
| 2000 | multiplication | UJMP 0.3.0 (core) | 16068.023 | 658.372533 |
| 512 | lu | UJMP 0.3.0 (core) | 42.8574867 | 1.62809512 |
| 1024 | lu | UJMP 0.3.0 (core) | 408.362567 | 50.0968607 |
| 2000 | lu | UJMP 0.3.0 (core) | 3380.31153 | 402.46857 |
| 512 | cholesky | UJMP 0.3.0 (core) | 21.61242 | 0.78936417 |
| 1024 | cholesky | UJMP 0.3.0 (core) | 191.516158 | 4.86140719 |
| 2000 | cholesky | UJMP 0.3.0 (core) | 1558.592 | 6.6452702 |
| 512 | qr | UJMP 0.3.0 (core) | 488.52055 | 26.6560642 |
| 1024 | qr | UJMP 0.3.0 (core) | 15669.347 | 566.871203 |
| 2000 | qr | UJMP 0.3.0 (core) | 203876.135 | 16349.379 |
| 512 | solve | UJMP 0.3.0 (core) | 42.73767 | 0.560574096 |
| 1024 | solve | UJMP 0.3.0 (core) | 400.098017 | 27.1582351 |
| 2000 | solve | UJMP 0.3.0 (core) | 3263.73293 | 315.104545 |

## Rectangular-All-Cores — 12 requested compute thread(s)

[Interactive chart](benchmarks-rectangular-all-cores-12thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 128x512x64 | multiplication | NM Dev 1.2.8 | 0.555102492 | 0.0999337578 |
| 512x128x256 | multiplication | NM Dev 1.2.8 | 1.65432001 | 0.372444387 |
| 1024x256x64 | multiplication | NM Dev 1.2.8 | 1.33050319 | 0.0832685366 |
| 255x257x129 | multiplication | NM Dev 1.2.8 | 0.88055496 | 0.0926360156 |
| 128x512x64 | multiplication | EJML 0.46.1 | 1.4085122 | 0.0931287034 |
| 512x128x256 | multiplication | EJML 0.46.1 | 3.75651153 | 0.918607634 |
| 1024x256x64 | multiplication | EJML 0.46.1 | 5.71147605 | 0.20478852 |
| 255x257x129 | multiplication | EJML 0.46.1 | 2.43559185 | 0.0622036664 |
| 128x512x64 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 4.9281046 | 0.103093812 |
| 512x128x256 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 17.7292806 | 0.793903005 |
| 1024x256x64 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 17.7480389 | 0.658716514 |
| 255x257x129 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — Array2DRowRealMatrix | 9.53002269 | 0.0945582431 |
| 128x512x64 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 2.18975945 | 0.0479663049 |
| 512x128x256 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 8.9573529 | 0.262082053 |
| 1024x256x64 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 8.81851537 | 0.226882036 |
| 255x257x129 | multiplication | Commons Math 4.0-SNAPSHOT (20261023.103154-1592) — BlockRealMatrix | 4.75645351 | 0.224797987 |
| 128x512x64 | multiplication | JAMA 1.0.3 | 4.17893191 | 0.145321258 |
| 512x128x256 | multiplication | JAMA 1.0.3 | 13.4662335 | 1.37728335 |
| 1024x256x64 | multiplication | JAMA 1.0.3 | 15.511306 | 1.20919422 |
| 255x257x129 | multiplication | JAMA 1.0.3 | 7.66340556 | 0.0322044323 |
| 128x512x64 | multiplication | Colt 1.2.0 | 5.36904283 | 2.79355508 |
| 512x128x256 | multiplication | Colt 1.2.0 | 26.8794103 | 5.58460701 |
| 1024x256x64 | multiplication | Colt 1.2.0 | 20.6383358 | 22.3545514 |
| 255x257x129 | multiplication | Colt 1.2.0 | 6.57134407 | 0.305000865 |
| 128x512x64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 1.18007384 | 0.329614052 |
| 512x128x256 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 4.32897719 | 0.687179656 |
| 1024x256x64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 4.30328437 | 1.33600254 |
| 255x257x129 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 2.08856084 | 0.518291334 |
| 128x512x64 | multiplication | jblas 1.2.5 (native) | 2.83533542 | 0.0729169314 |
| 512x128x256 | multiplication | jblas 1.2.5 (native) | 11.5113056 | 0.223787219 |
| 1024x256x64 | multiplication | jblas 1.2.5 (native) | 12.7443213 | 1.30423884 |
| 255x257x129 | multiplication | jblas 1.2.5 (native) | 5.58471395 | 0.398177346 |
| 128x512x64 | multiplication | F2J LAPACK/BLAS 0.1 | 1.32450465 | 0.0659165367 |
| 512x128x256 | multiplication | F2J LAPACK/BLAS 0.1 | 3.67269145 | 0.21192621 |
| 1024x256x64 | multiplication | F2J LAPACK/BLAS 0.1 | 3.73635099 | 0.123898053 |
| 255x257x129 | multiplication | F2J LAPACK/BLAS 0.1 | 2.29739943 | 0.139711573 |
| 128x512x64 | multiplication | MTJ 1.0.4 (F2J) | 1.32668606 | 0.0381370996 |
| 512x128x256 | multiplication | MTJ 1.0.4 (F2J) | 3.59976856 | 0.205885208 |
| 1024x256x64 | multiplication | MTJ 1.0.4 (F2J) | 3.75336188 | 0.472038253 |
| 255x257x129 | multiplication | MTJ 1.0.4 (F2J) | 2.29533363 | 0.231229331 |
| 128x512x64 | multiplication | ojAlgo 57.5.0 | 0.6502222 | 0.0293832062 |
| 512x128x256 | multiplication | ojAlgo 57.5.0 | 1.06223878 | 0.0491846632 |
| 1024x256x64 | multiplication | ojAlgo 57.5.0 | 1.81349007 | 0.0944254503 |
| 255x257x129 | multiplication | ojAlgo 57.5.0 | 0.699366581 | 0.0458299605 |
| 128x512x64 | multiplication | UJMP 0.3.0 (core) | 1.14596951 | 0.152014611 |
| 512x128x256 | multiplication | UJMP 0.3.0 (core) | 5.5542562 | 1.82595463 |
| 1024x256x64 | multiplication | UJMP 0.3.0 (core) | 9.86006988 | 1.07367573 |
| 255x257x129 | multiplication | UJMP 0.3.0 (core) | 2.14417033 | 0.69681141 |

