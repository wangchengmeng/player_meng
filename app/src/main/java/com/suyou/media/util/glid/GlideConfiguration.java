package com.suyou.media.util.glid;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.suyou.media.util.StorageUtils;

public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int cacheSize100MegaBytes = 104857600;//100Mb
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888)
                .setDiskCache(
                        new DiskLruCacheFactory(StorageUtils.getCacheDirectory(context, "image").getAbsolutePath(), cacheSize100MegaBytes)
                );
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
