#!/usr/bin/env python3
"""Check result completeness, raw samples, profiler evidence and artifact consistency."""
import csv,hashlib,json,sys,zipfile
from pathlib import Path
root=Path(sys.argv[1]);rows=json.loads((root/'jmh.json').read_text());env=json.loads((root/'environment.json').read_text())
expected=int(sys.argv[2]);assert len(rows)==expected
keys=[]
for r in rows:
 keys.append((r['benchmark'],tuple(sorted(r['params'].items()))))
 assert r['primaryMetric']['scoreUnit']=='ms/op' and r['primaryMetric']['score']>0
 assert len(r['primaryMetric']['rawData'])==r['forks']
 assert all(len(f)==r['measurementIterations'] for f in r['primaryMetric']['rawData'])
 assert 'gc.alloc.rate.norm' in r['secondaryMetrics']
 assert '-XX:ActiveProcessorCount='+str(env['requested_compute_threads']) in r['jvmArgs']
 assert '-Dbenchmark.threads='+str(env['requested_compute_threads']) in r['jvmArgs']
assert len(set(keys))==expected
for name,count in [('correctness.csv',429),('pivot-correctness.csv',66)]:
 checks=list(csv.DictReader((root/name).open()));assert len(checks)==count and all(c['status']=='PASS' for c in checks)
with zipfile.ZipFile(root/'harness-source.zip') as archive:
 for name,sha in json.loads((root/'source-checksums.json').read_text()).items():assert hashlib.sha256(archive.read(name)).hexdigest()==sha
jars=json.loads((root/'artifacts.json').read_text());nm=next(j for j in jars if j['file'].startswith('nmdev'))
proof=root/'validated-nmdev-artifact.json'
assert nm['sha256']==(json.loads(proof.read_text())['sha256'] if proof.exists() else 'a3e2b2492e79530b8e12920b48f48bfc295786b138aa26300a16b53bafe1386c')
assert (root/'completed-utc.txt').exists()
print(root.name,':',expected,'cases; input/correctness guards, exact source snapshot, artifact hash, thread flags and allocation metrics verified')
