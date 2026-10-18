#!/usr/bin/env python3
"""Generate the benchmark website from versioned guide sources and one completed run."""
import argparse
import html
import json
import re
from pathlib import Path
import markdown

REPO_URL = 'https://github.com/nmltd/nmdev-examples'
NAV = [('index.html', 'Charts'), ('results.html', 'Results'), ('methodology.html', 'Methodology'),
       ('libraries.html', 'Libraries'), ('validation.html', 'Validation'), ('reproduce.html', 'Reproduce')]
CSS = '''body{font:16px/1.6 system-ui,sans-serif;margin:0;background:#f4f6f8;color:#172b3a}main{max-width:1200px;margin:auto;padding:24px}a{color:#126c78}nav{display:flex;gap:18px;flex-wrap:wrap;padding:12px 0;border-bottom:1px solid #ccd6dd}nav a[aria-current]{font-weight:700;text-decoration-thickness:3px}h1{line-height:1.2}h2{margin-top:2em}table{border-collapse:collapse;width:100%;font-size:14px}th,td{padding:9px;border:1px solid #d5dde3;text-align:left}th{background:#e6eff2}pre{overflow:auto;padding:18px;background:#e6edf2;border-radius:8px}code{font-size:.9em}img{max-width:100%}.table-scroll{overflow-x:auto}footer{margin-top:40px;border-top:1px solid #ccd6dd;padding-top:16px}.note{color:#435766}'''

def nav(current):
    return '<nav aria-label="Benchmark navigation">' + ''.join(
        f'<a href="{url}"'+(' aria-current="page"' if url == current else '')+f'>{label}</a>' for url,label in NAV) + '</nav>'

def render(text):
    body = markdown.markdown(text, extensions=['tables', 'fenced_code', 'sane_lists'])
    body = body.replace('<table>', '<div class="table-scroll"><table>').replace('</table>', '</table></div>')
    return body

def page(title, text, current):
    return f'''<!doctype html><html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>{html.escape(title)} — NM Dev benchmarks</title><style>{CSS}</style></head><body><main>{nav(current)}{render(text)}<footer><a href="{REPO_URL}/tree/main/benchmark">Benchmark source and run archives</a> · <a href="{REPO_URL}/blob/main/docs/README.md">Java tutorials</a></footer></main></body></html>'''

def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('results', type=Path, help='Completed result archive with charts.html and jmh.json')
    args = parser.parse_args()
    repo = Path(__file__).resolve().parents[2]
    source = args.results.resolve()
    source.relative_to(repo)
    output = repo / 'docs'
    guide = repo / 'benchmark/site'
    rows = json.loads((source/'jmh.json').read_text())
    report = (source/'README.md').read_text()
    # Archive links remain durable GitHub links; navigation within the guide uses Pages.
    def archive_links(text):
        def link(match):
            label, target = match.groups()
            if target in dict(NAV) or re.match(r'https?://|#', target): return match.group(0)
            path = (source/target).resolve().relative_to(repo).as_posix()
            return f'[{label}]({REPO_URL}/blob/main/{path})'
        return re.sub(r'\[([^\]]+)\]\(([^)]+)\)', link, text)
    sections = re.split(r'(?m)^## ', report)
    groups = {}
    for section in sections[1:]:
        heading, _, content = section.partition('\n')
        groups[heading] = content.strip()
    intro = (f'# Benchmark results — {source.name}\n\n'
             f'{len(rows)} measured cases across {len({r["params"]["library"] for r in rows})} libraries. '
             '[Open interactive charts](index.html). Tables show mean ± JMH 99.9% confidence half-width in ms/op.\n\n')
    result = intro + '\n\n'.join('## '+h+'\n\n'+c for h,c in groups.items()
        if h not in ['Versions and backends', 'Correctness and interpretation'])
    result = archive_links(result).replace(REPO_URL+'/blob/main/docs/index.html','index.html')
    # Static chart previews duplicate the interactive figures, so tables link to Charts instead.
    result = re.sub(r'!\[[^\]]*\]\([^)]*\)', '[Inspect this matrix size on the interactive charts](index.html)', result)
    libraries = (guide/'libraries.md').read_text()
    libraries = re.sub(r'(?ms)^\| Library \|.*?(?=^## )', '', libraries)
    if 'Versions and backends' in groups:
        libraries += '\n\n## Measured backend records\n\n'+groups['Versions and backends']
    validation = '# Validation and interpretation\n\n'+groups['Correctness and interpretation']
    validation += '\n\n## Evidence\n\n'+groups.get('Raw evidence','').split('Regenerate with')[0]
    validation += '\n\nSee [Methodology](methodology.html) for input construction and residual checks.\n'
    pages = {'results.html':('Results',result), 'libraries.html':('Libraries',libraries),
             'validation.html':('Validation',archive_links(validation)),
             'methodology.html':('Methodology',(guide/'methodology.md').read_text()),
             'reproduce.html':('Reproduce',(guide/'reproduce.md').read_text())}
    for name,(title,text) in pages.items(): (output/name).write_text(page(title,text,name))
    chart = (source/'charts.html').read_text()
    chart = chart.replace('</style>',CSS+'</style>')
    chart = chart.replace('<h1>NM Dev benchmark charts</h1>',nav('index.html')+'<h1>NM Dev benchmark charts</h1>')
    chart = chart.replace('Full report and raw measurements','Raw measurements and archived report')
    chart = chart.replace('<p>Hover, tap or keyboard-focus any point to inspect its measurements.</p>',
        f'<p class="note">Run: {html.escape(source.name)} · {len(rows)} measured cases. <a href="results.html">Results tables</a> · <a href="libraries.html">Versions and backends</a></p><p>Hover, tap or keyboard-focus any point to inspect its measurements. Press Escape to dismiss.</p>')
    chart = chart.replace('</main>', '<footer><a href="reproduce.html">Reproduce and publish</a> · <a href="'+REPO_URL+'/blob/main/docs/README.md">Java tutorials</a></footer></main>')
    (output/'index.html').write_text(chart)
    (output/'.nojekyll').write_text('')
    print(f'Generated six benchmark Pages from {source.name}; raw measurements unchanged.')

if __name__ == '__main__': main()
