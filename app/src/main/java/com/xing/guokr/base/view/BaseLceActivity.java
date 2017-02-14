package com.xing.guokr.base.view;


import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

public abstract class BaseLceActivity<M, V extends MvpView, P extends MvpPresenter<V>>
        extends MvpActivity<V, P>
        implements MvpLceView<M> {

    @Override
    public void showLoading(boolean pullToRefresh) {}

    @Override
    public void showContent() {}

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {}

    @Override
    public void setData(M data) {}

    @Override
    public void loadData(boolean pullToRefresh) {}
}
