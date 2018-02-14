package com.qhcloud.demo.manager.model;

import android.content.Context;

import com.qhcloud.demo.entity.Message;
import com.qhcloud.demo.manager.ChatDBManager;
import com.qhcloud.demo.manager.DBHelper;
import com.qhcloud.demo.manager.model.biz.IChat;
import com.qhcloud.demo.util.AndroidUtil;
import com.qhcloud.net.DownloadFile;
import com.qhcloud.net.ServerInfo;
import com.qhcloud.net.UploadFile;
import com.qhcloud.net.UploadFileFinish;

import java.util.List;

public class ChatImp extends Base implements IChat {

    private ChatDBManager mChatDBManager;

    private ChatImp() {
        super();
    }

    public ChatImp(Context context) {
        this();
        mChatDBManager = new ChatDBManager(DBHelper.getInstance(context));
    }


    @Override
    public int getUid() {
        return mNetApi.onGetUID();
    }

    @Override
    public int getUrlById(long fileId, long seq) {
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.setFileId(fileId);
        downloadFile.setFileType(0);
        downloadFile.setThumbnails(0);
        return mNetApi.onDownloadFiles(downloadFile, seq);
    }

    @Override
    public int onUploadFile(int dstUid, String fileName, int fileType, long fileSize, long seq) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setDst_uid(dstUid);
        uploadFile.setFileName(fileName);
        uploadFile.setFileType(fileType);
        uploadFile.setFileSize(fileSize);
        uploadFile.setMd5(AndroidUtil.getByteMD5(fileName));
        return mNetApi.onUploadFiles(uploadFile, seq);
    }

    @Override
    public int onUploadFileFinish(String md5, int type, long size, long seq) {
        UploadFileFinish fileFinish = new UploadFileFinish();
        fileFinish.setMd5(md5);
        fileFinish.setFileType(type);
        fileFinish.setFileSize(size);
        return mNetApi.onUploadFileFinish(fileFinish, seq);
    }

    @Override
    public int onSendMsg(int uid, byte[] data, int len, int type, long seq) {
        return mNetApi.onSendMsg(uid, data, len, type, seq);
    }

    @Override
    public ServerInfo getServerInfo() {
        return mNetApi.getServerInfo();
    }

    @Override
    public long update(Message message) {
        return mChatDBManager.update(message);
    }

    @Override
    public List<Message> queryById(int toId, int fromId, int offset) {
        return mChatDBManager.queryById(toId, fromId, offset);
    }

    @Override
    public List<Message> query() {
        return mChatDBManager.query();
    }
}
