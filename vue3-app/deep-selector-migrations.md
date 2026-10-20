# Deep selector migrations

Vue 3 replaces the scoped-style descendant combinators `>>>`, `/deep/` and
`::v-deep` with the `:deep()` pseudo-class.

**No occurrence of `>>>`, `/deep/` or `::v-deep` exists in the source
application**, so no selector had to be rewritten:

```
$ grep -rn '>>>\|/deep/\|::v-deep' finalproject/nustudy_frontend/nustudy_central_management_site/src
(no matches)
```

The source reached into Element UI internals in two other ways, both of which are
still valid in Vue 3 and were therefore kept as they were:

| Where | Technique | Status in Vue 3 |
| --- | --- | --- |
| `src/styles/sidebar.scss`, `src/styles/element-ui.scss` | global (non-scoped) stylesheets targeting `.el-*` classes | unchanged - global styles need no deep selector |
| `src/views/login/index.vue` | a second, **non-scoped** `<style>` block (`/* reset element-ui css */`) | unchanged - still a non-scoped block |
| `src/views/campus/schedule.vue` | a non-scoped `<style>` block overriding `.el-tree-node` | unchanged |

Element Plus did move some styling targets, which required updating the
*selectors* (not the combinators). Those changes are listed in
`dependency-migration.md` and implemented in `src/styles/element-plus-compat.scss`
and in the login screen's reset block (`.el-input__wrapper`).
