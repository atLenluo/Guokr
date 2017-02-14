package com.xing.guokr.pro.search;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.xing.guokr.bean.HotItem;
import com.xing.guokr.bean.NewsItem;

import java.util.List;

public interface SearchView extends MvpView {

    void showError(Throwable throwable, boolean pullToRefresh);

    // 设置热搜关键词
    void setHotList(List<HotItem> hotItemList);

    void setSearchResult(List<NewsItem> newsItemList, String keyword, boolean pullToRefresh);

    void showEmpty();
}
