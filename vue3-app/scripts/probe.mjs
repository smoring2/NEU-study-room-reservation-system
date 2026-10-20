import { chromium } from 'playwright'

const targets = [
  { route: '/#/login', auth: false, sels: ['.login-form', '.el-form-item', '.el-input', '.el-input__inner', '.el-button--primary', '.tips', '.title'] },
  { route: '/#/campusset/add', auth: true, sels: ['.el-form-item', '.el-input__inner', '.el-form-item__label', '.el-button--primary'] },
  { route: '/#/campusset/list', auth: true, sels: ['.el-button--danger', '.el-table', '.el-input__inner'] },
  { route: '/#/dashboard', auth: true, sels: ['.navbar', '.el-menu-item', '.el-sub-menu__title, .el-submenu__title'] }
]

const b = await chromium.launch()
for (const t of targets) {
  const out = {}
  for (const [label, url] of [['vue2', 'http://localhost:8080'], ['vue3', 'http://localhost:5173']]) {
    const ctx = await b.newContext({ viewport: { width: 1280, height: 720 } })
    if (t.auth) await ctx.addCookies([{ name: 'Admin-Token', value: 'admin', domain: 'localhost', path: '/' }])
    const p = await ctx.newPage()
    p.on('pageerror', () => {})
    await p.goto(url + t.route, { waitUntil: 'load' })
    await p.waitForTimeout(1500)
    for (const sel of t.sels) {
      const box = await p.locator(sel).first().boundingBox().catch(() => null)
      out[sel] = out[sel] || {}
      out[sel][label] = box ? `${Math.round(box.x)},${Math.round(box.y)} ${Math.round(box.width)}x${Math.round(box.height)}` : null
    }
    await ctx.close()
  }
  console.log('==', t.route)
  for (const sel of t.sels) console.log('  ', sel, '| vue2:', out[sel].vue2, '| vue3:', out[sel].vue3)
}
await b.close()
