package com.code.e_qurbani.activities.buyer;

//import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.Table.map;
import static com.code.e_qurbani.utils.Constant.PROPOSAL;
import static com.code.e_qurbani.utils.Constant.docIncrement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.chat.ChatScreen;
import com.code.e_qurbani.databinding.ActivityButcherProfileBinding;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.firebase.entity.ButcherProposal;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.CustomSendButterProposalDialogue;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ButcherProfileActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private ActivityButcherProfileBinding binding;
    private Butcher butcher;

    private DatabaseReference reference;

    private DatabaseReference reference2;

    private FirebaseDatabase database;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    String email = null;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityButcherProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseApp.initializeApp(this);

        binding.btnChat.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatScreen.class).putExtra(Constant.BUTCHER, butcher));
        });

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });


        binding.btnCall.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.CALL_PHONE}, 1234);
            }
            else
            {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + butcher.getContactNumber()));
                this.startActivity(intent);
            }
        });







//        Log.e("checking reference",reference.getRef().toString());

        butcher = getIntent().getParcelableExtra(Constant.BUTCHER);
        binding.tvName.setText(butcher.getFullName());
        binding.tvLocation.setText(butcher.getFullAddress());
        binding.tvPhoneNumber.setText(butcher.getContactNumber());

        if (currentUser != null) {
            // User is signed in, currentUser will contain user information
            String uid = currentUser.getUid();
             email = currentUser.getEmail();
            // You can access other user information like display name, photo URL, etc.
        }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("requests");
//        reference2 = reference.child(encodeEmail(email)+"_"+encodeEmail(butcher.getEmail()));
//        reference = database.getReference("chats").child(encodeEmail(email)+"_"+encodeEmail(butcher.getEmail()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
        Date date= new Date();
        String day = simpleDateFormat.format(date);
        simpleDateFormat = new SimpleDateFormat("MMM");
        String month = simpleDateFormat.format(date);
        simpleDateFormat = new SimpleDateFormat("d");
        String dateOfMonth = simpleDateFormat.format(date);
        binding.doBid.setOnClickListener(view -> {
            CustomSendButterProposalDialogue customSendButterProposalDialogue = new CustomSendButterProposalDialogue(this, (name, animalType, animalWeight, amountDemandedString, address, number, paymentMethod) -> {
                ButcherProposal butcherProposal = new ButcherProposal(butcher.getFullName(),butcher.getFullAddress(),butcher.getDocReference(),animalWeight,String.valueOf(amountDemandedString),animalType,"Pending",butcher.getContactNumber(),day,dateOfMonth,month,butcher.getEmail(), String.valueOf(0), String.valueOf(docIncrement), "add review", false);
                ++docIncrement;
                String docNumber = String.valueOf(docIncrement);
                Log.d("TAG", "For Butcher butcherProposalForButcher: "+ butcherProposal);
                firebaseFirestore.collection(Constant.BUYER).document(auth.getUid().toString()).collection(PROPOSAL).document(docNumber).set(butcherProposal).addOnSuccessListener(unused -> {
                   String Reference = Constant.BUYER +"/" + auth.getUid().toString() +"/" + PROPOSAL +"/"+docNumber;
                    ButcherProposal butcherProposalForButcher = new ButcherProposal(name,address,Reference,animalWeight,String.valueOf(amountDemandedString),animalType,"Pending" ,number,day,dateOfMonth,month,butcher.getEmail(), String.valueOf(0),String.valueOf(docIncrement), "add review", false);
                    Log.d("TAG", "For Buyer butcherProposalForButcher: "+ butcherProposalForButcher);

                    firebaseFirestore.document(butcher.getDocReference()).collection(PROPOSAL).document(docNumber).set(butcherProposalForButcher).addOnSuccessListener(unused1 -> {


//                        Map<String, String> map1 = new HashMap<String, String>();

//                        map1.put("status","pending");
//                        map1.put("senderEmail",encodeEmail(email));
//                        map1.put("receiverEmail",encodeEmail(butcher.getEmail()));
//                        reference2.setValue(map1);

                        Toast.makeText(ButcherProfileActivity.this, "Proposal Sent Successful", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            });
            customSendButterProposalDialogue.show();
        });
    }

    private void addToRealTimeDatabase(DatabaseReference reference2)
    {
        this.reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                GenericTypeIndicator<Map<String, Object>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Object>>() {
                };

                Map<String, Object> map1 = snapshot.getValue(genericTypeIndicator);

                map1.put("status","pending");
                map1.put("senderEmail",encodeEmail(email));
                map1.put("receiverEmail",encodeEmail(butcher.getEmail()));
                reference2.setValue(map1);


//                if (map1 != null) {
//                    String status = (String) map1.get("status");
//                    if (map1.containsKey("count")) {
//                        if (!status.equals("online")) {
//                            String count = (String) map1.get("count");
//                            int val = Integer.parseInt(count);
//                            map.put("count", String.valueOf(++val));
//                            map.put("status", "offline");
//                            reference2.setValue(map);
//                            Log.d("VALUE", "YES" + val);
//                        } else {
//                            String count = (String) map1.get("count");
//                            int val = Integer.parseInt(count);
//                            map.put("count", String.valueOf(val++));
//                            map.put("status", "online");
//                            reference2.setValue(map);
//                            Log.d("VALUE", "No");
//                        }
//                    } else {
//                        map.put("count", String.valueOf(1));
//                        map.put("status", "offline");
//                        reference2.setValue(map);
//                        Log.d("VALUE", "No");
//                    }
//                } else {
//                    map.put("count", String.valueOf(1));
//                    map.put("status", "offline");
//                    reference2.setValue(map);
//                    Log.d("VALUE", "first time");
//                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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