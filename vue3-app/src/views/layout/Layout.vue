<template>
  <div :class="classObj" class="app-wrapper">
    <div v-if="deviceType==='mobile'&&sidebarState.opened" class="drawer-bg" @click="handleClickOutside"/>
    <Sidebar class="sidebar-container"/>
    <div class="main-container">
      <Navbar/>
      <AppMain/>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { storeToRefs } from 'pinia'
import { Navbar, Sidebar, AppMain } from './components'
import { useAppStore } from '@/stores/app'
import { useResizeHandler } from '@/composables/useResizeHandler'

defineOptions({ name: 'Layout' })

const appStore = useAppStore()
// renamed from `sidebar`/`device` so the bindings cannot shadow the
// <Sidebar> component imported above
const { sidebar: sidebarState, device: deviceType } = storeToRefs(appStore)

useResizeHandler()

const classObj = computed(() => {
  return {
    hideSidebar: !sidebarState.value.opened,
    openSidebar: sidebarState.value.opened,
    withoutAnimation: sidebarState.value.withoutAnimation,
    mobile: deviceType.value === 'mobile'
  }
})

function handleClickOutside() {
  appStore.closeSideBar({ withoutAnimation: false })
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @use "src/styles/mixin.scss" as *;
  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
    &.mobile.openSidebar{
      position: fixed;
      top: 0;
    }
  }
  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }
</style>
