package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.Progress.Progress;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PNCVisitStart extends AppCompatActivity {
    private boolean aztb, nvpb, ctxb;
    CheckBox azt1, nvp1, ctx1;

    String phne, z;
    Progress pr;
    ProcessMessage pm;
    Dialogs dialogs;
    SweetAlertDialog mdialog;
    Dialog mydialog;


    EditText PNC_VisitNo, PNC_ClinicNo,ANC_VisitNo1,DateDied, DeathCause, BabyDOB,Datetestedp, partnerCCCNo, DateTested, partnerDateTested, CCCEnrolDate, ARTStart_date, partnerCCCEnrolDate, partnerARTStart_date;
    private int mYear, mMonth, mDay;
    String[] hivResults = {"", "Unknown", "Negative", "known positive"};
    String[] hivResults2 = {"",  "Negative", "Positive"};
    String[] ClientVisitType = {"", "Labor and Delivery", "PNC"};
    String[] ModeDelivery = {"", "Spontaneous Vaginal Delivery (SVD)", "Cesarean Section (CS)", "Breech Delivery",  "Assisted Vaginal Delivery"};
    String[] placeDelivery = {"", "Home", "Facility", "Born before Arrival"};
    String[] DeliveryOutcome = {"", "Single", "Twins", "Triplets"};
    String[] BabyDelivered = {"", "Live Birth", "Fresh Still Birth", "Macerated Still Birth"};
    String[] BabySex = {"", "Emergency Contraceptive Pills", "Oral Contraceptive Pills", "Injectible","Implant", "Intrauterine Device", "Lactational Amenorhea Method", "Diaphram/Cervical Cap", "Fertility Awareness", "Tubal Ligation","Condoms","Vasectomy(partner)","None"};
    String[] MothersOutcome = {"", "Discharged", "Deceased",  "Transfered to CCC",  "Transfered to Another Facility"};
    String[] FP = {"", "Yes", "No"};
    String[] BabyMedication = {"", "AZT", "NVP", "CTX"};
    String[] mothertestedhivS = {"", "Yes", "No"};

    String[] haartdata = {"", "Yes", "No", "N/A"};

    String[] tbS = {"", "No TB Signs", "Presumed TB", "TB Confirmed", "TB Screening Not Done"};
    String[] Immunization = {"", "BCG", "OPV at Birth", "OPV 1", "OPV 2", "OPV 3", "IPV", "DPT/Hep B/Hib 1", "DPT/Hep B/Hib 2", "DPT/Hep B/Hib 3", "PCV 10 (Pneumoccal) 1","PCV 10 (Pneumoccal) 2", "PCV 10 (Pneumoccal) 3", "ROTA 1", "Measles/Rubella 1", "Yellow Fever", "Measles Rubella 2", "Measles at 6 months", "ROTA 2", "Vitamin A at 6 months (100,000 i.u)", "Vitamin A at 1 yr (200,000 i.u)",  "Vitamin A at 1 1/2yr (200,000 i.u)", "Vitamin A at 2yrs (200,000 i.u)", "Vitamin A at >2yrs-5yrs (200,000 i.u)" };


    String newCC;


    CheckInternet chkinternet;
    AccessServer acs;
    String[] Regimen= {"", "ABC/3TC/NVP", "AZT/3TC/NVP","ABC/3TC/EFV", "TDF/3TC/AZT","AZT/3TC/DTG","ETR/RAL/DRV/RTV","AZT/3TC/LPV/r","AZT/TDF/3TC/LPV/r", "TDF/ABC/LPV/r", "ABC/TDF/3TC/LPV/r", "ETR/TDF/3TC/LPV/r", "ABC/3TC/LPV/r","D4T/3TC/LPV/r","ABC/DDI/LPV/r","TDF/3TC/NVP","AZT/3TC/EFV","TDF/3TC/ATV/r","AZT/3TC/ATV/r","D4T/3TC/EFV","AZT/3TC/ABC","TDF/3TC/DTG",   "TDF/3TC/LPV/r","ABC/3TC/ATV/r","TDF/3TC/DTG/DRV/r","TDF/3TC/RAL/DRV/r","TDF/3TC/DTG/EFV/DRV/r","ABC/3TC/RAL", "AZT/3TC/RAL/DRV/r", "ABC/3TC/RAL/DRV/r","RAL/3TC/DRV/RTV/AZT","RAL/3TC/DRV/RTV/ABC", "ETV/3TC/DRV/RTV", "RAL/3TC/DRV/RTV/TDF","RAL/3TC/DRV/RTV","Other (Specify)"};

    Spinner haartSp, BabySexS, tb1S, hivResultsS2, hivResults2p1,  mothertestedhiv1,  hivResultsS, FPS,ModeDeliveryS, placeDeliveryS, ImmunizationS, DeliveryOutcomeS, MothersOutcomeS, MotherTestedS, BabyDeliveredS, RegimenS, BabyMedicationS;
    String VisitType_code,Mother_Tested_code,haart_code, TB_code, HIV_status_Code, HIV_results_Codep, HIV_results_Code2, DeliveryMode_code, Regimin_code, DeliveryPlace_code,Immunization_code, DeliveryOutcome_code,BabyDelivered_code, Baby_Sexcode, MothersOutcome_code,MotherTested_code, BabyMedication_code, Fp_code;
    private String CLIENT_VISIT_TYPE = "";

    private String HIV_RESULTS = "";

    private String MOTHER_TESTED = "";
    private String MODE_DELIVERY = "";
    private String PLACE_DELIVERY = "";
    private String DELIVERY_OUTCOME = "";
    private String MOTHERS_OUTCOME = "";
    private String MOTHERTESTED = "";
    private String BABY_DELIVERED = "";
    private String BABY_SEX = "";

    private String TB = "";
    private String F_P = "";
    private String REGIMEN = "";
    private String BABY_MEDICATION = "";
    private String HAART = "";
    private String IMMUNIZATION = "";

    LinearLayout pnclayout1, yLDlayout1, stillbirthlay,diededits,fpl1, hivResultL1, hivdata1c, hivdata2c, hivdata2L,RegimenLL;
    TextInputLayout pncVlay, pncClay,Regimentil;
    TextInputEditText Regimenedt1;
    Button buttonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pncvisit_start);

        try {

            pr = new Progress(PNCVisitStart.this);
            mydialog = new Dialog(PNCVisitStart.this);
            dialogs=new Dialogs(PNCVisitStart.this);
            pm=new ProcessMessage();

        } catch (Exception e) {


        }


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
       // pnclayout1 = (LinearLayout) findViewById(R.id.pnclayout11);

        buttonSave=(Button) findViewById(R.id.btn_submit_reg1);
        pncVlay =(TextInputLayout) findViewById(R.id.pncVlay);
        pncClay=(TextInputLayout) findViewById(R.id.pncClay);

        PNC_VisitNo =(EditText) findViewById(R.id.PNC_VisitNo);
        PNC_ClinicNo=(EditText) findViewById(R.id.PNC_ClinicNo);
        DateDied=(EditText) findViewById(R.id.Datedied5);
        DeathCause=(EditText) findViewById(R.id.deathcause5);
        BabyDOB =(EditText)findViewById(R.id.BabyDOB);



        //Datetestedp = (EditText)findViewById(R.id.testedDatep);

        acs = new AccessServer(PNCVisitStart.this);
        chkinternet = new CheckInternet(PNCVisitStart.this);

        BabySexS=(Spinner) findViewById(R.id.babySex);
        FPS=(Spinner) findViewById(R.id.FPS);
        ModeDeliveryS=(Spinner) findViewById(R.id.DeliveryMode1);
        placeDeliveryS=(Spinner) findViewById(R.id.DeliveryPlace1);
        RegimenS=(Spinner) findViewById(R.id.RegimenS);
        ImmunizationS=(Spinner) findViewById(R.id.BabyImmunization1);
        MothersOutcomeS=(Spinner) findViewById(R.id.mOutcome);

        mothertestedhiv1=(Spinner) findViewById(R.id.motherTested);

        hivResultsS=(Spinner) findViewById(R.id.hivResults1p);
        hivResultsS2=(Spinner) findViewById(R.id.hivResults);

        hivResults2p1=(Spinner) findViewById(R.id.hivResults2p);
        tb1S =(Spinner) findViewById(R.id.TBS);

        haartSp =(Spinner) findViewById(R.id.haartgiven);

        azt1 = (CheckBox) findViewById(R.id.azt);
        nvp1 = (CheckBox) findViewById(R.id.nvp);
        ctx1 = (CheckBox) findViewById(R.id.ctx);;


        Regimentil=(TextInputLayout) findViewById(R.id.Regimentil);

        diededits=(LinearLayout) findViewById(R.id.diededits);
        hivResultL1=(LinearLayout) findViewById(R.id.hivResultL);

        hivdata1c = (LinearLayout) findViewById(R.id.hivdata1);
        hivdata2c =(LinearLayout) findViewById(R.id.hivdata2);
        RegimenLL =(LinearLayout) findViewById(R.id. RegimenL);
       // hivdata2L =(LinearLayout) findViewById(R.id.hivdata2);
        fpl1=(LinearLayout) findViewById(R.id.fpl);
        Regimenedt1=(TextInputEditText) findViewById(R.id.Regimenedt);

        DateTested = (EditText) findViewById(R.id.testedDate);
        partnerDateTested = (EditText) findViewById(R.id.testedDatep);
        partnerCCCNo = (EditText) findViewById(R.id.partccno);
        CCCEnrolDate = (EditText) findViewById(R.id.ccEn);
        ARTStart_date = (EditText) findViewById(R.id.ARTstart);
        partnerCCCEnrolDate = (EditText) findViewById(R.id.partccEn);
        partnerARTStart_date = (EditText) findViewById(R.id.partARTstart);
        ANC_VisitNo1= (EditText) findViewById(R.id.ANC_VisitNo);
        ANC_VisitNo1= (EditText) findViewById(R.id.ANC_VisitNo);






        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                /*if (Baby_Sexcode.contentEquals("0")) {
                    Toast.makeText(PNCVisitStart.this, "Please Select If Counselled pn Family Planning", Toast.LENGTH_SHORT).show();
                }/*else if (Fp_code.contentEquals("0")){
                    Toast.makeText(PNCVisitStart.this, "Please Select The Family Planning Method", Toast.LENGTH_SHORT).show();

                }*/
                if (!haveNetworkConnection()){
                    Toast.makeText(PNCVisitStart.this, "Check your internet", Toast.LENGTH_LONG).show();
                }

             else if (BabyDOB.getText().toString().isEmpty()){
                    Toast.makeText(PNCVisitStart.this, "Please Select Date of Visit", Toast.LENGTH_SHORT).show();

                }
              else  if (PNC_ClinicNo.getText().toString().isEmpty()){
                  Toast.makeText(PNCVisitStart.this, "Please Enter PNC Clinic Number Visit", Toast.LENGTH_SHORT).show();
              }

              else  if (PNC_VisitNo.getText().toString().isEmpty()){
                  Toast.makeText(PNCVisitStart.this, "Please Enter PNC Visit Number", Toast.LENGTH_SHORT).show();
              }

              else  if (ANC_VisitNo1.getText().toString().isEmpty()){
                  Toast.makeText(PNCVisitStart.this, "Please Enter Number of ANC VIsits", Toast.LENGTH_SHORT).show();
              }


              else if (HIV_status_Code.contentEquals("0")){
                  Toast.makeText(PNCVisitStart.this, "Specify HIV Status", Toast.LENGTH_SHORT).show();
              }
              else if (Mother_Tested_code.contentEquals("0")){
                  Toast.makeText(PNCVisitStart.this, "Specify if Mother Tested for HIV", Toast.LENGTH_SHORT).show();
              }

              else if (TB_code.contentEquals("0")){
                  Toast.makeText(PNCVisitStart.this, "Specify if TB Screening was Done", Toast.LENGTH_SHORT).show();
              }
              /*else if(azt1.isChecked())
              {
                  aztb= Boolean.parseBoolean(azt1.getText().toString());
              }


              else if(nvp1.isChecked())
              {

                  nvpb= Boolean.parseBoolean(nvp1.getText().toString());

              }

              else if(ctx1.isChecked())
              {
                  ctxb = Boolean.parseBoolean(ctx1.getText().toString());
              }*/
              else if(!azt1.isChecked() && !nvp1.isChecked() && !ctx1.isChecked()){
                  Toast.makeText(PNCVisitStart.this, "Select Prophylaxis Given", Toast.LENGTH_LONG).show();
              }

              else if (haart_code.contentEquals("0")){


                  Toast.makeText(PNCVisitStart.this, "Specify if HAART for mother was given", Toast.LENGTH_SHORT).show();
              }
              else if (DeliveryMode_code.contentEquals("0")){
                  Toast.makeText(PNCVisitStart.this, "Specify Delivery Mode", Toast.LENGTH_SHORT).show();
              }

              else if (DeliveryPlace_code.contentEquals("0")){
                  Toast.makeText(PNCVisitStart.this, "Specify Place of Delivery", Toast.LENGTH_SHORT).show();
              }
             /* else if (Regimin_code.contentEquals("0")){
                  Toast.makeText(PNCVisitStart.this, "Specify the Regimen", Toast.LENGTH_SHORT).show();
              }*/
              else if (Immunization_code.contentEquals("0")){
                  Toast.makeText(PNCVisitStart.this, "Specify Baby's Immunization", Toast.LENGTH_SHORT).show();
              }
              else if (Fp_code.contentEquals("0")){
                  Toast.makeText(PNCVisitStart.this, "Please Select The Family Planning Method", Toast.LENGTH_SHORT).show();

              }
              else if (MothersOutcome_code.contentEquals("0")){
                  Toast.makeText(PNCVisitStart.this, "Specify Mother's Outcome", Toast.LENGTH_SHORT).show();
              }
              else if (MothersOutcome_code.contentEquals("2") && DateDied.getText().toString().isEmpty()){
                  Toast.makeText(PNCVisitStart.this, "Specify Date Died", Toast.LENGTH_SHORT).show();
                  DateDied.setError("");
              }
              else if (MothersOutcome_code.contentEquals("2") && DeathCause.getText().toString().isEmpty()){
                  Toast.makeText(PNCVisitStart.this, "Specify Cause of Death", Toast.LENGTH_SHORT).show();
                  DeathCause.setError("");
              }



               else if (Fp_code.contentEquals("1") && Baby_Sexcode.contentEquals("0")){
                    Toast.makeText(PNCVisitStart.this, "Please specify Family Planning Method", Toast.LENGTH_SHORT).show();
                }
               else if (Regimin_code.contentEquals("35") && Objects.requireNonNull(Regimenedt1.getText()).toString().isEmpty()){

                  Toast.makeText(PNCVisitStart.this, "Please Specify The Regimen", Toast.LENGTH_SHORT).show();

              }


                else{

               // savePNC();
                postPNC();
                }
            }
        });

        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("PNC Visit Details");
        }
        catch(Exception e){


        }

        //partnerdate tested
        //testedDate
        partnerDateTested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        //testedDate
        DateTested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                long timeInMilliseconds = calendar.getTimeInMillis() + TimeUnit.DAYS.toMillis(280);

                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(PNCVisitStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        DateTested.setText(dayOfMonth + "/" + (month + 1) + "/" + year);


                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });

        //partARTstart

        partnerARTStart_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(PNCVisitStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        partnerARTStart_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });


        //partccEn
        partnerCCCEnrolDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(PNCVisitStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        partnerCCCEnrolDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });

        //ARTstart
        ARTStart_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(PNCVisitStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        ARTStart_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });


        //ccEn
        CCCEnrolDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(PNCVisitStart.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        CCCEnrolDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });

        //Date died

        DateDied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(PNCVisitStart.this  , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        DateDied.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                 datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });






        //dateof visit
        BabyDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(PNCVisitStart.this  , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        BabyDOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                 datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });



        //CONTRACEPTIVES
        ArrayAdapter<String> BabySexAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, BabySex);
        BabySexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BabySexS.setAdapter(BabySexAdapter);

        BabySexS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BABY_SEX = BabySex[position];
                Baby_Sexcode =Integer.toString(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //hart
        //haart at maternity

        ArrayAdapter<String> haartAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, haartdata);
        haartAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        haartSp.setAdapter(haartAdapter);
        haartSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HAART= haartdata[position];
                haart_code = Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Regimen
        ArrayAdapter<String> RegimenAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, Regimen);
        RegimenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RegimenS.setAdapter(RegimenAdapter);

       RegimenS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                REGIMEN = Regimen[position];
               Regimin_code =Integer.toString(position);

               if (Regimin_code.contentEquals("35")){
                   Regimentil.setVisibility(View.VISIBLE);
                  // Toast.makeText(PNCVisitStart.this, "Other selected", Toast.LENGTH_SHORT).show();
               }
               else{
                   Regimentil.setVisibility(View.GONE);
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // hiv status
        ArrayAdapter<String> resultsAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, hivResults);
        resultsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hivResultsS.setAdapter(resultsAdapter);

        hivResultsS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HIV_RESULTS = hivResults[position];
               HIV_status_Code = Integer.toString(position);

                //hivResultL1

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //hiv results

        ArrayAdapter<String> resultsAdapter1 = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, hivResults2);
        resultsAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hivResultsS2.setAdapter(resultsAdapter1);

        hivResultsS2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HIV_RESULTS = hivResults[position];
                HIV_results_Code2 = Integer.toString(position);

                if (HIV_results_Code2.contentEquals("3")) {
                    hivdata1c.setVisibility(View.VISIBLE);
                    RegimenLL.setVisibility(View.VISIBLE);

                } else if (HIV_results_Code2.contentEquals("2")) {
                    hivdata1c.setVisibility(View.VISIBLE);
                    RegimenLL.setVisibility(View.VISIBLE);
                } else if (HIV_results_Code2.contentEquals("1")) {
                    hivdata1c.setVisibility(View.GONE);
                    RegimenLL.setVisibility(View.GONE);
                } else if (HIV_results_Code2.contentEquals("0")) {
                    hivdata1c.setVisibility(View.GONE);
                    RegimenLL.setVisibility(View.GONE);
                }

                //hivdata1c

                //hivResultL1

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//hivResults2p1
        ArrayAdapter<String> resultsAdapter2 = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, hivResults2);
        resultsAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hivResults2p1.setAdapter(resultsAdapter2);

        hivResults2p1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HIV_RESULTS = hivResults[position];
                HIV_results_Codep = Integer.toString(position);

                if (HIV_results_Codep.contentEquals("3")) {
                    hivdata2c.setVisibility(View.VISIBLE);

                } else if (HIV_results_Codep.contentEquals("2")) {
                    hivdata2c.setVisibility(View.VISIBLE);
                } else if (HIV_results_Codep.contentEquals("1")) {
                    hivdata2c.setVisibility(View.GONE);
                } else if (HIV_results_Codep.contentEquals("0")) {
                    hivdata2c.setVisibility(View.GONE);
                }

                //hivdata1c

                //hivResultL1

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //mother tested

        ArrayAdapter<String> motherTestedAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, mothertestedhivS);
        motherTestedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mothertestedhiv1.setAdapter(motherTestedAdapter);

        mothertestedhiv1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MOTHERTESTED= mothertestedhivS[position];
                Mother_Tested_code = Integer.toString(position);

                if (Mother_Tested_code.contentEquals("1")) {
                    hivResultL1.setVisibility(View.VISIBLE);
                } else if (Mother_Tested_code.contentEquals("2"))
                {hivResultL1.setVisibility(View.GONE);
                    hivdata1c.setVisibility(View.GONE);
                } else if (Mother_Tested_code.contentEquals("0")) {
                    hivResultL1.setVisibility(View.GONE);
                    hivdata1c.setVisibility(View.GONE);
                }
               else if (Mother_Tested_code.contentEquals("1") && HIV_results_Code2.contentEquals("3")) {
                    hivResultL1.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Immunization
        ArrayAdapter<String> ImmunizationAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, Immunization);
        ImmunizationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ImmunizationS.setAdapter(ImmunizationAdapter);

        ImmunizationS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                IMMUNIZATION = Immunization[position];
                Immunization_code =Integer.toString(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Outcome
        ArrayAdapter<String> OutcomeAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, MothersOutcome);
        OutcomeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MothersOutcomeS.setAdapter(OutcomeAdapter);

        MothersOutcomeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               MOTHERS_OUTCOME = MothersOutcome[position];
               MothersOutcome_code =Integer.toString(position);


                if (MothersOutcome_code.contentEquals("4")) {
                   diededits.setVisibility(View.GONE);


                } else if (MothersOutcome_code.contentEquals("3")) {
                    diededits.setVisibility(View.GONE);

                } else if (MothersOutcome_code.contentEquals("2")) {
                  diededits.setVisibility(View.VISIBLE);
                } else if (MothersOutcome_code.contentEquals("1")) {
                    diededits.setVisibility(View.GONE);
                }
                else if (MothersOutcome_code.contentEquals("0")) {
                    diededits.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Mode delivery
        ArrayAdapter<String> ModedeliveryAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, ModeDelivery);
        ModedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ModeDeliveryS.setAdapter(ModedeliveryAdapter);

        ModeDeliveryS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MODE_DELIVERY = ModeDelivery[position];
                DeliveryMode_code=Integer.toString(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //TB screening
        ArrayAdapter<String> tbAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, tbS);
        tbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tb1S.setAdapter(tbAdapter);
        tb1S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TB = tbS[position];
                TB_code = Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //placeDelivery
        ArrayAdapter<String> PlacedeliveryAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, placeDelivery);
        PlacedeliveryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeDeliveryS.setAdapter(PlacedeliveryAdapter);

        placeDeliveryS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                PLACE_DELIVERY = placeDelivery[position];
                DeliveryPlace_code=Integer.toString(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //FPCounsel
        ArrayAdapter<String> FPAdapter = new ArrayAdapter<String>(PNCVisitStart.this, android.R.layout.simple_spinner_item, FP);
        BabySexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        FPS.setAdapter(FPAdapter);

        FPS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                F_P = FP[position];

                Fp_code =Integer.toString(position);

                if (Fp_code.contentEquals("1")){
                    fpl1.setVisibility(View.VISIBLE);
                }
                else{
                    fpl1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void savePNC(){

         String det = BabyDOB.getText().toString();
         String visit = PNC_VisitNo.getText().toString();
        String clinic = PNC_ClinicNo.getText().toString();

        String dieddt = DateDied.getText().toString();
        String diedcause = DeathCause.getText().toString();

        String DateTested1 = DateTested.getText().toString();
        String partnerDateTested1 =partnerDateTested.getText().toString();
        String partnerCCCNo1 = partnerCCCNo.getText().toString();
        String CCCEnrolDate1 = CCCEnrolDate.getText().toString();
        String ARTStart_date1 = ARTStart_date.getText().toString();
        String partnerCCCEnrolDate1 = partnerCCCEnrolDate.getText().toString();
        String partnerARTStart_date1 = partnerARTStart_date.getText().toString();
        ANC_VisitNo1= (EditText) findViewById(R.id.ANC_VisitNo);





        String PNC_data = newCC + "*" + det + "*" + visit + "*" +  clinic + "*" +Mother_Tested_code+"*" +


                HIV_status_Code+ "*" +  HIV_results_Code2+ "*" +DateTested1+ "*" + newCC + "*" + CCCEnrolDate1 + "*" +ARTStart_date1+ "*" + Regimin_code+ "*" +HIV_results_Codep+ "*" +partnerDateTested1+ "*" + partnerCCCNo1+ "*" + partnerCCCEnrolDate1+ "*" +partnerARTStart_date1+ "*" +aztb+ "*" +nvpb+ "*" +ctxb+ "*" +haart_code+"*" + -1+"*" +DeliveryMode_code + "*" + DeliveryPlace_code + "*" +Immunization_code+ "*" +  Baby_Sexcode+ "*" + Fp_code+ "*" +MothersOutcome+ "*" +dieddt+ "*" +diedcause;
        Log.d("pnc", PNC_data);
        String enc = Base64Encoder.encryptString(PNC_data);


        List<Activelogin> myl = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
        for (int x = 0; x < myl.size(); x++) {

            String un = myl.get(x).getUname();
            List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select+ from Registrationtable where username=? limit 1", un);
            for (int y = 0; y < myl2.size(); y++) {

                String phne = myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                acs.PNCPost("pnc*" + enc, phne);



                //Toast.makeText(PNCVisitStart.this, "PNC Details Saved Successful", Toast.LENGTH_SHORT).show();
               // Log.e("", clientTreated_code + HepatitisB_code);



            }


    }}

public void postPNC() {

    /*String det = BabyDOB.getText().toString();
    String visit = PNC_VisitNo.getText().toString();
    String clinic = PNC_ClinicNo.getText().toString();

    String anc_visits = ANC_VisitNo1.getText().toString();

    String dieddt = DateDied.getText().toString();
    String diedcause = DeathCause.getText().toString();*/
    String Regspecify =Regimenedt1.getText().toString();


        aztb= Boolean.parseBoolean(azt1.getText().toString());

        nvpb= Boolean.parseBoolean(nvp1.getText().toString());

        ctxb = Boolean.parseBoolean(ctx1.getText().toString());




    String det = BabyDOB.getText().toString();
    String visit = PNC_VisitNo.getText().toString();
    String clinic = PNC_ClinicNo.getText().toString();
    String anc_visits = ANC_VisitNo1.getText().toString();

    String dieddt = DateDied.getText().toString();
    String diedcause = DeathCause.getText().toString();

    String DateTested1 = DateTested.getText().toString();
    String partnerDateTested1 =partnerDateTested.getText().toString();
    String partnerCCCNo1 = partnerCCCNo.getText().toString();
    String CCCEnrolDate1 = CCCEnrolDate.getText().toString();
    String ARTStart_date1 = ARTStart_date.getText().toString();
    String partnerCCCEnrolDate1 = partnerCCCEnrolDate.getText().toString();
    String partnerARTStart_date1 = partnerARTStart_date.getText().toString();
    //ANC_VisitNo1= (EditText) findViewById(R.id.ANC_VisitNo);




    String PNC_data = newCC + "*" + det + "*" + visit + "*" +  clinic + "*" +anc_visits +"*" + Mother_Tested_code+"*" + HIV_status_Code+ "*" +  HIV_results_Code2+ "*" +DateTested1+ "*" + newCC + "*" + CCCEnrolDate1 + "*" +ARTStart_date1+ "*" + Regimin_code+ "*" +HIV_results_Codep+ "*" +partnerDateTested1+ "*" + partnerCCCNo1+ "*" + partnerCCCEnrolDate1+ "*" +partnerARTStart_date1+ "*" +aztb+ "*" +nvpb+ "*" +ctxb+ "*" +haart_code+"*" + "-1" +"*" +DeliveryMode_code + "*" + DeliveryPlace_code + "*" +Immunization_code+ "*" +  Baby_Sexcode+ "*" + Fp_code+ "*" +MothersOutcome_code+ "*" +dieddt+ "*" +diedcause;
    Log.d("pnc", PNC_data);

   // String PNC_data = newCC + "*" + det + "*" + visit + "*" + clinic + "*" +anc_visits+ "*" +Mother_Tested_code + "*" + DeliveryMode_code + "*" + DeliveryPlace_code + "*" + Regimin_code + "*" +Regspecify+ "*" + Immunization_code + "*" + Baby_Sexcode + "*" + Fp_code + "*" + MothersOutcome + "*" + dieddt + "*" + diedcause;
    String enc = Base64Encoder.encryptString(PNC_data);

    List<Activelogin> myl = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
    for (int x = 0; x < myl.size(); x++) {

        String un = myl.get(x).getUname();
        List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
        for (int y = 0; y < myl2.size(); y++) {

            phne = myl2.get(y).getPhone();
            try {
                List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                if (_url.size() == 1) {
                    for (int xx = 0; xx < _url.size(); xx++) {
                        z = _url.get(xx).getBase_url1();

                        //all = "https://ushauriapi.kenyahmis.org/pmtct/anc";

                    }
                }

            } catch (Exception e) {

            }
            pr.showProgress("Sending PNC Details.....");
            final int[] mStatusCode = new int[1];

            JSONObject payload = new JSONObject();
            try {

                payload.put("msg", "pnc*"+enc);
                payload.put("phone_no", phne);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("payload: ", payload.toString());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, z+ Config.PNCstart, payload,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                       Toast.makeText(PNCVisitStart.this, "message "+response, Toast.LENGTH_SHORT).show();
                            Log.e("Response: ", "response.toString()");
                            pr.dissmissProgress();

                            JSONObject jsonObject = null;
                            JSONObject jsonObject1 = null;

                            String mss =null;
                            int cd =0;
                            try {
                                int code22 =response.getInt("code");
                                mss =response.getString("message");
                                    /*jsonObject = response.getJSONObject("code");
                                    jsonObject1=response.getJSONObject("message");
                                    String message1=jsonObject.getString("message");
                                    int code1=jsonObject1.getInt("code");*/

                                if (code22==200){

                                    Log.e("Response: ", "code200");
                                    // dialogs.showSuccessDialog(mss, "Server Response");

                                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(PNCVisitStart.this);
                                    builder1.setIcon(R.drawable.nascoplogonew);
                                    builder1.setTitle(mss);
                                    builder1.setMessage( "Server Response");
                                    builder1.setCancelable(false);

                                    builder1.setPositiveButton(
                                            "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {



                                                    Intent intent = new Intent(PNCVisitStart.this, PMTCT1.class);

                                                    startActivity(intent);
                                                    dialog.dismiss();

                                                    PNC_VisitNo.setText("");
                                                    PNC_ClinicNo.setText("");
                                                    DateDied.setText("");
                                                    DeathCause.setText("");
                                                    BabyDOB.setText("");

                                                    DateTested.setText("");


                                                    partnerDateTested.setText("");
                                                    partnerCCCNo.setText("");
                                                    CCCEnrolDate.setText("");
                                                    ARTStart_date.setText("");
                                                    partnerCCCEnrolDate.setText("");
                                                    partnerARTStart_date.setText("");
                                                    ANC_VisitNo1.setText("");





                                                    //dialog.cancel();
                                                }
                                            });


                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();



                                }else{
                                    dialogs.showErrorDialog(mss, "err");


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(PNCVisitStart.this, "ERROR", Toast.LENGTH_SHORT).show();
                            pr.dissmissProgress();

                            try {

                                byte[] htmlBodyBytes = error.networkResponse.data;

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                                dialogs.showErrorDialog(new String(htmlBodyBytes), "Server Response");

                                pr.dissmissProgress();

                            } catch (Exception e) {


//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                                dialogs.showErrorDialog("error occured, try again", "Server Response");

                                pr.dissmissProgress();


                            }


                        }
                    }) {


                @Override
                protected Map<String, String> getParams() {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    return headers;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(PNCVisitStart.this);
            requestQueue.add(jsonObjectRequest);

//        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
//        requestQueue.add(stringRequest);




        }}}

    //test internet
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }



}