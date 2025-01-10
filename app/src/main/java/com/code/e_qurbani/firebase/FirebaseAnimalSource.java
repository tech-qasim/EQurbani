package com.code.e_qurbani.firebase;

import static android.content.Intent.getIntent;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.code.e_qurbani.activities.ThirdParty.PlaceRecyclerAdapter;
import com.code.e_qurbani.adapter.AnimalRecyclerAdapter;
import com.code.e_qurbani.firebase.entity.Animal;
import com.code.e_qurbani.firebase.entity.OnSaleAnimal;
import com.code.e_qurbani.firebase.entity.Place;
import com.code.e_qurbani.firebase.entity.User;
import com.code.e_qurbani.utils.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FirebaseAnimalSource {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Context context;


    public void getListOfThirdParty (Context context, ArrayList<Place> thirdPartyList, PlaceRecyclerAdapter placeRecyclerAdapter, String getIntentRole, Dialog dialog)
    {
        thirdPartyList.clear();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (getIntentRole == null) {
//            Toast.makeText(context, "Role is not provided", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }

        db.collection(Constant.THIRD_PARTY).document(auth.getUid().toString()).collection(Constant.THIRD_PARTY).addSnapshotListener(((value, error) -> {
            thirdPartyList.clear();
            if (error != null) {
                dialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
           if (value!=null) {
               for (DocumentSnapshot snapshot : value) {
                   Place place = snapshot.toObject(Place.class);
                   assert place != null;
                   place.setDocReference(db.collection(Constant.SELLER).document(auth.getUid().toString()).collection(getIntentRole).document(snapshot.getId()).getPath().toString());
                   thirdPartyList.add(place);
               }
           }
            placeRecyclerAdapter.notifyDataSetChanged();
            dialog.dismiss();

        }));

    }


    public void getListOfAnimals(Context context, ArrayList<Animal> animalArrayList, AnimalRecyclerAdapter animalRecyclerAdapter, String getIntentRole, Dialog dialog) {
        animalArrayList.clear();
//        getIntentRole = getIntent(context).getStringExtra(Constant.GET_ROLE);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (getIntentRole == null) {
            Toast.makeText(context, "Role is not provided", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        }
        db.collection(Constant.SELLER).document(auth.getUid().toString()).collection(getIntentRole).addSnapshotListener(((value, error) -> {
            animalArrayList.clear();
            if (error != null) {
                dialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            assert value != null;
            for (DocumentSnapshot snapshot : value) {
                Animal animal = snapshot.toObject(Animal.class);
                assert animal != null;
                animal.setDocReference(db.collection(Constant.SELLER).document(auth.getUid().toString()).collection(getIntentRole).document(snapshot.getId()).getPath().toString());
                animalArrayList.add(animal);
            }
            animalRecyclerAdapter.notifyDataSetChanged();
            dialog.dismiss();

        }));
    }


    public void putAnimalOnSale(Context context, Animal animal, String collectionType, Dialog dialog, Double amountDemanded, String address, String contactNumber, String paymentMethod) {
        dialog.show();




        new Handler().post(() -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("putForSale", "true");
            db.document(animal.getDocReference()).set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Task<DocumentSnapshot> snapshotTask = db.collection(Constant.SELLER).document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).get();
                    snapshotTask.addOnCompleteListener(documentSnapshot -> {
                        if (snapshotTask.getResult().exists()) {
                            User user = snapshotTask.getResult().toObject(User.class);
                            assert user != null;
                            db.collection(Constant.SELLER_FOR_SALE_COLLECTION).document(auth.getCurrentUser().getUid()).set(user);
                            String animalReferenceId = animal.getDocReference().split("/")[3];

                            Log.d("TAG", "animal Reference: " +animalReferenceId);
                            Log.d("TAG", "User: " +user);
                            OnSaleAnimal onSaleAnimal = new OnSaleAnimal(animalReferenceId, auth.getUid(), collectionType, animal.getBreedType(), animal.getDateOfBirth()
                                    , animal.getBioNotes(), animal.getGenderType(), animal.getAnimalSrc(), animal.getIsFullyVaccinated(), animal.getAnimalName(), animal.getHealthDesc(),
                                    user.getFullName(), contactNumber, user.getEmail(), amountDemanded, address, animal.getWeight());
                            db.collection(Constant.SELLER_FOR_SALE_COLLECTION).document(auth.getCurrentUser().getUid()).collection(collectionType).document(animalReferenceId).set(onSaleAnimal).addOnSuccessListener(unused1 -> {
                                dialog.dismiss();
                                Toast.makeText(context, "Task Success", Toast.LENGTH_LONG).show();
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            dialog.dismiss();
                            Log.d("TAG", "Result Does Not Exist For Query: ");
                            Toast.makeText(context, "Invalid Query", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        });
    }

}
