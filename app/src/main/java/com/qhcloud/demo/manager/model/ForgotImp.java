package com.qhcloud.demo.manager.model;

import com.qhcloud.demo.manager.model.biz.IForgot;
import com.qhcloud.net.AccountPassword;

/**
 * Created by admin on 2017/4/18.
 */

public class ForgotImp extends Base implements IForgot {

    @Override
    public int getSmsCode(String tel) {
        return mNetApi.onGetResetPwdPhoneidentify(tel);
    }

    @Override
    public int resetPassword(String tel, String password, String code) {
        AccountPassword accountPassword = new AccountPassword();
        accountPassword.setAccount(tel);
        accountPassword.setAccountType("tel");
        accountPassword.setPassword(password);
        accountPassword.setIdentify(code);
        return mNetApi.onResetPwd(accountPassword);
    }
}
