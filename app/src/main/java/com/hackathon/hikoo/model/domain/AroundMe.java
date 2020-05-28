package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._AroundMe;


public class AroundMe extends _AroundMe implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ptinfo);
        dest.writeString(this.eventInfo);
        dest.writeDouble(this.distanceMeter);
        dest.writeDouble(this.latpt);
        dest.writeDouble(this.lngpt);
        dest.writeString(this.eventTypeName);
        dest.writeLong(this.logtime);
        dest.writeString(this.alertName);
    }

    public AroundMe() {
    }

    protected AroundMe(Parcel in) {
        this.ptinfo = in.readString();
        this.eventInfo = in.readString();
        this.distanceMeter = in.readDouble();
        this.latpt = in.readDouble();
        this.lngpt = in.readDouble();
        this.eventTypeName = in.readString();
        this.logtime = in.readLong();
        this.alertName = in.readString();
    }

    public static final Creator<AroundMe> CREATOR = new Creator<AroundMe>() {
        @Override
        public AroundMe createFromParcel(Parcel source) {
            return new AroundMe(source);
        }

        @Override
        public AroundMe[] newArray(int size) {
            return new AroundMe[size];
        }
    };
}
