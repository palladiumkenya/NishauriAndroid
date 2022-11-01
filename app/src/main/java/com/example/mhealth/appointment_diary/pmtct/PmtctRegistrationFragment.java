package com.example.mhealth.appointment_diary.pmtct;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.DCMActivity;
import com.example.mhealth.appointment_diary.Dialogs.ErrorMessage;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.config.VolleyErrors;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.google.android.material.snackbar.Snackbar;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PmtctRegistrationFragment extends Fragment {
    private Unbinder unbinder;
    private View root;
    private Context context;


    RequestQueue queue;

    private String phone_no;

    public  String z;


    String[] gender_list = {"Please select gender","Female","Male"};
    String[] yes_no = {"Type of caregiver","Breastfeeding - Yes","Breastfeeding - No", "Pregnant", "Not applicable" };


    private String BREASTFEEDING = "";
    private String GENDER = "";
    private String HEI_DOB = "";



    @BindView(R.id.mfl_code)
    EditText mfl_code;

    @BindView(R.id.ccc_no)
    EditText ccc_no;

    @BindView(R.id.btn_check)
    Button btn_check;

    @BindView(R.id.breastfeeding_layout)
    LinearLayout breastfeeding_layout;

    @BindView(R.id.breastfeeding_spinner)
    Spinner breastfeeding_spinner;

    @BindView(R.id.btn_submit_no_hei)
    Button btn_submit_no_hei;

    @BindView(R.id.register_layout)
    LinearLayout register_layout;

    @BindView(R.id.hei_no)
    EditText hei_no;

    @BindView(R.id.gender_spinner)
    Spinner gender_spinner;

    @BindView(R.id.dob)
    EditText dob;

    @BindView(R.id.first_name)
    EditText first_name;


    @BindView(R.id.middle_name)
    EditText middle_name;


    @BindView(R.id.last_name)
    EditText last_name;

    @BindView(R.id.btn_submit_reg)
    Button btn_submit_reg;



    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.pmtct_reg_fragment, container, false);
        unbinder = ButterKnife.bind(this, root);

        List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");

        for(int x=0;x<myl.size();x++){
            String un=myl.get(x).getUname();
            List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
            for(int y=0;y<myl2.size();y++){
                phone_no=myl2.get(y).getPhone();
            }
        }

        queue = Volley.newRequestQueue(context); // this = context


        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, gender_list);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(genderAdapter);


        ArrayAdapter<String> yesNoAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, yes_no);
        yesNoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breastfeeding_spinner.setAdapter(yesNoAdapter);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cur_calender = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                long date_ship_millis = calendar.getTimeInMillis();
                                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");

                                HEI_DOB = newFormat.format(date_ship_millis);
                                dob.setText(newFormat.format(date_ship_millis));
                            }
                        }, cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
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
                    breastfeeding_layout.setVisibility(View.GONE);
                    btn_submit_no_hei.setVisibility(View.GONE);
                    register_layout.setVisibility(View.GONE);

                    breastfeeding_spinner.setSelection(0);
                    gender_spinner.setSelection(0);

                    hei_no.getText().clear();
                    first_name.getText().clear();
                    middle_name.getText().clear();
                    last_name.getText().clear();
                    dob.getText().clear();

                    checkPmtct();

                }
            }
        });

        breastfeeding_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BREASTFEEDING = yes_no[position];

                if (BREASTFEEDING.equals("Breastfeeding - Yes") || BREASTFEEDING.equals("Breastfeeding - No")){
                    register_layout.setVisibility(View.VISIBLE);
                    btn_submit_no_hei.setVisibility(View.GONE);

                }else if (BREASTFEEDING.equals("Pregnant")){
                    btn_submit_no_hei.setVisibility(View.VISIBLE);
                    register_layout.setVisibility(View.GONE);
                }else if (BREASTFEEDING.equals("Not applicable")){

                    register_layout.setVisibility(View.VISIBLE);
                    btn_submit_no_hei.setVisibility(View.GONE);
                }

                else {
                    btn_submit_no_hei.setVisibility(View.GONE);
                    register_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                GENDER = gender_list[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_submit_no_hei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mfl_code.getText().toString())){
                    mfl_code.setError("Please enter MFL code");
                }else if (TextUtils.isEmpty(ccc_no.getText().toString())){
                    ccc_no.setError("Please enter CCC Number");
                }else {
//                    breastfeeding_layout.setVisibility(View.GONE);
//                    btn_submit_no_hei.setVisibility(View.GONE);
                    register_layout.setVisibility(View.GONE);

                    breastfeeding_spinner.setSelection(0);
                    gender_spinner.setSelection(0);

                    hei_no.getText().clear();
                    first_name.getText().clear();
                    middle_name.getText().clear();
                    last_name.getText().clear();
                    dob.getText().clear();
                    HEI_DOB = "";


                    submitNoHei();

                }

            }
        });

        btn_submit_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateRegisterHei())
                    registerHei();
            }
        });



        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
