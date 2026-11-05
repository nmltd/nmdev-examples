# Benchmark results — windows-014014Z

1,260 cases across 12 implementation variants; 2,520 independently warmed fork records; 7,560 measured iteration means. Lower ms/op is faster.

Intel(R) Core(TM) i7-8750H CPU @ 2.20GHz; 12 logical processors; 63.92 GiB physical RAM; Windows 11 amd64; Oracle Corporation JDK 27 (27+35-2325).

Two separately warmed JVM forks per case; two 100 ms warmups and three 200 ms measurements per fork; one JMH worker; requested compute threads are shown per profile. Fork heap: 256 MiB initial / 1 GiB maximum. CPU affinity was not set. Native worker counts were not independently audited. These short measurements may have wide confidence intervals; rankings are machine-specific.

The run measures **NM Dev 1.2.10**. Profiles are kept separate; requested compute thread counts do not imply every library executes in parallel.

## Validation

All 1,260 requested cases, available fork JSON checksums, raw samples, arithmetic means, summary values, JVM thread flags and machine identity fields passed validation. All 6 preflight processes exited successfully. The runner reports completion with zero failed cases. 0 individual fork JSON files are absent from the supplied ZIP; combined measurements remain available, but those missing files cannot be independently checksum-verified. Downloaded binaries are excluded, so their recorded hashes cannot be recomputed from this archive.

[Validation evidence](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-11-05/windows-014014Z/validation-report.json) · [Saved JMH measurements](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-11-05/windows-014014Z/jmh.json) · [Summary CSV](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-11-05/windows-014014Z/summary.csv) · [Hardware and archive digest](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-11-05/windows-014014Z/publication-format.json) · [Binary provenance](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-11-05/windows-014014Z/binary-provenance.json)

## Baseline — 1 requested compute thread(s)

