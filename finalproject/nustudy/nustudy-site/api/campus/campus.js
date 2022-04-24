import request from "@/utils/request";
const api_name = `/api/campus/campus`;
export default {
  // look up campus list
  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/findCampusList/${page}/${limit}`,
      method: "get",
      params: searchObj,
    });
  },
  // find campus by name
  getByCampusName(campusName) {
    return request({
      url: `${api_name}/findByCampusName/${campusName}`,
      method: "get",
    });
  },

  show(campusCode) {
    return request({
      url: `${api_name}/findCampusDetail/${campusCode}`,
      method: "get",
    });
  },

  findDepartment(campusCode) {
    return request({
      url: `${api_name}/department/${campusCode}`,
      method: "get",
    });
  },
  getBookingScheduleRule(page, limit, hoscode, depcode) {
    return request({
      url: `${api_name}/auth/getBookingScheduleRule/${page}/${limit}/${hoscode}/${depcode}`,
      method: "get",
    });
  },

  findScheduleList(hoscode, depcode, workDate) {
    return request({
      url: `${api_name}/auth/findScheduleList/${hoscode}/${depcode}/${workDate}`,
      method: "get",
    });
  },

  getSchedule(id) {
    return request({
      url: `${api_name}/getSchedule/${id}`,
      method: "get",
    });
  },

  getHospitalInfo(hoscode) {
    return request({
      url: `${api_name}/getHospitalInfo/${hoscode}`,
      method: "get",
    });
  },

  getBookingScheduleRule(page, limit, hoscode, depcode) {
    return request({
      url: `${api_name}/auth/getBookingScheduleRule/${page}/${limit}/${hoscode}/${depcode}`,
      method: "get",
    });
  },

  findScheduleList(hoscode, depcode, workDate) {
    return request({
      url: `${api_name}/auth/findScheduleList/${hoscode}/${depcode}/${workDate}`,
      method: "get",
    });
  },
};
