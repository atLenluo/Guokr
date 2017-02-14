package com.xing.guokr.base.adapter;

import android.support.v7.widget.RecyclerView;

// 列表项点击事件
public interface OnItemClickListener {

    void onItemClicked(RecyclerView.ViewHolder holder, int position);

    boolean onItemLongClicked(RecyclerView.ViewHolder holder, int position);

}
