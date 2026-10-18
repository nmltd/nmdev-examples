#!/usr/bin/env python3
"""Build the dependency-free Pages dashboard from a completed benchmark report."""
from pathlib import Path
import argparse
parser = argparse.ArgumentParser(description=__doc__)
parser.add_argument('results', type=Path, help='Completed results directory containing charts.html')
args = parser.parse_args()
repo = Path(__file__).resolve().parents[2]
source = args.results.resolve()
html = (source / 'charts.html').read_text()
nav = '''<nav aria-label="Resources"><a href="https://github.com/nmltd/nmdev-examples/wiki/Benchmark-Results">Benchmark guide</a> · <a href="https://github.com/nmltd/nmdev-examples/blob/main/benchmark/METHODOLOGY.md">Methodology</a> · <a href="https://github.com/nmltd/nmdev-examples/tree/main/benchmark">Reproduce the benchmark</a> · <a href="https://github.com/nmltd/nmdev-examples/blob/main/docs/README.md">Java tutorials</a></nav>
<p class="note">Run: 2026-10-18 · 11 libraries · 13 operations · 64 × 64 and 256 × 256 matrices · 286 measured cases. Timings are specific to the recorded versions, backends and shared VM.</p>'''
html = html.replace('<h1>NM Dev benchmark charts</h1>', '<h1>NM Dev benchmark charts</h1>\n' + nav)
(repo / 'docs' / 'index.html').write_text(html)
(repo / 'docs' / '.nojekyll').write_text('')
print('Built docs/index.html from ' + str(source.relative_to(repo)))
