package com.qhcloud.net;

/**
 * Created by QH on 2017/1/16.
 */
public class Settings {
    public final static int BACK = 0;

    public final static int NOBACK = 1;

    private int uid;

    private String params;

    private int type;

    private int sendType;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "uid=" + uid +
                ", params='" + params + '\'' +
                ", type=" + type +
                ", sendType=" + sendType +
                '}';
    }
}
