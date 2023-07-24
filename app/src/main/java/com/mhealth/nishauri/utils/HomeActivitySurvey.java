package com.mhealth.nishauri.utils;

import android.content.Intent;
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
import com.mhealth.nishauri.HomeFragmentSurvey;
import com.mhealth.nishauri.Models.ActiveSurveys;
import com.mhealth.nishauri.Models.auth;
import com.mhealth.nishauri.R;
import com.mhealth.nishauri.adapters.activeSurveyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_survey);



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
                    Toast.makeText(HomeActivitySurvey.this, "survey", Toast.LENGTH_SHORT).show();
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

}
