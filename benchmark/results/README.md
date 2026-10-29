# Historical benchmark results

Runs are stored under `YYYY-MM-DD/<run-id>/`. Dates and timestamps use UTC.

[Browse all historical benchmarks](https://nmltd.github.io/nmdev-examples/benchmarks/index.html) · [Latest charts](https://nmltd.github.io/nmdev-examples/benchmarks.html)

| Date / run | Status | Cases | Website |
|---|---|---:|---|
| [2026-10-28/windows-081313Z](2026-10-28/windows-081313Z/) | Completed; archive limitation disclosed | 1260 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-28/windows-081313Z/index.html) |
| [2026-10-20/windows-082238Z](2026-10-20/windows-082238Z/) | Completed and validated | 660 | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/2026-10-20/windows-082238Z/index.html) |

## Add a future run

1. Save the published measurements under a new `YYYY-MM-DD/<unique-run-id>/` directory. Never overwrite a previous run. Use a UTC time suffix when several runs share a date.
2. Add a `runs.json` entry with hardware, completion status, interpretation notes and SHA-256 digests for `jmh.json` and `summary.csv`. Include a release link and checksum when an original ZIP is published.
3. Set `latest` to the new entry, then run `python benchmark/scripts/publish-history.py` and `python scripts/check-site.py`. The build checks stored evidence digests and generates all historical pages.
4. Commit the saved data, registry and generated pages together. The main chart URL follows `latest`; dated URLs stay fixed.

Full original ZIPs belong in dated GitHub Releases. The website uses published measurements; raw logs and local filesystem details need not be embedded in the chart data.
