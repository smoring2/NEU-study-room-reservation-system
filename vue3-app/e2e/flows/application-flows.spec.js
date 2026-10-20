import { test, expect } from '../fixtures/app.js'
import { shot } from '../support/helpers.js'

const DIR = 'after_migration/flow-steps'

// End-to-end coverage of the application flows documented in
// application_flows.txt. Screenshots of every major step land in
// after_migration/flow-steps/.
test.describe('A. Authentication flows', () => {
  test('A1: successful login takes the user to the dashboard', async ({ browser, baseURL }) => {
    const page = await browser.newPage({ viewport: { width: 1280, height: 720 } })
    await page.goto(baseURL + '/#/dashboard')
    await page.waitForTimeout(1200)
    await expect(page).toHaveURL(/\/login/)
    await shot(page, DIR, 'A1-01-login-screen')
    await page.click('.el-button--primary')
    await page.waitForTimeout(1500)
    await expect(page).toHaveURL(/\/dashboard/)
    await expect(page.locator('.dashboard-container')).toContainText('name:admin')
    await shot(page, DIR, 'A1-02-dashboard')
    await page.close()
  })

  test('A2: an invalid username blocks the login', async ({ browser, baseURL }) => {
    const page = await browser.newPage({ viewport: { width: 1280, height: 720 } })
    await page.goto(baseURL + '/#/login')
    await page.waitForTimeout(1000)
    await page.fill('input[name=username]', 'nobody')
    await page.click('.el-button--primary')
    await page.waitForTimeout(800)
    await expect(page.locator('.el-form-item__error').first()).toHaveText('Please enter correct username')
    await expect(page).toHaveURL(/\/login/)
    await shot(page, DIR, 'A2-invalid-username')
    await page.close()
  })

  test('A3: a short password blocks the login', async ({ browser, baseURL }) => {
    const page = await browser.newPage({ viewport: { width: 1280, height: 720 } })
    await page.goto(baseURL + '/#/login')
    await page.waitForTimeout(1000)
    await page.fill('input[name=password]', 'abc')
    await page.click('.el-button--primary')
    await page.waitForTimeout(800)
    await expect(page.locator('.el-form-item__error').first()).toContainText('Password cannot be less than 5 characters')
    await shot(page, DIR, 'A3-short-password')
    await page.close()
  })

  test('A4: a protected route redirects to login and comes back after signing in', async ({ browser, baseURL }) => {
    const page = await browser.newPage({ viewport: { width: 1280, height: 720 } })
    await page.goto(baseURL + '/#/campusset/list')
    await page.waitForTimeout(1200)
    expect(page.url()).toContain('redirect=/campusset/list')
    await shot(page, DIR, 'A4-01-redirected-to-login')
    await page.click('.el-button--primary')
    await page.waitForTimeout(1800)
    await expect(page).toHaveURL(/\/campusset\/list/)
    await shot(page, DIR, 'A4-02-back-on-target-route')
    await page.close()
  })

  test('A5: an authenticated user cannot stay on the login screen', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/login')
    await authedPage.waitForTimeout(1200)
    expect(authedPage.url()).not.toContain('/login')
    await shot(authedPage, DIR, 'A5-login-redirected-away')
  })

  test('A6: logging out from the avatar dropdown returns to the login screen', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/dashboard')
    await authedPage.waitForTimeout(1200)
    await authedPage.click('.avatar-wrapper')
    await authedPage.waitForTimeout(600)
    await shot(authedPage, DIR, 'A6-01-dropdown-open')
    await authedPage.getByText('LogOut', { exact: true }).click()
    await authedPage.waitForTimeout(2000)
    await expect(authedPage).toHaveURL(/\/login/)
    await shot(authedPage, DIR, 'A6-02-logged-out')
  })
})

