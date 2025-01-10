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
import androidx.recyclerview.widget.RecyclerView;

import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.transporter.TransporterDecisionActivity;
import com.code.e_qurbani.firebase.entity.TransporterProposal;

import java.util.ArrayList;


public class TransporterOfferAdapter extends RecyclerView.Adapter<TransporterOfferAdapter.ButcherProposalViewHolder> {
    Context context;
    ArrayList<TransporterProposal> butcherList;
    Dialog dialog;

    public TransporterOfferAdapter(Context context, ArrayList<TransporterProposal> butcherList, Dialog dialog) {
        this.context = context;
        this.butcherList = butcherList;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ButcherProposalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ButcherProposalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bautcher_proposal_recycler, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ButcherProposalViewHolder holder, int position) {
        TransporterProposal butcherProposal = this.butcherList.get(position);
        if (butcherProposal != null) {
            Log.d("TAG", "onBindViewHoldeSDSADADADASDr: " + butcherProposal);
            holder.tvAnimaltype.setText(butcherProposal.getAnimalType());
            holder.tvWeight.setText(butcherProposal.getWeight());
            holder.tvName.setText(butcherProposal.getBuyerName());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: SDASDASDASD  " + butcherProposal);
                context.startActivity(new Intent(context, TransporterDecisionActivity.class).putExtra("butcherProposalData", butcherProposal));
            }
        });

    }


    @Override
    public int getItemCount() {
        return butcherList.size();
    }

    public class ButcherProposalViewHolder extends RecyclerView.ViewHolder {


        TextView tvAnimaltype;
        TextView tvName;
        TextView tvWeight;

        public ButcherProposalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAnimaltype = itemView.findViewById(R.id.tvAnimalTypeC);
            tvWeight = itemView.findViewById(R.id.tvWeightB);
            tvName = itemView.findViewById(R.id.tvNameA);


        }
    }
}
