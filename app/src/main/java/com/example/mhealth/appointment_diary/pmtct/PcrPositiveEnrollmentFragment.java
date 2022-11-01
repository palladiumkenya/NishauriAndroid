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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.Dialogs.ErrorMessage;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.config.VolleyErrors;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PcrPositiveEnrollmentFragment extends Fragment {
    private Unbinder unbinder;
    private View root;
    private Context context;


    RequestQueue queue;

    private String phone_no;
    private String MOTIVATION = "";

    private String ENROLLMENT_DATE = "";
    private long ENROLLMENT_DATE_MILLIS = 0;

    private String ART_DATE = "";
    private long ART_DATE_MILLIS = 0;

    private String DOB_DATE = "";
    private long DOB_DATE_MILLIS = 0;

    private String CLIENT_STATUS = "";

    private int SRV_ID = 0;
    private String SRV_CLINIC_NUMBER = "";
    private String SRV_CLIENT_STATUS = "";
    private String SRV_ENROLLMENT_DATE = "";
    private String SRV_ART_DATE = "";
    private String SRV_MOTIVATION_ENABLE = "";
    private String SRV_FILE_NO = "";
    private String SRV_HEI_NO = "";
    private String SRV_DOB = "";
    public String  z;


    String[] yes_no = {"Enable motivation?","YES","NO"};
    String[] client_status = {"Client status*","ART","On Care","Pre-ART"};


    @BindView(R.id.search_hei_no)
    EditText search_hei_no;

    @BindView(R.id.btn_search)
    Button btn_search;

    @BindView(R.id.motivation_spinner)
    Spinner motivation_spinner;

    @BindView(R.id.enrollment_date)
    EditText enrollment_date;

    @BindView(R.id.art_date)
    EditText art_date;

    @BindView(R.id.dob)
    EditText dob;

    @BindView(R.id.clinic_number)
    EditText clinic_number;

    @BindView(R.id.client_status_spinner)
    Spinner client_status_spinner;

    @BindView(R.id.file_no)
    EditText file_no;

    @BindView(R.id.hei_no)
    EditText hei_no;

    @BindView(R.id.pcr_details_layout)
    LinearLayout pcr_details_layout;

    @BindView(R.id.btn_submit_pcr)
    Button btn_submit_pcr;







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
        root =  inflater.inflate(R.layout.pcr_positive_enrollment_fragment, container, false);
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


        ArrayAdapter<String> pcrAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, yes_no);
        pcrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motivation_spinner.setAdapter(pcrAdapter);


        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, client_status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        client_status_spinner.setAdapter(statusAdapter);


        motivation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                MOTIVATION = yes_no[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        client_status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CLIENT_STATUS = client_status[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        enrollment_date.setOnClickListener(new View.OnClickListener() {
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

                                ENROLLMENT_DATE = newFormat.format(date_ship_millis);
                                ENROLLMENT_DATE_MILLIS = date_ship_millis;

                                enrollment_date.setText(newFormat.format(date_ship_millis));
                            }
                        }, cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        art_date.setOnClickListener(new View.OnClickListener() {
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

                                ART_DATE = newFormat.format(date_ship_millis);
                                ART_DATE_MILLIS = date_ship_millis;

                                art_date.setText(newFormat.format(date_ship_millis));
                            }
                        }, cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


//        dob.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar cur_calender = Calendar.getInstance();
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                                Calendar calendar = Calendar.getInstance();
//                                calendar.set(Calendar.YEAR, year);
//                                calendar.set(Calendar.MONTH, month);
//                                calendar.set(Calendar.DAY_OF_MONTH, day);
//                                long date_ship_millis = calendar.getTimeInMillis();
//                                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//                                DOB_DATE = newFormat.format(date_ship_millis);
//                                DOB_DATE_MILLIS = date_ship_millis;
//
//                                dob.setText(newFormat.format(date_ship_millis));
//                            }
//                        }, cur_calender.get(Calendar.YEAR),
//                        cur_calender.get(Calendar.MONTH),
//                        cur_calender.get(Calendar.DAY_OF_MONTH));
//
//               // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
//                datePickerDialog.show();
//            }
//        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(search_hei_no.getText().toString())){
                    ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Missing HEI number","Please enter HEI number to continue",context);
                    bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
                }else {
                    searchPcr();
                }
            }
        });

        btn_submit_pcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePcr())
                    updatePcr();
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

    private boolean validatePcr() {
        boolean valid = true;



        if (TextUtils.isEmpty(hei_no.getText().toString())) {
            hei_no.setError("HEI number is required");
            valid = false;
            return valid;
        }



        if (TextUtils.isEmpty(file_no.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please enter file number",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (CLIENT_STATUS.equals("") || CLIENT_STATUS.equals("Client status")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select Client status",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(clinic_number.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please enter clinic number",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (ENROLLMENT_DATE.equals("")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please enter enrollment date",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (ART_DATE.equals("")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please enter ART date",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (DOB_DATE.equals("")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please enter date of birth",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (MOTIVATION.equals("") || MOTIVATION.equals("Enable motivation?")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select if to enable motivation or not",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }


        if (ART_DATE_MILLIS < ENROLLMENT_DATE_MILLIS){
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","ART date can not be less than enrollment date",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (ENROLLMENT_DATE_MILLIS < DOB_DATE_MILLIS ){
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Enrollment date can not be less than dat of birth",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        return valid;
    }

    private void searchPcr() {
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
            payload.put("hei_no", search_hei_no.getText().toString());
            payload.put("user_phone", phone_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                z+Config.SEARCH_PCR1, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");

                    if (success) {
                        pcr_details_layout.setVisibility(View.VISIBLE);

                        JSONObject hei_data = response.has("message") ? response.getJSONObject("message") : null;

                        if (hei_data != null){

                            int srv_id = hei_data.has("id") ? hei_data.getInt("id") : 0;
                            String srv_clinic_number = hei_data.has("clinic_number") ? hei_data.getString("clinic_number") : "";
                            String srv_client_status = hei_data.has("client_status") ? hei_data.getString("client_status") : "";
                            String srv_enrollment_date = hei_data.has("enrollment_date") ? hei_data.getString("enrollment_date") : "";
                            String srv_art_date = hei_data.has("art_date") ? hei_data.getString("art_date") : "";
                            String srv_motivational_enable = hei_data.has("motivational_enable") ? hei_data.getString("motivational_enable") : "";
                            String srv_file_no = hei_data.has("file_no") ? hei_data.getString("file_no") : "";
                            String srv_hei_no = hei_data.has("hei_no") ? hei_data.getString("hei_no") : "";
                            String srv_dob = hei_data.has("dob") ? hei_data.getString("dob") : "";


                            SRV_ID = srv_id;
                            SRV_CLINIC_NUMBER = srv_clinic_number;
                            SRV_CLIENT_STATUS = srv_client_status;
                            SRV_ENROLLMENT_DATE = srv_enrollment_date;
                            SRV_ART_DATE = srv_art_date;
                            SRV_MOTIVATION_ENABLE = srv_motivational_enable;
                            SRV_FILE_NO = srv_file_no;
                            SRV_HEI_NO = srv_hei_no;
                            SRV_DOB = srv_dob;

                            if (srv_dob!=null){
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
                                Date date = sdf.parse(srv_dob);

                                DOB_DATE = srv_dob;
                                DOB_DATE_MILLIS = date.getTime();
                            }


                            hei_no.setText(srv_hei_no);
                            dob.setText(srv_dob);

                            if (srv_file_no!=null)
                                file_no.setText(srv_file_no);

                            if (srv_clinic_number!=null)
                                clinic_number.setText(srv_clinic_number);

                            if (srv_motivational_enable.equals("No"))
                                motivation_spinner.setSelection(2);
                            else if (srv_motivational_enable.equals("Yes"))
                                motivation_spinner.setSelection(1);
                            else
                                motivation_spinner.setSelection(0);


                        }else {
                            pcr_details_layout.setVisibility(View.GONE);

                           SRV_ID = 0;
                           SRV_CLINIC_NUMBER = "";
                           SRV_CLIENT_STATUS = "";
                           SRV_ENROLLMENT_DATE = "";
                           SRV_ART_DATE = "";
                           SRV_MOTIVATION_ENABLE = "";
                           SRV_FILE_NO = "";
                           SRV_HEI_NO = "";
                           SRV_DOB = "";

                            hei_no.clearComposingText();
                            dob.clearComposingText();
                            file_no.clearComposingText();
                            clinic_number.clearComposingText();
                            motivation_spinner.setSelection(0);

                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Error","Fatal system error occurred. Please contact admin",context);
                            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
                        }

                    } else {

                        pcr_details_layout.setVisibility(View.GONE);

                        String message = response.has("message") ? response.getString("message") : "";

                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Error",message, context);
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


    private void updatePcr() {
        try {
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                    //zz=_url.get(x).getStage_name1();
                    // Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
                }
            }
        }catch( Exception e){

        }
        /*UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
        String  z=  _url.base_url1;*/
        JSONObject payload = new JSONObject();
        try {
            payload.put("hei_no", hei_no.getText().toString());
            payload.put("user_phone", phone_no);
            payload.put("clinic_number", clinic_number.getText().toString());
            payload.put("client_status", CLIENT_STATUS);
            payload.put("enrollment_date", ENROLLMENT_DATE);
            payload.put("art_date", ART_DATE);
            payload.put("dob", DOB_DATE);
            payload.put("motivational_enable", MOTIVATION);
            payload.put("file_no", file_no.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                z+Config.UPDATE_PCR1+SRV_ID, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");
                    String message = response.has("message") ? response.getString("message") : "";

                    if (success) {
                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Success",message, context);
                        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());

                        //reset views
                        pcr_details_layout.setVisibility(View.GONE);

                        SRV_ID = 0;
                        SRV_CLINIC_NUMBER = "";
                        SRV_CLIENT_STATUS = "";
                        SRV_ENROLLMENT_DATE = "";
                        SRV_ART_DATE = "";
                        SRV_MOTIVATION_ENABLE = "";
                        SRV_FILE_NO = "";
                        SRV_HEI_NO = "";
                        SRV_DOB = "";

                        search_hei_no.clearComposingText();
                        hei_no.clearComposingText();
                        dob.clearComposingText();
                        file_no.clearComposingText();
                        clinic_number.clearComposingText();
                        motivation_spinner.setSelection(0);


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
