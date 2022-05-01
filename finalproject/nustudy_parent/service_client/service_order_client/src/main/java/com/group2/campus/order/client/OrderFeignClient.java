package com.group2.campus.order.client;

import com.group2.nustudy.vo.order.OrderCountQueryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * FeignClient of service-order
 */
@FeignClient(value = "service-order")
@Repository
public interface OrderFeignClient {
    /**
     * get order information by schedule id
     */
    @PostMapping("/api/order/orderInfo/inner/getCountMap")
    public Map<String, Object> getCountMap(@RequestBody OrderCountQueryVo orderCountQueryVo);

}
