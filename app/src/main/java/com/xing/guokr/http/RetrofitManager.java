package com.xing.guokr.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/1/12.
 */

public class RetrofitManager {

    private static Retrofit mRetrofit;

    static {
        // 构建okhttp
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        // 构建retrofit
        mRetrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(HttpApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static Retrofit getRetrofit() {
        return mRetrofit;
    }
}
