package com.xing.guokr.http.service;

import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.User;
import com.xing.guokr.http.HttpApi;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface GuokrService {

    @FormUrlEncoded
    @POST(HttpApi.GUOKR_URL + HttpApi.PATH_VERYTEL)
    Observable<JsonResult<User>> veryTel(@Field("tel") String tel);

    @FormUrlEncoded
    @POST(HttpApi.GUOKR_URL + HttpApi.PATH_REGISTER)
    Observable<JsonResult<User>> createAccount(@Field("tel") String tel,
                                               @Field("password") String password,
                                               @Field("nickName") String nickName);

    @FormUrlEncoded
    @POST(HttpApi.GUOKR_URL + HttpApi.PATH_LOGIN)
    Observable<JsonResult<User>> login(@Field("username") String username,
                                       @Field("password") String password);
}
