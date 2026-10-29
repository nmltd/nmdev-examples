"""Publish every portable-launcher profile, keeping thread configurations separate."""
import importlib.util
import json
from collections import defaultdict
from pathlib import Path
from interactive_charts import write_explorer


def publish(repo, source):
    def module(name, filename):
        spec=importlib.util.spec_from_file_location(name,Path(__file__).with_name(filename))
        mod=importlib.util.module_from_spec(spec);spec.loader.exec_module(mod);return mod
    site=module('pages','publish-pages.py')
    if (source/'publication-format.json').exists():
        from validate_published import validate
        validation=validate(source)
    else:
        validation=module('validate_launcher','validate-launcher-output.py').validate(source)
        (source/'validation-report.json').write_text(json.dumps(validation,indent=2)+'\n')
    rows=json.loads((source/'jmh.json').read_text());versions=json.loads((source/'versions.json').read_text(encoding='utf-8-sig'))
    groups=defaultdict(list)
    for row in rows: groups[(row['profile'],row['requested_threads'])].append(row)
    output=repo/'docs';base=site.REPO_URL+'/blob/main/'+source.relative_to(repo).as_posix()
    names={k:('benchmarks.html' if k==('baseline',1) else f'benchmarks-{k[0]}-{k[1]}thread.html') for k in groups}
    links='<nav aria-label="Measurement profiles">'+ ' · '.join(f'<a href="{names[k]}">{k[0].title()} · {k[1]} requested thread(s) · {len(v)} cases</a>' for k,v in groups.items())+'</nav>'
    machine=validation['machine']
    environment=(f"{machine['cpu_model']}; {machine['available_processors']} logical processors; "
        f"{machine['physical_memory_bytes']/2**30:.2f} GiB physical RAM; {machine['os.name']} {machine['os.arch']}; "
        f"{machine['java.vendor']} JDK {machine['java.version']} ({machine['java.vm.version']}).")
    note=('Two separately warmed JVM forks per case; two 100 ms warmups and three 200 ms measurements per fork; '
          'one JMH worker; requested compute threads are shown per profile. Fork heap: 256 MiB initial / 1 GiB maximum. '
          'CPU affinity was not set. Native worker counts were not independently audited. These short measurements may have wide confidence intervals; rankings are machine-specific.')
    for key,subset in groups.items():
        chart=write_explorer(source,subset,site.site_nav()+site.nav('benchmarks.html')+links,site.CSS)
        notice=f'<p><strong>{key[0].title()} · {key[1]} requested compute thread(s).</strong> {environment}</p><p>{note}</p>'
        chart=chart.replace('<h1>NM Dev benchmark explorer</h1>','<h1>NM Dev benchmark explorer</h1>'+notice)
        (output/names[key]).write_text(chart)
    intro=f'# Benchmark results — {source.name}\n\n{len(rows):,} cases across {len(versions)} implementation variants; {2*len(rows):,} independently warmed fork records; {validation['measurement_iteration_means']:,} measured iteration means. Lower ms/op is faster.\n\n{environment}\n\n{note}\n\n'
    intro+=f"The run measures **{versions['NMDEV']}**. Profiles are kept separate; requested compute thread counts do not imply every library executes in parallel.\n\n"
    intro+=f"## Validation\n\nAll {len(rows):,} requested cases, available fork JSON checksums, raw samples, arithmetic means, summary values, JVM thread flags and machine identity fields passed validation. All {validation['preflights_passed']} preflight processes exited successfully. The runner reports completion with zero failed cases. {len(validation.get('missing_fork_json', []))} individual fork JSON files are absent from the supplied ZIP; combined measurements remain available, but those missing files cannot be independently checksum-verified. Downloaded binaries are excluded, so their recorded hashes cannot be recomputed from this archive.\n\n"
    intro+=f'[Validation evidence]({base}/validation-report.json) · [Saved JMH measurements]({base}/jmh.json) · [Summary CSV]({base}/summary.csv) · [Hardware and archive digest]({base}/publication-format.json) · [Binary provenance]({base}/binary-provenance.json)\n\n'
    report=intro
    for key,subset in groups.items():
        report+=f'## {key[0].title()} — {key[1]} requested compute thread(s)\n\n[Interactive chart]({names[key]})\n\n| Size / shape | Operation | Library | Mean ms/op | 99.9% error ± ms |\n|---|---|---|---:|---:|\n'
        for r in subset:
            p=r['params'];m=r['primaryMetric']
            report+=f"| {p.get('size',p.get('shape'))} | {p.get('operation','multiplication')} | {versions[p['library']]} | {m['score']:.9g} | {m['scoreError']:.9g} |\n"
        report+='\n'
    (source/'PUBLICATION.md').write_text(report)
    (output/'results.html').write_text(site.page('Results',report,'results.html').replace('<main>','<main>'+links,1))
    (output/'validation.html').write_text(site.page('Validation',intro,'validation.html'))
    libraries='# Measured libraries\n\n| Library | Recorded version / backend |\n|---|---|\n'+''.join(f'| {k} | {v} |\n' for k,v in versions.items())
    libraries+='\nMTJ uses F2J; jblas uses its native backend. Parallel Colt is locally built from the recorded upstream source. Commons row and block variants are separately identified where present. EJML multiplication uses the sequential CommonOps_DDRM API; Commons row and block multiplication are sequential. Requested thread limits do not establish equal parallel execution.\n'
    (output/'libraries.html').write_text(site.page('Libraries',libraries,'libraries.html'))
    methodology='# Methodology\n\n'+environment+'\n\n'+note+f'\n\nThe benchmark adapters and launcher are available in the [benchmark source]({site.REPO_URL}/tree/main/benchmark). Input and output validation occur before timing; the original ZIP preserves correctness, pivot, fairness, origin and expanded rectangular preflight logs. Public data contains timings, samples and hardware specifications; local paths and raw logs are excluded.\n\nFor rectangular multiplication, `m × k × n` means `(m × k) · (k × n)`. Timings and confidence intervals are copied from JMH without rounding before plotting. Ratios compare means for the same profile, operation and size; ratio confidence intervals are not estimated.\n'
    methodology+='\nProfiles are published only when present in the saved measurements. Machine-capacity profiles use JVM-visible logical processors. Commons block results are a separate implementation variant. Worker counts were not independently audited. Compare the same operation, dimensions and requested-thread profile. The Commons snapshot differs between the October 20 and October 28 runs, so cross-run changes cannot be attributed solely to NM Dev.\n'
    (output/'methodology.html').write_text(site.page('Methodology',methodology,'methodology.html'))
    reproduce=f'# Reproduce\n\nUse the [portable benchmark launcher]({site.REPO_URL}/tree/main/benchmark) and retain the full local checkpoint directory for resume.\n\nValidate this saved run without executing benchmarks:\n\n```bash\npython benchmark/scripts/validate_published.py {source.relative_to(repo)}\npython benchmark/scripts/publish-pages.py {source.relative_to(repo)}\npython scripts/check-site.py\n```\n\nThe public report includes measured versions, hardware specifications and NM Dev binary provenance. Full logs, local paths and process command lines remain in the original ZIP. Reproduction requires the recorded dependency binaries and a suitable license.\n'
    (output/'reproduce.html').write_text(site.page('Reproduce',reproduce,'reproduce.html'))
    site.build_tutorials(repo,output)
    print(f'Published {len(groups)} profiles from saved measurements.')
