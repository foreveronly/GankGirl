package com.onlyleo.gankgirl.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GankRetrofit {
    /**
     * 数据主机地址
     */
    public static final String HOST = "http://gank.io/api/";
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();
    private static Retrofit retrofit;
    private static GankAPI mGankAPI;
    private static final Object monitor = new Object();

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    //单例获取API实例
    public static GankAPI getGuDongInstance() {
        synchronized (monitor) {
            if (mGankAPI == null) {
                mGankAPI = retrofit.create(GankAPI.class);
            }
            return mGankAPI;
        }
    }
}
