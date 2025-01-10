package com.code.e_qurbani.activities;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.ThirdParty.ThirdParty;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityRegisterBinding;
import com.code.e_qurbani.firebase.FirebaseRegister;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.firebase.entity.User;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;
import com.code.e_qurbani.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private Dialog dgLoading;
    private String[] getUser = new String[]{Constant.SELLER, Constant.BUYER,Constant.BUTCHER,Constant.TRANSPORTER, Constant.THIRD_PARTY};
    private boolean isRoleSelected = false;
    private String getCollectionName = null;
    private FirebaseRegister firebaseRegister;

    private ThirdParty thirdParty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /**
         * initializing firebase Register object
         */
        firebaseRegister = new FirebaseRegister();
        /**
         * initializing spinning Dialogue
         */
        dgLoading = new Dialog(this);
        DialogUtils.initLoadingDialog(dgLoading);
        /**
         * inserting data into select role view
         */
        handleDropdownMenu();
        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });

        /**
         *
         */
        binding.btnRegister.setOnClickListener(view -> {

            if (validateRegisterCredentials()) {
                dgLoading.show();
                Log.d("TAG", "GETROLEFETROLE: "+getCollectionName);
                if((Objects.equals(getCollectionName, Constant.BUYER) ||  Objects.equals(getCollectionName, Constant.SELLER))){
                    Log.d("Talha", "Calling Others: ");

                    double latitude = 0;
                    double longitude = 0;

                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(binding.tvlocation.getText().toString(), 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            Address location = addresses.get(0);
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            Log.d("Geocoder", "Latitude: " + latitude + ", Longitude: " + longitude);
                            // Use the latitude and longitude as needed
                        } else {
                            Log.d("Geocoder", "No address found.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Geocoder", "Unable to get Latitude and Longitude from the address.");
                    }



                    User user = new User(binding.tvFullName.getText().toString(),
                            binding.tvEmail.getText().toString(), binding.tvPassword.getText().toString(),
                            getCollectionName, longitude, latitude, binding.tvlocation.getText().toString());
                    firebaseRegister.createUserAccount(user, this, dgLoading);
                }
                else if (Objects.equals(getCollectionName, Constant.BUTCHER )|| Objects.equals(getCollectionName, Constant.TRANSPORTER )){
                    Log.d("Talha", "Calling Butcher: ");

                    double latitude = 0;
                    double longitude = 0;

                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(binding.tvlocation.getText().toString(), 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            Address location = addresses.get(0);
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            Log.d("Geocoder", "Latitude: " + latitude + ", Longitude: " + longitude);
                            // Use the latitude and longitude as needed
                        } else {
                            Log.d("Geocoder", "No address found.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Geocoder", "Unable to get Latitude and Longitude from the address.");
                    }






                    Butcher butcher = new Butcher(binding.tvFullName.getText().toString(),
                            binding.tvEmail.getText().toString(),binding.tvlocation.getText().toString(),binding.tvContactNumber.getText().toString(), binding.tvPassword.getText().toString(),
                            getCollectionName, longitude, latitude);
                    Log.d("Talha", "onCreate: " + butcher );
                    firebaseRegister.createUserAccount(butcher, this, dgLoading);
                }
                else
                {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

                    // Get the current user
                    FirebaseUser currentUser = mAuth.getCurrentUser();

                    String email = null;

                    if (currentUser != null) {
                        // User is signed in

                         email = currentUser.getEmail();
                        // Perform actions with user data
                    }

                    double latitude = 0;
                    double longitude = 0;

                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(binding.tvlocation.getText().toString(), 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            Address location = addresses.get(0);
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            Log.d("Geocoder", "Latitude: " + latitude + ", Longitude: " + longitude);
                            // Use the latitude and longitude as needed
                        } else {
                            Log.d("Geocoder", "No address found.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Geocoder", "Unable to get Latitude and Longitude from the address.");
                    }

                   ThirdParty thirdParty = new ThirdParty(binding.tvFullName.getText().toString(), binding.tvEmail.getText().toString(), binding.tvPassword.getText().toString(), binding.tvContactNumber.getText().toString(),binding.tvlocation.getText().toString(), binding.tvCNIC.getText().toString(), getCollectionName, email, latitude, longitude);
                    firebaseRegister.createUserAccountForThirdParty(thirdParty,this,dgLoading);
                }

            }

        });
    }

    private void getLatLongFromAddress(String address) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Log.d("Geocoder", "Latitude: " + latitude + ", Longitude: " + longitude);
                // Use the latitude and longitude as needed
            } else {
                Log.d("Geocoder", "No address found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Geocoder", "Unable to get Latitude and Longitude from the address.");
        }
    }

    private Boolean validateRegisterCredentials() {
        Log.d("TAG", "validateRegisterCredentials: ");
        Log.d("TAG", "validateRegisterCredentials: Name " + binding.tvFullName.getText().toString());
        if (Utils.validateUserName(binding.tvFullName.getText().toString())) {
            setHelperText(binding.lFullName, binding.tvFullName, Constant.INVALID_USERNAME);
            return false;
        }
        if (!isRoleSelected) {
            setHelperText(binding.lSelectRole, binding.selectRole, Constant.INVALID_ROLE);

            return false;
        }

        if (Utils.validateEmail(binding.tvEmail.getText().toString())) {
            setHelperText(binding.lemail, binding.tvEmail, Constant.INVALID_EMAIL);
            return false;
        }

        if (binding.tvlocation.getText().toString().length() < 15 && getCollectionName == Constant.BUTCHER) {
            setHelperText(binding.llocation, binding.tvlocation, Constant.INVALID_ADDRESS);
            return false;
        }

        if (!Utils.validatePhoneNumber(binding.tvContactNumber.getText().toString()) && getCollectionName == Constant.BUTCHER) {
            setHelperText(binding.lcontact, binding.tvContactNumber, Constant.INVALID_NUMBER);
            return false;
        }

        if (Utils.validateEmail(binding.tvEmail.getText().toString())) {
            setHelperText(binding.lemail, binding.tvEmail, Constant.INVALID_EMAIL);
            return false;
        }
        if (Utils.validatePassword(binding.tvPassword.getText().toString())) {
            setHelperText(binding.lPassword, binding.tvPassword, Constant.INVALID_PASSWORD);

            return false;
        }
        if (!binding.tvPassword.getText().toString().equals(binding.tvConfirmPassword.getText().toString())) {

            setHelperText(binding.lConfirmPassword, binding.tvConfirmPassword, Constant.INVALID_CONFIRM_PASSWORD);
            return false;
        }


        return true;
    }

    private void setHelperText(TextInputLayout layout, TextInputEditText helper, String message) {
        layout.setHelperText(message);
        helper.requestFocus();
    }

    private void setHelperText(TextInputLayout layout, AutoCompleteTextView helper, String message) {
        layout.setHelperText(message);
        helper.requestFocus();
    }


    private void handleDropdownMenu() {
        ArrayAdapter roleAdapter = new ArrayAdapter(this, R.layout.marketing_list, getUser);
        binding.selectRole.setAdapter(roleAdapter);
        ((AutoCompleteTextView) binding.lSelectRole.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                getCollectionName = roleAdapter.getItem(position).toString();
                isRoleSelected = true;
                if(getCollectionName.equals(Constant.BUTCHER) || getCollectionName == Constant.TRANSPORTER){
                    binding.lcontact.setVisibility(View.VISIBLE);
                    binding.llocation.setVisibility(View.VISIBLE);

                    binding.lCNIC.setVisibility(View.GONE);
                    binding.lCNIC.setVisibility(View.GONE);


                }else if (getCollectionName.equals(Constant.THIRD_PARTY)){
                    binding.lCNIC.setVisibility(View.VISIBLE);
                    binding.lCNIC.setVisibility(View.VISIBLE);
                    binding.lcontact.setVisibility(View.VISIBLE);
                    binding.llocation.setVisibility(View.VISIBLE);
                }
                else
                {
                    binding.lcontact.setVisibility(View.GONE);
                    binding.llocation.setVisibility(View.VISIBLE);

                    binding.lCNIC.setVisibility(View.GONE);
                    binding.lCNIC.setVisibility(View.GONE);
                }
            }
        });
    }
}