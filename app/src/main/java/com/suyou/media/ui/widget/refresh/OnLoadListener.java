package com.suyou.media.ui.widget.refresh;

public interface OnLoadListener {
    /**
     * 下拉刷新回调
     */
    void onRefresh();

    /**
     * 加载更多数据回调
     */
    void onLoadMore();
}