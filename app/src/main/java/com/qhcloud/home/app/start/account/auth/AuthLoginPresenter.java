package com.qhcloud.home.app.start.account.auth;

import android.content.Context;
import android.util.Log;

import com.qhcloud.home.app.BasePresenter;
import com.qhcloud.home.entity.Constant;
import com.qhcloud.home.manager.model.AuthImp;
import com.qhcloud.home.manager.model.biz.IAuth;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author youngbin
 * 验证码登录Presenter
 */
public class AuthLoginPresenter extends BasePresenter {

    private static final String TAG = "AuthLoginPresenter";

    private IAuthLoginView mIAuthLoginView;
    private IAuth mIAuth;


    public AuthLoginPresenter(Context context) {
        super(context);
    }

    public AuthLoginPresenter(Context context, IAuthLoginView iAuthView) {
        this(context);
        mIAuth = new AuthImp();
        mIAuthLoginView = iAuthView;

        init();
    }

    private void init() {
        mPreference.readSharedPreferences(mContext);
        mIAuthLoginView.setUser(mPreference.getValue(Constant.Configure.USER, ""));
    }


    /**
     * 获取短信验证码
     */
    public void getSmsCode() {

        String user = mIAuthLoginView.getUser();

        mDisposable.add(Flowable.just(user)
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String string) throws Exception {
                        return mIAuth.getSmsCode(string);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "onNext: i=" + integer);
                        if (integer == 0) {
                            mIAuthLoginView.startTimer();
                            mIAuthLoginView.showMsg("发送成功");
                        } else {
                            mIAuthLoginView.showMsg("发送失败");
                        }
                    }
                }));
    }

    /**
     * 通过短信验证码登录
     */
    public void loginBySmsCode() {

        final String tel = mIAuthLoginView.getUser();
        String code = mIAuthLoginView.getSmsCode();

        mDisposable.add(Flowable.just(code)
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String string) throws Exception {
                        return mIAuth.loginBySmsCoder(tel, string);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "onNext: i=" + integer);
                        if (integer == 0) {
                            mIAuthLoginView.showMsg("登录成功");
                        } else {
                            mIAuthLoginView.showMsg("登录失败");
                        }
                    }
                }));
    }

}
