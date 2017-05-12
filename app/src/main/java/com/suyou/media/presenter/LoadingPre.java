package com.suyou.media.presenter;

import android.Manifest;
import android.app.Activity;

import com.suyou.media.app.BasePresenter;
import com.suyou.media.ui.view.LoadingView;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

public class LoadingPre extends BasePresenter<LoadingView> {

    public LoadingPre(Activity activity, LoadingView view) {
        super(activity, view);
    }

    public void requestPermission() {
        RxPermissions.getInstance(mActivity)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if (granted) {
                            mView.showAnim();
                        } else {
                            mActivity.finish();
                        }
                    }
                });
    }
}
