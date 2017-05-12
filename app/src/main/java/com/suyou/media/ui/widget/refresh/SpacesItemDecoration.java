package com.suyou.media.ui.widget.refresh;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int     mSpaceLeft;
    private int     mSpaceTop;
    private int     mSpaceRight;
    private int     mSpaceBottom;
    private boolean mHasHeader;

    public SpacesItemDecoration(int left, int top, int right, int bottom) {
        this.mSpaceLeft = left;
        this.mSpaceTop = top;
        this.mSpaceRight = right;
        this.mSpaceBottom = bottom;
    }

    public void hasHeader(boolean hasHeader) {
        mHasHeader = hasHeader;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int position = parent.getChildLayoutPosition(view);
        if (layoutManager instanceof GridLayoutManager) {
            if (0 == position && mHasHeader) {
                return;
            }
            outRect.bottom = mSpaceBottom;
            position = mHasHeader ? position - 1 : position;
            int count = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
            int index = position % count;
            if (position < count) {
                outRect.top = mSpaceTop;
            }
            if (0 == index) {
                outRect.left = mSpaceLeft;
                outRect.right = mSpaceRight / 2;
            } else if (count - 1 == index) {
                outRect.left = mSpaceLeft / 2;
                outRect.right = mSpaceRight;
            } else {
                outRect.left = mSpaceLeft / 2;
                outRect.right = mSpaceRight / 2;
            }
        } else {
            outRect.left = mSpaceLeft;
            outRect.bottom = mSpaceBottom;
            outRect.right = mSpaceRight;
            outRect.top = mSpaceTop;
        }
    }
}