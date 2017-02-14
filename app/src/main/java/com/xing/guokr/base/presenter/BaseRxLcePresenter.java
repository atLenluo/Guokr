package com.xing.guokr.base.presenter;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 使用RxJava获取数据的Presenter
 */
public abstract class BaseRxLcePresenter<V extends MvpLceView<M>, M> extends MvpBasePresenter<V> {

    protected Subscriber<M> subscriber;
    private Context mContext;


    public BaseRxLcePresenter(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    protected void unsubscribe() {
        if (subscriber != null && !subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }

        subscriber = null;
    }

    public void subscribe(Observable<M> observable, final boolean pullToRefresh) {
        unsubscribe();
        subscriber = new Subscriber<M>() {
            private boolean ptr = pullToRefresh;

            @Override
            public void onCompleted() {
                BaseRxLcePresenter.this.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                BaseRxLcePresenter.this.onError(e, ptr);
            }

            @Override
            public void onNext(M m) {
                BaseRxLcePresenter.this.onNext(m);
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    protected void onCompleted() {
        if(isViewAttached()) {
            getView().showContent();
        }

        unsubscribe();
    }

    protected void onError(Throwable e, boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showError(e, pullToRefresh);
        }

        unsubscribe();
    }

    protected void onNext(M data) {
        if (isViewAttached()) {
            getView().setData(data);
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            unsubscribe();
        }
    }
}
