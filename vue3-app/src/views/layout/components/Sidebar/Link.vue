
<template>
  <!-- eslint-disable vue/require-component-is-->
  <component :is="linkComponent(to)" v-bind="linkProps(to)">
    <slot/>
  </component>
</template>

<script setup>
import { isExternal } from '@/utils'
import { RouterLink } from 'vue-router'

const props = defineProps({
  to: {
    type: String,
    required: true
  }
})

function isExternalLink(routePath) {
  return isExternal(routePath)
}

// Vue 3 resolves <component :is> from the binding, and an `is` key inside
// v-bind is no longer honoured, so the component and its props are split.
function linkComponent(url) {
  return isExternalLink(url) ? 'a' : RouterLink
}

function linkProps(url) {
  if (isExternalLink(url)) {
    return {
      href: url,
      target: '_blank',
      rel: 'noopener'
    }
  }
  return {
    to: url
  }
}
</script>
