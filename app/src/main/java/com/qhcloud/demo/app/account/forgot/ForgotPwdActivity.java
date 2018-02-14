package com.qhcloud.demo.app.account.forgot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.qhcloud.demo.R;
import com.qhcloud.demo.app.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author youngbin
 *
 * 忘记密码
 */

public class ForgotPwdActivity extends BaseActivity implements IForgotView {

    @BindView(R.id.forgot_user_et)
    EditText mUserEt;

    @BindView(R.id.forgot_password_et)
    EditText mPasswordEt;

    @BindView(R.id.forgot_code_et)
    EditText mCodeEt;

    private ForgotPresenter mPresenter;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, ForgotPwdActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_forgot_password);
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
        setTitle("忘记密码");
        mPresenter = new ForgotPresenter(this, this);
    }

    @Override
    protected void saveData(Bundle outState) {

    }

    @OnClick(R.id.forgot_code_btn)
    void onGetCode() {
        mPresenter.getSmsCode();
    }

    @OnClick(R.id.forgot_btn)
    void onRegister() {
        mPresenter.resetPassword();
    }

    @Override
    public String getUser() {
        return mUserEt.getText().toString();
    }

    @Override
    public void setUser(String user) {
        mUserEt.setText(user);
    }

    @Override
    public String getPassword() {
        return mPasswordEt.getText().toString();
    }

    @Override
    public void setPassword(String password) {
        mPasswordEt.setText(password);
    }

    @Override
    public String getSmsCode() {
        return mCodeEt.getText().toString();
    }

    @Override
    public void setSmsCode(String code) {
        mCodeEt.setText(code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }
}
