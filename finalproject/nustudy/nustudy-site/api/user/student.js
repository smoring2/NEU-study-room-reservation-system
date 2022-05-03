import request from '@/utils/request'

const api_name = `/api/user/student`
export default {

  findList() {
    return request({
      url: `${api_name}/auth/findAll`,
      method: `get`
    })
  },

  getById(id) {
    return request({
      url: `${api_name}/auth/get/${id}`,
      method: 'get'
    })
  }

}

