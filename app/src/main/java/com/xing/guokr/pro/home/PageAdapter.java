package com.xing.guokr.pro.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xing.guokr.R;
import com.xing.guokr.base.adapter.BaseRecyclerViewAdapter;
import com.xing.guokr.bean.NewsItem;
import com.xing.guokr.utils.ShareUtils;

import java.util.List;

public class PageAdapter extends BaseRecyclerViewAdapter<NewsItem, PageAdapter.PageHolder> {

    public PageAdapter(List<NewsItem> data) {
        super(data);
    }

    @Override
    public PageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_article_card, parent, false);
        return new PageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PageHolder holder, final int position) {
        final NewsItem item = mData.get(position);
        holder.card_source_name.setText(item.getSource());
        holder.card_category.setText(item.getChannelName());
        holder.article_title.setText(item.getTitle());
        holder.article_summary.setText(item.getDesc());
        if (item.isHavePic()) {
            holder.article_image.setVisibility(View.VISIBLE);
            Glide.with(holder.article_image.getContext())
                    .load(item.getImageurls().get(0).getUrl())
                    .asBitmap()
                    .into(holder.article_image);
        } else {
            holder.article_image.setVisibility(View.GONE);
        }

        // 新闻详情
        holder.home_item_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClicked(holder, position);
                }
            }
        });

        // 分享
        holder.share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareNews(v.getContext(), item);
            }
        });
    }

    class PageHolder extends RecyclerView.ViewHolder {

        public View home_item_holder;
        public TextView card_source_name, card_category;
        public TextView article_title, article_summary;
        public ImageView article_image;
        public View share_btn;


        public PageHolder(View itemView) {
            super(itemView);
            home_item_holder = itemView;
            card_source_name = (TextView) itemView.findViewById(R.id.card_source_name);
            card_category = (TextView) itemView.findViewById(R.id.card_category);
            article_title = (TextView) itemView.findViewById(R.id.article_title);
            article_summary = (TextView) itemView.findViewById(R.id.article_summary);
            article_image = (ImageView) itemView.findViewById(R.id.article_image);
            share_btn = itemView.findViewById(R.id.share_btn);
        }
    }
}
