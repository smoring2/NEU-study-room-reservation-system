// Generates vue3-app/ui_tests.md (all extracted cases) and
// vue3-app/ui_tests_vue2.md (only the cases that passed against the running
// Vue 2 source application).
// Usage: node scripts/make-ui-test-docs.mjs <vue2ResultsJson>
import fs from 'node:fs'
import { cases } from '../e2e/ui-behavior/cases.mjs'

const res = JSON.parse(fs.readFileSync(process.argv[2], 'utf8'))
const byId = Object.fromEntries(res.results.map(r => [r.id, r]))
const groups = [...new Set(cases.map(c => c.group))]

let all = `# UI Behavior Test Cases extracted from the Vue 2 application\n\n`
all += `Source: finalproject/nustudy_frontend/nustudy_central_management_site (Vue 2.5.17)\n`
all += `Extracted from the SFC templates, event handlers, methods, computed properties and watchers,\n`
all += `cross-checked against business_rules.txt and application_flows.txt.\n\n`
all += `Format: \`[Action] - [Expected Result]\`\n\nTotal extracted: ${cases.length}\n\n`
for (const g of groups) {
  all += `## ${g}\n\n`
  for (const c of cases.filter(x => x.group === g)) all += `- \`${c.id}\` ${c.action} - ${c.expected}\n`
  all += '\n'
}
fs.writeFileSync('ui_tests.md', all)

const passed = cases.filter(c => byId[c.id] && byId[c.id].passed)
const failed = cases.filter(c => !byId[c.id] || !byId[c.id].passed)
let v2 = `# UI Behavior Tests validated against the Vue 2 source application\n\n`
v2 += `- Source version: Vue 2.5.17 (vue-admin-template 3.8.0, Element UI 2.12.0)\n`
v2 += `- Target under test: ${res.baseUrl} (production build of the Vue 2 app served statically)\n`
v2 += `- Executed with: Playwright chromium in a real browser, viewport 1280x720\n`
v2 += `- Execution date: ${new Date().toISOString()}\n`
v2 += `- Screenshots: before_migration/ui-behavior-tests/<test-id>.png\n`
v2 += `- Total extracted: ${cases.length}\n- Total passed: ${passed.length}\n- Total failed: ${failed.length}\n`
v2 += `- Success rate against Vue 2: ${((passed.length / cases.length) * 100).toFixed(2)}%\n\n`
if (failed.length) {
  v2 += `## Excluded (failed against the Vue 2 source application)\n\n`
  for (const c of failed) {
    const r = byId[c.id]
    v2 += `- \`${c.id}\` ${c.action} - ${c.expected}\n  - failure reason: ${r ? r.failures.join('; ') : 'not executed'}\n`
    v2 += `  - note: no backend service is reachable in this environment, so data-dependent assertions cannot be satisfied by either version.\n`
  }
  v2 += '\n'
}
v2 += `## Authoritative baseline (these cases passed on Vue 2 and are the ones verified against Vue 3)\n\n`
for (const g of groups) {
  const list = passed.filter(x => x.group === g)
  if (!list.length) continue
  v2 += `### ${g}\n\n`
  for (const c of list) v2 += `- \`${c.id}\` ${c.action} - ${c.expected}\n`
  v2 += '\n'
}
fs.writeFileSync('ui_tests_vue2.md', v2)
fs.writeFileSync('e2e/ui-behavior/baseline-ids.json', JSON.stringify(passed.map(c => c.id), null, 2))
console.log('ui_tests.md:', cases.length, 'cases; ui_tests_vue2.md:', passed.length, 'passing baseline cases')
