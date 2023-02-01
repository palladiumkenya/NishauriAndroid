package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.R;

public class LaborAndDeliveryStart extends AppCompatActivity {
    String[] clientVisitType = {"", "Labor and Delivery", "ANC", "PNC"};
    String[] ModeDelivery = {"", "Spontaneous Vaginal Delivery (SVD)", "Cesarean Section (CS)", "Breech Delivery",  "Assisted Vaginal Delivery"};
    String[] placeDelivery = {"", "Home", "Facility", "Born before Arrival"};
    String[] DeliveryOutcome = {"", "Single", "Twins","Triplets", "Quadruplets", "Quintuplets"};
    String[] MothersOutcome = {"", "Alive", "Dead"};
    String[] MotherTested = {"", "Yes", "No"};
    String[] BabyDelivered = {"", "Live Birth", "Fresh Still Birth", "Macerated Still Birth"};
    String[] BabySex = {"", "Male", "Female"};
    String[] CurrentRe = {"", "TDF+3TC+EFV", "TDF+3TC+DTG", "TDF+3TC+DTG", "AZT+3TC+NVP", " AZT+3TC+EFV", "ABC+3TC+NVP", "ABC+3TC+EFV", " ABC+3TC+DTG", "ABC+3TC+LPV/r", " AZT+3TC+LPV/r+ RTV", "ART5TDF+3TC +ATV/r","ABC+3TC+DTG", "ABC+3TC+DTG", "ABC+3TC+ATV/r", "AZT+3TC+ATV/r", "AZT+3TC+DRV/r"};
    String[] Immunization = {"", "Penta", "PSV"};

