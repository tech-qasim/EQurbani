package com.code.e_qurbani.firebase.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Seller implements Parcelable {
    private String fullName = "";
    private String sellerReference = "";
    private String email = "";

    private String address;

    private Double longitude;

    private Double latitude;

    public Seller(String userName) {
        this.fullName = userName;
    }

    public Seller (String fullName, String sellerReference, String email, String address, Double longitude, Double latitude)
    {
        this.fullName = fullName;
        this.sellerReference = sellerReference;
        this.email = email;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }


    public Seller() {
    }

    protected Seller(Parcel in) {
        fullName = in.readString();
        sellerReference = in.readString();
        email = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<Seller> CREATOR = new Creator<Seller>() {
        @Override
        public Seller createFromParcel(Parcel in) {
            return new Seller(in);
        }

        @Override
        public Seller[] newArray(int size) {
            return new Seller[size];
        }
    };

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSellerReference() {
        return sellerReference;
    }

    public void setSellerReference(String sellerReference) {
        this.sellerReference = sellerReference;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "" +
                "fullName='" + fullName + '\'' +
                ", sellerReference='" + sellerReference + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getAddress ()
    {
        return address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(sellerReference);
        dest.writeString(email);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(address);
    }
}
