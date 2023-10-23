package com.mhealthkenya.psurvey.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.mhealthkenya.psurvey.adapters.activeSurveyAdapter;
import com.mhealthkenya.psurvey.models.ActiveSurveys;
import com.mhealthkenya.psurvey.models.AnswerEntity;
import com.mhealthkenya.psurvey.models.QueryID;
import com.mhealthkenya.psurvey.models.QuerynareID;
import com.mhealthkenya.psurvey.models.QuestionEntity;
import com.mhealthkenya.psurvey.models.QuestionnaireEntity;
import com.mhealthkenya.psurvey.models.UrlTable;

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
    long questionnaireIdInserted;
    long questionnaireIdInserted1;
    long questionIdInserted;
    int questionnaireId;

    //adapter
    public QuestionnairesAdapterOffline questionnairesAdapterOffline;
    public QuestionnaireEntity questionnaireEntity;
    public ArrayList<QuestionnaireEntity> questionnaireEntities;

    RecyclerView recyclerView;
    QuestionnaireEntity questionnaireEntity2;
    QuestionEntity questionEntity;
    AnswerEntity answerEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaires_offline);
        allQuestionDatabase = AllQuestionDatabase.getInstance(this);
        mHandler=new Handler();

        requestQueue = Volley.newRequestQueue(this);
        button =    findViewById(R.id.get_all);

        //adapter
        recyclerView=findViewById(R.id.recyclerViewOffline);

        questionnaireEntities = new ArrayList<>();
        questionnairesAdapterOffline = new QuestionnairesAdapterOffline(this, questionnaireEntities);

        questionnairesAdapterOffline.setOnItemClickListener(new QuestionnairesAdapterOffline.OnItemClickListener() {
            @Override
            public void onItemClick(QuestionnaireEntity questionnaireEntity) {

                Intent ii=new Intent(QuestionnairesOffline.this, QuetionsOffline.class);
                ii.putExtra("ID",  questionnaireEntity.getId());

                startActivity(ii);

               // Toast.makeText(QuestionnairesOffline.this, String.valueOf( questionnaireEntity.getId()), Toast.LENGTH_LONG).show();

            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        //set data and list adapter
        recyclerView.setAdapter(questionnairesAdapterOffline);


        //getAll();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Log.d("ALl", "ERRRRRRRRR");

                getAll();
            }
        });
        RetrieveQuestionnaire();


    }


    public void getAll(){
        String url = "https://psurveyapitest.kenyahmis.org/api/questions/dep/all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("ALl", "SUCCESS");
                Log.d("ALl", response.toString());

                for (int i=0; i<response.length(); i++ ){
                    try {
                         jsonObject=response.getJSONObject(i);







                        //-JSONArray jsonArray = jsonObject.getJSONArray("question");
                       // String questionText = jsonArray.getString("question");
                        //Log.d("QUESTION", questionText);

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

                        // Insert the QuestionnaireEntity into the Room database
                        questionnaireIdInserted = allQuestionDatabase.questionnaireDao().insert(questionnaireEntity2);

                        QuerynareID.deleteAll(QuerynareID.class);
                        QuerynareID querynareID =new QuerynareID(questionnaireIdInserted);
                        QuerynareID.save(querynareID);





                      //allQuestionDatabase.questionnaireDao().insert(questionnaireEntity2);
                     // RetrieveQuestionnaire();


                        /*QuestionnaireEntity retrievedQuestionnaire = allQuestionDatabase.questionnaireDao().getQuestionnaireById((int) questionnaireIdInserted);

                        if (retrievedQuestionnaire != null) {
                            // You have the retrieved questionnaire entity
                            Log.d("ALl", "data in questionnaire");
                            Log.d("ALl", retrievedQuestionnaire.getName());
                            Log.d("ALl", retrievedQuestionnaire.getDescription());


                        } else {
                            // No matching questionnaire found
                        }*/
                        RetrieveQuestionnaire();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

               try {






                    // Parse and insert the questions and answers
                   // JSONArray questionsArray = response.getJSONArray("questions");
                   JSONArray jsonArray = jsonObject.getJSONArray("questions");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject questionObject = jsonArray.getJSONObject(i);

                        int questionId = questionObject.getInt("id");
                        String questionText = questionObject.getString("question");
                        int questionType = questionObject.getInt("question_type");
                        int questionOrder = questionObject.getInt("question_order");
                        boolean isRequired = questionObject.getBoolean("is_required");

                        Log.d("QUESTIOOOOOOOOOON",questionText );

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
                        questionIdInserted = allQuestionDatabase.questionDao().insert(questionEntity);
                        QueryID.deleteAll(QueryID.class);
                        QueryID queryID =new QueryID(questionIdInserted);
                        QuerynareID.save(queryID);

                       questionIdInserted=allQuestionDatabase.questionDao().insert(questionEntity);


                        // Parse and insert answers
                       JSONArray answersArray = questionObject.getJSONArray("answers");
                        for (int j = 0; j < answersArray.length(); j++) {
                            JSONObject answerObject = answersArray.getJSONObject(j);

                            int answerId = answerObject.getInt("id");
                            String answerOption = answerObject.getString("option");
                            Log.d("ANSWERRRRRRRRR", answerOption);

                            // Create and insert the AnswerEntity
                             answerEntity = new AnswerEntity();
                            answerEntity.setId(answerId);
                            answerEntity.setQuestionId(questionIdInserted);
                            answerEntity.setOption(answerOption);
                            answerEntity.setCreatedAt(answerObject.getString("created_at"));
                            answerEntity.setCreatedBy(answerObject.getInt("created_by"));

                            // Insert the AnswerEntity into the Room database
                          //  allQuestionDatabase.answerDao().insert(answerEntity);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }





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

        try {
            List<QuerynareID> _url =QuerynareID.findWithQuery(QuerynareID.class, "SELECT *from QUERYNARE_ID ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    questionnaireIdInserted1=_url.get(x).getQuestinareID();
                    //zz=_url.get(x).getStage_name1();
                  //  Toast.makeText(QuestionnairesOffline.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();


                }
            }

        }catch (Exception e){

        }

     //   questionnaireIdInserted = allQuestionDatabase.questionnaireDao().insert(questionnaireEntity2);
      //  questionIdInserted = allQuestionDatabase.questionDao().insert(questionEntity);
        /* (questionnaireIdInserted==0){
            Toast
        }*/

        //questionnaireIdInserted = allQuestionDatabase.questionnaireDao().insert(questionnaireEntity2);
        //allQuestionDatabase.questionDao().getQuestionById(1);
      QuestionnaireEntity retrievedQuestionnaire = allQuestionDatabase.questionnaireDao().getQuestionnaireById((int) questionnaireIdInserted1);


    //QuestionnaireEntity retrievedQuestionnaire = allQuestionDatabase.questionnaireDao().geAllQuestionnaires();
    //List<QuestionnaireEntity>  retrievedQuestionnaire = allQuestionDatabase.questionnaireDao().getAllQuestionnaires();


        if (retrievedQuestionnaire != null) {
            // You have the retrieved questionnaire entity
            Log.d("ALl", "data in questionnaire");
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
            //recyclerView.setAdapter(questionnairesAdapterOffline);
           questionnairesAdapterOffline.notifyDataSetChanged();
          // notifyAll();





        } else {
            // No matching questionnaire found
            Toast.makeText(QuestionnairesOffline.this, "No Data", Toast.LENGTH_LONG).show();
        }

    }
}