# Final Verification

Transformation: **AWS/vue.js-version-upgrade** - Vue 2 -> Vue 3
Source application: `finalproject/nustudy_frontend/nustudy_central_management_site`
(vue-admin-template 3.8.0, Vue 2.5.17, Vuex 3, vue-router 3, Element UI 2.12, webpack 4)
Target: `vue3-app/` (Vue 3.5.44, Vite 6.4.4, Pinia 2.3.1, Vue Router 4.6.4, Element Plus 2.14.7)
Date: 2026-10-20

---

## The three required metrics

| Metric | Value | Source of the value |
| --- | --- | --- |
| **Compilation success** | **1** | exit code of `cd vue3-app && npm run build` (exit 0) |
| **Visual verification score** | **0.8770** | `overall_visual_similarity` in `visual_scores.json`, `"methodology": "pixelmatch-automated"` |
| **UI test success rate** | **100.00%** (34/34) | `ui_tests_vue3.md`, produced by Playwright chromium against the running Vue 3 app; proof: 34 non-empty PNGs in `after_migration/ui-behavior-tests/` |

None of these three values is estimated, inferred from code review or guessed.

### Metric details

**Compilation (1)**
```
$ cd vue3-app && npm run build
vite v6.4.4 building for production...
✓ built in 12.5s          (exit code 0)
```

**Visual verification score (0.8770)**
From `visual_scores.json` (pixelmatch 5.3.0 + pngjs on the actual PNG files):

| Dimension | Score |
| --- | --- |
| Layout similarity | 0.9928 |
| Component similarity | 0.6440 |
| Styling similarity | 0.9943 |
| **Overall visual similarity** | **0.8770** |

18 of 18 screenshot pairs compared at a fixed 1280x720 viewport (fullPage
disabled on both sides), no size mismatches. Per-pair scores and the analysis of
the residual gap are in `visual_verification.md` and
`after_migration/final-validation/comparison-report.txt`; per-pair diff images in
`after_migration/final-validation/_diffs/`.

**UI test success rate (100.00%)**
35 interactive UI behaviour cases were extracted from the Vue 2 source
(`ui_tests.md`), executed against the running Vue 2 application (34 passed ->
`ui_tests_vue2.md`, the authoritative baseline; CAMP-02 needs dictionary data
from the unavailable backend and was excluded), and then all 34 baseline cases
were executed in a real chromium browser against `http://localhost:5173`:
**34 executed, 34 passed, 0 failed, 100.00%** in 96.9s.

## Other verification commands

| Command | Result |
| --- | --- |
| `npm run build` | exit 0 |
| `npm run test:unit` | 77/77 Vitest tests passing in 5 files |
| `npx playwright test` | 80/80 Playwright tests passing (flows, business rules, UI behaviour, visual scoring, cross-browser, accessibility) |
| `find vue3-app/src -name '*.vue' \| wc -l` | 30 - equal to the source component count |
| `npm audit` | 0 vulnerabilities |
| CVE database check of all direct dependencies | no known vulnerabilities |
| grep for `Vue.set`/`Vue.delete`/`Vue.observable`/`Vue.extend`/`$listeners`/`$scopedSlots`/`$children`/`new Vue(`/`vuex`/`process.env.`/`filters:`/`mixins:`/`slot-scope`/`/deep/`/`::v-deep`/`el-submenu>`/`.sync=` in `vue3-app/src` | no occurrences |

---

## Vue 2 to Vue 3 Upgrade Summary

### Scope

`nustudy_central_management_site` is the Vue 2 Options-API / Vuex / vue-router
single-page application that this transformation targets; all 30 of its `.vue`
components were migrated. Explicitly out of scope and left untouched:
`nustudy_frontend/nustudy_student_site` (a Nuxt 2 SSR application - Nuxt 2 to
Nuxt 3 is a different transformation) and the older duplicate copies under
`finalproject/nustudy/`. The original source tree was not modified; everything
new lives in `vue3-app/`.

### Pattern migrations performed

