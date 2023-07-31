package com.mhealth.nishauri.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Activities.Auth.LoginPsurvey;
import com.mhealth.nishauri.Fragments.Chat.ChatFragment;
import com.mhealth.nishauri.HomeFragmentSurvey;
import com.mhealth.nishauri.Models.ActiveSurveys;
import com.mhealth.nishauri.Models.auth;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.activeSurveyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomeActivitySurvey extends AppCompatActivity {
    private auth loggedInUser;
    private activeSurveyAdapter mAdapter;
    private ArrayList<ActiveSurveys> activeSurveysArrayList;

    TextView txt_name;

    TextView txt_email;

    TextView tv_facility;
    TextView tv_active_surveys;
    TextView tv_completed_surveys;

    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    /*@BindView(R.id.recyclerView)
    RecyclerView recyclerView;*/

    @BindView(R.id.no_active_survey_lyt)
    LinearLayout no_active_survey_lyt;

    @BindView(R.id.error_lyt)
    LinearLayout error_lyt;

    @BindView(R.id.btn_questionnaires)
    Button btn_questionnaire;


    BottomNavigationView bottomNavigationView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

       // Intent intent = new Intent(HomeActivitySurvey.this, ChatFragment.class);
        //startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_survey);
       // loginRequest();



         txt_name = findViewById(R.id.tv_name);
         txt_email = findViewById(R.id.tv_email);
        tv_facility= findViewById(R.id.tv_facility);
        bottomNavigationView =findViewById(R.id.bottomNavigationView);


        tv_active_surveys = findViewById(R.id.tv_active_surveys);
        tv_completed_surveys = findViewById(R.id.tv_completed_surveys);;

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN2, auth.class);
        activeSurveysArrayList = new ArrayList<>();
        mAdapter = new activeSurveyAdapter(HomeActivitySurvey.this, activeSurveysArrayList);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.select_survey1){

                    Intent intent =new Intent(HomeActivitySurvey.this, SelectSurvey.class);
                    startActivity(intent);
                   // Toast.makeText(HomeActivitySurvey.this, "survey", Toast.LENGTH_SHORT).show();
                }
                else if (item.getItemId()==R.id.nav_home){
                    logout();

                    Intent intent =new Intent(HomeActivitySurvey.this, ChatFragment.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        /*recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivitySurvey.this,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);*/

        loadCurrentUser();
       // loadActiveSurveys();

      /*  btn_questionnaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.nav_questionnaire);

            }
        });*/

       // return root;
    }



    private void loadCurrentUser(){

        String auth_token = loggedInUser.getAuth_token();



        AndroidNetworking.get("https://psurveyapitest.kenyahmis.org/api/current/user")
                .addHeaders("Authorization","Token "+ auth_token)
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
//                        Log.e(TAG, response.toString());

                        try {

                            JSONObject user = response.getJSONObject("user");

                            int id = user.has("id") ? user.getInt("id"): 0;
                            String msisdn = user.has("msisdn") ? user.getString("msisdn") : "";
                            String email = user.has("email") ? user.getString("email") : "";
                            String firstName = user.has("f_name") ? user.getString("f_name") : "";
                            String lastName = user.has("l_name") ? user.getString("l_name") : "";
                            //JSONObject designation = user.getJSONObject("designation");

                           // int designationId = designation.has("id") ? designation.getInt("id"): 0;
                           // String designationName = designation.has("name") ? designation.getString("name") : "";

                            //*JSONObject facility = user.getJSONObject("facility");

                           /* int facilityId = facility.has("id") ? facility.getInt("id"): 0;
                            int mflCode = facility.has("mfl_code") ? facility.getInt("mfl_code"): 0;
                            String facilityName = facility.has("name") ? facility.getString("name") : "";
                            String county = facility.has("county") ? facility.getString("county") : "";
                            String subCounty = facility.has("sub_county") ? facility.getString("sub_county") : "";*/

                            String activeQuestionnaires = response.has("Active_questionnaires") ? response.getString("Active_questionnaires") : "";
                            String completedSurveys = response.has("Completed_surveys") ? response.getString("Completed_surveys") : "";


                            txt_name.setText(firstName + " " + lastName);
                            txt_email.setText(email);
                           // tv_facility.setText(facilityName);
                            tv_active_surveys.setText(activeQuestionnaires);
                            tv_completed_surveys.setText(completedSurveys);
                            Log.d("active", activeQuestionnaires);
                            Log.d("completed", completedSurveys);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());
                        Toast.makeText(HomeActivitySurvey.this, error.getErrorBody(), Toast.LENGTH_SHORT).show();

                      //  Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }






    private void loadActiveSurveys() {

        String auth_token = loggedInUser.getAuth_token();


        //ACTIVE SURVEYS
        AndroidNetworking.get("https://psurveyapitest.kenyahmis.org/api/questionnaire/active")
                .addHeaders("Authorization","Token "+ auth_token)
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
                        // Log.e(TAG, response.toString());

                        activeSurveysArrayList.clear();

                      /*  if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);*/

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        try {

                            JSONArray myArray = response.getJSONArray("data");

                            if (myArray.length() > 0){


                                for (int i = 0; i < myArray.length(); i++) {

                                    JSONObject item = (JSONObject) myArray.get(i);


                                    int  id = item.has("id") ? item.getInt("id") : 0;
                                    String survey_title = item.has("name") ? item.getString("name") : "";
                                    String survey_description = item.has("description") ? item.getString("description") : "";
                                    String status = item.has("is_active") ? item.getString("is_active") : "";
                                    String created_at = item.has("created_at") ? item.getString("created_at") : "";
                                    String active_till = item.has("active_till") ? item.getString("active_till") : "";
                                    int  created_by = item.has("created_by") ? item.getInt("created_by") : 0;


                                    ActiveSurveys newActiveSurvey = new ActiveSurveys(id,survey_title,survey_description,status,created_at,active_till,created_by);

                                    activeSurveysArrayList.add(newActiveSurvey);
                                    mAdapter.notifyDataSetChanged();

                                }

                            }else {
                                //not data found
                                no_active_survey_lyt.setVisibility(View.VISIBLE);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                       /* if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);*/

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        error_lyt.setVisibility(View.VISIBLE);

//                        Log.e(TAG, error.getErrorBody());
                        Toast.makeText(HomeActivitySurvey.this, error.getErrorBody(), Toast.LENGTH_SHORT).show();

                      //  Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });
    }
    //login
    private void loginRequest() {
        //private void loginRequest() {

        JSONObject jsonObject = new JSONObject();
        try {
            // jsonObject.put("msisdn", phoneNumber.getText().toString());
            jsonObject.put("msisdn", "0700000000");
            // jsonObject.put("password", password.getText().toString());
            jsonObject.put("password", "0987654321");

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

                       /* if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog.cancel();
                        }*/

                        try {
                            String auth_token = response.has("auth_token") ? response.getString("auth_token") : "";
                            auth newUser = new auth(auth_token);

                            Stash.put(Constants.AUTH_TOKEN2, newUser);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (response.has("auth_token")){

                           // Intent mint = new Intent(LoginPsurvey.this, HomeActivitySurvey.class);
                            // Intent mint = new Intent(LoginPsurvey.this, HomeFragmentSurvey.class);
                            // mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           // startActivity(mint);


                            Toast.makeText(HomeActivitySurvey.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            /*if (pDialog != null && pDialog.isShowing()) {
                                pDialog.hide();
                                pDialog.cancel();
                            }*/

                            Toast.makeText(HomeActivitySurvey.this, "Please Try again later!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("error", String.valueOf(error.getErrorCode()));

                      /*  if (pDialog != null && pDialog.isShowing()) {
                            pDialog.hide();
                            pDialog.cancel();
                        }*/

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

    public void logout(){

        String auth_token = loggedInUser.getAuth_token();

        AndroidNetworking.post("https://psurveyapitest.kenyahmis.org/auth/token/logout/")
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.e("Error", response.toString());



                        try {
                            boolean  status = response.has("success") && response.getBoolean("success");
                            String error = response.has("error") ? response.getString("error") : "";
                            String message = response.has("message") ? response.getString("message") : "";

                            if (status){

                                String endPoint = Stash.getString(Constants.AUTH_TOKEN);
                                Stash.clearAll();
                                Stash.put(Constants.AUTH_TOKEN, endPoint);

                                Intent intent = new Intent(HomeActivitySurvey.this, ChatFragment.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();


                            }else if (!status){

                                Snackbar.make(findViewById(R.id.drawer_layout), message, Snackbar.LENGTH_LONG).show();

                            }
                            else{

                                Snackbar.make(findViewById(R.id.drawer_layout), error, Snackbar.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("Logout Error", String.valueOf(error.getErrorCode()));


                        if (error.getErrorCode() == 0){

                            String endPoint = Stash.getString(Constants.AUTH_TOKEN);
                            Stash.clearAll();
                            Stash.put(Constants.AUTH_TOKEN, endPoint);

                            Intent intent = new Intent(HomeActivitySurvey.this, ChatFragment.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                        else{

                            Toast.makeText(HomeActivitySurvey.this, ""+error.getErrorBody(), Toast.LENGTH_SHORT).show();


                        }

                    }
                });


    }


}