[Interactive chart](benchmarks.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 64 | transpose | NM Dev 1.2.10 | 0.00590438436 | 0.00134478602 |
| 256 | transpose | NM Dev 1.2.10 | 0.223205829 | 0.072641758 |
| 64 | addition | NM Dev 1.2.10 | 0.00368213255 | 0.00103566414 |
| 256 | addition | NM Dev 1.2.10 | 0.11206929 | 0.0242984805 |
| 64 | scale | NM Dev 1.2.10 | 0.00386221708 | 0.00100208169 |
| 256 | scale | NM Dev 1.2.10 | 0.102597495 | 0.0341356141 |
| 64 | multiplication | NM Dev 1.2.10 | 0.100192067 | 0.00765160717 |
| 256 | multiplication | NM Dev 1.2.10 | 3.98183849 | 0.771761612 |
| 64 | determinant | NM Dev 1.2.10 | 0.079976513 | 0.0519019718 |
| 256 | determinant | NM Dev 1.2.10 | 8.64488114 | 3.38162399 |
| 64 | inverse | NM Dev 1.2.10 | 0.693348554 | 0.485842102 |
| 256 | inverse | NM Dev 1.2.10 | 31.3228746 | 5.98775105 |
| 64 | inverseSPD | NM Dev 1.2.10 | 0.513795617 | 0.234956979 |
| 256 | inverseSPD | NM Dev 1.2.10 | 23.185113 | 1.04907379 |
| 64 | cholesky | NM Dev 1.2.10 | 0.066030509 | 0.0545846206 |
| 256 | cholesky | NM Dev 1.2.10 | 2.83008579 | 0.166895394 |
| 64 | lu | NM Dev 1.2.10 | 0.0863272285 | 0.0668978526 |
| 256 | lu | NM Dev 1.2.10 | 10.8965722 | 5.69284934 |
| 64 | qr | NM Dev 1.2.10 | 0.488238797 | 0.2641334 |
| 256 | qr | NM Dev 1.2.10 | 36.9282353 | 18.952394 |
| 64 | eigenSymmetric | NM Dev 1.2.10 | 8.90579693 | 9.85902102 |
| 256 | eigenSymmetric | NM Dev 1.2.10 | 72.3247736 | 21.687915 |
| 64 | svd | NM Dev 1.2.10 | 3.83443546 | 4.77786761 |
| 256 | svd | NM Dev 1.2.10 | 148.989228 | 198.909711 |
| 64 | solve | NM Dev 1.2.10 | 0.191952151 | 0.0628697282 |
| 256 | solve | NM Dev 1.2.10 | 10.4145662 | 6.74477652 |
| 64 | transpose | EJML 0.46.1 | 0.00504613937 | 0.000906665265 |
| 256 | transpose | EJML 0.46.1 | 0.150057201 | 0.0153727114 |
| 64 | addition | EJML 0.46.1 | 0.00357774878 | 0.000419814011 |
| 256 | addition | EJML 0.46.1 | 0.0650111472 | 0.00815206032 |
| 64 | scale | EJML 0.46.1 | 0.00321065906 | 0.000291171641 |
| 256 | scale | EJML 0.46.1 | 0.0608960642 | 0.0172451025 |
| 64 | multiplication | EJML 0.46.1 | 0.0996481253 | 0.0107816683 |
| 256 | multiplication | EJML 0.46.1 | 4.65083732 | 2.32509684 |
| 64 | determinant | EJML 0.46.1 | 0.0869513924 | 0.00874155592 |
| 256 | determinant | EJML 0.46.1 | 5.33693812 | 0.370408321 |
| 64 | inverse | EJML 0.46.1 | 0.32554288 | 0.0751411868 |
| 256 | inverse | EJML 0.46.1 | 18.3665327 | 2.02851138 |
| 64 | inverseSPD | EJML 0.46.1 | 0.162669694 | 0.0599088973 |
| 256 | inverseSPD | EJML 0.46.1 | 9.87722762 | 0.534440479 |
| 64 | cholesky | EJML 0.46.1 | 0.048185465 | 0.00577389795 |
| 256 | cholesky | EJML 0.46.1 | 3.14943401 | 0.779588339 |
| 64 | lu | EJML 0.46.1 | 0.118792536 | 0.0511458513 |
| 256 | lu | EJML 0.46.1 | 5.86066836 | 1.6305401 |
| 64 | qr | EJML 0.46.1 | 0.223567223 | 0.0372896072 |
| 256 | qr | EJML 0.46.1 | 10.848444 | 1.22142704 |
| 64 | eigenSymmetric | EJML 0.46.1 | 0.664423737 | 0.484843052 |
| 256 | eigenSymmetric | EJML 0.46.1 | 22.3742257 | 2.38641394 |
| 64 | svd | EJML 0.46.1 | 0.946939963 | 0.191915342 |
| 256 | svd | EJML 0.46.1 | 40.8084228 | 1.58667465 |
| 64 | solve | EJML 0.46.1 | 0.0899396252 | 0.0103932925 |
| 256 | solve | EJML 0.46.1 | 5.52885562 | 0.717703294 |
| 64 | transpose | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.00617856828 | 0.000508115726 |
| 256 | transpose | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.14940245 | 0.0324843006 |
| 64 | addition | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.00492786603 | 0.000908281063 |
| 256 | addition | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.10324303 | 0.0678008049 |
| 64 | scale | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.00468098293 | 0.000824978513 |
| 256 | scale | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.0704532358 | 0.0107047453 |
| 64 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.256846845 | 0.00294640544 |
| 256 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 19.9815697 | 0.873268008 |
| 64 | determinant | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.0874543632 | 0.00574663947 |
| 256 | determinant | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 6.08627891 | 0.0925631771 |
| 64 | inverse | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.156541575 | 0.0144625665 |
| 256 | inverse | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 11.6197185 | 5.5615444 |
| 64 | inverseSPD | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.120436467 | 0.0218843884 |
| 256 | inverseSPD | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 9.51054925 | 10.2255583 |
| 64 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.0701034382 | 0.0355429379 |
| 256 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 2.33420668 | 0.195154168 |
| 64 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.153274902 | 0.0995541149 |
| 256 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 6.75651215 | 0.937797187 |
| 64 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.261944636 | 0.0371085856 |
| 256 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 15.4593948 | 1.15542773 |
| 64 | eigenSymmetric | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 1.70963858 | 2.40209861 |
| 256 | eigenSymmetric | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 63.9455903 | 25.7827558 |
| 64 | svd | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 36.3576362 | 73.3408606 |
| 256 | svd | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 232.4172 | 13.377812 |
| 64 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.109725178 | 0.03522057 |
| 256 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 7.08357473 | 0.349359833 |
| 64 | transpose | Commons Math 4.0-beta1 - BlockRealMatrix | 0.00546020519 | 0.000831723833 |
| 256 | transpose | Commons Math 4.0-beta1 - BlockRealMatrix | 0.0960927145 | 0.0160692336 |
| 64 | addition | Commons Math 4.0-beta1 - BlockRealMatrix | 0.00386943725 | 0.000286313961 |
| 256 | addition | Commons Math 4.0-beta1 - BlockRealMatrix | 0.0844800639 | 0.0103946652 |
| 64 | scale | Commons Math 4.0-beta1 - BlockRealMatrix | 0.00380853775 | 0.00103029149 |
| 256 | scale | Commons Math 4.0-beta1 - BlockRealMatrix | 0.0730671102 | 0.0167697276 |
| 64 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 0.176194194 | 0.0482175305 |
| 256 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 11.827494 | 6.49574312 |
| 64 | determinant | Commons Math 4.0-beta1 - BlockRealMatrix | 0.108700835 | 0.0170512308 |
| 256 | determinant | Commons Math 4.0-beta1 - BlockRealMatrix | 7.25602675 | 2.0928069 |
| 64 | inverse | Commons Math 4.0-beta1 - BlockRealMatrix | 0.176234061 | 0.0543546847 |
| 256 | inverse | Commons Math 4.0-beta1 - BlockRealMatrix | 11.5679009 | 2.95279399 |
| 64 | inverseSPD | Commons Math 4.0-beta1 - BlockRealMatrix | 0.121391143 | 0.0320009032 |
| 256 | inverseSPD | Commons Math 4.0-beta1 - BlockRealMatrix | 5.75829559 | 0.499995248 |
| 64 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 0.0564231547 | 0.0105159094 |
| 256 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 2.14752675 | 0.166596088 |
| 64 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 0.0981220852 | 0.00427951127 |
| 256 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 6.33862546 | 0.341806148 |
| 64 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 0.257398108 | 0.0177593347 |
| 256 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 14.7155872 | 0.905225127 |
| 64 | eigenSymmetric | Commons Math 4.0-beta1 - BlockRealMatrix | 1.23973168 | 2.00347709 |
| 256 | eigenSymmetric | Commons Math 4.0-beta1 - BlockRealMatrix | 55.2829583 | 5.42756835 |
| 64 | svd | Commons Math 4.0-beta1 - BlockRealMatrix | 16.6048528 | 49.3682083 |
| 256 | svd | Commons Math 4.0-beta1 - BlockRealMatrix | 218.659917 | 30.8963215 |
| 64 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 0.105413706 | 0.0144092864 |
| 256 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 6.3195179 | 0.187750639 |
| 64 | transpose | JAMA 1.0.3 | 0.00573388103 | 0.000279759072 |
| 256 | transpose | JAMA 1.0.3 | 0.139496658 | 0.00293996303 |
| 64 | addition | JAMA 1.0.3 | 0.00425451985 | 0.000188042435 |
| 256 | addition | JAMA 1.0.3 | 0.0758308357 | 0.00547640812 |
| 64 | scale | JAMA 1.0.3 | 0.00391253721 | 0.000280101592 |
| 256 | scale | JAMA 1.0.3 | 0.0711796283 | 0.00478178468 |
| 64 | multiplication | JAMA 1.0.3 | 0.186782329 | 0.0201933617 |
| 256 | multiplication | JAMA 1.0.3 | 15.9060603 | 0.357699512 |
| 64 | determinant | JAMA 1.0.3 | 0.0808271047 | 0.0534229187 |
| 256 | determinant | JAMA 1.0.3 | 4.79208389 | 0.262008786 |
| 64 | inverse | JAMA 1.0.3 | 0.238825175 | 0.0142140729 |
| 256 | inverse | JAMA 1.0.3 | 15.0476155 | 0.433367588 |
| 64 | inverseSPD | JAMA 1.0.3 | 0.364749518 | 0.0457248097 |
| 256 | inverseSPD | JAMA 1.0.3 | 33.7756984 | 2.14864958 |
| 64 | cholesky | JAMA 1.0.3 | 0.0415329156 | 0.00379236315 |
| 256 | cholesky | JAMA 1.0.3 | 2.71012629 | 1.39133238 |
| 64 | lu | JAMA 1.0.3 | 0.0923376555 | 0.036837514 |
| 256 | lu | JAMA 1.0.3 | 5.55778721 | 1.16954586 |
| 64 | qr | JAMA 1.0.3 | 0.436864387 | 0.0206157826 |
| 256 | qr | JAMA 1.0.3 | 43.40255 | 6.33586013 |
| 64 | eigenSymmetric | JAMA 1.0.3 | 1.04688146 | 0.819374672 |
| 256 | eigenSymmetric | JAMA 1.0.3 | 66.9604306 | 15.5017014 |
| 64 | svd | JAMA 1.0.3 | 26.1470876 | 81.0175889 |
| 256 | svd | JAMA 1.0.3 | 330.843833 | 573.49029 |
| 64 | solve | JAMA 1.0.3 | 0.118212003 | 0.0414179941 |
| 256 | solve | JAMA 1.0.3 | 5.02783584 | 0.198686017 |
| 64 | transpose | Colt 1.2.0 | 0.00515442958 | 0.00063374219 |
| 256 | transpose | Colt 1.2.0 | 0.143612357 | 0.0187184002 |
| 64 | addition | Colt 1.2.0 | 0.00635486127 | 0.000915320559 |
| 256 | addition | Colt 1.2.0 | 0.133399058 | 0.0472197075 |
| 64 | scale | Colt 1.2.0 | 0.0061448242 | 0.00251522877 |
| 256 | scale | Colt 1.2.0 | 0.0989873161 | 0.0469127521 |
| 64 | multiplication | Colt 1.2.0 | 0.302234482 | 0.10500582 |
| 256 | multiplication | Colt 1.2.0 | 29.8097524 | 2.48505683 |
| 64 | determinant | Colt 1.2.0 | 0.124435912 | 0.0612948297 |
| 256 | determinant | Colt 1.2.0 | 5.76153843 | 0.727028882 |
| 64 | inverse | Colt 1.2.0 | 0.416496577 | 0.229028912 |
| 256 | inverse | Colt 1.2.0 | 18.8491665 | 2.07820684 |
| 64 | inverseSPD | Colt 1.2.0 | 0.409155202 | 0.0697773863 |
| 256 | inverseSPD | Colt 1.2.0 | 45.6090211 | 32.7924208 |
| 64 | cholesky | Colt 1.2.0 | 0.0611399167 | 0.0106759639 |
| 256 | cholesky | Colt 1.2.0 | 2.69287184 | 0.592291422 |
| 64 | lu | Colt 1.2.0 | 0.140193861 | 0.0691633918 |
| 256 | lu | Colt 1.2.0 | 7.78605903 | 4.47787136 |
| 64 | qr | Colt 1.2.0 | 0.467095356 | 0.0348548297 |
| 256 | qr | Colt 1.2.0 | 86.5507556 | 43.2354987 |
| 64 | eigenSymmetric | Colt 1.2.0 | 1.49266432 | 1.21825572 |
| 256 | eigenSymmetric | Colt 1.2.0 | 79.9445097 | 57.37525 |
| 64 | svd | Colt 1.2.0 | 17.9365387 | 33.2620519 |
| 256 | svd | Colt 1.2.0 | 207.065792 | 25.9786771 |
| 64 | solve | Colt 1.2.0 | 0.191938454 | 0.205636921 |
| 256 | solve | Colt 1.2.0 | 6.62877823 | 1.28204543 |
| 64 | transpose | Parallel Colt 0.11.4 (upstream tag build) | 0.00528370504 | 0.000491275331 |
| 256 | transpose | Parallel Colt 0.11.4 (upstream tag build) | 0.148296613 | 0.0215315685 |
| 64 | addition | Parallel Colt 0.11.4 (upstream tag build) | 0.00643995148 | 0.000849437839 |
| 256 | addition | Parallel Colt 0.11.4 (upstream tag build) | 0.110121354 | 0.0167008189 |
| 64 | scale | Parallel Colt 0.11.4 (upstream tag build) | 0.00477418605 | 0.000292804249 |
| 256 | scale | Parallel Colt 0.11.4 (upstream tag build) | 0.0854126506 | 0.0072257553 |
| 64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 0.269283189 | 0.0413546898 |
| 256 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 35.1310065 | 43.9645736 |
| 64 | determinant | Parallel Colt 0.11.4 (upstream tag build) | 0.210711111 | 0.308435519 |
| 256 | determinant | Parallel Colt 0.11.4 (upstream tag build) | 7.40722287 | 2.33239703 |
| 64 | inverse | Parallel Colt 0.11.4 (upstream tag build) | 0.580630851 | 0.824789321 |
| 256 | inverse | Parallel Colt 0.11.4 (upstream tag build) | 21.1590236 | 5.10867015 |
| 64 | inverseSPD | Parallel Colt 0.11.4 (upstream tag build) | 0.606665662 | 0.124781617 |
| 256 | inverseSPD | Parallel Colt 0.11.4 (upstream tag build) | 19.2357471 | 6.49803714 |
| 64 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 0.186035419 | 0.0800197245 |
| 256 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 3.51408577 | 0.627248834 |
| 64 | lu | Parallel Colt 0.11.4 (upstream tag build) | 0.199353974 | 0.171307646 |
| 256 | lu | Parallel Colt 0.11.4 (upstream tag build) | 6.80365427 | 0.504907985 |
| 64 | qr | Parallel Colt 0.11.4 (upstream tag build) | 2.92675951 | 6.86508649 |
| 256 | qr | Parallel Colt 0.11.4 (upstream tag build) | 27.3880733 | 16.4189887 |
| 64 | eigenSymmetric | Parallel Colt 0.11.4 (upstream tag build) | 2.27237274 | 2.84479397 |
| 256 | eigenSymmetric | Parallel Colt 0.11.4 (upstream tag build) | 86.4208472 | 79.7621354 |
| 64 | svd | Parallel Colt 0.11.4 (upstream tag build) | 6.88110675 | 4.15440467 |
| 256 | svd | Parallel Colt 0.11.4 (upstream tag build) | 100.732142 | 32.5388327 |
| 64 | solve | Parallel Colt 0.11.4 (upstream tag build) | 0.341549282 | 0.20798924 |
| 256 | solve | Parallel Colt 0.11.4 (upstream tag build) | 9.06235885 | 3.73286714 |
| 64 | transpose | jblas 1.2.5 (native) | 0.00625479101 | 0.000783956381 |
| 256 | transpose | jblas 1.2.5 (native) | 0.15454517 | 0.0173274852 |
| 64 | addition | jblas 1.2.5 (native) | 0.00381829615 | 0.00110593936 |
| 256 | addition | jblas 1.2.5 (native) | 0.0676461226 | 0.0103553026 |
| 64 | scale | jblas 1.2.5 (native) | 0.00310431206 | 0.000323833843 |
| 256 | scale | jblas 1.2.5 (native) | 0.0568816406 | 0.00599182223 |
| 64 | multiplication | jblas 1.2.5 (native) | 0.193394809 | 0.0134508984 |
| 256 | multiplication | jblas 1.2.5 (native) | 12.6960935 | 1.47787119 |
| 64 | determinant | jblas 1.2.5 (native) | 0.111783454 | 0.0128923457 |
| 256 | determinant | jblas 1.2.5 (native) | 5.0921414 | 0.288727928 |
| 64 | inverse | jblas 1.2.5 (native) | 0.289421517 | 0.0236070698 |
| 256 | inverse | jblas 1.2.5 (native) | 15.1107286 | 1.54006615 |
| 64 | inverseSPD | jblas 1.2.5 (native) | 0.364095851 | 0.0571553332 |
| 256 | inverseSPD | jblas 1.2.5 (native) | 20.7398133 | 2.34758401 |
| 64 | cholesky | jblas 1.2.5 (native) | 0.0575924689 | 0.00683341472 |
| 256 | cholesky | jblas 1.2.5 (native) | 3.17284413 | 0.267219108 |
| 64 | lu | jblas 1.2.5 (native) | 0.114778234 | 0.0157291012 |
| 256 | lu | jblas 1.2.5 (native) | 5.23091934 | 0.742361021 |
| 64 | qr | jblas 1.2.5 (native) | 0.457938327 | 0.024573928 |
| 256 | qr | jblas 1.2.5 (native) | 28.1136509 | 4.22074462 |
| 64 | eigenSymmetric | jblas 1.2.5 (native) | 0.93342879 | 0.165845584 |
| 256 | eigenSymmetric | jblas 1.2.5 (native) | 30.4320643 | 1.78982832 |
| 64 | svd | jblas 1.2.5 (native) | 2.38113215 | 0.157319 |
| 256 | svd | jblas 1.2.5 (native) | 154.618725 | 23.7053714 |
| 64 | solve | jblas 1.2.5 (native) | 0.100437307 | 0.0148575302 |
| 256 | solve | jblas 1.2.5 (native) | 4.85061313 | 0.916553906 |
| 64 | transpose | F2J LAPACK/BLAS 0.1 | 0.00776514479 | 0.00221642534 |
| 256 | transpose | F2J LAPACK/BLAS 0.1 | 0.41302585 | 0.0678808465 |
| 64 | addition | F2J LAPACK/BLAS 0.1 | 0.0191664785 | 0.00254216276 |
| 256 | addition | F2J LAPACK/BLAS 0.1 | 0.303998938 | 0.0379370125 |
| 64 | scale | F2J LAPACK/BLAS 0.1 | 0.00337786375 | 0.000581610139 |
| 256 | scale | F2J LAPACK/BLAS 0.1 | 0.0594036355 | 0.00819292299 |
| 64 | multiplication | F2J LAPACK/BLAS 0.1 | 0.106011757 | 0.0129405198 |
| 256 | multiplication | F2J LAPACK/BLAS 0.1 | 5.15628329 | 3.87767134 |
| 64 | determinant | F2J LAPACK/BLAS 0.1 | 0.0610943548 | 0.0241773732 |
| 256 | determinant | F2J LAPACK/BLAS 0.1 | 3.14380299 | 0.962119968 |
| 64 | inverse | F2J LAPACK/BLAS 0.1 | 0.160706399 | 0.0570808703 |
| 256 | inverse | F2J LAPACK/BLAS 0.1 | 14.769284 | 32.9147194 |
| 64 | inverseSPD | F2J LAPACK/BLAS 0.1 | 0.143381503 | 0.0216735224 |
| 256 | inverseSPD | F2J LAPACK/BLAS 0.1 | 20.6046902 | 70.0000865 |
| 64 | cholesky | F2J LAPACK/BLAS 0.1 | 0.0467361182 | 0.0109805329 |
| 256 | cholesky | F2J LAPACK/BLAS 0.1 | 1.75694828 | 1.32185634 |
| 64 | lu | F2J LAPACK/BLAS 0.1 | 0.0766581824 | 0.0193874699 |
| 256 | lu | F2J LAPACK/BLAS 0.1 | 4.01945904 | 3.05037969 |
| 64 | qr | F2J LAPACK/BLAS 0.1 | 0.312716648 | 0.171090712 |
| 256 | qr | F2J LAPACK/BLAS 0.1 | 15.8780875 | 2.56857848 |
| 64 | eigenSymmetric | F2J LAPACK/BLAS 0.1 | 1.74444758 | 1.49357995 |
| 256 | eigenSymmetric | F2J LAPACK/BLAS 0.1 | 38.624619 | 22.9656194 |
| 64 | svd | F2J LAPACK/BLAS 0.1 | 2.56147955 | 0.682926958 |
| 256 | svd | F2J LAPACK/BLAS 0.1 | 130.99935 | 41.9039478 |
| 64 | solve | F2J LAPACK/BLAS 0.1 | 0.0520009194 | 0.004302931 |
| 256 | solve | F2J LAPACK/BLAS 0.1 | 2.47431488 | 0.532565794 |
| 64 | transpose | MTJ 1.0.4 (F2J) | 0.0124101243 | 0.00103054614 |
| 256 | transpose | MTJ 1.0.4 (F2J) | 0.624395635 | 0.0270669419 |
| 64 | addition | MTJ 1.0.4 (F2J) | 0.0268912321 | 0.00540493322 |
| 256 | addition | MTJ 1.0.4 (F2J) | 0.3840442 | 0.0348359889 |
| 64 | scale | MTJ 1.0.4 (F2J) | 0.0187363001 | 0.00301186869 |
| 256 | scale | MTJ 1.0.4 (F2J) | 0.379801043 | 0.273471151 |
| 64 | multiplication | MTJ 1.0.4 (F2J) | 0.126691961 | 0.0572744064 |
| 256 | multiplication | MTJ 1.0.4 (F2J) | 5.38071524 | 3.66553744 |
| 64 | determinant | MTJ 1.0.4 (F2J) | 0.0711003514 | 0.0381202521 |
| 256 | determinant | MTJ 1.0.4 (F2J) | 2.79860311 | 0.52122995 |
| 64 | inverse | MTJ 1.0.4 (F2J) | 0.264856712 | 0.0683737055 |
| 256 | inverse | MTJ 1.0.4 (F2J) | 14.2166804 | 0.546633584 |
| 64 | inverseSPD | MTJ 1.0.4 (F2J) | 0.367991359 | 0.0681480165 |
| 256 | inverseSPD | MTJ 1.0.4 (F2J) | 20.3596595 | 5.66087879 |
| 64 | cholesky | MTJ 1.0.4 (F2J) | 0.118471585 | 0.0640813623 |
| 256 | cholesky | MTJ 1.0.4 (F2J) | 4.73446816 | 4.15666866 |
| 64 | lu | MTJ 1.0.4 (F2J) | 0.0654393034 | 0.0127326669 |
| 256 | lu | MTJ 1.0.4 (F2J) | 3.0445952 | 1.26322848 |
| 64 | qr | MTJ 1.0.4 (F2J) | 0.320777879 | 0.0814336029 |
| 256 | qr | MTJ 1.0.4 (F2J) | 17.2726236 | 5.06010957 |
| 64 | eigenSymmetric | MTJ 1.0.4 (F2J) | 2.10697308 | 1.56314684 |
| 256 | eigenSymmetric | MTJ 1.0.4 (F2J) | 35.8063219 | 18.0566208 |
| 64 | svd | MTJ 1.0.4 (F2J) | 10.7596891 | 6.99251518 |
| 256 | svd | MTJ 1.0.4 (F2J) | 84.3178931 | 110.821428 |
| 64 | solve | MTJ 1.0.4 (F2J) | 0.0525266492 | 0.00645913644 |
| 256 | solve | MTJ 1.0.4 (F2J) | 2.65519247 | 0.989414043 |
| 64 | transpose | ojAlgo 57.5.0 | 0.00839125293 | 0.00471461274 |
| 256 | transpose | ojAlgo 57.5.0 | 0.184281886 | 0.0965473282 |
| 64 | addition | ojAlgo 57.5.0 | 0.00506821289 | 0.0022397891 |
| 256 | addition | ojAlgo 57.5.0 | 0.0900792281 | 0.0145758137 |
| 64 | scale | ojAlgo 57.5.0 | 0.00513514087 | 0.00171703082 |
| 256 | scale | ojAlgo 57.5.0 | 0.0568915197 | 0.0147109796 |
| 64 | multiplication | ojAlgo 57.5.0 | 0.086685162 | 0.00734651909 |
| 256 | multiplication | ojAlgo 57.5.0 | 3.78043549 | 0.234905159 |
| 64 | determinant | ojAlgo 57.5.0 | 0.054460162 | 0.0028333536 |
| 256 | determinant | ojAlgo 57.5.0 | 1.74818137 | 0.0937001107 |
| 64 | inverse | ojAlgo 57.5.0 | 0.208092702 | 0.0401906757 |
| 256 | inverse | ojAlgo 57.5.0 | 12.6389417 | 2.9625044 |
| 64 | inverseSPD | ojAlgo 57.5.0 | 0.138692248 | 0.0493429937 |
| 256 | inverseSPD | ojAlgo 57.5.0 | 6.70094675 | 0.667424133 |
| 64 | cholesky | ojAlgo 57.5.0 | 0.0328825527 | 0.00465012715 |
| 256 | cholesky | ojAlgo 57.5.0 | 1.05771481 | 0.137379875 |
| 64 | lu | ojAlgo 57.5.0 | 0.067817609 | 0.00897808459 |
| 256 | lu | ojAlgo 57.5.0 | 2.07720018 | 0.167748668 |
| 64 | qr | ojAlgo 57.5.0 | 0.237606321 | 0.034112765 |
| 256 | qr | ojAlgo 57.5.0 | 7.34224766 | 0.671239479 |
| 64 | eigenSymmetric | ojAlgo 57.5.0 | 1.01827802 | 1.03219274 |
| 256 | eigenSymmetric | ojAlgo 57.5.0 | 27.3966369 | 15.8723831 |
| 64 | svd | ojAlgo 57.5.0 | 1.52307805 | 0.628887143 |
| 256 | svd | ojAlgo 57.5.0 | 48.4708333 | 15.5711563 |
| 64 | solve | ojAlgo 57.5.0 | 0.0516403178 | 0.00529747439 |
| 256 | solve | ojAlgo 57.5.0 | 1.86139081 | 0.312605543 |
| 64 | transpose | UJMP 0.3.0 (core) | 0.0380661436 | 0.0459475827 |
| 256 | transpose | UJMP 0.3.0 (core) | 0.79883972 | 0.354777553 |
| 64 | addition | UJMP 0.3.0 (core) | 0.0087376378 | 0.00345910038 |
| 256 | addition | UJMP 0.3.0 (core) | 0.29337148 | 0.0589004792 |
| 64 | scale | UJMP 0.3.0 (core) | 0.00784209266 | 0.00316791557 |
| 256 | scale | UJMP 0.3.0 (core) | 0.282601169 | 0.0487152506 |
| 64 | multiplication | UJMP 0.3.0 (core) | 0.222447603 | 0.0389277846 |
| 256 | multiplication | UJMP 0.3.0 (core) | 23.596172 | 7.57748701 |
| 64 | determinant | UJMP 0.3.0 (core) | 0.0729682447 | 0.0114228801 |
| 256 | determinant | UJMP 0.3.0 (core) | 4.88764613 | 0.525850578 |
| 64 | inverse | UJMP 0.3.0 (core) | 0.25841131 | 0.058507655 |
| 256 | inverse | UJMP 0.3.0 (core) | 16.1132015 | 4.23135328 |
| 64 | inverseSPD | UJMP 0.3.0 (core) | 0.388341753 | 0.106196795 |
| 256 | inverseSPD | UJMP 0.3.0 (core) | 31.7938524 | 2.75719649 |
| 64 | cholesky | UJMP 0.3.0 (core) | 0.0496971388 | 0.0102405761 |
| 256 | cholesky | UJMP 0.3.0 (core) | 2.64998224 | 0.145209562 |
| 64 | lu | UJMP 0.3.0 (core) | 0.0904050755 | 0.0133953029 |
| 256 | lu | UJMP 0.3.0 (core) | 4.9823192 | 0.339671392 |
| 64 | qr | UJMP 0.3.0 (core) | 0.444157916 | 0.0440743133 |
| 256 | qr | UJMP 0.3.0 (core) | 42.62069 | 3.50256911 |
| 64 | eigenSymmetric | UJMP 0.3.0 (core) | 0.994939046 | 0.565951914 |
| 256 | eigenSymmetric | UJMP 0.3.0 (core) | 63.5285167 | 5.47212862 |
| 64 | svd | UJMP 0.3.0 (core) | 7.16363553 | 3.4801226 |
| 256 | svd | UJMP 0.3.0 (core) | 196.846825 | 27.4354263 |
| 64 | solve | UJMP 0.3.0 (core) | 0.085403407 | 0.00593137807 |
| 256 | solve | UJMP 0.3.0 (core) | 5.0061127 | 0.357448284 |

## Large — 1 requested compute thread(s)

[Interactive chart](benchmarks-large-1thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 512 | multiplication | NM Dev 1.2.10 | 31.8382134 | 18.0475869 |
| 1024 | multiplication | NM Dev 1.2.10 | 508.120867 | 78.5333039 |
| 2000 | multiplication | NM Dev 1.2.10 | 2233.11593 | 120.340302 |
| 512 | lu | NM Dev 1.2.10 | 28.3639122 | 9.12791762 |
| 1024 | lu | NM Dev 1.2.10 | 221.992483 | 20.9991662 |
| 2000 | lu | NM Dev 1.2.10 | 1935.85542 | 168.986354 |
| 512 | cholesky | NM Dev 1.2.10 | 24.0113574 | 1.2454973 |
| 1024 | cholesky | NM Dev 1.2.10 | 189.0951 | 4.54844449 |
| 2000 | cholesky | NM Dev 1.2.10 | 1415.03667 | 21.4670641 |
| 512 | qr | NM Dev 1.2.10 | 144.916858 | 9.62955664 |
| 1024 | qr | NM Dev 1.2.10 | 1084.32217 | 63.7819744 |
| 2000 | qr | NM Dev 1.2.10 | 7799.73642 | 412.351959 |
| 512 | solve | NM Dev 1.2.10 | 31.4002578 | 9.34055022 |
| 1024 | solve | NM Dev 1.2.10 | 184.582633 | 41.3198718 |
| 2000 | solve | NM Dev 1.2.10 | 2281.58413 | 533.463412 |
| 512 | multiplication | EJML 0.46.1 | 56.1290054 | 87.5561473 |
| 1024 | multiplication | EJML 0.46.1 | 642.23745 | 104.394127 |
| 2000 | multiplication | EJML 0.46.1 | 3788.18718 | 1076.37194 |
| 512 | lu | EJML 0.46.1 | 44.22563 | 2.30678497 |
| 1024 | lu | EJML 0.46.1 | 423.55785 | 63.5360001 |
| 2000 | lu | EJML 0.46.1 | 3173.84315 | 126.60855 |
| 512 | cholesky | EJML 0.46.1 | 22.9274148 | 0.738987008 |
| 1024 | cholesky | EJML 0.46.1 | 72.5271611 | 0.976554227 |
| 2000 | cholesky | EJML 0.46.1 | 496.141817 | 9.80602465 |
| 512 | qr | EJML 0.46.1 | 79.8435833 | 3.14362299 |
| 1024 | qr | EJML 0.46.1 | 793.304717 | 80.988222 |
| 2000 | qr | EJML 0.46.1 | 6674.97362 | 217.376959 |
| 512 | solve | EJML 0.46.1 | 44.1545467 | 0.969282615 |
| 1024 | solve | EJML 0.46.1 | 427.308633 | 33.179967 |
| 2000 | solve | EJML 0.46.1 | 3308.44232 | 329.792976 |
| 512 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 259.114783 | 124.215624 |
| 1024 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 7991.69732 | 432.522171 |
| 2000 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 87942.6725 | 5428.51867 |
| 512 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 58.4539125 | 8.02658802 |
| 1024 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 966.271083 | 261.566012 |
| 2000 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 19800.2628 | 919.83851 |
| 512 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 16.1855895 | 1.70188798 |
| 1024 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 133.213442 | 9.51463952 |
| 2000 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 1188.38017 | 61.8690661 |
| 512 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 127.033142 | 42.7464704 |
| 1024 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 1065.53433 | 320.016631 |
| 2000 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 8349.7113 | 2237.11557 |
| 512 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 59.0353208 | 1.78078283 |
| 1024 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 1131.04225 | 73.8357583 |
| 2000 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 19480.8634 | 263.12728 |
| 512 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 70.3230056 | 1.02112883 |
| 1024 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 570.828967 | 25.0883214 |
| 2000 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 4214.44497 | 30.5572231 |
| 512 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 56.5932042 | 1.65680914 |
| 1024 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 968.483583 | 297.389193 |
| 2000 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 19812.3762 | 1294.09152 |
| 512 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 15.6534452 | 0.892483246 |
| 1024 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 133.935875 | 16.9730476 |
| 2000 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 1212.35138 | 59.0049672 |
| 512 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 114.106425 | 13.6173483 |
| 1024 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 944.724917 | 15.5543988 |
| 2000 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 7769.78413 | 2207.39434 |
| 512 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 55.9038333 | 4.37035996 |
| 1024 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 1091.37738 | 73.1349095 |
| 2000 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 19258.1063 | 686.85663 |
| 512 | multiplication | JAMA 1.0.3 | 142.818283 | 7.02721487 |
| 1024 | multiplication | JAMA 1.0.3 | 1224.58877 | 18.2078624 |
| 2000 | multiplication | JAMA 1.0.3 | 9148.05018 | 55.5776602 |
| 512 | lu | JAMA 1.0.3 | 42.2952733 | 1.63667709 |
| 1024 | lu | JAMA 1.0.3 | 390.494467 | 9.8024468 |
| 2000 | lu | JAMA 1.0.3 | 3317.74673 | 430.35288 |
| 512 | cholesky | JAMA 1.0.3 | 21.9661291 | 3.78385962 |
| 1024 | cholesky | JAMA 1.0.3 | 190.529617 | 9.52641832 |
| 2000 | cholesky | JAMA 1.0.3 | 1554.05542 | 22.7655028 |
| 512 | qr | JAMA 1.0.3 | 454.776867 | 79.2463024 |
| 1024 | qr | JAMA 1.0.3 | 15776.3978 | 684.720338 |
| 2000 | qr | JAMA 1.0.3 | 195164.63 | 2520.79425 |
| 512 | solve | JAMA 1.0.3 | 45.27781 | 5.77806566 |
| 1024 | solve | JAMA 1.0.3 | 423.067467 | 23.7705805 |
| 2000 | solve | JAMA 1.0.3 | 3383.54952 | 469.756709 |
| 512 | multiplication | Colt 1.2.0 | 286.937433 | 11.8272027 |
| 1024 | multiplication | Colt 1.2.0 | 5805.69007 | 1158.74868 |
| 2000 | multiplication | Colt 1.2.0 | 40868.7406 | 71.8258754 |
| 512 | lu | Colt 1.2.0 | 41.0978928 | 3.76983737 |
| 1024 | lu | Colt 1.2.0 | 399.202217 | 14.3084947 |
| 2000 | lu | Colt 1.2.0 | 3181.81175 | 1047.64564 |
| 512 | cholesky | Colt 1.2.0 | 20.7478633 | 0.382771479 |
| 1024 | cholesky | Colt 1.2.0 | 168.543542 | 7.46554367 |
| 2000 | cholesky | Colt 1.2.0 | 1337.56227 | 12.2732698 |
| 512 | qr | Colt 1.2.0 | 661.334117 | 10.6857741 |
| 1024 | qr | Colt 1.2.0 | 10986.8526 | 201.087068 |
| 2000 | qr | Colt 1.2.0 | 122682.744 | 636.371267 |
| 512 | solve | Colt 1.2.0 | 43.54709 | 3.125698 |
| 1024 | solve | Colt 1.2.0 | 401.43145 | 55.624947 |
| 2000 | solve | Colt 1.2.0 | 3024.99105 | 395.815673 |
| 512 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 224.95205 | 20.6645387 |
| 1024 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 5211.56123 | 2417.31723 |
| 2000 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 41110.2642 | 85.2428991 |
| 512 | lu | Parallel Colt 0.11.4 (upstream tag build) | 46.59756 | 2.31635394 |
| 1024 | lu | Parallel Colt 0.11.4 (upstream tag build) | 382.459483 | 62.0881752 |
| 2000 | lu | Parallel Colt 0.11.4 (upstream tag build) | 2965.9102 | 453.534296 |
| 512 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 23.56825 | 0.510756172 |
| 1024 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 185.663758 | 2.37002938 |
| 2000 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 1284.3467 | 16.7645747 |
| 512 | qr | Parallel Colt 0.11.4 (upstream tag build) | 152.334208 | 4.35088143 |
| 1024 | qr | Parallel Colt 0.11.4 (upstream tag build) | 1147.46203 | 11.4751769 |
| 2000 | qr | Parallel Colt 0.11.4 (upstream tag build) | 8284.22058 | 148.996522 |
| 512 | solve | Parallel Colt 0.11.4 (upstream tag build) | 50.4114333 | 2.52504005 |
| 1024 | solve | Parallel Colt 0.11.4 (upstream tag build) | 436.020483 | 74.382545 |
| 2000 | solve | Parallel Colt 0.11.4 (upstream tag build) | 3032.89758 | 431.823851 |
| 512 | multiplication | jblas 1.2.5 (native) | 88.5508889 | 3.90062826 |
| 1024 | multiplication | jblas 1.2.5 (native) | 806.876 | 7.12765239 |
| 2000 | multiplication | jblas 1.2.5 (native) | 6228.21062 | 53.0474637 |
| 512 | lu | jblas 1.2.5 (native) | 33.6786254 | 1.52520387 |
| 1024 | lu | jblas 1.2.5 (native) | 265.17485 | 3.63296643 |
| 2000 | lu | jblas 1.2.5 (native) | 1777.72413 | 27.105928 |
| 512 | cholesky | jblas 1.2.5 (native) | 24.59281 | 1.59898226 |
| 1024 | cholesky | jblas 1.2.5 (native) | 200.251925 | 1.86911175 |
| 2000 | cholesky | jblas 1.2.5 (native) | 1429.0698 | 4.32953786 |
| 512 | qr | jblas 1.2.5 (native) | 186.568033 | 0.795529141 |
| 1024 | qr | jblas 1.2.5 (native) | 1528.86933 | 11.7574327 |
| 2000 | qr | jblas 1.2.5 (native) | 11634.1589 | 61.8263142 |
| 512 | solve | jblas 1.2.5 (native) | 31.2688667 | 0.288552587 |
| 1024 | solve | jblas 1.2.5 (native) | 242.989517 | 24.0150054 |
| 2000 | solve | jblas 1.2.5 (native) | 1691.23677 | 6.00870265 |
| 512 | multiplication | F2J LAPACK/BLAS 0.1 | 29.5849905 | 1.30566213 |
| 1024 | multiplication | F2J LAPACK/BLAS 0.1 | 403.015883 | 10.6374985 |
| 2000 | multiplication | F2J LAPACK/BLAS 0.1 | 3542.75828 | 40.8790856 |
| 512 | lu | F2J LAPACK/BLAS 0.1 | 14.302951 | 0.243725829 |
| 1024 | lu | F2J LAPACK/BLAS 0.1 | 115.408733 | 6.82551594 |
| 2000 | lu | F2J LAPACK/BLAS 0.1 | 699.837417 | 40.6885657 |
| 512 | cholesky | F2J LAPACK/BLAS 0.1 | 8.20112101 | 0.481325869 |
| 1024 | cholesky | F2J LAPACK/BLAS 0.1 | 64.0789208 | 2.53328394 |
| 2000 | cholesky | F2J LAPACK/BLAS 0.1 | 555.426517 | 11.239879 |
| 512 | qr | F2J LAPACK/BLAS 0.1 | 110.624658 | 0.84067105 |
| 1024 | qr | F2J LAPACK/BLAS 0.1 | 928.747967 | 10.276157 |
| 2000 | qr | F2J LAPACK/BLAS 0.1 | 7168.8438 | 98.6030607 |
| 512 | solve | F2J LAPACK/BLAS 0.1 | 12.9459719 | 0.539447236 |
| 1024 | solve | F2J LAPACK/BLAS 0.1 | 100.951494 | 8.53186777 |
| 2000 | solve | F2J LAPACK/BLAS 0.1 | 647.637483 | 38.539096 |
| 512 | multiplication | MTJ 1.0.4 (F2J) | 29.6861333 | 3.56294169 |
| 1024 | multiplication | MTJ 1.0.4 (F2J) | 390.3575 | 22.6674433 |
| 2000 | multiplication | MTJ 1.0.4 (F2J) | 3600.10602 | 317.244127 |
| 512 | lu | MTJ 1.0.4 (F2J) | 13.8878522 | 0.585304534 |
| 1024 | lu | MTJ 1.0.4 (F2J) | 102.813244 | 4.76020769 |
| 2000 | lu | MTJ 1.0.4 (F2J) | 649.729883 | 26.2104948 |
| 512 | cholesky | MTJ 1.0.4 (F2J) | 24.7128648 | 0.564767934 |
| 1024 | cholesky | MTJ 1.0.4 (F2J) | 195.4134 | 3.82783187 |
| 2000 | cholesky | MTJ 1.0.4 (F2J) | 1407.07137 | 22.2473981 |
| 512 | qr | MTJ 1.0.4 (F2J) | 111.4659 | 1.07520427 |
| 1024 | qr | MTJ 1.0.4 (F2J) | 927.715533 | 5.08149743 |
| 2000 | qr | MTJ 1.0.4 (F2J) | 7142.80727 | 36.8054931 |
| 512 | solve | MTJ 1.0.4 (F2J) | 13.196939 | 1.1460186 |
| 1024 | solve | MTJ 1.0.4 (F2J) | 97.144075 | 8.56010654 |
| 2000 | solve | MTJ 1.0.4 (F2J) | 640.23285 | 43.9434426 |
| 512 | multiplication | ojAlgo 57.5.0 | 27.4579854 | 1.63094857 |
| 1024 | multiplication | ojAlgo 57.5.0 | 367.713683 | 15.2773033 |
| 2000 | multiplication | ojAlgo 57.5.0 | 3417.60445 | 135.466383 |
| 512 | lu | ojAlgo 57.5.0 | 13.1872104 | 0.298301106 |
| 1024 | lu | ojAlgo 57.5.0 | 126.764583 | 16.033436 |
| 2000 | lu | ojAlgo 57.5.0 | 1487.50958 | 42.980046 |
| 512 | cholesky | ojAlgo 57.5.0 | 7.02012126 | 0.145863282 |
| 1024 | cholesky | ojAlgo 57.5.0 | 61.4426958 | 3.17658423 |
| 2000 | cholesky | ojAlgo 57.5.0 | 666.2121 | 25.7859016 |
| 512 | qr | ojAlgo 57.5.0 | 53.3352792 | 1.67472008 |
| 1024 | qr | ojAlgo 57.5.0 | 539.317217 | 14.0159651 |
| 2000 | qr | ojAlgo 57.5.0 | 5109.04847 | 239.33811 |
| 512 | solve | ojAlgo 57.5.0 | 12.2613874 | 0.753980165 |
| 1024 | solve | ojAlgo 57.5.0 | 125.439133 | 11.8175456 |
| 2000 | solve | ojAlgo 57.5.0 | 1482.1003 | 74.4299001 |
| 512 | multiplication | UJMP 0.3.0 (core) | 231.194783 | 4.48630536 |
| 1024 | multiplication | UJMP 0.3.0 (core) | 7532.9504 | 53.4447571 |
| 2000 | multiplication | UJMP 0.3.0 (core) | 87271.5337 | 397.299816 |
| 512 | lu | UJMP 0.3.0 (core) | 42.63516 | 1.11212562 |
| 1024 | lu | UJMP 0.3.0 (core) | 389.303283 | 6.8962107 |
| 2000 | lu | UJMP 0.3.0 (core) | 3178.18567 | 14.1241027 |
| 512 | cholesky | UJMP 0.3.0 (core) | 21.5190933 | 1.07604841 |
| 1024 | cholesky | UJMP 0.3.0 (core) | 190.363567 | 2.90572272 |
| 2000 | cholesky | UJMP 0.3.0 (core) | 1548.2195 | 38.729125 |
| 512 | qr | UJMP 0.3.0 (core) | 473.685367 | 26.3303302 |
| 1024 | qr | UJMP 0.3.0 (core) | 15708.6183 | 181.694228 |
| 2000 | qr | UJMP 0.3.0 (core) | 196239.504 | 2102.30259 |
| 512 | solve | UJMP 0.3.0 (core) | 43.0718733 | 0.805902408 |
| 1024 | solve | UJMP 0.3.0 (core) | 397.713867 | 7.17975634 |
| 2000 | solve | UJMP 0.3.0 (core) | 3194.81217 | 42.5939246 |

## Large — 4 requested compute thread(s)

[Interactive chart](benchmarks-large-4thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 512 | multiplication | NM Dev 1.2.10 | 9.48384834 | 2.70147686 |
| 1024 | multiplication | NM Dev 1.2.10 | 111.973006 | 43.2463459 |
| 2000 | multiplication | NM Dev 1.2.10 | 649.77145 | 13.7468776 |
| 512 | lu | NM Dev 1.2.10 | 24.4548963 | 1.28266669 |
| 1024 | lu | NM Dev 1.2.10 | 114.30305 | 13.6955272 |
| 2000 | lu | NM Dev 1.2.10 | 1363.16543 | 197.890021 |
| 512 | cholesky | NM Dev 1.2.10 | 11.6232376 | 1.41365769 |
| 1024 | cholesky | NM Dev 1.2.10 | 71.4456056 | 2.17166303 |
| 2000 | cholesky | NM Dev 1.2.10 | 442.1122 | 6.89992157 |
| 512 | qr | NM Dev 1.2.10 | 80.0269 | 3.7481197 |
| 1024 | qr | NM Dev 1.2.10 | 373.2009 | 12.5273521 |
| 2000 | qr | NM Dev 1.2.10 | 2884.2255 | 84.7098943 |
| 512 | solve | NM Dev 1.2.10 | 26.1918951 | 4.97557103 |
| 1024 | solve | NM Dev 1.2.10 | 116.503875 | 5.25807656 |
| 2000 | solve | NM Dev 1.2.10 | 1338.65452 | 272.816339 |
| 512 | multiplication | EJML 0.46.1 | 26.7127375 | 2.70113606 |
| 1024 | multiplication | EJML 0.46.1 | 371.333067 | 6.54265459 |
| 2000 | multiplication | EJML 0.46.1 | 3471.20303 | 81.9431642 |
| 512 | lu | EJML 0.46.1 | 43.45708 | 0.470041173 |
| 1024 | lu | EJML 0.46.1 | 416.369967 | 37.4387409 |
| 2000 | lu | EJML 0.46.1 | 3246.76238 | 247.712366 |
| 512 | cholesky | EJML 0.46.1 | 22.6760174 | 0.771424034 |
| 1024 | cholesky | EJML 0.46.1 | 71.5451028 | 1.61238773 |
| 2000 | cholesky | EJML 0.46.1 | 490.888383 | 11.4570524 |
| 512 | qr | EJML 0.46.1 | 78.5197722 | 4.91276014 |
| 1024 | qr | EJML 0.46.1 | 738.501983 | 100.23021 |
| 2000 | qr | EJML 0.46.1 | 6205.52402 | 80.4003362 |
| 512 | solve | EJML 0.46.1 | 43.7043667 | 1.31858842 |
| 1024 | solve | EJML 0.46.1 | 412.074367 | 31.1005242 |
| 2000 | solve | EJML 0.46.1 | 3134.62498 | 49.582334 |
| 512 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 211.912967 | 27.2351575 |
| 1024 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 8704.24765 | 225.833812 |
| 2000 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 87337.5037 | 7762.06466 |
| 512 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 56.1759917 | 3.01974406 |
| 1024 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 956.8233 | 471.085043 |
| 2000 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 19653.0865 | 1164.31312 |
| 512 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 16.0820321 | 1.19732515 |
| 1024 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 134.625967 | 7.53144625 |
| 2000 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 1225.88963 | 143.677784 |
| 512 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 115.549333 | 16.5021819 |
| 1024 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 1036.55423 | 287.06641 |
| 2000 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 8874.22687 | 55.486581 |
| 512 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 58.6874417 | 3.00364061 |
| 1024 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 1022.66222 | 281.707896 |
| 2000 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 19527.8709 | 629.196512 |
| 512 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 70.55985 | 1.08626405 |
| 1024 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 566.982133 | 6.72585554 |
| 2000 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 4213.36017 | 21.0423065 |
| 512 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 55.8689667 | 3.2561154 |
| 1024 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 1088.79913 | 302.973551 |
| 2000 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 19825.9857 | 1041.03319 |
| 512 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 15.6797103 | 0.650998335 |
| 1024 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 138.218858 | 20.9644724 |
| 2000 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 1222.1411 | 50.273878 |
| 512 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 114.389925 | 15.6722153 |
| 1024 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 1024.20293 | 307.389519 |
| 2000 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 7470.76493 | 1753.48044 |
| 512 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 55.5663583 | 1.38636658 |
| 1024 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 1000.82522 | 383.915728 |
| 2000 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 19438.5483 | 400.068026 |
| 512 | multiplication | JAMA 1.0.3 | 148.647567 | 8.06691049 |
| 1024 | multiplication | JAMA 1.0.3 | 1244.62985 | 66.9884524 |
| 2000 | multiplication | JAMA 1.0.3 | 9201.70358 | 98.1217476 |
| 512 | lu | JAMA 1.0.3 | 42.5046 | 2.51831708 |
| 1024 | lu | JAMA 1.0.3 | 389.6802 | 13.3923312 |
| 2000 | lu | JAMA 1.0.3 | 3281.7099 | 465.891753 |
| 512 | cholesky | JAMA 1.0.3 | 21.1532133 | 0.610599795 |
| 1024 | cholesky | JAMA 1.0.3 | 195.902342 | 30.6752616 |
| 2000 | cholesky | JAMA 1.0.3 | 1553.58457 | 21.7174055 |
| 512 | qr | JAMA 1.0.3 | 485.100083 | 19.8272204 |
| 1024 | qr | JAMA 1.0.3 | 15687.9567 | 118.356166 |
| 2000 | qr | JAMA 1.0.3 | 201115.551 | 10918.008 |
| 512 | solve | JAMA 1.0.3 | 42.8643933 | 0.588064235 |
| 1024 | solve | JAMA 1.0.3 | 404.88235 | 30.2538234 |
| 2000 | solve | JAMA 1.0.3 | 3194.67313 | 20.331422 |
| 512 | multiplication | Colt 1.2.0 | 275.94345 | 21.7097648 |
| 1024 | multiplication | Colt 1.2.0 | 5690.16173 | 617.402172 |
| 2000 | multiplication | Colt 1.2.0 | 41917.7451 | 213.894233 |
| 512 | lu | Colt 1.2.0 | 41.56732 | 2.86911554 |
| 1024 | lu | Colt 1.2.0 | 390.64375 | 38.1476059 |
| 2000 | lu | Colt 1.2.0 | 2849.3126 | 252.881152 |
| 512 | cholesky | Colt 1.2.0 | 20.6203583 | 0.472579067 |
| 1024 | cholesky | Colt 1.2.0 | 174.536567 | 30.009398 |
| 2000 | cholesky | Colt 1.2.0 | 1343.83088 | 28.5456831 |
| 512 | qr | Colt 1.2.0 | 658.356317 | 12.069167 |
| 1024 | qr | Colt 1.2.0 | 10554.3128 | 1065.45384 |
| 2000 | qr | Colt 1.2.0 | 124439.291 | 965.44515 |
| 512 | solve | Colt 1.2.0 | 48.5304375 | 11.7188058 |
| 1024 | solve | Colt 1.2.0 | 418.386683 | 24.3995283 |
| 2000 | solve | Colt 1.2.0 | 2958.00065 | 126.706549 |
| 512 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 78.4956236 | 42.0262038 |
| 1024 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 1324.33648 | 59.272671 |
| 2000 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 11089.6818 | 226.319754 |
| 512 | lu | Parallel Colt 0.11.4 (upstream tag build) | 41.19764 | 1.05919711 |
| 1024 | lu | Parallel Colt 0.11.4 (upstream tag build) | 354.7689 | 32.5097863 |
| 2000 | lu | Parallel Colt 0.11.4 (upstream tag build) | 2884.6873 | 54.7884077 |
| 512 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 15.1203212 | 1.20365834 |
| 1024 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 72.1657194 | 12.6057064 |
| 2000 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 388.252083 | 27.957535 |
| 512 | qr | Parallel Colt 0.11.4 (upstream tag build) | 69.0677583 | 9.4445769 |
| 1024 | qr | Parallel Colt 0.11.4 (upstream tag build) | 395.193917 | 48.344493 |
| 2000 | qr | Parallel Colt 0.11.4 (upstream tag build) | 2362.9018 | 71.1535426 |
| 512 | solve | Parallel Colt 0.11.4 (upstream tag build) | 45.32825 | 3.86383085 |
| 1024 | solve | Parallel Colt 0.11.4 (upstream tag build) | 395.243067 | 47.7454334 |
| 2000 | solve | Parallel Colt 0.11.4 (upstream tag build) | 2953.70302 | 51.5701333 |
| 512 | multiplication | jblas 1.2.5 (native) | 90.2859278 | 5.95456338 |
| 1024 | multiplication | jblas 1.2.5 (native) | 812.846783 | 17.6717164 |
| 2000 | multiplication | jblas 1.2.5 (native) | 6242.73322 | 84.575696 |
| 512 | lu | jblas 1.2.5 (native) | 34.3402016 | 3.96047445 |
| 1024 | lu | jblas 1.2.5 (native) | 265.82215 | 2.29394374 |
| 2000 | lu | jblas 1.2.5 (native) | 1793.02628 | 49.4979109 |
| 512 | cholesky | jblas 1.2.5 (native) | 24.3227648 | 1.17771959 |
| 1024 | cholesky | jblas 1.2.5 (native) | 199.567283 | 0.687136943 |
| 2000 | cholesky | jblas 1.2.5 (native) | 1432.6091 | 7.41682758 |
| 512 | qr | jblas 1.2.5 (native) | 186.468792 | 2.68489127 |
| 1024 | qr | jblas 1.2.5 (native) | 1530.24402 | 10.8069732 |
| 2000 | qr | jblas 1.2.5 (native) | 11643.8919 | 100.212686 |
| 512 | solve | jblas 1.2.5 (native) | 31.4752119 | 0.959992337 |
| 1024 | solve | jblas 1.2.5 (native) | 239.665117 | 9.34409584 |
| 2000 | solve | jblas 1.2.5 (native) | 1697.72582 | 13.3851107 |
| 512 | multiplication | F2J LAPACK/BLAS 0.1 | 30.3133976 | 0.8530601 |
| 1024 | multiplication | F2J LAPACK/BLAS 0.1 | 409.568117 | 13.9488667 |
| 2000 | multiplication | F2J LAPACK/BLAS 0.1 | 3561.03732 | 49.2580872 |
| 512 | lu | F2J LAPACK/BLAS 0.1 | 14.4680575 | 0.701566784 |
| 1024 | lu | F2J LAPACK/BLAS 0.1 | 117.576433 | 8.92623381 |
| 2000 | lu | F2J LAPACK/BLAS 0.1 | 696.707617 | 48.9558992 |
| 512 | cholesky | F2J LAPACK/BLAS 0.1 | 8.26383802 | 0.567609755 |
| 1024 | cholesky | F2J LAPACK/BLAS 0.1 | 64.1997542 | 3.7924739 |
| 2000 | cholesky | F2J LAPACK/BLAS 0.1 | 553.715317 | 28.8537745 |
| 512 | qr | F2J LAPACK/BLAS 0.1 | 110.814108 | 1.52850696 |
| 1024 | qr | F2J LAPACK/BLAS 0.1 | 935.4766 | 32.9657306 |
| 2000 | qr | F2J LAPACK/BLAS 0.1 | 7139.16272 | 81.2824727 |
| 512 | solve | F2J LAPACK/BLAS 0.1 | 13.0814053 | 0.658142726 |
| 1024 | solve | F2J LAPACK/BLAS 0.1 | 96.4274833 | 5.54597625 |
| 2000 | solve | F2J LAPACK/BLAS 0.1 | 641.654817 | 26.3209414 |
| 512 | multiplication | MTJ 1.0.4 (F2J) | 30.6148976 | 4.81324212 |
| 1024 | multiplication | MTJ 1.0.4 (F2J) | 399.4254 | 19.8985575 |
| 2000 | multiplication | MTJ 1.0.4 (F2J) | 3552.94595 | 39.9186816 |
| 512 | lu | MTJ 1.0.4 (F2J) | 13.6393875 | 0.782008638 |
| 1024 | lu | MTJ 1.0.4 (F2J) | 101.811183 | 2.85353123 |
| 2000 | lu | MTJ 1.0.4 (F2J) | 646.694433 | 46.2515974 |
| 512 | cholesky | MTJ 1.0.4 (F2J) | 24.8238157 | 0.519151013 |
| 1024 | cholesky | MTJ 1.0.4 (F2J) | 194.473208 | 1.66242902 |
| 2000 | cholesky | MTJ 1.0.4 (F2J) | 1408.03837 | 19.0032094 |
| 512 | qr | MTJ 1.0.4 (F2J) | 112.407625 | 2.48281488 |
| 1024 | qr | MTJ 1.0.4 (F2J) | 939.6199 | 16.5564499 |
| 2000 | qr | MTJ 1.0.4 (F2J) | 7166.79825 | 226.338799 |
| 512 | solve | MTJ 1.0.4 (F2J) | 13.1578845 | 1.85159809 |
| 1024 | solve | MTJ 1.0.4 (F2J) | 104.6558 | 5.67923838 |
| 2000 | solve | MTJ 1.0.4 (F2J) | 690.500383 | 64.116998 |
| 512 | multiplication | ojAlgo 57.5.0 | 10.9385414 | 5.89615224 |
| 1024 | multiplication | ojAlgo 57.5.0 | 121.870158 | 21.7352099 |
| 2000 | multiplication | ojAlgo 57.5.0 | 1364.10113 | 513.192021 |
| 512 | lu | ojAlgo 57.5.0 | 14.1883115 | 2.06407417 |
| 1024 | lu | ojAlgo 57.5.0 | 143.341508 | 39.0286572 |
| 2000 | lu | ojAlgo 57.5.0 | 1889.04355 | 655.064872 |
| 512 | cholesky | ojAlgo 57.5.0 | 22.286775 | 1.16820696 |
| 1024 | cholesky | ojAlgo 57.5.0 | 118.104644 | 147.392435 |
| 2000 | cholesky | ojAlgo 57.5.0 | 1009.57862 | 793.25368 |
| 512 | qr | ojAlgo 57.5.0 | 68.25915 | 28.6443224 |
| 1024 | qr | ojAlgo 57.5.0 | 585.988467 | 362.58788 |
| 2000 | qr | ojAlgo 57.5.0 | 5787.25888 | 2427.10126 |
| 512 | solve | ojAlgo 57.5.0 | 15.5582521 | 9.48298756 |
| 1024 | solve | ojAlgo 57.5.0 | 136.420133 | 37.065229 |
| 2000 | solve | ojAlgo 57.5.0 | 1777.08212 | 569.438522 |
| 512 | multiplication | UJMP 0.3.0 (core) | 124.327306 | 85.47365 |
| 1024 | multiplication | UJMP 0.3.0 (core) | 2235.55502 | 461.67598 |
| 2000 | multiplication | UJMP 0.3.0 (core) | 27808.631 | 3229.70526 |
| 512 | lu | UJMP 0.3.0 (core) | 45.17918 | 3.12182851 |
| 1024 | lu | UJMP 0.3.0 (core) | 480.523633 | 110.234312 |
| 2000 | lu | UJMP 0.3.0 (core) | 3287.04783 | 317.038704 |
| 512 | cholesky | UJMP 0.3.0 (core) | 22.3002798 | 0.967087411 |
| 1024 | cholesky | UJMP 0.3.0 (core) | 219.1786 | 51.0845156 |
| 2000 | cholesky | UJMP 0.3.0 (core) | 1587.81097 | 57.1976265 |
| 512 | qr | UJMP 0.3.0 (core) | 495.04465 | 46.5030982 |
| 1024 | qr | UJMP 0.3.0 (core) | 15895.4787 | 160.617493 |
| 2000 | qr | UJMP 0.3.0 (core) | 203528.744 | 14487.1684 |
| 512 | solve | UJMP 0.3.0 (core) | 55.1934706 | 24.0122645 |
| 1024 | solve | UJMP 0.3.0 (core) | 446.9776 | 53.5428471 |
| 2000 | solve | UJMP 0.3.0 (core) | 3429.26987 | 180.019326 |

## Rectangular — 1 requested compute thread(s)

[Interactive chart](benchmarks-rectangular-1thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 128x512x64 | multiplication | NM Dev 1.2.10 | 1.54407437 | 0.111908126 |
| 512x128x256 | multiplication | NM Dev 1.2.10 | 5.98305917 | 0.379580709 |
| 1024x256x64 | multiplication | NM Dev 1.2.10 | 6.20044326 | 1.75929524 |
| 255x257x129 | multiplication | NM Dev 1.2.10 | 3.13618002 | 0.327347672 |
| 128x512x64 | multiplication | EJML 0.46.1 | 1.83603982 | 0.813079549 |
| 512x128x256 | multiplication | EJML 0.46.1 | 4.9526904 | 3.56242112 |
| 1024x256x64 | multiplication | EJML 0.46.1 | 6.31617406 | 0.767335376 |
| 255x257x129 | multiplication | EJML 0.46.1 | 2.65647366 | 0.331425975 |
| 128x512x64 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 5.36671536 | 1.04777646 |
| 512x128x256 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 19.6535419 | 5.11769884 |
| 1024x256x64 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 26.221799 | 20.4736972 |
| 255x257x129 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 10.4930712 | 0.622552141 |
| 128x512x64 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 2.32172086 | 0.201456717 |
| 512x128x256 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 9.50728553 | 0.593297142 |
| 1024x256x64 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 9.50197655 | 0.522081488 |
| 255x257x129 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 5.08379444 | 0.638714869 |
| 128x512x64 | multiplication | JAMA 1.0.3 | 4.25758181 | 0.115673821 |
| 512x128x256 | multiplication | JAMA 1.0.3 | 13.206399 | 0.271872707 |
| 1024x256x64 | multiplication | JAMA 1.0.3 | 16.2501458 | 1.39291859 |
| 255x257x129 | multiplication | JAMA 1.0.3 | 7.84032377 | 0.205524158 |
| 128x512x64 | multiplication | Colt 1.2.0 | 5.06550539 | 0.523127055 |
| 512x128x256 | multiplication | Colt 1.2.0 | 20.4164085 | 0.416204268 |
| 1024x256x64 | multiplication | Colt 1.2.0 | 14.0035111 | 0.622570815 |
| 255x257x129 | multiplication | Colt 1.2.0 | 7.37312788 | 0.784324368 |
| 128x512x64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 4.82052452 | 0.534456176 |
| 512x128x256 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 21.8826422 | 4.74635273 |
| 1024x256x64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 13.0614973 | 0.659271709 |
| 255x257x129 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 6.55340341 | 0.573800013 |
| 128x512x64 | multiplication | jblas 1.2.5 (native) | 3.02374428 | 0.180936952 |
| 512x128x256 | multiplication | jblas 1.2.5 (native) | 12.2012163 | 1.76808121 |
| 1024x256x64 | multiplication | jblas 1.2.5 (native) | 20.0748302 | 20.2035566 |
| 255x257x129 | multiplication | jblas 1.2.5 (native) | 7.30422653 | 2.66772073 |
| 128x512x64 | multiplication | F2J LAPACK/BLAS 0.1 | 1.46051154 | 0.163390037 |
| 512x128x256 | multiplication | F2J LAPACK/BLAS 0.1 | 3.7962175 | 0.27622132 |
| 1024x256x64 | multiplication | F2J LAPACK/BLAS 0.1 | 4.09297148 | 0.395970301 |
| 255x257x129 | multiplication | F2J LAPACK/BLAS 0.1 | 2.40436646 | 0.177049924 |
| 128x512x64 | multiplication | MTJ 1.0.4 (F2J) | 1.42990199 | 0.376405484 |
| 512x128x256 | multiplication | MTJ 1.0.4 (F2J) | 4.14639385 | 1.22514782 |
| 1024x256x64 | multiplication | MTJ 1.0.4 (F2J) | 4.2229843 | 0.716141748 |
| 255x257x129 | multiplication | MTJ 1.0.4 (F2J) | 2.33801031 | 0.151465287 |
| 128x512x64 | multiplication | ojAlgo 57.5.0 | 1.19045008 | 0.0340108308 |
| 512x128x256 | multiplication | ojAlgo 57.5.0 | 3.45487537 | 0.150747164 |
| 1024x256x64 | multiplication | ojAlgo 57.5.0 | 3.75847998 | 0.535675812 |
| 255x257x129 | multiplication | ojAlgo 57.5.0 | 2.26530811 | 0.157112211 |
| 128x512x64 | multiplication | UJMP 0.3.0 (core) | 5.37918674 | 0.346177336 |
| 512x128x256 | multiplication | UJMP 0.3.0 (core) | 28.817261 | 7.55882979 |
| 1024x256x64 | multiplication | UJMP 0.3.0 (core) | 36.0874583 | 2.64629603 |
| 255x257x129 | multiplication | UJMP 0.3.0 (core) | 10.0206027 | 0.572021032 |

## Baseline-All-Cores — 12 requested compute thread(s)

[Interactive chart](benchmarks-baseline-all-cores-12thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 64 | transpose | NM Dev 1.2.10 | 0.00544483511 | 0.00151472862 |
| 256 | transpose | NM Dev 1.2.10 | 0.136164592 | 0.0290331051 |
| 64 | addition | NM Dev 1.2.10 | 0.00341981688 | 0.000782613821 |
| 256 | addition | NM Dev 1.2.10 | 0.0944911715 | 0.0215956297 |
| 64 | scale | NM Dev 1.2.10 | 0.0032552461 | 0.00140385683 |
| 256 | scale | NM Dev 1.2.10 | 0.0915431974 | 0.0268716614 |
| 64 | multiplication | NM Dev 1.2.10 | 0.0928411145 | 0.0036913911 |
| 256 | multiplication | NM Dev 1.2.10 | 1.1732718 | 0.189611352 |
| 64 | determinant | NM Dev 1.2.10 | 0.06227916 | 0.0258979537 |
| 256 | determinant | NM Dev 1.2.10 | 7.9122342 | 0.805096653 |
| 64 | inverse | NM Dev 1.2.10 | 0.288280952 | 0.131508298 |
| 256 | inverse | NM Dev 1.2.10 | 11.7081044 | 4.36890267 |
| 64 | inverseSPD | NM Dev 1.2.10 | 0.257330212 | 0.0590807499 |
| 256 | inverseSPD | NM Dev 1.2.10 | 6.24257348 | 1.42607114 |
| 64 | cholesky | NM Dev 1.2.10 | 0.0446217359 | 0.00495011504 |
| 256 | cholesky | NM Dev 1.2.10 | 2.72724256 | 0.117670524 |
| 64 | lu | NM Dev 1.2.10 | 0.0609970969 | 0.0129494532 |
| 256 | lu | NM Dev 1.2.10 | 8.12083883 | 1.7775352 |
| 64 | qr | NM Dev 1.2.10 | 0.322357553 | 0.0634264087 |
| 256 | qr | NM Dev 1.2.10 | 21.9456094 | 3.3335959 |
| 64 | eigenSymmetric | NM Dev 1.2.10 | 4.37259437 | 2.64747777 |
| 256 | eigenSymmetric | NM Dev 1.2.10 | 43.90262 | 5.18890719 |
| 64 | svd | NM Dev 1.2.10 | 2.37960882 | 0.9387906 |
| 256 | svd | NM Dev 1.2.10 | 73.5713167 | 7.18424525 |
| 64 | solve | NM Dev 1.2.10 | 0.292199687 | 0.34873186 |
| 256 | solve | NM Dev 1.2.10 | 8.89891005 | 2.62750134 |
| 64 | transpose | EJML 0.46.1 | 0.00491429969 | 0.000900484868 |
| 256 | transpose | EJML 0.46.1 | 0.151191179 | 0.0210322232 |
| 64 | addition | EJML 0.46.1 | 0.00336069478 | 0.000498837116 |
| 256 | addition | EJML 0.46.1 | 0.0610102335 | 0.00590058289 |
| 64 | scale | EJML 0.46.1 | 0.00309768229 | 0.000744056839 |
| 256 | scale | EJML 0.46.1 | 0.0547744343 | 0.0144874712 |
| 64 | multiplication | EJML 0.46.1 | 0.0932699011 | 0.00619668853 |
| 256 | multiplication | EJML 0.46.1 | 3.87095649 | 0.819548376 |
| 64 | determinant | EJML 0.46.1 | 0.0856290137 | 0.0130313698 |
| 256 | determinant | EJML 0.46.1 | 5.59386147 | 0.903463284 |
| 64 | inverse | EJML 0.46.1 | 0.307630881 | 0.0349928024 |
| 256 | inverse | EJML 0.46.1 | 18.1103826 | 0.821951326 |
| 64 | inverseSPD | EJML 0.46.1 | 0.147652335 | 0.0451452955 |
| 256 | inverseSPD | EJML 0.46.1 | 10.149301 | 0.708499088 |
| 64 | cholesky | EJML 0.46.1 | 0.0451929057 | 0.00337077662 |
| 256 | cholesky | EJML 0.46.1 | 2.79175444 | 0.186707879 |
| 64 | lu | EJML 0.46.1 | 0.0920253713 | 0.0106713705 |
| 256 | lu | EJML 0.46.1 | 5.4967428 | 0.552144674 |
| 64 | qr | EJML 0.46.1 | 0.242245549 | 0.0818199918 |
| 256 | qr | EJML 0.46.1 | 10.7151003 | 1.37364635 |
| 64 | eigenSymmetric | EJML 0.46.1 | 0.558940098 | 0.0599044977 |
| 256 | eigenSymmetric | EJML 0.46.1 | 22.1788287 | 0.77262268 |
| 64 | svd | EJML 0.46.1 | 0.934226983 | 0.185817854 |
| 256 | svd | EJML 0.46.1 | 54.6344394 | 41.4757874 |
| 64 | solve | EJML 0.46.1 | 0.0937477765 | 0.0216146696 |
| 256 | solve | EJML 0.46.1 | 5.31748689 | 0.328239285 |
| 64 | transpose | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.00733233249 | 0.00364564563 |
| 256 | transpose | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.158889081 | 0.0477647084 |
| 64 | addition | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.00511652345 | 0.00182837065 |
| 256 | addition | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.0843281189 | 0.025092741 |
| 64 | scale | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.0046025977 | 0.00067589878 |
| 256 | scale | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.0744118607 | 0.0229350662 |
| 64 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.302632025 | 0.0909741503 |
| 256 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 22.16452 | 7.38351896 |
| 64 | determinant | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.0955116798 | 0.0147455927 |
| 256 | determinant | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 6.24926851 | 0.253951704 |
| 64 | inverse | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.165610968 | 0.0309266696 |
| 256 | inverse | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 10.2091979 | 0.677430383 |
| 64 | inverseSPD | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.116760249 | 0.00962864107 |
| 256 | inverseSPD | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 5.85822806 | 0.493282342 |
| 64 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.050861397 | 0.00222333786 |
| 256 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 2.20384059 | 0.280144553 |
| 64 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.100465345 | 0.00498774243 |
| 256 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 6.55040744 | 0.285082799 |
| 64 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.265120073 | 0.0467123858 |
| 256 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 14.8082702 | 0.795198941 |
| 64 | eigenSymmetric | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.782268481 | 0.0769655924 |
| 256 | eigenSymmetric | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 60.0619083 | 36.7216 |
| 64 | svd | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 35.7792317 | 73.5720723 |
| 256 | svd | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 238.901083 | 56.2573718 |
| 64 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 0.105246685 | 0.00595484493 |
| 256 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 7.1325676 | 1.4742133 |
| 64 | transpose | Commons Math 4.0-beta1 - BlockRealMatrix | 0.00586878723 | 0.00166862291 |
| 256 | transpose | Commons Math 4.0-beta1 - BlockRealMatrix | 0.101198917 | 0.0195592034 |
| 64 | addition | Commons Math 4.0-beta1 - BlockRealMatrix | 0.00411654706 | 0.000623763834 |
| 256 | addition | Commons Math 4.0-beta1 - BlockRealMatrix | 0.0816999127 | 0.0120528062 |
| 64 | scale | Commons Math 4.0-beta1 - BlockRealMatrix | 0.00365060029 | 0.00019703201 |
| 256 | scale | Commons Math 4.0-beta1 - BlockRealMatrix | 0.0739268641 | 0.0153525346 |
| 64 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 0.168858567 | 0.0357473915 |
| 256 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 9.14125794 | 0.649368076 |
| 64 | determinant | Commons Math 4.0-beta1 - BlockRealMatrix | 0.0917690621 | 0.00485556325 |
| 256 | determinant | Commons Math 4.0-beta1 - BlockRealMatrix | 6.1879784 | 0.393813753 |
| 64 | inverse | Commons Math 4.0-beta1 - BlockRealMatrix | 0.160451718 | 0.0161611951 |
| 256 | inverse | Commons Math 4.0-beta1 - BlockRealMatrix | 10.5781816 | 0.692519416 |
| 64 | inverseSPD | Commons Math 4.0-beta1 - BlockRealMatrix | 0.135646079 | 0.0877842931 |
| 256 | inverseSPD | Commons Math 4.0-beta1 - BlockRealMatrix | 7.52524313 | 2.60012392 |
| 64 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 0.0556617334 | 0.0139876911 |
| 256 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 2.21462061 | 0.152831729 |
| 64 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 0.103981923 | 0.0221228428 |
| 256 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 7.07701276 | 1.78726633 |
| 64 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 0.270397391 | 0.041698705 |
| 256 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 15.4923741 | 2.53765612 |
| 64 | eigenSymmetric | Commons Math 4.0-beta1 - BlockRealMatrix | 0.798325762 | 0.150664634 |
| 256 | eigenSymmetric | Commons Math 4.0-beta1 - BlockRealMatrix | 52.4256958 | 2.39300991 |
| 64 | svd | Commons Math 4.0-beta1 - BlockRealMatrix | 45.2455623 | 130.930993 |
| 256 | svd | Commons Math 4.0-beta1 - BlockRealMatrix | 256.7397 | 168.118029 |
| 64 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 0.118134295 | 0.0193559908 |
| 256 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 6.77101331 | 0.623100315 |
| 64 | transpose | JAMA 1.0.3 | 0.00717080964 | 0.00554531018 |
| 256 | transpose | JAMA 1.0.3 | 0.162609991 | 0.0386240754 |
| 64 | addition | JAMA 1.0.3 | 0.00490749171 | 0.00101434853 |
| 256 | addition | JAMA 1.0.3 | 0.0929757736 | 0.0219731718 |
| 64 | scale | JAMA 1.0.3 | 0.00495505105 | 0.00164180935 |
| 256 | scale | JAMA 1.0.3 | 0.0818625237 | 0.026212127 |
| 64 | multiplication | JAMA 1.0.3 | 0.198149318 | 0.0320724208 |
| 256 | multiplication | JAMA 1.0.3 | 15.908091 | 0.729311201 |
| 64 | determinant | JAMA 1.0.3 | 0.0798176872 | 0.031061373 |
| 256 | determinant | JAMA 1.0.3 | 5.97354658 | 3.14539674 |
| 64 | inverse | JAMA 1.0.3 | 0.257095574 | 0.0656509993 |
| 256 | inverse | JAMA 1.0.3 | 14.8346036 | 0.965802461 |
| 64 | inverseSPD | JAMA 1.0.3 | 0.372456317 | 0.0466984896 |
| 256 | inverseSPD | JAMA 1.0.3 | 29.7753619 | 1.52936456 |
| 64 | cholesky | JAMA 1.0.3 | 0.0415999983 | 0.0034783065 |
| 256 | cholesky | JAMA 1.0.3 | 2.44051917 | 0.109202071 |
| 64 | lu | JAMA 1.0.3 | 0.0843216113 | 0.00663168273 |
| 256 | lu | JAMA 1.0.3 | 4.83801613 | 0.321631625 |
| 64 | qr | JAMA 1.0.3 | 0.416396962 | 0.0231190128 |
| 256 | qr | JAMA 1.0.3 | 40.9532167 | 0.861987114 |
| 64 | eigenSymmetric | JAMA 1.0.3 | 0.924298286 | 0.499147583 |
| 256 | eigenSymmetric | JAMA 1.0.3 | 60.65025 | 3.27682485 |
| 64 | svd | JAMA 1.0.3 | 6.34155789 | 2.53374571 |
| 256 | svd | JAMA 1.0.3 | 206.499508 | 28.9513433 |
| 64 | solve | JAMA 1.0.3 | 0.0870276416 | 0.00727845427 |
| 256 | solve | JAMA 1.0.3 | 4.91114284 | 0.242934163 |
| 64 | transpose | Colt 1.2.0 | 0.00628422191 | 0.00329491159 |
| 256 | transpose | Colt 1.2.0 | 0.162341922 | 0.0906974149 |
| 64 | addition | Colt 1.2.0 | 0.00729961804 | 0.00286659162 |
| 256 | addition | Colt 1.2.0 | 0.117033591 | 0.0438762107 |
| 64 | scale | Colt 1.2.0 | 0.00527537588 | 0.00122484237 |
| 256 | scale | Colt 1.2.0 | 0.0872665318 | 0.0370588908 |
| 64 | multiplication | Colt 1.2.0 | 0.234815433 | 0.00966010945 |
| 256 | multiplication | Colt 1.2.0 | 30.6629314 | 11.9876289 |
| 64 | determinant | Colt 1.2.0 | 0.113426245 | 0.0253464278 |
| 256 | determinant | Colt 1.2.0 | 5.72219987 | 0.623822592 |
| 64 | inverse | Colt 1.2.0 | 0.393982631 | 0.141524606 |
| 256 | inverse | Colt 1.2.0 | 19.1628848 | 1.75261661 |
| 64 | inverseSPD | Colt 1.2.0 | 0.415962473 | 0.0411749396 |
| 256 | inverseSPD | Colt 1.2.0 | 41.2580039 | 11.4584986 |
| 64 | cholesky | Colt 1.2.0 | 0.0562141793 | 0.00841468897 |
| 256 | cholesky | Colt 1.2.0 | 2.73719813 | 0.755284888 |
| 64 | lu | Colt 1.2.0 | 0.121715925 | 0.0116161984 |
| 256 | lu | Colt 1.2.0 | 8.14676918 | 5.71145309 |
| 64 | qr | Colt 1.2.0 | 0.514256405 | 0.141771028 |
| 256 | qr | Colt 1.2.0 | 76.6910389 | 25.6471468 |
| 64 | eigenSymmetric | Colt 1.2.0 | 0.923003406 | 0.138332329 |
| 256 | eigenSymmetric | Colt 1.2.0 | 64.8562389 | 9.94593123 |
| 64 | svd | Colt 1.2.0 | 6.81600014 | 3.91385177 |
| 256 | svd | Colt 1.2.0 | 227.006133 | 30.7993208 |
| 64 | solve | Colt 1.2.0 | 0.163062469 | 0.0340545172 |
| 256 | solve | Colt 1.2.0 | 6.53512649 | 0.852146466 |
| 64 | transpose | Parallel Colt 0.11.4 (upstream tag build) | 0.00627088114 | 0.00330019859 |
| 256 | transpose | Parallel Colt 0.11.4 (upstream tag build) | 0.113986526 | 0.0187527161 |
| 64 | addition | Parallel Colt 0.11.4 (upstream tag build) | 0.00675597886 | 0.00115711138 |
| 256 | addition | Parallel Colt 0.11.4 (upstream tag build) | 0.143318388 | 0.0768468052 |
| 64 | scale | Parallel Colt 0.11.4 (upstream tag build) | 0.00606842629 | 0.00402082051 |
| 256 | scale | Parallel Colt 0.11.4 (upstream tag build) | 0.112568493 | 0.0161704629 |
| 64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 0.136494867 | 0.0208909316 |
| 256 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 8.40641462 | 1.34219625 |
| 64 | determinant | Parallel Colt 0.11.4 (upstream tag build) | 0.155661966 | 0.0827392632 |
| 256 | determinant | Parallel Colt 0.11.4 (upstream tag build) | 6.15561978 | 2.00798748 |
| 64 | inverse | Parallel Colt 0.11.4 (upstream tag build) | 0.426939118 | 0.0832435195 |
| 256 | inverse | Parallel Colt 0.11.4 (upstream tag build) | 20.2111071 | 2.35399199 |
| 64 | inverseSPD | Parallel Colt 0.11.4 (upstream tag build) | 4.86374754 | 1.50104635 |
| 256 | inverseSPD | Parallel Colt 0.11.4 (upstream tag build) | 17.7219333 | 0.742609025 |
| 64 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 1.65007995 | 0.765748504 |
| 256 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 6.43690164 | 1.80265161 |
| 64 | lu | Parallel Colt 0.11.4 (upstream tag build) | 0.205010464 | 0.378021697 |
| 256 | lu | Parallel Colt 0.11.4 (upstream tag build) | 7.09269802 | 3.30153597 |
| 64 | qr | Parallel Colt 0.11.4 (upstream tag build) | 3.93402068 | 1.80436823 |
| 256 | qr | Parallel Colt 0.11.4 (upstream tag build) | 20.4668494 | 4.73522288 |
| 64 | eigenSymmetric | Parallel Colt 0.11.4 (upstream tag build) | 1.11775031 | 0.647419073 |
| 256 | eigenSymmetric | Parallel Colt 0.11.4 (upstream tag build) | 63.5655764 | 8.27521997 |
| 64 | svd | Parallel Colt 0.11.4 (upstream tag build) | 4.22645448 | 4.4205534 |
| 256 | svd | Parallel Colt 0.11.4 (upstream tag build) | 75.8439194 | 84.6601327 |
| 64 | solve | Parallel Colt 0.11.4 (upstream tag build) | 0.209275641 | 0.0765082569 |
| 256 | solve | Parallel Colt 0.11.4 (upstream tag build) | 7.24267477 | 0.943998883 |
| 64 | transpose | jblas 1.2.5 (native) | 0.00777695558 | 0.00957822938 |
| 256 | transpose | jblas 1.2.5 (native) | 0.147226961 | 0.0119250571 |
| 64 | addition | jblas 1.2.5 (native) | 0.00340692558 | 0.000768859513 |
| 256 | addition | jblas 1.2.5 (native) | 0.0637019377 | 0.00771449133 |
| 64 | scale | jblas 1.2.5 (native) | 0.00293395035 | 0.000422339471 |
| 256 | scale | jblas 1.2.5 (native) | 0.0537545244 | 0.00569764282 |
| 64 | multiplication | jblas 1.2.5 (native) | 0.221301767 | 0.163527919 |
| 256 | multiplication | jblas 1.2.5 (native) | 12.3200351 | 0.98134118 |
| 64 | determinant | jblas 1.2.5 (native) | 0.105896836 | 0.0122682933 |
| 256 | determinant | jblas 1.2.5 (native) | 5.01251981 | 0.45727015 |
| 64 | inverse | jblas 1.2.5 (native) | 0.284668703 | 0.0312774958 |
| 256 | inverse | jblas 1.2.5 (native) | 14.0189694 | 1.29441229 |
| 64 | inverseSPD | jblas 1.2.5 (native) | 0.308053344 | 0.0204629843 |
| 256 | inverseSPD | jblas 1.2.5 (native) | 19.0831333 | 1.47630674 |
| 64 | cholesky | jblas 1.2.5 (native) | 0.0533176318 | 0.00394574173 |
| 256 | cholesky | jblas 1.2.5 (native) | 3.52524454 | 0.826675018 |
| 64 | lu | jblas 1.2.5 (native) | 0.124973717 | 0.0511209894 |
| 256 | lu | jblas 1.2.5 (native) | 5.62696545 | 2.6974928 |
| 64 | qr | jblas 1.2.5 (native) | 0.529569572 | 0.190421592 |
| 256 | qr | jblas 1.2.5 (native) | 26.2741005 | 3.2166661 |
| 64 | eigenSymmetric | jblas 1.2.5 (native) | 0.85534266 | 0.131906746 |
| 256 | eigenSymmetric | jblas 1.2.5 (native) | 30.8616825 | 4.44349769 |
| 64 | svd | jblas 1.2.5 (native) | 2.26545334 | 0.235897467 |
| 256 | svd | jblas 1.2.5 (native) | 142.08115 | 13.2353654 |
| 64 | solve | jblas 1.2.5 (native) | 0.0981103743 | 0.0129872244 |
| 256 | solve | jblas 1.2.5 (native) | 4.61216269 | 1.72972396 |
| 64 | transpose | F2J LAPACK/BLAS 0.1 | 0.00670919382 | 0.00129188231 |
| 256 | transpose | F2J LAPACK/BLAS 0.1 | 0.378085861 | 0.019624789 |
| 64 | addition | F2J LAPACK/BLAS 0.1 | 0.0168163536 | 0.000669571347 |
| 256 | addition | F2J LAPACK/BLAS 0.1 | 0.302040491 | 0.135654148 |
| 64 | scale | F2J LAPACK/BLAS 0.1 | 0.00309550097 | 0.000490074099 |
| 256 | scale | F2J LAPACK/BLAS 0.1 | 0.0500116463 | 0.00459934335 |
| 64 | multiplication | F2J LAPACK/BLAS 0.1 | 0.0969066361 | 0.0122279693 |
| 256 | multiplication | F2J LAPACK/BLAS 0.1 | 3.93135256 | 0.394387377 |
| 64 | determinant | F2J LAPACK/BLAS 0.1 | 0.0469451574 | 0.00512693661 |
| 256 | determinant | F2J LAPACK/BLAS 0.1 | 2.38468285 | 0.641536221 |
| 64 | inverse | F2J LAPACK/BLAS 0.1 | 0.149201471 | 0.0219933408 |
| 256 | inverse | F2J LAPACK/BLAS 0.1 | 8.01983928 | 1.76115192 |
| 64 | inverseSPD | F2J LAPACK/BLAS 0.1 | 0.132265469 | 0.00701135185 |
| 256 | inverseSPD | F2J LAPACK/BLAS 0.1 | 5.60886426 | 0.487031617 |
| 64 | cholesky | F2J LAPACK/BLAS 0.1 | 0.0418766569 | 0.0176258125 |
| 256 | cholesky | F2J LAPACK/BLAS 0.1 | 1.49331603 | 0.600238589 |
| 64 | lu | F2J LAPACK/BLAS 0.1 | 0.0657085262 | 0.0108525234 |
| 256 | lu | F2J LAPACK/BLAS 0.1 | 2.65538729 | 0.388581188 |
| 64 | qr | F2J LAPACK/BLAS 0.1 | 0.267760949 | 0.0252771666 |
| 256 | qr | F2J LAPACK/BLAS 0.1 | 14.4703721 | 1.00754566 |
| 64 | eigenSymmetric | F2J LAPACK/BLAS 0.1 | 1.12490526 | 0.396793901 |
| 256 | eigenSymmetric | F2J LAPACK/BLAS 0.1 | 28.5718863 | 4.51436738 |
| 64 | svd | F2J LAPACK/BLAS 0.1 | 3.06065799 | 1.32157153 |
| 256 | svd | F2J LAPACK/BLAS 0.1 | 132.241792 | 43.8732279 |
| 64 | solve | F2J LAPACK/BLAS 0.1 | 0.0661081078 | 0.0269500845 |
| 256 | solve | F2J LAPACK/BLAS 0.1 | 2.41531376 | 0.65979971 |
| 64 | transpose | MTJ 1.0.4 (F2J) | 0.0147285977 | 0.00471117646 |
| 256 | transpose | MTJ 1.0.4 (F2J) | 0.639738033 | 0.0291270576 |
| 64 | addition | MTJ 1.0.4 (F2J) | 0.0266796758 | 0.00351770103 |
| 256 | addition | MTJ 1.0.4 (F2J) | 0.381231129 | 0.0147134678 |
| 64 | scale | MTJ 1.0.4 (F2J) | 0.019066598 | 0.00314935743 |
| 256 | scale | MTJ 1.0.4 (F2J) | 0.31348544 | 0.0178464342 |
| 64 | multiplication | MTJ 1.0.4 (F2J) | 0.0971704025 | 0.00492515923 |
| 256 | multiplication | MTJ 1.0.4 (F2J) | 4.50577221 | 0.572931596 |
| 64 | determinant | MTJ 1.0.4 (F2J) | 0.0734943866 | 0.0328669884 |
| 256 | determinant | MTJ 1.0.4 (F2J) | 2.78557375 | 1.20273433 |
| 64 | inverse | MTJ 1.0.4 (F2J) | 0.328090484 | 0.213114147 |
| 256 | inverse | MTJ 1.0.4 (F2J) | 14.8724447 | 3.9522551 |
| 64 | inverseSPD | MTJ 1.0.4 (F2J) | 0.37515028 | 0.0391250836 |
| 256 | inverseSPD | MTJ 1.0.4 (F2J) | 19.7213439 | 0.4184512 |
| 64 | cholesky | MTJ 1.0.4 (F2J) | 0.105881912 | 0.0415052148 |
| 256 | cholesky | MTJ 1.0.4 (F2J) | 3.61134324 | 0.538748632 |
| 64 | lu | MTJ 1.0.4 (F2J) | 0.0620345461 | 0.00463165991 |
| 256 | lu | MTJ 1.0.4 (F2J) | 2.51216561 | 0.475956126 |
| 64 | qr | MTJ 1.0.4 (F2J) | 0.317904107 | 0.0750868999 |
| 256 | qr | MTJ 1.0.4 (F2J) | 15.6911231 | 3.76579121 |
| 64 | eigenSymmetric | MTJ 1.0.4 (F2J) | 1.09925168 | 0.539135394 |
| 256 | eigenSymmetric | MTJ 1.0.4 (F2J) | 23.8188535 | 4.30710905 |
| 64 | svd | MTJ 1.0.4 (F2J) | 3.15797979 | 2.06028583 |
| 256 | svd | MTJ 1.0.4 (F2J) | 94.8747278 | 27.3715326 |
| 64 | solve | MTJ 1.0.4 (F2J) | 0.0576855244 | 0.0193877653 |
| 256 | solve | MTJ 1.0.4 (F2J) | 2.52127637 | 0.613196416 |
| 64 | transpose | ojAlgo 57.5.0 | 0.00773493678 | 0.00525001929 |
| 256 | transpose | ojAlgo 57.5.0 | 0.154939063 | 0.0118028446 |
| 64 | addition | ojAlgo 57.5.0 | 0.0042076196 | 0.000420431269 |
| 256 | addition | ojAlgo 57.5.0 | 0.0766525884 | 0.0060631163 |
| 64 | scale | ojAlgo 57.5.0 | 0.00380620318 | 0.000829838212 |
| 256 | scale | ojAlgo 57.5.0 | 0.0555271742 | 0.015233121 |
| 64 | multiplication | ojAlgo 57.5.0 | 0.0797092495 | 0.00906319545 |
| 256 | multiplication | ojAlgo 57.5.0 | 1.09048739 | 0.0712425509 |
| 64 | determinant | ojAlgo 57.5.0 | 0.0542845783 | 0.00492877141 |
| 256 | determinant | ojAlgo 57.5.0 | 1.65905806 | 0.207449295 |
| 64 | inverse | ojAlgo 57.5.0 | 0.200233852 | 0.0131736928 |
| 256 | inverse | ojAlgo 57.5.0 | 5.35614815 | 0.228515227 |
| 64 | inverseSPD | ojAlgo 57.5.0 | 0.123478683 | 0.0165179715 |
| 256 | inverseSPD | ojAlgo 57.5.0 | 7.85739316 | 0.954137488 |
| 64 | cholesky | ojAlgo 57.5.0 | 0.0320992718 | 0.00388483782 |
| 256 | cholesky | ojAlgo 57.5.0 | 4.63097809 | 0.542361154 |
| 64 | lu | ojAlgo 57.5.0 | 0.0685721333 | 0.0112114684 |
| 256 | lu | ojAlgo 57.5.0 | 1.89147917 | 0.11235773 |
| 64 | qr | ojAlgo 57.5.0 | 0.24335057 | 0.0485420558 |
| 256 | qr | ojAlgo 57.5.0 | 10.3151679 | 0.745742659 |
| 64 | eigenSymmetric | ojAlgo 57.5.0 | 0.552986674 | 0.307221343 |
| 256 | eigenSymmetric | ojAlgo 57.5.0 | 22.532595 | 3.34586414 |
| 64 | svd | ojAlgo 57.5.0 | 1.23218552 | 0.248195147 |
| 256 | svd | ojAlgo 57.5.0 | 40.753925 | 9.55713994 |
| 64 | solve | ojAlgo 57.5.0 | 0.0549395252 | 0.0158034888 |
| 256 | solve | ojAlgo 57.5.0 | 1.712688 | 0.039921501 |
| 64 | transpose | UJMP 0.3.0 (core) | 0.0335423819 | 0.0245542436 |
| 256 | transpose | UJMP 0.3.0 (core) | 0.648559705 | 0.157497473 |
| 64 | addition | UJMP 0.3.0 (core) | 0.00824668969 | 0.00236685432 |
| 256 | addition | UJMP 0.3.0 (core) | 0.265995728 | 0.021491347 |
| 64 | scale | UJMP 0.3.0 (core) | 0.0074108138 | 0.0022548726 |
| 256 | scale | UJMP 0.3.0 (core) | 0.260813397 | 0.0180611786 |
| 64 | multiplication | UJMP 0.3.0 (core) | 0.202210018 | 0.0298288543 |
| 256 | multiplication | UJMP 0.3.0 (core) | 5.12960379 | 0.749738235 |
| 64 | determinant | UJMP 0.3.0 (core) | 0.0725924506 | 0.00749864067 |
| 256 | determinant | UJMP 0.3.0 (core) | 5.68830934 | 2.42967308 |
| 64 | inverse | UJMP 0.3.0 (core) | 0.242028364 | 0.0285449623 |
| 256 | inverse | UJMP 0.3.0 (core) | 14.7599643 | 0.721231351 |
| 64 | inverseSPD | UJMP 0.3.0 (core) | 0.361845404 | 0.0148199668 |
| 256 | inverseSPD | UJMP 0.3.0 (core) | 30.654531 | 2.14081536 |
| 64 | cholesky | UJMP 0.3.0 (core) | 0.0449863222 | 0.00517496902 |
| 256 | cholesky | UJMP 0.3.0 (core) | 2.54398699 | 0.271685736 |
| 64 | lu | UJMP 0.3.0 (core) | 0.083978345 | 0.00626922862 |
| 256 | lu | UJMP 0.3.0 (core) | 4.98709329 | 0.402513595 |
| 64 | qr | UJMP 0.3.0 (core) | 0.421741873 | 0.0313900785 |
| 256 | qr | UJMP 0.3.0 (core) | 40.5762472 | 3.75939678 |
| 64 | eigenSymmetric | UJMP 0.3.0 (core) | 0.839340362 | 0.0832461613 |
| 256 | eigenSymmetric | UJMP 0.3.0 (core) | 64.0115625 | 4.50707872 |
| 64 | svd | UJMP 0.3.0 (core) | 5.55528461 | 0.348890339 |
| 256 | svd | UJMP 0.3.0 (core) | 181.022925 | 34.8592246 |
| 64 | solve | UJMP 0.3.0 (core) | 0.0859189541 | 0.00934597603 |
| 256 | solve | UJMP 0.3.0 (core) | 4.87967701 | 0.135849989 |

## Large-All-Cores — 12 requested compute thread(s)

[Interactive chart](benchmarks-large-all-cores-12thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 512 | multiplication | NM Dev 1.2.10 | 7.17002279 | 1.28568157 |
| 1024 | multiplication | NM Dev 1.2.10 | 70.6153514 | 1.93226204 |
| 2000 | multiplication | NM Dev 1.2.10 | 514.796017 | 30.2153488 |
| 512 | lu | NM Dev 1.2.10 | 23.7900093 | 1.68671252 |
| 1024 | lu | NM Dev 1.2.10 | 123.23805 | 29.0310599 |
| 2000 | lu | NM Dev 1.2.10 | 1391.5246 | 127.368966 |
| 512 | cholesky | NM Dev 1.2.10 | 9.73125159 | 0.229737338 |
| 1024 | cholesky | NM Dev 1.2.10 | 49.8086258 | 1.96652073 |
| 2000 | cholesky | NM Dev 1.2.10 | 252.812867 | 13.6330017 |
| 512 | qr | NM Dev 1.2.10 | 72.6211611 | 2.61350578 |
| 1024 | qr | NM Dev 1.2.10 | 326.576267 | 11.4837167 |
| 2000 | qr | NM Dev 1.2.10 | 2884.89173 | 137.700688 |
| 512 | solve | NM Dev 1.2.10 | 25.1222162 | 2.81318974 |
| 1024 | solve | NM Dev 1.2.10 | 122.6088 | 15.0991553 |
| 2000 | solve | NM Dev 1.2.10 | 1361.4816 | 49.5040554 |
| 512 | multiplication | EJML 0.46.1 | 27.2613601 | 4.0388727 |
| 1024 | multiplication | EJML 0.46.1 | 361.235033 | 18.265277 |
| 2000 | multiplication | EJML 0.46.1 | 3492.405 | 136.063012 |
| 512 | lu | EJML 0.46.1 | 44.0001867 | 1.70399527 |
| 1024 | lu | EJML 0.46.1 | 410.600467 | 19.9724186 |
| 2000 | lu | EJML 0.46.1 | 3168.31497 | 39.7503424 |
| 512 | cholesky | EJML 0.46.1 | 22.7245315 | 0.719439921 |
| 1024 | cholesky | EJML 0.46.1 | 72.2986667 | 1.35317416 |
| 2000 | cholesky | EJML 0.46.1 | 498.392467 | 2.86454789 |
| 512 | qr | EJML 0.46.1 | 77.3640056 | 5.05949351 |
| 1024 | qr | EJML 0.46.1 | 724.62575 | 13.1186163 |
| 2000 | qr | EJML 0.46.1 | 6174.66108 | 259.349836 |
| 512 | solve | EJML 0.46.1 | 44.0008333 | 0.766209455 |
| 1024 | solve | EJML 0.46.1 | 412.398683 | 22.8474893 |
| 2000 | solve | EJML 0.46.1 | 3151.77958 | 62.6737637 |
| 512 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 195.281392 | 21.1108315 |
| 1024 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 7694.2554 | 1564.6233 |
| 2000 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 85945.246 | 2317.23586 |
| 512 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 57.2027292 | 5.2395779 |
| 1024 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 924.246317 | 662.762991 |
| 2000 | lu | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 19802.3297 | 1251.25797 |
| 512 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 16.0165321 | 1.09050385 |
| 1024 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 132.2622 | 7.9290391 |
| 2000 | cholesky | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 1212.24575 | 46.9578593 |
| 512 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 115.041475 | 14.9556928 |
| 1024 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 1043.7725 | 296.198345 |
| 2000 | qr | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 7411.13718 | 75.0794061 |
| 512 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 58.5225083 | 2.36129148 |
| 1024 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 896.445017 | 481.488135 |
| 2000 | solve | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 20643.249 | 2633.85723 |
| 512 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 71.2389333 | 4.25744006 |
| 1024 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 577.268383 | 37.8572654 |
| 2000 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 4429.88105 | 313.436203 |
| 512 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 59.1808792 | 3.02285383 |
| 1024 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 988.64145 | 657.853622 |
| 2000 | lu | Commons Math 4.0-beta1 - BlockRealMatrix | 20156.1665 | 2586.29441 |
| 512 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 16.1618553 | 1.10197352 |
| 1024 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 134.466625 | 8.11005639 |
| 2000 | cholesky | Commons Math 4.0-beta1 - BlockRealMatrix | 1277.84987 | 203.729184 |
| 512 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 112.676633 | 2.518784 |
| 1024 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 978.566767 | 25.7248352 |
| 2000 | qr | Commons Math 4.0-beta1 - BlockRealMatrix | 7366.72837 | 197.838494 |
| 512 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 55.6806292 | 2.94919378 |
| 1024 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 864.71595 | 295.346628 |
| 2000 | solve | Commons Math 4.0-beta1 - BlockRealMatrix | 19989.5753 | 859.08339 |
| 512 | multiplication | JAMA 1.0.3 | 149.69435 | 21.5893636 |
| 1024 | multiplication | JAMA 1.0.3 | 1246.1421 | 15.7883495 |
| 2000 | multiplication | JAMA 1.0.3 | 9239.52267 | 100.688847 |
| 512 | lu | JAMA 1.0.3 | 42.6493267 | 1.4810049 |
| 1024 | lu | JAMA 1.0.3 | 388.829083 | 17.4064703 |
| 2000 | lu | JAMA 1.0.3 | 3176.7714 | 25.4190694 |
| 512 | cholesky | JAMA 1.0.3 | 21.123495 | 0.500646785 |
| 1024 | cholesky | JAMA 1.0.3 | 191.431667 | 15.3992703 |
| 2000 | cholesky | JAMA 1.0.3 | 1552.1089 | 18.43169 |
| 512 | qr | JAMA 1.0.3 | 490.049617 | 29.58258 |
| 1024 | qr | JAMA 1.0.3 | 15538.6124 | 783.46234 |
| 2000 | qr | JAMA 1.0.3 | 202480.552 | 16141.6438 |
| 512 | solve | JAMA 1.0.3 | 42.9650033 | 2.539965 |
| 1024 | solve | JAMA 1.0.3 | 400.585483 | 40.3267521 |
| 2000 | solve | JAMA 1.0.3 | 3308.0963 | 473.03967 |
| 512 | multiplication | Colt 1.2.0 | 283.467617 | 24.1098909 |
| 1024 | multiplication | Colt 1.2.0 | 5582.74533 | 340.647785 |
| 2000 | multiplication | Colt 1.2.0 | 41826.4664 | 702.120632 |
| 512 | lu | Colt 1.2.0 | 40.9902639 | 4.11367797 |
| 1024 | lu | Colt 1.2.0 | 365.977333 | 15.7074959 |
| 2000 | lu | Colt 1.2.0 | 3067.8665 | 297.681767 |
| 512 | cholesky | Colt 1.2.0 | 20.5977967 | 0.632793435 |
| 1024 | cholesky | Colt 1.2.0 | 166.451992 | 1.8101711 |
| 2000 | cholesky | Colt 1.2.0 | 1353.82367 | 16.7916515 |
| 512 | qr | Colt 1.2.0 | 652.733433 | 15.8732264 |
| 1024 | qr | Colt 1.2.0 | 11003.8873 | 508.920873 |
| 2000 | qr | Colt 1.2.0 | 123065.308 | 1047.17026 |
| 512 | solve | Colt 1.2.0 | 44.5595467 | 3.49601143 |
| 1024 | solve | Colt 1.2.0 | 397.652083 | 52.9982414 |
| 2000 | solve | Colt 1.2.0 | 2878.22125 | 141.492268 |
| 512 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 45.7768933 | 1.35150523 |
| 1024 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 1140.48143 | 96.9782259 |
| 2000 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 8460.12127 | 311.299328 |
| 512 | lu | Parallel Colt 0.11.4 (upstream tag build) | 41.9097633 | 2.15403393 |
| 1024 | lu | Parallel Colt 0.11.4 (upstream tag build) | 358.75525 | 25.9347282 |
| 2000 | lu | Parallel Colt 0.11.4 (upstream tag build) | 3095.76657 | 244.027098 |
| 512 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 18.8244109 | 1.45439849 |
| 1024 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 55.2928625 | 7.2012243 |
| 2000 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 218.587917 | 31.4395697 |
| 512 | qr | Parallel Colt 0.11.4 (upstream tag build) | 89.1217611 | 6.41556895 |
| 1024 | qr | Parallel Colt 0.11.4 (upstream tag build) | 265.400233 | 22.1487956 |
| 2000 | qr | Parallel Colt 0.11.4 (upstream tag build) | 1307.15797 | 110.980625 |
| 512 | solve | Parallel Colt 0.11.4 (upstream tag build) | 45.6903033 | 4.12167451 |
| 1024 | solve | Parallel Colt 0.11.4 (upstream tag build) | 382.4349 | 32.3487325 |
| 2000 | solve | Parallel Colt 0.11.4 (upstream tag build) | 3030.38313 | 513.745885 |
| 512 | multiplication | jblas 1.2.5 (native) | 91.8200833 | 5.67323971 |
| 1024 | multiplication | jblas 1.2.5 (native) | 816.542717 | 12.9631642 |
| 2000 | multiplication | jblas 1.2.5 (native) | 6267.1066 | 105.972154 |
| 512 | lu | jblas 1.2.5 (native) | 34.2780778 | 3.89837185 |
| 1024 | lu | jblas 1.2.5 (native) | 270.287917 | 23.3830864 |
| 2000 | lu | jblas 1.2.5 (native) | 1816.4403 | 115.442658 |
| 512 | cholesky | jblas 1.2.5 (native) | 24.4554593 | 1.06420487 |
| 1024 | cholesky | jblas 1.2.5 (native) | 200.286317 | 4.46466385 |
| 2000 | cholesky | jblas 1.2.5 (native) | 1448.85308 | 29.3731723 |
| 512 | qr | jblas 1.2.5 (native) | 188.059783 | 6.68103634 |
| 1024 | qr | jblas 1.2.5 (native) | 1538.57415 | 11.5483706 |
| 2000 | qr | jblas 1.2.5 (native) | 11687.8397 | 72.4604688 |
| 512 | solve | jblas 1.2.5 (native) | 31.2921238 | 0.133821145 |
| 1024 | solve | jblas 1.2.5 (native) | 243.703067 | 27.61076 |
| 2000 | solve | jblas 1.2.5 (native) | 1707.06222 | 44.2016559 |
| 512 | multiplication | F2J LAPACK/BLAS 0.1 | 36.1719552 | 22.2551013 |
| 1024 | multiplication | F2J LAPACK/BLAS 0.1 | 409.27155 | 123.765989 |
| 2000 | multiplication | F2J LAPACK/BLAS 0.1 | 3667.75468 | 119.025634 |
| 512 | lu | F2J LAPACK/BLAS 0.1 | 16.7510845 | 5.16230649 |
| 1024 | lu | F2J LAPACK/BLAS 0.1 | 115.267117 | 6.11549771 |
| 2000 | lu | F2J LAPACK/BLAS 0.1 | 716.421183 | 42.3179441 |
| 512 | cholesky | F2J LAPACK/BLAS 0.1 | 8.32950708 | 0.601283459 |
| 1024 | cholesky | F2J LAPACK/BLAS 0.1 | 68.7859556 | 2.49179378 |
| 2000 | cholesky | F2J LAPACK/BLAS 0.1 | 671.286383 | 137.647435 |
| 512 | qr | F2J LAPACK/BLAS 0.1 | 116.768817 | 5.22860408 |
| 1024 | qr | F2J LAPACK/BLAS 0.1 | 990.87145 | 32.0160562 |
| 2000 | qr | F2J LAPACK/BLAS 0.1 | 7317.22255 | 357.749008 |
| 512 | solve | F2J LAPACK/BLAS 0.1 | 13.4672839 | 1.3326835 |
| 1024 | solve | F2J LAPACK/BLAS 0.1 | 96.3235667 | 2.50612499 |
| 2000 | solve | F2J LAPACK/BLAS 0.1 | 663.606717 | 107.926819 |
| 512 | multiplication | MTJ 1.0.4 (F2J) | 33.2947056 | 2.10118457 |
| 1024 | multiplication | MTJ 1.0.4 (F2J) | 444.155133 | 21.5458889 |
| 2000 | multiplication | MTJ 1.0.4 (F2J) | 3542.28527 | 343.425899 |
| 512 | lu | MTJ 1.0.4 (F2J) | 13.2820385 | 0.0683781591 |
| 1024 | lu | MTJ 1.0.4 (F2J) | 97.9646167 | 3.56491278 |
| 2000 | lu | MTJ 1.0.4 (F2J) | 646.91585 | 22.7201911 |
| 512 | cholesky | MTJ 1.0.4 (F2J) | 24.7837373 | 0.502197151 |
| 1024 | cholesky | MTJ 1.0.4 (F2J) | 194.620908 | 1.03476526 |
| 2000 | cholesky | MTJ 1.0.4 (F2J) | 1395.62108 | 26.5642086 |
| 512 | qr | MTJ 1.0.4 (F2J) | 111.620817 | 2.54831054 |
| 1024 | qr | MTJ 1.0.4 (F2J) | 936.014217 | 19.302012 |
| 2000 | qr | MTJ 1.0.4 (F2J) | 7076.86398 | 192.324948 |
| 512 | solve | MTJ 1.0.4 (F2J) | 12.9255646 | 0.592338748 |
| 1024 | solve | MTJ 1.0.4 (F2J) | 96.4078 | 4.44548548 |
| 2000 | solve | MTJ 1.0.4 (F2J) | 631.515883 | 11.1042852 |
| 512 | multiplication | ojAlgo 57.5.0 | 7.53211515 | 0.708419801 |
| 1024 | multiplication | ojAlgo 57.5.0 | 60.591875 | 2.74065531 |
| 2000 | multiplication | ojAlgo 57.5.0 | 603.805533 | 23.7405628 |
| 512 | lu | ojAlgo 57.5.0 | 13.3162481 | 1.48455834 |
| 1024 | lu | ojAlgo 57.5.0 | 102.372981 | 49.6951203 |
| 2000 | lu | ojAlgo 57.5.0 | 1266.56072 | 223.626058 |
| 512 | cholesky | ojAlgo 57.5.0 | 22.1197344 | 1.52161486 |
| 1024 | cholesky | ojAlgo 57.5.0 | 80.7570444 | 11.0368422 |
| 2000 | cholesky | ojAlgo 57.5.0 | 571.577533 | 15.5820245 |
| 512 | qr | ojAlgo 57.5.0 | 54.4940125 | 9.89605764 |
| 1024 | qr | ojAlgo 57.5.0 | 434.7551 | 92.896442 |
| 2000 | qr | ojAlgo 57.5.0 | 3981.51428 | 645.93656 |
| 512 | solve | ojAlgo 57.5.0 | 12.2335392 | 0.113584795 |
| 1024 | solve | ojAlgo 57.5.0 | 106.071564 | 11.5362631 |
| 2000 | solve | ojAlgo 57.5.0 | 1224.67615 | 271.65852 |
| 512 | multiplication | UJMP 0.3.0 (core) | 52.9555433 | 9.66786566 |
| 1024 | multiplication | UJMP 0.3.0 (core) | 1848.27583 | 164.896627 |
| 2000 | multiplication | UJMP 0.3.0 (core) | 16138.0606 | 1363.94325 |
| 512 | lu | UJMP 0.3.0 (core) | 42.5433733 | 1.50005859 |
| 1024 | lu | UJMP 0.3.0 (core) | 415.774017 | 58.4967792 |
| 2000 | lu | UJMP 0.3.0 (core) | 3308.38748 | 361.951341 |
| 512 | cholesky | UJMP 0.3.0 (core) | 21.6550302 | 1.60603371 |
| 1024 | cholesky | UJMP 0.3.0 (core) | 193.461233 | 13.8993028 |
| 2000 | cholesky | UJMP 0.3.0 (core) | 1524.1141 | 64.5902928 |
| 512 | qr | UJMP 0.3.0 (core) | 477.439117 | 17.5604686 |
| 1024 | qr | UJMP 0.3.0 (core) | 15614.9075 | 1226.13746 |
| 2000 | qr | UJMP 0.3.0 (core) | 209227.8 | 5194.84205 |
| 512 | solve | UJMP 0.3.0 (core) | 42.7066167 | 0.829097008 |
| 1024 | solve | UJMP 0.3.0 (core) | 402.186333 | 19.3236708 |
| 2000 | solve | UJMP 0.3.0 (core) | 3174.86807 | 90.7741391 |

## Rectangular-All-Cores — 12 requested compute thread(s)

[Interactive chart](benchmarks-rectangular-all-cores-12thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 128x512x64 | multiplication | NM Dev 1.2.10 | 0.57304019 | 0.0963563898 |
| 512x128x256 | multiplication | NM Dev 1.2.10 | 1.78398807 | 0.350461153 |
| 1024x256x64 | multiplication | NM Dev 1.2.10 | 1.47587605 | 0.0376009584 |
| 255x257x129 | multiplication | NM Dev 1.2.10 | 0.935845042 | 0.0536583789 |
| 128x512x64 | multiplication | EJML 0.46.1 | 1.44678855 | 0.0974590048 |
| 512x128x256 | multiplication | EJML 0.46.1 | 3.75774352 | 1.36460177 |
| 1024x256x64 | multiplication | EJML 0.46.1 | 5.80477905 | 0.260129534 |
| 255x257x129 | multiplication | EJML 0.46.1 | 2.42924308 | 0.101873512 |
| 128x512x64 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 4.95362236 | 0.0616806928 |
| 512x128x256 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 17.8447253 | 0.88791444 |
| 1024x256x64 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 17.9108306 | 0.989073189 |
| 255x257x129 | multiplication | Commons Math 4.0-beta1 - Array2DRowRealMatrix | 9.56800296 | 0.321557478 |
| 128x512x64 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 2.17368722 | 0.0291926627 |
| 512x128x256 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 8.90728188 | 0.25049995 |
| 1024x256x64 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 8.94045352 | 0.504631936 |
| 255x257x129 | multiplication | Commons Math 4.0-beta1 - BlockRealMatrix | 4.73662862 | 0.152369179 |
| 128x512x64 | multiplication | JAMA 1.0.3 | 4.13894354 | 0.0343122304 |
| 512x128x256 | multiplication | JAMA 1.0.3 | 13.092574 | 0.223891961 |
| 1024x256x64 | multiplication | JAMA 1.0.3 | 15.5085996 | 1.42037881 |
| 255x257x129 | multiplication | JAMA 1.0.3 | 7.58815926 | 0.152350718 |
| 128x512x64 | multiplication | Colt 1.2.0 | 4.04351956 | 0.341963305 |
| 512x128x256 | multiplication | Colt 1.2.0 | 16.724404 | 0.923495485 |
| 1024x256x64 | multiplication | Colt 1.2.0 | 12.8871427 | 0.445451293 |
| 255x257x129 | multiplication | Colt 1.2.0 | 6.60719719 | 0.33588954 |
| 128x512x64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 1.22261971 | 0.261656475 |
| 512x128x256 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 4.2067736 | 0.551877371 |
| 1024x256x64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 3.88798605 | 1.74776966 |
| 255x257x129 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 1.9654303 | 0.334869845 |
| 128x512x64 | multiplication | jblas 1.2.5 (native) | 2.93963714 | 0.389889474 |
| 512x128x256 | multiplication | jblas 1.2.5 (native) | 11.4117843 | 0.0687759231 |
| 1024x256x64 | multiplication | jblas 1.2.5 (native) | 12.0315716 | 0.278958322 |
| 255x257x129 | multiplication | jblas 1.2.5 (native) | 5.4808512 | 0.118921563 |
| 128x512x64 | multiplication | F2J LAPACK/BLAS 0.1 | 1.36950357 | 0.193049747 |
| 512x128x256 | multiplication | F2J LAPACK/BLAS 0.1 | 3.60659325 | 0.127293563 |
| 1024x256x64 | multiplication | F2J LAPACK/BLAS 0.1 | 3.71158317 | 0.451003055 |
| 255x257x129 | multiplication | F2J LAPACK/BLAS 0.1 | 2.24433067 | 0.181369566 |
| 128x512x64 | multiplication | MTJ 1.0.4 (F2J) | 1.32524451 | 0.0695661811 |
| 512x128x256 | multiplication | MTJ 1.0.4 (F2J) | 3.61128566 | 0.208287049 |
| 1024x256x64 | multiplication | MTJ 1.0.4 (F2J) | 3.6289425 | 0.30559375 |
| 255x257x129 | multiplication | MTJ 1.0.4 (F2J) | 2.28766976 | 0.278808278 |
| 128x512x64 | multiplication | ojAlgo 57.5.0 | 0.683619726 | 0.141535611 |
| 512x128x256 | multiplication | ojAlgo 57.5.0 | 1.09268641 | 0.0817452501 |
| 1024x256x64 | multiplication | ojAlgo 57.5.0 | 1.7780554 | 0.0689053458 |
| 255x257x129 | multiplication | ojAlgo 57.5.0 | 0.751590848 | 0.0442664647 |
| 128x512x64 | multiplication | UJMP 0.3.0 (core) | 1.2473418 | 0.132164936 |
| 512x128x256 | multiplication | UJMP 0.3.0 (core) | 5.65341288 | 2.25260983 |
| 1024x256x64 | multiplication | UJMP 0.3.0 (core) | 11.1666897 | 2.53598123 |
| 255x257x129 | multiplication | UJMP 0.3.0 (core) | 2.1806319 | 0.134973588 |

