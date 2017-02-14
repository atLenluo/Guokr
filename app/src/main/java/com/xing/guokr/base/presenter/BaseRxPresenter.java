package com.xing.guokr.base.presenter;


import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.xing.guokr.utils.LogUtils;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseRxPresenter<V extends MvpView> extends MvpBasePresenter<V> {

    private Context mContext;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();


    public BaseRxPresenter(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public void addSubscriber(Subscription subscriber) {
        compositeSubscription.add(subscriber);
    }

    public void subscribe(Observable observable, Subscriber subscriber) {

        Subscription subscribe = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        addSubscriber(subscribe);
    }

    public void subscribe(Observable observable, Action1 onNext) {
        Subscription subscribe = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext);
        addSubscriber(subscribe);
    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
            LogUtils.e("BaseRxPresenter取消订阅");
        }
    }


}
