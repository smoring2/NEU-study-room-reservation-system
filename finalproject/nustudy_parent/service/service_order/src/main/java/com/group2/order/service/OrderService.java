package com.group2.order.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.group2.nustudy.model.order.OrderInfo;
import com.group2.nustudy.vo.order.OrderCountQueryVo;
import com.group2.nustudy.vo.order.OrderQueryVo;

import java.util.Map;

public interface OrderService extends IService<OrderInfo> {

    //Generate registered orders
    Long saveOrder(String scheduleId, Long studentId);

    //Query order details based on order id
    OrderInfo getOrder(String orderId);

}
