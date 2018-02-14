package com.qhcloud.demo.app.account.login;

import android.content.Context;
import android.text.TextUtils;
import com.qhcloud.demo.app.BasePresenter;
import com.qhcloud.demo.entity.Constant;
import com.qhcloud.demo.manager.model.LoginImp;
import com.qhcloud.demo.manager.model.biz.ILogin;
import com.qhcloud.demo.util.Log;
import com.qhcloud.demo.util.StringUtil;
import com.qhcloud.net.NetInfo;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录presenter
 * @author youngbin
 */
public class LoginPresenter extends BasePresenter {

    private static final String TAG = "LoginPresenter";

    private ILoginView mILoginView;
    private ILogin mILogin;

    private LoginPresenter(Context context) {
        super(context);

    }

    public LoginPresenter(Context context, ILoginView iLoginView) {
        this(context);
        mILoginView = iLoginView;
        mILogin = new LoginImp();

        init();
    }

    private void init() {
        mPreference.readSharedPreferences(mContext);
        mILoginView.setUser(mPreference.getValue(Constant.Configure.USER, ""));
        mILoginView.setPassword(mPreference.getValue(Constant.Configure.PASSWORD, ""));
    }


    /**
     * 登录
     */
    public void login() {

        final String user = mILoginView.getUser();
        final String password = mILoginView.getPassword();

        if(TextUtils.isEmpty(user)){
            mILoginView.onFailed("手机号码不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            mILoginView.onFailed("密码不能空");
            return;
        }
        if (!StringUtil.checkPhone(user)) {
            mILoginView.onFailed("手机号码不正确");
            return;
        }

        mDisposable.add(Flowable.just(1)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return mILogin.login(user, password);
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                        Log.i(TAG, "onNext: i=" + integer);
                        switch (integer) {
                            case NetInfo.LOGIN_ERROR_ACCOUNT_NOT_FOUND:
                                mILoginView.onFailed("帐号不存在");
                                break;
                            case 0:
                                saveUserInfo();
                                break;
                            default:
                                mILoginView.onFailed("未知错误");
                        }
                    }
                }));
    }


    /**
     * 保存用户和密码
     */
    private void saveUserInfo() {
        mPreference.writeSharedPreferences(mContext);
        mPreference.putValue(Constant.Configure.USER, mILoginView.getUser());
        mPreference.putValue(Constant.Configure.PASSWORD, mILoginView.getPassword());
        mPreference.commit();
    }


}
