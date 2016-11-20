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
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;

import com.onlyleo.gankgirl.GankGirlApp;
import com.onlyleo.gankgirl.R;
import com.onlyleo.gankgirl.model.entity.Version;
import com.onlyleo.gankgirl.net.VersionRetrofit;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class CheckVersion {

    private static CheckVersion instance = null;
    private Context context;
    private final static String appId = "575fb1e3e75e2d2bed000015";
    private final static String token = "4075cf1614935e8378de168581693fcf";
    private NotificationManager manager;
    private Notification downloadingN;
    private Notification openN;
    private NotificationCompat.Builder nBuilder;
    private AlertDialog downloading;
    private Version version = null;

    public static CheckVersion getInstance(Context c) {
        if (instance == null)
            instance = new CheckVersion(c);
        return instance;
    }

    private CheckVersion(Context context) {
        this.context = context;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public void checkVersion(final boolean auto) {
        VersionRetrofit.getGankApi().getVersionInfo(appId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Version>() {
                    @Override
                    public void call(Version version) {
                        setVersion(version);
                        Logger.d(GetAppInfo.getApkPath());
                        int versionCode = Integer.valueOf(version.version);
                        int appVersionCode = GetAppInfo.getAppVersionCode(GankGirlApp.getInstance().getApplicationContext());
                        if (versionCode > appVersionCode) {
                            File file = new File(GetAppInfo.getApkPath(), "GankGirl.apk");
                            if (file.exists()) {
                                installApk(file);
                            } else
                                update();
                        } else {
                            if (!auto) {
                                ToastUtils.showShort("已经是最新版本了！");
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

    private void installApk(final File file) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog updateDialog = builder.create();
        downloading = builder.create();
        updateDialog.setTitle("新版本已下载");
        updateDialog.setMessage("是否现在安装");
        updateDialog.setCanceledOnTouchOutside(false);
        updateDialog.setButton(DialogInterface.BUTTON_POSITIVE, "是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse("file://" + file.toString()),
                        "application/vnd.android.package-archive");
                context.startActivity(i);
            }
        });
        updateDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        updateDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "重新下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downNewVersion();
            }
        });
        updateDialog.show();
    }

    private void update() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog updateDialog = builder.create();
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
                    downNewVersion();
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
                            downNewVersion();
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

    private void downNewVersion() {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nBuilder = new NotificationCompat.Builder(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            nBuilder.setSmallIcon(R.mipmap.logo);
        } else {
            nBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }
        final ProgressListener p = new ProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                nBuilder.setContentTitle("正在下载 GankGirl ...")
                        .setTicker("正在下载 GankGirl ...")
                        .setProgress((int) contentLength, (int) bytesRead, false)
                        .setContentText((int) ((float) bytesRead / contentLength * 100) + "%");
                downloadingN = nBuilder.build();
                downloadingN.flags = Notification.FLAG_ONGOING_EVENT;
                manager.notify(1, downloadingN);
            }
        };
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new ProgressListener.ProgressResponseBody(originalResponse.body(), p))
                        .build();
            }
        }).build();

        Request request = new Request.Builder().url(version.installUrl).build();


        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Notification failed = nBuilder.setContentTitle("下载失败").build();
                failed.flags = Notification.FLAG_AUTO_CANCEL;
                manager.notify(1, failed);
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File dir = new File(GetAppInfo.getApkPath());
                    if (!dir.isDirectory() || !dir.exists())
                        dir.mkdir();
                    File file = new File(dir, "GankGirl.apk");
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setDataAndType(Uri.parse("file://" + file.toString()),
                            "application/vnd.android.package-archive");
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
                    openN = nBuilder.setContentTitle("下载完成")
                            .setTicker("下载完成")
                            .setContentIntent(pendingIntent)
                            .setProgress(0, 0, false)
                            .setContentText("点击安装").build();
                    openN.flags = Notification.FLAG_AUTO_CANCEL;
                    manager.notify(1, openN);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        response.body().close();
                        if (is != null) is.close();
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }
}
