import { chromium } from 'playwright'
const route = process.argv[2] || '/#/campus/list'
const b = await chromium.launch()
const dump = async (label, url) => {
  const ctx = await b.newContext({ viewport: { width: 1280, height: 720 } })
  await ctx.addCookies([{ name: 'Admin-Token', value: 'admin', domain: 'localhost', path: '/' }])
  const p = await ctx.newPage()
  p.on('pageerror', () => {})
  await p.goto(url + route, { waitUntil: 'load' })
  await p.waitForTimeout(2000)
  const rows = await p.evaluate(() => {
    const out = []
    const walk = (el, depth) => {
      const r = el.getBoundingClientRect()
      if (r.width > 0 && r.height > 0 && r.top < 720 && depth < 9) {
        const cs = getComputedStyle(el)
        out.push({
          tag: el.tagName.toLowerCase(),
          cls: (el.className || '').toString().split(/\s+/).slice(0, 3).join('.'),
          box: `${Math.round(r.x)},${Math.round(r.y)} ${Math.round(r.width)}x${Math.round(r.height)}`,
          font: cs.fontSize + '/' + cs.lineHeight,
          pad: cs.padding,
          mar: cs.margin
        })
        for (const c of el.children) walk(c, depth + 1)
      }
    }
    walk(document.querySelector('.app-main') || document.body, 0)
    return out
  })
  await ctx.close()
  return rows
}
const v2 = await dump('vue2', 'http://localhost:8080')
const v3 = await dump('vue3', 'http://localhost:5173')
console.log('--- VUE2 (' + v2.length + ')')
v2.forEach(r => console.log(`${r.tag}.${r.cls} ${r.box} f=${r.font} p=${r.pad} m=${r.mar}`))
console.log('--- VUE3 (' + v3.length + ')')
v3.forEach(r => console.log(`${r.tag}.${r.cls} ${r.box} f=${r.font} p=${r.pad} m=${r.mar}`))
await b.close()
