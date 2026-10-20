import { describe, it, expect, beforeEach, afterEach, vi } from 'vitest'
import { setActivePinia, createPinia } from 'pinia'
import { setToken, removeToken } from '@/utils/auth'

// End-to-end (in jsdom) integration of the migrated router guard, the Pinia
// stores and the axios interceptors.
// Covers business rules AUTH-03..AUTH-07, AUTH-10, AUTH-11, LIM-01..LIM-03
// and application flows A1, A4, A5, A6, A7, H1, H2, H3.
describe('router guard integration (permission.js)', () => {
  let router
  beforeEach(async () => {
    vi.resetModules()
    setActivePinia(createPinia())
    removeToken()
    router = (await import('@/router')).default
    await import('@/permission')
  })
  afterEach(() => removeToken())

  it('A4/AUTH-03: an unauthenticated user is redirected to /login with the original path', async () => {
    await router.push('/campusset/list')
    expect(router.currentRoute.value.path).toBe('/login')
    expect(router.currentRoute.value.query.redirect).toBe('/campusset/list')
  })

  it('AUTH-03: /login itself is whitelisted for unauthenticated users', async () => {
    await router.push('/login')
    expect(router.currentRoute.value.path).toBe('/login')
  })

  it('A5/AUTH-04: an authenticated user asking for /login is sent to the dashboard', async () => {
    setToken('admin')
    await router.push('/login')
    expect(router.currentRoute.value.path).toBe('/dashboard')
  })

  it('A1/AUTH-06: the guard loads the profile before completing the first navigation', async () => {
    setToken('admin')
    const { useUserStore } = await import('@/stores/user')
    const store = useUserStore()
    expect(store.roles.length).toBe(0)
    await router.push('/cmn/list')
    expect(store.roles).toBe('admin')
    expect(store.name).toBe('admin')
    expect(router.currentRoute.value.path).toBe('/cmn/list')
  })

  it('AUTH-06: the profile is not reloaded on a second navigation', async () => {
    setToken('admin')
    const { useUserStore } = await import('@/stores/user')
    const store = useUserStore()
    await router.push('/cmn/list')
    const spy = vi.spyOn(store, 'GetInfo')
    await router.push('/campusset/add')
    expect(spy).not.toHaveBeenCalled()
    expect(router.currentRoute.value.path).toBe('/campusset/add')
  })

  it('AUTH-07: a failing profile load logs the user out and routes away to /', async () => {
    setToken('admin')
    const { useUserStore } = await import('@/stores/user')
    const store = useUserStore()
    vi.spyOn(store, 'GetInfo').mockRejectedValue(new Error('boom'))
    await router.push('/cmn/list')
    expect(store.token).toBe('')
    // the guard sends the user to '/', which (now token-less) falls through to
    // the login screen exactly as the Vue 2 next({ path: '/' }) chain did
    expect(router.currentRoute.value.path).toBe('/login')
  })

  it('B5: an unknown route still resolves to the 404 page for an authenticated user', async () => {
    setToken('admin')
    await router.push('/nope/nope')
    expect(router.currentRoute.value.path).toBe('/404')
  })
})

