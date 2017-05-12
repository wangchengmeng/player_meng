package com.suyou.media.presenter;

import android.app.Activity;

import com.suyou.media.app.BasePresenter;
import com.suyou.media.ui.view.MainView;

public class MainPre extends BasePresenter<MainView> {

    public MainPre(Activity activity, MainView view) {
        super(activity, view);
    }

}
