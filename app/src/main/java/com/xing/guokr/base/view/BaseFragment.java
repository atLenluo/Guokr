package com.xing.guokr.base.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ParallelExecutorCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    private View contentView;

    @LayoutRes
    protected abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(getLayoutRes(), container, false);
            initView(contentView);
        }

        ViewGroup parent = (ViewGroup) contentView.getParent();
        if (parent != null) {
            parent.removeView(contentView);
        }

        return contentView;
    }

    protected abstract void initView(View contentView);

}
