#!/usr/bin/env python3
"""Summarize the separate rectangular multiplication track, preserving its shape parameters."""
import csv,json,sys
from pathlib import Path
root=Path(sys.argv[1]);rows=json.loads((root/'jmh.json').read_text())
libs=['NMDEV','EJML','OJALGO','JBLAS'];shapes=sorted({r['params']['shape'] for r in rows})
index={(r['params']['library'],r['params']['shape']):r for r in rows}
assert len(index)==len(rows)==len(libs)*len(shapes)
lines=['# Rectangular multiplication — '+root.name,'Shapes are m×k×n. All returned results are materialized, inputs are constructed outside timing, and every fork checks arithmetic twice and exact input preservation. This is a separate four-library track, not an eleven-library decomposition comparison.','Short diagnostic run on a shared VM. See the exact fork settings, affinity and thread requests in [environment.json](environment.json). Native jblas worker counts are not independently audited.','| Shape | NMDEV ms/op | EJML ms/op | OJALGO ms/op | JBLAS ms/op |','|---|---:|---:|---:|---:|']
versions=json.loads((root/'versions.json').read_text())
lines.insert(3,'**Versions:** '+', '.join(versions[lib] for lib in libs)+'. [Exact version labels](versions.json).')
with (root/'summary.csv').open('w',newline='') as out:
 writer=csv.writer(out);writer.writerow(['shape','library','mean_ms_per_op','jmh_99_9_error_ms','ratio_to_nmdev','allocated_bytes_per_op'])
 for shape in shapes:
  baseline=index['NMDEV',shape]['primaryMetric']['score'];cells=[shape]
  for lib in libs:
   row=index[lib,shape];metric=row['primaryMetric'];assert metric['scoreUnit']=='ms/op' and metric['score']>0
   assert len(metric['rawData'])==row['forks'] and all(len(f)==row['measurementIterations'] for f in metric['rawData'])
   allocation=row.get('secondaryMetrics',{}).get('gc.alloc.rate.norm',{}).get('score')
   writer.writerow([shape,lib,metric['score'],metric['scoreError'],metric['score']/baseline,allocation]);cells.append(f"{metric['score']:.6g} ± {metric['scoreError']:.2g}")
  lines.append('| '+' | '.join(cells)+' |')
lines+=['Errors are JMH 99.9% confidence half-widths. Wide or overlapping intervals do not support fine rankings. JVM allocation counts do not include all native allocations.','[Raw JMH](jmh.json) · [Log](jmh.log) · [Summary and allocation](summary.csv) · [Artifacts](artifacts.json) · [Exact source snapshot](harness-source.zip) · [Fairness audit](../2026-10-18-fairness-audit/README.md)']
text='\n\n'.join(lines)+'\n'
# Keep contiguous table rows so Markdown renders an actual table.
text=text.replace(' |\n\n|',' |\n|').replace('|\n\n|','|\n|')
(root/'README.md').write_text(text)
print('Summarized',len(rows),'rectangular cases')
