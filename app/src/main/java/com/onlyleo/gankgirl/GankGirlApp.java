package com.onlyleo.gankgirl;

import android.app.Application;

import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by bbc on 16/1/25.
 */
public class GankGirlApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            Logger
                    .init(getResources().getString(R.string.app_name))                 // default PRETTYLOGGER or use just init()
                    .methodCount(3)                 // default 2
                    .hideThreadInfo()               // default shown
                    .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                    .methodOffset(2)                // default 0
                    .logTool(new AndroidLogTool()); // custom log tool, optional
        }

    }
}
