#!/usr/bin/env python3
"""Summarize completed JMH JSON; never performs timing or changes measurements."""
import argparse
import csv
import json
from pathlib import Path
from chart_tooltips import annotate_svg, write_html


OPERATIONS = ["transpose", "addition", "scale", "multiplication", "determinant",
              "inverse", "inverseSPD", "cholesky", "lu", "qr", "eigenSymmetric", "svd", "solve"]
LIBRARIES = ["NMDEV", "EJML", "COMMONS", "JAMA"]
VERSIONS = {"NMDEV": "NM Dev 1.2.6-SNAPSHOT", "EJML": "EJML 0.46.1",
            "COMMONS": "Commons Math 3.6.1", "JAMA": "JAMA 1.0.3"}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("directory", type=Path)
    args = parser.parse_args()
    root = args.directory
    rows = json.loads((root / "jmh.json").read_text())
    index = {(r["params"]["library"], int(r["params"]["size"]), r["params"]["operation"]): r for r in rows}
    sizes = sorted({int(r["params"]["size"]) for r in rows})
    assert sizes == [64, 256] and len(rows) == 104, "Expected the complete published configuration"
    if len(index) != len(rows):
        raise ValueError("Duplicate benchmark cases")
    for size in sizes:
        for operation in OPERATIONS:
            for library in LIBRARIES:
                r = index[library, size, operation]
                assert r["primaryMetric"]["scoreUnit"] == "ms/op"
                assert len(r["primaryMetric"]["rawData"]) == 2
                assert all(len(fork) == 5 for fork in r["primaryMetric"]["rawData"])
                assert r["primaryMetric"]["score"] > 0
    with (root / "summary.csv").open("w", newline="") as output:
        writer = csv.writer(output)
        writer.writerow(["size", "operation", "library", "version", "mean_ms_per_op", "jmh_99_9_error_ms", "ratio_to_nmdev", "measurement_iterations"])
        for size in sizes:
            for operation in OPERATIONS:
                baseline = index["NMDEV", size, operation]["primaryMetric"]["score"]
                for library in LIBRARIES:
                    metric = index[library, size, operation]["primaryMetric"]
                    writer.writerow([size, operation, library, VERSIONS[library], metric["score"], metric["scoreError"], metric["score"] / baseline, 10])

    environment_path = root / "environment.json"
    environment = json.loads(environment_path.read_text()) if environment_path.exists() else {}
    environment_description = (f"{environment.get('cpu_model', 'Unrecorded CPU')}; {environment.get('os', 'Unrecorded OS')}. "
                               f"CPU quota: `{environment.get('cpu_quota', 'unrecorded')}`; visible CPUs: {environment.get('visible_cpus', 'unrecorded')}. "
                               "See the recorded environment files for JVM details. No exclusive CPU reservation or affinity pinning is claimed.")
    sections = [f"# Linear algebra benchmark results — {root.name}",
                "[Suite and reproduction instructions](../../README.md) · [Methodology](../../METHODOLOGY.md)",
                "**[Interactive charts with tooltips](charts.html)** — download and open the HTML file in a browser. Hover, tap or keyboard-focus a point for its timing, 99.9% confidence interval and ratio to NM Dev. GitHub inline image previews do not expose these tooltips; the standalone SVGs also include native tooltip titles.",
                "Measured here: **13 operations × four libraries × two sizes = 104 cases**, with two JVM forks and ten measured iteration means per case. Lower milliseconds per operation is faster. These are observations on a shared virtual machine, not universal performance rankings.",
                "## Environment",
                environment_description,
                "Three 300 ms warmup iterations and five 500 ms measurement iterations per fork; two forks. Matrices are deterministic, dense and well-conditioned. Native input construction is outside timing; result allocation, needed input copies and factor materialization are timed.",
                "## Checks and interpretation",
                "All 156 correctness cases (including size 4) passed twice. Each measured fork also validates its selected result before timing. See [correctness.csv](correctness.csv).",
                "Tables show **mean ± JMH 99.9% confidence half-width**, in milliseconds per operation. This is not standard deviation. Very small differences or overlapping intervals should not be treated as established rankings. Normalized charts use ratios of means and do not display ratio uncertainty.",
                "The adapters use different public APIs and sometimes different algorithms. In particular NM Dev uses its general inverse routine on the SPD task, while the other adapters use Cholesky-based inversion. Consult the [methodology](../../METHODOLOGY.md) and adapter source before interpreting any timing difference."]
    for size in sizes:
        sections += [f"## {size} × {size} matrices"]
        table = ["| Operation | NM Dev | EJML | Commons Math | JAMA |", "|---|---:|---:|---:|---:|"]
        for operation in OPERATIONS:
            values = []
            for library in LIBRARIES:
                metric = index[library, size, operation]["primaryMetric"]
                values.append(f"{metric['score']:.5g} ± {metric['scoreError']:.2g}")
            table.append("| " + " | ".join([operation] + values) + " |")
        sections.append("\n".join(table))
        sections += [f"![Timing ratios relative to NM Dev at size {size}](relative-times-{size}.svg)",
                     "The NM Dev reference is 1×. Below 1× means less time than NM Dev; above 1× means more time. Logarithmic scale. These are task-level API measurements on this input family."]
    sections += ["## Reading the results", "No single aggregate speedup is reported: averaging operation ratios would impose an arbitrary workload weighting. Choose the operations and sizes relevant to your application, then reproduce them on your hardware. The small-size results are especially sensitive to allocation and dispatch overhead.",
                 "This run does not measure sparse matrices, conic optimization, native BLAS, large matrices, parallel scaling or numerical stability on difficult inputs. It does not compare the removed legacy adapters.",
                 "## Raw evidence", "- [JMH JSON, including raw per-fork iterations and confidence intervals](jmh.json)\n- [Complete JMH output](jmh.log)\n- [Summary CSV](summary.csv)\n- [Correctness residuals](correctness.csv)\n- [Environment](environment.json)\n- [Artifact versions and SHA-256 checksums](artifacts.json)\n- [Measured source checksums](source-checksums.json)",
                 "Regenerate the summary with `python3 benchmark/scripts/report.py benchmark/results/2026-10-18` from the repository root. Python is used only for reporting; every measured operation runs in Java under JMH. Matplotlib is optional for regenerating the SVG charts."]
    (root / "README.md").write_text("\n\n".join(sections) + "\n")

    try:
        import matplotlib
        matplotlib.use("Agg")
        import matplotlib.pyplot as plt
    except ImportError:
        print("Summary generated; install matplotlib to regenerate SVG charts.")
        return
    plt.rcParams.update({"font.size": 10, "svg.fonttype": "none"})
    colors = {"NMDEV": "#126c78", "EJML": "#4678ba", "COMMONS": "#d18829", "JAMA": "#9d608e"}
    for size in sizes:
        fig, ax = plt.subplots(figsize=(12, 8))
        tooltips = {}
        for j, library in enumerate(LIBRARIES):
            values = [index[library, size, op]["primaryMetric"]["score"] / index["NMDEV", size, op]["primaryMetric"]["score"] for op in OPERATIONS]
            positions = [i + (j - 1.5) * 0.19 for i in range(len(OPERATIONS))]
            for i, operation in enumerate(OPERATIONS):
                point = ax.scatter([values[i]], [positions[i]], s=36, color=colors[library],
                                   label=VERSIONS[library] if i == 0 else None, zorder=3)
                key = f"point-{size}-{library}-{operation}"
                point.set_gid(key)
                metric = index[library, size, operation]["primaryMetric"]
                low, high = metric["scoreConfidence"]
                tooltips[key] = (f"{VERSIONS[library]} · {operation} · {size} × {size}\n"
                                 f"Mean: {metric['score']:.6g} ms/op\n"
                                 f"99.9% confidence interval: [{low:.6g}, {high:.6g}] ms/op\n"
                                 f"JMH error half-width: ±{metric['scoreError']:.3g} ms/op\n"
                                 f"Time / NM Dev: {values[i]:.4f}× (lower is faster)\n"
                                 "2 JVM forks · 10 measured iteration means")
        ax.set_yticks(range(len(OPERATIONS)), OPERATIONS)
        ax.invert_yaxis()
        ax.set_xscale("log")
        ax.axvline(1, color="#333333", linewidth=1, linestyle="--")
        ax.set_xlabel("Time / NM Dev time (log scale; lower is faster)")
        ax.set_title(f"Dense linear algebra — {size} × {size}\nRatios of measured means on a shared VM", loc="left", fontweight="bold", pad=15)
        ax.grid(axis="x", alpha=0.2)
        ax.set_axisbelow(True)
        ax.legend(loc="upper center", bbox_to_anchor=(0.5, -0.11), ncol=2, frameon=False)
        fig.text(0.01, 0.01, f"2 JVM forks · 10 measured iterations/case · JVM {rows[0]['jdkVersion']} · 1 worker · See report for confidence intervals", fontsize=9, color="#555555")
        fig.tight_layout(rect=(0, 0.05, 1, 1))
        fig.savefig(root / f"relative-times-{size}.svg", metadata={"Date": None})
        plt.close(fig)
        annotate_svg(root / f"relative-times-{size}.svg", tooltips)
    write_html(root, sizes)
    print(f"Summarized {len(rows)} measured cases")


if __name__ == "__main__":
    main()
