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

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    //单例获取API实例
    public static GankAPI getGankApi() {
        return GankAPIHolder.mGankAPI;
    }

    //内部类，在装载该内部类时才会去创建单利对象
    private static class GankAPIHolder {
        private static GankAPI mGankAPI = retrofit.create(GankAPI.class);
    }

}
