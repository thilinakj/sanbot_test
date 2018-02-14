package com.qhcloud.home.app.start.main.friend;

import com.qhcloud.home.app.IBaseView;
import com.qhcloud.home.entity.UserInfo;

import java.util.List;


/**
 * 好友列表操作类
 *
 * @author youngbin
 */
public interface IFriendView extends IBaseView {

    void setAdapter(List<UserInfo> list);

}
