package com.code.e_qurbani.activities.seller;

import static com.code.e_qurbani.utils.Constant.ANIMAL;
import static com.code.e_qurbani.utils.Constant.CAMEL;
import static com.code.e_qurbani.utils.Constant.COW;
import static com.code.e_qurbani.utils.Constant.GOAT;
import static com.code.e_qurbani.utils.Constant.SHEEP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import com.code.e_qurbani.R;
import com.code.e_qurbani.firebase.FirebaseLiveStockRecord;
import com.code.e_qurbani.utils.Constant;
import com.code.e_qurbani.utils.DialogUtils;
import com.google.android.material.snackbar.Snackbar;

public class LiveStockDetailRecord extends AppCompatActivity {

    TextView tvMaleCount, tvFemaleCount, tvPregnantCount, fullyVaccinatedCount, partiallyVaccinated, nonVaccinated, disesaedStatus,
            firstBreedName, firstBreedValue, secondBreedName, fourthBreedName, secondBreedValue, thirdBreedName, thirdBreedValue, totalLiveStock, fourthBreedValue, healthy;
    String getRole;
    FirebaseLiveStockRecord liveStockRecord;
    Dialog dialog;
    String[] breedArray = null;

    private void initViews() {
        tvMaleCount = findViewById(R.id.tvMaleCount);
        fourthBreedName = findViewById(R.id.fourthBreedName);
        fourthBreedValue = findViewById(R.id.fourthBreedValue);
         tvFemaleCount = findViewById(R.id.tvFemaleCount);
        tvPregnantCount = findViewById(R.id.tvPregnantCount);
        fullyVaccinatedCount = findViewById(R.id.fullyVaccinatedCount);
         nonVaccinated = findViewById(R.id.nonVaccinated);
         firstBreedName = findViewById(R.id.firstBreedName);
        firstBreedValue = findViewById(R.id.firstBreedValue);
        secondBreedName = findViewById(R.id.secondBreedName);
        secondBreedValue = findViewById(R.id.secondBreedValue);
        thirdBreedName = findViewById(R.id.thirdBreedName);
        thirdBreedValue = findViewById(R.id.thirdBreedValue);
        totalLiveStock = findViewById(R.id.totalLiveStock);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_stock_detail_record);
        getRole = getIntent().getStringExtra(ANIMAL);
        liveStockRecord = new FirebaseLiveStockRecord();
        initViews();
        dialog = new Dialog(this);
        DialogUtils.initLoadingDialog(dialog);
        fetchLiveStockRecord();
    }


    private void fetchLiveStockRecord() {
        switch (getRole) {
            case COW:
                breedArray = new String[]{"Chollistani", "Lohani", "Red Sindhi", "Tharparkar"};
                setBreedTextViews();
                liveStockRecord.getLiveStockRecord(dialog, this, COW, tvMaleCount, tvFemaleCount, tvPregnantCount, fullyVaccinatedCount , nonVaccinated,
                        firstBreedValue, secondBreedValue, thirdBreedValue, totalLiveStock , fourthBreedValue, breedArray);
                break;
            case GOAT:
                breedArray = new String[]{"Beetal", "Barbari", "Kamori", "Nachi"};
                setBreedTextViews();
                liveStockRecord.getLiveStockRecord(dialog, this, GOAT, tvMaleCount, tvFemaleCount, tvPregnantCount, fullyVaccinatedCount , nonVaccinated,
                        firstBreedValue, secondBreedValue, thirdBreedValue, totalLiveStock , fourthBreedValue, breedArray);
                break;
            case SHEEP:
                breedArray = new String[]{"Balkhi", "Baluchi", "Cholistani ", "Damani "};
                setBreedTextViews();
                liveStockRecord.getLiveStockRecord(dialog, this, SHEEP,tvMaleCount, tvFemaleCount, tvPregnantCount, fullyVaccinatedCount , nonVaccinated,
                        firstBreedValue, secondBreedValue, thirdBreedValue, totalLiveStock , fourthBreedValue, breedArray);
                break;
            case CAMEL:
                breedArray = new String[]{"Dromedary", "Arabian", "Bactrian","Damani"};;
                setBreedTextViews();
                liveStockRecord.getLiveStockRecord(dialog, this, CAMEL,tvMaleCount, tvFemaleCount, tvPregnantCount, fullyVaccinatedCount , nonVaccinated,
                        firstBreedValue, secondBreedValue, thirdBreedValue, totalLiveStock , fourthBreedValue, breedArray);
                break;
            default:
                showSnackBar("Default Case");
        }
    }

    private void setBreedTextViews() {
        firstBreedName.setText(breedArray[0]);
        secondBreedName.setText(breedArray[1]);
        thirdBreedName.setText(breedArray[2]);
        fourthBreedName.setText(breedArray[3]);
    }

    public void showSnackBar(String message) {
        Snackbar.make(findViewById(R.id.tvMaleCount), message, Snackbar.LENGTH_LONG).setTextColor(ContextCompat.getColor(this, R.color.redish1)).show();
    }
}