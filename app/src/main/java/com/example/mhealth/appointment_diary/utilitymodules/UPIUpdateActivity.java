package com.example.mhealth.appointment_diary.utilitymodules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mhealth.appointment_diary.R;

import org.json.JSONException;
import org.json.JSONObject;

public class UPIUpdateActivity extends AppCompatActivity {

    EditText upitext, cfile, f_name, s_name, o_name;
    String newUpi;
    Button populate1;

    String  fname;
    String file;
    String mname;
    String lname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upiupdate);

        upitext =(EditText) findViewById(R.id.cccupi);
        cfile =(EditText) findViewById(R.id.cfile);
        f_name =(EditText) findViewById(R.id.f_name);
        s_name =(EditText) findViewById(R.id.s_name);
        o_name =(EditText) findViewById(R.id.o_name);

        populate1 =(Button) findViewById(R.id.populate);






        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newUpi= null;
            } else {
                newUpi= extras.getString("UPI");

            }
        } else {
            newUpi= (String) savedInstanceState.getSerializable("Client_CCC");

        }

        Toast.makeText(UPIUpdateActivity.this, ""+newUpi, Toast.LENGTH_SHORT).show();
        upitext.setText(newUpi);
        populate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdetails1();

            }
        });

        try{
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Update Client's Details");

        }
        catch(Exception e){

        }


    }

    public void getdetails1(){
        //?client_id=MOH1668675613
      //  AndroidNetworking.get("https://ushauriapi.kenyahmis.org/mohupi/search?client_id=MOH1668675613")
        String urls ="?client_id="+newUpi;
        AndroidNetworking.get("https://ushauriapi.kenyahmis.org/mohupi/search"+urls)

                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(UPIUpdateActivity.this, "sucess", Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject =response.getJSONObject("message");

                              fname =jsonObject.getString("f_name");
                             file =jsonObject.getString("file_no");
                             mname =jsonObject.getString("m_name");
                            //Toast.makeText(UPIUpdateActivity.this, "sucess"+mname, Toast.LENGTH_SHORT).show();
                             lname =jsonObject.getString("l_name");

                            /*cfile.setText(file);
                            f_name.setText(fname);
                            s_name.setText(mname);
                            o_name.setText(lname);*/





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        f_name.setText(fname);
                        s_name.setText(mname);
                        cfile.setText(file);
                        o_name.setText(lname);

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}