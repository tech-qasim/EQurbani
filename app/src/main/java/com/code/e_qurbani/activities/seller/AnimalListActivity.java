package com.code.e_qurbani.activities.seller;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code.e_qurbani.adapter.AnimalRecyclerAdapter;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityAnimalListBinding;
import com.code.e_qurbani.firebase.FirebaseAnimalSource;
import com.code.e_qurbani.firebase.entity.Animal;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;

import java.util.ArrayList;

public class AnimalListActivity extends AppCompatActivity {
    ActivityAnimalListBinding binding;
    private String getIntentRole = null;
    private RecyclerView recyclerView;
    private AnimalRecyclerAdapter animalRecyclerAdapter;
    private ArrayList<Animal> animalArrayList = new ArrayList<>();
    private Dialog dialog;
    FirebaseAnimalSource firebaseAnimalSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnimalListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAnimalSource = new FirebaseAnimalSource();

        getIntentRole = getIntent().getStringExtra(Constant.GET_ROLE);
        Toast.makeText(this, getIntentRole, Toast.LENGTH_SHORT).show();
        /**
         * initialize dialogue
         */
        dialog = new Dialog(this);
        DialogUtils.initLoadingDialog(dialog);
        /**
         * setup Recycler View

         */

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });
        recyclerView = binding.animalListRecycler;
        setupRecyclerView();
        getAnimalSource();
        /**
         * + symbol click listener
         */
        binding.btnAddAnimal.setOnClickListener(view -> {
            startActivity(new Intent(this, AddAnimalDetailActivity.class).putExtra(Constant.GET_ROLE, getIntentRole));
        });

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });


    }

    private void getAnimalSource() {
                              dialog.show();
        firebaseAnimalSource.getListOfAnimals(this,animalArrayList, animalRecyclerAdapter, getIntentRole,dialog);


    }



    private void setupRecyclerView() {
        animalRecyclerAdapter = new AnimalRecyclerAdapter(this, animalArrayList, dialog, getIntentRole, binding.tvempty);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(animalRecyclerAdapter);
    }
}