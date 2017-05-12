package com.suyou.media.presenter;

import android.app.Activity;
import android.util.Log;

import com.suyou.media.app.BasePresenter;
import com.suyou.media.bean.VideoBean;
import com.suyou.media.config.ConstantSet;
import com.suyou.media.ui.view.VideoView;
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

import static android.R.attr.type;

public class VideoPre extends BasePresenter<VideoView> {

    private int mCurrentPage = 1;

    public VideoPre(Activity activity, VideoView view) {
        super(activity, view);
    }

    public void getData(final int status, final int cateId) {
        if (PULL_DOWN == status) {
            mCurrentPage = 1;
        }
        SubscriberAdapter<List<VideoBean>> subscriber = new SubscriberAdapter<List<VideoBean>>() {
            @Override
            public void onNext(List<VideoBean> o) {
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
        Observable.create(new Observable.OnSubscribe<List<VideoBean>>() {
            @Override
            public void call(Subscriber<? super List<VideoBean>> subscriber) {
                try {
                    subscriber.onNext(JsonUtil.getJson("video.json", VideoBean.class));
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<List<VideoBean>, List<VideoBean>>() {
            @Override
            public List<VideoBean> call(List<VideoBean> list) {
                List<VideoBean> tempList = new ArrayList<>();
                if (null == list) {
                    return tempList;
                }
                for (VideoBean bean : list) {
                    if (cateId == bean.getType()) {
                        tempList.add(bean);
                    }
                }
                return tempList;
            }
        }).map(new Func1<List<VideoBean>, List<VideoBean>>() {
            @Override
            public List<VideoBean> call(List<VideoBean> list) {
                List<VideoBean> tempList = new ArrayList<>();
                if (null == list) {
                    return tempList;
                }
                int start = (mCurrentPage - 1) * ConstantSet.PAGE_SIZE;
                int end = start + ConstantSet.PAGE_SIZE;
                if (list.size() <= start) {
                    return tempList;
                }
                tempList.addAll(list.subList(start, Math.min(end, list.size())));
                for (VideoBean bean : tempList) {
                    String path = new File(StorageUtils.getCacheDirectory(mActivity, "video"), bean.getName()).getAbsolutePath();
                    bean.setPath(path);
                    bean.setStatus(DownloadUtil.getDownloadStatus(bean.getDownloadUrl(), path));
                    int progress = DownloadUtil.getProgress(bean.getDownloadUrl(), path);
                    Log.d("aaa", "----" + progress);
                    bean.setProgress(progress);
                }
                return tempList;
            }
        }).compose(RxUtil.<List<VideoBean>>ioMain())
                .subscribe(subscriber);
        addSubscrebe(subscriber);
    }
}
