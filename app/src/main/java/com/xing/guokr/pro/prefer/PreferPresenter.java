package com.xing.guokr.pro.prefer;

import android.content.Context;
import android.text.format.Formatter;

import com.xing.guokr.R;
import com.xing.guokr.base.presenter.BaseRxPresenter;
import com.xing.guokr.utils.ToastUtils;

import rx.Observable;
import rx.Subscriber;

public class PreferPresenter extends BaseRxPresenter<PreferView> {

    private CacheManager mCacheManager;

    public PreferPresenter(Context context) {
        super(context);
        mCacheManager = new CacheManager(context);
    }

    // 计算缓存大小
    public void getCacheSize() {
        if (isViewAttached()) {
            getView().setCacheSize(R.string.setting_cache_calculating);
        }

        Observable<Long> observable = mCacheManager.getCacheSize();
        subscribe(observable, new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long size) {
                if (isViewAttached()) {
                    getView().setCacheSize(Formatter.formatFileSize(getContext(), size));
                }
            }
        });
    }


    public void clearCache() {
        if (isViewAttached()) {
            getView().showCleanCacheLoading();
        }

        Observable<Boolean> observable = mCacheManager.cleanCache();
        subscribe(observable, new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (isViewAttached()) {
                    getView().hideCleanCahceLoading();
                    ToastUtils.show(getContext(), R.string.setting_cache_clean_complete);
                    getCacheSize();
                }
            }
        });
    }
}
