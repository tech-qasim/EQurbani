package com.code.e_qurbani.activities.seller;

import static com.code.e_qurbani.utils.Constant.ANIMAL;
import static com.code.e_qurbani.utils.Constant.CAMEL;
import static com.code.e_qurbani.utils.Constant.COW;
import static com.code.e_qurbani.utils.Constant.GOAT;
import static com.code.e_qurbani.utils.Constant.SHEEP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.code.e_qurbani.R;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.google.android.material.card.MaterialCardView;

public class ForSaleAnimalGateway extends AppCompatActivity {

    MaterialCardView crdBull;
    MaterialCardView crdGoat;
    MaterialCardView crdSheep;
    MaterialCardView crdCamel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_sale_animal_gateway);
        initViews();

        crdBull.setOnClickListener(view -> startActivity(new Intent(this, CowBullOnSale.class).putExtra(ANIMAL , COW)));
        crdGoat.setOnClickListener(view -> startActivity(new Intent(this, GoatForSale.class).putExtra(ANIMAL, GOAT)));
        crdSheep.setOnClickListener(view -> startActivity(new Intent(this, SheepOnSale.class).putExtra(ANIMAL, SHEEP)));
        crdCamel.setOnClickListener(view -> startActivity(new Intent(this, CamelOnSale.class).putExtra(ANIMAL, CAMEL)));
    }

    private void initViews() {
        crdBull = findViewById(R.id.crdCowBull);
        crdGoat = findViewById(R.id.crdGoat);
        crdSheep = findViewById(R.id.crdSheep);
        crdCamel = findViewById(R.id.crdCamel);
    }

    public void back(View view) {
        finish();
    }
}