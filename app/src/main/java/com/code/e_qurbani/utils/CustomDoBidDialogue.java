package com.code.e_qurbani.utils;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;

import com.code.e_qurbani.databinding.ActivityDoBidDialogueBinding;

import java.util.Objects;

public class CustomDoBidDialogue extends AppCompatDialog {
    ActivityDoBidDialogueBinding binding;
    Context context;
    DoBidDialogueListener dialogueListener;
    Double amountDemanded;
    String name, address, number, payment = "Cash On Delivery";

    public CustomDoBidDialogue(Context context, DoBidDialogueListener putOnSaleDialogueListener) {
        super(context);
        this.context = context;
        this.dialogueListener = putOnSaleDialogueListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoBidDialogueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.doBid.setOnClickListener(v -> {
            if (binding.tvName.getText().toString().length() < 6) {
                binding.lName.setHelperText("Enter Valid Name");
                binding.tvName.requestFocus();
                Toast.makeText(context, "Enter Valid Name", Toast.LENGTH_SHORT).show();
                return;
            }
            name = binding.tvName.getText().toString();

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
            dialogueListener.doBidListener(name, amountDemanded, address, number, payment);
            dismiss();
        });
    }


}
