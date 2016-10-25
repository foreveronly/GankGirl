package com.onlyleo.gankgirl.utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import com.onlyleo.gankgirl.GankGirlApp;
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

    private final static String appId = "575fb1e3e75e2d2bed000015";
    private final static String token = "4075cf1614935e8378de168581693fcf";
    private final static String FILE_PATH = Environment.getExternalStorageDirectory().getPath();

    public void checkVersion(final Context context, final boolean auto) {

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
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        AlertDialog updateDialog = builder.create();
        final AlertDialog downloading = builder.create();
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
                    OkHttpUtils.get().url(version.installUrl).build().execute(new FileCallBack(FILE_PATH, version.name + "_" + version.versionShort + ".apk") {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(File response, int id) {
                            if (!response.exists()) {
                                return;
                            } else {
                                Logger.d(FILE_PATH);
                                final Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setDataAndType(Uri.parse("file://" + response.toString()),
                                        "application/vnd.android.package-archive");
                                context.startActivity(i);
                            }


                        }

                        @Override
                        public void inProgress(float progress, long total, int id) {

                        }
                    });
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
                            OkHttpUtils.get().url(version.installUrl).build().execute(new FileCallBack(FILE_PATH, version.name + "_" + version.versionShort + ".apk") {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(File response, int id) {
                                    if (!response.exists()) {
                                        return;
                                    } else {
                                        Logger.d(FILE_PATH);
                                        final Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setDataAndType(Uri.parse("file://" + response.toString()),
                                                "application/vnd.android.package-archive");
                                        context.startActivity(i);
                                    }


                                }

                                @Override
                                public void inProgress(float progress, long total, int id) {

                                }
                            });
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


}
