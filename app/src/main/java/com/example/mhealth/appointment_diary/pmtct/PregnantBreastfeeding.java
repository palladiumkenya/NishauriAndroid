package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mhealth.appointment_diary.R;

public class PregnantBreastfeeding extends AppCompatActivity {

    CardView card_hiv, card_pregnant, card_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnant_breastfeeding);

        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("PMTCT");

        }
        catch(Exception e){


        }

        card_hiv = (CardView) findViewById(R.id.hiv);
        card_pregnant = (CardView) findViewById(R.id.card_pregnant);
        card_exit = (CardView) findViewById(R.id.card_exit);

        card_hiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(PregnantBreastfeeding.this, ANCVisit.class);
                startActivity(intent);

            }
        });

        card_pregnant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(PregnantBreastfeeding.this, PNCVisit.class);
                startActivity(intent);


            }
        });

        card_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(PregnantBreastfeeding.this, ExitClient.class);
                startActivity(intent);

            }
        });







    }
}