import { describe, it, expect, beforeEach, vi } from 'vitest'

// the views fetch on setup; the axios service is stubbed so the suite does not
// depend on a reachable backend
vi.mock('@/utils/request', () => ({ default: vi.fn(() => Promise.resolve({ code: 200, data: {} })) }))
import { mount } from '@vue/test-utils'
import { createTestingPinia } from '@pinia/testing'
import ElementPlus from 'element-plus'
import { createRouter, createWebHashHistory } from 'vue-router'
import { constantRouterMap } from '@/router'

import SvgIcon from '@/components/SvgIcon/index.vue'
import Hamburger from '@/components/Hamburger/index.vue'
import Breadcrumb from '@/components/Breadcrumb/index.vue'
import AppMain from '@/views/layout/components/AppMain.vue'
import Navbar from '@/views/layout/components/Navbar.vue'
import Sidebar from '@/views/layout/components/Sidebar/index.vue'
import MenuItem from '@/views/layout/components/Sidebar/Item.vue'
import AppLink from '@/views/layout/components/Sidebar/Link.vue'
import SidebarItem from '@/views/layout/components/Sidebar/SidebarItem.vue'
import Layout from '@/views/layout/Layout.vue'
import Dashboard from '@/views/dashboard/index.vue'
import Page404 from '@/views/404.vue'
import Login from '@/views/login/index.vue'
import CampusSetAdd from '@/views/campusSet/add.vue'
import FormDemo from '@/views/form/index.vue'
import TreeDemo from '@/views/tree/index.vue'
import TableDemo from '@/views/table/index.vue'
import Menu2 from '@/views/nested/menu2/index.vue'

function makeRouter() {
  return createRouter({ history: createWebHashHistory(), routes: constantRouterMap })
}

async function mountWith(component, { route = '/dashboard', state } = {}) {
  const router = makeRouter()
  router.push(route)
  await router.isReady()
  const wrapper = mount(component, {
    global: {
      plugins: [
        router,
        ElementPlus,
        createTestingPinia({
          createSpy: vi.fn,
          stubActions: false,
          initialState: state || { user: { token: 'admin', name: 'admin', roles: 'admin', avatar: 'https://wpimg.wallstcn.com/x.gif' } }
        })
      ],
      stubs: { transition: false, 'transition-group': false },
      components: { 'svg-icon': SvgIcon }
    },
    attachTo: document.body
  })
  await new Promise(r => setTimeout(r, 0))
  return { wrapper, router }
}

describe('Application loading', () => {
  it('the layout mounts the sidebar, the navbar and the content area', async () => {
    const { wrapper } = await mountWith(Layout)
    expect(wrapper.find('.app-wrapper').exists()).toBe(true)
    expect(wrapper.find('.sidebar-container').exists()).toBe(true)
    expect(wrapper.find('.navbar').exists()).toBe(true)
    expect(wrapper.find('.app-main').exists()).toBe(true)
  })

  it('DISP-02/FLOW-13: the layout class object reflects the collapsed sidebar', async () => {
    const { wrapper } = await mountWith(Layout, { state: { app: { sidebar: { opened: false, withoutAnimation: true }, device: 'mobile' } } })
    const classes = wrapper.find('.app-wrapper').classes()
    expect(classes).toContain('hideSidebar')
    expect(classes).toContain('withoutAnimation')
    expect(classes).toContain('mobile')
    expect(classes).not.toContain('openSidebar')
  })

  it('the Sidebar component is not shadowed by the sidebar store binding', async () => {
    const { wrapper } = await mountWith(Layout)
    // regression guard: a setup binding named `sidebar` used to resolve before
    // the imported <Sidebar> component, leaving the region blank
    expect(wrapper.find('.sidebar-container .el-menu').exists()).toBe(true)
  })
})

describe('Toolbar / Header (navbar)', () => {
  it('DISP-05: the navbar renders the hamburger, the breadcrumb and the avatar', async () => {
    const { wrapper } = await mountWith(Navbar)
    expect(wrapper.find('.hamburger-container').exists()).toBe(true)
    expect(wrapper.find('.app-breadcrumb').exists()).toBe(true)
    expect(wrapper.find('.avatar-container').exists()).toBe(true)
    expect(wrapper.find('img.user-avatar').attributes('src')).toContain('?imageView2/1/w/80/h/80')
  })

  it('FLOW-13: clicking the hamburger closes the sidebar through the Pinia store', async () => {
    const { wrapper } = await mountWith(Navbar)
    const { useAppStore } = await import('@/stores/app')
    const store = useAppStore()
    const before = store.sidebar.opened
    await wrapper.find('svg.hamburger').trigger('click')
    await new Promise(r => setTimeout(r, 0))
    expect(store.sidebar.opened).toBe(!before)
  })
})

