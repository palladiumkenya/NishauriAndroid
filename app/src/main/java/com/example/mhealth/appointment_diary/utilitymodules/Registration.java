package com.example.mhealth.appointment_diary.utilitymodules;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.mhealth.appointment_diary.Mydates.MyDates;
import com.example.mhealth.appointment_diary.R;
//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.models.RegisterCounter;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Clientartdate;
import com.example.mhealth.appointment_diary.tables.Mflcode;
import com.example.mhealth.appointment_diary.tables.Myaffiliation;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 12/11/2015.
 */
public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    LinearLayout smslayoutL, idnoL, orphanL, altphoneL, disableL,groupingL;

    EditText cccE, upnE, fileserialE, f_nameE, s_nameE, o_nameE, dobE, enrollment_dateE, art_dateE, phoneE, buddyphoneE, idnoE, altphoneE,ageinyearsE,locatorcountyE,locatorsubcountyE,locatorlocationE,locatorwardE,locatorvillageE;

    Spinner genderS, maritalS, conditionS, enrollmentS, languageS, smsS, wklymotivation, messageTime, SelectstatusS, patientStatus, GroupingS, orphanS, schoolS, newGroupingS;

    String gender_code, marital_code, condition_code,grouping_code,new_grouping_code, category_code, language_code, sms_code, Selectstatus_code, wklyMotivation_code, messageTime_code, patientStatus_code, school_code, orphan_code, idnoS,locatorcountyS,locatorsubcountyS,locatorlocationS,locatorwardS,locatorvillageS;

    DatePickerDialog datePickerDialog;
    CheckInternet chkinternet;
    AccessServer acs;
    SendMessage sm;

    String[] genders = {"Please Select Gender", "Female", "Male"};

    String[] newgroupings = {"Please Select Grouping", "Adolescent","PMTCT","TB","Adults","Peads","TB-HIV","HEI"};
    String[] gendersUcsf = {"Please Select Sex", "Female", "Male"};
    String[] maritals = {"Please Select Marital Status", "Single", "Married Monogomaus", "Married Polygamous", "Divorced", "Widowed", "Cohabiting", "Not Applicable"};
    String[] maritalsInfants = {"Please Select Marital Status", "Single","Not Applicable"};
    String[] conditions = {"Please Select Condition", "ART", "Pre-Art"};
    String[] groups = {"Please Select Grouping", "peads", "adolescents", "PMTCT", "ART", "High VL (Suppressed & non suppressant)"};
    String[] languages = {"Please Select Language", "Swahili", "English"};
    String[] smss = {"Enable Sms", "Yes", "No"};
    String[] orphanOp = {"Are you an Orphan", "Yes", "No"};
    String[] schoolOp = {"Are you in school", "Yes", "No"};
    String[] weeklymotivation = {"Enable weekly motivation alerts", "Yes", "No"};
    String[] msgtime = {"Please select preffered messaging time", "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "22:00", "23:00"};
    String[] pntstatus = {"Please select transaction type", "New", "Update", "Transfer Client In (This module allows transfer in of a client from a facility without ushauri system)"};
    String[] statuss = {"Please Select Status", "Active", "Disabled", "Deceased","Transfer Out"};
    String[] statussnew = {"Please Select Status", "Active"};


    String counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        // components from activity_registration.xml

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Client Registration");


        initialise();
        populateSerialNumber();
        //SSLTrust.nuke();
        displayAlternativeNumber();

//        sendEncrypted();

//        Toast.makeText(this, ""+sha256("Reg*12345*ken*sit*nav*15/6/1991*1*3*1*13/6/2017*0713559850*1*2*2"), Toast.LENGTH_LONG).show();

        DobdateListener();
        ArtdateListener();
        EnrollmentdateListener();

//        populateCategory();
        populateCondition();
        populateGender();
        populateLanguage();

        populateNewGrouping();
        populateSms();
//        populateStatus();
        populateweekly();
        populatemsg();
        populatepntstatus();
        populategrouping();

        setSpinnerListeners();
        setPrompts();


        final Context gratitude = this;
        final Button btnRSubmit = (Button) findViewById(R.id.btnRSubmit);
        btnRSubmit.setEnabled(true);

        Stetho.initializeWithDefaults(this);


    }

    private void populateMflCode(){

        try{

            List<Mflcode> myl=Mflcode.findWithQuery(Mflcode.class,"select * from Mflcode limit 1");
            String mflcode="";

            for(int x=0;x<myl.size();x++){

                mflcode=myl.get(x).getMfl();

            }
            cccE.setText(mflcode);

        }
        catch(Exception e){

            Toast.makeText(this, "error occured populating mflcode", Toast.LENGTH_SHORT).show();
        }
    }

    private void populateSerialNumber(){

        try{

            upnE.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    fileserialE.setText(upnE.getText().toString());

                }
            });
        }
        catch(Exception e){

            Toast.makeText(this, "error setting serial number", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayAlternativeNumber() {
        try {

            if (isUcsf()) {

                altphoneL.setVisibility(View.VISIBLE);
            } else {

                altphoneL.setVisibility(View.GONE);
            }
        } catch (Exception e) {


        }
    }


    public boolean isUcsf() {

        boolean result = false;
        String myaff = "";
        List<Myaffiliation> mya = Myaffiliation.findWithQuery(Myaffiliation.class, "select * from Myaffiliation limit 1");
        for (int x = 0; x < mya.size(); x++) {
            myaff = mya.get(x).getAff();
        }
        try {
            if (myaff.contains("UCSF")) {
                result = true;
            } else {
                result = false;
            }
            return result;
        } catch (Exception e) {
            result = false;
            return result;
        }
    }

    public void setPrompts() {

        try {
            SelectstatusS.setPrompt("select item");
        } catch (Exception e) {


        }
    }

//    public void sendEncrypted(){
//
//
//        SmsManager sm = SmsManager.getDefault();
//        sm.sendTextMessage("40146", null, sha256("Reg*12345*ken*sit*nav*15/6/1991*1*3*1*13/6/2017*0713559850*1*2*2"), null, null);
//    }


    public void setSpinnerListeners() {

        try {


            SelectstatusS.setOnItemSelectedListener(this);
            genderS.setOnItemSelectedListener(this);
            maritalS.setOnItemSelectedListener(this);
            conditionS.setOnItemSelectedListener(this);
//            categoryS.setOnItemSelectedListener(this);
            languageS.setOnItemSelectedListener(this);
            smsS.setOnItemSelectedListener(this);
            wklymotivation.setOnItemSelectedListener(this);
            messageTime.setOnItemSelectedListener(this);
            patientStatus.setOnItemSelectedListener(this);
//            GroupingS.setOnItemSelectedListener(this);
            newGroupingS.setOnItemSelectedListener(this);


        } catch (Exception e) {

        }
    }

    public void setOrphanSpinners() {

        try {
            orphanS.setOnItemSelectedListener(this);
            schoolS.setOnItemSelectedListener(this);
        } catch (Exception e) {


        }
    }


    public void initialise() {

        try {
            ageinyearsE=(EditText) findViewById(R.id.ageinyears);
            sm = new SendMessage(Registration.this);
            acs = new AccessServer(Registration.this);
            chkinternet = new CheckInternet(Registration.this);
            idnoE = (EditText) findViewById(R.id.idno);
            idnoS = "";

            locatorcountyE= (EditText) findViewById(R.id.locatorcounty);
            locatorsubcountyE= (EditText) findViewById(R.id.locatorsubcounty);
            locatorlocationE= (EditText) findViewById(R.id.locatorlocation);
            locatorwardE= (EditText) findViewById(R.id.locatorward);
            locatorvillageE= (EditText) findViewById(R.id.locatorvillage);


            buddyphoneE = (EditText) findViewById(R.id.buddyphone);
            altphoneE = (EditText) findViewById(R.id.altphone);
            disableL = (LinearLayout) findViewById(R.id.disablell);

            altphoneL = (LinearLayout) findViewById(R.id.altphonell);
            orphanL = (LinearLayout) findViewById(R.id.orphanll);
            idnoL = (LinearLayout) findViewById(R.id.idnoll);
            fileserialE = (EditText) findViewById(R.id.cfile);
            cccE = (EditText) findViewById(R.id.ccc);
            upnE = (EditText) findViewById(R.id.upn);
            smslayoutL = (LinearLayout) findViewById(R.id.smslayout);
            groupingL = (LinearLayout) findViewById(R.id.groupingll);
            f_nameE = (EditText) findViewById(R.id.f_name);
            s_nameE = (EditText) findViewById(R.id.s_name);
            o_nameE = (EditText) findViewById(R.id.o_name);
            dobE = (EditText) findViewById(R.id.dob);
            enrollment_dateE = (EditText) findViewById(R.id.enrollment_date);
            art_dateE = (EditText) findViewById(R.id.art_date);
            phoneE = (EditText) findViewById(R.id.phone);


            genderS = (Spinner) findViewById(R.id.gender_spinner);
            schoolS = (Spinner) findViewById(R.id.school_spinner);
            orphanS = (Spinner) findViewById(R.id.orphan_spinner);
            newGroupingS = (Spinner) findViewById(R.id.grouping_spinner);

            maritalS = (Spinner) findViewById(R.id.marital_spinner);
            conditionS = (Spinner) findViewById(R.id.condition_spinner);
//           categoryS=(Spinner) findViewById(R.id.category_spinner);
            languageS = (Spinner) findViewById(R.id.language_spinner);
            smsS = (Spinner) findViewById(R.id.sms_spinner);
            SelectstatusS = (Spinner) findViewById(R.id.status_spinner);
            wklymotivation = (Spinner) findViewById(R.id.weekly_spinner);
            messageTime = (Spinner) findViewById(R.id.time_spinner);
            patientStatus = (Spinner) findViewById(R.id.Patientstatus_spinner);
//           GroupingS=(Spinner) findViewById(R.id.grouping_spinner);


            gender_code = "";
            grouping_code="";
            new_grouping_code="";
            marital_code = "";
            condition_code = "";
            category_code = "";
            language_code = "";
            sms_code = "";
            Selectstatus_code = "";
            wklyMotivation_code = "";
            messageTime_code = "";
            patientStatus_code = "";
            orphan_code = "";
            school_code = "";

            locatorcountyS="-1";
            locatorsubcountyS="-1";
            locatorlocationS="-1";
            locatorwardS="-1";
            locatorvillageS="-1";
//           grouping_code="";
        } catch (Exception e) {

            Toast.makeText(this, "error initialising variables", Toast.LENGTH_SHORT).show();


        }
    }

    public void clearFields() {

        try {

            cccE.setText("");
            upnE.setText("");
            f_nameE.setText("");
            s_nameE.setText("");
            o_nameE.setText("");
            dobE.setText("");
            enrollment_dateE.setText("");
            art_dateE.setText("");
            phoneE.setText("");
            idnoE.setText("");
            if (altphoneE.isShown()) {
                altphoneE.setText("");
            }

            buddyphoneE.setText("");
            fileserialE.setText("");


            populateCondition();
            populateGender();
            populateLanguage();

            populateNewGrouping();
            populateSms();
//           populateStatus();
            populateweekly();
            populatemsg();
            populatepntstatus();
            populategrouping();

            gender_code = "";
            marital_code = "";
            condition_code = "";
            category_code = "";
            language_code = "";
            sms_code = "";
            Selectstatus_code = "";
            wklyMotivation_code = "";
            messageTime_code = "";
            patientStatus_code = "";
            orphan_code = "";
            school_code = "";


        } catch (Exception e) {


        }
    }


    public void EnrollmentdateListener() {

        try {

            enrollment_dateE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(Registration.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    enrollment_dateE.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        } catch (Exception e) {


        }
    }

    public void ArtdateListener() {

        try {

            art_dateE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(Registration.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    art_dateE.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        } catch (Exception e) {


        }
    }


    public void DobdateListener() {

        try {

            dobE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day


                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(Registration.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    dobE.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                    int mycurrentYear = Integer.parseInt(MyDates.getCurrentYear());
                                    int difference = mycurrentYear - year;

                                    ageinyearsE.setText(difference+" Years");


                                    if (difference >= 18) {
                                        idnoL.setVisibility(View.VISIBLE);
                                        if (isUcsf()) {
                                            orphanL.setVisibility(View.GONE);
                                        }


                                    }
                                    else {

                                        idnoL.setVisibility(View.GONE);
                                        if (isUcsf()) {

                                            orphanL.setVisibility(View.VISIBLE);
                                            setOrphanSpinners();
                                            populateOrphan();
                                            populateSchool();

                                        }


                                    }

                                    if(difference<=12){


                                        populateMaritalInfant();

                                    }
                                    else{

                                        populateMarital();

                                    }

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        } catch (Exception e) {


        }
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Spinner spin = (Spinner) parent;

        if (spin.getId() == R.id.gender_spinner) {


            gender_code = Integer.toString(position);

        }
        if (spin.getId() == R.id.marital_spinner) {

            marital_code = Integer.toString(position);


        }
        if (spin.getId() == R.id.condition_spinner) {

            condition_code = Integer.toString(position);

            if(position==2){

                art_dateE.setVisibility(View.GONE);

            }
            else{

                art_dateE.setVisibility(View.VISIBLE);
            }


        }
//        if (spin.getId()==R.id.category_spinner){
//
//            category_code=Integer.toString(position+1);
//
//
//        }

        if (spin.getId() == R.id.language_spinner) {

            language_code = Integer.toString(position);


        }

        if (spin.getId() == R.id.grouping_spinner) {

            new_grouping_code = Integer.toString(position);


        }

        if (spin.getId() == R.id.sms_spinner) {

            sms_code = Integer.toString(position);
//            Toast.makeText(this, ""+sms_code, Toast.LENGTH_SHORT).show();


            if (sms_code.equalsIgnoreCase("1")) {
                smslayoutL.setVisibility(View.VISIBLE);

            } else if (sms_code.equalsIgnoreCase("2")) {

                smslayoutL.setVisibility(View.GONE);

            }


        }

        if (spin.getId() == R.id.status_spinner) {

            Selectstatus_code = Integer.toString(position);
            if (Selectstatus_code.contentEquals("2")) {

                disableL.setVisibility(View.VISIBLE);

            } else {

                disableL.setVisibility(View.GONE);
            }


        }
        if (spin.getId() == R.id.school_spinner) {

            school_code = Integer.toString(position);


        }

        if (spin.getId() == R.id.orphan_spinner) {

            orphan_code = Integer.toString(position);


        }
        if (spin.getId() == R.id.weekly_spinner) {

            wklyMotivation_code = Integer.toString(position);


        }
        if (spin.getId() == R.id.time_spinner) {

            messageTime_code = Integer.toString(position);


        }

        if (spin.getId() == R.id.Patientstatus_spinner) {

            patientStatus_code = Integer.toString(position);

            if (patientStatus_code.contentEquals("1")) {
                populateStatusNew();
                cccE.setEnabled(false);
                populateMflCode();


            } else {
                cccE.setEnabled(true);
                cccE.setText("");
                populateStatus();

            }


        }

        if (spin.getId() == R.id.orphan_spinner) {

            orphan_code = Integer.toString(position);


        }


        if (spin.getId() == R.id.school_spinner) {

            school_code = Integer.toString(position);


        }


//        if (spin.getId()==R.id.grouping_spinner){
//
//            grouping_code=Integer.toString(position);
//
//
//        }


    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void actOnSelected() {

//        Toast.makeText(this, "you selected "+selected_item+"the behind scene value is "+myselected, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "you selected "+selected_item2+"the behind scene value is "+myselected2, Toast.LENGTH_SHORT).show();
    }

    public void populategrouping() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), groups);

            GroupingS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }


    public void populateGender() {


        try {

            if (isUcsf()) {
                SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), gendersUcsf);

                genderS.setAdapter(customAdapter);

            } else {

                SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), genders);

                genderS.setAdapter(customAdapter);

            }


        } catch (Exception e) {


        }
    }

    private boolean displayGrouping(){

        boolean result=false;

        if(isUcsf()){
            groupingL.setVisibility(View.GONE);
            result=false;
        }
        else{

            groupingL.setVisibility(View.VISIBLE);
            result=true;
        }
        return  result;
    }


    public void populateNewGrouping() {


        try {

            if (displayGrouping()) {

                SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), newgroupings);


                newGroupingS.setAdapter(customAdapter);




            } else {





            }


        } catch (Exception e) {


        }
    }


    public void populateMarital() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), maritals);


            maritalS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateMaritalInfant() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), maritalsInfants);


            maritalS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }


    public void populateCondition() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), conditions);

            conditionS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateSchool() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), schoolOp);

            schoolS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateOrphan() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), orphanOp);

            orphanS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }


