// Executes the UI behaviour cases from e2e/ui-behavior/cases.mjs in a real
// chromium browser against a running application and writes a markdown report.
// Usage: node scripts/run-ui-tests.mjs <baseUrl> <outMarkdown> <screenshotDir> <label>
import { chromium } from 'playwright'
import fs from 'node:fs'
import path from 'node:path'
import { cases as allCases } from '../e2e/ui-behavior/cases.mjs'

// When a baseline id list is passed as the 5th argument only those cases (the
// ones that passed against the Vue 2 source app) are executed.
const baselineFile = process.argv[6]
const baselineIds = baselineFile ? JSON.parse(fs.readFileSync(baselineFile, 'utf8')) : null
const cases = baselineIds ? allCases.filter(c => baselineIds.includes(c.id)) : allCases

const [baseUrl, outMd, shotDir, label] = process.argv.slice(2)
const VIEWPORT = { width: 1280, height: 720 }

fs.mkdirSync(shotDir, { recursive: true })
const browser = await chromium.launch()
const results = []
const started = Date.now()

for (const c of cases) {
  const ctx = await browser.newContext({ viewport: VIEWPORT })
  if (c.auth) {
    const u = new URL(baseUrl)
    await ctx.addCookies([{ name: 'Admin-Token', value: 'admin', domain: u.hostname, path: '/' }])
  }
  const page = await ctx.newPage()
  const pageErrors = []
  page.on('pageerror', e => pageErrors.push(String(e).slice(0, 160)))
  const failures = []
  const expect = async (cond, what) => { if (!cond) failures.push(what) }
  try {
    await page.goto(baseUrl + c.route, { waitUntil: 'load' })
    await page.waitForTimeout(1300)
    await c.run(page, expect, ctx)
    await page.waitForTimeout(300)
  } catch (e) {
    failures.push('threw: ' + e.message.split('\n')[0].slice(0, 200))
  }
  const shot = path.join(shotDir, `${c.id}.png`)
  try { await page.screenshot({ path: shot, fullPage: false }) } catch { /* page closed */ }
  results.push({ ...c, run: undefined, passed: failures.length === 0, failures, pageErrors, screenshot: shot })
  console.log((failures.length === 0 ? 'PASS ' : 'FAIL ') + c.id + (failures.length ? ' :: ' + failures.join('; ') : ''))
  await ctx.close()
}
await browser.close()

const passed = results.filter(r => r.passed)
const failed = results.filter(r => !r.passed)
const rate = ((passed.length / results.length) * 100).toFixed(2)
const seconds = ((Date.now() - started) / 1000).toFixed(1)

const groups = [...new Set(results.map(r => r.group))]
let md = `# UI Behavior Tests - ${label}\n\n`
md += `- Target: ${baseUrl}\n- Executed with: Playwright ${'chromium'} in a real browser, viewport 1280x720\n`
md += `- Execution date: ${new Date().toISOString()}\n`
md += `- Total extracted: ${results.length}\n- Total passed: ${passed.length}\n- Total failed: ${failed.length}\n`
md += `- Success rate: ${rate}%\n- Execution time: ${seconds}s\n- Screenshots: ${shotDir}/<test-id>.png\n\n`
if (failed.length) {
  md += `## Failures\n\n`
  for (const f of failed) md += `- **${f.id}** ${f.action} - ${f.expected}\n  - reason: ${f.failures.join('; ')}\n${f.pageErrors.length ? '  - page errors: ' + f.pageErrors.join(' | ') + '\n' : ''}`
  md += '\n'
}
md += `## Test cases\n\n`
for (const g of groups) {
  md += `### ${g}\n\n`
  for (const r of results.filter(x => x.group === g)) {
    md += `- ${r.passed ? 'PASS' : 'FAIL'} \`${r.id}\` ${r.action} - ${r.expected}\n`
  }
  md += '\n'
}
fs.writeFileSync(outMd, md)
fs.writeFileSync(outMd.replace(/\.md$/, '.json'), JSON.stringify({ baseUrl, label, total: results.length, passed: passed.length, failed: failed.length, success_rate: Number(rate), seconds: Number(seconds), results: results.map(({ id, group, action, expected, passed, failures, pageErrors, screenshot }) => ({ id, group, action, expected, passed, failures, pageErrors, screenshot })) }, null, 2))
console.log(`\n${label}: ${passed.length}/${results.length} passed (${rate}%) in ${seconds}s -> ${outMd}`)
