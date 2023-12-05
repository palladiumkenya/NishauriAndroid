package com.mhealthkenya.psurvey.activities;

import static com.mhealthkenya.psurvey.depedancies.AppController.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.adapters.QuestionnairesAdapterOffline;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.interfaces.AnswerDao;
import com.mhealthkenya.psurvey.interfaces.QuestionDao;
import com.mhealthkenya.psurvey.interfaces.QuestionnaireDao;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.SurveyID;
import com.mhealthkenya.psurvey.models.UrlTable;
import com.mhealthkenya.psurvey.models.auth;
import com.orm.SugarContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Query2 extends AppCompatActivity {
    ProgressBar progressBar;
    int currentProgress=0;

    //android:name="com.orm.SugarApp"

   // ProgressBar progressBar;

    public String z;

    JSONObject jsonObject;
    Handler mHandler;

    private auth loggedInUser;

    private RequestQueue requestQueue;
    Button button;
    AllQuestionDatabase allQuestionDatabase;
    long questionnaireIdInserted;
    long questionnaireIdInserted1;
    int questionIdInserted;
    int questionnaireId;
    int questionId;
    //ProgressDialog pDialog;
   //  ProgressDialog progressDialog;
    //adapter
    public QuestionnairesAdapterOffline questionnairesAdapterOffline;
    public QuestionnaireEntity questionnaireEntity;
    public ArrayList<QuestionnaireEntity> questionnaireEntities;

    RecyclerView recyclerView;
    QuestionnaireEntity questionnaireEntity2;
    QuestionEntity questionEntity;
    AnswerEntity answerEntity;
    //TextView total1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query2);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Facility's Survey");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set the toolbar navigation icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Handle the back arrow click to navigate to the previous activity
            }
        });

        loggedInUser = (auth) Stash.getObject(Constants.AUTH_TOKEN, auth.class);

        //SugarContext.init(this);

       // progressDialog = new ProgressDialog(this);


        progressBar = findViewById(R.id.progress);
       // pDialog = new ProgressDialog(Query2.this);
       // total1 =findViewById(R.id.total);



        allQuestionDatabase = AllQuestionDatabase.getInstance(this);
        mHandler=new Handler();

        requestQueue = Volley.newRequestQueue(this);
        button =findViewById(R.id.get_all);

        //adapter
        recyclerView=findViewById(R.id.recyclerViewOffline);
        questionnaireEntities = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        questionnairesAdapterOffline = new QuestionnairesAdapterOffline(this);
        recyclerView.setAdapter(questionnairesAdapterOffline);

        questionnairesAdapterOffline.setOnItemClickListener(new QuestionnairesAdapterOffline.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                QuestionnaireEntity questionnaireEntity =questionnaireEntities.get(position);
                try {
                    SurveyID.deleteAll(SurveyID.class);
                    SurveyID surveyID = new SurveyID( questionnaireEntity.getId());
                    surveyID.save();

                }catch (Exception e){
                    e.printStackTrace();
                }


                Intent ii=new Intent(Query2.this, QuetionsOffline.class);
             //   ii.putExtra("ID",  questionnaireEntity.getId());
                startActivity(ii);
               // QuestionEntity questions = allQuestionDatabase.questionDao().getQuestionsOrderedByQuestionId(questionnaireEntity.getId());

              //  Toast.makeText(Query2.this, String.valueOf( questionnaireEntity.getId()), Toast.LENGTH_LONG).show();

            }
        });


       //fetchDataFromServer();

