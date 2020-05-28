package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._Locations;

public class Locations extends _Locations implements Parcelable {


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.elevation);
        dest.writeLong(this.recordTime);
        dest.writeInt(this.hikeId);
        dest.writeInt(this.hikerId);
        dest.writeDouble(this.latpt);
        dest.writeDouble(this.lngpt);
        dest.writeInt(this.battery);
        dest.writeInt(this.network);
        dest.writeLong(this.elapsedTime);
    }

    public Locations() {
    }

    protected Locations(Parcel in) {
        this.elevation = in.readInt();
        this.recordTime = in.readLong();
        this.hikeId = in.readInt();
        this.hikerId = in.readInt();
        this.latpt = in.readDouble();
        this.lngpt = in.readDouble();
        this.battery = in.readInt();
        this.network = in.readInt();
        this.elapsedTime = in.readLong();
    }

    public static final Creator<Locations> CREATOR = new Creator<Locations>() {
        @Override
        public Locations createFromParcel(Parcel source) {
            return new Locations(source);
        }

        @Override
        public Locations[] newArray(int size) {
            return new Locations[size];
        }
    };
}
