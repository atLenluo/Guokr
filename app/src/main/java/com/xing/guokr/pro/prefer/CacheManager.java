package com.xing.guokr.pro.prefer;


import android.content.Context;

import com.xing.guokr.utils.FileUtils;

import rx.Observable;

public class CacheManager {

    private Context mContext;

    public CacheManager(Context context) {
        mContext = context;
    }

    public Observable<Long> getCacheSize() {
        return Observable.just(FileUtils.getFileSize(mContext.getCacheDir()));
    }

    public Observable<Boolean> cleanCache() {
        return Observable.just(FileUtils.deleteFile(mContext.getCacheDir()));
    }

}
