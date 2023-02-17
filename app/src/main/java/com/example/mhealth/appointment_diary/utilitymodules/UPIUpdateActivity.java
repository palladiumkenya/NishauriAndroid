package com.example.mhealth.appointment_diary.utilitymodules;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.AppendFunction.AppendFunction;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.Progress.Progress;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.models.RegisterCounter;
import com.example.mhealth.appointment_diary.models.counties;
import com.example.mhealth.appointment_diary.models.scounties;
import com.example.mhealth.appointment_diary.models.wards;
import com.example.mhealth.appointment_diary.pmtct.ANCVisit;
import com.example.mhealth.appointment_diary.pmtct.ANCVisitStarted;
import com.example.mhealth.appointment_diary.pmtct.LaborAndDeliveryStart;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Mflcode;
import com.example.mhealth.appointment_diary.tables.Myaffiliation;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.example.mhealth.appointment_diary.wellnesstab.UPIErrorList;
import com.google.android.material.snackbar.Snackbar;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

public class UPIUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner genderS, maritalS, conditionS, enrollmentS, languageS, smsS, wklymotivation, messageTime, SelectstatusS, patientStatus, GroupingS, orphanS, schoolS, newGroupingS;
    String condition_code, grouping_code, new_grouping_code, category_code, language_code, sms_code,sms_id, Selectstatus_code, wklyMotivation_code, messageTime_code, patientStatus_code, school_code, orphan_code, idnoS, upi_no, birth_cert_no, locatorcountyS, locatorsubcountyS, locatorlocationS, locatorwardS, locatorvillageS, county_code, scounty_code, ward_code;

    String[] genders = {"", "Female", "Male"};
    String[] gendersUcsf = {"", "Female", "Male"};
    String mflcode = "";

    String[] smss = {"", "Yes", "No"};
    public String z;
    String county1;
    ArrayList<String> countiesListb;
    ArrayList<counties> countiessb;
    private int countyIDb = 0;

    ArrayList<String> countiesList;
    ArrayList<counties> countiess;

    ArrayList<String> scountyList;
    ArrayList<scounties> scountiess;



    ArrayList<String> wardsList;
    ArrayList<wards> wardss;

    private int countyID = 0;
    private int scountyID = 0;

    private int wardID = 0;





    EditText upitext, cfile, f_name, s_name, o_name, dob, idno, birthno, enrollment_date, art_date, phone;
    String newUpi, Cno, Idno, Birthno, Enrollment_date, Art_date, Phone;
    Button populate1,btnRSubm1;
    boolean a;

    int genderid, gender_code, marital_code, marital_id, county_code1, scounty_code1, ward_code1;

    //Please Select Sex
    String[] maritals = {"", "Single", "Married Monogomaus", "Married Polygamous", "Divorced", "Widowed", "Cohabiting", "Not Applicable"};
    //Please Select Marital Status*
    String[] maritalsInfants = {"", "Single", "Not Applicable"};

    String fname;
    String file;
    String mname;
    String lname;
    String dob1;
    private RequestQueue rq;
    private SearchableSpinner birthSpinner, ServiceSpinner, serviceUnitSpinner, rankSpinner, countrySpinner;

    Progress pr;
    ProcessMessage pm;
    Dialogs dialogs;

    CheckInternet chkinternet;
    AccessServer acs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upiupdate);
        chkinternet=new CheckInternet(UPIUpdateActivity.this);
        dialogs =new Dialogs(UPIUpdateActivity.this);


        rq = Volley.newRequestQueue(UPIUpdateActivity.this);

        ServiceSpinner = findViewById(R.id.ServiceSpinner);
        serviceUnitSpinner = findViewById(R.id.serviceUnit);
        rankSpinner = findViewById(R.id.RankSpinner);
        birthSpinner = findViewById(R.id.birthCountySpinner);
        countrySpinner = findViewById(R.id.countrySpinner);

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
        btnRSubm1= (Button) findViewById(R.id.btnRSubmit);
        gender_code = 0;
        marital_code = 0;
        sms_code="";


        setSpinnerListeners();
        getcountiesbirth();
        //getFacilities();
        //populateGender();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newUpi = null;
                Cno=null;
            } else {
                newUpi = extras.getString("UPI");
                Cno = extras.getString("ccc");

            }
        } else {
            newUpi = (String) savedInstanceState.getSerializable("Client_CCC");

        }


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(UPIUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        dob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });

        enrollment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(UPIUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        enrollment_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();
            }
        });

        art_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(UPIUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                      art_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();

            }
        });

        Toast.makeText(UPIUpdateActivity.this, "" + newUpi, Toast.LENGTH_SHORT).show();
        upitext.setText(newUpi);
        populate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!chkinternet.isInternetAvailable()){

                    Toast.makeText(UPIUpdateActivity.this, "Check Your Internet connection", Toast.LENGTH_LONG).show();
                }else{
                getdetails1();}}


        });

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Update Client's Details");

        } catch (Exception e) {

        }


        btnRSubm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!chkinternet.isInternetAvailable()) {
                    Toast.makeText(UPIUpdateActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                }else{
                   update1();
                    //updateUserDetails();
               }
               // Toast.makeText(UPIUpdateActivity.this, "sssss", Toast.LENGTH_LONG).show();
            }
        });


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
                               // county1 =jsonObject.getString("locator_county");
                               // county_code1 = Integer.parseInt(jsonObject.getString("locator_county"));
                                county_code1 = Integer.parseInt(jsonObject.getString("locator_county"));
                                scounty_code1= Integer.parseInt(jsonObject.getString("locator_sub_county"));
                                ward_code1= Integer.parseInt(jsonObject.getString("locator_ward"));

                              //  Log.d("COUNTY",String.valueOf(countyID));
                                Log.d("COUNTY",String.valueOf(county_code1));


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
                            /*county_code1=countyID;
                            scounty_code1=scountyID;
                            ward_code1=wardID;*/
                            populateGender();
                            populateMarital();
                            populateSms();
                           getFacilities();

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
    //get counties

    //getcounties

    public void getFacilities() {
        //String curl = "https://ushauriapi.kenyahmis.org/locator/counties";
        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {

        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                z+Config.COUNTIES, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {


                try {


                    countiess = new ArrayList<counties>();
                    countiesList = new ArrayList<String>();

                    countiess.clear();
                    countiesList.clear();


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject service = (JSONObject) response.get(i);


                        int id = service.has("id") ? service.getInt("id") : 0;
                        String name = service.has("name") ? service.getString("name") : "";
                        int code = service.has("code") ? service.getInt("code") : 0;


                        counties newCounty = new counties(id, name, code);

                        countiess.add(newCounty);
                        countiesList.add(newCounty.getName());
                    }
                    countiess.add(new counties(0, " ", 0));
                    countiesList.add(" ");




                    ArrayAdapter<String> aa = new ArrayAdapter<String>(UPIUpdateActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            countiesList) {
                        @Override
                        public int getCount() {
                            return super.getCount(); // you dont display last item. It is used as hint.

                          // return  countiesList.size();
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // if (ServiceSpinner != null){
                    ServiceSpinner.setAdapter(aa);
                    //ServiceSpinner.setSelection(countyID);
                    ServiceSpinner.setSelection(aa.getCount() - 1);
                    countyID = countiess.get(aa.getCount() - 1).getId();



                    ServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {



                           try {
                               countyID = countiess.get(position).getId();
                           }catch(Exception e){
                               e.printStackTrace();
                           }

                            try {
                                getDepartments(countyID);
                                //getDepartments(county_code1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //getDepartments(serviceID);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {


                        }
                    });




                    //}

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UPIUpdateActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(Registration.this, " cant get services", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                getFacilities();
            }
        }
        ) {

            /**
             * Passing some request headers
             */
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Authorization", loggedInUser.getToken_type()+" "+loggedInUser.getAccess_token());
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

        };


        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //AppController.getInstance().addToRequestQueue(jsonObjReq);
        rq.add(jsonArrayRequest);


    }

    //SubcountyList

    public void getDepartments(int ID) {

        String url = "https://ushauriapi.kenyahmis.org/locator/scounties?county=";
        //"https://ushauriapi.kenyahmis.org/locator/scounties?county=47";

        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {

        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, z+Config.S_COUNTIES+ID,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //Toast.makeText(Registration.this, "response", Toast.LENGTH_LONG).show();

                try {

                    scountiess = new ArrayList<scounties>();
                    scountyList = new ArrayList<String>();

                    scountiess.clear();
                    scountyList.clear();


                    //JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject serviceUnit = (JSONObject) response.get(i);

                        int id = serviceUnit.has("id") ? serviceUnit.getInt("id") : 0;
                        String name = serviceUnit.has("name") ? serviceUnit.getString("name") : "";


                        scounties newServiceUnit = new scounties(id, name);

                        scountiess.add(newServiceUnit);
                        scountyList.add(newServiceUnit.getName());
                    }

                    scountiess.add(new scounties(0, ""));
                    scountyList.add("");

                    ArrayAdapter<String> aa = new ArrayAdapter<String>(UPIUpdateActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            scountyList) {
                        @Override
                        public int getCount() {
                            return super.getCount(); // you dont display last item. It is used as hint.
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    serviceUnitSpinner.setAdapter(aa);

                    serviceUnitSpinner.setSelection(aa.getCount() - 1);
                   // serviceUnitSpinner.setSelection(scountyID - 1);
                    scountyID = scountiess.get(aa.getCount() - 1).getId();

                    serviceUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        //@Overide
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                            // Toast.makeText(Registration.this, "null selected", Toast.LENGTH_LONG).show();
                            scountyID = scountiess.get(position).getId();
                            getWards(scountyID);

//                                Toast.makeText(context,facilityDepartments.get(position).getDepartment_name(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Registration.this, "cant get", Toast.LENGTH_LONG).show();


                if (error instanceof NetworkError) {
                    Toast.makeText(UPIUpdateActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UPIUpdateActivity.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UPIUpdateActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
                error.printStackTrace();
                getDepartments(countyID);
               // getDepartments(county_code1);
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Authorization", loggedInUser.getToken_type()+" "+loggedInUser.getAccess_token());

                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //AppController.getInstance().addToRequestQueue(jsonObjReq);
        rq.add(jsonArrayRequest);


    }
//call wards

    public void getWards(int ID) {

        String url = "https://ushauriapi.kenyahmis.org/locator/wards?scounty=";
        //https://ushauriapi.kenyahmis.org/locator/wards?scounty=1
        //"https://ushauriapi.kenyahmis.org/locator/scounties?county=47";

        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {

        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,  z+Config.WARDS+ID,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //Toast.makeText(Registration.this, "response", Toast.LENGTH_LONG).show();

                try {

                    wardss = new ArrayList<wards>();
                    wardsList = new ArrayList<String>();

                    wardss.clear();
                    wardsList.clear();


                    //JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject serviceUnit = (JSONObject) response.get(i);

                        int id = serviceUnit.has("id") ? serviceUnit.getInt("id") : 0;
                        String name = serviceUnit.has("name") ? serviceUnit.getString("name") : "";
                        int scounty_id = serviceUnit.has("scounty_id") ? serviceUnit.getInt("scounty_id") : 0;


                        wards newServiceUnit = new wards(id, name, scounty_id);

                        wardss.add(newServiceUnit);
                        wardsList.add(newServiceUnit.getName());
                    }

                    wardss.add(new wards(0, "", 0));
                    wardsList.add("");

                    ArrayAdapter<String> aa = new ArrayAdapter<String>(UPIUpdateActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            wardsList) {
                        @Override
                        public int getCount() {
                            return super.getCount(); // you dont display last item. It is used as hint.
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    rankSpinner.setAdapter(aa);

                    rankSpinner.setSelection(aa.getCount() - 1);
                   // rankSpinner.setSelection(wardID - 1);
                    wardID = wardss.get(aa.getCount() - 1).getId();

                    rankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        //@Overide
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                            // Toast.makeText(Registration.this, "null selected", Toast.LENGTH_LONG).show();
                            wardID = wardss.get(position).getId();

//                                Toast.makeText(context,facilityDepartments.get(position).getDepartment_name(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(Registration.this, "cant get", Toast.LENGTH_LONG).show();


                if (error instanceof NetworkError) {
                    Toast.makeText(UPIUpdateActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UPIUpdateActivity.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UPIUpdateActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
                error.printStackTrace();
                getWards(scountyID);
               // getWards(scounty_code1);
                //getDepartments(countyID);
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Authorization", loggedInUser.getToken_type()+" "+loggedInUser.getAccess_token());

                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //AppController.getInstance().addToRequestQueue(jsonObjReq);
        rq.add(jsonArrayRequest);


    }

    //getcounties of birth
    public void getcountiesbirth() {
        String curl = "https://ushauriapi.kenyahmis.org/locator/counties";


        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {

        }


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                z+ Config.COUNTIES, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {


                try {


                    countiessb = new ArrayList<counties>();
                    countiesListb = new ArrayList<String>();

                    countiessb.clear();
                    countiesListb.clear();


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject service = (JSONObject) response.get(i);


                        int id = service.has("id") ? service.getInt("id") : 0;
                        String name = service.has("name") ? service.getString("name") : "";
                        int code = service.has("code") ? service.getInt("code") : 0;


                        counties newCounty = new counties(id, name, code);

                        countiessb.add(newCounty);
                        countiesListb.add(newCounty.getName());
                    }



                    countiessb.add(new counties(0, "", 0));
                    countiesListb.add("");



                    ArrayAdapter<String> aa = new ArrayAdapter<String>(UPIUpdateActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            countiesListb) {
                        @Override
                        public int getCount() {
                            return super.getCount(); // you dont display last item. It is used as hint.
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // if (ServiceSpinner != null){
                    birthSpinner.setAdapter(aa);
                    birthSpinner.setSelection(aa.getCount() - 1);

                    countyIDb = countiessb.get(aa.getCount() - 1).getId();

                    birthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                            // serviceUnitSpinner.setAdapter(null);

                            countyIDb = countiessb.get(position).getId();


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {


                        }
                    });

                }


                //}

                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UPIUpdateActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(Registration.this, " cant get services", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                getcountiesbirth();
            }
        }
        ) {

            /**
             * Passing some request headers
             */
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Authorization", loggedInUser.getToken_type()+" "+loggedInUser.getAccess_token());
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

        };


        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //AppController.getInstance().addToRequestQueue(jsonObjReq);
        rq.add(jsonArrayRequest);

}
public  void update1(){

   // String cccS = cccE.getText().toString();
    String fileserialS = cfile.getText().toString();
   // String upnS = upnE.getText().toString();
    String f_nameS = f_name.getText().toString();
    String s_nameS = s_name.getText().toString();
    String o_nameS =  o_name.getText().toString();
    String dobS = dob.getText().toString();
    String enrollmentS = enrollment_date.getText().toString();
    String art_dateS = art_date.getText().toString();
   String phoneS = phone.getText().toString();


  //  String newupns = AppendFunction.AppendUniqueIdentifier(upnS);
   // String myccnumber = cccS + newupns;
    String country ="KE";
    String countyIDB="029";


    String sendSms = Cno + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + newUpi + "*" + birth_cert_no + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" +-1 + "*" + -1 + "*" + -1 + "*" + -1+ "*" + -1 + "*" + -1 + "*" + -1 + "*" + -1+ "*" + -1 + "*" + country +"*" + countyIDB +"*"+countyID + "*" + scountyID + "*" + -1 + "*" + wardID + "*" + -1;
    //String sendSms = Cno + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + newUpi + "*" + birth_cert_no + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + -1 + "*" + -1 + "*" + -1 + "*" + -1+ "*" + -1 + "*" + -1 + "*" + -1 + "*" + -1+ "*" + -1 + "*" + -1 +"*" +-1 +"*"+countyID + "*" + scountyID + "*" + -1 + "*" + wardID + "*" + -1;

   // String sendSms = "" + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + upi_no + "*" + birth_cert_no + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + "" + "*" + "" + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code + "*" + new_grouping_code + "*" + ""+"*" +countyIDb+"*"+countyID + "*" + scountyID + "*" + locatorlocationS + "*" + wardID + "*" + locatorvillageS;
    String encrypted = Base64Encoder.encryptString(sendSms);

    Log.d("ENCRYPTEDDDDDDDDDDDD",sendSms);


    String mynumber = Config.mainShortcode;
    try {

        List<Mflcode> myl = Mflcode.findWithQuery(Mflcode.class, "select * from Mflcode limit 1");
        mflcode = "";

        for (int x = 0; x < myl.size(); x++) {

            mflcode = myl.get(x).getMfl();

        }
        // mfl_code.setText(mflcode);

    } catch (Exception e) {

        Toast.makeText(this, "error occured populating mflcode", Toast.LENGTH_SHORT).show();
    }

    if (chkinternet.isInternetAvailable()) {
        List<Activelogin> myl = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
        for (int x = 0; x < myl.size(); x++) {

            String un = myl.get(x).getUname();
            List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
            for (int y = 0; y < myl2.size(); y++) {

                String phne = myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                // acs.requestUPI("Reg*" + encrypted, "13023");
                //BEGIN UPI REQUEST


                try {
                    List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                    if (_url.size() == 1) {
                        for (int bb = 0; bb < _url.size(); bb++) {
                            z = _url.get(bb).getBase_url1();
                        }
                    }

                } catch (Exception e) {

                }
                //pr.showProgress("Requesting UPI...");
                final int[] mStatusCode = new int[1];

                String url ="https://ushauriapi.kenyahmis.org/mohupi/getupdateUPI";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                if(mStatusCode[0]==200){

                                    //dialogs.showSuccessDialog(response,"Server Response");

                                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(UPIUpdateActivity.this);
                                    builder1.setIcon(android.R.drawable.ic_dialog_alert);
                                    builder1.setTitle("Client's updated");
                                    builder1.setMessage( "Server Response");
                                    builder1.setCancelable(false);

                                    builder1.setPositiveButton(
                                            "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    Intent intent = new Intent(UPIUpdateActivity.this, UPIErrorList.class);
                                                    UPIUpdateActivity.this.startActivity(intent);


                                                    //dialog.cancel();
                                                }
                                            });



                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();

                                }

                                else{

                                    dialogs.showErrorDialog(response.toString(),"Server response");
                                }



                                Toast.makeText(UPIUpdateActivity.this, "Success", Toast.LENGTH_SHORT).show();

                                Log.d("qwsdfghjnm,", response.toString());




                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                /*dialogs.showErrorDialog( error.toString(), "Server response");
                                //Toast.makeText(UPIUpdateActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("errrro string", error.toString());
                                Log.d("CCCCNO",Cno);*/

                                if (error == null || error.networkResponse == null) {
                                    return;
                                }

                                String body;
                                //get status code here
                                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                                //get response body and parse with appropriate encoding
                                try {
                                    body = new String(error.networkResponse.data,"UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    // exception
                                }
                              // Log.e("Errrrors", body.3)

                            }
                        }) {


                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        mStatusCode[0] = response.statusCode;
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected VolleyError parseNetworkError(VolleyError volleyError) {
                        return super.parseNetworkError(volleyError);
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("reg_payload", "Reg*" + encrypted);
                        params.put("user_mfl", mflcode);

                        return params;
                    }

                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        800000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(UPIUpdateActivity.this);
                requestQueue.add(stringRequest);

            }

        }


    } else {


    }

}

    private void updateUserDetails() {
        // String cccS = cccE.getText().toString();
        String fileserialS = cfile.getText().toString();
        // String upnS = upnE.getText().toString();
        String f_nameS = f_name.getText().toString();
        String s_nameS = s_name.getText().toString();
        String o_nameS =  o_name.getText().toString();
        String dobS = dob.getText().toString();
        String enrollmentS = enrollment_date.getText().toString();
        String art_dateS = art_date.getText().toString();
        String phoneS = phone.getText().toString();

        String country ="KE";
        String countyIDB="029";


        String sendSms = Cno + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + newUpi + "*" + birth_cert_no + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" +-1 + "*" + -1 + "*" + -1 + "*" + -1+ "*" + -1 + "*" + -1 + "*" + -1 + "*" + -1+ "*" + -1 + "*" + country +"*" + countyIDB +"*"+countyID + "*" + scountyID + "*" + -1 + "*" + wardID + "*" + -1;

        // String sendSms = "" + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + upi_no + "*" + birth_cert_no + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + "" + "*" + "" + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code + "*" + new_grouping_code + "*" + ""+"*" +countyIDb+"*"+countyID + "*" + scountyID + "*" + locatorlocationS + "*" + wardID + "*" + locatorvillageS;
        String encrypted = Base64Encoder.encryptString(sendSms);

        Log.d("NON-ENCRYPTED",sendSms);
        Log.d("ENCRYPTED",encrypted);

        JSONObject jsonObject = new JSONObject();
        try {


            jsonObject.put("reg_payload", "Reg*" + encrypted);
            jsonObject.put("user_mfl", "13738");



        } catch (JSONException e) {
            e.printStackTrace();
        }
        final int[] mStatusCode = new int[1];
       // AndroidNetworking.initialize(getApplicationContext(), myUnsafeHttpClient());
        AndroidNetworking.post("https://ushauriapi.kenyahmis.org/mohupi/getupdateUPI")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
                        if(mStatusCode[0]==200){

                            //dialogs.showSuccessDialog(response,"Server Response");

                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(UPIUpdateActivity.this);
                            builder1.setIcon(android.R.drawable.ic_dialog_alert);
                            builder1.setTitle("Client's updated");
                            builder1.setMessage( "Server Response");
                            builder1.setCancelable(false);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            Intent intent = new Intent(UPIUpdateActivity.this, UPIErrorList.class);
                                            UPIUpdateActivity.this.startActivity(intent);


                                            //dialog.cancel();
                                        }
                                    });



                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }

                        else{

                            dialogs.showErrorDialog(response.toString(),"Server response");
                        }


                        //Toast.makeText(UPIUpdateActivity.this, "success", Toast.LENGTH_SHORT).show();
                        Log.e("response", response.toString());


                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(UPIUpdateActivity.this, "error"+error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        Log.d("messss", error.getMessage());

                       // Snackbar.make(root.findViewById(R.id.frag_update_user), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();
                    }
                });

    }
    private void populateMflCode() {

        try {

            List<Mflcode> myl = Mflcode.findWithQuery(Mflcode.class, "select * from Mflcode limit 1");
           mflcode = "";

            for (int x = 0; x < myl.size(); x++) {

                mflcode = myl.get(x).getMfl();

            }
           // mfl_code.setText(mflcode);

        } catch (Exception e) {

            Toast.makeText(this, "error occured populating mflcode", Toast.LENGTH_SHORT).show();
        }
    }
   /* private OkHttpClient myUnsafeHttpClient() {
        try {

            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {

                    new X509TrustManager() {

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) { }
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            //Using TLS 1_2 & 1_1 for HTTP/2 Server requests
            // Note : Please change accordingly
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)
                    .build();

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.connectionSpecs(Collections.singletonList(spec));
            builder.hostnameVerifier((hostname, session) -> true);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

}

