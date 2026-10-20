import { chromium } from 'playwright'
const b = await chromium.launch()
for (const [label,url] of [['vue2','http://localhost:8080'],['vue3','http://localhost:5173']]) {
  const ctx = await b.newContext({ viewport:{width:1280,height:720} })
  await ctx.addCookies([{name:'Admin-Token',value:'admin',domain:'localhost',path:'/'}])
  const p = await ctx.newPage(); p.on('pageerror',e=>console.log(label,'ERR',String(e).slice(0,120)))
  await p.goto(url+'/#/dashboard'); await p.waitForTimeout(1500)
  await p.getByText('Campus set',{exact:true}).click(); await p.waitForTimeout(900)
  const html = await p.locator('.sidebar-container').evaluate(el => {
    const out=[]
    el.querySelectorAll('a, .el-menu-item').forEach(n=>out.push(n.tagName+'.'+n.className+' href='+(n.getAttribute('href')||'-')+' text='+n.textContent.trim().slice(0,20)))
    return out.join('\n')
  })
  console.log('==',label,'\n'+html)
  await p.getByText('Campus Set Add',{exact:true}).click(); await p.waitForTimeout(1200)
  console.log(label,'after click url=',p.url())
  await ctx.close()
}
await b.close()
