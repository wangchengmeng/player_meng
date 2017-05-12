package com.suyou.media.app;

import android.os.StrictMode;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.maogu.htclibrary.app.HtcApplicationBase;
import com.maogu.htclibrary.util.PackageUtil;
import com.suyou.media.util.DBUtil;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 全局应用程序
 */
public class HtcApplication extends HtcApplicationBase {

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                DBUtil.getDataManager().firstOpen();
            }
        }).start();
        initStrictMode();
        initThird();
    }

    private void initStrictMode() {
        if (PackageUtil.getConfigBoolean("debug_model")) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }

    private void initThird() {
        FileDownloadLog.NEED_LOG = true;
        FileDownloader.init(getApplicationContext(),
                new FileDownloadHelper.OkHttpClientCustomMaker() {
                    @Override
                    public OkHttpClient customMake() {
                        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        builder.connectTimeout(15_000, TimeUnit.MILLISECONDS);
                        builder.proxy(Proxy.NO_PROXY);
                        return builder.build();
                    }
                });
    }
}
