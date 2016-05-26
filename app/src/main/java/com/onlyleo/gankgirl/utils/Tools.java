package com.onlyleo.gankgirl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by leoonly on 16/5/26.
 */
public class Tools {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    public static String todate(String date) throws ParseException {
        Date d = sdf.parse(date);
        return sdf.format(d);
    }
}
