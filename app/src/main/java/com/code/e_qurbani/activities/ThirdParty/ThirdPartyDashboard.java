package com.code.e_qurbani.activities.ThirdParty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.code.e_qurbani.activities.GatewayActivity;
import com.code.e_qurbani.activities.butcher.ProposalRecordTracker;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.chat.ChatScreen;
import com.code.e_qurbani.chat.ReceivedMessageScreen;
import com.code.e_qurbani.databinding.ActivityThirdPartyBinding;
import com.code.e_qurbani.utils.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThirdPartyDashboard extends AppCompatActivity {

    private ActivityThirdPartyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThirdPartyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Check if user is authenticated
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


                        if (currentUser != null) {
                            // User is authenticated, update token in database
                            String userEmail = encodeEmail(currentUser.getEmail());

                            FirebaseDatabase.getInstance().getReference().child("user tokens").child(userEmail).child("token").setValue(token);
                            FirebaseDatabase.getInstance().getReference().child("user tokens").child(userEmail).child("last updated").setValue(String.valueOf(new SimpleDateFormat("hh:mm a").format(new Date())));
                        }
                    }
                });

//        binding.crdAnimalSafekeeping.setOnClickListener(view -> {
//            startActivity(new Intent(this, AnimalSafekeepingScreen.class));
//        });


//        binding.crdAddPlace.setOnClickListener(view -> {
//            startActivity(new Intent(this, ThirdPartyListActivity.class).putExtra(Constant.GET_ROLE, Constant.THIRD_PARTY));
//        });

        binding.crdLogOut.setOnClickListener(view -> {
            logout();
        });

        binding.crdOrders.setOnClickListener(view -> {
            startActivity(new Intent(this, GetOrderForThirdParty.class));
        });

        binding.crdOrderHistory.setOnClickListener(view -> {
            startActivity(new Intent(this, ProposalRecordTracker.class));
        });

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });

        binding.crdChat.setOnClickListener(view -> {
            startActivity(new Intent(this, ReceivedMessageScreenThirdParty.class));
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

        public void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, GatewayActivity.class));
        finish();
    }
}