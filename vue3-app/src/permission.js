import router from './router'
import { useUserStore } from './stores/user'
import NProgress from 'nprogress' // Progress bar
import 'nprogress/nprogress.css'// Progress bar style
import { ElMessage } from 'element-plus'
import { getToken } from '@/utils/auth' // verification

const whiteList = ['/login'] // Do not redirect whitelist
router.beforeEach(async (to, from) => {
  NProgress.start()
  const userStore = useUserStore()
  if (getToken()) {
    if (to.path === '/login') {
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
      return { path: '/' }
    } else {
      if (userStore.roles.length === 0) {
        try {
          await userStore.GetInfo() // Pull user information
          return true
        } catch (err) {
          await userStore.FedLogOut()
          ElMessage.error(err || 'Verification failed, please login again')
          return { path: '/' }
        }
      } else {
        return true
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      return true
    } else {
      NProgress.done()
      return `/login?redirect=${to.path}` // Otherwise all redirect to login page
    }
  }
})

router.afterEach(() => {
  NProgress.done() // Progress over
})
