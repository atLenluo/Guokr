package com.xing.guokr.pro.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xing.guokr.R;
import com.xing.guokr.base.adapter.SimpleOnItemClickListener;
import com.xing.guokr.base.view.BaseLceFragment;
import com.xing.guokr.event.MessageBarEvent;
import com.xing.guokr.bean.NewsItem;
import com.xing.guokr.pro.detail.DetailActivity;
import com.xing.guokr.widget.MessageBar;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PageFragment extends BaseLceFragment<List<NewsItem>, PageView, PagePresenter>
        implements PageView {

    private static final String CHANNEL_ID = "channel_id";
    private static final String NAME = "name";

    private String mChannelId;
    private String mName;

    private XRecyclerView recyclerView;
    private PageAdapter mPageAdapter;
    private List<NewsItem> mNewsList;

    public static PageFragment newInstance(String channelId, String name) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString(CHANNEL_ID, channelId);
        args.putString(NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_page;
    }

    @Override
    protected void initView(View contentView) {
        recyclerView = ((XRecyclerView) contentView.findViewById(R.id.recyclerView));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (mNewsList != null) {
                    mNewsList.clear();
                }
                loadData(true);
            }

            @Override
            public void onLoadMore() {
                loadData(false);
            }
        });
        mPageAdapter = new PageAdapter(null);
        mPageAdapter.setOnItemClickedListener(new SimpleOnItemClickListener(){
            @Override
            public void onItemClicked(RecyclerView.ViewHolder holder, int position) {
                DetailActivity.start(getContext(), mPageAdapter.getItem(position));
            }
        });
        recyclerView.setAdapter(mPageAdapter);
    }

    @Override
    public PagePresenter createPresenter() {
        return new PagePresenter(getContext());
    }

    @Override
    protected void initData() {
        mChannelId = getArguments().getString(CHANNEL_ID);
        mName = getArguments().getString(NAME);
        // 获取新闻列表
        loadData(true);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().getNewsList(mChannelId, pullToRefresh);
    }

    @Override
    public void setData(List<NewsItem> data) {
        if (mNewsList == null || mNewsList.isEmpty()) {
            // 下拉刷新
            mNewsList = data;
            recyclerView.refreshComplete();
        } else {
            // 加载更多
            mNewsList.addAll(data);
            recyclerView.loadMoreComplete();
        }
        mPageAdapter.setData(mNewsList);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        EventBus.getDefault().post(new MessageBarEvent(0, e.getMessage(),
                MessageBar.MESSAGE_BAR_ALERT));
        if (pullToRefresh) {
            recyclerView.refreshComplete();
        } else {
            recyclerView.loadMoreComplete();
        }
    }

}
