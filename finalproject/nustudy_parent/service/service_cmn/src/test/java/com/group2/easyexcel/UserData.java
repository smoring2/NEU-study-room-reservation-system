package com.group2.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class UserData {
    @ExcelProperty(value = "user index", index = 0)
    private int id;
    @ExcelProperty(value = "username", index = 1)
    private String username;



}
