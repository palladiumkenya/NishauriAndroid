package com.mhealth.nishauri.Activities.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking
        .error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mhealth.nishauri.Activities.MainActivity;
import com.mhealth.nishauri.Models.UrlTable;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.PasswordReset;
import com.mhealth.nishauri.ProfileActivity;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.otpcodeActivity;
import com.mhealth.nishauri.utils.Constants;
import com.mhealth.nishauri.utils.Dialogs;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.mhealth.nishauri.utils.AppController.TAG;


public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private Toolbar toolbar;
    private TextView sign_up;
    private TextView reset1;
    private EditText phone;
    private EditText password;

    private TextInputLayout til_phone_no;
    private TextInputLayout til_password;
    private LottieAnimationView animationView;

   String errors1;
    Dialogs dialogs;

    String z, zz;


   String userID1;
  int  page;

  TextView connect;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stash.init(this);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Nishauri");
        setSupportActionBar(toolbar);

//        initialization of componentsg
        btn_login = findViewById(R.id.btn_login);
        sign_up = (TextView) findViewById(R.id.txt_sign_up);
        phone = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        til_phone_no = findViewById(R.id.til_phone_no);
        til_password = findViewById(R.id.til_pass);
        animationView = findViewById(R.id.animationView);

        reset1 = (MaterialTextView) findViewById(R.id.txt_reset);

        connect =findViewById(R.id.connected_to);

        try {

            // UrlTable _url = SugarRecord.findById(UrlTable.class, 4);
            //select *from getLastRecord ORDER BY id DESC LIMIT 1;

            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                    zz=_url.get(x).getStage_name1();
                    Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
                }
            }

            //UrlTable _url = SugarRecord.findById(UrlTable.class, 1);

            // z= _url.base_url1;
            // zz =_url.stage_name1;
            if (zz==null){
                dialogs.showErrorDialog("System not selected", "Please select the system to connect to");

            }
            Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
            connect.setText(zz);
            connect.setTextColor(Color.parseColor("#F32013"));

        }catch (Exception e){
            Log.d("No baseURL", e.getMessage());
        }





        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int a, int a1, int a2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int a, int a1, int a2) {

                if (phone.length() < 10) {
                    til_phone_no.setError("Complete your phone number");
                } else {
                    til_phone_no.setError(null);
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
                    til_password.setError("Password should be 8 characters long");
                } else {
                    til_password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mint = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(mint);

            }
        });

        reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mint = new Intent(getApplicationContext(), PasswordReset.class);
                startActivity(mint);

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (TextUtils.isEmpty(phone.getText().toString())){

                   Snackbar.make(findViewById(R.id.login_layout), "Please enter your phone number", Snackbar.LENGTH_LONG).show();
               }
               else if (TextUtils.isEmpty(password.getText().toString())) {

                   Snackbar.make(findViewById(R.id.login_layout), "Please enter your password", Snackbar.LENGTH_LONG).show();
               }
               else {

                   sendLoginRequest(password.getText().toString(),phone.getText().toString());
                   animationView.setVisibility(View.VISIBLE);

               }

            }
        });


    }


    private void sendLoginRequest(String password, String phone) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password);
            jsonObject.put("user_name", phone);
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


        AndroidNetworking.post(z+Constants.SIGNIN)
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
                        // do anything with response

//                        Log.e(TAG, response.toString());
                        try {

                            boolean status = response.has("success") && response.getBoolean("success");
                            String errors = response.has("error") ? response.getString("error") : "";
                            errors1 = response.has("msg") ? response.getString("msg") : "";

                            JSONObject jsonObject1 = response.getJSONObject("data");
                            userID1 = jsonObject1.getString("user_id");
                            page = jsonObject1.getInt("page_id");

                            // String encryptedID1 = Base64Encoder.encryptString(userID1);

                           /* auth newUser = new auth(access_token);
                            Stash.put(Constants.AUTH_TOKEN, newUser);*/

                           // String auth_token = response.has("auth_token") ? response.getString("auth_token") : "";
                            User newUser = new User(userID1);
                            Stash.put(Constants.AUTH_TOKEN, newUser);



                            if (status && !(page==0)) {


                                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                                 intent1.putExtra("user_ID", userID1);
                                startActivity(intent1);

                            }else if (status && page==0){

                                Intent intent1 = new Intent(LoginActivity.this, ProfileActivity.class);
                                intent1.putExtra("user_ID", userID1);
                                startActivity(intent1);


                            }



                            else {

                                Toast.makeText(LoginActivity.this, errors1, Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(LoginActivity.this, errors1, Toast.LENGTH_LONG).show();



                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, String.valueOf(error.getErrorCode()));

                        animationView.setVisibility(View.GONE);

                        if (error.getErrorBody().contains("Unable to log in with provided credentials.")){

                            Snackbar.make(findViewById(R.id.login_layout), "Invalid phone number or password." , Snackbar.LENGTH_LONG).show();

                        }
                        else if (error.getErrorCode()==0){

                            Snackbar.make(findViewById(R.id.login_layout), "Please retry later...", Snackbar.LENGTH_LONG).show();

                        }
                        else if(error.getErrorCode() == 500){

                            Snackbar.make(findViewById(R.id.login_layout), "Internal Server Error. Please retry later!", Snackbar.LENGTH_LONG).show();

                        }
                        else {

                            Snackbar.make(findViewById(R.id.login_layout), "" + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        }


                    }
                });

    }

}
