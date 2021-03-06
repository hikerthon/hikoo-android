package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hackathon.hikoo.model.entity._User;

public class User extends _User implements Parcelable {

    public Boolean isValidate() {
        return !TextUtils.isEmpty(this.lastName) && !TextUtils.isEmpty(this.firstName) &&
                !TextUtils.isEmpty(this.gender) && !TextUtils.isEmpty(this.nationality) &&
                this.dob != 0 && !TextUtils.isEmpty(this.identificationNumber) &&
                !TextUtils.isEmpty(this.mobileNumber) && !TextUtils.isEmpty(this.satelliteNumber) &&
                !TextUtils.isEmpty(this.image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.lastName);
        dest.writeString(this.image);
        dest.writeString(this.address);
        dest.writeString(this.gender);
        dest.writeString(this.mobileNumber);
        dest.writeString(this.emergencyContact);
        dest.writeString(this.emergencyNumber);
        dest.writeString(this.firstName);
        dest.writeString(this.password);
        dest.writeString(this.identification);
        dest.writeString(this.nationality);
        dest.writeString(this.watchStatus);
        dest.writeLong(this.dob);
        dest.writeString(this.satelliteNumber);
        dest.writeString(this.identificationNumber);
        dest.writeString(this.emergencyMobile);
        dest.writeString(this.homeNumber);
        dest.writeString(this.fcmToken);
        dest.writeString(this.email);
        dest.writeString(this.username);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.lastName = in.readString();
        this.image = in.readString();
        this.address = in.readString();
        this.gender = in.readString();
        this.mobileNumber = in.readString();
        this.emergencyContact = in.readString();
        this.emergencyNumber = in.readString();
        this.firstName = in.readString();
        this.password = in.readString();
        this.identification = in.readString();
        this.nationality = in.readString();
        this.watchStatus = in.readString();
        this.dob = in.readLong();
        this.satelliteNumber = in.readString();
        this.identificationNumber = in.readString();
        this.emergencyMobile = in.readString();
        this.homeNumber = in.readString();
        this.fcmToken = in.readString();
        this.email = in.readString();
        this.username = in.readString();
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
