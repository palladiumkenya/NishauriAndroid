package com.mhealthkenya.psurvey.activities.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.os.Build;
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
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.MainActivity;
import com.mhealthkenya.psurvey.depedancies.Constants;

import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.auth;

import org.json.JSONException;
import org.json.JSONObject;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;

public class LoginActivity extends AppCompatActivity {


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

        LoginActivity loginActivity =new LoginActivity();
        //loginActivity.
        setContentView(R.layout.activity_login);


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
               // dialogs.showErrorDialog("System not selected", "Please select the system to connect to");
                Toast.makeText(LoginActivity.this, "You are not connected to", Toast.LENGTH_LONG).show();

            }else{
           // Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
           // connect.setText(zz);
            }
            //connect.setTextColor(Color.parseColor("#F32013"));}

        }catch (Exception e){
            Log.d("No baseURL", e.getMessage());
        }

        Updateapp();


        Stash.init(this);
        setContentView(R.layout.activity_login);

        initialise();
        connect.setText(zz);
        connect.setTextColor(Color.parseColor("#F32013"));

        pDialog = new ProgressDialog(LoginActivity.this);
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
                            if (!isConnected(LoginActivity.this)){
                                //Toast.makeText(LoginActivity.this, "Please connect to internet", Toast.LENGTH_LONG).show();
                                Snackbar.make(findViewById(R.id.login_lyt), "Please connect to internet", Snackbar.LENGTH_LONG).show();
                            }
                            if(phoneNumber.getText().toString().equals("")){
                                Snackbar.make(findViewById(R.id.login_lyt), "Please enter phone number", Snackbar.LENGTH_LONG).show();
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

                //pDialog.show();
                //loginRequest();

            }
        });

       /* forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LoginActivity.this, "Forgot Password clicked!", Toast.LENGTH_SHORT).show();

            }
        });*/

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mint = new Intent(LoginActivity.this, SignUpActivity.class);
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

    private void loginRequest() throws KeyManagementException {
        //private void loginRequest() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msisdn", phoneNumber.getText().toString());
            //jsonObject.put("msisdn", "0712311264");
            jsonObject.put("password", password.getText().toString());
            //jsonObject.put("password", "12345678");

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


       // AndroidNetworking.initialize(getApplicationContext(), myUnsafeHttpClient());
        AndroidNetworking.post(z+Constants.LOGIN)
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

                            Stash.put(Constants.AUTH_TOKEN, newUser);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (response.has("auth_token")){

                            Intent mint = new Intent(LoginActivity.this, MainActivity.class);
                            mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mint);


                            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (pDialog != null && pDialog.isShowing()) {
                                pDialog.hide();
                                pDialog.cancel();
                            }

                            Toast.makeText(LoginActivity.this, "Please Try again later!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, String.valueOf(error.getErrorCode()));

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
    public void Updateapp(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        //Checks platform will allow type of update

        appUpdateInfoTask.addOnSuccessListener(result -> {

            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {

                //requestUpdate(result);

                // android.view.ContextThemeWrapper ctw = new android.view.ContextThemeWrapper(this, R.style.Theme_AlertDialog);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                alertDialogBuilder.setTitle("pSurvey");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setMessage("pSurvey Recommends That You Update To The Latest Version");
                alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {

                        try {
                            startActivity(new Intent("android.intent.action.View", Uri.parse("https://play.google.com/store/apps/details?id=com.mhealthkenya.psurvey" + getPackageName())));

                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent("android.intent.action.View", Uri.parse("https://play.google.com/store/apps/details?id=com.mhealthkenya.psurvey" + getPackageName())));

                        }

                    }
                });
                alertDialogBuilder.show();}




        });
    }

}