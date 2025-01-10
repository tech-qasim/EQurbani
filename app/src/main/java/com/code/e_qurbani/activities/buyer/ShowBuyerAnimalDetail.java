package com.code.e_qurbani.activities.buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.GatewayActivity;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityBuyerDashboardBinding;
import com.code.e_qurbani.databinding.ActivityShowBuyerAnimalDetailBinding;
import com.code.e_qurbani.firebase.entity.OnSaleAnimal;
import com.code.e_qurbani.utils.Constant;
import com.google.firebase.auth.FirebaseAuth;

public class ShowBuyerAnimalDetail extends AppCompatActivity {
    ActivityShowBuyerAnimalDetailBinding binding;
    OnSaleAnimal onSaleAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowBuyerAnimalDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onSaleAnimal = getIntent().getParcelableExtra(Constant.ANIMAL);
        binding.tvTitle.setText(onSaleAnimal.getName());
        try {
            Glide.with(this).load(onSaleAnimal.getImageUri()).diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.ImSrc);
        } catch (Exception exception) {
            Toast.makeText(this, "Image Loading Failed", Toast.LENGTH_SHORT).show();
        }
        binding.tvDescAnimal.setText(onSaleAnimal.getDesc());

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });


        binding.tvGenderType.setText(onSaleAnimal.getGender());
        String weight = String.valueOf(onSaleAnimal.getWeight())   ;
        binding.tvWeight.setText(weight);
        binding.tvBreed.setText(onSaleAnimal.getBreed());
        binding.tvDob.setText(onSaleAnimal.getDateOfBirth());
        binding.tvPrice.setText(String.valueOf(onSaleAnimal.getForSaleAnimalPrice()));
    }
}