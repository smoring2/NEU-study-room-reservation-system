import { describe, it, expect, beforeEach, vi } from 'vitest'
import { setActivePinia, createPinia } from 'pinia'

vi.mock('@/utils/request', () => ({
  default: vi.fn(() => Promise.resolve({ code: 200, data: {} }))
}))

import request from '@/utils/request'
import { login, getInfo, logout } from '@/api/login'
import { parseTime, formatTime } from '@/utils'

// Covers the login API module (flow A1/A5/A7), the remaining date helpers and
// the lazily imported route components of the Vue Router 4 configuration.
describe('login api module', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
    request.mockClear()
  })

  it('A1: login() posts the credentials to /user/login', async () => {
    await login('admin', '111111')
    expect(request).toHaveBeenCalledWith({
      url: '/user/login',
      method: 'post',
      data: { username: 'admin', password: '111111' }
    })
  })

  it('A5: getInfo() requests /user/info with the token as a query parameter', async () => {
    await getInfo('admin')
    expect(request).toHaveBeenCalledWith({
      url: '/user/info',
      method: 'get',
      params: { token: 'admin' }
    })
  })

  it('A7: logout() posts to /user/logout', async () => {
    await logout()
    expect(request).toHaveBeenCalledWith({ url: '/user/logout', method: 'post' })
  })
})

describe('date helpers (src/utils/index.js)', () => {
  it('parseTime returns null when called without arguments', () => {
    expect(parseTime()).toBeNull()
  })

  it('parseTime accepts a Date object and a custom format', () => {
    const date = new Date(2020, 0, 2, 3, 4, 5)
    expect(parseTime(date, '{y}-{m}-{d} {h}:{i}:{s}')).toBe('2020-01-02 03:04:05')
    expect(parseTime(date, '{a}')).toBe('Thursday')
  })

  it('parseTime expands a 10-digit unix timestamp', () => {
    expect(parseTime(1577934245, '{y}')).toBe('2020')
  })

  it('formatTime returns the relative wording used by the Vue 2 app', () => {
    const now = Date.now() / 1000
    expect(formatTime(now)).toBe('Just Now')
    expect(formatTime(now - 120)).toMatch(/minutes ago$/)
    expect(formatTime(now - 7200)).toMatch(/hours ago$/)
    expect(formatTime(now - 3600 * 30)).toBe('one day before')
  })

  it('formatTime falls back to parseTime when an option format is supplied', () => {
    const threeDaysAgo = Date.now() / 1000 - 3600 * 24 * 3
    expect(formatTime(threeDaysAgo, '{y}')).toMatch(/^\d{4}$/)
    expect(typeof formatTime(threeDaysAgo)).toBe('string')
  })
})

describe('router lazy component loaders', () => {
  it('every route component factory resolves to a Vue 3 component', async () => {
    const router = (await import('@/router')).default
    const factories = []
    const walk = routes => {
      routes.forEach(route => {
        if (typeof route.component === 'function') factories.push(route.component)
        if (route.children) walk(route.children)
      })
    }
    walk(router.options.routes)
    expect(factories.length).toBeGreaterThan(5)
    const resolved = await Promise.all(factories.map(f => f()))
    resolved.forEach(mod => expect(mod.default).toBeTruthy())
  })
})
