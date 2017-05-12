package com.suyou.media.bean;

import com.maogu.htclibrary.orm.BaseModel;

public class DownloadBaseBean extends BaseModel {

    public final static int HAS_INSTALL   = 1;
    public final static int NO_DOWNLOAD   = 2;
    public final static int PENDING       = 3;
    public final static int DOWNLOAD_ING  = 4;
    public final static int DOWNLOAD_STOP = 5;
    public final static int DOWNLOAD_OVER = 6;
    public final static int HAS_UNZIP     = 7;
    public final static int UNZIP_ING     = 8;

    private String downloadUrl;
    private int    progress;
    private int    status;
    private String path;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
