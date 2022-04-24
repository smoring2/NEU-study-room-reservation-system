package com.group2.campus.service;

import com.group2.nustudy.model.camp.Campus;
import com.group2.nustudy.vo.camp.CampusQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CampusService {
    //上传医院接口
    void save(Map<String, Object> paramMap);

    //实现根据医院编号查询
    Campus getByHoscode(String hoscode);

    //医院列表(条件查询分页)
    Page<Campus> selectCampusPage(Integer page, Integer limit, CampusQueryVo campusQueryVo);

    //更新医院上线状态
    void updateStatus(String id, Integer status);

    //医院详情信息
    Map<String, Object> getCampusById(String id);

    //获取医院名称
    String getCampusName(String hoscode);

    //根据医院名称查询
    List<Campus> findByCampusName(String campusName);

    //根据医院编号获取医院预约挂号详情
    Map<String, Object> item(String hoscode);
}
