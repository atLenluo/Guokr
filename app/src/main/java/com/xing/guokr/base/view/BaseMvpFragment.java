package com.xing.guokr.base.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.xing.guokr.base.BackPressListener;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseMvpFragment<V extends MvpView, P extends MvpPresenter<V>>
        extends MvpFragment<V, P>
        implements BackPressListener {

    private View contentView;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(getLayoutRes(), container, false);
            initView(contentView);
        }

        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent != null) {
            parent.removeView(contentView);
        }

        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected void initData() {

    }

    protected abstract void initView(View contentView);

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
