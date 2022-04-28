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

    //生成挂号订单
    @PostMapping("auth/submitOrder/{scheduleId}/{studentId}")
    public Result savaOrders(@PathVariable String scheduleId,
                             @PathVariable Long studentId) {
        Long orderId = orderService.saveOrder(scheduleId, studentId);
        return Result.ok(orderId);
    }

    //根据订单id查询订单详情
    @GetMapping("auth/getOrders/{orderId}")
    public Result getOrders(@PathVariable String orderId) {
        OrderInfo orderInfo = orderService.getOrder(orderId);
        return Result.ok(orderInfo);
    }

    //订单列表（条件查询带分页）
//    @GetMapping("auth/{page}/{limit}")
//    public Result list(@PathVariable Long page,
//                       @PathVariable Long limit,
//                       OrderQueryVo orderQueryVo, HttpServletRequest request) {
//        //设置当前用户id
//        orderQueryVo.setUserId(AuthContextHolder.getUserId(request));
//        Page<OrderInfo> pageParam = new Page<>(page,limit);
//        IPage<OrderInfo> pageModel =
//                orderService.selectPage(pageParam,orderQueryVo);
//        return Result.ok(pageModel);
//    }

//    @ApiOperation(value = "获取订单状态")
//    @GetMapping("auth/getStatusList")
//    public Result getStatusList() {
//        return Result.ok(OrderStatusEnum.getStatusList());
//    }

    //取消预约
//    @GetMapping("auth/cancelOrder/{orderId}")
//    public Result cancelOrder(@PathVariable Long orderId) {
//        Boolean isOrder = orderService.cancelOrder(orderId);
//        return Result.ok(isOrder);
//    }

//    @ApiOperation(value = "获取订单统计数据")
//    @PostMapping("inner/getCountMap")
//    public Map<String, Object> getCountMap(@RequestBody OrderCountQueryVo orderCountQueryVo) {
//        return orderService.getCountMap(orderCountQueryVo);
//    }
}



