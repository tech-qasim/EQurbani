package com.code.e_qurbani.activities.seller;

import static com.code.e_qurbani.utils.Constant.GET_ROLE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.code.e_qurbani.R;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivitySellAnimalToSelectedBidderBinding;
import com.code.e_qurbani.firebase.entity.BidAnimal;
import com.code.e_qurbani.firebase.entity.OnSaleAnimal;
import com.code.e_qurbani.firebase.entity.SoldOutAnimal;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SellAnimalToSelectedBidder extends AppCompatActivity {

    ActivitySellAnimalToSelectedBidderBinding binding;
    BidAnimal bidAnimal;
    OnSaleAnimal onSaleAnimal;
    Dialog dialog;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellAnimalToSelectedBidderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        dialog = new Dialog(this);
        DialogUtils.initLoadingDialog(dialog);
        bidAnimal = getIntent().getParcelableExtra(Constant.ANIMAL);
        onSaleAnimal = getIntent().getParcelableExtra(Constant.SELLER);
        binding.tvName.setText(bidAnimal.getOwnerName());
        Log.d("TAG", "error: " + onSaleAnimal.getAnimalType());
        binding.tvAddress.setText(bidAnimal.getAddress());
        binding.tvContactNo.setText(bidAnimal.getNumber());
        binding.tvPrice.setText(bidAnimal.getAmountOffered());

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });

        binding.acceptBidderOffer.setOnClickListener(v -> {
            /**
             * first update seller sold out animal
             */

            dialog.show();

            SoldOutAnimal soldOutAnimal = new SoldOutAnimal(onSaleAnimal.getName(), bidAnimal.getAmountOffered(), bidAnimal.getBuyerName(), System.currentTimeMillis(), onSaleAnimal.getImageUri(),onSaleAnimal.getContactNumber());


            db.collection(Constant.SELLER).document(onSaleAnimal.getFarmerReferenceNumber()).collection(Constant.SOLD_OUT).document().set(soldOutAnimal).addOnCompleteListener(task -> {
                /**
                 * update update buyer accepted bidding info
                 */
                if (task.isSuccessful()) {
                    SoldOutAnimal soldBuyer = new SoldOutAnimal(onSaleAnimal.getName(), bidAnimal.getAmountOffered(), onSaleAnimal.getOwnerName(), System.currentTimeMillis(), onSaleAnimal.getImageUri(),onSaleAnimal.getContactNumber());
                    db.collection(Constant.BUYER).document(bidAnimal.getBuyerReference()).collection(Constant.BUY_OUT).document().set(soldBuyer).addOnCompleteListener(task1 -> {
                        /**
                         * first delete from animal animals
                         */
                        if (task1.isSuccessful()) {
                            db.collection(Constant.SELLER)
                                    .document(onSaleAnimal.getFarmerReferenceNumber())
                                    .collection(onSaleAnimal.getAnimalType()).document(onSaleAnimal.getAnimalReferenceNumber()).addSnapshotListener((value, error) -> {
                                        if (error != null) {
                                            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                                        } else {

                                            dialog.dismiss();
                                            db.collection(Constant.SELLER_FOR_SALE_COLLECTION)
                                                    .document(onSaleAnimal.getFarmerReferenceNumber())
                                                    .collection(onSaleAnimal.getAnimalType()).document(onSaleAnimal.getAnimalReferenceNumber()).delete();
                                            startActivity(new Intent(SellAnimalToSelectedBidder.this, SoldAnimalDetails.class).putExtra(GET_ROLE, Constant.SELLER));
                                            finish();


                                        }
                                    });
                        } else {
                            dialog.dismiss();
                            Toast.makeText(SellAnimalToSelectedBidder.this, "Task Failed", Toast.LENGTH_SHORT).show();
                        }

                    });
                } else {
                    dialog.dismiss();
                    Toast.makeText(SellAnimalToSelectedBidder.this, "Task Failed", Toast.LENGTH_SHORT).show();
                }


            });


        });


    }
}