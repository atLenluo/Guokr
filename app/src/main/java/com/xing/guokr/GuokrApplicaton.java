package com.xing.guokr;

import android.app.Application;

import com.xing.guokr.bean.User;
import com.xing.guokr.http.CacheProvider;
import com.xing.guokr.bean.DaoMaster;
import com.xing.guokr.bean.DaoSession;

import java.io.File;

import io.rx_cache.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

public class GuokrApplicaton extends Application {

    private static DaoSession mDaoSession;
    private static GuokrApplicaton mApp;
    private static CacheProvider mCacheProvider;

    private static User mUser; // 登录后的用户信息

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        // 初始化数据库
        mDaoSession = DaoMaster.newDevSession(this, "guokr");
        // 初始化缓存对象
        File cacheDir = new File(getCacheDir(), "response");
        if(!cacheDir.exists()) { cacheDir.mkdir(); }
        mCacheProvider = new RxCache.Builder()
                .persistence(cacheDir, new GsonSpeaker())
                .using(CacheProvider.class);
    }

    public static GuokrApplicaton getApp() {
        return mApp;
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static CacheProvider getCacheProvider() {
        return mCacheProvider;
    }

    public static void setUser(User user) {
        GuokrApplicaton.mUser = user;
    }

    public static User getUser() {
        return mUser;
    }
}
