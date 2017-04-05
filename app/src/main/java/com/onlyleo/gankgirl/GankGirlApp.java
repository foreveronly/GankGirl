package com.onlyleo.gankgirl;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

public class GankGirlApp extends Application {

    private static GankGirlApp INSTANCE = null;
//    private RefWatcher refWatcher;

    public static GankGirlApp getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(GankGirlApp INSTANCE) {
        GankGirlApp.INSTANCE = INSTANCE;
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
//            refWatcher = LeakCanary.install(this);
            }
        setINSTANCE(this);
    }

    public static GankGirlApp getInstance() {
        return getINSTANCE();
        }

//    public static RefWatcher getRefWatcher(Context context) {
//        GankGirlApp application = (GankGirlApp) context.getApplicationContext();
////        return application.refWatcher;
//    }

}
