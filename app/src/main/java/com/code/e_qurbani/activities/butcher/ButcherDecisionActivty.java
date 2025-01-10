package com.code.e_qurbani.activities.butcher;

import static com.code.e_qurbani.utils.Constant.PROPOSAL;
import static com.code.e_qurbani.utils.Constant.ProposalRecord;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.code.e_qurbani.activities.ThirdParty.ThirdPartyProposal;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityButcherDecisionActivtyBinding;
import com.code.e_qurbani.firebase.entity.ButcherProposal;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ButcherDecisionActivty extends AppCompatActivity {
    private ActivityButcherDecisionActivtyBinding binding;
    ButcherProposal butcherProposal;
    FirebaseAuth auth;

    private DatabaseReference reference;
    FirebaseFirestore firebaseFirestore;
    Dialog loadingDialog;

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityButcherDecisionActivtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("requests");
        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        butcherProposal = getIntent().getParcelableExtra("butcherProposalData");
        binding.tvName.setText(butcherProposal.getBuyerName());
        binding.tvLocation.setText(butcherProposal.getBuyerAddress());
        binding.tvPrice.setText(butcherProposal.getPrice());
        binding.tvCow.setText(butcherProposal.getAnimalType());
        binding.tvPhoneNumber.setText(butcherProposal.getPhoneNumber());
        calculateAverageRating();
        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });
        Toast.makeText(this, butcherProposal.getStarRating(), Toast.LENGTH_SHORT).show();
        Log.d("TAG", "onCreateasdasdasda: " + butcherProposal);
        binding.btnAccpet.setOnClickListener(view -> {
            loadingDialog.show();
            firebaseFirestore.document(butcherProposal.getBuyerDocRef()).update("proposalStatus", "Accepted").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    butcherProposal.setProposalStatus("Accepted");
                    firebaseFirestore.collection(Constant.BUTCHER).document(auth.getUid().toString()).collection(ProposalRecord).document().set(butcherProposal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            loadingDialog.dismiss();
                            Toast.makeText(ButcherDecisionActivty.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                            String documentNumber = butcherProposal.getBuyerDocRef().split("/")[3];
                            Log.d("TAG", "onCreate: " +documentNumber);
                            firebaseFirestore.collection(Constant.BUTCHER).document(auth.getUid().toString()).collection(PROPOSAL).document(documentNumber).delete();
                            startActivity(new Intent(ButcherDecisionActivty.this, ProposalRecordTracker.class));
                        }
                    }).addOnFailureListener(e -> {
                        loadingDialog.dismiss();
                        Toast.makeText(ButcherDecisionActivty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingDialog.dismiss();
                    Toast.makeText(ButcherDecisionActivty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });

        binding.btnReject.setOnClickListener(view -> {
            loadingDialog.show();
            firebaseFirestore.document(butcherProposal.getBuyerDocRef()).update("proposalStatus", "Rejected").addOnSuccessListener(unused -> {
            butcherProposal.setProposalStatus("Rejected");
            firebaseFirestore.collection(Constant.BUTCHER).document(auth.getUid().toString()).collection(ProposalRecord).document().set(butcherProposal).addOnSuccessListener(unused1 -> {
                loadingDialog.dismiss();
                Toast.makeText(ButcherDecisionActivty.this, "Success", Toast.LENGTH_SHORT).show();
                finish();
                String documentNumber = butcherProposal.getBuyerDocRef().split("/")[3];
                Log.d("TAG", "onCreate: " +documentNumber);
                firebaseFirestore.collection(Constant.TRANSPORTER).document(auth.getUid().toString()).collection(PROPOSAL).document(documentNumber).delete();
                startActivity(new Intent(ButcherDecisionActivty.this, ProposalRecordTracker.class));

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingDialog.dismiss();
                    Toast.makeText(ButcherDecisionActivty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingDialog.dismiss();
                Toast.makeText(ButcherDecisionActivty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });});
    }


    public void calculateAverageRating() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();

        db.collection(Constant.BUTCHER)
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
                                                binding.tvAverageStarRating.setText(String.format("%.2f", averageRating));
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


    private void addAndChangeInRealTimeDatabase ()
    {

    }
}