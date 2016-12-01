package com.onlyleo.gankgirl.model.entity;

import android.os.Parcel;

import java.util.Date;


public class Category extends Gank {

    public boolean used;
    public Date updatedAt;
    public Date createdAt;
    public Date publishedAt;
    protected Category(Parcel in) {
        super(in);
    }
}
