package com.onlyleo.gankgirl.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by leoonly on 16/8/20.
 */

public class GankDBhelper extends SQLiteOpenHelper {

    private final static String TABLENAME = "gank"; // 表名
    private final static String TABLESQL = "create table if not exists " + TABLENAME +
            " ( used tinyint(1), " +
            "type varchar(10), " +
            "url varchar(100), " +
            "who varchar(20), " +
            "desc varchar(20), " +
            "updatedAt date, " +
            "createdAt date, " +
            "publishedAt date)";

    public GankDBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLESQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        sqLiteDatabase.execSQL("drop table if exists titles");
//        onCreate(sqLiteDatabase);
    }
}
