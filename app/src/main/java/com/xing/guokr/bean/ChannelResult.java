package com.xing.guokr.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */

public class ChannelResult {

    private Integer totalNum;
    private Integer ret_code;
    private List<Channel> channelList;

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(Integer ret_code) {
        this.ret_code = ret_code;
    }

    public List<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }
}
