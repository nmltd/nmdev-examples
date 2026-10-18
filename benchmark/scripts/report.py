#!/usr/bin/env python3
"""Generate benchmark tables, SVG/HTML charts and tooltips from completed JMH JSON."""
import argparse
import csv
import json
from pathlib import Path
from chart_tooltips import annotate_svg, write_html

OPERATIONS = ["transpose", "addition", "scale", "multiplication", "determinant", "inverse", "inverseSPD", "cholesky", "lu", "qr", "eigenSymmetric", "svd", "solve"]
LIBRARIES = ["NMDEV", "EJML", "COMMONS", "JAMA", "COLT", "PARALLEL_COLT", "JBLAS", "F2J_LAPACK", "MTJ", "OJALGO", "UJMP"]
# Historical fallback labels; every current run supplies its exact versions.json.
VERSIONS = {"NMDEV":"NM Dev 1.2.6-SNAPSHOT", "EJML":"EJML 0.46.1", "COMMONS":"Commons Math 3.6.1", "JAMA":"JAMA 1.0.3", "COLT":"Colt 1.2.0", "PARALLEL_COLT":"Parallel Colt 0.10.1", "JBLAS":"jblas 1.2.5 (native)", "F2J_LAPACK":"F2J LAPACK/BLAS 0.1", "MTJ":"MTJ 1.0.4 (F2J)", "OJALGO":"ojAlgo 57.5.0", "UJMP":"UJMP 0.3.0 (core)"}
COLORS = dict(zip(LIBRARIES,["#126c78","#4678ba","#d18829","#9d608e","#6b8c31","#b8493a","#6750a4","#4d8794","#b77265","#7a756e","#aa8a25"]))

def table(rows):
    return "\n".join("| " + " | ".join(map(str,row)) + " |" for row in rows)

