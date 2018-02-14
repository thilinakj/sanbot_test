package com.qhcloud.home.app.start.account.forgot;

import android.content.Context;
import android.text.TextUtils;

import com.qhcloud.home.app.BasePresenter;
import com.qhcloud.home.entity.Constant;
import com.qhcloud.home.manager.model.ForgotImp;
import com.qhcloud.home.manager.model.biz.IForgot;
import com.qhcloud.home.util.Log;
import com.qhcloud.home.util.StringUtil;
import com.qhcloud.net.NetInfo;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author youngbin
 * 忘记密码presenter
 */

public class ForgotPresenter extends BasePresenter {

    private static final String TAG = "ForgotPresenter";

    private IForgotView mIForgotView;
    private IForgot mIForgot;

    public ForgotPresenter(Context context) {
        super(context);
    }

    public ForgotPresenter(Context context, IForgotView iForgotView) {
        this(context);
        mIForgotView = iForgotView;
        mIForgot = new ForgotImp();

        init();
    }

    private void init() {
        mPreference.readSharedPreferences(mContext);
        mIForgotView.setUser(mPreference.getValue(Constant.Configure.USER, ""));
    }

    /**
     * 获取短信验证码
     */
    public void getSmsCode() {

        String user = mIForgotView.getUser();

        if (TextUtils.isEmpty(user)) {
            mIForgotView.onFailed("手机号码不能为空");
            return;
        }
        if (!StringUtil.checkPhone(user)) {
            mIForgotView.onFailed("手机号码不正确");
            return;
        }
        mDisposable.add(Flowable.just(user)
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return mIForgot.getSmsCode(s);
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "code:result=" + integer);
                        switch (integer) {
                            case 0:
                                mIForgotView.showMsg("发送成功");
                                break;
                            case 410011:
                                mIForgotView.showMsg("帐号格式错误");
                                break;
                            default:
                                mIForgotView.showMsg("未知错误");
                        }
                    }
                }));
    }

    /**
     * 重设密码
     */
    public void resetPassword() {

        final String user = mIForgotView.getUser();
        final String password = mIForgotView.getPassword();
        final String code = mIForgotView.getSmsCode();

        if (TextUtils.isEmpty(user)) {
            mIForgotView.onFailed("手机号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mIForgotView.onFailed("密码不能空");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            mIForgotView.onFailed("验证码不能为空");
            return;
        }

        if (!StringUtil.checkPhone(user)) {
            mIForgotView.onFailed("手机号码不正确");
            return;
        }

        mDisposable.add(Flowable.just(1)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer i) throws Exception {
                        return mIForgot.resetPassword(user, password, code);
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "code:result=" + integer);
                        switch (integer) {
                            case 0:
                                mIForgotView.showMsg("修改成功");
                                break;
                            case NetInfo.QLINK_ACCOUNT_SVR_IDENTIFY_CODE_NOT_FOUND_OR_OUT_OF_DATE_FAIL:
                                mIForgotView.showMsg("验证码不匹配");
                                break;
                            default:
                                mIForgotView.showMsg("未知错误");
                        }
                    }
                }));


    }


}
