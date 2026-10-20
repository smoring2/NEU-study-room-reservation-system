# UI Behavior Tests - Vue 3.5.44 migrated application (vite dev server at http://localhost:5173)

- Target: http://localhost:5173
- Executed with: Playwright chromium in a real browser, viewport 1280x720
- Execution date: 2026-10-20T17:55:44.099Z
- Total extracted: 34
- Total passed: 34
- Total failed: 0
- Success rate: 100.00%
- Execution time: 96.9s
- Screenshots: after_migration/ui-behavior-tests/<test-id>.png

## Test cases

### Login

- PASS `LOGIN-01` Open the application without an Admin-Token cookie - The login screen is shown and the URL carries the /login route with a redirect query
- PASS `LOGIN-02` Inspect the login form on load - The title "vue-admin-template" is shown and username/password are pre-filled with "admin"
- PASS `LOGIN-03` Read the tips line under the login form - It advertises "username: admin" and "password: admin"
- PASS `LOGIN-04` Click the eye icon next to the password field - The password input switches away from type "password" so the value becomes readable
- PASS `LOGIN-05` Enter the username "nobody" and submit the login form - The validation error "Please enter correct username" is shown and the route stays on /login
- PASS `LOGIN-06` Enter a password shorter than 5 characters and submit the login form - The validation error "Password cannot be less than 5 characters" is shown
- PASS `LOGIN-07` Submit the pre-filled login form - The Admin-Token cookie is written and the dashboard is shown inside the layout shell
- PASS `LOGIN-08` Open /#/login while holding a valid Admin-Token cookie - The guard redirects away from the login screen to the dashboard

### Dashboard

- PASS `DASH-01` Open the dashboard as the authenticated admin - The page prints "name:admin" and a roles line
- PASS `DASH-02` Inspect the layout shell on the dashboard - A 180px dark sidebar (#304156) and a 50px navbar are rendered

### Navbar

- PASS `NAV-01` Click the hamburger icon in the navbar - The sidebar collapses to 36px and the sidebarStatus cookie becomes 1
- PASS `NAV-02` Click the hamburger icon twice - The sidebar collapses and then expands back to 180px
- PASS `NAV-03` Click the avatar in the navbar - A dropdown opens offering "Home" and "LogOut"
- PASS `NAV-04` Open the avatar dropdown and click "LogOut" - The Admin-Token cookie is removed and the login screen is shown again
- PASS `NAV-05` Open the avatar dropdown and click "Home" - The application navigates to the dashboard

### Sidebar

- PASS `SIDE-01` Inspect the sidebar entries - The sub-menus "Data Management" and "Campus set" are listed and hidden routes are not
- PASS `SIDE-02` Expand "Campus set" in the sidebar - The children "Campus Set List", "Campus Set Add" and "Campus List" are shown
- PASS `SIDE-03` Expand "Campus set" and click "Campus Set Add" - The application navigates to /campusset/add and the add form is shown
- PASS `SIDE-04` Expand "Data Management" and click "Data Dict" - The application navigates to /cmn/list and the dictionary toolbar is shown

### Breadcrumb

- PASS `CRUMB-01` Navigate to /#/campusset/list and read the breadcrumb - The breadcrumb starts with "Dashboard" and ends with the plain-text current page
- PASS `CRUMB-02` Navigate from /#/campusset/list to /#/cmn/list - The breadcrumb updates to the new route

### Data Dictionary

- PASS `DICT-01` Open /#/cmn/list - The Export and Import toolbar buttons and the four table columns are shown
- PASS `DICT-02` Click the "Import" toolbar button - The "Import" dialog opens with the upload button and the 500kb Excel tip
- PASS `DICT-03` Open the Import dialog and click "Cancel" - The dialog closes again

### Campus Set

- PASS `CSET-01` Open /#/campusset/list - The heading text, both search inputs, the batch delete button and the table columns are shown
- PASS `CSET-02` Type a campus name filter on /#/campusset/list - The typed value is kept in the search input
- PASS `CSET-03` Click "Delete All Chosen Records" with no row selected - The confirmation dialog "It will delete all these chosen record." opens with Yes/Cancel
- PASS `CSET-04` Open /#/campusset/add - The five labelled fields and the Save button are shown
- PASS `CSET-05` Fill the campus-set add form - All five inputs keep the typed values

### Campus

- PASS `CAMP-01` Open /#/campusset/campus/list - The State/City selects, the campus filter, both buttons and the table columns are shown
- PASS `CAMP-03` Open the campus list while the backend is unreachable - The table keeps its loading state and the screen still renders the filters
- PASS `CAMP-04` Open /#/campusset/campus/schedule/1000_0 - The "Choose:" selection line, the department tree aside and the schedule table are shown

### Not Found

- PASS `404-01` Open a route that does not exist - The 404 screen with "OOPS!" and the "Back to Home" link is shown
- PASS `404-02` Inspect the URL after requesting an unknown route - The catch-all route has redirected the browser to /404

