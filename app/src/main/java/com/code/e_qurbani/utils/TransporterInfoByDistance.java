package com.code.e_qurbani.utils;

import com.code.e_qurbani.firebase.entity.Butcher;

public class TransporterInfoByDistance {

    Butcher transporter;

    double distanceFromUser;

    // Default constructor
    public TransporterInfoByDistance() {}

    // Parameterized constructor
    public TransporterInfoByDistance(Butcher transporter, double distanceFromUser) {
        this.transporter = transporter;
        this.distanceFromUser = distanceFromUser;
    }

    public Butcher getTransporter ()
    {
        return transporter;
    }

    public void setTransporter(Butcher transporter)
    {
        this.transporter = transporter;
    }


    public void setDistanceFromUser(double distanceFromUser) {
        this.distanceFromUser = distanceFromUser;
    }

    public Double getDistanceFromUser ()
    {
        return distanceFromUser;
    }


}
