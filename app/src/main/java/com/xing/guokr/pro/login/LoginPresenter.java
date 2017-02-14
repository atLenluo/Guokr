package com.xing.guokr.pro.login;


import android.content.Context;

import com.xing.guokr.base.presenter.BaseRxPresenter;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.User;
import com.xing.guokr.bean.VeryCode;
import com.xing.guokr.http.RetrofitManager;
import com.xing.guokr.http.service.GuokrService;
import com.xing.guokr.utils.StringFormatUtils;
import com.xing.guokr.utils.VerificationUtils;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class LoginPresenter extends BaseRxPresenter<LoginView> {

    private VeryCodeGen mVeryCodeGen;
    private GuokrService mGuokrService;

    public LoginPresenter(Context context) {
        super(context);
        mVeryCodeGen = new VeryCodeGen();
        mGuokrService = RetrofitManager.getRetrofit().create(GuokrService.class);
    }

    public void getcaptcha() {
        Observable<VeryCode> observable = mVeryCodeGen.getVeryCode(4);
        subscribe(observable, new Action1<VeryCode>() {
            @Override
            public void call(VeryCode code) {
                if (isViewAttached()) {
                    getView().setVeryCode(code);
                }
            }
        });
    }

    public void login(String username, String password) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        Observable<JsonResult<User>> observable = mGuokrService.login(username,
                VerificationUtils.encodeByMD5(password));

        subscribe(observable, new Subscriber<JsonResult<User>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e);
                }
            }

            @Override
            public void onNext(JsonResult<User> result) {
                if (isViewAttached()) {
                    getView().setLoginResult(result);
                }
            }
        });
    }
}
