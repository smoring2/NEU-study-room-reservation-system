
<template>
  <!-- eslint-disable vue/require-component-is-->
  <component v-bind="linkProps(to)">
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

function linkProps(url) {
  if (isExternalLink(url)) {
    return {
      is: 'a',
      href: url,
      target: '_blank',
      rel: 'noopener'
    }
  }
  // Vue 3 <component :is> needs the resolved component, not a string name
  return {
    is: RouterLink,
    to: url
  }
}
</script>
