package com.group2.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;

import java.util.Map;

public class TestRead {
    public static void main(String[] args) {
        String fileName = "excel/1.xlsx";
        EasyExcel.read(fileName, UserData.class, new ExcelListener()).sheet().doRead();

    }

    public static class ExcelListener extends AnalysisEventListener<UserData> {
        // read contend line by line and begin from line 2
        @Override
        public void invoke(UserData userData, AnalysisContext analysisContext) {
            System.out.println(userData);
        }

        // run after reading finished
        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        }

        @Override
        public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
            System.out.println("header info: " + headMap);
        }
    }
}
