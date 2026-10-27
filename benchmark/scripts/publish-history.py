#!/usr/bin/env python3
"""Build date-indexed benchmark history and keep the latest entry points compatible."""
import hashlib
import html
import importlib.util
import json
import re
import subprocess
import sys
from collections import defaultdict
from pathlib import Path

REPO=Path(__file__).resolve().parents[2]
ROOT=REPO/'benchmark/results'
OUTPUT=REPO/'docs'
URL='https://github.com/nmltd/nmdev-examples'
spec=importlib.util.spec_from_file_location('pages',Path(__file__).with_name('publish-pages.py'))
site=importlib.util.module_from_spec(spec);spec.loader.exec_module(site)
from interactive_charts import write_explorer


def shell(title, body, prefix):
    return f'<!doctype html><html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>{html.escape(title)} — NM Dev benchmark history</title><style>{site.CSS}</style></head><body><main>{site.site_nav(prefix)}{body}</main></body></html>'


def report_links(text, source):
    def rewrite(m):
        label,target=m.groups()
        if re.match(r'https?://|mailto:|#',target):return m.group(0)
        path,_,anchor=target.partition('#')
        resolved=(source/path).resolve()
        if not resolved.is_relative_to(REPO):return m.group(0)
        return f'[{label}]({URL}/blob/main/{resolved.relative_to(REPO).as_posix()}'+('#'+anchor if anchor else '')+')'
    return re.sub(r'\[([^\]]+)\]\(([^)]+)\)',rewrite,text)


def overview(run, prefix='../../../'):
    source=ROOT/run['source']
    release=run.get('release')
    text=f"# {run['date']} — {run['title']}\n\n**{run['status']}** · {run['cases']} cases · {run['libraries']} libraries.\n\n{run['machine']}\n\n{run['warning']}\n\n"
    if run['completed_utc']:text+=f"Recorded completion: **{run['completed_utc']}**. Run IDs and dates are permanent; profiles from different machines are never merged.\n\n"
    text+='[All historical runs]('+prefix+'benchmarks/index.html) · [This date](../index.html) · [Saved files on GitHub]('+URL+'/tree/main/benchmark/results/'+run['source']+')\n\n'
    if release:
        text+=f"[Original output ZIP]({release['download_url']}) · [GitHub Release]({release['url']})\n\nOriginal ZIP SHA-256: `{release['sha256']}`\n\n"
    return site.render(text)


def dataset_table(rows,versions):
    lines=['| Profile | Requested threads | Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |','|---|---:|---|---|---|---:|---:|']
    for r in rows:
        p=r['params'];m=r['primaryMetric']
        lines.append(f"| {r.get('profile','recorded')} | {r.get('requested_threads','see fork options')} | {p.get('size',p.get('shape'))} | {p.get('operation','multiplication')} | {versions.get(p['library'],p['library'])} | {m['score']:.9g} | {m['scoreError']:.9g} |")
    return site.render('\n'.join(lines))


def run_index(run, charts):
    content=overview(run)
    content+=site.render('## Views\n\n'+'\n'.join(f'- [{title}]({name})' for name,title in charts)+('\n- [Results and saved report](results.html)' if run['cases'] else '\n- [Audit report](results.html)'))
    content+=site.render('\n## Evidence integrity\n\n'+'\n'.join(f'- `{name}` SHA-256: `{sha}`' for name,sha in run['evidence_sha256'].items()))
    return shell(run['title'],content,'../../../')


def historical_run(run):
    source=ROOT/run['source'];dest=OUTPUT/'benchmarks'/run['source'];dest.mkdir(parents=True,exist_ok=True)
    rows=json.loads((source/'jmh.json').read_text()) if run['cases'] else []
    nav=site.site_nav('../../../')+'<nav aria-label="Run navigation"><a href="index.html">Run overview</a><a href="results.html">Results and evidence</a><a href="../../../benchmarks/index.html">All dates</a></nav>'
    charts=[]
    if rows:
        name='benchmarks.html';charts=[(name,'Interactive charts')]
        chart=write_explorer(source,rows,nav,site.CSS,run_id=run['source'])
        chart=chart.replace('<h1>NM Dev benchmark explorer</h1>','<h1>NM Dev benchmark explorer</h1>'+overview(run))
        # Historical charts use their own results; current guidance remains at the root.
        for target in ['methodology.html','libraries.html','reproduce.html']:chart=chart.replace(f'href="{target}"',f'href="../../../{target}"')
        (dest/name).write_text(chart)
    readme=source/'README.md'
    report=readme.read_text() if readme.exists() else '# Saved measurements\n'
    body=overview(run)+site.render('## Original report\n\n'+report_links(report,source))
    if rows:
        versions=json.loads((source/'versions.json').read_text(encoding='utf-8-sig')) if (source/'versions.json').exists() else {}
        body+=site.render('## All saved timings\n\n')+dataset_table(rows,versions)
    (dest/'results.html').write_text(shell(run['title']+' — results',body,'../../../'))
    (dest/'index.html').write_text(run_index(run,charts))
    return charts


