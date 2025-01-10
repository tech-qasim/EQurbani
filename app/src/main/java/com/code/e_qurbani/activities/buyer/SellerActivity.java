package com.code.e_qurbani.activities.buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.code.e_qurbani.R;
import com.code.e_qurbani.adapter.SellerAdapter;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivitySellerBinding;
import com.code.e_qurbani.firebase.FirebaseSellerSource;
import com.code.e_qurbani.firebase.entity.Seller;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;
import com.code.e_qurbani.utils.SellerInfoByDistance;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Objects;

public class SellerActivity extends AppCompatActivity {

    private ActivitySellerBinding binding;
    private SellerAdapter sellerAdapter;
    private String intentValue;
    private RecyclerView recyclerView;
    private ArrayList<Seller> sellerList = new ArrayList<>();



    private Dialog loadingDialog;
    private FirebaseSellerSource firebaseSellerSource;


    private ArrayList<SellerInfoByDistance> sellerInfoByDistanceArrayList = new ArrayList<>();
    private String BUYER ="" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intentValue = getIntent().getStringExtra(Constant.BUYER);
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        firebaseSellerSource = new FirebaseSellerSource();
        BUYER = getIntent().getStringExtra(Constant.BUYER);

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });

        setupRecyclerView();

            getListOfSeller();


        binding.discreteSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                sellerAdapter.filter(String.valueOf(value));
            }
        });

        binding.etEnterLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sellerAdapter.filterByName(charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sellerAdapter.filterByName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


            




        }






    private void getListOfSeller() {
        loadingDialog.show();
        firebaseSellerSource.getListOfSeller(sellerList, this, loadingDialog, sellerAdapter,sellerInfoByDistanceArrayList);

    }

    private void setupRecyclerView() {
        recyclerView = binding.rvSellerList;

        sellerAdapter = new SellerAdapter(this, sellerList, loadingDialog, intentValue , BUYER, sellerInfoByDistanceArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sellerAdapter);
    }
}