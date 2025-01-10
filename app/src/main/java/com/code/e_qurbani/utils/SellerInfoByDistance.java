package com.code.e_qurbani.utils;

import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.firebase.entity.Seller;

public class SellerInfoByDistance {

  Seller seller;



    double distanceFromUser;

    // Default constructor
    public SellerInfoByDistance() {}

    // Parameterized constructor
    public SellerInfoByDistance(Seller seller, double distanceFromUser) {
        this.seller = seller;
        this.distanceFromUser = distanceFromUser;
    }

    public Seller getSeller ()
    {
        return seller;
    }

    public void setSeller(Seller seller)
    {
        this.seller = seller;
    }


    public void setDistanceFromUser(double distanceFromUser) {
        this.distanceFromUser = distanceFromUser;
    }

    public Double getDistanceFromUser ()
    {
        return distanceFromUser;
    }


}
