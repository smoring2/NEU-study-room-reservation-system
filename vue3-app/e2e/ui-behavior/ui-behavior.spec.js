import { test, expect } from '@playwright/test'
import { cases } from './cases.mjs'
import baselineIds from './baseline-ids.json' with { type: 'json' }
import { shot } from '../support/helpers.js'

// Replays, as individual Playwright tests, exactly the UI behaviour cases that
// passed against the Vue 2 source application (ui_tests_vue2.md).
for (const c of cases.filter(x => baselineIds.includes(x.id))) {
  test(`${c.id} ${c.action} - ${c.expected}`, async ({ browser, baseURL }) => {
    const context = await browser.newContext({ viewport: { width: 1280, height: 720 } })
    if (c.auth) {
      const u = new URL(baseURL)
      await context.addCookies([{ name: 'Admin-Token', value: 'admin', domain: u.hostname, path: '/' }])
    }
    const page = await context.newPage()
    const failures = []
    const check = async (cond, what) => { if (!cond) failures.push(what) }
    await page.goto(baseURL + c.route, { waitUntil: 'load' })
    await page.waitForTimeout(1300)
    await c.run(page, check, context)
    await shot(page, 'after_migration/ui-behavior-tests', c.id)
    expect(failures, failures.join('; ')).toEqual([])
    await context.close()
  })
}
