package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._Event;

public class Event extends _Event implements Parcelable {

    public enum EventType {
        WILD_ANIMAL(1),
        ITEM_FOUND(2),
        BLOCKED_ROUTE(3),
        SOS(4);

        private int rawValue;

        EventType(int rawValue) {
            this.rawValue = rawValue;
        }

        public int getRawValue() {
            return rawValue;
        }
    }

    public EventType getEventType() {
        switch (eventTypeId) {
            case 1:
                return EventType.WILD_ANIMAL;
            case 2:
                return EventType.ITEM_FOUND;
            case 3:
                return EventType.BLOCKED_ROUTE;
            case 4:
                return EventType.SOS;
            default:
                return EventType.SOS;
        }
    }

    public enum EventAlertLevel {
        INFORMATION(1),
        CAUTION(2),
        DANGER(3);

        private int rawValue;

        EventAlertLevel(int rawValue) {
            this.rawValue = rawValue;
        }

        public int getRawValue() {
            return rawValue;
        }
    }

    public EventAlertLevel getAlertLevelType() {
        switch (alertLevelId) {
            case 1:
                return EventAlertLevel.INFORMATION;
            case 2:
                return EventAlertLevel.CAUTION;
            case 3:
                return EventAlertLevel.DANGER;
            default:
                return EventAlertLevel.INFORMATION;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stat);
        dest.writeStringList(this.attachments);
        dest.writeInt(this.alertLevelId);
        dest.writeInt(this.hikeId);
        dest.writeDouble(this.lngpt);
        dest.writeString(this.reporterName);
        dest.writeInt(this.eventTypeId);
        dest.writeString(this.eventInfo);
        dest.writeLong(this.eventTime);
        dest.writeString(this.eventTypeName);
        dest.writeInt(this.reporterId);
        dest.writeInt(this.id);
        dest.writeDouble(this.latpt);
        dest.writeInt(this.radius);
        dest.writeLong(this.logtime);
    }

    public Event() {
    }

    protected Event(Parcel in) {
        this.stat = in.readString();
        this.attachments = in.createStringArrayList();
        this.alertLevelId = in.readInt();
        this.hikeId = in.readInt();
        this.lngpt = in.readDouble();
        this.reporterName = in.readString();
        this.eventTypeId = in.readInt();
        this.eventInfo = in.readString();
        this.eventTime = in.readLong();
        this.eventTypeName = in.readString();
        this.reporterId = in.readInt();
        this.id = in.readInt();
        this.latpt = in.readDouble();
        this.radius = in.readInt();
        this.logtime = in.readLong();
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
