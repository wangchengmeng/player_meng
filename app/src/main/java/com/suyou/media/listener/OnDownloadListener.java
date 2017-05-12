package com.suyou.media.listener;

import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.suyou.media.bean.DownloadBaseBean;
import com.suyou.media.util.DownloadUtil;

public class OnDownloadListener extends FileDownloadSampleListener {
    @Override
    protected void completed(BaseDownloadTask task) {
        super.completed(task);
        DownloadUtil.notifyProgress(task.getUrl(), DownloadBaseBean.DOWNLOAD_OVER, 0, 1);
        Log.d("aaa", "completed:" + task.getUrl());
    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
        super.error(task, e);
        DownloadUtil.notifyProgress(task.getUrl(), DownloadBaseBean.DOWNLOAD_STOP, DownloadUtil.getProgress(task.getUrl(), task.getTargetFilePath()), 100);
        Log.d("aaa", "error:" + task.getUrl() + "\r\n" + e.toString());
    }

    @Override
    protected void warn(BaseDownloadTask task) {
        super.warn(task);
        //下载停止状态，按钮变成下载，能点击
        Log.d("aaa", "warn:" + task.getUrl());
    }

    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        super.pending(task, soFarBytes, totalBytes);
        DownloadUtil.notifyProgress(task.getUrl(), DownloadBaseBean.PENDING, soFarBytes, totalBytes);
        Log.d("aaa", "pending:" + task.getUrl());
    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        super.progress(task, soFarBytes, totalBytes);
        DownloadUtil.notifyProgress(task.getUrl(), DownloadBaseBean.DOWNLOAD_ING, soFarBytes, totalBytes);
        Log.d("aaa", "progress:" + ((float) soFarBytes / totalBytes * 100) + task.getUrl());
    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        super.paused(task, soFarBytes, totalBytes);
        DownloadUtil.notifyProgress(task.getUrl(), DownloadBaseBean.DOWNLOAD_STOP, soFarBytes, totalBytes);
        Log.d("aaa", "paused:" + task.getUrl());
    }
}
