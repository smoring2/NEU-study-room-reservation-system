import request from "@/utils/request";

// DEBUG TRANSLATE
export default {
  // campus list
  getCampusList(page, limit, searchObj) {
    return request({
      url: `/admin/campus/campus/list/${page}/${limit}`,
      method: "get",
      params: searchObj
    });
  },
  findByDictCode(dictCode) {
    return request({
      url: `/admin/cmn/dict/findByDictCode/${dictCode}`,
      method: "get"
    });
  },
  findChildId(id) {
    return request({
      url: `/admin/cmn/dict/findChildData/${id}`,
      method: "get"
    });
  },
  updateStatus(id, status) {
    return request({
      url: `/admin/campus/campus/updateCampusStatus/${id}/${status}`,
      method: "get"
    });
  },
  getCampusById(id) {
    return request({
      url: `/admin/campus/campus/showCampusDetail/${id}`,
      method: "get"
    });
  },
  getDeptByCampusCode(campuscode) {
    return request({
      url: `/admin/campus/department/getDeptList/${campuscode}`,
      method: "get"
    });
  },
  getScheduleRule(page, limit, campuscode, depcode) {
    return request({
      url: `admin/campus/schedule/getScheduleRule/${page}/${limit}/${campuscode}/${depcode}`,
      method: "get"
    });
  },
  getScheduleDetail(campuscode, depcode, workDate) {
    return request({
      url: `admin/campus/schedule/getScheduleDetail/${campuscode}/${depcode}/${workDate}`,
      method: "get"
    });
  }
};
