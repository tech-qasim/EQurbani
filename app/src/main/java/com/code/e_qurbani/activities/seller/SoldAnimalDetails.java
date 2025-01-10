package com.code.e_qurbani.activities.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.code.e_qurbani.R;
import com.code.e_qurbani.adapter.SellerSoldOutAnimalAdapter;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivitySoldAnimalDetailsBinding;
import com.code.e_qurbani.firebase.entity.SoldOutAnimal;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class SoldAnimalDetails extends AppCompatActivity {
    ActivitySoldAnimalDetailsBinding binding;
    Dialog dialog;
    SellerSoldOutAnimalAdapter sellerSoldOutAnimalAdapter;
    ArrayList<SoldOutAnimal> soldOutAnimalList = new ArrayList<>();
    RecyclerView recyclerView;
    FirebaseFirestore db;
    FirebaseAuth auth;
    String getIntent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySoldAnimalDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntent = getIntent().getStringExtra(Constant.GET_ROLE);
        setupRecyclerView();
        if (getIntent.equals(Constant.BUYER)) {

            getSoldOutAnimalDetailsForBuyer();
        } else {
            getSoldOutAnimalDetailsForSeller();
        }

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });

    }

    private void getSoldOutAnimalDetailsForBuyer() {
        dialog.show();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        db.collection(Constant.BUYER)
                .document(Objects.requireNonNull(auth.getUid()))
                .collection(Constant.BUY_OUT)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        dialog.dismiss();
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    String soldOut = "Buy Out " + value.size();
                    binding.soldOutCount.setText(soldOut);
                    for (QueryDocumentSnapshot documentSnapshot : value) {
                        SoldOutAnimal soldOutAnimal = documentSnapshot.toObject(SoldOutAnimal.class);
                        soldOutAnimalList.add(soldOutAnimal);
                    }
                    dialog.dismiss();
                    sellerSoldOutAnimalAdapter.notifyDataSetChanged();

                });
    }

    private void getSoldOutAnimalDetailsForSeller() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Log.d("TAG", "getSoldOutAnimalDetailsForSeller: ");
        Log.d("TAG", "getSoldOutAnimalDetailsForSeller: " + db.collection(Constant.SELLER)
                .document(Objects.requireNonNull(auth.getUid()))
                .collection(Constant.SOLD_OUT).getPath());

        dialog.show();

        db.collection(Constant.SELLER)
                .document(Objects.requireNonNull(auth.getUid()))
                .collection(Constant.SOLD_OUT)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        dialog.dismiss();
                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    String soldOut = "Sold Out " + value.size();
                    binding.soldOutCount.setText(soldOut);
                    if (value.isEmpty()){
                        Toast.makeText(this, "No Data Available", Toast.LENGTH_SHORT).show();
                    }
                    for (QueryDocumentSnapshot documentSnapshot : value) {
                        SoldOutAnimal soldOutAnimal = documentSnapshot.toObject(SoldOutAnimal.class);
                        Log.d("TAG", "getSoldOutAnimalDetailsForSeller: " +soldOutAnimal);
                        soldOutAnimalList.add(soldOutAnimal);
                    }
                    sellerSoldOutAnimalAdapter.notifyDataSetChanged();
                    dialog.dismiss();


                });
    }

    private void setupRecyclerView() {
        dialog = new Dialog(this);
        DialogUtils.initLoadingDialog(dialog);
        sellerSoldOutAnimalAdapter = new SellerSoldOutAnimalAdapter(this, soldOutAnimalList ,getIntent);
        recyclerView = binding.rvSoldOut;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sellerSoldOutAnimalAdapter);
    }
}