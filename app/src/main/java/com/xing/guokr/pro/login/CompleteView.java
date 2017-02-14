package com.xing.guokr.pro.login;


import com.hannesdorfmann.mosby.mvp.MvpView;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.User;

public interface CompleteView extends MvpView {

    void showError(Throwable e);

    void showLoading();

    void setCreateResult(JsonResult<User> result);
}
