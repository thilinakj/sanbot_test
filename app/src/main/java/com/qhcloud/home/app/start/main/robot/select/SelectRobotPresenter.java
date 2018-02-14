package com.qhcloud.home.app.start.main.robot.select;

import android.content.Context;

import com.qhcloud.home.app.BasePresenter;
import com.qhcloud.home.app.start.main.friend.IFriendView;
import com.qhcloud.home.entity.UserInfo;
import com.qhcloud.home.manager.model.FriendImp;
import com.qhcloud.home.manager.model.biz.IFriend;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 选择机器人presenter
 * @author youngbin
 */

public class SelectRobotPresenter extends BasePresenter {

    private IFriend mIFriend;
    private IFriendView mIFriendView;
    //本地用户信息
    private List<UserInfo> mUserInfoList;

    public SelectRobotPresenter(Context context) {
        super(context);
    }

    public SelectRobotPresenter(Context context, IFriendView iFriendView) {
        this(context);

        mIFriendView = iFriendView;
        mIFriend = new FriendImp(context);
        init();
    }

    private void init() {
        mDisposable.add(Flowable.empty()
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mUserInfoList = mIFriend.queryRobot();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        mIFriendView.setAdapter(mUserInfoList);
                    }
                })
                .subscribe());
    }


}
