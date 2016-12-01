
package com.onlyleo.gankgirl.model.entity;

import android.os.Parcel;
import android.os.Parcelable;


public class Gank extends Soul implements Parcelable{

    //每个干货的链接

    public String desc;
    public String type;
    public String url;
    public String who;

    protected Gank(Parcel in) {
        desc = in.readString();
        type = in.readString();
        url = in.readString();
        who = in.readString();
    }

    public static final Creator<Gank> CREATOR = new Creator<Gank>() {
        @Override
        public Gank createFromParcel(Parcel in) {
            return new Gank(in);
        }

        @Override
        public Gank[] newArray(int size) {
            return new Gank[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(desc);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(who);
    }
}
