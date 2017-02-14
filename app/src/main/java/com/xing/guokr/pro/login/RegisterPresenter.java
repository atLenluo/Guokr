package com.xing.guokr.pro.login;

import android.content.Context;

import com.xing.guokr.base.presenter.BaseRxPresenter;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.User;
import com.xing.guokr.bean.VeryCode;
import com.xing.guokr.http.RetrofitManager;
import com.xing.guokr.http.service.GuokrService;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class RegisterPresenter extends BaseRxPresenter<RegisterView> {

    private VeryCodeGen mVeryCodeGen;
    private GuokrService mGuokrService;

    public RegisterPresenter(Context context) {
        super(context);
        mVeryCodeGen = new VeryCodeGen();
        Retrofit retrofit = RetrofitManager.getRetrofit();
        mGuokrService = retrofit.create(GuokrService.class);
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

    public void veryTel(String tel) {

        if (isViewAttached()) {
            getView().showLoading();
        }

        Observable<JsonResult<User>> resultObservable = mGuokrService.veryTel(tel);

        subscribe(resultObservable, new Subscriber<JsonResult<User>>() {
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
                    getView().setVeryTelResult(jsonResult);
                }
            }
        });
    }
}
