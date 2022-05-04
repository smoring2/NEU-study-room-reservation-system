import request from "@/utils/request";

 // DEBUG TRANSLATE
export default {
  dictList(id) {
    return request({
      url: `/admin/cmn/dict/findChildData/${id}`,
      method: "get"
    });
  }
};
