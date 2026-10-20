# Mixin -> composable mapping

The source application had exactly one mixin.

| Vue 2 mixin | Vue 3 composable |
| --- | --- |
| `src/views/layout/mixin/ResizeHandler.js` | `src/composables/useResizeHandler.js` (`useResizeHandler()`) |

Consumed by `src/views/layout/Layout.vue` (`mixins: [ResizeMixin]` -> `useResizeHandler()`).

| Mixin member | Composable equivalent |
| --- | --- |
| `watch: { $route(route) { ... } }` | `watch(route, () => ...)` with `route = useRoute()` |
| `beforeMount()` -> `window.addEventListener('resize', this.resizeHandler)` | `onBeforeMount(() => window.addEventListener('resize', resizeHandler))` |
| `mounted()` -> mobile detection | `onMounted(...)` with the same body |
| `methods.isMobile()` | local function `isMobile()` |
| `methods.resizeHandler()` | local function `resizeHandler()` |
| `store.dispatch('ToggleDevice', ...)` | `appStore.toggleDevice(...)` |
| `store.dispatch('CloseSideBar', { withoutAnimation })` | `appStore.closeSideBar({ withoutAnimation })` |
| `this.device` / `this.sidebar` (resolved from the host component) | `appStore.device` / `appStore.sidebar` (no implicit dependency on the host) |
| (none - the mixin leaked its listener) | `onBeforeUnmount(() => window.removeEventListener('resize', resizeHandler))` |

The composable returns `{ isMobile, resizeHandler }` so the behaviour stays
unit-testable; the listener cleanup is the only addition and is required because
a composable must not outlive the component that used it.
