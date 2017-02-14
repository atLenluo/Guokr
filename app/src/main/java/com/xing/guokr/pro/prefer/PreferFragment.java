package com.xing.guokr.pro.prefer;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.xing.guokr.R;
import com.xing.guokr.base.view.BaseMvpFragment;

// 设置Fragment
public class PreferFragment extends BaseMvpFragment<PreferView, PreferPresenter>
        implements PreferView {

    private TextView cache_size;
    private ImageView cleaning_img;
    private Switch large_font_switch;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_prefer;
    }

    @Override
    protected void initView(View contentView) {
        cache_size = (TextView) contentView.findViewById(R.id.cache_size);
        cleaning_img = (ImageView) contentView.findViewById(R.id.cleaning_img);
        contentView.findViewById(R.id.clean_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().clearCache();
            }
        });
        large_font_switch = (Switch) contentView.findViewById(R.id.large_font_switch);
        large_font_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppConfig.setLargeFont(getContext(), isChecked);
            }
        });

        contentView.findViewById(R.id.set_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setServiceIP();
            }
        });
    }

    // 设置服务器IP地址
    private void setServiceIP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_input, null);
        final EditText ip = (EditText) view.findViewById(R.id.ip);
        view.findViewById(R.id.dialog_negative_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        builder.setView(view);
        builder.show();
    }

    @Override
    protected void initData() {
        large_font_switch.setChecked(AppConfig.isLargeFont(getContext()));
        getPresenter().getCacheSize();
    }

    @Override
    public PreferPresenter createPresenter() {
        return new PreferPresenter(getContext());
    }

    @Override
    public void showCleanCacheLoading() {
        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.loading_rotate);
        cleaning_img.setVisibility(View.VISIBLE);
        cleaning_img.startAnimation(rotate);
    }

    @Override
    public void setCacheSize(String size) {
        cache_size.setText(size);
    }

    @Override
    public void setCacheSize(int resId) {
        cache_size.setText(resId);
    }

    @Override
    public void hideCleanCahceLoading() {
        cleaning_img.clearAnimation();
        cleaning_img.setVisibility(View.GONE);
    }
}