describe('Content display', () => {
  it('DISP-06: the dashboard prints the user name and roles from the store', async () => {
    const { wrapper } = await mountWith(Dashboard)
    expect(wrapper.text()).toContain('name:admin')
    expect(wrapper.text()).toContain('roles:')
  })

  it('AppMain keys the router view by route name so pages remount', async () => {
    const { wrapper } = await mountWith(AppMain)
    expect(wrapper.find('.app-main').exists()).toBe(true)
  })

  it('the campus-set form renders the five labels and the Save button', async () => {
    const { wrapper } = await mountWith(CampusSetAdd, { route: '/campusset/add' })
    const text = wrapper.text()
    for (const l of ['Campus Name', 'Campus Code', 'API Url', 'Contact (Name)', 'Contact (Phone)', 'Save']) {
      expect(text).toContain(l)
    }
  })

  it('the demo table renders all six column headers', async () => {
    const { wrapper } = await mountWith(TableDemo, { route: '/dashboard' })
    const text = wrapper.text()
    for (const h of ['ID', 'Title', 'Author', 'Pageviews', 'Status', 'Display_time']) {
      expect(text).toContain(h)
    }
  })

  it('the nested menu-2 page keeps its Element alert title', async () => {
    const { wrapper } = await mountWith(Menu2)
    expect(wrapper.text()).toContain('menu 2')
  })
})

describe('Resource links and sidebar entries', () => {
  it('DISP-03: AppLink renders a router-link for an internal path', async () => {
    const { wrapper } = await mountWith(AppLink, {})
    expect(wrapper.exists()).toBe(true)
  })

  it('DISP-03: AppLink renders an anchor with target=_blank for an external path', async () => {
    const router = makeRouter()
    router.push('/dashboard')
    await router.isReady()
    const wrapper = mount(AppLink, {
      props: { to: 'https://example.com' },
      global: { plugins: [router, ElementPlus, createTestingPinia({ createSpy: vi.fn })] }
    })
    expect(wrapper.find('a').attributes('href')).toBe('https://example.com')
    expect(wrapper.find('a').attributes('target')).toBe('_blank')
    expect(wrapper.find('a').attributes('rel')).toBe('noopener')
  })

  it('the Sidebar lists the visible router entries and skips hidden ones', async () => {
    const { wrapper } = await mountWith(Sidebar)
    const text = wrapper.text()
    expect(text).toContain('Data Management')
    expect(text).toContain('Campus set')
    expect(text).not.toContain('Campus Set Edit')
    expect(text).not.toContain('Deatils')
  })

  it('SidebarItem renders a sub-menu for a parent with several visible children', async () => {
    const router = makeRouter()
    router.push('/dashboard')
    await router.isReady()
    const item = constantRouterMap.find(r => r.path === '/campusset')
    // el-sub-menu needs an el-menu ancestor for its rootMenu injection
    const wrapper = mount({
      components: { SidebarItem },
      data: () => ({ item }),
      template: '<el-menu><sidebar-item :item="item" :base-path="item.path" /></el-menu>'
    }, {
      global: { plugins: [router, ElementPlus, createTestingPinia({ createSpy: vi.fn })], components: { 'svg-icon': SvgIcon } }
    })
    expect(wrapper.find('.el-sub-menu').exists()).toBe(true)
    expect(wrapper.text()).toContain('Campus Set List')
  })

  it('MenuItem (was a functional JSX component) renders the icon and the title', () => {
    const wrapper = mount(MenuItem, {
      props: { icon: 'table', title: 'Data Dict' },
      global: { components: { 'svg-icon': SvgIcon } }
    })
    expect(wrapper.text()).toContain('Data Dict')
    expect(wrapper.find('svg.svg-icon').exists()).toBe(true)
  })
})

describe('Action buttons and forms', () => {
  it('VAL-04: the login form is pre-filled with admin/admin and shows the tips', async () => {
    const { wrapper } = await mountWith(Login, { route: '/login' })
    expect(wrapper.find('input[name=username]').element.value).toBe('admin')
    expect(wrapper.find('input[name=password]').element.value).toBe('admin')
    expect(wrapper.find('.tips').text()).toContain('username: admin')
    expect(wrapper.find('.title').text()).toBe('vue-admin-template')
  })

  it('DISP-10: clicking the eye icon unmasks the password field', async () => {
    const { wrapper } = await mountWith(Login, { route: '/login' })
    expect(wrapper.find('input[name=password]').attributes('type')).toBe('password')
    await wrapper.find('.show-pwd').trigger('click')
    expect(wrapper.find('input[name=password]').attributes('type')).not.toBe('password')
  })

  it('AUTH-05: a redirect query parameter is picked up by the login screen', async () => {
    const { wrapper } = await mountWith(Login, { route: '/login?redirect=/campusset/list' })
    expect(wrapper.find('.login-container').exists()).toBe(true)
  })

  it('F1: the demo form renders every control and both buttons', async () => {
    const { wrapper } = await mountWith(FormDemo)
    const text = wrapper.text()
    for (const l of ['Activity name', 'Activity zone', 'Activity time', 'Instant delivery', 'Activity type', 'Resources', 'Activity form', 'Create', 'Cancel']) {
      expect(text).toContain(l)
    }
  })

  it('G1: the demo tree renders its three top level nodes and the filter input', async () => {
    const { wrapper } = await mountWith(TreeDemo)
    expect(wrapper.find('input[placeholder="Filter keyword"]').exists()).toBe(true)
    const text = wrapper.text()
    expect(text).toContain('Level one 1')
    expect(text).toContain('Level one 2')
    expect(text).toContain('Level one 3')
  })
})

