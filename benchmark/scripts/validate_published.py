"""Check public measurement data against the validation summary from the original ZIP."""
import csv
import json
import math
import statistics
import sys
from collections import Counter
from pathlib import Path


def validate(source):
    load=lambda n:json.loads((source/n).read_text(encoding='utf-8-sig'))
    rows=load('jmh.json');report=load('validation-report.json');manifest=load('publication-format.json')
    assert len(rows)==manifest['cases']==report['cases']==660
    keys=[(r['profile'],r['requested_threads'],r['params']['library'],r['params'].get('operation','multiplication'),r['params'].get('size',r['params'].get('shape'))) for r in rows]
    assert len(set(keys))==len(keys)
    assert {f'{p}/{t}':n for (p,t),n in Counter((r['profile'],r['requested_threads']) for r in rows).items()}==manifest['profiles']==report['profiles']
    assert manifest['machine']==report['machine']
    summary=list(csv.DictReader((source/'summary.csv').open()))
    assert len(summary)==len(rows)
    for r,s,key in zip(rows,summary,keys):
        m=r['primaryMetric'];raw=m['rawData']
        assert len(raw)==r['forks']==2 and all(len(f)==r['measurementIterations']==3 for f in raw)
        assert all(math.isfinite(x) and x>0 for f in raw for x in f)
        assert math.isclose(m['score'],statistics.mean(x for f in raw for x in f),rel_tol=1e-12)
        assert m['scoreUnit']=='ms/op' and math.isfinite(m['scoreError'])
        assert (s['profile'],int(s['requested_threads']),s['library'],s['operation'],s['size_or_shape'])==key
        assert float(s['mean_ms_per_op'])==m['score']
        assert not {'jvm','jvmArgs'}.intersection(r)
    assert sum(len(f) for r in rows for f in r['primaryMetric']['rawData'])==report['measurement_iteration_means']==3960
    return report

if __name__=='__main__':
    result=validate(Path(sys.argv[1]));print(f"Validated {result['cases']} public cases and {result['measurement_iteration_means']} saved measurement means")
