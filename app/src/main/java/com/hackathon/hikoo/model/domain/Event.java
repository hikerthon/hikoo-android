package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._Event;

public class Event extends _Event implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.eventId);
        dest.writeInt(this.hikeId);
        dest.writeString(this.alertLevel);
        dest.writeString(this.reporter);
        dest.writeString(this.eventType);
        dest.writeInt(this.ttl);
        dest.writeLong(this.lng);
        dest.writeString(this.eventInfo);
        dest.writeString(this.eventTime);
        dest.writeInt(this.hikerId);
        dest.writeInt(this.alertId);
        dest.writeInt(this.radius);
        dest.writeLong(this.lat);
        dest.writeInt(this.status);

    }

    public Event() {}

    protected Event(Parcel in) {
        this.eventId = in.readInt();
        this.hikeId = in.readInt();
        this.alertLevel = in.readString();
        this.reporter = in.readString();
        this.eventType = in.readString();
        this.ttl = in.readInt();
        this.lng = in.readLong();
        this.eventInfo = in.readString();
        this.eventTime = in.readString();
        this.hikerId = in.readInt();
        this.alertId = in.readInt();
        this.radius = in.readInt();
        this.lat = in.readLong();
        this.status = in.readInt();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
