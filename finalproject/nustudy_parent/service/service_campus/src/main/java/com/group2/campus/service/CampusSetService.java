package com.group2.campus.service;

import com.group2.nustudy.model.camp.CampusSet;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
public interface CampusSetService extends IService<CampusSet> {
    String getSignKey(String hoscode);
}
