package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._LoginInfo;

public class LoginInfo extends _LoginInfo implements Parcelable {


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accessToken);
        dest.writeString(this.refreshToken);
    }

    public LoginInfo() {
    }

    protected LoginInfo(Parcel in) {
        this.accessToken = in.readString();
        this.refreshToken = in.readString();
    }

    public static final Creator<LoginInfo> CREATOR = new Creator<LoginInfo>() {
        @Override
        public LoginInfo createFromParcel(Parcel source) {
            return new LoginInfo(source);
        }

        @Override
        public LoginInfo[] newArray(int size) {
            return new LoginInfo[size];
        }
    };
}
