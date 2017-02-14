package com.xing.guokr.pro.login;


import android.content.Context;

import com.xing.guokr.base.presenter.BaseRxPresenter;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.User;
import com.xing.guokr.http.RetrofitManager;
import com.xing.guokr.http.service.GuokrService;
import com.xing.guokr.utils.VerificationUtils;

import rx.Observable;
import rx.Subscriber;

public class CompletePresenter extends BaseRxPresenter<CompleteView> {

    private GuokrService mGuokrService;

    public CompletePresenter(Context context) {
        super(context);
        mGuokrService = RetrofitManager.getRetrofit().create(GuokrService.class);
    }

    public void createAccount(String nickName, String password, String tel) {
        if(isViewAttached()) {
            getView().showLoading();
        }

        // 加密密码
        Observable<JsonResult<User>> observable = mGuokrService.createAccount(tel,
                VerificationUtils.encodeByMD5(password), nickName);
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
            public void onNext(JsonResult<User> jsonResult) {

                if (isViewAttached()) {
                    getView().setCreateResult(jsonResult);
                }
            }
        });
    }
}
