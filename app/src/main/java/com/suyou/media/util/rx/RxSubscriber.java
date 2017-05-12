package com.suyou.media.util.rx;

import android.app.Activity;

import com.maogu.htclibrary.auth.ActionResult;
import com.suyou.media.R;

import rx.Subscriber;

/**
 * 封装Subscriber
 */
public class RxSubscriber<T> extends Subscriber<ActionResult<T>> {

    private Activity mActivity;

    public RxSubscriber(Activity activity) {
        super();
        mActivity = activity;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        _onError(mActivity.getString(R.string.network_is_not_available));
        e.printStackTrace();
    }

    @Override
    public void onNext(ActionResult<T> t) {
        switch (t.ResultCode) {
            case ActionResult.RESULT_CODE_NO_LOGIN:
                onNoAuth();
                break;
            case ActionResult.RESULT_CODE_SUCCESS:
                _onNext(t.ResultObject);
                break;
            default:
                if (null != t.ResultObject) {
                    _onError(t.ResultObject.toString());
                } else {
                    _onError(mActivity.getString(R.string.network_is_not_available));
                }
                break;
        }
    }

    public void _onNext(T t) {

    }

    public void _onError(String error) {

    }

    public void onNoAuth() {
    }
}