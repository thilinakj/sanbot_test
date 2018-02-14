package com.qhcloud.home.app.start.account.register;

import android.content.Context;
import android.text.TextUtils;

import com.qhcloud.home.app.BasePresenter;
import com.qhcloud.home.manager.model.RegisterImp;
import com.qhcloud.home.manager.model.biz.IRegister;
import com.qhcloud.home.util.Log;
import com.qhcloud.home.util.StringUtil;
import com.qhcloud.net.NetInfo;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * 注册presenter
 * @author youngbin
 */
public class RegisterPresenter extends BasePresenter {

    private static final String TAG = "RegisterPresenter";

    private IRegisterView mIRegisterView;
    private IRegister mIRegister;

    public RegisterPresenter(Context context) {
        super(context);
    }

    public RegisterPresenter(Context context, IRegisterView iRegisterView) {
        this(context);

        mIRegisterView = iRegisterView;
        mIRegister = new RegisterImp();
    }

    public void getSmsCode() {

        String user = mIRegisterView.getUser();
        if (TextUtils.isEmpty(user)) {
            mIRegisterView.onFailed("手机号码不能为空");
            return;
        }
        if (!StringUtil.checkPhone(user)) {
            mIRegisterView.onFailed("手机号码不正确");
            return;
        }
        mDisposable.add(Flowable.just(user)
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return mIRegister.getSmsCode(s);
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "code:result=" + integer);
                        switch (integer) {
                            case 0:
                                mIRegisterView.showMsg("发送成功");
                                break;
                            case 410011:
                                mIRegisterView.showMsg("帐号格式错误");
                                break;
                            case NetInfo.REGISTER_SVR_ALREADY_REGISTER:
                                mIRegisterView.showMsg("已经注册");
                                break;
                            default:
                                mIRegisterView.showMsg("未知错误");
                        }
                    }
                }));
    }

    public void register() {

        final String user = mIRegisterView.getUser();
        final String password = mIRegisterView.getPassword();
        final String code = mIRegisterView.getSmsCode();

        if (TextUtils.isEmpty(user)) {
            mIRegisterView.onFailed("手机号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mIRegisterView.onFailed("密码不能空");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            mIRegisterView.onFailed("验证码不能为空");
            return;
        }

        if (!StringUtil.checkPhone(user)) {
            mIRegisterView.onFailed("手机号码不正确");
            return;
        }

        mDisposable.add(Flowable.just(1)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer i) throws Exception {
                        return mIRegister.register(user, password, code);
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "code:result=" + integer);
                        switch (integer) {
                            case 0:
                                mIRegisterView.showMsg("注册成功");
                                break;
                            default:
                                mIRegisterView.showMsg("未知错误");
                        }
                    }
                }));


    }


}
