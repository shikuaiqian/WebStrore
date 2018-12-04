package com.cskaoyan.utils;

import java.util.List;

public class PageHelper<T> {
    public static final int CATEGORY_PER_PAGE = 3;
    public static final int ADMIN_PER_PAGE = 2;
    public static final int PRODUCT_PER_PAGE = 5;
    public static final int PRODUCT_SEARCH_PER_PAGE = 3;
    public static final int USER_PER_PAGE = 4;
    public static final int PRODUCT_PER_PAGE_FRONT_END = 9;
    public static final int ORDER_PER_PAGE = 4;

    // 总记录数据项数
    private int totalRecordsNum;
    // 当前页码
    private int currentPageNum;
    // 总页数
    private int totalPageNum;
    // 上一页
    private int prevPageNum;
    // 下一页
    private int nextPageNum;

    // 记录数据
    private List<T> records;

    public PageHelper() {
    }

    public PageHelper(int pageNumber, int totalNumber, int pageCount) {

        //当前页码
        this.currentPageNum=pageNumber;

        //总记录数
        this.totalRecordsNum = totalNumber;

        //总页码
        this.totalPageNum = (totalRecordsNum +pageCount -1 )/ pageCount  ;

        //上一页
        this.prevPageNum = currentPageNum - 1 == 0 ? currentPageNum : currentPageNum - 1;

        // 下一页 nextPageNum
        this.nextPageNum = currentPageNum + 1 > this.totalPageNum ? currentPageNum : currentPageNum + 1;


    }

    public int getTotalRecordsNum() {
        return totalRecordsNum;
    }

    public void setTotalRecordsNum(int totalRecordsNum, int perPage) {
        this.totalRecordsNum = totalRecordsNum;
        // 总页数 totalPageNum
        int i = totalRecordsNum / perPage;
        this.totalPageNum = totalRecordsNum % perPage == 0 ? i : i + 1;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
        // 上一页 prevPageNum
        this.prevPageNum = currentPageNum - 1 == 0 ? currentPageNum : currentPageNum - 1;
        // 下一页 nextPageNum
        this.nextPageNum = currentPageNum + 1 > this.totalPageNum ? currentPageNum : currentPageNum + 1;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getPrevPageNum() {
        return prevPageNum;
    }

    public void setPrevPageNum(int prevPageNum) {
        this.prevPageNum = prevPageNum;
    }

    public int getNextPageNum() {
        return nextPageNum;
    }

    public void setNextPageNum(int nextPageNum) {
        this.nextPageNum = nextPageNum;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {

        this.records = records;
    }

    public void setTotalRecordsNum(int totalRecordsNum) {
        this.totalRecordsNum = totalRecordsNum;
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalRecordsNum=" + totalRecordsNum +
                ", currentPageNum=" + currentPageNum +
                ", totalPageNum=" + totalPageNum +
                ", prevPageNum=" + prevPageNum +
                ", nextPageNum=" + nextPageNum +
                ", records=" + records +
                '}';
    }
 }
