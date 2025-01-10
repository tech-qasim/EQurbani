package com.code.e_qurbani.activities.ThirdParty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.code.e_qurbani.R;
import com.code.e_qurbani.adapter.ButcherOfferAdapter;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityGetOrderForThirdPartyBinding;
import com.code.e_qurbani.firebase.FirebaseButcherSource;
import com.code.e_qurbani.firebase.entity.ButcherProposal;

import java.util.ArrayList;

public class GetOrderForThirdParty extends AppCompatActivity {

    ActivityGetOrderForThirdPartyBinding binding;

    private ThirdPartyOfferAdapter thirdPartyOfferAdapter;

    private Dialog loadingDialog;

    RecyclerView recyclerView;

    FirebaseButcherSource firebaseButcherSource;

    private ArrayList<ThirdPartyProposal> thirdPartyProposalArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetOrderForThirdPartyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingDialog = new Dialog(this);
        firebaseButcherSource = new FirebaseButcherSource();
        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });


        setupRecyclerView();
        getListOfThirdParty();

    }


    private void getListOfThirdParty ()
    {
        loadingDialog.show();
        firebaseButcherSource.getOrderDetailForThirdParty(thirdPartyProposalArrayList, this, loadingDialog, thirdPartyOfferAdapter);
    }

    private void setupRecyclerView() {
        recyclerView = binding.orderRecyler;

        thirdPartyOfferAdapter = new ThirdPartyOfferAdapter(this, thirdPartyProposalArrayList, loadingDialog );
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(thirdPartyOfferAdapter);
    }

}