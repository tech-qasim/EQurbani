package com.code.e_qurbani.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.code.e_qurbani.R;
import com.code.e_qurbani.chat.ChatScreen;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.firebase.entity.ButcherProposal;
import com.code.e_qurbani.utils.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class OrderTrackerAdapter extends RecyclerView.Adapter<OrderTrackerAdapter.ButcherViewHolder> {
    Context context;
    ArrayList<ButcherProposal> butcherList;
    Dialog dialog;

    public OrderTrackerAdapter(Context context, ArrayList<ButcherProposal> butcherList, Dialog dialog) {
        this.context = context;
        this.butcherList = butcherList;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ButcherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ButcherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_summary_recycler, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ButcherViewHolder holder, int position) {
        ButcherProposal butcherProposal = this.butcherList.get(position);

        holder.tvAnimaltype.setText(butcherProposal.getAnimalType());
        holder.tvWeight.setText(butcherProposal.getWeight());
        holder.tvAmount.setText(butcherProposal.getPrice());
        holder.tvName.setText(butcherProposal.getBuyerName());
        holder.tvPhoneNumber.setText(butcherProposal.getPhoneNumber());
        holder.status.setText(butcherProposal.getProposalStatus());
        holder.day.setText(butcherProposal.getDay());
        holder.date.setText(butcherProposal.getDate());
        holder.month.setText(butcherProposal.getMonth());
        holder.addReview.setText(butcherProposal.getReview());
        holder.starRating.setText(butcherProposal.getStarRating());

        holder.completeOrder.setVisibility(View.GONE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Define the path to the document
        String documentPath = butcherProposal.getBuyerDocRef();

// Get a reference to the document
        final DocumentReference[] docRef = {db.document(documentPath)};

// Fetch the document
        docRef[0].get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Document found, handle the data
                        holder.starRating.setText(document.getString("starRating"));
                        holder.addReview.setText(document.getString("review"));
                    } else {
                        // No such document
                        Log.d("Document", "No such document");
                    }
                } else {
                    // Task failed
                    Log.d("Document", "get failed with ", task.getException());
                }
            }
        });


        holder.btnChat.setOnClickListener(view -> {

            docRef[0] = db.document(getSubstringUntilSecondSlash(butcherProposal.getBuyerDocRef()));

            docRef[0].get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Butcher butcher = document.toObject(Butcher.class);

//                            Toast.makeText(context, butcher.getFullName(), Toast.LENGTH_SHORT).show();

                            if (butcher != null) {
                                Intent intent = new Intent(context, ChatScreen.class);
                                intent.putExtra("key", encodeEmail(butcher.getEmail()));
                                intent.putExtra(Constant.BUTCHER, butcher);
                                context.startActivity(intent);
                            } else {
                                Log.e("OrderTrackerAdapter", "Butcher is null after parsing");
                            }

                        } else {
                            // No such document
                            Log.d("Document", "No such document");
                        }
                    } else {
                        // Task failed
                        Log.d("Document", "get failed with ", task.getException());
                    }
                }
            });

        });





        holder.btnCall.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1234);
            }
            else
            {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + butcherProposal.getPhoneNumber()));
                context.startActivity(intent);
            }
        });







//        holder.tvPhoneNumber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Uri dialUri = Uri.parse("tel:" + butcherProposal.getPhoneNumber());
//                Intent dialIntent = new Intent(Intent.ACTION_DIAL, dialUri);
//                context.startActivity(dialIntent);
//            }
//        });


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



    public static String getSubstringUntilSecondSlash(String input) {
        int firstSlashIndex = input.indexOf('/');
        int secondSlashIndex = input.indexOf('/', firstSlashIndex + 1);

        if (secondSlashIndex != -1) {
            return input.substring(0, secondSlashIndex);
        } else {
            return input; // Return the whole string if there is no second slash
        }
    }


    @Override
    public int getItemCount() {
        return butcherList.size();
    }

    public class ButcherViewHolder extends RecyclerView.ViewHolder {
        TextView day;

        TextView tvAnimaltype;
        TextView tvName;
        TextView tvWeight;
        TextView tvAmount;
        TextView tvPhoneNumber;
        TextView status;

        TextView date;
        TextView month, starRating, addReview;

        Button completeOrder;

        TextView tvCompleted;

        Button btnCall;

        Button btnChat;

        public ButcherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAnimaltype = itemView.findViewById(R.id.tvAnimaltype);
            tvWeight = itemView.findViewById(R.id.tvWeight);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            status = itemView.findViewById(R.id.status);
            day = itemView.findViewById(R.id.day);
            date = itemView.findViewById(R.id.date);
            month = itemView.findViewById(R.id.month);
            completeOrder = itemView.findViewById(R.id.completeOrder);
            starRating = itemView.findViewById(R.id.starRating);
            addReview = itemView.findViewById(R.id.addReview);
            tvCompleted = itemView.findViewById(R.id.tvCompleted);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnChat = itemView.findViewById(R.id.btnChat);


        }
    }
}
