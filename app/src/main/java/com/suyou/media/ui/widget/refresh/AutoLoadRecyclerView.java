package com.suyou.media.ui.widget.refresh;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.maogu.htclibrary.util.EvtLog;

public class AutoLoadRecyclerView extends RecyclerView {

    private boolean            isLoadingMore;
    private boolean            mHasMore;
    private OnLoadListener     mLoadListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public AutoLoadRecyclerView(Context context) {
        this(context, null);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        isLoadingMore = false;
        addOnScrollListener(new AutoLoadScrollListener());
    }

    /**
     * 设置上拉加载更多的监听
     *
     * @param loadListener 上拉加载更多的监听
     */
    public void setAllListener(SwipeRefreshLayout layout, OnLoadListener loadListener) {
        RefreshConstantSet.setColorSchemeResources(layout);
        this.mLoadListener = loadListener;
        mSwipeRefreshLayout = layout;
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (null != mLoadListener) {
                        mLoadListener.onRefresh();
                    }
                }
            });
        }
    }

    /**
     * 自动加载
     *
     * @param refreshing 设置自动加载数据
     */
    public void setRefreshing(final boolean refreshing) {
        if (null == mSwipeRefreshLayout) {
            return;
        }
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (refreshing) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    if (null != mLoadListener) {
                        mLoadListener.onRefresh();
                    }
                } else {
                    loadFinish();
                }
            }
        });
    }

    public void hasMoreData(boolean hasMore) {
        mHasMore = hasMore;
    }

    /**
     * 加载数据完成调用
     */
    private void loadFinish() {
        isLoadingMore = false;
        if (null != mSwipeRefreshLayout) {
            post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    /**
     * 滑动自动加载监听器
     */
    private class AutoLoadScrollListener extends OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            //由于GridLayoutManager是LinearLayoutManager子类，所以也适用
            if (getLayoutManager() instanceof LinearLayoutManager) {
                int lastVisibleItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = AutoLoadRecyclerView.this.getAdapter().getItemCount();

                //有回调接口，并且不是加载状态，并且剩下2个item，并且向下滑动，则自动加载
                EvtLog.d("aaa", (mLoadListener != null) + "--" + !isLoadingMore + "---" + (lastVisibleItem >= totalItemCount - 2) + "---" + (dy > 0) + "---" + mHasMore);
                if (mLoadListener != null && !isLoadingMore && lastVisibleItem >= totalItemCount -
                        2 && dy > 0 && mHasMore) {
                    if (null != mSwipeRefreshLayout) {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                    mLoadListener.onLoadMore();
                    isLoadingMore = true;
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }
    }
}