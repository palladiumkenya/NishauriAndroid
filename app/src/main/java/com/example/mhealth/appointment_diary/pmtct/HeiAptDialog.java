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
import com.example.mhealth.appointment_diary.models.Hei;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
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


public class HeiAptDialog extends BottomSheetDialogFragment {


    private Hei hei;
    private Context context;
    private Unbinder unbinder;
    private String clinicNumber;
    private String phone_no;
    private String PCR_TAKEN = "";

    public  String z;

    RequestQueue queue;


    String[] pcr_taken = {"Has PCR been taken?","YES","NO"};
    String[] appnment = {"Please select appointment type","Re-Fill","Clinical review","Enhanced Adherence counseling","Lab investigation","VL Booking","Other", "PCR"};


    private String APPOINTMENT_DATE = "";
    private String APT_TYPE = "";



    @BindView(R.id.other_et)
    EditText other_et;

    @BindView(R.id.appointment_date)
    EditText appointment_date;

    @BindView(R.id.title)
    TextView titleTextView;


    @BindView(R.id.appointment_type_spinner)
    Spinner appointment_type_spinner;

    @BindView(R.id.btn_submit_apt)
    Button btn_submit_apt;

    @BindView(R.id.other_layout)
    LinearLayout other_layout;

    @BindView(R.id.pcr_taken_spinner)
    Spinner pcr_taken_spinner;

    public HeiAptDialog() {
        // Required empty public constructor
    }



    public static HeiAptDialog newInstance(String clinicNumber, Hei hei, Context context) {
        HeiAptDialog fragment = new HeiAptDialog();
        fragment.hei = hei;
        fragment.context = context;
        fragment.clinicNumber = clinicNumber;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.hei_apt_bottom_sheet, container, false);
        unbinder = ButterKnife.bind(this, view);

        List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");

        for(int x=0;x<myl.size();x++){
            String un=myl.get(x).getUname();
            List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
            for(int y=0;y<myl2.size();y++){
                phone_no=myl2.get(y).getPhone();
            }
        }

        queue = Volley.newRequestQueue(context); // this = context


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

        titleTextView.setText("Book appointment for "+clinicNumber);
//        titleTextView.setText("Book appointment for "+hei.getHei_first_name()+" "+hei.getHei_last_name());

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

        ArrayAdapter<String> customAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, appnment);
        customAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointment_type_spinner.setAdapter(customAdapter);

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

        btn_submit_apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateHei())
                    bookHeiApt();
            }
        });


        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean validateHei() {
        boolean valid = true;

        if (PCR_TAKEN.equals("") || PCR_TAKEN.equals("Has PCR been taken?")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select if PCR has been taken",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (TextUtils.isEmpty(appointment_date.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select appointment date",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        if (APT_TYPE.equals("") || APT_TYPE.equals("Please select appointment type")) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please select appointment type",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }


        if (APT_TYPE.equals("Other") && TextUtils.isEmpty(other_et.getText().toString())) {
            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Validation error","Please specify other",context);
            bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
            valid = false;
            return valid;
        }

        return valid;
    }

    private void bookHeiApt() {
        try {
            UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
              z=  _url.base_url1;
        }catch (Exception e){
            //e.printStackTrace();
        }

        JSONObject payload = new JSONObject();
        try {
            payload.put("clinic_number", clinicNumber);
            payload.put("phone_no", phone_no);
            payload.put("appointment_date", APPOINTMENT_DATE);
            payload.put("appointment_type", java.util.Arrays.asList(appnment).indexOf(APT_TYPE));
            payload.put("appointment_other", TextUtils.isEmpty(other_et.getText().toString()) ? -1 : other_et.getText().toString());
            payload.put("hei_number", hei.getHei_no());
            payload.put("pcr_taken", PCR_TAKEN);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                z+Config.BOOK_HEI_APT1, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");
                    String message = response.has("message") ? response.getString("message") : "";

                    if (success) {
                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Success",message,context);
                        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());

                        //reset views
                        appointment_type_spinner.setSelection(0);

                        appointment_date.getText().clear();
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
