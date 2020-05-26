package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._Permit;

public class Permit extends _Permit implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.trailList);
        dest.writeInt(this.permitId);
        dest.writeString(this.permitName);
        dest.writeParcelable(this.hik, flags);
    }

    public Permit(){}

    protected Permit(Parcel in) {
        this.trailList = in.createStringArrayList();
        this.permitId = in.readInt();
        this.permitName = in.readString();
        this.hik = in.readParcelable(Hik.class.getClassLoader());
    }

    public static final Creator<Permit> CREATOR = new Creator<Permit>() {
        @Override
        public Permit createFromParcel(Parcel source) {
            return new Permit(source);
        }

        @Override
        public Permit[] newArray(int size) {
            return new Permit[size];
        }
    };
}