//        shimmer_my_container.startShimmerAnimation();
    }

    @Override
    public void onPause() {
//        shimmer_my_container.stopShimmerAnimation();
        super.onPause();
    }

    private boolean validateRegisterHei() {
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


        if (TextUtils.isEmpty(hei_no.getText().toString())) {
            hei_no.setError("HEI number is required");
            valid = false;
            return valid;
        }



        if (GENDER.equals("") || GENDER.equals("Please select gender")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select gender",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(dob.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select date of birth",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(first_name.getText().toString())) {
            first_name.setError("First name is required");
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(last_name.getText().toString())) {
            last_name.setError("Last name is required");
            valid = false;
            return valid;
        }

        return valid;
    }


    private void checkPmtct() {

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                    //zz=_url.get(x).getStage_name1();
                    // Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){

        }
        /*UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
        String  z=  _url.base_url1;*/
        JSONObject payload = new JSONObject();
        try {
            payload.put("clinic_number", mfl_code.getText().toString()+ccc_no.getText().toString());
            payload.put("phone_no", phone_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                z+Config.CHECK_PMTCT1, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");
                    String message = response.has("message") ? response.getString("message") : "";

                    if (success) {

                        breastfeeding_layout.setVisibility(View.VISIBLE);
                        btn_submit_no_hei.setVisibility(View.GONE);
                        register_layout.setVisibility(View.GONE);

                        breastfeeding_spinner.setSelection(0);
                        gender_spinner.setSelection(0);


                        hei_no.getText().clear();
                        first_name.getText().clear();
                        middle_name.getText().clear();
                        last_name.getText().clear();
                        dob.getText().clear();
                        HEI_DOB = "";



                    } else {

                        breastfeeding_layout.setVisibility(View.GONE);
                        btn_submit_no_hei.setVisibility(View.GONE);
                        register_layout.setVisibility(View.GONE);

                        breastfeeding_spinner.setSelection(0);
                        gender_spinner.setSelection(0);

                        hei_no.getText().clear();
                        first_name.getText().clear();
                        middle_name.getText().clear();
                        last_name.getText().clear();
                        dob.getText().clear();
                        HEI_DOB = "";


                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Invalid",message, context);
                        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
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

                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance(message,reason,context);
                            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());

                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }else {

                    Log.e("VOlley error :", error.getLocalizedMessage()+" message:"+error.getMessage());
                    Toast.makeText(context, VolleyErrors.getVolleyErrorMessages(error, context),Toast.LENGTH_LONG).show();
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

    private void submitNoHei(){


        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                    //zz=_url.get(x).getStage_name1();
                    // Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){

        }
       /* UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
        String  z=  _url.base_url1;*/

        JSONObject payload = new JSONObject();
        try {
            payload.put("clinic_number", mfl_code.getText().toString()+ccc_no.getText().toString());
            payload.put("phone_no", phone_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                z+Config.REGISTER_NON_BREASTFEEDING1, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");
                    String message = response.has("message") ? response.getString("message") : "";


                    breastfeeding_layout.setVisibility(View.GONE);
                    btn_submit_no_hei.setVisibility(View.GONE);
                    register_layout.setVisibility(View.GONE);

                    breastfeeding_spinner.setSelection(0);
                    gender_spinner.setSelection(0);

                    hei_no.getText().clear();
                    first_name.getText().clear();
                    middle_name.getText().clear();
                    last_name.getText().clear();
                    dob.getText().clear();
                    HEI_DOB = "";


                    if (success) {
                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Success",message, context);
                        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());

                    } else {

                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Duplicate",message, context);
                        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
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

                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance(message,reason,context);
                            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());

                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }else {

                    Log.e("VOlley error :", error.getLocalizedMessage()+" message:"+error.getMessage());
                    Toast.makeText(context, VolleyErrors.getVolleyErrorMessages(error, context),Toast.LENGTH_LONG).show();
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

    private void registerHei(){
        try {
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                    //zz=_url.get(x).getStage_name1();
                    // Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){

        }
       /* UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
        String  z=  _url.base_url1;*/
        JSONObject payload = new JSONObject();
        try {
            payload.put("clinic_number", mfl_code.getText().toString()+ccc_no.getText().toString());
            payload.put("phone_no", phone_no);
            payload.put("hei_no", hei_no.getText().toString());
            payload.put("hei_gender", java.util.Arrays.asList(gender_list).indexOf(GENDER));
            payload.put("breastfeeding", java.util.Arrays.asList(yes_no).indexOf(BREASTFEEDING));
            payload.put("hei_dob", HEI_DOB);
            payload.put("hei_first_name", first_name.getText().toString());
            payload.put("hei_middle_name", middle_name.getText().toString());
            payload.put("hei_last_name", last_name.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                z+Config.REGISTER_HEI1, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");
                    String message = response.has("message") ? response.getString("message") : "";

                    if (success) {
//                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Success",message,context);
//                        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());

                        //reset views
                        breastfeeding_layout.setVisibility(View.GONE);
                        btn_submit_no_hei.setVisibility(View.GONE);
                        register_layout.setVisibility(View.GONE);

                        breastfeeding_spinner.setSelection(0);
                        gender_spinner.setSelection(0);

                        hei_no.getText().clear();
                        first_name.getText().clear();
                        middle_name.getText().clear();
                        last_name.getText().clear();
                        dob.getText().clear();
                        HEI_DOB = "";


                        mfl_code.getText().clear();
                        ccc_no.getText().clear();

                        Toast.makeText(context,message, Toast.LENGTH_LONG).show();

                        NavHostFragment.findNavController(PmtctRegistrationFragment.this).navigate(R.id.hei_apt);



                    } else {
                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Failed",message,context);
                        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
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

                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance(message,reason,context);
                            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());

                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }else {

                    Log.e("VOlley error :", error.getLocalizedMessage()+" message:"+error.getMessage());
                    Toast.makeText(context, VolleyErrors.getVolleyErrorMessages(error, context),Toast.LENGTH_LONG).show();
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





}
