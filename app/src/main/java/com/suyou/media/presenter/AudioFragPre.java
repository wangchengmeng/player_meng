package com.suyou.media.presenter;

import android.app.Activity;

import com.suyou.media.app.BasePresenter;
import com.suyou.media.bean.AudioCateBean;
import com.suyou.media.config.ConstantSet;
import com.suyou.media.ui.view.AudioFragmentView;
import com.suyou.media.util.JsonUtil;
import com.suyou.media.util.rx.RxUtil;
import com.suyou.media.util.rx.SubscriberAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class AudioFragPre extends BasePresenter<AudioFragmentView> {

    private int mCurrentPage = 1;

    public AudioFragPre(Activity activity, AudioFragmentView view) {
        super(activity, view);
    }

    public void getData(final int status) {
        if (PULL_DOWN == status) {
            mCurrentPage = 1;
        }
        SubscriberAdapter<List<AudioCateBean>> subscriber = new SubscriberAdapter<List<AudioCateBean>>() {
            @Override
            public void onNext(List<AudioCateBean> o) {
                mView.dismissLoading();
                mView.update(status, o);
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoading();
                super.onError(e);
            }
        };
        Observable.create(new Observable.OnSubscribe<List<AudioCateBean>>() {
            @Override
            public void call(Subscriber<? super List<AudioCateBean>> subscriber) {
                try {
                    subscriber.onNext(JsonUtil.getJson("audio_cate.json", AudioCateBean.class));
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).map(new Func1<List<AudioCateBean>, List<AudioCateBean>>() {
            @Override
            public List<AudioCateBean> call(List<AudioCateBean> list) {
                List<AudioCateBean> tempList = new ArrayList<>();
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
        }).compose(RxUtil.<List<AudioCateBean>>ioMain())
                .subscribe(subscriber);
        addSubscrebe(subscriber);
    }
}
