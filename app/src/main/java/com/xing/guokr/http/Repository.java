package com.xing.guokr.http;


import android.text.TextUtils;

import com.xing.guokr.GuokrApplicaton;
import com.xing.guokr.bean.ChannelResult;
import com.xing.guokr.bean.HotResult;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.NewsResult;
import com.xing.guokr.http.service.HotService;
import com.xing.guokr.http.service.NewsService;

import java.util.HashMap;
import java.util.Map;

import io.rx_cache.DynamicKeyGroup;
import io.rx_cache.EvictDynamicKeyGroup;
import io.rx_cache.EvictProvider;
import rx.Observable;
import rx.functions.Func1;

public class Repository {

    private CacheProvider mProvider;
    private NewsService mNewsService;
    private HotService mHotService;

    public Repository() {
        mProvider = GuokrApplicaton.getCacheProvider();
        mNewsService = RetrofitManager.getRetrofit().create(NewsService.class);
        mHotService = RetrofitManager.getRetrofit().create(HotService.class);
    }

    /**
     * 获取新闻频道
     * @param update 是否读取缓存
     * @return
     */
    public Observable<ChannelResult> getChannelList(boolean update) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpApi.Params.SHOWAPI_APPID, HttpApi.APPID);
        paramMap.put(HttpApi.Params.SHOWAPI_SIGN, HttpApi.SECRET);
        Observable<ChannelResult> observable = mNewsService.getChannelList(paramMap)
                .map(new Func1<JsonResult<ChannelResult>, ChannelResult>() {
                    @Override
                    public ChannelResult call(JsonResult<ChannelResult> jsonResult) {
                        return jsonResult.getShowapi_res_body();
                    }
                });
        return mProvider.getChannelList(observable, new EvictProvider(update));
    }

    /**
     * 获取新闻列表
     * @param channelId 频道ID
     * @param page 第几页
     * @param update 是否读取缓存
     * @return
     */
    public Observable<NewsResult> getNewsList(String channelId, int page, boolean update) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpApi.Params.SHOWAPI_APPID, HttpApi.APPID);
        paramMap.put(HttpApi.Params.SHOWAPI_SIGN, HttpApi.SECRET);
        paramMap.put(HttpApi.Params.CHANNELID, channelId);
        paramMap.put(HttpApi.Params.PAGE, page);
        Observable<NewsResult> observable = mNewsService.getNewsList(paramMap)
                .map(new Func1<JsonResult<NewsResult>, NewsResult>() {
                    @Override
                    public NewsResult call(JsonResult<NewsResult> result) {
                        return result.getShowapi_res_body();
                    }
                });
        DynamicKeyGroup keyGroup;
        if(TextUtils.isEmpty(channelId)) {
            // 推荐频道
            keyGroup = new DynamicKeyGroup("recommend", page);
        } else {
            keyGroup = new DynamicKeyGroup(channelId, page);
        }
        return mProvider.getNewsList(observable, keyGroup, new EvictDynamicKeyGroup(update));
    }

    /**
     * 搜索新闻
     * @param keyWord  关键词
     * @param page 第几页
     * @param update 是否更新
     * @return
     */
    public Observable<NewsResult> searchNews(String keyWord, int page, boolean update) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpApi.Params.SHOWAPI_APPID, HttpApi.APPID);
        paramMap.put(HttpApi.Params.SHOWAPI_SIGN, HttpApi.SECRET);
        paramMap.put(HttpApi.Params.TITLE, keyWord);
        paramMap.put(HttpApi.Params.PAGE, page);
        Observable<NewsResult> observable = mNewsService.getNewsList(paramMap)
                .map(new Func1<JsonResult<NewsResult>, NewsResult>() {
            @Override
            public NewsResult call(JsonResult<NewsResult> result) {
                return result.getShowapi_res_body();
            }
        });
        return mProvider.getNewsList(observable, new DynamicKeyGroup(keyWord, page),
                new EvictDynamicKeyGroup(update));
    }

    /**
     * 获取新闻热搜榜
     * @param num 获取热搜新闻的条数
     * @param update
     * @return
     */
    public Observable<HotResult> getHotList(int num, boolean update) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpApi.Params.SHOWAPI_APPID, HttpApi.APPID);
        paramMap.put(HttpApi.Params.SHOWAPI_SIGN, HttpApi.SECRET);
        paramMap.put(HttpApi.Params.N, num);
        Observable<HotResult> observable = mHotService.getHotList(paramMap)
                .map(new Func1<JsonResult<HotResult>, HotResult>() {
                    @Override
                    public HotResult call(JsonResult<HotResult> jsonResult) {
                        return jsonResult.getShowapi_res_body();
                    }
                });
        return mProvider.getHotList(observable, new EvictProvider(update));
    }

}
