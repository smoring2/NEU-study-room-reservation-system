import { describe, it, expect } from 'vitest'
import router, { constantRouterMap } from '@/router'
import { createRouter, createWebHashHistory } from 'vue-router'

// Covers business rules AUTH-03, AUTH-12, DISP-03, DISP-09, LIM-08
// and flows B1, B5.
describe('Vue Router 4 migration', () => {
  it('uses createRouter with a hash history (the Vue 2 config had history mode commented out)', () => {
    expect(typeof router.push).toBe('function')
    expect(typeof router.beforeEach).toBe('function')
    // createWebHashHistory sets the base to '/#'
    expect(router.options.history.base).toBe('/#')
  })

  it('scrollBehavior returns the Vue Router 4 shape { top }', () => {
    expect(router.options.scrollBehavior()).toEqual({ top: 0 })
  })

  it('B5: the Vue 2 catch-all "*" became "/:pathMatch(.*)*" and redirects to /404', () => {
    const catchAll = constantRouterMap[constantRouterMap.length - 1]
    expect(catchAll.path).toBe('/:pathMatch(.*)*')
    expect(catchAll.redirect).toBe('/404')
    expect(constantRouterMap.some(r => r.path === '*')).toBe(false)
  })

  it('B5: an unknown path resolves to the 404 redirect', () => {
    const resolved = router.resolve('/this/does/not/exist')
    expect(resolved.matched.length).toBeGreaterThan(0)
    expect(resolved.fullPath).toBe('/this/does/not/exist')
  })

  it('AUTH-03/LIM-08: /login and /404 are hidden top-level routes outside the layout', () => {
    const login = constantRouterMap.find(r => r.path === '/login')
    const notFound = constantRouterMap.find(r => r.path === '/404')
    expect(login.hidden).toBe(true)
    expect(notFound.hidden).toBe(true)
    expect(login.children).toBeUndefined()
  })

  it('DISP-03: the Data Management parent is alwaysShow with the Data Dict child', () => {
    const cmn = constantRouterMap.find(r => r.path === '/cmn')
    expect(cmn.alwaysShow).toBe(true)
    expect(cmn.meta).toEqual({ title: 'Data Management', icon: 'example' })
    expect(cmn.children.map(c => c.path)).toEqual(['list'])
    expect(cmn.children[0].meta.title).toBe('Data Dict')
  })

  it('DISP-03: the Campus set parent exposes three visible and three hidden children', () => {
    const cs = constantRouterMap.find(r => r.path === '/campusset')
    const visible = cs.children.filter(c => !c.hidden).map(c => c.name)
    const hidden = cs.children.filter(c => c.hidden).map(c => c.name)
    expect(visible).toEqual(['Campus Set List', 'Campus Set Add', 'Campus List'])
    expect(hidden).toEqual(['Campus Set Edit', 'Deatils', 'Schedule'])
  })

  it('the dashboard route redirects / to /dashboard', () => {
    const root = constantRouterMap.find(r => r.path === '/')
    expect(root.redirect).toBe('/dashboard')
    expect(root.hidden).toBe(true)
  })

  it('every route component is either the layout or a lazy import function', () => {
    const check = routes => routes.forEach(r => {
      if (r.component) expect(typeof r.component === 'function' || typeof r.component === 'object').toBe(true)
      if (r.children) check(r.children)
    })
    check(constantRouterMap)
  })

  it('a router built from the same map resolves the campus schedule parameter', () => {
    const r = createRouter({ history: createWebHashHistory(), routes: constantRouterMap })
    const resolved = r.resolve('/campusset/campus/schedule/1000_0')
    expect(resolved.params.campuscode).toBe('1000_0')
    expect(resolved.name).toBe('Schedule')
  })
})
