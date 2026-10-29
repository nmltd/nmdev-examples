#!/usr/bin/env python3
"""Validate portable-launcher evidence without rerunning benchmark measurements."""
import csv
import hashlib
import json
import math
import statistics
import sys
from collections import Counter
from pathlib import Path


def validate(root):
    load=lambda name: json.loads((root/name).read_text(encoding='utf-8-sig'))
    integrity=root/'archive-integrity.json'
    if integrity.exists():
        for name,sha in load('archive-integrity.json')['extracted_files_sha256'].items():
            assert hashlib.sha256((root/name).read_bytes()).hexdigest()==sha,name
    request=load('request.json'); rows=load('jmh.json'); expected=request['cases']
    key=lambda c: (c['profile'],c['threads'],c['library'],c['operation'],c['dimension'])
    rowkey=lambda r: (r['profile'],r['requested_threads'],r['params']['library'],r['params'].get('operation','multiplication'),r['params'].get('size',r['params'].get('shape')))
    assert len(set(map(key,expected)))==len(expected)==len(rows)
    assert Counter(map(key,expected))==Counter(map(rowkey,rows))
    assert load('suite-status.json')['complete'] and not load('failures.json')
    progress=load('progress.json')
    assert progress['complete'] and progress['completed_cases']==progress['total_cases']==len(rows)
    assert progress['failed_cases']==0 and not load('suite-status.json')['preflight_failures']
    assert load('active.json')['status']=='finished' and (root/'completed-utc.txt').exists()
    machine=load('machine.json')
    for k,v in request['machine'].items(): assert machine[k]==v,(k,v,machine[k])
    preflights=list((root/'preflight').glob('*.json'))
    assert len(preflights)==(6 if "COMMONS_BLOCK" in load("versions.json") else 5) and all(json.loads(p.read_text())['exit_code']==0 for p in preflights)
    measurements=0; missing_forks=[]; verified_forks=0
    for r in rows:
        profile,threads,library,operation,dimension=rowkey(r)
        case=root/'cases'/f'{profile}__{threads}thread__{library}__{operation}__{dimension}'
        saved=json.loads((case/'result.json').read_text())
        assert saved['primaryMetric']==r['primaryMetric'] and saved['params']==r['params']
        metric=r['primaryMetric']; raw=metric['rawData']
        assert r['threads']==1 and r['forks']==request['forks']==2
        assert len(raw)==2 and all(len(f)==request['measurement_iterations']==3 for f in raw)
        assert metric['scoreUnit']=='ms/op'
        assert all(math.isfinite(x) and x>0 for f in raw for x in f)
        assert math.isclose(metric['score'],statistics.mean(x for f in raw for x in f),rel_tol=1e-12)
        assert 'gc.alloc.rate.norm' in r['secondaryMetrics']
        assert f'-XX:ActiveProcessorCount={threads}' in r['jvmArgs']
        assert f'-Dbenchmark.threads={threads}' in r['jvmArgs']
        assert r['jdkVersion']==machine['java.version'] and r['vmVersion']==machine['java.vm.version']
        for i in (1,2):
            path=case/f'fork-{i}.json'; checkpoint=json.loads((case/f'fork-{i}-checkpoint.json').read_text())
            if not path.exists():
                missing_forks.append(str(path.relative_to(root))); continue
            verified_forks+=1
            assert hashlib.sha256(path.read_bytes()).hexdigest()==checkpoint['json_sha256']
            fork=json.loads(path.read_text()); fork=fork[0] if isinstance(fork,list) else fork
            assert fork['params']==r['params'] and fork['primaryMetric']['rawData']==[raw[i-1]]
        measurements+=sum(map(len,raw))
    summary=list(csv.DictReader((root/'summary.csv').open()))
    assert len(summary)==len(rows)
    for s,r in zip(summary,rows):
        assert (s['profile'],int(s['requested_threads']),s['library'],s['operation'],s['size_or_shape'])==rowkey(r)
        assert math.isclose(float(s['mean_ms_per_op']),r['primaryMetric']['score'],rel_tol=1e-14)
    assert load('artifacts.json')==request['artifacts']
    proof=load('binary-provenance.json')
    assert request['artifacts'][next(n for n in request['artifacts'] if n.startswith('nmdev-') and n.endswith('.jar'))]==proof['nmdev_sha256']
    report={'cases':len(rows),'libraries':len(load('versions.json')),'fork_json_checksums':verified_forks,'missing_fork_json':missing_forks,'measurement_iteration_means':measurements,'preflights_passed':len(preflights),'profiles':{f'{p}/{t}':n for (p,t),n in Counter((r['profile'],r['requested_threads']) for r in rows).items()},'machine':machine,'limitations':['Downloaded binaries and binary fork checkpoints are intentionally absent; their recorded hashes cannot be recomputed from this archive.','Requested native worker counts were not independently audited.','Historical logs are retained. Missing individual fork JSON files, if any, are explicitly listed; combined samples are validated separately.']}
    print(json.dumps(report,indent=2))
    return report

if __name__=='__main__': validate(Path(sys.argv[1]))
