package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

import libs.mjn.fieldset.FieldSetView;

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
    String[] hivResults = {"", "Unknown", "Negative", "Positive"};
    Button saveLD;
    FieldSetView maceratedfield1,maceratedfield2,maceratedfield3,maceratedfield4,maceratedfield5, livelayfield1,livelayfield2,livelayfield3,livelayfield4,livelayfield5;


    Spinner clientVisitTypeS,  ModeDeliveryS, placeDeliveryS, DeliveryOutcomeS, MothersOutcomeS, MotherTestedS, BabyDeliveredS,BabyDeliveredS2,BabyDeliveredS3,BabyDeliveredS4,BabyDeliveredS5,  BabySexS,BabySexS2, BabySexS3, BabySexS4, BabySexS5, currentS, ImmunizationS, hivResultsS;
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
    private String BABY_SEX2 = "";
    private String BABY_SEX3 = "";
    private String BABY_SEX4 = "";
    private String BABY_SEX5 = "";
    private String CURRENT_R = "";
    private String IMMNUNIZATION = "";

    CheckInternet chkinternet;
    AccessServer acs;
    String newCC;

    private String HIV = "";
    private String TESTED = "";
    LinearLayout diedlay, singleLay, twinLay,tripleLay, quadlay, fifthlay,diedlay2,diedlay3,diedlay4, diedlay5,livetextss,livetextss2,livetextss3,livetextss4,livetextss5,hivlay;
    TextInputLayout livetexts,livetexts2,livetexts3,livetexts4,livetexts5;
    EditText ANCVisit_NO, deliveryDate, Datedied1, deathcause1,BabysDOB1, Datedied2,deathcause2,BabysDOB2, Datedied3,deathcause3,BabysDOB3, Datedied4,deathcause4,BabysDOB4,  Datedied5,deathcause5,BabysDOB5;
    String BabyDeliveredS_code,BabyDeliveredS2_code,BabyDeliveredS3_code,BabyDeliveredS4_code,BabyDeliveredS5_code,ModeDeliveryS_code, placeDeliveryS_code, DeliveryOutcomeS_code, MothersOutcomeS_code, MotherTestedS_code, BabySexS_code,BabySexS2_code,BabySexS3_code,BabySexS4_code,BabySexS5_code, currentS_code, ImmunizationS_code, hivResultsS_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor_and_delivery_start);
        acs = new AccessServer(LaborAndDeliveryStart.this);
        chkinternet = new CheckInternet(LaborAndDeliveryStart.this);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newCC= null;
            } else {
                newCC= extras.getString("Client_CCC");
            }
        } else {
            newCC= (String) savedInstanceState.getSerializable("Client_CCC");
        }

      //  maceratedfield1=(FieldSetView) findViewById(R.id.maceratedfield1);
        //livelayfield1=(FieldSetView) findViewById(R.id.livelayfield1);
                ANCVisit_NO=(EditText) findViewById(R.id.ANCVisit_NO);
                saveLD=(Button) findViewById(R.id.btn_submit_reg);

                deliveryDate=(EditText) findViewById(R.id.deliveryDate);

                Datedied1=(EditText) findViewById(R.id.Datedied1);
                deathcause1=(EditText) findViewById(R.id.deathcause1);
                BabysDOB1=(EditText) findViewById(R.id.BabysDOB1);

                Datedied2=(EditText) findViewById(R.id.Datedied2);
                deathcause2=(EditText) findViewById(R.id.deathcause2);
                BabysDOB2=(EditText) findViewById(R.id.BabysDOB2);

                Datedied3=(EditText) findViewById(R.id.Datedied3);
                deathcause3=(EditText) findViewById(R.id.deathcause3);
                BabysDOB3=(EditText) findViewById(R.id.BabysDOB3);

                Datedied4=(EditText) findViewById(R.id.Datedied4);
                deathcause4=(EditText) findViewById(R.id.deathcause4);
                BabysDOB4=(EditText) findViewById(R.id.BabysDOB4);

                Datedied5=(EditText) findViewById(R.id.Datedied5);
                deathcause5=(EditText) findViewById(R.id.deathcause5);
                BabysDOB5=(EditText) findViewById(R.id.BabysDOB5);

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
        hivlay=(LinearLayout)findViewById(R.id.hivlay);

        livetexts=(TextInputLayout) findViewById(R.id.livetexts);
        livetexts2=(TextInputLayout) findViewById(R.id.livetexts2);
        livetexts3=(TextInputLayout) findViewById(R.id.livetexts3);
        livetexts4=(TextInputLayout) findViewById(R.id.livetexts4);
        livetexts5=(TextInputLayout) findViewById(R.id.livetexts5);


        livetextss=(LinearLayout) findViewById(R.id.livetextss);
        livetextss2=(LinearLayout) findViewById(R.id.livetextss2);
        livetextss3=(LinearLayout) findViewById(R.id.livetextss3);
        livetextss4=(LinearLayout) findViewById(R.id.livetextss4);
        livetextss5=(LinearLayout) findViewById(R.id.livetextss5);


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
        hivResultsS = (Spinner) findViewById(R.id.hivResults1);


         BabySexS=(Spinner) findViewById(R.id.BabySex1);
        BabySexS2=(Spinner) findViewById(R.id.BabySex2);
        BabySexS3=(Spinner) findViewById(R.id.BabySex3);
        BabySexS4=(Spinner) findViewById(R.id.BabySex4);
        BabySexS5=(Spinner) findViewById(R.id.BabySex5);

        //delivery_date
        deliveryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(LaborAndDeliveryStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        deliveryDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });
        //Datedied1
        Datedied1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(LaborAndDeliveryStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        Datedied1.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });

        //Datedied2
        Datedied2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(LaborAndDeliveryStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        Datedied2.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });
        //Datedied3
        Datedied3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(LaborAndDeliveryStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        Datedied3.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });
        //Datedied4
        Datedied4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(LaborAndDeliveryStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        Datedied4.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });
        //Datedied5
        Datedied5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(LaborAndDeliveryStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        Datedied5.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });
        //BabysDOB1
        BabysDOB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(LaborAndDeliveryStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        BabysDOB1.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });

        //BabysDOB2
        BabysDOB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(LaborAndDeliveryStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        BabysDOB2.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });
        //BabysDOB3
        BabysDOB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(LaborAndDeliveryStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        BabysDOB3.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });

        //BabysDOB4
        BabysDOB4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(LaborAndDeliveryStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        BabysDOB4.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });

        //BabysDOB1
        BabysDOB5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(LaborAndDeliveryStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        BabysDOB5.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });
      //  ImmunizationS=(Spinner) findViewById(R.id.BabyImmunization1);


        try {
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Labor and Delivery Details");

        } catch (Exception e) {


        }
        ArrayAdapter<String> ModedeliveryAdapter = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, ModeDelivery);
        ModedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ModeDeliveryS.setAdapter(ModedeliveryAdapter);

        ModeDeliveryS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MODE_DELIVERY = ModeDelivery[position];
                ModeDeliveryS_code=Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //hivreluts
        ArrayAdapter<String> HIVAdapter = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, hivResults);
        ModedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hivResultsS.setAdapter(HIVAdapter);

        hivResultsS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HIV = hivResults[position];
                hivResultsS_code=Integer.toString(position);
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
               placeDeliveryS_code=Integer.toString(position);
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
                MothersOutcomeS_code=Integer.toString(position);

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
                MotherTestedS_code=Integer.toString(position);

                if ( MOTHER_TESTED.contentEquals("Yes")){
                   // Toast.makeText(LaborAndDeliveryStart.this, "yes", Toast.LENGTH_SHORT).show();
                    hivlay.setVisibility(View.VISIBLE);
                }
               else if ( MOTHER_TESTED.contentEquals("No")){
                    hivlay.setVisibility(View.GONE);
                }

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
                BabyDeliveredS_code=Integer.toString(position);

                if (BABY_DELIVERED.contentEquals("Macerated Still Birth") || BABY_DELIVERED.contentEquals("Fresh Still Birth")){
                    livetexts.setVisibility(View.GONE);
                    livetextss.setVisibility(View.GONE);
                    diedlay.setVisibility(View.VISIBLE);
                    //maceratedfield1.setVisibility(View.VISIBLE);
                   /* livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);


                    livetextss2.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);*/


                    /*diedlay2.setVisibility(View.GONE);
                    diedlay3.setVisibility(View.GONE);
                    diedlay4.setVisibility(View.GONE);
                    diedlay5.setVisibility(View.GONE);*/

                }
                else if (BABY_DELIVERED.contentEquals("Live Birth")){
                    diedlay.setVisibility(View.GONE);
                    //livelayfield1.setVisibility(View.VISIBLE);

                    livetexts.setVisibility(View.VISIBLE);
                    livetextss.setVisibility(View.VISIBLE);
                    /*diedlay2.setVisibility(View.GONE);
                    diedlay3.setVisibility(View.GONE);
                    diedlay4.setVisibility(View.GONE);
                    diedlay5.setVisibility(View.GONE);*/

                    /*livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);


                    livetextss2.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);*/
                }
                /*else if (BABY_DELIVERED.contentEquals("Fresh Still Birth")){
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
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //BabyDelivered2
        ArrayAdapter<String> BabyDeliveredAdapter2 = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, BabyDelivered);
        BabyDeliveredAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabyDeliveredS2.setAdapter(BabyDeliveredAdapter2);

        BabyDeliveredS2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_DELIVERED2 = BabyDelivered[position];
                BabyDeliveredS2_code=Integer.toString(position);


                if (BABY_DELIVERED2.contentEquals("Macerated Still Birth")|| BABY_DELIVERED2.contentEquals("Fresh Still Birth")){
                    diedlay2.setVisibility(View.VISIBLE);
                    livetexts2.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);


                    /*livetexts.setVisibility(View.GONE);

                     livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);

                    livetextss.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);*/
                }
                else if (BABY_DELIVERED2.contentEquals("Live Birth")){

                    livetexts2.setVisibility(View.VISIBLE);
                    livetextss2.setVisibility(View.VISIBLE);
                    diedlay2.setVisibility(View.GONE);
                   /* livetexts.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);

                    livetextss.setVisibility(View.GONE);

                    livetextss3.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);

                    ;*/
                }
               /* else if (BABY_DELIVERED.contentEquals("Fresh Still Birth")){
                    diedlay2.setVisibility(View.GONE);
                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                }*/
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
                BabyDeliveredS3_code=Integer.toString(position);

                if (BABY_DELIVERED3.contentEquals("Macerated Still Birth")|| BABY_DELIVERED3.contentEquals("Fresh Still Birth")){
                    /*livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);

                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);

                    livetextss.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);*/
                    livetextss3.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    diedlay3.setVisibility(View.VISIBLE);
                }
                else if (BABY_DELIVERED3.contentEquals("Live Birth")){
                    livetexts3.setVisibility(View.VISIBLE);
                    livetextss3.setVisibility(View.VISIBLE);
                    diedlay3.setVisibility(View.GONE);

                   /* livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);


                    livetextss.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);*/

                }
               /* else if (BABY_DELIVERED3.contentEquals("Fresh Still Birth")){

                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    diedlay3.setVisibility(View.GONE);
                }*/
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
                BabyDeliveredS4_code=Integer.toString(position);

                if (BABY_DELIVERED4.contentEquals("Macerated Still Birth")|| BABY_DELIVERED4.contentEquals("Fresh Still Birth")){
                    /*livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);

                    livetextss.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);*/

                    diedlay4.setVisibility(View.VISIBLE);
                    livetexts4.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);
                }
                else if (BABY_DELIVERED4.contentEquals("Live Birth")){
                    livetexts4.setVisibility(View.VISIBLE);
                    livetextss4.setVisibility(View.VISIBLE);
                    diedlay4.setVisibility(View.GONE);

                    /*livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);

                    livetexts5.setVisibility(View.GONE);

                    livetextss.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);*/
                }
                /*else if (BABY_DELIVERED4.contentEquals("Fresh Still Birth")){

                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    diedlay4.setVisibility(View.GONE);
                }*/
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
                BabyDeliveredS5_code=Integer.toString(position);

                if (BABY_DELIVERED5.contentEquals("Macerated Still Birth")|| BABY_DELIVERED5.contentEquals("Fresh Still Birth")){

                    /*livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);


                    livetextss.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);
                    */

                    diedlay5.setVisibility(View.VISIBLE);
                    livetexts5.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);
                }
                else if (BABY_DELIVERED5.contentEquals("Live Birth")){

                    /*livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);

                    livetextss.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);*/
                    livetextss5.setVisibility(View.VISIBLE);
                    livetexts5.setVisibility(View.VISIBLE);

                    diedlay5.setVisibility(View.GONE);
                }
               /* else if (BABY_DELIVERED5.contentEquals("Fresh Still Birth")){

                    livetexts.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    diedlay5.setVisibility(View.GONE);
                }*/
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
                DeliveryOutcomeS_code=Integer.toString(position);

                if (DELIVERY_OUTCOME.contentEquals("Single")){
                    //Toast.makeText(getApplicationContext(), "sinle", Toast.LENGTH_SHORT).show();
                    singleLay.setVisibility(View.VISIBLE);
                    twinLay.setVisibility(View.GONE);
                    tripleLay.setVisibility(View.GONE);
                    quadlay.setVisibility(View.GONE);
                    fifthlay.setVisibility(View.GONE);

                    // last logic
                    diedlay.setVisibility(View.GONE);
                    livetexts.setVisibility(View.GONE);
                    livetextss.setVisibility(View.GONE);

                    diedlay2.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);

                    diedlay3.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);

                    diedlay4.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);

                    diedlay5.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);


                    BABY_DELIVERED.contentEquals("");
                    BABY_DELIVERED2="";
                    BABY_DELIVERED3="";
                    BABY_DELIVERED4="";
                    BABY_DELIVERED5="";
                    BabyDeliveredS_code="";
                    //,BabyDeliveredS2_code,BabyDeliveredS3_code,BabyDeliveredS4_code,BabyDeliveredS5_code;

                }
                else if (DELIVERY_OUTCOME.contentEquals("Twins")){
                    singleLay.setVisibility(View.VISIBLE);
                    twinLay.setVisibility(View.VISIBLE);
                    tripleLay.setVisibility(View.GONE);
                    quadlay.setVisibility(View.GONE);
                    fifthlay.setVisibility(View.GONE);

                    // last logic
                    diedlay.setVisibility(View.GONE);
                    livetexts.setVisibility(View.GONE);
                    livetextss.setVisibility(View.GONE);

                    diedlay2.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);

                    diedlay3.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);

                    diedlay4.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);

                    diedlay5.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);
                    BABY_DELIVERED2.contentEquals("");
                }

                else if (DELIVERY_OUTCOME.contentEquals("Triplets")){
                    singleLay.setVisibility(View.VISIBLE);
                    twinLay.setVisibility(View.VISIBLE);
                    tripleLay.setVisibility(View.VISIBLE);
                    quadlay.setVisibility(View.GONE);
                    fifthlay.setVisibility(View.GONE);


                    // last logic
                    diedlay.setVisibility(View.GONE);
                    livetexts.setVisibility(View.GONE);
                    livetextss.setVisibility(View.GONE);

                    diedlay2.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);

                    diedlay3.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);

                    diedlay4.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);

                    diedlay5.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);
                }
                else if (DELIVERY_OUTCOME.contentEquals("Quadruplets")){
                    singleLay.setVisibility(View.VISIBLE);
                    twinLay.setVisibility(View.VISIBLE);
                    tripleLay.setVisibility(View.VISIBLE);
                    quadlay.setVisibility(View.VISIBLE);
                    fifthlay.setVisibility(View.GONE);

                    // last logic
                    diedlay.setVisibility(View.GONE);
                    livetexts.setVisibility(View.GONE);
                    livetextss.setVisibility(View.GONE);

                    diedlay2.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);

                    diedlay3.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);

                    diedlay4.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);

                    diedlay5.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);
                }

                else if (DELIVERY_OUTCOME.contentEquals("Quintuplets")){
                    singleLay.setVisibility(View.VISIBLE);
                    twinLay.setVisibility(View.VISIBLE);
                    tripleLay.setVisibility(View.VISIBLE);
                    quadlay.setVisibility(View.VISIBLE);
                    fifthlay.setVisibility(View.VISIBLE);

                    // last logic
                    diedlay.setVisibility(View.GONE);
                    livetexts.setVisibility(View.GONE);
                    livetextss.setVisibility(View.GONE);

                    diedlay2.setVisibility(View.GONE);
                    livetexts2.setVisibility(View.GONE);
                    livetextss2.setVisibility(View.GONE);

                    diedlay3.setVisibility(View.GONE);
                    livetexts3.setVisibility(View.GONE);
                    livetextss3.setVisibility(View.GONE);

                    diedlay4.setVisibility(View.GONE);
                    livetexts4.setVisibility(View.GONE);
                    livetextss4.setVisibility(View.GONE);

                    diedlay5.setVisibility(View.GONE);
                    livetexts5.setVisibility(View.GONE);
                    livetextss5.setVisibility(View.GONE);
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



        //Baby sex1
        ArrayAdapter<String> BabySexAdapter = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, BabySex);
        BabySexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabySexS.setAdapter(BabySexAdapter);

        BabySexS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_SEX = BabySex[position];
                BabySexS_code =Integer.toString(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Baby sex2
        ArrayAdapter<String> BabySexAdapter2 = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, BabySex);
        BabySexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabySexS2.setAdapter(BabySexAdapter2);

        BabySexS2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_SEX2 = BabySex[position];
                BabySexS2_code =Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Baby sex3
        ArrayAdapter<String> BabySexAdapter3 = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, BabySex);
        BabySexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabySexS3.setAdapter(BabySexAdapter3);

        BabySexS3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_SEX3 = BabySex[position];
                BabySexS3_code =Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Baby sex4
        ArrayAdapter<String> BabySexAdapter4 = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, BabySex);
        BabySexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabySexS4.setAdapter(BabySexAdapter3);

        BabySexS4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_SEX4 = BabySex[position];
                BabySexS4_code =Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Baby sex5
        ArrayAdapter<String> BabySexAdapter5 = new ArrayAdapter<String>(LaborAndDeliveryStart.this, android.R.layout.simple_spinner_item, BabySex);
        BabySexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabySexS5.setAdapter(BabySexAdapter5);

        BabySexS5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_SEX5 = BabySex[position];
                BabySexS5_code =Integer.toString(position);

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
        submitLD();

    }

    public void submitLD(){
        saveLD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ANCVisit_NO1=ANCVisit_NO.getText().toString();
                String saveLD1=saveLD.getText().toString();

               String  deliveryDate1= deliveryDate.getText().toString();

               String  Datedied11=Datedied1.getText().toString();
               String deathcause11=deathcause1.getText().toString();
               String  BabysDOB11=BabysDOB1.getText().toString();

               String Datedied22=Datedied2.getText().toString();
               String deathcause22=deathcause2.getText().toString();
               String BabysDOB22=BabysDOB2.getText().toString();

               String Datedied33=Datedied3.getText().toString();
               String deathcause33=deathcause3.getText().toString();
               String BabysDOB33=BabysDOB3.getText().toString();

               String Datedied44=Datedied4.getText().toString();
               String deathcause44=deathcause4.getText().toString();
               String   BabysDOB44=BabysDOB4.getText().toString();

               String Datedied55=Datedied5.getText().toString();
               String deathcause55=deathcause5.getText().toString();
               String  BabysDOB55=BabysDOB5.getText().toString();



                String LD_data =  newCC + "*" + ANCVisit_NO1 + "*" + MotherTestedS_code+ "*" + deliveryDate1 + "*" +ModeDeliveryS_code + "*" + placeDeliveryS_code+ "*" + DeliveryOutcomeS_code + "*" + BabyDeliveredS_code + "*" + Datedied11+ "*" +deathcause11+"*" + BabysDOB11 + "*"+ BabySexS_code + "*" + BabyDeliveredS2_code + "*" +Datedied22 + "*" + deathcause22 + "*" + BabysDOB22 + "*" +  BabySexS2_code+ "*" +BabyDeliveredS3_code + "*" + Datedied33+ "*" +  deathcause33 + "*" +BabysDOB33 + "*" + BabySexS3_code + "*" + BabyDeliveredS4_code + "*" + Datedied44+ "*" +deathcause44+"*" + BabysDOB44 + "*"+ BabySexS4_code+ "*" + BabyDeliveredS5_code + "*" + Datedied55+ "*" +deathcause55+"*" + BabysDOB55 + "*"+ BabySexS5_code+ "*"+ MothersOutcomeS_code;

                String enc = Base64Encoder.encryptString(LD_data);


                List<Activelogin> myl = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
                for (int x = 0; x < myl.size(); x++) {

                    String un = myl.get(x).getUname();
                    List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
                    for (int y = 0; y < myl2.size(); y++) {

                        String phne = myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                        acs.ANCPost("ld*" + enc, phne);



                        Toast.makeText(LaborAndDeliveryStart.this, "Labour and Delivery Details Saved Successful", Toast.LENGTH_SHORT).show();
                       // Log.e("", clientTreated_code + HepatitisB_code);

                    }


                }


            }
        });
    }
    }
