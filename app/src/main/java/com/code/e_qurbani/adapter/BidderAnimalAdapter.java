

package com.code.e_qurbani.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.seller.SellAnimalToSelectedBidder;
import com.code.e_qurbani.firebase.entity.BidAnimal;
import com.code.e_qurbani.firebase.entity.OnSaleAnimal;
import com.code.e_qurbani.utils.Constant;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class BidderAnimalAdapter extends RecyclerView.Adapter<BidderAnimalAdapter.SellerAnimalViewHolder> {
    private final Context context;
    private ArrayList<BidAnimal> onSaleAnimalArrayList;
    private OnSaleAnimal onSaleAnimal;

    public BidderAnimalAdapter(Context context, ArrayList<BidAnimal> onSaleAnimalArrayList, OnSaleAnimal onSaleAnimal) {
        this.context = context;
        this.onSaleAnimal = onSaleAnimal;
        this.onSaleAnimalArrayList = onSaleAnimalArrayList;
    }

    @NonNull
    @Override
    public SellerAnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SellerAnimalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bidding, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SellerAnimalViewHolder holder, int position) {
        BidAnimal bidAnimal = onSaleAnimalArrayList.get(position);
        holder.tvName.setText(bidAnimal.getOwnerName());
        holder.tvPrice.setText(bidAnimal.getAmountOffered());
        holder.materialCardView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, SellAnimalToSelectedBidder.class)
                    .putExtra(Constant.ANIMAL, bidAnimal).putExtra(Constant.SELLER, onSaleAnimal));
        });

    }

    @Override
    public int getItemCount() {
        return onSaleAnimalArrayList.size();
    }

    public class SellerAnimalViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice;
        MaterialCardView materialCardView;

        public SellerAnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            materialCardView = itemView.findViewById(R.id.cardBidder);


        }


    }

}

