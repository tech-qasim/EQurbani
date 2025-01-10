package com.code.e_qurbani.firebase;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.code.e_qurbani.activities.ThirdParty.ThirdParty;
import com.code.e_qurbani.activities.butcher.ButcherDashboard;
import com.code.e_qurbani.activities.buyer.BuyerDashboard;
import com.code.e_qurbani.activities.seller.SellerDashboardActivity;
import com.code.e_qurbani.activities.transporter.TransporterDashboard;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.firebase.entity.User;
import com.code.e_qurbani.utils.Constant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class FirebaseRegister {


    public void createUserAccount(User user, Context context, Dialog dialog) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("TAG", "createUserAccount: "+user.getEmail());
        Log.d("TAG", "createUserAccount: "+user.getPassword());
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnSuccessListener(authResult -> {
            db.collection(user.getSelectedRole()).document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            dialog.dismiss();
                            Toast.makeText(context, "Verify Your Email For Login", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
//            if (user.getSelectedRole() == Constant.SELLER) {
//                dialog.dismiss();
//                context.startActivity(new Intent(context, SellerDashboardActivity.class));
//            }else if(user.getSelectedRole() == Constant.BUYER){
//                context.startActivity(new Intent(context, BuyerDashboard.class));
//            }
        }).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    public void createUserAccountForThirdParty(ThirdParty thirdParty, Context context, Dialog dialog) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("TAG", "createUserAccount: "+thirdParty.getEmail());
        Log.d("TAG", "createUserAccount: "+thirdParty.getPassword());
        auth.createUserWithEmailAndPassword(thirdParty.getEmail(), thirdParty.getPassword()).addOnSuccessListener(authResult -> {
            db.collection(thirdParty.getSelectedRole()).document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).set(thirdParty).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            dialog.dismiss();
                            Toast.makeText(context, "Verify Your Email For Login", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
//            if (user.getSelectedRole() == Constant.SELLER) {
//                dialog.dismiss();
//                context.startActivity(new Intent(context, SellerDashboardActivity.class));
//            }else if(user.getSelectedRole() == Constant.BUYER){
//                context.startActivity(new Intent(context, BuyerDashboard.class));
//            }
        }).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }



    public void createUserAccount(Butcher user, Context context, Dialog dialog) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("TAG", "Creating Butcher Account: "+user.getEmail());
        Log.d("TAG", "Butcher: "+user.getPassword());
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnSuccessListener(authResult -> {
            db.collection(user.getSelectedRole()).document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            dialog.dismiss();
                            Toast.makeText(context, "Verify Your Email For Login", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
//            if (Objects.equals(user.getSelectedRole(), Constant.BUTCHER)) {
//                dialog.dismiss();
//                context.startActivity(new Intent(context, ButcherDashboard.class));
//            } else if (Objects.equals(user.getSelectedRole(), Constant.TRANSPORTER)) {
//                dialog.dismiss();
//
//                context.startActivity(new Intent(context, TransporterDashboard.class));
//            }
            dialog.dismiss();
        }).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }
}
