package com.suyou.media.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.maogu.htclibrary.util.DensityUtils;
import com.maogu.htclibrary.util.PackageUtil;
import com.suyou.media.R;

public class ViewUtil {

    /**
     * 进入 从下往上动画
     *
     * @param activity 上下文对象
     */
    public static void bottom2TopIn(Activity activity) {
        if (null == activity) {
            return;
        }
        activity.overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.no_anim);
    }

    /**
     * 出去 从下往上动画
     *
     * @param activity 上下文对象
     */
    public static void bottom2TopOut(Activity activity) {
        if (null == activity) {
            return;
        }
        activity.overridePendingTransition(R.anim.no_anim, R.anim.abc_slide_out_top);
    }

    /**
     * 出去 从上往下动画
     *
     * @param activity 上下文对象
     */
    public static void top2BottomOut(Activity activity) {
        if (null == activity) {
            return;
        }
        activity.overridePendingTransition(R.anim.no_anim, R.anim.abc_slide_out_bottom);
    }

    /**
     * 出去 从上往下动画
     *
     * @param activity 上下文对象
     */
    public static void top2BottomIn(Activity activity) {
        if (null == activity) {
            return;
        }
        activity.overridePendingTransition(R.anim.abc_slide_in_top, R.anim.no_anim);
    }

    public static void right2LeftIn(Activity activity) {
        if (null == activity) {
            return;
        }
        activity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.no_anim);
    }

    public static void left2RightOut(Activity activity) {
        if (null == activity) {
            return;
        }
        activity.overridePendingTransition(R.anim.no_anim, R.anim.slide_out_to_right);
    }

    public static int getResource(Context context, String defType, String imageName) {
        return context.getResources().getIdentifier(imageName, defType, PackageUtil.getPackageName());
    }

    public static void oneKeyToTop(final boolean hasHead, final RecyclerView recyclerView, final View viewTitle) {
        if (null == viewTitle || null == recyclerView) {
            return;
        }
        viewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasHead) {
                    int[] location = new int[2];
                    recyclerView.getChildAt(0).getLocationOnScreen(location);
                    int titleHeight = viewTitle.getMeasuredHeight() + DensityUtils.getStatusHeight();
                    if (location[1] - titleHeight < 0) {
                        recyclerView.smoothScrollToPosition(0);
                    }
                } else {
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        });
    }

    public static void enableTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
