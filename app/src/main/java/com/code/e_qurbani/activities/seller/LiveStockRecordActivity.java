package com.code.e_qurbani.activities.seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.code.e_qurbani.R;
import com.code.e_qurbani.chat.ChatBotScreen;
import com.code.e_qurbani.utils.Constant;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LiveStockRecordActivity extends AppCompatActivity {
    MaterialCardView cardCattle, cardGoat, cardSheep,cardCamel;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_stock_record);
        initViews();

        fab = findViewById(R.id.fab);
        cardCattle.setOnClickListener(click -> {
            startActivity(new Intent(this, LiveStockDetailRecord.class).putExtra(Constant.ANIMAL, Constant.COW));
        });

        fab.setOnClickListener(view -> {
            startActivity(new Intent(this, ChatBotScreen.class));
        });


        cardGoat.setOnClickListener(click -> {
            startActivity(new Intent(this, LiveStockDetailRecord.class).putExtra(Constant.ANIMAL, Constant.GOAT));
        });
        cardSheep.setOnClickListener(click -> {
            startActivity(new Intent(this, LiveStockDetailRecord.class).putExtra(Constant.ANIMAL, Constant.SHEEP));
        });
        cardCamel.setOnClickListener(click -> {
            startActivity(new Intent(this, LiveStockDetailRecord.class).putExtra(Constant.ANIMAL, Constant.CAMEL));
        });
    }
    private void initViews() {
        cardCattle = findViewById(R.id.cardCattle);
        cardGoat = findViewById(R.id.cardGoat);
        cardSheep = findViewById(R.id.cardSheep);
        cardCamel = findViewById(R.id.camelProfile);
    }
}