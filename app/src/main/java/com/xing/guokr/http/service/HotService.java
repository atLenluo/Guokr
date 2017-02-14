package com.xing.guokr.http.service;

import com.xing.guokr.http.HttpApi;
import com.xing.guokr.bean.HotResult;
import com.xing.guokr.bean.JsonResult;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

// 热搜请求接口
public interface HotService {

    @GET(HttpApi.PATH_HOT)
    Observable<JsonResult<HotResult>> getHotList(@QueryMap Map<String, Object> paramMap);
}
