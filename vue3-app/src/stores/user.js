import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'

// Pinia replacement for the Vuex `user` module (Setup Store syntax).
// All actions are async so that existing `.then()` call sites keep working
// exactly as they did with Vuex's implicit Promise wrapping of dispatch().
export const useUserStore = defineStore('user', () => {
  const token = ref(getToken())
  const name = ref('')
  const avatar = ref('')
  const roles = ref([])

  // 登录
  async function Login(userInfo) {
    const data = { token: 'admin' }
    setToken(data.token)
    token.value = data.token

    // const username = userInfo.username.trim()
    // const response = await login(username, userInfo.password)
    // const data = response.data
    // setToken(data.token)
    // token.value = data.token
  }

  // 获取用户信息
  async function GetInfo() {
    const data = {
      roles: 'admin',
      name: 'admin',
      avatar:
        'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif'
    }
    if (data.roles && data.roles.length > 0) {
      // 验证返回的roles是否是一个非空数组
      roles.value = data.roles
    } else {
      throw new Error('getInfo: roles must be a non-null array !')
    }
    name.value = data.name
    avatar.value = data.avatar

    // const response = await getInfo(token.value)
    // const data = response.data
    // if (data.roles && data.roles.length > 0) {
    //   // 验证返回的roles是否是一个非空数组
    //   roles.value = data.roles
    // } else {
    //   throw new Error("getInfo: roles must be a non-null array !")
    // }
    // name.value = data.name
    // avatar.value = data.avatar
    // return response
  }

  // 登出
  async function LogOut() {
    token.value = ''
    roles.value = []
    removeToken()
    // await logout(token.value)
    // token.value = ""
    // roles.value = []
    // removeToken()
  }

  // 前端 登出
  async function FedLogOut() {
    token.value = ''
    removeToken()
  }

  return { token, name, avatar, roles, Login, GetInfo, LogOut, FedLogOut }
})
