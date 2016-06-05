package com.onlyleo.gankgirl;

import android.app.Activity;
import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by bbc on 16/1/25.
 */
public class GankGirlApp extends Application {
    private static GankGirlApp instance = null;
    private List<Activity> activityList = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Logger
                    .init(getResources().getString(R.string.app_name))                 // default PRETTYLOGGER or use just init()
                    .methodCount(3)                 // default 2
                    .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                    .methodOffset(2);        // default 0
        }

    }

    public static GankGirlApp getInstance() {
        if (instance == null) {
            instance = new GankGirlApp();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