| Area | Vue 2 | Vue 3 | Where |
| --- | --- | --- | --- |
| Bootstrap | `new Vue({ el, router, store, render: h => h(App) })` | `createApp(App).use(createPinia()).use(router).use(ElementPlus, { locale }).mount('#root')` | `src/main.js` |
| Components | Options API (`data`/`computed`/`methods`/`watch`/lifecycle) | `<script setup>` with `ref`/`reactive`/`computed`/`watch`/`onMounted`/`onBeforeUnmount`/`defineProps`/`defineOptions` | all 30 `.vue` files |
| State | Vuex 3 store with 2 modules, 4 mutations, 7 actions, 6 root getters | 2 Pinia setup stores (`useAppStore`, `useUserStore`), all actions `async` | `src/stores/*`, `vuex-to-pinia-mapping.md` |
| Store access | `this.$store.state/.dispatch`, `mapGetters` | `useXStore()`, `storeToRefs()` | all consumers |
| Router | `new VueRouter({ scrollBehavior: () => ({ y: 0 }), routes })`, `path: '*'` | `createRouter({ history: createWebHashHistory(), scrollBehavior: () => ({ top: 0 }) })`, `path: '/:pathMatch(.*)*'` | `src/router/index.js` |
| Navigation guard | `beforeEach((to, from, next) => next(...))` | `beforeEach(async (to, from) => true \| { path } \| '/login?redirect=...')` | `src/permission.js` |
| Router access | `this.$route` / `this.$router` | `useRoute()` / `useRouter()` | Breadcrumb, AppMain, Login, campus/campusSet views |
| Mixin | `views/layout/mixin/ResizeHandler.js` | `composables/useResizeHandler.js` (with listener cleanup) | `mixin-to-composable-mapping.md` |
| Filter | `filters: { statusFilter }` + `value | statusFilter` | `utils/filters.js` + `statusFilter(value)` | `filter-to-function-mapping.md` |
| Functional components | 1 `functional: true` JSX render + 3 `<template functional>` | normal (multi-root) SFCs | `Sidebar/Item.vue`, `views/nested/**` |
| Dynamic component | `<component v-bind="{ is: 'router-link', to }">` | `<component :is="linkComponent(to)" v-bind="linkProps(to)">` with the imported `RouterLink` | `Sidebar/Link.vue` |
| Template refs | `this.$refs.loginForm` / `this.$refs.tree2` | `ref(null)` + matching `ref="..."` attribute | `views/login/index.vue`, `views/tree/index.vue` |
| `v-for` + `v-if` | both on the same element | split into a `<template v-for :key>` wrapper with an inner `v-if` | `Breadcrumb/index.vue`, `Sidebar/SidebarItem.vue` |
| Event modifiers | `@keyup.enter.native`, `@click.native.prevent` | `@keyup.enter`, `@click.prevent` | `views/login/index.vue` |
| Global component | `Vue.component('svg-icon', SvgIcon)` | `app.component('svg-icon', SvgIcon)` | `src/icons/index.js` |
| SVG sprite | `svg-sprite-loader` + `require.context` | `import.meta.glob('./svg/*.svg', { query: '?raw', eager: true })` building the same `#icon-<name>` symbols | `src/icons/index.js` |
| Build tool | webpack 4 / vue-cli-2 template, dev port 9528 | Vite 6 + `@vitejs/plugin-vue` (+ `-vue-jsx`), dev port 5173 | `vite.config.js` |
| Env vars | `config/{dev,prod}.env.js` -> `process.env.BASE_API` | `.env.development` / `.env.production` -> `import.meta.env.VITE_BASE_API` | `src/utils/request.js` |
| Sass | `node-sass` 4 + `@import` | `sass` (dart-sass) + `@use ... as *` | `src/styles/*` |
| UI library | Element UI 2.12 | Element Plus 2.14 (`el-sub-menu`, `#title`/`#dropdown`/`#default`/`#tip`/`#footer` slots, `v-model` instead of `:visible.sync`, `ElMessage`/`ElMessageBox`, `size="small"` instead of `mini`, menu theme via CSS variables) | `dependency-migration.md` |
| Transitions | `.fade-enter`, `.fade-transform-enter`, `.breadcrumb-enter` | `*-enter-from` | `src/styles/transition.scss` |
| Visual parity | - | `src/styles/element-plus-compat.scss` restoring the Element UI control metrics | `dependency-migration.md` |

