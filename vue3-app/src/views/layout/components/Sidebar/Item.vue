<template>
  <svg-icon v-if="icon" :icon-class="icon"/>
  <span v-if="title">{{ title }}</span>
</template>

<script setup>
// Was a Vue 2 `functional: true` render-function component using JSX.
// Vue 3 has no functional-component template option and supports multiple
// root nodes, so it becomes a normal SFC with the same output.
defineOptions({ name: 'MenuItem' })

defineProps({
  icon: {
    type: String,
    default: ''
  },
  title: {
    type: String,
    default: ''
  }
})
</script>