//    public void populateCategory(){
//        List<String> mydata=new ArrayList<>();
//        mydata.add("Adults");
//        mydata.add("Adolescents");
//
//        try{
//
//            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mydata);
//
//            // Drop down layout style - list view with radio button
//            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            categoryS.setAdapter(dataAdapter);
//
//
//        }
//
//        catch(Exception e){
//
//
//        }
//    }


    public void populateLanguage() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), languages);


            languageS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateSms() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), smss);


            smsS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }


    public void populateStatus() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), statuss);


            SelectstatusS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateStatusNew() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), statussnew);


            SelectstatusS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateweekly() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), weeklymotivation);


            wklymotivation.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populatemsg() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), msgtime);


            messageTime.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }


    public void populatepntstatus() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), pntstatus);


            patientStatus.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }


    public void submitClicked(View v) {

        try {



            //start set locator information variables

//            locatorsubcountyS,locatorlocationS,locatorwardS,locatorvillageS

            if(locatorcountyE.getText().toString().trim().isEmpty()){

                locatorcountyS="-1";

            }
            else{

                locatorcountyS=locatorcountyE.getText().toString();

            }

            if(locatorsubcountyE.getText().toString().trim().isEmpty()){

                locatorsubcountyS="-1";

            }
            else{

                locatorsubcountyS=locatorsubcountyE.getText().toString();

            }


            if(locatorlocationE.getText().toString().trim().isEmpty()){

                locatorlocationS="-1";

            }
            else{

                locatorlocationS=locatorlocationE.getText().toString();

            }


            if(locatorwardE.getText().toString().trim().isEmpty()){

                locatorwardS="-1";

            }
            else{

                locatorwardS=locatorwardE.getText().toString();

            }

            if(locatorvillageE.getText().toString().trim().isEmpty()){

                locatorvillageS="-1";

            }
            else{

                locatorvillageS=locatorvillageE.getText().toString();

            }


            //end set locator information variables

            String cccS = cccE.getText().toString();
            String fileserialS = fileserialE.getText().toString();
            String upnS = upnE.getText().toString();
            String f_nameS = f_nameE.getText().toString();
            String s_nameS = s_nameE.getText().toString();
            String o_nameS = o_nameE.getText().toString();
            String dobS = dobE.getText().toString();
            String enrollmentS = enrollment_dateE.getText().toString();
            String art_dateS = art_dateE.getText().toString();
            String phoneS = phoneE.getText().toString();
            String altphoneNumber = "-1";
            String buddyphoneNumber = "-1";
            String idNumber = "-1";

            String[] dobArray = new String[]{};
            String[] enrollmentArray = new String[]{};
            String[] artArray = new String[]{};

            String dobYear = "";
            String dobMnth = "";
            String dobDay = "";

            int dobYearv = -1;
            int dobMnthv = -1;
            int dobDayv = -1;

            String artYear = "";
            String artMnth = "";
            String artDay = "";

            int artYearv = -1;
            int artMnthv = -1;
            int artDayv = -1;

            String enrollYear = "";
            String enrollMnth = "";
            String enrollDay = "";

            int enrollYearv = -1;
            int enrollMnthv = -1;
            int enrollDayv = -1;


            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String currentArray[] = timeStamp.split("\\.");
            String currentDate = currentArray[2];
            String currentMonth = currentArray[1];
            String currentYear = currentArray[0];

            int cdate = Integer.parseInt(currentDate);
            int cmnth = Integer.parseInt(currentMonth);
            int cyear = Integer.parseInt(currentYear);

            if (!dobS.isEmpty()) {
                dobArray = dobS.split("/");
                dobYear = dobArray[2];
                dobMnth = dobArray[1];
                dobDay = dobArray[0];

                dobYearv = Integer.parseInt(dobYear);
                dobMnthv = Integer.parseInt(dobMnth);
                dobDayv = Integer.parseInt(dobDay);

            }
            if (!enrollmentS.isEmpty()) {
                enrollmentArray = enrollmentS.split("/");

                enrollYear = enrollmentArray[2];
                enrollMnth = enrollmentArray[1];
                enrollDay = enrollmentArray[0];

                enrollYearv = Integer.parseInt(enrollYear);
                enrollMnthv = Integer.parseInt(enrollMnth);
                enrollDayv = Integer.parseInt(enrollDay);

            }
            if (!art_dateS.isEmpty()) {

                artArray = art_dateS.split("/");

                artYear = artArray[2];
                artMnth = artArray[1];
                artDay = artArray[0];

                artYearv = Integer.parseInt(artYear);
                artMnthv = Integer.parseInt(artMnth);
                artDayv = Integer.parseInt(artDay);
            }


//            get the dob years


//            get the art years


//            get the art years


            if (patientStatus_code.contentEquals("0")) {

                Toast.makeText(this, "Please Select transaction type", Toast.LENGTH_SHORT).show();


            } else if (cccS.trim().isEmpty()) {

                cccE.setError("mfl code is required");

            } else if (upnS.trim().isEmpty()) {

                upnE.setError("CCC No. required");

            }
//           else if(fileserialS.trim().isEmpty() && !patientStatus_code.contentEquals("2")){
//
//                fileserialE.setError("file serial number is required");
//           }

            else if (f_nameS.trim().isEmpty() && !patientStatus_code.contentEquals("2")) {

                f_nameE.setError("First name required");

            } else if (s_nameS.trim().isEmpty() && !patientStatus_code.contentEquals("2")) {
                s_nameE.setError("Second name required");
            } else if (dobS.trim().isEmpty() && !patientStatus_code.contentEquals("2")) {
                dobE.setError("Date of Birth required");
            }
//            else if(idnoL.isShown() && idnoE.getText().toString().trim().isEmpty()){
//                idnoE.setError("ID Number is required");
//           }
            else if (enrollmentS.trim().isEmpty() && !patientStatus_code.contentEquals("2")) {
                enrollment_dateE.setError("Enrollment date required");
            } else if (art_dateE.isShown() && art_dateS.trim().isEmpty() && !patientStatus_code.contentEquals("2")) {
                art_dateE.setError("ART date required");
            } else if (phoneS.trim().isEmpty() && !patientStatus_code.contentEquals("2")) {
                phoneE.setError("Phone required");
            } else if ((!dobS.trim().isEmpty()) && dobYearv > cyear) {

                dobE.setError("Date of Birth should be less than today");
                Toast.makeText(this, "Date of Birth should be less than today", Toast.LENGTH_SHORT).show();

            } else if ((!dobS.trim().isEmpty()) && dobYearv == cyear && dobMnthv > cmnth) {

                dobE.setError("Date of Birth should be less than today");
                Toast.makeText(this, "Date of Birth should be less than today", Toast.LENGTH_SHORT).show();

            } else if ((!dobS.trim().isEmpty()) && dobYearv == cyear && dobMnthv == cmnth && dobDayv >= cdate) {

                dobE.setError("Date of Birth should be less than today");
                Toast.makeText(this, "Date of Birth should be less than today", Toast.LENGTH_SHORT).show();

            } else if ((!dobS.trim().isEmpty()) && (!enrollmentS.trim().isEmpty()) && dobYearv > enrollYearv) {

                dobE.setError("Date of Birth should be less than enroll date");
                Toast.makeText(this, "Date of Birth should be less than enroll date", Toast.LENGTH_SHORT).show();

            } else if ((!dobS.trim().isEmpty()) && (!enrollmentS.trim().isEmpty()) && dobYearv == enrollYearv && dobMnthv > enrollMnthv) {

                dobE.setError("Date of Birth should be less than enroll date");
                Toast.makeText(this, "Date of Birth should be less than enroll date", Toast.LENGTH_SHORT).show();

            } else if ((!dobS.trim().isEmpty()) && (!enrollmentS.trim().isEmpty()) && dobYearv == enrollYearv && dobMnthv == enrollMnthv && dobDayv >= enrollDayv) {

                dobE.setError("Date of Birth should be less than enroll date");
                Toast.makeText(this, "Date of Birth should be less than enroll date", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!dobS.trim().isEmpty()) && (!art_dateS.trim().isEmpty()) && dobYearv > artYearv) {

                dobE.setError("Date of Birth should be less than ART date");
                Toast.makeText(this, "Date of Birth should be less than ART date", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!dobS.trim().isEmpty()) && (!art_dateS.trim().isEmpty()) && dobYearv == artYearv && dobMnthv > artMnthv) {

                dobE.setError("Date of Birth should be less than ART date");
                Toast.makeText(this, "Date of Birth should be less than  ART date", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!dobS.trim().isEmpty()) && (!art_dateS.trim().isEmpty()) && dobYearv == artYearv && dobMnthv == artMnthv && dobDayv >= artDayv) {

                dobE.setError("Date of Birth should be less than ART date");
                Toast.makeText(this, "Date of Birth should be less than ART date", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!art_dateS.trim().isEmpty()) && artYearv > cyear) {

                art_dateE.setError("ART date should not be greater than today");
                Toast.makeText(this, "ART date should not be greater than today", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!art_dateS.trim().isEmpty()) && artYearv == cyear && artMnthv > cmnth) {
                art_dateE.setError("ART date should not be greater than today");
                Toast.makeText(this, "ART date should not be greater than today", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!art_dateS.trim().isEmpty()) && artYearv == cyear && artMnthv == cmnth && artDayv > cdate) {

                art_dateE.setError("ART date should not be greater than today");
                Toast.makeText(this, "ART date should not be greater than today", Toast.LENGTH_SHORT).show();

            }

//            else if(enrollYearv>cyear){
//
//                enrollment_dateE.setError("Enrollment date should be less than today");
//                Toast.makeText(this, "Enrollment date should be less than today", Toast.LENGTH_SHORT).show();
//
//            }
//            else if(enrollYearv==cyear&&enrollMnthv>cmnth){
//                enrollment_dateE.setError("Enrollment date should be less than today");
//                Toast.makeText(this, "Enrollment date should be less than today", Toast.LENGTH_SHORT).show();
//
//            }
//
//            else if(enrollYearv==cyear&&enrollMnthv==cmnth && enrollDayv>=cdate){
//
//                enrollment_dateE.setError("Enrollment date should be less than today");
//                Toast.makeText(this, "Enrollment date should be less than today", Toast.LENGTH_SHORT).show();
//
//            }

            else if (art_dateE.isShown() && (!enrollmentS.trim().isEmpty()) && enrollYearv > artYearv) {

                enrollment_dateE.setError("Enrollment date should be less than ART date");
                Toast.makeText(this, "Enrollment date should be less than ART date", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!enrollmentS.trim().isEmpty()) && enrollYearv == artYearv && enrollMnthv > artMnthv) {
                enrollment_dateE.setError("Enrollment date should be less than ART date");
                Toast.makeText(this, "Enrollment date should be less than ART date", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!enrollmentS.trim().isEmpty()) && enrollYearv == artYearv && enrollMnthv == artMnthv && enrollDayv > artDayv) {

                enrollment_dateE.setError("Enrollment date should be less than or equal to ART date");
                Toast.makeText(this, "Enrollment date should be less than or equal to ART date", Toast.LENGTH_SHORT).show();

            } else if (gender_code.contentEquals("0") && !patientStatus_code.contentEquals("2")) {
                Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();


            } else if (marital_code.contentEquals("0") && !patientStatus_code.contentEquals("2")) {
                Toast.makeText(this, "Please Select Marital Status", Toast.LENGTH_SHORT).show();


            } else if (condition_code.contentEquals("0") && !patientStatus_code.contentEquals("2")) {

                Toast.makeText(this, "Please Select Condition", Toast.LENGTH_SHORT).show();


            }
//            else if(grouping_code.contentEquals("0")){
//                Toast.makeText(this, "Please Select Grouping", Toast.LENGTH_SHORT).show();
//
//
//            }
            else if (smslayoutL.isShown() && language_code.contentEquals("0")) {

                Toast.makeText(this, "Please Select Language", Toast.LENGTH_SHORT).show();


            } else if (sms_code.contentEquals("0") && !patientStatus_code.contentEquals("2")) {
                Toast.makeText(this, "Please Select Sms Option", Toast.LENGTH_SHORT).show();


            } else if (sms_code.contentEquals("1") && wklyMotivation_code.contentEquals("0") && !patientStatus_code.contentEquals("2")) {
                Toast.makeText(this, "Please Select motivational code", Toast.LENGTH_SHORT).show();


            } else if (Selectstatus_code.contentEquals("0") && !patientStatus_code.contentEquals("2")) {
                Toast.makeText(this, "Please Select Status", Toast.LENGTH_SHORT).show();


            } else {

                if (sms_code.contentEquals("0") || sms_code.contentEquals("2")) {

                    sms_code = "-1";
                    wklyMotivation_code = "-1";
                }
                if (idnoE.isShown() && !idnoE.getText().toString().trim().isEmpty()) {
                    idnoS = idnoE.getText().toString();
                } else {

                    idnoS = "-1";
                }

                //*******check empty values and set them to -1***

                /* Encrypt */
                if (cccS.trim().isEmpty()) {
                    cccS = "-1";
                }
                if (f_nameS.trim().isEmpty()) {
                    f_nameS = "-1";
                }
                if (s_nameS.trim().isEmpty()) {

                    s_nameS = "-1";
                }
                if (o_nameS.trim().isEmpty()) {

                    o_nameS = "-1";
                }
                if (dobS.trim().isEmpty()) {

                    dobS = "-1";
                }
                if (gender_code.trim().isEmpty()) {

                    gender_code = "-1";
                }
                if (gender_code.contentEquals("0")) {

                    gender_code = "-1";
                }
                if (marital_code.trim().isEmpty()) {
                    marital_code = "-1";
                }
                if (marital_code.contentEquals("0")) {
                    marital_code = "-1";
                }
                if (condition_code.trim().isEmpty()) {
                    condition_code = "-1";
                }
                if (condition_code.contentEquals("0")) {
                    condition_code = "-1";
                }

                if (enrollmentS.trim().isEmpty()) {
                    enrollmentS = "-1";
                }
                if (art_dateS.trim().isEmpty()) {
                    art_dateS = "-1";
                }
                if (phoneS.trim().isEmpty()) {
                    phoneS = "-1";
                }
                if (language_code.trim().isEmpty()) {
                    language_code = "-1";
                }
                if (language_code.contentEquals("0")) {
                    language_code = "-1";
                }
                if (sms_code.trim().isEmpty()) {
                    sms_code = "-1";
                }
                if (sms_code.contentEquals("0")) {
                    sms_code = "-1";
                }
                if (wklyMotivation_code.trim().isEmpty()) {
                    wklyMotivation_code = "-1";
                }
                if (wklyMotivation_code.contentEquals("0")) {
                    wklyMotivation_code = "-1";
                }
                if (messageTime_code.trim().isEmpty()) {
                    messageTime_code = "-1";
                }

                if (messageTime_code.contentEquals("0")) {

                    messageTime_code = "-1";
                }
                if (Selectstatus_code.trim().isEmpty()) {
                    Selectstatus_code = "-1";
                }
                if (Selectstatus_code.contentEquals("0")) {
                    Selectstatus_code = "-1";
                }
                if (patientStatus_code.trim().isEmpty()) {
                    patientStatus_code = "-1";
                }
                if (patientStatus_code.contentEquals("0")) {

                    patientStatus_code = "-1";
                }
                if (altphoneE.isShown() && !altphoneE.getText().toString().isEmpty()) {

                    altphoneNumber = altphoneE.getText().toString();
                } else {

                    altphoneNumber = "-1";
                }

                if (buddyphoneE.isShown() && !buddyphoneE.getText().toString().isEmpty()) {

                    buddyphoneNumber = altphoneE.getText().toString();
                } else {

                    buddyphoneNumber = "-1";
                }

                if(!groupingL.isShown()){

                    new_grouping_code="-1";


                }

                if(!art_dateE.isShown()){

                    art_dateS="-1";

                }

//*******check empty values and set them to -1***

                String newupns = AppendFunction.AppendUniqueIdentifier(upnS);
                String myccnumber = cccS + newupns;

                String sendSms = myccnumber + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + altphoneNumber + "*" + buddyphoneNumber + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code+"*"+new_grouping_code+"*"+locatorcountyS+"*"+locatorsubcountyS+"*"+locatorlocationS+"*"+locatorwardS+"*"+locatorvillageS;

//                    String sendSms = "Reg*" + cccS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + language_code + "*" + sms_code + "*" +wkly_code+"*"+pnt_code+"*"+message_code+"*"+ status_code;


                String encrypted = Base64Encoder.encryptString(sendSms);


                String mynumber = Config.mainShortcode;

                if (chkinternet.isInternetAvailable()) {
                    List<Activelogin> myl = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
                    for (int x = 0; x < myl.size(); x++) {

                        String un = myl.get(x).getUname();
                        List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
                        for (int y = 0; y < myl2.size(); y++) {

                            String phne = myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                            acs.sendDetailsToDbPost("Reg*" + encrypted, phne);
                        }
                    }


                } else {

                    sm.sendMessageApi("Reg*" + encrypted, mynumber);
                    LogindisplayDialog("Client registered successfully, kindly confirm that you have received the client registration successful SMS before booking an appointment");



                }


                final String mycc = cccE.getText().toString();
                final String myupn = upnE.getText().toString();

                saveClientArtData(mycc, myupn, art_dateS);

                clearFields();

                counter = counter + 1;


                RegisterCounter newcount = new RegisterCounter(counter);
                newcount.save();

//                        System.out.println("decrypted text is "+decrypted);

//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//
//                    alertDialogBuilder.setMessage("DO you want to make appointment");
//                    alertDialogBuilder.setPositiveButton("yes",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface arg0, int arg1) {
////                                    Toast.makeText(Appointment.this,"You clicked yes button",Toast.LENGTH_LONG).show();
//
//
//                                    Intent myint = new Intent(Registration.this , Appointment.class);
//                                    myint.putExtra("ccc",mycc);
//                                    myint.putExtra("upn",myupn);
//                                    startActivity(myint);
//
//                                    Toast.makeText(getApplicationContext(), "Registration was submitted successfully", Toast.LENGTH_SHORT).show();
//
//
////                                    myint.putExtra("value","ths value");
//                                }
//                            });
//
//                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//
//
//                            Toast.makeText(getApplicationContext(), "Registration was submitted successfully", Toast.LENGTH_SHORT).show();
//
////                            finish();
//                        }
//                    });
//
//
//
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//                    alertDialog.show();

//                    LogindisplayDialog("success submitting Registration");


            }


        } catch (Exception e) {
            Toast.makeText(this, "error in submission, try again " + e, Toast.LENGTH_SHORT).show();


        }
    }


    public void saveClientArtData(String mycc, String myupn, String art_dateS) {

        try {

            List<Clientartdate> myl = Clientartdate.findWithQuery(Clientartdate.class, "select * from Clientartdate where mfl=? and upn=?", mycc, myupn);
            if (myl.size() > 0) {

                Clientartdate.executeQuery("update Clientartdate set artdate=? where mfl=? and upn=?", art_dateS, mycc, myupn);

            } else {

                Clientartdate crd = new Clientartdate(mycc, myupn, art_dateS);
                crd.save();
            }
        } catch (Exception e) {


        }
    }


//    public static String MD5_Hash(String s) {
//        MessageDigest m = null;
//
//        try {
//            m = MessageDigest.getInstance("SHA-256");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//
//        m.update(s.getBytes(),0,s.length());
//        String hash = new BigInteger(1, m.digest()).toString(16);
//        return hash;
//    }


    public void LogindisplayDialog(String message) {

        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(this);
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
            AlertDialog mydialog = adb.create();
            mydialog.show();
        } catch (Exception e) {


        }

    }


}

