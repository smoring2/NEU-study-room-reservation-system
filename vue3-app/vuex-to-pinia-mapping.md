# Vuex 3 -> Pinia 2 mapping

The Vue 2 store (`src/store/index.js` + `src/store/getters.js` + two modules) became
two Pinia **setup stores**. `src/store/index.js` and `src/store/getters.js` have no
Pinia equivalent: the root store and the global getter map disappear, because each
Pinia store is namespaced by its own id and exposes its state directly.

| Vue 2 (Vuex) | Vue 3 (Pinia) |
| --- | --- |
| `new Vuex.Store({ modules: { app, user }, getters })` | `createPinia()` in `src/main.js` + one `defineStore` per module |
| `src/store/modules/app.js` | `src/stores/app.js` - `useAppStore`, id `app` |
| `src/store/modules/user.js` | `src/stores/user.js` - `useUserStore`, id `user` |
| `state: { sidebar, device }` | `const sidebar = ref({...})`, `const device = ref('desktop')` |
| `state: { token, name, avatar, roles }` | `ref(getToken())`, `ref('')`, `ref('')`, `ref([])` |
| `getters.sidebar` / `.device` | `useAppStore().sidebar` / `.device` |
| `getters.token` / `.avatar` / `.name` / `.roles` | `useUserStore().token` / `.avatar` / `.name` / `.roles` |
| `mutations.TOGGLE_SIDEBAR` | folded into the `toggleSideBar` action (Pinia has no mutations) |
| `mutations.CLOSE_SIDEBAR` | folded into `closeSideBar` |
| `mutations.TOGGLE_DEVICE` | folded into `toggleDevice` |
| `mutations.SET_TOKEN/SET_NAME/SET_AVATAR/SET_ROLES` | direct `ref.value = ...` assignments inside the actions |
| `actions.ToggleSideBar` | `toggleSideBar()` |
| `actions.CloseSideBar` | `closeSideBar({ withoutAnimation })` |
| `actions.ToggleDevice` | `toggleDevice(device)` |
| `actions.Login` | `Login(userInfo)` (name kept - it is called from the login screen) |
| `actions.GetInfo` | `GetInfo()` |
| `actions.LogOut` | `LogOut()` |
| `actions.FedLogOut` | `FedLogOut()` |
| `...mapGetters(['sidebar','avatar'])` | `const { sidebar } = storeToRefs(useAppStore())` / `const { avatar } = storeToRefs(useUserStore())` |
| `this.$store.state.app.sidebar` | `storeToRefs(useAppStore()).sidebar` |
| `this.$store.dispatch('ToggleSideBar')` | `useAppStore().toggleSideBar()` |
| `this.$store.dispatch('CloseSideBar', { withoutAnimation: false })` | `useAppStore().closeSideBar({ withoutAnimation: false })` |
| `this.$store.dispatch('Login', form).then(...)` | `useUserStore().Login(form).then(...)` |
| `this.$store.dispatch('LogOut').then(...)` | `useUserStore().LogOut().then(...)` |
| `store.getters.roles.length === 0` (guard) | `useUserStore().roles.length === 0` |
| `store.dispatch('FedLogOut')` (interceptor) | `useUserStore().FedLogOut()` resolved lazily inside the interceptor |

## Behavioural notes

1. **Every action is declared `async`.** `store.dispatch()` in Vuex always wrapped
   the return value in a Promise, so the existing `.then()` call sites
   (`Login(...).then()`, `LogOut().then()`, `FedLogOut().then()`) keep working only
   if the Pinia actions return a Promise. A non-async action returning `undefined`
   would make `.then` throw a TypeError at runtime.
2. **Token semantics are unchanged**: the token is read from the response *body*
   property `data.token`, persisted in the cookie `Admin-Token`
   (`src/utils/auth.js`) and sent in the request header named `token`. The source's
   hard-coded stub (`{ token: 'admin' }`) and the commented-out real API calls are
   preserved verbatim.
3. `GetInfo` previously called an undefined `reject(...)` when roles were empty;
   that became `throw new Error(...)`, which the guard's `catch` handles exactly as
   the Vuex promise rejection did.
4. `src/utils/request.js` resolves `useUserStore()` **inside** the interceptor
   callbacks, so the module can be imported before `createPinia()` is installed.
5. `roles` holds the string `'admin'` in the source stub (not an array); this is
   preserved, so the dashboard still renders one `<span>` per character exactly as
   the Vue 2 application did.
