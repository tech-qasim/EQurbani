package com.code.e_qurbani.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.transporter.HireTransporter;
import com.code.e_qurbani.adapter.ButcherHireAdapter;
import com.code.e_qurbani.adapter.TransporterHireAdapter;
import com.code.e_qurbani.databinding.ActivityHireButcherBinding;
import com.code.e_qurbani.databinding.ActivityHireTransporterBinding;
import com.code.e_qurbani.firebase.FirebaseButcherSource;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.utils.DialogUtils;
import com.code.e_qurbani.utils.TransporterInfoByDistance;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class TransporterScreenForChat extends AppCompatActivity {
    ActivityHireTransporterBinding binding;
    private TransporterHireAdapter transporterHireAdapter;


    private RecyclerView recyclerView;
    private ArrayList<Butcher> butcherArrayList = new ArrayList<>();

    private ArrayList<TransporterInfoByDistance> transporterInfoByDistanceArrayList = new ArrayList<>();

    FirebaseButcherSource firebaseButcherSource;
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHireTransporterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseButcherSource= new FirebaseButcherSource();
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        setupRecyclerView();
        getListOfButcher();

        binding.discreteSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                transporterHireAdapter.filter(String.valueOf(value));
            }
        });

        binding.etEnterLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                transporterHireAdapter.filterByName(charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                transporterHireAdapter.filterByName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

    }
    private void getListOfButcher() {
        loadingDialog.show();
        firebaseButcherSource.getListOFTransporter(butcherArrayList, this, loadingDialog, transporterHireAdapter, transporterInfoByDistanceArrayList, "Chat");

    }
    private void setupRecyclerView() {
        recyclerView = binding.rvSellerList;

        transporterHireAdapter = new TransporterHireAdapter(this, "Chat Screen", butcherArrayList, loadingDialog, transporterInfoByDistanceArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(transporterHireAdapter);
    }
}