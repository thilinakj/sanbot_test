package com.qhcloud.demo.manager.model;

import com.qhcloud.demo.manager.model.biz.ILogin;
import com.qhcloud.net.LoginAccount;
import com.qhcloud.net.NetApi;



public class LoginImp extends Base implements ILogin {

    @Override
    public int login(String user, String password) {
        LoginAccount loginAccount = new LoginAccount();
        loginAccount.setAccountType("tel");
        loginAccount.setAccount(user);
        loginAccount.setPassword(password);
        NetApi.getInstance().stopLogin();
        return mNetApi.onLogin(loginAccount);
    }
}
