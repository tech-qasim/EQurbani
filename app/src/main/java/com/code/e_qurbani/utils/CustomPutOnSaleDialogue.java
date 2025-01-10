package com.code.e_qurbani.utils;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;

import com.code.e_qurbani.databinding.AnimalPriceDialogueBinding;

import java.util.Objects;

public class CustomPutOnSaleDialogue extends AppCompatDialog {
    AnimalPriceDialogueBinding binding;
    Context context;
    PutOnSaleDialogueListener dialogueListener;
    Double amountDemanded;
    String address;
    String number;
    String payment = "Cash On Delivery";

    public CustomPutOnSaleDialogue(Context context, PutOnSaleDialogueListener putOnSaleDialogueListener) {
        super(context);
        this.context = context;
        this.dialogueListener = putOnSaleDialogueListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AnimalPriceDialogueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnPutOnSale.setOnClickListener(v -> {
            if (!Utils.validateAmount(Objects.requireNonNull(binding.tvPrice.getText()).toString())) {
                binding.lPrice.setHelperText("Enter Valid Amount");
                binding.tvPrice.requestFocus();
                Toast.makeText(context, "Enter Valid Amount", Toast.LENGTH_SHORT).show();
                return;
            }
            amountDemanded = Double.parseDouble(binding.tvPrice.getText().toString());
            if (Objects.requireNonNull(binding.tvAddress.getText()).length() < 20) {
                binding.lAddress.setHelperText("Enter Complete Address");
                binding.tvAddress.requestFocus();
                Toast.makeText(context, "Enter Complete Address", Toast.LENGTH_SHORT).show();

                return;
            }
            address = binding.tvAddress.getText().toString();
            if (!Utils.validatePhoneNumber(Objects.requireNonNull(binding.tvContactNumber.getText()).toString())) {
                binding.lContactNo.setHelperText("Enter Valid Number");
                binding.tvContactNumber.requestFocus();
                Toast.makeText(context, "Enter Valid Number", Toast.LENGTH_SHORT).show();

                return;
            }
            number = binding.tvContactNumber.getText().toString();
            dialogueListener.putOnSaleListener(amountDemanded, address, number, payment);
            dismiss();
        });
    }
}