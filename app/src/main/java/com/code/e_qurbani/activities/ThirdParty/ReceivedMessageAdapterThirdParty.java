package com.code.e_qurbani.activities.ThirdParty;

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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.code.e_qurbani.R;

import com.code.e_qurbani.activities.ThirdParty.ThirdPartyChat;
import com.code.e_qurbani.chat.ChatScreen;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.firebase.entity.Seller;
import com.code.e_qurbani.utils.Constant;


import java.util.ArrayList;


public class ReceivedMessageAdapterThirdParty extends RecyclerView.Adapter<ReceivedMessageAdapterThirdParty.ButcherViewHolder> {
    Context context;
    ArrayList<Seller> butcherList;
    Dialog dialog;

    String screen;

    ArrayList<String> usernames;

    public ReceivedMessageAdapterThirdParty(Context context, ArrayList<String> usernames) {
        this.context = context;
        this.usernames = usernames;
    }

    @NonNull
    @Override
    public ButcherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ButcherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.seller, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ButcherViewHolder holder, int position) {
//        Butcher butcher = this.butcherList.get(position);
        String user = this.usernames.get(position);
        Log.e("user value",user);
        holder.butcherName.setText(user);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ThirdPartyChat.class);
                intent.putExtra("key",user);
                context.startActivity(intent);

            }
        });

        holder.btnChat.setOnClickListener(view -> {
            Intent intent = new Intent(context, ThirdPartyChat.class);
            intent.putExtra("key",user);
            context.startActivity(intent);
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





    }


    @Override
    public int getItemCount() {
        return usernames.size();
    }

    public class ButcherViewHolder extends RecyclerView.ViewHolder {
        TextView butcherName;
        TextView butcherNumber;
        CardView butcherCard;
        Button btnChat, btnCall;

        public ButcherViewHolder(@NonNull View itemView) {
            super(itemView);
            butcherName = itemView.findViewById(R.id.tvName);
            butcherNumber = itemView.findViewById(R.id.contact_no);
            butcherCard = itemView.findViewById(R.id.crdseller);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnChat = itemView.findViewById(R.id.btnChat);
        }
    }
}
