package com.example.mhealth.appointment_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.Dialogs.ErrorMessage;
import com.example.mhealth.appointment_diary.appointment_diary.TodaysAppointment;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.config.VolleyErrors;
import com.example.mhealth.appointment_diary.defaulters_diary.DefaulterMainActivity;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.example.mhealth.appointment_diary.utilitymodules.Appointment;
import com.example.mhealth.appointment_diary.utilitymodules.SpinnerAdapter;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DCMActivity extends AppCompatActivity {

    public String z;

    private Spinner wellness_level_spinner,stability_level_spinner,on_dcm_spinner,new_continuing_dcm,facility_community_spinner,facility_based_model_spinner,
            community_based_model_spinner,appointment_type_spinner;
    private EditText mfl_code,ccc_no,stable_appointment_date,clinical_review_date,appointment_date,other_et;
    private LinearLayout wellness_level_layout,stability_layout,on_dcm_layout,facility_community_layout,facility_based_model_layout,community_based_model_layout,
            dcm_submit_layout,normal_tca_layout,other_layout;
    private Button btn_check,btn_submit_apt,btn_dcm_submit_apt;

    SweetAlertDialog mdialog;

   // public String z;





    private String WELLNESS_LEVEL = "";
    private String APT_TYPE = "";
    private String STABILITY_LEVEL = "";
    private String ON_DCM_STATUS = "";
    private int NEW_CONTINUING_DCM = 0;
    private String FACILITY_BASED_MODEL = "";
    private String COMMUNITY_BASED_MODEL = "";

    private String STABLE_REFILL_DATE = "-1";
    private String CLINICAL_REVIEW_DATE = "-1";
    private String APPOINTMENT_DATE = "";

    private int months = 0;
    private String phone_no;

    RequestQueue queue;




    String[] appnment = {"Please select appointment type","Re-Fill","Clinical review","Enhanced Adherence counseling","Lab investigation","VL Booking","Other"};
    String[] wellness = {"Please select wellness level*","Well","Advanced"};
    String[] stability = {"Please select stability level","Stable","Unstable"};
    String[] on_dcm = {"Please select if on DSD","On DSD","NOT on DSD"};
    String[] new_continuing_dcm_choice = {"Please select if new or continuing on DSD","New On DSD","Continuing on DSD"};
    String[] facility_community = {"Please select DCM mode","Facility based","Community based"};
    String[] facility_based_model = {"Please select facility model","Fast track Model","Facility based Adherence clubs/ Support group"};
    String[] community_based_model = {"Please select community model","Peer Led Community ART group","Health provider led Community ART group","Community ART distribution point","Individual patient ART Distribution in the community", "Home based re-fill", "School based re-fill"};


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
        getSupportActionBar().setTitle("DSD clients");

        List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");

        for(int x=0;x<myl.size();x++){
            String un=myl.get(x).getUname();
            List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
            for(int y=0;y<myl2.size();y++){
                phone_no=myl2.get(y).getPhone();
            }
        }

        queue = Volley.newRequestQueue(this); // this = context

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
                    dcm_submit_layout.setVisibility(View.GONE);
                    facility_community_layout.setVisibility(View.GONE);
                    facility_based_model_layout.setVisibility(View.GONE);
                    community_based_model_layout.setVisibility(View.GONE);
                }else {
                    normal_tca_layout.setVisibility(View.GONE);
                    on_dcm_layout.setVisibility(View.GONE);
                    dcm_submit_layout.setVisibility(View.GONE);
                    facility_community_layout.setVisibility(View.GONE);
                    facility_based_model_layout.setVisibility(View.GONE);
                    community_based_model_layout.setVisibility(View.GONE);

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
                    ON_DCM_STATUS = on_dcm[position];
                    new_continuing_dcm.setVisibility(View.VISIBLE);
                    normal_tca_layout.setVisibility(View.GONE);
                }else if (position == 2){ //not on DCM
                    ON_DCM_STATUS = on_dcm[position];
                    facility_community_layout.setVisibility(View.GONE);
                    facility_based_model_layout.setVisibility(View.GONE);
                    dcm_submit_layout.setVisibility(View.GONE);
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

        new_continuing_dcm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NEW_CONTINUING_DCM = position;
                if (position==1){ //new on dcm
                    facility_community_layout.setVisibility(View.VISIBLE);
                }else if (position==2){ //continuinig on dcm
                    facility_community_layout.setVisibility(View.VISIBLE);
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

        appointment_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                APT_TYPE = appnment[position];

                if (APT_TYPE.equals("Other")){
                    other_layout.setVisibility(View.VISIBLE);
                }else {
                    other_layout.setVisibility(View.GONE);
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
                    wellness_level_layout.setVisibility(View.GONE);
                    stability_layout.setVisibility(View.GONE);
                    on_dcm_layout.setVisibility(View.GONE);
                    facility_community_layout.setVisibility(View.GONE);
                    facility_based_model_layout.setVisibility(View.GONE);
                    community_based_model_layout.setVisibility(View.GONE);
                    dcm_submit_layout.setVisibility(View.GONE);
                    normal_tca_layout.setVisibility(View.GONE);

                    wellness_level_spinner.setSelection(0);
                    stability_level_spinner.setSelection(0);
                    on_dcm_spinner.setSelection(0);
                    facility_community_spinner.setSelection(0);
                    facility_based_model_spinner.setSelection(0);
                    community_based_model_spinner.setSelection(0);
                    appointment_type_spinner.setSelection(0);

                    stable_appointment_date.getText().clear();
                    clinical_review_date.getText().clear();
                    appointment_date.getText().clear();
                    other_et.getText().clear();

                    getDuration();
                }
            }
        });

        btn_dcm_submit_apt.setOnClickListener(new View.OnClickListener() {
              /*  UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
              String   a=  _url.base_url1;*/

            @Override
            public void onClick(View v) {

                try {
                    List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                    if (_url.size()==1){
                        for (int x=0; x<_url.size(); x++){
                            z=_url.get(x).getBase_url1();
                        }
                    }
                }catch (Exception e){

                }
                Log.e("months:", String.valueOf(months));
                if (months >= 12) {
                    Log.e("Stability level: ", STABILITY_LEVEL);
                    if(STABILITY_LEVEL.equals("Stable")){
                        //continue with stable logic
                        Log.e("On DCM status: ", ON_DCM_STATUS);
                        if (ON_DCM_STATUS.equals("On DSD")){
                            //validate and post on DCM
                            if (validateOnDcm())
                                bookOnDcm();

                        }else if (ON_DCM_STATUS.equals("NOT on DSD")){
                            if (validateNotOnDcm())
                                bookNormalTca(z+Config.NOT_ON_DCM_BOOKING1);
                        }
                    }else if (STABILITY_LEVEL.equals("Unstable")){
                        if (validateUnstable())
                            bookNormalTca(z+Config.UNSTABLE_BOOKING1);
                    }
                }else {
                    if (validateWellAdvanced())
                        bookNormalTca(z+Config.WELL_ADVANCED_BOOKING1);
                }
            }
        });


        btn_submit_apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("months:", String.valueOf(months));
                if (months >= 12) {
                    Log.e("Stability level: ", STABILITY_LEVEL);
                    if(STABILITY_LEVEL.equals("Stable")){
                        //continue with stable logic
                        Log.e("On DCM status: ", ON_DCM_STATUS);
                        if (ON_DCM_STATUS.equals("On DSD")){
                            //validate and post on DCM
                            if (validateOnDcm())
                                bookOnDcm();

                        }else if (ON_DCM_STATUS.equals("NOT on DSD")){
                            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                            if (_url.size()==1){
                                for (int x=0; x<_url.size(); x++){
                                    z=_url.get(x).getBase_url1();
                                }
                            }
                            if (validateNotOnDcm())
                                bookNormalTca(z+Config.NOT_ON_DCM_BOOKING1);

                        }
                    }else if (STABILITY_LEVEL.equals("Unstable")){
                        List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                        if (_url.size()==1){
                            for (int x=0; x<_url.size(); x++){
                                z=_url.get(x).getBase_url1();
                                                            }
                        }
                        if (validateUnstable())
                            bookNormalTca(z+Config.UNSTABLE_BOOKING1);
                    }
                }else {
                    try {
                        List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                        if (_url.size()==1){
                            for (int x=0; x<_url.size(); x++){
                                z=_url.get(x).getBase_url1();

                            }
                        }
                    }catch (Exception e){

                    }
                    if (validateWellAdvanced())
                        bookNormalTca(z+Config.NOT_ON_DCM_BOOKING1);
                }
            }
        });

    }

    private boolean validateWellAdvanced() {
        boolean valid = true;

        if (TextUtils.isEmpty(mfl_code.getText().toString())) {
            mfl_code.setError(getString(R.string.mfl_code_required));
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(ccc_no.getText().toString())) {
            ccc_no.setError(getString(R.string.ccc_required));
            valid = false;
            return valid;
        }



        if (WELLNESS_LEVEL.equals("") || WELLNESS_LEVEL.equals("Please select wellness level")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select wellness level",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(appointment_date.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select appointment date",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (APT_TYPE.equals("") || APT_TYPE.equals("Please select appointment type")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select appointment type",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (APT_TYPE.equals("Other") && TextUtils.isEmpty(other_et.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please specify other",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        return valid;
    }

    private boolean validateOnDcm() {
        boolean valid = true;

        if (TextUtils.isEmpty(mfl_code.getText().toString())) {
            mfl_code.setError(getString(R.string.mfl_code_required));
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(ccc_no.getText().toString())) {
            ccc_no.setError(getString(R.string.ccc_required));
            valid = false;
            return valid;
        }



        if (STABILITY_LEVEL.equals("") || STABILITY_LEVEL.equals("Please select stability level")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select stability level",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (ON_DCM_STATUS.equals("") || ON_DCM_STATUS.equals("Please select if on DSD")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select if on DSD",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(stable_appointment_date.getText().toString()) && TextUtils.isEmpty(clinical_review_date.getText().toString()) && NEW_CONTINUING_DCM == 2) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select either appointment date or clinical review date",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(stable_appointment_date.getText().toString())) {
            if (NEW_CONTINUING_DCM == 2){ //continuinng
                return  valid;
            }else {
                ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select appointment date",DCMActivity.this);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                valid = false;
                return valid;
            }
        }

        if (TextUtils.isEmpty(clinical_review_date.getText().toString())) {
            if (NEW_CONTINUING_DCM == 2){ //continuinng
                return  valid;
            }else {
                ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select clinical review date",DCMActivity.this);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                valid = false;
                return valid;
            }

        }



        return valid;
    }

    private boolean validateNotOnDcm() {
        boolean valid = true;

        if (TextUtils.isEmpty(mfl_code.getText().toString())) {
            mfl_code.setError(getString(R.string.mfl_code_required));
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(ccc_no.getText().toString())) {
            ccc_no.setError(getString(R.string.ccc_required));
            valid = false;
            return valid;
        }



        if (STABILITY_LEVEL.equals("") || STABILITY_LEVEL.equals("Please select stability level")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select stability level",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (ON_DCM_STATUS.equals("") || ON_DCM_STATUS.equals("Please select if on DSD")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select if on DSD",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }


        if (TextUtils.isEmpty(appointment_date.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select appointment date",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (APT_TYPE.equals("") || APT_TYPE.equals("Please select appointment type")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select appointment type",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (APT_TYPE.equals("Other") && TextUtils.isEmpty(other_et.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please specify other",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }



        return valid;
    }

    private boolean validateUnstable() {
        boolean valid = true;

        if (TextUtils.isEmpty(mfl_code.getText().toString())) {
            mfl_code.setError(getString(R.string.mfl_code_required));
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(ccc_no.getText().toString())) {
            ccc_no.setError(getString(R.string.ccc_required));
            valid = false;
            return valid;
        }



        if (STABILITY_LEVEL.equals("") || STABILITY_LEVEL.equals("Please select stability level")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select stability level",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }


        if (TextUtils.isEmpty(appointment_date.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select appointment date",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (APT_TYPE.equals("") || APT_TYPE.equals("Please select appointment type")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select appointment type",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (APT_TYPE.equals("Other") && TextUtils.isEmpty(other_et.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please specify other",DCMActivity.this);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }



        return valid;
    }


    private void bookNormalTca(String endpoint) {
        JSONObject payload = new JSONObject();
        try {
            payload.put("clinic_number", mfl_code.getText().toString()+ccc_no.getText().toString());
            payload.put("phone_no", phone_no);
            payload.put("appointment_date", APPOINTMENT_DATE);
            payload.put("appointment_type", java.util.Arrays.asList(appnment).indexOf(APT_TYPE));
            payload.put("appointment_other", TextUtils.isEmpty(other_et.getText().toString()) ? -1 : other_et.getText().toString());
            payload.put("category_type", java.util.Arrays.asList(wellness).indexOf(WELLNESS_LEVEL));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                endpoint, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");
                    String message = response.has("message") ? response.getString("message") : "";
                    String status = response.has("status") ? response.getString("status") : "";

                    if (success) {
                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Success",message,DCMActivity.this);
                        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

                        //reset views
                        wellness_level_layout.setVisibility(View.GONE);
                        stability_layout.setVisibility(View.GONE);
                        on_dcm_layout.setVisibility(View.GONE);
                        facility_community_layout.setVisibility(View.GONE);
                        facility_based_model_layout.setVisibility(View.GONE);
                        community_based_model_layout.setVisibility(View.GONE);
                        dcm_submit_layout.setVisibility(View.GONE);
                        normal_tca_layout.setVisibility(View.GONE);

                        wellness_level_spinner.setSelection(0);
                        stability_level_spinner.setSelection(0);
                        on_dcm_spinner.setSelection(0);
                        facility_community_spinner.setSelection(0);
                        facility_based_model_spinner.setSelection(0);
                        community_based_model_spinner.setSelection(0);
                        appointment_type_spinner.setSelection(0);

                        mfl_code.getText().clear();
                        ccc_no.getText().clear();
                        stable_appointment_date.getText().clear();
                        clinical_review_date.getText().clear();
                        appointment_date.getText().clear();
                        other_et.getText().clear();


                    } else {
//                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Failed",message,DCMActivity.this);
//                        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

                        mdialog= new SweetAlertDialog(DCMActivity.this, SweetAlertDialog.ERROR_TYPE);
                        mdialog.setTitleText(message);
//                        mdialog.setContentText(message);
                        mdialog.setCancelable(false);
                        mdialog.setConfirmButton("OK",new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (status.equals("Defaulter")){
                                    startActivity(new Intent(DCMActivity.this, DefaulterMainActivity.class));
                                }else if (status.equals("Appointment")){
                                    startActivity(new Intent(DCMActivity.this, TodaysAppointment.class));
                                }else {
                                    mdialog.dismiss();
                                }
                            }
                        });
                        mdialog.show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    String body;
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");

                            JSONObject json = new JSONObject(body);
                            //                            Log.e("error response : ", json.toString());


                            String message = json.has("message") ? json.getString("message") : "";
                            String reason = json.has("reason") ? json.getString("reason") : "";

                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance(message,reason,DCMActivity.this);
                            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }else {

                    Log.e("VOlley error :", error.getLocalizedMessage()+" message:"+error.getMessage());
                    Toast.makeText(DCMActivity.this, VolleyErrors.getVolleyErrorMessages(error, DCMActivity.this),Toast.LENGTH_LONG).show();
                }

//             Log.e(TAG, "Error: " + error.getMessage());
            }
        }){
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        queue.add(jsonObjReq);
    }

    private void bookOnDcm() {
        try {
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        }catch ( Exception e){


        }
        JSONObject payload = new JSONObject();
        try {
            payload.put("clinic_number", mfl_code.getText().toString()+ccc_no.getText().toString());
            payload.put("phone_no", phone_no);
            payload.put("refill_date", STABLE_REFILL_DATE);
            payload.put("review_date", CLINICAL_REVIEW_DATE);
            payload.put("facility", FACILITY_BASED_MODEL.equals("") ? -1 :  java.util.Arrays.asList(facility_based_model).indexOf(FACILITY_BASED_MODEL));
            payload.put("community", COMMUNITY_BASED_MODEL.equals("") ? -1 :  java.util.Arrays.asList(community_based_model).indexOf(COMMUNITY_BASED_MODEL));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                z+Config.ON_DCM_BOOKING1, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");
                    String message = response.has("message") ? response.getString("message") : "";
                    String status = response.has("status") ? response.getString("status") : "";

                    if (success) {
                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Success",message,DCMActivity.this);
                        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

                        //reset views
                        wellness_level_layout.setVisibility(View.GONE);
                        stability_layout.setVisibility(View.GONE);
                        on_dcm_layout.setVisibility(View.GONE);
                        facility_community_layout.setVisibility(View.GONE);
                        facility_based_model_layout.setVisibility(View.GONE);
                        community_based_model_layout.setVisibility(View.GONE);
                        dcm_submit_layout.setVisibility(View.GONE);
                        normal_tca_layout.setVisibility(View.GONE);

                        wellness_level_spinner.setSelection(0);
                        stability_level_spinner.setSelection(0);
                        on_dcm_spinner.setSelection(0);
                        facility_community_spinner.setSelection(0);
                        facility_based_model_spinner.setSelection(0);
                        community_based_model_spinner.setSelection(0);
                        appointment_type_spinner.setSelection(0);

                        mfl_code.getText().clear();
                        ccc_no.getText().clear();
                        stable_appointment_date.getText().clear();
                        clinical_review_date.getText().clear();
                        appointment_date.getText().clear();
                        other_et.getText().clear();

//                        mdialog= new SweetAlertDialog(DCMActivity.this, SweetAlertDialog.ERROR_TYPE);
//                        mdialog.setTitleText("Success");
//                        mdialog.setContentText(message);
//                        mdialog.setCancelable(false);
//                        mdialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                if (status.equals("Defaulter")){
//                                    startActivity(new Intent(DCMActivity.this, DefaulterMainActivity.class));
//                                }else if (status.equals("Appointment")){
//                                    startActivity(new Intent(DCMActivity.this, TodaysAppointment.class));
//                                }
//                            }
//                        });
//                        mdialog.show();


                    } else {
//                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Failed",message,DCMActivity.this);
//                        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

                        mdialog= new SweetAlertDialog(DCMActivity.this, SweetAlertDialog.ERROR_TYPE);
                        mdialog.setTitleText(message);
//                        mdialog.setContentText(message);
                        mdialog.setCancelable(false);
                        mdialog.setConfirmButton("OK",new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (status.equals("Defaulter")){
                                    startActivity(new Intent(DCMActivity.this, DefaulterMainActivity.class));
                                }else if (status.equals("Appointment")){
                                    startActivity(new Intent(DCMActivity.this, TodaysAppointment.class));
                                }else {
                                    mdialog.dismiss();
                                }
                            }
                        });
                        mdialog.show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    String body;
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");

                            JSONObject json = new JSONObject(body);
                            //                            Log.e("error response : ", json.toString());


                            String message = json.has("message") ? json.getString("message") : "";
                            String reason = json.has("reason") ? json.getString("reason") : "";

                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance(message,reason,DCMActivity.this);
                            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }else {

                    Log.e("VOlley error :", error.getLocalizedMessage()+" message:"+error.getMessage());
                    Toast.makeText(DCMActivity.this, VolleyErrors.getVolleyErrorMessages(error, DCMActivity.this),Toast.LENGTH_LONG).show();
                }

//             Log.e(TAG, "Error: " + error.getMessage());
            }
        }){
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        queue.add(jsonObjReq);
    }

    private void getDuration() {
        try {
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();

                }
            }
        }catch (Exception e){

        }
        JSONObject payload = new JSONObject();
        try {
            payload.put("clinic_number", mfl_code.getText().toString()+ccc_no.getText().toString());
            payload.put("phone_no", phone_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                z+Config.GET_ENROLLMENT_DURATION1, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");
                    String message = response.has("message") ? response.getString("message") : "";
                    months = response.has("months") ? response.getInt("months") : 0;

                    if (success) {

                        if (months >= 12) {
                            wellness_level_layout.setVisibility(View.GONE);
                            stability_layout.setVisibility(View.VISIBLE);
                        }else {
                            wellness_level_layout.setVisibility(View.VISIBLE);
                            stability_layout.setVisibility(View.GONE);
                        }
                    } else {

                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Invalid",message,DCMActivity.this);
                        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    String body;
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");

                            JSONObject json = new JSONObject(body);
                            //                            Log.e("error response : ", json.toString());


                            String message = json.has("message") ? json.getString("message") : "";
                            String reason = json.has("reason") ? json.getString("reason") : "";

                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance(message,reason,DCMActivity.this);
                            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }else {

                    Log.e("VOlley error :", error.getLocalizedMessage()+" message:"+error.getMessage());
                    Toast.makeText(DCMActivity.this, VolleyErrors.getVolleyErrorMessages(error, DCMActivity.this),Toast.LENGTH_LONG).show();
                }

//             Log.e(TAG, "Error: " + error.getMessage());
            }
        }){
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        queue.add(jsonObjReq);
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
        wellness_level_spinner=(Spinner) findViewById(R.id.wellness_level_spinner);
        stability_level_spinner=(Spinner) findViewById(R.id.stability_level_spinner);
        on_dcm_spinner=(Spinner) findViewById(R.id.on_dcm_spinner);
        new_continuing_dcm=(Spinner) findViewById(R.id.new_continuing_dcm);
        facility_community_spinner=(Spinner) findViewById(R.id.facility_community_spinner);
        facility_based_model_spinner=(Spinner) findViewById(R.id.facility_based_model_spinner);
        community_based_model_spinner=(Spinner) findViewById(R.id.community_based_model_spinner);

        mfl_code= findViewById(R.id.mfl_code);
        ccc_no= findViewById(R.id.ccc_no);
        clinical_review_date= findViewById(R.id.clinical_review_date);
        stable_appointment_date= findViewById(R.id.stable_appointment_date);
        appointment_date= findViewById(R.id.appointment_date);

        other_et= findViewById(R.id.other_et);
        other_layout= findViewById(R.id.other_layout);


        ArrayAdapter<String> customAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, appnment);
        customAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointment_type_spinner.setAdapter(customAdapter);


        ArrayAdapter<String> wellnessAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, wellness);
        wellnessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wellness_level_spinner.setAdapter(wellnessAdapter);

        ArrayAdapter<String> stabilityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stability);
        stabilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stability_level_spinner.setAdapter(stabilityAdapter);

        ArrayAdapter<String> on_dcmAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, on_dcm);
        on_dcmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        on_dcm_spinner.setAdapter(on_dcmAdapter);

        ArrayAdapter<String> new_continuing_dcmAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new_continuing_dcm_choice);
        new_continuing_dcmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        new_continuing_dcm.setAdapter(new_continuing_dcmAdapter);

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
    private void callURL(){
        try{
            UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
            z=  _url.base_url1;
        }catch (Exception e){

        }
    }
}
