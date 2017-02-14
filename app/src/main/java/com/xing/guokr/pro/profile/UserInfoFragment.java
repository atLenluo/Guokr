package com.xing.guokr.pro.profile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xing.guokr.GuokrApplicaton;
import com.xing.guokr.R;
import com.xing.guokr.base.view.BaseMvpFragment;
import com.xing.guokr.bean.User;

// 用户信息界面
public class UserInfoFragment extends BaseMvpFragment<UserInfoView, UserInfoPresenter> {

    private Toolbar toolbar;
    private TextView nickName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user_info;
    }

    @Override
    protected void initView(View contentView) {
        TextView toolbar_title = (TextView) contentView.findViewById(R.id.toolbar_title);
        toolbar_title.setText("账号信息");
        toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        nickName = (TextView) contentView.findViewById(R.id.nickname);
    }

    @Override
    public UserInfoPresenter createPresenter() {
        return new UserInfoPresenter(getContext());
    }

    @Override
    protected void initData() {
        // 设置toolbar
        AppCompatActivity activity = getActivity() instanceof AppCompatActivity ?
                ((AppCompatActivity) getActivity()) : null;
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
            }
        }

        User user = GuokrApplicaton.getUser();
        nickName.setText(user.getUserNick());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Activity activity = getContext() instanceof Activity ? ((Activity) getContext()) : null;
                if (activity != null) {
                    activity.finish();
                }
                break;
        }
        return true;
    }
}
