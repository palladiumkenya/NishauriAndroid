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
import com.example.mhealth.appointment_diary.DCMActivity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class PmtctHeiAptFragment extends Fragment {
    private Unbinder unbinder;
    private View root;
    private Context context;
    public  String z;

    RequestQueue queue;

    private String phone_no;


    String[] appnment = {"","Re-Fill","Clinical review","Enhanced Adherence counseling","Lab investigation","VL Booking","Other","PCR"};
    //Please select appointment type
    String[] pcr_taken = {"","YES","NO"};
    //Has PCR been taken?*



    private String APT_TYPE = "";
    private String PCR_TAKEN = "";
    private String APPOINTMENT_DATE = "";





    @BindView(R.id.hei_no)
    EditText hei_no;

    @BindView(R.id.appointment_date)
    EditText appointment_date;


    @BindView(R.id.other_layout)
    LinearLayout other_layout;

    @BindView(R.id.appointment_type_spinner)
    Spinner appointment_type_spinner;

    @BindView(R.id.pcr_taken_spinner)
    Spinner pcr_taken_spinner;


    @BindView(R.id.other_et)
    EditText other_et;


    @BindView(R.id.btn_submit_apt)
    Button btn_submit_apt;




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
        root =  inflater.inflate(R.layout.pmtct_hei_apt_fragment, container, false);
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


        ArrayAdapter<String> customAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, appnment);
        customAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointment_type_spinner.setAdapter(customAdapter);


        ArrayAdapter<String> pcrAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, pcr_taken);
        pcrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pcr_taken_spinner.setAdapter(pcrAdapter);


        pcr_taken_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                PCR_TAKEN = pcr_taken[position];

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


        appointment_date.setOnClickListener(new View.OnClickListener() {
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


        btn_submit_apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateHeiApt())
                    bookNormalTca();
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

    private boolean validateHeiApt() {
        boolean valid = true;



        if (TextUtils.isEmpty(hei_no.getText().toString())) {
            hei_no.setError("HEI number is required");
            valid = false;
            return valid;
        }



        if (TextUtils.isEmpty(appointment_date.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select appointment date",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        //if (APT_TYPE.equals("") || APT_TYPE.equals("Please select appointment type")) {
            if (APT_TYPE.equals("") || APT_TYPE.contentEquals("0")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select appointment type",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

           //if (PCR_TAKEN.equals("") || PCR_TAKEN.equals("Has PCR been taken?")) {
            if (PCR_TAKEN.equals("") || PCR_TAKEN.contentEquals("0")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select if PCR was taken",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }


        return valid;
    }


    private void bookNormalTca() {

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
            payload.put("hei_number", hei_no.getText().toString());
            payload.put("phone_no", phone_no);
            payload.put("appointment_date", APPOINTMENT_DATE);
            payload.put("appointment_type", java.util.Arrays.asList(appnment).indexOf(APT_TYPE));
            payload.put("appointment_other", TextUtils.isEmpty(other_et.getText().toString()) ? -1 : other_et.getText().toString());
            payload.put("pcr_taken", PCR_TAKEN);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                z+Config.BOOK_HEI_ONLY_APT1, payload, new Response.Listener<JSONObject>() {

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
                        appointment_type_spinner.setSelection(0);
                        hei_no.getText().clear();
                        appointment_date.getText().clear();
                        APPOINTMENT_DATE = "";
                        other_et.getText().clear();


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
