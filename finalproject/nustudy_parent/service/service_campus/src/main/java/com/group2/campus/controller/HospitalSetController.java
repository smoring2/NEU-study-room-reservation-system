package com.group2.campus.controller;

import com.atguigu.yygh.model.hosp.CampusSet;
import com.atguigu.yygh.vo.hosp.CampusSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.campus.service.HospitalSetService;
import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.common.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
@Api(tags = "Campus meeting room information management")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    //read all data
    // http://localhost:8201/admin/hosp/hospitalSet/findAll
    @ApiOperation(value = "Get all campus info list")
    @GetMapping("findAll")
    public Result findAllHospitalSet(){
        List<CampusSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    // delete
    @ApiOperation(value = "Delete campus by its id")
    @DeleteMapping("{id}")
    public Result removeHospitalSet(@PathVariable Long id){
        boolean flag = hospitalSetService.removeById(id);
        if (flag){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    //3 条件查询带分页
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  // @RequestBody means that we should use json format to pass data to the backend,
                                  // which can be more adaptive to frontend
                                  @RequestBody(required = false) CampusSetQueryVo campusSetQueryVo) { //Vo class



        //创建page对象，传递当前页，每页记录数
        Page<CampusSet> page = new Page<>(current,limit);
        //构建条件
        QueryWrapper<CampusSet> wrapper = new QueryWrapper<>();
        String hosname = campusSetQueryVo.getHosname();//医院名称
        String hoscode = campusSetQueryVo.getHoscode();//医院编号
        if(!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname", campusSetQueryVo.getHosname());
        }
        if(!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", campusSetQueryVo.getHoscode());
        }

        //调用方法实现分页查询
        IPage<CampusSet> pageHospitalSet = hospitalSetService.page(page, wrapper);

        //返回结果
        return Result.ok(pageHospitalSet);
    }

    //4 添加医院设置
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody CampusSet campusSet) {
        //设置状态 1 使用 0 不能使用
        campusSet.setStatus(1);
        //签名秘钥
        Random random = new Random();
        campusSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        //调用service
        boolean save = hospitalSetService.save(campusSet);
        if(save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //5 根据id获取医院设置
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id) {
//        try {
//            //模拟异常
//            int a = 1/0;
//        }catch (Exception e) {
//            throw new YyghException("失败",201);
//        }

        CampusSet campusSet = hospitalSetService.getById(id);
        return Result.ok(campusSet);
    }

    //6 修改医院设置
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody CampusSet campusSet) {
        boolean flag = hospitalSetService.updateById(campusSet);
        if(flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //7 批量删除医院设置
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    //8 医院设置锁定和解锁
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询医院设置信息
        CampusSet campusSet = hospitalSetService.getById(id);
        //设置状态
        campusSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(campusSet);
        return Result.ok();
    }


    //9 发送签名秘钥
    @PutMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id) {
        CampusSet campusSet = hospitalSetService.getById(id);
        String signKey = campusSet.getSignKey();
        String hoscode = campusSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }
}