test.describe('B. Navigation flows', () => {
  test('B1: the sidebar navigates to every visible page', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/dashboard')
    await authedPage.waitForTimeout(1200)
    await authedPage.getByText('Campus set', { exact: true }).click()
    await authedPage.waitForTimeout(700)
    await shot(authedPage, DIR, 'B1-01-campus-set-expanded')
    for (const [label, url] of [['Campus Set List', /\/campusset\/list/], ['Campus Set Add', /\/campusset\/add/], ['Campus List', /\/campusset\/campus\/list/]]) {
      await authedPage.getByText(label, { exact: true }).click()
      await authedPage.waitForTimeout(1200)
      await expect(authedPage).toHaveURL(url)
      await shot(authedPage, DIR, 'B1-' + label.replace(/\s+/g, '-'))
    }
  })

  test('B2: the breadcrumb follows the active route', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/campusset/list')
    await authedPage.waitForTimeout(1300)
    await expect(authedPage.locator('.app-breadcrumb')).toContainText('Dashboard')
    await expect(authedPage.locator('.app-breadcrumb')).toContainText('Campus Set List')
    await shot(authedPage, DIR, 'B2-01-breadcrumb-campus-set')
    await authedPage.getByText('Data Management', { exact: true }).click()
    await authedPage.waitForTimeout(700)
    await authedPage.getByText('Data Dict', { exact: true }).click()
    await authedPage.waitForTimeout(1300)
    await expect(authedPage.locator('.app-breadcrumb')).toContainText('Data Dict')
    await shot(authedPage, DIR, 'B2-02-breadcrumb-data-dict')
  })

  test('B3: the hamburger collapses and restores the sidebar', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/dashboard')
    await authedPage.waitForTimeout(1200)
    await authedPage.click('.hamburger-container svg')
    await authedPage.waitForTimeout(800)
    expect(Math.round((await authedPage.locator('.sidebar-container').boundingBox()).width)).toBe(36)
    await shot(authedPage, DIR, 'B3-01-collapsed')
    await authedPage.click('.hamburger-container svg')
    await authedPage.waitForTimeout(800)
    expect(Math.round((await authedPage.locator('.sidebar-container').boundingBox()).width)).toBe(180)
    await shot(authedPage, DIR, 'B3-02-expanded')
  })

  test('B5: an unknown route shows the 404 page', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/nothing/here')
    await authedPage.waitForTimeout(1200)
    await expect(authedPage.locator('.wscn-http404')).toBeVisible()
    await expect(authedPage).toHaveURL(/\/404/)
    await shot(authedPage, DIR, 'B5-404')
  })
})

test.describe('C/D/E. Screen flows', () => {
  test('C1/C3: the campus-set list and add screens render their controls', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/campusset/list')
    await authedPage.waitForTimeout(1500)
    await expect(authedPage.locator('.app-container')).toContainText('Delete All Chosen Records')
    await shot(authedPage, DIR, 'C1-campus-set-list')
    await authedPage.goto(baseURL + '/#/campusset/add')
    await authedPage.waitForTimeout(1300)
    const inputs = authedPage.locator('.app-container .el-input input')
    const vals = ['Boston Campus', 'BOS01', 'http://localhost:8201', 'Jane Doe', '6170000000']
    for (let i = 0; i < vals.length; i++) await inputs.nth(i).fill(vals[i])
    await expect(inputs.nth(0)).toHaveValue('Boston Campus')
    await shot(authedPage, DIR, 'C3-campus-set-add-filled')
  })

  test('C5/C6: delete actions ask for confirmation', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/campusset/list')
    await authedPage.waitForTimeout(1500)
    await authedPage.getByText('Delete All Chosen Records').click()
    await authedPage.waitForTimeout(900)
    await expect(authedPage.locator('.el-message-box')).toContainText('It will delete all these chosen record.')
    await shot(authedPage, DIR, 'C6-batch-delete-confirm')
    await authedPage.locator('.el-message-box').getByText('Cancel').click()
    await authedPage.waitForTimeout(600)
    await expect(authedPage.locator('.el-message-box')).toHaveCount(0)
  })

  test('D1: the campus list renders its filters and columns', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/campusset/campus/list')
    await authedPage.waitForTimeout(1500)
    await expect(authedPage.locator('.demo-form-inline')).toBeVisible()
    await expect(authedPage.locator('.app-container')).toContainText('Search')
    await expect(authedPage.locator('.app-container')).toContainText('Clear')
    await shot(authedPage, DIR, 'D1-campus-list')
  })

  test('D4: the campus schedule screen renders the tree and the schedule table', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/campusset/campus/schedule/1000_0')
    await authedPage.waitForTimeout(1500)
    await expect(authedPage.locator('.app-container')).toContainText('Choose:')
    await expect(authedPage.locator('.el-aside')).toBeVisible()
    await shot(authedPage, DIR, 'D4-campus-schedule')
  })

  test('E1/E2/E3: the dictionary screen exposes browse, import and export', async ({ authedPage, baseURL }) => {
    await authedPage.goto(baseURL + '/#/cmn/list')
    await authedPage.waitForTimeout(1500)
    await expect(authedPage.locator('.el-toolbar')).toContainText('Export')
    await expect(authedPage.locator('.el-toolbar')).toContainText('Import')
    await shot(authedPage, DIR, 'E1-dictionary-list')
    await authedPage.getByText('Import', { exact: false }).first().click()
    await authedPage.waitForTimeout(900)
    await expect(authedPage.locator('.el-dialog')).toContainText('Excel files only and maximum size is 500kb')
    await shot(authedPage, DIR, 'E2-import-dialog')
    await authedPage.locator('.el-dialog__footer').getByText('Cancel').click()
    await authedPage.waitForTimeout(700)
    await expect(authedPage.locator('.el-dialog')).toBeHidden()
  })
})
