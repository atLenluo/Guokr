package com.xing.guokr.pro.home;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.xing.guokr.bean.Channel;

import java.util.List;

public interface HomeView extends MvpView {

    void setAttentionChannelList(List<Channel> channelList);

    void setNoAttentionChannels(List<Channel> list);
}
