package com.code.e_qurbani;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;

import com.code.e_qurbani.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        LayerDrawable stars = (LayerDrawable) binding.ratingBar.getProgressDrawable();

        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);





    }

    public void Call(View v)
    {
        // This function is called when button is clicked.
        // Display ratings, which is required to be converted into string first.

        binding.textView2.setText("You Rated :"+String.valueOf(binding.ratingBar.getRating()));

    }
}