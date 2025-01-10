package com.code.e_qurbani.activities.ThirdParty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.code.e_qurbani.R;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityThirdPartyProfileBinding;
import com.code.e_qurbani.firebase.FirebaseButcherSource;
import com.code.e_qurbani.utils.DialogUtils;
import com.code.e_qurbani.utils.UserInfoByDistance;
import com.google.android.material.slider.Slider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThirdPartyProfile extends AppCompatActivity {

    ActivityThirdPartyProfileBinding binding;

    private ArrayList<ThirdParty> thirdPartyArrayList = new ArrayList<>();
    private ArrayList<UserInfoByDistance> thirdPartyArrayListWithDistance = new ArrayList<>();
    private ThirdPartyAdapter thirdPartyAdapter;
    private FirebaseButcherSource firebaseButcherSource;
    private Dialog loadingDialog;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThirdPartyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });
        firebaseButcherSource = new FirebaseButcherSource();
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        setupRecyclerView();
        getListOfThirdParty();
        binding.discreteSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                thirdPartyAdapter.filter(String.valueOf(value));
            }
        });

        binding.etEnterLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                thirdPartyAdapter.filterByName(charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                thirdPartyAdapter.filterByName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });


//        binding.btnSubmit.setOnClickListener(view -> {
//            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//            try {
//                List<Address> addresses = geocoder.getFromLocationName(binding.etEnterLocation.getText().toString(), 1);
//                if (addresses != null && !addresses.isEmpty()) {
//                    Address location = addresses.get(0);
//                    Double latitude = location.getLatitude();
//                    Double longitude = location.getLongitude();
//
//                    Toast.makeText(this, "latitude " + latitude + "\n" + "longitude" + longitude, Toast.LENGTH_SHORT).show();
//                    // Use the latitude and longitude as needed
//                } else {
//                    Log.d("Geocoder", "No address found.");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.e("Geocoder", "Unable to get Latitude and Longitude from the address.");
//            }
//        });





    }

    private void getListOfThirdParty() {
        loadingDialog.show();
        firebaseButcherSource.getListOfThirdParty(thirdPartyArrayList, this, loadingDialog, thirdPartyAdapter, thirdPartyArrayListWithDistance);
    }

    private void setupRecyclerView() {
        recyclerView = binding.rvSellerList;
        thirdPartyAdapter = new ThirdPartyAdapter(this, "Chat Scree", thirdPartyArrayList, loadingDialog, thirdPartyArrayListWithDistance);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(thirdPartyAdapter);
    }
}
