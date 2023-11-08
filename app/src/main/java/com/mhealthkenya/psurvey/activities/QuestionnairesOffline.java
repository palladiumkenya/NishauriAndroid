package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mhealthkenya.psurvey.R;
import com.mhealthkenya.psurvey.activities.auth.LoginActivity;
import com.mhealthkenya.psurvey.adapters.QuestionnairesAdapterOffline;
import com.mhealthkenya.psurvey.interfaces.AnswerDao;
import com.mhealthkenya.psurvey.interfaces.QuestionDao;
import com.mhealthkenya.psurvey.interfaces.QuestionnaireDao;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.MyAsyncTask;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionnairesOffline extends AppCompatActivity {

    JSONObject jsonObject;
    Handler mHandler;

    private RequestQueue requestQueue;
    Button button;
    AllQuestionDatabase allQuestionDatabase;
    int questionnaireIdInserted;
    int questionnaireIdInserted1;
    int questionIdInserted;
    int questionnaireId;

     ProgressDialog pDialog;

    //adapter
    public QuestionnairesAdapterOffline questionnairesAdapterOffline;
    public QuestionnaireEntity questionnaireEntity;
    public ArrayList<QuestionnaireEntity> questionnaireEntities;

    RecyclerView recyclerView;
    QuestionnaireEntity questionnaireEntity2;
    QuestionEntity questionEntity;
    AnswerEntity answerEntity;
    MyAsyncTask1 myAsyncTask1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaires_offline);
        allQuestionDatabase = AllQuestionDatabase.getInstance(this);
        pDialog = new ProgressDialog(QuestionnairesOffline.this);
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


        //  questionnairesAdapterOffline.setUser(questionnaireEntities);
        MyAsyncTask myAsyncTask = new MyAsyncTask(this, "https://psurveyapitest.kenyahmis.org/api/questions/dep/all", allQuestionDatabase.questionnaireDao(), allQuestionDatabase.questionDao(), allQuestionDatabase.answerDao());
      //  MyAsyncTask1 myAsyncTask1 = new MyAsyncTask1();
         myAsyncTask1 = new MyAsyncTask1(allQuestionDatabase, questionnairesAdapterOffline);



        questionnairesAdapterOffline.setOnItemClickListener(new QuestionnairesAdapterOffline.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                QuestionnaireEntity questionnaireEntity =questionnaireEntities.get(position);
               

                /*Intent ii=new Intent(QuestionnairesOffline.this, QuetionsOffline.class);
                ii.putExtra("ID",  questionnaireEntity.getId());

                startActivity(ii);*/
                QuestionEntity questions = allQuestionDatabase.questionDao().getQuestionsOrderedByQuestionId(questionnaireEntity.getId());

                Toast.makeText(QuestionnairesOffline.this, String.valueOf( questions.getId()), Toast.LENGTH_LONG).show();

            }
        });






        //getAll();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myAsyncTask.execute();



                //  BackgroundTask task1111 = new BackgroundTask();
                //task1111.execute();

                //getAll();
            }
        });

        //RetrieveQuestionnaire();
        myAsyncTask1.execute();

    }


    public void getAll(){
        String url = "https://psurveyapitest.kenyahmis.org/api/questions/dep/all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                pDialog.setTitle("Signing In...");
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();

                Log.d("ALl", "SUCCESS");
                Log.d("ALl", response.toString());

                for (int i=0; i<response.length(); i++ ){
                    try {

                         jsonObject=response.getJSONObject(i);


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

                        for (int j = 0; j < jsonArray.length(); i++) {
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
                           /* questionEntity.setDateValidation(null); // You may set this as needed
                            questionEntity.setRepeatable(false); // You may set this as needed
                            questionEntity.setResponseColName(null); // You may set this as needed
                            questionEntity.setCreatedAt(questionObject.getString("created_at"));
                            questionEntity.setCreatedBy(questionObject.getInt("created_by"));*/

                            // Insert the QuestionEntity into the Room database
                            allQuestionDatabase.questionDao().insert(questionEntity);




                            // Parse and insert answers
                            JSONArray answersArray = questionObject.getJSONArray("answers");
                            for (int k = 0; k< answersArray.length(); k++) {
                                JSONObject answerObject = answersArray.getJSONObject(j);

                                int answerId = answerObject.getInt("id");
                                String answerOption = answerObject.getString("option");

                                // Create and insert the AnswerEntity
                                answerEntity = new AnswerEntity();
                                answerEntity.setId(answerId);
                               // answerEntity.setQuestionId(questionIdInserted);
                                answerEntity.setOption(answerOption);
                                answerEntity.setCreatedAt(answerObject.getString("created_at"));
                                answerEntity.setCreatedBy(answerObject.getInt("created_by"));

                                allQuestionDatabase.answerDao().insert(answerEntity);

                                // Insert the AnswerEntity into the Room database

                         }
                        }
                       // RetrieveQuestionnaire();
                        myAsyncTask1.execute();

                         //RetrieveQuestionnaire();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
               // RetrieveQuestionnaire();
                pDialog.dismiss();

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

   /* public class BackgroundTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            // This method runs on a background thread
            // Perform your background tasks here
           // return "Task Completed";
            getAll();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // This method runs on the main (UI) thread after doInBackground finishes
            // Update the UI with the result+
           // textView.setText(result);
          //  Toast.makeText(QuestionnairesOffline.this, "DOne", Toast.LENGTH_LONG);
            //RetrieveQuestionnaire();
        }
    }*/

    // To execute the AsyncTask:




//background
public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

     Context context;
     String url;
    QuestionnaireDao questionnaireDao;
    QuestionDao questionDao;
    AnswerDao answerDao;

    QuestionnaireEntity questionnaireEntity2;
    QuestionEntity questionEntity;
    AnswerEntity answerEntity;


    JSONObject jsonObject;
    Handler mHandler;

    private RequestQueue requestQueue;
    Button button;
    AllQuestionDatabase allQuestionDatabase;
    long questionnaireIdInserted;
    long questionnaireIdInserted1;
    long questionIdInserted;
    int questionnaireId;

    public MyAsyncTask(Context context, String url, QuestionnaireDao questionnaireDao, QuestionDao questionDao, AnswerDao answerDao) {
        this.context = context;
        this.url = url;
        this.questionnaireDao = questionnaireDao;
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @Override
    protected Void doInBackground(Void... params) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);

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

                          //  allQuestionDatabase.questionnaireDao().insert(questionnaireEntity2);
                            questionnaireDao.insert(questionnaireEntity2);

                            //questions
                            JSONArray jsonArray = jsonObject.getJSONArray("questions");

                            for (int j = 0; j < jsonArray.length(); i++) {
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
                               // allQuestionDatabase.questionDao().insert(questionEntity);
                                questionDao.insert(questionEntity);

                                // Parse and insert answers
                                JSONArray answersArray = questionObject.getJSONArray("answers");
                                for (int k = 0; k< answersArray.length(); k++) {
                                    JSONObject answerObject = answersArray.getJSONObject(j);

                                    int answerId = answerObject.getInt("id");
                                    String answerOption = answerObject.getString("option");

                                    // Create and insert the AnswerEntity
                                    answerEntity = new AnswerEntity();
                                    answerEntity.setId(answerId);
                                    //answerEntity.setQuestionId(questionIdInserted);
                                    answerEntity.setOption(answerOption);
                                    answerEntity.setCreatedAt(answerObject.getString("created_at"));
                                    answerEntity.setCreatedBy(answerObject.getInt("created_by"));

                                    //allQuestionDatabase.answerDao().insert(answerEntity);
                                    answerDao.insert(answerEntity);

                                    // Insert the AnswerEntity into the Room database

                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                    // Handle error
                });

        requestQueue.add(jsonArrayRequest);
        return null;
    }

}



//retrie with background thread
public class MyAsyncTask1 extends AsyncTask<Void, Void, List<QuestionnaireEntity>> {

    private final AllQuestionDatabase allQuestionDatabase;
    private final QuestionnairesAdapterOffline questionnairesAdapterOffline;

    public MyAsyncTask1(AllQuestionDatabase allQuestionDatabase, QuestionnairesAdapterOffline questionnairesAdapterOffline) {
        this.allQuestionDatabase = allQuestionDatabase;
        this.questionnairesAdapterOffline = questionnairesAdapterOffline;
    }

    @Override
    protected List<QuestionnaireEntity> doInBackground(Void... params) {
        List<QuestionnaireEntity> questionnaireEntities = new ArrayList<>();
        List<QuestionnaireEntity> questionnaires = allQuestionDatabase.questionnaireDao().getAllQuestionnaires();
        for (QuestionnaireEntity retrievedQuestionnaire : questionnaires) {
            int questionnaireId = retrievedQuestionnaire.getId();
            String questionnaireName =  retrievedQuestionnaire.getName();
            String questionnaireDescription = retrievedQuestionnaire.getDescription();
            boolean questionnaireIsActive = retrievedQuestionnaire.isActive();
            String questionnaireCreatedAt = retrievedQuestionnaire.getCreatedAt();
            int questionnaireNumberOfQuestions = retrievedQuestionnaire.getNumberOfQuestions();
            String questionnaireActiveTill = retrievedQuestionnaire.getActiveTill();
            String questionnaireTargetApp =  retrievedQuestionnaire.getTargetApp();

            Log.d("name", questionnaireName);

            QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity(questionnaireId,questionnaireName, questionnaireDescription, questionnaireCreatedAt, questionnaireNumberOfQuestions, questionnaireActiveTill, questionnaireTargetApp);


            questionnaireEntities.add(questionnaireEntity);
        }

        return questionnaireEntities;
    }

    @Override
    protected void onPostExecute(List<QuestionnaireEntity> result) {
        questionnairesAdapterOffline.setUser(result);
    }
}






}