#!/usr/bin/env bash
set -euo pipefail
repo_root="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$repo_root"
output="${1:?Supply a new output directory}"
shift
if [[ -e "$output/jmh.json" || -e "$output/jmh.log" ]]; then
  echo 'Choose a new output directory to preserve earlier results' >&2; exit 1
fi
[[ -f nm.lic ]] || { echo 'Place your valid NM Dev license at the repository root as nm.lic' >&2; exit 1; }
threads="${BENCH_THREADS:-1}"
[[ "$threads" =~ ^[1-9][0-9]*$ ]] || { echo 'BENCH_THREADS must be a positive integer' >&2; exit 1; }
profile="${BENCH_PROFILE:-baseline}"
case "$profile" in
  baseline) benchmark='dev.nm.benchmark.MatrixBenchmark.compute'; parameters=(-p size=64,256) ;;
  large) benchmark='dev.nm.benchmark.MatrixBenchmark.compute'; parameters=(-p size=512,1024 -p library=NMDEV,EJML,OJALGO,JBLAS -p operation=multiplication,lu,cholesky,qr,solve) ;;
  rectangular) benchmark='dev.nm.benchmark.RectangularBenchmark.compute'; parameters=() ;;
  *) echo 'BENCH_PROFILE must be baseline, large or rectangular' >&2; exit 1 ;;
esac
NMDEV_JAR="$repo_root/benchmark/target/lib/nmdev-1.2.6.jar" bash benchmark/scripts/build-direct.sh
mkdir -p "$output"
classpath='benchmark/target/classes:benchmark/target/lib/*'
export OPENBLAS_NUM_THREADS="$threads" OMP_NUM_THREADS="$threads" MKL_NUM_THREADS="$threads" BLIS_NUM_THREADS="$threads" VECLIB_MAXIMUM_THREADS="$threads"
backend_flags=(
  -Dcom.github.fommil.netlib.BLAS=com.github.fommil.netlib.F2jBLAS
  -Dcom.github.fommil.netlib.LAPACK=com.github.fommil.netlib.F2jLAPACK
  -Dcom.github.fommil.netlib.ARPACK=com.github.fommil.netlib.F2jARPACK
)
fork_flags="-Xms1g -Xmx1g -XX:ActiveProcessorCount=$threads -Dbenchmark.threads=$threads ${backend_flags[*]}"
runner=()
if [[ -n "${BENCH_CPUSET:-}" ]]; then
  command -v taskset >/dev/null || { echo 'taskset is required for BENCH_CPUSET' >&2; exit 1; }
  runner=(taskset -c "$BENCH_CPUSET")
fi
{
  date -u; uname -a; java -version
  if command -v lscpu >/dev/null; then lscpu; fi
  if [[ -r /sys/fs/cgroup/cpu.max ]]; then cat /sys/fs/cgroup/cpu.max; fi
  if [[ -r /sys/fs/cgroup/memory.max ]]; then cat /sys/fs/cgroup/memory.max; fi
} > "$output/environment.txt" 2>&1
sha256sum benchmark/target/lib/*.jar > "$output/artifact-checksums.txt"
python3 benchmark/scripts/record-environment.py "$output" "$threads" "$profile" "$benchmark" "${parameters[@]}" "$@"
"${runner[@]}" java -cp "$classpath" dev.nm.benchmark.OriginValidationMain > "$output/class-origins.csv"
"${runner[@]}" java -Xms1g -Xmx1g -XX:ActiveProcessorCount="$threads" -Dbenchmark.threads="$threads" "${backend_flags[@]}" -cp "$classpath" dev.nm.benchmark.FairnessValidationMain > "$output/fairness-check.txt" 2> "$output/fairness-check.log"
"${runner[@]}" java -Xms1g -Xmx1g -XX:ActiveProcessorCount="$threads" -Dbenchmark.threads="$threads" "${backend_flags[@]}" -cp "$classpath" dev.nm.benchmark.CorrectnessMain > "$output/correctness.csv" 2> "$output/correctness.log"
"${runner[@]}" java -Xms1g -Xmx1g -XX:ActiveProcessorCount="$threads" -Dbenchmark.threads="$threads" "${backend_flags[@]}" -cp "$classpath" dev.nm.benchmark.PivotValidationMain > "$output/pivot-correctness.csv" 2> "$output/pivot-correctness.log"
echo "Running JMH; follow progress in $output/jmh.log"
"${runner[@]}" java -cp "$classpath" org.openjdk.jmh.Main "$benchmark" "${parameters[@]}" -jvmArgsAppend "$fork_flags" -prof gc -foe true -rf json -rff "$output/jmh.json" "$@" > "$output/jmh.log" 2>&1
date -u +%FT%TZ > "$output/completed-utc.txt"
echo "Raw results written to $output"
