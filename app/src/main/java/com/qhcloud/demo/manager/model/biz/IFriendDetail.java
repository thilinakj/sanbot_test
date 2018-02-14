package com.qhcloud.demo.manager.model.biz;

import com.qhcloud.demo.entity.UserInfo;

public interface IFriendDetail {

    UserInfo queryById(int uid);

    int setRemark(int uid, String remark, long seq);

}
