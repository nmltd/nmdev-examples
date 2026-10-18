#!/usr/bin/env python3
"""Build the latest upstream tagged Parallel Colt release, absent from its old Central coordinate."""
import pathlib,subprocess,sys,zipfile
root=pathlib.Path(__file__).resolve().parents[2];work=root/'benchmark/target/parallelcolt-source'
commit='7c34364026680c1113f1a9586b9fb8c55080330c'
if not (work/'.git').exists():subprocess.run(['git','clone','--depth','1','--branch','parallelcolt-0.11.4','https://github.com/rwl/ParallelColt.git',str(work)],check=True)
assert subprocess.check_output(['git','-C',str(work),'rev-parse','HEAD'],text=True).strip()==commit
classes=work/'target/classes';classes.mkdir(parents=True,exist_ok=True)
cp=':'.join(str(p) for p in sorted((root/'benchmark/target/lib').glob('*.jar')) if not p.name.startswith('parallelcolt-'))
sources=work/'target/sources.txt';sources.write_text('\n'.join(str(p) for p in sorted((work/'src/main/java').rglob('*.java'))))
subprocess.run(['java','com.sun.tools.javac.Main','--release','8','-proc:none','-cp',cp,'-d',str(classes),'@'+str(sources)],check=True)
output=pathlib.Path(sys.argv[1])
with zipfile.ZipFile(output,'w') as z:
    entries=[(p.relative_to(classes).as_posix(),p) for p in classes.rglob('*') if p.is_file()]+[('license.txt',work/'license.txt')]
    resources=work/'src/main/resources'
    if resources.exists():entries += [(p.relative_to(resources).as_posix(),p) for p in resources.rglob('*') if p.is_file()]
    for name,p in sorted(entries):
        info=zipfile.ZipInfo(name,(2022,10,16,0,0,0));info.create_system=3;info.external_attr=0o100644<<16;info.compress_type=zipfile.ZIP_DEFLATED;z.writestr(info,p.read_bytes())
print('Built pinned Parallel Colt 0.11.4:',output)
