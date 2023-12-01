package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.adapters.activeSurveyAdapter;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.ActiveSurveys;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.Completed;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.auth;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

public class offlineHome extends AppCompatActivity {

    JSONObject jsonObject;
    private RequestQueue requestQueue;

    int questionnaireId;


    public ArrayList<QuestionnaireEntity> questionnaireEntities;

    QuestionnaireEntity questionnaireEntity2;


    public String z;

    private Unbinder unbinder;
    private View root;
    private Context context;

    private auth loggedInUser;
    private activeSurveyAdapter mAdapter;
    private ArrayList<ActiveSurveys> activeSurveysArrayList;

    TextView txt_name;
    TextView txt_email;
    TextView tv_facility;

    int countS;





    TextView tv_completed_surveys1, tv_active_surveys;

    CardView surveysID1;

    private ProgressDialog pDialog;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_home);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Set to true if you want the dialog to be cancelable
        progressDialog.setIndeterminate(true); // Set to false if you want a progress bar instead of an indeterminate spinner
        progressDialog.show();





        requestQueue = Volley.newRequestQueue(this);
        questionnaireEntities = new ArrayList<>();

        txt_name= findViewById(R.id.tv_name);
        txt_email= findViewById(R.id.tv_email);
        tv_facility = findViewById(R.id.tv_facility);

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);


        tv_completed_surveys1 = findViewById(R.id.tv_completed_surveys);
        try2();
        tv_active_surveys = findViewById(R.id.tv_active_surveys);



       // tv_completed_surveys1.setText("");


        surveysID1 = findViewById(R.id.surveysID);

        surveysID1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(offlineHome.this, Query2.class);
                startActivity(intent);

                Toast.makeText(offlineHome.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });
        loadCurrentUser();
      //  try2();


        try {

            // UrlTable _url = SugarRecord.findById(UrlTable.class, 4);
            //select *from getLastRecord ORDER BY id DESC LIMIT 1;

            List<Completed> _url = Completed.findWithQuery(Completed.class, "SELECT *from Completed ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    countS = _url.get(x).getComplete();

                    Toast.makeText(offlineHome.this, String.valueOf(countS), Toast.LENGTH_LONG).show();
                    Log.d("COMPLETED SURVEYS", String.valueOf(countS));

                 //   Select.from(Completed.class).orderBy("ID DESC").first();


                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        tv_completed_surveys1.setText(String.valueOf(countS));


        //if (countS==0)
        /*countS = getLastIndex();
        tv_completed_surveys1.setText(String.valueOf(countS));*/






        //end
    }

    private int getLastIndex() {
        // Use Sugar ORM's Select to query the last value inserted for index
        Completed lastCompleted = Select.from(Completed.class).orderBy("ID DESC").first();

        if (lastCompleted != null) {
            // If a record is found, return the index value
            return lastCompleted.getComplete();
        } else {
            // Handle the case where no record is found (e.g., set a default value)
            return -1;
        }
    }







    private void loadCurrentUser(){

        String auth_token = loggedInUser.getAuth_token();

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }


        AndroidNetworking.get(z+Constants.CURRENT_USER_DETAILED)
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
                            //tv_active_surveys.setText(activeQuestionnaires);
                            //tv_completed_surveys.setText(completedSurveys);

                            Stash.put(String.valueOf(Constants.MFL_CODE), mflCode);



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

    private void try2(){




        String url = "https://psurveyapitest.kenyahmis.org/api/questions/dep/all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //pDialog.show();
               // pDialog.hide();



                Log.d("ALl", "SUCCESS");
                Log.d("ALl", response.toString());

                for (int i = 0; i < response.length(); i++) {
                    try {

                        jsonObject = response.getJSONObject(i);


                        // Parse the JSON data
                        questionnaireId = jsonObject.getInt("id");
                        String questionnaireName = jsonObject.getString("name");
                        String questionnaireDescription = jsonObject.getString("description");
                        boolean questionnaireIsActive = jsonObject.getBoolean("is_active");
                        String questionnaireCreatedAt = jsonObject.getString("created_at");
                        int questionnaireNumberOfQuestions = jsonObject.getInt("number_of_questions");
                        String questionnaireActiveTill = jsonObject.getString("active_till");
                        String questionnaireTargetApp = jsonObject.getString("target_app");
                        //Log.d("ALl", questionnaireName);

                        // Create and insert the QuestionnaireEntity
                        questionnaireEntity2 = new QuestionnaireEntity();
                        questionnaireEntity2.setId(questionnaireId);
                        questionnaireEntity2.setName(questionnaireName);
                        questionnaireEntity2.setDescription(questionnaireDescription);
                        questionnaireEntity2.setActive(questionnaireIsActive);
                        questionnaireEntity2.setCreatedAt(questionnaireCreatedAt);
                        questionnaireEntity2.setNumberOfQuestions(questionnaireNumberOfQuestions);
                        questionnaireEntity2.setActiveTill(questionnaireActiveTill);
                        questionnaireEntity2.setTargetApp(questionnaireTargetApp);
                        questionnaireEntity2.setResponsesTableName(null); // You may set this as needed
                        questionnaireEntity2.setIsPublished(null); // You may set this as needed
                        questionnaireEntity2.setCreatedBy(14); // Y
                        // ou may set this as needed

                        QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId,questionnaireName, questionnaireDescription, questionnaireCreatedAt, questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);

                        questionnaireEntities.add(questionnaireEntity);




                        // RetrieveQuestionnaire();
                        // myAsyncTask1.execute();

                        //RetrieveQuestionnaire();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //   progressBar.setVisibility(View.GONE);
                tv_active_surveys.setText(String.valueOf(questionnaireEntities.size()));
                progressDialog.dismiss();
               // Toast.makeText(offlineHome.this, "nOMBERSUrveys"+""+String.valueOf(questionnaireEntities.size()), Toast.LENGTH_LONG).show();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d("SURVEYSSS", error.getMessage());

            }
        });
        requestQueue.add(jsonArrayRequest);


    }

}