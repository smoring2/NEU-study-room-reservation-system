package com.group2.cmn.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.group2.cmn.service.DictService;
import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */

@Api(description = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
@CrossOrigin
public class DictController {

    @Autowired
    private DictService dictService;

    //export data dic interface
    @ApiOperation(value = "exportData")
    @GetMapping("exportData")
    public void exportDict(HttpServletResponse response) {
        dictService.exportDict(response);
    }

    //import data dic interface
    @ApiOperation(value = "importData")
    @PostMapping("importData")
    public Result importDict(MultipartFile file) throws IOException {
        dictService.importDict(file);
        return Result.ok();
    }

    //根据数据id查询子数据列表
    @ApiOperation(value = "Lookup child data list based on id")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id) {
        List<Dict> list = dictService.findChlidData(id);
        return Result.ok(list);
    }

    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode,
                          @PathVariable String value) {
        String dictName = dictService.getDictName(dictCode, value);
        return dictName;
    }

    @GetMapping("getName/{value}")
    public String getName(@PathVariable String value) {
        String dictName = dictService.getDictName("", value);
        return dictName;
    }


}
