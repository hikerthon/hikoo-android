package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._Alert;

public class Alert extends _Alert implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.alertLevelId);
        dest.writeInt(this.permitId);
        dest.writeInt(this.creatorId);
        dest.writeInt(this.originEventId);
        dest.writeLong(this.lngpt);
        dest.writeInt(this.eventTypeId);
        dest.writeString(this.eventInfo);
        dest.writeString(this.eventEnd);
        dest.writeString(this.eventTime);
        dest.writeInt(this.id);
        dest.writeLong(this.latpt);
        dest.writeInt(this.radius);
        dest.writeString(this.logtime);
    }

    public Alert(){}

    protected Alert(Parcel in) {
        this.alertLevelId = in.readInt();
        this.permitId = in.readInt();
        this.creatorId = in.readInt();
        this.originEventId = in.readInt();
        this.lngpt = in.readLong();
        this.eventTypeId = in.readInt();
        this.eventInfo = in.readString();
        this.eventEnd = in.readString();
        this.eventTime = in.readString();
        this.id = in.readInt();
        this.latpt = in.readLong();
        this.radius = in.readInt();
        this.logtime = in.readString();

    }

    public static final Creator<Alert> CREATOR = new Creator<Alert>() {
        @Override
        public Alert createFromParcel(Parcel source) {
            return new Alert(source);
        }

        @Override
        public Alert[] newArray(int size) {
            return new Alert[size];
        }
    };
}
