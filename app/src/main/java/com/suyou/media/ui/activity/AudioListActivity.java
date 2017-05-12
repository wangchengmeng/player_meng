package com.suyou.media.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.View.OnClickListener;

import com.maogu.htclibrary.util.StringUtil;
import com.suyou.media.R;
import com.suyou.media.app.BaseActivity;
import com.suyou.media.bean.AudioBean;
import com.suyou.media.bean.DownloadBaseBean;
import com.suyou.media.config.ConstantSet;
import com.suyou.media.presenter.AudioPre;
import com.suyou.media.ui.adapter.AudioAdapter;
import com.suyou.media.ui.view.AudioView;
import com.suyou.media.ui.widget.refresh.AutoLoadRecyclerView;
import com.suyou.media.ui.widget.refresh.OnLoadListener;
import com.suyou.media.ui.widget.refresh.RefreshConstantSet;
import com.suyou.media.ui.widget.refresh.SwipeRefreshLayout;
import com.suyou.media.util.ViewUtil;
import com.suyou.media.util.bus.EventBusBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class AudioListActivity extends BaseActivity<AudioPre> implements OnClickListener, AudioView {

    private AutoLoadRecyclerView mRvContent;
    private AudioAdapter         mMainAdapter;
    private List<AudioBean>      mAudioBeans;

    public static void intentTo(Activity activity) {
        activity.startActivity(new Intent(activity, AudioListActivity.class));
        ViewUtil.right2LeftIn(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);
        initVariables();
        initViews();
    }

    private void initVariables() {
        ButterKnife.bind(this);
        mAudioBeans = new ArrayList<>();
        mMainAdapter = new AudioAdapter(this, mAudioBeans);
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.audio));
        setTitleLeftIcon(R.mipmap.btn_back_white, this);

        mRvContent = (AutoLoadRecyclerView) findViewById(R.id.rv_list);
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mRvContent.setAllListener(refreshLayout, new OnLoadListener() {
            @Override
            public void onRefresh() {
                getData(PULL_DOWN);
            }

            @Override
            public void onLoadMore() {
                getData(PULL_UP);
            }
        });
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(mMainAdapter);
        RefreshConstantSet.setSpace(mRvContent, 10, 10, 10, 0, false);
        mRvContent.setItemAnimator(null);
        mRvContent.setRefreshing(true);
    }

    @Override
    protected AudioPre getPresenter() {
        return new AudioPre(this, this);
    }

    private void getData(int status) {
        mPresenter.getData(status);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_left:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void dismissLoading() {
        mRvContent.setRefreshing(false);
    }

    @Override
    public void update(int status, List<AudioBean> list) {
        if (PULL_DOWN == status) {
            mAudioBeans.clear();
        }
        mRvContent.hasMoreData(list.size() == ConstantSet.PAGE_SIZE);
        mAudioBeans.addAll(list);
        mMainAdapter.notifyDataSetChanged();
    }

    public void onEventMainThread(final EventBusBean bean) {
        if (null == bean || StringUtil.isNullOrEmpty(bean.getEventBusAction())) {
            return;
        }
        switch (bean.getEventBusAction()) {
            case ConstantSet.KEY_EVENT_ACTION_DOWNLOAD_UPDATE:
                updateProgress((DownloadBaseBean) bean.getEventBusObject());
                break;
            default:
                break;
        }
    }

    private void updateProgress(DownloadBaseBean model) {
        if (null == model) {
            return;
        }
        for (int i = 0; i < mAudioBeans.size(); i++) {
            AudioBean bean = mAudioBeans.get(i);
            if (model.getDownloadUrl().equals(bean.getDownloadUrl())) {
                bean.setStatus(model.getStatus());
                bean.setProgress(model.getProgress());
                mMainAdapter.notifyItemChanged(i);
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        ViewUtil.left2RightOut(this);
    }
}
