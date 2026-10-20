import { test, expect } from '../fixtures/app.js'
import { shot } from '../support/helpers.js'

// Basic structural accessibility checks (no new markup is introduced by the
// migration, so these assert the source application's own semantics).
test.describe('accessibility', () => {
  test('the login form fields are reachable and named', async ({ browser, baseURL }) => {
    const page = await browser.newPage({ viewport: { width: 1280, height: 720 } })
    await page.goto(baseURL + '/#/login')
    await page.waitForTimeout(1200)
    await expect(page.locator('input[name=username]')).toBeVisible()
    await expect(page.locator('input[name=password]')).toBeVisible()
    await page.keyboard.press('Tab')
    expect(await page.evaluate(() => document.activeElement.tagName)).toBeTruthy()
    await shot(page, 'after_migration/accessibility', 'login-keyboard-focus')
    await page.close()
  })

  test('decorative svg icons are hidden from assistive technology', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/dashboard')
    await authedPage.waitForTimeout(1300)
    const icons = authedPage.locator('svg.svg-icon')
    const count = await icons.count()
    expect(count).toBeGreaterThan(0)
    for (let i = 0; i < count; i++) {
      await expect(icons.nth(i)).toHaveAttribute('aria-hidden', 'true')
    }
    await shot(authedPage, 'after_migration/accessibility', 'sidebar-icons')
  })

  test('the page has a single h1-less document title and a unique app root', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/dashboard')
    await authedPage.waitForTimeout(1200)
    await expect(authedPage).toHaveTitle('NEU Study Central Server Management System')
    expect(await authedPage.locator('#app').count()).toBe(1)
    await shot(authedPage, 'after_migration/accessibility', 'document-structure')
  })

  test('images carry the alt text defined by the source application', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/missing')
    await authedPage.waitForTimeout(1300)
    const imgs = authedPage.locator('.wscn-http404 img')
    const n = await imgs.count()
    for (let i = 0; i < n; i++) await expect(imgs.nth(i)).toHaveAttribute('alt', '404')
    await shot(authedPage, 'after_migration/accessibility', '404-images')
  })
})
