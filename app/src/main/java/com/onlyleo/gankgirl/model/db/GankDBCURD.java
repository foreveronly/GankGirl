package com.onlyleo.gankgirl.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.onlyleo.gankgirl.model.entity.Gank;
import com.onlyleo.gankgirl.utils.CommonTools;


public class GankDBCURD {
    private GankDBhelper gankDBhelper;
    private final static String TABLENAME = "gank"; // 表名

    private final static String NULLCOLUMNHACK = "ganknull"; // 表名

    public GankDBCURD(Context context) {
        gankDBhelper = new GankDBhelper(context, "ganks.db", null, 1);
    }

    public void addGank(Gank gank) {
        SQLiteDatabase db = gankDBhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("used", gank.used);
        contentValues.put("type", gank.type);
        contentValues.put("url", gank.url);
        contentValues.put("who", gank.who);
        contentValues.put("desc", gank.desc);
        contentValues.put("updatedAt", CommonTools.toDateTimeStr(gank.updatedAt));
        contentValues.put("createdAt", CommonTools.toDateTimeStr(gank.createdAt));
        contentValues.put("publishedAt", CommonTools.toDateTimeStr(gank.publishedAt));
        db.insert(TABLENAME,NULLCOLUMNHACK,contentValues);
//        db.execSQL("insert into gank(used,type,url,who,desc,updatedAt,createdAt,publishedAt)values(?,?,?,?,?,?,?,?)",
//                new Object[]{gank.used, gank.type, gank.url, gank.who, gank.desc, gank.updatedAt, gank.createdAt, gank.publishedAt
//                });
        db.close();
    }
}
