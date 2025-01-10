
package com.code.e_qurbani.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

 
import com.bumptech.glide.Glide;
import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.seller.SellAnimalToHighestBidder;
import com.code.e_qurbani.firebase.entity.OnSaleAnimal;
import com.code.e_qurbani.utils.Constant;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ForSaleAnimalSellerAdapter extends RecyclerView.Adapter<ForSaleAnimalSellerAdapter.SellerAnimalViewHolder> {
    private Context context;
    private ArrayList<OnSaleAnimal> onSaleAnimalArrayList;
    private Dialog dialog;


    public ForSaleAnimalSellerAdapter(Context context, ArrayList<OnSaleAnimal> onSaleAnimalArrayList, Dialog dialog) {
        this.context = context;
        this.onSaleAnimalArrayList = onSaleAnimalArrayList;
        this.dialog = dialog;

    }

    @NonNull
    @Override
    public SellerAnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SellerAnimalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_on_sale_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SellerAnimalViewHolder holder, int position) {
        OnSaleAnimal onSaleAnimal = onSaleAnimalArrayList.get(position);
        Glide.with(context).load(onSaleAnimal.getImageUri()).into(holder.imageUrl);
        holder.tvTitle.setText(onSaleAnimal.getName());
        holder.tvDescription.setText(onSaleAnimal.getDesc());
        holder.tvPrice.setText(String.valueOf(onSaleAnimal.getForSaleAnimalPrice()));
        Log.d("TAG", "onBindViewHolder: " + onSaleAnimal);
        holder.materialCardView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, SellAnimalToHighestBidder.class).putExtra(Constant.ANIMAL, onSaleAnimal));
        });
        holder.btbBuyerProposal.setOnClickListener(v -> {
            context.startActivity(new Intent(context, SellAnimalToHighestBidder.class)
                    .putExtra(Constant.ANIMAL, onSaleAnimal).putExtra(Constant.ANIMAL, onSaleAnimal));
        });
    }

    @Override
    public int getItemCount() {
        return onSaleAnimalArrayList.size();
    }

    public class SellerAnimalViewHolder extends RecyclerView.ViewHolder {
        ImageView imageUrl;
        TextView tvTitle, tvDescription, tvPrice;
        MaterialCardView materialCardView;
        MaterialButton btbBuyerProposal;

        public SellerAnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            imageUrl = itemView.findViewById(R.id.ImSrc);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            materialCardView = itemView.findViewById(R.id.cardClicked);
            btbBuyerProposal = itemView.findViewById(R.id.btbBuyerProposal);

        }


    }

}
