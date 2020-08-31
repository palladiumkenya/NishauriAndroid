package com.example.mhealth.appointment_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.mhealth.appointment_diary.utilitymodules.Appointment;
import com.example.mhealth.appointment_diary.utilitymodules.SpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DCMActivity extends AppCompatActivity {

    private Spinner wellness_level_spinner,stability_level_spinner,on_dcm_spinner,facility_community_spinner,facility_based_model_spinner,
            community_based_model_spinner,appointment_type_spinner,previous_appointment_spinner;
    private EditText mfl_code,ccc_no,stable_appointment_date,clinical_review_date,appointment_date;
    private LinearLayout wellness_level_layout,stability_layout,on_dcm_layout,facility_community_layout,facility_based_model_layout,community_based_model_layout,
            dcm_submit_layout,normal_tca_layout;
    private Button btn_check,btn_submit_apt,btn_dcm_submit_apt;





    private String WELLNESS_LEVEL = "";
    private String STABILITY_LEVEL = "";
    private String ON_DCM_STATUS = "";
    private String FACILITY_BASED_MODEL = "";
    private String COMMUNITY_BASED_MODEL = "";

    private String STABLE_REFILL_DATE = "";
    private String CLINICAL_REVIEW_DATE = "";
    private String APPOINTMENT_DATE = "";

    private boolean isGreaterThanYear = true;




    String[] appnment = {"Please select appointment type","Re-Fill","Clinical review","Enhanced Adherence counseling","Lab investigation","VL Booking","Other"};
    String[] previous = {"Was previous appointment kept?","Yes","No","Not Applicable"};
    String[] wellness = {"Please select wellness level","Well","Advanced"};
    String[] stability = {"Please select stability level","Stable","Unstable"};
    String[] on_dcm = {"Please select if on DCM","On DCM","NOT on DCM"};
    String[] facility_community = {"Please select DCM mode","Facility based","Community based"};
    String[] facility_based_model = {"Please select facility model","Fast track Model","Facility based Adherence clubs/ Support group"};
    String[] community_based_model = {"Please select community model","Peer Led Community ART group","Health provider led Community ART group","Community ART distribution point","Individual patient ART Distribution in the community"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_c_m);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DCM Clients");

        initViews();


        wellness_level_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1){ //well
                    WELLNESS_LEVEL = wellness[position];
                    normal_tca_layout.setVisibility(View.VISIBLE);
                }else if (position == 2){ //advanced
                    WELLNESS_LEVEL = wellness[position];
                    normal_tca_layout.setVisibility(View.VISIBLE);
                }else {
                    normal_tca_layout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        stability_level_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1){ //stable
                    STABILITY_LEVEL = stability[position];
                    normal_tca_layout.setVisibility(View.GONE);
                    on_dcm_layout.setVisibility(View.VISIBLE);
                }else if (position == 2){ //unstable
                    STABILITY_LEVEL = stability[position];
                    normal_tca_layout.setVisibility(View.VISIBLE);
                    on_dcm_layout.setVisibility(View.GONE);
                }else {
                    normal_tca_layout.setVisibility(View.GONE);
                    on_dcm_layout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        on_dcm_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1){ //on DCM
                    ON_DCM_STATUS = stability[position];
                    facility_community_layout.setVisibility(View.VISIBLE);
                    normal_tca_layout.setVisibility(View.GONE);
                }else if (position == 2){ //not on DCM
                    ON_DCM_STATUS = stability[position];
                    facility_community_layout.setVisibility(View.GONE);
                    normal_tca_layout.setVisibility(View.VISIBLE);
                }else {
                    facility_community_layout.setVisibility(View.GONE);
                    normal_tca_layout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        facility_community_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1){ //Facility based
                    facility_based_model_layout.setVisibility(View.VISIBLE);
                    community_based_model_layout.setVisibility(View.GONE);
                }else if (position == 2){ //Community based
                    facility_based_model_layout.setVisibility(View.GONE);
                    community_based_model_layout.setVisibility(View.VISIBLE);
                }else {
                    facility_based_model_layout.setVisibility(View.GONE);
                    community_based_model_layout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        facility_based_model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){
                    FACILITY_BASED_MODEL = facility_based_model[position];
                    dcm_submit_layout.setVisibility(View.VISIBLE);
                }else {
                    dcm_submit_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        community_based_model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){
                    COMMUNITY_BASED_MODEL = community_based_model[position];
                    dcm_submit_layout.setVisibility(View.VISIBLE);
                }else {
                    dcm_submit_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mfl_code.getText().toString())){
                    mfl_code.setError("Please enter MFL code");
                }else if (TextUtils.isEmpty(ccc_no.getText().toString())){
                    ccc_no.setError("Please enter CCC Number");
                }else {
                    if (isGreaterThanYear){
                        wellness_level_layout.setVisibility(View.GONE);
                        stability_layout.setVisibility(View.VISIBLE);
                    }else {
                        wellness_level_layout.setVisibility(View.VISIBLE);
                        stability_layout.setVisibility(View.GONE);
                    }
                }
            }
        });





    }

    void initViews(){

        wellness_level_layout= findViewById(R.id.wellness_level_layout);
        stability_layout= findViewById(R.id.stability_layout);
        on_dcm_layout= findViewById(R.id.on_dcm_layout);
        facility_community_layout= findViewById(R.id.facility_community_layout);
        facility_based_model_layout= findViewById(R.id.facility_based_model_layout);
        community_based_model_layout= findViewById(R.id.community_based_model_layout);
        dcm_submit_layout= findViewById(R.id.dcm_submit_layout);
        normal_tca_layout= findViewById(R.id.normal_tca_layout);

        btn_check = findViewById(R.id.btn_check);
        btn_submit_apt = findViewById(R.id.btn_submit_apt);
        btn_dcm_submit_apt = findViewById(R.id.btn_dcm_submit_apt);



        appointment_type_spinner=(Spinner) findViewById(R.id.appointment_type_spinner);
        previous_appointment_spinner=(Spinner) findViewById(R.id.previous_appointment_spinner);
        wellness_level_spinner=(Spinner) findViewById(R.id.wellness_level_spinner);
        stability_level_spinner=(Spinner) findViewById(R.id.stability_level_spinner);
        on_dcm_spinner=(Spinner) findViewById(R.id.on_dcm_spinner);
        facility_community_spinner=(Spinner) findViewById(R.id.facility_community_spinner);
        facility_based_model_spinner=(Spinner) findViewById(R.id.facility_based_model_spinner);
        community_based_model_spinner=(Spinner) findViewById(R.id.community_based_model_spinner);

        mfl_code= findViewById(R.id.mfl_code);
        ccc_no= findViewById(R.id.ccc_no);
        clinical_review_date= findViewById(R.id.clinical_review_date);
        stable_appointment_date= findViewById(R.id.stable_appointment_date);
        appointment_date= findViewById(R.id.appointment_date);


//        SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),appnment);
        ArrayAdapter<String> customAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, appnment);
        customAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointment_type_spinner.setAdapter(customAdapter);

