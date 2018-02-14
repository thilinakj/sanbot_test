package com.qhcloud.net;

/**
 * 服务器信息
 * Created by QHPC on 2016/5/9.
 */
public class ServerInfo {
    //消息服务器
    private String mSmsServer = null;
    //消息服务器端口
    private int mSmsServerPort = 0;
    //P2P服务器
    private String mP2pServer = null;
    //P2P服务器端口
    private int mP2pServerPort = 0;
    //文件服务器
    private String mFileServer = null;
    //文件服务器端口
    private int mFileServerPort = 0;
    //用户UID
    private int mUserId = 0;

    public String getSmsServer() {
        return mSmsServer;
    }

    public void setSmsServer(String mSmsServer) {
        this.mSmsServer = mSmsServer;
    }

    public int getSmsServerPort() {
        return mSmsServerPort;
    }

    public void setSmsServerPort(int mSmsServerPort) {
        this.mSmsServerPort = mSmsServerPort;
    }

    public String getP2pServer() {
        return mP2pServer;
    }

    public void setP2pServer(String mP2pServer) {
        this.mP2pServer = mP2pServer;
    }

    public int getP2pServerPort() {
        return mP2pServerPort;
    }

    public void setP2pServerPort(int mP2pServerPort) {
        this.mP2pServerPort = mP2pServerPort;
    }

    public String getFileServer() {
        return mFileServer;
    }

    public void setFileServer(String mFileServer) {
        this.mFileServer = mFileServer;
    }

    public int getFileServerPort() {
        return mFileServerPort;
    }

    public void setFileServerPort(int mFileServerPort) {
        this.mFileServerPort = mFileServerPort;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }
}
