package com.code.e_qurbani.firebase;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.code.e_qurbani.adapter.ForSaleAnimalSellerAdapter;
import com.code.e_qurbani.adapter.SellerAnimalListAdapter;
import com.code.e_qurbani.firebase.entity.OnSaleAnimal;
import com.code.e_qurbani.firebase.entity.Seller;
import com.code.e_qurbani.utils.Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class FirebaseSellerAnimalSource {


    public void getOnSaleAnimalList(Context context, ArrayList<OnSaleAnimal> onSaleAnimalArrayList, Dialog loadingDialog, SellerAnimalListAdapter sellerAnimalListAdapter, Seller seller, String collectionName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        loadingDialog.show();
        db.document(seller.getSellerReference()).collection(collectionName).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            assert value != null;
            if (value.isEmpty()) {
                Toast.makeText(context, "List Empty", Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            } else {
                for (DocumentSnapshot snapshot : value) {
                    OnSaleAnimal onSaleAnimal = snapshot.toObject(OnSaleAnimal.class);
                    Log.d("TAG", "getOnSaleAnimalList: " + onSaleAnimal);
                    onSaleAnimalArrayList.add(onSaleAnimal);
                }
                sellerAnimalListAdapter.notifyDataSetChanged();
                loadingDialog.dismiss();
            }
        }));
    }

    public void getOnSaleAnimalListForSeller(Context context, ArrayList<OnSaleAnimal> onSaleAnimalArrayList, Dialog loadingDialog, ForSaleAnimalSellerAdapter sellerAnimalListAdapter, String collectionName) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        loadingDialog.show();
        Log.d("TAG", "getOnSaleAnimalListForSeller: " + db.collection(Constant.SELLER_FOR_SALE_COLLECTION).document(Objects.requireNonNull(auth.getUid())).collection(collectionName).getPath());
        db.collection(Constant.SELLER_FOR_SALE_COLLECTION).document(Objects.requireNonNull(auth.getUid())).collection(collectionName).addSnapshotListener(((value, error) -> {
            if (error != null) {
                loadingDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            assert value != null;
            if (value.isEmpty()) {
                Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
            } else {
                for (DocumentSnapshot snapshot : value) {
                    OnSaleAnimal onSaleAnimal = snapshot.toObject(OnSaleAnimal.class);
                    Log.d("TAG", "getOnSaleAnimalListForSeller: " + onSaleAnimal);
                    onSaleAnimalArrayList.add(onSaleAnimal);
                }
                sellerAnimalListAdapter.notifyDataSetChanged();
            }
            loadingDialog.dismiss();
        }));
    }

}
