import request from "@/utils/request";

//DEBUG TRANSLATE
export default {
  getHospSetList(current, limit, searchObj) {
    return request({
      url: `admin/hosp/hospitalSet/findPageHospSet/${current}/${limit}`,
      method: "post",
      data: searchObj // using json format
    });
  },
  deleteHospitalSet(id) {
    return request({
      url: `/admin/hosp/hospitalSet/${id}`,
      method: "delete"
    });
  },
  batchRemoveHospitalSet(idList) {
    return request({
      url: "/admin/hosp/hospitalSet/batchRemove",
      method: "delete",
      data: idList
    });
  },
  lockHospSet(id, status) {
    return request({
      url: `/admin/hosp/hospitalSet/lockHospSet/${id}/${status}`,
      method: "put"
    });
  },
  saveHospSet(hospitalSet) {
    return request({
      url: "/admin/hosp/hospitalSet/saveHospSet",
      method: "post",
      data: hospitalSet
    });
  },
  getHospSet(id) {
    return request({
      url: `/admin/hosp/hospitalSet/getHospSet/${id}`,
      method: "get"
    });
  },
  updateHospSet(hospitalSet) {
    return request({
      url: "/admin/hosp/hospitalSet/updateHospSet",
      method: "post",
      data: hospitalSet
    });
  }
};
