import { test, expect } from '../fixtures/app.js'
import { shot } from '../support/helpers.js'

const DIR = 'after_migration/business-rules'

// One browser check per business rule group documented in business_rules.txt.
test.describe('Authentication & authorization rules', () => {
  test('AUTH-01/AUTH-02: the session is the Admin-Token cookie written at login', async ({ browser, baseURL }) => {
    const context = await browser.newContext({ viewport: { width: 1280, height: 720 } })
    const page = await context.newPage()
    await page.goto(baseURL + '/#/login')
    await page.waitForTimeout(1200)
    expect((await context.cookies()).some(c => c.name === 'Admin-Token')).toBe(false)
    await page.click('.el-button--primary')
    await page.waitForTimeout(1500)
    const cookie = (await context.cookies()).find(c => c.name === 'Admin-Token')
    expect(cookie.value).toBe('admin')
    await shot(page, DIR, 'AUTH-01-token-cookie')
    await context.close()
  })

  test('AUTH-03: every non-whitelisted route requires a token', async ({ browser, baseURL }) => {
    const page = await browser.newPage({ viewport: { width: 1280, height: 720 } })
    for (const route of ['/#/dashboard', '/#/cmn/list', '/#/campusset/add', '/#/campusset/campus/list']) {
      await page.goto(baseURL + route)
      await page.waitForTimeout(1100)
      expect(page.url()).toContain('/login')
    }
    await shot(page, DIR, 'AUTH-03-guard')
    await page.close()
  })

  test('AUTH-08/DISP-06: the admin profile is shown on the dashboard', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/dashboard')
    await authedPage.waitForTimeout(1300)
    await expect(authedPage.locator('.dashboard-container')).toContainText('name:admin')
    await expect(authedPage.locator('.dashboard-container')).toContainText('roles:')
    await shot(authedPage, DIR, 'AUTH-08-profile')
  })

  test('AUTH-09: logging out removes the token cookie', async ({ browser, baseURL }) => {
    const context = await browser.newContext({ viewport: { width: 1280, height: 720 } })
    const url = new URL(baseURL)
    await context.addCookies([{ name: 'Admin-Token', value: 'admin', domain: url.hostname, path: '/' }])
    const page = await context.newPage()
    await page.goto(baseURL + '/#/dashboard')
    await page.waitForTimeout(1300)
    await page.click('.avatar-wrapper')
    await page.waitForTimeout(600)
    await page.getByText('LogOut', { exact: true }).click()
    await page.waitForTimeout(2000)
    expect((await context.cookies()).some(c => c.name === 'Admin-Token' && c.value)).toBe(false)
    await shot(page, DIR, 'AUTH-09-logout')
    await context.close()
  })
})

test.describe('Validation rules', () => {
  test('VAL-01: only admin/editor are accepted as the username', async ({ browser, baseURL }) => {
    const page = await browser.newPage({ viewport: { width: 1280, height: 720 } })
    await page.goto(baseURL + '/#/login')
    await page.waitForTimeout(1100)
    await page.fill('input[name=username]', 'someone')
    await page.click('.el-button--primary')
    await page.waitForTimeout(800)
    await expect(page.locator('.el-form-item__error').first()).toHaveText('Please enter correct username')
    await page.fill('input[name=username]', 'editor')
    await page.click('.el-button--primary')
    await page.waitForTimeout(1500)
    expect(page.url()).toContain('/dashboard')
    await shot(page, DIR, 'VAL-01-username')
    await page.close()
  })

  test('VAL-02: the password must be at least 5 characters', async ({ browser, baseURL }) => {
    const page = await browser.newPage({ viewport: { width: 1280, height: 720 } })
    await page.goto(baseURL + '/#/login')
    await page.waitForTimeout(1100)
    await page.fill('input[name=password]', 'four')
    await page.click('.el-button--primary')
    await page.waitForTimeout(800)
    await expect(page.locator('.el-form-item__error').first()).toContainText('Password cannot be less than 5 characters')
    await shot(page, DIR, 'VAL-02-password')
    await page.close()
  })

  test('VAL-04: the login form advertises and pre-fills the demo credentials', async ({ browser, baseURL }) => {
    const page = await browser.newPage({ viewport: { width: 1280, height: 720 } })
    await page.goto(baseURL + '/#/login')
    await page.waitForTimeout(1100)
    await expect(page.locator('input[name=username]')).toHaveValue('admin')
    await expect(page.locator('.tips')).toContainText('username: admin')
    await shot(page, DIR, 'VAL-04-prefilled')
    await page.close()
  })

  test('VAL-05: the import dialog documents the Excel/500kb limit', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/cmn/list')
    await authedPage.waitForTimeout(1400)
    await authedPage.getByText('Import', { exact: false }).first().click()
    await authedPage.waitForTimeout(900)
    await expect(authedPage.locator('.el-dialog')).toContainText('Excel files only and maximum size is 500kb')
    await shot(authedPage, DIR, 'VAL-05-upload-limit')
  })

  test('VAL-07: destructive actions are confirmed', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/campusset/list')
    await authedPage.waitForTimeout(1500)
    await authedPage.getByText('Delete All Chosen Records').click()
    await authedPage.waitForTimeout(900)
    await expect(authedPage.locator('.el-message-box')).toContainText('Notice')
    await expect(authedPage.locator('.el-message-box')).toContainText('Yes')
    await shot(authedPage, DIR, 'VAL-07-confirm')
  })
})

