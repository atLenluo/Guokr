package com.xing.guokr.pro.detail;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xing.guokr.R;
import com.xing.guokr.base.view.BaseMvpActivity;
import com.xing.guokr.bean.NewsItem;
import com.xing.guokr.pro.prefer.AppConfig;
import com.xing.guokr.utils.ShareUtils;

import cn.sharesdk.framework.ShareSDK;

public class DetailActivity extends BaseMvpActivity<DetailView, DetailPresenter>
        implements DetailView {

    private static final String NEWS_ITEM = "news_item";

    private ContentLoadingProgressBar progressBar;
    private NewsItem mNewsItem;

    public static void start(Context context, NewsItem newsItem) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(NEWS_ITEM, newsItem);
        context.startActivity(intent);
        Activity activity = context instanceof Activity ? ((Activity) context) : null;
        if (activity != null) {
            activity.overridePendingTransition(R.anim.slide_in_bottom, 0);
        }
    }

    @Override
    protected void initView() {
        ShareSDK.initSDK(this);

        // 初始化toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // WebView初始化
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.show();
                ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", 0, 98);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(500);
                animator.start();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.hide();
            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setTextZoom(AppConfig.isLargeFont(this) ? 150 : 100);
        mNewsItem = getIntent().getParcelableExtra(NEWS_ITEM);
        if (mNewsItem != null) {
            webView.loadUrl(mNewsItem.getLink());
        }

        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_article_detail;
    }

    @NonNull
    @Override
    public DetailPresenter createPresenter() {
        return new DetailPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_article_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.share:
                ShareUtils.shareNews(this, mNewsItem);
                break;
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_bottom);
    }
}
