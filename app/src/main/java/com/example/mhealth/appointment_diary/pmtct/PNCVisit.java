package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mhealth.appointment_diary.R;

public class PNCVisit extends AppCompatActivity {

    String[] clientVisitType = {"", "Labor and Delivery", "ANC", "PNC"};
    String[] ModeDelivery = {"", "Spontaneous Vaginal Delivery (SVD)", "Cesarean Section (CS)", "Breech Delivery",  "Assisted Vaginal Delivery"};
    String[] placeDelivery = {"", "Home", "Facility", "Born before Arrival"};
    String[] DeliveryOutcome = {"", "Penta", "PSV"};
    String[] MothersOutcome = {"", "Alive", "Dead"};
    String[] MotherTested = {"", "Yes", "No"};
    String[] BabyDelivered = {"", "Live Birth", "Fresh Still Birth", "Macerated Still Birth"};
    String[] BabySex = {"", "Male", "Female"};

    Spinner clientVisitTypeS,  ModeDeliveryS, placeDeliveryS, DeliveryOutcomeS, MothersOutcomeS, MotherTestedS, BabyDeliveredS,  BabySexS;
    private String CLIENT_VISIT_TYPE = "";
    private String MODE_DELIVERY = "";
    private String PLACE_DELIVERY = "";
    private String DELIVERY_OUTCOME = "";
    private String MOTHERS_OUTCOME = "";
    private String MOTHER_TESTED = "";
    private String BABY_DELIVERED = "";
    private String BABY_SEX = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pncvisit);
        //clientVisitTypeS =(Spinner) findViewById(R.id.clientVisitspinner);
        ModeDeliveryS = (Spinner) findViewById(R.id.deliveryMode);
        placeDeliveryS =(Spinner) findViewById(R.id.DeliveryPlace);
        MothersOutcomeS =(Spinner) findViewById(R.id.mothersOutcome);
        DeliveryOutcomeS =(Spinner) findViewById(R.id.deliveryOutcome);

        MotherTestedS =(Spinner) findViewById(R.id.motherTested);
        BabyDeliveredS =(Spinner) findViewById(R.id.Babydelivered);
       // BabySexS=(Spinner) findViewById(R.id.babySex);




        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("PNC Visit");

        }
        catch(Exception e){


        }
        //client visit type
       /* ArrayAdapter<String> clientvisitAdapter = new ArrayAdapter<String>(PNCVisit.this, android.R.layout.simple_spinner_item, clientVisitType);
        clientvisitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientVisitTypeS.setAdapter(clientvisitAdapter);

        clientVisitTypeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CLIENT_VISIT_TYPE = clientVisitType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        //Mode delivery
        ArrayAdapter<String> ModedeliveryAdapter = new ArrayAdapter<String>(PNCVisit.this, android.R.layout.simple_spinner_item, ModeDelivery);
        ModedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      ModeDeliveryS.setAdapter(ModedeliveryAdapter);

       ModeDeliveryS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               MODE_DELIVERY = ModeDelivery[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       //placeDelivery
        ArrayAdapter<String> PlacedeliveryAdapter = new ArrayAdapter<String>(PNCVisit.this, android.R.layout.simple_spinner_item, placeDelivery);
        PlacedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeDeliveryS.setAdapter(PlacedeliveryAdapter);

        placeDeliveryS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                PLACE_DELIVERY = placeDelivery[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Delivery Outcome

        ArrayAdapter<String> OutcomedeliveryAdapter = new ArrayAdapter<String>(PNCVisit.this, android.R.layout.simple_spinner_item, DeliveryOutcome);
        PlacedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DeliveryOutcomeS.setAdapter(OutcomedeliveryAdapter);

     DeliveryOutcomeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               DELIVERY_OUTCOME = DeliveryOutcome[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
     //MothersOutcome

        ArrayAdapter<String> MotherOutcomeAdapter = new ArrayAdapter<String>(PNCVisit.this, android.R.layout.simple_spinner_item, MothersOutcome);
        PlacedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       MothersOutcomeS.setAdapter(MotherOutcomeAdapter);

        MothersOutcomeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              MOTHERS_OUTCOME = MothersOutcome[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // MotherTested

        ArrayAdapter<String> MotherTestedAdapter = new ArrayAdapter<String>(PNCVisit.this, android.R.layout.simple_spinner_item, MotherTested);
        PlacedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MotherTestedS.setAdapter(MotherTestedAdapter);

        MotherTestedS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MOTHER_TESTED = MotherTested[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //BabyDelivered
        ArrayAdapter<String> BabyDeliveredAdapter = new ArrayAdapter<String>(PNCVisit.this, android.R.layout.simple_spinner_item, BabyDelivered);
       BabyDeliveredAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabyDeliveredS.setAdapter(BabyDeliveredAdapter);

        BabyDeliveredS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_DELIVERED = BabyDelivered[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Baby sex

       /* ArrayAdapter<String> BabySexAdapter = new ArrayAdapter<String>(PNCVisit.this, android.R.layout.simple_spinner_item, BabySex);
        BabySexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabySexS.setAdapter(BabySexAdapter);

        BabySexS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_SEX = BabySex[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/






    }
}