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

    //Order list (conditional query with pagination）
//    @GetMapping("auth/{page}/{limit}")
//    public Result list(@PathVariable Long page,
//                       @PathVariable Long limit,
//                       OrderQueryVo orderQueryVo, HttpServletRequest request) {
//        //set current user id
//        orderQueryVo.setUserId(AuthContextHolder.getUserId(request));
//        Page<OrderInfo> pageParam = new Page<>(page,limit);
//        IPage<OrderInfo> pageModel =
//                orderService.selectPage(pageParam,orderQueryVo);
//        return Result.ok(pageModel);
//    }

//    @ApiOperation(value = "get order status")
//    @GetMapping("auth/getStatusList")
//    public Result getStatusList() {
//        return Result.ok(OrderStatusEnum.getStatusList());
//    }

    //cancel registeration
//    @GetMapping("auth/cancelOrder/{orderId}")
//    public Result cancelOrder(@PathVariable Long orderId) {
//        Boolean isOrder = orderService.cancelOrder(orderId);
//        return Result.ok(isOrder);
//    }

//    @ApiOperation(value = "get registration total data")
//    @PostMapping("inner/getCountMap")
//    public Map<String, Object> getCountMap(@RequestBody OrderCountQueryVo orderCountQueryVo) {
//        return orderService.getCountMap(orderCountQueryVo);
//    }
}



