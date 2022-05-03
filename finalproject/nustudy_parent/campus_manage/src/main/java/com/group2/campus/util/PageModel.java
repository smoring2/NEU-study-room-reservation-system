package com.group2.campus.util;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

public class PageModel<T> implements java.io.Serializable {


    private long total;


    private int pageSize;


    private int totalPage;


    private int pageNum = 1;

    private int[] navigatepageNums;

    private JSONArray list;

    public PageModel() {
    }

    public PageModel(JSONArray list, int pageNum, long total, int pageSize) {
        this.list = list;
        this.pageNum = pageNum;
        this.total = total;
        this.pageSize = pageSize;

        this.init();
    }

    public void init() {
        // pageSize default 5
        if (pageSize <= 0) {
            pageSize = 5;
        }

        totalPage = (int)(total / pageSize);

        if (0 != total % pageSize) {
            totalPage += 1;
        }

        if (pageNum > totalPage) {
            pageNum = totalPage;
        }
        if (pageNum < 1) {
            pageNum = 1;
        }
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public JSONArray getList() {
        return list;
    }

    public void setList(JSONArray list) {
        this.list = list;
    }

    public int[] getNavigatepageNums() {
        int beginPageIndex;//The starting index of the page number list
        int endPageIndex;//The ending index of the page number list
        if(totalPage <= 10){
            beginPageIndex = 1;
            endPageIndex = totalPage;
        }
        //If the total number of pages is more than 10 pages, a total of 10 page numbers near the current page will be displayed
        else{
            //A total of 10 page numbers near the current page (the first 4 + the current page + the last 5)
            beginPageIndex = pageNum - 4;
            endPageIndex = pageNum + 5;

            //When the previous page numbers are less than 4, the first 10 page numbers will be displayed
            if(beginPageIndex < 1){
                beginPageIndex = 1;
                endPageIndex = 10;
            }
            //When the following page numbers are less than 5, the last 10 page numbers will be displayed
            if(endPageIndex > totalPage){
                endPageIndex = totalPage;
                beginPageIndex = totalPage -10 + 1;
            }
        }
        navigatepageNums = new int[endPageIndex-beginPageIndex+1];
        int j = 0;
        for(int i=beginPageIndex; i<=endPageIndex; i++) {
            navigatepageNums[j] = i;
            j++;
        }
        return navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    @Override
    public String toString() {
        return "Page [total=" + total + ", pageSize=" + pageSize + ", totalPage=" + totalPage + ", pageNum="
                + pageNum + "]";
    }
}