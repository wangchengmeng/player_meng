package com.suyou.media.ui.widget.refresh;


import android.support.v7.widget.RecyclerView;

import com.maogu.htclibrary.util.DensityUtils;
import com.suyou.media.R;


public class RefreshConstantSet {
    public static void setColorSchemeResources(SwipeRefreshLayout layout) {
        layout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorPrimary, R.color.colorPrimaryDark);
    }

    /**
     * 设置间距
     *
     * @param recyclerView RecyclerView
     * @param space        间距值
     */
    public static void setSpace(RecyclerView recyclerView, int space) {
        setSpace(recyclerView, space, false);
    }

    public static void setSpace(RecyclerView recyclerView, int space, boolean hasHeader) {
        int spaceValue = DensityUtils.dip2px(space);
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(spaceValue, spaceValue, spaceValue, spaceValue);
        itemDecoration.hasHeader(hasHeader);
        recyclerView.addItemDecoration(itemDecoration);
    }

    public static void setSpace(RecyclerView recyclerView, int left, int top, int right, int bottom, boolean hasHeader) {
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(DensityUtils.dip2px(left), DensityUtils.dip2px(top), DensityUtils.dip2px(right), DensityUtils.dip2px(bottom));
        itemDecoration.hasHeader(hasHeader);
        recyclerView.addItemDecoration(itemDecoration);
    }
}