package com.group2.campus.service;

import com.group2.nustudy.model.camp.CampusSet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.group2.nustudy.vo.order.SignInfoVo;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
public interface CampusSetService extends IService<CampusSet> {
    String getSignKey(String hoscode);

    SignInfoVo getSignInfoVo(String hoscode);
}
