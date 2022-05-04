package com.group2.order.api;


import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.model.order.OrderInfo;
import com.group2.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    //Generate registered orders
    @PostMapping("auth/submitOrder/{scheduleId}/{studentId}")
    public Result savaOrders(@PathVariable String scheduleId,
                             @PathVariable Long studentId) {
        Long orderId = orderService.saveOrder(scheduleId, studentId);
        return Result.ok(orderId);
    }

    //Query order details based on order id
    @GetMapping("auth/getOrders/{orderId}")
    public Result getOrders(@PathVariable String orderId) {
        OrderInfo orderInfo = orderService.getOrder(orderId);
        return Result.ok(orderInfo);
    }


}



