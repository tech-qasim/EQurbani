package com.code.e_qurbani.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.buyer.ButcherProfileActivity;
import com.code.e_qurbani.activities.transporter.TransporterDecisionActivity;
import com.code.e_qurbani.chat.ChatScreen;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.firebase.entity.TransporterProposal;
import com.code.e_qurbani.utils.Constant;

import java.util.ArrayList;

public class DisplaySellerAdapter extends RecyclerView.Adapter<DisplaySellerAdapter.ButcherViewHolder> {
    Context context;
    ArrayList<Butcher> butcherList;
    Dialog dialog;

    String screen;

    public DisplaySellerAdapter(Context context, String screen, ArrayList<Butcher> butcherList, Dialog dialog) {
        this.context = context;
        this.butcherList = butcherList;
        this.dialog = dialog;
        this.screen = screen;
    }

    @NonNull
    @Override
    public ButcherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ButcherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.seller, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ButcherViewHolder holder, int position) {
        Butcher butcher = this.butcherList.get(position);
        Log.d("butcherList", "butcherList: " + butcher);
        holder.butcherName.setText(butcher.getFullName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (screen.equals("Chat Screen"))
                {
                    context.startActivity(new Intent(context, ChatScreen.class).putExtra(Constant.BUTCHER, butcher));
                }
                else {
                    context.startActivity(new Intent(context, ButcherProfileActivity.class).putExtra(Constant.BUTCHER, butcher));
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return butcherList.size();
    }

    public class ButcherViewHolder extends RecyclerView.ViewHolder {
        TextView butcherName;
        TextView butcherNumber;
        CardView butcherCard;

        public ButcherViewHolder(@NonNull View itemView) {
            super(itemView);
            butcherName = itemView.findViewById(R.id.tvName);
            butcherNumber = itemView.findViewById(R.id.contact_no);
            butcherCard = itemView.findViewById(R.id.crdseller);
        }
    }
}

