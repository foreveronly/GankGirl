package com.onlyleo.gankgirl.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import com.onlyleo.gankgirl.model.entity.Gank;


/**
 * Created by panl on 15/12/25.
 */
public class StringStyleUtil {
    private StringStyleUtil(){

    }
    public static SpannableString getGankStyleStr(Gank gank){
        String gankStr = gank.desc + " by " + gank.who;
        SpannableString spannableString = new SpannableString(gankStr);
        spannableString.setSpan(new RelativeSizeSpan(0.8f),gank.desc.length()+1,gankStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.GRAY),gank.desc.length()+1,gankStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
