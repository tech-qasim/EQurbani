package com.code.e_qurbani.firebase.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Animal implements Parcelable {
    private String animalName;
    private String genderType;
    private int noOfChild;
    private String isFullyVaccinated;
    private String breedType;
    private String isPregnant;
    private String dateOfBirth;
    private double weight;
    private String bioNotes;
     private String animalSrc;
    private String docReference;
    private String healthDesc;
    private String putForSale = "false";
    public Animal() {
    }

    public Animal(String animalName, String genderType, int noOfChild, String isFullyVaccinated, String breedType, String isPregnant, String dateOfBirth, double weight, String bioNotes , String animalSrc, String docReference, String healthDesc,String putForSale ) {
        this.animalName = animalName;
        this.genderType = genderType;
        this.noOfChild = noOfChild;
        this.isFullyVaccinated = isFullyVaccinated;
        this.breedType = breedType;
        this.isPregnant = isPregnant;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.bioNotes = bioNotes;
         this.animalSrc = animalSrc;
        this.docReference = docReference;
        this.healthDesc = healthDesc;
        this.putForSale = putForSale;
     }


    protected Animal(Parcel in) {
        animalName = in.readString();
        genderType = in.readString();
        noOfChild = in.readInt();
        isFullyVaccinated = in.readString();
        breedType = in.readString();
        isPregnant = in.readString();
        dateOfBirth = in.readString();
        weight = in.readDouble();
        bioNotes = in.readString();
        animalSrc = in.readString();
        docReference = in.readString();
        healthDesc = in.readString();
        putForSale = in.readString();
    }

    public static final Creator<Animal> CREATOR = new Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };

    public String isPutForSale() {
        return putForSale;
    }
    

    public void setDocReference(String docReference) {
        this.docReference = docReference;
    }

    public String getAnimalName() {
        return animalName;
    }

    public String getGenderType() {
        return genderType;
    }

    @Override
    public String toString() {
        return "Animal{" +
                       "animalName='" + animalName + '\'' +
                       ", genderType='" + genderType + '\'' +
                       ", noOfChild=" + noOfChild +
                       ", isFullyVaccinated='" + isFullyVaccinated + '\'' +
                       ", breedType='" + breedType + '\'' +
                       ", isPregnant='" + isPregnant + '\'' +
                       ", dateOfBirth='" + dateOfBirth + '\'' +
                       ", weight=" + weight +
                       ", bioNotes='" + bioNotes + '\'' +
                       ", animalSrc='" + animalSrc + '\'' +
                       ", docReference='" + docReference + '\'' +
                       ", healthDesc='" + healthDesc + '\'' +
                       '}';
    }

    public int getNoOfChild() {
        return noOfChild;
    }

    public String getIsFullyVaccinated() {
        return isFullyVaccinated;
    }

    public String getBreedType() {
        return breedType;
    }

    public String getIsPregnant() {
        return isPregnant;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public double getWeight() {
        return weight;
    }

    public String getBioNotes() {
        return bioNotes;
    }


    public String getAnimalSrc() {
        return animalSrc;
    }

    public String getDocReference() {
        return docReference;
    }

    public String getHealthDesc() {
        return healthDesc;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(animalName);
        parcel.writeString(genderType);
        parcel.writeInt(noOfChild);
        parcel.writeString(isFullyVaccinated);
        parcel.writeString(breedType);
        parcel.writeString(isPregnant);
        parcel.writeString(dateOfBirth);
        parcel.writeDouble(weight);
        parcel.writeString(bioNotes);
        parcel.writeString(animalSrc);
        parcel.writeString(docReference);
        parcel.writeString(healthDesc);
        parcel.writeString(putForSale);
    }
}
