import request from "@/utils/request";

const api_name = `/api/order/orderInfo`;

export default {
  submitOrder(scheduleId, studentId) {
    return request({
      url: `${api_name}/auth/submitOrder/${scheduleId}/${studentId}`,
      method: "post",
    });
  },


  getOrderInfo(orderId) {
    return request({
      // url: `${api_name}/auth/getOrderInfo/${orderId}`,
      url: `${api_name}/auth/getOrders/${orderId}`,
      method: "get",
    });
  },

};
