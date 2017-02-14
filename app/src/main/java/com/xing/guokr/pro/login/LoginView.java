package com.xing.guokr.pro.login;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.User;
import com.xing.guokr.bean.VeryCode;

public interface LoginView extends MvpView {

    void setVeryCode(VeryCode code);

    void showLoading();

    void showError(Throwable e);

    void setLoginResult(JsonResult<User> result);
}
