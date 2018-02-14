package com.qhcloud.home.manager;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.qhcloud.home.entity.JniResponse;
import com.qhcloud.home.entity.Message;
import com.qhcloud.home.util.DateUtil;
import com.qhcloud.net.ChatMessage;
import com.qhcloud.net.NetApi;
import com.qhcloud.net.NetInfo;

import java.util.Date;

/**
 * 数据管理
 * @author youngbin
 */
public class DataManager implements NetApi.ShowRecvListener {

    private static final String TAG = "DataManager";

    private static DataManager mInstance;
    private ChatDBManager mChatDBManager;

    private Context mContext;

    public static DataManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DataManager.class) {
                mInstance = new DataManager(context);
            }
        }
        return mInstance;
    }

    private DataManager(Context context) {
        mContext = context.getApplicationContext();
        NetApi.getInstance().setrListener(this);
        mChatDBManager = new ChatDBManager(DBHelper.getInstance(context));

    }

    @Override
    public void showRecv(int cmd, int result, Object obj, long seq) {
        Intent intent = new Intent(String.valueOf(cmd));
        intent.putExtra("response", new JniResponse(cmd, result, obj, seq));
        switch (cmd) {
            case NetInfo.QHC_CMD_LOGIN_RSP:
                //登录回调，登录出错，停止登录
                if (result != 0) {
                    NetApi.getInstance().stopLogin();
                }
                break;
            case NetInfo.QHC_CMD_RECV_CHAT_MSG:
                //收到消息，保存数据库
                Message message = saveChatMessage(obj);
                if(message != null){
                    intent.putExtra("message", message);
                }
                break;
            default:
        }
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    private Message saveChatMessage(Object object){
        if(object != null && object instanceof ChatMessage){
            ChatMessage chatMessage = (ChatMessage) object;
            Message message = new Message();
            message.setData(chatMessage.getData());
            long date = chatMessage.getDate();
            message.setDate(date);
            message.setType(chatMessage.getType());
            message.setFromId(chatMessage.getFromId());
            message.setToId(chatMessage.getToId());
            message.setState(Message.STATE_SEND_SUCCRSS);
            message.setRead(false);
            String dateText = DateUtil.getText(new Date(date * 1000));
            message.setDateText(dateText);

            if(message.getType() == 0){
                message.setContent(new String(message.getData()));
            }

            long result = mChatDBManager.update(message);
            if(result > 0){
                message.setId(result);
            }
            return message;
        }
        return null;
    }


}
