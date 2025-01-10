package com.code.e_qurbani.utils;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;

import com.code.e_qurbani.databinding.ActivityDoBidDialogueBinding;
import com.code.e_qurbani.databinding.ActivityProposalButcherBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class CustomSendButterProposalDialogue  extends AppCompatDialog {
    ActivityProposalButcherBinding binding;
    Context context;
    SendButcherProposal dialogueListener;
    Double amountDemanded;

    private DatabaseReference reference;

    private FirebaseDatabase database;
    String name, address,animaltype ,animalWeight , number, payment = "Cash On Delivery";

    public CustomSendButterProposalDialogue(Context context, SendButcherProposal sendButcherProposal) {
        super(context);
        this.context = context;
        this.dialogueListener = sendButcherProposal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProposalButcherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("requests");

        binding.sendProposal.setOnClickListener(v -> {







            if (binding.tvName.getText().toString().length() < 6) {
                binding.lName.setHelperText("Enter Valid Name");
                binding.tvName.requestFocus();
                Toast.makeText(context, "Enter Valid Name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (binding.tvanimalType.getText().toString().length() < 3) {
                binding.lanimalType.setHelperText("Enter Animal Type");
                binding.tvanimalType.requestFocus();
                Toast.makeText(context, "Enter Animal Type", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Utils.validateWeight(Objects.requireNonNull(binding.tvanimalWeight.getText()).toString())) {
                binding.lanimalWeight.setHelperText("Enter Valid Amount");
                binding.tvanimalWeight.requestFocus();
                Toast.makeText(context, "Enter Valid Amount", Toast.LENGTH_SHORT).show();
                return;
            }
            name = binding.tvName.getText().toString();
            animaltype = binding.tvanimalType.getText().toString();
            animalWeight = binding.tvanimalWeight.getText().toString();

            if (!Utils.validateAmount(Objects.requireNonNull(binding.tvPrice.getText()).toString())) {
                binding.lName.setHelperText("Enter Valid Amount");
                binding.tvName.requestFocus();
                Toast.makeText(context, "Enter Valid Amount", Toast.LENGTH_SHORT).show();
                return;
            }
            amountDemanded = Double.parseDouble(binding.tvPrice.getText().toString());
            if (binding.tvAddress.getText().length() < 20) {
                binding.lAddress.setHelperText("Enter Complete Address");
                binding.tvAddress.requestFocus();
                Toast.makeText(context, "Enter Complete Address", Toast.LENGTH_SHORT).show();
                return;
            }
            address = binding.tvAddress.getText().toString();
            if (!Utils.validatePhoneNumber(binding.tvContactNumber.getText().toString())) {
                binding.lContactNo.setHelperText("Enter Valid Number");
                binding.tvContactNumber.requestFocus();
                Toast.makeText(context, "Enter Valid Number", Toast.LENGTH_SHORT).show();
                return;
            }
            number = binding.tvContactNumber.getText().toString();
            dialogueListener.sendProposal(name, animaltype,animalWeight,amountDemanded, address, number, payment);
            dismiss();
        });
    }
}
