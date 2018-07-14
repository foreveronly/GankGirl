package com.onlyleo.gankgirl;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
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
            Logger.addLogAdapter(new AndroidLogAdapter());
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
