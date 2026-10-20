import { test, expect } from '../fixtures/app.js'
import { shot } from '../support/helpers.js'

// The project runs a single chromium project (see playwright.config.js); these
// specs record the per-browser rendering evidence for that project.
const ROUTES = [
  ['login', '/#/login', false],
  ['dashboard', '/#/dashboard', true],
  ['dictionary', '/#/cmn/list', true],
  ['campus-set-list', '/#/campusset/list', true],
  ['campus-set-add', '/#/campusset/add', true],
  ['campus-list', '/#/campusset/campus/list', true],
  ['campus-schedule', '/#/campusset/campus/schedule/1000_0', true],
  ['not-found', '/#/missing', true]
]

for (const [name, route, auth] of ROUTES) {
  test(`chromium renders ${name} at 1280x720`, async ({ browser, authedPage, baseURL }) => {
    const page = auth ? authedPage : await browser.newPage({ viewport: { width: 1280, height: 720 } })
    await page.goto(baseURL + route)
    await page.waitForTimeout(1300)
    expect(page.viewportSize()).toEqual({ width: 1280, height: 720 })
    await expect(page.locator('#app')).toBeVisible()
    await shot(page, 'after_migration/cross-browser/chromium', name)
    if (!auth) await page.close()
  })
}
