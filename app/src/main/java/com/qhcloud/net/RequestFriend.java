package com.qhcloud.net;

/**
 * Created by QHPC on 2016/4/19.
 * 添求添加好友
 * A想加B为好友，A向B发送该消息
 * B用户收到该消息
 */
public class RequestFriend {

    private int ask_uid;//请求ID
    private int dev_uid; //设备ID
    private int msg_id; //消息ID
    private String dsc; //请求内容

    public RequestFriend(int ask_uid, int dev_uid, int msg_id, String des)
    {
        this.ask_uid = ask_uid;
        this.msg_id = msg_id;
        this.dev_uid = dev_uid;
        this.dsc = des;
    }

    public int getAsk_uid() {
        return ask_uid;
    }

    public void setAsk_uid(int ask_uid) {
        this.ask_uid = ask_uid;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public int getDev_uid() {
        return dev_uid;
    }

    public void setDev_uid(int dev_uid) {
        this.dev_uid = dev_uid;
    }
}
