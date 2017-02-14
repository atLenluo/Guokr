package com.xing.guokr.pro.home;

import android.content.Context;

import com.xing.guokr.base.presenter.BaseRxLcePresenter;
import com.xing.guokr.http.Repository;
import com.xing.guokr.bean.NewsItem;
import com.xing.guokr.bean.NewsResult;
import com.xing.guokr.utils.LogUtils;
import com.xing.guokr.utils.NetUtils;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class PagePresenter extends BaseRxLcePresenter<PageView, List<NewsItem>> {

    private int mCurrentPage; // 当前请求的页
    private Repository mRepository;

    public PagePresenter(Context context) {
        super(context);
        mRepository = new Repository();
    }

    public void getNewsList(String channelId, boolean pullToRefresh) {
        boolean netAvailable = NetUtils.isNetAvailable(getContext());
        if (!netAvailable) {
            if (isViewAttached() && mCurrentPage > 0) {
                // 仅在第一次之后请求数据没有网络的情况下显示错误信息
                getView().showError(new Exception("%>_<% 没有网络"), pullToRefresh);
                return;
            }
        }

        mCurrentPage = pullToRefresh ? 1 : mCurrentPage + 1;

        Observable<NewsResult> observable = mRepository.getNewsList(channelId, mCurrentPage,
                pullToRefresh && netAvailable);

        subscribe(observable.map(new Func1<NewsResult, List<NewsItem>>() {
            @Override
            public List<NewsItem> call(NewsResult newsResult) {
                if (newsResult != null) {
                    return newsResult.getPagebean().getContentlist();
                } else {
                    return null;
                }
            }
        }), pullToRefresh);
    }

    @Override
    protected void onNext(List<NewsItem> data) {
        super.onNext(data);

        LogUtils.e("获取新闻列表："+data.size());
    }

    @Override
    protected void onCompleted() {
        super.onCompleted();
        LogUtils.e("获取新闻列表完成");
    }

    @Override
    protected void onError(Throwable e, boolean pullToRefresh) {
        unsubscribe();
        LogUtils.e("获取新闻列表出错"+e.getMessage());
        if(isViewAttached()) {
            getView().showError(new Exception("%>_<% 数据获取出错"), pullToRefresh);
        }
    }
}
