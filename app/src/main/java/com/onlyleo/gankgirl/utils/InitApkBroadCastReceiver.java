package com.onlyleo.gankgirl.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.File;

public class InitApkBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        File file = new File(GetAppInfo.getApkPath(), "GankGirl.apk");
        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }

        if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }

        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {

            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }
    }
}
