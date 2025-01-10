package com.code.e_qurbani.activities.butcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.code.e_qurbani.R;
import com.code.e_qurbani.adapter.ButcherOfferAdapter;
import com.code.e_qurbani.adapter.OrderDetailAdapter;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityGetOrderBinding;
import com.code.e_qurbani.databinding.ActivityOrderSummaryDetailBinding;
import com.code.e_qurbani.firebase.FirebaseButcherSource;
import com.code.e_qurbani.firebase.entity.ButcherProposal;

import java.util.ArrayList;

public class GetOrderActivity extends AppCompatActivity {
    private ButcherOfferAdapter butcherOfferAdapter;


    private RecyclerView recyclerView;
    private ArrayList<ButcherProposal> butcherProposalsArray = new ArrayList<>();

    FirebaseButcherSource firebaseButcherSource;

    private ActivityGetOrderBinding binding;
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog = new Dialog(this);
        firebaseButcherSource = new FirebaseButcherSource();
        setupRecyclerView();
        getListOfButcher();

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });
    }
    private void getListOfButcher() {
        loadingDialog.show();
        firebaseButcherSource.getOrderDetailForButcher(butcherProposalsArray, this, loadingDialog, butcherOfferAdapter);

    }
    private void setupRecyclerView() {
        recyclerView = binding.orderRecyler;

        butcherOfferAdapter = new ButcherOfferAdapter(this, butcherProposalsArray, loadingDialog );
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(butcherOfferAdapter);
    }
}