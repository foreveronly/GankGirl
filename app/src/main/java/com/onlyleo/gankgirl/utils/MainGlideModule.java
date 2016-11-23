package com.onlyleo.gankgirl.utils;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

import java.io.File;


public class MainGlideModule implements GlideModule {

    private int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
    private int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
    private int diskCacheSize = 1024 * 1024 * 30;//最多可以缓存多少字节的数据
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        File cacheDir = context.getExternalCacheDir();//指定的是数据的缓存地址
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));//设置磁盘缓存大小
        builder.setDiskCache(new DiskLruCacheFactory(cacheDir.getPath(), "glide", diskCacheSize));//设置内存缓存大小
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
