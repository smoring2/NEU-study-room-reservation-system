# Feasibility Assessment (Step 0)

Date: 2026-10-20
Source application: `finalproject/nustudy_frontend/nustudy_central_management_site`
(vue-admin-template 3.8.0, Vue 2.5.17, Vuex 3.0.1, vue-router 3.0.1, Element UI 2.12.0,
webpack 4 / vue-cli-2 build, axios 0.18.0)

## 0.1 Can the Vue 2 application be served or built?

| Attempt | Result |
| --- | --- |
| `npm install --legacy-peer-deps` | FAILED - `node-sass@4` cannot be compiled on Node.js 22 (node-gyp 3.8 / no Python) |
| `npm install` after swapping `node-sass` for `sass` in a throwaway copy at `/tmp/vue2-baseline` | OK |
| `npm run dev` (webpack-dev-server 3.1.5) | not usable on Node.js 22 |
| `NODE_OPTIONS=--openssl-legacy-provider npm run build` | OK after shimming `node-sass` -> `sass` for `sass-loader@7` |
| `npx serve dist -l 8080` | OK - the full application runs (hash-mode router, so a static server is sufficient) |

**Vue 2 server status: CAN_BUILD_ONLY** (served statically from `dist/`).
The original source tree was never modified; all workarounds were applied to the
throwaway copy in `/tmp/vue2-baseline`. The fallback used is recorded in
`before_migration/manifest.json` under `server_method`.

## 0.2 Component count and complexity

- `find src -name '*.vue' | wc -l` in the source app: **30**
- Complexity multipliers: Element UI 2 -> Element Plus 2 migration (x1.3). No
  class-based components (no `vue-property-decorator` / `vue-class-component`),
  no i18n, no Firebase, no form-validation library.
- **Effective complexity: 30 x 1.3 = 39** - below the 40 threshold, so a full
  component migration (not stubs) was planned and executed.

## 0.3 Third-party library Vue 3 compatibility

| Vue 2 dependency | Vue 3 replacement | Status |
| --- | --- | --- |
| element-ui 2.12.0 | element-plus 2.14.7 | OK |
| vuex 3.0.1 | pinia 2.3.1 | OK |
| vue-router 3.0.1 | vue-router 4.6.4 | OK |
| @vue/cli-service / webpack 4 | vite 6.4.4 + @vitejs/plugin-vue | OK |
| vue-template-compiler | @vue/compiler-sfc (bundled with vue 3) | OK |
| svg-sprite-loader + `require.context` | `import.meta.glob` sprite builder | OK (no plugin needed) |
| path-to-regexp 2.4.0 | path-to-regexp 6.3.0 (`compile` named export) | OK |
| node-sass 4 | sass (dart-sass) 1.105 | OK |
| axios 0.18.0 | axios 1.20.0 | OK |
| js-cookie 2.2.0 | js-cookie 3.0.8 | OK |
| nprogress, normalize.css | unchanged / 8.0.1 | OK |

No library without a Vue 3 equivalent. `npm audit` reports **0 vulnerabilities**
and the CVE database reports no known vulnerabilities for any resolved version.

## 0.4 Playwright

`npm install -D @playwright/test && npx playwright install chromium` succeeded and
a headless chromium screenshot at 1280x720 was produced.
**Playwright status: FUNCTIONAL**

## 0.5 Decision

| Risk | Present |
| --- | --- |
| Vue 2 cannot be served or built | No (CAN_BUILD_ONLY is sufficient for baselines) |
| Effective complexity > 40 | No (39) |
| Library without a Vue 3 equivalent | No |
| Playwright blocked | No |

**0 blocking risks - the transformation proceeded with all steps.**

Known environment limitation (not a feasibility risk): the Java microservice
backend is not running, so every data-driven screen renders in its empty/loading
state in both the Vue 2 baseline and the Vue 3 result. The comparison is
therefore like-for-like, and one extracted UI case (CAMP-02, which needs
dictionary data) could not be validated against Vue 2 and was excluded from the
authoritative baseline.