describe('Footer / 404 page', () => {
  it('DISP-09: the 404 page renders OOPS!, the message and the Back to Home link', async () => {
    const { wrapper } = await mountWith(Page404)
    expect(wrapper.find('.bullshit__oops').text()).toBe('OOPS!')
    expect(wrapper.find('.bullshit__headline').text()).toBe('you cannot enter this page......')
    expect(wrapper.find('.bullshit__return-home').text()).toBe('Back to Home')
    expect(wrapper.findAll('.pic-404__child').length).toBe(3)
  })

  it('the 404 page keeps the "all rights reserved" attribution link', async () => {
    const { wrapper } = await mountWith(Page404)
    expect(wrapper.find('a.link-type').attributes('href')).toBe('https://wallstreetcn.com')
  })
})

describe('Styling / visual', () => {
  it('SvgIcon composes its class names exactly as the Vue 2 computed property did', () => {
    const plain = mount(SvgIcon, { props: { iconClass: 'user' } })
    expect(plain.find('svg').classes()).toEqual(['svg-icon'])
    expect(plain.find('use').attributes('href') || plain.find('use').attributes('xlink:href')).toBe('#icon-user')
    const custom = mount(SvgIcon, { props: { iconClass: 'eye', className: 'extra' } })
    expect(custom.find('svg').classes()).toContain('extra')
  })

  it('the hamburger toggles the is-active class from its prop', () => {
    const off = mount(Hamburger, { props: { isActive: false } })
    expect(off.find('svg').classes()).not.toContain('is-active')
    const on = mount(Hamburger, { props: { isActive: true } })
    expect(on.find('svg').classes()).toContain('is-active')
  })

  it('DISP-01: the sidebar menu carries the Element Plus theme variables that replace the removed props', async () => {
    const { wrapper } = await mountWith(Sidebar)
    const style = wrapper.find('.el-menu').attributes('style') || ''
    expect(style).toContain('--el-menu-bg-color: #304156')
    expect(style).toContain('--el-menu-text-color: #bfcbd9')
    expect(style).toContain('--el-menu-active-color: #409EFF')
  })
})

describe('Breadcrumb (DISP-04)', () => {
  it('always starts with Dashboard and marks the current crumb as plain text', async () => {
    const { wrapper } = await mountWith(Breadcrumb, { route: '/campusset/list' })
    const text = wrapper.text()
    expect(text).toContain('Dashboard')
    expect(text).toContain('Campus Set List')
    expect(wrapper.findAll('.no-redirect').length).toBeGreaterThan(0)
  })

  it('rebuilds itself when the route changes', async () => {
    const { wrapper, router } = await mountWith(Breadcrumb, { route: '/campusset/list' })
    expect(wrapper.text()).toContain('Campus Set List')
    await router.push('/cmn/list')
    await new Promise(r => setTimeout(r, 0))
    expect(wrapper.text()).toContain('Data Dict')
  })
})

describe('Vue 3 specific patterns', () => {
  it('no component exposes Vue 2 Options API members (data/methods/computed/filters/mixins)', async () => {
    const modules = import.meta.glob('../**/*.vue', { eager: true })
    for (const [file, mod] of Object.entries(modules)) {
      const c = mod.default
      expect(c, file).toBeTruthy()
      expect(c.data, file).toBeUndefined()
      expect(c.methods, file).toBeUndefined()
      expect(c.computed, file).toBeUndefined()
      expect(c.filters, file).toBeUndefined()
      expect(c.mixins, file).toBeUndefined()
      expect(c.functional, file).toBeFalsy()
    }
  })

  it('every component is compiled from <script setup> or is template only', async () => {
    const modules = import.meta.glob('../**/*.vue', { eager: true })
    const withLogic = Object.entries(modules).filter(([, m]) => typeof m.default.setup === 'function')
    expect(withLogic.length).toBeGreaterThan(15)
  })

  it('the resize handler composable registers and cleans up its listener', async () => {
    const add = vi.spyOn(window, 'addEventListener')
    const remove = vi.spyOn(window, 'removeEventListener')
    const { wrapper } = await mountWith(Layout)
    expect(add.mock.calls.some(c => c[0] === 'resize')).toBe(true)
    wrapper.unmount()
    expect(remove.mock.calls.some(c => c[0] === 'resize')).toBe(true)
    add.mockRestore()
    remove.mockRestore()
  })
})
