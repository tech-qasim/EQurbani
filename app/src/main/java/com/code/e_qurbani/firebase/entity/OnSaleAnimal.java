package com.code.e_qurbani.firebase.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class OnSaleAnimal implements Parcelable {
    private String healthStatus = "";
    private String animalReferenceNumber = "";
    private String farmerReferenceNumber = "";
    private String animalType = "";
    private String breed = "";
    private String dateOfBirth = "";

    private String desc = "";
    private String gender = "";
    private String imageUri = "";
    private double weight = 0.0;
    private String isFullyVaccinated = "";
    private String name = "";

    private String ownerName = "";
    private String contactNumber = "";
    private String ownerEmail = "";
    private double forSaleAnimalPrice = 0;
    private String ownerAddress = "";
    private String isPregnant = "";

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getAnimalReferenceNumber() {
        return animalReferenceNumber;
    }

    public void setAnimalReferenceNumber(String animalReferenceNumber) {
        this.animalReferenceNumber = animalReferenceNumber;
    }

    public String getFarmerReferenceNumber() {
        return farmerReferenceNumber;
    }

    public void setFarmerReferenceNumber(String farmerReferenceNumber) {
        this.farmerReferenceNumber = farmerReferenceNumber;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getIsFullyVaccinated() {
        return isFullyVaccinated;
    }

    public void setIsFullyVaccinated(String isFullyVaccinated) {
        this.isFullyVaccinated = isFullyVaccinated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public double getForSaleAnimalPrice() {
        return forSaleAnimalPrice;
    }

    public void setForSaleAnimalPrice(double forSaleAnimalPrice) {
        this.forSaleAnimalPrice = forSaleAnimalPrice;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getIsPregnant() {
        return isPregnant;
    }

    public void setIsPregnant(String isPregnant) {
        this.isPregnant = isPregnant;
    }

    public OnSaleAnimal() {
    }

    public OnSaleAnimal(String animalReferenceNumber, String farmerReferenceNumber, String animalType, String breed, String dateOfBirth, String desc, String gender, String imageUri, String isFullyVaccinated, String name, String healthStatus, String ownerName, String contactNumber, String ownerEmail, double forSaleAnimalPrice, String ownerAddress, double weight) {
        this.animalReferenceNumber = animalReferenceNumber;
        this.farmerReferenceNumber = farmerReferenceNumber;
        this.animalType = animalType;
        this.breed = breed;
        this.dateOfBirth = dateOfBirth;

        this.desc = desc;
        this.gender = gender;
        this.imageUri = imageUri;
        this.isFullyVaccinated = isFullyVaccinated;
        this.name = name;

        this.healthStatus = healthStatus;
        this.ownerName = ownerName;
        this.contactNumber = contactNumber;
        this.ownerEmail = ownerEmail;
        this.forSaleAnimalPrice = forSaleAnimalPrice;
        this.ownerAddress = ownerAddress;
        this.weight = weight;
    }


    protected OnSaleAnimal(Parcel in) {
        healthStatus = in.readString();
        animalReferenceNumber = in.readString();
        farmerReferenceNumber = in.readString();
        animalType = in.readString();
        breed = in.readString();
        dateOfBirth = in.readString();
        desc = in.readString();
        gender = in.readString();
        imageUri = in.readString();
        weight = in.readDouble();
        isFullyVaccinated = in.readString();
        name = in.readString();
        ownerName = in.readString();
        contactNumber = in.readString();
        ownerEmail = in.readString();
        forSaleAnimalPrice = in.readDouble();
        ownerAddress = in.readString();
        isPregnant = in.readString();
    }

    public static final Creator<OnSaleAnimal> CREATOR = new Creator<OnSaleAnimal>() {
        @Override
        public OnSaleAnimal createFromParcel(Parcel in) {
            return new OnSaleAnimal(in);
        }

        @Override
        public OnSaleAnimal[] newArray(int size) {
            return new OnSaleAnimal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(healthStatus);
        parcel.writeString(animalReferenceNumber);
        parcel.writeString(farmerReferenceNumber);
        parcel.writeString(animalType);
        parcel.writeString(breed);
        parcel.writeString(dateOfBirth);
        parcel.writeString(desc);
        parcel.writeString(gender);
        parcel.writeString(imageUri);
        parcel.writeDouble(weight);
        parcel.writeString(isFullyVaccinated);
        parcel.writeString(name);
        parcel.writeString(ownerName);
        parcel.writeString(contactNumber);
        parcel.writeString(ownerEmail);
        parcel.writeDouble(forSaleAnimalPrice);
        parcel.writeString(ownerAddress);
        parcel.writeString(isPregnant);
    }
}
