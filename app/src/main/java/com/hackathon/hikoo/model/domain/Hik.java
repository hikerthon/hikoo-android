package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._Hik;

public class Hik extends _Hik implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.permitAccepted ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.trailList);
        dest.writeInt(this.permitId);
        dest.writeString(this.permitName);
        dest.writeInt(this.hikeId);
        dest.writeString(this.hikeEndTime);
        dest.writeString(this.hikerName);
        dest.writeInt(this.hikerAge);
        dest.writeByte(this.hikeFinished ? (byte) 1 : (byte) 0);
        dest.writeString(this.hikerGender);
        dest.writeString(this.hikerNationality);
        dest.writeInt(this.hikerId);
        dest.writeByte(this.hikeStarted ? (byte) 1 : (byte) 0);
        dest.writeString(this.hikeStartTime);
    }

    public Hik() {}

    protected Hik(Parcel in) {
        this.permitAccepted = in.readByte() != 0;
        this.trailList = in.createStringArrayList();
        this.permitId = in.readInt();
        this.permitName = in.readString();
        this.hikeId = in.readInt();
        this.hikeEndTime = in.readString();
        this.hikerName = in.readString();
        this.hikerAge = in.readInt();
        this.hikeFinished = in.readByte() != 0;
        this.hikerGender = in.readString();
        this.hikerNationality = in.readString();
        this.hikerId = in.readInt();
        this.hikeStarted = in.readByte() != 0;
        this.hikeStartTime = in.readString();
    }

    public static final Creator<Hik> CREATOR = new Creator<Hik>() {
        @Override
        public Hik createFromParcel(Parcel source) {
            return new Hik(source);
        }

        @Override
        public Hik[] newArray(int size) {
            return new Hik[size];
        }
    };
}
