package com.xing.guokr.base.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected List<T> mData;
    protected OnItemClickListener mItemClickListener;

    public BaseRecyclerViewAdapter(List<T> data) {
        this.mData = data;
    }

    public void setData(List<T> data) {
        this.mData = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void setOnItemClickedListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
}
