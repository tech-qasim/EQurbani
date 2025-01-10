package com.code.e_qurbani.utils;

import com.code.e_qurbani.activities.ThirdParty.ThirdParty;
import com.code.e_qurbani.activities.ThirdParty.ThirdPartyAdapter;

public class UserInfoByDistance {

   ThirdParty thirdParty;

    double distanceFromUser;

    // Default constructor
    public UserInfoByDistance() {}

    // Parameterized constructor
    public UserInfoByDistance(ThirdParty thirdParty, double distanceFromUser) {
        this.thirdParty = thirdParty;
        this.distanceFromUser = distanceFromUser;
    }

   public ThirdParty getThirdParty ()
   {
       return thirdParty;
   }

   public void setThirdParty(ThirdParty thirdParty)
   {
       this.thirdParty = thirdParty;
   }


    public void setDistanceFromUser(double distanceFromUser) {
        this.distanceFromUser = distanceFromUser;
    }

    public Double getDistanceFromUser ()
    {
        return distanceFromUser;
    }


}
