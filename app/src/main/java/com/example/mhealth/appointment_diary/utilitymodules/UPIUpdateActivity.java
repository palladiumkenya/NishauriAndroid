package com.example.mhealth.appointment_diary.utilitymodules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.models.counties;
import com.example.mhealth.appointment_diary.models.scounties;
import com.example.mhealth.appointment_diary.models.wards;
import com.example.mhealth.appointment_diary.tables.Myaffiliation;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UPIUpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner genderS, maritalS, conditionS, enrollmentS, languageS, smsS, wklymotivation, messageTime, SelectstatusS, patientStatus, GroupingS, orphanS, schoolS, newGroupingS;
    String condition_code, grouping_code, new_grouping_code, category_code, language_code, sms_code,sms_id, Selectstatus_code, wklyMotivation_code, messageTime_code, patientStatus_code, school_code, orphan_code, idnoS, upi_no, birth_cert_no, locatorcountyS, locatorsubcountyS, locatorlocationS, locatorwardS, locatorvillageS, county_code, scounty_code, ward_code;

    String[] genders = {"", "Female", "Male"};
    String[] gendersUcsf = {"", "Female", "Male"};

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
    String newUpi, Idno, Birthno, Enrollment_date, Art_date, Phone;
    Button populate1;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upiupdate);

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
                               // county1 =jsonObject.getString("locator_county");
                               // county_code1 = Integer.parseInt(jsonObject.getString("locator_county"));
                                countyID = Integer.parseInt(jsonObject.getString("locator_county"));
                                scountyID = Integer.parseInt(jsonObject.getString("locator_sub_county"));
                                wardID = Integer.parseInt(jsonObject.getString("locator_ward"));

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
                            countyID=county_code1;
                            scountyID=scounty_code1;
                            wardID=ward_code1;
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
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // if (ServiceSpinner != null){
                    ServiceSpinner.setAdapter(aa);
                   //ServiceSpinner.setSelection(countyID);
                    ServiceSpinner.setSelection(county_code1);
                   // ServiceSpinner.setSelection(aa.getCount() - 1);

                 //  county1=  ServiceSpinner.getSelectedItem().toString();

      //              countyID = countiess.get(aa.getCount() - 1).getId();
       //             county_code1 = countiess.get(aa.getCount() - 1).getId();
                    //countyID = Integer.parseInt(ServiceSpinner.getSelectedItem().toString());

                    ServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                            // serviceUnitSpinner.setAdapter(null);

                          //countyID = countiess.get(position).getId();
                           //countyID =position;
                            county_code1 = countiess.get(position).getId();
                           // county_code1 = position;



                            //getDepartments(services.get(position).getService_id());

//
                                   /* if (serviceID !=0)
                                        Toast.makeText(Registration.this, "getting units", Toast.LENGTH_LONG).show();*/
                            try {
                                //getDepartments(countyID);
                                getDepartments(county_code1);
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
                   // serviceUnitSpinner.setSelection(aa.getCount() - 1);
                   // serviceUnitSpinner.setSelection(scountyID);
                    serviceUnitSpinner.setSelection(scounty_code1);

      //              scountyID = scountiess.get(aa.getCount() - 1).getId();

                    serviceUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        //@Overide
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                            // Toast.makeText(Registration.this, "null selected", Toast.LENGTH_LONG).show();
                  //          scountyID = scountiess.get(position).getId();
                  //          scounty_code1 = scountiess.get(position).getId();
                            scounty_code1 = position;
                           // scountyID = position;
                  //          getWards(scountyID);
                            getWards(scounty_code1);
                            //call wards here


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
                    //rankSpinner.setSelection(aa.getCount() - 1);
        //            rankSpinner.setSelection(wardID);
                    rankSpinner.setSelection(ward_code1);

                    //wardID = wardss.get(aa.getCount() - 1).getId();
          //          wardID = wardss.get(aa.getCount() -1).getId();
                    rankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        //@Overide
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                            // Toast.makeText(Registration.this, "null selected", Toast.LENGTH_LONG).show();
          //                  wardID = wardss.get(position).getId();
                            //wardID = position;
                            ward_code1 = position;

                            //call wards here


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
     //           getWards(scountyID);
                getWards(scounty_code1);
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

}}