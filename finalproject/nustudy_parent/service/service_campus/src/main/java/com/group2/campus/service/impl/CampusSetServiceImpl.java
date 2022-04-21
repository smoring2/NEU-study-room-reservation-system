package com.group2.campus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.group2.campus.mapper.CampusSetMapper;
import com.group2.nustudy.model.hosp.CampusSet;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group2.campus.service.CampusSetService;
import org.springframework.stereotype.Service;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
@Service
public class CampusSetServiceImpl extends ServiceImpl<CampusSetMapper, CampusSet> implements CampusSetService {

    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<CampusSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        CampusSet campusSet = baseMapper.selectOne(wrapper);
        return campusSet.getSignKey();
    }

//    @Override
//    public SignInfoVo getSignInfoVo(String hoscode) {
//        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
//        wrapper.eq("hoscode",hoscode);
//        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
//        if(null == hospitalSet) {
//            throw new YyghException(ResultCodeEnum.HOSPITAL_OPEN);
//        }
//        SignInfoVo signInfoVo = new SignInfoVo();
//        signInfoVo.setApiUrl(hospitalSet.getApiUrl());
//        signInfoVo.setSignKey(hospitalSet.getSignKey());
//        return signInfoVo;
//    }

}
