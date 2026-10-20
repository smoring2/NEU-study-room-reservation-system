import { chromium } from 'playwright'
const route = process.argv[2]
const b = await chromium.launch()
const dump = async url => {
  const ctx = await b.newContext({ viewport: { width: 1280, height: 720 } })
  await ctx.addCookies([{ name: 'Admin-Token', value: 'admin', domain: 'localhost', path: '/' }])
  const p = await ctx.newPage(); p.on('pageerror', () => {})
  await p.goto(url + route, { waitUntil: 'load' }); await p.waitForTimeout(2000)
  const r = await p.evaluate(() => {
    const out = []
    const w = document.createTreeWalker(document.body, NodeFilter.SHOW_TEXT)
    let n
    while ((n = w.nextNode())) {
      const t = n.textContent.trim()
      if (!t) continue
      const rg = document.createRange(); rg.selectNodeContents(n)
      const rect = rg.getBoundingClientRect()
      if (rect.width && rect.top < 720) out.push(`${t.slice(0,24)} @${Math.round(rect.x)},${Math.round(rect.y)} ${Math.round(rect.width)}x${Math.round(rect.height)} ${getComputedStyle(n.parentElement).font}`)
    }
    // placeholders
    document.querySelectorAll('input[placeholder], .el-select__placeholder').forEach(e => {
      const rect = e.getBoundingClientRect()
      out.push(`PH:${e.placeholder || e.textContent.trim()} @${Math.round(rect.x)},${Math.round(rect.y)} ${Math.round(rect.width)}x${Math.round(rect.height)} ${getComputedStyle(e).font}`)
    })
    return out
  })
  await ctx.close(); return r
}
const v2 = await dump('http://localhost:8080'), v3 = await dump('http://localhost:5173')
console.log('--VUE2'); v2.forEach(x => console.log(' ', x))
console.log('--VUE3'); v3.forEach(x => console.log(' ', x))
await b.close()
