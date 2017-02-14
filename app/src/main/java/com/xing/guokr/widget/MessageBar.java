package com.xing.guokr.widget;


import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xing.guokr.R;

public class MessageBar {

    public static final int MESSAGE_BAR_ALERT = 0;
    public static final int MESSAGE_BAR_INFO = 1;

    private Context mContext;
    private Builder mBuilder;
    private View message_bar;
    private ImageView message_bar_icon;
    private TextView message_bar_text;

    private boolean isAnimOut = true;


    private MessageBar(Builder builder) {
        mBuilder = builder;
        mContext = builder.getContext();
        message_bar = builder.getView().findViewById(R.id.message_bar);
        message_bar_icon = (ImageView) builder.getView().findViewById(R.id.message_bar_icon);
        message_bar_text = (TextView) builder.getView().findViewById(R.id.message_bar_text);
    }


    public void show(int type) {
        if (type == MESSAGE_BAR_ALERT) {
            message_bar.setBackgroundColor(mContext.getResources()
                    .getColor(R.color.message_bar_alert_bg));
            message_bar_text.setTextColor(mContext.getResources()
                    .getColor(R.color.message_bar_alert_text));
        } else if (type == MESSAGE_BAR_INFO) {
            message_bar.setBackgroundColor(mContext.getResources()
                    .getColor(R.color.message_bar_info_bg));
            message_bar_text.setTextColor(mContext.getResources()
                    .getColor(R.color.message_bar_info_text));
        }

        if (mBuilder.getIconResId() == 0) {
            message_bar_icon.setVisibility(View.GONE);
        } else {
            message_bar_icon.setVisibility(View.VISIBLE);
            message_bar_icon.setImageResource(mBuilder.getIconResId());
        }

        message_bar_text.setText(mBuilder.getMessage());

        if (isAnimOut) {
            animateShow();
        }
    }

    private void animateShow() {
        message_bar.setVisibility(View.VISIBLE);
        message_bar.setTranslationY(-message_bar.getMeasuredHeight());
        message_bar.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewCompat.animate(message_bar)
                        .translationY(0)
                        .setDuration(300)
                        .setListener(new ViewPropertyAnimatorListenerAdapter(){
                            @Override
                            public void onAnimationStart(View view) {
                                isAnimOut = false;
                            }

                            @Override
                            public void onAnimationEnd(View view) {
                                animateHide();
                            }
                        })
                        .start();
            }
        }, 50);
    }

    private void animateHide() {
        message_bar.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewCompat.animate(message_bar)
                        .translationY(-message_bar.getMeasuredHeight())
                        .setDuration(300)
                        .setListener(new ViewPropertyAnimatorListenerAdapter(){
                            @Override
                            public void onAnimationEnd(View view) {
                                isAnimOut = true;
                            }
                        })
                        .start();
            }
        }, 1000);
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    /**
     * 消息栏建造者
     */
    public static class Builder {

        private View mView;
        private String mMessage;
        private int mIconResId;
        private Context mContext;

        public Builder(View view) {
            this.mView = view;
            mContext = mView.getContext();
        }

        public Builder setMessage(int resId) {
            mMessage = mContext.getString(resId);
            return this;
        }

        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        public Builder setIcon(int resId) {
            mIconResId = resId;
            return this;
        }

        public MessageBar build() {
            return new MessageBar(this);
        }

        public void show(int type) {
            build().show(type);
        }

        public Context getContext() {
            return mContext;
        }

        public String getMessage() {
            return mMessage;
        }

        public int getIconResId() {
            return mIconResId;
        }

        public View getView() {
            return mView;
        }
    }

}
