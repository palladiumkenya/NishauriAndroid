package com.example.mhealth.appointment_diary.pmtct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.Dialogs.ErrorMessage;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.appointment_diary.AppCal;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.config.VolleyErrors;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ANCVisit extends AppCompatActivity {
    /*String[] clientIs = {"","Pregnant", "Pregnant and Breastfeeding"};
    String[] hivResults = {"", "TDF+3TC+EFV", "TDF+3TC+DTG", "TDF+3TC+DTG", "AZT+3TC+NVP", " AZT+3TC+EFV", "ABC+3TC+NVP", "ABC+3TC+EFV", " ABC+3TC+DTG", "ABC+3TC+LPV/r", " AZT+3TC+LPV/r+ RTV", "ART5TDF+3TC +ATV/r","ABC+3TC+DTG", "ABC+3TC+DTG", "ABC+3TC+ATV/r", "AZT+3TC+ATV/r", "AZT+3TC+DRV/r"};
    Spinner clientIsS, hivResultsS;
    private String CLIENT_IS = "";
    private String HIV_RESULTS = "";*/

    Button startvisit,searchbtn;
    LinearLayout details;
    String z, phone;
    EditText ccno,clinicno,fname,Mname,lname,dobi,reg, upino;
    Dialogs dialogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ancvisit);
        dialogs=new Dialogs(ANCVisit.this);

        /*clientIsS = (Spinner) findViewById(R.id.ClientSpinner);
        hivResultsS =(Spinner) findViewById(R.id.hivResults);*/
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
                    Toast.makeText(ANCVisit.this, "Enter CCC Number", Toast.LENGTH_LONG).show();
                }else{
                searchANC();}
               // details.setVisibility(View.VISIBLE);

            }
        });

        startvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sendCC =clinicno.getText().toString();
                Intent intent = new Intent(ANCVisit.this, ANCVisitStarted.class);
                intent.putExtra("Client_CCC", sendCC);

                startActivity(intent);

            }
        });


        try{
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
           // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("ANC Visit");

        }
        catch(Exception e){

        }

    }
    private void  searchANC(){

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
                //Toast.makeText(ANCVisit.this, "SUCCESS", Toast.LENGTH_SHORT).show();
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


                try{

                    byte[] htmlBodyBytes = error.networkResponse.data;

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                    dialogs.showErrorDialog(new String(htmlBodyBytes),"Server Response");



                }
                catch(Exception e){



//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                    dialogs.showErrorDialog("error occured, try again"+error.getMessage(),"Server Response");




                }

                details.setVisibility(View.GONE);
                //Toast.makeText(ANCVisit.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ANCVisit.this);
        requestQueue.add(jsonArrayRequest);
    }


}