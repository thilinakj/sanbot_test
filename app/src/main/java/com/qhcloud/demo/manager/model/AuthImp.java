package com.qhcloud.demo.manager.model;

import com.qhcloud.demo.manager.model.biz.IAuth;
import com.qhcloud.net.IdentifyInfo;

public class AuthImp extends Base implements IAuth {

    @Override
    public int getSmsCode(String phone) {
        return mNetApi.onGetLoginIdentify(phone);
    }

    @Override
    public int loginBySmsCoder(String tel, String code) {
        IdentifyInfo info = new IdentifyInfo();
        info.setAreaCode(6);
        info.setIdentify(code);
        info.setTel(tel);
        return mNetApi.onLoginByIdentify(info);
    }
}
