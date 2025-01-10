package com.code.e_qurbani.firebase;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.code.e_qurbani.activities.ThirdParty.ThirdParty;
import com.code.e_qurbani.activities.ThirdParty.ThirdPartyAdapter;
import com.code.e_qurbani.activities.ThirdParty.ThirdPartyOfferAdapter;
import com.code.e_qurbani.activities.ThirdParty.ThirdPartyProfile;
import com.code.e_qurbani.activities.ThirdParty.ThirdPartyProposal;
import com.code.e_qurbani.activities.butcher.HireButcher;
import com.code.e_qurbani.activities.buyer.ButcherProfileActivity;
import com.code.e_qurbani.activities.buyer.BuyerDashboard;
import com.code.e_qurbani.activities.buyer.OrderDetailAdapterForThirdParty;
import com.code.e_qurbani.activities.buyer.OrderTrackerAdapterThirdParty;
import com.code.e_qurbani.activities.transporter.HireTransporter;
import com.code.e_qurbani.adapter.ButcherHireAdapter;
import com.code.e_qurbani.adapter.ButcherOfferAdapter;
import com.code.e_qurbani.adapter.OrderDetailAdapter;
import com.code.e_qurbani.adapter.OrderTrackerAdapter;
import com.code.e_qurbani.adapter.TransporterHireAdapter;
import com.code.e_qurbani.adapter.TransporterOfferAdapter;
import com.code.e_qurbani.chat.ButcherScreenForChat;
import com.code.e_qurbani.chat.TransporterScreenForChat;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.firebase.entity.ButcherProposal;
import com.code.e_qurbani.firebase.entity.TransporterProposal;
import com.code.e_qurbani.utils.ButcherInfoByDistance;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.LocationHandler;
import com.code.e_qurbani.utils.TransporterInfoByDistance;
import com.code.e_qurbani.utils.UserInfoByDistance;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FirebaseButcherSource {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    FirebaseUser currentUser = auth.getCurrentUser();


    public void getListOFTransporter(ArrayList<Butcher> butcherArrayList, Context context, Dialog loadingDialog, TransporterHireAdapter butcherHireAdapter, ArrayList<TransporterInfoByDistance> transporterInfoByDistanceArrayList, String screen) {
        butcherArrayList.clear();
        db.collection(Constant.TRANSPORTER).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            assert value != null;
            for (QueryDocumentSnapshot snapshot : value) {
                Butcher butcher = snapshot.toObject(Butcher.class);
                butcher.setDocReference(db.collection(Constant.TRANSPORTER).document(snapshot.getId()).getPath().toString());
                butcherArrayList.add(butcher);
                Log.d("TAG", "getListOfSeller: " + butcher);

                LocationHandler locationHandler = new LocationHandler(context);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (locationHandler.locationPermissionChecker()) {
                        locationHandler.getLastLocation(new LocationHandler.LocationCallback() {
                            @Override
                            public void onLocationReceived(double latitude, double longitude) {
                                double distanceFromUser = haversineFormula(latitude, longitude, butcher.getLatitude(), butcher.getLongitude());
                                transporterInfoByDistanceArrayList.add(new TransporterInfoByDistance(butcher, distanceFromUser));



                                if (screen.equals("Chat")) {
                                    ((TransporterScreenForChat) context).runOnUiThread(() -> butcherHireAdapter.notifyDataSetChanged());
                                } else
                                {
                                    ((HireTransporter) context).runOnUiThread(() -> butcherHireAdapter.notifyDataSetChanged());
                                }
                            }
                        });
                    } else {
                        locationHandler.requestLocationPermission();
                    }
                }
            }
            loadingDialog.dismiss();
            butcherHireAdapter.notifyDataSetChanged();

        }));

    }

    public void getListOFButcher(ArrayList<Butcher> butcherList, Context context, Dialog dialog, ButcherHireAdapter butcherAdapter, ArrayList<ButcherInfoByDistance> butcherInfoByDistanceArrayList, String screen) {
        butcherList.clear();
        db.collection(Constant.BUTCHER).addSnapshotListener(((value, error) -> {
            if (error != null) {
                dialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if (value!=null) {
                butcherList.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                    Butcher butcher = snapshot.toObject(Butcher.class);
                    butcher.setDocReference(db.collection(Constant.BUTCHER).document(snapshot.getId()).getPath().toString());
                    butcherList.add(butcher);

                    LocationHandler locationHandler = new LocationHandler(context);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (locationHandler.locationPermissionChecker()) {
                            locationHandler.getLastLocation(new LocationHandler.LocationCallback() {
                                @Override
                                public void onLocationReceived(double latitude, double longitude) {
                                    double distanceFromUser = haversineFormula(latitude, longitude, butcher.getLatitude(), butcher.getLongitude());
                                    butcherInfoByDistanceArrayList.add(new ButcherInfoByDistance(butcher, distanceFromUser));


                                    if (screen.equals("Hire")) {
                                        ((HireButcher) context).runOnUiThread(() -> butcherAdapter.notifyDataSetChanged());
                                    } else {
                                        ((ButcherScreenForChat) context).runOnUiThread(() -> butcherAdapter.notifyDataSetChanged());
                                    }


                                    // Update adapter on the main thread

                                }
                            });
                        } else {
                            locationHandler.requestLocationPermission();
                        }
                    }

                }
            }

            Log.d("TAG", "getListOfButcherInfoByDistance: " + butcherInfoByDistanceArrayList + butcherList.size());

            dialog.dismiss();



//            butcherAdapter.notifyDataSetChanged();

        }));
    }



    public void getListOfThirdParty(ArrayList<ThirdParty> thirdPartyList, Context context, Dialog dialog, ThirdPartyAdapter thirdPartyAdapter, ArrayList<UserInfoByDistance> thirdArrayListWithDistance) {
        thirdPartyList.clear();
        thirdArrayListWithDistance.clear();
        db.collection(Constant.THIRD_PARTY).addSnapshotListener((value, error) -> {
            if (error != null) {
                dialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (value != null) {
                for (QueryDocumentSnapshot snapshot : value) {
                    ThirdParty thirdParty = snapshot.toObject(ThirdParty.class);
                    thirdParty.setDocReference(db.collection(Constant.THIRD_PARTY).document(snapshot.getId()).getPath().toString());
                    thirdPartyList.add(thirdParty);

                    LocationHandler locationHandler = new LocationHandler(context);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (locationHandler.locationPermissionChecker()) {
                            locationHandler.getLastLocation(new LocationHandler.LocationCallback() {
                                @Override
                                public void onLocationReceived(double latitude, double longitude) {
                                    double distanceFromUser = haversineFormula(latitude, longitude, thirdParty.getLatitude(), thirdParty.getLongitude());
                                    thirdArrayListWithDistance.add(new UserInfoByDistance(thirdParty, distanceFromUser));

                                    // Update adapter on the main thread
                                    ((ThirdPartyProfile) context).runOnUiThread(() -> thirdPartyAdapter.notifyDataSetChanged());
                                }
                            });
                        } else {
                            locationHandler.requestLocationPermission();
                        }
                    }
                }

                Toast.makeText(context, String.valueOf(thirdArrayListWithDistance.size()), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public void getOrderDetailForBuyer(ArrayList<ButcherProposal> butcherProposalsArray, Context context, Dialog loadingDialog, OrderDetailAdapter orderDetailAdapter) {
        butcherProposalsArray.clear();

        FirebaseUser currentUser = auth.getCurrentUser();
        String uid = currentUser.getUid();
        Toast.makeText(context,uid, Toast.LENGTH_SHORT).show();
        db.collection(Constant.BUYER).document(currentUser.getUid().toString()).collection(Constant.PROPOSAL).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if (value != null) {
                butcherProposalsArray.clear(); // Ensure list is cleared again in case of live updates
                for (QueryDocumentSnapshot snapshot : value) {
                    ButcherProposal butcher = snapshot.toObject(ButcherProposal.class);
                    butcherProposalsArray.add(butcher);
                    Log.d("TAG", "getOrderDetailForBuyer: " + butcherProposalsArray.size());
                }
            }

            loadingDialog.dismiss();
            orderDetailAdapter.notifyDataSetChanged();

        }));
    }

    public void getOrderDetailForThirdPartyB (ArrayList<ThirdPartyProposal> thirdPartyProposalArrayList, Context context, Dialog loadingDialog, OrderDetailAdapterForThirdParty orderDetailAdapter)
    {
        thirdPartyProposalArrayList.clear();
        db.collection(Constant.BUYER).document(currentUser.getUid().toString()).collection(Constant.PROPOSALTHIRDPARTY).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if(value!=null) {
                thirdPartyProposalArrayList.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                    ThirdPartyProposal thirdParty = snapshot.toObject(ThirdPartyProposal.class);
                    thirdPartyProposalArrayList.add(thirdParty);
                    Log.d("TAG", "getListOfSellerFORBUTCHER: " + thirdParty);
                }
            }
            loadingDialog.dismiss();
            orderDetailAdapter.notifyDataSetChanged();

        }));

    }


    public void getOrderDetailForTransporter(ArrayList<ButcherProposal> butcherProposalsArray, Context context, Dialog loadingDialog, OrderDetailAdapter orderDetailAdapter) {
        butcherProposalsArray.clear();
        db.collection(Constant.BUYER).document(currentUser.getUid().toString()).collection(Constant.PROPOSALTRANSPORTER).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if (value != null) {
                butcherProposalsArray.clear(); // Ensure list is cleared again in case of live updates
                for (QueryDocumentSnapshot snapshot : value) {
                    ButcherProposal butcher = snapshot.toObject(ButcherProposal.class);
                    butcherProposalsArray.add(butcher);
                    Log.d("TAG", "getOrderDetailForTransporter: " + butcher);
                }
            }
            loadingDialog.dismiss();
            orderDetailAdapter.notifyDataSetChanged();

        }));
    }

    public void getOrderDetailForButcher(ArrayList<ButcherProposal> butcherProposalsArray, Context context, Dialog loadingDialog, ButcherOfferAdapter orderDetailAdapter) {
        butcherProposalsArray.clear();
        db.collection(Constant.BUTCHER).document(currentUser.getUid().toString()).collection(Constant.PROPOSAL).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            if (value != null) {
                butcherProposalsArray.clear(); // Ensure list is cleared again in case of live updates
                for (QueryDocumentSnapshot snapshot : value) {
                    ButcherProposal butcher = snapshot.toObject(ButcherProposal.class);
                    butcherProposalsArray.add(butcher);
                    Log.d("TAG", "getOrderDetailForButcher: " + butcher);
                }
            }
            loadingDialog.dismiss();
            orderDetailAdapter.notifyDataSetChanged();

        }));
    }

    public void getOrderDetailForThirdParty (ArrayList<ThirdPartyProposal> thirdPartyProposalArrayList, Context context, Dialog loadingDialog, ThirdPartyOfferAdapter orderDetailAdapter)
    {
        thirdPartyProposalArrayList.clear();
        db.collection(Constant.THIRD_PARTY).document(currentUser.getUid().toString()).collection(Constant.PROPOSAL).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            assert value != null;
            for (QueryDocumentSnapshot snapshot : value) {
                ThirdPartyProposal thirdParty = snapshot.toObject(ThirdPartyProposal.class);
                thirdPartyProposalArrayList.add(thirdParty);
                Log.d("TAG", "getListOfSellerFORBUTCHER: " + thirdParty);
            }
            loadingDialog.dismiss();
            orderDetailAdapter.notifyDataSetChanged();

        }));

    }





    public void getOrderTrackerDetailForBuyer(ArrayList<ButcherProposal> butcherProposalsArray, Context context, Dialog loadingDialog, OrderTrackerAdapter orderDetailAdapter) {
        butcherProposalsArray.clear();
        Toast.makeText(context, String.valueOf(butcherProposalsArray.size()), Toast.LENGTH_SHORT).show();
        db.collection(Constant.BUTCHER).document(auth.getUid().toString()).collection(Constant.ProposalRecord).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            assert value != null;
            for (QueryDocumentSnapshot snapshot : value) {
                ButcherProposal butcher = snapshot.toObject(ButcherProposal.class);
                butcherProposalsArray.add(butcher);

            }
            loadingDialog.dismiss();
            orderDetailAdapter.notifyDataSetChanged();

        }));

    }

    public void getOrderTrackerDetailFoTransporter(ArrayList<ButcherProposal> butcherProposalsArray, Context context, Dialog loadingDialog, OrderTrackerAdapter orderDetailAdapter) {
        butcherProposalsArray.clear();
        db.collection(Constant.TRANSPORTER).document(auth.getUid().toString()).collection(Constant.ProposalRecord).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            assert value != null;
            for (QueryDocumentSnapshot snapshot : value) {
                ButcherProposal butcher = snapshot.toObject(ButcherProposal.class);
                butcherProposalsArray.add(butcher);
                Log.d("TAG", "transporter " + butcher);
            }
            loadingDialog.dismiss();
            orderDetailAdapter.notifyDataSetChanged();

        }));

    }


    public void getOrderTrackerDetailForThirdParty(ArrayList<ThirdPartyProposal> butcherProposalsArray, Context context, Dialog loadingDialog, OrderTrackerAdapterThirdParty orderDetailAdapter) {
        butcherProposalsArray.clear();
//        Toast.makeText(context, "control transfered", Toast.LENGTH_SHORT).show();
        db.collection(Constant.THIRD_PARTY).document(auth.getUid().toString()).collection(Constant.ProposalRecord).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            assert value != null;
            for (QueryDocumentSnapshot snapshot : value) {
                ThirdPartyProposal butcher = snapshot.toObject(ThirdPartyProposal.class);
                butcherProposalsArray.add(butcher);
                Log.d("TAG", "getListOfSellerFORBUTCHER: " + butcher);
            }
            loadingDialog.dismiss();
            orderDetailAdapter.notifyDataSetChanged();

        }));

    }





    public void getListOFSeller(ArrayList<Butcher> butcherArrayList, Context context, Dialog loadingDialog, TransporterHireAdapter butcherHireAdapter) {
        butcherArrayList.clear();
        db.collection(Constant.SELLER).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            assert value != null;
            for (QueryDocumentSnapshot snapshot : value) {
                Butcher butcher = snapshot.toObject(Butcher.class);
                butcher.setDocReference(db.collection(Constant.TRANSPORTER).document(snapshot.getId()).getPath().toString());
                butcherArrayList.add(butcher);
                Log.d("TAG", "getListOfSeller: " + butcher);
            }
            loadingDialog.dismiss();
            butcherHireAdapter.notifyDataSetChanged();

        }));

    }

    public void getOrderDetailForTransporter(ArrayList<TransporterProposal> butcherProposalsArray, Context context, Dialog loadingDialog, TransporterOfferAdapter butcherOfferAdapter) {
        butcherProposalsArray.clear();
        db.collection(Constant.TRANSPORTER).document(auth.getUid().toString()).collection(Constant.PROPOSAL).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            assert value != null;
            for (QueryDocumentSnapshot snapshot : value) {
                TransporterProposal butcher = snapshot.toObject(TransporterProposal.class);
                butcherProposalsArray.add(butcher);
//                Toast.makeText(context, butcherProposalsArray.size(), Toast.LENGTH_SHORT).show();
//                Log.d("TAG", "getListOfSellerFORBUTCHER: " + butcher);
            }
            loadingDialog.dismiss();
            butcherOfferAdapter.notifyDataSetChanged();

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




}
