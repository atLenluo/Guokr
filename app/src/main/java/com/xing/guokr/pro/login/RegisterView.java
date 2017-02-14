package com.xing.guokr.pro.login;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.User;
import com.xing.guokr.bean.VeryCode;

import java.net.URL;

public interface RegisterView extends MvpView {

    void setVeryCode(VeryCode code);

    void setVeryTelResult(JsonResult<User> result);

    void showError(Throwable e);

    void showLoading();
}
