package com.hackathon.hikoo.model.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.hackathon.hikoo.model.entity._Shelter;

import java.text.DecimalFormat;

public class Shelter extends _Shelter implements Parcelable {

    public enum ShelterType {
        MY_LOCATION,
        SHELTER;
    }

    public ShelterType getShelterType() {
        switch (shelterName) {
            case "My Location":
                return ShelterType.MY_LOCATION;
            default:
                return ShelterType.SHELTER;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shelterName);
        dest.writeInt(this.id);
        dest.writeDouble(this.latpt);
        dest.writeDouble(this.lngpt);
        dest.writeInt(this.capacity);
    }

    public Shelter createUserShelter(Double lat, Double lng) {
        DecimalFormat doubleFormat = new DecimalFormat("#.#######");
        Shelter shelter = new Shelter();
        shelter.id = 0;
        shelter.capacity = 0;
        shelter.shelterName = "My Location";
        shelter.latpt = Double.parseDouble(doubleFormat.format(lat));
        shelter.lngpt = Double.parseDouble(doubleFormat.format(lng));
        return shelter;
    }

    public Shelter() {
    }

    protected Shelter(Parcel in) {
        this.shelterName = in.readString();
        this.id = in.readInt();
        this.latpt = in.readDouble();
        this.lngpt = in.readDouble();
        this.capacity = in.readInt();
    }

    public static final Creator<Shelter> CREATOR = new Creator<Shelter>() {
        @Override
        public Shelter createFromParcel(Parcel source) {
            return new Shelter(source);
        }

        @Override
        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };
}
