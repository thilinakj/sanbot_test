package com.qhcloud.net;

/**
 * Created by QH on 2017/1/16.
 */

public class AccountRegister extends AccountPassword{

    private String tel;

    private int areaCode;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }
}
