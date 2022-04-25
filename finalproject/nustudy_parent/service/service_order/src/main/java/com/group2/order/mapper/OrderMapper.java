package com.group2.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.group2.nustudy.model.order.OrderInfo;
import com.group2.nustudy.vo.order.OrderCountQueryVo;
import com.group2.nustudy.vo.order.OrderCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<OrderInfo> {

    //查询预约统计数据的方法
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
