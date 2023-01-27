package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mhealth.appointment_diary.R;

public class ANCVisitStarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancvisit_started);

        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("ANC Visit Started");

        }
        catch(Exception e){

        }
    }
}