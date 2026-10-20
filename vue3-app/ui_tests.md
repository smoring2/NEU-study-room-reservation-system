# UI Behavior Test Cases extracted from the Vue 2 application

Source: finalproject/nustudy_frontend/nustudy_central_management_site (Vue 2.5.17)
Extracted from the SFC templates, event handlers, methods, computed properties and watchers,
cross-checked against business_rules.txt and application_flows.txt.

Format: `[Action] - [Expected Result]`

Total extracted: 35

## Login

- `LOGIN-01` Open the application without an Admin-Token cookie - The login screen is shown and the URL carries the /login route with a redirect query
- `LOGIN-02` Inspect the login form on load - The title "vue-admin-template" is shown and username/password are pre-filled with "admin"
- `LOGIN-03` Read the tips line under the login form - It advertises "username: admin" and "password: admin"
- `LOGIN-04` Click the eye icon next to the password field - The password input switches away from type "password" so the value becomes readable
- `LOGIN-05` Enter the username "nobody" and submit the login form - The validation error "Please enter correct username" is shown and the route stays on /login
- `LOGIN-06` Enter a password shorter than 5 characters and submit the login form - The validation error "Password cannot be less than 5 characters" is shown
- `LOGIN-07` Submit the pre-filled login form - The Admin-Token cookie is written and the dashboard is shown inside the layout shell
- `LOGIN-08` Open /#/login while holding a valid Admin-Token cookie - The guard redirects away from the login screen to the dashboard

## Dashboard

- `DASH-01` Open the dashboard as the authenticated admin - The page prints "name:admin" and a roles line
- `DASH-02` Inspect the layout shell on the dashboard - A 180px dark sidebar (#304156) and a 50px navbar are rendered

## Navbar

- `NAV-01` Click the hamburger icon in the navbar - The sidebar collapses to 36px and the sidebarStatus cookie becomes 1
- `NAV-02` Click the hamburger icon twice - The sidebar collapses and then expands back to 180px
- `NAV-03` Click the avatar in the navbar - A dropdown opens offering "Home" and "LogOut"
- `NAV-04` Open the avatar dropdown and click "LogOut" - The Admin-Token cookie is removed and the login screen is shown again
- `NAV-05` Open the avatar dropdown and click "Home" - The application navigates to the dashboard

## Sidebar

- `SIDE-01` Inspect the sidebar entries - The sub-menus "Data Management" and "Campus set" are listed and hidden routes are not
- `SIDE-02` Expand "Campus set" in the sidebar - The children "Campus Set List", "Campus Set Add" and "Campus List" are shown
- `SIDE-03` Expand "Campus set" and click "Campus Set Add" - The application navigates to /campusset/add and the add form is shown
- `SIDE-04` Expand "Data Management" and click "Data Dict" - The application navigates to /cmn/list and the dictionary toolbar is shown

## Breadcrumb

- `CRUMB-01` Navigate to /#/campusset/list and read the breadcrumb - The breadcrumb starts with "Dashboard" and ends with the plain-text current page
- `CRUMB-02` Navigate from /#/campusset/list to /#/cmn/list - The breadcrumb updates to the new route

## Data Dictionary

- `DICT-01` Open /#/cmn/list - The Export and Import toolbar buttons and the four table columns are shown
- `DICT-02` Click the "Import" toolbar button - The "Import" dialog opens with the upload button and the 500kb Excel tip
- `DICT-03` Open the Import dialog and click "Cancel" - The dialog closes again

## Campus Set

- `CSET-01` Open /#/campusset/list - The heading text, both search inputs, the batch delete button and the table columns are shown
- `CSET-02` Type a campus name filter on /#/campusset/list - The typed value is kept in the search input
- `CSET-03` Click "Delete All Chosen Records" with no row selected - The confirmation dialog "It will delete all these chosen record." opens with Yes/Cancel
- `CSET-04` Open /#/campusset/add - The five labelled fields and the Save button are shown
- `CSET-05` Fill the campus-set add form - All five inputs keep the typed values

## Campus

- `CAMP-01` Open /#/campusset/campus/list - The State/City selects, the campus filter, both buttons and the table columns are shown
- `CAMP-02` Click the State select on the campus list - The select dropdown panel opens
- `CAMP-03` Open the campus list while the backend is unreachable - The table keeps its loading state and the screen still renders the filters
- `CAMP-04` Open /#/campusset/campus/schedule/1000_0 - The "Choose:" selection line, the department tree aside and the schedule table are shown

## Not Found

- `404-01` Open a route that does not exist - The 404 screen with "OOPS!" and the "Back to Home" link is shown
- `404-02` Inspect the URL after requesting an unknown route - The catch-all route has redirected the browser to /404

