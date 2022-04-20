import request from "@/utils/request";

//DEBUG TRANSLATE
export default {
  getCampusSetList(current, limit, searchObj) {
    return request({
      url: `admin/campus/campusSet/findPageCampusSet/${current}/${limit}`,
      method: 'post',
      data: searchObj // using json format
    })
  },
  deleteCampusSet(id) {
    return request({
      url: `/admin/campus/campusSet/${id}`,
      method: 'delete'
    })
  },
  batchRemoveCampusSet(idList) {
    return request({
      url: '/admin/campus/campusSet/batchRemove',
      method: 'delete',
      data: idList
    })
  },
  lockCampusSet(id, status) {
    return request({
      url: `/admin/campus/campusSet/lockCampusSet/${id}/${status}`,
      method: 'put'
    })
  },
  saveCampusSet(hospitalSet) {
    return request({
      url: '/admin/campus/campusSet/saveCampusSet',
      method: 'post',
      data: hospitalSet
    })
  },
  getCampusSet(id) {
    return request({
      url: `/admin/campus/campusSet/getCampusSet/${id}`,
      method: 'get'
    })
  },
  updateCampusSet(hospitalSet) {
    return request({
      url: '/admin/campus/campusSet/updateCampusSet',
      method: 'post',
      data: hospitalSet
    })
  }
}
