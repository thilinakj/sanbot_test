package com.qhcloud.demo.app.account.register;

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
 * 注册页
 * @author youngbin
 */

public class RegisterActivity extends BaseActivity implements IRegisterView {

    private static final String TAG = "RegisterActivity";

    @BindView(R.id.register_user_et)
    EditText mUserEt;

    @BindView(R.id.register_password_et)
    EditText mPasswordEt;

    @BindView(R.id.register_code_et)
    EditText mCodeEt;

    private RegisterPresenter mPresenter;

    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_register);
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
        setTitle("注册");
        mPresenter = new RegisterPresenter(this, this);
    }

    @Override
    protected void saveData(Bundle outState) {

    }

    @OnClick(R.id.register_code_btn)
    void onGetCode() {
        mPresenter.getSmsCode();
    }

    @OnClick(R.id.register_btn)
    void onRegister() {
        mPresenter.register();
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
