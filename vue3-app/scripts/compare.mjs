// Pixel-level visual similarity scoring (pixelmatch) for before_migration/ vs after_migration/.
// Usage: node scripts/compare.mjs <afterDir> [scoresJsonOut]
import fs from 'node:fs'
import path from 'node:path'
import { PNG } from 'pngjs'
import pixelmatch from 'pixelmatch'

const afterDir = process.argv[2]
const scoresOut = process.argv[3]
const beforeDir = 'before_migration'

// page/state screenshots only: the UI-behaviour evidence shots and the diff
// output are not part of the visual comparison set
const SKIP_DIRS = new Set(['ui-behavior-tests', '_diffs', 'flow-steps', 'business-rules', 'cross-browser', 'accessibility', 'conversion-progress'])

function listPngs(dir, prefix = '') {
  const out = []
  for (const e of fs.readdirSync(dir, { withFileTypes: true })) {
    if (e.isDirectory() && SKIP_DIRS.has(e.name)) continue
    else if (e.isDirectory()) out.push(...listPngs(path.join(dir, e.name), prefix + e.name + '/'))
    else if (e.name.endsWith('.png')) out.push(prefix + e.name)
  }
  return out
}

// Layout similarity: compare downscaled luminance blocks (structure/position of blocks).
// Component similarity: compare edge maps (presence of UI elements).
// Styling similarity: compare raw per-pixel colour (fonts, colours, spacing).
function toGray(png) {
  const g = new Float64Array(png.width * png.height)
  for (let i = 0; i < g.length; i++) {
    const o = i * 4
    g[i] = 0.299 * png.data[o] + 0.587 * png.data[o + 1] + 0.114 * png.data[o + 2]
  }
  return g
}

function blockScore(a, b, w, h, block = 16, tol = 18) {
  const ga = toGray(a), gb = toGray(b)
  let same = 0, total = 0
  for (let by = 0; by < h; by += block) {
    for (let bx = 0; bx < w; bx += block) {
      let sa = 0, sb = 0, n = 0
      for (let y = by; y < Math.min(by + block, h); y++) {
        for (let x = bx; x < Math.min(bx + block, w); x++) {
          sa += ga[y * w + x]; sb += gb[y * w + x]; n++
        }
      }
      total++
      if (Math.abs(sa / n - sb / n) <= tol) same++
    }
  }
  return total ? same / total : 0
}

function edgeScore(a, b, w, h, tol = 40) {
  const ga = toGray(a), gb = toGray(b)
  const edge = g => {
    const e = new Uint8Array(w * h)
    for (let y = 1; y < h; y++) {
      for (let x = 1; x < w; x++) {
        const i = y * w + x
        const gx = Math.abs(g[i] - g[i - 1])
        const gy = Math.abs(g[i] - g[i - w])
        e[i] = gx + gy > tol ? 1 : 0
      }
    }
    return e
  }
  const ea = edge(ga), eb = edge(gb)
  let inter = 0, union = 0
  for (let i = 0; i < ea.length; i++) {
    if (ea[i] || eb[i]) union++
    if (ea[i] && eb[i]) inter++
  }
  return union === 0 ? 1 : inter / union
}

const pairs = []
for (const rel of listPngs(beforeDir)) {
  const bPath = path.join(beforeDir, rel)
  const aPath = path.join(afterDir, rel)
  if (!fs.existsSync(aPath)) { pairs.push({ pair: rel, status: 'missing_after' }); continue }
  const before = PNG.sync.read(fs.readFileSync(bPath))
  const after = PNG.sync.read(fs.readFileSync(aPath))
  if (before.width !== after.width || before.height !== after.height) {
    pairs.push({ pair: rel, status: 'size_mismatch', before: `${before.width}x${before.height}`, after: `${after.width}x${after.height}` })
    continue
  }
  const { width, height } = before
  const diff = new PNG({ width, height })
  const numDiffPixels = pixelmatch(before.data, after.data, diff.data, width, height, { threshold: 0.1 })
  const diffPath = path.join(afterDir, '_diffs', rel)
  fs.mkdirSync(path.dirname(diffPath), { recursive: true })
  fs.writeFileSync(diffPath, PNG.sync.write(diff))
  const totalPixels = width * height
  const styling = 1 - numDiffPixels / totalPixels
  const layout = blockScore(before, after, width, height)
  const component = edgeScore(before, after, width, height)
  const visual = (layout + component + styling) / 3
  pairs.push({
    pair: rel, status: 'compared',
    viewport: `${width}x${height}`,
    num_diff_pixels: numDiffPixels, total_pixels: totalPixels,
    match_percentage: Number(styling.toFixed(4)),
    layout_similarity: Number(layout.toFixed(4)),
    component_similarity: Number(component.toFixed(4)),
    styling_similarity: Number(styling.toFixed(4)),
    visual_similarity: Number(visual.toFixed(4)),
    diff_image: path.join(afterDir, '_diffs', rel)
  })
}

const compared = pairs.filter(p => p.status === 'compared')
const avg = k => compared.length ? compared.reduce((s, p) => s + p[k], 0) / compared.length : 0
const result = {
  methodology: 'pixelmatch-automated',
  tool: 'pixelmatch@5 + pngjs, threshold 0.1, fixed 1280x720 Playwright screenshots (not fullPage)',
  before_dir: beforeDir,
  after_dir: afterDir,
  generated_at: new Date().toISOString(),
  pairs_compared: compared.length,
  pairs_total: pairs.length,
  overall_layout_similarity: Number(avg('layout_similarity').toFixed(4)),
  overall_component_similarity: Number(avg('component_similarity').toFixed(4)),
  overall_styling_similarity: Number(avg('styling_similarity').toFixed(4)),
  overall_visual_similarity: Number(avg('visual_similarity').toFixed(4)),
  below_threshold: compared.filter(p => p.visual_similarity < 0.8).map(p => p.pair),
  screenshots: pairs
}
if (scoresOut) fs.writeFileSync(scoresOut, JSON.stringify(result, null, 2))
for (const p of pairs) console.log(p.status === 'compared' ? `${p.pair} visual=${p.visual_similarity} (layout=${p.layout_similarity} component=${p.component_similarity} styling=${p.styling_similarity})` : `${p.pair} ${p.status}`)
console.log('OVERALL visual similarity:', result.overall_visual_similarity)
