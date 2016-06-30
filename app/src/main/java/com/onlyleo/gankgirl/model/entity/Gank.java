
package com.onlyleo.gankgirl.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Gank extends Soul implements Parcelable {

    //每个干货的链接
    public boolean used;
    public String type;
    public String url;
    public String who;
    public String desc;
    public Date updatedAt;
    public Date createdAt;
    public Date publishedAt;

    @Override
    public String toString() {
        return "Gank{" +
                "used=" + used +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", who='" + who + '\'' +
                ", desc='" + desc + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", publishedAt=" + publishedAt +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.used ? (byte) 1 : (byte) 0);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeString(this.who);
        dest.writeString(this.desc);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeLong(this.publishedAt != null ? this.publishedAt.getTime() : -1);
    }

    public Gank() {
    }

    protected Gank(Parcel in) {
        this.used = in.readByte() != 0;
        this.type = in.readString();
        this.url = in.readString();
        this.who = in.readString();
        this.desc = in.readString();
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpPublishedAt = in.readLong();
        this.publishedAt = tmpPublishedAt == -1 ? null : new Date(tmpPublishedAt);
    }

    public static final Parcelable.Creator<Gank> CREATOR = new Parcelable.Creator<Gank>() {
        @Override
        public Gank createFromParcel(Parcel source) {
            return new Gank(source);
        }

        @Override
        public Gank[] newArray(int size) {
            return new Gank[size];
        }
    };
}
