package com.suyou.media.util;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

public class ToastUtil {
    public static void show(View view, CharSequence msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void show(View view, int resId) {
        show(view, view.getContext().getResources().getString(resId));
    }
}
