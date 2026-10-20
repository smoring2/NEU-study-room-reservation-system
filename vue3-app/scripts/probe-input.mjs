import { chromium } from 'playwright'
const b = await chromium.launch()
for (const [label,url] of [['vue2','http://localhost:8080'],['vue3','http://localhost:5173']]) {
  const ctx = await b.newContext({ viewport:{width:1280,height:720} })
  await ctx.addCookies([{name:'Admin-Token',value:'admin',domain:'localhost',path:'/'}])
  const p = await ctx.newPage(); p.on('pageerror',()=>{})
  await p.goto(url+'/#/campusset/add'); await p.waitForTimeout(1500)
  for (const sel of ['.el-input','.el-input__wrapper','.el-input__inner','.el-form-item__content']) {
    const r = await p.locator(sel).first().evaluate(el=>{const c=getComputedStyle(el);const r=el.getBoundingClientRect();return `${Math.round(r.x)},${r.y.toFixed(1)} ${Math.round(r.width)}x${r.height.toFixed(1)} border=${c.borderWidth} radius=${c.borderRadius} bs=${c.boxShadow.slice(0,40)}`}).catch(()=>'null')
    console.log(label, sel, r)
  }
  await ctx.close()
}
await b.close()
