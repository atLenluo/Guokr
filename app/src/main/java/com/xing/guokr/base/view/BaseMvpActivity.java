package com.xing.guokr.base.view;

import android.os.Bundle;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

public abstract  class BaseMvpActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpActivity<V, P> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        initView();
    }

    protected abstract void initView();

    public abstract int getLayoutRes();
}
