# Checkpointed benchmark runs

`benchmark/scripts/run.sh` saves every completed case automatically. Run the **same command with the same output directory** to resume:

```bash
BENCH_PROFILE=large-missing BENCH_THREADS=4 BENCH_CPUSET=0-3 \
  bash benchmark/scripts/run.sh benchmark/results/my-large-4thread \
  -wi 2 -w 100ms -i 3 -r 200ms
```

A case is one library, operation and matrix size (or rectangular shape), including all its independent JVM forks and measurement iterations. The runner validates and atomically commits full-precision JMH results to `checkpoints/` after each case. It then updates `jmh.json` and `progress.json`. Completed checkpoints are retained unchanged on restart. An interrupted case is repeated from the beginning, including its warmups and correctness checks; partial measurements are never mixed into a completed case.

- `jmh.log`: overall progress, retained checkpoints and committed cases.
- `progress.json`: number of completed cases and total requested cases.
- `active.json`: current case, process IDs, heartbeat and termination status when observable. An abrupt host termination can leave a stale heartbeat.
- `checkpoints/*.log`: per-case JMH output, including both forks and pre-timing validation failures.
- `checkpoints/*.json`: checksummed envelopes containing the original JMH result rows.
- `checkpoints/request.json`: exact command and fingerprints used to reject incompatible restarts.
- `completed-utc.txt`: created only after the entire requested set succeeds.

Do not publish a partial `jmh.json` as a complete run. The existing report format is retained when all cases finish. The launcher prevents two processes from writing to the same output directory. To run a separate comparison with different parameters or dependencies, choose a new directory. Do not edit the launcher or benchmark sources during a run.

Resume checks cover requested parameters, iteration counts, thread configuration, CPU affinity, JVM version, Java source hashes, compiled classes and dependency JAR hashes. Corrupt or incomplete case checkpoints are repeated. Timing still reflects a shared host; these checks do not guarantee identical background load between resumed segments. Requested native thread counts are not independently audited.

A checkpoint protects completed results; it does not keep a process alive after its execution environment ends. Use a persistent machine/job runner for unattended runs. For an intentional interruption, send SIGINT or SIGTERM to the checkpoint runner: it records the signal and forwards it to the active JMH process group. SIGKILL or host cleanup cannot be recorded by the killed process.

## Earlier interrupted runs

The old launcher left an empty `jmh.json` until JMH finished the entire suite. Rounded console lines are not substitutes for full-precision JMH raw data. Archives without checkpoints are preserved and rejected for in-place resume. Their completed cases must be remeasured in a new checkpointed run. Complete historical archives remain valid and do not need to be rerun.

## Verify restart behavior

The license-free regression tests interrupt a process during its second case, resume it, verify that the first checkpoint's bytes and modification time are unchanged, and check rejection of changed settings/sources and recovery from corruption:

```bash
python3 -m unittest discover -s benchmark/tests -v
```
