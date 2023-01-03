package com.mhealthkenya.psurvey.activities.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.Designation;
import com.mhealthkenya.psurvey.models.Facility;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

public class SignUpActivity extends AppCompatActivity {
    public String z;

    private Toolbar toolbar;
    private Button btn_signup;
    private TextView sign_in;

    private EditText first_name;
    private EditText last_name;
    private EditText email;
    private EditText msisdn;
    private EditText password;
    private EditText re_password;
    private SearchableSpinner designationSpinner;
    private SearchableSpinner facilitySpinner;

    private int facilityID = 0;
    private int designationID = 0;

    ArrayList<String> facilitiesList;
    ArrayList<Facility> facilities;

    ArrayList<String> designationList;
    ArrayList<Designation> designations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initialise();
        initToolbar();
        checkNulls();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUp();

            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mint = new Intent(SignUpActivity.this, LoginActivity.class);
                mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mint);

            }
        });

    }

    private void initialise(){

//        initialization of components

        first_name = (EditText) findViewById(R.id.edtxt_fname);
        last_name = (EditText) findViewById(R.id.edtxt_lname);
        email = (EditText) findViewById(R.id.edtxt_email);
        msisdn = (EditText) findViewById(R.id.edtxt_msisn);
        password = (EditText) findViewById(R.id.edtxt_password);
        re_password = (EditText) findViewById(R.id.edtxt_repassword);
        facilitySpinner = (SearchableSpinner) findViewById(R.id.facility_Spinner);
        designationSpinner = (SearchableSpinner) findViewById(R.id.designation_Spinner);

        btn_signup = findViewById(R.id.btn_sign_up);
        sign_in = (TextView) findViewById(R.id.tv_signup);

        facilitySpinner.setTitle("Select the facility ");
        facilitySpinner.setPositiveButton("OK");
        getFacilities();

        designationSpinner.setTitle("Select your designation ");
        designationSpinner.setPositiveButton("OK");
        getDesignation();

    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkNulls() {

        msisdn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int a, int a1, int a2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int a, int a1, int a2) {

                if (msisdn.length() < 10) {
                    msisdn.setError("Complete your phone number");
                } else {
                    msisdn.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int b, int b1, int b2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int b, int b1, int b2) {
                if (password.length() < 8) {
                    password.setError("Password should be 8 characters long");
                } else {
                    password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        re_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int c, int c1, int c2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int c, int c1, int c2) {
                if (re_password.length() < 8) {
                    re_password.setError("Password should be 8 characters long");
                } else if (!password.getText().toString().equals(re_password.getText().toString())) {
                    re_password.setError(getString(R.string.must_match));

                } else {
                    re_password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void signUp() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("designation", designationID != 0 ? designationID : "");
            jsonObject.put("facility", facilityID != 0 ? facilityID : "");
            jsonObject.put("email", email.getText().toString());
            jsonObject.put("f_name", first_name.getText().toString());
            jsonObject.put("l_name", last_name.getText().toString());
            jsonObject.put("msisdn", msisdn.getText().toString());
            jsonObject.put("password", password.getText().toString());
            jsonObject.put("re_password", re_password.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
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

        AndroidNetworking.post(z+Constants.SIGNUP)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .setMaxAgeCacheControl(300000, TimeUnit.MILLISECONDS)
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e(TAG, response.toString());

                        try {

                            String  success = response.has("id")? response.getString("id") : "";
                            String  errors = response.has("error") ? response.getString("error") : "" ;


                            if (response.has("id")){

                                Intent mint = new Intent(SignUpActivity.this, LoginActivity.class);
                                mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mint);
                                Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();

                            }
                            else{

                                Toast.makeText(SignUpActivity.this, errors, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(findViewById(R.id.signup_lyt), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        /*if (error.getErrorBody().contains("This field may not be blank.")) {

                            Snackbar.make(findViewById(R.id.signup_lyt), "Please fill all fields", Snackbar.LENGTH_LONG).show();
                        }*/
                   // }
                    }
                });

    }

    private void getFacilities() {

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }

        AndroidNetworking.get(z+Constants.ALL_FACILITIES)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.e(TAG, response.toString());
                        //Toast.makeText(SignUpActivity.this, "Sign up successful!"+z, Toast.LENGTH_SHORT).show();

                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  message = response.has("message") ? response.getString("message") : "" ;
                            String  errors = response.has("errors") ? response.getString("errors") : "" ;


                            facilities = new ArrayList<Facility>();
                            facilitiesList = new ArrayList<String>();

                            facilities.clear();
                            facilitiesList.clear();

                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject facility = (JSONObject) jsonArray.get(i);

                                int id = facility.has("id") ? facility.getInt("id") : 0;
                                String mfl_code = facility.has("mfl_code") ? facility.getString("mfl_code") : "";
                                String name = facility.has("name") ? facility.getString("name") : "";
                                String county = facility.has("county") ? facility.getString("county") : "";
                                String sub_county = facility.has("sub_county") ? facility.getString("sub_county") : "";

                                Facility newFacility = new Facility(id,mfl_code,name,county,sub_county);

                                facilities.add(newFacility);
                                facilitiesList.add(newFacility.getName());
                            }

                            facilities.add(new Facility(0,"Select your facility.","Select your facility.","--select--","--select--"));
                            facilitiesList.add("Select your facility.");

                            ArrayAdapter<String> aa=new ArrayAdapter<String>(SignUpActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    facilitiesList){
                                @Override
                                public int getCount() {
                                    return super.getCount(); // you don't display last item. It is used as hint.
                                }
                            };

                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            if (facilitySpinner != null){
                                facilitySpinner.setAdapter(aa);
                                facilitySpinner.setSelection(aa.getCount()-1);

                                facilityID = facilities.get(aa.getCount()-1).getId();

                                facilitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                        facilityID = facilities.get(position).getId();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Snackbar.make(findViewById(R.id.signup_lyt), e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

                        Log.e(TAG, String.valueOf(error.getErrorCode()));

                    }
                });
    }

    private void getDesignation() {

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }


        AndroidNetworking.get(z+Constants.DESIGNATION)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        Log.e(TAG, response.toString());

                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  message = response.has("message") ? response.getString("message") : "" ;
                            String  errors = response.has("errors") ? response.getString("errors") : "" ;


                            designations = new ArrayList<Designation>();
                            designationList = new ArrayList<String>();

                            designations.clear();
                            designationList.clear();

                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject designation = (JSONObject) jsonArray.get(i);

                                int id = designation.has("id") ? designation.getInt("id") : 0;
                                String name = designation.has("name") ? designation.getString("name") : "";


                                Designation newDesignation = new Designation(id,name);

                                designations.add(newDesignation);
                                designationList.add(newDesignation.getName());
                            }

                            designations.add(new Designation(0,"Select your designation."));
                            designationList.add("Select your designation.");

                            ArrayAdapter<String> aa=new ArrayAdapter<String>(SignUpActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    designationList){
                                @Override
                                public int getCount() {
                                    return super.getCount(); // you don't display last item. It is used as hint.
                                }
                            };

                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            if (designationSpinner != null){
                                designationSpinner.setAdapter(aa);
                                designationSpinner.setSelection(aa.getCount()-1);

                                designationID = designations.get(aa.getCount()-1).getId();

                                designationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                        designationID = designations.get(position).getId();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Snackbar.make(findViewById(R.id.signup_lyt), e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }


                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

                        Log.e(TAG, String.valueOf(error.getErrorCode()));

                    }
                });
    }


}