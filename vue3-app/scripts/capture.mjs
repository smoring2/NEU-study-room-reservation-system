// Baseline / post-migration screenshot capture driver.
// Usage: node scripts/capture.mjs <baseUrl> <outDir> <vueVersionLabel> <serverMethod>
import { chromium } from 'playwright'
import fs from 'node:fs'
import path from 'node:path'

const [baseUrl, outDir, vueVersion, serverMethod] = process.argv.slice(2)
const VIEWPORT = { width: 1280, height: 720 }

// Every distinct route plus key UI states of the application.
const shots = [
  { file: 'login/default.png', route: '/#/login', state: 'default (unauthenticated)', auth: false },
  { file: 'login/filled.png', route: '/#/login', state: 'form filled', auth: false, actions: ['fillLogin'] },
  { file: 'login/password-revealed.png', route: '/#/login', state: 'password revealed', auth: false, actions: ['showPwd'] },
  { file: 'login/validation-error.png', route: '/#/login', state: 'form validation errors', auth: false, actions: ['invalidLogin'] },
  { file: 'guard/redirect-to-login.png', route: '/#/dashboard', state: 'guard redirect, unauthenticated', auth: false },
  { file: 'dashboard/default.png', route: '/#/dashboard', state: 'default (authenticated)', auth: true },
  { file: 'dashboard/sidebar-collapsed.png', route: '/#/dashboard', state: 'sidebar collapsed', auth: true, actions: ['toggleSidebar'] },
  { file: 'dashboard/avatar-dropdown.png', route: '/#/dashboard', state: 'avatar dropdown open', auth: true, actions: ['openAvatar'] },
  { file: 'dict/list.png', route: '/#/cmn/list', state: 'default / empty table (no backend)', auth: true },
  { file: 'dict/import-dialog.png', route: '/#/cmn/list', state: 'import dialog open', auth: true, actions: ['openImport'] },
  { file: 'campusset/list.png', route: '/#/campusset/list', state: 'default / empty table (no backend)', auth: true },
  { file: 'campusset/add.png', route: '/#/campusset/add', state: 'pristine form', auth: true },
  { file: 'campusset/add-filled.png', route: '/#/campusset/add', state: 'filled form', auth: true, actions: ['fillCampusSet'] },
  { file: 'campusset/edit.png', route: '/#/campusset/edit/1', state: 'edit mode (no backend)', auth: true },
  { file: 'campus/list.png', route: '/#/campusset/campus/list', state: 'default / loading table (no backend)', auth: true },
  { file: 'campus/list-state-dropdown.png', route: '/#/campusset/campus/list', state: 'state select dropdown open', auth: true, actions: ['openStateSelect'] },
  { file: 'campus/schedule.png', route: '/#/campusset/campus/schedule/1000_0', state: 'default (no backend)', auth: true },
  { file: 'notfound/404.png', route: '/#/this-route-does-not-exist', state: '404 catch-all', auth: true }
]

async function applyAction(page, action) {
  if (action === 'fillLogin') {
    await page.fill('input[name=username]', 'admin')
    await page.fill('input[name=password]', 'admin')
  } else if (action === 'showPwd') {
    await page.click('.show-pwd')
  } else if (action === 'invalidLogin') {
    await page.fill('input[name=username]', 'nobody')
    await page.fill('input[name=password]', 'abc')
    await page.click('button[type=button], .el-button--primary')
  } else if (action === 'toggleSidebar') {
    await page.click('.hamburger-container svg')
  } else if (action === 'openAvatar') {
    await page.click('.avatar-wrapper')
  } else if (action === 'openImport') {
    await page.getByText('Import', { exact: false }).first().click()
  } else if (action === 'fillCampusSet') {
    const inputs = page.locator('.app-container .el-input__inner')
    const vals = ['Boston Campus', 'BOS01', 'http://localhost:8201', 'Jane Doe', '6170000000']
    for (let i = 0; i < vals.length; i++) await inputs.nth(i).fill(vals[i])
  } else if (action === 'openStateSelect') {
    await page.locator('.el-select').first().click()
  }
}

const browser = await chromium.launch()
const manifest = { source_vue_version: vueVersion, server_method: serverMethod, base_url: baseUrl, viewport: VIEWPORT, full_page: false, screenshots: [] }

for (const shot of shots) {
  const context = await browser.newContext({ viewport: VIEWPORT })
  if (shot.auth) {
    const u = new URL(baseUrl)
    await context.addCookies([{ name: 'Admin-Token', value: 'admin', domain: u.hostname, path: '/' }])
  }
  const page = await context.newPage()
  const errors = []
  page.on('pageerror', e => errors.push(String(e)))
  try {
    await page.goto(baseUrl + shot.route, { waitUntil: 'load' })
    await page.waitForTimeout(1200)
    for (const a of shot.actions || []) {
      try { await applyAction(page, a); await page.waitForTimeout(700) } catch (e) { errors.push('action ' + a + ': ' + e.message) }
    }
    await page.waitForTimeout(500)
    const out = path.join(outDir, shot.file)
    fs.mkdirSync(path.dirname(out), { recursive: true })
    await page.screenshot({ path: out, fullPage: false })
    manifest.screenshots.push({
      file: shot.file, route: shot.route, ui_state: shot.state,
      viewport: `${VIEWPORT.width}x${VIEWPORT.height}`, full_page: false,
      setup_required: shot.auth ? 'Admin-Token cookie set (authenticated admin)' : 'none (unauthenticated)',
      actions: shot.actions || [], timestamp: new Date().toISOString(),
      source_vue_version: vueVersion, page_errors: errors,
      bytes: fs.statSync(out).size
    })
    console.log('captured', shot.file, fs.statSync(out).size, 'bytes', errors.length ? 'errors:' + errors.length : '')
  } catch (e) {
    console.log('FAILED', shot.file, e.message)
    manifest.screenshots.push({ file: shot.file, route: shot.route, ui_state: shot.state, failed: e.message })
  }
  await context.close()
}
await browser.close()
fs.writeFileSync(path.join(outDir, 'manifest.json'), JSON.stringify(manifest, null, 2))
console.log('manifest written to', path.join(outDir, 'manifest.json'))
