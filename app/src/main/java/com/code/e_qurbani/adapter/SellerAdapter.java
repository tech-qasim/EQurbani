package com.code.e_qurbani.adapter;


import static com.code.e_qurbani.utils.Constant.CAMEL;
import static com.code.e_qurbani.utils.Constant.GOAT;
import static com.code.e_qurbani.utils.Constant.SHEEP;

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
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.buyer.BuyCamel;
import com.code.e_qurbani.activities.buyer.BuyCowBull;
import com.code.e_qurbani.activities.buyer.BuyGoat;
import com.code.e_qurbani.activities.buyer.BuySheep;
import com.code.e_qurbani.activities.seller.SellerChat;
import com.code.e_qurbani.chat.ChatScreen;
import com.code.e_qurbani.firebase.FirebaseAnimalSource;
import com.code.e_qurbani.firebase.entity.Seller;
import com.code.e_qurbani.utils.ButcherInfoByDistance;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.SellerInfoByDistance;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.SellerViewHolder> {
    Context context;
    ArrayList<Seller> sellerList;
    Dialog dialog;
    String animalType;
    FirebaseAnimalSource animalSource;
    String collectionType = "";

    ArrayList<SellerInfoByDistance> sellerInfoByDistanceArrayList;
    String BUYER, DAIRY_TYPE;

    ArrayList<SellerInfoByDistance> filteredList;

    ArrayList<SellerInfoByDistance> nameFilteredList = new ArrayList<>();

    public SellerAdapter(Context context, ArrayList<Seller> sellerList, Dialog dialog, String animalType, String BUYER, ArrayList<SellerInfoByDistance> sellerInfoByDistanceArrayList) {
        this.context = context;
        this.sellerList = sellerList;
        this.dialog = dialog;
        animalSource = new FirebaseAnimalSource();
        this.animalType = animalType;
        this.BUYER = BUYER;
        this.sellerInfoByDistanceArrayList = sellerInfoByDistanceArrayList;
        this.filteredList = new ArrayList<>(sellerInfoByDistanceArrayList);
        this.nameFilteredList = new ArrayList<>(sellerInfoByDistanceArrayList);


    }


    @NonNull
    @Override
    public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SellerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.seller, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull SellerViewHolder holder, int position) {
        Seller seller = this.sellerList.get(position);
        holder.sellerName.setText(seller.getFullName());
        Log.d("onBindViewHolder", "onBindViewHolder: " + seller);


        holder.sellerCard.setBackgroundColor(ContextCompat.getColor(context, R.color.white));


        holder.btnChat.setOnClickListener(view -> {
            context.startActivity(new Intent(context, SellerChat.class).putExtra(Constant.SELLER, seller));
        });

        holder.btnCall.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1234);
            }
            else
            {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "03123456789"));
                context.startActivity(intent);
            }
        });

//        holder.btnCall.setOnClickListener(view -> {
////            Intent i = new Intent(Intent.ACTION_DIAL);
////            String p = "tel:" + butcher.getContactNumber();
////            i.setData(Uri.parse(p));
////            context.startActivity(i);
//
//
//            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
//            {
//                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1234);
//            }
//            else
//            {
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + seller.getContactNumber()));
//                context.startActivity(intent);
//            }
//        });


        holder.sellerCard.setOnClickListener(v -> {

            switch (animalType) {
                case Constant.COW: {
                    collectionType = Constant.COW;
                    context.startActivity(new Intent(context, BuyCowBull.class).putExtra(Constant.SELLER, seller));
                    holder.sellerCard.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    break;
                }
                case GOAT: {
                    collectionType = GOAT;
                    context.startActivity(new Intent(context, BuyGoat.class).putExtra(Constant.SELLER, seller));
                    holder.sellerCard.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    break;
                }
                case SHEEP: {
                    collectionType = SHEEP;
                    context.startActivity(new Intent(context, BuySheep.class).putExtra(Constant.SELLER, seller));
                    holder.sellerCard.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    break;
                }

                case CAMEL: {
                    collectionType = CAMEL;
                    context.startActivity(new Intent(context, BuyCamel.class).putExtra(Constant.SELLER, seller));
                    holder.sellerCard.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    break;
                }
                default:


            }
        });
    }

    public void filter(String query) {
        filteredList.clear();

        if (Double.parseDouble(query) == 0.0) {
//            Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
            filteredList.addAll(nameFilteredList);
        } else {
//            Toast.makeText(context, "moving", Toast.LENGTH_SHORT).show();
            Double distance = Double.parseDouble(query);
            Log.e("checking distance", String.valueOf(distance));
            for (SellerInfoByDistance item : nameFilteredList) {
                if (distance > item.getDistanceFromUser())
                    filteredList.add(item);
            }
        }
        notifyDataSetChanged();
    }

    public void filterByName(String toString) {
        nameFilteredList.clear();
        filteredList.clear();

        if (toString.isEmpty()) {
            nameFilteredList.addAll(sellerInfoByDistanceArrayList);
            filteredList.addAll(sellerInfoByDistanceArrayList);
        } else {
            Log.e("called from where", "else statement");

            String lowerCaseQuery = toString.toLowerCase();

            for (SellerInfoByDistance item : sellerInfoByDistanceArrayList) {
                if (item.getSeller().getAddress().toLowerCase().contains(lowerCaseQuery)) {
                    nameFilteredList.add(item);
                }
            }
            filteredList.addAll(nameFilteredList);
        }
        notifyDataSetChanged();
    }





    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class SellerViewHolder extends RecyclerView.ViewHolder {
        TextView sellerName;
        TextView sellerNumber;
        CardView sellerCard;

        Button btnChat, btnCall;

        public SellerViewHolder(@NonNull View itemView) {
            super(itemView);
            sellerName = itemView.findViewById(R.id.tvName);
            sellerNumber = itemView.findViewById(R.id.contact_no);
            sellerCard = itemView.findViewById(R.id.crdseller);
            btnChat = itemView.findViewById(R.id.btnChat);
            btnCall = itemView.findViewById(R.id.btnCall);
        }
    }
}
 