package com.code.e_qurbani.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.code.e_qurbani.MainActivity2;
import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.butcher.ButcherDashboard;
import com.code.e_qurbani.activities.buyer.BuyerDashboard;
import com.code.e_qurbani.activities.seller.SellerDashboardActivity;
import com.code.e_qurbani.activities.transporter.TransporterDashboard;
import com.code.e_qurbani.utils.Constant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    GatewayActivity gatewayActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Show Screen For 2 seconds then show gateway Screen
         */

         
        if (auth.getUid() != null) {
             db.collection(Constant.SELLER).document(auth.getUid()).get().addOnSuccessListener(task -> {
                if (task.exists()) {

                    startActivity(new Intent(this, SellerDashboardActivity.class));

                } else {
                    db.collection(Constant.BUYER).document(auth.getUid()).get().addOnSuccessListener(taskI -> {
                         if (taskI.exists()) {
                            startActivity(new Intent(this, BuyerDashboard.class));
                        }else{
                             db.collection(Constant.BUTCHER).document(auth.getUid()).get().addOnSuccessListener(taskII -> {
                                 if (taskII.exists()) {
                                     startActivity(new Intent(this, ButcherDashboard.class));
                                 }else{
                                     db.collection(Constant.BUTCHER).document(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                         @Override
                                         public void onSuccess(DocumentSnapshot documentSnapshot) {
                                             startActivity(new Intent(MainActivity.this, TransporterDashboard.class));

                                         }
                                     });
                                 }
                             });
                         }
                    });
                }
            });


        } else {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, GatewayActivity.class));
                finish();
            }, 2000);
        }
    }
}