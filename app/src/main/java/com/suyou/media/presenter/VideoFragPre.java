package com.suyou.media.presenter;

import android.app.Activity;

import com.suyou.media.app.BasePresenter;
import com.suyou.media.bean.BannerBean;
import com.suyou.media.bean.VideoCateBean;
import com.suyou.media.config.ConstantSet;
import com.suyou.media.ui.view.VideoFragmentView;
import com.suyou.media.util.JsonUtil;
import com.suyou.media.util.rx.RxUtil;
import com.suyou.media.util.rx.SubscriberAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class VideoFragPre extends BasePresenter<VideoFragmentView> {

    private int mCurrentPage = 1;

    public VideoFragPre(Activity activity, VideoFragmentView view) {
        super(activity, view);
    }

    public void getData(final int status) {
        if (PULL_DOWN == status) {
            mCurrentPage = 1;
        }
        SubscriberAdapter<List<VideoCateBean>> subscriber = new SubscriberAdapter<List<VideoCateBean>>() {
            @Override
            public void onNext(List<VideoCateBean> o) {
                mView.dismissLoading();
                mView.update(status, o);
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoading();
                super.onError(e);
            }
        };
        Observable.create(new Observable.OnSubscribe<List<VideoCateBean>>() {
            @Override
            public void call(Subscriber<? super List<VideoCateBean>> subscriber) {
                try {
                    subscriber.onNext(JsonUtil.getJson("video_cate.json", VideoCateBean.class));
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<List<VideoCateBean>, List<VideoCateBean>>() {
            @Override
            public List<VideoCateBean> call(List<VideoCateBean> list) {
                List<VideoCateBean> tempList = new ArrayList<>();
                if (null == list) {
                    return tempList;
                }
                int start = (mCurrentPage - 1) * ConstantSet.PAGE_SIZE;
                int end = start + ConstantSet.PAGE_SIZE;
                if (list.size() <= start) {
                    return tempList;
                }
                tempList.addAll(list.subList(start, Math.min(end, list.size())));
                return tempList;
            }
        }).compose(RxUtil.<List<VideoCateBean>>ioMain())
                .subscribe(subscriber);
        addSubscrebe(subscriber);
    }

    public void getBanner() {
        SubscriberAdapter<List<BannerBean>> subscriber = new SubscriberAdapter<List<BannerBean>>() {
            @Override
            public void onNext(List<BannerBean> o) {
                if (null == o) {
                    o = new ArrayList<>();
                }
                mView.updateBanner(o);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        };
        Observable.create(new Observable.OnSubscribe<List<BannerBean>>() {
            @Override
            public void call(Subscriber<? super List<BannerBean>> subscriber) {
                try {
                    subscriber.onNext(JsonUtil.getJson("main_banner.json", BannerBean.class));
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).compose(RxUtil.<List<BannerBean>>ioMain())
                .subscribe(subscriber);
        addSubscrebe(subscriber);
    }
}
