package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mhealth.appointment_diary.R;

public class HIVOutcome extends AppCompatActivity {
    String[] clientIs = {"", "Breastfeeding", "Not Breastfeeding", "Pregnant", "Pregnant and Breastfeeding"};
    String[] hivResults = {"", "Unknown", "Negative", "Positive"};
    Spinner clientIsS, hivResultsS;
    private String CLIENT_IS = "";
    private String HIV_RESULTS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hivoutcome);

        clientIsS = (Spinner) findViewById(R.id.ClientSpinner);
        hivResultsS =(Spinner) findViewById(R.id.hivResults);

//      Client is
        ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(HIVOutcome.this, android.R.layout.simple_spinner_item, clientIs);
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
        ArrayAdapter<String> resultsAdapter = new ArrayAdapter<String>(HIVOutcome.this, android.R.layout.simple_spinner_item, hivResults);
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





        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("HIV Outcome");

        }
        catch(Exception e){

        }


    }
}