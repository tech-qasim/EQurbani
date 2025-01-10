package com.code.e_qurbani.firebase.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Butcher  implements Parcelable{
    private String fullName  = "";
    private String email  = "";
    private String fullAddress  = "";
    private String contactNumber  = "";

    private Double latitude;

    private Double longitude;

    protected Butcher(Parcel in) {
        fullName = in.readString();
        email = in.readString();
        fullAddress = in.readString();
        contactNumber = in.readString();
        docReference = in.readString();
        password = in.readString();
        selectedRole = in.readString();
        latitude = Double.valueOf(in.readString());
        longitude = Double.valueOf(in.readString());
    }

    public static final Creator<Butcher> CREATOR = new Creator<Butcher>() {
        @Override
        public Butcher createFromParcel(Parcel in) {
            return new Butcher(in);
        }

        @Override
        public Butcher[] newArray(int size) {
            return new Butcher[size];
        }
    };

    public String getDocReference() {
        return docReference;
    }

    public void setDocReference(String docReference) {
        this.docReference = docReference;
    }

    private String docReference  = "";





    @Override
    public String toString() {
        return "Butcher{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", password='" + password + '\'' +
                ", selectedRole='" + selectedRole + '\'' +
                '}';
    }

    private String password  = "";
    private String selectedRole  = "";

    public Butcher() {
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public Double getLatitude ()
    {
        return latitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public Double getLongitude ()
    {
        return longitude;
    }





    public Butcher(String fullName, String email, String fullAddress, String contactNumber, String password, String selectedRole, Double latitude, Double longitude) {
        this.fullName = fullName;
        this.email = email;
        this.fullAddress = fullAddress;
        this.contactNumber = contactNumber;
        this.password = password;
        this.selectedRole = selectedRole;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(fullName);
        parcel.writeString(email);
        parcel.writeString(fullAddress);
        parcel.writeString(contactNumber);
        parcel.writeString(docReference);
        parcel.writeString(password);
        parcel.writeString(selectedRole);
        parcel.writeString(String.valueOf(longitude));
        parcel.writeString(String.valueOf(latitude));
    }
}
