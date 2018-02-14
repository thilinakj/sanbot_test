package com.qhcloud.net;

/**
 * Created by QH on 2017/1/18.
 */

public class SystemMessage {
    private int msg_id;

    private int note_type;

    private int note_time;

    private int src_uid;

    private int dev_uid;

    private int dst_uid;

    private int answer_type;

    private String msg_value;

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public int getNote_type() {
        return note_type;
    }

    public void setNote_type(int note_type) {
        this.note_type = note_type;
    }

    public int getNote_time() {
        return note_time;
    }

    public void setNote_time(int note_time) {
        this.note_time = note_time;
    }

    public int getSrc_uid() {
        return src_uid;
    }

    public void setSrc_uid(int src_uid) {
        this.src_uid = src_uid;
    }

    public int getDev_uid() {
        return dev_uid;
    }

    public void setDev_uid(int dev_uid) {
        this.dev_uid = dev_uid;
    }

    public int getDst_uid() {
        return dst_uid;
    }

    public void setDst_uid(int dst_uid) {
        this.dst_uid = dst_uid;
    }

    public int getAnswer_type() {
        return answer_type;
    }

    public void setAnswer_type(int answer_type) {
        this.answer_type = answer_type;
    }

    public String getValue() {
        return msg_value;
    }

    public void setValue(String value) {
        this.msg_value = value;
    }
}
