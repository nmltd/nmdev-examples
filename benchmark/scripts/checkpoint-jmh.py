#!/usr/bin/env python3
"""Run JMH cases sequentially, preserving full-precision JSON after every case.

A stopped case is rerun; successfully committed cases are never remeasured.
The command, Java sources and dependency hashes must match before resuming.
"""
import datetime
import hashlib
import fcntl
import itertools
import json
import math
import os
import re
import signal
from pathlib import Path
import subprocess
import sys


def atomic_json(path, value):
    temporary = path.with_suffix(path.suffix + '.tmp')
    with temporary.open('w') as stream:
        json.dump(value, stream, indent=2, allow_nan=True)
        stream.write('\n')
        stream.flush()
        os.fsync(stream.fileno())
    temporary.replace(path)
    directory_fd = os.open(path.parent, os.O_RDONLY | os.O_DIRECTORY)
    try:
        os.fsync(directory_fd)
    finally:
        os.close(directory_fd)


def valid_case(path, params, expected):
    try:
        envelope = json.loads(path.read_text())
        rows = envelope['results']
        if hashlib.sha256(json.dumps(rows, sort_keys=True).encode()).hexdigest() != envelope['sha256']:
            return False
        if len(rows) != 1 or rows[0]['params'] != params:
            return False
        row = rows[0]
        samples = row['primaryMetric']['rawData']
        return (all(row[key] == value for key, value in expected.items())
                and len(samples) == expected['forks']
                and all(len(fork) == expected['measurementIterations'] for fork in samples)
                and math.isfinite(row['primaryMetric']['score'])
                and all(math.isfinite(x) and x >= 0 for fork in samples for x in fork))
    except (OSError, ValueError, KeyError, TypeError):
        return False


