package com.code.e_qurbani.activities.ThirdParty;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

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

import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.ThirdParty.ThirdParty;
import com.code.e_qurbani.activities.seller.SellerChat;
import com.code.e_qurbani.chat.ChatScreen;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.UserInfoByDistance;

import java.io.Serializable;
import java.util.ArrayList;

public class ThirdPartyAdapter extends RecyclerView.Adapter<ThirdPartyAdapter.ThirdPartyViewHolder> {

    Context context;
    ArrayList<ThirdParty> thirdPartyArrayList;
    ArrayList<UserInfoByDistance> thirdPartyArrayListWithDistance;
    Dialog dialog;
    String screen;

    ArrayList<UserInfoByDistance> filteredList = new ArrayList<>();
    ArrayList<UserInfoByDistance> nameFilteredList = new ArrayList<>();

    public ThirdPartyAdapter(Context context, String screen, ArrayList<ThirdParty> thirdPartyArrayList, Dialog dialog, ArrayList<UserInfoByDistance> thirdPartyArrayListWithDistance) {
        this.context = context;
        this.screen = screen;
        this.dialog = dialog;
        this.thirdPartyArrayList = thirdPartyArrayList;
        this.thirdPartyArrayListWithDistance = thirdPartyArrayListWithDistance;
        this.filteredList = new ArrayList<>(thirdPartyArrayListWithDistance);
        this.nameFilteredList = new ArrayList<>(thirdPartyArrayListWithDistance);
    }

    @NonNull
    @Override
    public ThirdPartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThirdPartyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.seller, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ThirdPartyViewHolder holder, int position) {
        UserInfoByDistance thirdParty = this.filteredList.get(position); // Use filteredList here
        holder.thirdPartyName.setText(thirdParty.getThirdParty().getFullName());
        holder.thirdPartyDistance.setText(String.valueOf(thirdParty.getDistanceFromUser() + " KM"));
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, AnimalSafekeepingScreen.class);
            ThirdParty TP = thirdParty.getThirdParty();
            intent.putExtra("thirdparty", (Serializable) TP);
            context.startActivity(intent);
        });


        holder.chatButton.setOnClickListener(view -> {
            Toast.makeText(context, thirdParty.getThirdParty().getFullName(), Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, ThirdPartyChat.class).putExtra(Constant.THIRD_PARTY, thirdParty.getThirdParty()));
        });

        holder.callButton.setOnClickListener(view -> {
//            Intent i = new Intent(Intent.ACTION_DIAL);
//            String p = "tel:" + butcher.getContactNumber();
//            i.setData(Uri.parse(p));
//            context.startActivity(i);


            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1234);
            }
            else
            {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "123456789"));
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
            for (UserInfoByDistance item : nameFilteredList) {
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
            nameFilteredList.addAll(thirdPartyArrayListWithDistance);
            filteredList.addAll(thirdPartyArrayListWithDistance);
        } else {
            Log.e("called from where", "else statement");

            String lowerCaseQuery = toString.toLowerCase();

            for (UserInfoByDistance item : thirdPartyArrayListWithDistance) {
                if (item.getThirdParty().getAddress().toLowerCase().contains(lowerCaseQuery)) {
                    nameFilteredList.add(item);
                }
            }
            filteredList.addAll(nameFilteredList);
        }
        notifyDataSetChanged();
    }

    public class ThirdPartyViewHolder extends RecyclerView.ViewHolder {
        TextView thirdPartyName;
        TextView thirdPartyNumber;
        CardView thirdPartyCard;
        TextView thirdPartyDistance;

        Button chatButton;

        Button callButton;

        public ThirdPartyViewHolder(@NonNull View itemView) {
            super(itemView);
            thirdPartyName = itemView.findViewById(R.id.tvName);
            thirdPartyNumber = itemView.findViewById(R.id.contact_no);
            thirdPartyCard = itemView.findViewById(R.id.crdseller);
            thirdPartyDistance = itemView.findViewById(R.id.tvDistance);
            chatButton = itemView.findViewById(R.id.btnChat);
            callButton = itemView.findViewById(R.id.btnCall);
        }
    }
}