describe('axios service integration (utils/request.js)', () => {
  let service, store, created
  beforeEach(async () => {
    vi.resetModules()
    setActivePinia(createPinia())
    removeToken()
    created = undefined
    vi.doMock('axios', () => ({
      default: {
        create: cfg => {
          created = cfg
          const s = { interceptors: { request: { use: (ok, err) => { s._req = [ok, err] } }, response: { use: (ok, err) => { s._res = [ok, err] } } } }
          return s
        }
      }
    }))
    service = (await import('@/utils/request')).default
    store = (await import('@/stores/user')).useUserStore()
  })

  it('LIM-01/LIM-10: the instance uses the VITE_BASE_API base url and a 5s timeout', () => {
    expect(created.timeout).toBe(5000)
    expect(created.baseURL).toBe(import.meta.env.VITE_BASE_API)
  })

  it('AUTH-10: the token header is attached only when the store holds a token', () => {
    const [onRequest] = service._req
    let cfg = onRequest({ headers: {} })
    expect(cfg.headers.token).toBeUndefined()
    setToken('admin')
    store.token = 'admin'
    cfg = onRequest({ headers: {} })
    expect(cfg.headers.token).toBe('admin')
  })

  it('H1/LIM-02: a body code other than 200 rejects the promise', async () => {
    const [onResponse] = service._res
    await expect(onResponse({ data: { code: 500, message: 'nope' } })).rejects.toBe('error')
  })

  it('a body code of 200 resolves with the response body', async () => {
    const [onResponse] = service._res
    const body = { code: 200, data: { records: [] } }
    expect(onResponse({ data: body })).toEqual(body)
  })

  it('H2/LIM-03: a transport error is surfaced and rejected', async () => {
    const [, onError] = service._res
    await expect(onError(new Error('Network Error'))).rejects.toThrow('Network Error')
  })

  it('AUTH-11/H3: session codes 50008, 50012 and 50014 are handled as logouts', async () => {
    const [onResponse] = service._res
    for (const code of [50008, 50012, 50014]) {
      await expect(onResponse({ data: { code, message: 'expired' } })).rejects.toBe('error')
    }
  })
})

describe('api modules', () => {
  beforeEach(() => {
    vi.resetModules()
    setActivePinia(createPinia())
  })

  it('AUTH-12: every admin endpoint is served under the /admin prefix', async () => {
    const calls = []
    vi.doMock('@/utils/request', () => ({ default: cfg => { calls.push(cfg); return Promise.resolve({ code: 200, data: {} }) } }))
    const campus = (await import('@/api/campus')).default
    const campusset = (await import('@/api/campusset')).default
    const dict = (await import('@/api/dict')).default
    campus.getCampusList(1, 10, {})
    campus.findByDictCode('State')
    campus.findChildId(1)
    campus.updateStatus(1, 0)
    campus.getCampusById(1)
    campus.getDeptByCampusCode('1000_0')
    campus.getScheduleRule(1, 10, '1000_0', 'd1')
    campus.getScheduleDetail('1000_0', 'd1', '2022-05-03')
    campusset.getCampusSetList(1, 5, {})
    campusset.deleteCampusSet(1)
    campusset.batchRemoveCampusSet([1])
    campusset.lockCampusSet(1, 0)
    campusset.saveCampusSet({})
    campusset.getCampusSet(1)
    campusset.updateCampusSet({})
    dict.dictList(1)
    expect(calls.length).toBe(16)
    for (const c of calls) expect(c.url).toContain('admin/')
  })

  it('FLOW-02/FLOW-03/FLOW-04: campus-set mutations use the documented verbs', async () => {
    const calls = []
    vi.doMock('@/utils/request', () => ({ default: cfg => { calls.push(cfg); return Promise.resolve({ code: 200, data: {} }) } }))
    const campusset = (await import('@/api/campusset')).default
    campusset.deleteCampusSet(7)
    campusset.batchRemoveCampusSet([1, 2])
    campusset.lockCampusSet(7, 0)
    campusset.saveCampusSet({ campusname: 'x' })
    expect(calls[0]).toMatchObject({ method: 'delete', url: '/admin/campus/campusSet/7' })
    expect(calls[1]).toMatchObject({ method: 'delete', url: '/admin/campus/campusSet/batchRemove', data: [1, 2] })
    expect(calls[2]).toMatchObject({ method: 'put', url: '/admin/campus/campusSet/lockCampusSet/7/0' })
    expect(calls[3]).toMatchObject({ method: 'post', url: '/admin/campus/campusSet/saveCampusSet' })
  })

  it('DATA-04/DATA-06: the dictionary and state lookups use the documented endpoints', async () => {
    const calls = []
    vi.doMock('@/utils/request', () => ({ default: cfg => { calls.push(cfg); return Promise.resolve({ code: 200, data: {} }) } }))
    const dict = (await import('@/api/dict')).default
    const campus = (await import('@/api/campus')).default
    dict.dictList(1)
    campus.findByDictCode('State')
    expect(calls[0].url).toBe('/admin/cmn/dict/findChildData/1')
    expect(calls[1].url).toBe('/admin/cmn/dict/findByDictCode/State')
  })
})
