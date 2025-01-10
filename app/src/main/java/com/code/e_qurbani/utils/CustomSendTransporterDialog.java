package com.code.e_qurbani.utils;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;

import com.code.e_qurbani.databinding.ActivityProposalButcherBinding;
import com.code.e_qurbani.databinding.ActivityProposalTransporterBinding;
import com.code.e_qurbani.firebase.entity.Butcher;

import java.util.Objects;

public class CustomSendTransporterDialog extends AppCompatDialog {

    ActivityProposalTransporterBinding binding;
    Context context;
    SendTransporterDialog dialogueListener;
    Double amountDemanded;


    String butcherEmail;

    Butcher butcher;
    String name, pickupAddress, dropAddress, animaltype, animalWeight, number, payment = "Cash On Delivery";

    public CustomSendTransporterDialog(Context context, Butcher butcher, SendTransporterDialog sendButcherProposal) {
        super(context);
        this.context = context;
        this.butcher = butcher;
        this.dialogueListener = sendButcherProposal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProposalTransporterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.doBid.setOnClickListener(v -> {
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

            if (binding.tvdropAddress.getText().length() < 20) {
                binding.ldropAddress.setHelperText("Enter Complete Address");
                binding.tvdropAddress.requestFocus();
                Toast.makeText(context, "Enter Complete Address", Toast.LENGTH_SHORT).show();
                return;
            }
            pickupAddress = binding.tvAddress.getText().toString();
            dropAddress = binding.tvdropAddress.getText().toString();
            if (!Utils.validatePhoneNumber(binding.tvContactNumber.getText().toString())) {
                binding.lContactNo.setHelperText("Enter Valid Number");
                binding.tvContactNumber.requestFocus();
                Toast.makeText(context, "Enter Valid Number", Toast.LENGTH_SHORT).show();
                return;
            }
            number = binding.tvContactNumber.getText().toString();
            dialogueListener.sendProposal(name, animaltype, animalWeight, amountDemanded, pickupAddress,dropAddress, number, payment, butcher.getEmail());
            dismiss();
        });
    }
}
