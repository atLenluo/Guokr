package com.xing.guokr.pro.search;

import android.content.Context;

import com.xing.guokr.base.presenter.BaseRxPresenter;
import com.xing.guokr.http.Repository;
import com.xing.guokr.bean.HotItem;
import com.xing.guokr.bean.HotResult;
import com.xing.guokr.bean.NewsItem;
import com.xing.guokr.bean.NewsResult;
import com.xing.guokr.utils.LogUtils;
import com.xing.guokr.utils.NetUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class SearchPresenter extends BaseRxPresenter<SearchView> {

    private Repository mRepository;
    private int mCurrentPage = 1;

    public SearchPresenter(Context context) {
        super(context);
        mRepository = new Repository();
    }

    /**
     * 获取热搜列表
     *
     * @param num [0, 50]
     */
    public void getHotList(int num) {
        boolean netAvailable = NetUtils.isNetAvailable(getContext());
        if (!netAvailable) return;
        // 获取热搜词
        Observable<HotResult> hotResultObservable = mRepository.getHotList(num, false);
        Observable<List<HotItem>> observable = hotResultObservable.map(new Func1<HotResult,
                List<HotItem>>() {
            @Override
            public List<HotItem> call(HotResult hotResult) {
                return hotResult.getList();
            }
        });

        subscribe(observable, new Subscriber<List<HotItem>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<    HotItem> result) {
                LogUtils.e("热搜词:" + result.size());
                if (isViewAttached()) {
                    getView().setHotList(result);
                }
            }
        });

    }

    /**
     * 根据关键词搜索新闻
     *
     * @param keyWord 关键词
     */
    public void searchNews(final String keyWord, final boolean pullToRefresh) {
        boolean netAvailable = NetUtils.isNetAvailable(getContext());
        if (!netAvailable) {
            if(isViewAttached()) {
                getView().showError(null, pullToRefresh);
            }
            return;
        }

        mCurrentPage = pullToRefresh ? 1 : mCurrentPage + 1;

        Observable<NewsResult> searchObservable = mRepository.searchNews(keyWord,
                mCurrentPage, pullToRefresh);

        Observable<List<NewsItem>> observable = searchObservable.map(new Func1<NewsResult,
                List<NewsItem>>() {
            @Override
            public List<NewsItem> call(NewsResult newsResult) {
                return newsResult == null ? null : newsResult.getPagebean().getContentlist();
            }
        });

        subscribe(observable, new Subscriber<List<NewsItem>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(isViewAttached()) {
                    getView().showError(e, pullToRefresh);
                }
            }

            @Override
            public void onNext(List<NewsItem> result) {
                if (result != null && !result.isEmpty()) {
                    if(isViewAttached()) {
                        getView().setSearchResult(result, keyWord, pullToRefresh);
                    }
                } else {
                    if(isViewAttached()) {
                        getView().showEmpty();
                    }
                }
            }
        });

    }

}
