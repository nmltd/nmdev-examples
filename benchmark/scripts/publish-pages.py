#!/usr/bin/env python3
"""Generate the benchmark website from versioned guide sources and one completed run."""
import argparse
import html
import json
import re
from pathlib import Path
import markdown

REPO_URL = 'https://github.com/nmltd/nmdev-examples'
NAV = [('benchmarks.html', 'Charts'), ('results.html', 'Results'), ('methodology.html', 'Methodology'),
       ('libraries.html', 'Libraries'), ('validation.html', 'Validation'), ('fairness-audit.html', 'Audit'), ('reproduce.html', 'Reproduce')]
CSS = '''body{font:16px/1.6 system-ui,sans-serif;margin:0;background:#f4f6f8;color:#172b3a}main{max-width:1200px;margin:auto;padding:24px}a{color:#126c78}nav{display:flex;gap:18px;flex-wrap:wrap;padding:12px 0;border-bottom:1px solid #ccd6dd}nav a[aria-current]{font-weight:700;text-decoration-thickness:3px}h1{line-height:1.2}h2{margin-top:2em}table{border-collapse:collapse;width:100%;font-size:14px}th,td{padding:9px;border:1px solid #d5dde3;text-align:left}th{background:#e6eff2}pre{overflow:auto;padding:18px;background:#e6edf2;border-radius:8px}code{font-size:.9em}img{max-width:100%}.table-scroll{overflow-x:auto}footer{margin-top:40px;border-top:1px solid #ccd6dd;padding-top:16px}.note{color:#435766}'''

def nav(current):
    return '<nav aria-label="Benchmark navigation">' + ''.join(
        f'<a href="{url}"'+(' aria-current="page"' if url == current else '')+f'>{label}</a>' for url,label in NAV) + '</nav>'

def render(text):
    body = markdown.markdown(text, extensions=['tables', 'fenced_code', 'sane_lists'])
    body = body.replace('<table>', '<div class="table-scroll"><table>').replace('</table>', '</table></div>')
    return body

def site_nav(prefix=''):
    return '<nav aria-label="Site navigation">'+''.join(f'<a href="{prefix}{url}">{label}</a>' for url,label in [('index.html','Home'),('getting-started.html','Getting Started'),('tutorials.html','Tutorials'),('benchmarks.html','Benchmarks')])+f'<a href="{REPO_URL}">Source</a></nav>'

def page(title, text, current):
    return f'''<!doctype html><html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>{html.escape(title)} — NM Dev benchmarks</title><style>{CSS}</style></head><body><main>{site_nav()}{nav(current)}{render(text)}<footer><a href="{REPO_URL}/tree/main/benchmark">Benchmark source and run archives</a> · <a href="tutorials.html">Java tutorials</a></footer></main></body></html>'''

