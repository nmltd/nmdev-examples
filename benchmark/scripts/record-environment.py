#!/usr/bin/env python3
"""Record the exact harness, artifact hashes and execution request for an isolated run."""
import datetime, hashlib, json, os, platform, subprocess, sys, zipfile
from pathlib import Path
root=Path(sys.argv[1]);threads=int(sys.argv[2])
def read(path):
    p=Path(path);return p.read_text().strip() if p.exists() else 'unavailable'
now=datetime.datetime.now(datetime.timezone.utc).isoformat()
cpu=next((line.split(':',1)[1].strip() for line in read('/proc/cpuinfo').splitlines() if line.startswith('model name')),'unrecorded')
env={'fairness_revision':2,'recorded_utc':now,'os':platform.platform(),'cpu_model':cpu,'visible_cpus':os.cpu_count(),'cpu_quota':read('/sys/fs/cgroup/cpu.max'),'memory_limit_bytes':read('/sys/fs/cgroup/memory.max'),'requested_compute_threads':threads,'profile':sys.argv[3],'benchmark':sys.argv[4],'jmh_parameters_and_overrides':sys.argv[5:],'requested_affinity':os.getenv('BENCH_CPUSET'),'launcher_affinity':sorted(os.sched_getaffinity(0)) if hasattr(os,'sched_getaffinity') else None,'native_thread_counts_independently_audited':False,'environment':'Shared VM/container; CPU affinity is not exclusive CPU reservation','requested_native_thread_environment':{k:os.getenv(k) for k in ('OPENBLAS_NUM_THREADS','OMP_NUM_THREADS','MKL_NUM_THREADS','BLIS_NUM_THREADS','VECLIB_MAXIMUM_THREADS')},'jvm':subprocess.run(['java','-version'],capture_output=True,text=True,check=True).stderr}
(root/'environment.json').write_text(json.dumps(env,indent=2)+'\n')
(root/'started-utc.txt').write_text(now+'\n')
artifacts=[{'file':p.name,'sha256':hashlib.sha256(p.read_bytes()).hexdigest(),'bytes':p.stat().st_size} for p in sorted(Path('benchmark/target/lib').glob('*.jar'))]
(root/'artifacts.json').write_text(json.dumps(artifacts,indent=2)+'\n')
paths=list(Path('benchmark/src/main/java').rglob('*.java'))+list(Path('benchmark/scripts').glob('*'))+[Path('benchmark/dependencies.lock.tsv'),Path('benchmark/versions.json')]
(root/'source-checksums.json').write_text(json.dumps({str(p):hashlib.sha256(p.read_bytes()).hexdigest() for p in sorted(paths) if p.is_file()},indent=2)+'\n')

with zipfile.ZipFile(root/'harness-source.zip','w',zipfile.ZIP_DEFLATED) as archive:
    for p in sorted(paths):
        if p.is_file(): archive.write(p,str(p))

for name in ['versions.json']:
    src=Path('benchmark')/name
    if src.exists(): (root/name).write_bytes(src.read_bytes())
proof=Path('benchmark/dependency-audit/2026-10-18/nmdev-validated.json')
if proof.exists(): (root/'validated-nmdev-artifact.json').write_bytes(proof.read_bytes())
