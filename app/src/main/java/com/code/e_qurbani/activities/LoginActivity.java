package com.code.e_qurbani.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityLoginBinding;
import com.code.e_qurbani.firebase.FirebaseLogin;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;
import com.code.e_qurbani.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseLogin firebaseLogin;
    private String getIntentRole;
    private Dialog dgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseLogin = new FirebaseLogin();
        getIntentRole = getIntent().getStringExtra(Constant.GET_ROLE);
        dgLoading = new Dialog(this);
        DialogUtils.initLoadingDialog(dgLoading);
        binding.btnLogin.setOnClickListener(view -> {
            if (validateForm()) {
                LoginCustomer();
            }
        });

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });
    }

    private boolean validateForm() {
        if (Utils.validateEmail(binding.editEmail.getText().toString())) {
            setHelperText(binding.emailLayout, binding.editEmail, Constant.INVALID_EMAIL);
            return false;
        }
        if (Utils.validatePassword(binding.password.getText().toString())) {
            setHelperText(binding.passwordLayout, binding.password, Constant.INVALID_PASSWORD);

            return false;
        }
        return true;
    }

    private void setHelperText(TextInputLayout layout, TextInputEditText helper, String message) {
        layout.setHelperText(message);
        helper.requestFocus();
    }

    private void LoginCustomer() {
        switch (getIntentRole) {
            case Constant.SELLER: {
                firebaseLogin.loginWithEmailPassword(binding.editEmail.getText().toString(), binding.password.getText().toString(), getIntentRole, this,dgLoading);
                break;
            }
            case Constant.BUYER: {
                firebaseLogin.loginWithEmailPassword(binding.editEmail.getText().toString(), binding.password.getText().toString(), getIntentRole, this,dgLoading);
                break;

            }
            case Constant.BUTCHER: {
                firebaseLogin.loginWithEmailPassword(binding.editEmail.getText().toString(), binding.password.getText().toString(), getIntentRole, this,dgLoading);

                break;
            }
            case Constant.TRANSPORTER: {
                firebaseLogin.loginWithEmailPassword(binding.editEmail.getText().toString(), binding.password.getText().toString(), getIntentRole, this,dgLoading);

                break;

            }

            case Constant.THIRD_PARTY:
            {
                firebaseLogin.loginWithEmailPassword(binding.editEmail.getText().toString(), binding.password.getText().toString(), getIntentRole, this, dgLoading);
            }
        }
    }
}