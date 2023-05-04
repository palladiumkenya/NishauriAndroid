package com.mhealth.nishauri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Activities.Auth.SignUpActivity;
import com.mhealth.nishauri.Activities.MainActivity;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProfileActivity extends AppCompatActivity {

    EditText ccc, upi, first;
    Button btn_sub;
    Toolbar toolbar;

    String userExtra;

    String z;
    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);
        init();
        btn_sub = findViewById(R.id.bt_prof);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userExtra = extras.getString("user_ID");
            // and get whatever type user account id is
        }

        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(ProfileActivity.this, "Profile created", Toast.LENGTH_SHORT).show();

                if (ccc.getText().toString().isEmpty()){
                    Toast.makeText(ProfileActivity.this, "Enter CCC Number", Toast.LENGTH_SHORT).show();

                }else if(first.getText().toString().isEmpty()){
                    Toast.makeText(ProfileActivity.this, "Enter First Name", Toast.LENGTH_SHORT).show();

                }else{


                send(userExtra, ccc.getText().toString(), upi.getText().toString(), first.getText().toString());}

            }
        });
    }
    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Complete profile");

        ccc= findViewById(R.id.ccc_no);
        upi = findViewById(R.id.upi_no);
        first = findViewById(R.id.f_name);



    }

    private void send(String userid, String cc, String up, String fname){
        String auth_token = loggedInUser.getAuth_token();
        String urls2 ="?user_id="+auth_token;

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userid);

            jsonObject.put("ccc_no", cc);
            jsonObject.put("upi_no", up);
            jsonObject.put("firstname", fname);

        } catch (JSONException e) {
            e.printStackTrace();
        }



        AndroidNetworking.post(z+ Constants.SET_program)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .setMaxAgeCacheControl(300000, TimeUnit.MILLISECONDS)
                .addJSONObjectBody(jsonObject) // posting json
                /*.setRetryPolicy(new DefaultRetryPolicy(
                        30000, //for 30 Seconds
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))*/

                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e(TAG, response.toString());+

                        //Toast.makeText(ProfileActivity.this, "Profile created", Toast.LENGTH_SHORT).show();


                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  errors = response.has("error") ? response.getString("error") : "" ;
                            String  errors1 = response.has("msg") ? response.getString("msg") : "" ;


                            if (status){

                                Intent mint = new Intent(ProfileActivity.this, MainActivity.class);
                                Toast.makeText(ProfileActivity.this, "Profile created", Toast.LENGTH_SHORT).show();
                               // mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mint);

                            }
                            else{

                                Toast.makeText(ProfileActivity.this, errors1, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());
                        Toast.makeText(ProfileActivity.this, "errors", Toast.LENGTH_SHORT).show();



                        // Snackbar.make(findViewById(R.id.signup_layout), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        //JSONObject jsonObject = new JSONObject();
                        int  errors = error.getErrorCode();
                        if (errors==400){
                            Toast.makeText(ProfileActivity.this, "null", Toast.LENGTH_SHORT).show();
                           // Snackbar.make(findViewById(R.id.signup_layout1), " Invalid CCC number", Snackbar.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(ProfileActivity.this, "null2", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout1), "Error: " + error.getErrorDetail(), Snackbar.LENGTH_LONG).show();
                        }


                    }
                });



    }
}