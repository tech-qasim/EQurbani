package com.code.e_qurbani.activities.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;

import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.ThirdParty.ThirdParty;
import com.code.e_qurbani.activities.ThirdParty.ThirdPartyProposal;
import com.code.e_qurbani.adapter.ButcherHireAdapter;
import com.code.e_qurbani.adapter.OrderDetailAdapter;
//import com.code.e_qurbani.adapter.OrderDetailAdapterForThirdParty;
import com.code.e_qurbani.databinding.ActivityHireButcherBinding;
import com.code.e_qurbani.databinding.ActivityOrderSummaryDetailBinding;
import com.code.e_qurbani.firebase.FirebaseButcherSource;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.firebase.entity.ButcherProposal;
import com.code.e_qurbani.utils.Constant;

import java.util.ArrayList;
import java.util.Objects;

public class OrderSummaryDetail extends AppCompatActivity {
    private OrderDetailAdapter orderDetailAdapter;

//    private OrderDetailAdapterForThirdParty orderDetailAdapterForThirdParty;

    private OrderDetailAdapterForThirdParty orderDetailAdapterForThirdParty;


    private RecyclerView recyclerView;
    private ArrayList<ButcherProposal> butcherProposalsArray = new ArrayList<>();

    private ArrayList<ThirdPartyProposal> thirdPartyProposalsArray = new ArrayList<>();
    String getRole ="";
    FirebaseButcherSource firebaseButcherSource;
    private Dialog loadingDialog;
    private ActivityOrderSummaryDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderSummaryDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog = new Dialog(this);
        firebaseButcherSource = new FirebaseButcherSource();
        getRole = getIntent().getStringExtra(Constant.GET_ROLE);


        setupRecyclerView();
        if (Objects.equals(getRole, Constant.TRANSPORTER)){
            getListOfTransporter();
        }else if (Objects.equals(getRole, Constant.BUTCHER)){
            getListOfButcher();

        }
        else {
            getListOfThirdParty();
        }
    }

    private void getListOfButcher() {
        loadingDialog.show();
        butcherProposalsArray.clear();
        firebaseButcherSource.getOrderDetailForBuyer(butcherProposalsArray, this, loadingDialog, orderDetailAdapter);

    }
    private void getListOfTransporter() {
        loadingDialog.show();
        firebaseButcherSource.getOrderDetailForTransporter(butcherProposalsArray, this, loadingDialog, orderDetailAdapter);

    }

    private void getListOfThirdParty () {
        loadingDialog.show();
        firebaseButcherSource.getOrderDetailForThirdPartyB(thirdPartyProposalsArray, this, loadingDialog, orderDetailAdapterForThirdParty);
    }



    private void setupRecyclerView() {
        recyclerView = binding.rvSellerList;
//        orderDetailAdapter = new OrderDetailAdapter(this, butcherProposalsArray, loadingDialog, "transporter");
        if (Objects.equals(getRole, Constant.TRANSPORTER))
        {
            orderDetailAdapter = new OrderDetailAdapter(this, butcherProposalsArray, loadingDialog, "transporter");
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(orderDetailAdapter);
        }
        else if (Objects.equals(getRole, Constant.BUTCHER))
        {
            orderDetailAdapter = new OrderDetailAdapter(this, butcherProposalsArray, loadingDialog, "butcher" );
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(orderDetailAdapter);
        }
        else {
            orderDetailAdapterForThirdParty = new OrderDetailAdapterForThirdParty(this, thirdPartyProposalsArray, loadingDialog, "thirdparty" );
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(orderDetailAdapterForThirdParty);
        }


//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(orderDetailAdapter);
    }
}