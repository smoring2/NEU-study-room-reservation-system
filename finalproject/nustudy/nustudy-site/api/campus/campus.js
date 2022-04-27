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

  // show campus by campusCode
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
  getBookingScheduleRule(page, limit, campuscode, depcode) {
    return request({
      url: `${api_name}/auth/getBookingScheduleRule/${page}/${limit}/${campuscode}/${depcode}`,
      method: "get",
    });
  },

  findScheduleList(campuscode, depcode, workDate) {
    return request({
      url: `${api_name}/auth/findScheduleList/${campuscode}/${depcode}/${workDate}`,
      method: "get",
    });
  },

  getSchedule(id) {
    return request({
      url: `${api_name}/getSchedule/${id}`,
      method: "get",
    });
  },

  getHospitalInfo(campuscode) {
    return request({
      url: `${api_name}/getHospitalInfo/${campuscode}`,
      method: "get",
    });
  },
};
