package com.qhcloud.home.manager.model.biz;

/**
 * 忘记密码业务逻辑接口
 *
 */

public interface IForgot {

    /**
     * 获取验证码
     * @param tel 手机号码
     * @return 返回值：0-成功，其他-错误码
     */
    int getSmsCode(String tel);

    /**
     * 重设密码
     * @param tel 手机号码
     * @param password 新密码
     * @param code 验证码
     * @return 返回值：0-成功，其他-错误码
     */
    int resetPassword(String tel, String password, String code);

}
