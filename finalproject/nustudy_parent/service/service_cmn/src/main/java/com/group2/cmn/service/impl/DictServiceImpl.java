package com.group2.cmn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.group2.cmn.mapper.DictMapper;
import com.group2.cmn.service.DictService;
import com.group2.nustudy.model.cmn.Dict;
import org.springframework.stereotype.Service;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
}
