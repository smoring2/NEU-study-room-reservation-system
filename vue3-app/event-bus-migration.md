# Event bus / instance event API migration

Vue 3 removes the instance event emitter (`$on`, `$off`, `$once`) and `$children`.

**The source application does not use any of them.** A search of
`finalproject/nustudy_frontend/nustudy_central_management_site/src` for
`$on(`, `$off(`, `$once(`, `$children`, `new Vue(` (as an event bus),
`Vue.observable`, `Vue.extend`, `Vue.set`, `Vue.delete`, `$listeners` and
`$scopedSlots` returns no matches outside of the application bootstrap
(`new Vue({ el, router, store, render })` in `src/main.js`, which became
`createApp(App).use(...).mount(...)`).

Consequently no `mitt` instance and no `provide/inject` event channel were
introduced - adding one would be dead code. Cross-component communication in this
application uses exactly three mechanisms, all of which were migrated directly:

| Mechanism | Vue 2 | Vue 3 |
| --- | --- | --- |
| Shared state | Vuex modules | Pinia stores (`useAppStore`, `useUserStore`) |
| Parent -> child data | `props` | `defineProps()` |
| Child -> parent callback | a `Function` prop (`toggle-click` on `Hamburger`) | the same `Function` prop, declared with `defineProps()` |

If an event bus is needed later, the recommended replacement is `mitt` provided
through `app.provide()`; nothing in the current code base requires it.

## Related removed-API checklist

| Removed Vue 2 API | Occurrences in the source | Action |
| --- | --- | --- |
| `new Vue()` | 1 (`src/main.js`) | `createApp(App)` |
| `Vue.use(ElementUI, { locale })` | 1 | `app.use(ElementPlus, { locale })` |
| `Vue.use(Router)` / `Vue.use(Vuex)` | 2 | removed (plugins are installed on the app instance) |
| `Vue.component('svg-icon', SvgIcon)` | 1 (`src/icons/index.js`) | `app.component('svg-icon', SvgIcon)` via `registerSvgIcon(app)` |
| `Vue.config.productionTip` | 1 | removed (no longer exists in Vue 3) |
| `Vue.prototype.$x` | 0 | n/a |
| `$on` / `$off` / `$once` / `$children` | 0 | n/a |
| `$listeners` / `$scopedSlots` | 0 | n/a |
| `Vue.set` / `Vue.delete` / `Vue.observable` / `Vue.extend` | 0 | n/a |
| `functional: true` component | 1 (`Sidebar/Item.vue`) | normal multi-root SFC |
| `<template functional>` | 3 (`nested/menu1/**`) | normal SFCs |
| `filters:` | 1 | utility function (see filter-to-function-mapping.md) |
| `mixins:` | 1 | composable (see mixin-to-composable-mapping.md) |
| custom directives (`bind`/`inserted`/...) | 0 | n/a |
| `Vue.config.keyCodes` | 0 | n/a |
