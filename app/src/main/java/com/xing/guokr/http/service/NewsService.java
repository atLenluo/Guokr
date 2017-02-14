package com.xing.guokr.http.service;

import com.xing.guokr.http.HttpApi;
import com.xing.guokr.bean.ChannelResult;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.NewsResult;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/1/9.
 */

public interface NewsService {

    @GET(HttpApi.PATH_CHANNEL)
    Observable<JsonResult<ChannelResult>> getChannelList(@QueryMap Map<String, Object> paramMap);

    @GET(HttpApi.PATH_NEWS)
    Observable<JsonResult<NewsResult>> getNewsList(@QueryMap Map<String, Object> paramMap);

}
