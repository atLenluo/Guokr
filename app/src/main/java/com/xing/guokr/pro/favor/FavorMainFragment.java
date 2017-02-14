package com.xing.guokr.pro.favor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.xing.guokr.R;
import com.xing.guokr.base.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

// 收藏夹Fragment
public class FavorMainFragment extends BaseFragment {

    private TabLayout favorite_tab;
    private ViewPager tab_pager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_favor_main;
    }

    @Override
    protected void initView(View contentView) {
        favorite_tab = (TabLayout) contentView.findViewById(R.id.favorite_tab);
        tab_pager = (ViewPager) contentView.findViewById(R.id.tab_pager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FavorFragment());
        fragments.add(new LikeFragment());
        fragments.add(new ReplyFragment());
        List<String> titles = new ArrayList<>();
        titles.add("收藏的");
        titles.add("赞过的");
        titles.add("评论过的");
        tab_pager.setAdapter(new FavorMainAdapter(getChildFragmentManager(), fragments, titles));
        favorite_tab.setupWithViewPager(tab_pager);
    }

}
