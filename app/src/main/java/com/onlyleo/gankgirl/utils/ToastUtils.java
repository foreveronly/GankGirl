package com.onlyleo.gankgirl.utils;

import android.content.Context;
import android.widget.Toast;

import com.onlyleo.gankgirl.GankGirlApp;

/**
 * Created by BBC on 2016/5/26 0026.
 */
public class ToastUtils {
    private static Toast toast;

    /**
     * 短时间显示  Toast
     *
     * @param
     * @param sequence
     */
    public static void showShort(CharSequence sequence) {

        if (toast == null) {
            toast = Toast.makeText(GankGirlApp.getInstance().getApplicationContext(), sequence, Toast.LENGTH_SHORT);
        } else {
            toast.setText(sequence);
        }
        toast.show();

    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (null == toast) {
            toast = Toast.makeText(GankGirlApp.getInstance().getApplicationContext(), message, Toast.LENGTH_SHORT);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(GankGirlApp.getInstance().getApplicationContext(), message, Toast.LENGTH_LONG);
            // toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (null == toast) {
            toast = Toast.makeText(GankGirlApp.getInstance().getApplicationContext(), message, Toast.LENGTH_LONG);
            //    toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 自定义显示时间
     *
     * @param context
     * @param sequence
     * @param duration
     */
    public static void show(Context context, CharSequence sequence, int duration) {
        if (toast == null) {
            toast = Toast.makeText(GankGirlApp.getInstance().getApplicationContext(), sequence, duration);
        } else {
            toast.setText(sequence);
        }
        toast.show();

    }

    /**
     * 隐藏toast
     */
    public static void hideToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
