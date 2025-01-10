package com.code.e_qurbani.firebase;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.code.e_qurbani.activities.ThirdParty.ThirdPartyDashboard;
import com.code.e_qurbani.activities.butcher.ButcherDashboard;
import com.code.e_qurbani.activities.buyer.BuyerDashboard;
import com.code.e_qurbani.activities.seller.SellerDashboardActivity;
import com.code.e_qurbani.activities.transporter.TransporterDashboard;
import com.code.e_qurbani.utils.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseLogin {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean isExist = false;

    public void loginWithEmailPassword(String email, String password, String role, Context context, Dialog dialog) {
        dialog.show();

        Log.d("TAG", "loginWithEmailPassword: "+ email + password + role);
        db.collection(role).whereEqualTo(Constant.EMAIL, email).get().addOnCompleteListener(task -> {
            if (!task.getResult().isEmpty()) {
                auth.signInWithEmailAndPassword(email.trim(), password.trim()).addOnSuccessListener(authResult -> {
                    if(auth.getCurrentUser().isEmailVerified()){
                        switch (role) {
                            case Constant.SELLER:
                                dialog.dismiss();
                                context.startActivity(new Intent(context, SellerDashboardActivity.class));
                                break;
                            case Constant.BUYER:
                                dialog.dismiss();
                                context.startActivity(new Intent(context, BuyerDashboard.class));
                                break;

                            case Constant.BUTCHER:
                                dialog.dismiss();
                                context.startActivity(new Intent(context, ButcherDashboard.class));
                                break;

                            case Constant.TRANSPORTER:
                                dialog.dismiss();
                                context.startActivity(new Intent(context, TransporterDashboard.class));
                                break;

                            case Constant.THIRD_PARTY:
                                dialog.dismiss();
                                context.startActivity(new Intent(context, ThirdPartyDashboard.class));
                            default:
                                dialog.dismiss();
                                Toast.makeText(context, "Role Not Defined In Code", Toast.LENGTH_SHORT).show();

                        }
                    }else{
                        Toast.makeText(context, "UnVerified Email Address", Toast.LENGTH_SHORT).show();

                    }

                }).addOnFailureListener(e -> {
                    dialog.dismiss();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("TAG", e.getMessage() );

                });


            }   else{
                dialog.dismiss();
                Toast.makeText(context, Constant.INVALID_CREDENTAILS, Toast.LENGTH_SHORT).show();
            }

        });


    }


    }