package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mhealth.appointment_diary.MainOptions;
import com.example.mhealth.appointment_diary.PmtctActivity;
import com.example.mhealth.appointment_diary.R;

public class PMTCT1 extends AppCompatActivity {

    CardView card_hiv, card_pregnant, card_exit, card_delivery,card_hei;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(PMTCT1.this, MainOptions.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmtct1);

        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
           // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("PMTCT");

        }
        catch(Exception e){


        }

        card_hiv = (CardView) findViewById(R.id.hiv);
        card_pregnant = (CardView) findViewById(R.id.card_pregnant);
        card_exit = (CardView) findViewById(R.id.card_exit1);
        card_delivery = (CardView) findViewById(R.id.card_delivery);
        card_hei = (CardView) findViewById(R.id.hei);

        card_hiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(PMTCT1.this, ANCVisit.class);
                startActivity(intent);

            }
        });
        card_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(PMTCT1.this, LaborAndDelivery.class);
                startActivity(intent);

            }
        });

        card_hei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(PMTCT1.this,  PmtctActivity.class);
                startActivity(intent);

            }
        });

        card_pregnant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(PMTCT1.this, PNCVisit.class);
                startActivity(intent);


            }
        });

        card_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(PMTCT1.this, ExitClient.class);
                startActivity(intent);

            }
        });







    }
}