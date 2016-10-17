package com.onlyleo.gankgirl.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Content extends Soul implements Parcelable {
    public String content;
    public String publishedAt;
    public String title;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.publishedAt);
        dest.writeString(this.title);
    }


    protected Content(Parcel in) {
        this.content = in.readString();
        this.publishedAt = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Content> CREATOR = new Parcelable.Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel source) {
            return new Content(source);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };
}
