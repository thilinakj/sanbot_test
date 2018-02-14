package com.qhcloud.demo.manager.model;

import com.qhcloud.demo.manager.model.biz.IRegister;
import com.qhcloud.net.AccountIdentify;
import com.qhcloud.net.AccountRegister;

import java.util.Locale;

/**
 * Created by admin on 2017/4/18.
 */

public class RegisterImp extends Base implements IRegister {

    @Override
    public int getSmsCode(String tel) {
        AccountIdentify identify = new AccountIdentify();
        identify.setAccount(String.format(Locale.getDefault(), "qlink%s", tel));
        identify.setAreaCode(86);
        identify.setTel(tel);
        return mNetApi.onGetRegistIdentify(identify);
    }

    @Override
    public int register(String tel, String password, String code) {
        AccountRegister register = new AccountRegister();
        register.setTel(tel);
        register.setAccount(String.format(Locale.getDefault(), "qlink%s", tel));
        register.setPassword(password);
        register.setIdentify(code);
        register.setAccountType("tel");
        register.setAreaCode(6);
        return mNetApi.onRegistAccount(register);
    }
}
