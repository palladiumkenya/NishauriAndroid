package com.example.mhealth.appointment_diary.utilitymodules;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.AppendFunction.AppendFunction;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.R;

//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by DELL on 12/11/2015.
 */


public class Appointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner appointment_type,previous_appointment;
    String appointment_code,previous_code;
    LinearLayout llother;
    CheckInternet chkinternet;
    AccessServer acs;
    SendMessage sm;

    String[] appnment = {"","Re-Fill","Clinical review","Enhanced Adherence counseling","Lab investigation","VL Booking","Other"};
    //Please select appointment type
    String[] previous = {"","Yes","No","Not Applicable"};
   // Was previous appointment kept


    EditText cccE,upnE,adateE,aotherE;

    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment);
        // components from activity_registration.xml


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Book appointment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initialise();
        //SSLTrust.nuke();

        AppointmentdateListener();

        final Context gratitude = this;
        final Button btnRSubmit = (Button) findViewById(R.id.btnRSubmit);
        btnRSubmit.setEnabled(true);


        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                populateAppointment();
                populatePrevious();

                setSpinnerListeners();
            }
        };handler.post(runnable);

       /* populateAppointment();
        populatePrevious();
        setSpinnerListeners();*/





    }



    public void setSpinnerListeners(){

        try{



            appointment_type.setOnItemSelectedListener(this);
            previous_appointment.setOnItemSelectedListener(this);



        }
        catch(Exception e){

        }
    }



    public void initialise(){

        try{

            sm=new SendMessage(Appointment.this);
            acs=new AccessServer(Appointment.this);
            chkinternet=new CheckInternet(Appointment.this);
            cccE=(EditText) findViewById(R.id.ccc);
            upnE=(EditText) findViewById(R.id.appupn);
            adateE=(EditText) findViewById(R.id.adate);
            llother=(LinearLayout) findViewById(R.id.aotherll);
            aotherE=(EditText) findViewById(R.id.aother);

            Intent i = getIntent();
            String mflnum = i.getStringExtra("ccc");
            String upnS=i.getStringExtra("upn");

            cccE.setText(mflnum);
            upnE.setText(upnS);

            appointment_type=(Spinner) findViewById(R.id.appointment_type_spinner);
            previous_appointment=(Spinner) findViewById(R.id.previous_appointment_spinner);

            appointment_code="";
            previous_code="";


        }
        catch(Exception e){

            Toast.makeText(this, "error initialising variables", Toast.LENGTH_SHORT).show();


        }
    }

    public void clearFields(){

        try{

            cccE.setText("");
            adateE.setText("");
            upnE.setText("");
            aotherE.setText("");


            Handler handler = new Handler();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    populateAppointment();
                    populatePrevious();
                }
            };handler.post(runnable);

           /* populateAppointment();
            populatePrevious();*/
        }
        catch(Exception e){


        }
    }



    public void AppointmentdateListener(){

        try{

            adateE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(Appointment.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    adateE.setText(dayOfMonth + "/"
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

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Spinner spin=(Spinner) parent;

        if(spin.getId()==R.id.appointment_type_spinner){


            appointment_code=Integer.toString(position);
            if(appointment_code.contentEquals("6")){
                llother.setVisibility(View.VISIBLE);

            }
            else{
                llother.setVisibility(View.GONE);
                aotherE.setText("");

            }

        }
        if (spin.getId()==R.id.previous_appointment_spinner){

            previous_code=Integer.toString(position);


        }



    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void populateAppointment(){


        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),appnment);

            appointment_type.setAdapter(customAdapter);


        }

        catch(Exception e){


        }
    }

    public void populatePrevious(){


        try{

            SpinnerAdapter customAdapter=new SpinnerAdapter(getApplicationContext(),previous);

            previous_appointment.setAdapter(customAdapter);


        }

        catch(Exception e){


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public void submitClicked(View v){

        try{


            String cccS=cccE.getText().toString();
            String upnS=upnE.getText().toString();
            String a_dateS=adateE.getText().toString();


            if(cccS.trim().isEmpty()){
                cccE.setError("ccc number required");
            }
            else if(upnS.trim().isEmpty()){

                upnE.setError("CCC No. required");
            }
            else if(a_dateS.trim().isEmpty()){
                adateE.setError("Appointment date required");
            }
            else if(appointment_code.contentEquals("0")){
                Toast.makeText(this, "Please specify appointment type", Toast.LENGTH_SHORT).show();
            }
            else if(appointment_code.contentEquals("6") && aotherE.getText().toString().isEmpty()){
                Toast.makeText(this, "Please specify other for appointment", Toast.LENGTH_SHORT).show();
            }

            else if(previous_code.contentEquals("0")){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();


            }
            else{

                    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    String currentArray[]=timeStamp.split("\\.");
                    String currentDate=currentArray[2];
                    String currentMonth=currentArray[1];
                    String currentYear=currentArray[0];

                    int cdate=Integer.parseInt(currentDate);
                    int cmnth=Integer.parseInt(currentMonth);
                    int cyear=Integer.parseInt(currentYear);

                    String selectedArray[]=a_dateS.split("/");

                    String selectedDate=selectedArray[0];
                    String selectedMonth=selectedArray[1];
                    String selectedYear=selectedArray[2];

                    int sdate=Integer.parseInt(selectedDate);
                    int smonth=Integer.parseInt(selectedMonth);
                    int syear=Integer.parseInt(selectedYear);

                    if(syear<cyear){
                        adateE.setError("Appointment date should be greater than today");
                        Toast.makeText(this, "Appointment date should be greater than today", Toast.LENGTH_SHORT).show();


                    }
                    else if(syear==cyear && smonth<cmnth){

                        adateE.setError("Appointment date should be greater than today");
                        Toast.makeText(this, "Appointment date should be greater than today", Toast.LENGTH_SHORT).show();


                    }
                    else if(syear==cyear && smonth==cmnth && sdate<=cdate){

                        adateE.setError("Appointment date should be greater than today");
                        Toast.makeText(this, "Appointment date should be greater than today", Toast.LENGTH_SHORT).show();


                    }
                    else{


                        String newupn= AppendFunction.AppendUniqueIdentifier(upnS);
                        String ClientCcc=cccS+newupn;
                        String sendSms="";

                        if(appointment_code.contentEquals("6")){


                            String myother=aotherE.getText().toString();
                            sendSms=ClientCcc+"*"+a_dateS+"*"+appointment_code+"*"+myother+"*"+previous_code+"*-1";

                        }
                        else{
                            String myother="-1";
                            sendSms=ClientCcc+"*"+a_dateS+"*"+appointment_code+"*"+myother+"*"+previous_code+"*-1";

                        }

                        String encrypted = Base64Encoder.encryptString(sendSms);

                        if(chkinternet.isInternetAvailable()){
                            List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");
                            for(int x=0;x<myl.size();x++){

                                String un=myl.get(x).getUname();
                                List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
                                for(int y=0;y<myl2.size();y++){

                                    String phne=myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                                    acs.sendDetailsToDbPost("APP*"+encrypted,phne);
                                }
                            }



                        }
                        else{





//                        SmsManager sm=SmsManager.getDefault();
//
//                        ArrayList<String> parts = sm.divideMessage("APP*" +encrypted);
//
                        String mynumber = Config.mainShortcode;
//
//                        sm.sendMultipartTextMessage(mynumber,null,parts,null,null);

                            sm.sendMessageApi("APP*" +encrypted,mynumber);

                        }
                        clearFields();


                        Toast.makeText(this, "Successfully submitted ", Toast.LENGTH_SHORT).show();
                    }







//                    Toast.makeText(this, "submitting", Toast.LENGTH_SHORT).show();




            }



        }
        catch(Exception e){
            Toast.makeText(this, "error in submission "+e, Toast.LENGTH_SHORT).show();


        }
    }




    public void AppointmentDialog(String message){

        try{

            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Success");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message);
            adb.setCancelable(false);

            adb.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();

                }
            });
            AlertDialog mydialog=adb.create();
            mydialog.show();
        }
        catch(Exception e){


        }

    }



    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public String hexToString(String hex) {
        return ""+(Integer.decode(hex));
    }



    public static String stringToHex(String base)
    {
        StringBuffer buffer = new StringBuffer();
        int intValue;
        for(int x = 0; x < base.length(); x++)
        {
            int cursor = 0;
            intValue = base.charAt(x);
            String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
            for(int i = 0; i < binaryChar.length(); i++)
            {
                if(binaryChar.charAt(i) == '1')
                {
                    cursor += 1;
                }
            }
            if((cursor % 2) > 0)
            {
                intValue += 128;
            }
            buffer.append(Integer.toHexString(intValue) + " ");
        }
        return buffer.toString();
    }


    private static String hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<bytes.length; i++) {
            sb.append(String.format("%02X ",bytes[i]));
        }
        return sb.toString();
    }

    public byte[] getBytes(String mys){


        return mys.getBytes();



    }
}
