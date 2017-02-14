package com.xing.guokr.pro.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.jakewharton.rxbinding.view.RxView;
import com.xing.guokr.R;
import com.xing.guokr.base.adapter.OnItemClickListener;
import com.xing.guokr.base.adapter.SimpleOnItemClickListener;
import com.xing.guokr.base.view.BaseMvpFragment;
import com.xing.guokr.bean.Channel;
import com.xing.guokr.utils.LogUtils;
import com.xing.guokr.utils.ToastUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

// 首页Fragment，展示新闻选项卡页面
public class HomeFragment extends BaseMvpFragment<HomeView, HomePresenter>
        implements HomeView {

    private TabLayout category_tab;
    private ViewPager tab_pager;
    private ImageView plus_btn;

    private RecyclerView rv_atten, rv_no_atten;
    private ChannelAdapter mAttenAdapter, mNoAttenAdapter;

    private View channel_layout, home_layout;

    private boolean isTagShow = false;
    private boolean isChannelChange = false;  // 是否改变了订阅频道

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public boolean onBackPressed() {
        if(isTagShow) {
            toggleChannelLayout();
            return true;
        }
        return false;
    }

    @Override
    protected void initView(View contentView) {
        category_tab = (TabLayout) contentView.findViewById(R.id.category_tab);
        tab_pager = (ViewPager) contentView.findViewById(R.id.tab_pager);
        plus_btn = (ImageView) contentView.findViewById(R.id.plus_btn);

        channel_layout = contentView.findViewById(R.id.channel_layout);
        home_layout = contentView.findViewById(R.id.home_layout);

        RxView.clicks(plus_btn)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        toggleChannelLayout();
                    }
                });

        initTagRecyclerView(contentView);
    }

    private void initTagRecyclerView(View contentView) {
        // 订阅频道
        rv_atten = (RecyclerView) contentView.findViewById(R.id.rv_attention);
        rv_atten.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mAttenAdapter = new ChannelAdapter(null);
        mAttenAdapter.setOnItemClickedListener(new OnItemClickListener() {

            @Override
            public void onItemClicked(RecyclerView.ViewHolder holder, int position) {
                Channel channel = swapChannelItem(mAttenAdapter, mNoAttenAdapter, position);
                getPresenter().removeAttentionChannel(channel);
            }

            @Override
            public boolean onItemLongClicked(RecyclerView.ViewHolder holder, int position) {
                mItemTouchHelper.startDrag(holder);
                return true;
            }
        });
        rv_atten.setAdapter(mAttenAdapter);
        mItemTouchHelper.attachToRecyclerView(rv_atten);

        // 没订阅的频道
        rv_no_atten = (RecyclerView) contentView.findViewById(R.id.rv_no_attention);
        rv_no_atten.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mNoAttenAdapter = new ChannelAdapter(null);
        mNoAttenAdapter.setOnItemClickedListener(new SimpleOnItemClickListener(){
            @Override
            public void onItemClicked(RecyclerView.ViewHolder holder, int position) {
                if(mAttenAdapter.getItemCount() < 8) {
                    Channel channel = swapChannelItem(mNoAttenAdapter, mAttenAdapter, position);
                    getPresenter().addAttentionChannel(channel);
                } else {
                    ToastUtils.show(getContext(), "最多只可订阅8个频道哦");
                }
            }
        });
        rv_no_atten.setAdapter(mNoAttenAdapter);
    }

    // 交换频道
    private Channel swapChannelItem(ChannelAdapter from, ChannelAdapter to, int position) {
        isChannelChange = true;

        Channel channel = from.getItem(position);
        from.getData().remove(position);
        from.notifyItemRemoved(position);
        // 进行change操作重新绑定holder的position
        if(position != from.getItemCount()) {
            from.notifyItemRangeChanged(position, from.getItemCount() - position);
        }

        to.getData().add(channel);
        to.notifyItemInserted(to.getItemCount());
        return channel;
    }


    private ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper
            .SimpleCallback(ItemTouchHelper.LEFT
            | ItemTouchHelper.RIGHT
            | ItemTouchHelper.UP
            | ItemTouchHelper.DOWN, 0) {

        @Override
        public boolean isLongPressDragEnabled() {
            return false;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            // 交换两个频道的位置
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();
            if(to != 0) {
                List<Channel> channelList = mAttenAdapter.getData();
                Channel moveItem = channelList.get(from);
                channelList.remove(from);
                channelList.add(to, moveItem);
                mAttenAdapter.notifyItemMoved(from, to);
                isChannelChange = true;
            }
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    });


    public void toggleChannelLayout() {
        if (isTagShow) {
            hideTag();
            if (isChannelChange) {
                setAttentionChannelList(mAttenAdapter.getData());
                isChannelChange = false;
            }
        } else {
            showTag();
        }
    }

    // 隐藏标签列表
    private void hideTag() {
        isTagShow = false;

        home_layout.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(plus_btn, "rotation", 135, 0);
        animator.setDuration(500);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                channel_layout.setVisibility(View.GONE);
            }
        });
        animator.start();

        ObjectAnimator alpha = ObjectAnimator.ofFloat(channel_layout, "alpha", 1, 0);
        alpha.setDuration(500);
        alpha.start();
    }

    // 显示标签列表
    private void showTag() {
        isTagShow = true;

        // 按钮动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(plus_btn, "rotation", 0, 135);
        animator.setDuration(500);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 标签完全显示出来的时候隐藏主页面布局
                home_layout.setVisibility(View.GONE);
            }
        });
        animator.start();

        channel_layout.setVisibility(View.VISIBLE);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(channel_layout, "alpha", 0, 1);
        alpha.setDuration(500);
        alpha.start();
    }


    @Override
    public void setAttentionChannelList(List<Channel> channelList) {
        LogUtils.e("频道数量：" + channelList.size());
        // 设置viewpager数据
        tab_pager.setAdapter(new HomeAdapter(getChildFragmentManager(), channelList));
        tab_pager.setOffscreenPageLimit(channelList.size() - 1);
        category_tab.setupWithViewPager(tab_pager);

        // 设置订阅频道列表
        mAttenAdapter.setData(channelList);
    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter(getContext());
    }

    @Override
    protected void initData() {
        // 获取关注的频道
        getPresenter().getAttentionChannelList();
        // 获取网络全部频道
        getPresenter().getNoAttentionChannel();
    }

    @Override
    public void setNoAttentionChannels(List<Channel> list) {
        mNoAttenAdapter.setData(list);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_search, menu);
    }
}
