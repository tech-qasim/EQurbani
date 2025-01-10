package com.code.e_qurbani.activities.buyer;

import static com.code.e_qurbani.utils.Constant.PROPOSAL;
import static com.code.e_qurbani.utils.Constant.PROPOSALTRANSPORTER;
import static com.code.e_qurbani.utils.Constant.docIncrement;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.chat.ChatScreen;
import com.code.e_qurbani.databinding.ActivityTransporterProfileActiviyBinding;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.firebase.entity.TransporterProposal;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.CustomSendTransporterDialog;
import com.code.e_qurbani.utils.DialogUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransporterProfileActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ActivityTransporterProfileActiviyBinding binding;
    private Butcher butcher;

    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransporterProfileActiviyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new Dialog(this);
        DialogUtils.initLoadingDialog(dialog);
        butcher = getIntent().getParcelableExtra(Constant.BUTCHER);


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


        Toast.makeText(this, "this is working", Toast.LENGTH_SHORT).show();
        binding.tvName.setText(butcher.getFullName());
        binding.tvLocation.setText(butcher.getFullAddress());
        binding.tvPhoneNumber.setText(butcher.getContactNumber());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
        Date date = new Date();
        String day = simpleDateFormat.format(date);
        simpleDateFormat = new SimpleDateFormat("MMM");
        String month = simpleDateFormat.format(date);
        simpleDateFormat = new SimpleDateFormat("d");
        String dateOfMonth = simpleDateFormat.format(date);
        binding.doBid.setOnClickListener(view -> {
            dialog.show();
            CustomSendTransporterDialog customSendButterProposalDialogue = new CustomSendTransporterDialog(this, butcher, (name, animaltype, animalWeight, amountDemanded, pickupAddress, dropAddress, number, payment, butcherEmail) -> {
                String butcheremail = butcher.getEmail();
                TransporterProposal transporterProposal = new TransporterProposal(name, pickupAddress, dropAddress, butcher.getDocReference(), animalWeight, String.valueOf(amountDemanded), animaltype, "Pending", number, day, dateOfMonth, month, butcheremail, String.valueOf(0), String.valueOf(docIncrement), "add review",false);
                ++docIncrement;
                String docNumber = String.valueOf(docIncrement);
                Log.d("TAG", "For Butcher butcherProposalForButcher: " + transporterProposal);
                firebaseFirestore.collection(Constant.BUYER).document(auth.getUid().toString()).collection(PROPOSALTRANSPORTER).document(docNumber).set(transporterProposal).addOnSuccessListener(unused -> {
                    String Reference = Constant.BUYER + "/" + auth.getUid().toString() + "/" + PROPOSALTRANSPORTER + "/" + docNumber;
                    TransporterProposal transporterProposalForTransporter = new TransporterProposal(butcher.getFullName(), pickupAddress, dropAddress, Reference, animalWeight, String.valueOf(amountDemanded), animaltype, "Pending", number, day, dateOfMonth, month, butcherEmail,String.valueOf(0), String.valueOf(docIncrement), "add review",false);
                    Log.d("TAG", "For Buyer butcherProposalForButcher: " + transporterProposalForTransporter);
                    firebaseFirestore.document(butcher.getDocReference()).collection(PROPOSAL).document(docNumber).set(transporterProposalForTransporter).addOnSuccessListener(unused1 -> {
                        Toast.makeText(TransporterProfileActivity.this, "Proposal Sent Successful", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                        startActivity(new Intent(this ,BuyerDashboard.class));

                    }).addOnFailureListener(e -> {
                        dialog.dismiss();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }).addOnFailureListener(e -> {
                    dialog.dismiss();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            });
            customSendButterProposalDialogue.show();
        });
    }
}