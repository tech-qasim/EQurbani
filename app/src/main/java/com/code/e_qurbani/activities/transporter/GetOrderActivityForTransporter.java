package com.code.e_qurbani.activities.transporter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;

import com.code.e_qurbani.R;
import com.code.e_qurbani.adapter.ButcherOfferAdapter;
import com.code.e_qurbani.adapter.TransporterOfferAdapter;
import com.code.e_qurbani.databinding.ActivityGetOrderAcivityForTransporterBinding;
import com.code.e_qurbani.databinding.ActivityGetOrderBinding;
import com.code.e_qurbani.firebase.FirebaseButcherSource;
import com.code.e_qurbani.firebase.entity.ButcherProposal;
import com.code.e_qurbani.firebase.entity.TransporterProposal;

import java.util.ArrayList;

public class GetOrderActivityForTransporter extends AppCompatActivity {
 ActivityGetOrderAcivityForTransporterBinding binding;

    private TransporterOfferAdapter butcherOfferAdapter;
    private RecyclerView recyclerView;
    private ArrayList<TransporterProposal> butcherProposalsArray = new ArrayList<>();
    private Dialog loadingDialog;
    FirebaseButcherSource firebaseButcherSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetOrderAcivityForTransporterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog = new Dialog(this);
        firebaseButcherSource = new FirebaseButcherSource();
        setupRecyclerView();
        getListOfButcher();
    }

    private void getListOfButcher() {
        loadingDialog.show();
        firebaseButcherSource.getOrderDetailForTransporter(butcherProposalsArray, this, loadingDialog, butcherOfferAdapter);

    }
    private void setupRecyclerView() {
        recyclerView = binding.orderRecyler;

        butcherOfferAdapter = new TransporterOfferAdapter(this, butcherProposalsArray, loadingDialog );
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(butcherOfferAdapter);
    }
}