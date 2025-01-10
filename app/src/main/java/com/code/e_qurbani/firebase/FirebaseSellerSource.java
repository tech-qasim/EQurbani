package com.code.e_qurbani.firebase;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.code.e_qurbani.activities.buyer.SellerActivity;
import com.code.e_qurbani.activities.transporter.HireTransporter;
import com.code.e_qurbani.adapter.SellerAdapter;
import com.code.e_qurbani.chat.TransporterScreenForChat;
import com.code.e_qurbani.firebase.entity.Seller;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.LocationHandler;
import com.code.e_qurbani.utils.SellerInfoByDistance;
import com.code.e_qurbani.utils.TransporterInfoByDistance;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FirebaseSellerSource {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getListOfSeller(ArrayList<Seller> sellersList, Context context, Dialog dialog, SellerAdapter sellerAdapter, ArrayList<SellerInfoByDistance> sellerInfoByDistanceArrayList) {
        sellersList.clear();
        db.collection(Constant.SELLER_FOR_SALE_COLLECTION).addSnapshotListener(((value, error) -> {
            if (error != null) {
                dialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            assert value != null;
            for (QueryDocumentSnapshot snapshot :
                    value) {
                Seller seller = snapshot.toObject(Seller.class);
                seller.setSellerReference(db.collection(Constant.SELLER_FOR_SALE_COLLECTION).document(snapshot.getId()).getPath().toString());
                sellersList.add(seller);

                LocationHandler locationHandler = new LocationHandler(context);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (locationHandler.locationPermissionChecker()) {
                        locationHandler.getLastLocation(new LocationHandler.LocationCallback() {
                            @Override
                            public void onLocationReceived(double latitude, double longitude) {
                                double distanceFromUser = haversineFormula(latitude, longitude, seller.getLatitude(), seller.getLongitude());
                                sellerInfoByDistanceArrayList.add(new SellerInfoByDistance(seller, distanceFromUser));
                                ((SellerActivity) context).runOnUiThread(() -> sellerAdapter.notifyDataSetChanged());


//                                if (screen.equals("Chat")) {
//                                    ((TransporterScreenForChat) context).runOnUiThread(() -> butcherHireAdapter.notifyDataSetChanged());
//                                } else
//                                {
//                                    ((HireTransporter) context).runOnUiThread(() -> butcherHireAdapter.notifyDataSetChanged());
//                                }
                            }
                        });
                    } else {
                        locationHandler.requestLocationPermission();
                    }
                }
            }


            dialog.dismiss();
            sellerAdapter.notifyDataSetChanged();

        }));
    }

    private double haversineFormula(double lat1, double lon1, double lat2, double lon2) {
        // Convert latitude and longitude from degrees to radians
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        System.out.println(lat1);
        System.out.println(lon1);
        System.out.println(lat2);
        System.out.println(lon2);


        // Calculate differences
        double dlat = Math.abs(lat2 - lat1);
        double dlon = Math.abs(lon2 - lon1);

        // Haversine formula
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate distance
        double distance = 6371.0 * c;

        return distance;
    }


    public void getListOfSellerForDairy(ArrayList<Seller> sellersList, Context context, Dialog dialog, SellerAdapter sellerAdapter) {
        db.collection(Constant.SELLER_FOR_SALE_COLLECTION).addSnapshotListener(((value, error) -> {
            sellersList.clear();
            if (error != null) {
                dialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            assert value != null;
            for (QueryDocumentSnapshot snapshot :
                    value) {
                Seller seller = snapshot.toObject(Seller.class);
                seller.setSellerReference(db.collection(Constant.SELLER_FOR_SALE_COLLECTION).document(snapshot.getId()).getPath().toString());
                sellersList.add(seller);
                Log.d("TAG", "getListOfSeller: " + seller);
            }
            dialog.dismiss();
            sellerAdapter.notifyDataSetChanged();
        }));
    }
}
