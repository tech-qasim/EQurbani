package com.code.e_qurbani.activities.ThirdParty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.code.e_qurbani.R;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityAddThirdPartyScreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddThirdPartyScreen extends AppCompatActivity {
    private FirebaseFirestore db;
    private ActivityAddThirdPartyScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddThirdPartyScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        final FirebaseUser[] firebaseUser = {FirebaseAuth.getInstance().getCurrentUser()};

        binding.button2.setOnClickListener(view -> {
            if (firebaseUser[0] !=null)
            {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Location",binding.tvLocation.getText().toString());
                map.put("Charges",binding.tvCharges.getText().toString());
                map.put("Name",binding.tvName.getText().toString());

                firebaseUser[0] = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                FirebaseDatabase.getInstance().getReference().child("safe keepers").child(encodeEmail(firebaseUser[0].getEmail())).push().setValue(map);

            }
        });






    }

    private String encodeEmail(String email) {
        int atIndex = email.indexOf('@');


        String username = null;


        if (atIndex != -1) { // Check if "@" exists in the email
            username = email.substring(0, atIndex);
            System.out.println("Username: " + username);

        } else {
            System.out.println("Invalid email format.");
        }



        return username;

    }

}