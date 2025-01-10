package com.code.e_qurbani.firebase.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {
    private String name;
    private String location;
    private String placeSrc;
    private String docReference;

    public Place() {
    }

    public Place(String name, String location, String placeSrc, String docReference) {
        this.name = name;
        this.location = location;
        this.placeSrc = placeSrc;
        this.docReference = docReference;
    }

    protected Place(Parcel in) {
        name = in.readString();
        location = in.readString();
        placeSrc = in.readString();
        docReference = in.readString();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPlaceSrc() {
        return placeSrc;
    }

    public String getDocReference() {
        return docReference;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPlaceSrc(String placeSrc) {
        this.placeSrc = placeSrc;
    }

    public void setDocReference(String docReference) {
        this.docReference = docReference;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", placeSrc='" + placeSrc + '\'' +
                ", docReference='" + docReference + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(placeSrc);
        parcel.writeString(docReference);
    }
}