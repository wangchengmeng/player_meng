package com.suyou.media.util.glid;

import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.maogu.htclibrary.util.StringUtil;
import com.suyou.media.app.HtcApplication;

import java.util.Locale;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class GlideUtil {

    public static void show(String url, ImageView imageView) {
        show(url, imageView, 0);
    }

    public static void show(String url, ImageView imageView, int resId) {
        show(url, imageView, resId, false, null);
    }

    public static void showCircle(String url, ImageView imageView, int resId) {
        show(url, imageView, resId, true, null);
    }

    public static void show(String url, ImageView imageView, int resId, boolean isCircle, RequestListener listener) {
        DrawableRequestBuilder requestBuilder = Glide.with(HtcApplication.getInstance())
                .load(getUrl(imageView, url))
                .skipMemoryCache(true);
        if (0 != resId) {
            requestBuilder = requestBuilder.error(resId)
                    .placeholder(resId);
        }
        if (isCircle) {
            requestBuilder = requestBuilder.bitmapTransform(new CropCircleTransformation(HtcApplication.getInstance()));
        }
        if (null != listener) {
            requestBuilder = requestBuilder.listener(listener);
        }
        requestBuilder.into(imageView);
    }

    public static void showBlur(String url, ImageView imageView, int radius) {
        DrawableRequestBuilder requestBuilder = Glide.with(HtcApplication.getInstance())
                .load(getUrl(imageView, url))
                .bitmapTransform(new BlurTransformation(HtcApplication.getInstance(), radius))
                .skipMemoryCache(true);
        requestBuilder.into(imageView);
    }

    public static void showNoPlace(String url, ImageView imageView, int resId) {
        Glide.with(HtcApplication.getInstance())
                .load(getUrl(imageView, url))
                .error(resId)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void showNoPlace(String url, ImageView imageView, int resId, RequestListener listener) {
        Glide.with(HtcApplication.getInstance())
                .load(getUrl(imageView, url))
                .listener(listener)
                .error(resId)
                .skipMemoryCache(true)
                .into(imageView);
    }

    private static String getUrl(ImageView imageView, String url) {
        if (null == imageView || StringUtil.isNullOrEmpty(url) || !url.startsWith("http")) {
            return url;
        }
        int width = imageView.getWidth();
        int height = imageView.getHeight();
        if (0 == width || 0 == height) {
            return url;
        }
        return String.format(Locale.getDefault(), "%s?imageView2/0/w/%s/h/%s", url, width, height);
    }

    /**
     * 暂停所有的请求
     */
    public static void pause() {
        //        Glide.with(HtcApplication.getInstance())
        //                .pauseRequestsRecursive();
    }

    /**
     * 恢复所有的请求
     */
    public static void resume() {
        //        Glide.with(HtcApplication.getInstance())
        //                .resumeRequestsRecursive();
    }
}
