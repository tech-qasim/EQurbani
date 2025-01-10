package com.code.e_qurbani.adapter;

import static com.code.e_qurbani.utils.Constant.ProposalRecord;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import angtrim.com.fivestarslibrary.FiveStarsDialog;
import angtrim.com.fivestarslibrary.NegativeReviewListener;
import angtrim.com.fivestarslibrary.ReviewListener;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ButcherViewHolder> {
    Context context;
    ArrayList<ButcherProposal> butcherList;
    Dialog dialog;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    String screen;

    public OrderDetailAdapter(Context context, ArrayList<ButcherProposal> butcherList, Dialog dialog, String screen) {
        this.context = context;
        this.butcherList = butcherList;
        this.dialog = dialog;
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
        this.screen = screen;
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
        holder.starRating.setText(butcherProposal.getStarRating());
        holder.addReview.setText(butcherProposal.getReview());



        if (butcherProposal.getProposalStatus().equals("Rejected") || butcherProposal.getProposalStatus().equals("Pending"))
        {
            holder.completeOrder.setVisibility(View.GONE);
        }
        else
        {
            holder.completeOrder.setVisibility(View.VISIBLE);
        }


        holder.btnChat.setOnClickListener(view -> {

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            DocumentReference docRef = db.document(butcherProposal.getBuyerDocRef());

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        DocumentSnapshot documentSnapshot=task.getResult();

                        if (documentSnapshot.exists())
                        {
                            Butcher butcher = documentSnapshot.toObject(Butcher.class);

                            context.startActivity(new Intent(context, ChatScreen.class).putExtra(Constant.BUTCHER, butcher));

                            Log.d("Firestore", "DocumentSnapshot data: " + documentSnapshot.getData());


                        }
                        else
                        {
                            Log.d("Firestore", "No such document");
                        }
                    }
                    else
                    {
                        Log.d("Firestore", "get failed with ", task.getException());
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

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                DocumentReference docRef = db.document(butcherProposal.getBuyerDocRef());

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot=task.getResult();

                            if (documentSnapshot.exists())
                            {
                                Butcher butcher = documentSnapshot.toObject(Butcher.class);

                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + butcher.getContactNumber()));
                                context.startActivity(intent);



                                Log.d("Firestore", "DocumentSnapshot data: " + documentSnapshot.getData());


                            }
                            else
                            {
                                Log.d("Firestore", "No such document");
                            }
                        }
                        else
                        {
                            Log.d("Firestore", "get failed with ", task.getException());
                        }
                    }
                });
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + butcher.getContactNumber()));
//                context.startActivity(intent);
            }
        });




        if (butcherProposal.getIsOrderCompleted()) {
            holder.completeOrder.setVisibility(View.GONE);
            holder.tvCompleted.setVisibility(View.VISIBLE);

        }

        holder.tvPhoneNumber.setOnClickListener(view -> {
            Uri dialUri = Uri.parse("tel:" + butcherProposal.getPhoneNumber());
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, dialUri);
            context.startActivity(dialIntent);
        });



        holder.completeOrder.setOnClickListener(view -> {
            showRatingDialog(holder, butcherProposal);
//            Toast.makeText(context, butcherProposal.getBuyerName(), Toast.LENGTH_SHORT).show();
        });
    }

    private void showRatingDialog(ButcherViewHolder holder, ButcherProposal butcherProposal) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogLayout = inflater.inflate(R.layout.rating_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogLayout);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final RatingBar ratingBar = dialogLayout.findViewById(R.id.ratingBar);
        Button buttonSubmit = dialogLayout.findViewById(R.id.buttonSubmit);
        Button buttonCancel = dialogLayout.findViewById(R.id.buttonCancel);
        EditText editText = dialogLayout.findViewById(R.id.etDialogBox);



        buttonSubmit.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            holder.starRating.setText(String.valueOf(rating));
            holder.addReview.setText(editText.getText().toString());

//            updateStarRatingOnFirestore(butcherProposal, String.valueOf(rating), editText.getText().toString());

            if(screen.equals("butcher")) {
                updateStarRatingOnFirestore(butcherProposal, String.valueOf(rating), editText.getText().toString());
            }
            else
            {
                updateStarRatingOnFirestoreForTransporter(butcherProposal, String.valueOf(rating), editText.getText().toString());
            }
            dialog.dismiss();
        });

        buttonCancel.setOnClickListener(v -> dialog.dismiss());
    }

    private void updateStarRatingOnFirestoreForTransporter(ButcherProposal butcherProposal, String rating, String review)
    {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

//        Toast.makeText(context, "executing", Toast.LENGTH_SHORT).show();

        String userId = auth.getUid();
        if (userId != null) {
            firebaseFirestore.collection(Constant.BUYER)
                    .document(userId)
                    .collection(Constant.PROPOSALTRANSPORTER)
                    .whereEqualTo("proposalID", butcherProposal.getProposalID())
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                String documentId = document.getId();
                                document.getReference().update("starRating", rating);
                                document.getReference().update("review", review);
                                document.getReference().update("isOrderCompleted", true)
                                        .addOnSuccessListener(aVoid -> {
                                            butcherProposal.setStarRating(rating);
                                            butcherProposal.setReview(review);
                                            butcherProposal.setIsOrderCompleted(true);
                                            notifyItemChanged(butcherList.indexOf(butcherProposal));
                                            Toast.makeText(context, "Order completed successfully", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        } else {
                            Toast.makeText(context, "Proposal not found", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firestore Query Error", "Error querying Firestore", e);
                        Toast.makeText(context, "Failed to query Firestore", Toast.LENGTH_SHORT).show();
                    });
        }
    }



    private void updateStarRatingOnFirestore(ButcherProposal butcherProposal, String rating, String review) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String userId = auth.getUid();
        if (userId != null) {
            firebaseFirestore.collection(Constant.BUYER)
                    .document(userId)
                    .collection(Constant.PROPOSAL)
                    .whereEqualTo("proposalID", butcherProposal.getProposalID())
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                String documentId = document.getId();
                                document.getReference().update("starRating", rating);
                                document.getReference().update("review", review);
                                document.getReference().update("isOrderCompleted", true)
                                        .addOnSuccessListener(aVoid -> {
                                            butcherProposal.setStarRating(rating);
                                            butcherProposal.setReview(review);
                                            butcherProposal.setIsOrderCompleted(true);
                                            notifyItemChanged(butcherList.indexOf(butcherProposal));
                                            Toast.makeText(context, "Order completed successfully", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        } else {
                            Toast.makeText(context, "Proposal not found", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firestore Query Error", "Error querying Firestore", e);
                        Toast.makeText(context, "Failed to query Firestore", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    @Override
    public int getItemCount() {
        return butcherList.size();
    }

    public static class ButcherViewHolder extends RecyclerView.ViewHolder {
        TextView tvAnimaltype, tvName, tvWeight, tvAmount, tvPhoneNumber, status, day, date, month, starRating, addReview;
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
