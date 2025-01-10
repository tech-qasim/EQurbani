package com.code.e_qurbani.activities.ThirdParty;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.transporter.TransporterDecisionActivity;

import java.util.ArrayList;

public class ThirdPartyOfferAdapter extends RecyclerView.Adapter <ThirdPartyOfferAdapter.ThirdPartyProposalViewHolder>{

    Context context;

    ArrayList<ThirdPartyProposal> thirdPartyList;

    Dialog dialog;





    public ThirdPartyOfferAdapter(Context context, ArrayList<ThirdPartyProposal> thirdPartyList, Dialog dialog)
    {
        this.context = context;
        this.thirdPartyList = thirdPartyList;
        this.dialog = dialog;
    }



    @NonNull
    @Override
    public ThirdPartyProposalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThirdPartyOfferAdapter.ThirdPartyProposalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bautcher_proposal_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ThirdPartyProposalViewHolder holder, int position) {
        ThirdPartyProposal thirdPartyProposal = this.thirdPartyList.get(position);
        if (thirdPartyProposal!=null)
        {
            holder.tvAnimaltype.setText(thirdPartyProposal.animalType);
            holder.tvWeight.setText(thirdPartyProposal.getWeight());
            holder.tvName.setText(thirdPartyProposal.getBuyerName());

            holder.itemView.setOnClickListener(view -> {
                context.startActivity(new Intent(context, ThirdPartyDecisionActivity.class).putExtra("thirdPartyProposalData", thirdPartyProposal));
            });
        }

    }

    @Override
    public int getItemCount() {
        return thirdPartyList.size();
    }

    public class ThirdPartyProposalViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvAnimaltype;
        TextView tvName;
        TextView tvWeight;
        public ThirdPartyProposalViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAnimaltype = itemView.findViewById(R.id.tvAnimalTypeC);
            tvWeight = itemView.findViewById(R.id.tvWeightB);
            tvName = itemView.findViewById(R.id.tvNameA);
        }
    }

}
