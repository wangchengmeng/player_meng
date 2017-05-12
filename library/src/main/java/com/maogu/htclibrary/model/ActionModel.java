/**
 * @Project: Framework
 * @Title: ActionObject.java
 * @package com.maogu.htclibrary.authentication
 * @author tan.xx
 * @date 2013-12-17 下午4:18:52
 * @Copyright: 2013 www.paidui.cn Inc. All rights reserved.
 * @version V1.0
 */
package com.maogu.htclibrary.model;

import android.app.Activity;

import com.maogu.htclibrary.listener.RequestListener;

/**
 * 记录动作信息类
 *
 * @param <T> 动作回调
 * @author tan.xx
 */
public class ActionModel<T extends RequestListener> {
    // 接口回调
    private T        mListener;
    // 当前Activity
    private Activity mActivity;
    private boolean  mIsOnUiThreadCallBack;// 是否在UI线程中回调

    /**
     * 构造方法
     *
     * @param context              上下文
     * @param listener             回调
     * @param isOnUiThreadCallBack 是否在UI线程回调
     */
    public ActionModel(Activity context, T listener, boolean isOnUiThreadCallBack) {
        this.mActivity = context;
        this.mListener = listener;
        this.mIsOnUiThreadCallBack = isOnUiThreadCallBack;
    }

    public T getListener() {
        return mListener;
    }

    public void setListener(T listener) {
        this.mListener = listener;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    public boolean isOnUiThreadCallBack() {
        return mIsOnUiThreadCallBack;
    }
}
