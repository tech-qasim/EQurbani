package com.code.e_qurbani.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.code.e_qurbani.R;
import com.code.e_qurbani.databinding.ActivitySelectOptionScreenBinding;

public class SelectOptionScreen extends AppCompatActivity {

    private ActivitySelectOptionScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectOptionScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.selectButcher.setOnClickListener(view -> {
            startActivity(new Intent(this, ButcherScreenForChat.class));
        });

        binding.selectTransporter.setOnClickListener(view -> {
            startActivity(new Intent(this, TransporterScreenForChat.class));
        });


        binding.selectSeller.setOnClickListener(view -> {
            startActivity(new Intent(this, SellerScreenForChat.class));
        });

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });




    }
}