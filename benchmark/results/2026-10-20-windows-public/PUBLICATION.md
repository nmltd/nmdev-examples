# Benchmark results — 2026-10-20-windows-public

660 cases across 11 libraries; 1,320 independently warmed fork records; 3,960 measured iteration means. Lower ms/op is faster.

Intel(R) Core(TM) i7-8750H CPU @ 2.20GHz; 12 logical processors; 63.92 GiB physical RAM; Windows 11 amd64; Oracle Corporation JDK 27 (27+35-2325).

Two separately warmed JVM forks per case; two 100 ms warmups and three 200 ms measurements per fork; one JMH worker; requested compute threads are shown per profile. Fork heap: 256 MiB initial / 1 GiB maximum. CPU affinity was not set. Native worker counts were not independently audited. These short measurements may have wide confidence intervals; rankings are machine-specific.

The run measures NM Dev **1.2.6**, not a later release. Profiles are kept separate: a 4-thread request does not imply every library executes in parallel. This archive contains no all-12-thread profile and no separate Commons block adapter.

## Validation

All 660 requested case identities, both fork JSON checksums per case, raw sample counts, finite positive timings, arithmetic means, combined results, summary CSV values, JVM thread flags and machine identity fields passed validation. All five preflight processes exited successfully.

The original ZIP retains historical launcher and packaging errors. The final status is complete, with zero failed cases; every requested case and fork is present, so the final archive is complete despite those earlier errors. Downloaded binaries are excluded: their recorded hashes establish provenance but cannot be independently recomputed from this ZIP.

[Validation evidence](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-10-20-windows-public/validation-report.json) · [Saved JMH measurements](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-10-20-windows-public/jmh.json) · [Summary CSV](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-10-20-windows-public/summary.csv) · [Hardware and archive digest](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-10-20-windows-public/publication-format.json) · [Binary provenance](https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/2026-10-20-windows-public/binary-provenance.json)

## Baseline — 1 requested compute thread(s)

