package com.xing.guokr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xing.guokr.base.BackPressListener;
import com.xing.guokr.base.BaseEvent;
import com.xing.guokr.base.view.EventBusActivity;
import com.xing.guokr.bean.User;
import com.xing.guokr.event.MessageBarEvent;
import com.xing.guokr.event.UserInfoEvent;
import com.xing.guokr.pro.favor.FavorMainFragment;
import com.xing.guokr.pro.home.HomeFragment;
import com.xing.guokr.pro.login.LoginActivity;
import com.xing.guokr.pro.message.MessageFragment;
import com.xing.guokr.pro.prefer.PreferFragment;
import com.xing.guokr.pro.profile.UserInfoActivity;
import com.xing.guokr.pro.search.SearchActivity;
import com.xing.guokr.utils.LogUtils;
import com.xing.guokr.utils.ToastUtils;
import com.xing.guokr.widget.MessageBar;

import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends EventBusActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private Fragment mCurrentFragment;
    private TextView toolbar_title;
    private Toolbar toolbar;
    private MessageBar mMessageBar;
    private TextView menu_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化Toolbar
        initToolbar();
        // 初始化侧边菜单栏
        initNavigationView();

        if (savedInstanceState == null) {
            changeFragment(null, new HomeFragment());
        }
    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_holder);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.open_drawer,
                R.string.close_drawer);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        // 头部布局
        View headerView = navigationView.getHeaderView(0);
        if (headerView != null) {
            headerView.findViewById(R.id.menu_avatar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toInfoOrLogin();
                }
            });

            menu_nickname = (TextView) headerView.findViewById(R.id.menu_nickname);
            menu_nickname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toInfoOrLogin();
                }
            });
        }

    }

    private void toInfoOrLogin() {
        User user = GuokrApplicaton.getUser();
        if (user != null) {
            // 进入用户信息界面
            startActivity(new Intent(this, UserInfoActivity.class));
        } else {
            // 进入登录界面
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void initToolbar() {
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void changeFragment(Fragment from, Fragment to) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (from != null) {
            fragmentTransaction.hide(from);
        }
        Fragment fragment = fragmentManager.findFragmentByTag(to.getClass().getName());
        if (fragment != null) {
            fragmentTransaction.show(fragment);
            mCurrentFragment = fragment;
        } else {
            fragmentTransaction.add(R.id.frag_container, to, to.getClass().getName());
            mCurrentFragment = to;
        }
        fragmentTransaction.commit();
    }

    private long mExit;

    @Override
    public void onBackPressed() {
        BackPressListener pressListener = mCurrentFragment instanceof BackPressListener ?
                ((BackPressListener) mCurrentFragment) : null;
        if (pressListener != null) {
            // 子页面处理了返回键
            if (pressListener.onBackPressed()) {
                return;
            }
        }

        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            long diff = System.currentTimeMillis() - mExit;
            if(diff < 2000) {
                super.onBackPressed();
            } else {
                ToastUtils.show(this, R.string.home_exit_confirm);
                mExit = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        // 清除全局信息
        GuokrApplicaton.setUser(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home:
                changeFragment(mCurrentFragment, new HomeFragment());
                toolbar_title.setText(R.string.app_name);
                break;
            case R.id.item_favor:
                if (GuokrApplicaton.getUser() == null) {
                    ToastUtils.show(this, R.string.error_not_logged_in);
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    changeFragment(mCurrentFragment, new FavorMainFragment());
                    toolbar_title.setText(R.string.favorite_toolbar_title);
                }
                break;
            case R.id.item_message:
                if (GuokrApplicaton.getUser() == null) {
                    ToastUtils.show(this, R.string.error_not_logged_in);
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    changeFragment(mCurrentFragment, new MessageFragment());
                    toolbar_title.setText(R.string.message_toolbar_title);
                }
                break;
            case R.id.item_preference:
                changeFragment(mCurrentFragment, new PreferFragment());
                toolbar_title.setText(R.string.setting_toolbar_title);
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Subscribe
    public void onMessageEvent(BaseEvent event) {
        LogUtils.e("收到消息");
        if(event.getType() == BaseEvent.TYPE_MESSAGE_BAR) {
            showMessageBar((MessageBarEvent) event);
        } else if (event.getType() == BaseEvent.TYPE_USER_INFO) {
            changeUserInfo((UserInfoEvent) event);
        }
    }

    private void changeUserInfo(UserInfoEvent event) {
        LogUtils.e("更改用户信息");
        if(event != null) {
            User user = event.getUser();
            menu_nickname.setText(user.getUserNick());
        }
    }

    private void showMessageBar(MessageBarEvent event) {
        if(event != null) {
            if(mMessageBar == null) {
                mMessageBar = new MessageBar.Builder(mDrawer)
                        .build();
            }
            mMessageBar.getBuilder()
                    .setIcon(event.getIconResId())
                    .setMessage(event.getMessage());
            mMessageBar.show(event.getShowType());
        }
    }

}
