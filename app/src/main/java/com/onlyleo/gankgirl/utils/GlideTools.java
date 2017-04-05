package com.onlyleo.gankgirl.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class GlideTools {

    /**
     * 图片加载
     */
    public static void LoadImage(Context context, ImageView imageView, Object obj) {
        Glide.with(context).load(obj).diskCacheStrategy(DiskCacheStrategy.RESULT).crossFade(500).into(imageView);
    }


}
