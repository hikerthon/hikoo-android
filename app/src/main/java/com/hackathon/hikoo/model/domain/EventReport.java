package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hackathon.hikoo.model.entity._EventReport;

import java.util.logging.Logger;

public class EventReport extends _EventReport implements Parcelable {

    public boolean isValidate() {
        return !TextUtils.isEmpty(this.eventInfo) && this.eventTypeId != 0 && this.alertLevelId != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.eventTypeId);
        dest.writeString(this.eventInfo);
        dest.writeString(this.stat);
        dest.writeInt(this.alertLevelId);
        dest.writeInt(this.hikeId);
        dest.writeLong(this.eventTime);
        dest.writeInt(this.reporterId);
        dest.writeDouble(this.latpt);
        dest.writeDouble(this.lngpt);
        dest.writeInt(this.radius);
        dest.writeStringList(this.attachments);
    }

    public EventReport() {}

    protected EventReport(Parcel in) {
        this.eventTypeId = in.readInt();
        this.eventInfo = in.readString();
        this.stat = in.readString();
        this.alertLevelId = in.readInt();
        this.hikeId = in.readInt();
        this.eventTime = in.readLong();
        this.reporterId = in.readInt();
        this.latpt = in.readDouble();
        this.lngpt = in.readDouble();
        this.radius = in.readInt();
        this.attachments = in.createStringArrayList();
    }

    public static final Creator<EventReport> CREATOR = new Creator<EventReport>() {
        @Override
        public EventReport createFromParcel(Parcel source) {
            return new EventReport(source);
        }

        @Override
        public EventReport[] newArray(int size) {
            return new EventReport[size];
        }
    };
}
