import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'
import { setActivePinia, createPinia } from 'pinia'
import Cookies from 'js-cookie'
import { useAppStore } from '@/stores/app'
import { useUserStore } from '@/stores/user'
import { getToken, setToken, removeToken } from '@/utils/auth'

// Covers: AUTH-01, AUTH-02, AUTH-08, AUTH-09, FLOW-13, DISP-06 and flows A1, A6, B3.
describe('Pinia stores (Vuex -> Pinia migration)', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
    removeToken()
    Cookies.remove('sidebarStatus')
  })
  afterEach(() => {
    removeToken()
    Cookies.remove('sidebarStatus')
  })

  describe('user store', () => {
    it('AUTH-01/AUTH-02: Login writes the token from the response body into the store and the Admin-Token cookie', async () => {
      const store = useUserStore()
      await store.Login({ username: 'admin', password: 'admin' })
      expect(store.token).toBe('admin')
      expect(getToken()).toBe('admin')
      expect(Cookies.get('Admin-Token')).toBe('admin')
    })

    it('all actions return promises so Vuex-style .then() call sites keep working', async () => {
      const store = useUserStore()
      expect(typeof store.Login({}).then).toBe('function')
      expect(typeof store.GetInfo().then).toBe('function')
      expect(typeof store.LogOut().then).toBe('function')
      expect(typeof store.FedLogOut().then).toBe('function')
    })

    it('AUTH-08/DISP-06: GetInfo fills name, roles and avatar', async () => {
      const store = useUserStore()
      await store.GetInfo()
      expect(store.name).toBe('admin')
      expect(store.roles).toBe('admin')
      expect(store.avatar).toContain('https://wpimg.wallstcn.com/')
    })

    it('AUTH-09: LogOut clears the token, the roles and the cookie', async () => {
      const store = useUserStore()
      await store.Login({})
      await store.GetInfo()
      await store.LogOut()
      expect(store.token).toBe('')
      expect(store.roles).toEqual([])
      expect(getToken()).toBeUndefined()
    })

    it('AUTH-07: FedLogOut clears the token and the cookie but not the profile', async () => {
      const store = useUserStore()
      setToken('admin')
      await store.FedLogOut()
      expect(store.token).toBe('')
      expect(getToken()).toBeUndefined()
    })

    it('the store id is namespaced as "user"', () => {
      expect(useUserStore().$id).toBe('user')
    })
  })

  describe('app store', () => {
    it('FLOW-13: toggleSideBar closes an open sidebar and remembers it in the cookie', async () => {
      const store = useAppStore()
      expect(store.sidebar.opened).toBe(true)
      await store.toggleSideBar()
      expect(store.sidebar.opened).toBe(false)
      expect(Cookies.get('sidebarStatus')).toBe('1')
      expect(store.sidebar.withoutAnimation).toBe(false)
    })

    it('FLOW-13: toggling twice reopens the sidebar and resets the cookie', async () => {
      const store = useAppStore()
      await store.toggleSideBar()
      await store.toggleSideBar()
      expect(store.sidebar.opened).toBe(true)
      expect(Cookies.get('sidebarStatus')).toBe('0')
    })

    it('FLOW-14: closeSideBar closes the sidebar and honours withoutAnimation', async () => {
      const store = useAppStore()
      await store.closeSideBar({ withoutAnimation: true })
      expect(store.sidebar.opened).toBe(false)
      expect(store.sidebar.withoutAnimation).toBe(true)
      expect(Cookies.get('sidebarStatus')).toBe('1')
    })

    it('FLOW-14: toggleDevice switches the device mode', async () => {
      const store = useAppStore()
      expect(store.device).toBe('desktop')
      await store.toggleDevice('mobile')
      expect(store.device).toBe('mobile')
    })

    it('the sidebar starts collapsed when the sidebarStatus cookie is 1', () => {
      Cookies.set('sidebarStatus', 1)
      setActivePinia(createPinia())
      expect(useAppStore().sidebar.opened).toBe(false)
    })
  })
})
