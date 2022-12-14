package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mhealth.appointment_diary.R;

public class ExitClient extends AppCompatActivity {
    String[] MothersOutcome = {"", "Discharged", "Deceased", "Transfered to CCC", "Transfered to another Facility"};
    Spinner MothersOutcomeS;
    private String MOTHER_OUTCOME = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_client);
        MothersOutcomeS =(Spinner) findViewById(R.id.motherspinner);

        //client visit type
        ArrayAdapter<String> motherOutcomeAdapter = new ArrayAdapter<String>(ExitClient.this, android.R.layout.simple_spinner_item, MothersOutcome);
        motherOutcomeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       MothersOutcomeS.setAdapter(motherOutcomeAdapter);

        MothersOutcomeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               MOTHER_OUTCOME = MothersOutcome[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Exit Client");

        }
        catch(Exception e){


        }
    }
}