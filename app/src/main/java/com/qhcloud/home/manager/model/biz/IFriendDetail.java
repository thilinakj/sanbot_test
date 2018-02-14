package com.qhcloud.home.manager.model.biz;

import com.qhcloud.home.entity.UserInfo;

public interface IFriendDetail {

    UserInfo queryById(int uid);

    int setRemark(int uid, String remark, long seq);

}
