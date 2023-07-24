package com.mhealth.nishauri.utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
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
import butterknife.Unbinder;

public class SelectSurvey extends AppCompatActivity {


    private auth loggedInUser;
    private activeSurveyAdapter mAdapter;
    private ActiveSurveys activeSurveys;
    private ArrayList<ActiveSurveys> activeSurveysArrayList;

    ShimmerFrameLayout shimmer_my_container;

    RecyclerView recyclerView;

    LinearLayout no_active_survey_lyt;

    LinearLayout error_lyt;

    Button btn_back;

    Button btn_select_survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_survey);

        shimmer_my_container = findViewById(R.id.shimmer_my_container);
        recyclerView = findViewById(R.id.recyclerView);
        no_active_survey_lyt = findViewById(R.id.no_active_survey_lyt);
        error_lyt = findViewById(R.id.error_lyt);
         btn_back= findViewById(R.id.btn_back);
         btn_select_survey= findViewById(R.id.btn_select_survey);

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN2, auth.class);

        activeSurveysArrayList = new ArrayList<>();
        mAdapter = new activeSurveyAdapter(SelectSurvey.this, activeSurveysArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(SelectSurvey.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);

        loadActiveSurveys();


        mAdapter.setOnItemClickListener(new activeSurveyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                ActiveSurveys clickedItem = activeSurveysArrayList.get(position);

                Intent intent = new Intent(SelectSurvey.this, LastConsent.class);
                intent.putExtra("questionnaire", clickedItem);
                startActivity(intent);
                Toast.makeText(SelectSurvey.this, "selected", Toast.LENGTH_SHORT).show();

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



    private void loadActiveSurveys() {

        String auth_token = loggedInUser.getAuth_token();


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
}