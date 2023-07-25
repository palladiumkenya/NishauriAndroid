package com.mhealth.nishauri.Activities.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.mhealth.nishauri.HomeFragmentSurvey;
import com.mhealth.nishauri.Models.auth;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.utils.Constants;
import com.mhealth.nishauri.utils.HomeActivitySurvey;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.util.List;

public class LoginPsurvey extends AppCompatActivity {


    private Button btn_login;
    private TextView sign_up, connect;
    private TextView forgot_password;
    private TextInputEditText phoneNumber;
    private TextInputEditText password;
    private ProgressDialog pDialog;

    public String z, zz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_psurvey);

        //Updateapp();


        Stash.init(this);

        initialise();

        pDialog = new ProgressDialog(LoginPsurvey.this);
        pDialog.setTitle("Signing In...");
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if(phoneNumber.getText().toString().equals("")){

                            }else if(password.getText().toString().equals("")){
                                Snackbar.make(findViewById(R.id.login_lyt), "Please enter password", Snackbar.LENGTH_LONG).show();
                            }
                            pDialog.show();
                            loginRequest();

                        }catch (Exception e){
                            e.printStackTrace();

                        }
                    }
                });


            }
        });


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mint = new Intent(LoginPsurvey.this, SignUpActivity.class);
                mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mint);
            }
        });


    }

    private void initialise(){

//        initialization of components

        btn_login = findViewById(R.id.btn_login);
        sign_up = (TextView) findViewById(R.id.tv_sign_up);
        connect =(TextView) findViewById(R.id.connected_to);
//        forgot_password = findViewById(R.id.tv_forgot_password);

        phoneNumber = (TextInputEditText) findViewById(R.id.edtxt_phone_no);
        password = (TextInputEditText) findViewById(R.id.edtxt_pass);


    }

    private void loginRequest() {
        //private void loginRequest() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msisdn", phoneNumber.getText().toString());
           // jsonObject.put("msisdn", "0700000000");
            jsonObject.put("password", password.getText().toString());
            //jsonObject.put("password", "0987654321");

        } catch (JSONException e) {
            e.printStackTrace();
        }




        // AndroidNetworking.initialize(getApplicationContext(), myUnsafeHttpClient());
        AndroidNetworking.post("https://psurveyapitest.kenyahmis.org/auth/token/login")
                .addHeaders("Content-Type", "application.json")
                // .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .addHeaders("Accept", "application/json")
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

//                        Log.e(TAG, response.toString());

                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog.cancel();
                        }

                        try {
                            String auth_token = response.has("auth_token") ? response.getString("auth_token") : "";
                            auth newUser = new auth(auth_token);

                            Stash.put(Constants.AUTH_TOKEN2, newUser);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (response.has("auth_token")){

                            Intent mint = new Intent(LoginPsurvey.this, HomeActivitySurvey.class);
                           // Intent mint = new Intent(LoginPsurvey.this, HomeFragmentSurvey.class);
                           // mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mint);


                            Toast.makeText(LoginPsurvey.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (pDialog != null && pDialog.isShowing()) {
                                pDialog.hide();
                                pDialog.cancel();
                            }

                            Toast.makeText(LoginPsurvey.this, "Please Try again later!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("error", String.valueOf(error.getErrorCode()));

                        if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog.cancel();
                        }

                        if (error.getErrorBody() != null && error.getErrorBody().contains("Unable to log in with provided credentials.")){

                            Snackbar.make(findViewById(R.id.login_lyt), "Invalid phone number or password." , Snackbar.LENGTH_LONG).show();


                        }
                        else {

                            // Snackbar.make(findViewById(R.id.login_lyt), "Error: " + error.getErrorCode(), Snackbar.LENGTH_LONG).show();


                        }


                    }
                });
    }

    /*private OkHttpClient myUnsafeHttpClient() {
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
            };*/

    //Using TLS 1_2 & 1_1 for HTTP/2 Server requests
    // Note : Please change accordingly
          /*  ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
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
                    .build();*/

    // Install the all-trusting trust manager
            /*final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());*/
    // Create an ssl socket factory with our all-trusting manager
            /*final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.connectionSpecs(Collections.singletonList(spec));
            builder.hostnameVerifier((hostname, session) -> true);
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    private  boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo =connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if((wifiInfo !=null && wifiInfo.isConnected())|| (mobileInfo !=null && mobileInfo.isConnected())){
            return true;
        }
        else{
            return false;
        }

    }

}