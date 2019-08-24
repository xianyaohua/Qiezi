package com.tsrj.qiezi.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.tsrj.qiezi.R;

/**
 * Created by dab on 17/9/11.
 */

@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int memoryCacheSizeBytes = 1024 * 1024 * 20; // 20mb
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));

        int diskCacheSizeBytes = 1024 * 1024 * 200; // 200 MB
        String CACHE_FILE_NAME = "cacheFolderName";
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,CACHE_FILE_NAME, diskCacheSizeBytes));

        builder.setDefaultRequestOptions(
                new RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565)
                        .centerCrop()
                        .placeholder(R.color.window_bg)
                        .disallowHardwareConfig());
    }
}
