package com.xing.guokr.pro.login;

import com.xing.guokr.http.HttpApi;
import com.xing.guokr.http.RetrofitManager;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.VeryCode;
import com.xing.guokr.http.service.VeryCodeService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

// 验证码生成
public class VeryCodeGen {

    private final VeryCodeService mVeryCodeService;
    // 验证码生成集合
    private final String producer = "abcdefghijklmnopqrstuvwxyz0123456789";

    public VeryCodeGen() {
        Retrofit retrofit = RetrofitManager.getRetrofit();
        mVeryCodeService = retrofit.create(VeryCodeService.class);
    }

    /**
     * 获取验证码
     * @param len 验证码长度
     * @return
     */
    public Observable<VeryCode> getVeryCode(int len) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(HttpApi.Params.SHOWAPI_APPID, HttpApi.APPID);
        paramMap.put(HttpApi.Params.SHOWAPI_SIGN, HttpApi.SECRET);
        paramMap.put(HttpApi.Params.TEXTPRODUCER_CHAR_STRING, producer);
        paramMap.put(HttpApi.Params.TEXTPRODUCER_CHAR_LENGTH, len);
        paramMap.put(HttpApi.Params.BORDER, "no");
        Observable<JsonResult<VeryCode>> result = mVeryCodeService.getVeryCode(paramMap);
        return result.map(new Func1<JsonResult<VeryCode>, VeryCode>() {
            @Override
            public VeryCode call(JsonResult<VeryCode> jsonResult) {
                return jsonResult.getShowapi_res_body();
            }
        });
    }

}
