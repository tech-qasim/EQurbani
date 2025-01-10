package com.code.e_qurbani.firebase.entity;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class TransporterProposal implements Parcelable {

    String buyerName;

    @Override
    public String toString() {
        return "TransporterProposal{" +
                "buyerName='" + buyerName + '\'' +
                ", PickupAddress='" + PickupAddress + '\'' +
                ", dropAddress='" + dropAddress + '\'' +
                ", buyerDocRef='" + buyerDocRef + '\'' +
                ", weight='" + weight + '\'' +
                ", price='" + price + '\'' +
                ", animalType='" + animalType + '\'' +
                ", proposalStatus='" + proposalStatus + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", day='" + day + '\'' +
                ", date='" + date + '\'' +
                ", month='" + month + '\'' +
                '}';
    }

    String PickupAddress;
    String dropAddress;
    String buyerDocRef;
    String weight;
    String price;
    String animalType;
    String proposalStatus;
    String phoneNumber;
    String day;
    String date;
    String month;

    String butcherEmail;

    String starRating;


    String review;

    boolean isOrderCompleted;

    String proposalID;

    public TransporterProposal() {
    }

    public TransporterProposal(String buyerName, String pickupAddress, String dropAddress, String buyerDocRef, String weight, String price, String animalType, String proposalStatus, String phoneNumber, String day, String date, String month, String butcherEmail, String starRating, String proposalID, String review, boolean isOrderCompleted) {
        this.buyerName = buyerName;
        PickupAddress = pickupAddress;
        this.dropAddress = dropAddress;
        this.buyerDocRef = buyerDocRef;
        this.weight = weight;
        this.price = price;
        this.animalType = animalType;
        this.proposalStatus = proposalStatus;
        this.phoneNumber = phoneNumber;
        this.day = day;
        this.date = date;
        this.month = month;
        this.butcherEmail = butcherEmail;
        this.review = review;
        this.starRating = starRating;
        this.isOrderCompleted = isOrderCompleted;
        this.proposalID = proposalID;
    }

    public TransporterProposal(Parcel in) {
        buyerName = in.readString();
        PickupAddress = in.readString();
        dropAddress = in.readString();
        buyerDocRef = in.readString();
        weight = in.readString();
        price = in.readString();
        animalType = in.readString();
        proposalStatus = in.readString();
        phoneNumber = in.readString();
        day = in.readString();
        date = in.readString();
        month = in.readString();
        butcherEmail = in.readString();
        review = in.readString();
        starRating = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isOrderCompleted = in.readBoolean();
        }
        proposalID = in.readString();

    }

    public static final Creator<TransporterProposal> CREATOR = new Creator<TransporterProposal>() {
        @Override
        public TransporterProposal createFromParcel(Parcel in) {
            return new TransporterProposal(in);
        }

        @Override
        public TransporterProposal[] newArray(int size) {
            return new TransporterProposal[size];
        }
    };

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getPickupAddress() {
        return PickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        PickupAddress = pickupAddress;
    }

    public String getDropAddress() {
        return dropAddress;
    }

    public void setDropAddress(String dropAddress) {
        this.dropAddress = dropAddress;
    }

    public String getBuyerDocRef() {
        return buyerDocRef;
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

    @Override
    public int describeContents() {
        return 0;
    }


    public void setButcherEmail(String butcherEmail)
    {
        this.butcherEmail = butcherEmail;
    }

    public String getButcherEmail ()
    {
        return butcherEmail;
    }

    public String getStarRating()
    {
        return starRating;
    }

    public String getReview ()
    {
        return review;
    }

    public String getProposalID()
    {
        return proposalID;
    }

    public boolean getIsOrderCompleted ()
    {
        return isOrderCompleted;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setProposalID(String proposalID) {
        this.proposalID = proposalID;
    }

    public void setIsOrderCompleted(boolean isOrderCompleted) {
        this.isOrderCompleted = isOrderCompleted;
    }








    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(buyerName);
        parcel.writeString(PickupAddress);
        parcel.writeString(dropAddress);
        parcel.writeString(buyerDocRef);
        parcel.writeString(weight);
        parcel.writeString(price);
        parcel.writeString(animalType);
        parcel.writeString(proposalStatus);
        parcel.writeString(phoneNumber);
        parcel.writeString(day);
        parcel.writeString(date);
        parcel.writeString(month);
        parcel.writeString(butcherEmail);
        parcel.writeString(review);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            parcel.writeBoolean(isOrderCompleted);
        }
        parcel.writeString(starRating);
        parcel.writeString(proposalID);
    }
}
