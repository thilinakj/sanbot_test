package com.qhcloud.demo.app.account.register;

import com.qhcloud.demo.app.IBaseView;

/**
 * 注册操作类
 * @author youngbin
 * {@link com.qhcloud.demo.app.account.login.ILoginView}
 */

public interface IRegisterView extends IBaseView{

    String getUser();

    void setUser(String user);

    String getPassword();

    void setPassword(String password);

    String getSmsCode();

    void setSmsCode(String code);

}
