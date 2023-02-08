package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LaborAndDelivery extends AppCompatActivity {

    Button startvisit,searchbtn;
    LinearLayout details;
    String z, phone;
    EditText ccno,clinicno,fname,Mname,lname,dobi,reg, upino;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labor_and_delivery);

        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Labor And Delivery");

        }
        catch(Exception e){
        }

        startvisit=(Button) findViewById(R.id.btn_startVisit);
        searchbtn =(Button) findViewById(R.id.btn_search);
        details =(LinearLayout) findViewById(R.id.hei_details_layout);

        ccno=(EditText) findViewById(R.id.cc);
        clinicno=(EditText) findViewById(R.id.clinicno);
        fname=(EditText) findViewById(R.id.fname);
        Mname=(EditText) findViewById(R.id.Mname);
        lname=(EditText) findViewById(R.id.lname);
        dobi=(EditText) findViewById(R.id.dobb);
        reg=(EditText) findViewById(R.id.reg);
        upino=(EditText) findViewById(R.id.upino);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ccno.getText().toString().isEmpty()){
                    Toast.makeText(LaborAndDelivery.this, "Enter CCC Number", Toast.LENGTH_LONG).show();
                }else{
                    searchPNC();}
                // details.setVisibility(View.VISIBLE);

            }
        });

        startvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendCC =clinicno.getText().toString();
                Intent intent = new Intent(LaborAndDelivery.this, LaborAndDeliveryStart.class);
                intent.putExtra("Client_CCC", sendCC);
                startActivity(intent);

            }
        });



    }
    private void  searchPNC(){

        List<Activelogin> al=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin limit 1");
        for(int x=0;x<al.size();x++) {
            String myuname = al.get(x).getUname();
            List<Registrationtable> myl = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", myuname);
            for (int y = 0; y < myl.size(); y++) {

                phone = myl.get(y).getPhone();

            }
        }

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }
        String urls1 ="?ccc="+ccno.getText().toString();
        String tt1 ="&phone_number="+phone;
        //z+ Config.CALENDER_LIST+urls+tt,
        //  String url1 ="https://ushauriapi.kenyahmis.org/pmtct/search?ccc=1305701529&phone_number=0780888928";
        //https://ushauriapi.kenyahmis.org/pmtct/search?ccc=1305701529&phone_number=0780888928
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, z+ Config.SEARCHANCPNC+urls1+tt1, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Toast.makeText(LaborAndDelivery.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                details.setVisibility(View.VISIBLE);
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject =response.getJSONObject(i);

                        String clinicnumber =jsonObject.getString("clinic_number");
                        String f_name =jsonObject.getString("f_name");
                        String m_name =jsonObject.getString("m_name");
                        String l_name =jsonObject.getString("l_name");
                        String currentregimen =jsonObject.getString("currentregimen");
                        String dob =jsonObject.getString("dob");
                        String upi_no =jsonObject.getString("upi_no");


                     /*String upi_no =jsonObject.getString("upi_no");
                     String f_name =jsonObject.getString("f_name");
                     String m_name =jsonObject.getString("m_name");
                     String l_name =jsonObject.getString("l_name");
                     String dob =jsonObject.getString("dob");
                     String currentregimen =jsonObject.getString("currentregimen");*/


                        clinicno.setText(clinicnumber);
                        fname.setText(f_name);
                        Mname.setText(m_name);
                        lname.setText(l_name);
                        reg.setText(currentregimen);
                        dobi.setText(dob);
                        upino.setText(upi_no);
                     /*clinicno.setText(upi_no);
                     fname.setText(f_name);
                     Mname.setText(m_name);
                     lname.setText(l_name);
                     dobi.setText(dob);
                     reg.setText(currentregimen);*/


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                //details.setVisibility(View.VISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                details.setVisibility(View.GONE);
                Toast.makeText(LaborAndDelivery.this, error.getMessage(), Toast.LENGTH_SHORT).show();


                ////

                // try {
                // String  body = new String(error.networkResponse.data, "UTF-8");
                    /*String  body = new String(error.networkResponse.data, "UTF-8");

                    JSONObject json = new JSONObject(body);*/
                //                            Log.e("error response : ", json.toString());

                     /*JSONObject json1 = new JSONObject(error.getMessage());
                    String message = json1.has("message") ? json1.getString("message") : "";
                    String reason = json1.has("reason") ? json1.getString("reason") : "";

                    Toast.makeText(PNCVisit.this, message, Toast.LENGTH_SHORT).show();*/


                // }
              /*catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }

            ////
               /* NetworkResponse response = error.networkResponse;

                if(response != null && response.data != null) {
                    String body;
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if (error.networkResponse.data != null) {
                        try {
                            body = new String(error.networkResponse.data, "UTF-8");

                            JSONObject json = new JSONObject(body);
                            //                            Log.e("error response : ", json.toString());


                            String message = json.has("message") ? json.getString("message") : "";
                            String reason = json.has("reason") ? json.getString("reason") : "";

                            Toast.makeText(PNCVisit.this, error.getMessage(), Toast.LENGTH_SHORT).show();


                        } catch (UnsupportedEncodingException | JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }

                }*/
        });
        RequestQueue requestQueue = Volley.newRequestQueue(LaborAndDelivery.this);
        requestQueue.add(jsonArrayRequest);
    }


    }
