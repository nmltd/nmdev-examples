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

def site_nav(prefix=''):
    return '<nav aria-label="Site navigation">'+''.join(f'<a href="{prefix}{url}">{label}</a>' for url,label in [('getting-started.html','Getting Started'),('tutorials.html','Tutorials'),('index.html','Benchmarks')])+f'<a href="{REPO_URL}">Source</a></nav>'

def page(title, text, current):
    return f'''<!doctype html><html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>{html.escape(title)} — NM Dev benchmarks</title><style>{CSS}</style></head><body><main>{site_nav()}{nav(current)}{render(text)}<footer><a href="{REPO_URL}/tree/main/benchmark">Benchmark source and run archives</a> · <a href="tutorials.html">Java tutorials</a></footer></main></body></html>'''

def build_tutorials(repo, output):
    sources = [repo/'docs/README.md', repo/'docs/Getting-Started.md',
               repo/'docs/starter-project.md', repo/'docs/Source-Provenance.md', repo/'docs/Validation.md']
    sources += sorted((repo/'docs/tutorials').glob('*.md'))
    destinations = {source: ('tutorials.html' if source.name == 'README.md' else
                   'getting-started.html' if source.name == 'Getting-Started.md' else
                   source.relative_to(repo/'docs').with_suffix('.html').as_posix()) for source in sources}
    import posixpath
    for source, destination in destinations.items():
        target = output/destination
        prefix = '../' if '/' in destination else ''
        text = source.read_text()
        def rewrite(match):
            label, href = match.groups()
            if re.match(r'https?://|#|mailto:', href): return match.group(0)
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

def add_library_filters(chart, rows):
    """Add one accessible selection shared by both inline SVG charts."""
    from report import LIBRARIES, VERSIONS, COLORS
    libraries = [lib for lib in LIBRARIES if any(row['params']['library'] == lib for row in rows)]
    controls = '<fieldset id="library-filters"><legend>Visible libraries</legend><p>Select libraries to compare. Selection applies to both charts; NM Dev remains the 1× reference even when its points are hidden. Axis scales stay fixed for comparison.</p><div class="library-options">'
    for lib in libraries:
        controls += (f'<label><input type="checkbox" value="{lib}" checked> '
                     f'<span class="library-swatch" style="background:{COLORS[lib]}" aria-hidden="true"></span> '
                     f'{html.escape(VERSIONS[lib])}</label>')
        chart = re.sub(r'(id="chart-\d+-point-\d+-'+re.escape(lib)+r'-[^\"]+")',
                       r'\1 data-library="'+lib+'"', chart)
    controls += '</div><div class="filter-actions"><button type="button" id="show-all">Show all</button> <button type="button" id="hide-all">Hide all</button></div><p id="selection-status" role="status" aria-live="polite"></p></fieldset>'
    chart = chart.replace('<section><h2>', controls+'<section><h2>', 1)
    style = '#library-filters{border:1px solid #ccd6dd;border-radius:12px;background:white;padding:16px;margin:24px 0}#library-filters legend{font-size:1.2em;font-weight:700}.library-options{display:grid;grid-template-columns:repeat(auto-fit,minmax(240px,1fr));gap:10px}.library-options label{display:flex;align-items:center;gap:7px;cursor:pointer;padding:8px;border:1px solid #dbe2e8;border-radius:6px}.library-options input{width:18px;height:18px;accent-color:#126c78}.library-swatch{width:12px;height:12px;border-radius:50%;flex-shrink:0}.filter-actions{margin-top:16px}.filter-actions button{padding:8px 16px;cursor:pointer}svg [data-library][hidden]{display:none!important}'
    chart = chart.replace('</style>', style+'</style>')
    script = """
<script>
(()=>{
 const boxes=[...document.querySelectorAll('#library-filters input')];
 const points=[...document.querySelectorAll('svg [data-library]')];
 const status=document.getElementById('selection-status');
 function update(){
  const selected=new Set(boxes.filter(box=>box.checked).map(box=>box.value));
  points.forEach(point=>{
   const visible=selected.has(point.dataset.library);
   point.toggleAttribute('hidden',!visible);
   point.setAttribute('aria-hidden',String(!visible));
   point.setAttribute('tabindex',visible?'0':'-1');
  });
  // The plot legend retains every series; dim unselected legend labels.
  document.querySelectorAll('svg g[id*="legend_"] text').forEach(label=>{
   const box=boxes.find(box=>box.closest('label').textContent.trim()===label.textContent.trim());
   if(box)label.style.opacity=box.checked?'1':'0.25';
  });
  document.getElementById('tooltip').hidden=true;
  status.textContent=selected.size+' of '+boxes.length+' libraries shown'+(selected.size===0?'. Select a library or Show all to display results.':'.');
 }
 boxes.forEach(box=>box.addEventListener('change',update));
 document.getElementById('show-all').addEventListener('click',()=>{boxes.forEach(box=>box.checked=true);update()});
 document.getElementById('hide-all').addEventListener('click',()=>{boxes.forEach(box=>box.checked=false);update()});
 update();
})();
</script>
"""
    return chart.replace('</body>',script+'</body>')

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
    chart = chart.replace('<h1>NM Dev benchmark charts</h1>',site_nav()+nav('index.html')+'<h1>NM Dev benchmark charts</h1>')
    chart = chart.replace('Full report and raw measurements','Raw measurements and archived report')
    chart = chart.replace('<p>Hover, tap or keyboard-focus any point to inspect its measurements.</p>',
        f'<p class="note">Run: {html.escape(source.name)} · {len(rows)} measured cases. <a href="results.html">Results tables</a> · <a href="libraries.html">Versions and backends</a></p><p>Hover, tap or keyboard-focus any point to inspect its measurements. Press Escape to dismiss.</p>')
    chart = chart.replace('</main>', '<footer><a href="reproduce.html">Reproduce and publish</a> · <a href="tutorials.html">Java tutorials</a></footer></main>')
    chart = add_library_filters(chart, rows)
    (output/'index.html').write_text(chart)
    (output/'.nojekyll').write_text('')
    build_tutorials(repo, output)
    print(f'Generated six benchmark Pages from {source.name}; raw measurements unchanged.')

if __name__ == '__main__': main()
