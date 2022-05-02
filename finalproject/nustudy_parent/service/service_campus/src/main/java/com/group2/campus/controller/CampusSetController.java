package com.group2.campus.controller;

import com.group2.nustudy.model.camp.CampusSet;
import com.group2.nustudy.vo.camp.CampusSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.group2.campus.service.CampusSetService;
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
@RequestMapping("/admin/campus/campusSet")
//@CrossOrigin
class CampusSetController {

    @Autowired
    private CampusSetService campusSetService;

    //read all data
    // http://localhost:8201/admin/campus/campusSet/findAll
    @ApiOperation(value = "Get all campus info list")
    @GetMapping("findAll")
    public Result findAllCampusSet(){
        List<CampusSet> list = campusSetService.list();
        return Result.ok(list);
    }

    // delete
    @ApiOperation(value = "Delete campus by its id")
    @DeleteMapping("{id}")
    public Result removeCampusSet(@PathVariable Long id){
        boolean flag = campusSetService.removeById(id);
        if (flag){
            return Result.ok();
        }else{
            return Result.fail();
        }
    }

    //3 Conditional query
    @PostMapping("findPageCampusSet/{current}/{limit}")
    public Result findPageCampusSet(@PathVariable long current,
                                    @PathVariable long limit,
                                    // @RequestBody means that we should use json format to pass data to the backend,
                                    // which can be more adaptive to frontend
                                    @RequestBody(required = false) CampusSetQueryVo campusSetQueryVo) { //Vo class



        //Create a page object, pass the current page, the number of records per page
        Page<CampusSet> page = new Page<>(current,limit);
        //build condition
        QueryWrapper<CampusSet> wrapper = new QueryWrapper<>();
        String campusname = campusSetQueryVo.getCampusname();//医院名称
        String campuscode = campusSetQueryVo.getCampuscode();//医院编号
        if(!StringUtils.isEmpty(campusname)) {
            wrapper.like("campusname", campusSetQueryVo.getCampusname());
        }
        if(!StringUtils.isEmpty(campuscode)) {
            wrapper.eq("campuscode", campusSetQueryVo.getCampuscode());
        }

        //Call method to implement paging query
        IPage<CampusSet> pageCampusSet = campusSetService.page(page, wrapper);

        //result
        return Result.ok(pageCampusSet);
    }

    //4 Add campus settings
    @PostMapping("saveCampusSet")
    public Result saveCampusSet(@RequestBody CampusSet campusSet) {
        //set status 1:use 0:not-used
        campusSet.setStatus(1);
        //signature key
        Random random = new Random();
        campusSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        //use service
        boolean save = campusSetService.save(campusSet);
        if(save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //5 Get campus settings by id
    @GetMapping("getCampusSet/{id}")
    public Result getCampusSet(@PathVariable Long id) {
//        try {
//            //Mock exception
//            int a = 1/0;
//        }catch (Exception e) {
//            throw new NustudyException("fail",201);
//        }

        CampusSet campusSet = campusSetService.getById(id);
        return Result.ok(campusSet);
    }

    //6 Modify campus settings
    @PostMapping("updateCampusSet")
    public Result updateCampusSet(@RequestBody CampusSet campusSet) {
        boolean flag = campusSetService.updateById(campusSet);
        if(flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //7 Batch delete campus settings
    @DeleteMapping("batchRemove")
    public Result batchRemoveCampusSet(@RequestBody List<Long> idList) {
        campusSetService.removeByIds(idList);
        return Result.ok();
    }

    //8 Hospital Settings Lock and Unlock
    @PutMapping("lockCampusSet/{id}/{status}")
    public Result lockCampusSet(@PathVariable Long id,
                                @PathVariable Integer status) {
        //Query campus setting information based on id
        CampusSet campusSet = campusSetService.getById(id);
        //set state
        campusSet.setStatus(status);
        //use function
        campusSetService.updateById(campusSet);
        return Result.ok();
    }


    //9 Send signing key
    @PutMapping("sendKey/{id}")
    public Result lockCampusSet(@PathVariable Long id) {
        CampusSet campusSet = campusSetService.getById(id);
        String signKey = campusSet.getSignKey();
        String campuscode = campusSet.getCampuscode();
        //TODO text
        return Result.ok();
    }
}