[Interactive chart](benchmarks.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 64 | transpose | NM Dev 1.2.6 | 0.00580577178 | 0.00240314042 |
| 256 | transpose | NM Dev 1.2.6 | 0.203537232 | 0.0315847997 |
| 64 | addition | NM Dev 1.2.6 | 0.00369553061 | 0.000715866306 |
| 256 | addition | NM Dev 1.2.6 | 0.111899852 | 0.0281497292 |
| 64 | scale | NM Dev 1.2.6 | 0.00329830324 | 0.000946205328 |
| 256 | scale | NM Dev 1.2.6 | 0.11114747 | 0.0700810542 |
| 64 | multiplication | NM Dev 1.2.6 | 0.235147645 | 0.113709634 |
| 256 | multiplication | NM Dev 1.2.6 | 29.7645878 | 39.8386902 |
| 64 | determinant | NM Dev 1.2.6 | 0.077914506 | 0.0449691157 |
| 256 | determinant | NM Dev 1.2.6 | 7.80127135 | 1.76441614 |
| 64 | inverse | NM Dev 1.2.6 | 0.521673833 | 0.0968346512 |
| 256 | inverse | NM Dev 1.2.6 | 28.2909777 | 4.07141019 |
| 64 | inverseSPD | NM Dev 1.2.6 | 0.488352387 | 0.176763598 |
| 256 | inverseSPD | NM Dev 1.2.6 | 24.5098368 | 6.38660565 |
| 64 | cholesky | NM Dev 1.2.6 | 0.0473449942 | 0.00718215466 |
| 256 | cholesky | NM Dev 1.2.6 | 2.85772516 | 0.213220741 |
| 64 | lu | NM Dev 1.2.6 | 0.0703195077 | 0.025769411 |
| 256 | lu | NM Dev 1.2.6 | 9.14865208 | 5.32813841 |
| 64 | qr | NM Dev 1.2.6 | 0.367146568 | 0.0977717063 |
| 256 | qr | NM Dev 1.2.6 | 28.4391475 | 14.2118565 |
| 64 | eigenSymmetric | NM Dev 1.2.6 | 5.76145103 | 4.71537751 |
| 256 | eigenSymmetric | NM Dev 1.2.6 | 117.566608 | 31.9047855 |
| 64 | svd | NM Dev 1.2.6 | 3.31409393 | 2.083533 |
| 256 | svd | NM Dev 1.2.6 | 132.899283 | 36.2725 |
| 64 | solve | NM Dev 1.2.6 | 0.213976672 | 0.177811077 |
| 256 | solve | NM Dev 1.2.6 | 8.64684868 | 2.57288847 |
| 64 | transpose | EJML 0.46.1 | 0.00495646705 | 0.000668438123 |
| 256 | transpose | EJML 0.46.1 | 0.171423198 | 0.0886002562 |
| 64 | addition | EJML 0.46.1 | 0.00385311098 | 0.00201976682 |
| 256 | addition | EJML 0.46.1 | 0.0851261444 | 0.0712093193 |
| 64 | scale | EJML 0.46.1 | 0.00326102249 | 0.00101707496 |
| 256 | scale | EJML 0.46.1 | 0.0591470076 | 0.0221147279 |
| 64 | multiplication | EJML 0.46.1 | 0.107525745 | 0.0497805315 |
| 256 | multiplication | EJML 0.46.1 | 4.17183276 | 1.67591035 |
| 64 | determinant | EJML 0.46.1 | 0.08524932 | 0.0130260185 |
| 256 | determinant | EJML 0.46.1 | 5.27104358 | 0.592594165 |
| 64 | inverse | EJML 0.46.1 | 0.306661482 | 0.0212364846 |
| 256 | inverse | EJML 0.46.1 | 17.9855092 | 2.49053013 |
| 64 | inverseSPD | EJML 0.46.1 | 0.141241679 | 0.00951044515 |
| 256 | inverseSPD | EJML 0.46.1 | 9.90209783 | 1.09583652 |
| 64 | cholesky | EJML 0.46.1 | 0.0443449223 | 0.00507416552 |
| 256 | cholesky | EJML 0.46.1 | 2.74323125 | 0.161045967 |
| 64 | lu | EJML 0.46.1 | 0.0935643218 | 0.0143843453 |
| 256 | lu | EJML 0.46.1 | 5.45658425 | 0.422146841 |
| 64 | qr | EJML 0.46.1 | 0.221218296 | 0.0484796005 |
| 256 | qr | EJML 0.46.1 | 11.7413106 | 5.41113496 |
| 64 | eigenSymmetric | EJML 0.46.1 | 1.01620255 | 1.55682596 |
| 256 | eigenSymmetric | EJML 0.46.1 | 23.8209143 | 5.87003525 |
| 64 | svd | EJML 0.46.1 | 1.12930159 | 0.52866964 |
| 256 | svd | EJML 0.46.1 | 42.2050194 | 6.84725031 |
| 64 | solve | EJML 0.46.1 | 0.100979829 | 0.0261854083 |
| 256 | solve | EJML 0.46.1 | 6.32937334 | 3.60896233 |
| 64 | transpose | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.00665352991 | 0.00194877389 |
| 256 | transpose | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.154659441 | 0.0473172083 |
| 64 | addition | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.00453892605 | 0.000681856539 |
| 256 | addition | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.0815548314 | 0.015535962 |
| 64 | scale | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.00529002329 | 0.0039094142 |
| 256 | scale | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.0832983645 | 0.0348816924 |
| 64 | multiplication | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.260780182 | 0.00848395343 |
| 256 | multiplication | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 20.1058891 | 3.26069486 |
| 64 | determinant | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.13910391 | 0.166680587 |
| 256 | determinant | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 7.95382585 | 2.49440812 |
| 64 | inverse | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.194576411 | 0.0960196038 |
| 256 | inverse | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 18.1755151 | 20.3647773 |
| 64 | inverseSPD | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.15282034 | 0.0757233401 |
| 256 | inverseSPD | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 7.80393501 | 5.06800573 |
| 64 | cholesky | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.0656727125 | 0.02528306 |
| 256 | cholesky | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 2.78804684 | 0.923024514 |
| 64 | lu | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.114619636 | 0.0277133417 |
| 256 | lu | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 8.82090567 | 3.40129003 |
| 64 | qr | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.282817543 | 0.0490609638 |
| 256 | qr | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 16.1196739 | 3.945335 |
| 64 | eigenSymmetric | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 2.37182658 | 6.91614231 |
| 256 | eigenSymmetric | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 62.3061153 | 16.3196181 |
| 64 | svd | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 53.415621 | 71.5310689 |
| 256 | svd | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 218.594917 | 22.7530349 |
| 64 | solve | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 0.127200593 | 0.0377668101 |
| 256 | solve | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 7.48962741 | 3.47293221 |
| 64 | transpose | JAMA 1.0.3 | 0.00727044597 | 0.00275652239 |
| 256 | transpose | JAMA 1.0.3 | 0.211192637 | 0.19457545 |
| 64 | addition | JAMA 1.0.3 | 0.00467682552 | 0.00128228494 |
| 256 | addition | JAMA 1.0.3 | 0.0913893395 | 0.0318314801 |
| 64 | scale | JAMA 1.0.3 | 0.0049579416 | 0.00116041467 |
| 256 | scale | JAMA 1.0.3 | 0.0967053626 | 0.0362421996 |
| 64 | multiplication | JAMA 1.0.3 | 0.223430151 | 0.082291068 |
| 256 | multiplication | JAMA 1.0.3 | 19.006505 | 3.34740505 |
| 64 | determinant | JAMA 1.0.3 | 0.097289187 | 0.0626473722 |
| 256 | determinant | JAMA 1.0.3 | 5.45111607 | 2.69892492 |
| 64 | inverse | JAMA 1.0.3 | 0.276792546 | 0.15624825 |
| 256 | inverse | JAMA 1.0.3 | 17.416505 | 11.2142633 |
| 64 | inverseSPD | JAMA 1.0.3 | 0.440867964 | 0.260309298 |
| 256 | inverseSPD | JAMA 1.0.3 | 42.365096 | 31.2798456 |
| 64 | cholesky | JAMA 1.0.3 | 0.0485839831 | 0.0121272752 |
| 256 | cholesky | JAMA 1.0.3 | 2.95958153 | 1.50022108 |
| 64 | lu | JAMA 1.0.3 | 0.0886499593 | 0.0129324273 |
| 256 | lu | JAMA 1.0.3 | 4.95821546 | 0.742409685 |
| 64 | qr | JAMA 1.0.3 | 0.436155917 | 0.0584230884 |
| 256 | qr | JAMA 1.0.3 | 41.2595967 | 3.14105969 |
| 64 | eigenSymmetric | JAMA 1.0.3 | 0.895384865 | 0.345390961 |
| 256 | eigenSymmetric | JAMA 1.0.3 | 64.1187236 | 9.38555875 |
| 64 | svd | JAMA 1.0.3 | 7.13136559 | 7.82817777 |
| 256 | svd | JAMA 1.0.3 | 195.815217 | 40.494386 |
| 64 | solve | JAMA 1.0.3 | 0.102652296 | 0.0367629208 |
| 256 | solve | JAMA 1.0.3 | 5.33139849 | 0.521860253 |
| 64 | transpose | Colt 1.2.0 | 0.00539232446 | 0.000696837191 |
| 256 | transpose | Colt 1.2.0 | 0.15496339 | 0.0275575444 |
| 64 | addition | Colt 1.2.0 | 0.00753208029 | 0.00459223483 |
| 256 | addition | Colt 1.2.0 | 0.164827258 | 0.117859495 |
| 64 | scale | Colt 1.2.0 | 0.00705496151 | 0.00511358214 |
| 256 | scale | Colt 1.2.0 | 0.111226186 | 0.0990987937 |
| 64 | multiplication | Colt 1.2.0 | 0.257675375 | 0.0444049251 |
| 256 | multiplication | Colt 1.2.0 | 38.6787028 | 14.3548094 |
| 64 | determinant | Colt 1.2.0 | 0.133780136 | 0.067183952 |
| 256 | determinant | Colt 1.2.0 | 9.02176207 | 5.33590825 |
| 64 | inverse | Colt 1.2.0 | 0.710676538 | 0.231134596 |
| 256 | inverse | Colt 1.2.0 | 31.1696329 | 24.5420843 |
| 64 | inverseSPD | Colt 1.2.0 | 0.510630359 | 0.185744434 |
| 256 | inverseSPD | Colt 1.2.0 | 44.2094067 | 5.25178666 |
| 64 | cholesky | Colt 1.2.0 | 0.0734972003 | 0.0464249392 |
| 256 | cholesky | Colt 1.2.0 | 2.87199957 | 0.447927778 |
| 64 | lu | Colt 1.2.0 | 0.201981336 | 0.221719029 |
| 256 | lu | Colt 1.2.0 | 6.17994592 | 1.56503408 |
| 64 | qr | Colt 1.2.0 | 0.490921184 | 0.1067796 |
| 256 | qr | Colt 1.2.0 | 91.4433528 | 57.831493 |
| 64 | eigenSymmetric | Colt 1.2.0 | 1.47219458 | 1.88559843 |
| 256 | eigenSymmetric | Colt 1.2.0 | 89.0688583 | 70.3975128 |
| 64 | svd | Colt 1.2.0 | 7.43559841 | 8.71788828 |
| 256 | svd | Colt 1.2.0 | 203.01565 | 14.1034071 |
| 64 | solve | Colt 1.2.0 | 0.341400264 | 0.348298233 |
| 256 | solve | Colt 1.2.0 | 10.2459553 | 13.6212396 |
| 64 | transpose | Parallel Colt 0.11.4 (upstream tag build) | 0.00591528492 | 0.00128304877 |
| 256 | transpose | Parallel Colt 0.11.4 (upstream tag build) | 0.158034355 | 0.0232786541 |
| 64 | addition | Parallel Colt 0.11.4 (upstream tag build) | 0.00705457203 | 0.00206416261 |
| 256 | addition | Parallel Colt 0.11.4 (upstream tag build) | 0.180778476 | 0.0638524094 |
| 64 | scale | Parallel Colt 0.11.4 (upstream tag build) | 0.00692804443 | 0.00470130402 |
| 256 | scale | Parallel Colt 0.11.4 (upstream tag build) | 0.116528616 | 0.0884376527 |
| 64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 0.278405361 | 0.0273814462 |
| 256 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 37.8973426 | 27.6854021 |
| 64 | determinant | Parallel Colt 0.11.4 (upstream tag build) | 0.277198278 | 0.300329743 |
| 256 | determinant | Parallel Colt 0.11.4 (upstream tag build) | 7.95291463 | 2.96381164 |
| 64 | inverse | Parallel Colt 0.11.4 (upstream tag build) | 1.43715291 | 0.397786463 |
| 256 | inverse | Parallel Colt 0.11.4 (upstream tag build) | 26.1420538 | 18.5491553 |
| 64 | inverseSPD | Parallel Colt 0.11.4 (upstream tag build) | 0.877811857 | 0.67984905 |
| 256 | inverseSPD | Parallel Colt 0.11.4 (upstream tag build) | 19.1622496 | 5.76532549 |
| 64 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 0.29373982 | 0.372724205 |
| 256 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 4.95050128 | 4.5010846 |
| 64 | lu | Parallel Colt 0.11.4 (upstream tag build) | 0.274671715 | 0.307468598 |
| 256 | lu | Parallel Colt 0.11.4 (upstream tag build) | 7.88107302 | 4.37561187 |
| 64 | qr | Parallel Colt 0.11.4 (upstream tag build) | 1.75545004 | 2.75983451 |
| 256 | qr | Parallel Colt 0.11.4 (upstream tag build) | 20.9225524 | 4.66449099 |
| 64 | eigenSymmetric | Parallel Colt 0.11.4 (upstream tag build) | 1.29278934 | 1.64404244 |
| 256 | eigenSymmetric | Parallel Colt 0.11.4 (upstream tag build) | 65.4890444 | 21.5032898 |
| 64 | svd | Parallel Colt 0.11.4 (upstream tag build) | 7.95868255 | 4.51438144 |
| 256 | svd | Parallel Colt 0.11.4 (upstream tag build) | 106.227107 | 133.429171 |
| 64 | solve | Parallel Colt 0.11.4 (upstream tag build) | 0.443192813 | 0.294567912 |
| 256 | solve | Parallel Colt 0.11.4 (upstream tag build) | 8.99860987 | 3.50597028 |
| 64 | transpose | jblas 1.2.5 (native) | 0.00718395947 | 0.00452269673 |
| 256 | transpose | jblas 1.2.5 (native) | 0.168235729 | 0.0585339191 |
| 64 | addition | jblas 1.2.5 (native) | 0.00355859589 | 0.000266510393 |
| 256 | addition | jblas 1.2.5 (native) | 0.0691239746 | 0.0176493316 |
| 64 | scale | jblas 1.2.5 (native) | 0.00314767197 | 0.000959737311 |
| 256 | scale | jblas 1.2.5 (native) | 0.078773343 | 0.0247505255 |
| 64 | multiplication | jblas 1.2.5 (native) | 0.187672907 | 0.0213565668 |
| 256 | multiplication | jblas 1.2.5 (native) | 13.1536271 | 4.53144739 |
| 64 | determinant | jblas 1.2.5 (native) | 0.108243805 | 0.0153223754 |
| 256 | determinant | jblas 1.2.5 (native) | 6.21480858 | 4.47980385 |
| 64 | inverse | jblas 1.2.5 (native) | 0.277558122 | 0.0471122079 |
| 256 | inverse | jblas 1.2.5 (native) | 17.434852 | 5.6463622 |
| 64 | inverseSPD | jblas 1.2.5 (native) | 0.430788291 | 0.397183702 |
| 256 | inverseSPD | jblas 1.2.5 (native) | 21.2117177 | 8.54813846 |
| 64 | cholesky | jblas 1.2.5 (native) | 0.0571393079 | 0.00877219977 |
| 256 | cholesky | jblas 1.2.5 (native) | 3.1496875 | 0.31603771 |
| 64 | lu | jblas 1.2.5 (native) | 0.113909947 | 0.0134178146 |
| 256 | lu | jblas 1.2.5 (native) | 5.05372638 | 0.286513588 |
| 64 | qr | jblas 1.2.5 (native) | 0.479593053 | 0.117688198 |
| 256 | qr | jblas 1.2.5 (native) | 26.7833973 | 7.45679691 |
| 64 | eigenSymmetric | jblas 1.2.5 (native) | 0.917580273 | 0.247505888 |
| 256 | eigenSymmetric | jblas 1.2.5 (native) | 33.3353671 | 9.94940634 |
| 64 | svd | jblas 1.2.5 (native) | 2.49595201 | 0.680238782 |
| 256 | svd | jblas 1.2.5 (native) | 152.556883 | 25.3080784 |
| 64 | solve | jblas 1.2.5 (native) | 0.110832912 | 0.0599447045 |
| 256 | solve | jblas 1.2.5 (native) | 4.6672808 | 1.19974973 |
| 64 | transpose | F2J LAPACK/BLAS 0.1 | 0.0078056036 | 0.0013700955 |
| 256 | transpose | F2J LAPACK/BLAS 0.1 | 0.398826249 | 0.0636060487 |
| 64 | addition | F2J LAPACK/BLAS 0.1 | 0.018409598 | 0.00307151169 |
| 256 | addition | F2J LAPACK/BLAS 0.1 | 0.311475888 | 0.121466895 |
| 64 | scale | F2J LAPACK/BLAS 0.1 | 0.00347065158 | 0.00134341159 |
| 256 | scale | F2J LAPACK/BLAS 0.1 | 0.060458792 | 0.0206356085 |
| 64 | multiplication | F2J LAPACK/BLAS 0.1 | 0.120959785 | 0.0844328096 |
| 256 | multiplication | F2J LAPACK/BLAS 0.1 | 4.34609775 | 1.47177869 |
| 64 | determinant | F2J LAPACK/BLAS 0.1 | 0.0506619374 | 0.0130095482 |
| 256 | determinant | F2J LAPACK/BLAS 0.1 | 2.67431549 | 2.46986723 |
| 64 | inverse | F2J LAPACK/BLAS 0.1 | 0.187767226 | 0.146098527 |
| 256 | inverse | F2J LAPACK/BLAS 0.1 | 8.82649484 | 5.02598045 |
| 64 | inverseSPD | F2J LAPACK/BLAS 0.1 | 0.191102104 | 0.182177098 |
| 256 | inverseSPD | F2J LAPACK/BLAS 0.1 | 17.35129 | 76.1226275 |
| 64 | cholesky | F2J LAPACK/BLAS 0.1 | 0.0524776444 | 0.0444224686 |
| 256 | cholesky | F2J LAPACK/BLAS 0.1 | 1.46743405 | 0.403580969 |
| 64 | lu | F2J LAPACK/BLAS 0.1 | 0.0682124772 | 0.0105875538 |
| 256 | lu | F2J LAPACK/BLAS 0.1 | 2.80864749 | 0.440868663 |
| 64 | qr | F2J LAPACK/BLAS 0.1 | 0.26773489 | 0.025026866 |
| 256 | qr | F2J LAPACK/BLAS 0.1 | 15.9770071 | 4.4819234 |
| 64 | eigenSymmetric | F2J LAPACK/BLAS 0.1 | 1.64348881 | 1.44501265 |
| 256 | eigenSymmetric | F2J LAPACK/BLAS 0.1 | 31.1254075 | 7.82253599 |
| 64 | svd | F2J LAPACK/BLAS 0.1 | 3.06851021 | 0.54358646 |
| 256 | svd | F2J LAPACK/BLAS 0.1 | 158.357217 | 126.199546 |
| 64 | solve | F2J LAPACK/BLAS 0.1 | 0.0568090824 | 0.00781525906 |
| 256 | solve | F2J LAPACK/BLAS 0.1 | 2.52522239 | 0.751206177 |
| 64 | transpose | MTJ 1.0.4 (F2J) | 0.0126823521 | 0.00132368857 |
| 256 | transpose | MTJ 1.0.4 (F2J) | 0.628488776 | 0.0487297321 |
| 64 | addition | MTJ 1.0.4 (F2J) | 0.0278276253 | 0.00278045713 |
| 256 | addition | MTJ 1.0.4 (F2J) | 0.445388538 | 0.185629459 |
| 64 | scale | MTJ 1.0.4 (F2J) | 0.0202915898 | 0.00436541921 |
| 256 | scale | MTJ 1.0.4 (F2J) | 0.349343125 | 0.0574641435 |
| 64 | multiplication | MTJ 1.0.4 (F2J) | 0.117827045 | 0.0393874727 |
| 256 | multiplication | MTJ 1.0.4 (F2J) | 4.38356893 | 1.11410357 |
| 64 | determinant | MTJ 1.0.4 (F2J) | 0.0638758791 | 0.0128899166 |
| 256 | determinant | MTJ 1.0.4 (F2J) | 3.60480499 | 2.82941531 |
| 64 | inverse | MTJ 1.0.4 (F2J) | 0.291728661 | 0.153576939 |
| 256 | inverse | MTJ 1.0.4 (F2J) | 16.3395038 | 1.82138361 |
| 64 | inverseSPD | MTJ 1.0.4 (F2J) | 0.708434759 | 1.84449212 |
| 256 | inverseSPD | MTJ 1.0.4 (F2J) | 27.970466 | 15.0211779 |
| 64 | cholesky | MTJ 1.0.4 (F2J) | 0.110002251 | 0.0387878194 |
| 256 | cholesky | MTJ 1.0.4 (F2J) | 4.31792648 | 1.03257296 |
| 64 | lu | MTJ 1.0.4 (F2J) | 0.0866840563 | 0.0800320649 |
| 256 | lu | MTJ 1.0.4 (F2J) | 3.49414336 | 2.39390604 |
| 64 | qr | MTJ 1.0.4 (F2J) | 0.362710506 | 0.180345872 |
| 256 | qr | MTJ 1.0.4 (F2J) | 17.1855606 | 9.18155589 |
| 64 | eigenSymmetric | MTJ 1.0.4 (F2J) | 2.37732808 | 2.18555694 |
| 256 | eigenSymmetric | MTJ 1.0.4 (F2J) | 46.6846345 | 96.1840897 |
| 64 | svd | MTJ 1.0.4 (F2J) | 12.2085965 | 12.9958709 |
| 256 | svd | MTJ 1.0.4 (F2J) | 176.167892 | 144.514113 |
| 64 | solve | MTJ 1.0.4 (F2J) | 0.0662835989 | 0.037147315 |
| 256 | solve | MTJ 1.0.4 (F2J) | 2.89143191 | 1.36219977 |
| 64 | transpose | ojAlgo 57.5.0 | 0.00832358279 | 0.00377098865 |
| 256 | transpose | ojAlgo 57.5.0 | 0.190374201 | 0.112087196 |
| 64 | addition | ojAlgo 57.5.0 | 0.00474730993 | 0.0020773182 |
| 256 | addition | ojAlgo 57.5.0 | 0.126850739 | 0.102138838 |
| 64 | scale | ojAlgo 57.5.0 | 0.00612398257 | 0.00835326797 |
| 256 | scale | ojAlgo 57.5.0 | 0.0594318373 | 0.019528263 |
| 64 | multiplication | ojAlgo 57.5.0 | 0.100678371 | 0.0536910769 |
| 256 | multiplication | ojAlgo 57.5.0 | 4.00132061 | 0.527727583 |
| 64 | determinant | ojAlgo 57.5.0 | 0.0586386434 | 0.00346118497 |
| 256 | determinant | ojAlgo 57.5.0 | 1.79060121 | 0.226553933 |
| 64 | inverse | ojAlgo 57.5.0 | 0.215606408 | 0.0402296707 |
| 256 | inverse | ojAlgo 57.5.0 | 13.5153276 | 4.64462072 |
| 64 | inverseSPD | ojAlgo 57.5.0 | 0.133481771 | 0.0308754975 |
| 256 | inverseSPD | ojAlgo 57.5.0 | 6.93578355 | 0.913857405 |
| 64 | cholesky | ojAlgo 57.5.0 | 0.0414836601 | 0.0339119493 |
| 256 | cholesky | ojAlgo 57.5.0 | 1.14234446 | 0.358428819 |
| 64 | lu | ojAlgo 57.5.0 | 0.0767589455 | 0.0114596096 |
| 256 | lu | ojAlgo 57.5.0 | 2.04863597 | 0.317785165 |
| 64 | qr | ojAlgo 57.5.0 | 0.293431204 | 0.26813094 |
| 256 | qr | ojAlgo 57.5.0 | 7.80771549 | 1.7400486 |
| 64 | eigenSymmetric | ojAlgo 57.5.0 | 0.781855766 | 0.631191501 |
| 256 | eigenSymmetric | ojAlgo 57.5.0 | 24.6281426 | 14.9525059 |
| 64 | svd | ojAlgo 57.5.0 | 1.88825046 | 0.856583914 |
| 256 | svd | ojAlgo 57.5.0 | 49.6586622 | 29.2877088 |
| 64 | solve | ojAlgo 57.5.0 | 0.060305073 | 0.0396347358 |
| 256 | solve | ojAlgo 57.5.0 | 1.88349801 | 0.586992506 |
| 64 | transpose | UJMP 0.3.0 (core) | 0.0351836043 | 0.0223084225 |
| 256 | transpose | UJMP 0.3.0 (core) | 0.741931646 | 0.319131903 |
| 64 | addition | UJMP 0.3.0 (core) | 0.00873621462 | 0.00386871481 |
| 256 | addition | UJMP 0.3.0 (core) | 0.29317602 | 0.082370612 |
| 64 | scale | UJMP 0.3.0 (core) | 0.00791422884 | 0.00318708142 |
| 256 | scale | UJMP 0.3.0 (core) | 0.274295314 | 0.024562973 |
| 64 | multiplication | UJMP 0.3.0 (core) | 0.21422912 | 0.033122714 |
| 256 | multiplication | UJMP 0.3.0 (core) | 22.4595815 | 1.3854791 |
| 64 | determinant | UJMP 0.3.0 (core) | 0.0785563551 | 0.0329916864 |
| 256 | determinant | UJMP 0.3.0 (core) | 4.92110122 | 1.01724333 |
| 64 | inverse | UJMP 0.3.0 (core) | 0.243899931 | 0.0306791146 |
| 256 | inverse | UJMP 0.3.0 (core) | 15.0871418 | 1.2081501 |
| 64 | inverseSPD | UJMP 0.3.0 (core) | 0.378726908 | 0.081414713 |
| 256 | inverseSPD | UJMP 0.3.0 (core) | 30.2162286 | 1.09667684 |
| 64 | cholesky | UJMP 0.3.0 (core) | 0.0493564721 | 0.00820369326 |
| 256 | cholesky | UJMP 0.3.0 (core) | 2.75599895 | 0.695528467 |
| 64 | lu | UJMP 0.3.0 (core) | 0.0947084252 | 0.0410981896 |
| 256 | lu | UJMP 0.3.0 (core) | 5.00676227 | 0.531303982 |
| 64 | qr | UJMP 0.3.0 (core) | 0.435146698 | 0.0338960576 |
| 256 | qr | UJMP 0.3.0 (core) | 40.0298456 | 0.901369281 |
| 64 | eigenSymmetric | UJMP 0.3.0 (core) | 1.14358934 | 0.775062228 |
| 256 | eigenSymmetric | UJMP 0.3.0 (core) | 63.3346458 | 7.32682078 |
| 64 | svd | UJMP 0.3.0 (core) | 8.62708324 | 11.952523 |
| 256 | svd | UJMP 0.3.0 (core) | 217.717617 | 94.8245717 |
| 64 | solve | UJMP 0.3.0 (core) | 0.0884285729 | 0.0129060162 |
| 256 | solve | UJMP 0.3.0 (core) | 5.34703635 | 1.38894727 |

## Large — 1 requested compute thread(s)

[Interactive chart](benchmarks-large-1thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 512 | multiplication | NM Dev 1.2.6 | 230.140717 | 47.0259174 |
| 1024 | multiplication | NM Dev 1.2.6 | 1003.46012 | 44.7127031 |
| 2000 | multiplication | NM Dev 1.2.6 | 6948.46898 | 928.737377 |
| 512 | lu | NM Dev 1.2.6 | 40.3524224 | 34.3526172 |
| 1024 | lu | NM Dev 1.2.6 | 229.305283 | 22.6346567 |
| 2000 | lu | NM Dev 1.2.6 | 1919.35308 | 299.051886 |
| 512 | cholesky | NM Dev 1.2.6 | 24.099313 | 1.11524631 |
| 1024 | cholesky | NM Dev 1.2.6 | 189.67425 | 3.74177251 |
| 2000 | cholesky | NM Dev 1.2.6 | 1456.23282 | 157.777151 |
| 512 | qr | NM Dev 1.2.6 | 151.738033 | 34.4095181 |
| 1024 | qr | NM Dev 1.2.6 | 1118.59205 | 131.486103 |
| 2000 | qr | NM Dev 1.2.6 | 7996.40368 | 1384.87157 |
| 512 | solve | NM Dev 1.2.6 | 32.7936 | 5.21440352 |
| 1024 | solve | NM Dev 1.2.6 | 175.115767 | 12.0607314 |
| 2000 | solve | NM Dev 1.2.6 | 1680.11988 | 122.369636 |
| 512 | multiplication | EJML 0.46.1 | 29.4755717 | 11.2291749 |
| 1024 | multiplication | EJML 0.46.1 | 443.724433 | 129.625161 |
| 2000 | multiplication | EJML 0.46.1 | 3606.21822 | 233.098906 |
| 512 | lu | EJML 0.46.1 | 46.1654783 | 7.79440592 |
| 1024 | lu | EJML 0.46.1 | 439.961167 | 35.2654966 |
| 2000 | lu | EJML 0.46.1 | 3352.298 | 206.109831 |
| 512 | cholesky | EJML 0.46.1 | 23.2167222 | 1.07709569 |
| 1024 | cholesky | EJML 0.46.1 | 77.5781111 | 15.9698771 |
| 2000 | cholesky | EJML 0.46.1 | 502.459167 | 15.3080985 |
| 512 | qr | EJML 0.46.1 | 91.3280083 | 24.0264376 |
| 1024 | qr | EJML 0.46.1 | 800.001433 | 38.8358034 |
| 2000 | qr | EJML 0.46.1 | 6489.10672 | 279.199711 |
| 512 | solve | EJML 0.46.1 | 49.0620008 | 11.3070879 |
| 1024 | solve | EJML 0.46.1 | 435.568 | 62.3582465 |
| 2000 | solve | EJML 0.46.1 | 3250.3809 | 284.261832 |
| 512 | multiplication | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 203.336433 | 22.8259746 |
| 1024 | multiplication | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 8116.12403 | 224.871081 |
| 2000 | multiplication | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 89597.9667 | 3367.20133 |
| 512 | lu | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 56.3521667 | 5.01105502 |
| 1024 | lu | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 1092.62363 | 235.669689 |
| 2000 | lu | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 19945.4525 | 865.24385 |
| 512 | cholesky | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 16.6447091 | 2.28129517 |
| 1024 | cholesky | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 153.368358 | 99.500133 |
| 2000 | cholesky | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 1237.82873 | 54.6382162 |
| 512 | qr | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 133.818233 | 39.0129628 |
| 1024 | qr | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 1044.89058 | 299.177503 |
| 2000 | qr | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 8708.83372 | 2919.04948 |
| 512 | solve | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 70.8671139 | 64.1123675 |
| 1024 | solve | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 1303.75553 | 559.92655 |
| 2000 | solve | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 20280.2618 | 760.28211 |
| 512 | multiplication | JAMA 1.0.3 | 157.649975 | 37.8066114 |
| 1024 | multiplication | JAMA 1.0.3 | 1269.80005 | 61.8656894 |
| 2000 | multiplication | JAMA 1.0.3 | 9819.90395 | 1113.96187 |
| 512 | lu | JAMA 1.0.3 | 51.1334642 | 5.67847749 |
| 1024 | lu | JAMA 1.0.3 | 453.035583 | 94.7734656 |
| 2000 | lu | JAMA 1.0.3 | 3354.58527 | 487.587831 |
| 512 | cholesky | JAMA 1.0.3 | 22.7034976 | 3.39276705 |
| 1024 | cholesky | JAMA 1.0.3 | 203.6996 | 22.1543199 |
| 2000 | cholesky | JAMA 1.0.3 | 1653.65912 | 315.111072 |
| 512 | qr | JAMA 1.0.3 | 457.681217 | 65.9425164 |
| 1024 | qr | JAMA 1.0.3 | 16192.689 | 743.008344 |
| 2000 | qr | JAMA 1.0.3 | 204783.694 | 6371.40977 |
| 512 | solve | JAMA 1.0.3 | 48.0255992 | 9.25987082 |
| 1024 | solve | JAMA 1.0.3 | 479.097817 | 90.6805493 |
| 2000 | solve | JAMA 1.0.3 | 3381.3644 | 379.478832 |
| 512 | multiplication | Colt 1.2.0 | 297.020817 | 48.3001778 |
| 1024 | multiplication | Colt 1.2.0 | 6267.62797 | 340.030217 |
| 2000 | multiplication | Colt 1.2.0 | 43475.7968 | 1371.46251 |
| 512 | lu | Colt 1.2.0 | 51.7733239 | 23.1482623 |
| 1024 | lu | Colt 1.2.0 | 446.763367 | 84.3596983 |
| 2000 | lu | Colt 1.2.0 | 3436.88737 | 948.063193 |
| 512 | cholesky | Colt 1.2.0 | 23.3776383 | 4.08611467 |
| 1024 | cholesky | Colt 1.2.0 | 209.449083 | 72.7864013 |
| 2000 | cholesky | Colt 1.2.0 | 1515.5035 | 358.880462 |
| 512 | qr | Colt 1.2.0 | 734.178467 | 80.3749926 |
| 1024 | qr | Colt 1.2.0 | 11800.8159 | 1354.46257 |
| 2000 | qr | Colt 1.2.0 | 126661.872 | 2476.90297 |
| 512 | solve | Colt 1.2.0 | 61.9790097 | 31.2147578 |
| 1024 | solve | Colt 1.2.0 | 587.116583 | 155.218669 |
| 2000 | solve | Colt 1.2.0 | 3101.41208 | 196.72405 |
| 512 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 277.9055 | 127.256552 |
| 1024 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 6891.24315 | 1387.283 |
| 2000 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 43362.854 | 1765.6507 |
| 512 | lu | Parallel Colt 0.11.4 (upstream tag build) | 64.7478958 | 25.4423063 |
| 1024 | lu | Parallel Colt 0.11.4 (upstream tag build) | 364.096833 | 18.2437735 |
| 2000 | lu | Parallel Colt 0.11.4 (upstream tag build) | 3040.03832 | 545.223539 |
| 512 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 25.3414903 | 5.55331722 |
| 1024 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 216.137925 | 11.6250011 |
| 2000 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 1374.96668 | 128.09072 |
| 512 | qr | Parallel Colt 0.11.4 (upstream tag build) | 182.909075 | 133.100054 |
| 1024 | qr | Parallel Colt 0.11.4 (upstream tag build) | 1483.05832 | 316.084083 |
| 2000 | qr | Parallel Colt 0.11.4 (upstream tag build) | 8900.29987 | 663.573649 |
| 512 | solve | Parallel Colt 0.11.4 (upstream tag build) | 56.2407333 | 12.5089942 |
| 1024 | solve | Parallel Colt 0.11.4 (upstream tag build) | 429.018117 | 138.54937 |
| 2000 | solve | Parallel Colt 0.11.4 (upstream tag build) | 3626.25117 | 802.94335 |
| 512 | multiplication | jblas 1.2.5 (native) | 104.551456 | 31.8074882 |
| 1024 | multiplication | jblas 1.2.5 (native) | 840.9257 | 30.3508733 |
| 2000 | multiplication | jblas 1.2.5 (native) | 8346.46052 | 7499.2898 |
| 512 | lu | jblas 1.2.5 (native) | 35.5496 | 2.66301737 |
| 1024 | lu | jblas 1.2.5 (native) | 270.677667 | 17.0177343 |
| 2000 | lu | jblas 1.2.5 (native) | 1832.46985 | 116.291362 |
| 512 | cholesky | jblas 1.2.5 (native) | 25.9523688 | 1.81559572 |
| 1024 | cholesky | jblas 1.2.5 (native) | 208.535667 | 24.683146 |
| 2000 | cholesky | jblas 1.2.5 (native) | 1449.17993 | 26.477076 |
| 512 | qr | jblas 1.2.5 (native) | 235.637708 | 104.424752 |
| 1024 | qr | jblas 1.2.5 (native) | 1579.44817 | 102.313995 |
| 2000 | qr | jblas 1.2.5 (native) | 11584.9262 | 236.860412 |
| 512 | solve | jblas 1.2.5 (native) | 32.5972452 | 2.85700082 |
| 1024 | solve | jblas 1.2.5 (native) | 248.53265 | 23.2422692 |
| 2000 | solve | jblas 1.2.5 (native) | 1717.51667 | 100.444234 |
| 512 | multiplication | F2J LAPACK/BLAS 0.1 | 32.0993544 | 8.0809553 |
| 1024 | multiplication | F2J LAPACK/BLAS 0.1 | 432.974683 | 66.4166609 |
| 2000 | multiplication | F2J LAPACK/BLAS 0.1 | 3564.90573 | 159.798318 |
| 512 | lu | F2J LAPACK/BLAS 0.1 | 14.7098812 | 1.49185944 |
| 1024 | lu | F2J LAPACK/BLAS 0.1 | 118.560542 | 13.0538347 |
| 2000 | lu | F2J LAPACK/BLAS 0.1 | 704.5227 | 45.8106798 |
| 512 | cholesky | F2J LAPACK/BLAS 0.1 | 8.9605642 | 4.14211658 |
| 1024 | cholesky | F2J LAPACK/BLAS 0.1 | 65.2412486 | 7.09324365 |
| 2000 | cholesky | F2J LAPACK/BLAS 0.1 | 574.957533 | 76.8536077 |
| 512 | qr | F2J LAPACK/BLAS 0.1 | 119.190733 | 17.4090383 |
| 1024 | qr | F2J LAPACK/BLAS 0.1 | 976.518533 | 89.4153552 |
| 2000 | qr | F2J LAPACK/BLAS 0.1 | 7188.05813 | 208.4014 |
| 512 | solve | F2J LAPACK/BLAS 0.1 | 14.3201082 | 4.25368242 |
| 1024 | solve | F2J LAPACK/BLAS 0.1 | 101.509853 | 8.59418082 |
| 2000 | solve | F2J LAPACK/BLAS 0.1 | 673.4682 | 114.892191 |
| 512 | multiplication | MTJ 1.0.4 (F2J) | 31.0642996 | 4.20949612 |
| 1024 | multiplication | MTJ 1.0.4 (F2J) | 434.035 | 61.456159 |
| 2000 | multiplication | MTJ 1.0.4 (F2J) | 3544.62947 | 217.302709 |
| 512 | lu | MTJ 1.0.4 (F2J) | 13.516255 | 0.507164049 |
| 1024 | lu | MTJ 1.0.4 (F2J) | 105.504142 | 19.3293533 |
| 2000 | lu | MTJ 1.0.4 (F2J) | 673.707 | 46.1520278 |
| 512 | cholesky | MTJ 1.0.4 (F2J) | 25.4966032 | 2.92005277 |
| 1024 | cholesky | MTJ 1.0.4 (F2J) | 194.95565 | 4.29045414 |
| 2000 | cholesky | MTJ 1.0.4 (F2J) | 1409.4851 | 30.72079 |
| 512 | qr | MTJ 1.0.4 (F2J) | 114.769358 | 6.60381648 |
| 1024 | qr | MTJ 1.0.4 (F2J) | 973.583933 | 84.4861264 |
| 2000 | qr | MTJ 1.0.4 (F2J) | 7155.9168 | 149.047173 |
| 512 | solve | MTJ 1.0.4 (F2J) | 14.4148916 | 4.02799975 |
| 1024 | solve | MTJ 1.0.4 (F2J) | 101.561614 | 8.50822665 |
| 2000 | solve | MTJ 1.0.4 (F2J) | 665.012917 | 61.0712301 |
| 512 | multiplication | ojAlgo 57.5.0 | 28.5409643 | 5.94057485 |
| 1024 | multiplication | ojAlgo 57.5.0 | 436.602917 | 169.45506 |
| 2000 | multiplication | ojAlgo 57.5.0 | 3438.44983 | 225.074017 |
| 512 | lu | ojAlgo 57.5.0 | 13.8313936 | 1.71768738 |
| 1024 | lu | ojAlgo 57.5.0 | 155.89595 | 114.197003 |
| 2000 | lu | ojAlgo 57.5.0 | 1720.88453 | 447.333751 |
| 512 | cholesky | ojAlgo 57.5.0 | 7.09166188 | 0.168444611 |
| 1024 | cholesky | ojAlgo 57.5.0 | 69.7514139 | 41.0595755 |
| 2000 | cholesky | ojAlgo 57.5.0 | 877.400517 | 153.279441 |
| 512 | qr | ojAlgo 57.5.0 | 72.8432931 | 17.6196526 |
| 1024 | qr | ojAlgo 57.5.0 | 665.823617 | 109.576411 |
| 2000 | qr | ojAlgo 57.5.0 | 5979.8049 | 235.113269 |
| 512 | solve | ojAlgo 57.5.0 | 13.7588124 | 2.67024061 |
| 1024 | solve | ojAlgo 57.5.0 | 169.240175 | 55.5424112 |
| 2000 | solve | ojAlgo 57.5.0 | 1870.5451 | 363.492565 |
| 512 | multiplication | UJMP 0.3.0 (core) | 257.873867 | 45.5392717 |
| 1024 | multiplication | UJMP 0.3.0 (core) | 7803.69508 | 154.889043 |
| 2000 | multiplication | UJMP 0.3.0 (core) | 88152.5366 | 3234.98463 |
| 512 | lu | UJMP 0.3.0 (core) | 44.45752 | 3.25832676 |
| 1024 | lu | UJMP 0.3.0 (core) | 396.901733 | 10.2428326 |
| 2000 | lu | UJMP 0.3.0 (core) | 3197.98938 | 34.429924 |
| 512 | cholesky | UJMP 0.3.0 (core) | 22.0457493 | 1.91717441 |
| 1024 | cholesky | UJMP 0.3.0 (core) | 195.392125 | 12.112528 |
| 2000 | cholesky | UJMP 0.3.0 (core) | 1585.12858 | 68.8627133 |
| 512 | qr | UJMP 0.3.0 (core) | 468.756567 | 131.838265 |
| 1024 | qr | UJMP 0.3.0 (core) | 15915.9198 | 845.573927 |
| 2000 | qr | UJMP 0.3.0 (core) | 203120.498 | 18578.4619 |
| 512 | solve | UJMP 0.3.0 (core) | 53.9121069 | 29.8204394 |
| 1024 | solve | UJMP 0.3.0 (core) | 497.617733 | 91.8637699 |
| 2000 | solve | UJMP 0.3.0 (core) | 3553.21162 | 245.955838 |

## Large — 4 requested compute thread(s)

[Interactive chart](benchmarks-large-4thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 512 | multiplication | NM Dev 1.2.6 | 79.5911611 | 18.6134206 |
| 1024 | multiplication | NM Dev 1.2.6 | 357.6633 | 315.303466 |
| 2000 | multiplication | NM Dev 1.2.6 | 1894.14083 | 253.807491 |
| 512 | lu | NM Dev 1.2.6 | 35.609784 | 28.7169733 |
| 1024 | lu | NM Dev 1.2.6 | 201.797967 | 116.700927 |
| 2000 | lu | NM Dev 1.2.6 | 2212.31613 | 482.997019 |
| 512 | cholesky | NM Dev 1.2.6 | 13.9180529 | 7.09109221 |
| 1024 | cholesky | NM Dev 1.2.6 | 75.9423778 | 15.9760845 |
| 2000 | cholesky | NM Dev 1.2.6 | 454.537867 | 28.9853844 |
| 512 | qr | NM Dev 1.2.6 | 106.554481 | 45.862868 |
| 1024 | qr | NM Dev 1.2.6 | 467.183 | 194.343919 |
| 2000 | qr | NM Dev 1.2.6 | 4213.1137 | 1417.0838 |
| 512 | solve | NM Dev 1.2.6 | 36.3184361 | 5.2682754 |
| 1024 | solve | NM Dev 1.2.6 | 247.961192 | 147.742333 |
| 2000 | solve | NM Dev 1.2.6 | 2117.08538 | 954.448887 |
| 512 | multiplication | EJML 0.46.1 | 41.4638158 | 30.478386 |
| 1024 | multiplication | EJML 0.46.1 | 599.062033 | 545.179267 |
| 2000 | multiplication | EJML 0.46.1 | 4326.08873 | 688.140915 |
| 512 | lu | EJML 0.46.1 | 135.144378 | 183.689731 |
| 1024 | lu | EJML 0.46.1 | 563.527117 | 397.617565 |
| 2000 | lu | EJML 0.46.1 | 3857.48878 | 991.279533 |
| 512 | cholesky | EJML 0.46.1 | 24.1592134 | 2.45644515 |
| 1024 | cholesky | EJML 0.46.1 | 86.4824556 | 31.3130716 |
| 2000 | cholesky | EJML 0.46.1 | 553.972983 | 86.841402 |
| 512 | qr | EJML 0.46.1 | 93.0514194 | 25.8746767 |
| 1024 | qr | EJML 0.46.1 | 826.746333 | 143.221616 |
| 2000 | qr | EJML 0.46.1 | 7485.57557 | 1212.56418 |
| 512 | solve | EJML 0.46.1 | 48.4139042 | 5.58140272 |
| 1024 | solve | EJML 0.46.1 | 468.126167 | 50.2818842 |
| 2000 | solve | EJML 0.46.1 | 3387.62785 | 106.490366 |
| 512 | multiplication | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 336.5664 | 257.089393 |
| 1024 | multiplication | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 9429.0506 | 876.560207 |
| 2000 | multiplication | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 97368.6897 | 10593.3147 |
| 512 | lu | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 65.8944972 | 21.8243965 |
| 1024 | lu | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 1112.47828 | 375.729896 |
| 2000 | lu | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 20454.3125 | 2215.80808 |
| 512 | cholesky | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 16.8819517 | 2.2856366 |
| 1024 | cholesky | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 147.120158 | 51.4981926 |
| 2000 | cholesky | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 1312.9787 | 158.120393 |
| 512 | qr | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 127.710733 | 26.8821497 |
| 1024 | qr | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 1010.71105 | 85.8789956 |
| 2000 | qr | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 7599.8905 | 250.597719 |
| 512 | solve | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 58.1673417 | 7.88445564 |
| 1024 | solve | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 1072.3206 | 420.643992 |
| 2000 | solve | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 19857.5452 | 321.257775 |
| 512 | multiplication | JAMA 1.0.3 | 153.196267 | 9.31230464 |
| 1024 | multiplication | JAMA 1.0.3 | 1252.27182 | 46.3335541 |
| 2000 | multiplication | JAMA 1.0.3 | 9298.83415 | 129.58753 |
| 512 | lu | JAMA 1.0.3 | 46.1796433 | 6.74510319 |
| 1024 | lu | JAMA 1.0.3 | 398.46005 | 41.2521686 |
| 2000 | lu | JAMA 1.0.3 | 3223.74603 | 139.574211 |
| 512 | cholesky | JAMA 1.0.3 | 21.674145 | 1.66092268 |
| 1024 | cholesky | JAMA 1.0.3 | 201.549792 | 48.0755444 |
| 2000 | cholesky | JAMA 1.0.3 | 1584.36835 | 103.547927 |
| 512 | qr | JAMA 1.0.3 | 506.1706 | 99.8513113 |
| 1024 | qr | JAMA 1.0.3 | 15752.0057 | 397.685108 |
| 2000 | qr | JAMA 1.0.3 | 200908.555 | 13705.2932 |
| 512 | solve | JAMA 1.0.3 | 44.0890967 | 4.50533876 |
| 1024 | solve | JAMA 1.0.3 | 406.334783 | 24.6309377 |
| 2000 | solve | JAMA 1.0.3 | 3278.42263 | 426.336886 |
| 512 | multiplication | Colt 1.2.0 | 295.134883 | 36.717689 |
| 1024 | multiplication | Colt 1.2.0 | 5797.56865 | 907.576503 |
| 2000 | multiplication | Colt 1.2.0 | 42646.0022 | 1229.6184 |
| 512 | lu | Colt 1.2.0 | 45.6123983 | 13.8523273 |
| 1024 | lu | Colt 1.2.0 | 423.662883 | 100.099736 |
| 2000 | lu | Colt 1.2.0 | 3451.09645 | 1112.9399 |
| 512 | cholesky | Colt 1.2.0 | 21.6981526 | 2.04751827 |
| 1024 | cholesky | Colt 1.2.0 | 183.589642 | 25.0460336 |
| 2000 | cholesky | Colt 1.2.0 | 1411.37243 | 75.3531067 |
| 512 | qr | Colt 1.2.0 | 683.654283 | 39.230475 |
| 1024 | qr | Colt 1.2.0 | 11723.1087 | 1451.07185 |
| 2000 | qr | Colt 1.2.0 | 133891.788 | 40771.1483 |
| 512 | solve | Colt 1.2.0 | 48.0770758 | 11.0778133 |
| 1024 | solve | Colt 1.2.0 | 482.53155 | 137.948203 |
| 2000 | solve | Colt 1.2.0 | 3404.60832 | 506.587016 |
| 512 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 80.8407389 | 30.1969256 |
| 1024 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 1562.8065 | 823.432167 |
| 2000 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 14076.1794 | 2437.60222 |
| 512 | lu | Parallel Colt 0.11.4 (upstream tag build) | 78.8276089 | 100.525345 |
| 1024 | lu | Parallel Colt 0.11.4 (upstream tag build) | 426.565017 | 101.090508 |
| 2000 | lu | Parallel Colt 0.11.4 (upstream tag build) | 3653.30085 | 773.343812 |
| 512 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 21.6602618 | 16.5764688 |
| 1024 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 84.1614611 | 20.0236941 |
| 2000 | cholesky | Parallel Colt 0.11.4 (upstream tag build) | 467.1696 | 139.674549 |
| 512 | qr | Parallel Colt 0.11.4 (upstream tag build) | 90.0591722 | 42.2257137 |
| 1024 | qr | Parallel Colt 0.11.4 (upstream tag build) | 428.183917 | 47.5959264 |
| 2000 | qr | Parallel Colt 0.11.4 (upstream tag build) | 3959.38765 | 4330.0869 |
| 512 | solve | Parallel Colt 0.11.4 (upstream tag build) | 129.397792 | 47.4330076 |
| 1024 | solve | Parallel Colt 0.11.4 (upstream tag build) | 642.8775 | 381.262912 |
| 2000 | solve | Parallel Colt 0.11.4 (upstream tag build) | 3545.9621 | 492.552378 |
| 512 | multiplication | jblas 1.2.5 (native) | 149.630617 | 79.7047274 |
| 1024 | multiplication | jblas 1.2.5 (native) | 1204.90065 | 528.058457 |
| 2000 | multiplication | jblas 1.2.5 (native) | 7156.7802 | 1547.20079 |
| 512 | lu | jblas 1.2.5 (native) | 39.1185578 | 11.1135202 |
| 1024 | lu | jblas 1.2.5 (native) | 292.493633 | 67.004411 |
| 2000 | lu | jblas 1.2.5 (native) | 2122.68875 | 775.45577 |
| 512 | cholesky | jblas 1.2.5 (native) | 26.5132612 | 5.27004514 |
| 1024 | cholesky | jblas 1.2.5 (native) | 220.605458 | 42.4527219 |
| 2000 | cholesky | jblas 1.2.5 (native) | 1487.73433 | 152.759361 |
| 512 | qr | jblas 1.2.5 (native) | 206.644325 | 37.818475 |
| 1024 | qr | jblas 1.2.5 (native) | 1560.77052 | 27.065424 |
| 2000 | qr | jblas 1.2.5 (native) | 12500.1434 | 319.365098 |
| 512 | solve | jblas 1.2.5 (native) | 34.5074468 | 4.61938008 |
| 1024 | solve | jblas 1.2.5 (native) | 246.681433 | 15.4185269 |
| 2000 | solve | jblas 1.2.5 (native) | 1892.93058 | 197.244989 |
| 512 | multiplication | F2J LAPACK/BLAS 0.1 | 38.3029733 | 8.50098938 |
| 1024 | multiplication | F2J LAPACK/BLAS 0.1 | 511.3645 | 59.914742 |
| 2000 | multiplication | F2J LAPACK/BLAS 0.1 | 4351.29305 | 383.126338 |
| 512 | lu | F2J LAPACK/BLAS 0.1 | 16.0156733 | 3.28044853 |
| 1024 | lu | F2J LAPACK/BLAS 0.1 | 128.451742 | 11.1990792 |
| 2000 | lu | F2J LAPACK/BLAS 0.1 | 746.500367 | 30.1327127 |
| 512 | cholesky | F2J LAPACK/BLAS 0.1 | 9.654928 | 3.91261172 |
| 1024 | cholesky | F2J LAPACK/BLAS 0.1 | 102.102164 | 76.0632874 |
| 2000 | cholesky | F2J LAPACK/BLAS 0.1 | 727.62915 | 151.174882 |
| 512 | qr | F2J LAPACK/BLAS 0.1 | 131.835542 | 41.9522862 |
| 1024 | qr | F2J LAPACK/BLAS 0.1 | 1017.79245 | 96.5404175 |
| 2000 | qr | F2J LAPACK/BLAS 0.1 | 7698.37472 | 298.060542 |
| 512 | solve | F2J LAPACK/BLAS 0.1 | 14.110578 | 0.684226125 |
| 1024 | solve | F2J LAPACK/BLAS 0.1 | 104.525233 | 19.3188442 |
| 2000 | solve | F2J LAPACK/BLAS 0.1 | 677.685133 | 36.3975284 |
| 512 | multiplication | MTJ 1.0.4 (F2J) | 37.3600952 | 11.383614 |
| 1024 | multiplication | MTJ 1.0.4 (F2J) | 570.156917 | 212.463701 |
| 2000 | multiplication | MTJ 1.0.4 (F2J) | 4035.82713 | 962.102329 |
| 512 | lu | MTJ 1.0.4 (F2J) | 14.0264263 | 0.928685793 |
| 1024 | lu | MTJ 1.0.4 (F2J) | 106.222458 | 18.8465874 |
| 2000 | lu | MTJ 1.0.4 (F2J) | 657.310283 | 36.6110815 |
| 512 | cholesky | MTJ 1.0.4 (F2J) | 25.3948312 | 1.92834246 |
| 1024 | cholesky | MTJ 1.0.4 (F2J) | 198.094742 | 10.8670899 |
| 2000 | cholesky | MTJ 1.0.4 (F2J) | 1413.80205 | 17.2855953 |
| 512 | qr | MTJ 1.0.4 (F2J) | 132.103392 | 41.5849588 |
| 1024 | qr | MTJ 1.0.4 (F2J) | 1009.60117 | 86.2956217 |
| 2000 | qr | MTJ 1.0.4 (F2J) | 7423.35352 | 794.770854 |
| 512 | solve | MTJ 1.0.4 (F2J) | 13.5209415 | 1.45480909 |
| 1024 | solve | MTJ 1.0.4 (F2J) | 104.587881 | 6.93685533 |
| 2000 | solve | MTJ 1.0.4 (F2J) | 662.21715 | 51.346308 |
| 512 | multiplication | ojAlgo 57.5.0 | 9.00460977 | 2.55499891 |
| 1024 | multiplication | ojAlgo 57.5.0 | 115.643919 | 37.2059845 |
| 2000 | multiplication | ojAlgo 57.5.0 | 1252.38285 | 221.085626 |
| 512 | lu | ojAlgo 57.5.0 | 15.1274477 | 2.10062618 |
| 1024 | lu | ojAlgo 57.5.0 | 210.8515 | 201.944576 |
| 2000 | lu | ojAlgo 57.5.0 | 1506.00102 | 505.40271 |
| 512 | cholesky | ojAlgo 57.5.0 | 23.020512 | 3.56085131 |
| 1024 | cholesky | ojAlgo 57.5.0 | 82.3318056 | 27.458882 |
| 2000 | cholesky | ojAlgo 57.5.0 | 617.97525 | 109.049635 |
| 512 | qr | ojAlgo 57.5.0 | 57.682125 | 8.94969936 |
| 1024 | qr | ojAlgo 57.5.0 | 458.269083 | 60.0347422 |
| 2000 | qr | ojAlgo 57.5.0 | 5013.79983 | 1392.71502 |
| 512 | solve | ojAlgo 57.5.0 | 14.067439 | 2.7077795 |
| 1024 | solve | ojAlgo 57.5.0 | 197.271358 | 203.779857 |
| 2000 | solve | ojAlgo 57.5.0 | 1795.3608 | 264.465201 |
| 512 | multiplication | UJMP 0.3.0 (core) | 95.1414583 | 27.8598443 |
| 1024 | multiplication | UJMP 0.3.0 (core) | 2095.83733 | 254.99165 |
| 2000 | multiplication | UJMP 0.3.0 (core) | 26139.4844 | 1110.66878 |
| 512 | lu | UJMP 0.3.0 (core) | 55.1313756 | 26.7317099 |
| 1024 | lu | UJMP 0.3.0 (core) | 439.829167 | 68.1646305 |
| 2000 | lu | UJMP 0.3.0 (core) | 3386.67032 | 76.1632877 |
| 512 | cholesky | UJMP 0.3.0 (core) | 25.2067191 | 6.54694419 |
| 1024 | cholesky | UJMP 0.3.0 (core) | 211.676875 | 23.7561396 |
| 2000 | cholesky | UJMP 0.3.0 (core) | 1664.86062 | 53.1168365 |
| 512 | qr | UJMP 0.3.0 (core) | 539.127917 | 82.7833456 |
| 1024 | qr | UJMP 0.3.0 (core) | 16183.5512 | 618.013978 |
| 2000 | qr | UJMP 0.3.0 (core) | 203408.989 | 7309.06546 |
| 512 | solve | UJMP 0.3.0 (core) | 44.1017267 | 3.37708128 |
| 1024 | solve | UJMP 0.3.0 (core) | 420.247433 | 58.2716518 |
| 2000 | solve | UJMP 0.3.0 (core) | 3246.7872 | 85.2087997 |

## Rectangular — 1 requested compute thread(s)

[Interactive chart](benchmarks-rectangular-1thread.html)

| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |
|---|---|---|---:|---:|
| 128x512x64 | multiplication | NM Dev 1.2.6 | 6.96605029 | 0.124861265 |
| 512x128x256 | multiplication | NM Dev 1.2.6 | 15.0672881 | 0.360131043 |
| 1024x256x64 | multiplication | NM Dev 1.2.6 | 28.7790935 | 5.77863749 |
| 255x257x129 | multiplication | NM Dev 1.2.6 | 14.8678561 | 1.77500838 |
| 128x512x64 | multiplication | EJML 0.46.1 | 1.6629505 | 0.559537664 |
| 512x128x256 | multiplication | EJML 0.46.1 | 3.97812809 | 1.21515553 |
| 1024x256x64 | multiplication | EJML 0.46.1 | 6.4208685 | 0.871203503 |
| 255x257x129 | multiplication | EJML 0.46.1 | 2.608395 | 0.708365667 |
| 128x512x64 | multiplication | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 4.94395666 | 0.247217527 |
| 512x128x256 | multiplication | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 17.7833417 | 0.573614778 |
| 1024x256x64 | multiplication | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 17.8919014 | 0.43545865 |
| 255x257x129 | multiplication | Commons Math 4.0-SNAPSHOT (20261018.085925-1575) | 9.48139841 | 0.339888272 |
| 128x512x64 | multiplication | JAMA 1.0.3 | 4.20304111 | 0.0562616157 |
| 512x128x256 | multiplication | JAMA 1.0.3 | 13.7431968 | 3.91007067 |
| 1024x256x64 | multiplication | JAMA 1.0.3 | 16.2423358 | 2.74505657 |
| 255x257x129 | multiplication | JAMA 1.0.3 | 8.12136831 | 0.929454469 |
| 128x512x64 | multiplication | Colt 1.2.0 | 5.04317714 | 0.591714525 |
| 512x128x256 | multiplication | Colt 1.2.0 | 20.0874415 | 2.97442244 |
| 1024x256x64 | multiplication | Colt 1.2.0 | 14.382131 | 2.63169582 |
| 255x257x129 | multiplication | Colt 1.2.0 | 6.64838922 | 0.327012129 |
| 128x512x64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 4.86058801 | 0.800992297 |
| 512x128x256 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 20.0213848 | 3.17630791 |
| 1024x256x64 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 12.6682066 | 0.541677013 |
| 255x257x129 | multiplication | Parallel Colt 0.11.4 (upstream tag build) | 6.42751461 | 1.66334511 |
| 128x512x64 | multiplication | jblas 1.2.5 (native) | 3.10678522 | 1.05997809 |
| 512x128x256 | multiplication | jblas 1.2.5 (native) | 13.3575547 | 2.75890002 |
| 1024x256x64 | multiplication | jblas 1.2.5 (native) | 13.2072499 | 2.23426479 |
| 255x257x129 | multiplication | jblas 1.2.5 (native) | 5.62957025 | 0.30482615 |
| 128x512x64 | multiplication | F2J LAPACK/BLAS 0.1 | 1.35589445 | 0.0945720853 |
| 512x128x256 | multiplication | F2J LAPACK/BLAS 0.1 | 3.69818946 | 0.237408287 |
| 1024x256x64 | multiplication | F2J LAPACK/BLAS 0.1 | 4.28780808 | 1.24216729 |
| 255x257x129 | multiplication | F2J LAPACK/BLAS 0.1 | 2.33697814 | 0.0983674459 |
| 128x512x64 | multiplication | MTJ 1.0.4 (F2J) | 1.43003047 | 0.413458145 |
| 512x128x256 | multiplication | MTJ 1.0.4 (F2J) | 4.04609858 | 1.11446706 |
| 1024x256x64 | multiplication | MTJ 1.0.4 (F2J) | 4.86156042 | 1.54184188 |
| 255x257x129 | multiplication | MTJ 1.0.4 (F2J) | 2.48061308 | 0.314503086 |
| 128x512x64 | multiplication | ojAlgo 57.5.0 | 1.17497938 | 0.0770942674 |
| 512x128x256 | multiplication | ojAlgo 57.5.0 | 3.44726556 | 0.292201271 |
| 1024x256x64 | multiplication | ojAlgo 57.5.0 | 3.78359678 | 0.467419704 |
| 255x257x129 | multiplication | ojAlgo 57.5.0 | 2.37762284 | 0.632695225 |
| 128x512x64 | multiplication | UJMP 0.3.0 (core) | 6.09487567 | 0.526720237 |
| 512x128x256 | multiplication | UJMP 0.3.0 (core) | 28.4309381 | 6.42980921 |
| 1024x256x64 | multiplication | UJMP 0.3.0 (core) | 33.9014135 | 3.65642062 |
| 255x257x129 | multiplication | UJMP 0.3.0 (core) | 10.3572672 | 1.99216044 |