def build_tutorials(repo, output):
    sources = [repo/'docs/Home.md', repo/'docs/README.md', repo/'docs/Getting-Started.md',
               repo/'docs/starter-project.md', repo/'docs/Source-Provenance.md', repo/'docs/Validation.md']
    sources += sorted((repo/'docs/tutorials').glob('*.md'))
    destinations = {source: ('index.html' if source.name == 'Home.md' else
                   'tutorials.html' if source.name == 'README.md' else
                   'getting-started.html' if source.name == 'Getting-Started.md' else
                   source.relative_to(repo/'docs').with_suffix('.html').as_posix()) for source in sources}
    import posixpath
    for source, destination in destinations.items():
        target = output/destination
        prefix = '../' if '/' in destination else ''
        text = source.read_text()
        def rewrite(match):
            label, href = match.groups()
            if re.match(r'https?://|#|mailto:', href) or href.endswith('.html'): return match.group(0)
            path, _, anchor = href.partition('#')
            resolved = (source.parent/path).resolve()
            if resolved in destinations:
                href = posixpath.relpath(destinations[resolved], posixpath.dirname(destination) or '.')
                if anchor: href += '#'+anchor
            else:
                href = REPO_URL+'/blob/main/'+resolved.relative_to(repo).as_posix()
                if anchor: href += '#'+anchor
            return f'[{label}]({href})'
        text = re.sub(r'\[([^\]]+)\]\(([^)]+)\)', rewrite, text)
        # GitHub math fences become display equations for MathJax on Pages.
        text = re.sub(r'```math\s*\n(.*?)\n```', lambda m: '<div class="math-display">\\[\n'+html.escape(m.group(1))+'\n\\]</div>', text, flags=re.S)
        body = render(text)
        title = re.search(r'^# (.+)', text).group(1)
        target.write_text(f'<!doctype html><html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>{html.escape(title)} — NM Dev examples</title><style>{CSS}.math-display{{overflow-x:auto}}</style><script defer src="https://cdn.jsdelivr.net/npm/mathjax@3.2.2/es5/tex-mml-chtml.js"></script></head><body><main>{site_nav(prefix)}{body}<footer><a href="{prefix}tutorials.html">All tutorials</a> · <a href="{REPO_URL}/blob/main/{source.relative_to(repo).as_posix()}">Edit this page</a></footer></main></body></html>')
    print(f'Generated {len(sources)} tutorial and setup pages from docs Markdown.')

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
    def archive_links(text, base=source):
        def link(match):
            label, target = match.groups()
            if target in dict(NAV) or re.match(r'https?://|#', target): return match.group(0)
            path = (base/target).resolve().relative_to(repo).as_posix()
            return f'[{label}]({REPO_URL}/blob/main/{path})'
        return re.sub(r'\[([^\]]+)\]\(([^)]+)\)', link, text)
    sections = re.split(r'(?m)^## ', report)
    groups = {}
    for section in sections[1:]:
        heading, _, content = section.partition('\n')
        groups[heading] = content.strip()
    intro = (f'# Benchmark results — {source.name}\n\n'
             f'{len(rows)} measured cases across {len({r["params"]["library"] for r in rows})} libraries. '
             '[Open interactive charts](benchmarks.html). Tables show mean ± JMH 99.9% confidence half-width in ms/op.\n\n')
    intro += '\n'.join(line for line in report.splitlines() if line.startswith('> '))+'\n\n'
    result = intro + '\n\n'.join('## '+h+'\n\n'+c for h,c in groups.items()
        if h not in ['Versions and backends', 'Correctness and interpretation'])
    result = archive_links(result).replace(REPO_URL+'/blob/main/docs/benchmarks.html','benchmarks.html')
    # Static chart previews duplicate the interactive figures, so tables link to Charts instead.
    result = re.sub(r'!\[[^\]]*\]\([^)]*\)', '[Inspect this matrix size on the interactive charts](benchmarks.html)', result)
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
    audit=repo/'benchmark/results/2026-10-18-fairness-audit'
    if (audit/'README.md').exists(): pages['fairness-audit.html']=('Fairness audit',archive_links((audit/'README.md').read_text(),audit))
    for name,(title,text) in pages.items(): (output/name).write_text(page(title,text,name))
    from interactive_charts import write_explorer
    chart = write_explorer(source, rows, site_nav()+nav('benchmarks.html'), CSS)
    environment=json.loads((source/'environment.json').read_text()) if (source/'environment.json').exists() else {}
    if environment.get('fairness_revision'):
        notice='<p role="note"><strong>Fairness revision 2:</strong> timed outputs are materialized and native inputs are checked for mutation. Short audit timings are diagnostic. <a href="fairness-audit.html">Audit and accuracy findings</a>.</p>'
    else:
        notice='<p role="note"><strong>Historical harness issue:</strong> ojAlgo scaling returned deferred work. Its scaling points are invalid for completed-work comparisons. <a href="fairness-audit.html">Read the audit</a>.</p>'
    chart=chart.replace('<h1>NM Dev benchmark explorer</h1>','<h1>NM Dev benchmark explorer</h1>'+notice)
    (output/'benchmarks.html').write_text(chart)
    (output/'.nojekyll').write_text('')
    build_tutorials(repo, output)
    print(f'Generated {len(pages)+1} benchmark Pages from {source.name}; raw measurements unchanged.')

if __name__ == '__main__': main()
