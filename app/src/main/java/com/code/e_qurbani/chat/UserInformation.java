package com.code.e_qurbani.chat;

import com.google.firebase.auth.UserInfo;

public class UserInformation {
    private String fullName;
    private String email;

    public UserInformation() {
        // Default constructor required for Firebase
    }

    public UserInformation(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    // Getters and Setters
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

    // Additional Functions
    public void displayUserInfo() {
        System.out.println("Full Name: " + fullName);
        System.out.println("Email: " + email);
    }

    public boolean isValidEmail() {
        // Add your email validation logic here
        // For simplicity, let's assume any non-null or non-empty email is valid
        return email != null && !email.isEmpty();
    }

    // You can add more functions as per your requirements
}
