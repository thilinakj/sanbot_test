package com.qhcloud.home.app.start.main.friend.detail;

import com.qhcloud.home.app.IBaseView;


/**
 * 好友详情操作类
 */
public interface IFriendDetailView extends IBaseView {

    int getUid();

    void setText(String text);

    String getRemark();

}
