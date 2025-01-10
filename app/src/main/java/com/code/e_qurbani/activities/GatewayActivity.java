package com.code.e_qurbani.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityGatewayBinding;
import com.code.e_qurbani.utils.Constant;

public class GatewayActivity extends AppCompatActivity {
    ActivityGatewayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGatewayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /**
         * execute when you click listener
         */
        binding.btnSeller.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class).putExtra(Constant.GET_ROLE, Constant.SELLER));
        });


        binding.btnBuyer.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class).putExtra(Constant.GET_ROLE, Constant.BUYER));
        });

//        binding.btnChatBot.setOnClickListener(view -> {
//            startActivity(new Intent(this, ChatBotScreen.class));
//        });

        binding.btnThirdParty.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class).putExtra(Constant.GET_ROLE, Constant.THIRD_PARTY));
        });


        binding.btnButcher.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class).putExtra(Constant.GET_ROLE, Constant.BUTCHER));
        });


        binding.btnTransporter.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class).putExtra(Constant.GET_ROLE, Constant.TRANSPORTER));
        });

        binding.tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));

        });

        binding.fab.setOnClickListener(v -> {
            startActivity(new Intent(this, ChatBotScreen.class));

        });



    }
}