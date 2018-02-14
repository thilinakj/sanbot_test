package com.qhcloud.demo.manager.model;

import com.qhcloud.demo.manager.model.biz.IRobot;
import com.qhcloud.net.RobotCmd;
import com.qhcloud.net.VideoHandle;
import com.qhcloud.net.VideoInfo;

/**
 * Created by admin on 2017/4/18.
 */

public class RobotImp extends Base implements IRobot {

    @Override
    public int onRobotMove(int uid, int bodyPart, int cmd, float power, long seq) {
        RobotCmd robotCmd = new RobotCmd();
        robotCmd.setDevUid(uid);
        robotCmd.setBodyPart(bodyPart);
        robotCmd.setMoveCmd(cmd);
        robotCmd.setSpeed((int) (power * 10));
        robotCmd.setSendType(1);
        return mNetApi.onRobotMove(robotCmd, seq);
    }

    @Override
    public long onOpenVideo(int uid, int channel, int type, int streamType) {
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setDev_id(uid);
        videoInfo.setChannel(channel);
        videoInfo.setType(type);
        videoInfo.setStreamContents(streamType);
        VideoHandle videoHandle = mNetApi.onOpenVideo(videoInfo);
        return videoHandle != null ? videoHandle.getHandle() : -1;
    }

    @Override
    public int onCloseVideo(long sessionId) {
        return mNetApi.onCloseVideo(sessionId);
    }

    @Override
    public int onChangeVideoStream(long sessionId, int streamType) {
        return mNetApi.onChangeVideoStream(sessionId, streamType);
    }
}
