# Dependency and build-tool migration

Source: `finalproject/nustudy_frontend/nustudy_central_management_site`
(vue-admin-template 3.8.0 / Vue 2.5.17 / webpack 4 / vue-cli-2 template)
Target: `vue3-app/` (Vue 3.5.44 / Vite 6.4.4)

## Runtime dependencies

| Vue 2 | Version | Vue 3 | Resolved version | Notes |
| --- | --- | --- | --- | --- |
| vue | 2.5.17 | vue | 3.5.44 | `createApp`, `<script setup>` |
| vuex | 3.0.1 | pinia | 2.3.1 | see `vuex-to-pinia-mapping.md` |
| vue-router | 3.0.1 | vue-router | 4.6.4 | `createRouter` + `createWebHashHistory` |
| element-ui | 2.12.0 | element-plus | 2.14.7 | see the Element Plus section below |
| axios | 0.18.0 | axios | 1.20.0 | API unchanged for this code base |
| js-cookie | 2.2.0 | js-cookie | 3.0.8 | API unchanged |
| normalize.css | 7.0.0 | normalize.css | 8.0.1 | |
| nprogress | 0.2.0 | nprogress | 0.2.0 | unchanged |
| path-to-regexp | 2.4.0 (devDependency) | path-to-regexp | 6.3.0 | now a runtime dependency; `pathToRegexp.compile` -> named `compile` import |
| (node `path` via webpack polyfill) | - | path-browserify | 1.0.1 | aliased as `path` in `vite.config.js`; `SidebarItem` uses `path.resolve` |

## Build tooling

| Vue 2 | Vue 3 |
| --- | --- |
| `@vue/cli-service`-style webpack 4 config in `build/*.js` + `config/*.js` | `vite.config.js` |
| `webpack-dev-server` on port 9528 | Vite dev server on port 5173 |
| `vue-loader` 15 + `vue-template-compiler` | `@vitejs/plugin-vue` (uses `@vue/compiler-sfc`) |
| `babel-plugin-transform-vue-jsx` | `@vitejs/plugin-vue-jsx` (kept for parity; the only JSX component became a template) |
| `node-sass` 4 + `sass-loader` 7 | `sass` (dart-sass) 1.105 - Vite has built-in sass support |
| `svg-sprite-loader` + `require.context('./svg')` | `import.meta.glob('./svg/*.svg', { query: '?raw', eager: true })` building the same `#icon-<name>` sprite in `src/icons/index.js` |
| `html-webpack-plugin` + `index.html` template | Vite's native `index.html` entry (`<script type="module" src="/src/main.js">`) |
| `copy-webpack-plugin` for `static/` | `public/` directory |
| `uglifyjs-webpack-plugin` / `optimize-css-assets-webpack-plugin` | Vite's built-in esbuild/rollup minification |
| `eslint-loader` | not wired into the build (lint stays a separate concern) |
| output `dist/` with `static/` assets subdirectory | same (`build.outDir: 'dist'`, `build.assetsDir: 'static'`) |

## Environment variables

| Vue 2 | Vue 3 |
| --- | --- |
| `config/dev.env.js` -> `BASE_API: "http://localhost"` | `.env.development` -> `VITE_BASE_API=http://localhost` |
| `config/prod.env.js` -> `BASE_API: "https://easy-mock.com/mock/5950a2419adc231f356a6636/vue-admin"` | `.env.production` -> `VITE_BASE_API=...` (same value) |
| `process.env.BASE_API` in `src/utils/request.js` | `import.meta.env.VITE_BASE_API` |
| `config/index.js` -> `dev.proxyTable` (empty) | `vite.config.js` -> `server.proxy` (empty) |

There were no `VUE_APP_*` variables (the project predates vue-cli 3) and no other
`process.env` reference in `src/`.

## Backend / CORS

The backend is a separate Java + Spring Cloud multi-module Maven project
(`finalproject/nustudy_parent`, service registry in `nacos/`, prebuilt jars in
`jars/`). It is not part of the frontend build and is therefore **not** copied
into `vue3-app/`; its deployment is unchanged. Two things must be adjusted when
running the Vue 3 front end against it:

1. The gateway/services must allow the Vite dev origin `http://localhost:5173`
   (the Vue 2 dev server used `http://localhost:9528`).
2. `VITE_BASE_API` must point at the gateway (default `http://localhost`), and the
   production build output is still `dist/`, so any static hosting or
   reverse-proxy configuration keeps working unchanged.

The dictionary import/export endpoints are hard-coded to
`http://localhost:8202/admin/cmn/dict/...` in the source and were preserved
verbatim.

## Element UI 2 -> Element Plus 2

### Component / API renames applied

