#!/usr/bin/env bash
set -euo pipefail
repo_root="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$repo_root"
output="${1:?Supply a new output directory, for example benchmark/results/my-run}"
shift
if [[ -e "$output/jmh.json" || -e "$output/jmh.log" ]]; then
  echo 'Choose a new output directory to preserve earlier results' >&2
  exit 1
fi
if [[ ! -f nm.lic ]]; then
  echo 'Place your valid NM Dev license at the repository root as nm.lic' >&2
  exit 1
fi
mkdir -p "$output"
classpath='benchmark/target/classes:benchmark/target/lib/*'
{
  date -u
  uname -a
  java -version
  if command -v lscpu >/dev/null; then lscpu; fi
  if [[ -r /sys/fs/cgroup/cpu.max ]]; then cat /sys/fs/cgroup/cpu.max; fi
  if [[ -r /sys/fs/cgroup/memory.max ]]; then cat /sys/fs/cgroup/memory.max; fi
} > "$output/environment.txt" 2>&1
sha256sum benchmark/target/lib/*.jar > "$output/artifact-checksums.txt"
java -Xms1g -Xmx1g -XX:ActiveProcessorCount=1 -cp "$classpath" \
  dev.nm.benchmark.CorrectnessMain > "$output/correctness.csv"
echo "Running JMH; follow progress in $output/jmh.log"
java -cp "$classpath" org.openjdk.jmh.Main \
  dev.nm.benchmark.MatrixBenchmark.compute -foe true -rf json -rff "$output/jmh.json" \
  "$@" > "$output/jmh.log" 2>&1
echo "Raw results written to $output"
