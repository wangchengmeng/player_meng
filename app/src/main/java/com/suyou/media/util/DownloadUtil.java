package com.suyou.media.util;

import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.suyou.media.bean.DownloadBaseBean;
import com.suyou.media.config.ConstantSet;
import com.suyou.media.listener.OnDownloadListener;
import com.suyou.media.util.bus.BusUtil;
import com.suyou.media.util.bus.EventBusBean;

import java.io.File;

/**
 * 下载工具封装类
 *
 * @author zou.sq
 */
public class DownloadUtil {
    public static void startDownload(String url, String path) {
        File parent = new File(path).getParentFile();
        if (null != parent && !parent.exists()) {
            if (!parent.mkdirs()) {
                Log.d("aaa", "创建父目录失败");
            }
        }
        final BaseDownloadTask task = FileDownloader.getImpl().create(url)
                .setPath(path)
                .setListener(new OnDownloadListener())
                .setCallbackProgressMinInterval(500);
        task.start();
        Log.d("aaa", "点击下载");
    }

    public static void pauseDownload(String url, String path) {
        int id = FileDownloadUtils.generateId(url, path);
        FileDownloader.getImpl().pause(id);
        Log.d("aaa", "暂停下载");
    }

    public static void notifyProgress(String url, int status, int soFarBytes, int totalBytes) {
        if (totalBytes <= 0 || soFarBytes < 0) {
            return;
        }
        DownloadBaseBean model = new DownloadBaseBean();
        model.setProgress((int) ((float) soFarBytes / totalBytes * 100));
        model.setDownloadUrl(url);
        model.setStatus(status);
        BusUtil.post(new EventBusBean(ConstantSet.KEY_EVENT_ACTION_DOWNLOAD_UPDATE, model));
    }

    public static int getProgress(String url, String path) {
        int id = FileDownloadUtils.generateId(url, path);
        long soFar = FileDownloader.getImpl().insureServiceBind().getSoFar(id);
        long total = FileDownloader.getImpl().insureServiceBind().getTotal(id);
        Log.d("aaa", "getProgress soFarBytes:" + soFar + "---total:" + total + "----+progress:" + ((int) ((float) soFar / total * 100)));
        if (total == 0) {
            return 0;
        }
        return (int) ((float) soFar / total * 100);
    }

    public static int getDownloadStatus(String url, String path) {
        byte status = FileDownloader.getImpl().getStatus(url, path);
        if (FileDownloadStatus.completed == status) {
            return DownloadBaseBean.DOWNLOAD_OVER;
        }
        if (FileDownloadStatus.pending == status) {
            return DownloadBaseBean.PENDING;
        }
        if (FileDownloadStatus.isOver(status)) {
            return DownloadBaseBean.DOWNLOAD_STOP;
        }
        if (FileDownloadStatus.isIng(status)) {
            return DownloadBaseBean.DOWNLOAD_ING;
        }
        return DownloadBaseBean.NO_DOWNLOAD;
    }

}