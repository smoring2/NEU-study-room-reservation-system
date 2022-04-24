import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
// create axios instance
const service = axios.create({
  baseURL: 'http://localhost',
  timeout: 15000
})
// http request
service.interceptors.request.use(
  config => {
    return config
  }
),
// http response
 service.interceptors.response.use(
   response => {
     if (response.data.code != 200) {
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

