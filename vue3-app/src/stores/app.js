import { defineStore } from 'pinia'
import { ref } from 'vue'
import Cookies from 'js-cookie'

// Pinia replacement for the Vuex `app` module (Setup Store syntax).
export const useAppStore = defineStore('app', () => {
  const sidebar = ref({
    opened: !+Cookies.get('sidebarStatus'),
    withoutAnimation: false
  })
  const device = ref('desktop')

  // was mutation TOGGLE_SIDEBAR + action ToggleSideBar
  async function toggleSideBar() {
    if (sidebar.value.opened) {
      Cookies.set('sidebarStatus', 1)
    } else {
      Cookies.set('sidebarStatus', 0)
    }
    sidebar.value.opened = !sidebar.value.opened
    sidebar.value.withoutAnimation = false
  }

  // was mutation CLOSE_SIDEBAR + action CloseSideBar
  async function closeSideBar({ withoutAnimation }) {
    Cookies.set('sidebarStatus', 1)
    sidebar.value.opened = false
    sidebar.value.withoutAnimation = withoutAnimation
  }

  // was mutation TOGGLE_DEVICE + action ToggleDevice
  async function toggleDevice(value) {
    device.value = value
  }

  return { sidebar, device, toggleSideBar, closeSideBar, toggleDevice }
})
