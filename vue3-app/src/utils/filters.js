// Vue 2 global/local template filters converted to plain utility functions.
// Replaces the `filters: { statusFilter }` option of views/table/index.vue.
export function statusFilter(status) {
  const statusMap = {
    published: 'success',
    draft: 'gray',
    deleted: 'danger'
  }
  return statusMap[status]
}
