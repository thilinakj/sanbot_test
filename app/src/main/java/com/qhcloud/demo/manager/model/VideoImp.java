package com.qhcloud.demo.manager.model;

import android.content.Context;

import com.qhcloud.demo.entity.Message;
import com.qhcloud.demo.manager.ChatDBManager;
import com.qhcloud.demo.manager.DBHelper;
import com.qhcloud.demo.manager.model.biz.IVideo;

public class VideoImp extends Base implements IVideo {

    private ChatDBManager mChatDBManager;

    private VideoImp() {
        super();
    }

    public VideoImp(Context context) {
        this();
        mChatDBManager = new ChatDBManager(DBHelper.getInstance(context));
    }

    @Override
    public int getUid() {
        return mNetApi.onGetUID();
    }

    @Override
    public int onSendMsg(int uid, byte[] data, int len, int type, long seq) {
        return mNetApi.onSendMsg(uid, data, len, type, seq);
    }

    @Override
    public long update(Message message) {
        return mChatDBManager.update(message);
    }
}
