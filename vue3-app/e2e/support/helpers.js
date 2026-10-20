import fs from 'node:fs'
import path from 'node:path'

export const VIEWPORT = { width: 1280, height: 720 }

// Authenticates the browser context the same way the application does: by
// setting the Admin-Token cookie that AUTH-01 defines.
export async function authenticate(context, baseURL = 'http://localhost:5173') {
  const url = new URL(baseURL)
  await context.addCookies([{ name: 'Admin-Token', value: 'admin', domain: url.hostname, path: '/' }])
}

export async function shot(page, dir, name) {
  const file = path.join(dir, name.endsWith('.png') ? name : name + '.png')
  fs.mkdirSync(path.dirname(file), { recursive: true })
  await page.screenshot({ path: file, fullPage: false })
  return file
}
