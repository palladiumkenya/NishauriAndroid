package com.mhealth.nishauri;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealth.nishauri.Models.ActiveSurveys;
import com.mhealth.nishauri.Models.auth;
import com.mhealth.nishauri.adapters.activeSurveyAdapter;
import com.mhealth.nishauri.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFragmentSurvey extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private auth loggedInUser;
    private activeSurveyAdapter mAdapter;
    private ArrayList<ActiveSurveys> activeSurveysArrayList;

    @BindView(R.id.tv_name)
    TextView txt_name;

    @BindView(R.id.tv_email)
    TextView txt_email;

    @BindView(R.id.tv_facility)
    TextView tv_facility;

    @BindView(R.id.tv_active_surveys)
    TextView tv_active_surveys;

    @BindView(R.id.tv_completed_surveys)
    TextView tv_completed_surveys;

    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.no_active_survey_lyt)
    LinearLayout no_active_survey_lyt;

    @BindView(R.id.error_lyt)
    LinearLayout error_lyt;

    @BindView(R.id.btn_questionnaires)
    Button btn_questionnaire;

    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_home_survey, container, false);

        unbinder = ButterKnife.bind(this, root);
        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN2, auth.class);
        activeSurveysArrayList = new ArrayList<>();
        mAdapter = new activeSurveyAdapter(context, activeSurveysArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);

        loadCurrentUser();
        loadActiveSurveys();

        btn_questionnaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.nav_questionnaire);

            }
        });

        return root;
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
                            JSONObject designation = user.getJSONObject("designation");

                            int designationId = designation.has("id") ? designation.getInt("id"): 0;
                            String designationName = designation.has("name") ? designation.getString("name") : "";

                            JSONObject facility = user.getJSONObject("facility");

                            int facilityId = facility.has("id") ? facility.getInt("id"): 0;
                            int mflCode = facility.has("mfl_code") ? facility.getInt("mfl_code"): 0;
                            String facilityName = facility.has("name") ? facility.getString("name") : "";
                            String county = facility.has("county") ? facility.getString("county") : "";
                            String subCounty = facility.has("sub_county") ? facility.getString("sub_county") : "";

                            String activeQuestionnaires = response.has("Active_questionnaires") ? response.getString("Active_questionnaires") : "";
                            String completedSurveys = response.has("Completed_surveys") ? response.getString("Completed_surveys") : "";


                            txt_name.setText(firstName + " " + lastName);
                            txt_email.setText(email);
                            tv_facility.setText(facilityName);
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

                        Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmer_my_container.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        shimmer_my_container.stopShimmerAnimation();
        super.onPause();
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

                        Snackbar.make(root.findViewById(R.id.frag_home), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                    }
                });
    }
    }
