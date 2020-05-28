package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._TrailsItem;

public class TrailsItem extends _TrailsItem implements Parcelable {


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.permit);
        dest.writeInt(this.id);
    }

    public TrailsItem() {
    }

    protected TrailsItem(Parcel in) {
        this.name = in.readString();
        this.permit = in.readInt();
        this.id = in.readInt();
    }

    public static final Creator<TrailsItem> CREATOR = new Creator<TrailsItem>() {
        @Override
        public TrailsItem createFromParcel(Parcel source) {
            return new TrailsItem(source);
        }

        @Override
        public TrailsItem[] newArray(int size) {
            return new TrailsItem[size];
        }
    };
}
