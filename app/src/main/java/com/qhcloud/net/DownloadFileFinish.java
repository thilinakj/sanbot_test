package com.qhcloud.net;

/**
 * Created by admin on 2017/3/3.
 */
public class DownloadFileFinish {
    private long fileId;

    private int thumbnails;

    private int cancelFlag;

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public int getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(int thumbnails) {
        this.thumbnails = thumbnails;
    }

    public int getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(int cancelFlag) {
        this.cancelFlag = cancelFlag;
    }
}
