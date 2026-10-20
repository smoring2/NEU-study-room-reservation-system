import { chromium } from 'playwright'

const sels = [
  '.sidebar-container .el-submenu__title, .sidebar-container .el-sub-menu__title',
  '.sidebar-container .el-menu',
  '.navbar',
  '.navbar .el-menu-item, .navbar',
  '.app-breadcrumb'
]
const b = await chromium.launch()
for (const [label, url] of [['vue2', 'http://localhost:8080'], ['vue3', 'http://localhost:5173']]) {
  const ctx = await b.newContext({ viewport: { width: 1280, height: 720 } })
  await ctx.addCookies([{ name: 'Admin-Token', value: 'admin', domain: 'localhost', path: '/' }])
  const p = await ctx.newPage()
  p.on('pageerror', () => {})
  await p.goto(url + '/#/dashboard', { waitUntil: 'load' })
  await p.waitForTimeout(1500)
  for (const sel of sels) {
    const info = await p.locator(sel).first().evaluate(el => {
      const c = getComputedStyle(el)
      return {
        cls: el.className,
        bg: c.backgroundColor, color: c.color, h: c.height, lh: c.lineHeight,
        pad: c.padding, border: c.borderBottom, font: c.fontSize
      }
    }).catch(e => ({ err: e.message.slice(0, 60) }))
    console.log(label, sel.split(',')[0], JSON.stringify(info))
  }
  await ctx.close()
}
await b.close()
