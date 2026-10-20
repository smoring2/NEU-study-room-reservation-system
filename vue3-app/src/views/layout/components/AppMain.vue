<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <!-- or name="fade" -->
      <!--      <router-view/>-->
      <router-view :key="key"></router-view>
    </transition>
  </section>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

defineOptions({ name: 'AppMain' })

const route = useRoute()

const key = computed(() => {
  return route.name !== undefined ? route.name + +new Date() : route + +new Date()
})
</script>

<style scoped>
.app-main {
  /*50 = navbar  */
  min-height: calc(100vh - 50px);
  position: relative;
  overflow: hidden;
}
</style>
