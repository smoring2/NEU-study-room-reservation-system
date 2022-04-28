package com.group2.campus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group2.campus.mapper.CampusSetMapper;
import com.group2.nustudy.common.exception.NustudyException;
import com.group2.nustudy.common.result.ResultCodeEnum;
import com.group2.nustudy.model.camp.CampusSet;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group2.campus.service.CampusSetService;
import com.group2.nustudy.vo.order.SignInfoVo;
import org.springframework.stereotype.Service;

@Service
public class CampusSetServiceImpl extends ServiceImpl<CampusSetMapper, CampusSet> implements CampusSetService {

    @Override
    public String getSignKey(String campuscode) {
        QueryWrapper<CampusSet> wrapper = new QueryWrapper<>();
        wrapper.eq("campuscode",campuscode);
        CampusSet campusSet = baseMapper.selectOne(wrapper);
        return campusSet.getSignKey();
    }

    @Override
    public SignInfoVo getSignInfoVo(String campuscode) {
        QueryWrapper<CampusSet> wrapper = new QueryWrapper<>();
        wrapper.eq("campuscode",campuscode);
        CampusSet campusSet = baseMapper.selectOne(wrapper);
        if(null == campusSet) {
            throw new NustudyException(ResultCodeEnum.CAMPUS_OPEN);
        }
        SignInfoVo signInfoVo = new SignInfoVo();
        signInfoVo.setApiUrl(campusSet.getApiUrl());
        signInfoVo.setSignKey(campusSet.getSignKey());
        return signInfoVo;
    }

}
