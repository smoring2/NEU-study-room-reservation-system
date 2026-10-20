import { test as base } from '@playwright/test'
import { authenticate } from '../support/helpers.js'

// `authedPage` is a page whose context already holds the Admin-Token cookie.
export const test = base.extend({
  authedPage: async ({ browser, baseURL }, use) => {
    const context = await browser.newContext({ viewport: { width: 1280, height: 720 } })
    await authenticate(context, baseURL)
    const page = await context.newPage()
    await use(page)
    await context.close()
  }
})

export { expect } from '@playwright/test'
