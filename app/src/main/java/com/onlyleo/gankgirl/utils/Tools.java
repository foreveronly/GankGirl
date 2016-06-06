package com.onlyleo.gankgirl.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onlyleo.gankgirl.model.entity.Girl;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by leoonly on 16/5/26.
 */
public class Tools {
    private static final String GANK = "gank";
    private static final String GIRLS = "girls";
    private static final String IS_FIRST_OPEN = "is_first_open";
    private static Gson gson = new Gson();

    public static boolean saveFirstPageGirls(Context context, List<Girl> girls) {
        SharedPreferences preferences = context.getSharedPreferences(GANK, Context.MODE_PRIVATE);
        return preferences.edit().putString(GIRLS, gson.toJson(girls)).commit();
    }

    public static List<Girl> getFirstPageGirls(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(GANK, Context.MODE_PRIVATE);
        return gson.fromJson(preferences.getString(GIRLS, ""), new TypeToken<List<Girl>>() {}.getType());
    }
}
