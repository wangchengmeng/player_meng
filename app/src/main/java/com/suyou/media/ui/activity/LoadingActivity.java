package com.suyou.media.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.widget.ImageView;

import com.suyou.media.R;
import com.suyou.media.app.BaseActivity;
import com.suyou.media.presenter.LoadingPre;
import com.suyou.media.ui.view.LoadingView;

public class LoadingActivity extends BaseActivity<LoadingPre> implements LoadingView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initVariables();
    }

    private void initVariables() {
        mPresenter.requestPermission();
    }

    @Override
    protected LoadingPre getPresenter() {
        return new LoadingPre(this, this);
    }

    @Override
    public void showAnim() {
        ImageView ivLoading = (ImageView) findViewById(R.id.iv_loading);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0.5f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(ivLoading, pvhX, pvhY, pvhZ).setDuration(3000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                MainActivity.intentTo(LoadingActivity.this);
                finish();
            }
        });
        animator.start();
    }

    @Override
    public void onBackPressed() {
    }
}
