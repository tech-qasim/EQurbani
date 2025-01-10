package com.code.e_qurbani.firebase.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class BidAnimal implements Parcelable {
    private String buyerName;
    private String address;
    private String number;
    private String amountOffered;
    private String buyerReference;
    private String paymentMethod;
    private String sellerReference;
    private String animalReference;
    private String bidDocReference;
    private String ownerName;
    private String owner;
    private String ownerAddress;

    public BidAnimal() {
    }

    @Override
    public String toString() {
        return "BidAnimal{" +
                       "buyerName='" + buyerName + '\'' +
                       ", address='" + address + '\'' +
                       ", number='" + number + '\'' +
                       ", amountOffered='" + amountOffered + '\'' +
                       ", buyerReference='" + buyerReference + '\'' +
                       ", paymentMethod='" + paymentMethod + '\'' +
                       ", sellerReference='" + sellerReference + '\'' +
                       ", animalReference='" + animalReference + '\'' +
                       ", bidDocReference='" + bidDocReference + '\'' +
                       ", ownerName='" + ownerName + '\'' +
                       ", owner='" + owner + '\'' +
                       ", ownerAddress='" + ownerAddress + '\'' +
                       '}';
    }

    protected BidAnimal(Parcel in) {
        buyerName = in.readString();
        address = in.readString();
        number = in.readString();
        amountOffered = in.readString();
        buyerReference = in.readString();
        paymentMethod = in.readString();
        sellerReference = in.readString();
        animalReference = in.readString();
        bidDocReference = in.readString();
        ownerName = in.readString();
        owner = in.readString();
        ownerAddress = in.readString();
    }

    public static final Creator<BidAnimal> CREATOR = new Creator<BidAnimal>() {
        @Override
        public BidAnimal createFromParcel(Parcel in) {
            return new BidAnimal(in);
        }

        @Override
        public BidAnimal[] newArray(int size) {
            return new BidAnimal[size];
        }
    };

    public void setBidDocReference(String bidDocReference) {
        this.bidDocReference = bidDocReference;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public String getAmountOffered() {
        return amountOffered;
    }

    public String getBuyerReference() {
        return buyerReference;
    }


    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getSellerReference() {
        return sellerReference;
    }

    public String getAnimalReference() {
        return animalReference;
    }

    public String getBidDocReference() {
        return bidDocReference;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwner() {
        return owner;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public BidAnimal(String buyerName, String address, String number, String amountOffered, String buyerReference, String paymentMethod, String sellerReference, String animalReference, String bidDocReference, String ownerName, String owner, String ownerAddress) {
        this.buyerName = buyerName;
        this.address = address;
        this.number = number;
        this.amountOffered = amountOffered;
        this.buyerReference = buyerReference;
        this.paymentMethod = paymentMethod;
        this.sellerReference = sellerReference;
        this.animalReference = animalReference;
        this.bidDocReference = bidDocReference;
        this.ownerName = ownerName;
        this.owner = owner;
        this.ownerAddress = ownerAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(buyerName);
        dest.writeString(address);
        dest.writeString(number);
        dest.writeString(amountOffered);
        dest.writeString(buyerReference);
        dest.writeString(paymentMethod);
        dest.writeString(sellerReference);
        dest.writeString(animalReference);
        dest.writeString(bidDocReference);
        dest.writeString(ownerName);
        dest.writeString(owner);
        dest.writeString(ownerAddress);
    }
}
