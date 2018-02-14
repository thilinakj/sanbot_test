package com.qhcloud.home.manager.model.biz;

/**
 * Created by admin on 2017/4/18.
 */

public interface IRegister {

    int getSmsCode(String tel);

    int register(String tel, String password, String code);

}
