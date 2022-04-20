package com.group2.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.LinkedList;
import java.util.List;

public class TestWrite {
    public static void main(String[] args) {
        //
        List<UserData> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            UserData data = new UserData();
            data.setId(i);
            data.setUsername("user" + i);
            list.add(data);
        }
        String fileName = "excel/1.xlsx";
        EasyExcel.write(fileName, UserData.class).sheet("user info")
                .doWrite(list);
    }
}
