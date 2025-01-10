package com.code.e_qurbani.activities.butcher;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.code.e_qurbani.activities.ThirdParty.ThirdPartyProposal;
import com.code.e_qurbani.activities.buyer.OrderDetailAdapterForThirdParty;
import com.code.e_qurbani.activities.buyer.OrderTrackerAdapterThirdParty;
import com.code.e_qurbani.adapter.OrderTrackerAdapter;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityProposalRecordTrackerBinding;
import com.code.e_qurbani.firebase.FirebaseButcherSource;
import com.code.e_qurbani.firebase.entity.ButcherProposal;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;

import java.util.ArrayList;
import java.util.Objects;

public class ProposalRecordTracker extends AppCompatActivity {
    String getRole = "";
    FirebaseButcherSource firebaseButcherSource;
    ActivityProposalRecordTrackerBinding binding;
    private OrderTrackerAdapter orderDetailAdapter;

    private OrderTrackerAdapterThirdParty orderDetailAdapterForThirdParty;
    private RecyclerView recyclerView;
    private ArrayList<ButcherProposal> butcherProposalsArray = new ArrayList<>();


    private ArrayList<ThirdPartyProposal> thirdPartyProposalArrayList = new ArrayList<>();
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProposalRecordTrackerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog = new Dialog(this);
        getRole = getIntent().getStringExtra(Constant.GET_ROLE);
        DialogUtils.initLoadingDialog(loadingDialog);
        firebaseButcherSource = new FirebaseButcherSource();
        setupRecyclerView();
        if (Objects.equals(getRole, Constant.TRANSPORTER)) {

            getListForTransporter();
        } else if (Objects.equals(getRole, Constant.BUTCHER)){
//            Toast.makeText(this, "grab the attention here", Toast.LENGTH_SHORT).show();
            getListOfButcher();

        }
        else
        {
            getListOfThirdParty();
        }

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });
    }


    private void getListOfThirdParty ()
    {
        loadingDialog.show();
//        Toast.makeText(this, "control is going here thirdparty", Toast.LENGTH_SHORT).show();
        firebaseButcherSource.getOrderTrackerDetailForThirdParty(thirdPartyProposalArrayList, this, loadingDialog, orderDetailAdapterForThirdParty);
    }

    private void getListOfButcher() {
        loadingDialog.show();
//        Toast.makeText(this, "control is going here butcher", Toast.LENGTH_SHORT).show();
        firebaseButcherSource.getOrderTrackerDetailForBuyer(butcherProposalsArray, this, loadingDialog, orderDetailAdapter);

    }

    private void getListForTransporter() {
        loadingDialog.show();
//        Toast.makeText(this, "control is giong here transporter", Toast.LENGTH_SHORT).show();
        firebaseButcherSource.getOrderTrackerDetailFoTransporter(butcherProposalsArray, this, loadingDialog, orderDetailAdapter);

    }

    private void setupRecyclerView() {
        recyclerView = binding.rvSellerList;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (Objects.equals(getRole, Constant.BUTCHER) ) {
            orderDetailAdapter = new OrderTrackerAdapter(this, butcherProposalsArray, loadingDialog);

            recyclerView.setAdapter(orderDetailAdapter);
        }
        else if ( Objects.equals(getRole, Constant.TRANSPORTER)) {
            orderDetailAdapter = new OrderTrackerAdapter(this, butcherProposalsArray, loadingDialog);
            recyclerView.setAdapter(orderDetailAdapter);
        }
        else {
            orderDetailAdapterForThirdParty = new OrderTrackerAdapterThirdParty(this, thirdPartyProposalArrayList, loadingDialog);

            recyclerView.setAdapter(orderDetailAdapterForThirdParty);
        }

    }
}