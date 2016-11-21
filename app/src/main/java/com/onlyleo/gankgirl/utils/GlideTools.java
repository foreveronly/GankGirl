package com.onlyleo.gankgirl.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onlyleo.gankgirl.GankGirlApp;

/**
 * Created by only1 on 2016/11/21 0021.
 */

public class GlideTools {

    /**
     * 封装图片加载库
     */

    public static void ImageLoade(ImageView imageView, String url) {

        Glide.with(GankGirlApp.getInstance().getApplicationContext()).load(url).diskCacheStrategy(DiskCacheStrategy.RESULT).centerCrop().crossFade().into(imageView);
    }

}
