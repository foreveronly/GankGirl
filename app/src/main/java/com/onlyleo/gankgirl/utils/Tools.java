package com.onlyleo.gankgirl.utils;

import java.text.SimpleDateFormat;

/**
 * Created by leoonly on 16/5/26.
 */
public class Tools {
    static SimpleDateFormat sdf;
    public static String toDate(long date) {
        sdf = new SimpleDateFormat("yyyy/MM/dd");
        String d = sdf.format(date);
        return d;
    }
}
