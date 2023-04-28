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
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Activities.Auth.SignUpActivity;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PasswordReset extends AppCompatActivity {

    EditText etxt_email1;
    Button btn_reset_password1;

  String  userID1;
   int page;
    String  errors1;

    Toolbar toolbar1;

    String z;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        toolbar1 =findViewById(R.id.toolbarr);
        toolbar1.setTitle("Reset Password");
        setSupportActionBar(toolbar1);

        etxt_email1 =findViewById(R.id.etxt_email);
        btn_reset_password1=findViewById(R.id.btn_reset_password);


        btn_reset_password1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postReset(etxt_email1.getText().toString());

            }
        });
    }

    public void postReset(String userID){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("user_name", userID);


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

        AndroidNetworking.post(z+ Constants.RESET_pwd)
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
//                        Log.e(TAG, response.toString());

                       // animationView.setVisibility(View.GONE);

                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  errors = response.has("error") ? response.getString("error") : "" ;
                            errors1 = response.has("msg") ? response.getString("msg") : "" ;

                            JSONObject jsonObject1 =response.getJSONObject("data");
                            userID1 =jsonObject1.getString("user_id");
                            page = jsonObject1.getInt("page_id");

                           // String encryptedID1 = Base64Encoder.encryptString(userID1);



                            if (status){


                                Intent intent1 =new Intent(PasswordReset.this, otpcodeActivity.class);
                                intent1.putExtra("user_ID", userID1);
                                startActivity(intent1);

                            }
                            else {

                                Toast.makeText(PasswordReset.this, errors1, Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(PasswordReset.this, errors1, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());

                        //animationView.setVisibility(View.GONE);

                        // Snackbar.make(findViewById(R.id.signup_layout), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        //JSONObject jsonObject = new JSONObject();
                        int  errors = error.getErrorCode();
                        if (errors==400){
                            Toast.makeText(PasswordReset.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout), "Invalid OTP Code", Snackbar.LENGTH_LONG).show();
                        }else {

                            Toast.makeText(PasswordReset.this, error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.signup_layout), "Error: " + error.getErrorDetail(), Snackbar.LENGTH_LONG).show();
                        }


                    }
                });


    }
}