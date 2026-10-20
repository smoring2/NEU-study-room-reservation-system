# Filter -> utility function mapping

Vue 3 removed template filters (`{{ value | filter }}`). The source application
declared one local filter and no global (`Vue.filter`) filters.

| Vue 2 filter | Declared in | Vue 3 function | Template change |
| --- | --- | --- | --- |
| `statusFilter` | `src/views/table/index.vue` (`filters: { statusFilter }`) | `statusFilter` exported from `src/utils/filters.js` | `:type="scope.row.status | statusFilter"` -> `:type="statusFilter(scope.row.status)"` |

The mapping table inside the function (`published -> success`, `draft -> gray`,
`deleted -> danger`) and its `undefined` result for an unknown status are
unchanged (business rule DISP-14), and it is covered by
`src/__tests__/utils.spec.js`.

No other `|` filter expressions exist in the source templates, and the
`parseTime` / `formatTime` helpers in `src/utils/index.js` were already plain
functions, so they were copied unchanged.
