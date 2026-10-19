# Run the complete benchmark unattended

Requires a **full JDK 17 or newer**, Maven to build, and Internet access on the first run. Windows, Linux and macOS use the same Java entry point; no WSL, Python, shell scripts, GitHub token, manual dependency installation or profile selection is required.

From a downloaded/cloned `nmdev-examples` project:

```text
mvn -s benchmark/settings.xml -gs benchmark/settings.xml -f benchmark/pom.xml package
java -jar benchmark/target/nmdev-benchmark-1.1.0.jar
```

The public application entry point is `dev.nm.benchmark.launcher.BenchmarkMain`. An IDE can invoke it from the packaged JAR. The JAR embeds the benchmark sources and build configuration and can be copied to another computer with a full JDK 17+.

With no arguments, the application automatically:

1. Downloads checksum-verified numerical dependencies and the repository's public trial license. It uses the validated NM Dev 1.2.6 public release and the timestamp-pinned Commons Math 4 snapshot from the lockfile, keeping version labels explicit. The public NM Dev binary hash differs from the older archived benchmark binary; local measurements must be treated as a separate experiment.
2. Builds Parallel Colt 0.11.4 from a checksum-verified archive of its exact upstream commit, then compiles the bundled JMH harness. The locally built binary's actual hash and compiler version are recorded; compiler-dependent binary hashes can differ between machines.
3. Collects machine information and runs correctness, input-preservation, pivot and backend preflight checks.
4. Runs all **660 cases** serially: 286 baseline cases, 330 large-square cases, and 44 rectangular cases. All eleven libraries are attempted. The one- and four-thread profiles are scheduled automatically; they never run concurrently.
5. Saves each independent JVM fork immediately, merges completed cases using JMH's own statistics, updates the CSV and JSON reports, and creates a ZIP beside the result directory.

Every case has two independently warmed JVM forks, two warmup iterations and three measurement iterations per fork. Warmup targets are 100 ms and measurement targets 200 ms; expensive operations still complete in full. Full-size correctness checks run before timing. The complete suite can take many hours, especially at 2000×2000. Keep the computer awake and otherwise idle.

## Resume after interruption

Run the **same JAR and command** again. The default result directory is `nmdev-benchmark-output` in your current working directory. Completed forks are verified and retained, including a first fork whose second fork was interrupted. Only the unfinished/corrupt fork is repeated; warmups and correctness checks are performed again for that fork. Keep the entire local output directory, including `.runtime/` and binary fork checkpoints, for resume.

The launcher rejects incompatible resumes after a change in its JAR, dependency hashes, configuration or recorded machine identity. Use a different output directory for a different experiment:

```text
java -jar benchmark/target/nmdev-benchmark-1.1.0.jar --output laptop-results
```

Do not rebuild the application JAR between resumes. The same output directory is locked against concurrent runners. Logs and JSON heartbeats identify the active case. The launcher retries each failed fork twice without prompting and continues to other cases if a library is unsupported or fails. Failures are explicitly recorded and excluded from timing results. It exits with an error and packages partial evidence when failures remain; re-running retries failed/unfinished forks.

The trial license is downloaded from the public example repository, never from a private local file. If your public trial has expired, use a valid NM Dev license through the optional JVM property `-Dnmdev.license=/path/to/nm.lic`. License validity and platform-native dependencies cannot be bypassed by the launcher. jblas support depends on the native binaries available for the host architecture; an unsupported backend is recorded as a failure rather than silently replaced with another implementation.

## Output ZIP

Send `nmdev-benchmark-output-results.zip` (or `laptop-results-results.zip`) for validation and publication. The ZIP is refreshed after completed forks/cases and at orderly termination. A hard kill or power loss can prevent the last ZIP refresh; restart the application to reconstruct it from saved checkpoints.

Included:

- `machine.json` and `latest-machine.json`: CPU model and processor count, OS/architecture, physical memory/swap where detectable, JVM version, heap limit, and container limits where available. Unavailable information is labelled. No hostname, username or complete environment-variable dump is collected.
- `request.json`: full case plan, exact binary hashes, JVM/thread settings, machine identity and application hash.
- `jmh.json`, `summary.csv`, per-fork JSON, case results and logs.
- Preflight checks, source archive, actual Parallel Colt build provenance and dependency versions.
- `progress.json`, `suite-status.json`, `failures.json`, timestamps and interruption evidence when available.

Excluded: licenses, downloaded dependency JARs, private runtime cache, Maven settings/credentials, and binary Java serialization checkpoints. The shareable ZIP is evidence for publication, not a standalone resume cache. A run is complete only when its status says complete and `completed-utc.txt` exists. Measurements from different machines must be labelled and compared separately.

## Quick verification (not publishable measurements)

```text
java -jar benchmark/target/nmdev-benchmark-1.1.0.jar --smoke --output smoke-results
```

Smoke mode exercises every library across all profile types using tiny matrices and 10 ms iteration targets. Its metadata explicitly says `SMOKE-NOT-FOR-PUBLICATION`. Internal test options `--libraries NMDEV,EJML` and `--max-cases 2` allow narrow recovery checks. Ordinary users need no options.

## Legacy repositories in local Maven settings

Both project POMs contain no legacy NM repository URLs. If Maven nevertheless prints
`nm-public` or `nm-private`, inspect your user settings (`~/.m2/settings.xml`) and
Maven installation settings (`conf/settings.xml`) for active repository profiles or
mirrors. The command above supplies this project's empty settings file as both user
and global settings, bypassing those local profiles without changing them. It also
bypasses any custom proxy, credentials, mirrors or local-repository settings; networks
that require a proxy should use their own reviewed settings instead.
