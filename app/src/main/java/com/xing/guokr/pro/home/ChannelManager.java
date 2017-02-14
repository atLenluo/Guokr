package com.xing.guokr.pro.home;

import com.xing.guokr.GuokrApplicaton;
import com.xing.guokr.http.Repository;
import com.xing.guokr.bean.Channel;
import com.xing.guokr.bean.ChannelDao;
import com.xing.guokr.bean.ChannelResult;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class ChannelManager {

    private final ChannelDao mChannelDao;

    public ChannelManager() {
        mChannelDao = GuokrApplicaton.getDaoSession().getChannelDao();
    }

    /**
     * 获取关注频道
     * @return
     */
    public Observable<List<Channel>> getAttentionChannelList() {
        // 从网络获取
        Observable<List<Channel>> netChannel = getChannelsFromNet();
        if(mChannelDao.count() > 0) {
            // 过滤掉不关注的频道
            netChannel = netChannel.map(new Func1<List<Channel>, List<Channel>>() {
                @Override
                public List<Channel> call(List<Channel> channels) {
                    channels.retainAll(mChannelDao.loadAll()); // 与数据库中的频道取交集
                    // 加入推荐频道
                    channels.add(0, new Channel("", "推荐最新"));
                    return channels;
                }
            });
        } else {
            // 数据库中没有关注的频道
            netChannel = netChannel.map(new Func1<List<Channel>, List<Channel>>() {
                @Override
                public List<Channel> call(List<Channel> channels) {
                    channels = channels.subList(0, 7);
                    // 向数据库写入关注的频道
                    mChannelDao.insertInTx(channels);
                    // 加入推荐频道
                    channels.add(0, new Channel("", "推荐最新"));
                    return channels;
                }
            });
        }
        return netChannel;
    }

    /**
     * 从网络获取新闻频道
     * @return
     */
    public Observable<List<Channel>> getChannelsFromNet() {
        Repository repository = new Repository();
        // 从网络获取
        Observable<ChannelResult> netChannelResult = repository.getChannelList(false);
        Observable<List<Channel>> netChannel = netChannelResult.map(new Func1<ChannelResult,
                List<Channel>>() {
            @Override
            public List<Channel> call(ChannelResult channelResult) {
                return channelResult.getChannelList();
            }
        });
        return netChannel;
    }

    /**
     * 获取不关注的频道
     * @return
     */
    public Observable<List<Channel>> getNoAttentionChannels() {
        Observable<List<Channel>> netChannel = getChannelsFromNet();
        if(mChannelDao.count() > 0) {
            // 过滤关注的频道
            netChannel = netChannel.map(new Func1<List<Channel>, List<Channel>>() {
                @Override
                public List<Channel> call(List<Channel> channels) {
                    // 与数据库中的频道取差集
                    channels.removeAll(mChannelDao.loadAll());
                    return channels;
                }
            });
        } else {
            netChannel = netChannel.map(new Func1<List<Channel>, List<Channel>>() {
                @Override
                public List<Channel> call(List<Channel> channels) {
                    return channels.subList(7, channels.size());
                }
            });
        }
        return netChannel;
    }

    public void removeAttentionChannel(Channel channel) {
        mChannelDao.deleteByKey(mChannelDao.getKey(channel));
    }

    public void addAttentionChannel(Channel channel) {
        mChannelDao.insert(channel);
    }
}
