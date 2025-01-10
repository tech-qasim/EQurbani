package com.code.e_qurbani.activities.ThirdParty;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.code.e_qurbani.R;
import com.code.e_qurbani.activities.seller.UpdateAnimalProfile;
import com.code.e_qurbani.firebase.FirebaseAnimalSource;
import com.code.e_qurbani.firebase.entity.Animal;
import com.code.e_qurbani.firebase.entity.Place;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.CustomPutOnSaleDialogue;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class PlaceRecyclerAdapter extends RecyclerView.Adapter<PlaceRecyclerAdapter.PlaceViewHolder> {

    Context context;
    ArrayList<Place> placeArrayList = new ArrayList<>();
    Dialog dialog;

    String thirdParty;

    TextView listEmpty;

    FirebaseAnimalSource placeSource;

    public PlaceRecyclerAdapter(Context context, ArrayList placeArrayList, Dialog dialog, String thirdParty, TextView listEmpty)
    {
        this.context = context;
        this.placeArrayList = placeArrayList;
        this.dialog = dialog;
        this.thirdParty = thirdParty;
        this.listEmpty = listEmpty;
        placeSource = new FirebaseAnimalSource();
    }

    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new PlaceRecyclerAdapter.PlaceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_animal_loader, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        if (placeArrayList.isEmpty()) {
            listEmpty.setVisibility(View.VISIBLE);
        }



        Place place =  placeArrayList.get(position);

        holder.materialCardView.setOnClickListener(view -> {
            Toast.makeText(context,place.getPlaceSrc() , Toast.LENGTH_SHORT).show();
        });
        holder.tvTitle.setText(place.getName());
        holder.tvDesc.setText(place.getLocation());
        try {
            Glide.with(context)
                    .load(place.getPlaceSrc())
                    .skipMemoryCache(false) // Do not skip memory cache
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache all versions of the image
                    .error(R.drawable.loading) // Replace with your error placeholder image
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Toast.makeText(context, "Error Loading Image", Toast.LENGTH_LONG).show();
                            holder.imageSrc.setImageResource(R.drawable.loading); // Replace with your error image
                            return true;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.imageSrc);
//            Glide.with(context).load(animal.getAnimalSrc()).diskCacheStrategy(DiskCacheStrategy.DATA).into(holder.imageSrc);

        } catch (Exception exception) {
            Toast.makeText(context, "Error Loading Image", Toast.LENGTH_LONG).show();
        }

            holder.btnPutOnSale.setVisibility(View.GONE);




//        holder.putForSale.setEnabled(Objects.equals(animal.isPutForSale(), Constant.FALSE));
//        holder.btnEdit.setEnabled(Objects.equals(animal.isPutForSale(), Constant.FALSE));

//        holder.btnDelete.setEnabled(Objects.equals("false", Constant.FALSE));
        holder.btnDelete.setOnClickListener(view -> {
            dialog.show();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String docReference = place.getDocReference();
            Toast.makeText(context, docReference, Toast.LENGTH_SHORT).show();
            db.document(docReference).delete()
                    .addOnSuccessListener(aVoid -> {
                        dialog.dismiss();
                        Toast.makeText(context, "Place Removed", Toast.LENGTH_LONG).show();
//                        placeArrayList.remove(position);
//                        notifyItemRemoved(position);
                    })
                    .addOnFailureListener(e -> {
                        dialog.dismiss();
                        Toast.makeText(context, "Failed to Remove Place", Toast.LENGTH_LONG).show();
                    });
        });


//        holder.btnEdit.setOnClickListener(view -> context.
//                startActivity(new Intent(context, UpdateAnimalProfile.class).putExtra(Constant.ANIMAL, animal).putExtra(Constant.GET_ROLE, animalType)));
////
//        holder.putForSale.setOnClickListener(v -> {
//            Toast.makeText(context, animal.getDocReference(), Toast.LENGTH_SHORT).show();
//            CustomPutOnSaleDialogue saleDialogue = new CustomPutOnSaleDialogue(context, (amountDemanded, address, number, paymentMethod) -> {
//                holder.putForSale.setEnabled(false);
//                animalSource.putAnimalOnSale(context ,animal, animalType, dialog, amountDemanded, address, number, paymentMethod);
//            });
//            saleDialogue.show();
//        });

    }



    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageSrc;
        public TextView tvTitle, tvTag, tvDesc;
        public Button btnEdit, btnDelete;
        public MaterialCardView materialCardView;


        Button btnPutOnSale;



        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            materialCardView = itemView.findViewById(R.id.card);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDescription);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            imageSrc = itemView.findViewById(R.id.ImSrc);
            btnPutOnSale = itemView.findViewById(R.id.btnPutOnSale);

        }



//        imageSrc = itemView.findViewById(R.id.ImSrc);
//        tvTitle = itemView.findViewById(R.id.tvTitle);
//        tvTag = itemView.findViewById(R.id.tvTag);
//        tvDesc = itemView.findViewById(R.id.tvDescription);
//        btnEdit = itemView.findViewById(R.id.btnEdit);
//        btnDelete = itemView.findViewById(R.id.btnDelete);
//        materialCardView = itemView.findViewById(R.id.card);
    }
}
