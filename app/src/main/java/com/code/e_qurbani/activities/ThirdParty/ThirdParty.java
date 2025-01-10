package com.code.e_qurbani.activities.ThirdParty;

import java.io.Serializable;

public class ThirdParty implements Serializable {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String cnicNumber;

    private String docReference;

    private String buyerEmail;
    private String selectedRole;

    Double latitude;

    Double longitude;

    // Constructor
    public ThirdParty(String fullName, String email, String password, String phoneNumber, String address, String cnicNumber, String selectedRole, String buyerEmail, Double latitude, Double longitude) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.cnicNumber = cnicNumber;
        this.selectedRole = selectedRole;
        this.buyerEmail = buyerEmail;
        this.latitude = latitude;
        this.longitude = longitude;
//        this.docReference = docReference;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }



    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public ThirdParty() {
        // Required for Firestore deserialization
    }

    public String getDocReference() {
        return docReference;
    }

    public void setDocReference (String docReference)
    {
        this.docReference = docReference;
    }



    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBuyerEmail()
    {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }







    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSelectedRole (String selectedRole)
    {
        this.selectedRole = selectedRole;
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCnicNumber() {
        return cnicNumber;
    }

    public void setCnicNumber(String cnicNumber) {
        this.cnicNumber = cnicNumber;
    }

    // toString method to print the object

}