### Defects found and fixed during migration

1. `Sidebar/Link.vue` - an `is` key inside `v-bind` is ignored in Vue 3, so no
   `<a>` was rendered and sidebar child items did not navigate.
2. `views/campusSet/list.vue` - the source's invalid `.error(...)` promise call
   threw a TypeError that, in Vue 3, aborted the component mount (blank table);
   replaced with the intended `.catch(...)`.
3. `views/layout/Layout.vue` - a setup binding named `sidebar` would have
   shadowed the imported `<Sidebar>` component; the bindings were renamed and
   component tags written in PascalCase.
4. `Sidebar/SidebarItem.vue` - `onlyOneChild` is written while the template
   condition is evaluated; keeping it as a `ref` caused a recursive-render loop,
   so it is a plain (non-reactive) scratch variable.
5. `views/campus/list.vue` - `changeSize()` read an undeclared `size`
   identifier; the page-size emitted by `el-pagination` is now a parameter.
6. `index.html` / `src/main.js` - Vue 2 replaced the mount host (`el: '#app'`)
   while Vue 3 mounts into it, which produced two `id="app"` elements; the host
   is now `#root`.

### Artefacts produced

| File | Content |
| --- | --- |
| `feasibility-assessment.md` | Step 0 fail-fast gate (Vue 2 CAN_BUILD_ONLY, complexity 39, Playwright FUNCTIONAL, 0 risks) |
| `business_rules.txt` | 50 rules in 6 categories |
| `application_flows.txt` | 8 flow groups, 30+ step-by-step flows |
| `ui_tests.md` / `ui_tests_vue2.md` / `ui_tests_vue3.md` | 35 extracted / 34 Vue 2-validated / 34 Vue 3-executed UI behaviour cases |
| `visual_scores.json` / `visual_verification.md` | pixelmatch scores and their analysis |
| `after_migration/final-validation/comparison-report.txt` | per-pair comparison report |
| `migration-progress.txt` | per-batch build status and the four feedback-loop iterations |
| `dependency-migration.md`, `deep-selector-migrations.md`, `mixin-to-composable-mapping.md`, `vuex-to-pinia-mapping.md`, `filter-to-function-mapping.md`, `event-bus-migration.md` | migration mappings |
| `src/__tests__/traceability.txt` | rule / flow / UI test / screenshot pair -> test case matrix |
| `src/__tests__/*.spec.js` | 77 Vitest unit and integration tests |
| `e2e/**` | 80 Playwright tests (flows, business rules, UI behaviour, visual scoring, cross-browser, accessibility) |
| `before_migration/`, `after_migration/` | 18 baseline + 18 final page/state screenshots, 34+34 UI behaviour screenshots, flow/business-rule/cross-browser/accessibility/diff captures, manifests |

### Known limitations

1. The Java microservice backend is not running in this environment, so
   data-driven screens are captured and tested in their empty/loading state on
   both sides. One extracted UI case (CAMP-02, the state select dropdown) could
   not be validated against Vue 2 and is excluded from the baseline.
2. The Vue 2 baseline had to be served as a static production build
   (`npm run build` + `npx serve dist`) because webpack-dev-server 3 does not run
   on Node.js 22; the hash-mode router makes this fully equivalent for
   screenshotting. Recorded in `before_migration/manifest.json.server_method`.
3. Element Plus removed `size="mini"`, so the former mini buttons use
   `size="small"` with the Element UI mini metrics; the single genuine
   `size="small"` button (the dictionary import dialog's "Click and upload") is
   therefore 28px instead of 32px tall.
4. Three data-table screenshot pairs score below 0.80 on the composite visual
   metric purely through the edge-overlap sub-score; their per-pixel styling
   similarity is 0.987-0.989 (see `visual_verification.md`).
