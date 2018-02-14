package com.qhcloud.home.manager.model;

import android.text.TextUtils;

import com.qhcloud.home.manager.model.biz.ILogin;
import com.qhcloud.net.LoginAccount;
import com.qhcloud.net.NetApi;



public class LoginImp extends Base implements ILogin {

    @Override
    public int login(String user, String password) {
        LoginAccount loginAccount = new LoginAccount();
        loginAccount.setAccountType(TextUtils.isDigitsOnly(user) ? "tel" : "qlink_id");
        loginAccount.setAccount(user);
        loginAccount.setPassword(password);
        NetApi.getInstance().stopLogin();
        return mNetApi.onLogin(loginAccount);
    }
}
