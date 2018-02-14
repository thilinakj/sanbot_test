package com.qhcloud.net;

/**
 * Created by admin on 2017/5/12.
 */

public class GroupList {
    private int groupId;
    //是否在通讯录中 0=不在通讯录中，1=在通讯录中 default=0
    private int status;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
