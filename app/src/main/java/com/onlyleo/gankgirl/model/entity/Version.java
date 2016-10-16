package com.onlyleo.gankgirl.model.entity;


import android.os.Parcel;
import android.os.Parcelable;

public class Version extends Soul implements Parcelable {


    public String name;
    public String version;
    public String changelog;
    public int updated_at;
    public String versionShort;
    public String build;
    public String installUrl;
    public String install_url;
    public String direct_install_url;
    public String update_url;
    public int fsize;

    protected Version(Parcel in) {
        name = in.readString();
        version = in.readString();
        changelog = in.readString();
        updated_at = in.readInt();
        versionShort = in.readString();
        build = in.readString();
        installUrl = in.readString();
        install_url = in.readString();
        direct_install_url = in.readString();
        update_url = in.readString();
        fsize = in.readInt();
    }

    public static final Creator<Version> CREATOR = new Creator<Version>() {
        @Override
        public Version createFromParcel(Parcel in) {
            return new Version(in);
        }

        @Override
        public Version[] newArray(int size) {
            return new Version[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(version);
        dest.writeString(changelog);
        dest.writeInt(updated_at);
        dest.writeString(versionShort);
        dest.writeString(build);
        dest.writeString(installUrl);
        dest.writeString(install_url);
        dest.writeString(direct_install_url);
        dest.writeString(update_url);
        dest.writeInt(fsize);
    }
}