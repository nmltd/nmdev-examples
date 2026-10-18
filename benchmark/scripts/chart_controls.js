/* Interactive views of saved JMH measurements. No external chart dependency. */
(()=>{
'use strict';
const data=JSON.parse(document.getElementById('benchmark-data').textContent);
const $=id=>document.getElementById(id), ns='http://www.w3.org/2000/svg';
const libraryIds=data.libraries.map(l=>l.id), operationIds=data.operations;
const byLib=new Map(data.libraries.map(l=>[l.id,l]));
const lookup=new Map(data.points.map(p=>[[p.library,p.size,p.operation].join('|'),p]));
const defaults=()=>({libraries:[...libraryIds],operations:[...operationIds],mode:'relative',reference:'NMDEV',axis:'fixed'});
const storageKey='nmdev-benchmark-controls-v1';
function clean(value){
 const base=defaults(); if(!value||typeof value!=='object')return base;
 return {libraries:Array.isArray(value.libraries)?libraryIds.filter(x=>value.libraries.includes(x)):base.libraries,
 operations:Array.isArray(value.operations)?operationIds.filter(x=>value.operations.includes(x)):base.operations,
 mode:['relative','absolute'].includes(value.mode)?value.mode:base.mode,
 reference:libraryIds.includes(value.reference)?value.reference:base.reference,
 axis:['fixed','visible'].includes(value.axis)?value.axis:base.axis};
}
let state=defaults();
try{state=clean(JSON.parse(localStorage.getItem(storageKey)))}catch(_){}
const query=new URLSearchParams(location.search);
if(['libs','ops','mode','ref','axis'].some(k=>query.has(k))){
 const from=defaults();
 if(query.has('libs'))from.libraries=query.get('libs').split(',');
 if(query.has('ops'))from.operations=query.get('ops').split(',');
 if(query.has('mode'))from.mode=query.get('mode');
 if(query.has('ref'))from.reference=query.get('ref');
 if(query.has('axis'))from.axis=query.get('axis');
 state=clean(from);
}
const libraryBoxes=[...document.querySelectorAll('input[name=library]')];
const operationBoxes=[...document.querySelectorAll('input[name=operation]')];
function apply(){
 libraryBoxes.forEach(b=>b.checked=state.libraries.includes(b.value));
 operationBoxes.forEach(b=>b.checked=state.operations.includes(b.value));
 $('mode').value=state.mode;$('reference').value=state.reference;$('axis').value=state.axis;
 $('operation-group').value='custom';
}
function selection(){return data.points.filter(p=>state.libraries.includes(p.library)&&state.operations.includes(p.operation))}
function ratio(p){return p.mean/lookup.get([state.reference,p.size,p.operation].join('|')).mean}
function value(p){return state.mode==='absolute'?p.mean:ratio(p)}
function format(x){return Number(x).toPrecision(6).replace(/\.?0+(?=e|$)/,'')}
function make(tag,attrs,text){const node=document.createElementNS(ns,tag);Object.entries(attrs||{}).forEach(([k,v])=>node.setAttribute(k,String(v)));if(text!==undefined)node.textContent=text;return node}
function label(svg,x,y,text,attrs){svg.append(make('text',{x,y,fill:'#172b3a','font-family':'system-ui,sans-serif','font-size':13,...attrs},text))}
let activePoint=null;
function hideTooltip(){activePoint=null;$('tooltip').hidden=true}
function showTooltip(point,x,y){
 activePoint=point;const tooltip=$('tooltip');tooltip.textContent=point.dataset.tooltip;tooltip.hidden=false;
 tooltip.style.left=Math.max(12,Math.min(x+14,innerWidth-tooltip.offsetWidth-12))+'px';
 tooltip.style.top=Math.max(12,Math.min(y+14,innerHeight-tooltip.offsetHeight-12))+'px';
}
function tooltip(p){const lib=byLib.get(p.library);return `${lib.label} · ${p.operation} · ${p.size} × ${p.size}\nMean: ${format(p.mean)} ms/op\n99.9% confidence interval: [${format(p.low)}, ${format(p.high)}] ms/op\nJMH error half-width: ±${format(p.error)} ms/op\nTime / ${byLib.get(state.reference).label}: ${format(ratio(p))}×\n${p.forks} JVM forks · ${p.iterations} measured iteration means\nBackend: ${lib.backend}`}
function draw(size,selected){
 const visible=selected.filter(p=>p.size===size), ops=operationIds.filter(x=>state.operations.includes(x));
 const section=document.createElement('section'), heading=document.createElement('h2');heading.textContent=`${size} × ${size} matrices`;section.append(heading);
 const download=document.createElement('button');download.type='button';download.textContent=`Download ${size} × ${size} SVG`;section.append(download);
 if(!visible.length){const empty=document.createElement('p');empty.textContent='No results selected. Select at least one library and operation.';section.append(empty);download.disabled=true;$('charts').append(section);return}
 const reference=byLib.get(state.reference).label, rowHeight=Math.max(54,state.libraries.length*7);
 const width=1120,left=170,right=32,top=85+Math.ceil(state.libraries.length/3)*22,bottom=90,height=top+ops.length*rowHeight+bottom;
 const svg=make('svg',{xmlns:ns,viewBox:`0 0 ${width} ${height}`,role:'group','aria-label':`${size} × ${size} benchmark chart`});
 svg.append(make('rect',{width,height,fill:'white'}));
 svg.append(make('title',{},`${data.run}: ${size} × ${size}, ${state.mode==='absolute'?'milliseconds per operation':'time relative to '+reference}`));
 label(svg,20,26,`${size} × ${size} matrices · ${state.mode==='absolute'?'milliseconds per operation':'time / '+reference}`,{'font-size':17,'font-weight':700});
 label(svg,20,49,`Run ${data.run} · lower is faster · logarithmic axis`,{'font-size':12});
 state.libraries.forEach((id,i)=>{const x=20+(i%3)*360,y=71+Math.floor(i/3)*22;svg.append(make('circle',{cx:x+5,cy:y-4,r:4.5,fill:byLib.get(id).color}));label(svg,x+16,y,byLib.get(id).label,{'font-size':12})});
 const population=state.axis==='visible'?visible:data.points.filter(p=>p.size===size);
 const values=population.map(value);if(state.mode==='relative')values.push(1);
 let lo=Math.log10(Math.min(...values)),hi=Math.log10(Math.max(...values));
 if(hi-lo<0.25){lo-=0.25;hi+=0.25}const pad=(hi-lo)*0.08;lo-=pad;hi+=pad;
 const xpos=v=>left+(Math.log10(v)-lo)/(hi-lo)*(width-left-right);
 const floor=top+ops.length*rowHeight;
 for(let exponent=Math.ceil(lo);exponent<=Math.floor(hi);exponent++){
  const tick=10**exponent,x=xpos(tick);
  svg.append(make('line',{x1:x,y1:top-10,x2:x,y2:floor,stroke:'#dfe5e9','stroke-width':1}));
  label(svg,x,floor+25,Math.abs(exponent)>2?'1e'+exponent:String(Number(tick.toPrecision(4))),{'text-anchor':'middle'});
 }
 if(state.mode==='relative')svg.append(make('line',{x1:xpos(1),y1:top-10,x2:xpos(1),y2:floor,stroke:'#334b59','stroke-width':1.5,'stroke-dasharray':'5 4'}));
 ops.forEach((op,i)=>{
  const center=top+(i+0.5)*rowHeight;
  label(svg,left-12,center+4,op,{'text-anchor':'end'});
  svg.append(make('line',{x1:left,y1:center,x2:width-right,y2:center,stroke:'#f0f3f5'}));
  state.libraries.forEach((library,j)=>{
   const p=lookup.get([library,size,op].join('|')), y=center+(j-(state.libraries.length-1)/2)*7;
   const dot=make('circle',{cx:xpos(value(p)),cy:y,r:4.5,fill:byLib.get(library).color,stroke:'white','stroke-width':0.6,tabindex:0,role:'img','aria-label':tooltip(p),'data-library':library,'data-operation':op,'data-size':size,'data-tooltip':tooltip(p)});
   dot.append(make('title',{},tooltip(p)));svg.append(dot);
  });
 });
 label(svg,(left+width-right)/2,height-26,state.mode==='absolute'?'Mean time (ms/op, log scale)':'Time / '+reference+' (log scale)',{'text-anchor':'middle'});
 label(svg,20,height-7,'Shared VM · ratios of means · exact versions, backends and uncertainty in point tooltips',{'font-size':11});
 const wrap=document.createElement('div');wrap.className='plot';wrap.append(svg);section.append(wrap);$('charts').append(section);
 download.addEventListener('click',()=>{
  const clone=svg.cloneNode(true);clone.querySelectorAll('circle').forEach(p=>{p.removeAttribute('tabindex')});
  save(new XMLSerializer().serializeToString(clone),'image/svg+xml',`nmdev-${size}-${state.mode}.svg`);
 });
}
function comparisonUrl(){const url=new URL(location.href);url.search='';url.hash='';url.searchParams.set('libs',state.libraries.join(','));url.searchParams.set('ops',state.operations.join(','));url.searchParams.set('mode',state.mode);url.searchParams.set('ref',state.reference);url.searchParams.set('axis',state.axis);return url.href}
function render(){
 hideTooltip();$('charts').replaceChildren();const selected=selection();data.sizes.forEach(size=>draw(size,selected));
 $('selection-status').textContent=`${state.libraries.length} of ${libraryIds.length} libraries · ${state.operations.length} of ${operationIds.length} operations · ${selected.length} points shown`;
 $('share-url').value=comparisonUrl();$('export-csv').disabled=!selected.length;
 try{localStorage.setItem(storageKey,JSON.stringify(state))}catch(_){}
}
function changed(){state=clean({libraries:libraryBoxes.filter(b=>b.checked).map(b=>b.value),operations:operationBoxes.filter(b=>b.checked).map(b=>b.value),mode:$('mode').value,reference:$('reference').value,axis:$('axis').value});render()}
[...libraryBoxes,...operationBoxes,$('mode'),$('reference'),$('axis')].forEach(control=>control.addEventListener('change',changed));
function setLibraries(all){state.libraries=all?[...libraryIds]:[];apply();render()}
$('show-all').addEventListener('click',()=>setLibraries(true));$('hide-all').addEventListener('click',()=>setLibraries(false));
$('all-operations').addEventListener('click',()=>{state.operations=[...operationIds];apply();render()});
$('no-operations').addEventListener('click',()=>{state.operations=[];apply();render()});
const groups={all:operationIds,basic:['transpose','addition','scale','multiplication'],decomposition:['cholesky','lu','qr','eigenSymmetric','svd'],systems:['determinant','inverse','inverseSPD','solve']};
$('operation-group').addEventListener('change',()=>{const name=$('operation-group').value;if(groups[name]){state.operations=[...groups[name]];operationBoxes.forEach(b=>b.checked=state.operations.includes(b.value));render()}});
operationBoxes.forEach(box=>box.addEventListener('change',()=>{$('operation-group').value='custom'}));
$('reset').addEventListener('click',()=>{state=defaults();apply();render();$('action-status').textContent='Default settings restored.'});
$('share').addEventListener('click',async()=>{const url=comparisonUrl();try{await navigator.clipboard.writeText(url);$('action-status').textContent='Comparison link copied.'}catch(_){$('share-url').focus();$('share-url').select();$('action-status').textContent='Copy the selected comparison link with Ctrl+C (Command+C on Mac).'}});
function save(content,type,name){const url=URL.createObjectURL(new Blob([content],{type}));const a=document.createElement('a');a.href=url;a.download=name;a.click();setTimeout(()=>URL.revokeObjectURL(url),1000);$('action-status').textContent=`Downloaded ${name}`}
function csvCell(value){return '"'+String(value).replaceAll('"','""')+'"'}
$('export-csv').addEventListener('click',()=>{
 const columns=['run','size','operation','library','version','backend','mean_ms_per_op','ci_99_9_low_ms','ci_99_9_high_ms','jmh_error_ms','reference','ratio_to_reference','forks','iteration_means'];
 const lines=selection().map(p=>[data.run,p.size,p.operation,p.library,byLib.get(p.library).label,byLib.get(p.library).backend,p.mean,p.low,p.high,p.error,state.reference,ratio(p),p.forks,p.iterations]);
 save([columns,...lines].map(row=>row.map(csvCell).join(',')).join('\r\n')+'\r\n','text/csv;charset=utf-8','nmdev-selected-results.csv');
});
$('charts').addEventListener('pointerover',e=>{const p=e.target.closest('[data-tooltip]');if(p)showTooltip(p,e.clientX,e.clientY)});
$('charts').addEventListener('pointermove',e=>{const p=e.target.closest('[data-tooltip]');if(p&&activePoint===p)showTooltip(p,e.clientX,e.clientY)});
$('charts').addEventListener('pointerout',e=>{if(e.target.closest('[data-tooltip]'))hideTooltip()});
$('charts').addEventListener('focusin',e=>{const p=e.target.closest('[data-tooltip]');if(p){const r=p.getBoundingClientRect();showTooltip(p,r.right,r.top)}});
$('charts').addEventListener('focusout',hideTooltip);
$('charts').addEventListener('click',e=>{const p=e.target.closest('[data-tooltip]');if(p){showTooltip(p,e.clientX,e.clientY);e.stopPropagation()}});
document.addEventListener('click',e=>{if(!e.target.closest('[data-tooltip]'))hideTooltip()});
document.addEventListener('keydown',e=>{if(e.key==='Escape')hideTooltip()});
window.addEventListener('resize',hideTooltip);window.addEventListener('scroll',hideTooltip,{passive:true});
apply();render();
})();
