package com.code.e_qurbani.activities.ThirdParty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.code.e_qurbani.R;
import com.code.e_qurbani.databinding.ActivityAnimalListBinding;
import com.code.e_qurbani.databinding.ActivityThirdPartyListBinding;
import com.code.e_qurbani.firebase.FirebaseAnimalSource;
import com.code.e_qurbani.firebase.entity.Animal;
import com.code.e_qurbani.firebase.entity.Place;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;

import java.util.ArrayList;

public class ThirdPartyListActivity extends AppCompatActivity {

    ActivityThirdPartyListBinding binding;

    FirebaseAnimalSource firebaseAnimalSource;

    private RecyclerView recyclerView;

    private PlaceRecyclerAdapter placeRecyclerAdapter;

    private ArrayList<Place> thirdPartyArrayList = new ArrayList<>();


    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThirdPartyListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAnimalSource = new FirebaseAnimalSource();
        dialog = new Dialog(this);

        DialogUtils.initLoadingDialog(dialog);

        recyclerView = binding.animalListRecycler;


        setUpRecyclerView();
        getPlaceSource();





        binding.btnThirdParty.setOnClickListener(view -> {
            startActivity(new Intent(this, AddPlaceActivity.class).putExtra(Constant.GET_ROLE,Constant.THIRD_PARTY));

        });
    }


    private void getPlaceSource() {
        dialog.show();
        firebaseAnimalSource.getListOfThirdParty(this, thirdPartyArrayList,placeRecyclerAdapter,Constant.THIRD_PARTY,dialog);


    }

    private void setUpRecyclerView ()
    {
        placeRecyclerAdapter = new PlaceRecyclerAdapter(this, thirdPartyArrayList, dialog, Constant.THIRD_PARTY, binding.tvempty);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(placeRecyclerAdapter);
    }


}