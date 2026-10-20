<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">
    <el-menu
      :show-timeout="200"
      :default-active="route.path"
      :collapse="isCollapse"
      mode="vertical"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
      :style="menuThemeVars"
    >
      <sidebar-item v-for="r in routes" :key="r.path" :item="r" :base-path="r.path"/>
    </el-menu>
  </el-scrollbar>
</template>

<script setup>
import { computed } from 'vue'
import { storeToRefs } from 'pinia'
import { useRoute, useRouter } from 'vue-router'
import SidebarItem from './SidebarItem.vue'
import { useAppStore } from '@/stores/app'

const appStore = useAppStore()
const { sidebar } = storeToRefs(appStore)
const route = useRoute()
const router = useRouter()

const routes = computed(() => router.options.routes)
const isCollapse = computed(() => !sidebar.value.opened)

// Element Plus dropped el-menu's background-color/text-color/active-text-color
// props; the identical theme is applied through CSS variables instead.
const menuThemeVars = {
  '--el-menu-bg-color': '#304156',
  '--el-menu-text-color': '#bfcbd9',
  '--el-menu-active-color': '#409EFF',
  // Element UI derived the hover/opened shade from background-color as
  // mix(#304156, #000, 10%) = #263445
  '--el-menu-hover-bg-color': '#263445'
}
</script>
