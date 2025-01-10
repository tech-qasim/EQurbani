package com.code.e_qurbani.activities.buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityBuyerAnimalDetailsBinding;
import com.code.e_qurbani.utils.Constant;

public class BuyerAnimalDetails extends AppCompatActivity {
    ActivityBuyerAnimalDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyerAnimalDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.crdCowBull.setOnClickListener(v -> {
            startActivity(new Intent(this, SellerActivity.class).putExtra(Constant.BUYER , Constant.COW));
        });
        binding.crdGoat.setOnClickListener(v -> {
            startActivity(new Intent(this, SellerActivity.class).putExtra(Constant.BUYER , Constant.GOAT));
        });
        binding.crdSheep.setOnClickListener(v -> {
            startActivity(new Intent(this, SellerActivity.class).putExtra(Constant.BUYER , Constant.SHEEP));
        });
        binding.crdCamel.setOnClickListener(v -> {
            startActivity(new Intent(this, SellerActivity.class).putExtra(Constant.BUYER , Constant.CAMEL));
        });

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });

    }
}