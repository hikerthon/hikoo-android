package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._HikerInfo;
import com.hackathon.hikoo.model.entity._MountainPermit;
import com.hackathon.hikoo.model.entity._TrailsItem;

import java.util.ArrayList;

public class MountainPermit extends _MountainPermit implements Parcelable {

    public enum PermitAccessType {
        PENDING("PENDING"),
        ACCEPTED("ACCEPTED"),
        REJECTED("REJECTED");

        private String  rawValue;

        PermitAccessType(String rawValue) {
            this.rawValue = rawValue;
        }

        public String getRawValue() {
            return rawValue;
        }
    }

    public PermitAccessType getPermitAccessType() {
        switch (permitAccepted) {
            case "PENDING":
                return PermitAccessType.PENDING;
            case "ACCEPTED":
                return PermitAccessType.ACCEPTED;
            case "REJECTED":
                return PermitAccessType.REJECTED;
            default:
                return PermitAccessType.PENDING;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.permitAccepted);
        dest.writeLong(this.hikeStart);
        dest.writeString(this.guideContact2);
        dest.writeParcelable(this.hikerInfo, flags);
        dest.writeInt(this.permitId);
        dest.writeString(this.permitName);
        dest.writeString(this.guideContact);
        dest.writeLong(this.hikeEnd);
        dest.writeString(this.memo);
        dest.writeList(this.trails);
        dest.writeByte(this.hikeFinished ? (byte) 1 : (byte) 0);
        dest.writeLong(this.acceptedTime);
        dest.writeByte(this.hikeCancelled ? (byte) 1 : (byte) 0);
        dest.writeInt(this.hikerId);
        dest.writeByte(this.hikeStarted ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
        dest.writeLong(this.logtime);
        dest.writeString(this.guideName);
    }

    public MountainPermit() {
    }

    protected MountainPermit(Parcel in) {
        this.permitAccepted = in.readString();
        this.hikeStart = in.readLong();
        this.guideContact2 = in.readString();
        this.hikerInfo = in.readParcelable(HikerInfo.class.getClassLoader());
        this.permitId = in.readInt();
        this.permitName = in.readString();
        this.guideContact = in.readString();
        this.hikeEnd = in.readLong();
        this.memo = in.readString();
        this.trails = new ArrayList<TrailsItem>();
        in.readList(this.trails, TrailsItem.class.getClassLoader());
        this.hikeFinished = in.readByte() != 0;
        this.acceptedTime = in.readLong();
        this.hikeCancelled = in.readByte() != 0;
        this.hikerId = in.readInt();
        this.hikeStarted = in.readByte() != 0;
        this.id = in.readInt();
        this.logtime = in.readLong();
        this.guideName = in.readString();
    }

    public static final Creator<MountainPermit> CREATOR = new Creator<MountainPermit>() {
        @Override
        public MountainPermit createFromParcel(Parcel source) {
            return new MountainPermit(source);
        }

        @Override
        public MountainPermit[] newArray(int size) {
            return new MountainPermit[size];
        }
    };
}
