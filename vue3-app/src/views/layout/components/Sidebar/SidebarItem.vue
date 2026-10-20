<template>
  <div v-if="!item.hidden&&item.children" class="menu-wrapper">

    <template v-if="hasOneShowingChild(item.children,item) && (!onlyOneChild.children||onlyOneChild.noShowingChildren)&&!item.alwaysShow">
      <app-link :to="resolvePath(onlyOneChild.path)">
        <el-menu-item :index="resolvePath(onlyOneChild.path)" :class="{'submenu-title-noDropdown':!isNest}">
          <item v-if="onlyOneChild.meta" :icon="onlyOneChild.meta.icon||item.meta.icon" :title="onlyOneChild.meta.title" />
        </el-menu-item>
      </app-link>
    </template>

    <el-sub-menu v-else :index="resolvePath(item.path)">
      <template #title>
        <item v-if="item.meta" :icon="item.meta.icon" :title="item.meta.title" />
      </template>

      <template v-for="child in item.children" :key="child.path">
        <template v-if="!child.hidden">
          <sidebar-item
            v-if="child.children&&child.children.length>0"
            :is-nest="true"
            :item="child"
            :base-path="resolvePath(child.path)"
            class="nest-menu" />
          <app-link v-else :to="resolvePath(child.path)">
            <el-menu-item :index="resolvePath(child.path)">
              <item v-if="child.meta" :icon="child.meta.icon" :title="child.meta.title" />
            </el-menu-item>
          </app-link>
        </template>
      </template>
    </el-sub-menu>

  </div>
</template>

<script setup>
import path from 'path'
import { isExternal } from '@/utils'
import Item from './Item.vue'
import AppLink from './Link.vue'

defineOptions({ name: 'SidebarItem' })

const props = defineProps({
  // route object
  item: {
    type: Object,
    required: true
  },
  isNest: {
    type: Boolean,
    default: false
  },
  basePath: {
    type: String,
    default: ''
  }
})

// kept intentionally non-reactive: the Vue 2 component used it as a scratch
// value written while the template condition is evaluated, and a ref would
// re-trigger the render that writes it

let onlyOneChild = null

function hasOneShowingChild(children, parent) {
  const showingChildren = children.filter(item => {
    if (item.hidden) {
      return false
    } else {
      // Temp set(will be used if only has one showing child)
      onlyOneChild = item
      return true
    }
  })

  // When there is only one child router, the child router is displayed by default
  if (showingChildren.length === 1) {
    return true
  }

  // Show parent if there are no child router to display
  if (showingChildren.length === 0) {
    onlyOneChild = { ... parent, path: '', noShowingChildren: true }
    return true
  }

  return false
}

function resolvePath(routePath) {
  if (isExternalLink(routePath)) {
    return routePath
  }
  return path.resolve(props.basePath, routePath)
}

function isExternalLink(routePath) {
  return isExternal(routePath)
}
</script>
