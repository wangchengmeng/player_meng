package com.suyou.media.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suyou.media.R;
import com.suyou.media.app.BaseFragment;
import com.suyou.media.bean.BannerBean;
import com.suyou.media.bean.VideoCateBean;
import com.suyou.media.config.ConstantSet;
import com.suyou.media.listener.OnPageChangeListener;
import com.suyou.media.presenter.VideoFragPre;
import com.suyou.media.ui.adapter.BannerAdapter;
import com.suyou.media.ui.adapter.VideoCateAdapter;
import com.suyou.media.ui.view.VideoFragmentView;
import com.suyou.media.ui.widget.AutoScrollViewPager;
import com.suyou.media.ui.widget.CircleFlowIndicator;
import com.suyou.media.ui.widget.refresh.AutoLoadRecyclerView;
import com.suyou.media.ui.widget.refresh.OnLoadListener;
import com.suyou.media.ui.widget.refresh.RefreshConstantSet;
import com.suyou.media.ui.widget.refresh.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends BaseFragment<VideoFragPre> implements View.OnClickListener, VideoFragmentView {

    private AutoLoadRecyclerView mRvContent;
    private AutoScrollViewPager  mViewPager;
    private CircleFlowIndicator  mIndicator;

    private BannerAdapter       mBannerAdapter;
    private List<BannerBean>    mBannerBeen;
    private VideoCateAdapter    mVideoCateAdapter;
    private List<VideoCateBean> mVideoCateBeans;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
    }

    private void initVariables() {
        mBannerBeen = new ArrayList<>();
        mVideoCateBeans = new ArrayList<>();
        mBannerAdapter = new BannerAdapter(getActivity(), mBannerBeen);
        mVideoCateAdapter = new VideoCateAdapter(getActivity(), mVideoCateBeans);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = View.inflate(getActivity(), R.layout.fragment_audio, null);
        initViews(layout);
        initHeader();
        return layout;
    }

    private void initHeader() {
        View viewHeader = View.inflate(getActivity(), R.layout.view_banner, null);
        mViewPager = (AutoScrollViewPager) viewHeader.findViewById(R.id.scroll_pager);
        mIndicator = (CircleFlowIndicator) viewHeader.findViewById(R.id.indicator);
        measure(mViewPager, 0, 630);
        mViewPager.setCycle(true);
        mViewPager.startAutoScroll(8000);
        mViewPager.setInterval(8000);
        mVideoCateAdapter.addHeaderView(viewHeader);
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
        mRvContent.setAdapter(mVideoCateAdapter);
        RefreshConstantSet.setSpace(mRvContent, 10, 10, 10, 10, true);
        mRvContent.setItemAnimator(null);
        mRvContent.setRefreshing(true);
    }

    @Override
    protected VideoFragPre getPresenter() {
        return new VideoFragPre(getActivity(), this);
    }

    private void getData(int status) {
        if (PULL_DOWN == status) {
            mPresenter.getBanner();
        }
        mPresenter.getData(status);
    }

    @Override
    public void updateBanner(List<BannerBean> list) {
        mBannerBeen.clear();
        mBannerBeen.addAll(list);
        final int size = list.size();
        mViewPager.setAdapter(mBannerAdapter);
        mIndicator.setCount(size);
        if (size <= 1) {
            mIndicator.setVisibility(View.GONE);
        } else {
            mIndicator.setVisibility(View.VISIBLE);
            mIndicator.setSelection(0);
            mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    mIndicator.setSelection(position % size);
                }
            });
        }
    }

    @Override
    public void update(int status, List<VideoCateBean> list) {
        if (PULL_DOWN == status) {
            mVideoCateBeans.clear();
        }
        mRvContent.hasMoreData(list.size() == ConstantSet.PAGE_SIZE);
        mVideoCateBeans.addAll(list);
        mVideoCateAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissLoading() {
        mRvContent.setRefreshing(false);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroyView() {
        mViewPager.stopAutoScroll();
        super.onDestroyView();
    }
}
