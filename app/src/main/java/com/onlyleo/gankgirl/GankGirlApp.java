package com.onlyleo.gankgirl;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class GankGirlApp extends Application {


    private RefWatcher refWatcher;

    public GankGirlApp() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Logger
                    .init(getResources().getString(R.string.app_name))                 // default PRETTYLOGGER or use just init()
                    .methodCount(3)                 // default 2
                    .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                    .methodOffset(0);        // default 0
            refWatcher = LeakCanary.install(this);
        }

    }

    private static class GankGirlAppHolder {
        private static GankGirlApp instance = new GankGirlApp();
    }

    public static GankGirlApp getInstance() {
        return GankGirlAppHolder.instance;
    }

    public static RefWatcher getRefWatcher(Context context) {
        GankGirlApp application = (GankGirlApp) context.getApplicationContext();
        return application.refWatcher;
    }

}