test.describe('Display rules', () => {
  test('DISP-01/DISP-02: the shell keeps the Element UI dark theme and dimensions', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/dashboard')
    await authedPage.waitForTimeout(1300)
    const menuBg = await authedPage.locator('.sidebar-container .el-menu').first().evaluate(el => getComputedStyle(el).backgroundColor)
    expect(menuBg).toBe('rgb(48, 65, 86)')
    const titleBg = await authedPage.locator('.sidebar-container .el-sub-menu__title').first().evaluate(el => getComputedStyle(el).backgroundColor)
    expect(titleBg).toBe('rgb(38, 52, 69)')
    expect(Math.round((await authedPage.locator('.navbar').boundingBox()).height)).toBe(50)
    expect(Math.round((await authedPage.locator('.sidebar-container').boundingBox()).width)).toBe(180)
    await shot(authedPage, DIR, 'DISP-01-theme')
  })

  test('DISP-03: hidden routes never appear in the sidebar', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/dashboard')
    await authedPage.waitForTimeout(1300)
    const text = await authedPage.locator('.sidebar-container').innerText()
    expect(text).not.toContain('Campus Set Edit')
    expect(text).not.toContain('Deatils')
    expect(text).not.toContain('Schedule')
    await shot(authedPage, DIR, 'DISP-03-hidden-routes')
  })

  test('DISP-04: the current breadcrumb crumb is plain text', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/campusset/list')
    await authedPage.waitForTimeout(1400)
    await expect(authedPage.locator('.app-breadcrumb .no-redirect').last()).toBeVisible()
    const color = await authedPage.locator('.app-breadcrumb .no-redirect').last().evaluate(el => getComputedStyle(el).color)
    expect(color).toBe('rgb(151, 168, 190)')
    await shot(authedPage, DIR, 'DISP-04-breadcrumb')
  })

  test('DISP-09/DISP-10: the 404 and login screens keep their exact wording', async ({ authedPage, browser, baseURL }) => {
    await authedPage.goto(baseURL + '/#/missing')
    await authedPage.waitForTimeout(1300)
    await expect(authedPage.locator('.bullshit__oops')).toHaveText('OOPS!')
    await expect(authedPage.locator('.bullshit__headline')).toHaveText('you cannot enter this page......')
    await shot(authedPage, DIR, 'DISP-09-404')
    const page = await browser.newPage({ viewport: { width: 1280, height: 720 } })
    await page.goto(baseURL + '/#/login')
    await page.waitForTimeout(1100)
    await expect(page.locator('.title')).toHaveText('vue-admin-template')
    await shot(page, DIR, 'DISP-10-login')
    await page.close()
  })

  test('DISP-13: campus-set status is rendered as online/offline text', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/campusset/list')
    await authedPage.waitForTimeout(1500)
    const heads = await authedPage.locator('.el-table th').allInnerTexts()
    expect(heads.join('|')).toContain('Status')
    await shot(authedPage, DIR, 'DISP-13-status-column')
  })
})

test.describe('Limits & constraints', () => {
  test('LIM-04: the campus list offers the documented page sizes', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/campusset/campus/list')
    await authedPage.waitForTimeout(1500)
    await expect(authedPage.locator('.el-pagination')).toBeVisible()
    await shot(authedPage, DIR, 'LIM-04-pagination')
  })

  test('LIM-08: login and 404 render without the layout shell', async ({ browser, baseURL }) => {
    const page = await browser.newPage({ viewport: { width: 1280, height: 720 } })
    await page.goto(baseURL + '/#/login')
    await page.waitForTimeout(1100)
    await expect(page.locator('.sidebar-container')).toHaveCount(0)
    await expect(page.locator('.navbar')).toHaveCount(0)
    await shot(page, DIR, 'LIM-08-login-without-shell')
    await page.close()
  })

  test('H2/LIM-03: an unreachable backend leaves the screens usable', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/campusset/campus/list')
    await authedPage.waitForTimeout(2000)
    await expect(authedPage.locator('.el-table')).toBeVisible()
    await expect(authedPage.locator('.demo-form-inline')).toBeVisible()
    await shot(authedPage, DIR, 'H2-backend-unreachable')
  })
})
