package com.code.e_qurbani.firebase.entity;

public class User {

    private String fullName  = "";
    private String email  = "";
    private String password  = "";
    private String selectedRole  = "";


    private Double longitude ;
    private Double latitude;

    private String address;
    public User() {
    }

    public User(String fullName, String email, String password, String selectedRole, Double longtitude , Double latitude, String address) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.selectedRole = selectedRole;
        this.longitude = longtitude;
        this.latitude = latitude;
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

    public Double getLongitude() {
        return longitude;
    }

    // Setter for longitude
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    // Getter for latitude
    public Double getLatitude() {
        return latitude;
    }

    // Setter for latitude
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }


    public String getAddress()
    {
        return  address;
    }



}