| Element UI 2 | Element Plus 2 | Files |
| --- | --- | --- |
| `<el-submenu>` | `<el-sub-menu>` | `Sidebar/SidebarItem.vue` |
| `<template slot="title">` | `<template #title>` | `Sidebar/SidebarItem.vue` |
| `<el-dropdown-menu slot="dropdown">` | `<template #dropdown><el-dropdown-menu>` | `layout/components/Navbar.vue` |
| `<template slot-scope="scope">` | `<template #default="scope">` | `dict/list.vue`, `campus/list.vue`, `campus/schedule.vue`, `campusSet/list.vue`, `table/index.vue` |
| `<div slot="tip">` (el-upload) | `<template #tip>` | `dict/list.vue` |
| `<div slot="footer">` (el-dialog) | `<template #footer>` | `dict/list.vue` |
| `:visible.sync="x"` | `v-model="x"` | `dict/list.vue` |
| `size="mini"` | `size="small"` (mini was removed) | `campus/list.vue`, `campusSet/list.vue` |
| `this.$message(...)` / `this.$confirm(...)` | `ElMessage(...)` / `ElMessageBox.confirm(...)` imported from `element-plus` | `dict/list.vue`, `campusSet/list.vue`, `campusSet/add.vue`, `form/index.vue` |
| `Message` / `MessageBox` imports | `ElMessage` / `ElMessageBox` | `utils/request.js`, `permission.js` |
| `element-ui/lib/locale/lang/en` | `element-plus/es/locale/lang/en` | `main.js` |
| `element-ui/lib/theme-chalk/index.css` | `element-plus/dist/index.css` | `main.js` |
| `@keyup.enter.native` / `@click.native.prevent` | `@keyup.enter` / `@click.prevent` (`.native` was removed) | `login/index.vue` |
| `el-menu` `background-color` / `text-color` / `active-text-color` props | `--el-menu-bg-color` / `--el-menu-text-color` / `--el-menu-active-color` / `--el-menu-hover-bg-color` CSS variables | `Sidebar/index.vue` |

### Visual-parity layer (`src/styles/element-plus-compat.scss`)

Element Plus changed default control metrics and moved some styling targets.
These rules restore the Element UI 2 appearance so the screenshots stay
pixel-comparable with `before_migration/`:

| Element Plus default | Element UI 2 value restored |
| --- | --- |
| `--el-component-size: 32px` | `40px` (plus `small: 32px`, `large: 40px`) |
| button height/padding 32px / 8px 15px | `40px` / `12px 20px`, radius 4px |
| `.el-button--small` 24px | `28px` / `7px 15px` / 12px (the former `mini` metrics) |
| input wrapper padding `1px 11px`, height 38px | padding `0 15px`, height `40px` |
| form item margin 18px, label line-height 32px | margin `22px`, label and content line-height `40px` |
| table cell padding 8px | `12px 0` |
| pagination button 32px | `28px` (width 35px), font-size 13px |
| dialog padding on `.el-dialog` | header `20px 20px 10px`, body `30px 20px`, footer `10px 20px 20px` |
| horizontal menu border `#dcdfe6` | `#e6e6e6` |
| sub-menu title `padding-right: 44px` | `20px` |
| input background/box-shadow on `.el-input__wrapper` (login reset) | transparent / none (the Vue 2 reset targeted `input`) |
| `el-menu` hover/opened shade (default light) | `#263445`, i.e. Element UI's `mix(#304156, #000, 10%)` |

### Transition class renames (Vue 3 core, `src/styles/transition.scss`)

| Vue 2 | Vue 3 |
| --- | --- |
| `.fade-enter` | `.fade-enter-from` |
| `.fade-transform-enter` | `.fade-transform-enter-from` |
| `.breadcrumb-enter` | `.breadcrumb-enter-from` |

### Sass module system

`src/styles/index.scss` and `src/styles/sidebar.scss` use `@use ... as *` instead
of `@import`, because dart-sass deprecated `@import`; the emitted CSS is
unchanged.

## Security

- `npm audit`: **0 vulnerabilities**.
- A CVE-database check of every direct dependency at its resolved lockfile
  version (`vue`, `vue-router`, `pinia`, `vite`, `axios`, `js-cookie`,
  `element-plus`, `nprogress`, `normalize.css`, `sass`, `vitest`, `jsdom`,
  `@playwright/test`, `@vue/test-utils`, `path-to-regexp`, `path-browserify`,
  `pixelmatch`, `pngjs`) reported **no known vulnerabilities**.
- `vite-plugin-svg-icons` was deliberately **not** used: it pulls in
  `postcss@5`/`braces@2`/`micromatch@3` with high-severity advisories. The sprite
  is built with Vite's native `import.meta.glob` instead.
- `vitest` was pinned to `^5` because 2.1.0-4.1.10 are affected by a path
  traversal advisory and older `tinypool` versions by a prototype-pollution RCE.
