package com.xing.guokr.bean;

/**
 * Created by Administrator on 2017/1/9.
 */

public class NewsResult {

    private Integer ret_code;
    private PageBean pagebean;

    public Integer getRet_code() {
        return ret_code;
    }

    public void setRet_code(Integer ret_code) {
        this.ret_code = ret_code;
    }

    public PageBean getPagebean() {
        return pagebean;
    }

    public void setPagebean(PageBean pagebean) {
        this.pagebean = pagebean;
    }
}
