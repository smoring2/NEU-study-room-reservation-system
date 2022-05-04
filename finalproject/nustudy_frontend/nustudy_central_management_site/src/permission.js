import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress bar
import 'nprogress/nprogress.css'// Progress bar style
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth' // verification

const whiteList = ['/login'] // Do not redirect whitelist
router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      if (store.getters.roles.length === 0) {
        store.dispatch('GetInfo').then(res => { // Pull user information
          next()
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            Message.error(err || 'Verification failed, please login again')
            next({ path: '/' })
          })
        })
      } else {
        next()
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`) // Otherwise all redirect to login page
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // Progress over
})
