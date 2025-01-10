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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.buyer.ButcherProfileActivity;
import com.code.e_qurbani.activities.buyer.TransporterProfileActivity;
import com.code.e_qurbani.chat.ChatScreen;
import com.code.e_qurbani.chat.TransporterScreenForChat;
import com.code.e_qurbani.firebase.entity.Butcher;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.TransporterInfoByDistance;
import com.code.e_qurbani.utils.UserInfoByDistance;

import java.util.ArrayList;


public class TransporterHireAdapter extends RecyclerView.Adapter<TransporterHireAdapter.ButcherViewHolder> {
    Context context;
    ArrayList<Butcher> butcherList;

    ArrayList<TransporterInfoByDistance> transporterInfoByDistanceArrayList;
    Dialog dialog;

    ArrayList<TransporterInfoByDistance> nameFilteredList = new ArrayList<>();

    String type;

    ArrayList<TransporterInfoByDistance> filteredList = new ArrayList<>();

    public TransporterHireAdapter(Context context,String type , ArrayList<Butcher> butcherList, Dialog dialog, ArrayList<TransporterInfoByDistance> transporterInfoByDistanceArrayList) {
        this.context = context;
        this.butcherList = butcherList;
        this.type = type;
        this.dialog = dialog;
        this.transporterInfoByDistanceArrayList = transporterInfoByDistanceArrayList;
        this.filteredList = new ArrayList<>(transporterInfoByDistanceArrayList);
        this.nameFilteredList = new ArrayList<>(transporterInfoByDistanceArrayList);


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
                if (type.equals("Chat Screen")) {
                    context.startActivity(new Intent(context, ChatScreen.class).putExtra(Constant.BUTCHER, butcher));
                }
                else if (type.equals("Seller"))
                {
                    context.startActivity(new Intent(context, ChatScreen.class).putExtra(Constant.BUTCHER, butcher));
                }
                else{
                    context.startActivity(new Intent(context, TransporterProfileActivity.class).putExtra(Constant.BUTCHER, butcher));
                }



            }
        });



        holder.chatButton.setOnClickListener(view1 -> {
            context.startActivity(new Intent(context, ChatScreen.class).putExtra(Constant.BUTCHER, butcher));
        });

        holder.callButton.setOnClickListener(view1 -> {


            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1234);
            }
            else
            {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + butcher.getContactNumber()));
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return filteredList.size();
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
            for (TransporterInfoByDistance item : nameFilteredList) {
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
            nameFilteredList.addAll(transporterInfoByDistanceArrayList);
            filteredList.addAll(transporterInfoByDistanceArrayList);
        } else {
            Log.e("called from where", "else statement");

            String lowerCaseQuery = toString.toLowerCase();

            for (TransporterInfoByDistance item : transporterInfoByDistanceArrayList) {
                if (item.getTransporter().getFullAddress().toLowerCase().contains(lowerCaseQuery)) {
                    nameFilteredList.add(item);
                }
            }
            filteredList.addAll(nameFilteredList);
        }
        notifyDataSetChanged();
    }


    public class ButcherViewHolder extends RecyclerView.ViewHolder {
        TextView butcherName;
        TextView butcherNumber;
        CardView butcherCard;

        Button chatButton;

        Button callButton;


        public ButcherViewHolder(@NonNull View itemView) {
            super(itemView);
            butcherName = itemView.findViewById(R.id.tvName);
            butcherNumber = itemView.findViewById(R.id.contact_no);
            butcherCard = itemView.findViewById(R.id.crdseller);
            chatButton = itemView.findViewById(R.id.btnChat);
            callButton = itemView.findViewById(R.id.btnCall);
        }
    }
}