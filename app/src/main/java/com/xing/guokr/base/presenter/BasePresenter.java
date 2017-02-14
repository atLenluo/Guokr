package com.xing.guokr.base.presenter;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * 普通的Presenter
 */
public class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {

    private Context mContext;

    public BasePresenter(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}
