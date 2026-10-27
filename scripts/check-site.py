#!/usr/bin/env python3
"""Check generated site links and key navigation without external network requests."""
from html.parser import HTMLParser
from pathlib import Path
from urllib.parse import unquote, urlsplit
import sys
import json
import re

repo=Path(__file__).resolve().parents[1]
root=repo/'docs'
class Page(HTMLParser):
 def __init__(self): super().__init__();self.links=[];self.ids=set();self.navs=[]
 def handle_starttag(self,tag,attrs):
  attrs=dict(attrs)
  if 'id' in attrs:self.ids.add(attrs['id'])
  if tag=='nav':self.navs.append(attrs.get('aria-label'))
  self.links.extend(value for key,value in attrs.items() if key in ('href','src'))
pages={}
for path in root.rglob('*.html'):
 page=Page();page.feed(path.read_text());pages[path.resolve()]=page
errors=[]
for path,page in pages.items():
 if 'Site navigation' not in page.navs:errors.append(f'{path}: missing global navigation')
 for link in page.links:
  url=urlsplit(link)
  if url.scheme in ('http','https','mailto','data'):continue
  target=(path.parent/unquote(url.path)).resolve() if url.path else path
  if not target.is_relative_to(root):errors.append(f'{path}: link escapes site: {link}');continue
  if not target.is_file():errors.append(f'{path}: missing target: {link}')
  elif url.fragment and target in pages and unquote(url.fragment) not in pages[target].ids:
   errors.append(f'{path}: missing anchor: {link}')
for name in ['index.html','getting-started.html','tutorials.html','benchmarks.html','results.html','methodology.html','libraries.html','validation.html','reproduce.html']:
 if (root/name).resolve() not in pages:errors.append(f'Missing required page: {name}')
chart=(root/'benchmarks.html').read_text()
payload=re.search(r'<script type="application/json" id="benchmark-data">(.*?)</script>',chart,re.S)
if not payload or len(json.loads(payload.group(1))['points'])!=286:errors.append('Expected 286 measured chart points')
if not (root/'benchmarks/index.html').exists():errors.append('Missing historical benchmark index')
for source in (root/'tutorials').glob('*.md'):
 if not source.with_suffix('.html').exists():errors.append(f'Missing generated tutorial: {source}')
if errors:print('\n'.join(errors));sys.exit(1)
print(f'Checked {len(pages)} pages: internal links, anchors, global navigation and chart points passed.')

# Additional profile pages can have fewer libraries/operations than the baseline.
for path in pages:
    text=path.read_text()
    payload=re.search(r'<script type="application/json" id="benchmark-data">(.*?)</script>',text,re.S)
    if not payload: continue
    dataset=json.loads(payload.group(1))
    expected={(lib['id'],size,op) for lib in dataset['libraries'] for size in dataset['sizes'] for op in dataset['operations']}
    actual={(point['library'],point['size'],point['operation']) for point in dataset['points']}
    if expected!=actual or len(actual)!=len(dataset['points']):
        raise SystemExit(f'Incomplete or duplicate chart profile: {path}')
    if any(point['mean']<=0 for point in dataset['points']):
        raise SystemExit(f'Nonpositive logarithmic chart value: {path}')
print('All chart profiles have complete, unique library/size/operation matrices.')

# Every registered dated archive must exist and preserve its saved measurements.
import hashlib
registry=json.loads((repo/'benchmark/results/runs.json').read_text())
for run in registry['runs']:
    source=repo/'benchmark/results'/run['source']
    dated=root/'benchmarks'/run['source']
    for name in ['index.html','results.html']:
        if not (dated/name).exists():raise SystemExit(f'Missing dated page: {dated/name}')
    for name,digest in run['evidence_sha256'].items():
        if hashlib.sha256((source/name).read_bytes()).hexdigest()!=digest:
            raise SystemExit(f'Historical evidence changed: {source/name}')
    charts=[]
    for path in dated.glob('*.html'):
        payload=re.search(r'<script type="application/json" id="benchmark-data">(.*?)</script>',path.read_text(),re.S)
        if payload:charts.extend(json.loads(payload.group(1))['points'])
    if len(charts)!=run['cases']:raise SystemExit(f'Wrong dated case count: {dated}')
# Detect stale links into moved repository archives without network requests.
for path,page in pages.items():
    for link in page.links:
        prefix='https://github.com/nmltd/nmdev-examples/blob/main/benchmark/results/'
        if link.startswith(prefix):
            target=repo/'benchmark/results'/unquote(urlsplit(link[len(prefix):]).path)
            if not target.exists():raise SystemExit(f'Missing Git archive link in {path}: {link}')
print(f"Verified {len(registry['runs'])} permanent dated archives and their original measurement digests.")
