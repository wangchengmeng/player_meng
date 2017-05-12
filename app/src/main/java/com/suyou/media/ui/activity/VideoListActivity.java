package com.suyou.media.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.View.OnClickListener;

import com.maogu.htclibrary.util.StringUtil;
import com.suyou.media.R;
import com.suyou.media.app.BaseActivity;
import com.suyou.media.bean.DownloadBaseBean;
import com.suyou.media.bean.VideoBean;
import com.suyou.media.config.ConstantSet;
import com.suyou.media.presenter.VideoPre;
import com.suyou.media.ui.adapter.VideoAdapter;
import com.suyou.media.ui.view.VideoView;
import com.suyou.media.ui.widget.refresh.AutoLoadRecyclerView;
import com.suyou.media.ui.widget.refresh.OnLoadListener;
import com.suyou.media.ui.widget.refresh.RefreshConstantSet;
import com.suyou.media.ui.widget.refresh.SwipeRefreshLayout;
import com.suyou.media.util.ViewUtil;
import com.suyou.media.util.bus.EventBusBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class VideoListActivity extends BaseActivity<VideoPre> implements OnClickListener, VideoView {

    private AutoLoadRecyclerView mRvContent;
    private VideoAdapter         mMainAdapter;
    private List<VideoBean>      mVideoBeans;
    private int                  mCateId;

    public static void startActivity(Activity activity, int cateId) {
        Intent intent = new Intent(activity, VideoListActivity.class);
        intent.putExtra(ConstantSet.KEY_CATE_ID, cateId);
        activity.startActivity(intent);
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
        mCateId = getIntent().getIntExtra(ConstantSet.KEY_CATE_ID, -1);
        mVideoBeans = new ArrayList<>();
        mMainAdapter = new VideoAdapter(this, mVideoBeans);
    }

    private void initViews() {
        super.initTitle();
        setTitleText(getString(R.string.video));
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
        mRvContent.setLayoutManager(new GridLayoutManager(this, 3));
        mRvContent.setAdapter(mMainAdapter);
        RefreshConstantSet.setSpace(mRvContent, 10, 10, 10, 10, false);
        mRvContent.setItemAnimator(null);
        mRvContent.setRefreshing(true);
    }

    @Override
    protected VideoPre getPresenter() {
        return new VideoPre(this, this);
    }

    private void getData(int status) {
        mPresenter.getData(status, mCateId);
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
    public void update(int status, List<VideoBean> list) {
        if (PULL_DOWN == status) {
            mVideoBeans.clear();
        }
        mRvContent.hasMoreData(list.size() == ConstantSet.PAGE_SIZE);
        mVideoBeans.addAll(list);
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
        for (int i = 0; i < mVideoBeans.size(); i++) {
            VideoBean bean = mVideoBeans.get(i);
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
