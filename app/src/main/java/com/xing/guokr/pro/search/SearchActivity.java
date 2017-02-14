package com.xing.guokr.pro.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xing.guokr.R;
import com.xing.guokr.base.adapter.SimpleOnItemClickListener;
import com.xing.guokr.bean.HotItem;
import com.xing.guokr.bean.NewsItem;
import com.xing.guokr.pro.detail.DetailActivity;
import com.xing.guokr.utils.KeyboardUtils;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

// 搜索界面
public class SearchActivity extends MvpActivity<SearchView, SearchPresenter>
    implements SearchView {

    private FlowLayout flowLayout;
    private EditText keyWord;
    private ImageView clear;
    private XRecyclerView recyclerView;
    private TextView noResult;
    private View errorContainer;

    private SearchAdapter mSearchAdapter;
    private List<NewsItem> mSearchResult = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        getPresenter().getHotList(10);
    }

    private void initView() {
        flowLayout = (FlowLayout) findViewById(R.id.flowLayout);
        keyWord = (EditText) findViewById(R.id.keyword);
        // 监听键盘动作
        keyWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchNews(keyWord.getText().toString(), true);
                    KeyboardUtils.hideKeyboard(SearchActivity.this);
                }
                return true;
            }
        });

        clear = (ImageView) findViewById(R.id.clear);
        KeyboardUtils.hideKeyboard(this);

        recyclerView = (XRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                searchNews(keyWord.getText().toString(), false);
            }
        });
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(true);
        mSearchAdapter = new SearchAdapter(mSearchResult);
        mSearchAdapter.setKeywordColor(getResources().getColor(R.color.colorPrimary));
        recyclerView.setAdapter(mSearchAdapter);
        mSearchAdapter.setOnItemClickedListener(new SimpleOnItemClickListener(){
            @Override
            public void onItemClicked(RecyclerView.ViewHolder holder, int position) {
                DetailActivity.start(SearchActivity.this, mSearchAdapter.getItem(position));
            }
        });

        noResult = (TextView) findViewById(R.id.noResult);
        errorContainer = findViewById(R.id.errorContainer);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWord.setText("");
                goneViews();
                flowLayout.setVisibility(View.VISIBLE);
            }
        });
        keyWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                clear.setVisibility(TextUtils.isEmpty(s.toString()) ? View.GONE : View.VISIBLE);
            }
        });

        initToolbar();
    }

    @NonNull
    @Override
    public SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.search:
                searchNews(keyWord.getText().toString(), true);
                KeyboardUtils.hideKeyboard(this);
                break;
        }
        return true;
    }

    private void searchNews(String keyWord, boolean pullToRefresh) {
        if(!TextUtils.isEmpty(keyWord.trim())) {
            getPresenter().searchNews(keyWord, pullToRefresh); // 搜索新闻
        }
    }

    @Override
    public void setHotList(final List<HotItem> hotItemList) {
        // 设置热搜词数据
        flowLayout.removeAllViews(); // 移除之前添加的view

        for (int i = 0; i < hotItemList.size(); i++) {
            final TextView textView = (TextView) LayoutInflater.from(SearchActivity.this)
                    .inflate(R.layout.item_search_recommendation, flowLayout, false);
            textView.setText(hotItemList.get(i).getTitle());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 点击搜索新闻
                    searchNews(textView.getText().toString(), true);
                    keyWord.setText(textView.getText());
                }
            });
            flowLayout.addView(textView);
        }
    }

    @Override
    public void showError(Throwable throwable, boolean pullToRefresh) {
        goneViews();
        errorContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void setSearchResult(List<NewsItem> newsItemList, String keyword, boolean pullToRefresh) {
        goneViews();
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.getLayoutManager().scrollToPosition(0);
        if(pullToRefresh) {
            mSearchResult.clear();
        } else {
            recyclerView.loadMoreComplete();
        }
        mSearchResult.addAll(newsItemList);
        mSearchAdapter.setData(mSearchResult);
        mSearchAdapter.setKeyWord(keyword);
    }

    @Override
    public void showEmpty() {
        goneViews();
        noResult.setVisibility(View.VISIBLE);
        flowLayout.setVisibility(View.VISIBLE);
    }

    private void goneViews() {
        errorContainer.setVisibility(View.GONE);
        flowLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        noResult.setVisibility(View.GONE);
    }

}