def latest_run(run):
    # Existing publisher retains all current chart controls and evidence summaries.
    subprocess.run([sys.executable,str(Path(__file__).with_name('publish-pages.py')),str(ROOT/run['source'])],check=True)
    dest=OUTPUT/'benchmarks'/run['source'];dest.mkdir(parents=True,exist_ok=True)
    names=['benchmarks.html','results.html','libraries.html','methodology.html','validation.html','reproduce.html']
    rows=json.loads((ROOT/run['source']/'jmh.json').read_text())
    groups=sorted({(r['profile'],r['requested_threads']) for r in rows})
    names += [f'benchmarks-{p}-{t}thread.html' for p,t in groups if (p,t)!=('baseline',1)]
    charts=[('benchmarks.html','Baseline charts')]+[(f'benchmarks-{p}-{t}thread.html',f'{p.title()} — {t} requested thread(s)') for p,t in groups if (p,t)!=('baseline',1)]
    for name in names:
        page=(OUTPUT/name).read_text().replace(site.site_nav(),site.site_nav('../../../'),1)
        # Keep run-local pages relative; route every other local link to the site root.
        def rewrite(m):
            target=m.group(1)
            if re.match(r'[a-z]+:|#|\.\./',target) or target.split('#')[0] in names:return m.group(0)
            return 'href="../../../'+target+'"'
        page=re.sub(r'href="([^"]+)"',rewrite,page)
        page=page.replace('<main>','<main><p><a href="index.html">Dated run overview</a> · <a href="../../../benchmarks/index.html">All historical benchmarks</a></p>',1)
        # Source name alone is not unique across dates.
        page=page.replace('"run":"'+(ROOT/run['source']).name+'"','"run":"'+run['source']+'"')
        (dest/name).write_text(page)
        current=(OUTPUT/name).read_text()
        banner=f'<p role="note"><strong>Latest published run: {run["date"]}.</strong> <a href="benchmarks/{run["source"]}/index.html">Permanent dated archive</a> · <a href="benchmarks/index.html">All historical runs</a></p>'
        (OUTPUT/name).write_text(current.replace('<main>','<main>'+banner,1))
    (dest/'index.html').write_text(run_index(run,charts))


def history_table(runs, prefix):
    rows=['| Date / run | Status | Cases | Libraries | Machine |','|---|---|---:|---:|---|']
    for r in sorted(runs,key=lambda r:(r['date'],r['completed_utc'] or '',r['id']),reverse=True):
        rows.append(f"| [{r['date']} — {r['title']}]({prefix}{r['source']}/index.html) | {r['status']} | {r['cases']} | {r['libraries']} | {r['machine']} |")
    return '\n'.join(rows)


def main():
    config=json.loads((ROOT/'runs.json').read_text());runs=config['runs']
    assert len({r['source'] for r in runs})==len(runs)
    for r in runs:
        assert r['source'].startswith(r['date']+'/') and '..' not in Path(r['source']).parts
        for name,digest in r['evidence_sha256'].items():
            assert hashlib.sha256((ROOT/r['source']/name).read_bytes()).hexdigest()==digest,f"Historical evidence changed: {r['source']}/{name}"
    current=next(r for r in runs if r['source']==config['latest'])
    assert current['date']==max(r['date'] for r in runs)
    latest_run(current)
    for run in runs:
        if run!=current:historical_run(run)
    heading='# Historical benchmarks\n\nBrowse every saved run by date. Each run has a permanent page, original measurements, hardware context and interpretation notes. Older runs remain available when the latest benchmark changes. Dates and timestamps use UTC.\n\n[Latest benchmark charts](../benchmarks.html) · [Latest dated run]('+current['source']+'/index.html)\n\n'
    folder=OUTPUT/'benchmarks';folder.mkdir(exist_ok=True)
    (folder/'index.html').write_text(shell('Historical benchmarks',site.render(heading+history_table(runs,'')),'../'))
    for date in sorted({r['date'] for r in runs}):
        subset=[r for r in runs if r['date']==date]
        text=f'# Benchmark runs — {date}\n\n[All historical dates](../index.html)\n\n'+history_table(subset,'../')
        (folder/date/'index.html').write_text(shell('Benchmarks — '+date,site.render(text),'../../'))
    # GitHub directory entry points are generated from the same registry as the site.
    md='# Historical benchmark results\n\nRuns are stored under `YYYY-MM-DD/<run-id>/`. Dates and timestamps use UTC.\n\n[Browse all historical benchmarks](https://nmltd.github.io/nmdev-examples/benchmarks/index.html) · [Latest charts](https://nmltd.github.io/nmdev-examples/benchmarks.html)\n\n| Date / run | Status | Cases | Website |\n|---|---|---:|---|\n'
    for r in sorted(runs,key=lambda r:r['source'],reverse=True):md+=f"| [{r['source']}]({r['source']}/) | {r['status']} | {r['cases']} | [Open](https://nmltd.github.io/nmdev-examples/benchmarks/{r['source']}/index.html) |\n"
    md+='\n## Add a future run\n\n1. Save the published measurements under a new `YYYY-MM-DD/<unique-run-id>/` directory. Never overwrite a previous run. Use a UTC time suffix when several runs share a date.\n2. Add a `runs.json` entry with hardware, completion status, interpretation notes and SHA-256 digests for `jmh.json` and `summary.csv`. Include a release link and checksum when an original ZIP is published.\n3. Set `latest` to the new entry, then run `python benchmark/scripts/publish-history.py` and `python scripts/check-site.py`. The build checks stored evidence digests and generates all historical pages.\n4. Commit the saved data, registry and generated pages together. The main chart URL follows `latest`; dated URLs stay fixed.\n\nFull original ZIPs belong in dated GitHub Releases. The website uses published measurements; raw logs and local filesystem details need not be embedded in the chart data.\n'
    (ROOT/'README.md').write_text(md)
    for date in {r['date'] for r in runs}:
        text=f'# Benchmark runs — {date}\n\n[All dates](../README.md)\n\n'
        text+='\n'.join(f"- [{r['title']}]({r['id']}/) — {r['cases']} cases; {r['status']}" for r in runs if r['date']==date)+'\n'
        (ROOT/date/'README.md').write_text(text)
    print(f'Generated {len(runs)} dated archives across {len({r["date"] for r in runs})} dates; historical evidence digests unchanged.')

if __name__=='__main__':main()
