package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._Shelter;

public class Shelter extends _Shelter implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeLong(this.latpt);
        dest.writeLong(this.lngpt);
        dest.writeInt(this.capacity);
    }

    public Shelter() { }

    protected Shelter(Parcel in) {

    }

    public static final Creator<Shelter> CREATOR = new Creator<Shelter>() {
        @Override
        public Shelter createFromParcel(Parcel source) {
            return new Shelter(source);
        }

        @Override
        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };
}
