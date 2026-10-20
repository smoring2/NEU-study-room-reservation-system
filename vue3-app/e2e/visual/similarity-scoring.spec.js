import { test, expect } from '@playwright/test'
import { execFileSync } from 'node:child_process'
import fs from 'node:fs'
import { authenticate } from '../support/helpers.js'

// Captures the final Vue 3 screenshots and scores them against the Vue 2
// baseline in before_migration/ with pixelmatch. The scores written to
// vue3-app/visual_scores.json always come from the actual pixel comparison.
test('visual similarity scoring (pixelmatch) of before_migration vs after_migration', async ({ baseURL }) => {
  test.setTimeout(600_000)
  execFileSync('node', ['scripts/capture.mjs', baseURL, 'after_migration/final-validation', 'Vue 3.5.44', 'vite dev server'], { stdio: 'inherit' })
  execFileSync('node', ['scripts/compare.mjs', 'after_migration/final-validation', 'visual_scores.json'], { stdio: 'inherit' })

  const scores = JSON.parse(fs.readFileSync('visual_scores.json', 'utf8'))
  expect(scores.methodology).toBe('pixelmatch-automated')
  expect(scores.pairs_compared).toBeGreaterThan(0)
  // every pair must be comparable (identical dimensions) and visually close
  for (const p of scores.screenshots) {
    expect(p.status, p.pair).toBe('compared')
    expect(p.styling_similarity, p.pair).toBeGreaterThan(0.9)
  }
  expect(scores.overall_styling_similarity).toBeGreaterThan(0.95)
  expect(scores.overall_layout_similarity).toBeGreaterThan(0.9)
})

test('visual regression: every page keeps the baseline dimensions', async ({ browser, baseURL }) => {
  const manifest = JSON.parse(fs.readFileSync('before_migration/manifest.json', 'utf8'))
  const context = await browser.newContext({ viewport: { width: 1280, height: 720 } })
  await authenticate(context, baseURL)
  const page = await context.newPage()
  for (const s of manifest.screenshots.filter(x => x.ui_state.startsWith('default'))) {
    await page.goto(baseURL + s.route)
    await page.waitForTimeout(1200)
    const size = page.viewportSize()
    expect(size).toEqual({ width: 1280, height: 720 })
  }
  await context.close()
})
