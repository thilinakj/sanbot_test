package com.qhcloud.home.app.start.account.auth;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Button;
import android.widget.EditText;

import com.qhcloud.demo.R;
import com.qhcloud.home.app.BaseActivity;
import com.qhcloud.home.app.start.main.MainActivity;
import com.qhcloud.home.entity.JniResponse;
import com.qhcloud.net.NetInfo;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author youngbin
 *
 * 验证码登录，当登录（{@link com.qhcloud.home.app.start.account.login.LoginActivity}）时,
 * 返回{@link NetInfo #QLINK_ACCOUNT_SVR_IMEI_OR_UDID_NOT_MATCH}，启动此activity
 */
public class AuthLoginActivity extends BaseActivity implements IAuthLoginView {

    private static final String TAG = "AuthLoginActivity";

    @BindView(R.id.auth_login_user_et)
    EditText mUserEt;
    @BindView(R.id.auth_login_code_et)
    EditText mAuthCodeEt;

    @BindView(R.id.auth_login_code_btn)
    Button mSmsCodeBtn;

    private AuthLoginPresenter mPresenter;
    private TimeCount mTimeCount;

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, AuthLoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_auth_login);
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
        LocalBroadcastManager.getInstance(this).registerReceiver(mAuthReceiver, filter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitle("验证码登录");
        mPresenter = new AuthLoginPresenter(this, this);
    }

    @Override
    protected void saveData(Bundle outState) {

    }

    @Override
    public void onSuccess() {
        super.onSuccess();
        showMsg("登录成功");
        startActivity(MainActivity.class);
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
    public String getSmsCode() {
        return mAuthCodeEt.getText().toString();
    }

    @Override
    public void setSmsCode(String smsCode) {
        mAuthCodeEt.setText(smsCode);
    }

    @Override
    public void startTimer() {
        mTimeCount = new TimeCount(60000, 1000);
        mTimeCount.start();
    }

    @OnClick(R.id.auth_login_code_btn)
    void onSmsCode() {
        if(mTimeCount != null){
            mTimeCount.onFinish();
        }
        mPresenter.getSmsCode();
    }

    @OnClick(R.id.auth_login_btn)
    void onLogin() {
        showDialog();
        mPresenter.loginBySmsCode();
    }

    private BroadcastReceiver mAuthReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            JniResponse jniResponse = intent.getParcelableExtra("response");

            if (String.valueOf(NetInfo.QHC_CMD_LOGIN_RSP).equals(action) && jniResponse != null) {
                switch (jniResponse.getResult()) {
                    case 0:
                        //登录成功
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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mAuthReceiver);
    }

    private class TimeCount extends CountDownTimer {

        private TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            mSmsCodeBtn.setEnabled(false);
            mSmsCodeBtn.setText(String.format(Locale.getDefault(), "(%ds)", l / 1000));
        }

        @Override
        public void onFinish() {
            mSmsCodeBtn.setText("重新获取");
            mSmsCodeBtn.setEnabled(true);
        }
    }

}