def main():
    parser=argparse.ArgumentParser(description=__doc__);parser.add_argument("directory",type=Path);root=parser.parse_args().directory
    versions=json.loads((root/"versions.json").read_text()) if (root/"versions.json").exists() else dict(VERSIONS)
    rows=json.loads((root/"jmh.json").read_text())
    index={(r["params"]["library"],int(r["params"]["size"]),r["params"]["operation"]):r for r in rows}
    libraries=[lib for lib in LIBRARIES if any(r["params"]["library"]==lib for r in rows)]
    sizes=sorted({int(r["params"]["size"]) for r in rows})
    operations=[op for op in OPERATIONS if any(r["params"]["operation"]==op for r in rows)]
    expected=len(libraries)*len(sizes)*len(operations)
    assert len(index)==len(rows)==expected, "Incomplete or duplicate benchmark matrix"
    assert "NMDEV" in libraries
    for size in sizes:
        for op in operations:
            for lib in libraries:
                r=index[lib,size,op];metric=r["primaryMetric"]
                assert metric["scoreUnit"]=="ms/op" and metric["score"]>0
                assert len(metric["rawData"])==r["forks"]
                assert all(len(fork)==r["measurementIterations"] for fork in metric["rawData"])
    iterations=sum(len(fork) for r in rows for fork in r["primaryMetric"]["rawData"])
    with (root/"summary.csv").open("w",newline="") as output:
        writer=csv.writer(output);writer.writerow(["size","operation","library","version","mean_ms_per_op","jmh_99_9_error_ms","ratio_to_nmdev","measurement_iterations"])
        for size in sizes:
            for op in operations:
                baseline=index["NMDEV",size,op]["primaryMetric"]["score"]
                for lib in libraries:
                    metric=index[lib,size,op]["primaryMetric"]
                    writer.writerow([size,op,lib,versions[lib],metric["score"],metric["scoreError"],metric["score"]/baseline,sum(map(len,metric["rawData"]))])
    if any("gc.alloc.rate.norm" in row.get("secondaryMetrics",{}) for row in rows):
        with (root/"allocation.csv").open("w",newline="") as output:
            writer=csv.writer(output);writer.writerow(["size","operation","library","jvm_allocated_bytes_per_op","jmh_99_9_error_bytes"])
            for row in rows:
                metric=row.get("secondaryMetrics",{}).get("gc.alloc.rate.norm",{})
                writer.writerow([row["params"]["size"],row["params"]["operation"],row["params"]["library"],metric.get("score"),metric.get("scoreError")])
    environment=json.loads((root/"environment.json").read_text()) if (root/"environment.json").exists() else {}
    first=rows[0]
    sections=[f"# Linear algebra benchmark results — {root.name}",
        "[Suite](../../README.md) · [Methodology](../../METHODOLOGY.md) · [Library coverage](../../LIBRARY-COVERAGE.md)",
        f"**{len(operations)} operations × {len(libraries)} libraries × {len(sizes)} sizes = {len(rows)} measured cases**, containing {iterations:,} measured iteration means. Lower milliseconds per operation is faster. These are observations on a shared VM, not universal performance rankings.",
        "**[Interactive charts with tooltips](charts.html)** — download and open the self-contained HTML in a browser. Hover, tap or keyboard-focus a point for its version/backend, timing, confidence interval and ratio to NM Dev. GitHub inline image previews do not expose point tooltips. Standalone SVGs also contain native tooltip titles.",
        "## Environment",
        f"{environment.get('cpu_model','Unrecorded CPU')}; {environment.get('os','Unrecorded OS')}. Visible CPUs: {environment.get('visible_cpus','unrecorded')}; container CPU quota: `{environment.get('cpu_quota','unrecorded')}`. JVM {first['jdkVersion']}. No exclusive CPU reservation; any requested affinity is recorded below.",
        f"{first['forks']} JVM forks per case; {first['warmupIterations']} warmup iterations of {first['warmupTime']}; {first['measurementIterations']} measurement iterations of {first['measurementTime']}. One JMH worker. The fork options fix a 1 GiB heap. Requested computational threads: {environment.get('requested_compute_threads',1)}; requested CPU affinity: {environment.get('requested_affinity','unrecorded')}. Affinity does not reserve a CPU exclusively.",
        "Native thread environment variables request the recorded computational thread count. Parallel Colt, ojAlgo and UJMP also receive explicit library thread limits; MTJ uses an explicitly forced pure-Java F2J backend. Native jblas thread counts are not independently audited. See the environment and backend records below.",
        "## Versions and backends"]
    backend=json.loads((root/"backends.json").read_text()) if (root/"backends.json").exists() else {}
    if "OJALGO" in libraries and not environment.get("fairness_revision"):
        sections.insert(2,"> Historical run: ojAlgo scalar multiplication returned a deferred expression. Its scaling timings are invalid for completed-work comparisons. Raw measurements are preserved. See the [fairness audit](../2026-10-18-fairness-audit/README.md).")
    if environment.get("fairness_revision"):
        sections.insert(2,"> Corrected harness: deferred ojAlgo results are materialized inside timing, native inputs are checked for mutation, and every fork validates repeat execution. These short iterations provide a diagnostic baseline; use longer runs on dedicated hardware for fine rankings. See the [fairness audit](../2026-10-18-fairness-audit/README.md).")
    sections.append(table([["Library","Measured version / backend"],["---","---"]]+[[lib,versions[lib]+(" — "+backend[lib] if lib in backend else "")] for lib in libraries]))
    correctness=list(csv.DictReader((root/"correctness.csv").open()))
    assert all(r["status"]=="PASS" for r in correctness), "Correctness failure in published evidence"
    sections += ["## Correctness and interpretation",f"All **{len(correctness)} baseline correctness cases passed twice**. Every measured fork also checks its selected result before timing. Reference arithmetic and residual checks are outside timing."]
    if (root/"pivot-correctness.csv").exists():
        pivot=list(csv.DictReader((root/"pivot-correctness.csv").open()));assert all(r["status"]=="PASS" for r in pivot)
        sections.append(f"All **{len(pivot)} targeted row-swap/row-cycle cases** also passed. These check determinant signs, LU permutation orientation and solves on pivoting inputs.")
    sections += ["Tables show **mean ± JMH 99.9% confidence half-width**, in ms/op. These error bars are not standard deviations. Overlapping intervals or high uncertainty make fine rankings unreliable. Chart ratios are ratios of means and do not show ratio uncertainty.",
        "Native input conversion is outside timing; necessary copies, allocations and full factor output materialization are timed. This compares public API tasks, not identical algorithms. NM Dev Inverse detects symmetry and attempts Cholesky on SPD inputs, falling back to LU if that path raises a runtime exception. The exact measured JAR was verified separately by bytecode and runtime constructor-breakpoint tracing; see the fairness audit. F2J transpose/addition/scaling use adapter-owned Java loops because LAPACK does not define those matrix convenience APIs."]
    for size in sizes:
        sections.append(f"## {size} × {size} matrices")
        data=[["Operation"]+libraries,["---"]+["---:"]*len(libraries)]
        for op in operations:
            data.append([op]+[f"{index[lib,size,op]['primaryMetric']['score']:.5g} ± {index[lib,size,op]['primaryMetric']['scoreError']:.2g}" for lib in libraries])
        sections += [table(data),f"![Timing ratios relative to NM Dev at size {size}](relative-times-{size}.svg)","NM Dev is 1×. Below 1× means less time than NM Dev; above 1× means more time. Logarithmic scale. Use the interactive HTML for exact point details."]
    sections += ["## Limits", "No aggregate speedup is reported because averaging operation ratios imposes an arbitrary workload weighting. This run uses one deterministic family of well-conditioned square matrices. It does not measure sparse matrices, conic optimization, parallel scaling, very large inputs or stability on difficult problems. Parameter order is deterministic, and the VM is shared. Reproduce your own workload on dedicated hardware before making broad performance claims.","## Raw evidence"]
    evidence=[("jmh.json","JMH JSON and per-fork iteration means"),("jmh.log","Complete JMH log"),("summary.csv","Summary CSV"),("allocation.csv","JVM allocation per operation; native allocations are not fully included"),("correctness.csv","Baseline correctness residuals"),("pivot-correctness.csv","Targeted pivot/sign checks"),("environment.json","Environment and requested thread settings"),("backends.json","Backend records"),("backend-observation.txt","Post-run backend configuration probe"),("artifacts.json","Artifact versions and SHA-256 checksums"),("source-checksums.json","Measured source checksums"),("dependency-metadata.json","Dependency release metadata"),("harness-source.zip","Exact harness source snapshot"),("fairness-check.txt","Deferred-output and mutation regression checks")]
    sections.append("\n".join(f"- [{label}]({name})" for name,label in evidence if (root/name).exists()))
    sections.append(f"Regenerate with `python3 benchmark/scripts/report.py benchmark/results/{root.name}`. Python only summarizes measurements and renders charts; all measured calculations run in Java/JMH. Matplotlib is required to regenerate charts and the self-contained HTML.")
    (root/"README.md").write_text("\n\n".join(sections)+"\n")
    import matplotlib
    matplotlib.use("Agg")
    import matplotlib.pyplot as plt
    plt.rcParams.update({"font.size":10,"svg.fonttype":"none"})
    for size in sizes:
        fig,ax=plt.subplots(figsize=(13,12 if len(libraries)>4 else 8));tooltips={}
        for j,lib in enumerate(libraries):
            for i,op in enumerate(operations):
                metric=index[lib,size,op]["primaryMetric"];ratio=metric["score"]/index["NMDEV",size,op]["primaryMetric"]["score"]
                y=i+(j-(len(libraries)-1)/2)*(.78/len(libraries))
                point=ax.scatter([ratio],[y],s=28,color=COLORS[lib],label=versions[lib] if i==0 else None,zorder=3)
                key=f"point-{size}-{lib}-{op}";point.set_gid(key)
                low,high=metric["scoreConfidence"]
                tooltips[key]=(f"{versions[lib]} · {op} · {size} × {size}\n"
                    f"Mean: {metric['score']:.6g} ms/op\n"
                    f"99.9% confidence interval: [{low:.6g}, {high:.6g}] ms/op\n"
                    f"JMH error half-width: ±{metric['scoreError']:.3g} ms/op\n"
                    f"Time / NM Dev: {ratio:.4f}× (lower is faster)\n"
                    f"{first['forks']} JVM forks · {first['forks']*first['measurementIterations']} measured iteration means")
        ax.set_yticks(range(len(operations)),operations);ax.invert_yaxis();ax.set_xscale("log")
        ax.axvline(1,color="#333333",linewidth=1,linestyle="--");ax.set_xlabel("Time / NM Dev time (log scale; lower is faster)")
        ax.set_title(f"Dense linear algebra — {size} × {size}\n{len(libraries)} libraries · ratios of measured means on a shared VM",loc="left",fontweight="bold",pad=15)
        ax.grid(axis="x",alpha=.2);ax.set_axisbelow(True)
        ax.legend(loc="upper center",bbox_to_anchor=(.5,-.07 if len(libraries)>4 else -.11),ncol=3 if len(libraries)>4 else 2,frameon=False,fontsize=9)
        fig.text(.01,.01,f"{first['forks']} JVM forks · JVM {first['jdkVersion']} · 1 worker · See report for backend details and confidence intervals",fontsize=9,color="#555555")
        fig.tight_layout(rect=(0,.04,1,1));fig.savefig(root/f"relative-times-{size}.svg",metadata={"Date":None});plt.close(fig)
        annotate_svg(root/f"relative-times-{size}.svg",tooltips)
    write_html(root,sizes)
    chart_path=root/"charts.html"
    chart=chart_path.read_text()
    if "OJALGO" in libraries and not environment.get("fairness_revision"):
        notice='<p role="note"><strong>Historical harness issue:</strong> ojAlgo scaling returned deferred work. Its scaling points do not measure completed scaling and must not be used for speed comparisons. See the fairness audit.</p>'
    else:
        notice='<p role="note"><strong>Fairness revision 2:</strong> timed outputs are materialized; inputs and repeat execution are checked outside timing. Short audit timings are diagnostic. See the report for thread settings and uncertainty.</p>'
    chart_path.write_text(chart.replace('<h1>NM Dev benchmark charts</h1>','<h1>NM Dev benchmark charts</h1>'+notice))
    print(f"Summarized {len(rows)} cases and {iterations} iteration means; generated {len(rows)} tooltip points")

if __name__=="__main__":main()
