package com.example.mhealth.appointment_diary.utilitymodules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.tables.Myaffiliation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class UPIUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner genderS, maritalS, conditionS, enrollmentS, languageS, smsS, wklymotivation, messageTime, SelectstatusS, patientStatus, GroupingS, orphanS, schoolS, newGroupingS;
    String condition_code, grouping_code, new_grouping_code, category_code, language_code, sms_code,sms_id, Selectstatus_code, wklyMotivation_code, messageTime_code, patientStatus_code, school_code, orphan_code, idnoS, upi_no, birth_cert_no, locatorcountyS, locatorsubcountyS, locatorlocationS, locatorwardS, locatorvillageS;

    String[] genders = {"", "Female", "Male"};
    String[] gendersUcsf = {"", "Female", "Male"};

    String[] smss = {"", "Yes", "No"};

    EditText upitext, cfile, f_name, s_name, o_name, dob, idno, birthno, enrollment_date, art_date, phone;
    String newUpi, Idno, Birthno, Enrollment_date, Art_date, Phone;
    Button populate1;
    boolean a;

    int genderid, gender_code, marital_code, marital_id;

    //Please Select Sex
    String[] maritals = {"", "Single", "Married Monogomaus", "Married Polygamous", "Divorced", "Widowed", "Cohabiting", "Not Applicable"};
    //Please Select Marital Status*
    String[] maritalsInfants = {"", "Single", "Not Applicable"};

    String fname;
    String file;
    String mname;
    String lname;
    String dob1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upiupdate);

        genderS = (Spinner) findViewById(R.id.gender_spinner);
        schoolS = (Spinner) findViewById(R.id.school_spinner);
        orphanS = (Spinner) findViewById(R.id.orphan_spinner);
        newGroupingS = (Spinner) findViewById(R.id.grouping_spinner);

        smsS = (Spinner) findViewById(R.id.sms_spinner);

        maritalS = (Spinner) findViewById(R.id.marital_spinner);
        conditionS = (Spinner) findViewById(R.id.condition_spinner);
//           categoryS=(Spinner) findViewById(R.id.category_spinner);
        languageS = (Spinner) findViewById(R.id.language_spinner);
        smsS = (Spinner) findViewById(R.id.sms_spinner);
        SelectstatusS = (Spinner) findViewById(R.id.status_spinner);
        wklymotivation = (Spinner) findViewById(R.id.weekly_spinner);
        messageTime = (Spinner) findViewById(R.id.time_spinner);
        patientStatus = (Spinner) findViewById(R.id.Patientstatus_spinner);

        upitext = (EditText) findViewById(R.id.cccupi);
        cfile = (EditText) findViewById(R.id.cfile);
        f_name = (EditText) findViewById(R.id.f_name);
        s_name = (EditText) findViewById(R.id.s_name);
        o_name = (EditText) findViewById(R.id.o_name);
        dob = (EditText) findViewById(R.id.dob);
        idno = (EditText) findViewById(R.id.idno);
        birthno = (EditText) findViewById(R.id.birthno);
        enrollment_date = (EditText) findViewById(R.id.enrollment_date);
        art_date = (EditText) findViewById(R.id.art_date);

        phone = (EditText) findViewById(R.id.phone);


        populate1 = (Button) findViewById(R.id.populate);
        gender_code = 0;
        marital_code = 0;
        sms_code="";


        setSpinnerListeners();
        //populateGender();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newUpi = null;
            } else {
                newUpi = extras.getString("UPI");

            }
        } else {
            newUpi = (String) savedInstanceState.getSerializable("Client_CCC");

        }

        Toast.makeText(UPIUpdateActivity.this, "" + newUpi, Toast.LENGTH_SHORT).show();
        upitext.setText(newUpi);
        populate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdetails1();

            }
        });

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Update Client's Details");

        } catch (Exception e) {

        }


    }

    public void getdetails1() {
        //?client_id=MOH1668675613
        //  AndroidNetworking.get("https://ushauriapi.kenyahmis.org/mohupi/search?client_id=MOH1668675613")
        String urls = "?client_id=" + newUpi;
        AndroidNetworking.get("https://ushauriapi.kenyahmis.org/mohupi/search" + urls)

                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection", "keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(UPIUpdateActivity.this, "sucess", Toast.LENGTH_SHORT).show();
                        try {
                            a = response.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (a) {

                            try {

                                JSONObject jsonObject = response.getJSONObject("message");

                                fname = jsonObject.getString("f_name");
                                file = jsonObject.getString("file_no");
                                mname = jsonObject.getString("m_name");
                                //Toast.makeText(UPIUpdateActivity.this, "sucess"+mname, Toast.LENGTH_SHORT).show();
                                lname = jsonObject.getString("l_name");
                                dob1 = jsonObject.getString("dob");
                                Idno = jsonObject.getString("national_id");
                                Birthno = jsonObject.getString("birth_cert_no");
                                Enrollment_date = jsonObject.getString("enrollment_date");
                                Art_date = jsonObject.getString("art_date");
                                Phone = jsonObject.getString("phone_no");

                                genderid = jsonObject.getInt("gender");
                                marital_id = jsonObject.getInt("marital");
                                sms_id= jsonObject.getString("smsenable");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            f_name.setText(fname);
                            s_name.setText(mname);
                            cfile.setText(file);
                            dob.setText(dob1);
                            idno.setText(Idno);
                            birthno.setText(Birthno);
                            enrollment_date.setText(Enrollment_date);
                            art_date.setText(Art_date);
                            phone.setText(Phone);
                            gender_code = genderid;
                            marital_code = marital_id;
                            sms_code=sms_id;
                            populateGender();
                            populateMarital();
                            populateSms();

                            Log.d("SMS", sms_code);
                            // Toast.makeText(UPIUpdateActivity.this, gender_code, Toast.LENGTH_SHORT).show();

                            o_name.setText(lname);
                        } else {
                            Toast.makeText(UPIUpdateActivity.this, "No record found", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    {


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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        Spinner spin = (Spinner) parent;

        if (spin.getId() == R.id.gender_spinner) {


            gender_code = position;


        }
        if (spin.getId() == R.id.marital_spinner) {

            marital_code = position;

        }

        if (spin.getId() == R.id.sms_spinner) {

           // sms_code = String.valueOf(position);

           sms_code = String.valueOf(spin.getSelectedItem());
           Log.d("SELECT", sms_code);
            //sms_code =smsS.getSelectedItem().toString();


        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void populateGender() {


        try {

            if (isUcsf()) {
                SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), gendersUcsf);

                genderS.setAdapter(customAdapter);
                //genderS.setSelection(Integer.parseInt(gender_code));
                genderS.setSelection(gender_code);

            } else {

                SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), genders);

                genderS.setAdapter(customAdapter);
                genderS.setSelection(gender_code);

            }


        } catch (Exception e) {


        }
    }

    public void populateMarital() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), maritals);


            maritalS.setAdapter(customAdapter);
            maritalS.setSelection(marital_code);


        } catch (Exception e) {


        }
    }

    public void populateSms() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), smss);


            smsS.setAdapter(customAdapter);
            //smsS.setSelection(sms_code);
            //smsS.getSelectedItem().toString();

          // smsS.setSelection(Integer.parseInt(sms_code));
           // sms_code = String.valueOf(spin.getSelectedItem());
            //sms_code =smsS.getSelectedItem().toString();

        } catch (Exception e) {


        }
    }

}