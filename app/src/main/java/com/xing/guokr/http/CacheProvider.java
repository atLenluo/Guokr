package com.xing.guokr.http;

import com.xing.guokr.bean.ChannelResult;
import com.xing.guokr.bean.HotResult;
import com.xing.guokr.bean.NewsResult;

import java.util.concurrent.TimeUnit;

import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.EvictDynamicKeyGroup;
import io.rx_cache.EvictProvider;
import io.rx_cache.LifeCache;
import rx.Observable;

public interface CacheProvider {

    @LifeCache(duration = 5, timeUnit = TimeUnit.HOURS)
    Observable<ChannelResult> getChannelList(Observable<ChannelResult> result, EvictProvider evict);

    @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
    Observable<NewsResult> getNewsList(Observable<NewsResult> result);

    @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
    Observable<NewsResult> getNewsList(Observable<NewsResult> result,
                                       DynamicKeyGroup keyGroup,
                                       EvictDynamicKeyGroup evict);

    @LifeCache(duration = 10, timeUnit = TimeUnit.MINUTES)
    Observable<HotResult> getHotList(Observable<HotResult> result, EvictProvider evict);
}
