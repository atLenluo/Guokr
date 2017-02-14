package com.xing.guokr.pro.search;

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
import com.xing.guokr.utils.StringFormatUtils;

import java.util.List;

public class SearchAdapter extends BaseRecyclerViewAdapter<NewsItem, SearchAdapter.SearchHolder> {

    private String mKeyword;
    private int mKeywordColor;

    public SearchAdapter(List<NewsItem> data) {
        super(data);
    }

    public void setKeyWord(String keyWord) {
        this.mKeyword = keyWord;
    }

    public void setKeywordColor(int keywordColor) {
        this.mKeywordColor = keywordColor;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_result, parent, false);
        return new SearchHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SearchHolder holder, final int position) {
        final NewsItem newsItem = mData.get(position);

        holder.title.setText(StringFormatUtils.highlightKeyWord(newsItem.getTitle(), mKeyword,
                mKeywordColor));
        holder.summary.setText(StringFormatUtils.highlightKeyWord(newsItem.getDesc(), mKeyword,
                mKeywordColor));

        if (newsItem.isHavePic()) {
            holder.imageContainer.setVisibility(View.VISIBLE);
            Glide.with(holder.image.getContext())
                    .load(newsItem.getImageurls().get(0).getUrl())
                    .into(holder.image);
        } else {
            holder.imageContainer.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClicked(holder, position);
                }
            }
        });

    }

    class SearchHolder extends RecyclerView.ViewHolder {

        public View imageContainer;
        public ImageView image;
        public TextView title, summary;

        public SearchHolder(View itemView) {
            super(itemView);
            imageContainer = itemView.findViewById(R.id.imageContainer);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            summary = (TextView) itemView.findViewById(R.id.summary);
        }
    }
}
