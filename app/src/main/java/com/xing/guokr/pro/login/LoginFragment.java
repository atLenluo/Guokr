package com.xing.guokr.pro.login;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.xing.guokr.GuokrApplicaton;
import com.xing.guokr.R;
import com.xing.guokr.base.view.BaseMvpFragment;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.User;
import com.xing.guokr.bean.VeryCode;
import com.xing.guokr.event.UserInfoEvent;
import com.xing.guokr.utils.ToastUtils;
import com.xing.guokr.utils.VerificationUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class LoginFragment extends BaseMvpFragment<LoginView, LoginPresenter>
        implements LoginView {

    private Toolbar toolbar;
    private EditText username, password, captcha;
    private TextView login, register;
    private ImageView captchaImage;
    private AlertDialog mProgressDlg;

    private String mVeryCodeText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View contentView) {
        toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) contentView.findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.umeng_socialize_login);

        username = (EditText) contentView.findViewById(R.id.username);
        password = (EditText) contentView.findViewById(R.id.password);
        captcha = (EditText) contentView.findViewById(R.id.captcha);

        captchaImage = (ImageView) contentView.findViewById(R.id.captchaImage);
        RxView.clicks(contentView.findViewById(R.id.refreshCaptcha))
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        getPresenter().getcaptcha();
                    }
                });

        login = (TextView) contentView.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 联网登录
                doLogin();
            }
        });

        contentView.findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top,
                        R.anim.slide_in_top, R.anim.slide_out_bottom);
                transaction.replace(R.id.container, new RegisterFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void doLogin() {
        String userNameStr = username.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        String captchaStr = captcha.getText().toString().trim();
        if(!VerificationUtils.checkUserName(getContext(), userNameStr)) {
            getPresenter().getcaptcha();
            return;
        }
        if(TextUtils.isEmpty(passwordStr)) {
            ToastUtils.show(getContext(), R.string.error_password_empty);
            getPresenter().getcaptcha();
            return;
        }
        if(TextUtils.isEmpty(captchaStr)) {
            ToastUtils.show(getContext(), R.string.error_captcha_empty);
            getPresenter().getcaptcha();
            return;
        }
        if(!captchaStr.equalsIgnoreCase(mVeryCodeText)) {
            ToastUtils.show(getContext(), R.string.error_captcha_failed);
            getPresenter().getcaptcha();
            return;
        }

        getPresenter().login(userNameStr, passwordStr);
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(getContext());
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
        // 获取验证码
        getPresenter().getcaptcha();
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

    @Override
    public void setVeryCode(VeryCode code) {
        if (code != null) {
            mVeryCodeText = code.getText();
            Glide.with(getContext())
                    .load(code.getImg_path())
                    .into(captchaImage);
        }
    }

    @Override
    public void showLoading() {
        if (mProgressDlg == null) {
            mProgressDlg = new AlertDialog.Builder(getContext())
                    .setView(R.layout.dialog_loading)
                    .create();
        }
        mProgressDlg.setCanceledOnTouchOutside(false);
        mProgressDlg.setCancelable(false);
        mProgressDlg.show();
    }

    private void hideProgressDlg() {
        if (mProgressDlg != null && mProgressDlg.isShowing()) {
            mProgressDlg.dismiss();
        }
    }

    @Override
    public void showError(Throwable e) {
        hideProgressDlg();
        ToastUtils.show(getContext(), e.getMessage());
    }

    @Override
    public void setLoginResult(JsonResult<User> result) {
        hideProgressDlg();

        Integer code = result.getShowapi_res_code();
        if(code == -1) {
            getPresenter().getcaptcha();
            ToastUtils.show(getContext(), R.string.error_wrong_login_info);
        } else if(code == 0) {
            ToastUtils.show(getContext(), R.string.info_login_succeed);
            User user = result.getShowapi_res_body();
            GuokrApplicaton.setUser(user);
            EventBus.getDefault().post(new UserInfoEvent(user));
            Activity activity = getContext() instanceof Activity ? ((Activity) getContext()) : null;
            if (activity != null) {
                activity.finish();
            }
        }

    }
}
