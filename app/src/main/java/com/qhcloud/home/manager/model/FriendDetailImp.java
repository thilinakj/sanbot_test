package com.qhcloud.home.manager.model;

import android.content.Context;

import com.qhcloud.home.entity.UserInfo;
import com.qhcloud.home.manager.DBHelper;
import com.qhcloud.home.manager.FriendDBManager;
import com.qhcloud.home.manager.model.biz.IFriendDetail;
import com.qhcloud.net.RemarkInfo;

public class FriendDetailImp extends Base implements IFriendDetail {

    private FriendDBManager mFriendDBManager;

    private FriendDetailImp(){
        super();
    }

    public FriendDetailImp(Context context){
        this();
        mFriendDBManager = new FriendDBManager(DBHelper.getInstance(context));
    }


    @Override
    public UserInfo queryById(int uid) {
        return mFriendDBManager.queryById(uid);
    }

    @Override
    public int setRemark(int uid, String remark, long seq) {
        RemarkInfo remarkInfo = new RemarkInfo();
        remarkInfo.setUid(uid);
        remarkInfo.setRemark(remark);
        return mNetApi.onSetRemark(remarkInfo, seq);
    }
}
