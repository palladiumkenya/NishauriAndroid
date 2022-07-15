package com.example.mhealth.appointment_diary.utilitymodules;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.AppendFunction.AppendFunction;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.Datecalculator.DateCalculator;
import com.example.mhealth.appointment_diary.R;
//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Clientartdate;
import com.example.mhealth.appointment_diary.tables.Registrationtable;

import java.util.Calendar;
import java.util.List;


/**
 * Created by DELL on 12/11/2015.
 */
public class Consent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    EditText cccE, upnE, adateE, ctimeE, cphoneE;
    AccessServer acs;
    CheckInternet chkinternet;
    SendMessage sm;

    DatePickerDialog datePickerDialog;
    DateCalculator dcalc;
    Spinner languageS, weeklyS;
    String language_code, weekly_code;

    String[] languages = {"", "Swahili", "English"};
    //Please Select Language*
    String[] weeklys = {"", "Yes", "No"};
    //Enable weekly motivational alerts*


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Consent client");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initialise();

        //SSLTrust.nuke();

        populateLanguage();
        populateWeekly();

        setSpinnerListeners();
        ConsentdateListener();
        ConsentTimeListener();

        final Context gratitude = this;
        final Button btnRSubmit = (Button) findViewById(R.id.btnRSubmit);
        btnRSubmit.setEnabled(true);


    }


    public void setSpinnerListeners() {

        try {

            languageS.setOnItemSelectedListener(this);
            weeklyS.setOnItemSelectedListener(this);


        } catch (Exception e) {

        }
    }


    public void populateWeekly() {


        try {

            SpinnerAdapter customAdapterW = new SpinnerAdapter(getApplicationContext(), weeklys);

            weeklyS.setAdapter(customAdapterW);


        } catch (Exception e) {


        }
    }

    public void populateLanguage() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), languages);


            languageS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }


    public void initialise() {

        try {

            sm = new SendMessage(Consent.this);
            acs = new AccessServer(Consent.this);
            chkinternet = new CheckInternet(Consent.this);

            languageS = (Spinner) findViewById(R.id.conlanguage_spinner);
            weeklyS = (Spinner) findViewById(R.id.conweekly_spinner);
            cccE = (EditText) findViewById(R.id.consentccc);
            upnE = (EditText) findViewById(R.id.consentupn);
            adateE = (EditText) findViewById(R.id.consentadate);
            ctimeE = (EditText) findViewById(R.id.ctime);
            cphoneE = (EditText) findViewById(R.id.cphone);
            dcalc = new DateCalculator(Consent.this);

            Intent i = getIntent();
            String s1 = i.getStringExtra("ccc");

            cccE.setText(s1);
            language_code = "";
            weekly_code = "";


        } catch (Exception e) {

            Toast.makeText(this, "error initialising variables", Toast.LENGTH_SHORT).show();


        }
    }

    public void clearFields() {

        try {

            cccE.setText("");
            upnE.setText("");
            adateE.setText("");
            ctimeE.setText("");
            cphoneE.setText("");
            populateLanguage();
            populateWeekly();
        } catch (Exception e) {


        }
    }


    public void ConsentTimeListener() {

        try {

            ctimeE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();


                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);


                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(Consent.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                            ctimeE.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);
                    mTimePicker.setTitle("Select Preferred Time");
                    mTimePicker.show();

                }
            });
        } catch (Exception e) {


        }
    }


    public void ConsentdateListener() {

        try {

            adateE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(Consent.this,
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
        } catch (Exception e) {


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


    public void consentClient(View v) {

        try {


            String cccS = cccE.getText().toString();
            String upnS = upnE.getText().toString();
            String a_dateS = adateE.getText().toString();
            String cphoneS = cphoneE.getText().toString();
            String timeS = ctimeE.getText().toString();


            if (cccS.trim().isEmpty()) {
                cccE.setError("ccc number required");
            } else if (upnS.trim().isEmpty()) {

                upnE.setError("CCC No required");
            } else if (a_dateS.trim().isEmpty()) {
                adateE.setError("Consent date required");
            } else if (language_code.contentEquals("0")) {
                Toast.makeText(this, "please specify language", Toast.LENGTH_SHORT).show();
            } else if (weekly_code.contentEquals("0")) {
                Toast.makeText(this, "please specify weekly alerts", Toast.LENGTH_SHORT).show();
            } else if (cphoneS.trim().isEmpty()) {
                cphoneE.setError("Preffered phone is required");
            } else if (timeS.trim().isEmpty()) {
                ctimeE.setError("time is required");
            } else {

                List<Clientartdate> myl = Clientartdate.findWithQuery(Clientartdate.class, "select * from Clientartdate where mfl=? and upn=? limit 1", cccS, upnS);
                if (myl.size() > 0) {
                    for (int x = 0; x < myl.size(); x++) {

                        String artDAte = myl.get(x).getArtdate();
                        if (dcalc.checkDateDifferenceWithTwoDates(artDAte, a_dateS)) {

//                             Toast.makeText(this, "we are good", Toast.LENGTH_SHORT).show();

                            String newupn = AppendFunction.AppendUniqueIdentifier(upnS);
                            String clientCode = cccS + newupn;
                            String sendSms = clientCode + "*" + a_dateS + "*" + timeS + "*" + cphoneS + "*" + language_code + "*" + weekly_code;

                            String encrypted = Base64Encoder.encryptString(sendSms);

                            if (chkinternet.isInternetAvailable()) {
                                List<Activelogin> myl1 = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
                                for (int x1 = 0; x1 < myl1.size(); x1++) {

                                    String un = myl1.get(x1).getUname();
                                    List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
                                    for (int y = 0; y < myl2.size(); y++) {

                                        String phne = myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                                        acs.sendDetailsToDbPost("CON*" + encrypted, phne);
                                    }
                                }


                            } else {


                                String mynumber = Config.mainShortcode;

                                sm.sendMessageApi("CON*" + encrypted, mynumber);


                            }


                            clearFields();


                            Toast.makeText(this, "Successfully submitted ", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(this, "Client consent date shouldn’t be earlier than ART start date", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {


                    String newupn = AppendFunction.AppendUniqueIdentifier(upnS);
                    String clientCode = cccS + newupn;
                    String sendSms = clientCode + "*" + a_dateS + "*" + timeS + "*" + cphoneS;
                    String encrypted = Base64Encoder.encryptString(sendSms);

                    if (chkinternet.isInternetAvailable()) {
                        List<Activelogin> myl1 = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
                        for (int x1 = 0; x1 < myl1.size(); x1++) {

                            String un = myl1.get(x1).getUname();
                            List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
                            for (int y = 0; y < myl2.size(); y++) {

                                String phne = myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                                acs.sendDetailsToDbPost("CON*" + encrypted, phne);
                            }
                        }


                    } else {

                        String mynumber = Config.mainShortcode;

                        sm.sendMessageApi("CON*" + encrypted, mynumber);

                        clearFields();


                    }


                    Toast.makeText(this, "Successfully submitted ", Toast.LENGTH_SHORT).show();

                }


            }

        } catch (Exception e) {
            Toast.makeText(this, "error in submission " + e, Toast.LENGTH_SHORT).show();

        }
    }


//
//    public void AppointmentDialog(String message){
//
//        try{
//
//            AlertDialog.Builder adb=new AlertDialog.Builder(this);
//            adb.setTitle("Success");
//            adb.setIcon(R.mipmap.success);
//            adb.setMessage(message);
//            adb.setCancelable(false);
//
//            adb.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    // Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();
//
//                }
//            });
//            AlertDialog mydialog=adb.create();
//            mydialog.show();
//        }
//        catch(Exception e){
//
//
//        }
//
//    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spin = (Spinner) adapterView;

        if (spin.getId() == R.id.conlanguage_spinner) {


            language_code = Integer.toString(i);

        }
        if (spin.getId() == R.id.conweekly_spinner) {


            weekly_code = Integer.toString(i);

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
