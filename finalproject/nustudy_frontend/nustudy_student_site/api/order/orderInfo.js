import request from "@/utils/request";

const api_name = `/api/order/orderInfo`;

export default {
  submitOrder(scheduleId, studentId) {
    return request({
      url: `${api_name}/auth/submitOrder/${scheduleId}/${studentId}`,
      method: "post",
    });
  },

  // getPageList(page, limit, searchObj) {
  //   return request({
  //     url: `${api_name}/auth/${page}/${limit}`,
  //     method: 'get',
  //     params: searchObj
  //   })
  // },
  //
  // getStatusList() {
  //   return request({
  //     url: `${api_name}/auth/getStatusList`,
  //     method: 'get'
  //   })
  // },

  getOrderInfo(orderId) {
    return request({
      // url: `${api_name}/auth/getOrderInfo/${orderId}`,
      url: `${api_name}/auth/getOrders/${orderId}`,
      method: "get",
    });
  },

  // cancelOrder(orderId) {
  //   return request({
  //     url: `${api_name}/auth/cancelOrder/${orderId}`,
  //     method: 'get'
  //   })
  // }
};
