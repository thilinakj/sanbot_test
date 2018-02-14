package com.qhcloud.home.app.start.account.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Button;
import android.widget.EditText;

import com.qhcloud.demo.R;
import com.qhcloud.home.app.BaseActivity;
import com.qhcloud.home.app.start.main.MainActivity;
import com.qhcloud.home.app.start.account.auth.AuthLoginActivity;
import com.qhcloud.home.entity.JniResponse;
import com.qhcloud.net.NetInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页
 * @author youngbin
 */

public class LoginActivity extends BaseActivity implements ILoginView {

    private static final String TAG = "LoginActivity";


    @BindView(R.id.login_user_et)
    EditText mUserEt;
    @BindView(R.id.login_password_et)
    EditText mPasswordEt;
    @BindView(R.id.login_btn)
    Button mLoginBtn;

    private LoginPresenter mPresenter;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        mUnBinder = ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(String.valueOf(NetInfo.QHC_CMD_LOGIN_RSP));
        filter.addAction(String.valueOf(NetInfo.QHC_CMD_ONLINE_STATUS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mLoginReceiver, filter);
    }

    @Override
    protected void initData(Bundle bundle) {
        setTitle("登录");
        mPresenter = new LoginPresenter(this, this);
    }

    @Override
    protected void saveData(Bundle outState) {

    }

    @Override
    public void onSuccess() {
        super.onSuccess();
        showMsg("登录成功");
        startActivity(MainActivity.class);
        finish();
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


    @OnClick(R.id.login_btn)
    void onLogin() {
        showDialog();
        mPresenter.login();
    }

    private BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            JniResponse jniResponse = intent.getParcelableExtra("response");

            if (String.valueOf(NetInfo.QHC_CMD_LOGIN_RSP).equals(action) && jniResponse != null) {
                switch (jniResponse.getResult()) {
                    case 0:
                        //登录成功
                        break;
                    case NetInfo.QLINK_ACCOUNT_SVR_IMEI_OR_UDID_NOT_MATCH:
                        //需要验证码登录
                        onFailed("需要验证码登录");
                        AuthLoginActivity.startActivity(LoginActivity.this);
                        break;
                    case NetInfo.QLINK_ACCOUNT_SVR_PWD_NOT_MATCH:
                        onFailed("密码不正确");
                        break;
                    default:
                        onFailed("未知错误");
                }
            } else if (String.valueOf(NetInfo.QHC_CMD_ONLINE_STATUS).equals(action)) {
                onSuccess();
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mLoginReceiver);
    }
}
