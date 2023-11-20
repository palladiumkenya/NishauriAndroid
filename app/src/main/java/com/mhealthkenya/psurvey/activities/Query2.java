package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.adapters.QuestionnairesAdapterOffline;
import com.mhealthkenya.psurvey.depedancies.Constants;
import com.mhealthkenya.psurvey.interfaces.AnswerDao;
import com.mhealthkenya.psurvey.interfaces.QuestionDao;
import com.mhealthkenya.psurvey.interfaces.QuestionnaireDao;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.SurveyID;
import com.orm.SugarContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Query2 extends AppCompatActivity {
    ProgressBar progressBar;
    int currentProgress=0;

    JSONObject jsonObject;
    Handler mHandler;

    private RequestQueue requestQueue;
    Button button;
    AllQuestionDatabase allQuestionDatabase;
    long questionnaireIdInserted;
    long questionnaireIdInserted1;
    int questionIdInserted;
    int questionnaireId;
    int questionId;
    ProgressDialog pDialog;

    //adapter
    public QuestionnairesAdapterOffline questionnairesAdapterOffline;
    public QuestionnaireEntity questionnaireEntity;
    public ArrayList<QuestionnaireEntity> questionnaireEntities;

    RecyclerView recyclerView;
    QuestionnaireEntity questionnaireEntity2;
    QuestionEntity questionEntity;
    AnswerEntity answerEntity;
    TextView total1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query2);
        //SugarContext.init(this);
        pDialog = new ProgressDialog(Query2.this);
        total1 =findViewById(R.id.total);


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


       fetchDataFromServer();





        //getAll();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // myAsyncTask.execute();



                //  BackgroundTask task1111 = new BackgroundTask();
                //task1111.execute();

              //  getAll();
                fetchDataFromServer();

            }
        });

        RetrieveQuestionnaire();
        total1.setText("Completed Surveys"+" "+Constants.counter);
        //myAsyncTask1.execute();

    }




    public void getAll(){
        /*ProgressBar progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        currentProgress =currentProgress + 10;
        progressBar.setProgress(currentProgress);
        progressBar.setMax(100);*/

        String url = "https://psurveyapitest.kenyahmis.org/api/questions/dep/all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("ALl", "SUCCESS");
                Log.d("ALl", response.toString());

                for (int i=0; i<response.length(); i++ ){
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
                        questionnaireEntity2.setCreatedBy(14); // You may set this as needed

                        allQuestionDatabase.questionnaireDao().insert(questionnaireEntity2);


                        //questions
                        JSONArray jsonArray = jsonObject.getJSONArray("questions");

                        for (int j=0; j<jsonArray.length(); j++) {
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
                            for (int k=0; k<answersArray.length(); k++) {
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
             //   progressBar.setVisibility(View.GONE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ALl", error.getMessage());

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

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

            QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId,questionnaireName, questionnaireDescription, questionnaireCreatedAt, questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);

            questionnaireEntities.add(questionnaireEntity);
            questionnairesAdapterOffline.setUser(questionnaireEntities);

        }

    }
    //background call
    private void fetchDataFromServer() {
        // Show the progress bar
        ProgressBar progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        currentProgress =currentProgress + 10;
        progressBar.setProgress(currentProgress);
        progressBar.setMax(100);


        // Use AsyncTask to perform network request in the background
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                getAll();
                // Perform your network request here
                // This runs in a background thread
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // Hide the progress bar when data fetching is complete
                progressBar.setVisibility(View.GONE);


                // Update your UI with the fetched data
            }
        }.execute();
    }



}