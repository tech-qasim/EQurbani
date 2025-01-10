package com.code.e_qurbani.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;

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

import java.util.ArrayList;

public class SellerScreenForChat extends AppCompatActivity {
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
        getListOfSellers();
    }
    private void getListOfSellers() {
        loadingDialog.show();
        firebaseButcherSource.getListOFSeller(butcherArrayList, this, loadingDialog, transporterHireAdapter);

    }
    private void setupRecyclerView() {
        recyclerView = binding.rvSellerList;

        transporterHireAdapter = new TransporterHireAdapter(this, "Seller", butcherArrayList, loadingDialog, transporterInfoByDistanceArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(transporterHireAdapter);
    }
}