//       callback();






        //getAll();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* progressDialog.setMessage("Loading Surveys...");
                progressDialog.setCancelable(false); // Set to true if you want the dialog to be cancelable
                progressDialog.setIndeterminate(true); // Set to false if you want a progress bar instead of an indeterminate spinner
                progressDialog.show();*/

                callback();


               // myAsyncTask.execute();



                //  BackgroundTask task1111 = new BackgroundTask();
                //task1111.execute();

              //  getAll();
    //
                //            fetchDataFromServer();

            }
        });

       // RetrieveQuestionnaire();
        new RetrieveQuestionnaireTask(allQuestionDatabase, questionnaireEntities, questionnairesAdapterOffline).execute();

       // total1.setText("Completed Surveys"+" "+Constants.counter);
        //myAsyncTask1.execute();

    }




    public void getAll(){
        /*ProgressBar progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        currentProgress =currentProgress + 10;
        progressBar.setProgress(currentProgress);
        progressBar.setMax(100);*/
        //int totalItems = 100;



        String url = "https://psurveyapitest.kenyahmis.org/api/questions/dep/all";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

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

                            allQuestionDatabase.questionnaireDao().insert(questionnaireEntity2);




                            //questions
                            JSONArray jsonArray = jsonObject.getJSONArray("questions");

                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject questionObject = jsonArray.getJSONObject(j);

                                int questionId = questionObject.getInt("id");
                                String questionText = questionObject.getString("question");
                                int questionType = questionObject.getInt("question_type");
                                int questionOrder = questionObject.getInt("question_order");
                                boolean isRequired = questionObject.getBoolean("is_required");

                                // Create and insert the QuestionEntity
                                questionEntity = new QuestionEntity();
                                questionEntity.setId(questionId);
                                questionEntity.setQuestionnaireId(questionnaireId);
                                questionEntity.setQuestion(questionText);
                                questionEntity.setQuestionType(questionType);
                                questionEntity.setQuestionOrder(questionOrder);
                                questionEntity.setRequired(isRequired);
                                questionEntity.setDateValidation(null); // You may set this as needed
                                questionEntity.setRepeatable(false); // You may set this as needed
                                questionEntity.setResponseColName(null); // You may set this as needed
                                questionEntity.setCreatedAt(questionObject.getString("created_at"));
                                questionEntity.setCreatedBy(questionObject.getInt("created_by"));

                                // Insert the QuestionEntity into the Room database
                                allQuestionDatabase.questionDao().insert(questionEntity);

                                //}


                                // Parse and insert answers
                                JSONArray answersArray = questionObject.getJSONArray("answers");
                                // JSONArray answersArray = jsonObject.getJSONArray("answers");
                                for (int k = 0; k < answersArray.length(); k++) {
                                    JSONObject answerObject = answersArray.getJSONObject(k);

                                    int answerId = answerObject.getInt("id");
                                    String answerOption = answerObject.getString("option");

                                    Log.d("ANSWER OPTION", answerOption);

                                    // Create and insert the AnswerEntity
                                    answerEntity = new AnswerEntity();
                                    answerEntity.setId(answerId);
                                    answerEntity.setQuestionId(questionId);
                                    answerEntity.setQuestionnaireId(questionnaireId);
                                    answerEntity.setOption(answerOption);
                                    answerEntity.setCreatedAt(answerObject.getString("created_at"));
                                    answerEntity.setCreatedBy(answerObject.getInt("created_by"));


                                    allQuestionDatabase.answerDao().insert(answerEntity);

                                    // Insert the AnswerEntity into the Room database

                                }
                            }
                            // RetrieveQuestionnaire();
                            // myAsyncTask1.execute();

                            //RetrieveQuestionnaire();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    RetrieveQuestionnaire();
                 //   progressDialog.dismiss();
                    //   progressBar.setVisibility(View.GONE);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ALl", error.getMessage());

                }
            });
            requestQueue.add(jsonArrayRequest);

            //end
        //}
    }


    //retrieve background
    public class RetrieveQuestionnaireTask extends AsyncTask<Void, Void, List<QuestionnaireEntity>> {

        private AllQuestionDatabase allQuestionDatabase;
        private List<QuestionnaireEntity> questionnaireEntities;
        private QuestionnairesAdapterOffline questionnairesAdapterOffline;

        public RetrieveQuestionnaireTask(AllQuestionDatabase allQuestionDatabase, List<QuestionnaireEntity> questionnaireEntities, QuestionnairesAdapterOffline questionnairesAdapterOffline) {
            this.allQuestionDatabase = allQuestionDatabase;
            this.questionnaireEntities = questionnaireEntities;
            this.questionnairesAdapterOffline = questionnairesAdapterOffline;
        }

        @Override
        protected List<QuestionnaireEntity> doInBackground(Void... voids) {
            return allQuestionDatabase.questionnaireDao().getAllQuestionnaires();
        }

        @Override
        protected void onPostExecute(List<QuestionnaireEntity> questionnaires) {
            super.onPostExecute(questionnaires);

            for (QuestionnaireEntity retrievedQuestionnaire : questionnaires) {
                Log.d("ALl", retrievedQuestionnaire.getName());
                Log.d("ALl", retrievedQuestionnaire.getDescription());

                int questionnaireId = retrievedQuestionnaire.getId();
                String questionnaireCreatedAt = retrievedQuestionnaire.getCreatedAt();
                String questionnaireDescription = retrievedQuestionnaire.getDescription();
                int questionnaireNumberOfQuestions = retrievedQuestionnaire.getNumberOfQuestions();
                String questionnaireActiveTill = retrievedQuestionnaire.getActiveTill();
                String questionnaireTargetApp = retrievedQuestionnaire.getTargetApp();

                String questionnaireName = retrievedQuestionnaire.getName();

                boolean questionnaireIsActive = retrievedQuestionnaire.isActive();


                QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId, questionnaireCreatedAt, questionnaireName, questionnaireDescription,  questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);
               // QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId, questionnaireCreatedAt,  questionnaireDescription,  questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);
                                                              //QuestionnaireEntity(int id, String createdAt, String name, String description, int numberOfQuestions, String activeTill, String targetApp)

                questionnaireEntities.add(questionnaireEntity);
                questionnairesAdapterOffline.setUser(questionnaireEntities);
            }
        }
    }



    //end background

    public void RetrieveQuestionnaire(){

        List<QuestionnaireEntity> questionnaires = allQuestionDatabase.questionnaireDao().getAllQuestionnaires();
        for (QuestionnaireEntity retrievedQuestionnaire:questionnaires) {
            Log.d("ALl", retrievedQuestionnaire.getName());
            Log.d("ALl", retrievedQuestionnaire.getDescription());

            int questionnaireId = retrievedQuestionnaire.getId();
            String questionnaireName =  retrievedQuestionnaire.getName();
            String questionnaireDescription = retrievedQuestionnaire.getDescription();
            boolean questionnaireIsActive = retrievedQuestionnaire.isActive();
            String questionnaireCreatedAt = retrievedQuestionnaire.getCreatedAt();
            int questionnaireNumberOfQuestions = retrievedQuestionnaire.getNumberOfQuestions();
            String questionnaireActiveTill = retrievedQuestionnaire.getActiveTill();
            String questionnaireTargetApp =  retrievedQuestionnaire.getTargetApp();

            QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId, questionnaireCreatedAt, questionnaireName, questionnaireDescription,  questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);
            //QuestionnaireEntity(int id, String createdAt, String name, String description, int numberOfQuestions, String activeTill, String targetApp)

            questionnaireEntities.add(questionnaireEntity);
            questionnairesAdapterOffline.setUser(questionnaireEntities);

            Log.d("Number of Quetionnaires", String.valueOf(questionnaireEntities.size()));
          //  Toast.makeText(Query2.this, "SUrveys"+""+String.valueOf(questionnaireEntities.size()), Toast.LENGTH_LONG).show();



        }
  //      Toast.makeText(Query2.this, "SUrveys"+""+String.valueOf(questionnaireEntities.size()), Toast.LENGTH_LONG).show();

    }


    public class  FetchDataAsyncTask extends AsyncTask<Void, Void, Void>{


        private Context context;
        private ProgressDialog progressDialog1;

        public FetchDataAsyncTask(Context context) {
            this.context = context;
        }




        @Override
        protected void onPreExecute() {
            super.onPreExecute();

         //   progressBar.setVisibility(View.VISIBLE);

            //2nd dialo
            progressDialog1 = new ProgressDialog(context);
            progressDialog1.setMessage("Loading Surveys...");
            progressDialog1.setCancelable(false); // Set to true if you want the dialog to be cancelable
            progressDialog1.setIndeterminate(true); // Set to false if you want a progress bar instead of an indeterminate spinner
            progressDialog1.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
          //  progressDialog.dismiss();


            // Dismiss the ProgressDialog when the task is complete
      /*      if (progressDialog1 != null && progressDialog1.isShowing()) {
                progressDialog1.dismiss();
            }*/

         //   progressBar.setVisibility(View.GONE);
           progressDialog1.dismiss();

        }


    }

    private void callback(){
        new FetchDataAsyncTask(this).execute();

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

        AndroidNetworking.post(z+Constants.LOGOUT)
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

                                Intent intent = new Intent(Query2.this, LoginActivity.class);
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

                            Intent intent = new Intent(Query2.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }
                        else{

                            Toast.makeText(Query2.this, ""+error.getErrorBody(), Toast.LENGTH_SHORT).show();


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