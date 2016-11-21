package com.onlyleo.gankgirl.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.Toast;

import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Gank;

import java.util.Calendar;
import java.util.Date;

public class CommonTools {

    /**
     * drawble转bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitamp(Drawable drawable) {

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 拼接String
     *
     * @param gank
     * @return
     */
    public static SpannableString getGankStyleStr(Gank gank) {
        String gankStr = gank.desc + " by " + gank.who;
        SpannableString spannableString = new SpannableString(gankStr);
        spannableString.setSpan(new RelativeSizeSpan(0.8f), gank.desc.length() + 1, gankStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), gank.desc.length() + 1, gankStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 复制到剪切板
     *
     * @param context
     * @param text
     * @param success
     */
    public static void copyToClipBoard(Context context, String text, String success) {
        ClipData clipData = ClipData.newPlainText("girl_copy", text);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(
                Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        Toast.makeText(context, success, Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return false
     */
    public static boolean isWIFIConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info != null && info.isConnected();
    }

    /**
     * 日期做标题
     *
     * @param date
     * @return
     */
    public static StringBuilder toDateString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new StringBuilder(year + "/" + month + "/" + day);
    }

    /**
     * 日期做标题2
     *
     * @param date
     * @return
     */
    public static String toDateTimeStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "年" + month + "月" + day + "日";
    }


    /**
     * 比较日期
     *
     * @param one
     * @param another
     * @return
     */
    public static boolean isTheSameDay(Date one, Date another) {
        Calendar _one = Calendar.getInstance();
        _one.setTime(one);
        Calendar _another = Calendar.getInstance();
        _another.setTime(another);
        int oneDay = _one.get(Calendar.DAY_OF_YEAR);
        int anotherDay = _another.get(Calendar.DAY_OF_YEAR);
        return oneDay == anotherDay;
    }
    //================分享

    /**
     * 分享图片
     *
     * @param context
     * @param uri
     * @param title
     */
    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(shareIntent, title));
    }

    /**
     * 分享app
     *
     * @param context
     */
    public static void shareApp(Context context) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.share_app));
        shareIntent.setType("text/plain");
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_app_to_friend)));
    }

    /**
     * 分享干货
     *
     * @param context
     * @param gank
     */
    public static void shareGank(Context context, Gank gank) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, gank.desc + gank.url + context.getString(R.string.download_gank));
        intent.setType("text/plain");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_gank_to)));
    }

    /**
     * 分享干货
     *
     * @param context
     * @param url
     */
    public static void shareGankDaily(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "这是今天的干活" + url);
        intent.setType("text/plain");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_gank_to)));
    }

    /**
     * 分享视频
     *
     * @param context
     * @param gank
     */
    public static void shareVideo(Context context, Gank gank) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, gank.desc + gank.url + context.getString(R.string.download_gank));
        intent.setType("text/plain");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_video_to)));
    }


}
