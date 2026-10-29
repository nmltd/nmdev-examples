# NM Dev 1.2.8 — Windows full suite

Completed 2026-10-28. All 1,260 cases and six preflight checks passed. Two independently warmed forks per case; six measurement means per case.

See [publication tables](PUBLICATION.md), [saved measurements](jmh.json), [summary](summary.csv), [validation](validation-report.json) and [archive digest](publication-format.json).

Requested compute thread counts are configuration limits, not audited worker counts. EJML uses the sequential CommonOps_DDRM multiplication API. Commons row and block implementations are sequential. All-core profiles therefore compare configured library behavior, not equal parallel implementations. Short measurements have potentially wide confidence intervals; results are machine specific. No cross-version speedup is inferred because the Commons snapshot changed.

Archive limitation: `cases/large__1thread__UJMP__solve__2000/fork-1.json` is missing. Its combined samples and checkpoint digest are present, but that fork cannot be checksum-verified. The other 2,519 fork JSON files passed checksum verification.
