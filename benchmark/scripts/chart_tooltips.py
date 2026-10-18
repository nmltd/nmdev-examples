"""Add accessible SVG titles and produce a self-contained hoverable HTML report."""
from pathlib import Path
import html
import xml.etree.ElementTree as ET


SVG = "http://www.w3.org/2000/svg"
ET.register_namespace("", SVG)
ET.register_namespace("xlink", "http://www.w3.org/1999/xlink")


def annotate_svg(path, tooltips):
    """Annotate identified point groups; numerical data comes directly from JMH JSON."""
    tree = ET.parse(path)
    found = set()
    for element in tree.iter():
        key = element.get("id")
        if key in tooltips:
            text = tooltips[key]
            title = ET.Element(f"{{{SVG}}}title")
            title.text = text
            element.insert(0, title)
            element.set("data-tooltip", text)
            element.set("tabindex", "0")
            element.set("role", "img")
            element.set("aria-label", text)
            found.add(key)
    if found != set(tooltips):
        raise ValueError("Missing tooltip points: " + str(set(tooltips) - found))
    tree.write(path, encoding="utf-8", xml_declaration=True)


def write_html(root, sizes):
    charts = []
    for size in sizes:
        svg = ET.parse(root / f"relative-times-{size}.svg").getroot()
        # Two inline SVGs share one document: namespace their IDs and references.
        ids = {element.get("id"): f"chart-{size}-{element.get('id')}"
               for element in svg.iter() if element.get("id")}
        for element in svg.iter():
            for attribute, value in list(element.attrib.items()):
                if attribute == "id":
                    element.set(attribute, ids[value])
                elif value.startswith("#") and value[1:] in ids:
                    element.set(attribute, "#" + ids[value[1:]])
                elif "url(#" in value:
                    for original, replacement in ids.items():
                        value = value.replace(f"url(#{original})", f"url(#{replacement})")
                    element.set(attribute, value)
        charts.append(f'<section><h2>{size} × {size} matrices</h2>'
                      + ET.tostring(svg, encoding="unicode") + '</section>')
    document = '''<!doctype html>
<html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>NM Dev benchmark charts — interactive tooltips</title>
<style>
body{font:16px/1.5 system-ui,sans-serif;margin:0;background:#f4f6f8;color:#172b3a}
main{max-width:1200px;margin:auto;padding:24px}h1{line-height:1.2}a{color:#126c78}
section{background:white;border:1px solid #dbe2e8;border-radius:12px;margin:24px 0;padding:16px}
svg{display:block;width:100%;height:auto}svg [data-tooltip]{cursor:help;outline:none}
svg [data-tooltip]:hover use,svg [data-tooltip]:focus use{stroke:#111;stroke-width:2}
#tooltip{position:fixed;z-index:10;pointer-events:none;white-space:pre-line;max-width:min(380px,calc(100vw - 40px));
background:#102a38;color:white;padding:12px 16px;border-radius:8px;box-shadow:0 4px 20px #0004;
font-size:14px;line-height:1.6;box-sizing:border-box}
.note{color:#435766}h2{margin:0 0 8px}button{font:inherit}
</style></head><body><main>
<h1>NM Dev benchmark charts</h1>
<p>Hover, tap or keyboard-focus any point to inspect its measurements.</p>
<p class="note">Lower time ratios are faster. NM Dev is the 1× reference. Confidence intervals describe timing uncertainty;
the displayed ratio is a ratio of means. Results are from a shared VM and different library API paths.</p>
<p><a href="https://github.com/nmltd/nmdev-examples/tree/main/benchmark/results/''' + html.escape(root.name, quote=True) + '''">Full report and raw measurements</a></p>
''' + "\n".join(charts) + '''
<p class="note">This file is self-contained and works offline. GitHub's inline image previews do not expose point tooltips.</p>
</main><div id="tooltip" role="tooltip" hidden></div>
<script>
const tooltip = document.getElementById('tooltip');
let active = null;
function position(x,y){
  const margin=12;
  tooltip.style.left=Math.max(margin,Math.min(x+14,innerWidth-tooltip.offsetWidth-margin))+'px';
  tooltip.style.top=Math.max(margin,Math.min(y+14,innerHeight-tooltip.offsetHeight-margin))+'px';
}
function show(point,x,y){
  active=point;tooltip.textContent=point.dataset.tooltip;tooltip.hidden=false;
  position(x,y);
}
function hide(){active=null;tooltip.hidden=true;}
document.querySelectorAll('[data-tooltip]').forEach(point=>{
  // Keep native SVG titles in standalone SVGs; custom tooltips replace them in this HTML.
  point.querySelectorAll('title').forEach(title=>title.remove());
  point.addEventListener('pointerenter',event=>show(point,event.clientX,event.clientY));
  point.addEventListener('pointermove',event=>{if(active===point)position(event.clientX,event.clientY)});
  point.addEventListener('pointerleave',hide);
  point.addEventListener('focus',()=>{const r=point.getBoundingClientRect();show(point,r.right,r.top)});
  point.addEventListener('blur',hide);
  point.addEventListener('click',event=>{show(point,event.clientX,event.clientY);event.stopPropagation()});
});
document.addEventListener('click',hide);
document.addEventListener('keydown',event=>{if(event.key==='Escape')hide()});
window.addEventListener('resize',hide);
window.addEventListener('scroll',hide,{passive:true});
</script></body></html>
'''
    (root / "charts.html").write_text(document)
