# Reproduce the benchmark


Requires **JDK 11+**; the measured runs use JDK 17. Configure [GitHub Packages credentials](https://github.com/nmltd/nmdev-examples/blob/main/docs/Getting-Started.md) and place your valid external `nm.lic` in the repository root. No binary or license is committed.

```bash
mvn -f benchmark/pom.xml clean package
bash benchmark/scripts/run.sh benchmark/results/my-run
```

Alternatively use an existing published combined NM Dev JAR and the checked dependency lockfile:

```bash
export NMDEV_JAR=/absolute/path/to/nmdev-dist-1.2.6-SNAPSHOT.jar
bash benchmark/scripts/build-direct.sh
bash benchmark/scripts/run.sh benchmark/results/my-run
```

The direct build is the validated route used in this environment. It verifies third-party checksums and invokes the JMH annotation processor. The Maven project is independent of the Java 8 tutorial application. Runtime dependencies are copied only into ignored `target/` directories.

Allow roughly 35–45 minutes for the complete expanded run on similar hardware. Follow progress in your output directory's `jmh.log`. Pass optional JMH arguments after the output-directory argument and record any changed settings.

## Checks and report generation

The run stops on failed correctness checks. See [Validation](validation.html) for the checks and evidence.

```bash
python3 benchmark/scripts/report.py benchmark/results/my-run
```

Python only summarizes JMH measurements. Matplotlib renders the SVGs and self-contained HTML with hover, focus and tap tooltips. Download/open `charts.html` in a browser; GitHub inline image previews do not expose point tooltips. Raw timing JSON, logs, CSV, environment, backends, checksums and correctness evidence accompany each published run.

Results on a shared VM are a workload-specific baseline. Native BLAS and pure-Java paths are explicitly labelled; performance claims should reflect these differences and the recorded uncertainty.

## Publish updated results

Install the report and site dependencies, then generate the report and website from the repository root:

```bash
python3 -m pip install -r benchmark/scripts/requirements.txt
python3 benchmark/scripts/report.py benchmark/results/my-run
python3 benchmark/scripts/publish-pages.py benchmark/results/my-run
```

Commit the generated `docs/*.html` and `docs/.nojekyll` together with the result archive. The Documentation workflow builds from Markdown, checks internal links, uploads the generated site and publishes it through GitHub Pages on pushes to `main`. Pull requests build and check without deployment. Publishing the site does not rerun the Java benchmarks. Edit shared benchmark guide content in `benchmark/site/*.md`; the HTML pages are generated.
