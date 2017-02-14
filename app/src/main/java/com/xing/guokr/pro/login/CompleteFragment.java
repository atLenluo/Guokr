package com.xing.guokr.pro.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xing.guokr.GuokrApplicaton;
import com.xing.guokr.R;
import com.xing.guokr.base.view.BaseMvpFragment;
import com.xing.guokr.bean.JsonResult;
import com.xing.guokr.bean.User;
import com.xing.guokr.event.UserInfoEvent;
import com.xing.guokr.pro.prefer.AppConfig;
import com.xing.guokr.utils.ToastUtils;
import com.xing.guokr.utils.VerificationUtils;

import org.greenrobot.eventbus.EventBus;

public class CompleteFragment extends BaseMvpFragment<CompleteView, CompletePresenter>
        implements CompleteView {

    private static final String TEL = "tel";

    private TextView confirm_text, create_account;
    private EditText nickName, password, passwordConfirm;
    private CheckBox protocol;
    private Toolbar toolbar;

    private AlertDialog mProgressDlg;

    private String mTel;

    public static CompleteFragment newInstance(String tel) {
        CompleteFragment fragment = new CompleteFragment();
        Bundle args = new Bundle();
        args.putString(TEL, tel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_complete;
    }

    @Override
    protected void initView(View contentView) {
        toolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) contentView.findViewById(R.id.toolbar_title);
        toolbar_title.setText("创建账号");

        confirm_text = (TextView) contentView.findViewById(R.id.confirm_text);
        create_account = (TextView) contentView.findViewById(R.id.create_account);
        nickName = (EditText) contentView.findViewById(R.id.nickname);
        password = (EditText) contentView.findViewById(R.id.password);
        passwordConfirm = (EditText) contentView.findViewById(R.id.passwordConfirm);
        protocol = (CheckBox) contentView.findViewById(R.id.protocol);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    @Override
    public CompletePresenter createPresenter() {
        return new CompletePresenter(getContext());
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

        mTel = getArguments().getString(TEL);
        confirm_text.setText("手机号验证成功！你的手机号是：" + mTel);
    }


    private void createAccount() {
        String nickNameStr = nickName.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        String passwordConfirmStr = passwordConfirm.getText().toString().trim();
        // 检查数据的有效性
        if(!VerificationUtils.isNickName(getContext(), nickNameStr)) {
            return;
        }
        if(!VerificationUtils.veryPassword(getContext(), passwordStr, passwordConfirmStr)) {
            return;
        }
        if(!protocol.isChecked()) {
            // 没有同意协议
            ToastUtils.show(getContext(), "必须同意协议");
            return;
        }

        // 联网创建账号
        getPresenter().createAccount(nickNameStr, passwordStr, mTel);
    }

    @Override
    public void showError(Throwable e) {
        hideProgressDlg();
        ToastUtils.show(getContext(), e.getMessage());
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
    public void setCreateResult(JsonResult<User> result) {
        hideProgressDlg();

        Integer code = result.getShowapi_res_code();
        if(code == -1) {
            // 昵称已被使用
            ToastUtils.show(getContext(), R.string.error_nickname_unavailable);
        } else if (code == 0) {
            ToastUtils.show(getContext(), R.string.info_sign_up_succeed);
            User user = result.getShowapi_res_body();
            // 给全局user赋值
            GuokrApplicaton.setUser(user);
            // 发送消息更改用户信息
            EventBus.getDefault().post(new UserInfoEvent(user));
            // 记住用户名和密码
            AppConfig.setUserTel(getContext(), user.getUserTel());
            AppConfig.setUserPassword(getContext(), user.getUserPassword());
            // 退出登陆注册页面
            Activity activity = getContext() instanceof Activity ? ((Activity) getContext()) : null;
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
