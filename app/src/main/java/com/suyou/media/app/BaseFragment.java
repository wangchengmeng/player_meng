package com.suyou.media.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.maogu.htclibrary.util.DensityUtils;
import com.maogu.htclibrary.util.UIUtil;
import com.maogu.htclibrary.widget.LoadingUpView;
import com.suyou.media.R;
import com.suyou.media.config.ConstantSet;
import com.suyou.media.util.bus.BusUtil;
import com.suyou.media.util.bus.EventBusBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseFragment<T extends IPresenter> extends Fragment {

    protected static final int PULL_DOWN = ConstantSet.PULL_DOWN;
    protected static final int PULL_UP   = ConstantSet.PULL_UP;

    protected TextView mTvTitle;
    protected View     mViewStatusBar;
    protected TextView mTvTitleLeft;
    protected TextView mTvTitleRight;
    protected View     mViewTitle;
    private   View     mViewTitleLeft;
    private   View     mViewTitleRight;
    protected T        mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusUtil.register(this);
        mPresenter = getPresenter();
    }

    protected abstract T getPresenter();

    protected void initTitle(View layout) {
        mViewTitle = layout.findViewById(R.id.view_title);
        mViewStatusBar = layout.findViewById(R.id.view_status_bar);
        if (null != mViewStatusBar) {
            UIUtil.setViewHeight(mViewStatusBar, DensityUtils.getStatusHeight());
        }
        if (null != mViewTitle) {
            measure(mViewTitle, 0, 140);
            mViewTitleLeft = layout.findViewById(R.id.ll_title_left);
            mViewTitleRight = layout.findViewById(R.id.ll_title_right);
            mTvTitle = (TextView) layout.findViewById(R.id.tv_title_mid);
            mTvTitleLeft = (TextView) layout.findViewById(R.id.tv_title_left);
            mTvTitleRight = (TextView) layout.findViewById(R.id.tv_title_right);
        }
    }

    protected void setTitleBg(int color) {
        if (null != mViewTitle) {
            mViewTitle.setBackgroundColor(color);
        }
    }

    protected void setTitleText(String title) {
        if (null != mTvTitle) {
            mTvTitle.setText(title);
        }
    }

    protected void setTitleTextColor(int color) {
        if (null != mTvTitle) {
            mTvTitle.setTextColor(color);
        }
    }

    protected void setTitleListener(View.OnClickListener listener) {
        if (null != listener & null != mTvTitle) {
            mTvTitle.setOnClickListener(listener);
        }
    }

    //左边标题
    protected void setTitleLeftText(String title, View.OnClickListener listener) {
        if (null != mTvTitleLeft) {
            mTvTitleLeft.setText(title);
        }
        if (null != listener & null != mViewTitleLeft) {
            mViewTitleLeft.setOnClickListener(listener);
        }
    }

    protected void setTitleLeftIcon(int resId, View.OnClickListener listener) {
        if (null != mTvTitleLeft) {
            mTvTitleLeft.setBackgroundResource(resId);
            measure(mTvTitleLeft, 50, 50);
        }
        if (null != listener & null != mViewTitleLeft) {
            mViewTitleLeft.setOnClickListener(listener);
        }
    }

    //左边标题
    protected void setTitleRightText(String title, View.OnClickListener listener) {
        if (null != mTvTitleRight) {
            mTvTitleRight.setText(title);
        }
        if (null != listener & null != mViewTitleRight) {
            mViewTitleRight.setOnClickListener(listener);
        }
    }

    protected void setTitleRightIcon(int resId, View.OnClickListener listener) {
        if (null != mTvTitleRight) {
            mTvTitleRight.setBackgroundResource(resId);
            measure(mTvTitleRight, 50, 50);
        }
        if (null != listener & null != mViewTitleRight) {
            mViewTitleRight.setOnClickListener(listener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        BusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean model) {

    }

    protected void measure(View view, int width, int height) {
        DensityUtils.measure(view, width, height);
    }

    public boolean showLoadingUpView(LoadingUpView loadingUpView) {
        return showLoadingUpView(loadingUpView, "");
    }

    public boolean showLoadingUpView(LoadingUpView loadingUpView, String info) {
        if (loadingUpView != null && !loadingUpView.isShowing()) {
            if (null == info) {
                info = "";
            }
            loadingUpView.showPopup(info);
            return true;
        }
        return false;
    }

    public boolean dismissLoadingUpView(LoadingUpView loadingUpView) {
        if (loadingUpView != null && loadingUpView.isShowing()) {
            loadingUpView.dismiss();
            return true;
        }
        return false;
    }

}
