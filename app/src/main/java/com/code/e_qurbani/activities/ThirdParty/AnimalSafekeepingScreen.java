package com.code.e_qurbani.activities.ThirdParty;

import static com.code.e_qurbani.activities.FcmNotifications.NotificationTemplate.message;
import static com.code.e_qurbani.utils.Constant.PROPOSAL;
import static com.code.e_qurbani.utils.Constant.PROPOSALTHIRDPARTY;
import static com.code.e_qurbani.utils.Constant.PROPOSALTRANSPORTER;
import static com.code.e_qurbani.utils.Constant.docIncrement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

//import com.code.e_qurbani.databinding.ActivityAddThirdPartyBinding;
import com.code.e_qurbani.activities.buyer.BuyerDashboard;
import com.code.e_qurbani.activities.buyer.TransporterProfileActivity;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.databinding.ActivityAnimalSafekeepingBinding;
import com.code.e_qurbani.emailservice.SendEmail;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.firebase.entity.CurrentUser;
import com.code.e_qurbani.firebase.entity.TransporterProposal;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.CustomSendThirdPartyDialog;
import com.code.e_qurbani.utils.CustomSendTransporterDialog;
import com.code.e_qurbani.utils.DialogUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.internet.AddressException;

public class AnimalSafekeepingScreen extends AppCompatActivity {

    private ActivityAnimalSafekeepingBinding binding;
    private FirebaseFirestore db;


//    SendEmail sendEmail = new SendEmail();
    String senderEmail;
    String receiverEmail;

    ThirdParty thirdParty;

    private Dialog dialog;

    private Butcher butcher;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();


//    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnimalSafekeepingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        dialog = new Dialog(this);
        DialogUtils.initLoadingDialog(dialog);
        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });

        Intent intent = getIntent();

         thirdParty = (ThirdParty) intent.getSerializableExtra("thirdparty");


        binding.tvName.setText(thirdParty.getFullName());
        binding.tvLocation.setText(thirdParty.getAddress());
        binding.tvPhoneNumber.setText(thirdParty.getPhoneNumber());
        binding.tvCNIC.setText("CNIC : " + thirdParty.getCnicNumber());


        binding.btnChat.setOnClickListener(view -> {
//            Toast.makeText(this, thirdParty.getFullName(), Toast.LENGTH_SHORT).show();
            this.startActivity(new Intent(this, ThirdPartyChat.class).putExtra(Constant.THIRD_PARTY, thirdParty));
        });

        binding.btnCall.setOnClickListener(view -> {
//            Intent i = new Intent(Intent.ACTION_DIAL);
//            String p = "tel:" + butcher.getContactNumber();
//            i.setData(Uri.parse(p));
//            context.startActivity(i);


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.CALL_PHONE}, 1234);
            }
            else
            {
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "123456789"));
                this.startActivity(intent2);
            }
        });



//         senderEmail = CurrentUser.getCurrentUser().getEmail();
         receiverEmail = thirdParty.getEmail();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

// Check if user is signed in (not null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, you can get user details like email, name, etc.
            senderEmail = currentUser.getEmail();
            String userName = currentUser.getDisplayName();
            // You can also get the user's UID
            String userId = currentUser.getUid();
            // Perform actions with the user object
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
        Date date = new Date();
        String day = simpleDateFormat.format(date);
        simpleDateFormat = new SimpleDateFormat("MMM");
        String month = simpleDateFormat.format(date);
        simpleDateFormat = new SimpleDateFormat("d");
        String dateOfMonth = simpleDateFormat.format(date);
//        butcher = getIntent().getParcelableExtra(Constant.BUTCHER);

        binding.bookService.setOnClickListener(view -> {
            dialog.show();
            CustomSendThirdPartyDialog customSendThirdPartyDialog = new CustomSendThirdPartyDialog(this, thirdParty, (name, animaltype, animalWeight, amountDemanded, pickupAddress, dropAddress, number, payment, butcherEmail) -> {
                String thirdPartyEmail = thirdParty.getEmail();
                ThirdPartyProposal thirdPartyProposal = new ThirdPartyProposal(name, pickupAddress, dropAddress, thirdParty.getDocReference(), animalWeight, String.valueOf(amountDemanded), animaltype, "Pending", number, day, dateOfMonth, month, thirdPartyEmail, String.valueOf(0), String.valueOf(docIncrement), "add review", false);
                ++docIncrement;
                String docNumber = String.valueOf(docIncrement);
                Log.d("TAG", "For Butcher thirdPartyProposal: " + thirdPartyProposal);
                firebaseFirestore.collection(Constant.BUYER).document(auth.getUid()).collection(PROPOSALTHIRDPARTY).document(docNumber).set(thirdPartyProposal).addOnSuccessListener(unused -> {
                    String Reference = Constant.BUYER + "/" + auth.getUid() + "/" + PROPOSALTHIRDPARTY + "/" + docNumber;
                    ThirdPartyProposal thirdPartyProposalForThirdParty = new ThirdPartyProposal(thirdParty.getFullName(), pickupAddress, dropAddress, Reference, animalWeight, String.valueOf(amountDemanded), animaltype, "Pending", number, day, dateOfMonth, month, thirdPartyEmail, String.valueOf(0),String.valueOf(docIncrement),"add review", false);
                    Log.d("TAG", "For Buyer transporterProposal: " + thirdPartyProposalForThirdParty);
                    firebaseFirestore.document(thirdParty.getDocReference()).collection(PROPOSAL).document(docNumber).set(thirdPartyProposalForThirdParty).addOnSuccessListener(unused1 -> {
                        Toast.makeText(AnimalSafekeepingScreen.this, "Proposal Sent Successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                        startActivity(new Intent(this, BuyerDashboard.class));
                    }).addOnFailureListener(e -> {
                        dialog.dismiss();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }).addOnFailureListener(e -> {
                    dialog.dismiss();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            });
            customSendThirdPartyDialog.show();
        });
    }

//    void getPassword ()
//    {
//        CollectionReference buyerCollection = db.collection(Constant.BUYER);
//        buyerCollection.whereEqualTo("email",senderEmail).get()
//                .addOnSuccessListener(queryDocumentSnapshots -> {
//                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
//                    String senderPassword = documentSnapshot.getString("password");
//
//                    sendEmail(senderPassword);
//
//
//                });
//
//
//
//    }
//
//    void sendEmail(String password)
//    {
//        Log.e("This is going to work","jakljdfaklsdfa");
//        SendEmail sendEmailTask = new SendEmail(this, "Hello", senderEmail, receiverEmail, password);
//        sendEmailTask.execute();
//    }
}