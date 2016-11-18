package com.onlyleo.gankgirl.utils;


import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;

import com.onlyleo.gankgirl.GankGirlApp;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Version;
import com.onlyleo.gankgirl.net.VersionRetrofit;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.AndPermission;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class CheckVersion {
    private Context context;
    private final static String appId = "575fb1e3e75e2d2bed000015";
    private final static String token = "4075cf1614935e8378de168581693fcf";
    private final static String FILE_PATH = Environment.getExternalStorageDirectory().getPath();

    private AlertDialog.Builder builder;
    private AlertDialog downloading;
    private AlertDialog updateDialog;
    private DownLoadThread downLoadThread;

    public CheckVersion(Context context) {
        this.context = context;
    }

    public void checkVersion(final boolean auto) {

        VersionRetrofit.getGankApi().getVersionInfo(appId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Version>() {
                    @Override
                    public void call(Version version) {
                        int versionCode = Integer.valueOf(version.version);
                        int appVersionCode = GetAppInfo.getAppVersionCode(GankGirlApp.getInstance().getApplicationContext());
                        if (appVersionCode != -1 && versionCode > appVersionCode) {
                            update(context, version);
                        } else {
                            if (!auto) {
                                ToastUtils.showShort(context, "已经是最新版本了！");
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e(throwable.toString());
                    }
                });
    }

    private void update(final Context context, final Version version) {
        downLoadThread = new DownLoadThread(version);
        builder = new AlertDialog.Builder(context);
        updateDialog = builder.create();
        downloading = builder.create();
        updateDialog.setTitle("发现新版本:");
        updateDialog.setMessage("版本号" + version.versionShort + "\n" + version.changelog);
        updateDialog.setCanceledOnTouchOutside(false);
        updateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "现在下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                AndPermission.with((Activity) context)
                        .requestCode(101)
                        .permission(Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .send();

                if (CommonTools.isWIFIConnected(context)) {
                    downLoadThread.start();
                } else {
                    downloading.setTitle("提示");
                    downloading.setMessage("您现在没有连接 WIFI , 是否继续下载？");
                    downloading.setCanceledOnTouchOutside(false);
                    downloading.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    downloading.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downLoadThread.start();
                        }
                    });
                    downloading.show();
                }

            }
        });
        updateDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        updateDialog.show();
    }


    class DownHandler extends Handler {
        private NotificationManager manager;
        private Notification DownloadingN;
        private Notification OpenN;
        private NotificationCompat.Builder nBuilder;

        @Override
        public void handleMessage(Message msg) {
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nBuilder = new NotificationCompat.Builder(context);

            switch (msg.what) {
                case 1:
                    File response = new File(msg.getData().getString("responsePath"));
                    if (!response.exists()) {
                        return;
                    } else {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setDataAndType(Uri.parse("file://" + response.toString()),
                                "application/vnd.android.package-archive");
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
                        OpenN = nBuilder.setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle("下载完成")
                                .setTicker("下载完成")
                                .setContentIntent(pendingIntent)
                                .setProgress(0, 0, false)
                                .setContentText("点击安装").build();
                        OpenN.flags = Notification.FLAG_AUTO_CANCEL;
                        manager.notify(1, OpenN);
                    }
                    break;
                case 2:
                    float progress = msg.getData().getFloat("progress");
                    float total = msg.getData().getFloat("total");
                    DownloadingN = nBuilder.setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("正在下载 GankGirl ...")
                            .setTicker("正在下载 GankGirl ...")
                            .setProgress((int) total, (int) (progress * total), false)
                            .setContentText((int) (progress * 100) + "%").build();
                    DownloadingN.flags = Notification.FLAG_ONGOING_EVENT;
                    manager.notify(1, DownloadingN);
                    break;
            }

        }
    }

    class DownLoadThread extends Thread {

        public DownLoadThread(Version version) {
            this.version = version;
        }

        private Version version;
        private DownHandler downHandler = new DownHandler();

        @Override
        public void run() {

            ToastUtils.showShort(context,Thread.currentThread().getName());
            OkHttpUtils.get().url(version.installUrl).build().execute(new FileCallBack(FILE_PATH, version.name + "_" + version.versionShort + ".apk") {
                @Override
                public void onError(Call call, Exception e, int id) {
                    ToastUtils.showShort(context, "下载失败");
                }

                @Override
                public void onResponse(File response, int id) {
                    Message message = new Message();
                    message.what = 1;
                    Bundle b = new Bundle();
                    b.putString("responsePath",response.getAbsolutePath());
                    message.setData(b);
                    downHandler.sendMessage(message);
                }

                @Override
                public void inProgress(float progress, long total, int id) {
                    Message message = new Message();
                    message.what = 2;
                    Bundle b = new Bundle();
                    b.putFloat("progress", progress);
                    b.putFloat("total", total);
                    message.setData(b);
                    downHandler.sendMessage(message);
                }
            });
        }
    }
}
