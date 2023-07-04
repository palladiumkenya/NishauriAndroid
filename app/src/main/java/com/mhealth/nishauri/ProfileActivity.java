package com.mhealth.nishauri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.mhealth.nishauri.Activities.ProfileOTP;
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
    private User loggedInUser;
    String phonenoA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);
        String auth_token = loggedInUser.getAuth_token();
       // String urls2 =auth_token;
        init();
        btn_sub = findViewById(R.id.bt_prof);
        Bundle extras = getIntent().getExtras();

       /* if (extras != null) {
            userExtra = extras.getString("user_ID");
            // and get whatever type user account id is
        }*/

        ccc= findViewById(R.id.ccc_no);
        upi = findViewById(R.id.upi_no);
        first = findViewById(R.id.f_name);




        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               // Toast.makeText(ProfileActivity.this, "Profile created", Toast.LENGTH_SHORT).show();

                if (ccc.getText().toString().isEmpty()){
                    Toast.makeText(ProfileActivity.this, "Enter CCC Number", Toast.LENGTH_SHORT).show();

                }else if(first.getText().toString().isEmpty()){
                    Toast.makeText(ProfileActivity.this, "Enter First Name", Toast.LENGTH_SHORT).show();

                }

                else{


                send(auth_token, ccc.getText().toString(), upi.getText().toString(), first.getText().toString());}

            }
        });
    }
    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Set Program");



    }

    private void send(String userid, String cc, String up, String fname){


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userid);
            jsonObject.put("ccc_no", cc);
            jsonObject.put("upi_no", up);
            jsonObject.put("firstname", fname);

            Log.d("profile", jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }



        AndroidNetworking.post(Constants.ENDPOINT+Constants.VALIDATE_program)
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
                            String  Message = response.has("msg") ? response.getString("msg") : "" ;


                            if (status){
                             JSONObject jsonObject1 = response.getJSONObject("data");
                             phonenoA = jsonObject1.getString("phoneno");

                                Intent mint = new Intent(ProfileActivity.this, ProfileOTP.class);

                               // mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mint.putExtra("cc1",  ccc.getText().toString());
                                mint.putExtra("upi1",  upi.getText().toString());
                                mint.putExtra("first1",  first.getText().toString());
                                mint.putExtra("phoneA",  phonenoA);
                                startActivity(mint); Toast.makeText(ProfileActivity.this, "Profile created"+ " "+Message, Toast.LENGTH_SHORT).show();

                            }
                            else if (!status){

                                Toast.makeText(ProfileActivity.this, Message, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());
                        Toast.makeText(ProfileActivity.this, error.getErrorBody(), Toast.LENGTH_SHORT).show();



                    }
                });



    }
}