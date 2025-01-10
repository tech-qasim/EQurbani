package com.code.e_qurbani.activities.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.code.e_qurbani.R;
import com.code.e_qurbani.adapter.SellerAnimalListAdapter;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityBuyGoatBinding;
import com.code.e_qurbani.firebase.FirebaseSellerAnimalSource;
import com.code.e_qurbani.firebase.entity.OnSaleAnimal;
import com.code.e_qurbani.firebase.entity.Seller;
import com.code.e_qurbani.utils.Constant;

import java.util.ArrayList;

public class BuyGoat extends AppCompatActivity {

    ActivityBuyGoatBinding binding;
    private Seller seller;
    SellerAnimalListAdapter sellerAnimalListAdapter;
    RecyclerView recyclerView;
    ArrayList<OnSaleAnimal> onSaleAnimalArrayList = new ArrayList<>();
    Dialog loadingDialog;
    FirebaseSellerAnimalSource firebaseSellerAnimalSource;
    String collectionName = Constant.GOAT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyGoatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        seller = getIntent().getParcelableExtra(Constant.SELLER);
        loadingDialog = new Dialog(this);
        Log.d("TAG", "onCreate: " + seller);
        firebaseSellerAnimalSource = new FirebaseSellerAnimalSource();
        setupRecyclerView();
        getForSaleAnimal();

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });

    }

    private void getForSaleAnimal() {
        firebaseSellerAnimalSource.getOnSaleAnimalList(this, onSaleAnimalArrayList, loadingDialog, sellerAnimalListAdapter, seller ,collectionName);
    }

    private void setupRecyclerView() {
        recyclerView = binding.cowBuyRecycler;
        sellerAnimalListAdapter = new SellerAnimalListAdapter(this, onSaleAnimalArrayList, loadingDialog, seller);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sellerAnimalListAdapter);
    }
}