package com.code.e_qurbani.adapter;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.buyer.BuyCowBull;
import com.code.e_qurbani.activities.buyer.ShowBuyerAnimalDetail;
import com.code.e_qurbani.activities.seller.SellerChat;
import com.code.e_qurbani.firebase.FirebaseBidding;
import com.code.e_qurbani.firebase.entity.OnSaleAnimal;
import com.code.e_qurbani.firebase.entity.Seller;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.CustomDoBidDialogue;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class SellerAnimalListAdapter extends RecyclerView.Adapter<SellerAnimalListAdapter.SellerAnimalViewHolder> {
    private Context context;
    private ArrayList<OnSaleAnimal> onSaleAnimalArrayList;
    private Dialog dialog;
    private Seller seller;

    public SellerAnimalListAdapter(Context context, ArrayList<OnSaleAnimal> onSaleAnimalArrayList, Dialog dialog, Seller seller) {
        this.context = context;
        this.onSaleAnimalArrayList = onSaleAnimalArrayList;
        this.dialog = dialog;
        this.seller = seller;
    }

    @NonNull
    @Override
    public SellerAnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SellerAnimalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity__buy_cow_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SellerAnimalViewHolder holder, int position) {
        OnSaleAnimal onSaleAnimal = onSaleAnimalArrayList.get(position);

        try {
            Glide.with(context).load(onSaleAnimal.getImageUri()).into(holder.imageUrl);
        }   catch (Exception exception){
//            Toast.makeText(context, "Image Loading Failed", Toast.LENGTH_SHORT).show();
        }

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


        Log.d("onBindViewHolder", "onBindViewHolder: " + onSaleAnimal);
        holder.tvTitle.setText(onSaleAnimal.getName());
        holder.tvDescription.setText(onSaleAnimal.getDesc());
        holder.tvPrice.setText(String.valueOf(onSaleAnimal.getForSaleAnimalPrice()));

        Log.d("TAG", "onBindViewHolder: " + onSaleAnimal);
        holder.materialCardView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, ShowBuyerAnimalDetail.class).putExtra(Constant.ANIMAL, onSaleAnimal));
        });
        holder.doAnimalBiddingButton.setOnClickListener(v -> {
            CustomDoBidDialogue bidDialogue = new CustomDoBidDialogue(context, (name, amountDemanded, address, number, paymentMethod) -> {
                FirebaseBidding bidding = new FirebaseBidding();
                bidding.updateAnimalBidRecord(onSaleAnimal, dialog, context, name, amountDemanded, address, number, paymentMethod);

            });
            bidDialogue.show();
        });
    }

    @Override
    public int getItemCount() {
        return onSaleAnimalArrayList.size();
    }

    public class SellerAnimalViewHolder extends RecyclerView.ViewHolder {
        ImageView imageUrl;
        TextView tvTitle, tvDescription, tvPrice;
        AppCompatButton doAnimalBiddingButton;
        MaterialCardView materialCardView;

        Button btnCall, btnChat;


        public SellerAnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            imageUrl = itemView.findViewById(R.id.ImSrc);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            doAnimalBiddingButton = itemView.findViewById(R.id.doBid);
            materialCardView = itemView.findViewById(R.id.card);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnChat = itemView.findViewById(R.id.btnChat);
        }


    }

}
