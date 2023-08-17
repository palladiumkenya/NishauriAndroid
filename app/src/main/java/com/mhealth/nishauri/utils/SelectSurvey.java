package com.mhealth.nishauri.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.mhealth.nishauri.Activities.Auth.LoginActivity;
import com.mhealth.nishauri.Activities.MainActivity;
import com.mhealth.nishauri.Models.ActiveSurveys;
import com.mhealth.nishauri.Models.User;
import com.mhealth.nishauri.Models.auth;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.activeSurveyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

public class SelectSurvey extends AppCompatActivity {
    AppBarConfiguration mAppBarConfiguration;


   // public auth loggedInUser;
    public User loggedInUser;
    public activeSurveyAdapter mAdapter;
    public ActiveSurveys activeSurveys;
    public ArrayList<ActiveSurveys> activeSurveysArrayList;

    ShimmerFrameLayout shimmer_my_container;

    RecyclerView recyclerView;

    LinearLayout no_active_survey_lyt;

    LinearLayout error_lyt;

    Button btn_back;

    Button btn_select_survey;
   // BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

    TextView tv_active_surveys;
    TextView tv_completed_surveys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_survey);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });*/


        /*NavigationView navigationView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);*/

        shimmer_my_container = findViewById(R.id.shimmer_my_container);
        recyclerView = findViewById(R.id.recyclerView);
        no_active_survey_lyt = findViewById(R.id.no_active_survey_lyt);
        error_lyt = findViewById(R.id.error_lyt);
         btn_back= findViewById(R.id.btn_back);
         btn_select_survey= findViewById(R.id.btn_select_survey);
         tv_active_surveys = findViewById(R.id.tv_active_surveys);
         tv_completed_surveys= findViewById(R.id.tv_completed_surveys);

       // loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);
        loggedInUser = (User) Stash.getObject(Constants.AUTH_TOKEN, User.class);

        activeSurveysArrayList = new ArrayList<>();
        mAdapter = new activeSurveyAdapter(SelectSurvey.this, activeSurveysArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(SelectSurvey.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);
        try{
        loadActiveSurveys();
        loadCurrentUser();}catch(Exception e){
            e.printStackTrace();
        }


        mAdapter.setOnItemClickListener(new activeSurveyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                ActiveSurveys clickedItem = activeSurveysArrayList.get(position);


                Bundle bundle = new Bundle();
                bundle.putSerializable("questionnaire", clickedItem);
                Intent intent = new Intent(SelectSurvey.this, LastConsent.class);
                intent.putExtras(bundle);
                startActivity(intent);






                // creating a bundle object
               /* Bundle bundle = new Bundle();
                bundle.putSerializable("questionnaire", String.valueOf(clickedItem));*/

               // Toast.makeText(SelectSurvey.this, "selected", Toast.LENGTH_SHORT).show();

               /* Bundle bundle = new Bundle();
                bundle.putSerializable("questionnaire", clickedItem);
                Navigation.findNavController(root).navigate(R.id.lastConsent2, bundle);*/
               // Toast.makeText(SelectSurvey.this, "selected", Toast.LENGTH_SHORT).show();
                //NavHostFragment.findNavController(SelectSurveyFragment.this).navigate(R.id.nav_patient_consent);
            }
        });

        btn_select_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // NavHostFragment.findNavController(SelectSurveyFragment.this).navigate(R.id.nav_patient_consent);
               // NavHostFragment.findNavController(SelectSurveyFragment.this).navigate(R.id.nav_patient_consent);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  NavHostFragment.findNavController(SelectSurveyFragment.this).navigate(R.id.nav_home);

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void logout(){

        String endPoint = Stash.getString(Constants.AUTH_TOKEN);
        Stash.clearAll();

        Stash.put(Constants.AUTH_TOKEN, endPoint);

        Intent intent = new Intent(SelectSurvey.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }



    private void loadActiveSurveys() {

        String auth_token = loggedInUser.getAuth_token();
        String urls ="?user_id="+auth_token;


       // AndroidNetworking.get("https://psurveyapitest.kenyahmis.org/api/questionnaire/active")
        AndroidNetworking.post("https://ushauriapi.kenyahmis.org/nishauri/getactive_q"+urls)
               // .addHeaders("Authorization","Token "+ auth_token)
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
                        Log.e("Active Surveys", response.toString());

                        activeSurveysArrayList.clear();

                        if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);

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
                                btn_select_survey.setVisibility(View.GONE);
                                btn_back.setVisibility(View.VISIBLE);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        error_lyt.setVisibility(View.VISIBLE);

//                        Log.e(TAG, error.getErrorBody());

                    //    Snackbar.make(root.findViewById(R.id.frag_select_survey), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });
    }

    private void loadCurrentUser(){

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.post("https://ushauriapi.kenyahmis.org/nishauri/getactive_q_list")
               // .addHeaders("Authorization","Token "+ auth_token)
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

                           /* int id = user.has("id") ? user.getInt("id"): 0;
                            String msisdn = user.has("msisdn") ? user.getString("msisdn") : "";
                            String email = user.has("email") ? user.getString("email") : "";
                            String firstName = user.has("f_name") ? user.getString("f_name") : "";
                            String lastName = user.has("l_name") ? user.getString("l_name") : "";
                            JSONObject designation = user.getJSONObject("designation");

                            int designationId = designation.has("id") ? designation.getInt("id"): 0;
                            String designationName = designation.has("name") ? designation.getString("name") : "";

                            JSONObject facility = user.getJSONObject("facility");

                            int facilityId = facility.has("id") ? facility.getInt("id"): 0;
                            int mflCode = facility.has("mfl_code") ? facility.getInt("mfl_code"): 0;
                            String facilityName = facility.has("name") ? facility.getString("name") : "";
                            String county = facility.has("county") ? facility.getString("county") : "";
                            String subCounty = facility.has("sub_county") ? facility.getString("sub_county") : "";*/

                            String activeQuestionnaires = response.has("Active_questionnaires") ? response.getString("Active_questionnaires") : "";
                            String completedSurveys = response.has("Completed_surveys") ? response.getString("Completed_surveys") : "";


                            /*txt_name.setText(firstName + " " + lastName);
                            txt_email.setText(email);
                            tv_facility.setText(facilityName);*/
                            tv_active_surveys.setText(activeQuestionnaires);
                            tv_completed_surveys.setText(completedSurveys);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());
                        Toast.makeText(SelectSurvey.this, "Error: " + error.getErrorBody(), Toast.LENGTH_SHORT).show();

                      //  Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }

}