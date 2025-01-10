package com.code.e_qurbani.activities.seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.code.e_qurbani.R;
import com.code.e_qurbani.adapter.BidderAnimalAdapter;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivitySellAnimalToHighestBidderBinding;
import com.code.e_qurbani.firebase.FirebaseBidding;
import com.code.e_qurbani.firebase.entity.BidAnimal;
import com.code.e_qurbani.firebase.entity.OnSaleAnimal;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;

import java.util.ArrayList;

public class SellAnimalToHighestBidder extends AppCompatActivity {

    private OnSaleAnimal onSaleAnimal;
    ActivitySellAnimalToHighestBidderBinding binding;
    RecyclerView recyclerView;
    BidderAnimalAdapter bidderAnimalAdapter;
    ArrayList<BidAnimal> bidAnimalArrayList = new ArrayList<>();
    Dialog dialog;
    FirebaseBidding bidding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySellAnimalToHighestBidderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onSaleAnimal = getIntent().getParcelableExtra(Constant.ANIMAL);
        Log.d("Bidder", "onCreate: ");
        setupRecyclerView();
        getBidder();

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });


    }

    private void getBidder() {

        bidding = new FirebaseBidding();

        dialog.show();

        bidding.getAnimalBidderList(this, dialog, bidAnimalArrayList, onSaleAnimal, bidderAnimalAdapter);

    }


    private void setupRecyclerView() {
        dialog = new Dialog(this);
        DialogUtils.initLoadingDialog(dialog);
        bidderAnimalAdapter = new BidderAnimalAdapter(this, bidAnimalArrayList, onSaleAnimal);
        recyclerView = binding.rvBidder;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bidderAnimalAdapter);
    }
}