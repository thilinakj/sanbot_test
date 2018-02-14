package com.qhcloud.home.app.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.qhcloud.demo.R;
import com.qhcloud.home.app.BaseActivity;
import com.qhcloud.home.app.start.account.forgot.ForgotPwdActivity;
import com.qhcloud.home.app.start.account.login.LoginActivity;
import com.qhcloud.home.app.start.account.register.RegisterActivity;
import com.qhcloud.home.entity.Constant;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends BaseActivity {

    private static final String TAG = "StartActivity";

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, StartActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_start);
        mUnBinder = ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initReceiver() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void saveData(Bundle outState) {

    }

    @OnClick(R.id.start_login_btn)
    void onLogin() {
        LoginActivity.startActivity(this);
    }

    @OnClick(R.id.start_register_btn)
    void onRegister() {
        RegisterActivity.startActivity(this);
    }

    @OnClick(R.id.start_forgot_password_btn)
    void onForgot() {
        ForgotPwdActivity.startActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.Message.APP_FINISH));
    }
}
