package com.code.e_qurbani.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;

import com.code.e_qurbani.R;
import com.code.e_qurbani.adapter.ButcherHireAdapter;
import com.code.e_qurbani.databinding.ActivityButcherScreenForChatBinding;
import com.code.e_qurbani.databinding.ActivityHireButcherBinding;
import com.code.e_qurbani.firebase.FirebaseButcherSource;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.utils.ButcherInfoByDistance;
import com.code.e_qurbani.utils.DialogUtils;

import java.util.ArrayList;

public class ButcherScreenForChat extends AppCompatActivity {

    private ButcherHireAdapter butcherHireAdapter;
    private ActivityButcherScreenForChatBinding binding;

    private RecyclerView recyclerView;
    private ArrayList<Butcher> butcherArrayList = new ArrayList<>();

    FirebaseButcherSource firebaseButcherSource;

    private ArrayList<ButcherInfoByDistance> butcherInfoByDistanceArrayList = new ArrayList<>();
    private Dialog loadingDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityButcherScreenForChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseButcherSource= new FirebaseButcherSource();
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        setupRecyclerView();
        getListOfButcher();
    }

    private void getListOfButcher() {
        loadingDialog.show();
        firebaseButcherSource.getListOFButcher(butcherArrayList, this, loadingDialog, butcherHireAdapter, butcherInfoByDistanceArrayList, "Chat");

    }
    private void setupRecyclerView() {
        recyclerView = binding.rvButcherList;

        butcherHireAdapter = new ButcherHireAdapter(this, "Chat Screen", butcherArrayList, loadingDialog, butcherInfoByDistanceArrayList );
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(butcherHireAdapter);
    }
}