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
import com.code.e_qurbani.chat.ChatScreen;
import com.code.e_qurbani.firebase.entity.Butcher;

import java.util.ArrayList;

public class ReceivedMessageAdapterForTransporter extends RecyclerView.Adapter<ReceivedMessageAdapterForTransporter.ButcherViewHolder> {
    Context context;
    ArrayList<Butcher> butcherList;
    Dialog dialog;

    String screen;

    ArrayList<String> usernames;

    public ReceivedMessageAdapterForTransporter(Context context, ArrayList<String> usernames) {
        this.context = context;
        this.usernames = usernames;
    }

    @NonNull
    @Override
    public ReceivedMessageAdapterForTransporter.ButcherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
                Intent intent = new Intent(context, ChatScreen.class);
                intent.putExtra("key",user);
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

        public ButcherViewHolder(@NonNull View itemView) {
            super(itemView);
            butcherName = itemView.findViewById(R.id.tvName);
            butcherNumber = itemView.findViewById(R.id.contact_no);
            butcherCard = itemView.findViewById(R.id.crdseller);
        }
    }
}
