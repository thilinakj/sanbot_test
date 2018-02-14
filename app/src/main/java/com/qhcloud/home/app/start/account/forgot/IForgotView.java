package com.qhcloud.home.app.start.account.forgot;

import com.qhcloud.home.app.IBaseView;

/**
 * @author youngbin
 * 忘记密码操作类
 * {@link com.qhcloud.home.app.start.account.login.ILoginView}
 */

public interface IForgotView extends IBaseView{

    String getUser();

    void setUser(String user);

    String getPassword();

    void setPassword(String password);

    String getSmsCode();

    void setSmsCode(String code);

}
