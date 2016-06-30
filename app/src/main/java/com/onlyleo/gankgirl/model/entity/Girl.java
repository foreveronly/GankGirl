
package com.onlyleo.gankgirl.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Girl extends Soul implements Parcelable {
    //每个妹子图片的信息
    public boolean used;
    public String type;
    public String url;
    public String who;
    public String desc;
    public Date createdAt;
    public Date publishedAt;
    public Date updatedAt;

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
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeLong(this.publishedAt != null ? this.publishedAt.getTime() : -1);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
    }

    public Girl() {
    }

    protected Girl(Parcel in) {
        this.used = in.readByte() != 0;
        this.type = in.readString();
        this.url = in.readString();
        this.who = in.readString();
        this.desc = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpPublishedAt = in.readLong();
        this.publishedAt = tmpPublishedAt == -1 ? null : new Date(tmpPublishedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
    }

    public static final Parcelable.Creator<Girl> CREATOR = new Parcelable.Creator<Girl>() {
        @Override
        public Girl createFromParcel(Parcel source) {
            return new Girl(source);
        }

        @Override
        public Girl[] newArray(int size) {
            return new Girl[size];
        }
    };
}
