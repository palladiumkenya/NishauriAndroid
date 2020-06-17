package com.example.mhealth.appointment_diary.utilitymodules;


import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.R;

import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;

import java.util.Calendar;
import java.util.List;

/**
 * Created by DELL on 12/11/2015.
 */

public class Broadcast extends AppCompatActivity
{
    EditText broadcast_name,broadcast_message,mydate;
    String brd_name , brd_message ,current_date, selected_targetgroup, selected_time;
    Spinner targetgroup,time;
    Button submit;
    String encrypted;
    AccessServer acs;
    CheckInternet chkinternet;
    SendMessage sm;


    DatePickerDialog datePickerDialog;

    String[] search_group={"select target group","Male","Female","All clients","All active Appointments","All active todays Appointments","Adults","Adolescents"};
    String[] msg_time={"Please select Broadcast Time","00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","22:00","23:00"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcasting);



         initialise();

        SSLTrust.nuke();

         setToolbar();


        setAdapters();
        dateListener();
        timeListener();
        searchGroupListener();
        submitBrd();










    }

    private void setAdapters(){

        try{

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,search_group);

            final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,msg_time);

            targetgroup.setAdapter(adapter);

            time.setAdapter(adapter3);


        }
        catch(Exception e){


        }
    }

    private void searchGroupListener(){

        try{

            targetgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected_targetgroup =Integer.toString(position) ;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        catch(Exception e){


        }
    }


    private void timeListener(){

        try{


            time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected_time =Integer.toString(position) ;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }
        catch(Exception e){


        }
    }

    private void dateListener(){

        try{

            mydate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(v.getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    mydate.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });

        }
        catch(Exception e){


        }
    }

    private void setToolbar(){
        try{

            // Find the toolbar view inside the activity layout
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            // Sets the Toolbar to act as the ActionBar for this Activity window.
            // Make sure the toolbar exists in the activity and is not null
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Broadcast");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch(Exception e){


        }
    }

    private void initialise(){

        try{

            broadcast_name = (EditText)findViewById(R.id.brdname);
            broadcast_message = (EditText)findViewById(R.id.brdmesg);
            submit = (Button)findViewById(R.id.btnRSubmit);
            chkinternet=new CheckInternet(Broadcast.this);
            acs=new AccessServer(Broadcast.this);
            sm=new SendMessage(Broadcast.this);

            targetgroup = (Spinner)findViewById(R.id.search_group);
            time = (Spinner)findViewById(R.id.time_spinner);

            mydate = (EditText)findViewById(R.id.current_date);

        }
        catch(Exception e){


        }
    }

    private void clearfields(){

        try{

            broadcast_name.setText("");
            broadcast_message.setText("");
            mydate.setText("");
            setAdapters();

        }
        catch(Exception e){


        }
    }


    private void submitBrd(){

        try{

            submit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    brd_name = broadcast_name.getText().toString().trim();
                    brd_message = broadcast_message.getText().toString();
                    current_date = mydate.getText().toString().trim();

                    if(brd_name.trim().isEmpty()){
                        Toast.makeText(Broadcast.this, "Broadcast title is required", Toast.LENGTH_SHORT).show();
                    }
                    else if(selected_targetgroup.trim().isEmpty() || selected_targetgroup.contentEquals("0")){

                        Toast.makeText(Broadcast.this, "target group is required", Toast.LENGTH_SHORT).show();

                    }
                    else if(current_date.trim().isEmpty()){

                        Toast.makeText(Broadcast.this, "Broadcast date is required", Toast.LENGTH_SHORT).show();

                    }
                    else if(selected_time.trim().isEmpty() || selected_time.contentEquals("0")){

                        Toast.makeText(Broadcast.this, "broadcast time is required", Toast.LENGTH_SHORT).show();

                    }
                    else if(brd_message.trim().isEmpty()){

                        Toast.makeText(Broadcast.this, "broadcast message is required", Toast.LENGTH_SHORT).show();

                    }
                    else{


                        String mynumber = Config.mainShortcode;


                        String sendSms = brd_name + "*" + selected_targetgroup + "*" + current_date +"*" + selected_time + "*" + brd_message ;


                        try {
                            encrypted = Base64Encoder.encryptString(sendSms);

                        } catch (Exception e) {

                        }

                        if(chkinternet.isInternetAvailable()){
                            List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");
                            for(int x=0;x<myl.size();x++){

                                String un=myl.get(x).getUname();
                                List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
                                for(int y=0;y<myl2.size();y++){

                                    String phne=myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                                    acs.sendDetailsToDbPost("BRD*"+encrypted,phne);
                                }
                            }



                        }
                        else{

                            sm.sendMessageApi("BRD*" + encrypted,mynumber);


                        }


                        clearfields();
                        Toast.makeText(v.getContext(), "Broadcast successfully sent", Toast.LENGTH_SHORT).show();


                    }



                }
            });

        }
        catch(Exception e){


        }
    }
}