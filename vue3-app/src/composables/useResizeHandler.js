import { watch, onBeforeMount, onMounted, onBeforeUnmount } from 'vue'
import { useRoute } from 'vue-router'
import { useAppStore } from '@/stores/app'

const { body } = document
const WIDTH = 1024
const RATIO = 3

// Composable replacement for views/layout/mixin/ResizeHandler.js
export function useResizeHandler() {
  const appStore = useAppStore()
  const route = useRoute()

  function isMobile() {
    const rect = body.getBoundingClientRect()
    return rect.width - RATIO < WIDTH
  }

  function resizeHandler() {
    if (!document.hidden) {
      const mobile = isMobile()
      appStore.toggleDevice(mobile ? 'mobile' : 'desktop')

      if (mobile) {
        appStore.closeSideBar({ withoutAnimation: true })
      }
    }
  }

  watch(route, () => {
    if (appStore.device === 'mobile' && appStore.sidebar.opened) {
      appStore.closeSideBar({ withoutAnimation: false })
    }
  })

  onBeforeMount(() => {
    window.addEventListener('resize', resizeHandler)
  })

  onMounted(() => {
    const mobile = isMobile()
    if (mobile) {
      appStore.toggleDevice('mobile')
      appStore.closeSideBar({ withoutAnimation: true })
    }
  })

  onBeforeUnmount(() => {
    window.removeEventListener('resize', resizeHandler)
  })

  return { isMobile, resizeHandler }
}
