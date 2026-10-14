package com.group2.cmn.controller;

import com.group2.cmn.service.DictService;
import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.model.cmn.Dict;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Author: Group2
 * Description:
 */

@Tag(name = "Data Dict Api")
@RestController
@RequestMapping("/admin/cmn/dict")
//@CrossOrigin
public class DictController {

    @Autowired
    private DictService dictService;

    //export data dic interface
    @Operation(summary = "exportData")
    @GetMapping("exportData")
    public void exportDict(HttpServletResponse response) {
        dictService.exportDict(response);
    }

    //import data dic interface
    @Operation(summary = "importData")
    @PostMapping("importData")
    public Result importDict(MultipartFile file) throws IOException {
        dictService.importDict(file);
        return Result.ok();
    }

    //Query sub-data list based on data id
    @Operation(summary = "Lookup child data list based on id")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id) {
        List<Dict> list = dictService.findChildData(id);
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

    @Operation(summary = "get childer node by dict code")
    @GetMapping(value = "findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode) {
        System.out.println("Test");
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }


}
