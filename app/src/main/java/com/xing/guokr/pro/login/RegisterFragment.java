package com.xing.guokr.pro.login;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.xing.guokr.R;
import com.xing.guokr.base.view.BaseMvpFragment;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.User;
import com.xing.guokr.bean.VeryCode;
import com.xing.guokr.utils.ToastUtils;
import com.xing.guokr.utils.VerificationUtils;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

public class RegisterFragment extends BaseMvpFragment<RegisterView, RegisterPresenter>
        implements RegisterView {

    private Toolbar toolbar;
    private EditText phoneNumber, captcha;
    private TextView register;
    private ImageView refreshCaptcha, captchaImage;

    private String mVeryCodeText;
    private AlertDialog mProgressDlg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_register;
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

    @Override
    protected void initView(View contentView) {
        toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) contentView.findViewById(R.id.toolbar_title);
        toolbar_title.setText("注册");

        phoneNumber = (EditText) contentView.findViewById(R.id.phoneNumber);
        captcha = (EditText) contentView.findViewById(R.id.captcha);
        contentView.findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        captchaImage = (ImageView) contentView.findViewById(R.id.captchaImage);
        refreshCaptcha = (ImageView) contentView.findViewById(R.id.refreshCaptcha);
        RxView.clicks(refreshCaptcha).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        getPresenter().getcaptcha();
                    }
                });
    }

    // 实现注册
    private void doRegister() {
        String tel = phoneNumber.getText().toString().trim(); // 电话
        String captchaStr = captcha.getText().toString(); // 验证码
        if(!VerificationUtils.isMobile(tel)) {
            ToastUtils.show(getContext(), R.string.error_illegal_phone);
            return;
        }
        if(!mVeryCodeText.equalsIgnoreCase(captchaStr)) {
            ToastUtils.show(getContext(), R.string.error_captcha_failed);
            getPresenter().getcaptcha();
            return;
        }
        // 联网查询手机号是否已经被使用
        getPresenter().veryTel(tel);
    }

    @Override
    public void setVeryTelResult(JsonResult<User> result) {
        hideProgressDlg();

        if(result.getShowapi_res_code() == -1) {
            ToastUtils.show(getContext(), R.string.error_290005);
            getPresenter().getcaptcha();
        } else if(result.getShowapi_res_code() == 0) {
            // 跳转到完善信息页面
            User user = result.getShowapi_res_body();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
            transaction.replace(R.id.container, CompleteFragment.newInstance(user.getUserTel()));
            transaction.commit();
        }
    }

    private void hideProgressDlg() {
        if (mProgressDlg != null && mProgressDlg.isShowing()) {
            mProgressDlg.dismiss();
        }
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

        // 请求验证码
        getPresenter().getcaptcha();
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
    public void showError(Throwable e) {
        hideProgressDlg();
        ToastUtils.show(getContext(), e.getMessage());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fragmentManager = getFragmentManager();
                int count = fragmentManager.getBackStackEntryCount();
                if(count > 0) {
                    fragmentManager.popBackStack();
                }
                break;
        }
        return true;
    }

    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter(getContext());
    }
}
