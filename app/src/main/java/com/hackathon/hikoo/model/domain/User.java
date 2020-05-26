package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._User;

public class User extends _User implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lastName);
        dest.writeString(this.address);
        dest.writeString(this.gender);
        dest.writeString(this.mobileNumber);
        dest.writeString(this.emergencyContact);
        dest.writeString(this.userPwd);
        dest.writeString(this.idNumber);
        dest.writeString(this.emergencyNumber);
        dest.writeInt(this.userId);
        dest.writeString(this.firstName);
        dest.writeString(this.nationality);
        dest.writeString(this.dob);
        dest.writeString(this.satelliteNumber);
        dest.writeString(this.serPwd);
        dest.writeString(this.email);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.lastName = in.readString();
        this.address = in.readString();
        this.gender = in.readString();
        this.mobileNumber = in.readString();
        this.emergencyContact = in.readString();
        this.userPwd = in.readString();
        this.idNumber = in.readString();
        this.emergencyNumber = in.readString();
        this.userId = in.readInt();
        this.firstName = in.readString();
        this.nationality = in.readString();
        this.nationality = in.readString();
        this.dob = in.readString();
        this.satelliteNumber = in.readString();
        this.serPwd = in.readString();
        this.email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
