package com.xing.guokr.pro.home;

import android.content.Context;

import com.xing.guokr.base.presenter.BaseRxPresenter;
import com.xing.guokr.bean.Channel;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

// 首页presenter
public class HomePresenter extends BaseRxPresenter<HomeView> {

    private final ChannelManager mCm;

    public HomePresenter(Context context) {
        super(context);
        mCm = new ChannelManager();
    }

    /**
     * 获取关注的新闻频道
     */
    public void getAttentionChannelList() {
        // 从数据库和网络获取订阅的新闻频道
        Observable<List<Channel>> observable = mCm.getAttentionChannelList();
        subscribe(observable, new Subscriber<List<Channel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Channel> channels) {
                if (channels != null) {
                    if (isViewAttached()) {
                        getView().setAttentionChannelList(channels);
                    }
                }
            }
        });
    }

    public void getNoAttentionChannel() {
        Observable<List<Channel>> netObservable = mCm.getNoAttentionChannels();
        subscribe(netObservable, new Subscriber<List<Channel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Channel> list) {
                if (isViewAttached()) {
                    // 设置所有的频道
                    getView().setNoAttentionChannels(list);
                }
            }
        });
    }

    // 移除订阅的频道
    public void removeAttentionChannel(Channel channel) {
        mCm.removeAttentionChannel(channel);
    }

    // 添加订阅频道
    public void addAttentionChannel(Channel channel) {
        mCm.addAttentionChannel(channel);
    }
}
