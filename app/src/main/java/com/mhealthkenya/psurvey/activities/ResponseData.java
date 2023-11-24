package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.adapters.UserResponseAdapter;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.UserResponseEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResponseData extends AppCompatActivity {
    int IDvalue;
    AllQuestionDatabase allQuestionDatabase;


    UserResponseEntity userResponseEntity;

    ArrayList<UserResponseEntity> answerList = new ArrayList<>();

    public ArrayList<UserResponseEntity> userResponseEntities;
    RecyclerView recyclerView1;
    public  UserResponseAdapter adapter;


    List<UserResponseEntity> userResponses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_data);
        // Initialize your RecyclerView



        allQuestionDatabase = AllQuestionDatabase.getInstance(this);
        //userResponseEntities =allQuestionDatabase.userResponseDao().getUserResponsesForQuestionnaire()

         Intent mIntent = getIntent();
        IDvalue = mIntent.getIntExtra("Quetionnaire_ID", 0);


      //  Toast.makeText(ResponseData.this, "ID Is"+IDvalue, Toast.LENGTH_SHORT).show();
        recyclerView1 = findViewById(R.id.recyclerViewResponse);
        userResponseEntities= new ArrayList<>();



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(layoutManager);
        // recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setHasFixedSize(true);
        adapter = new UserResponseAdapter(this);
       // recyclerView1.addItemDecoration(new DividerItemDecoration(recyclerView1.getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView1.getContext(), layoutManager.getOrientation());
               // layoutManager.getOrientation());
        recyclerView1.addItemDecoration(dividerItemDecoration);

        recyclerView1.setAdapter(adapter);

        getResponses1();
        //getResponsesA();
        //recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
       // recyclerView1.setHasFixedSize(true);
        // Create and set the adapter



        // Optionally, set the layout manager (e.g., LinearLayoutManager)
       // recyclerView1.setLayoutManager(new LinearLayoutManager(this));




        //

    }

    public void getResponses1(){
        userResponses = allQuestionDatabase.userResponseDao().getUserResponsesForQuestionnaire(IDvalue);
        //try

        for (UserResponseEntity userResponseEntity:userResponses) {
            Log.d("QuetionnaireID", String.valueOf(userResponseEntity.getQuestionnaireId()));
            Log.d("QuetionID", String.valueOf(userResponseEntity.getQuestionId()));
            Log.d("Option", String.valueOf(userResponseEntity.getOption()));

            int questionnaireId = userResponseEntity.getQuestionnaireId();
            int questionId =  userResponseEntity.getQuestionId();
            String answer = userResponseEntity.getOption();

            // QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId,questionnaireName, questionnaireDescription, questionnaireCreatedAt, questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);
            UserResponseEntity userResponseEntity1 = new UserResponseEntity(questionnaireId, questionId, answer);
            // UserResponseEntity userResponseEntity1 = new UserResponseEntity(userResponseEntity.getQuestionnaireId(), userResponseEntity.getQuestionId(), userResponseEntity.getOption());
            userResponseEntities.add(userResponseEntity1);

            // adapter.setUser(userResponseEntities);
            // adapter.setUser(userResponseEntities);
            adapter.setUser2(userResponseEntities);
        }
        if (userResponseEntities.isEmpty()){
            Toast.makeText(ResponseData.this, "No Responses for this Quetionnaire", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitUserResponseToServer(UserResponseEntity userResponse) {
        // Replace the following URL with your server endpoint
        String url = "submitUserResponse";

        // Create JSONObject with user response data
        JSONObject jsonUserResponse = new JSONObject();
        try {
            jsonUserResponse.put("questionnaireId", userResponse.getQuestionnaireId());
            jsonUserResponse.put("questionId", userResponse.getQuestionId());
            jsonUserResponse.put("answerId", userResponse.getOption());

            // Add other fields as needed
        } catch ( JSONException e) {
            e.printStackTrace();
        }

        // Create JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonUserResponse,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the server response on success
                        Log.d("Volley Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Log.e("Volley Error", "Error submitting user response to server: " + error.toString());
                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void submitUserResponsesToServer() {
        // Iterate through userResponses and submit each response to the server
        for (UserResponseEntity userResponse : userResponses) {
           submitUserResponseToServer(userResponse);
        }
    }



    //background call
    private void getResponsesA() {
        // Show the progress bar
       // ProgressBar progressBar = findViewById(R.id.progress);
        //progressBar.setVisibility(View.VISIBLE);
        //currentProgress =currentProgress + 10;
        //progressBar.setProgress(currentProgress);
        //progressBar.setMax(100);


        // Use AsyncTask to perform network request in the background
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
               // getAll();
                userResponses = allQuestionDatabase.userResponseDao().getUserResponsesForQuestionnaire(IDvalue);
                //try

                for (UserResponseEntity userResponseEntity:userResponses) {
                    Log.d("QuetionnaireID", String.valueOf(userResponseEntity.getQuestionnaireId()));
                    Log.d("QuetionID", String.valueOf(userResponseEntity.getQuestionId()));
                    Log.d("Option", String.valueOf(userResponseEntity.getOption()));

                    int questionnaireId = userResponseEntity.getQuestionnaireId();
                    int questionId =  userResponseEntity.getQuestionId();
                    String answer = userResponseEntity.getOption();

                    // QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId,questionnaireName, questionnaireDescription, questionnaireCreatedAt, questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);
                    UserResponseEntity userResponseEntity1 = new UserResponseEntity(questionnaireId, questionId, answer);
                    // UserResponseEntity userResponseEntity1 = new UserResponseEntity(userResponseEntity.getQuestionnaireId(), userResponseEntity.getQuestionId(), userResponseEntity.getOption());
                    userResponseEntities.add(userResponseEntity1);

                    // adapter.setUser(userResponseEntities);
                    // adapter.setUser(userResponseEntities);
                    adapter.setUser2(userResponseEntities);
                }
                if (userResponseEntities.isEmpty()){
                    Toast.makeText(ResponseData.this, "No Responses for this Quetionnaire", Toast.LENGTH_SHORT).show();
                }
                // Perform your network request here
                // This runs in a background thread
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // Hide the progress bar when data fetching is complete
               // progressBar.setVisibility(View.GONE);


                // Update your UI with the fetched data
            }
        }.execute();
    }


}