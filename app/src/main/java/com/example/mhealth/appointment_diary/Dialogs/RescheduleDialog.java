package com.example.mhealth.appointment_diary.Dialogs;

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
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.RescheduleActivity;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.config.VolleyErrors;
import com.example.mhealth.appointment_diary.models.Appointment;
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


public class RescheduleDialog extends BottomSheetDialogFragment {

    private Context context;
    private Unbinder unbinder;
    private Appointment appointment;
    private String phone_no;

    RequestQueue queue;


    private String APPOINTMENT_DATE = "";


    @BindView(R.id.appointment_date)
    EditText appointment_date;

    @BindView(R.id.title)
    TextView titleTextView;

    @BindView(R.id.btn_submit_apt)
    Button btn_submit_apt;


    public RescheduleDialog() {
        // Required empty public constructor
    }



    public static RescheduleDialog newInstance(Appointment apt, Context context) {
        RescheduleDialog fragment = new RescheduleDialog();
        fragment.context = context;
        fragment.appointment = apt;
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
        View view = inflater.inflate(R.layout.reschedule_bottom_sheet, container, false);
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




        titleTextView.setText("Reschedule appointment for "+appointment.getF_name()+" "+appointment.getM_name()+" "+appointment.getL_name());
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

        btn_submit_apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!APPOINTMENT_DATE.equals(""))
                    reschedule();
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


    private void reschedule() {
        UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
        String  z=  _url.base_url1;

        JSONObject payload = new JSONObject();
        try {
            payload.put("appointment_id", appointment.getAppointment_id());
            payload.put("user_phone", phone_no);
            payload.put("appointment_date", APPOINTMENT_DATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                z+Config.RESCHEDULE_APT1+appointment.getAppointment_id(), payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");
                    String message = response.has("message") ? response.getString("message") : "";

                    if (success) {

                        //reset views
                        appointment_date.getText().clear();
                        APPOINTMENT_DATE = "";

                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Success",message,context);
                        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());




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
