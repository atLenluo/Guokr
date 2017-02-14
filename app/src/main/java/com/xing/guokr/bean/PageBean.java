package com.xing.guokr.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */

public class PageBean {

    private Integer allPages;
    private List<NewsItem> contentlist;
    private Integer currentPage;
    private Integer allNum;
    private Integer maxResult;

    public Integer getAllPages() {
        return allPages;
    }

    public void setAllPages(Integer allPages) {
        this.allPages = allPages;
    }

    public List<NewsItem> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<NewsItem> contentlist) {
        this.contentlist = contentlist;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    public Integer getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(Integer maxResult) {
        this.maxResult = maxResult;
    }
}
