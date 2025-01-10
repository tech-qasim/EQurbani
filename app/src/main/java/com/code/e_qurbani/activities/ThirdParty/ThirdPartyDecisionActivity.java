package com.code.e_qurbani.activities.ThirdParty;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.code.e_qurbani.utils.Constant.PROPOSAL;
import static com.code.e_qurbani.utils.Constant.ProposalRecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.code.e_qurbani.activities.butcher.ProposalRecordTracker;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityThirdPartyDecisionBinding;
import com.code.e_qurbani.emailservice.SendEmail;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ThirdPartyDecisionActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference reference;

    ThirdPartyProposal thirdPartyProposal;

    FirebaseAuth auth;
    ActivityThirdPartyDecisionBinding binding;

    Dialog loadingDialog;

    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThirdPartyDecisionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("requests");
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        thirdPartyProposal = getIntent().getParcelableExtra("thirdPartyProposalData");
        binding.tvName.setText(thirdPartyProposal.getBuyerName());
        binding.tvLocation.setText("From Date:" + thirdPartyProposal.getPickupAddress() + "\n" + "Till Date:" + thirdPartyProposal.getDropAddress());
        binding.tvPrice.setText(thirdPartyProposal.getPrice());
        binding.tvCow.setText(thirdPartyProposal.getAnimalType());
        binding.tvPhoneNumber.setText(thirdPartyProposal.getPhoneNumber());

        calculateAverageRating();
        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });



        binding.btnAccpet.setOnClickListener(view -> {
            loadingDialog.show();
            Toast.makeText(this, thirdPartyProposal.getBuyerDocRef(), Toast.LENGTH_SHORT).show();
            firebaseFirestore.document(thirdPartyProposal.getBuyerDocRef()).update("proposalStatus", "Accepted").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    thirdPartyProposal.setProposalStatus("Accepted");
                    firebaseFirestore.collection(Constant.THIRD_PARTY).document(auth.getUid().toString()).collection(ProposalRecord).document().set(thirdPartyProposal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            loadingDialog.dismiss();
                            Toast.makeText(ThirdPartyDecisionActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                            String documentNumber = thirdPartyProposal.getBuyerDocRef().split("/")[3];
                            Log.d("TAG", "onCreate: " +documentNumber);
//                            getThirdPartyEmailandPassword(extractSubstring(thirdPartyProposal.getBuyerDocRef()), Constant.THIRD_PARTY+"/"+auth.getUid().toString());
                            firebaseFirestore.collection(Constant.THIRD_PARTY).document(auth.getUid().toString()).collection(PROPOSAL).document(documentNumber).delete();
                            startActivity(new Intent(ThirdPartyDecisionActivity.this, ProposalRecordTracker.class));

//                            getBuyerEmailAndPassword(extractSubstring(thirdPartyProposal.getBuyerDocRef()));
//                            Toast.makeText(ThirdPartyDecisionActivity.this,    extractSubstring(thirdPartyProposal.getBuyerDocRef()), Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(e -> {
                        loadingDialog.dismiss();
                        Toast.makeText(ThirdPartyDecisionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingDialog.dismiss();
                    Toast.makeText(ThirdPartyDecisionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


        binding.btnReject.setOnClickListener(view -> {
            loadingDialog.show();
            firebaseFirestore.document(thirdPartyProposal.getBuyerDocRef()).update("proposalStatus", "Rejected").addOnSuccessListener(unused -> {
                thirdPartyProposal.setProposalStatus("Rejected");
                firebaseFirestore.collection(Constant.THIRD_PARTY).document(auth.getUid().toString()).collection(ProposalRecord).document().set(thirdPartyProposal).addOnSuccessListener(unused1 -> {
                    loadingDialog.dismiss();
                    Toast.makeText(ThirdPartyDecisionActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                    String documentNumber = thirdPartyProposal.getBuyerDocRef().split("/")[3];
                    Log.d("TAG", "onCreate: " +documentNumber);



                    firebaseFirestore.collection(Constant.THIRD_PARTY).document(auth.getUid().toString()).collection(PROPOSAL).document(documentNumber).delete();
                    startActivity(new Intent(ThirdPartyDecisionActivity.this, ProposalRecordTracker.class));

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingDialog.dismiss();
                        Toast.makeText(ThirdPartyDecisionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingDialog.dismiss();
                    Toast.makeText(ThirdPartyDecisionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });});


    }



    public void calculateAverageRating() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();

        db.collection(Constant.THIRD_PARTY)
                .document(auth.getUid().toString())
                .collection("ProposalRecord")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        // Handle the error here
                        return;
                    }

                    if (value != null && !value.isEmpty()) {
                        final double[] totalRating = {0.0};
                        final int[] count = {0};

                        for (QueryDocumentSnapshot snapshot : value) {
                            ThirdPartyProposal thirdPartyProposal1 = snapshot.toObject(ThirdPartyProposal.class);
                            db.document(thirdPartyProposal1.getBuyerDocRef()).get().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();

                                    if (document.exists()) {
                                        String starRatingStr = document.getString("starRating");
                                        if (starRatingStr != null && !starRatingStr.isEmpty()) {
                                            double starRating = Double.parseDouble(starRatingStr);
                                            totalRating[0] += starRating;
                                            count[0]++;

                                            if (count[0] == value.size()) {
                                                double averageRating = totalRating[0] / count[0];
                                                // Use the average rating as needed
                                                binding.tvAverageRating.setText(String.valueOf(averageRating));
//                                                Toast.makeText(this, String.valueOf(averageRating), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        // Handle the case where there are no documents
                        System.out.println("No proposals found.");
                    }
                });
    }


//    public String extractSubstring(String input) {
//        // Find the index of the first slash
//        int firstSlashIndex = input.indexOf('/');
//
//        // Find the index of the second slash after the first one
//        int secondSlashIndex = input.indexOf('/', firstSlashIndex + 1);
//
//        // Extract the substring up to (but not including) the second slash
//        String result = input.substring(0, secondSlashIndex);
//
//        return result;
//    }


//    void getThirdPartyEmailandPassword (String thirdPartyString, String buyerString)
//    {
//        Toast.makeText(this, thirdPartyString, Toast.LENGTH_SHORT).show();
//        Log.e("checking the value of s",thirdPartyString );
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference docRef1 = db.document(thirdPartyString);
//        DocumentReference docRef2 = db.document(buyerString);
//
//        docRef1.get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            // Document exists in Firestore
//                            // Access data using documentSnapshot.getData()
//                            String senderEmail = documentSnapshot.getString("email");
//                            String senderPassword = documentSnapshot.getString("password");
//                            String cnic = documentSnapshot.getString("cnicNumber");
//
//
//
//
//
//                            docRef2.get()
//                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                        @Override
//                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                            if (documentSnapshot.exists()) {
//                                                // Document exists in Firestore
//                                                // Access data using documentSnapshot.getData()
//                                                String receiverEmail = documentSnapshot.getString("email");
//
//
//
//                                                sendEmail(senderEmail, receiverEmail, senderPassword, cnic);
//
//                                                Toast.makeText(ThirdPartyDecisionActivity.this, senderEmail + "\n" + receiverEmail, Toast.LENGTH_SHORT).show();
//                                            } else {
//                                                // Document does not exist
//                                                Log.d(TAG, "No such document");
//                                            }
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            // Handle errors
//                                            Log.w(TAG, "Error getting document", e);
//                                        }
//                                    });
//
//
////                            Toast.makeText(ThirdPartyDecisionActivity.this, email+password, Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Document does not exist
//                            Log.d(TAG, "No such document");
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Handle errors
//                        Log.w(TAG, "Error getting document", e);
//                    }
//                });
//    }

//    void sendEmail(String senderEmail, String receiverEmail, String senderPassword, String cnic)
//    {
//        Log.e("This is going to work","jakljdfaklsdfa");
//        SendEmail sendEmailTask = new SendEmail(this, cnic, senderEmail, receiverEmail, senderPassword);
//        sendEmailTask.execute();
//    }



//    void getBuyerEmailAndPassword (String s)
//    {
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference docRef = db.document(s);
//
//        docRef.get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            // Document exists in Firestore
//                            // Access data using documentSnapshot.getData()
//                            String email = documentSnapshot.getString("email");
//                            String password = documentSnapshot.getString("password");
////                            Toast.makeText(ThirdPartyDecisionActivity.this, email, Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Document does not exist
//                            Log.d(TAG, "No such document");
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Handle errors
//                        Log.w(TAG, "Error getting document", e);
//                    }
//                });
//    }

    }


//    void getPassword ()
//    {
//        CollectionReference buyerCollection = db.collection(Constant.BUYER);
//        buyerCollection.whereEqualTo("email",senderEmail).get()
//                .addOnSuccessListener(queryDocumentSnapshots -> {
//                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
//                    String senderPassword = documentSnapshot.getString("password");
//
//                    sendEmail(senderPassword);
//
//
//                });
//
//
//
//    }
//
//    void sendEmail(String password)
//    {
//        Log.e("This is going to work","jakljdfaklsdfa");
//        SendEmail sendEmailTask = new SendEmail(this, "Hello", senderEmail, receiverEmail, password);
//        sendEmailTask.execute();
//    }
