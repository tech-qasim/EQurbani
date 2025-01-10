package com.code.e_qurbani.firebase;


import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.code.e_qurbani.adapter.BidderAnimalAdapter;
import com.code.e_qurbani.firebase.entity.BidAnimal;
import com.code.e_qurbani.firebase.entity.OnSaleAnimal;
import com.code.e_qurbani.utils.Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Objects;

public class FirebaseBidding {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public void updateAnimalBidRecord(OnSaleAnimal onSaleAnimal, Dialog dialog, Context context, String name, Double amountDemanded, String address, String number, String paymentMethod) {

        dialog.show();
        BidAnimal animal = new BidAnimal(name, address, number, String.valueOf(amountDemanded), auth.getUid()
                , paymentMethod, onSaleAnimal.getFarmerReferenceNumber()
                , onSaleAnimal.getAnimalReferenceNumber(), "", onSaleAnimal.getOwnerName(), onSaleAnimal.getOwnerEmail(), onSaleAnimal.getOwnerAddress());
        db.collection(Constant.SELLER_FOR_SALE_COLLECTION)
                .document(onSaleAnimal.getFarmerReferenceNumber()).collection(onSaleAnimal.getAnimalType())
                .document(onSaleAnimal.getAnimalReferenceNumber()).collection(Constant.BIDDING).document(Objects.requireNonNull(auth.getUid())).set(animal, SetOptions.merge());
        Toast.makeText(context, "You Will Be Contacted , If Your Proposal Accepted", Toast.LENGTH_SHORT).show();
        dialog.dismiss();

    }

    public void getAnimalBidderList(Context context, Dialog dialog, ArrayList<BidAnimal> bidAnimalArrayList, OnSaleAnimal onSaleAnimal,
                                    BidderAnimalAdapter bidderAnimalAdapter) {

        Log.d("TAG", "getAnimalType: " +onSaleAnimal.getAnimalType());
        Log.d("TAG", "getAnimalReferenceNumber: " +onSaleAnimal.getAnimalReferenceNumber());
        db.collection(Constant.SELLER_FOR_SALE_COLLECTION)
                .document(onSaleAnimal.getFarmerReferenceNumber()).collection(onSaleAnimal.getAnimalType())
                .document(onSaleAnimal.getAnimalReferenceNumber()).collection(Constant.BIDDING)
                .addSnapshotListener((value, error) -> {
                            if (error != null) {
                                dialog.dismiss();
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                if (value.isEmpty()) {
                                    Toast.makeText(context, "No Data Available", Toast.LENGTH_LONG).show();
                                }
                                for (QueryDocumentSnapshot documentSnapshot : value) {
                                    BidAnimal bidAnimal = documentSnapshot.toObject(BidAnimal.class);
                                    bidAnimal.setBidDocReference(db.collection(Constant.SELLER_FOR_SALE_COLLECTION)
                                            .document(onSaleAnimal.getFarmerReferenceNumber()).collection(onSaleAnimal.getAnimalType())
                                            .document(onSaleAnimal.getAnimalReferenceNumber()).collection(Constant.BIDDING).document(documentSnapshot.getId()).getPath());
                                    Log.d("Bidder", "getAnimalBidderList: " + bidAnimal);
                                    bidAnimalArrayList.add(bidAnimal);
                                }
                                dialog.dismiss();
                                bidderAnimalAdapter.notifyDataSetChanged();
                            }
                        }
                );
    }
}
