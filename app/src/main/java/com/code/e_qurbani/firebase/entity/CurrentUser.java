package com.code.e_qurbani.firebase.entity;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CurrentUser extends Application {

    private static FirebaseAuth mAuth;

    @Override
    public void onCreate() {
        super.onCreate();
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Get the current authenticated user.
     * @return FirebaseUser object if a user is logged in, otherwise null.
     */
    public static FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    /**
     * Sign out the current user.
     */
    public void signOut() {
        mAuth.signOut();
    }
}