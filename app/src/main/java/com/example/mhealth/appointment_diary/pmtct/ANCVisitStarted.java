package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.mhealth.appointment_diary.Mydates.MyDates;
import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.Progress.Progress;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.example.mhealth.appointment_diary.utilitymodules.Registration;

import org.joda.time.DateTime;
import org.joda.time.Weeks;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ANCVisitStarted extends AppCompatActivity {
    private boolean aztb, nvpb, ctxb;
    private RadioGroup radioGroup;
    String z, all;
    Date datelmp;
    Date date1;
    Date date2;
    String phne;

    String phone_no;

    Progress pr;
    ProcessMessage pm;
    Dialogs dialogs;
    SweetAlertDialog mdialog;
    Dialog mydialog;

    String[] clientIs = {"", "Breastfeeding", "Not Breastfeeding", "Pregnant", "Pregnant and Breastfeeding"};
    String[] clientTreated = {"", "Yes", "No"};
    String[] hepatitisB = {"", "Positive", "Negative", "Not Done"};
    String[] hivResults = {"", "Unknown", "Negative", "Positive"};
    String[] hivfirstANCS = {"", "Unknown", "Negative", "Positive"};
    String[] mothertestedhivS = {"", "Yes", "No"};

    String[] tbS = {"", "No TB Signs", "Presumed TB", "TB Confirmed", "TB Screening Not Done"};
    String[] syphilis = {"", "Negative", "Positive", "Requested", "Not Requested", "Poor Sample Quality"};
    Spinner clientIsS, hivResultsS, SyphilisS, hivResultsSp, clientTreatp, hepBp, hivfirstANC1, mothertestedhiv1, tb1S;
    private String CLIENT_IS = "";
    private String HIV_RESULTS = "";
    private String CLIENT_TREATED = "";
    private String HEPATITIS_B = "";
    private String SYPHILIS = "";
    private String HIVANC = "";
    private String MOTHERTESTED = "";
    private String TB = "";
    LinearLayout pregnant, positiveLayout, partnerLayout, positiveselected, hivdata1c, hivdata2c;
    EditText CCCNo, partnerCCCNo, gravida, EDD_date, LMP_date, DateTested, ANC_Visitno, ANC_clinicno, ANCNumber, partnerDateTested, CCCEnrolDate, ARTStart_date, partnerCCCEnrolDate, partnerARTStart_date, VLdate, parity1, parity2, VLResults, CCCNo22, Gestation, weight1, muac1;
    String ClientIS_code, HIV_Results_Code, partnerHIV_Results_Code, SyphilisSerology_code, clientTreated_code, HepatitisB_code, HIV_ANC_Code, Mother_Tested_code, TB_code;
    RadioGroup syphilisID, hepatitisID;
    RadioButton radioButtonChecked;

    Calendar calendar1;

    Button saveANC;

    CheckInternet chkinternet;
    AccessServer acs;
    String newCC, pa1, pa2;

    int pa11, pa22, gra;
    int sum;

    /*gravida=gravida,lmp=LMP_date,edd=EDD_date,gestation,testedDate,
    ccno, ccEn, ARTstart,, partccno, partccEn, partARTstart,VLResults=VLResults*/


    //SyphilisTreated_code, HepatitisB-code
    int hepatitisID_code, syphilisIDc_code;

    CheckBox azt1, nvp1, ctx1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancvisit_started);

        List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");

        for(int x=0;x<myl.size();x++){
            String un=myl.get(x).getUname();
            List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
            for(int y=0;y<myl2.size();y++){
                phone_no=myl2.get(y).getPhone();
            }
        }




        try {

            pr = new Progress(ANCVisitStarted.this);
            mydialog = new Dialog(ANCVisitStarted.this);
            dialogs=new Dialogs(ANCVisitStarted.this);
            pm=new ProcessMessage();

        } catch (Exception e) {


        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newCC = null;
            } else {
                newCC = extras.getString("Client_CCC");
            }
        } else {
            newCC = (String) savedInstanceState.getSerializable("Client_CCC");
        }

        acs = new AccessServer(ANCVisitStarted.this);
        chkinternet = new CheckInternet(ANCVisitStarted.this);
        pregnant = (LinearLayout) findViewById(R.id.pregnant);
        positiveLayout = (LinearLayout) findViewById(R.id.positiveLayout);
        partnerLayout = (LinearLayout) findViewById(R.id.partnerLayout);
        positiveselected = (LinearLayout) findViewById(R.id.positiveSelected);
        hivdata1c = findViewById(R.id.hivdata1);
        hivdata2c = findViewById(R.id.hivdata2);
        // hivdata2==  findViewById(R.id.hivdata1);

        LMP_date = (EditText) findViewById(R.id.lmp);
        parity1 = (EditText) findViewById(R.id.parity);
        parity2 = (EditText) findViewById(R.id.parity2);
        VLResults = (EditText) findViewById(R.id.VLResults);

        EDD_date = (EditText) findViewById(R.id.edd);
        Gestation = (EditText) findViewById(R.id.gestation);
        gravida = (EditText) findViewById(R.id.gravida);
        ANC_Visitno = (EditText) findViewById(R.id.ANCVisit);
        ANC_clinicno = (EditText) findViewById(R.id.ANCClinic);
        ANCNumber = (EditText) findViewById(R.id.ANCNumber);
        partnerCCCNo = (EditText) findViewById(R.id.partccno);
        DateTested = (EditText) findViewById(R.id.testedDate);
        partnerDateTested = (EditText) findViewById(R.id.testedDatep);
        CCCNo22 = (EditText) findViewById(R.id.ccno);

        CCCEnrolDate = (EditText) findViewById(R.id.ccEn);
        ARTStart_date = (EditText) findViewById(R.id.ARTstart);
        partnerCCCEnrolDate = (EditText) findViewById(R.id.partccEn);
        partnerARTStart_date = (EditText) findViewById(R.id.partARTstart);

        weight1 = (EditText) findViewById(R.id.weight);
        muac1 = (EditText) findViewById(R.id.muac);
        saveANC = (Button) findViewById(R.id.btn_save);

        azt1 = (CheckBox) findViewById(R.id.azt);
        nvp1 = (CheckBox) findViewById(R.id.nvp);
        ctx1 = (CheckBox) findViewById(R.id.ctx);;

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);



        //syphilisID= (RadioGroup) findViewById(R.id.syphilisID);
        //hepatitisID= (RadioGroup) findViewById(R.id.hepatitisID);

        VLdate = (EditText) findViewById(R.id.vldatesample);
        ClientIS_code = "";
        HIV_Results_Code = "";
        partnerHIV_Results_Code = "";
        SyphilisSerology_code = "";
        HepatitisB_code = "";
        populategravida();


        // populategravida();

        //EDD_date
       /* EDD_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(ANCVisitStarted.this  , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        EDD_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });*/


        //vldatesample
        VLdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(ANCVisitStarted.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        VLdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
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
                DatePickerDialog datePicker = new DatePickerDialog(ANCVisitStarted.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(ANCVisitStarted.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(ANCVisitStarted.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePicker = new DatePickerDialog(ANCVisitStarted.this, new DatePickerDialog.OnDateSetListener() {
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


        //testedDatep
        partnerDateTested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(ANCVisitStarted.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        partnerDateTested.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
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
                DatePickerDialog datePicker = new DatePickerDialog(ANCVisitStarted.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        DateTested.setText(dayOfMonth + "/" + (month + 1) + "/" + year);



                       /* Calendar calendar = Calendar.getInstance();
                        calendar.set(year,monthOfYear + 1,dayOfMonth);

                        long timeInMilliseconds = calendar.getTimeInMillis()+TimeUnit.DAYS.toMillis(280);
                        calendar.setTimeInMillis(timeInMilliseconds);
                        int mYear = calendar.get(Calendar.YEAR);
                        int mMonth = calendar.get(Calendar.MONTH);
                        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                        eText.setText(mDay + "/" + mMonth + "/" + mYear);*/


                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });


        //lmp  date
        LMP_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar1 = Calendar.getInstance();
                final int day = calendar1.get(Calendar.DAY_OF_MONTH);
                final int year = calendar1.get(Calendar.YEAR);
                final int month = calendar1.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(ANCVisitStarted.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        LMP_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);


                    }
                }, year, month, day);

                // set maximum date to be selected as today
                 datePicker.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });

        LMP_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ss = LMP_date.getText().toString();


                //try gest
              //  int mycurrentYear = Integer.parseInt(MyDates.getCurrentDate()+ "/"+MyDates.getCurrentMonth()+"/"+MyDates.getCurrentYear());
               // int difference = mycurrentYear - ss;



                int x = 280 / 7;


                //Gestation.setText(String.valueOf(x));
                //Gestation.setText(String.valueOf(mycurrentYear));


                String dt = "2012-01-04";  // Start date
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");    //yyyy-MM-dd
                Calendar c = Calendar.getInstance();
                try {
                    // dd/MM/yyyy
                    c.setTime(Objects.requireNonNull(sdf.parse(ss)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DATE, 280);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                String output = sdf1.format(c.getTime());

                EDD_date.setText(output);


                //gestation
                Gestation.setText(ss);

                String lm = LMP_date.getText().toString();

                try {
                    date1 = sdf1.parse(lm);
                } catch (ParseException e) {
                    e.printStackTrace();
                }




                  Calendar aa =Calendar.getInstance();
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

                String datecurr =sdf2.format(aa.getTime());

                try {
                  date2 = sdf2.parse(datecurr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //String  datelmp1 =ss;

                //assert datelmp1 != null;
                DateTime dateTime1 = new DateTime(date1);
               DateTime dateTime2 = new DateTime(date2);

               int weeks = Weeks.weeksBetween(dateTime1, dateTime2).getWeeks();
              //  String lmpdt =sdf2.format()
                Gestation.setText(String.valueOf(weeks));


                //Calendar aa =Calendar.getInstance();
                //Calendar bb =Calendar.getInstance();

               // aa.set(String.valueOf(LMP_date.getText().toString()));




            }
        });

        try {
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("ANC Visit Details");

        } catch (Exception e) {

        }
        clientIsS = (Spinner) findViewById(R.id.ClientSpinner);
        hivResultsS = (Spinner) findViewById(R.id.hivResults1);
        hivResultsSp = (Spinner) findViewById(R.id.hivResults1p);
        SyphilisS = (Spinner) findViewById(R.id.SyphilisSpinner);

        clientTreatp = (Spinner) findViewById(R.id.clientTreat);
        hepBp = (Spinner) findViewById(R.id.hepB);
        hivfirstANC1= (Spinner) findViewById(R.id.hivfirstANC);

        tb1S= (Spinner) findViewById(R.id.TBS);

        mothertestedhiv1= (Spinner) findViewById(R.id.mothertestedhiv);


        //Client is
        ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(ANCVisitStarted.this, android.R.layout.simple_spinner_item, clientIs);
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientIsS.setAdapter(clientAdapter);

        clientIsS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CLIENT_IS = clientIs[position];
                ClientIS_code = Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //TB screening
        ArrayAdapter<String> tbAdapter = new ArrayAdapter<String>(ANCVisitStarted.this, android.R.layout.simple_spinner_item, tbS);
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
        //hiv first ANC

        ArrayAdapter<String> hivANCAdapter = new ArrayAdapter<String>(ANCVisitStarted.this, android.R.layout.simple_spinner_item, hivfirstANCS);
        hivANCAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hivfirstANC1.setAdapter(hivANCAdapter);

        hivfirstANC1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HIVANC= hivfirstANCS[position];
                HIV_ANC_Code = Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Mother tested

        ArrayAdapter<String> motherTestedAdapter = new ArrayAdapter<String>(ANCVisitStarted.this, android.R.layout.simple_spinner_item, mothertestedhivS);
        motherTestedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mothertestedhiv1.setAdapter(motherTestedAdapter);

        mothertestedhiv1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MOTHERTESTED= mothertestedhivS[position];
                Mother_Tested_code = Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // hiv results1
        ArrayAdapter<String> resultsAdapter = new ArrayAdapter<String>(ANCVisitStarted.this, android.R.layout.simple_spinner_item, hivResults);
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hivResultsS.setAdapter(resultsAdapter);

        hivResultsS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HIV_RESULTS = hivResults[position];
                HIV_Results_Code = Integer.toString(position);

                if (HIV_Results_Code.contentEquals("3")) {
                    hivdata1c.setVisibility(View.VISIBLE);

                } else if (HIV_Results_Code.contentEquals("2")) {
                    hivdata1c.setVisibility(View.GONE);
                } else if (HIV_Results_Code.contentEquals("1")) {
                    hivdata1c.setVisibility(View.GONE);
                } else if (HIV_Results_Code.contentEquals("0")) {
                    hivdata1c.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // hiv results2
        ArrayAdapter<String> resultsAdapter1 = new ArrayAdapter<String>(ANCVisitStarted.this, android.R.layout.simple_spinner_item, hivResults);
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hivResultsSp.setAdapter(resultsAdapter1);

        hivResultsSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HIV_RESULTS = clientIs[position];
                partnerHIV_Results_Code = Integer.toString(position);


                if (partnerHIV_Results_Code.contentEquals("3")) {
                    hivdata2c.setVisibility(View.VISIBLE);

                } else if (partnerHIV_Results_Code.contentEquals("2")) {
                    hivdata2c.setVisibility(View.GONE);
                } else if (partnerHIV_Results_Code.contentEquals("1")) {
                    hivdata2c.setVisibility(View.GONE);
                } else if (partnerHIV_Results_Code.contentEquals("0")) {
                    hivdata2c.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //clientTreat
        ArrayAdapter<String> clietTreatAdapter = new ArrayAdapter<String>(ANCVisitStarted.this, android.R.layout.simple_spinner_item, clientTreated);
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientTreatp.setAdapter(clietTreatAdapter);

        clientTreatp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CLIENT_TREATED = clientTreated[position];

                clientTreated_code = Integer.toString(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //hpB

        ArrayAdapter<String> hpbAdapter = new ArrayAdapter<String>(ANCVisitStarted.this, android.R.layout.simple_spinner_item, hepatitisB);
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hepBp.setAdapter(hpbAdapter);

        hepBp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HEPATITIS_B = hepatitisB[position];

                HepatitisB_code = Integer.toString(position);
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

                SyphilisSerology_code = Integer.toString(position);
                // HepatitisB_code="";

                if (SYPHILIS.contentEquals("Positive")) {
                    positiveselected.setVisibility(View.VISIBLE);
                } else if (SYPHILIS.contentEquals("Negative")) {
                    positiveselected.setVisibility(View.GONE);
                } else if (SYPHILIS.contentEquals("Requested")) {
                    positiveselected.setVisibility(View.GONE);
                } else if (SYPHILIS.contentEquals("Not Requested")) {
                    positiveselected.setVisibility(View.GONE);
                } else if (SYPHILIS.contentEquals("Poor Sample Quality")) {
                    positiveselected.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // on below line we are adding check change listener for our radio group.
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                radioButtonChecked = findViewById(checkedId);

                // on below line we are displaying a toast message.
                Toast.makeText(ANCVisitStarted.this, "Selected Radio Button is : " + radioButtonChecked.getText(), Toast.LENGTH_SHORT).show();
            }
        });


        saveANC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ANC_Visitno.getText().toString().isEmpty()) {
                    Toast.makeText(ANCVisitStarted.this, "Enter ANC Visit Number", Toast.LENGTH_LONG).show();
                } else if (ANC_clinicno.getText().toString().isEmpty()) {
                    Toast.makeText(ANCVisitStarted.this, "Enter ANC Clinic Number", Toast.LENGTH_LONG).show();
                } else if (ClientIS_code.contentEquals("0")) {
                    Toast.makeText(ANCVisitStarted.this, "Specify If Client Is Breastfeeding", Toast.LENGTH_LONG).show();
                } else if (parity1.getText().toString().isEmpty()) {
                    Toast.makeText(ANCVisitStarted.this, "Enter parity 1", Toast.LENGTH_LONG).show();
                } else if (parity2.getText().toString().isEmpty()) {
                    Toast.makeText(ANCVisitStarted.this, "Enter parity 2", Toast.LENGTH_LONG).show();
                } else if (LMP_date.getText().toString().isEmpty()) {
                    Toast.makeText(ANCVisitStarted.this, "Enter LMP Date", Toast.LENGTH_LONG).show();
                } else if (EDD_date.getText().toString().isEmpty()) {
                    Toast.makeText(ANCVisitStarted.this, "Enter EDD Date", Toast.LENGTH_LONG).show();
                } else if (Gestation.getText().toString().isEmpty()) {
                    Toast.makeText(ANCVisitStarted.this, "Enter Gestation", Toast.LENGTH_LONG).show();
                } else if (HIV_Results_Code.contentEquals("0")) {
                    Toast.makeText(ANCVisitStarted.this, "Enter HIV Results", Toast.LENGTH_LONG).show();
                }

                else if (weight1.getText().toString().isEmpty()) {
                    Toast.makeText(ANCVisitStarted.this, "Enter Weight", Toast.LENGTH_LONG).show();
                }
                else if (muac1.getText().toString().isEmpty()) {
                    Toast.makeText(ANCVisitStarted.this, "Enter MUAC", Toast.LENGTH_LONG).show();
                }

               /* else if (DateTested.getText().toString().isEmpty()){
                    Toast.makeText(ANCVisitStarted.this, "Enter Date Tested", Toast.LENGTH_LONG).show();
                }*/

                //checkboxes

                //aztb, nvpb, ctxb;
                //azt1, nvp1, ctx1;

                if(azt1.isChecked())
                {
                    aztb= Boolean.parseBoolean(azt1.getText().toString());
                }


                if(nvp1.isChecked())
                {
                    //description=checkPrivacy.getText().toString();
                    nvpb= Boolean.parseBoolean(nvp1.getText().toString());

                }

                if(ctx1.isChecked())
                {
                    ctxb = Boolean.parseBoolean(ctx1.getText().toString());
                }
                else if (SyphilisSerology_code.contentEquals("0")) {
                    Toast.makeText(ANCVisitStarted.this, "Select Syphilis Serology", Toast.LENGTH_LONG).show();
                } else if (HepatitisB_code.contentEquals("0")) {
                    Toast.makeText(ANCVisitStarted.this, "Select Hepatitis B Serology", Toast.LENGTH_LONG).show();
                } else {
                   // submitANC();
                    ANCPost();
                }

            }
        });
    }

    public void submitANC() {

               /* try {
                    gra= 0;

                    pa11 =Integer.parseInt(parity1.getText().toString());

                    pa22 =Integer.parseInt(parity2.getText().toString());

                    gra =pa11+pa22+1;

                    // t1.setText(Integer.toStringly(sum));

                }catch (Exception e){
                    e.printStackTrace();
                }


                //pa11
                try {
                    gravida.setText(Integer.toString(gra));

                        Log.e("gravida", Integer.toString(gra));
                        Toast.makeText(ANCVisitStarted.this, Integer.toString(gra), Toast.LENGTH_SHORT).show();}catch (Exception e){

                }*/


        String ANC_no = ANC_Visitno.getText().toString();
        String ANC_clinic_no = ANC_clinicno.getText().toString();


        String parity_1 = parity1.getText().toString();
        String parity_2 = parity2.getText().toString();
        String gravida1 = gravida.getText().toString();
        String LMPdate = LMP_date.getText().toString();

        // String LMPdate =LMP_date.getText().toString();
        String EDDDATE = EDD_date.getText().toString();
        String gestation = Gestation.getText().toString();

        String DateTested1 = DateTested.getText().toString();
        String CCCNo2 = CCCNo22.getText().toString();
        // String CCCNo2="";
        String CCCEnrolDate1 = CCCEnrolDate.getText().toString();
        String ARTStart_date1 = ARTStart_date.getText().toString();
        String partnerDateTested1 = partnerDateTested.getText().toString();
        String partnerCCCNo1 = partnerCCCNo.getText().toString();
        String partnerCCCEnrolDate1 = partnerCCCEnrolDate.getText().toString();
        String partnerARTStart_date1 = partnerARTStart_date.getText().toString();
        String VLdate1 = VLdate.getText().toString();
        String VLResults1 = VLResults.getText().toString();


        String ANC_data = newCC + "*" + ANC_no + "*" + ANC_clinic_no + "*" + ClientIS_code + "*" + pa11 + "*" + pa22 + "*" + gra + "*" + LMPdate + "*" + EDDDATE + "*" + gestation + "*" + HIV_Results_Code + "*" + DateTested1 + "*" + CCCNo2 + "*" + CCCEnrolDate1 + "*" + ARTStart_date1 + "*" + partnerHIV_Results_Code + "*" + partnerDateTested1 + "*" + partnerCCCNo1 + "*" + partnerCCCEnrolDate1 + "*" + partnerARTStart_date1 + "*" + VLdate1 + "*" + VLResults1 + "*" + SyphilisSerology_code + "*" + clientTreated_code + "*" + HepatitisB_code;

        String enc = Base64Encoder.encryptString(ANC_data);


        List<Activelogin> myl = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
        for (int x = 0; x < myl.size(); x++) {

            String un = myl.get(x).getUname();
            List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
            for (int y = 0; y < myl2.size(); y++) {

                 phne = myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
               acs.ANCPost("anc*" + enc, phne);


                //send data





                //finish sending data


                // Toast.makeText(ANCVisitStarted.this, "ANC Details Saved Successful", Toast.LENGTH_SHORT).show();
                Log.e("", clientTreated_code + HepatitisB_code);

            }


        }
    }

     private void populategravida() {


       /*/ gravida.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (hasFocus && parity2.getText().toString().isEmpty()) {
                    Toast.makeText(ANCVisitStarted.this, "Enter Parity", Toast.LENGTH_SHORT).show();

                }else {
                    // Toast.makeText(ANCVisitStarted.this, "has focus", Toast.LENGTH_SHORT).show();
                    gra = 0;

                    pa11 = Integer.parseInt(parity1.getText().toString());

                    pa22 = Integer.parseInt(parity2.getText().toString());

                    gra = pa11 + pa22 + 1;

                    gravida.setText(String.valueOf(gra));
                }
                /*else {

                }*/

    // }
    //  });*/
//}
        parity2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (parity1.getText().toString().isEmpty() || parity2.getText().toString().isEmpty()){
                    Toast.makeText(ANCVisitStarted.this, "Enter Parity", Toast.LENGTH_LONG).show();
                }else{

                gra = 0;

                pa11 = Integer.parseInt(parity1.getText().toString());

                pa22 = Integer.parseInt(parity2.getText().toString());

                gra = pa11 + pa22 + 1;

                gravida.setText(String.valueOf(gra));}

            }
        });


        //end onfocus

    }
    // Post TO ANC
    public void ANCPost() {
        String ANC_no = ANC_Visitno.getText().toString();
        String ANC_clinic_no = ANC_clinicno.getText().toString();


        String parity_1 = parity1.getText().toString();
        String parity_2 = parity2.getText().toString();
        String gravida1 = gravida.getText().toString();
        String LMPdate = LMP_date.getText().toString();

        // String LMPdate =LMP_date.getText().toString();
        String EDDDATE = EDD_date.getText().toString();
        String gestation = Gestation.getText().toString();

        String DateTested1 = DateTested.getText().toString();
        String CCCNo2 = CCCNo22.getText().toString();
        // String CCCNo2="";
        String CCCEnrolDate1 = CCCEnrolDate.getText().toString();
        String ARTStart_date1 = ARTStart_date.getText().toString();
        String partnerDateTested1 = partnerDateTested.getText().toString();
        String partnerCCCNo1 = partnerCCCNo.getText().toString();
        String partnerCCCEnrolDate1 = partnerCCCEnrolDate.getText().toString();
        String partnerARTStart_date1 = partnerARTStart_date.getText().toString();
        String VLdate1 = VLdate.getText().toString();
        String VLResults1 = VLResults.getText().toString();


        String ANC_data = newCC + "*" + ANC_no + "*" + ANC_clinic_no + "*" + ClientIS_code + "*" + pa11 + "*" + pa22 + "*" + gra + "*" + LMPdate + "*" + EDDDATE + "*" + gestation + "*" + HIV_Results_Code + "*" + DateTested1 + "*" + CCCNo2 + "*" + CCCEnrolDate1 + "*" + ARTStart_date1 + "*" + partnerHIV_Results_Code + "*" + partnerDateTested1 + "*" + partnerCCCNo1 + "*" + partnerCCCEnrolDate1 + "*" + partnerARTStart_date1 + "*" + VLdate1 + "*" + VLResults1 + "*" + SyphilisSerology_code + "*" + clientTreated_code + "*" + HepatitisB_code;

        String enc = Base64Encoder.encryptString(ANC_data);


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

                            all = "https://ushauriapi.kenyahmis.org/pmtct/anc";

                        }
                    }

                } catch (Exception e) {

                }
                pr.showProgress("Sending ANC Details.....");
                final int[] mStatusCode = new int[1];

                JSONObject payload = new JSONObject();
                try {

                    payload.put("msg", "anc*"+enc);
                    payload.put("phone_no", phne);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("payload: ", payload.toString());


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, z+Config.ANCstart, payload,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
//                        Toast.makeText(ctx, "message "+response, Toast.LENGTH_SHORT).show();
                                Log.e("Response: ", response.toString());
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
                                       // dialogs.showSuccessDialog(mss, "Server Response");

                                        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(ANCVisitStarted.this);
                                        builder1.setIcon(R.drawable.nascoplogonew);
                                        builder1.setTitle(mss);
                                        builder1.setMessage( "Server Response");
                                        builder1.setCancelable(false);

                                        builder1.setPositiveButton(
                                                "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                        //clear text






                                                        Intent intent = new Intent(ANCVisitStarted.this, ANCVisit.class);
                                                        startActivity(intent);


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



                               /* if (mStatusCode[0] == 200) {

                                    //dialogs.showSuccessDialog(response,"Server Response");

                                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(ANCVisitStarted.this);
                                    builder1.setIcon(R.drawable.nascoplogonew);
                                    builder1.setTitle(mss);
                                    builder1.setMessage("Server Response");
                                    builder1.setCancelable(false);

                                    builder1.setPositiveButton(
                                            "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    Intent intent = new Intent(ANCVisitStarted.this, ANCVisit.class);
                                                    startActivity(intent);


                                                    //dialog.cancel();
                                                }
                                            });


                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();


                                } else {

                                    dialogs.showErrorDialog("Errresponse", "Server response"+response);
                                }*/

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
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

                RequestQueue requestQueue = Volley.newRequestQueue(ANCVisitStarted.this);
                requestQueue.add(jsonObjectRequest);

//        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
//        requestQueue.add(stringRequest);

            }
}}}