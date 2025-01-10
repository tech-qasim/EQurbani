package com.code.e_qurbani.utils;

import com.code.e_qurbani.activities.ThirdParty.ThirdParty;
import com.code.e_qurbani.activities.ThirdParty.ThirdPartyAdapter;
import com.code.e_qurbani.firebase.entity.Butcher;

public class ButcherInfoByDistance {

    Butcher butcher;

    double distanceFromUser;

    // Default constructor
    public ButcherInfoByDistance() {}

    // Parameterized constructor
    public ButcherInfoByDistance(Butcher butcher, double distanceFromUser) {
        this.butcher = butcher;
        this.distanceFromUser = distanceFromUser;
    }

    public Butcher getButcher ()
    {
        return butcher;
    }

    public void setButcher(Butcher butcher)
    {
        this.butcher = butcher;
    }


    public void setDistanceFromUser(double distanceFromUser) {
        this.distanceFromUser = distanceFromUser;
    }

    public Double getDistanceFromUser ()
    {
        return distanceFromUser;
    }


}
