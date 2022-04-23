import request from '@/utils/request'

// DEBUG TRANSLATE
export default {
  // campus list
  getCampusList(page, limit, searchObj) {
    return request({
      url: `/admin/campus/campus/list/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  findByDictCode(dictCode) {
    return request({
      url: `/admin/cmn/dict/findByDictCode/${dictCode}`,
      method: 'get'
    })
  },
  findChildId(id) {
    return request({
      url: `/admin/cmn/dict/findChildData/${id}`,
      method: 'get'
    })
  },
  updateStatus(id, status) {
    return request({
      url: `/admin/campus/campus/updateCampusStatus/${id}/${status}`,
      method: 'get'
    })
  },
  getCampusById(id) {
    return request({
      url: `/admin/campus/campus/showCampusDetail/${id}`,
      method: 'get'
    })
  },
  getDeptByCampusCode(hoscode) {
    return request({
      url: `/admin/campus/department/getDeptList/${hoscode}`,
      method: 'get'
    })
  },
  getScheduleRule(page, limit, hoscode, depcode) {
    return request({
      url: `admin/campus/schedule/getScheduleRule/${page}/${limit}/${hoscode}/${depcode}`,
      method: 'get'
    })
  }
  // getScheduleDetail(){
  //   return request({
  //     // url: `admin/campus/schedule/getScheduleRule/${page}/${limit}/${hascode}/${depcode}` ,
  //     // method: 'get'
  //   })
  // }
}
