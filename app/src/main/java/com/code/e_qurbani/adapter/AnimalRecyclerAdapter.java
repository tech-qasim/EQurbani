package com.code.e_qurbani.adapter;

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
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.CustomPutOnSaleDialogue;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class AnimalRecyclerAdapter extends RecyclerView.Adapter<AnimalRecyclerAdapter.AnimalViewHolder> {
    private Context context;
    private Dialog dialog;
    private String animalType;
    private ArrayList<Animal> animalArrayList;
    private TextView listEmpty;
    FirebaseAnimalSource animalSource;
    public AnimalRecyclerAdapter(Context context, ArrayList animalArrayList, Dialog dialog, String animalType, TextView listEmpty) {
        this.context = context;
        this.dialog = dialog;
        this.animalArrayList = animalArrayList;
        this.animalType = animalType;
        this.listEmpty = listEmpty;
        animalSource = new FirebaseAnimalSource();
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnimalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_animal_loader, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        if (animalArrayList.isEmpty()) {
            listEmpty.setVisibility(View.VISIBLE);
        }
        Animal animal = animalArrayList.get(position);
        holder.tvTitle.setText(animal.getAnimalName());
        holder.tvDesc.setText(animal.getBioNotes());
        try {
            Glide.with(context)
                    .load(animal.getAnimalSrc())
                    .skipMemoryCache(false) // Do not skip memory cache
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache all versions of the image
                    .error(R.drawable.loading) // Replace with your error placeholder image
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            Toast.makeText(context, "Error Loading Image", Toast.LENGTH_LONG).show();
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
//            Toast.makeText(context, "Error Loading Image", Toast.LENGTH_LONG).show();
        }

        holder.putForSale.setEnabled(Objects.equals(animal.isPutForSale(), Constant.FALSE));
        holder.btnEdit.setEnabled(Objects.equals(animal.isPutForSale(), Constant.FALSE));
        holder.btnDelete.setEnabled(Objects.equals(animal.isPutForSale(), Constant.FALSE));
        holder.btnDelete.setOnClickListener(view -> {
            dialog.show();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Toast.makeText(context, animal.getDocReference(), Toast.LENGTH_SHORT).show();
            db.document(animal.getDocReference()).delete();
            dialog.dismiss();
//            Toast.makeText(context, "Animal Removed", Toast.LENGTH_LONG).show();
        });


        holder.btnEdit.setOnClickListener(view -> context.
                startActivity(new Intent(context, UpdateAnimalProfile.class).putExtra(Constant.ANIMAL, animal).putExtra(Constant.GET_ROLE, animalType)));
//
        holder.putForSale.setOnClickListener(v -> {
            Toast.makeText(context, animal.getDocReference(), Toast.LENGTH_SHORT).show();
            CustomPutOnSaleDialogue saleDialogue = new CustomPutOnSaleDialogue(context, (amountDemanded, address, number, paymentMethod) -> {
                holder.putForSale.setEnabled(false);
                animalSource.putAnimalOnSale(context ,animal, animalType, dialog, amountDemanded, address, number, paymentMethod);
            });
            saleDialogue.show();
        });
    }


    @Override
    public int getItemCount() {
        return animalArrayList.size();
    }

    public class AnimalViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageSrc;
        public TextView tvTitle, tvTag, tvDesc;
        public Button btnEdit, btnDelete;
        public MaterialCardView materialCardView;
        public MaterialButton putForSale;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSrc = itemView.findViewById(R.id.ImSrc);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvDesc = itemView.findViewById(R.id.tvDescription);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            materialCardView = itemView.findViewById(R.id.card);
            putForSale = itemView.findViewById(R.id.btnPutOnSale);

        }
    }
}
