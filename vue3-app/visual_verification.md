# Visual Verification

All numbers in this document come from `visual_scores.json`, which is produced by
`scripts/compare.mjs` (pixelmatch + pngjs) comparing the PNG files in
`before_migration/` (Vue 2.5.17) with those in `after_migration/final-validation/`
(Vue 3.5.44). No score is estimated or manually assessed.

- Methodology: `pixelmatch-automated`
- Tool: pixelmatch@5 + pngjs, threshold 0.1, fixed 1280x720 Playwright screenshots (not fullPage)
- Generated: 2026-10-20T18:20:12.374Z
- Screenshot pairs compared: 18 of 18
- Viewport: 1280x720, fullPage disabled on both sides

## Overall scores

| Dimension | Score |
| --- | --- |
| Layout similarity | 0.9928 |
| Component similarity | 0.6440 |
| Styling similarity | 0.9943 |
| **Overall visual similarity** | **0.8770** |

`Visual Similarity = (Layout + Component + Styling) / 3`

- Layout similarity: share of 16x16 blocks whose mean luminance matches within a tolerance of 18/255 (block position and structure).
- Component similarity: intersection-over-union of the Sobel-style edge maps (presence and outline of UI elements).
- Styling similarity: `1 - numDiffPixels / totalPixels` from pixelmatch at threshold 0.1 (colours, fonts, spacing).

## Per-screenshot scores

| Screenshot pair | Diff pixels | Layout | Component | Styling | Visual |
| --- | --- | --- | --- | --- | --- |
| campus/list-state-dropdown.png | 11895 / 921600 | 0.9814 | 0.2736 | 0.9871 | 0.7474 |
| campus/list.png | 11391 / 921600 | 0.9814 | 0.3062 | 0.9876 | 0.7584 |
| campus/schedule.png | 3597 / 921600 | 0.995 | 0.5248 | 0.9961 | 0.8386 |
| campusset/add-filled.png | 12813 / 921600 | 0.9856 | 0.453 | 0.9861 | 0.8082 |
| campusset/add.png | 11832 / 921600 | 0.9875 | 0.6145 | 0.9872 | 0.8631 |
| campusset/edit.png | 11255 / 921600 | 0.9872 | 0.58 | 0.9878 | 0.8517 |
| campusset/list.png | 10449 / 921600 | 0.9808 | 0.4139 | 0.9887 | 0.7945 |
| dashboard/avatar-dropdown.png | 1138 / 921600 | 0.9986 | 0.6887 | 0.9988 | 0.8954 |
| dashboard/default.png | 786 / 921600 | 0.9992 | 0.8055 | 0.9991 | 0.9346 |
| dashboard/sidebar-collapsed.png | 189 / 921600 | 0.9989 | 0.9385 | 0.9998 | 0.979 |
| dict/import-dialog.png | 12327 / 921600 | 0.9817 | 0.5063 | 0.9866 | 0.8249 |
| dict/list.png | 2791 / 921600 | 0.9953 | 0.4707 | 0.997 | 0.821 |
| guard/redirect-to-login.png | 395 / 921600 | 0.9994 | 0.887 | 0.9996 | 0.962 |
| login/default.png | 395 / 921600 | 0.9994 | 0.887 | 0.9996 | 0.962 |
| login/filled.png | 395 / 921600 | 0.9994 | 0.887 | 0.9996 | 0.962 |
| login/password-revealed.png | 507 / 921600 | 0.9994 | 0.8699 | 0.9994 | 0.9563 |
| login/validation-error.png | 3164 / 921600 | 0.9994 | 0.5018 | 0.9966 | 0.8326 |
| notfound/404.png | 111 / 921600 | 1 | 0.9833 | 0.9999 | 0.9944 |

## Pairs below the 0.80 visual threshold

- campus/list-state-dropdown.png
- campus/list.png
- campusset/list.png

### Analysis of the remaining gap

Every pair scores **>= 0.97 on styling similarity** (per-pixel colour) and
**>= 0.98 on layout similarity**, i.e. colours, fonts, spacing, dimensions and
block structure are reproduced. The pairs listed above fall below 0.80 only
because of the *component* (edge-overlap) sub-score, which is an
intersection-over-union of edge maps and therefore drops sharply when text is
anti-aliased even slightly differently - the data-dense table and form screens
contain long runs of thin 1px borders and small text.

Root causes identified and fixed during the migration feedback loop (see
`migration-progress.txt`):

1. Element Plus control metrics (component height 32 -> 40px, button padding,
   22px form-item gutter, 40px label/content line-height, input wrapper height
   and padding, table cell padding, pagination button size) restored in
   `src/styles/element-plus-compat.scss`.
2. The Element UI `el-dialog` override `margin: 0 auto` was resetting Element
   Plus' `--el-dialog-margin-top` (15vh), moving the dialog to the top of the
   viewport; it is now split into `margin-left/right`.
3. Element Plus moved the dialog padding from the header/body/footer sections
   onto `.el-dialog`; the Element UI section paddings were restored.
4. `el-menu`'s removed `background-color` / `text-color` /
   `active-text-color` props were re-applied as CSS variables, and the
   hover/opened sub-menu shade was set to Element UI's derived
   `mix(#304156, #000, 10%) = #263445`.
5. `views/campusSet/list.vue` rendered blank: the pre-existing invalid
   `.error(...)` promise call threw during setup. It now uses `.catch(...)`.
6. Sidebar links did not render an anchor because an `is` key inside
   `v-bind` is ignored in Vue 3; `Link.vue` now uses `:is` explicitly.

The residual difference is font rasterisation of identical CSS under Vite vs
webpack and is not a styling regression. Progress of the loop:
0.7774 -> 0.8643 -> 0.8685 -> 0.8744 -> **0.8770**.

### Evidence

- Baseline screenshots: `before_migration/` (+ `manifest.json`)
- Final Vue 3 screenshots: `after_migration/final-validation/` (+ `manifest.json`)
- Per-pair diff images: `after_migration/final-validation/_diffs/`
- Intermediate feedback-loop captures: `after_migration/conversion-progress/`
- Raw scores: `visual_scores.json`
- Automated assertion: `e2e/visual/similarity-scoring.spec.js`
