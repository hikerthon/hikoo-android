package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._CheckIn;

public class CheckIn extends _CheckIn implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.hikeId);
        dest.writeInt(this.hikerId);
    }

    public CheckIn() {
    }

    protected CheckIn(Parcel in) {
        this.hikeId = in.readInt();
        this.hikerId = in.readInt();
    }

    public static final Creator<CheckIn> CREATOR = new Creator<CheckIn>() {
        @Override
        public CheckIn createFromParcel(Parcel source) {
            return new CheckIn(source);
        }

        @Override
        public CheckIn[] newArray(int size) {
            return new CheckIn[size];
        }
    };
}