//        SpinnerAdapter previousAdapter=new SpinnerAdapter(getApplicationContext(),previous);
        ArrayAdapter<String> previousAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, previous);
        previousAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        previous_appointment_spinner.setAdapter(previousAdapter);


        ArrayAdapter<String> wellnessAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, wellness);
        wellnessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wellness_level_spinner.setAdapter(wellnessAdapter);

        ArrayAdapter<String> stabilityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stability);
        stabilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stability_level_spinner.setAdapter(stabilityAdapter);

        ArrayAdapter<String> on_dcmAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, on_dcm);
        on_dcmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        on_dcm_spinner.setAdapter(on_dcmAdapter);

        ArrayAdapter<String> facility_communityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, facility_community);
        facility_communityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facility_community_spinner.setAdapter(facility_communityAdapter);

        ArrayAdapter<String> facility_based_modelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, facility_based_model);
        facility_based_modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facility_based_model_spinner.setAdapter(facility_based_modelAdapter);

        ArrayAdapter<String> community_based_modelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, community_based_model);
        community_based_modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        community_based_model_spinner.setAdapter(community_based_modelAdapter);


        stable_appointment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cur_calender = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(DCMActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                long date_ship_millis = calendar.getTimeInMillis();
                                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

                                STABLE_REFILL_DATE = newFormat.format(date_ship_millis);
                                stable_appointment_date.setText(newFormat.format(date_ship_millis));
                            }
                        }, cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        clinical_review_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cur_calender = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(DCMActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                long date_ship_millis = calendar.getTimeInMillis();
                                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

                                CLINICAL_REVIEW_DATE = newFormat.format(date_ship_millis);
                                clinical_review_date.setText(newFormat.format(date_ship_millis));
                            }
                        }, cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        appointment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cur_calender = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(DCMActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                long date_ship_millis = calendar.getTimeInMillis();
                                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

                                APPOINTMENT_DATE = newFormat.format(date_ship_millis);
                                appointment_date.setText(newFormat.format(date_ship_millis));
                            }
                        }, cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

    }
}
