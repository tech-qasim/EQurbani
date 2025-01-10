package com.code.e_qurbani.activities.seller;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityAnimalProfileBinding;
import com.code.e_qurbani.utils.Constant;

public class AnimalProfileActivity extends AppCompatActivity {
    ActivityAnimalProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnimalProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /**
         * Profile Click Listener
         */
        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });
        binding.cowProfile.setOnClickListener(view -> {
            startActivity(new Intent(this, AnimalListActivity.class).putExtra(Constant.GET_ROLE, Constant.COW));
        });
        binding.goatProfile.setOnClickListener(view -> {
            startActivity(new Intent(this, AnimalListActivity.class).putExtra(Constant.GET_ROLE, Constant.GOAT));
        });
        binding.sheepProfile.setOnClickListener(view -> {
            startActivity(new Intent(this, AnimalListActivity.class).putExtra(Constant.GET_ROLE, Constant.SHEEP));
        });
        binding.camelProfile.setOnClickListener(view -> {
            startActivity(new Intent(this, AnimalListActivity.class).putExtra(Constant.GET_ROLE, Constant.CAMEL));
        });

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });

    }
}