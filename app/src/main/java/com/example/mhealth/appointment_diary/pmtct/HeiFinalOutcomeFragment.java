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
import com.google.android.material.textfield.TextInputLayout;

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


public class HeiFinalOutcomeFragment extends Fragment {
    private Unbinder unbinder;
    private View root;
    private Context context;


    RequestQueue queue;

    private String phone_no;



    private String FINAL_OUTCOME = "";
    private String HEI_NO = "";
    private int ID = 0;
    private String DECEASED_DATE = "";
    private String TO_DATE = "";


    String[] young_final_outcome = {"Select final outcome","Dead","LTFU", "TO"};
    String[] old_final_outcome = {"Select final outcome","Enroll to CCC","Discharged from PMTCT"};


    @BindView(R.id.search_hei_no)
    EditText search_hei_no;

    @BindView(R.id.btn_search)
    Button btn_search;

    @BindView(R.id.outcome_spinner)
    Spinner outcome_spinner;

    @BindView(R.id.f_name)
    TextView f_name;

    @BindView(R.id.m_name)
    TextView m_name;

    @BindView(R.id.l_name)
    TextView l_name;

    @BindView(R.id.dob)
    TextView dob;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.final_outcome_layout)
    LinearLayout final_outcome_layout;

    @BindView(R.id.deceased_date_input)
    TextInputLayout deceased_date_input;

    @BindView(R.id.deceased_date)
    EditText deceased_date;

    @BindView(R.id.to_date_input)
    TextInputLayout to_date_input;

    @BindView(R.id.to_date)
    EditText to_date;




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
        root =  inflater.inflate(R.layout.hei_final_outcome_fragment, container, false);
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



        deceased_date.setOnClickListener(new View.OnClickListener() {
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

                                DECEASED_DATE = newFormat.format(date_ship_millis);

                                deceased_date.setText(newFormat.format(date_ship_millis));
                            }
                        }, cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH));

                 datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });

        to_date.setOnClickListener(new View.OnClickListener() {
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

                                TO_DATE = newFormat.format(date_ship_millis);

                                to_date.setText(newFormat.format(date_ship_millis));
                            }
                        }, cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(search_hei_no.getText().toString())){
                    ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Missing HEI number","Please enter HEI number to continue",context);
                    bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
                }else {
                    searchHei();
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFinalOutcome())
                    updateFinalOutcome();
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

    private boolean validateFinalOutcome() {
        boolean valid = true;


        if (FINAL_OUTCOME.equals("") || FINAL_OUTCOME.equals("Select final outcome")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select final outcome",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (HEI_NO.equals("") ) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please search a HEI number",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (FINAL_OUTCOME.equals("Dead") && DECEASED_DATE.equals("") ) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select Deceased Date",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (FINAL_OUTCOME.equals("TO") && TO_DATE.equals("") ) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select TO Date",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        return valid;
    }

    private void searchHei() {
        JSONObject payload = new JSONObject();
        try {
            payload.put("hei_no", search_hei_no.getText().toString());
            payload.put("phone_no", phone_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Config.SEARCH_HEI_FINAL, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");

                    if (success) {

                        JSONObject hei_data = response.has("message") ? response.getJSONObject("message") : null;

                        if (hei_data != null){

                            final_outcome_layout.setVisibility(View.VISIBLE);


                            int _id = hei_data.has("id") ? hei_data.getInt("id") : 0;
                            String fname = hei_data.has("f_name") ? hei_data.getString("f_name") : "";
                            String mname = hei_data.has("m_name") ? hei_data.getString("m_name") : "";
                            String lname = hei_data.has("l_name") ? hei_data.getString("l_name") : "";
                            String _dob = hei_data.has("dob") ? hei_data.getString("dob") : "";
                            int months = hei_data.has("months") ? hei_data.getInt("months") : 0;

                            HEI_NO = search_hei_no.getText().toString();
                            ID = _id;

                            f_name.setText("FIRST NAME: "+fname);
                            m_name.setText("MIDDLE NAME: "+mname);
                            l_name.setText("LAST NAME: "+lname);
                            dob.setText("DOB: "+_dob);



                            if (months < 24){
                                ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, young_final_outcome);
                                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                outcome_spinner.setAdapter(mAdapter);


                                outcome_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        FINAL_OUTCOME = young_final_outcome[position];

                                        if (FINAL_OUTCOME.equals("Dead"))
                                            deceased_date_input.setVisibility(View.VISIBLE);
                                        else
                                            deceased_date_input.setVisibility(View.GONE);


                                        if (FINAL_OUTCOME.equals("TO"))
                                            to_date_input.setVisibility(View.VISIBLE);
                                        else
                                            to_date_input.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }else {
                                ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, old_final_outcome);
                                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                outcome_spinner.setAdapter(mAdapter);


                                outcome_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        FINAL_OUTCOME = old_final_outcome[position];
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }


                        }else {

                            final_outcome_layout.setVisibility(View.GONE);

                            f_name.setVisibility(View.GONE);
                            m_name.setVisibility(View.GONE);
                            l_name.setVisibility(View.GONE);
                            outcome_spinner.setVisibility(View.GONE);

                           HEI_NO = "";
                           FINAL_OUTCOME = "";

                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Error","Fatal system error occurred. Please contact admin",context);
                            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
                        }

                    } else {

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
                final_outcome_layout.setVisibility(View.GONE);


                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    String body;
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");

                            Log.e("error response : ", body.toString());
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


    private void updateFinalOutcome() {
//        JSONObject payload = new JSONObject();
//        try {
//            payload.put("hei_no", hei_no.getText().toString());
//            payload.put("user_phone", phone_no);
//            payload.put("clinic_number", clinic_number.getText().toString());
//            payload.put("client_status", CLIENT_STATUS);
//            payload.put("enrollment_date", ENROLLMENT_DATE);
//            payload.put("art_date", ART_DATE);
//            payload.put("dob", DOB_DATE);
//            payload.put("motivational_enable", MOTIVATION);
//            payload.put("file_no", file_no.getText().toString());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        Log.e("payload: ", payload.toString());
//
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
//                Config.UPDATE_PCR+SRV_ID, payload, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.e("Response: ", response.toString());
//                try {
//                    boolean success = response.has("success") && response.getBoolean("success");
//                    String message = response.has("message") ? response.getString("message") : "";
//
//                    if (success) {
//                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Success",message, context);
//                        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
//
//                        //reset views
//                        pcr_details_layout.setVisibility(View.GONE);
//
//                        SRV_ID = 0;
//                        SRV_CLINIC_NUMBER = "";
//                        SRV_CLIENT_STATUS = "";
//                        SRV_ENROLLMENT_DATE = "";
//                        SRV_ART_DATE = "";
//                        SRV_MOTIVATION_ENABLE = "";
//                        SRV_FILE_NO = "";
//                        SRV_HEI_NO = "";
//                        SRV_DOB = "";
//
//                        search_hei_no.clearComposingText();
//                        hei_no.clearComposingText();
//                        dob.clearComposingText();
//                        file_no.clearComposingText();
//                        clinic_number.clearComposingText();
//                        motivation_spinner.setSelection(0);
//
//
//                    } else {
//                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Failed",message,context);
//                        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                NetworkResponse response = error.networkResponse;
//                if(response != null && response.data != null){
//                    String body;
//                    //get status code here
//                    String statusCode = String.valueOf(error.networkResponse.statusCode);
//                    //get response body and parse with appropriate encoding
//                    if(error.networkResponse.data!=null) {
//                        try {
//                            body = new String(error.networkResponse.data,"UTF-8");
//
//                            JSONObject json = new JSONObject(body);
//                            //                            Log.e("error response : ", json.toString());
//
//
//                            String message = json.has("message") ? json.getString("message") : "";
//                            String reason = json.has("reason") ? json.getString("reason") : "";
//
//                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance(message,reason,context);
//                            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
//
//                        } catch (UnsupportedEncodingException | JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }else {
//
//                    Log.e("VOlley error :", error.getLocalizedMessage()+" message:"+error.getMessage());
//                    Toast.makeText(context, VolleyErrors.getVolleyErrorMessages(error, context),Toast.LENGTH_LONG).show();
//                }
//
////             Log.e(TAG, "Error: " + error.getMessage());
//            }
//        }){
//            /**
//             * Passing some request headers
//             */
//            @Override
//            public Map<String, String> getHeaders() {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                headers.put("Accept", "application/json");
//                return headers;
//            }
//        };
//
//        queue.add(jsonObjReq);
    }






}
