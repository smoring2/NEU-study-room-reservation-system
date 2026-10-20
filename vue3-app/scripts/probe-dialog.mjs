import { chromium } from 'playwright'
const b = await chromium.launch()
for (const [label, url] of [['vue2','http://localhost:8080'],['vue3','http://localhost:5173']]) {
  const ctx = await b.newContext({ viewport:{width:1280,height:720} })
  await ctx.addCookies([{name:'Admin-Token',value:'admin',domain:'localhost',path:'/'}])
  const p = await ctx.newPage(); p.on('pageerror',()=>{})
  await p.goto(url+'/#/cmn/list'); await p.waitForTimeout(1500)
  await p.getByText('Import',{exact:false}).first().click(); await p.waitForTimeout(1000)
  for (const sel of ['.el-dialog','.el-dialog__header','.el-dialog__body','.el-dialog__footer','.el-form-item__label','.el-upload__tip','.el-dialog .el-button']) {
    const r = await p.locator(sel).first().evaluate(el=>{const c=getComputedStyle(el);const r=el.getBoundingClientRect();return `${Math.round(r.x)},${Math.round(r.y)} ${Math.round(r.width)}x${Math.round(r.height)} pad=${c.padding} mb=${c.marginBottom}`}).catch(e=>'null')
    console.log(label, sel, r)
  }
  await ctx.close()
}
await b.close()
