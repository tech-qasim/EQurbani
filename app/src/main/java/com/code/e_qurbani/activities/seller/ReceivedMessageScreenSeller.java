package com.code.e_qurbani.activities.seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.Toast;

import com.code.e_qurbani.adapter.ReceivedMessageAdaper;
import com.code.e_qurbani.databinding.ActivityReceivedMessageAdapterBinding;
import com.code.e_qurbani.databinding.ActivityReceivedMessageScreenSellerBinding;
import com.code.e_qurbani.firebase.FirebaseButcherSource;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.firebase.entity.Seller;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class ReceivedMessageScreenSeller extends AppCompatActivity {





    private Dialog loadingDialog;

    ReceivedMessageAdapterSeller adapter;

    FirebaseButcherSource firebaseButcherSource;

    private ArrayList<String> usernames = new ArrayList<>();

    private ReceivedMessageAdaper butcherHireAdapter;

//    private RecyclerView recyclerView;

    private FirebaseDatabase database;


    private DatabaseReference reference;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private ArrayList<Seller> butcherArrayList = new ArrayList<>();

    String current;

   ActivityReceivedMessageScreenSellerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReceivedMessageScreenSellerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usernames.clear();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("chats").child("Information for unread messages");

        if (currentUser!=null)
        {
//            current = encodeEmail(currentUser.getEmail().toString());

            String uid = currentUser.getUid().toString();

            getUserData(uid);



//            current = "Amna Raheel";

        }

        buildRecyclerView();
        displayDataFromRealTimeDatabase();

//        Log.i("current user",current);

    }



    private void getUserData(String uid) {

         FirebaseAuth mAuth;
         FirebaseFirestore db;

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        // Access Firestore using the UID
        db.collection(Constant.SELLER).document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Handle the retrieved data
//                        String name = documentSnapshot.getString("name");

                        current = encodeEmail(documentSnapshot.getString("email"));
                        displayDataFromRealTimeDatabase();
                        // Do something with the data
                    } else {
                        Toast.makeText(this, "no such document found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                });
    }



    private void displayDataFromRealTimeDatabase ()
    {


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usernames.clear();
                if (snapshot.hasChildren())
                {
                    for (DataSnapshot childSnapshot : snapshot.getChildren())
                    {
                        GenericTypeIndicator<Map<String, Object>> genericTypeIndicator =
                                new GenericTypeIndicator<Map<String, Object>>() {
                                };

                        Map<String, Object> data = childSnapshot.getValue(genericTypeIndicator);





                        if (data != null && data.containsKey("receiverEmail") && data.get("receiverEmail") != null &&
                                data.get("receiverEmail").equals(current)) {
                            if (Integer.parseInt(data.get("count").toString()) > 0
                            ) {


                                ArrayList<String> users = new ArrayList<>();

                                for (String user : usernames)
                                {
                                    users.add(user);
                                }

                                if (!users.contains(data.get("senderEmail").toString()))
                                {
                                    usernames.add(data.get("senderEmail").toString());
                                }

                                adapter.notifyDataSetChanged();





                            }
                        }

                    }
                }
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

    private void buildRecyclerView ()
    {
        binding.rvSellerList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReceivedMessageAdapterSeller(this, usernames);
        binding.rvSellerList.setAdapter(adapter);
    }


}