// Authoritative UI behaviour test cases extracted from the Vue 2 application.
// Each case is executed in a real chromium browser by scripts/run-ui-tests.mjs,
// first against the Vue 2 app (-> ui_tests_vue2.md) and then against the
// migrated Vue 3 app (-> ui_tests_vue3.md).
export const cases = [
  // ---------------------------------------------------------------- login
  {
    id: 'LOGIN-01', group: 'Login',
    action: 'Open the application without an Admin-Token cookie',
    expected: 'The login screen is shown and the URL carries the /login route with a redirect query',
    auth: false, route: '/#/dashboard',
    async run(p, expect) {
      await expect(p.url().includes('/login'), 'url contains /login, got ' + p.url())
      await expect(await p.locator('.login-container').isVisible(), '.login-container visible')
      await expect(p.url().includes('redirect='), 'url carries redirect query')
    }
  },
  {
    id: 'LOGIN-02', group: 'Login',
    action: 'Inspect the login form on load',
    expected: 'The title "vue-admin-template" is shown and username/password are pre-filled with "admin"',
    auth: false, route: '/#/login',
    async run(p, expect) {
      await expect((await p.locator('.title').innerText()).trim() === 'vue-admin-template', 'title text')
      await expect(await p.inputValue('input[name=username]') === 'admin', 'username prefilled')
      await expect(await p.inputValue('input[name=password]') === 'admin', 'password prefilled')
    }
  },
  {
    id: 'LOGIN-03', group: 'Login',
    action: 'Read the tips line under the login form',
    expected: 'It advertises "username: admin" and "password: admin"',
    auth: false, route: '/#/login',
    async run(p, expect) {
      const t = await p.locator('.tips').innerText()
      await expect(t.includes('username: admin') && t.includes('password: admin'), 'tips text, got ' + JSON.stringify(t))
    }
  },
  {
    id: 'LOGIN-04', group: 'Login',
    action: 'Click the eye icon next to the password field',
    expected: 'The password input switches away from type "password" so the value becomes readable',
    auth: false, route: '/#/login',
    async run(p, expect) {
      await expect(await p.getAttribute('input[name=password]', 'type') === 'password', 'initially masked')
      await p.click('.show-pwd')
      await p.waitForTimeout(300)
      await expect(await p.getAttribute('input[name=password]', 'type') !== 'password', 'unmasked after click')
    }
  },
  {
    id: 'LOGIN-05', group: 'Login',
    action: 'Enter the username "nobody" and submit the login form',
    expected: 'The validation error "Please enter correct username" is shown and the route stays on /login',
    auth: false, route: '/#/login',
    async run(p, expect) {
      await p.fill('input[name=username]', 'nobody')
      await p.click('.el-button--primary')
      await p.waitForTimeout(800)
      const err = await p.locator('.el-form-item__error').first().innerText()
      await expect(err.includes('Please enter correct username'), 'error text, got ' + JSON.stringify(err))
      await expect(p.url().includes('/login'), 'still on /login')
    }
  },
  {
    id: 'LOGIN-06', group: 'Login',
    action: 'Enter a password shorter than 5 characters and submit the login form',
    expected: 'The validation error "Password cannot be less than 5 characters" is shown',
    auth: false, route: '/#/login',
    async run(p, expect) {
      await p.fill('input[name=password]', 'abc')
      await p.click('.el-button--primary')
      await p.waitForTimeout(800)
      const errs = await p.locator('.el-form-item__error').allInnerTexts()
      await expect(errs.join('|').includes('Password cannot be less than 5 characters'), 'error text, got ' + JSON.stringify(errs))
    }
  },
  {
    id: 'LOGIN-07', group: 'Login',
    action: 'Submit the pre-filled login form',
    expected: 'The Admin-Token cookie is written and the dashboard is shown inside the layout shell',
    auth: false, route: '/#/login',
    async run(p, expect, ctx) {
      await p.click('.el-button--primary')
      await p.waitForTimeout(1500)
      const cookies = await ctx.cookies()
      await expect(cookies.some(c => c.name === 'Admin-Token' && c.value === 'admin'), 'Admin-Token cookie set')
      await expect(p.url().includes('/dashboard'), 'navigated to dashboard, got ' + p.url())
      await expect(await p.locator('.sidebar-container').isVisible(), 'sidebar visible')
    }
  },
  {
    id: 'LOGIN-08', group: 'Login',
    action: 'Open /#/login while holding a valid Admin-Token cookie',
    expected: 'The guard redirects away from the login screen to the dashboard',
    auth: true, route: '/#/login',
    async run(p, expect) {
      await expect(!p.url().includes('/login'), 'redirected away from /login, got ' + p.url())
      await expect(await p.locator('.navbar').isVisible(), 'navbar visible')
    }
  },

  // -------------------------------------------------------------- dashboard
  {
    id: 'DASH-01', group: 'Dashboard',
    action: 'Open the dashboard as the authenticated admin',
    expected: 'The page prints "name:admin" and a roles line',
    auth: true, route: '/#/dashboard',
    async run(p, expect) {
      const t = await p.locator('.dashboard-container').innerText()
      await expect(t.includes('name:admin'), 'name line, got ' + JSON.stringify(t))
      await expect(t.includes('roles:'), 'roles line')
    }
  },
  {
    id: 'DASH-02', group: 'Dashboard',
    action: 'Inspect the layout shell on the dashboard',
    expected: 'A 180px dark sidebar (#304156) and a 50px navbar are rendered',
    auth: true, route: '/#/dashboard',
    async run(p, expect) {
      const sb = await p.locator('.sidebar-container').boundingBox()
      await expect(Math.round(sb.width) === 180, 'sidebar width 180, got ' + sb.width)
      const nb = await p.locator('.navbar').boundingBox()
      await expect(Math.round(nb.height) === 50, 'navbar height 50, got ' + nb.height)
      const bg = await p.locator('.sidebar-container .el-menu').first().evaluate(el => getComputedStyle(el).backgroundColor)
      await expect(bg === 'rgb(48, 65, 86)', 'menu background #304156, got ' + bg)
    }
  },

  // ---------------------------------------------------------------- navbar
  {
    id: 'NAV-01', group: 'Navbar',
    action: 'Click the hamburger icon in the navbar',
    expected: 'The sidebar collapses to 36px and the sidebarStatus cookie becomes 1',
    auth: true, route: '/#/dashboard',
    async run(p, expect, ctx) {
      await p.click('.hamburger-container svg')
      await p.waitForTimeout(800)
      const sb = await p.locator('.sidebar-container').boundingBox()
      await expect(Math.round(sb.width) === 36, 'sidebar width 36, got ' + sb.width)
      const cookies = await ctx.cookies()
      await expect(cookies.some(c => c.name === 'sidebarStatus' && c.value === '1'), 'sidebarStatus cookie = 1')
    }
  },
  {
    id: 'NAV-02', group: 'Navbar',
    action: 'Click the hamburger icon twice',
    expected: 'The sidebar collapses and then expands back to 180px',
    auth: true, route: '/#/dashboard',
    async run(p, expect) {
      await p.click('.hamburger-container svg')
      await p.waitForTimeout(700)
      await p.click('.hamburger-container svg')
      await p.waitForTimeout(700)
      const sb = await p.locator('.sidebar-container').boundingBox()
      await expect(Math.round(sb.width) === 180, 'sidebar width back to 180, got ' + sb.width)
    }
  },
  {
    id: 'NAV-03', group: 'Navbar',
    action: 'Click the avatar in the navbar',
    expected: 'A dropdown opens offering "Home" and "LogOut"',
    auth: true, route: '/#/dashboard',
    async run(p, expect) {
      await p.click('.avatar-wrapper')
      await p.waitForTimeout(700)
      const t = await p.locator('.user-dropdown').innerText()
      await expect(t.includes('Home'), 'Home item, got ' + JSON.stringify(t))
      await expect(t.includes('LogOut'), 'LogOut item')
    }
  },
  {
    id: 'NAV-04', group: 'Navbar',
    action: 'Open the avatar dropdown and click "LogOut"',
    expected: 'The Admin-Token cookie is removed and the login screen is shown again',
    auth: true, route: '/#/dashboard',
    async run(p, expect, ctx) {
      await p.click('.avatar-wrapper')
      await p.waitForTimeout(600)
      await p.getByText('LogOut', { exact: true }).click()
      await p.waitForTimeout(2000)
      const cookies = await ctx.cookies()
      await expect(!cookies.some(c => c.name === 'Admin-Token' && c.value), 'Admin-Token cookie cleared')
      await expect(p.url().includes('/login'), 'back on the login screen, got ' + p.url())
    }
  },
  {
    id: 'NAV-05', group: 'Navbar',
    action: 'Open the avatar dropdown and click "Home"',
    expected: 'The application navigates to the dashboard',
    auth: true, route: '/#/cmn/list',
    async run(p, expect) {
      await p.click('.avatar-wrapper')
      await p.waitForTimeout(600)
      await p.getByText('Home', { exact: true }).click()
      await p.waitForTimeout(1200)
      await expect(p.url().includes('/dashboard'), 'navigated to dashboard, got ' + p.url())
    }
  },

  // --------------------------------------------------------------- sidebar
  {
    id: 'SIDE-01', group: 'Sidebar',
    action: 'Inspect the sidebar entries',
    expected: 'The sub-menus "Data Management" and "Campus set" are listed and hidden routes are not',
    auth: true, route: '/#/dashboard',
    async run(p, expect) {
      const t = await p.locator('.sidebar-container').innerText()
      await expect(t.includes('Data Management'), 'Data Management entry, got ' + JSON.stringify(t))
      await expect(t.includes('Campus set'), 'Campus set entry')
      await expect(!t.includes('Deatils'), 'hidden Deatils route not listed')
      await expect(!t.includes('Campus Set Edit'), 'hidden edit route not listed')
    }
  },
  {
    id: 'SIDE-02', group: 'Sidebar',
    action: 'Expand "Campus set" in the sidebar',
    expected: 'The children "Campus Set List", "Campus Set Add" and "Campus List" are shown',
    auth: true, route: '/#/dashboard',
    async run(p, expect) {
      await p.getByText('Campus set', { exact: true }).click()
      await p.waitForTimeout(800)
      const t = await p.locator('.sidebar-container').innerText()
      for (const item of ['Campus Set List', 'Campus Set Add', 'Campus List']) {
        await expect(t.includes(item), item + ' child entry, got ' + JSON.stringify(t))
      }
    }
  },
  {
    id: 'SIDE-03', group: 'Sidebar',
    action: 'Expand "Campus set" and click "Campus Set Add"',
    expected: 'The application navigates to /campusset/add and the add form is shown',
    auth: true, route: '/#/dashboard',
    async run(p, expect) {
      await p.getByText('Campus set', { exact: true }).click()
      await p.waitForTimeout(700)
      await p.getByText('Campus Set Add', { exact: true }).click()
      await p.waitForTimeout(1200)
      await expect(p.url().includes('/campusset/add'), 'url is /campusset/add, got ' + p.url())
      await expect((await p.locator('.app-container').innerText()).includes('Campus Set Add'), 'add screen rendered')
    }
  },
  {
    id: 'SIDE-04', group: 'Sidebar',
    action: 'Expand "Data Management" and click "Data Dict"',
    expected: 'The application navigates to /cmn/list and the dictionary toolbar is shown',
    auth: true, route: '/#/dashboard',
    async run(p, expect) {
      await p.getByText('Data Management', { exact: true }).click()
      await p.waitForTimeout(700)
      await p.getByText('Data Dict', { exact: true }).click()
      await p.waitForTimeout(1200)
      await expect(p.url().includes('/cmn/list'), 'url is /cmn/list, got ' + p.url())
      await expect(await p.locator('.el-toolbar').isVisible(), 'toolbar visible')
    }
  },

  // ------------------------------------------------------------ breadcrumb
  {
    id: 'CRUMB-01', group: 'Breadcrumb',
    action: 'Navigate to /#/campusset/list and read the breadcrumb',
    expected: 'The breadcrumb starts with "Dashboard" and ends with the plain-text current page',
    auth: true, route: '/#/campusset/list',
    async run(p, expect) {
      const items = await p.locator('.app-breadcrumb .el-breadcrumb__item').allInnerTexts()
      await expect(items.length >= 2, 'at least two crumbs, got ' + JSON.stringify(items))
      await expect(items[0].trim().startsWith('Dashboard'), 'first crumb Dashboard, got ' + JSON.stringify(items))
      await expect(await p.locator('.app-breadcrumb .no-redirect').count() >= 1, 'last crumb is plain text')
    }
  },
  {
    id: 'CRUMB-02', group: 'Breadcrumb',
    action: 'Navigate from /#/campusset/list to /#/cmn/list',
    expected: 'The breadcrumb updates to the new route',
    auth: true, route: '/#/campusset/list',
    async run(p, expect) {
      const before = (await p.locator('.app-breadcrumb').innerText()).trim()
      // navigate in-app (Vue Router 4 reacts to popstate, not to an external
      // location.hash assignment, so a hash-only goto is not a navigation)
      await p.getByText('Data Management', { exact: true }).click()
      await p.waitForTimeout(700)
      await p.getByText('Data Dict', { exact: true }).click()
      await p.waitForTimeout(1300)
      const after = (await p.locator('.app-breadcrumb').innerText()).trim()
      await expect(after !== before, 'breadcrumb changed from ' + JSON.stringify(before) + ' to ' + JSON.stringify(after))
      await expect(after.includes('Data'), 'breadcrumb mentions the Data route, got ' + JSON.stringify(after))
    }
  },

  // ------------------------------------------------------------- dictionary
  {
    id: 'DICT-01', group: 'Data Dictionary',
    action: 'Open /#/cmn/list',
    expected: 'The Export and Import toolbar buttons and the four table columns are shown',
    auth: true, route: '/#/cmn/list',
    async run(p, expect) {
      const t = await p.locator('.el-toolbar').innerText()
      await expect(t.includes('Export') && t.includes('Import'), 'toolbar buttons, got ' + JSON.stringify(t))
      const heads = (await p.locator('.el-table th').allInnerTexts()).join('|')
      for (const h of ['name', 'code', 'value', 'create_time']) {
        await expect(heads.includes(h), 'column ' + h + ', got ' + heads)
      }
    }
  },
  {
    id: 'DICT-02', group: 'Data Dictionary',
    action: 'Click the "Import" toolbar button',
    expected: 'The "Import" dialog opens with the upload button and the 500kb Excel tip',
    auth: true, route: '/#/cmn/list',
    async run(p, expect) {
      await p.getByText('Import', { exact: false }).first().click()
      await p.waitForTimeout(900)
      await expect(await p.locator('.el-dialog').isVisible(), 'dialog visible')
      const t = await p.locator('.el-dialog').innerText()
      await expect(t.includes('Click and upload'), 'upload button, got ' + JSON.stringify(t))
      await expect(t.includes('Excel files only and maximum size is 500kb'), 'tip text')
    }
  },
  {
    id: 'DICT-03', group: 'Data Dictionary',
    action: 'Open the Import dialog and click "Cancel"',
    expected: 'The dialog closes again',
    auth: true, route: '/#/cmn/list',
    async run(p, expect) {
      await p.getByText('Import', { exact: false }).first().click()
      await p.waitForTimeout(800)
      await p.locator('.el-dialog__footer').getByText('Cancel').click()
      await p.waitForTimeout(900)
      await expect(!(await p.locator('.el-dialog').isVisible()), 'dialog hidden')
    }
  },

  // ------------------------------------------------------------ campus set
  {
    id: 'CSET-01', group: 'Campus Set',
    action: 'Open /#/campusset/list',
    expected: 'The heading text, both search inputs, the batch delete button and the table columns are shown',
    auth: true, route: '/#/campusset/list',
    async run(p, expect) {
      const t = await p.locator('.app-container').innerText()
      await expect(t.includes('Campus Set List'), 'heading, got ' + JSON.stringify(t))
      await expect(t.includes('Delete All Chosen Records'), 'batch delete button')
      await expect(await p.locator('input[placeholder="Campus Name"]').count() === 1, 'campus name filter')
      await expect(await p.locator('input[placeholder="Campus Code"]').count() === 1, 'campus code filter')
      const heads = (await p.locator('.el-table th').allInnerTexts()).join('|')
      for (const h of ['Campus Name', 'Campus Code', 'API Url', 'Contact (Name)', 'Contact (Phone)', 'Status', 'operations']) {
        await expect(heads.includes(h), 'column ' + h + ', got ' + heads)
      }
    }
  },
  {
    id: 'CSET-02', group: 'Campus Set',
    action: 'Type a campus name filter on /#/campusset/list',
    expected: 'The typed value is kept in the search input',
    auth: true, route: '/#/campusset/list',
    async run(p, expect) {
      await p.fill('input[placeholder="Campus Name"]', 'Boston')
      await p.waitForTimeout(300)
      await expect(await p.inputValue('input[placeholder="Campus Name"]') === 'Boston', 'value kept')
    }
  },
  {
    id: 'CSET-03', group: 'Campus Set',
    action: 'Click "Delete All Chosen Records" with no row selected',
    expected: 'The confirmation dialog "It will delete all these chosen record." opens with Yes/Cancel',
    auth: true, route: '/#/campusset/list',
    async run(p, expect) {
      await p.getByText('Delete All Chosen Records').click()
      await p.waitForTimeout(900)
      const t = await p.locator('.el-message-box').innerText()
      await expect(t.includes('It will delete all these chosen record.'), 'confirm text, got ' + JSON.stringify(t))
      await expect(t.includes('Yes') && t.includes('Cancel'), 'Yes/Cancel buttons')
    }
  },
  {
    id: 'CSET-04', group: 'Campus Set',
    action: 'Open /#/campusset/add',
    expected: 'The five labelled fields and the Save button are shown',
    auth: true, route: '/#/campusset/add',
    async run(p, expect) {
      const labels = (await p.locator('.el-form-item__label').allInnerTexts()).map(s => s.trim())
      for (const l of ['Campus Name', 'Campus Code', 'API Url', 'Contact (Name)', 'Contact (Phone)']) {
        await expect(labels.includes(l), 'label ' + l + ', got ' + JSON.stringify(labels))
      }
      await expect((await p.locator('.app-container').innerText()).includes('Save'), 'Save button')
    }
  },
  {
    id: 'CSET-05', group: 'Campus Set',
    action: 'Fill the campus-set add form',
    expected: 'All five inputs keep the typed values',
    auth: true, route: '/#/campusset/add',
    async run(p, expect) {
      const vals = ['Boston Campus', 'BOS01', 'http://localhost:8201', 'Jane Doe', '6170000000']
      const inputs = p.locator('.app-container .el-input input')
      for (let i = 0; i < vals.length; i++) await inputs.nth(i).fill(vals[i])
      await p.waitForTimeout(300)
      for (let i = 0; i < vals.length; i++) {
        await expect(await inputs.nth(i).inputValue() === vals[i], 'input ' + i + ' keeps its value')
      }
    }
  },

  // ---------------------------------------------------------------- campus
  {
    id: 'CAMP-01', group: 'Campus',
    action: 'Open /#/campusset/campus/list',
    expected: 'The State/City selects, the campus filter, both buttons and the table columns are shown',
    auth: true, route: '/#/campusset/campus/list',
    async run(p, expect) {
      // Element Plus renders a non-filterable select placeholder as a span
      // instead of an input, so the placeholder is matched DOM-agnostically
      const hasPlaceholder = async text => p.evaluate(t => {
        if (document.querySelector(`input[placeholder="${t}"]`)) return true
        return [...document.querySelectorAll('.el-select *')].some(n => n.textContent.trim() === t)
      }, text)
      await expect(await hasPlaceholder('State'), 'State select placeholder')
      await expect(await hasPlaceholder('City'), 'City select placeholder')
      await expect(await p.locator('input[placeholder="Campus"]').count() === 1, 'Campus filter')
      const t = await p.locator('.app-container').innerText()
      await expect(t.includes('Search') && t.includes('Clear'), 'Search and Clear buttons, got ' + JSON.stringify(t))
      const heads = (await p.locator('.el-table th').allInnerTexts()).join('|')
      for (const h of ['No.', 'Campus Logo', 'Campus Name', 'Area', 'Location', 'Status', 'Created Time', 'Operation']) {
        await expect(heads.includes(h), 'column ' + h + ', got ' + heads)
      }
    }
  },
  {
    id: 'CAMP-02', group: 'Campus',
    action: 'Click the State select on the campus list',
    expected: 'The select dropdown panel opens',
    auth: true, route: '/#/campusset/campus/list',
    async run(p, expect) {
      await p.locator('.el-select').first().click()
      await p.waitForTimeout(800)
      const open = await p.evaluate(() => {
        const d = document.querySelector('.el-select-dropdown')
        if (!d) return false
        const host = d.closest('[style]') || d
        return getComputedStyle(d).display !== 'none' && getComputedStyle(host).display !== 'none'
      })
      await expect(open, 'dropdown panel open')
    }
  },
  {
    id: 'CAMP-03', group: 'Campus',
    action: 'Open the campus list while the backend is unreachable',
    expected: 'The table keeps its loading state and the screen still renders the filters',
    auth: true, route: '/#/campusset/campus/list',
    async run(p, expect) {
      await expect(await p.locator('.el-table').isVisible(), 'table rendered')
      await expect(await p.locator('.demo-form-inline').isVisible(), 'filter form rendered')
    }
  },
  {
    id: 'CAMP-04', group: 'Campus',
    action: 'Open /#/campusset/campus/schedule/1000_0',
    expected: 'The "Choose:" selection line, the department tree aside and the schedule table are shown',
    auth: true, route: '/#/campusset/campus/schedule/1000_0',
    async run(p, expect) {
      const t = await p.locator('.app-container').innerText()
      await expect(t.includes('Choose:'), 'selection line, got ' + JSON.stringify(t))
      await expect(await p.locator('.el-aside').isVisible(), 'department aside')
      const heads = (await p.locator('.el-table th').allInnerTexts()).join('|')
      for (const h of ['Room', 'Time', 'Reserve Number', 'Available Number', 'Amount', 'Skill']) {
        await expect(heads.includes(h), 'column ' + h + ', got ' + heads)
      }
    }
  },

  // ------------------------------------------------------------------- 404
  {
    id: '404-01', group: 'Not Found',
    action: 'Open a route that does not exist',
    expected: 'The 404 screen with "OOPS!" and the "Back to Home" link is shown',
    auth: true, route: '/#/no-such-route-at-all',
    async run(p, expect) {
      await expect(await p.locator('.wscn-http404').isVisible(), '404 screen visible')
      const t = await p.locator('.wscn-http404').innerText()
      await expect(t.includes('OOPS!'), 'OOPS! text, got ' + JSON.stringify(t))
      await expect(t.includes('you cannot enter this page......'), 'message text')
      await expect(t.includes('Back to Home'), 'Back to Home link')
    }
  },
  {
    id: '404-02', group: 'Not Found',
    action: 'Inspect the URL after requesting an unknown route',
    expected: 'The catch-all route has redirected the browser to /404',
    auth: true, route: '/#/another-missing-route',
    async run(p, expect) {
      await expect(p.url().includes('/404'), 'redirected to /404, got ' + p.url())
    }
  }
]
