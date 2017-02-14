package com.xing.guokr.http.service;


import com.xing.guokr.http.HttpApi;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.VeryCode;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

// 验证码生成服务，不需要缓存
public interface VeryCodeService {

    @GET(HttpApi.PATH_VERYCODE)
    Observable<JsonResult<VeryCode>> getVeryCode(@QueryMap Map<String, Object> paramMap);

}
