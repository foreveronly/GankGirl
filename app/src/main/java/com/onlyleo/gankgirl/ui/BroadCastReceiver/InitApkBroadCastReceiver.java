package com.onlyleo.gankgirl.ui.BroadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.onlyleo.gankgirl.utils.FileUtil;
import com.onlyleo.gankgirl.utils.GetAppInfo;

import java.io.File;

public class InitApkBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        File file = new File(GetAppInfo.getApkPath(), "GankGirl.apk");
        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
            FileUtil.deleteFile(file);
        }

        if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
            FileUtil.deleteFile(file);
        }


        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
            FileUtil.deleteFile(file);
        }
    }


}
