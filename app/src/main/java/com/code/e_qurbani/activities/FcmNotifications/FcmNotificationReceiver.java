package com.code.e_qurbani.activities.FcmNotifications;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.media.session.MediaSession;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.code.e_qurbani.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FcmNotificationReceiver extends FirebaseMessagingService {

    FirebaseFirestore db;
    private DatabaseReference databaseReference;

    Random random = new Random();

    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = token;

        if (firebaseUser != null) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Token t = new Token(refreshToken);
            FirebaseDatabase.getInstance().getReference().child("user tokens").child(encodeEmail(firebaseUser.getEmail())).child("last updated").setValue(String.valueOf(new SimpleDateFormat("hh:mm a").format(new Date())));
            FirebaseDatabase.getInstance().getReference().child("user tokens").child(encodeEmail(firebaseUser.getEmail())).child("token").setValue(String.valueOf(token));
            reference.child(firebaseUser.getUid()).child("token").setValue(t);

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

    private void sendLocalNotification(RemoteMessage remoteMessage) {
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this, "Notification_Channel")
                .setSmallIcon(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_normal_background)
                .setContentTitle(remoteMessage.getData().get("for"))
                .setContentText(remoteMessage.getData().get("body"))
                .setColor(ContextCompat.getColor(this, R.color.white));
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(random.nextInt() + 1000, builder.build());

    }

    private void createNotificationChannel() {

        Log.d("NotificationChannel", "Creating notification channel");
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification";
            String description = "Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notification_Channel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        createNotificationChannel();

        String receiverEmail = message.getData().get("forUid");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

      String email = null;

        sendLocalNotification(message);











    }
}