    Spinner clientVisitTypeS,  ModeDeliveryS, placeDeliveryS, DeliveryOutcomeS, MothersOutcomeS, MotherTestedS, BabyDeliveredS,BabyDeliveredS2,BabyDeliveredS3,BabyDeliveredS4,BabyDeliveredS5,  BabySexS, currentS, ImmunizationS;
    private String CLIENT_VISIT_TYPE = "";
    private String MODE_DELIVERY = "";
    private String PLACE_DELIVERY = "";
    private String DELIVERY_OUTCOME = "";
    private String MOTHERS_OUTCOME = "";
    private String MOTHER_TESTED = "";
    private String BABY_DELIVERED = "";
    private String BABY_DELIVERED2 = "";
    private String BABY_DELIVERED3 = "";
    private String BABY_DELIVERED4 = "";
    private String BABY_DELIVERED5 = "";
    private String BABY_SEX = "";
    private String CURRENT_R = "";
    private String IMMNUNIZATION = "";
    LinearLayout diedlay, singleLay, twinLay,tripleLay, quadlay, fifthlay,diedlay2,diedlay3,diedlay4, diedlay5,livetexts,livetexts2,livetexts3,livetexts4,livetexts5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor_and_delivery_start);
        diedlay=(LinearLayout)findViewById(R.id.diededits);
        diedlay2=(LinearLayout)findViewById(R.id.diededits2);
        diedlay3=(LinearLayout)findViewById(R.id.diededits3);
        diedlay4=(LinearLayout)findViewById(R.id.diededits4);
        diedlay5=(LinearLayout)findViewById(R.id.diededits5);
        singleLay=(LinearLayout)findViewById(R.id.singleLay);
        twinLay=(LinearLayout)findViewById(R.id.twinLay);
        tripleLay=(LinearLayout)findViewById(R.id.tripLay);
        quadlay=(LinearLayout)findViewById(R.id.quadLay);
        fifthlay=(LinearLayout)findViewById(R.id.fifthLay);

        livetexts=(LinearLayout)findViewById(R.id.livetexts);
        livetexts2=(LinearLayout)findViewById(R.id.livetexts2);
        livetexts3=(LinearLayout)findViewById(R.id.livetexts3);
        livetexts4=(LinearLayout)findViewById(R.id.livetexts4);
        livetexts5=(LinearLayout)findViewById(R.id.livetexts5);

        ModeDeliveryS = (Spinner) findViewById(R.id.deliveryMode);
        placeDeliveryS = (Spinner) findViewById(R.id.DeliveryPlace);
        MothersOutcomeS = (Spinner) findViewById(R.id.mothersOutcome);
        DeliveryOutcomeS = (Spinner) findViewById(R.id.deliveryOutcome);

        MotherTestedS = (Spinner) findViewById(R.id.motherTested);
        BabyDeliveredS = (Spinner) findViewById(R.id.BabyDelivered1);
        BabyDeliveredS2 = (Spinner) findViewById(R.id.BabyDelivered2);
        BabyDeliveredS3 = (Spinner) findViewById(R.id.BabyDelivered3);
        BabyDeliveredS4 = (Spinner) findViewById(R.id.BabyDelivered4);
        BabyDeliveredS5 = (Spinner) findViewById(R.id.BabyDelivered5);
        currentS = (Spinner) findViewById(R.id. motherCurrentRegimen1);

         BabySexS=(Spinner) findViewById(R.id.BabySex1);
      //  ImmunizationS=(Spinner) findViewById(R.id.BabyImmunization1);


        try {
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Labor and Delivery Started");

        } catch (Exception e) {


        }
        ArrayAdapter<String> ModedeliveryAdapter = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, ModeDelivery);
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
        ArrayAdapter<String> PlacedeliveryAdapter = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, placeDelivery);
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

        ArrayAdapter<String> OutcomedeliveryAdapter = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, DeliveryOutcome);
        PlacedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DeliveryOutcomeS.setAdapter(OutcomedeliveryAdapter);

        DeliveryOutcomeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DELIVERY_OUTCOME = DeliveryOutcome[position];

                if (DELIVERY_OUTCOME.contentEquals("Single")){
                    Toast.makeText(getApplicationContext(), "sinle", Toast.LENGTH_SHORT).show();
                    singleLay.setVisibility(View.VISIBLE);
                    twinLay.setVisibility(View.GONE);
                    tripleLay.setVisibility(View.GONE);
                   quadlay.setVisibility(View.GONE);
                    fifthlay.setVisibility(View.GONE);
                }
                else if (DELIVERY_OUTCOME.contentEquals("Twins")){
                    singleLay.setVisibility(View.VISIBLE);
                    twinLay.setVisibility(View.VISIBLE);
                    tripleLay.setVisibility(View.GONE);
                    quadlay.setVisibility(View.GONE);
                    fifthlay.setVisibility(View.GONE);
                }

                else if (DELIVERY_OUTCOME.contentEquals("Triplets")){
                    singleLay.setVisibility(View.VISIBLE);
                    twinLay.setVisibility(View.VISIBLE);
                    tripleLay.setVisibility(View.VISIBLE);
                    quadlay.setVisibility(View.GONE);
                    fifthlay.setVisibility(View.GONE);
                }
                else if (DELIVERY_OUTCOME.contentEquals("Quadruplets")){
                    singleLay.setVisibility(View.VISIBLE);
                    twinLay.setVisibility(View.VISIBLE);
                    tripleLay.setVisibility(View.VISIBLE);
                    quadlay.setVisibility(View.VISIBLE);
                    fifthlay.setVisibility(View.GONE);
                }

                else if (DELIVERY_OUTCOME.contentEquals("Quintuplets")){
                    singleLay.setVisibility(View.VISIBLE);
                    twinLay.setVisibility(View.VISIBLE);
                    tripleLay.setVisibility(View.VISIBLE);
                    quadlay.setVisibility(View.VISIBLE);
                    fifthlay.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //MothersOutcome

        ArrayAdapter<String> MotherOutcomeAdapter = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, MothersOutcome);
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

        ArrayAdapter<String> MotherTestedAdapter = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, MotherTested);
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

        //BabyDelivered1
        ArrayAdapter<String> BabyDeliveredAdapter = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, BabyDelivered);
        BabyDeliveredAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabyDeliveredS.setAdapter(BabyDeliveredAdapter);

        BabyDeliveredS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_DELIVERED = BabyDelivered[position];

                if (BABY_DELIVERED.contentEquals("Macerated Still Birth")){
                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);


                    diedlay.setVisibility(View.VISIBLE);
                    diedlay2.setVisibility(View.GONE);
                    diedlay3.setVisibility(View.GONE);
                    diedlay4.setVisibility(View.GONE);
                    diedlay5.setVisibility(View.GONE);

                }
                else if (BABY_DELIVERED.contentEquals("Live Birth")){
                    diedlay.setVisibility(View.GONE);
                    diedlay2.setVisibility(View.GONE);
                    diedlay3.setVisibility(View.GONE);
                    diedlay4.setVisibility(View.GONE);
                    diedlay5.setVisibility(View.GONE);


                    livetexts.setVisibility(View.VISIBLE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                }
                else if (BABY_DELIVERED.contentEquals("Fresh Still Birth")){
                    diedlay.setVisibility(View.GONE);
                    diedlay2.setVisibility(View.GONE);
                    diedlay3.setVisibility(View.GONE);
                    diedlay4.setVisibility(View.GONE);
                    diedlay5.setVisibility(View.GONE);

                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //BabyDelivered2
        ArrayAdapter<String> BabyDeliveredAdapter2 = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, BabyDelivered);
        BabyDeliveredAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabyDeliveredS2.setAdapter(BabyDeliveredAdapter);

        BabyDeliveredS2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_DELIVERED2 = BabyDelivered[position];

                if (BABY_DELIVERED2.contentEquals("Macerated Still Birth")){
                    diedlay2.setVisibility(View.VISIBLE);


                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                }
                else if (BABY_DELIVERED.contentEquals("Live Birth")){
                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.VISIBLE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);

                    diedlay2.setVisibility(View.GONE);
                }
                else if (BABY_DELIVERED.contentEquals("Fresh Still Birth")){
                    diedlay2.setVisibility(View.GONE);
                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //BabyDelivered3
        ArrayAdapter<String> BabyDeliveredAdapter3= new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, BabyDelivered);
        BabyDeliveredAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabyDeliveredS3.setAdapter(BabyDeliveredAdapter3);

        BabyDeliveredS3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_DELIVERED3 = BabyDelivered[position];

                if (BABY_DELIVERED3.contentEquals("Macerated Still Birth")){
                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    diedlay3.setVisibility(View.VISIBLE);
                }
                else if (BABY_DELIVERED3.contentEquals("Live Birth")){

                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.VISIBLE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);

                    diedlay3.setVisibility(View.GONE);
                }
                else if (BABY_DELIVERED3.contentEquals("Fresh Still Birth")){

                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    diedlay3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//BabyDelivered4
        ArrayAdapter<String> BabyDeliveredAdapter4= new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, BabyDelivered);
        BabyDeliveredAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabyDeliveredS4.setAdapter(BabyDeliveredAdapter4);

        BabyDeliveredS4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_DELIVERED4 = BabyDelivered[position];

                if (BABY_DELIVERED4.contentEquals("Macerated Still Birth")){
                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);

                    diedlay4.setVisibility(View.VISIBLE);
                }
                else if (BABY_DELIVERED4.contentEquals("Live Birth")){

                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.VISIBLE);
                    livetexts5.setVisibility(View.GONE);
                    diedlay4.setVisibility(View.GONE);
                }
                else if (BABY_DELIVERED4.contentEquals("Fresh Still Birth")){

                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    diedlay4.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //BabyDelivered5
        ArrayAdapter<String> BabyDeliveredAdapter5= new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, BabyDelivered);
        BabyDeliveredAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabyDeliveredS5.setAdapter(BabyDeliveredAdapter5);

        BabyDeliveredS5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_DELIVERED5 = BabyDelivered[position];

                if (BABY_DELIVERED5.contentEquals("Macerated Still Birth")){

                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    diedlay5.setVisibility(View.VISIBLE);
                }
                else if (BABY_DELIVERED5.contentEquals("Live Birth")){

                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    diedlay5.setVisibility(View.VISIBLE);
                }
                else if (BABY_DELIVERED5.contentEquals("Fresh Still Birth")){

                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    diedlay5.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //current reg
        ArrayAdapter<String> CurrentregAdapter = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, CurrentRe);
        CurrentregAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentS.setAdapter(CurrentregAdapter);

        currentS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CURRENT_R = CurrentRe[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //Baby sex

        ArrayAdapter<String> BabySexAdapter = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, BabySex);
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
        });

        //immunization
       /* ArrayAdapter<String> ImmunizationAdapter = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, Immunization);
        ImmunizationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       ImmunizationS.setAdapter(ImmunizationAdapter);

        ImmunizationS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
