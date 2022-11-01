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
import com.example.mhealth.appointment_diary.models.Hei;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PmtctUpdateHeiFragment extends Fragment {
    private Unbinder unbinder;
    private View root;
    private Context context;

    public String z;


    RequestQueue queue;

    private String phone_no;
    private int HEI_PRIM_KEY = 0;


    String[] gender_list = {"Please select gender","Female","Male"};


    private String HEI_GENDER = "";
    private String HEI_DOB = "";
    private int HEI_ID = 0;
    private int LANGAUAGE_ID = 0;
    private int MFL_CODE = 0;
    private String CLINIC_ID = "";
    private String HEI_NO = "";



    @BindView(R.id.phone_no_et)
    EditText phone_no_et;

    @BindView(R.id.search_hei_no)
    EditText search_hei_no;

    @BindView(R.id.btn_search)
    Button btn_search;

    @BindView(R.id.hei_no_et)
    EditText hei_no_et;

    @BindView(R.id.clinic_id_tv)
    TextView clinic_id_tv;

    @BindView(R.id.hei_gender_spinner)
    Spinner hei_gender_spinner;

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


    @BindView(R.id.hei_details_layout)
    LinearLayout hei_details_layout;


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
        root =  inflater.inflate(R.layout.pmtct_update_hei_fragment, container, false);
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
        hei_gender_spinner.setAdapter(genderAdapter);




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





        hei_gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HEI_GENDER = gender_list[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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


        btn_submit_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUpdateHei())
                    updateHei();
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

    private boolean validateUpdateHei() {
        boolean valid = true;


        if (HEI_GENDER.equals("") || HEI_GENDER.equals("Please select gender")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select HEI gender",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(hei_no_et.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please enter HEI number",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(phone_no_et.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please enter phone number",context);
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

    private void searchHei() {
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
            payload.put("hei_no", search_hei_no.getText().toString());
            payload.put("user_phone", phone_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                z+Config.SEARCH_HEI1, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");

                    if (success) {
                        hei_details_layout.setVisibility(View.VISIBLE);

                        JSONObject hei_data = response.has("hei_data") ? response.getJSONObject("hei_data") : null;

                        if (hei_data != null){

                            int id = hei_data.has("id") ? hei_data.getInt("id") : 0;
                            String f_name = hei_data.has("f_name") ? hei_data.getString("f_name") : "";
                            String m_name = hei_data.has("m_name") ? hei_data.getString("m_name") : "";
                            String l_name = hei_data.has("l_name") ? hei_data.getString("l_name") : "";
                            String hei_dob = hei_data.has("dob") ? hei_data.getString("dob") : "";
                            String hei_no = hei_data.has("hei_no") ? hei_data.getString("hei_no") : "";
                            String clinic_id = hei_data.has("clinic_id") ? hei_data.getString("clinic_id") : "";
                            String phone_no_str = hei_data.has("phone_no") ? hei_data.getString("phone_no") : "";
                            int gender = hei_data.has("gender") ? hei_data.getInt("gender") : 0;
                            int language_id = hei_data.has("language_id") ? hei_data.getInt("language_id") : 0;
                            int mfl_code = hei_data.has("mfl_code") ? hei_data.getInt("mfl_code") : 0;
                            int hei_prim_key = hei_data.has("hei_prim_key") ? hei_data.getInt("hei_prim_key") : 0;

                            CLINIC_ID = clinic_id;
                            LANGAUAGE_ID = language_id;
                            HEI_NO = hei_no;
                            MFL_CODE = mfl_code;
                            HEI_ID = id;
                            HEI_DOB = hei_dob;
                            HEI_GENDER = gender_list[gender];
                            dob.setText(hei_dob);
                            first_name.setText(f_name);
                            middle_name.setText(m_name);
                            last_name.setText(l_name);
                            hei_gender_spinner.setSelection(gender);
                            phone_no_et.setText(phone_no_str);

                            HEI_PRIM_KEY = hei_prim_key;

                            hei_no_et.setText(hei_no);
                            clinic_id_tv.setText("CLINIC ID: "+clinic_id);

                        }else {
                            hei_details_layout.setVisibility(View.GONE);

                            CLINIC_ID = "";
                            LANGAUAGE_ID = 0;
                            HEI_NO = "";
                            MFL_CODE = 0;
                            HEI_ID = 0;
                            HEI_DOB = "";
                            HEI_GENDER = gender_list[0];
                            phone_no_et.clearComposingText();
                            dob.clearComposingText();
                            first_name.clearComposingText();
                            middle_name.clearComposingText();
                            last_name.clearComposingText();
                            hei_gender_spinner.setSelection(0);

                            hei_no_et.clearComposingText();
                            clinic_id_tv.setText("CLINIC ID: ");

                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Error","Fatal system error occurred. Please contact admin",context);
                            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
                        }

                    } else {

                        hei_details_layout.setVisibility(View.GONE);


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


    private void updateHei(){
        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                    //zz=_url.get(x).getStage_name1();
                    // Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e){

        }
        /*UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
        String  z=  _url.base_url1;*/
        JSONObject payload = new JSONObject();
        try {
            payload.put("user_phone", phone_no);
            payload.put("id", HEI_ID);
            payload.put("f_name", first_name.getText().toString());
            payload.put("m_name", middle_name.getText().toString());
            payload.put("l_name", last_name.getText().toString());
            payload.put("language_id", LANGAUAGE_ID);
            payload.put("dob", HEI_DOB);
            payload.put("phone_no", phone_no_et.getText().toString());
            payload.put("mfl_code", MFL_CODE);
            payload.put("gender", java.util.Arrays.asList(gender_list).indexOf(HEI_GENDER));
            payload.put("clinic_id", CLINIC_ID);
            payload.put("hei_no", hei_no_et.getText().toString());
            payload.put("hei_prim_key", HEI_PRIM_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                z+Config.UPDATE_HEI1+HEI_ID, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");
                    String message = response.has("message") ? response.getString("message") : "";

                    if (success) {
                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Success",message,context);
                        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());



                        hei_details_layout.setVisibility(View.GONE);

                        CLINIC_ID = "";
                        LANGAUAGE_ID = 0;
                        HEI_NO = "";
                        MFL_CODE = 0;
                        HEI_ID = 0;
                        HEI_DOB = "";
                        HEI_GENDER = gender_list[0];
                        phone_no_et.clearComposingText();
                        dob.clearComposingText();
                        first_name.clearComposingText();
                        middle_name.clearComposingText();
                        last_name.clearComposingText();
                        hei_gender_spinner.setSelection(0);

                        hei_no_et.clearComposingText();
                        clinic_id_tv.setText("CLINIC ID: ");




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

                            //JSONObject json = new JSONObject(body);
                            Log.e("error response : ", body.toString());


//                            String message = json.has("message") ? json.getString("message") : "";
//                            String reason = json.has("reason") ? json.getString("reason") : "";

//                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance(message,reason,context);
//                            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());

                        } catch (UnsupportedEncodingException  e) {
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
