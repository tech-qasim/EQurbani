
package com.code.e_qurbani.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.code.e_qurbani.R;
import com.code.e_qurbani.firebase.entity.Seller;
import com.code.e_qurbani.firebase.entity.SoldOutAnimal;
import com.code.e_qurbani.utils.Constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class SellerSoldOutAnimalAdapter extends RecyclerView.Adapter<SellerSoldOutAnimalAdapter.SellerAnimalViewHolder> {
    private Context context;
    private ArrayList<SoldOutAnimal> onSaleAnimalArrayList;
    private Seller seller;
    private String role;
    public SellerSoldOutAnimalAdapter(Context context, ArrayList<SoldOutAnimal> onSaleAnimalArrayList ,String Role) {
        this.context = context;
        this.onSaleAnimalArrayList = onSaleAnimalArrayList;
        this.seller = seller;
        this.role = Role;
    }

    @NonNull
    @Override
    public SellerAnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SellerAnimalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sold_out_animal_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SellerAnimalViewHolder holder, int position) {

        SoldOutAnimal onSaleAnimal = onSaleAnimalArrayList.get(position);
        try {
            Glide.with(context).load(onSaleAnimal.getImageUrl()).diskCacheStrategy(DiskCacheStrategy.DATA).into(holder.imageView);
        } catch (Exception exception) {
//            Toast.makeText(context, "Image Loading Failed", Toast.LENGTH_SHORT).show();
        }
        holder.animalName.setText(onSaleAnimal.getAnimalName());
        holder.soldTo.setText(onSaleAnimal.getAnimalBuyerName());
        holder.tvPrice.setText(onSaleAnimal.getAnimalSalePrice());
        holder.tvCall.setText(onSaleAnimal.getPhoneNumber());
        Date date = new Date(onSaleAnimal.getAnimalSoldOutDate());
        holder.tvDate.setText(String.valueOf(date));
        if (Objects.equals(this.role, Constant.BUYER)){
            holder.tvSoldBuy.setText("Buy From");
            holder.tvTag.setText("Bought Out");
        }


        holder.tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri dialUri = Uri.parse("tel:" + onSaleAnimal.getPhoneNumber());
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, dialUri);
                context.startActivity(dialIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return onSaleAnimalArrayList.size();
    }

    public class SellerAnimalViewHolder extends RecyclerView.ViewHolder {

        TextView animalName, soldTo, tvPrice, tvDate,tvSoldBuy,tvTag,tvCall;
        ImageView imageView;

        public SellerAnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            animalName = itemView.findViewById(R.id.animalName);
            soldTo = itemView.findViewById(R.id.soldTo);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDate = itemView.findViewById(R.id.tvDate);
            imageView = itemView.findViewById(R.id.ImSrc);
            tvSoldBuy = itemView.findViewById(R.id.tvSoldBuy);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvCall = itemView.findViewById(R.id.tvCall);
        }


    }

}
