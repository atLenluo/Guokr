package com.xing.guokr.pro.home;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xing.guokr.R;
import com.xing.guokr.base.adapter.BaseRecyclerViewAdapter;
import com.xing.guokr.bean.Channel;

import java.util.List;

public class ChannelAdapter extends BaseRecyclerViewAdapter<Channel, ChannelAdapter.ChannelHolder> {

    public ChannelAdapter(List<Channel> data) {
        super(data);
    }

    @Override
    public ChannelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_channel, parent, false);
        return new ChannelHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChannelHolder holder, final int position) {
        Channel channel = mData.get(position);
        String name = channel.getName();
        holder.channel.setText(name.substring(0, name.length() - 2));
        if (!TextUtils.isEmpty(channel.getChannelId())) {
            holder.channel.setTextColor(ContextCompat.getColor(holder.channel.getContext(),
                    R.color.search_item_text_color));
            holder.channel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClicked(holder, position);
                    }
                }
            });
            holder.channel.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        return mItemClickListener.onItemLongClicked(holder, position);
                    }
                    return false;
                }
            });
        } else {
            holder.channel.setTextColor(ContextCompat.getColor(holder.channel.getContext(),
                    R.color.text_hint));
        }
    }

    class ChannelHolder extends RecyclerView.ViewHolder {

        private TextView channel;

        public ChannelHolder(View itemView) {
            super(itemView);
            channel = (TextView) itemView.findViewById(R.id.channel);
        }
    }
}
