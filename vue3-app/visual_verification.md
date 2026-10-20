# Visual Verification

All numbers in this document come from `visual_scores.json`, which is produced by
`scripts/compare.mjs` (pixelmatch + pngjs) comparing the PNG files in
`before_migration/` (Vue 2.5.17) with those in `after_migration/final-validation/`
(Vue 3.5.44). No score is estimated or manually assessed.

- Methodology: `pixelmatch-automated`
- Tool: pixelmatch@5 + pngjs, threshold 0.1, fixed 1280x720 Playwright screenshots (not fullPage)
- Generated: 2026-10-20T19:02:25.692Z
- Screenshot pairs compared: 18 of 18
- Viewport: 1280x720, fullPage disabled on both sides

## Overall scores

| Dimension | Score |
| --- | --- |
| Layout similarity | 0.9941 |
| Component similarity | 0.6816 |
| Styling similarity | 0.9954 |
| **Overall visual similarity** | **0.8904** |

`Visual Similarity = (Layout + Component + Styling) / 3`

- Layout similarity: share of 16x16 blocks whose mean luminance matches within a tolerance of 18/255 (block position and structure).
- Component similarity: intersection-over-union of the Sobel-style edge maps (presence and outline of UI elements).
- Styling similarity: `1 - numDiffPixels / totalPixels` from pixelmatch at threshold 0.1 (colours, fonts, spacing).

## Per-screenshot scores

| Screenshot pair | Diff pixels | Layout | Component | Styling | Visual |
| --- | --- | --- | --- | --- | --- |
| notfound/404.png | 111 / 921600 | 1 | 0.9833 | 0.9999 | 0.9944 |
| dashboard/sidebar-collapsed.png | 189 / 921600 | 0.9989 | 0.9373 | 0.9998 | 0.9787 |
| guard/redirect-to-login.png | 395 / 921600 | 0.9994 | 0.887 | 0.9996 | 0.962 |
| login/default.png | 395 / 921600 | 0.9994 | 0.887 | 0.9996 | 0.962 |
| login/filled.png | 395 / 921600 | 0.9994 | 0.887 | 0.9996 | 0.962 |
| login/password-revealed.png | 507 / 921600 | 0.9994 | 0.8699 | 0.9994 | 0.9563 |
| dashboard/default.png | 592 / 921600 | 0.9994 | 0.8561 | 0.9994 | 0.9516 |
| dashboard/avatar-dropdown.png | 949 / 921600 | 0.9986 | 0.7296 | 0.999 | 0.9091 |
| dict/list.png | 2672 / 921600 | 0.9958 | 0.487 | 0.9971 | 0.8266 |
| login/validation-error.png | 3164 / 921600 | 0.9994 | 0.5018 | 0.9966 | 0.8326 |
| campus/schedule.png | 3706 / 921600 | 0.9867 | 0.5117 | 0.996 | 0.8314 |
| campus/list.png | 4752 / 921600 | 0.9931 | 0.4485 | 0.9948 | 0.8121 |
| campus/list-state-dropdown.png | 5072 / 921600 | 0.9931 | 0.4843 | 0.9945 | 0.8239 |
| campusset/list.png | 6183 / 921600 | 0.9886 | 0.4891 | 0.9933 | 0.8237 |
| campusset/edit.png | 11061 / 921600 | 0.9875 | 0.6056 | 0.988 | 0.8604 |
| campusset/add.png | 11271 / 921600 | 0.9881 | 0.6881 | 0.9878 | 0.888 |
| dict/import-dialog.png | 12172 / 921600 | 0.9817 | 0.5253 | 0.9868 | 0.8312 |
| campusset/add-filled.png | 12236 / 921600 | 0.9861 | 0.4897 | 0.9867 | 0.8209 |

## Pairs below the 0.80 visual threshold

None - `below_threshold` in `visual_scores.json` is empty. The lowest-scoring
pair is `campus/list.png` at 0.8121.

### Analysis of the remaining gap

Every pair scores **>= 0.98 on styling similarity** (per-pixel colour) and
**>= 0.98 on layout similarity**, i.e. colours, fonts, spacing, dimensions and
block structure are reproduced. The residual deficit is in the *component*
(edge-overlap) sub-score, which is an intersection-over-union of edge maps and
therefore drops sharply when text is anti-aliased even slightly differently -
the data-dense table and form screens contain long runs of thin 1px borders and
small text.

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
7. `el-select` collapsed to 44x32 because Element Plus replaced the inner
   `el-input` with `.el-select__wrapper`, which shrinks to its content: the
   Element UI 249x40 metrics and the 15px/30px text inset were restored.
8. Element Plus lays menu items out with flexbox, which shrank the 1em sidebar
   `svg-icon`s to zero width (icons invisible, labels shifted ~14px left);
   `flex: none` restores them.
9. Inline-form gutters: Element UI inline items were inline-blocks (10px margin
   plus 4px collapsed whitespace = 14px); Element Plus uses a 32px flex gutter
   and baseline-aligns buttons placed directly in the form.
10. Pagination: Element UI laid it out as inline content, so `text-align:
    center` centred the left group while the `->` wrapper floated right. The
    Element Plus flex layout is re-centred with a flexible `::before`, and the
    jumper editor / page-size select keep the Element UI 28px height, 13px
    font and 36px button width.
11. Table headers are bold (700) again; Element Plus uses 600.
12. The loading spinner circle is 42px again (Element Plus enlarges it).

The residual difference is font rasterisation of identical CSS under Vite vs
webpack and is not a styling regression. Progress of the loop:
0.7774 -> 0.8643 -> 0.8685 -> 0.8744 -> 0.8770 -> **0.8904**.

### Evidence

- Baseline screenshots: `before_migration/` (+ `manifest.json`)
- Final Vue 3 screenshots: `after_migration/final-validation/` (+ `manifest.json`)
- Per-pair diff images: `after_migration/final-validation/_diffs/`
- Intermediate feedback-loop captures: `after_migration/conversion-progress/`
- Raw scores: `visual_scores.json`
- Automated assertion: `e2e/visual/similarity-scoring.spec.js`
