package com.example.mhealth.appointment_diary;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.Dialogs.ErrorMessage;
import com.example.mhealth.appointment_diary.Dialogs.RescheduleDialog;
import com.example.mhealth.appointment_diary.adapter.AppointmentsAdapter;
import com.example.mhealth.appointment_diary.adapter.AttachedHeisAdapter;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.config.VolleyErrors;
import com.example.mhealth.appointment_diary.models.Appointment;
import com.example.mhealth.appointment_diary.models.Hei;
import com.example.mhealth.appointment_diary.pmtct.HeiAptDialog;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RescheduleActivity extends AppCompatActivity {


    private String phone_no;
    public String z;

    private Button btn_check;
    private EditText ccc_no;
    private TextView select_appointment;
    private RecyclerView recyclerView;

    private AppointmentsAdapter mAdapter;
    private ArrayList<Appointment> appointmentArrayList;

    RequestQueue queue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reschedule appointments");


        List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");

        for(int x=0;x<myl.size();x++){
            String un=myl.get(x).getUname();
            List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
            for(int y=0;y<myl2.size();y++){
                phone_no=myl2.get(y).getPhone();
            }
        }

        queue = Volley.newRequestQueue(this); // this = context


        appointmentArrayList = new ArrayList<>();
        mAdapter = new AppointmentsAdapter(RescheduleActivity.this, appointmentArrayList);


        select_appointment = findViewById(R.id.select_appointment);
        recyclerView = findViewById(R.id.recyclerView);
        ccc_no = findViewById(R.id.ccc_no);
        btn_check = findViewById(R.id.btn_check);

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ccc_no.getText().toString())){
                    Toast.makeText(RescheduleActivity.this,"Please enter CCC/HEI number", Toast.LENGTH_LONG).show();
                }else {
                    searchApt();
                }
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(RescheduleActivity.this,LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new AppointmentsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Appointment apt = appointmentArrayList.get(position);

                RescheduleDialog bottomSheetFragment = RescheduleDialog.newInstance(apt, RescheduleActivity.this);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

            }
        });



    }

    private void searchApt() {
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
        /*UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
        String  z=  _url.base_url1;*/
        JSONObject payload = new JSONObject();
        try {
            payload.put("clinic_number", ccc_no.getText().toString());
            payload.put("phone_no", phone_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("payload: ", payload.toString());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                z+Config.SEARCH_RESCHEDULE_APT1, payload, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response: ", response.toString());
                try {
                    boolean success = response.has("success") && response.getBoolean("success");

                    if (success) {
                        recyclerView.setVisibility(View.VISIBLE);
                        appointmentArrayList.clear();

                        JSONArray heisArray = response.has("arr_data") ? response.getJSONArray("arr_data") : null;

                        assert heisArray != null;
                        if (heisArray.length() > 0){


                            for (int i = 0; i < heisArray.length(); i++) {

                                JSONObject item = (JSONObject) heisArray.get(i);


                                int appointment_id = item.has("appointment_id") ? item.getInt("appointment_id") : 0;
                                String f_name = item.has("f_name") ? item.getString("f_name") : "";
                                String m_name = item.has("m_name") ? item.getString("m_name") : "";
                                String l_name = item.has("l_name") ? item.getString("l_name") : "";
                                String clinic_number = item.has("clinic_number") ? item.getString("clinic_number") : "";
                                String hei_no = item.has("hei_no") ? item.getString("hei_no") : "";
                                String clinic = item.has("clinic") ? item.getString("clinic") : "";
                                String appointment_date = item.has("appointment_date") ? item.getString("appointment_date") : "";
                                String appointment_type = item.has("appointment_type") ? item.getString("appointment_type") : "";



                                Appointment hei =new Appointment(appointment_id,f_name,m_name,l_name,clinic_number,hei_no,clinic,appointment_date,appointment_type);
                                appointmentArrayList.add(hei);
                                mAdapter.notifyDataSetChanged();

                            }

                        }else {

                            select_appointment.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.GONE);


                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("No appointments found","There are no appointments with the given CCC/HEI number",RescheduleActivity.this);
                            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
                        }

                    } else {

                        select_appointment.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);


                        String message = response.has("message") ? response.getString("message") : "";

                        ErrorMessage bottomSheetFragment = ErrorMessage.newInstance("Error",message, RescheduleActivity.this);
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

                            ErrorMessage bottomSheetFragment = ErrorMessage.newInstance(message,reason,RescheduleActivity.this);
                            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }else {

                    Log.e("VOlley error :", error.getLocalizedMessage()+" message:"+error.getMessage());
                    Toast.makeText(RescheduleActivity.this, VolleyErrors.getVolleyErrorMessages(error, RescheduleActivity.this),Toast.LENGTH_LONG).show();
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
