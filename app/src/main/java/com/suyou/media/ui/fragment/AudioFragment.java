package com.suyou.media.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suyou.media.R;
import com.suyou.media.app.BaseFragment;
import com.suyou.media.bean.AudioCateBean;
import com.suyou.media.config.ConstantSet;
import com.suyou.media.presenter.AudioFragPre;
import com.suyou.media.ui.adapter.AudioCateAdapter;
import com.suyou.media.ui.view.AudioFragmentView;
import com.suyou.media.ui.widget.refresh.AutoLoadRecyclerView;
import com.suyou.media.ui.widget.refresh.OnLoadListener;
import com.suyou.media.ui.widget.refresh.RefreshConstantSet;
import com.suyou.media.ui.widget.refresh.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class AudioFragment extends BaseFragment<AudioFragPre> implements View.OnClickListener, AudioFragmentView {

    private AutoLoadRecyclerView mRvContent;

    private AudioCateAdapter    mMainAdapter;
    private List<AudioCateBean> mAudioCateBeans;

    public static AudioFragment newInstance() {
        return new AudioFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
    }

    private void initVariables() {
        mAudioCateBeans = new ArrayList<>();
        mMainAdapter = new AudioCateAdapter(getActivity(), mAudioCateBeans);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = View.inflate(getActivity(), R.layout.fragment_audio, null);
        initViews(layout);
        return layout;
    }

    private void initViews(View layout) {
        super.initTitle(layout);
        setTitleText(getString(R.string.app_name));
        setTitleRightIcon(R.mipmap.search_icon_white, this);

        mRvContent = (AutoLoadRecyclerView) layout.findViewById(R.id.rv_list);
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
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
        mRvContent.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRvContent.setAdapter(mMainAdapter);
        RefreshConstantSet.setSpace(mRvContent, 10, 10, 10, 10, false);
        mRvContent.setItemAnimator(null);
        mRvContent.setRefreshing(true);
    }

    @Override
    protected AudioFragPre getPresenter() {
        return new AudioFragPre(getActivity(), this);
    }

    private void getData(int status) {
        mPresenter.getData(status);
    }

    @Override
    public void update(int status, List<AudioCateBean> list) {
        if (PULL_DOWN == status) {
            mAudioCateBeans.clear();
        }
        mRvContent.hasMoreData(list.size() == ConstantSet.PAGE_SIZE);
        mAudioCateBeans.addAll(list);
        mMainAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissLoading() {
        mRvContent.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }
}
