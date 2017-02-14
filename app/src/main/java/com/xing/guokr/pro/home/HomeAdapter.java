package com.xing.guokr.pro.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xing.guokr.bean.Channel;

import java.util.List;

/**
 * Created by Administrator on 2017/1/15.
 */

public class HomeAdapter extends FragmentStatePagerAdapter {

    private List<Channel> mChannels;

    public HomeAdapter(FragmentManager fm, List<Channel> channels) {
        super(fm);
        this.mChannels = channels;
    }

    @Override
    public Fragment getItem(int position) {
        Channel channel = mChannels.get(position);
        return PageFragment.newInstance(channel.getChannelId(), channel.getName());
    }

    @Override
    public int getCount() {
        return mChannels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String name = mChannels.get(position).getName();
        return name.substring(0, name.length() - 2);
    }
}
