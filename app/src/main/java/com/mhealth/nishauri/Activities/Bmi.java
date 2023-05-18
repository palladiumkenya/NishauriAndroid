package com.mhealth.nishauri.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Bmi extends AppCompatActivity {

    private Button btn_BMI;
    private TextView bmi, comment2;
    private Toolbar toolbar;

    private TextInputEditText weightE;
    private TextInputEditText heightE;
    AlertDialog.Builder builder;
    private User loggedInUser;

    String z;

    // private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);



        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BMI Calculator");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Bmi.this, MainActivity.class);
                startActivity(intent);
            }
        });

       // loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        btn_BMI = findViewById(R.id.btn_bmi);
         bmi = (TextView) findViewById(R.id.bmi);
        comment2 = (TextView) findViewById(R.id.comment1);
//        forgot_password = findViewById(R.id.tv_forgot_password);

        weightE = (TextInputEditText) findViewById(R.id.edtxt_weight);
        heightE = (TextInputEditText) findViewById(R.id.edtxt_height);

        btn_BMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(weightE.getText().toString().equals("")){
                    Snackbar.make(findViewById(R.id.bmiL), "Please enter your weight", Snackbar.LENGTH_LONG).show();
                }else if(heightE.getText().toString().equals("")){
                    Snackbar.make(findViewById(R.id.bmiL), "Please enter your height", Snackbar.LENGTH_LONG).show();
                }


                getBMI();

            }
        });


    }
    public void getBMI(){
       // String auth_token = loggedInUser.getAuth_token();

        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);
        String auth_token = loggedInUser.getAuth_token();
        String urls ="?user_id="+auth_token;


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("weight", weightE.getText().toString());
            jsonObject.put("height", heightE.getText().toString());
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
        AndroidNetworking.post(z+Constants.BMI+urls)
                //.addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setMaxAgeCacheControl(300000, TimeUnit.MILLISECONDS)
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(Bmi.this, "Your BMI is "+response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject1 = response.getJSONObject("msg");
                          String  bmi1 = jsonObject1.getString("bmi");
                           String comment = jsonObject1.getString("comment");


                           // int d = response.getInt("bmi");

                            bmi.setText("Your BMI is"+ " "+bmi1);
                            comment2.setText(comment);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // do anything with response


                    }



                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(Bmi.this, "error"+error.getErrorDetail(), Toast.LENGTH_SHORT).show();
                       // Log.d("server error", error.getErrorDetail());
                        // handle error
                    }
                });
    }
}