def main():
    output = Path(sys.argv[1])
    lock = (output / '.checkpoint.lock').open('a')
    try:
        fcntl.flock(lock, fcntl.LOCK_EX | fcntl.LOCK_NB)
    except BlockingIOError:
        raise SystemExit('Another checkpoint runner is using this output directory')
    command = sys.argv[2:]
    if command[:1] == ['--']:
        command.pop(0)
    parameters = {}
    base = []
    i = 0
    while i < len(command):
        if command[i] == '-p':
            key, values = command[i + 1].split('=', 1)
            parameters[key] = values.split(',')
            i += 2
        elif command[i] == '-rff':
            i += 2
        else:
            base.append(command[i])
            i += 1
    benchmark = next(arg for arg in base if arg.startswith('dev.nm.benchmark.') and arg.endswith('.compute'))
    class_name = benchmark.rsplit('.', 1)[0].rsplit('.', 1)[-1]
    java_source = Path('benchmark/src/main/java/dev/nm/benchmark') / (class_name + '.java')
    source = java_source.read_text()
    for values, key in re.findall(r'@Param\(\{([^}]+)\}\)\s+public\s+\w+\s+(\w+)', source):
        parameters.setdefault(key, re.findall(r'"([^"\n]+)"', values))
    expected = {}
    for field, flag, annotation in [('forks', '-f', 'Fork'),
            ('warmupIterations', '-wi', 'Warmup'), ('measurementIterations', '-i', 'Measurement')]:
        match = re.search(r'@' + annotation + r'\((?:value=|iterations=)?(\d+)', source)
        value = int(match.group(1)) if match else None
        if flag in base:
            value = int(base[base.index(flag) + 1])
        if value is None or value < 1:
            raise ValueError('Checkpoint runs require positive explicit fork/iteration counts')
        expected[field] = value
    if not parameters:
        raise ValueError('Explicit benchmark parameters are required')
    checkpoint_dir = output / 'checkpoints'
    checkpoint_dir.mkdir(exist_ok=True)
    checks = json.loads((output / 'source-checksums.json').read_text())
    for name, expected_hash in checks.items():
        if name.endswith('.java') and hashlib.sha256(Path(name).read_bytes()).hexdigest() != expected_hash:
            raise ValueError('Java source changed since run preparation: ' + name)
    artifacts = json.loads((output / 'artifacts.json').read_text())
    if {p.name for p in Path('benchmark/target/lib').glob('*.jar')} != {item['file'] for item in artifacts}:
        raise ValueError('Dependency JAR set changed since run preparation')
    for item in artifacts:
        path = Path('benchmark/target/lib') / item['file']
        if hashlib.sha256(path.read_bytes()).hexdigest() != item['sha256']:
            raise ValueError('Dependency changed: ' + str(path))
    specification = {'command': command, 'parameters': parameters,
        'source_checksums': checks, 'expected': expected,
        'compiled_classes': {str(p): hashlib.sha256(p.read_bytes()).hexdigest()
            for p in sorted(Path('benchmark/target/classes').rglob('*')) if p.is_file()},
        'runtime': {'java': subprocess.run(['java', '-version'], capture_output=True, text=True, check=True).stderr,
            'affinity': sorted(os.sched_getaffinity(0)),
            'native_threads': {k: os.getenv(k) for k in ('OPENBLAS_NUM_THREADS', 'OMP_NUM_THREADS', 'MKL_NUM_THREADS', 'BLIS_NUM_THREADS', 'VECLIB_MAXIMUM_THREADS')}},
        'artifacts_sha256': hashlib.sha256((output / 'artifacts.json').read_bytes()).hexdigest()}
    manifest = checkpoint_dir / 'request.json'
    if manifest.exists() and json.loads(manifest.read_text()) != specification:
        raise ValueError('Checkpoint request differs; use a new output directory')
    atomic_json(manifest, specification)
    (output / 'completed-utc.txt').unlink(missing_ok=True)
    previous = json.loads((output / 'active.json').read_text()) if (output / 'active.json').exists() else None
    attempt = {'started_utc': datetime.datetime.now(datetime.timezone.utc).isoformat(),
        'runner_pid': os.getpid(), 'previous_status': previous,
        'runner_sha256': hashlib.sha256(Path(__file__).read_bytes()).hexdigest(),
        'resource_events': {name: (Path('/sys/fs/cgroup') / name).read_text()
            for name in ('memory.events', 'pids.events') if (Path('/sys/fs/cgroup') / name).exists()}}
    with (output / 'resume-history.jsonl').open('a') as history:
        history.write(json.dumps(attempt) + '\n')
        history.flush()
        os.fsync(history.fileno())
    keys = sorted(parameters)
    cases = [dict(zip(keys, values)) for values in itertools.product(*(parameters[k] for k in keys))]
    aggregate = []
    for index, params in enumerate(cases, 1):
        name = '__'.join(key + '-' + params[key] for key in keys)
        path = checkpoint_dir / (name + '.json')
        if valid_case(path, params, expected):
            print(f'CHECKPOINT {index}/{len(cases)} retained {params}', flush=True)
        else:
            temporary = checkpoint_dir / (name + '.pending.json')
            temporary.unlink(missing_ok=True)
            args = base + [arg for key in keys for arg in ('-p', key + '=' + params[key])]
            args += ['-rff', str(temporary)]
            print(f'CHECKPOINT {index}/{len(cases)} starting {params}', flush=True)
            with (checkpoint_dir / (name + '.log')).open('a') as log:
                active = {'case': params, 'runner_pid': os.getpid(), 'status': 'running'}
                child = subprocess.Popen(args, stdout=log, stderr=subprocess.STDOUT, start_new_session=True)
                def interrupted(signum, frame):
                    active.update(status='interrupted', signal=signal.Signals(signum).name)
                    atomic_json(output / 'active.json', active)
                    try:
                        os.killpg(child.pid, signum)
                    except ProcessLookupError:
                        pass
                    raise SystemExit(128 + signum)
                signal.signal(signal.SIGHUP, interrupted)
                signal.signal(signal.SIGTERM, interrupted)
                signal.signal(signal.SIGINT, interrupted)
                while True:
                    active.update(child_pid=child.pid, heartbeat_utc=datetime.datetime.now(datetime.timezone.utc).isoformat())
                    atomic_json(output / 'active.json', active)
                    try:
                        returncode = child.wait(timeout=15)
                        break
                    except subprocess.TimeoutExpired:
                        pass
                active.update(status='finished' if returncode == 0 else 'failed', returncode=returncode)
                atomic_json(output / 'active.json', active)
                if returncode:
                    raise subprocess.CalledProcessError(returncode, args)
                log.flush()
                os.fsync(log.fileno())
            results = json.loads(temporary.read_text())
            envelope = {'results': results, 'sha256': hashlib.sha256(json.dumps(results, sort_keys=True).encode()).hexdigest()}
            staging = checkpoint_dir / (name + '.commit.json')
            atomic_json(staging, envelope)
            if not valid_case(staging, params, expected):
                raise ValueError('Incomplete or invalid case: ' + name)
            staging.replace(path)
            directory_fd = os.open(checkpoint_dir, os.O_RDONLY | os.O_DIRECTORY)
            try:
                os.fsync(directory_fd)
            finally:
                os.close(directory_fd)
            temporary.unlink()
            print(f'CHECKPOINT {index}/{len(cases)} committed {params}', flush=True)
        aggregate.extend(json.loads(path.read_text())['results'])
        atomic_json(output / 'jmh.json', aggregate)
        atomic_json(output / 'progress.json', {'completed_cases': index, 'total_cases': len(cases),
            'last_case': params, 'updated_utc': datetime.datetime.now(datetime.timezone.utc).isoformat()})
    print(f'All {len(cases)} cases committed; full-precision aggregate saved to {output / "jmh.json"}', flush=True)


if __name__ == '__main__':
    main()
