# Historical benchmark results

Runs are stored under `YYYY-MM-DD/<run-id>/`. Dates and timestamps use UTC.

[Browse all historical benchmarks](https://nmltd.github.io/nmdev-examples/benchmarks/index.html) · [Latest charts](https://nmltd.github.io/nmdev-examples/benchmarks.html)

| Date / run | Status | Cases | Website |
|---|---|---:|---|
| [2026-10-20/windows-082238Z](2026-10-20/windows-082238Z/) | Completed and validated | 660 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-20/windows-082238Z/index.html) |
| [2026-10-18/updated-libraries-baseline](2026-10-18/updated-libraries-baseline/) | Completed | 286 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-18/updated-libraries-baseline/index.html) |
| [2026-10-18/rectangular](2026-10-18/rectangular/) | Completed | 16 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-18/rectangular/index.html) |
| [2026-10-18/original](2026-10-18/original/) | Historical snapshot; no completion marker | 104 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-18/original/index.html) |
| [2026-10-18/large-4threads](2026-10-18/large-4threads/) | Completed | 40 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-18/large-4threads/index.html) |
| [2026-10-18/large-1thread](2026-10-18/large-1thread/) | Completed | 40 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-18/large-1thread/index.html) |
| [2026-10-18/fairness-corrected](2026-10-18/fairness-corrected/) | Completed | 286 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-18/fairness-corrected/index.html) |
| [2026-10-18/fairness-audit](2026-10-18/fairness-audit/) | Audit | 0 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-18/fairness-audit/index.html) |
| [2026-10-18/expanded](2026-10-18/expanded/) | Completed | 286 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-18/expanded/index.html) |
| [2026-10-18/2000-4thread](2026-10-18/2000-4thread/) | Completed | 20 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-18/2000-4thread/index.html) |
| [2026-10-18/2000-1thread](2026-10-18/2000-1thread/) | Completed | 20 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-18/2000-1thread/index.html) |

## Add a future run

1. Save the published measurements under a new `YYYY-MM-DD/<unique-run-id>/` directory. Never overwrite a previous run. Use a UTC time suffix when several runs share a date.
2. Add a `runs.json` entry with hardware, completion status, interpretation notes and SHA-256 digests for `jmh.json` and `summary.csv`. Include a release link and checksum when an original ZIP is published.
3. Set `latest` to the new entry, then run `python benchmark/scripts/publish-history.py` and `python scripts/check-site.py`. The build checks stored evidence digests and generates all historical pages.
4. Commit the saved data, registry and generated pages together. The main chart URL follows `latest`; dated URLs stay fixed.

Full original ZIPs belong in dated GitHub Releases. The website uses published measurements; raw logs and local filesystem details need not be embedded in the chart data.
