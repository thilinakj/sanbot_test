package com.qhcloud.home.app.splash;

import android.content.Context;
import android.content.pm.PackageManager;

import com.qhcloud.home.app.BasePresenter;
import com.qhcloud.home.app.IBaseView;
import com.qhcloud.home.entity.Constant;
import com.qhcloud.home.util.AndroidUtil;
import com.qhcloud.home.util.Log;
import com.qhcloud.net.NetApi;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author youngbin
 *
 * 启动页presenter
 */
public class SplashPresenter extends BasePresenter {

    private static final String TAG = "SplashPresenter";


    private IBaseView mIBaseView;

    public SplashPresenter(Context context) {
        super(context);
        init();
    }

    public SplashPresenter(Context context, IBaseView iBaseView) {
        this(context);
        mIBaseView = iBaseView;
    }


    private void init() {
        Flowable.timer(1, TimeUnit.SECONDS)
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        onInitNetLibParams();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        mIBaseView.onSuccess();
                    }
                })
                .subscribe();
    }

    /**
     * 关闭时，登录退出，清空网络库
     */
    protected void close() {
        Flowable.empty()
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        NetApi.getInstance().onLogout();
                        NetApi.getInstance().cleanupLib();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }


    /**
     * 设置网络库参数
     */
    private void onInitNetLibParams() {
        try {
            int appId = AndroidUtil.getAppStore(mContext);
            String currentVersion = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            int result = NetApi.getInstance().initLib(appId, Constant.Service.IP, Constant.Service.PORT, currentVersion);
            Log.i(TAG, "初始化,result="+result);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


}
