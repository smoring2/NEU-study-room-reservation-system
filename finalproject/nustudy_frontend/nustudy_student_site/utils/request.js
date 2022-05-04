import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import cookie from "js-cookie"
// create axios instance
const service = axios.create({
  baseURL: 'http://localhost',
  timeout: 15000
})
// http request
service.interceptors.request.use(
  config => {
    if (cookie.get('token')) {
      config.headers['token']=cookie.get('token')
    }
    return config
  },
  err => {
    return Promise.reject(err)
  }
),
// http response
 service.interceptors.response.use(
   response => {
     if (response.data.code  == 208) {
       loginEvent.$emit('loginDialogEvent')
       return
     }
     else if (response.data.code != 200) {
       Message ({
         message: response.data.message,
         type:'error',
         duration: 5 * 1000
       })
       return Promise.reject(response.data)
     } else {
       return response.data
     }
   },
   error => {
     return Promise.reject(error.response)
   }
 )
 export default service

