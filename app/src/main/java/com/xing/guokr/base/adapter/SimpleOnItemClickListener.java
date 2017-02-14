package com.xing.guokr.base.adapter;


import android.support.v7.widget.RecyclerView;

public class SimpleOnItemClickListener implements OnItemClickListener {

    @Override
    public void onItemClicked(RecyclerView.ViewHolder holder, int position) {}

    @Override
    public boolean onItemLongClicked(RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
