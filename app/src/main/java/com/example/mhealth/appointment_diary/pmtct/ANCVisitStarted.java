package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.mhealth.appointment_diary.R;

public class ANCVisitStarted extends AppCompatActivity {

    String[] clientIs = {"","Breastfeeding", "Not Breastfeeding", "Pregnant", "Pregnant and Breastfeeding"};
    String[] hivResults = {"", "Unknown", "Negative", "Positive"};
    String[] syphilis = {"","Negative", "Positive", "Requested", "Not Requested", "Poor Sample Quality"};
    Spinner clientIsS, hivResultsS, SyphilisS;
    private String CLIENT_IS = "";
    private String HIV_RESULTS = "";
    private String SYPHILIS = "";
    LinearLayout pregnant, positiveLayout,partnerLayout,positiveselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancvisit_started);
        pregnant = (LinearLayout) findViewById(R.id.pregnant);
        positiveLayout = (LinearLayout) findViewById(R.id.positiveLayout);
        partnerLayout = (LinearLayout) findViewById(R.id.partnerLayout);
        positiveselected= (LinearLayout) findViewById(R.id.positiveSelected);

        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("ANC Visit Started");

        }
        catch(Exception e){

        }
        clientIsS = (Spinner) findViewById(R.id.ClientSpinner);
        hivResultsS =(Spinner) findViewById(R.id.hivResults1);
        SyphilisS =(Spinner) findViewById(R.id.SyphilisSpinner);

        //Client is
        ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(ANCVisitStarted.this, android.R.layout.simple_spinner_item, clientIs);
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientIsS.setAdapter(clientAdapter);

        clientIsS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             CLIENT_IS = clientIs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // hiv results
        ArrayAdapter<String> resultsAdapter = new ArrayAdapter<String>(ANCVisitStarted.this, android.R.layout.simple_spinner_item, hivResults);
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hivResultsS.setAdapter(resultsAdapter);

        hivResultsS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HIV_RESULTS = clientIs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Syphilis

        ArrayAdapter<String> syphilisAdapter = new ArrayAdapter<String>(ANCVisitStarted.this, android.R.layout.simple_spinner_item, syphilis);
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SyphilisS.setAdapter(syphilisAdapter);

      SyphilisS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               SYPHILIS = syphilis[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
}