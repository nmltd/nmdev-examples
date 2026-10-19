"""Exercise interruption/resume without requiring licenses or numerical JARs."""
import json
import os
from pathlib import Path
import signal
import subprocess
import sys
import tempfile
import time
import unittest

RUNNER = Path(__file__).resolve().parents[1] / 'scripts/checkpoint-jmh.py'


class CheckpointTest(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.addCleanup(self.temp.cleanup)
        self.root = Path(self.temp.name)
        source = self.root / 'benchmark/src/main/java/dev/nm/benchmark/MatrixBenchmark.java'
        source.parent.mkdir(parents=True)
        source.write_text('''@Fork(value=2) @Warmup(iterations=2) @Measurement(iterations=3)
@Param({"EJML"}) public String library;
@Param({"1","2"}) public int size;
@Param({"multiplication"}) public String operation;''')
        self.output = self.root / 'results'
        self.output.mkdir()
        import hashlib
        (self.output / 'source-checksums.json').write_text(json.dumps({str(source.relative_to(self.root)): hashlib.sha256(source.read_bytes()).hexdigest()}))
        (self.output / 'artifacts.json').write_text('[]')
        fake = self.root / 'fake_jmh.py'
        fake.write_text('''import json,sys,time
from pathlib import Path
args=sys.argv[1:];p={}
for i,arg in enumerate(args):
 if arg=='-p':
  k,v=args[i+1].split('=',1);p[k]=v
with open('calls.log','a') as f:f.write(p['size']+'\\n')
if p['size']=='2' and Path('pause').exists():time.sleep(30)
row={'params':p,'forks':2,'warmupIterations':2,'measurementIterations':3,
'primaryMetric':{'score':1.23456789012345,'rawData':[[1,2,3],[4,5,6]]}}
Path(args[args.index('-rff')+1]).write_text(json.dumps([row]))
''')
        self.command = [sys.executable, str(RUNNER), str(self.output), '--', sys.executable,
            str(fake), 'dev.nm.benchmark.MatrixBenchmark.compute', '-rf', 'json']

    def run_runner(self, extra=(), check=True):
        return subprocess.run(self.command + list(extra), cwd=self.root, capture_output=True, text=True, check=check)

    def test_interrupt_and_resume(self):
        self.interrupt_and_resume(signal.SIGTERM)

    def test_hangup_and_resume(self):
        self.interrupt_and_resume(signal.SIGHUP)

    def interrupt_and_resume(self, stop_signal):
        (self.root / 'pause').touch()
        process = subprocess.Popen(self.command, cwd=self.root, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
        try:
            deadline = time.monotonic() + 10
            while time.monotonic() < deadline:
                calls = (self.root / 'calls.log').read_text().splitlines() if (self.root / 'calls.log').exists() else []
                if calls == ['1', '2']:
                    break
                time.sleep(.02)
            self.assertEqual(calls, ['1', '2'])
            checkpoint = next((self.output / 'checkpoints').glob('*size-1.json'))
            original = checkpoint.read_bytes()
            stamp = checkpoint.stat().st_mtime_ns
            process.send_signal(stop_signal)
            process.wait(timeout=5)
            self.assertEqual(json.loads((self.output / 'active.json').read_text())['status'], 'interrupted')
            (self.root / 'pause').unlink()
            result = self.run_runner()
            self.assertIn('retained', result.stdout)
            self.assertEqual(checkpoint.read_bytes(), original)
            self.assertEqual(checkpoint.stat().st_mtime_ns, stamp)
            self.assertEqual((self.root / 'calls.log').read_text().splitlines(), ['1', '2', '2'])
            rows = json.loads((self.output / 'jmh.json').read_text())
            self.assertEqual(len(rows), 2)
            self.assertEqual(rows[0]['primaryMetric']['score'], 1.23456789012345)
        finally:
            if process.poll() is None:
                process.terminate()
                process.wait(timeout=5)

    def test_changed_request_rejected(self):
        self.run_runner()
        calls = (self.root / 'calls.log').read_bytes()
        result = self.run_runner(['-i', '4'], check=False)
        self.assertNotEqual(result.returncode, 0)
        self.assertIn('Checkpoint request differs', result.stderr)
        self.assertEqual((self.root / 'calls.log').read_bytes(), calls)

    def test_corrupt_checkpoint_remeasured(self):
        self.run_runner()
        checkpoint = next((self.output / 'checkpoints').glob('*size-1.json'))
        envelope = json.loads(checkpoint.read_text())
        envelope['results'][0]['primaryMetric']['score'] = 999
        checkpoint.write_text(json.dumps(envelope))
        self.run_runner()
        self.assertEqual((self.root / 'calls.log').read_text().splitlines(), ['1', '2', '1'])

    def test_changed_source_rejected(self):
        self.run_runner()
        source = next((self.root / 'benchmark/src').rglob('*.java'))
        source.write_text(source.read_text() + '\n// changed\n')
        result = self.run_runner(check=False)
        self.assertNotEqual(result.returncode, 0)
        self.assertIn('Java source changed', result.stderr)

    def test_changed_dependency_set_rejected(self):
        self.run_runner()
        lib = self.root / 'benchmark/target/lib'
        lib.mkdir(parents=True)
        (lib / 'unexpected.jar').write_bytes(b'extra classpath entry')
        result = self.run_runner(check=False)
        self.assertNotEqual(result.returncode, 0)
        self.assertIn('Dependency JAR set changed', result.stderr)

    def test_stale_completion_marker_removed(self):
        self.run_runner()
        marker = self.output / 'completed-utc.txt'
        marker.write_text('stale')
        self.run_runner()
        self.assertFalse(marker.exists())
        history = [json.loads(line) for line in (self.output / 'resume-history.jsonl').read_text().splitlines()]
        self.assertEqual(len(history), 2)
        self.assertEqual(history[-1]['previous_status']['status'], 'finished')

    def test_concurrent_runner_rejected(self):
        (self.root / 'pause').touch()
        process = subprocess.Popen(self.command, cwd=self.root, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
        try:
            deadline = time.monotonic() + 10
            while not (self.output / 'active.json').exists() and time.monotonic() < deadline:
                time.sleep(.02)
            result = self.run_runner(check=False)
            self.assertNotEqual(result.returncode, 0)
            self.assertIn('Another checkpoint runner', result.stderr)
        finally:
            process.terminate()
            process.wait(timeout=5)


if __name__ == '__main__':
    unittest.main()
