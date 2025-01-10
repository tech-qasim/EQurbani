package com.code.e_qurbani.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import com.code.e_qurbani.activities.ThirdParty.ThirdParty;
import com.code.e_qurbani.databinding.ActivityCustomSendThirdPartyDialogBinding;

import java.util.Calendar;
import java.util.Objects;

public class CustomSendThirdPartyDialog extends AppCompatDialog {

    Context context;
    ThirdParty thirdParty;
    SendThirdPartyDialog dialogueListener;
    Double amountDemanded;
    ActivityCustomSendThirdPartyDialogBinding binding;
    String name, pickupAddress, dropAddress, animaltype, animalWeight, number, payment = "Cash On Delivery";

    public CustomSendThirdPartyDialog(@NonNull Context context, ThirdParty butcher, SendThirdPartyDialog sendThirdPartyProposal) {
        super(context);
        this.context = context;
        this.thirdParty = butcher;
        this.dialogueListener = sendThirdPartyProposal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomSendThirdPartyDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvdropAddress.setOnClickListener(v -> showDatePickerDialog());

        binding.tvAddress.setOnClickListener(v -> showDatePickerDialog2());

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
            if (binding.tvAddress.getText().length() < 1) {
                binding.lAddress.setHelperText("Enter Complete Date");
                binding.tvAddress.requestFocus();
                Toast.makeText(context, "Enter Complete Date", Toast.LENGTH_SHORT).show();
                return;
            }

            if (binding.tvdropAddress.getText().length() < 1) {
                binding.ldropAddress.setHelperText("Enter Complete Date");
                binding.tvdropAddress.requestFocus();
                Toast.makeText(context, "Enter Complete Date", Toast.LENGTH_SHORT).show();
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
            dialogueListener.sendProposal(name, animaltype, animalWeight, amountDemanded, pickupAddress, dropAddress, number, payment, thirdParty.getEmail());
            dismiss();
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year1, monthOfYear, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
            binding.tvdropAddress.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showDatePickerDialog2() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year1, monthOfYear, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
            binding.tvAddress.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }
}
