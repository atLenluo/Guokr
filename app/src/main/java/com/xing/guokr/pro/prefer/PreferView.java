package com.xing.guokr.pro.prefer;


import com.hannesdorfmann.mosby.mvp.MvpView;

public interface PreferView extends MvpView {

    void showCleanCacheLoading();

    void setCacheSize(int resId);

    void setCacheSize(String text);

    void hideCleanCahceLoading();
}
