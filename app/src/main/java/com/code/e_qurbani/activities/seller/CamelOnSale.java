package com.code.e_qurbani.activities.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.code.e_qurbani.R;
import com.code.e_qurbani.adapter.ForSaleAnimalSellerAdapter;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityCamelOnSaleBinding;
import com.code.e_qurbani.databinding.ActivityCowBullOnSaleBinding;
import com.code.e_qurbani.firebase.FirebaseSellerAnimalSource;
import com.code.e_qurbani.firebase.entity.OnSaleAnimal;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;

import java.util.ArrayList;

public class CamelOnSale extends AppCompatActivity {
    ActivityCamelOnSaleBinding binding;
    ForSaleAnimalSellerAdapter forSaleAnimalSellerAdapater;
    RecyclerView recyclerView;
    ArrayList<OnSaleAnimal> onSaleAnimalArrayList = new ArrayList<>();
    Dialog loadingDialog;
    String collectionName = Constant.CAMEL;
    FirebaseSellerAnimalSource firebaseSellerAnimalSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCamelOnSaleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        setupRecyclerView();
        getForSaleAnimal();

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });

    }

    private void getForSaleAnimal() {
        firebaseSellerAnimalSource = new FirebaseSellerAnimalSource();
        firebaseSellerAnimalSource.getOnSaleAnimalListForSeller(this, onSaleAnimalArrayList, loadingDialog, forSaleAnimalSellerAdapater, collectionName);
    }

    private void setupRecyclerView() {
        recyclerView = binding.rvCardCow;
        forSaleAnimalSellerAdapater = new ForSaleAnimalSellerAdapter(this, onSaleAnimalArrayList, loadingDialog);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(forSaleAnimalSellerAdapater);
    }
}