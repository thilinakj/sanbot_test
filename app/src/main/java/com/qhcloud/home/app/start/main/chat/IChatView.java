package com.qhcloud.home.app.start.main.chat;

import com.qhcloud.home.app.IBaseView;
import com.qhcloud.home.entity.Message;

import java.util.List;

/**
 * 消息操作类
 * @author youngbin
 */

public interface IChatView extends IBaseView {


    /**
     * 获取对方uid
     * @return uid
     */
    int getToUid();

    /**
     * 获取文字内容
     * @return text
     */
    String getContent();

    /**
     * 设置文字内容，一般用于清空
     * @param content text
     */
    void setContent(String content);

    /**
     * 设置数据
     * @param toUid uid
     * @param list 数据
     */
    void setAdapter(int toUid, List<Message> list);

    /**
     * 添加数据
     * @param toUid uid
     * @param list 数据
     */
    void addAdapter(int toUid, List<Message> list);

    /**
     * 刷新
     */
    void notifyData();

}
