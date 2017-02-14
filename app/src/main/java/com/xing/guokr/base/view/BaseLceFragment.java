package com.xing.guokr.base.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * 懒加载功能、LCE的Fragment
 */
public abstract class BaseLceFragment<M, V extends MvpView, P extends MvpPresenter<V>>
        extends MvpFragment<V, P>
        implements MvpLceView<M> {

    private View contentView;
    private boolean isViewInit = false;
    private boolean isDataInit = false;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(getLayoutRes(), container, false);
            initView(contentView);
            isViewInit = true;
        }

        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent != null) {
            parent.removeView(contentView);
        }

        return contentView;
    }

    protected abstract void initView(View contentView);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewInit = isDataInit = false;
    }

    private void lazyLoad() {
        boolean visibleHint = getUserVisibleHint();
        if(visibleHint) {
            if(isViewInit && !isDataInit) {
                initData();
                isDataInit = true;
            }
        }
    }

    protected void initData() {

    }

    @Override
    public void showLoading(boolean pullToRefresh) {}

    @Override
    public void showError(Throwable e, boolean pullToRefresh) { }

    @Override
    public void setData(M data) { }

    @Override
    public void showContent() { }

    @Override
    public void loadData(boolean pullToRefresh) { }
}
