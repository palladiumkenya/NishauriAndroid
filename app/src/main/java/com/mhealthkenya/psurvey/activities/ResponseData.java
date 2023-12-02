package com.mhealthkenya.psurvey.activities;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.adapters.UserResponseAdapter;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.UserResponseEntity;
import com.mhealthkenya.psurvey.models.auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResponseData extends AppCompatActivity {

    public String z;
    private auth loggedInUser;






    Button btnsubmit;
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
        btnsubmit = (Button) findViewById(R.id.submit_all);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Responses");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set the toolbar navigation icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Handle the back arrow click to navigate to the previous activity
            }
        });
        // Initialize your RecyclerView

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);



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
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ResponseData.this, "Coming Soon", Toast.LENGTH_SHORT).show();


               // callSubmit();
            }
        });

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

            adapter.setUser2(userResponseEntities);
        }
        if (userResponseEntities.isEmpty()){
            getAlert();
           // Toast.makeText(ResponseData.this, "No Responses for this Quetionnaire", Toast.LENGTH_SHORT).show();
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



    //Alert
    private void getAlert(){

       /* try {


            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                    zz=_url.get(x).getStage_name1();
                    //Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.d("No baseURL", e.getMessage());
        }*/



        AlertDialog.Builder builder1 = new AlertDialog.Builder(ResponseData.this);
        builder1.setIcon(R.drawable.logo);
        builder1.setTitle("No Responses for This Questionnaire");
       // builder1.setMessage( zz);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(ResponseData.this, Query2.class);
                        startActivity(intent);
                        finish();

                        //dialog.cancel();
                    }
                });

        /*builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Config.this, SelectUrls.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
    public void callSubmit(){

        // Assume userResponses is a List<UserResponseEntity> containing your responses

// Create the main JSON object
        JSONObject mainJsonObject = new JSONObject();
        try {
            mainJsonObject.put("questionnaire_id", 55);

            // Create the responses array
            JSONArray responsesArray = new JSONArray();

            // Iterate through userResponses and create JSON objects for each response
            for (UserResponseEntity userResponse : userResponses) {
                JSONObject responseObj = new JSONObject();

                // Set values for responseObj (adjust these according to your data model)
                responseObj.put("ccc_number", "12345678");
                responseObj.put("first_name", "");
                responseObj.put("questionnaire_participant_id", 1);
                responseObj.put("informed_consent", true);
                responseObj.put("privacy_policy", true);
                responseObj.put("interviewer_statement", true);

                // Create the question_answers array
                JSONArray questionAnswersArray = new JSONArray();

                // Add question-answer pairs to the question_answers array
                // Adjust these according to your data model
          //      questionAnswersArray.put(createQuestionAnswer("430", String.valueOf(userResponse.getQuestionId()), ""));

                questionAnswersArray.put(createQuestionAnswer("430", String.valueOf(userResponse.getQuestionId()), userResponse.getOption()));

                // Add question_answers array to the responseObj
                responseObj.put("question_answers", questionAnswersArray);

                // Add responseObj to the responses array
                responsesArray.put(responseObj);
            }

            // Add responses array to the main JSON object
            mainJsonObject.put("responses", responsesArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

// Create a JsonObjectRequest
        String url = "https://psurveyapitest.kenyahmis.org/api/questions/answers/all/"; // Replace with your server endpoint
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                mainJsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server
                        // You may want to update UI or perform other actions here
                        Log.d("success", response.toString());

                        Toast.makeText(ResponseData.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Errror", "OnError");
                        // Handle errors
                    }
                }
        );

// Add the request to the Volley request queue
       // requestQueue.add(jsonObjectRequest);
        Volley.newRequestQueue(this).add(jsonObjectRequest);



    }


    // Helper method to create a question answer JSONObject
    private JSONObject createQuestionAnswer(String question, String answer, String openText) throws JSONException {
        JSONObject questionAnswerObject = new JSONObject();
        questionAnswerObject.put("question", question);
        questionAnswerObject.put("answer", answer);
        questionAnswerObject.put("open_text", openText);
        return questionAnswerObject;
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
                break;

            case R.id.action_about_app:

                aboutAppDialog();
                break;

            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
    public void logout(){

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

        AndroidNetworking.post(z+ Constants.LOGOUT)
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

                        Log.e(TAG, response.toString());



                        try {
                            boolean  status = response.has("success") && response.getBoolean("success");
                            String error = response.has("error") ? response.getString("error") : "";
                            String message = response.has("message") ? response.getString("message") : "";

                            if (status){

                                String endPoint = Stash.getString(Constants.AUTH_TOKEN);
                                Stash.clearAll();
                                Stash.put(Constants.AUTH_TOKEN, endPoint);

                                Intent intent = new Intent(ResponseData.this, LoginActivity.class);
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
                        Log.e(TAG, String.valueOf(error.getErrorCode()));


                        if (error.getErrorCode() == 0){

                            String endPoint = Stash.getString(Constants.AUTH_TOKEN);
                            Stash.clearAll();
                            Stash.put(Constants.AUTH_TOKEN, endPoint);

                            Intent intent = new Intent(ResponseData.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                        else{

                            Toast.makeText(ResponseData.this, ""+error.getErrorBody(), Toast.LENGTH_SHORT).show();


                        }

                    }
                });


    }
    public void aboutAppDialog(){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_about);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;//Version Name
            int verCode = pInfo.versionCode;//Version Code

            ((TextView) dialog.findViewById(R.id.tv_version)).setText("Version: " + version);

            ((TextView) dialog.findViewById(R.id.tv_build)).setText("Build: " + String.valueOf(verCode));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }



}