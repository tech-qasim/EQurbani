package com.code.e_qurbani.activities.buyer;

import static com.code.e_qurbani.utils.Constant.GET_ROLE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.GatewayActivity;
import com.code.e_qurbani.activities.MainActivity;
import com.code.e_qurbani.activities.ThirdParty.ThirdPartyProfile;
import com.code.e_qurbani.activities.butcher.HireButcher;
import com.code.e_qurbani.activities.seller.SoldAnimalDetails;
import com.code.e_qurbani.activities.transporter.HireTransporter;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.chat.SelectOptionScreen;
import com.code.e_qurbani.databinding.ActivityBuyerDashboardBinding;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.LocationHandler;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyerDashboard extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 2;
    ActivityBuyerDashboardBinding binding;

    private LocationRequest locationRequest;

    private LocationManager locationManager;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private LocationHandler locationHandler;
    private FusedLocationProviderClient fusedLocationClient;

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyerDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /**
         * Animal Logs
         */


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationHandler = new LocationHandler(this);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (locationHandler.locationPermissionChecker()) {
//                Toast.makeText(BuyerDashboard.this, "Permission granted to the user", Toast.LENGTH_SHORT).show();
//                locationHandler.getLastLocation();
//            } else {
//                locationHandler.requestLocationPermission();
//            }
//        } else {
//            locationHandler.getLastLocation();
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (locationHandler.locationPermissionChecker()) {
                Toast.makeText(BuyerDashboard.this, "Permission granted to the user", Toast.LENGTH_SHORT).show();

                locationHandler.getLastLocation(new LocationHandler.LocationCallback() {
                    @Override
                    public void onLocationReceived(double latitude, double longitude) {
                        BuyerDashboard.this.latitude = latitude;
                        BuyerDashboard.this.longitude = longitude;


                        Log.e("checking locatin",BuyerDashboard.this.latitude + "\n" +  BuyerDashboard.this.longitude);


                        Toast.makeText(BuyerDashboard.this, "Location Taken", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(BuyerDashboard.this, BuyerDashboard.this.latitude + "\n" +  BuyerDashboard.this.longitude , Toast.LENGTH_SHORT).show();


                    }
                });
            } else {
                locationHandler.requestLocationPermission();
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (locationPermissionChecker()) {
//                Toast.makeText(BuyerDashboard.this, "Permission granted to the user", Toast.LENGTH_SHORT).show();
            } else {
//                                Toast.makeText(SignUpAsLawyerScreen.this, "Permission not granted to the user", Toast.LENGTH_SHORT).show();
//                requestLocationPermission();
            }

//                            ActivityCompat.requestPermissions(SignUpAsLawyerScreen.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
//                            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
//                            Toast.makeText(SignUpAsLawyerScreen.this, "else statement", Toast.LENGTH_SHORT).show();
//            setupLocationManager();
        }

//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
//        } else {
//            getLastLocation();
//        }


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Check if user is authenticated
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


                        if (currentUser != null) {
                            // User is authenticated, update token in database
                            String userEmail = encodeEmail(currentUser.getEmail());

                            FirebaseDatabase.getInstance().getReference().child("user tokens").child(userEmail).child("token").setValue(token);
                            FirebaseDatabase.getInstance().getReference().child("user tokens").child(userEmail).child("last updated").setValue(String.valueOf(new SimpleDateFormat("hh:mm a").format(new Date())));
                        }
                    }
                });

        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });


        binding.crdAnimal.setOnClickListener(v -> {
            startActivity(new Intent(this, BuyerAnimalDetails.class));
        });


        binding.crdBuyerBought.setOnClickListener(v -> {
            startActivity(new Intent(this, SoldAnimalDetails.class).putExtra(GET_ROLE, Constant.BUYER));
        });

        binding.hirebutcher.setOnClickListener(v -> {
            startActivity(new Intent(this, HireButcher.class));

        });

        binding.thirdPartyRecord.setOnClickListener(view -> {
            startActivity(new Intent(this, OrderSummaryDetail.class).putExtra(GET_ROLE, Constant.THIRD_PARTY));
        });

        binding.safeKeepingCrd.setOnClickListener(v -> {
            startActivity(new Intent(this, ThirdPartyProfile.class));
        });

        binding.transporterCrd.setOnClickListener(v -> {
            startActivity(new Intent(this, HireTransporter.class));

        });

        binding.crdRequest.setOnClickListener(v -> {
            startActivity(new Intent(this, OrderSummaryDetail.class).putExtra(GET_ROLE, Constant.BUTCHER));

        });

        binding.crdTransporterRequest.setOnClickListener(v -> {
            startActivity(new Intent(this, OrderSummaryDetail.class).putExtra(GET_ROLE, Constant.TRANSPORTER));

        });

//        binding.crdChat.setOnClickListener(v -> {
//            startActivity(new Intent(this, SelectOptionScreen.class));
//        });


    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, GatewayActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void requestLocationPermission() {
        // Request the location permission from the user

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_CODE);
    }

    private boolean locationPermissionChecker() {
        return ContextCompat.checkSelfPermission(BuyerDashboard.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location location = task.getResult();
                            // Use the location object (latitude and longitude)
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            Toast.makeText(BuyerDashboard.this,
                                    "Latitude: " + latitude + ", Longitude: " + longitude,
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(BuyerDashboard.this,
                                    "Failed to get location.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this,
                        "Location permission denied.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private String encodeEmail(String email) {
        int atIndex = email.indexOf('@');


        String username = null;


        if (atIndex != -1) { // Check if "@" exists in the email
            username = email.substring(0, atIndex);
            System.out.println("Username: " + username);

        } else {
            System.out.println("Invalid email format.");
        }



        return username;

    }

    private void setupLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // Check if the location provider is enabled
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationClient.getCurrentLocation(locationRequest.QUALITY_HIGH_ACCURACY, cancellationTokenSource.getToken())
                    .addOnSuccessListener(BuyerDashboard.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                //                                    String city = getCityName(location.getLongitude(), location.getLatitude());
//
//                                    geoLocation = new GeoLocation(location.getLatitude(),location.getLongitude());
//
//                                    locationEditText.setText(city);

                                Toast.makeText(BuyerDashboard.this, location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();


                            }

                        }
                    });

//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 1, (LocationListener) locationListener);
        } else {
            // You can prompt the user to enable the GPS
            Toast.makeText(this, "Please enable GPS", Toast.LENGTH_SHORT).show();
        }
    }
}