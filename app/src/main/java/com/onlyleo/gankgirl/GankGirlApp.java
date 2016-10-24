package com.onlyleo.gankgirl;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class GankGirlApp extends Application {

    private static GankGirlApp INSTANCE = null;
//    private RefWatcher refWatcher;

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
            INSTANCE = this;
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

    }


    public static GankGirlApp getInstance() {
        return INSTANCE;
        }

//    public static RefWatcher getRefWatcher(Context context) {
//        GankGirlApp application = (GankGirlApp) context.getApplicationContext();
////        return application.refWatcher;
//    }

}
