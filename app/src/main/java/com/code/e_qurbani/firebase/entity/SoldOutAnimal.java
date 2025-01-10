package com.code.e_qurbani.firebase.entity;

public class SoldOutAnimal {
    private String animalName;
    private String animalSalePrice;
    private String animalBuyerName;
    private Long animalSoldOutDate;
    private String imageUrl="";
    private String phoneNumber="";


    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public void setAnimalSalePrice(String animalSalePrice) {
        this.animalSalePrice = animalSalePrice;
    }

    public void setAnimalBuyerName(String animalBuyerName) {
        this.animalBuyerName = animalBuyerName;
    }

    public void setAnimalSoldOutDate(Long animalSoldOutDate) {
        this.animalSoldOutDate = animalSoldOutDate;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SoldOutAnimal() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getAnimalSalePrice() {
        return animalSalePrice;
    }

    public String getAnimalBuyerName() {
        return animalBuyerName;
    }

    public Long getAnimalSoldOutDate() {
        return animalSoldOutDate;
    }

    public SoldOutAnimal(String animalName, String animalSalePrice, String animalBuyerName, Long animalSoldOutDate, String imageUr , String phoneNumber) {
        this.animalName = animalName;
        this.animalSalePrice = animalSalePrice;
        this.animalBuyerName = animalBuyerName;
        this.animalSoldOutDate = animalSoldOutDate;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
    }
}
