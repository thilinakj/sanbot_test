package com.qhcloud.home.app.start.account.register;

import com.qhcloud.home.app.IBaseView;

/**
 * 注册操作类
 * @author youngbin
 * {@link com.qhcloud.home.app.start.account.login.ILoginView}
 */

public interface IRegisterView extends IBaseView{

    String getUser();

    void setUser(String user);

    String getPassword();

    void setPassword(String password);

    String getSmsCode();

    void setSmsCode(String code);

}
