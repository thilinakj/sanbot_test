package com.qhcloud.home.app.start.account.auth;

import com.qhcloud.home.app.IBaseView;

/**
 * @author youngbin
 * 验证码登录操作类
 */
public interface IAuthLoginView extends IBaseView{

    String getUser();

    void setUser(String user);

    String getSmsCode();

    void setSmsCode(String smsCode);

    void startTimer();

}
