package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._Alert;

public class Alert extends _Alert implements Parcelable {

    public enum AlertLevel {
        INFORMATION(1),
        CAUTION(2),
        DANGER(3);

        private int rawValue;

        AlertLevel(int rawValue) {
            this.rawValue = rawValue;
        }

        public int getRawValue() {
            return rawValue;
        }
    }

    public AlertLevel getAlertLevelType() {
        switch (alertLevelId) {
            case 1:
                return AlertLevel.INFORMATION;
            case 2:
                return AlertLevel.CAUTION;
            case 3:
                return AlertLevel.DANGER;
            default:
                return AlertLevel.INFORMATION;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.attachments);
        dest.writeInt(this.alertLevelId);
        dest.writeInt(this.permitId);
        dest.writeString(this.permitName);
        dest.writeInt(this.creatorId);
        dest.writeString(this.creatorName);
        dest.writeDouble(this.lngpt);
        dest.writeString(this.alertLevelName);
        dest.writeInt(this.eventTypeId);
        dest.writeString(this.eventInfo);
        dest.writeString(this.eventEnd);
        dest.writeLong(this.eventTime);
        dest.writeString(this.eventTypeName);
        dest.writeInt(this.id);
        dest.writeDouble(this.latpt);
        dest.writeInt(this.radius);
    }

    public Alert() {
    }

    protected Alert(Parcel in) {
        this.attachments = in.createStringArrayList();
        this.alertLevelId = in.readInt();
        this.permitId = in.readInt();
        this.permitName = in.readString();
        this.creatorId = in.readInt();
        this.creatorName = in.readString();
        this.lngpt = in.readDouble();
        this.alertLevelName = in.readString();
        this.eventTypeId = in.readInt();
        this.eventInfo = in.readString();
        this.eventEnd = in.readString();
        this.eventTime = in.readLong();
        this.eventTypeName = in.readString();
        this.id = in.readInt();
        this.latpt = in.readDouble();
        this.radius = in.readInt();
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
