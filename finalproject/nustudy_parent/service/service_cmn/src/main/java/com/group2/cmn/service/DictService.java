package com.group2.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group2.nustudy.model.cmn.Dict;

import java.util.List;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
public interface DictService extends IService<Dict> {

    //根据数据id查询子数据列表
    List<Dict> findChlidData(Long id);
}
