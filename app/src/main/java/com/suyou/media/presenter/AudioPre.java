package com.suyou.media.presenter;

import android.app.Activity;
import android.util.Log;

import com.suyou.media.app.BasePresenter;
import com.suyou.media.bean.AudioBean;
import com.suyou.media.config.ConstantSet;
import com.suyou.media.ui.view.AudioView;
import com.suyou.media.util.DownloadUtil;
import com.suyou.media.util.JsonUtil;
import com.suyou.media.util.StorageUtils;
import com.suyou.media.util.rx.RxUtil;
import com.suyou.media.util.rx.SubscriberAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class AudioPre extends BasePresenter<AudioView> {

    private int mCurrentPage = 1;

    public AudioPre(Activity activity, AudioView view) {
        super(activity, view);
    }

    public void getData(final int status) {
        if (PULL_DOWN == status) {
            mCurrentPage = 1;
        }
        SubscriberAdapter<List<AudioBean>> subscriber = new SubscriberAdapter<List<AudioBean>>() {
            @Override
            public void onNext(List<AudioBean> o) {
                mView.dismissLoading();
                mView.update(status, o);
                mCurrentPage++;
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoading();
                super.onError(e);
            }
        };
        Observable.create(new Observable.OnSubscribe<List<AudioBean>>() {
            @Override
            public void call(Subscriber<? super List<AudioBean>> subscriber) {
                try {
                    subscriber.onNext(JsonUtil.getJson("music.json", AudioBean.class));
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<List<AudioBean>, List<AudioBean>>() {
            @Override
            public List<AudioBean> call(List<AudioBean> list) {
                List<AudioBean> tempList = new ArrayList<>();
                if (null == list) {
                    return tempList;
                }
                int start = (mCurrentPage - 1) * ConstantSet.PAGE_SIZE;
                int end = start + ConstantSet.PAGE_SIZE;
                if (list.size() <= start) {
                    return tempList;
                }
                tempList.addAll(list.subList(start, Math.min(end, list.size())));
                for (AudioBean bean : tempList) {
                    String path = new File(StorageUtils.getCacheDirectory(mActivity, "audio"), bean.getTitle()).getAbsolutePath();
                    bean.setPath(path);
                    bean.setStatus(DownloadUtil.getDownloadStatus(bean.getDownloadUrl(), path));
                    int progress = DownloadUtil.getProgress(bean.getDownloadUrl(), path);
                    Log.d("aaa", "----" + progress);
                    bean.setProgress(progress);
                }
                return tempList;
            }
        }).compose(RxUtil.<List<AudioBean>>ioMain())
                .subscribe(subscriber);
        addSubscrebe(subscriber);
    }
}
