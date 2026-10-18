#!/usr/bin/env bash
set -euo pipefail

# Reproducible alternative when Maven cannot reach repositories in the runtime.
# Supply the published combined binary yourself; no library or license is bundled.
repo_root="$(cd "$(dirname "$0")/../.." && pwd)"
benchmark_root="$repo_root/benchmark"
: "${NMDEV_JAR:?Set NMDEV_JAR to the absolute path of your NM Dev combined JAR}"
if [[ "$NMDEV_JAR" != /* || ! -f "$NMDEV_JAR" ]]; then
  echo 'NMDEV_JAR must be an existing absolute path' >&2
  exit 1
fi
mkdir -p "$benchmark_root/target/lib" "$benchmark_root/target/classes" "$benchmark_root/target/generated-sources"
while IFS=$'\t' read -r filename sha256 url; do
  [[ -z "$filename" || "$filename" == \#* ]] && continue
  destination="$benchmark_root/target/lib/$filename"
  if [[ ! -f "$destination" ]]; then
    curl --fail --location --silent --show-error --retry 2 "$url" --output "$destination"
  fi
  printf '%s  %s\n' "$sha256" "$destination" | sha256sum --check --status
done < "$benchmark_root/dependencies.lock.tsv"
cp "$NMDEV_JAR" "$benchmark_root/target/lib/nmdev-dist-1.2.6-SNAPSHOT.jar"
classpath=""
for dependency in "$benchmark_root"/target/lib/*.jar; do
  classpath="${classpath:+$classpath:}$dependency"
done
mapfile -t sources < <(find "$benchmark_root/src/main/java" -name '*.java' -print | sort)
# Calling the compiler module also works on JDK installations without a javac launcher.
java com.sun.tools.javac.Main --release 11 \
  -cp "$classpath" -processorpath "$classpath" \
  -d "$benchmark_root/target/classes" -s "$benchmark_root/target/generated-sources" \
  "${sources[@]}"
echo 'Benchmark compiled. Run bash benchmark/scripts/run.sh OUTPUT_DIRECTORY'
