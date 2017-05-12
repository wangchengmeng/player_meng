package com.suyou.media.app;

import android.app.Activity;

import com.suyou.media.config.ConstantSet;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T extends IView> implements IPresenter {

    protected static final int PULL_DOWN = ConstantSet.PULL_DOWN;
    protected static final int PULL_UP   = ConstantSet.PULL_UP;
    protected Activity              mActivity;
    protected T                     mView;
    protected CompositeSubscription mCompositeSubscription;

    public BasePresenter(Activity activity, T view) {
        this.mActivity = activity;
        this.mView = view;
    }

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}