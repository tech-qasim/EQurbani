package com.code.e_qurbani.activities.butcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.code.e_qurbani.adapter.ButcherHireAdapter;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityHireButcherBinding;
import com.code.e_qurbani.firebase.FirebaseButcherSource;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.utils.ButcherInfoByDistance;
import com.code.e_qurbani.utils.DialogUtils;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class HireButcher extends AppCompatActivity {
    private ButcherHireAdapter butcherHireAdapter;
    private ActivityHireButcherBinding binding;

    private RecyclerView recyclerView;
    private ArrayList<Butcher> butcherArrayList = new ArrayList<>();

    private ArrayList<ButcherInfoByDistance> butcherArrayListWithDistance = new ArrayList<>();
    FirebaseButcherSource firebaseButcherSource;
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHireButcherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseButcherSource= new FirebaseButcherSource();
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        setupRecyclerView();
        getListOfButcher();

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });
        binding.discreteSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                butcherHireAdapter.filter(String.valueOf(value));
            }
        });

        binding.etEnterLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                butcherHireAdapter.filterByName(charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                butcherHireAdapter.filterByName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }
    private void getListOfButcher() {
        loadingDialog.show();
        firebaseButcherSource.getListOFButcher(butcherArrayList, this, loadingDialog, butcherHireAdapter, butcherArrayListWithDistance, "Hire");

    }
    private void setupRecyclerView() {
        recyclerView = binding.rvSellerList;

        butcherHireAdapter = new ButcherHireAdapter(this, "Chat Scree", butcherArrayList, loadingDialog , butcherArrayListWithDistance);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(butcherHireAdapter);
    }
}