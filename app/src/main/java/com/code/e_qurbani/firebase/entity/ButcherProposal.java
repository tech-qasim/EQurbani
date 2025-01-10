package com.code.e_qurbani.firebase.entity;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ButcherProposal implements Parcelable {
    String buyerName;
    String buyerAddress;
    String buyerDocRef;

    String proposalID;

    @Override
    public String toString() {
        return "ButcherProposal{" +
                "buyerName='" + buyerName + '\'' +
                ", buyerAddress='" + buyerAddress + '\'' +
                ", buyerDocRef='" + buyerDocRef + '\'' +
                ", weight='" + weight + '\'' +
                ", price='" + price + '\'' +
                ", animalType='" + animalType + '\'' +
                ", proposalStatus='" + proposalStatus + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", day='" + day + '\'' +
                ", date='" + date + '\'' +
                ", month='" + month + '\'' +
                ", proposalID='" + proposalID + '\'' +
                '}';
    }

    public ButcherProposal() {
        // Default constructor required for calls to DataSnapshot.getValue(ButcherProposal.class)
    }

    String weight;
    String price;
    String animalType;
    String proposalStatus;
    String phoneNumber;
    String day;
    String date;
    String month;

    String buyerEmail;

    String starRating;

    String review;

    boolean isOrderCompleted;






    protected ButcherProposal(Parcel in) {
        buyerName = in.readString();
        buyerAddress = in.readString();
        buyerDocRef = in.readString();
        weight = in.readString();
        price = in.readString();
        animalType = in.readString();
        proposalStatus = in.readString();
        phoneNumber = in.readString();
        day = in.readString();
        date = in.readString();
        month = in.readString();
        buyerEmail = in.readString();
        starRating  = in.readString();
        proposalID = in.readString();
        review = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isOrderCompleted = in.readBoolean();
        }
    }

    public static final Creator<ButcherProposal> CREATOR = new Creator<ButcherProposal>() {
        @Override
        public ButcherProposal createFromParcel(Parcel in) {
            return new ButcherProposal(in);
        }

        @Override
        public ButcherProposal[] newArray(int size) {
            return new ButcherProposal[size];
        }
    };

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerDocRef() {
        return buyerDocRef;
    }

    public String getReview()
    {
        return review;
    }

    public void setReview (String review)
    {
        this.review = review;
    }



    public void setBuyerDocRef(String buyerDocRef) {
        this.buyerDocRef = buyerDocRef;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getProposalStatus() {
        return proposalStatus;
    }

    public void setProposalStatus(String proposalStatus) {
        this.proposalStatus = proposalStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }


    public void setMonth(String month) {
        this.month = month;
    }

    public String getStarRating(){
        return starRating;
    }

    public void setStarRating(String starRating)
    {
        this.starRating = starRating;
    }

    public void setProposalID (String proposalID)
    {
        this.proposalID = proposalID;
    }

    public String getProposalID()
    {
        return proposalID;
    }

    public void setIsOrderCompleted (boolean isOrderCompleted)
    {
        this.isOrderCompleted = isOrderCompleted;
    }

    public Boolean getIsOrderCompleted ()
    {
        return isOrderCompleted;
    }




    public ButcherProposal(String buyerName, String buyerAddress, String buyerDocRef, String weight, String price, String animalType, String proposalStatus, String phoneNumber, String day, String date, String month, String buyerEmail, String starRating, String proposalID, String review, boolean isOrderCompleted) {
        this.buyerName = buyerName;
        this.buyerAddress = buyerAddress;
        this.buyerDocRef = buyerDocRef;
        this.weight = weight;
        this.price = price;
        this.animalType = animalType;
        this.proposalStatus = proposalStatus;
        this.phoneNumber = phoneNumber;
        this.day = day;
        this.date = date;
        this.month = month;
        this.buyerEmail = buyerEmail;
        this.starRating = starRating;
        this.proposalID = proposalID;
        this.review = review;
        this.isOrderCompleted = isOrderCompleted;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(buyerName);
        parcel.writeString(buyerAddress);
        parcel.writeString(buyerDocRef);
        parcel.writeString(weight);
        parcel.writeString(price);
        parcel.writeString(animalType);
        parcel.writeString(proposalStatus);
        parcel.writeString(phoneNumber);
        parcel.writeString(day);
        parcel.writeString(date);
        parcel.writeString(month);
        parcel.writeString(buyerEmail);
        parcel.writeString(starRating);
        parcel.writeString(proposalID);
        parcel.writeString(review);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            parcel.writeBoolean(isOrderCompleted);
        }
    }
}
