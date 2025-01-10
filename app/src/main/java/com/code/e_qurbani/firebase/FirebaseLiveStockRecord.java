package com.code.e_qurbani.firebase;

import static com.code.e_qurbani.utils.Constant.BREED;
import static com.code.e_qurbani.utils.Constant.FEMALE;
import static com.code.e_qurbani.utils.Constant.HEALTHY;
import static com.code.e_qurbani.utils.Constant.IS_PREGNANT;
import static com.code.e_qurbani.utils.Constant.MALE;
import static com.code.e_qurbani.utils.Constant.NO;
import static com.code.e_qurbani.utils.Constant.YES;
import static com.code.e_qurbani.utils.Constant.ZERO;
import static com.code.e_qurbani.utils.Constant.isFullyVaccinated;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.code.e_qurbani.R;
import com.code.e_qurbani.utils.Constant;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class FirebaseLiveStockRecord {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Context context;
    String TAG = "TAG";
    int counterAnimals = 0;

    public void getLiveStockRecord(Dialog dialog, Context liveContext,
                                   String cattleCollection, TextView tvMaleCount, TextView tvFemaleCount,
                                   TextView tvPregnantCount, TextView fullyVaccinatedCount,
                                   TextView nonVaccinated,

                                   TextView firstBreedValue,
                                   TextView secondBreedValue,
                                   TextView thirdBreedValue, TextView totalLiveStock,
                                  TextView fourthBreedValue, String[] breed) {
        context = liveContext;
        dialog.show();
        counterAnimals = 0;
        countLiveRecord(dialog, Constant.genderType, MALE, tvMaleCount, cattleCollection);
        /**
         * Male Count
         */

        /**
         * Female Count
         */
        countLiveRecord(dialog, Constant.genderType, FEMALE, tvFemaleCount, cattleCollection);

        /**
         * Pregnant Count
         */
        countLiveRecord(dialog, IS_PREGNANT, YES, tvPregnantCount, cattleCollection);

        /**
         * Fully Vaccinated
         */
        countLiveRecord(dialog, isFullyVaccinated, YES, fullyVaccinatedCount, cattleCollection);

        /**
         * Un-Vaccinated
         */
        countLiveRecord(dialog, isFullyVaccinated, NO, nonVaccinated, cattleCollection);
        /**
         * Diseased Count
         */
       

        /**
         * Breed Count
         */
        countLiveRecord(dialog, BREED, breed[0], firstBreedValue, cattleCollection);
        countLiveRecord(dialog, BREED, breed[1], secondBreedValue, cattleCollection);
        countLiveRecord(dialog, BREED, breed[2], thirdBreedValue, cattleCollection);
        countLiveRecord(dialog, BREED, breed[3], fourthBreedValue, cattleCollection);
        new Handler().postDelayed(() -> {
            dialog.dismiss();
            totalLiveStock.setText(String.valueOf(counterAnimals));
        }, 3000);
    }

    private void countLiveRecord(Dialog dialog, String key, String valueKey, TextView tvToCount, String cattleCollection) {
        new Handler().post(() -> {
            db.collection(Constant.SELLER).document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).
                    collection(cattleCollection).whereEqualTo(key, valueKey).addSnapshotListener(((value, error) -> {
                        if (error != null) {
                            showSnackBar(error.getMessage(), tvToCount);
                            dialog.dismiss();
                        }
                        assert value != null;
                        if (value.isEmpty()) {
                            tvToCount.setText("0");
                            if (valueKey.equals(MALE) || valueKey.equals(FEMALE)) {
                                counterIncrement(0);
                            }

                            Log.d(TAG, "getLiveStockRecord: Empty");

                        } else {
                            tvToCount.setText(String.valueOf(value.size()));
                            if (valueKey.equals(MALE) || valueKey.equals(FEMALE)) {
                                counterIncrement(value.size());
                            }

                            Log.d(TAG, "getLiveStockRecord: " + value.size());
                        }
                    }));

        });
    }

    private synchronized void counterIncrement(int value) {
        counterAnimals += value;
    }


    public void showSnackBar(String message, TextView id) {
        Snackbar.make(id, message, Snackbar.LENGTH_LONG).setTextColor(ContextCompat.getColor(context, R.color.redish1)).show();
    }
}
