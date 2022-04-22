import request from "@/utils/request";

//DEBUG TRANSLATE
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
  findChindId(id) {
    return request({
      url: `/admin/cmn/dict/findChildData/${id}`,
      method: "get"
    });
  }
};
